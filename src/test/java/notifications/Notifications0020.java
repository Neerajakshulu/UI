package notifications;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class Notifications0020 extends NotificationsTestBase {

	static int status = 1;
	String document_title = null;
	String document_url = null;

	PageFactory pf = new PageFactory();
	String postString = null;
	public int screen= 0;

	@BeforeTest
	public void beforeTest() throws Exception {
		rowData = testcase.get(this.getClass().getSimpleName());
	}

	@Test
	public void testcaseF20() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("Test --" + suiteRunmode + "--" + testRunmode);
		if (!master_condition) {
			status = 3;// excel
			extent = ExtentManager.getReporter(filePath);
			String var = rowData.getTestcaseId();
			String dec = rowData.getTestcaseDescription();
			String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
			String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
			logger.info(rowData.getTestcaseId());
			for (int i = 0; i < tests.length; i++) {
				logger.info(tests_dec[i]);
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("Notifications");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		try {
			if (user1 != null && user2 != null && user3 != null) {
				postString = "PostCreationTest" + RandomStringUtils.randomNumeric(10);
				openBrowser();
				maximizeWindow();
				clearCookies();
				ob.navigate().to(host);
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
				boolean postStatus = watchPost();
				boolean commentstatus = addCommentOnPost();
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-217",
									"Verify that user receives a notification when someone comments on an post contained in his watchlist")
							.assignCategory("Notifications");
					test.log(LogStatus.INFO, "Verify a post -" + postString);
					if (postStatus && commentstatus) {
						test.log(LogStatus.INFO, "Watching post successfully");
						test.log(LogStatus.INFO, "added commnet on post successfully");
						verifyWatchPostNotification();
					} else {
						if (!postStatus) {
							test.log(LogStatus.INFO, "Facing issue with watching post");
						}
						if (!commentstatus) {
							test.log(LogStatus.INFO, "Facing issue with adding comment");
						}
						throw new Exception("Verify Post Exception");
					}
				} catch (Throwable t) {
					test.log(LogStatus.FAIL, t.getMessage());
					logger.info(t.getMessage());
				} finally {
					extent.endTest(test);
				}

				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent.startTest("OPQA-216",
							"Verify that user receives a notification when someone he is following user comments on a post")

							.assignCategory("Notifications");
					if (commentstatus) {
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
				try {
					extent = ExtentManager.getReporter(filePath);
					test = extent
							.startTest("OPQA-218",
									"Verify that user receives a notification if someone likes his comment on a post")
							.assignCategory("Notifications");
					if (commentstatus) {
						test.log(LogStatus.INFO, "added commnet on post successfully");
						notification3();
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
				}finally {
					extent.endTest(test);
				}
			} else {
				logger.info("User creation problem hence Failing this test case");
				throw new Exception("User creation problem hence throwing exception");
			}

//			AddCommentWatchPost();
//			BrowserWaits.waitTime(4);
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
				test.log(LogStatus.FAIL, "Something unexpected happened" + t);// extent
				// next 3 lines to print whole testng error in report
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			}
		} finally {
			closeBrowser();
		}
	}

//	private void AddCommentWatchPost() throws Exception {
//		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
//		pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
//		pf.getLoginTRInstance(ob).clickLogin();
//		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("apps")), 30);
//		ob.navigate().to(document_url);
//		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 30);
//		BrowserWaits.waitTime(7);
//		ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("Thanks for watching my post");
//		BrowserWaits.waitTime(5);
//		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_addComment_button")), 30);
//		jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
//		BrowserWaits.waitTime(10);
//		pf.getLoginTRInstance(ob).logOutApp();
//	}

	private void notification3() throws Exception {
		try {
			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.navigate().to(document_url);
			logger.info(document_url);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_commentLike_button")), 30);
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("document_commentLike_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_commentLike_button"))));
			BrowserWaits.waitTime(1);
			pf.getLoginTRInstance(ob).logOutApp();

			// 3)Login with user2 again and verify that he receives a correct
			// notification
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
			pf.getLoginTRInstance(ob).enterTRCredentials(user3, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			BrowserWaits.waitTime(10);
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("notificationForLike")), 30);
			for (int i = 1; i <= 3; i++) {
				String text = ob.findElement(By.xpath(OR.getProperty("notificationForLike"))).getText();
				if (text.length() > 0) {
					break;
				}
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
				BrowserWaits.waitTime(3);
			}
			String text = ob.findElement(By.xpath(OR.getProperty("notificationForLike"))).getText();
			logger.info("Notification Text: " + text);
			try {
				Assert.assertTrue(text.contains("Liked your comment") && text.contains(OR.getProperty("COMMENT_TEXT"))
						&& text.contains(fn2 + " " + ln2));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				Assert.assertTrue(false);
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports // reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + screen++)));// screenshot
			}
		} catch (Exception e) {
			throw new Exception("Got exceprion while liking comment");
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}

	}

	private void verifycommentNotification() throws Exception {
		try {
			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			BrowserWaits.waitTime(10);
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			for (int i = 1; i <= 3; i++) {
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
				BrowserWaits.waitTime(3);
				String text = ob.findElement(By.xpath(OR.getProperty("notificationCommentEvent"))).getText();
				if (text.length() > 0) {
					break;
				}
			}
			String text = ob.findElement(By.xpath(OR.getProperty("notificationCommentEvent"))).getText();
			logger.info("Notification Text: " + text);
			try {
				Assert.assertTrue(text.contains(fn3 + " " + ln3) && text.contains("commented on")
						&& text.contains(document_title));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString()); // reports
				test.log(LogStatus.INFO, "Error--->" + t.getMessage());
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened" + t);// extent
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + screen++)));// screenshot
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
	}

	private void verifyWatchPostNotification() throws Exception {
		try {
			// 3)Login with user1 again and verify that he receives a correct
			// notification
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("notificationDocumentComment")), 100);
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			for (int i = 1; i <= 3; i++) {
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
				BrowserWaits.waitTime(3);
				String text = ob.findElement(By.xpath(OR.getProperty("notificationDocumentComment"))).getText();
				if (text.length() > 0) {
					break;
				}
			}
			String text = ob.findElement(By.xpath(OR.getProperty("notificationDocumentComment"))).getText();
			logger.info("Notification Text: " + text);
			String expected_text = fn3 + " " + ln3;
			logger.info("Expeted Text : " + expected_text);
			try {
				Assert.assertTrue(text.contains("New comments") && text.contains(expected_text)
						&& text.contains(document_title) && text.contains(OR.getProperty("COMMENT_TEXT")));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User receiving notification with incorrect content" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				// test.log(LogStatus.INFO, errors.toString()); // reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + screen++)));// screenshot
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Fail");
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			 pf.getLoginTRInstance(ob).logOutApp();
		}

	}

	private boolean addCommentOnPost() throws Exception {
		boolean status = false;
		try {
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
			pf.getLoginTRInstance(ob).enterTRCredentials(user3, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("apps")), 30);
			ob.navigate().to(document_url);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 30);
			BrowserWaits.waitTime(7);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox")))
					.sendKeys(OR.getProperty("COMMENT_TEXT"));
			BrowserWaits.waitTime(5);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_addComment_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			BrowserWaits.waitTime(10);
			status = true;
		} catch (Exception e) {
			logger.error("Probem happens while adding comment on post" + e.getMessage());
			captureScreenshot(this.getClass().getSimpleName() + "_adding_cooment");
			//throw new Exception("Probem happens while adding comment on post" + e.getMessage());
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
		return status;
	}

	private boolean watchPost() throws Exception {
		boolean status = false;
		try {
			
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			BrowserWaits.waitTime(4);
			waitForElementTobeVisible(ob,
					By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']"), 30);
			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']"))
					.click();
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'Posts')]"), 30);
			BrowserWaits.waitTime(2);
			ob.findElement(By.xpath("//a[contains(text(),'Posts')]")).click();
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("posts");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 120);
			document_title = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			document_url = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getAttribute("href");
			logger.info(document_url);
			String watchStatus = ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).getText();
			if (watchStatus.contains("Stop Watching")) {
				ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).click();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("selectWatchListInBucket")), 30);
				ob.findElement(By.xpath(OR.getProperty("selectWatchListInBucket"))).click();
			}
			BrowserWaits.waitTime(2);
			ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("selectWatchListInBucket")), 30);
			ob.findElement(By.xpath(OR.getProperty("selectWatchListInBucket"))).click();
			BrowserWaits.waitTime(2);
			// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("closeWatchListBucketDisplay")), 30);
			ob.findElement(By.xpath(OR.getProperty("closeWatchListBucketDisplay"))).click();
			BrowserWaits.waitTime(4);
			status = true;
		} catch (Exception e) {
			logger.error("Watch post problem" + e.getMessage());
			captureScreenshot(this.getClass().getSimpleName() + "_watching_Post");
			//throw new Exception("Watch post problem" + e.getMessage());
		} finally {
			pf.getLoginTRInstance(ob).logOutApp();
		}
		return status;
	}

	@AfterTest
	public void reportTestResult() {
		//extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "FAIL"); else TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
