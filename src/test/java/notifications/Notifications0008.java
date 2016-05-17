package notifications;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Notifications0008 extends NotificationsTestBase {

	static int status = 1;
	PageFactory pf = new PageFactory();
	String postString;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
	}

	@Test
	public void testcaseF8() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Notifications");
		boolean testRunmode = TestUtil.isTestCaseRunnable(notificationxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			extent = ExtentManager.getReporter(filePath);
			extent = ExtentManager.getReporter(filePath);
			String var = xlRead2(returnExcelPath('F'), this.getClass().getSimpleName(), 1);
			String dec = xlRead2(returnExcelPath('F'), this.getClass().getSimpleName(), 2);
			String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
			String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
			for (int i = 0; i < tests.length; i++) {
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("Notifications");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		try {
			if (user2 != null && user3 != null && user1 != null) {
				postString = "PostCreationTest" + RandomStringUtils.randomNumeric(10);
				openBrowser();
				maximizeWindow();
				clearCookies();
				ob.navigate().to(host);
				pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
				pf.getLoginTRInstance(ob).clickLogin();
				publishPost();

				// Verify that user receives a notification when someone he is following user publishes a post
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-877",
									"Verify that user receives a notification when someone he is following  publishes a post")
							.assignCategory("Notifications");
					test.log(LogStatus.INFO, "Published a post -" + postString);
					notification1();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					extent.endTest(test);
				}

				// Verify that user is receiving notification when someone liked his post(aggregated notification)
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-1013",
									"Verify that user is receiving notification when someone liked his post(aggregated notification)")
							.assignCategory("Notifications");
					test.log(LogStatus.INFO, "Published a post -" + postString);
					notification2();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					extent.endTest(test);
				}
				// Verify that user able to recevie's a notification when other user commented on his post
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-215",
									"Verify that user able to recevie's a notification when other user commented on his post")
							.assignCategory("Notifications");
					test.log(LogStatus.INFO, "Published a post -" + postString);
					notification3();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					extent.endTest(test);
				}

				closeBrowser();
			} else {
				test.log(LogStatus.INFO, "User creation problem hence Failing this test case");
				throw new Exception("User creation problem hence throwing exception");
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
			// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
			closeBrowser();
		}

	}

	private void notification3() throws Exception {
		try {
			// USER1 WILL COMMENT ON THE POST CREATED
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(postString);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			BrowserWaits.waitTime(2);

			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0,-500)");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_results_posts_tab_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("search_results_posts_tab_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_results_post_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 30);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("Very Nice Post");
			BrowserWaits.waitTime(5);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_addComment_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			BrowserWaits.waitTime(6);
			pf.getLoginTRInstance(ob).logOutApp();

			// LOGIN WITH USER3 AND CHECK FOR THE NOTIFICATION
			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			BrowserWaits.waitTime(5);
			String text = ob.findElement(By.xpath(OR.getProperty("notificationDocumentComment"))).getText();
			logger.info("Notification Text: " + text);
			try {
				Assert.assertTrue(text.contains("New comments on your post") && /*
																				 * text.contains("TODAY") &&
																				 */text.contains(postString)
						&& text.contains(fn1 + " " + ln1) && text.contains("Very Nice Post"));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	/**
	 * Verify that user is receiving notification when someone liked his post(aggregated notification)
	 * 
	 * @throws Exception
	 */
	private void notification2() throws Exception {
		try {
			logger.info("Stated execution for liking post - " + postString);
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			logger.info("User Loggedin successfully for liking post - " + postString);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(postString);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			BrowserWaits.waitTime(4);
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0,-500)");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_results_posts_tab_link")), 30);
			test.log(LogStatus.INFO, "Liking the post");
			ob.findElement(By.xpath(OR.getProperty("search_results_posts_tab_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_results_post_link")), 150);
			ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).click();

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("post_like_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("post_like_button"))).click();
			BrowserWaits.waitTime(3);
			pf.getLoginTRInstance(ob).logOutApp();
			logger.info("Liked Post" + postString);
			// Login using user2 and check for the notification
			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			BrowserWaits.waitTime(7);
			String text = ob.findElement(By.xpath(OR.getProperty("notificationForLike"))).getText();
			System.out.println(text);

			String expected_text = fn1 + " " + ln1;
			try {
				Assert.assertTrue(/* text.contains("TODAY") && */text.contains(expected_text)
						&& text.contains("Liked your post") && text.contains(postString));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t.getMessage());
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	/**
	 * Verify that user receives a notification when someone he is following user publishes a post
	 * 
	 * @throws Exception
	 */
	private void notification1() throws Exception {
		try {
			// Login using user1 and check for the notification
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			BrowserWaits.waitTime(5);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 50);
			// String text = ob.findElement(By.xpath(OR.getProperty("notificationForNewPost"))).getText();
			String text = null;
			List<WebElement> listOfNotifications = ob
					.findElements(By.xpath(OR.getProperty("all_notifications_in_homepage")));
			for (int i = 0; i < listOfNotifications.size(); i++) {
				text = listOfNotifications.get(i).getText();
				if (text.contains("published a post") && text.contains(postString)) {
					break;
				}
			}
			logger.info("Notification Text: " + text);
			String expected_text = fn2 + " " + ln2;
			try {
				Assert.assertTrue(/* text.contains("TODAY") && */text.contains(expected_text)
						&& text.contains("published a post") && text.contains(postString));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t.getMessage());
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Fail");
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	private void publishPost() throws Exception {
		try {
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("home_page_publish_post_link")), 3000);
			ob.findElement(By.xpath(OR.getProperty("home_page_publish_post_link"))).click();
			pf.getProfilePageInstance(ob).enterPostTitle(postString);
			logger.info("Entered Post Title");
			pf.getProfilePageInstance(ob).enterPostContent(postString);
			logger.info("Entered Post Content");
			pf.getProfilePageInstance(ob).clickOnPostPublishButton();
			logger.info("Published the post");
			BrowserWaits.waitTime(3);
		} catch (Exception e) {
			logger.error("Post creation problem" + e.getMessage());
			throw new Exception("Post creation problem" + e.getMessage());
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	@AfterTest
	public void reportTestResult() {
	}
}
