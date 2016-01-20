package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E11 extends TestBase {
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
		test = extent.startTest(var, "Verify that user is able to unwatch an Article from Record View page")
				.assignCategory("Suite E");

	}

	@Test
	public void testcaseE2() throws Exception {

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

			String search_query = "biology";

			openBrowser();
			maximizeWindow();
			clearCookies();

			// 1)Create new user and login
			createNewUser("mask", "man");
			// Navigate to home page
			/*ob.navigate().to(host);
			Thread.sleep(8000);
			// login using TR credentials
			LoginTR.enterTRCredentials("prasenjit.patra@thomsonreuters.com", "Techm@2015");
			LoginTR.clickLogin();*/

			// 2)Add an article to watchlist from record view page
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(8000);

			// Clicking on Articles content result set
			ob.findElement(By.cssSelector("li[ng-click='vm.updateSearchType(\"ARTICLES\")']")).click();
			Thread.sleep(8000);
			// Watching the first article
			ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).click();
			Thread.sleep(4000);
			// 3)Opening the record view page
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();

			Thread.sleep(8000);
			// 4)Unwatching the article from record view page
			ob.findElement(By.xpath(OR.getProperty("document_watchlist_button"))).click();
			Thread.sleep(2000);
			// Opening the watchlist page
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();
			Thread.sleep(8000);

			// 5)Verify that particular article has been removed from watchlist
			WebElement noResultPanel = ob.findElement(By.xpath("//div[@ng-show='noResults']"));

			if (!noResultPanel.isDisplayed()) {

				test.log(LogStatus.FAIL, "User not able to unwatch an article from record view page");// extent
																										// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_unable_to_unwatch_article_from_record_view_page")));// screenshot

			} else {
				test.log(LogStatus.PASS, "User is able to unwatch an article from record view page");// extent
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
