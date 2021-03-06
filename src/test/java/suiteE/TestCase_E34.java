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
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E34 extends TestBase {
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
		test = extent
				.startTest(var,
						"Verify that no one can see the private watchlists of a user on user's profile page||Verify that user1 is not able to see a watchlist on user2's profile page,  once user2's public watchlist is reverted to private.")
				.assignCategory("Suite E");

	}

	@Test
	public void testPrivateWatchlistNotVisibleFromOthersProfile() throws Exception {

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

			// String search_query = "biology";

			// 1) Login as user2
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.get(host);
			loginAsSpecifiedUser(user2, CONFIG.getProperty("defaultPassword"));
			// Navigate to the watch list landing page
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("watchlist_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("createWatchListButton")), 30);
			// Creating 5 public watch list
			String newWatchlistName = "New Watchlist";
			for (int i = 1; i <= 3; i++) {
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("createWatchListButton")), 30);
				ob.findElement(By.xpath(OR.getProperty("createWatchListButton"))).click();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("newWatchListNameTextBox")), 30);
				ob.findElement(By.xpath(OR.getProperty("newWatchListNameTextBox"))).sendKeys(newWatchlistName + i);
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("newWatchListDescriptionTextArea")), 30);
				ob.findElement(By.xpath(OR.getProperty("newWatchListDescriptionTextArea")))
						.sendKeys("This is my newly created watch list.");
				waitForElementTobeClickable(ob, By.xpath(OR.getProperty("newWatchListCreateButton")), 30);
				ob.findElement(By.xpath(OR.getProperty("newWatchListCreateButton"))).click();
				waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'" + newWatchlistName + i + "')]"), 30);
			}
			// Making the last watch list as private
			ob.findElement(By.xpath(OR.getProperty("newWatchListPublicCheckBox"))).click();
			Thread.sleep(2000);
			LoginTR.logOutApp();
			closeBrowser();
			// 2)Login as User1 and navigate to the user2 profile page
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.get(host);
			loginAsSpecifiedUser(user1, CONFIG.getProperty("defaultPassword"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			// Searching for article
			selectSearchTypeFromDropDown("People");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(fn2 + " " + ln2);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.linkText(fn2 + " " + ln2), 60);
			// Navigating to the first user profile page
			ob.findElement(By.linkText(fn2 + " " + ln2)).click();
			waitForPageLoad(ob);
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("tr_watchlists_tab_in_profile_page")), 60);
			// Navigating to the watch list tab
			ob.findElement(By.xpath(OR.getProperty("tr_watchlists_tab_in_profile_page"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_watchlist_results_in_profile_page")), 30);
			List<WebElement> watchlists = ob
					.findElements(By.xpath(OR.getProperty("tr_watchlist_results_in_profile_page")));
			int count = 0;
			// Checking if private watch list is visible to others or not
			for (WebElement watchlist : watchlists) {
				if (watchlist.getText().equals(newWatchlistName + 5)) {
					count++;
					break;
				}
			}

			try {
				Assert.assertEquals(count, 0);
				test.log(LogStatus.PASS, "Others can not see the private watchlists of a user on user's profile page");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "Others able to see the private watchlists of a user on user's profile page");
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
