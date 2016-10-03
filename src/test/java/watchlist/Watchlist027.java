package watchlist;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

/**
 * Verify that user is able to watch an article to a particular watchlist from notification in home page||Verify that
 * user is able to unwatch an article from watchlist from notification in home page
 * 
 * @author chinna putha
 *
 */
public class Watchlist027 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("Watchlist");

	}

	@Test
	public void testWatchUnwatchArticleFromHomePage() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			// Open browser
			openBrowser();
			maximizeWindow();

			clearCookies();

			ob.navigate().to(host);

			loginAsSpecifiedUser(LOGIN.getProperty("LOGINUSERNAME2"), LOGIN.getProperty("LOGINPASSWORD2"));
			pf.getHFPageInstance(ob).searchForText(LOGIN.getProperty("PROFILE9"));
			pf.getSearchResultsPageInstance(ob).clickOnPeopleName(LOGIN.getProperty("PROFILE9"));
			pf.getProfilePageInstance(ob).followOtherProfile();
			pf.getLoginTRInstance(ob).logOutApp();

			// 1)Login as user1 and comment on some article
			loginAsSpecifiedUser(LOGIN.getProperty("USERNAME9"), LOGIN.getProperty("PASSWORD9"));

			// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_type_dropdown")), 30);
			// selectSearchTypeFromDropDown("Patents");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("biology");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);

			pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
			String document_title = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			System.out.println(document_title);
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 30);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox")))
					.sendKeys("Automation Script Comment: Watchlist027");
			waitForElementTobeVisible(ob,
					By.cssSelector(OnePObjectMap.RECORD_VIEW_PAGE_COMMENTS_EDIT_SUBMIT_BUTTON_CSS.toString()), 30);
			jsClick(ob, ob.findElement(
					By.cssSelector(OnePObjectMap.RECORD_VIEW_PAGE_COMMENTS_EDIT_SUBMIT_BUTTON_CSS.toString())));
			BrowserWaits.waitTime(2);
			pf.getLoginTRInstance(ob).logOutApp();

			// 2)Login with user2 and and try to watch the article from
			// notification panel

			loginAsSpecifiedUser(LOGIN.getProperty("LOGINUSERNAME2"), LOGIN.getProperty("LOGINPASSWORD2"));
			waitForAjax(ob);
			// Create watch list
			String newWatchlistName = this.getClass().getSimpleName() + "_" + getCurrentTimeStamp();
			// BrowserWaits.waitTime(30);
			// pf.getHFPageInstance(ob).searchForText("test");
			// waitForAjax(ob);
			// pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
			// waitForAjax(ob);

			// jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("watchlist_link"))));
			// BrowserWaits.waitTime(4);
			// jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("watchlist_link"))));
			createWatchList("private", newWatchlistName, "This is my test watchlist.");

			// Navigating to the home page
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("home_link"))));

			// Check if user gets the notification
			waitForElementTobeVisible(ob, By.cssSelector("div[class$='-event']"), 60);

			logger.info("new comment size-->" + ob.findElements(By.cssSelector("div[class$='-event']")).size());
			if (!(ob.findElements(By.cssSelector("div[class$='-event']")).size() >= 1)) {

				test.log(LogStatus.FAIL, "User not receiving notification");// extent
																			// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_user_not_receiving_notification")));// screenshot
				closeBrowser();
				return;
			}

			WebElement watchButton = null;
			String docTitle = null;
			List<WebElement> newComments = ob
					.findElements(By.cssSelector("div[class$='-event'] div[class='wui-card__content']"));
			for (WebElement newComment : newComments) {
				docTitle = newComment.findElement(By.cssSelector("a div[ng-class='vm.titleSizeClass()']")).getText();
				if (StringUtils.containsIgnoreCase(document_title, docTitle)) {
					watchButton = newComment
							.findElement(By.cssSelector("button[ng-click='WatchButton.openWatchlistSelector()']"));
					break;
				}
			}

			logger.info("document title in watchlist page-->" + docTitle);
			BrowserWaits.waitTime(10);
			// Watching the article to a particular watch list
			/*
			 * WebElement watchButton = ob .findElement(By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" +
			 * 2 + "]"));
			 */
			watchOrUnwatchItemToAParticularWatchlist(newWatchlistName, watchButton);

			// Selecting the document name
			/*
			 * String documentName = ob .findElement(By.xpath("(" + OR.getProperty("document_link_in_home_page") + ")["
			 * + 2 + "]")) .getText();
			 */

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(newWatchlistName);

			List<WebElement> watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));

			int count = 0;
			for (int i = 0; i < watchedItems.size(); i++) {

				if (watchedItems.get(i).getText().equals(docTitle))
					count++;

			}

			if (compareNumbers(1, count)) {
				test.log(LogStatus.PASS, "User is able to add an article into watchlist from home page");

			} else {
				test.log(LogStatus.FAIL, "User not able to add an article into watchlist from home page");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO,
						"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
								+ "_user_unable_to_add_article_into_watchlist_from_home_page")));// screenshot
				return;
			}

			// Navigating to the home page
			ob.findElement(By.xpath(OR.getProperty("home_link"))).click();
			// waitForElementTobeVisible(ob, By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"),
			// 30);

			waitForElementTobeVisible(ob, By.cssSelector("div[class$='-event']"), 60);

			List<WebElement> newComments2 = ob
					.findElements(By.cssSelector("div[class$='-event'] div[class='wui-card__content']"));
			for (WebElement newComment : newComments2) {
				docTitle = newComment.findElement(By.cssSelector("a div[ng-class='vm.titleSizeClass()']")).getText();
				if (StringUtils.containsIgnoreCase(document_title, docTitle)) {
					watchButton = newComment
							.findElement(By.cssSelector("button[ng-click='WatchButton.openWatchlistSelector()']"));
					break;
				}
			}

			// Unwatching the article to a particular watch list
			// watchButton = ob.findElement(By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"));
			watchOrUnwatchItemToAParticularWatchlist(newWatchlistName, watchButton);

			// Selecting the document name
			// documentName = ob.findElement(By.xpath("(" + OR.getProperty("document_link_in_home_page") + ")[" + 2 +
			// "]"))
			// .getText();

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(newWatchlistName);

			try {

				WebElement defaultMessage = ob.findElement(By.xpath(OR.getProperty("default_message_watchlist")));

				if (defaultMessage.isDisplayed()) {

					test.log(LogStatus.PASS, "User is able to remove an article from watchlist in home page");// extent
				} else {
					test.log(LogStatus.FAIL, "User not able to remove an article from watchlist in home page");// extent
					// reports
					status = 2;// excel
					test.log(LogStatus.INFO,
							"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_user_unable_to_remove_article_from_watchlist_in_home_page")));// screenshot
				}
			} catch (NoSuchElementException e) {

				watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links1")));
				count = 0;
				for (int i = 0; i < watchedItems.size(); i++) {

					if (watchedItems.get(i).getText().equals(docTitle))
						count++;

				}
				Assert.assertEquals(count, 0);
			}

			// Deleting the watch list
			deleteParticularWatchlist(newWatchlistName);
			pf.getLoginTRInstance(ob).logOutApp();
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
