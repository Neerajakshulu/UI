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

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Watchlist017 extends TestBase {

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
		test = extent.startTest(var, "Verify that a user has 1 watchlist by default once we try to watch an item")
				.assignCategory("Watchlist");

	}

	@Test
	public void testDefaultWatchListPresent() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "E Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteExls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			// Opening the browser
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Creating new user as need to test default watch list
			createNewUser("mask", "man");

			// Step1: Checking the default watch list
			// Clicking on first watch button from home page
			waitForPageLoad(ob);
			ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).click();

			// Wait until select a watch list model loads
			waitForElementTobeVisible(ob, By.xpath("//div[@class='select-watchlist-modal ng-scope']"), 5);
			// Select the first watch list from the model
			waitForElementTobeClickable(ob,
					By.xpath("//button[@class='pull-left btn webui-icon-btn watchlist-toggle-button']"), 5);

			try {
				// Finding the no of watch lists
				List<WebElement> watchLists = ob.findElements(By
						.xpath("//button[@class='pull-left btn webui-icon-btn watchlist-toggle-button']"));
				// Closing the select a model
				ob.findElement(By.xpath(OR.getProperty("watchlist_model_close_button"))).click();
				BrowserWaits.waitTime(3);
				Assert.assertEquals(watchLists.size(), 1);
				test.log(LogStatus.PASS, "User has 1 watchlist by default once we try to watch an item");
			} catch (Throwable t) {
				status = 2;
				test.log(LogStatus.FAIL, "User does not have 1 watchlist by default once we try to watch an item");
			}

			// Step2: Verify that every user watch list is private by default
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("watchlist_link")), 30);
			BrowserWaits.waitTime(2);
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();
			BrowserWaits.waitTime(4);
			// Check if watch list is private by default
			boolean watchListStatus = ob.findElement(By.xpath("(//input[@type='checkbox'])[1]")).isSelected();
			if (!watchListStatus) {
				test.log(LogStatus.PASS, "User watchlist is private by default");
			} else {
				test.log(LogStatus.FAIL, "User watchlist is not private by default");
			}

			// Step3: Verify that user is able to have a watch list with 0 item
			// Navigating to the default watch list details page
			ob.findElement(By.xpath(OR.getProperty("watchlist_name"))).click();
			waitForPageLoad(ob);

			// Getting the items count
			int itemCount = Integer.parseInt(ob.findElement(By.xpath(OR.getProperty("itemsCount_in_watchlist")))
					.getText());

			try {
				Assert.assertEquals(itemCount, 0);
				test.log(LogStatus.PASS, "User can have a watchlist with 0 item under it");
			} catch (Exception e) {
				status = 2;
				test.log(LogStatus.FAIL, "User can not have a watchlist with 0 item under it");
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
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
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
