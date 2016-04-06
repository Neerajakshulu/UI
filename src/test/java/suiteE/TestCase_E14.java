package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class TestCase_E14 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent
				.startTest(var,
						"Verify that user is able to create a new watchlist||Verify that user is able to see his private watchlists on his own profile page")
				.assignCategory("Suite E");

	}

	@Test
	public void testCreateWatchList() throws Exception {

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

			// ob.get(host);
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			loginAsSpecifiedUser(user1, CONFIG.getProperty("defaultPassword"));
			// loginAsSpecifiedUser("Prasenjit.Patra@Thomsonreuters.com", "Techm@2015");

			// Create watch list
			String newWatchlistName = "Watchlist_" + this.getClass().getSimpleName();
			createWatchList("private", newWatchlistName, "This is my test watchlist.");

			// Getting all the watch lists
			List<WebElement> watchLists = ob.findElements(By.xpath(OR.getProperty("watchlist_name")));
			// Finding the newly created watch list
			int count = 0;
			for (int i = 0; i < watchLists.size(); i++) {
				if (watchLists.get(i).getText().equals(newWatchlistName)) {
					count++;
					break;
				}
			}

			try {
				Assert.assertEquals(1, count);
				test.log(LogStatus.PASS, "User is able to create new watch list with name and description");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "User is unable to create new watch list with name and description");
			}

			// Navigating to the private watch list tab
			ob.findElement(By.xpath(OR.getProperty("watchListPrivateTabLink"))).click();
			watchLists = ob.findElements(By.xpath(OR.getProperty("watchlist_name")));
			count = 0;
			for (int i = 0; i < watchLists.size(); i++) {
				if (watchLists.get(i).getText().equals(newWatchlistName)) {
					count++;
					break;
				}
			}

			try {
				Assert.assertEquals(1, count);
				test.log(LogStatus.PASS, "User is able to see private watch list in own profile page");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "User is unable to see private watch list in own profile page");
			}

			// Deleting the watch list
			deleteParticularWatchlist(newWatchlistName);
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
		 * if (status == 1) TestUtil.reportDataSetResult(suiteExls, "Test Cases"
		 * , TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()),
		 * "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(suiteExls, "Test Cases",
		 * TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(suiteExls, "Test Cases",
		 * TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()),
		 * "SKIP");
		 */
	}

}
