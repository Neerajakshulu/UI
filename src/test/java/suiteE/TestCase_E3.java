package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E3 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent.startTest(var, "Verify that user is able to delete a document from watchlist")
				.assignCategory("Suite E");

	}

	@Test
	@Parameters({ "userName", "password" })
	public void testcaseE3(String userName, String password) throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "E Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteExls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			String search_query = "kernel";

			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);
			Thread.sleep(8000);

			// login using TR credentials
			LoginTR.enterTRCredentials(userName, password);
			LoginTR.clickLogin();

			Thread.sleep(15000);
			// Clean watchlist
			cleanWatchlist();
			// 2)Add some documents to watchlist
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);

			List<WebElement> mylist = ob.findElements(By.xpath(OR.getProperty("search_watchlist_image")));

			WebElement ele;
			// Watching the documents from All content results page
			for (int i = 0; i < 5; i++) {
				ele = mylist.get(i);
				ele.click();
				((JavascriptExecutor) ob).executeScript("arguments[0].scrollIntoView(true);", ele);
				Thread.sleep(1000);
			}

			// 3)Go to watchlist page,delete all the articles and verify that
			// all the articles have been deleted
			cleanWatchlist();
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();
			Thread.sleep(4000);

			List<WebElement> total_documents = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));

			if (!compareNumbers(0, total_documents.size())) {

				test.log(LogStatus.FAIL, "User not able to delete a document from watchlist");// extent
																								// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_unable_to_delete_document_from_watchlist")));// screenshot

			}

			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "SKIP");

	}

}
