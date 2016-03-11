package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E39 extends TestBase {
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
						"Verify that same article can be added to multiple watchlists||Verify that user is able to add an item to a particular watchlist during watching")
				.assignCategory("Suite E");

	}

	@Test
	public void testWatchArticleToMultipleWatchlist() throws Exception {

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
			// Navigate to the watch list landing page
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("createWatchListButton")), 30);
			// Creating a new watch list
			String newWatchlistName = "New Watchlist";
			for (int i = 1; i <= 3; i++) {
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("createWatchListButton")), 30);
				ob.findElement(By.xpath(OR.getProperty("createWatchListButton"))).click();
				waitForElementTobeVisible(ob, By.xpath("//div[@data-submit-callback='Workspace.submitWatchlistForm']"), 30);
				ob.findElement(By.xpath(OR.getProperty("newWatchListNameTextBox"))).sendKeys(newWatchlistName + i);
				ob.findElement(By.xpath(OR.getProperty("newWatchListDescriptionTextArea")))
						.sendKeys("This is my newly created watch list");
				// Clicking on Create button
				ob.findElement(By.xpath(OR.getProperty("newWatchListCreateButton"))).click();
			}

			// Searching for article
			String articleName = "biology";
			selectSearchTypeFromDropDown("Articles");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(articleName);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath("//div[@class='search-page-results']"), 30);

			// Watching an article to a multiple watch list
			ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).click();
			// Wait until select a watch list model loads
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("watchlist_select_model")), 5);
			// Watch the article to multiple watch list
			for (int i = 1; i <= 3; i++) {
				waitForElementTobeClickable(ob,
						By.xpath("(" + OR.getProperty("watchlist_watch_button") + ")[" + i + "]"), 30);
				ob.findElement(By.xpath("(" + OR.getProperty("watchlist_watch_button") + ")[" + i + "]")).click();
			}

			// Closing the select a model
			ob.findElement(By.xpath(OR.getProperty("watchlist_model_close_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);

			// Selecting the document name
			String documentName = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			int count;
			List<WebElement> watchedItems;
			for (int i = 1; i <= 3; i++) {
				count = 0;
				// Navigate to a particular watch list page
				navigateToParticularWatchlistPage(newWatchlistName + i);
				watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
				for (int j = 0; j < watchedItems.size(); j++) {
					if (watchedItems.get(j).getText().equals(documentName))
						count++;

				}
				if (compareNumbers(1, count)) {
					test.log(LogStatus.INFO, "User is able to add the article into watchlist" + i);

				} else {
					test.log(LogStatus.FAIL, "User is not able to add the article into watchlist" + i);// extent
					// reports
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_user_unable_to_add_article_into_watchlist" + i)));// screenshot
				}
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
