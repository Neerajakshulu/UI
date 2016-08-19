package watchlist;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
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
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

/**
 * Verify that user is able to watch a post(user generated content) to a particular watchlist from notification in home
 * page||Verify that user is able to unwatch a post from watchlist from notification in home page
 * 
 * @author Prasenjit Patra
 *
 */
public class Watchlist029 extends TestBase {

	static int status = 1;
	PageFactory pf = new PageFactory();

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
						"Verify that user is able to watch a post(user generated content) to a particular watchlist from notification in home page||Verify that user is able to unwatch a post from watchlist from notification in home page")
				.assignCategory("Watchlist");

	}
	@Test
	public void testWatchUnwatchPostFromHomePage() throws Exception {

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
			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);
			
			//login with user 2 and follow user1 to get the notifications
			loginAsSpecifiedUser(LOGIN.getProperty("LOGINUSERNAME2"), LOGIN.getProperty("LOGINPASSWORD2"));
			pf.getHFPageInstance(ob).searchForText(LOGIN.getProperty("PROFILE1"));
			pf.getSearchResultsPageInstance(ob).clickOnPeopleName(LOGIN.getProperty("PROFILE1"));
			pf.getProfilePageInstance(ob).followOtherProfile();
			pf.getLoginTRInstance(ob).logOutApp();
			
			// 1)Login as user1 and publish a post
			loginAsSpecifiedUser(LOGIN.getProperty("LOGINUSERNAME1"), LOGIN.getProperty("LOGINPASSWORD1"));

			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS.toString()),
					30);
			pf.getHFPageInstance(ob).clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			int postCountBefore = 0;
			test.log(LogStatus.INFO, "Post count:" + postCountBefore);
			pf.getProfilePageInstance(ob).clickOnPublishPostButton();
			pf.getProfilePageInstance(ob).enterPostTitle("My Post");
			test.log(LogStatus.INFO, "Entered Post Title");
			pf.getProfilePageInstance(ob).enterPostContent("This is my post description" +RandomStringUtils.randomNumeric(500));
			test.log(LogStatus.INFO, "Entered Post Content");
			pf.getProfilePageInstance(ob).clickOnPostPublishButton();
			Thread.sleep(4000);
			test.log(LogStatus.INFO, "Published the post");
			int postCountAfter = pf.getProfilePageInstance(ob).getPostsCount();
			test.log(LogStatus.INFO, "Post count:" + postCountAfter);

			BrowserWaits.waitTime(2);
			logout();

			// 2)Login with user2 and and try to watch the post from
			// notification panel
			loginAsSpecifiedUser(LOGIN.getProperty("LOGINUSERNAME2"), LOGIN.getProperty("LOGINPASSWORD2"));

			// Create watch list
			String newWatchlistName = this.getClass().getSimpleName() + "_" + getCurrentTimeStamp();
			createWatchList("private", newWatchlistName, "This is my test watchlist.");

			// Navigating to the home page
			ob.findElement(By.xpath(OR.getProperty("home_link"))).click();

			// Check if user gets the notification
			waitForElementTobeVisible(ob, By.cssSelector("div[class='wui-card wui-card--new-post']"), 60);

			if (!(ob.findElements(By.cssSelector("div[class='wui-card wui-card--new-post']")).size() >= 1)) {
				test.log(LogStatus.FAIL, "User not receiving notification");// extent
																			// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_user_not_receiving_notification")));// screenshot
				closeBrowser();
				return;
			}
			
			
			
			WebElement watchButton = null;
			String docTitle=null;
			List<WebElement> newComments=ob.findElements(By.cssSelector("div[class='wui-card wui-card--new-post'] div[class='wui-card__content']"));
			for(WebElement newComment:newComments){
				docTitle=newComment.findElement(By.cssSelector("a div[ng-class='vm.titleSizeClass()']")).getText();
				if(StringUtils.containsIgnoreCase("My Post", docTitle)) {
					watchButton =newComment.findElement(By.cssSelector("button[ng-click='WatchButton.openWatchlistSelector()']"));
					break;
				}
			}
			
			logger.info("post title in watchlist page-->"+docTitle);
			BrowserWaits.waitTime(10);
			

			// Watching the post to a particular watch list
			//WebElement watchButton = ob
				//	.findElement(By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"));
			watchOrUnwatchItemToAParticularWatchlist( newWatchlistName,watchButton);

			
			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(newWatchlistName);

			List<WebElement> watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));

			int count = 0;
			for (int i = 0; i < watchedItems.size(); i++) {

				if (watchedItems.get(i).getText().equals(docTitle))
					count++;

			}

			if (compareNumbers(1, count)) {
				test.log(LogStatus.PASS, "User is able to add a post into watchlist from home page");

			} else {
				test.log(LogStatus.FAIL, "User not able to add a post into watchlist from home page");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_unable_to_add_post_into_watchlist_from_home_page")));// screenshot
				return;
			}

			// Navigating to the home page
			ob.findElement(By.xpath(OR.getProperty("home_link"))).click();
			waitForElementTobeVisible(ob, By.cssSelector("div[class='wui-card wui-card--new-post']"), 60);
			
			
			
			List<WebElement> newPosts=ob.findElements(By.cssSelector("div[class='wui-card wui-card--new-post'] div[class='wui-card__content']"));
			for(WebElement newPost:newPosts){
				docTitle=newPost.findElement(By.cssSelector("a div[ng-class='vm.titleSizeClass()']")).getText();
				if(StringUtils.containsIgnoreCase("My Post", docTitle)) {
					watchButton =newPost.findElement(By.cssSelector("button[ng-click='WatchButton.openWatchlistSelector()']"));
					break;
				}
			}

			// Unwatching the post to a particular watch list
			//watchButton = ob.findElement(By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"));
			watchOrUnwatchItemToAParticularWatchlist( newWatchlistName,watchButton);


			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(newWatchlistName);

			try {

				WebElement defaultMessage = ob.findElement(By.xpath(OR.getProperty("default_message_watchlist")));

				if (defaultMessage.isDisplayed()) {

					test.log(LogStatus.PASS, "User is able to remove a post from watchlist in home page");// extent
				} else {
					test.log(LogStatus.FAIL, "User not able to remove a post from watchlist in home page");// extent
					// reports
					status = 2;// excel
					test.log(LogStatus.INFO,
							"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_user_unable_to_remove_post_from_watchlist_in_home_page")));// screenshot
				}
			} catch (NoSuchElementException e) {

				watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
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
