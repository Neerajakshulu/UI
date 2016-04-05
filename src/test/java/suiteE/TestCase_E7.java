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
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class TestCase_E7 extends TestBase {
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
						"Verify that user is able to add an Article from Articles content search results page to a particular watchlist||Verify that user is able to unwatch an Article from watchlist page||Verify that user is able to unwatch an Article from Articles content search results page")
				.assignCategory("Suite E");

	}

	@Test
	@Parameters({ "articleName" })
	public void testWatchArticleFromArticleContentSearchResult(String articleName) throws Exception {

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

			// Searching for article
			selectSearchTypeFromDropDown("Articles");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(articleName);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			waitForElementTobeVisible(ob, By.xpath("//div[@class='search-page-results']"), 60);
			// Getting watch button list for articles
			List<WebElement> watchButtonList = ob.findElements(By.xpath(OR.getProperty("search_watchlist_image")));
			// Watching 2 articles to a particular watch list
			for (int i = 0; i < 2; i++) {
				WebElement watchButton = watchButtonList.get(i);
				watchOrUnwatchItemToAParticularWatchlist(watchButton, newWatchlistName);
				((JavascriptExecutor) ob).executeScript("arguments[0].scrollIntoView(true);", watchButton);
				BrowserWaits.waitTime(2);
			}

			// Selecting the document name
			String firstdocumentName = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(newWatchlistName);
			waitForPageLoad(ob);

			List<WebElement> watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));

			int count = 0;
			for (int i = 0; i < watchedItems.size(); i++) {

				if (watchedItems.get(i).getText().equals(firstdocumentName))
					count++;

			}

			if (!compareNumbers(1, count)) {

				test.log(LogStatus.FAIL,
						"User not able to add an article into watchlist from Article content search results page");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_user_unable_to_add_article_into_watchlist_from_Article_content_searchResults_page")));// screenshot

			}

			// Steps2: Removing the first item from watch list page
			firstdocumentName = ob.findElement(By.xpath(OR.getProperty("result_title_in_watchlist"))).getText();
			// Unwatching the first document from results
			ob.findElement(By.xpath(OR.getProperty("watchlist_watchlist_image"))).click();
			BrowserWaits.waitTime(2);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("result_title_in_watchlist")), 30);
			// Checking if first document still exists in the watch list
			List<WebElement> documentList = ob.findElements(By.xpath(OR.getProperty("result_title_in_watchlist")));
			count = 0;
			String documentTitle;
			for (WebElement document : documentList) {
				documentTitle = document.getText();
				if (documentTitle.equals(firstdocumentName))
					count++;
			}

			try {
				Assert.assertEquals(count, 0);
				test.log(LogStatus.PASS, "User is able to unwatch an Article from watchlist page");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "User is unable to unwatch an Article from watchlist page");
			}

			// Steps3: Unwatching an article from article content result page
			// Searching for article
			selectSearchTypeFromDropDown("Articles");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).clear();
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("hello");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			waitForElementTobeVisible(ob, By.xpath("//div[@class='search-page-results']"), 60);
			// Watching an article to a particular watch list
			WebElement watchButton = ob.findElement(By.xpath(OR.getProperty("search_watchlist_image")));
			watchOrUnwatchItemToAParticularWatchlist(watchButton, newWatchlistName);

			// Unwatching an article to a particular watch list
			watchButton = ob.findElement(By.xpath(OR.getProperty("search_watchlist_image")));
			watchOrUnwatchItemToAParticularWatchlist(watchButton, newWatchlistName);

			// Selecting the document name
			String documentName = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(newWatchlistName);

			try {

				watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
				count = 0;
				for (int i = 0; i < watchedItems.size(); i++) {

					if (watchedItems.get(i).getText().equals(documentName))
						count++;

				}
				Assert.assertEquals(count, 0);
				test.log(LogStatus.PASS,
						"User is able to remove an article from watchlist in Article content search results page");// extent

			} catch (Throwable t) {

				test.log(LogStatus.FAIL,
						"User not able to remove an article from watchlist in Article content search results page");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_user_unable_to_remove_article_from_watchlist_in_Article_content_searchResults_page")));// screenshot
			}
			// Delete the watch list
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
