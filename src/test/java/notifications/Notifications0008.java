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
import util.OnePObjectMap;

public class Notifications0008 extends NotificationsTestBase {

	static int status = 1;
	PageFactory pf = new PageFactory();
	String postString;
	String document_url = null;
	int screen = 0;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		rowData = testcase.get(this.getClass().getSimpleName());
	}

	@Test
	public void testcaseF8() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			extent = ExtentManager.getReporter(filePath);
			String var = rowData.getTestcaseId();
			String dec = rowData.getTestcaseDescription();
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
				waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.NEWSFEED_FEATURED_POST_XPATH.toString()), 120,
						"Home page is not loaded successfully");
				boolean poststatus = publishPost();

				// Verify that user receives a notification when someone he is following user publishes a post
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-877",
									"Verify that user receives a notification when someone he is following  publishes a post")
							.assignCategory("Notifications");

					if (poststatus) {
						test.log(LogStatus.INFO, "User logged in successfully");
						test.log(LogStatus.INFO, "Published a post -" + postString);
						test.log(LogStatus.INFO, "User logged out successfully");
						verifyPostNotification();
					} else
						throw new Exception("Post creation Exception");

				} catch (Exception e) {
					test.log(LogStatus.FAIL, e.getMessage());
					e.printStackTrace();
				} finally {
					extent.endTest(test);
				}

				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-1395",
									"Verify that all users receive notification when other user published a post and validate notification.")
							.assignCategory("Notifications");
					if (poststatus) {
						test.log(LogStatus.INFO, "User logged in successfully");
						test.log(LogStatus.INFO, "Published a post -" + postString);
						test.log(LogStatus.INFO, "User logged out successfully");
						verifyPostNotificationForAllUsers();
					} else
						throw new Exception("Post creation Exception");

				} catch (Exception e) {
					test.log(LogStatus.FAIL, e.getMessage());
					e.printStackTrace();
				} finally {
					extent.endTest(test);
				}
				//
				// Verify that user is receiving notification when someone liked his post(aggregated notification)
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-1013",
									"Verify that user is receiving notification when someone liked his post(aggregated notification)")
							.assignCategory("Notifications");
					if (poststatus) {
						test.log(LogStatus.INFO, "User logged in successfully");
						test.log(LogStatus.INFO, "Published a post -" + postString);
						test.log(LogStatus.INFO, "User logged out successfully");
						verifyPostLikeNotification();
					} else
						throw new Exception("Post creation Exception");
				} catch (Exception e) {
					test.log(LogStatus.FAIL, e.getMessage());
					e.printStackTrace();
				} finally {
					extent.endTest(test);
				}
				boolean commentstatus = false;
				if (poststatus) {
					commentstatus = addCommentOnPost();
				}
				// Verify that user able to recevie's a notification when other user commented on his post
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-215",
									"Verify that user able to recevie's a notification when other user commented on his post")
							.assignCategory("Notifications");
					test.log(LogStatus.INFO, "Published a post -" + postString);
					if (commentstatus && poststatus) {
						notification3();
					} else
						throw new Exception("Post creation Exception");
				} catch (Exception e) {
					test.log(LogStatus.FAIL, e.getMessage());
					e.printStackTrace();
				} finally {
					extent.endTest(test);
				}
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-1397",
									"Verify that all users receive notification when other user published a comment on post and validate notification.")
							.assignCategory("Notifications");
					if (commentstatus && poststatus) {
						test.log(LogStatus.INFO, "added commnet on post successfully");
						verifycommentNotification();
					} else {
						if (!commentstatus) {
							test.log(LogStatus.INFO, "Facing issue with adding comment");
						}
						throw new Exception(
								"Failed to recevie a notification when someone he is following user comments on a post");
					}

				} catch (Throwable t) {
					test.log(LogStatus.FAIL, t.getMessage());
					logger.info(t.getMessage());
				} finally {
					extent.endTest(test);
				}

				closeBrowser();
			} else {
				test.log(LogStatus.INFO, "User creation problem hence Failing this test case");
				throw new Exception("User creation problem hence throwing exception");
			}
		} catch (Throwable t) {
			if (test == null) {
				extent = ExtentManager.getReporter(filePath);
				String var = rowData.getTestcaseId();
				String dec = rowData.getTestcaseDescription();
				String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
				String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
				for (int i = 0; i < tests.length; i++) {
					test = extent.startTest(tests[i], tests_dec[i]).assignCategory("Notifications");
					test.log(LogStatus.FAIL, "FAIL - " + t.getMessage());
					extent.endTest(test);
				}
			} else {
				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
			}
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + screen++)));// screenshot
		} finally {
			closeBrowser();
		}

	}

	private void verifycommentNotification() throws Exception {
		try {
			pf.getLoginTRInstance(ob).enterTRCredentials(CONFIG.getProperty("defaultUsername"),
					CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			BrowserWaits.waitTime(10);
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			List<WebElement> listOfNotifications = null;
			String text = null;
			for (int i = 1; i <= 3; i++) {

				BrowserWaits.waitTime(3);
				listOfNotifications = ob.findElements(By.xpath(OR.getProperty("all_notifications_in_homepage")));
				for (int j = 0; j < listOfNotifications.size(); j++) {
					String temp = listOfNotifications.get(j).getText();
					if (temp.contains(fn1 + " " + ln1) && temp.contains("New Comment") && temp.contains(postString)) {
						text = temp;
					}
				}
				if (text.length() > 0) {
					break;
				}
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
			}
			logger.info("Notification Text: " + text);
			try {
				Assert.assertTrue(text.contains("New Comment") && text.contains(fn1 + " " + ln1)
						&& text.contains("commented on") && text.contains(postString));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				test.log(LogStatus.FAIL, "Error--->" + t.getMessage());
				ErrorUtil.addVerificationFailure(t);
				logger.error(this.getClass().getSimpleName() + "--->" + t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: "
						+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_OPQA-877")));
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Error--->" + t.getMessage());
			ErrorUtil.addVerificationFailure(t);
			logger.error(this.getClass().getSimpleName() + "--->" + t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_OPQA-877")));// screenshot
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	private boolean addCommentOnPost() throws Exception {
		boolean status = false;
		try {
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
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox")))
					.sendKeys(OR.getProperty("COMMENT_TEXT"));
			BrowserWaits.waitTime(5);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_addComment_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			BrowserWaits.waitTime(6);
			status = true;
		} catch (Exception e) {
			logger.error("Probem happens while adding comment on post" + e.getMessage());
			captureScreenshot(this.getClass().getSimpleName() + "_adding_cooment");
			// throw new Exception("Probem happens while adding comment on post" + e.getMessage());
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
		return status;
	}

	private void notification3() throws Exception {
		try {
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
						&& text.contains(fn1 + " " + ln1) && text.contains(OR.getProperty("COMMENT_TEXT")));
				test.log(LogStatus.PASS, "User receivied notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receivied notification with incorrect content");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: "
						+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + screen++)));// screenshot
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
	private void verifyPostLikeNotification() throws Exception {
		try {
			logger.info("Stated execution for liking post - " + postString);
			clearCookies();
			pf.getLoginTRInstance(ob).waitForTRHomePage();
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.NEWSFEED_FEATURED_POST_XPATH.toString()), 60,
					"Home page is not loaded successfully");
			test.log(LogStatus.INFO, "User logged in successfully for liking post");
			logger.info("User Loggedin successfully for liking post - " + postString);

			pf.getHFPageInstance(ob).searchForText(postString);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 120,
					"Search Results not loaded successfully");
			pf.getSearchResultsPageInstance(ob).clickOnPostTab();
			document_url = ob.findElement(By.xpath(OR.getProperty("searchResults_links") + "[3]")).getAttribute("href");
			ob.navigate().to(document_url);
			logger.info(document_url);
			ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS.toString()))
					.click();
			BrowserWaits.waitTime(3);
			pf.getLoginTRInstance(ob).logOutApp();
			test.log(LogStatus.INFO, "User logged out successfully");
			logger.info("Liked Post" + postString);
			// Login using user2 and check for the notification
			clearCookies();
			pf.getLoginTRInstance(ob).waitForTRHomePage();
			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.NEWSFEED_FEATURED_POST_XPATH.toString()), 120,
					"Home page is not loaded successfully");
			test.log(LogStatus.INFO, "User logged in successfully for verifiying notification");
			String text = ob.findElement(By.xpath(OnePObjectMap.NEWSFEED_NOTIFICATION_LIKE_POST_XPATH.toString()))
					.getText();
			System.out.println(text);

			String expected_text = fn1 + " " + ln1;
			try {
				Assert.assertTrue(text.contains("TODAY") && text.contains(expected_text)
						&& text.contains("Liked your post") && text.contains(postString));
				test.log(LogStatus.PASS, "User receivied notification with correct content");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User receivied notification with incorrect content");// extent
				test.log(LogStatus.FAIL, "Error--->" + t.getMessage());
				ErrorUtil.addVerificationFailure(t);
				logger.error(this.getClass().getSimpleName() + "--->" + t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: "
						+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_OPQA_1013")));
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Error--->" + t.getMessage());
			ErrorUtil.addVerificationFailure(t);
			logger.error(this.getClass().getSimpleName() + "--->" + t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_OPQA_1013")));
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	/**
	 * Verify that user receives a notification when someone he is following user publishes a post
	 * 
	 * @throws Exception
	 */
	private void verifyPostNotification() throws Exception {
		try {
			// Login using user1 and check for the notification
			clearCookies();
			pf.getLoginTRInstance(ob).waitForTRHomePage();

			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			pf.getHFPageInstance(ob).clickOnHomeLink();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.NEWSFEED_FEATURED_POST_XPATH.toString()), 60,
					"Home page is not loaded successfully");
			test.log(LogStatus.INFO, "User logged in successfully for verifying notification");
			// String text = ob.findElement(By.xpath(OR.getProperty("notificationForNewPost"))).getText();
			String text = null;
			List<WebElement> listOfNotifications = ob
					.findElements(By.xpath(OnePObjectMap.NEWSFEED_ALL_NOTIFICATIONS_XPATH.toString()));
			for (int i = 0; i < listOfNotifications.size(); i++) {
				text = listOfNotifications.get(i).getText();
				if (text.contains("New post") && text.contains(postString)) {
					break;
				}
			}
			logger.info("Notification Text: " + text);
			String expected_text = fn2 + " " + ln2;
			try {
				Assert.assertTrue(text.contains("TODAY") && text.contains(expected_text) && text.contains("New post")
						&& text.contains(postString));
				test.log(LogStatus.PASS, "User received notification with correct content");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User receivied notification with incorrect content");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t.getMessage());
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: "
						+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_OPQA_207")));// screenshot
			}
		} catch (Exception t) {
			test.log(LogStatus.INFO, "Error--->" + t.getMessage());
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_OPQA_207")));// screenshot
			test.log(LogStatus.FAIL, "Fail");
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	/**
	 * Verify that all users receive notification when other user published a post and validate notification.
	 * 
	 * @throws Exception
	 */

	private void verifyPostNotificationForAllUsers() throws Exception {
		try {

			clearCookies();
			pf.getLoginTRInstance(ob).waitForTRHomePage();

			pf.getLoginTRInstance(ob).enterTRCredentials(CONFIG.getProperty("defaultUsername"),
					CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			pf.getHFPageInstance(ob).clickOnHomeLink();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.NEWSFEED_FEATURED_POST_XPATH.toString()), 60,
					"Home page is not loaded successfully");
			test.log(LogStatus.INFO, "User logged in successfully for verifying notification");
			String text = null;
			List<WebElement> listOfNotifications = ob
					.findElements(By.xpath(OnePObjectMap.NEWSFEED_ALL_NOTIFICATIONS_XPATH.toString()));
			for (int i = 0; i < listOfNotifications.size(); i++) {
				text = listOfNotifications.get(i).getText();
				if (text.contains("New post") && text.contains(postString)) {
					break;
				}
			}
			logger.info("Notification Text: " + text);
			String expected_text = fn2 + " " + ln2;
			try {
				Assert.assertTrue(text.contains("TODAY") && text.contains(expected_text) && text.contains("New post")
						&& text.contains(postString));
				test.log(LogStatus.PASS, "User received notification with correct content");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User received notification with incorrect content");// extent
				test.log(LogStatus.FAIL, "Error--->" + t.getMessage());
				ErrorUtil.addVerificationFailure(t);
				logger.error(this.getClass().getSimpleName() + "--->" + t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: "
						+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_OPQA-1395")));
			}
		} catch (Exception t) {
			test.log(LogStatus.FAIL, "Error--->" + t.getMessage());
			ErrorUtil.addVerificationFailure(t);
			logger.error(this.getClass().getSimpleName() + "--->" + t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_OPQA-1395")));
			test.log(LogStatus.FAIL, "Fail");
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	private boolean publishPost() throws Exception {
		boolean status = false;
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
			status = true;
		} catch (Exception e) {
			logger.error("Post creation problem" + e.getMessage());
			throw new Exception("Post creation problem" + e.getMessage());
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
		return status;
	}

	@AfterTest
	public void reportTestResult() {
	}
}
