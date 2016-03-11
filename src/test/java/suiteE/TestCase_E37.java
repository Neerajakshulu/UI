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
import util.TestUtil;

public class TestCase_E37 extends TestBase {
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
						"Verify that user is able to delete a watchlist||Verify that user is not able to see his watchlist on his own profile page once that particular watchlist is deleted.")
				.assignCategory("Suite E");

	}

	@Test
	public void testDeleteWatchList() throws Exception {

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
			// Creating user
			createNewUser("mask", "man");

			// Navigate to the watch list landing page
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("createWatchListButton")), 30);
			// Creating a new watch list
			String newWatchlistName = "New Watchlist";
			for (int i = 1; i <= 2; i++) {
				waitForElementTobeClickable(ob, By.xpath(OR.getProperty("createWatchListButton")), 30);
				ob.findElement(By.xpath(OR.getProperty("createWatchListButton"))).click();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("newWatchListNameTextBox")), 30);
				ob.findElement(By.xpath(OR.getProperty("newWatchListNameTextBox"))).sendKeys(newWatchlistName + i);
				ob.findElement(By.xpath(OR.getProperty("newWatchListDescriptionTextArea")))
						.sendKeys("This is my newly created watch list");
				// Clicking on Create button
				ob.findElement(By.xpath(OR.getProperty("newWatchListCreateButton"))).click();
			}

			// Deleting the first watch list
			ob.findElement(By.xpath(OR.getProperty("watchlist_name"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("delete_button_image")), 30);
			ob.findElement(By.xpath(OR.getProperty("delete_button_image"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("delete_watchlist_popup")), 4);
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("delete_button_in_popup")), 2);
			ob.findElement(By.xpath(OR.getProperty("delete_button_in_popup"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("watchlist_name")), 30);

			// Getting all the watch lists
			List<WebElement> watchLists = ob.findElements(By.xpath(OR.getProperty("watchlist_name")));
			// Finding the deleted watch list is visible or not
			int count = 0;
			for (WebElement watchlist : watchLists) {
				if (watchlist.getText().equals(newWatchlistName + 2))
					count++;
			}

			try {
				Assert.assertEquals(count, 0);
				test.log(LogStatus.PASS, "User is able to delete watch list");
				test.log(LogStatus.PASS, "User is not able to see the deleted watch list");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "User is not able to delete watch list");
				test.log(LogStatus.FAIL, "User is able to see the deleted watch list");
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
