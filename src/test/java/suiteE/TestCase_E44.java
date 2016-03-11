package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.HeaderFooterLinksPage;
import pages.ProfilePage;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;

public class TestCase_E44 extends TestBase {
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
						"Verify that user is able to watch a post(user generated content) to a particular watchlist from notification in home page||Verify that user is able to unwatch a post from watchlist from notification in home page")
				.assignCategory("Suite E");

	}

	@Test
	public void testWatchUnwatchPostFromHomePage() throws Exception {

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
			// 1)Create User1 and logout
			openBrowser();
			maximizeWindow();
			clearCookies();
			fn1 = generateRandomName(8);
			ln1 = generateRandomName(10);
			System.out.println(fn1 + " " + ln1);
			user1 = createNewUser(fn1, ln1);
			 waitForElementTobeVisible(ob,
			 By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS.toString()),
			 180);
			LoginTR.logOutApp();
			closeBrowser();
			// 2)Create User2 and follow User1
			openBrowser();
			maximizeWindow();
			clearCookies();
			fn2 = generateRandomName(8);
			ln2 = generateRandomName(10);
			System.out.println(fn2 + " " + ln2);
			user2 = createNewUser(fn2, ln2);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(fn1 + " " + ln1);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("profilesTabHeading_link")), 30);
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0,-500)");
			Thread.sleep(2000);

			ob.findElement(By.xpath(OR.getProperty("profilesTabHeading_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_follow_button")), 40);
			ob.findElement(By.xpath(OR.getProperty("search_follow_button"))).click();
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS.toString()), 30);
			LoginTR.logOutApp();

			// 3)Login as user1 and publish a post
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			waitForElementTobeVisible(ob, By.id(OR.getProperty("TR_email_textBox")), 30);
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(user1);
			ob.findElement(By.id(OR.getProperty("TR_password_textBox")))
					.sendKeys(CONFIG.getProperty("defaultPassword"));
			ob.findElement(By.id(OR.getProperty("login_button"))).click();

			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS.toString()),
					30);
			HeaderFooterLinksPage.clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			int postCountBefore = 0;
			test.log(LogStatus.INFO, "Post count:" + postCountBefore);
			ProfilePage.clickOnPublishPostButton();
			ProfilePage.enterPostTitle("My Post");
			test.log(LogStatus.INFO, "Entered Post Title");
			ProfilePage.enterPostContent("This is my post description");
			test.log(LogStatus.INFO, "Entered Post Content");
			ProfilePage.clickOnPostPublishButton();
			Thread.sleep(4000);
			test.log(LogStatus.INFO, "Published the post");
			int postCountAfter = ProfilePage.getPostsCount();
			test.log(LogStatus.INFO, "Post count:" + postCountAfter);

			try {
				Assert.assertEquals(postCountBefore + 1, postCountAfter);
				test.log(LogStatus.PASS, "Post count is incremented after the new post creation");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Post count is incremented after the new post creation");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "Post_count_validation_failed")));// screenshot

			}

			Thread.sleep(2000);
			logout();

			// 2)Login with user2 and and try to watch the post from
			// notification panel
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			waitForElementTobeVisible(ob, By.id("userid"), 30);
			ob.findElement(By.id("userid")).clear();
			ob.findElement(By.id("userid")).sendKeys(user2);
			ob.findElement(By.id("password")).sendKeys(CONFIG.getProperty("defaultPassword"));
			ob.findElement(By.id(OR.getProperty("login_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("notification")), 30);
			if (!checkElementPresence("notification")) {

				test.log(LogStatus.FAIL, "User not receiving notification");// extent
																			// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_user_not_receiving_notification")));// screenshot
				closeBrowser();
				return;
			}
			// Watching the post to a particular watch list
			WebElement watchButton = ob
					.findElement(By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"));
			String selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);

			// Selecting the document name
			String documentName = ob
					.findElement(By.xpath("(" + OR.getProperty("document_link_in_home_page") + ")[" + 2 + "]"))
					.getText();

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(selectedWatchlistName);

			List<WebElement> watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));

			int count = 0;
			for (int i = 0; i < watchedItems.size(); i++) {

				if (watchedItems.get(i).getText().equals(documentName))
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
			waitForElementTobeVisible(ob, By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"),
					30);

			// Unwatching the post to a particular watch list
			watchButton = ob.findElement(By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"));
			selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);

			// Selecting the document name
			documentName = ob.findElement(By.xpath("(" + OR.getProperty("document_link_in_home_page") + ")[" + 2 + "]"))
					.getText();

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(selectedWatchlistName);

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

					if (watchedItems.get(i).getText().equals(documentName))
						count++;

				}
				Assert.assertEquals(count, 0);
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
