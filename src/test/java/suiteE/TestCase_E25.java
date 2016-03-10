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
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E25 extends TestBase {
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
						"Verify that document count gets decreased in the watchlist page when a item is deleted from watchlist")
				.assignCategory("Suite E");

	}

	@Test
	public void testItemsCountInWatchlist() throws Exception {

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

			createNewUser("mask", "man");

			// Searching for post
			selectSearchTypeFromDropDown("All");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("biology");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			// Getting watch button list for posts
			List<WebElement> watchButtonList = ob.findElements(By.xpath(OR.getProperty("search_watchlist_image")));

			String selectedWatchlistName = null;
			// Watching 10 posts to a particular watch list
			for (WebElement watchButton : watchButtonList) {

				selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);
				((JavascriptExecutor) ob).executeScript("arguments[0].scrollIntoView(true);", watchButton);
				Thread.sleep(2000);
			}

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(selectedWatchlistName);
			// Getting the items count
			int itemCount = Integer
					.parseInt(ob.findElement(By.xpath(OR.getProperty("itemsCount_in_watchlist"))).getText());

			try {
				Assert.assertEquals(itemCount, 10);
				test.log(LogStatus.INFO, "User is able to watch 10 items into watchlist");
			} catch (Exception e) {
				status = 2;
				test.log(LogStatus.INFO, "User is not able to watch 10 items into watchlist");
			}

			// Unwatching the first 5 document from results
			watchButtonList = ob.findElements(By.xpath(OR.getProperty("watchlist_watchlist_image")));
			for (int i = 0; i < 5; i++) {
				watchButtonList.get(i).click();
				Thread.sleep(2000);
			}

			itemCount = Integer.parseInt(ob.findElement(By.xpath(OR.getProperty("itemsCount_in_watchlist"))).getText());

			try {
				Assert.assertEquals(itemCount, 5);
				test.log(LogStatus.PASS, "Items counts is decreased by 5 after unwatching 5 item");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "Items counts is not decreased by 5 after unwatching 5 item");
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
