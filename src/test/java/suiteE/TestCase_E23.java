package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E23 extends TestBase {
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
		test = extent.startTest(var, "Verify that user is able to unwatch a Patent from watchlist page")
				.assignCategory("Suite E");

	}

	@Test
	@Parameters({ "patentName" })
	public void testUnwathPatentsFromWatchlist(String patentName) throws Exception {

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

			// Opening browser
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			ob.get(host);
			loginAsSpecifiedUser(user1, CONFIG.getProperty("defaultPassword"));
			// Delete first watch list
			deleteFirstWatchlist();
			waitForPageLoad(ob);
			// Create watch list
			createWatchList("private", "TestWatchlist2", "This is my test watchlist.");

			// Searching for patent
			selectSearchTypeFromDropDown("Patents");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(patentName);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath("//div[@class='search-page-results']"), 30);

			// Getting watch button list for patents
			List<WebElement> watchButtonList = ob.findElements(By.xpath(OR.getProperty("search_watchlist_image")));

			String selectedWatchlistName = null;
			// Watching 2 patents to a particular watch list
			for (int i = 0; i < 2; i++) {
				WebElement watchButton = watchButtonList.get(i);
				selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);
				((JavascriptExecutor) ob).executeScript("arguments[0].scrollIntoView(true);", watchButton);
				Thread.sleep(2000);
			}

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(selectedWatchlistName);
			waitForPageLoad(ob);
			// Getting the first result title
			String firstDocumentTitle = ob.findElement(By.xpath(OR.getProperty("result_title_in_watchlist"))).getText();

			// Unwatching the first document from results
			ob.findElement(By.xpath(OR.getProperty("watchlist_watchlist_image"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("result_title_in_watchlist")), 30);
			// Checking if first document still exists in the watch list
			List<WebElement> documentList = ob.findElements(By.xpath(OR.getProperty("result_title_in_watchlist")));
			int count = 0;
			String documentTitle;
			for (WebElement document : documentList) {
				documentTitle = document.getText();
				if (documentTitle.equals(firstDocumentTitle))
					count++;
			}

			try {
				Assert.assertEquals(0, count);
				test.log(LogStatus.PASS, "User is able to unwatch a Patent from watchlist page");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "User is unable to unwatch a Patent from watchlist page");
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
