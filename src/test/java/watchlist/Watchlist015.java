package watchlist;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

/**
 * Verify that user is able to name the watchlists||Verify that a user can add description to his watchlist||Verify that
 * watchlist name is customizable
 * 
 * @author Prasenjit Patra
 *
 */
public class Watchlist015 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('E'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(var,
						"Verify that user is able to name the watchlists||Verify that a user can add description to his watchlist||Verify that watchlist name is customizable")
				.assignCategory("Watchlist");

	}

	@Test
	public void testEditWatchList() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Watchlist");
		boolean testRunmode = TestUtil.isTestCaseRunnable(watchlistXls, this.getClass().getSimpleName());
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

			ob.navigate().to(host);
			loginAsSpecifiedUser(LOGIN.getProperty("LOGINUSERNAME1"), LOGIN.getProperty("LOGINPASSWORD1"));

			// Create watch list
			String newWatchlistName = this.getClass().getSimpleName() + "_" + getCurrentTimeStamp();
			createWatchList("private", newWatchlistName, "This is my test watchlist.");
			// Editing the first watch list
			String watchlistName = this.getClass().getSimpleName() + "_" + getCurrentTimeStamp() + "_Updated";
			String watchlistDescription = "This is my watchlist";
			ob.findElement(By.xpath(OR.getProperty("edit_watch_list_button"))).click();
			waitForElementTobeVisible(ob, By.xpath("//div[@data-submit-callback='Workspace.submitWatchlistForm']"), 30);
			ob.findElement(By.xpath(OR.getProperty("newWatchListNameTextBox"))).clear();
			ob.findElement(By.xpath(OR.getProperty("newWatchListNameTextBox"))).sendKeys(watchlistName);
			ob.findElement(By.xpath(OR.getProperty("newWatchListDescriptionTextArea"))).clear();
			ob.findElement(By.xpath(OR.getProperty("newWatchListDescriptionTextArea"))).sendKeys(watchlistDescription);
			ob.findElement(By.xpath(OR.getProperty("watchListUpdateButton"))).click();
			waitForElementTobeVisible(ob, By.xpath("//a[@class='ng-binding']"), 30);
			String updatedWatchlistName = ob.findElement(By.xpath("//a[@class='ng-binding']")).getText();
			String updatedWatchlistDescription = ob
					.findElement(By.xpath("//p[@class='watchlist-item-description ng-binding']")).getText();

			// Compare watch list name
			try {
				Assert.assertEquals(watchlistName, updatedWatchlistName);
				test.log(LogStatus.PASS, "User is able to update watch list name");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "User is not able to update watch list name");
			}

			// Compare watch list description
			try {
				Assert.assertEquals(watchlistDescription, updatedWatchlistDescription);
				test.log(LogStatus.PASS, "User is able to update watch list description");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "User is not able to update watch list description");
			}

			// Deleting the watch list
			deleteParticularWatchlist(watchlistName);
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

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(suiteExls, "Test Cases" , TestUtil.getRowNum(suiteExls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(suiteExls,
		 * "Test Cases", TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
