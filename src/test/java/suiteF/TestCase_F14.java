package suiteF;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.ProfilePage;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class TestCase_F14 extends TestBase {

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
			test = extent.startTest(var, "Verify that user is receiving notification when someone liked his post(aggregated notification)")
					.assignCategory("Suite F");

		}
		
		@Test
		public void testcaseF14() throws Exception {
			boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "F Suite");
			boolean testRunmode = TestUtil.isTestCaseRunnable(suiteFxls, this.getClass().getSimpleName());
			boolean master_condition = suiteRunmode && testRunmode;

			if (!master_condition) {

				status = 3;// excel
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

			}

			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
			try{
				String postString="SamplePostTest"+RandomStringUtils.randomNumeric(10);
				openBrowser();
				maximizeWindow();
				clearCookies();
				JavascriptExecutor jse = (JavascriptExecutor) ob;
				ob.navigate().to(host);
				//Logging in with User2
				LoginTR.enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
				LoginTR.clickLogin();
				waitForElementTobeVisible(ob,By.xpath(OR.getProperty("home_page_publish_post_link")),3000);
				ob.findElement(By.xpath(OR.getProperty("home_page_publish_post_link"))).click();
				ProfilePage.enterPostTitle(postString);
				test.log(LogStatus.INFO, "Entered Post Title");
				ProfilePage.enterPostContent(postString);
				test.log(LogStatus.INFO, "Entered Post Content");
				ProfilePage.clickOnPostPublishButton();
				test.log(LogStatus.INFO, "Published the post");
				Thread.sleep(3000);
				LoginTR.logOutApp();
				
				//Login using user1 and like the post
				LoginTR.enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
				LoginTR.clickLogin();
				waitForElementTobeVisible(ob,By.xpath(OR.getProperty("searchBox_textBox")), 30);
				ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(postString);
				ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
				Thread.sleep(4000);
				
				jse.executeScript("scroll(0,-500)");
				waitForElementTobeVisible(ob,By.xpath(OR.getProperty("search_results_posts_tab_link")), 30);
				test.log(LogStatus.INFO,"Liking the post");
				ob.findElement(By.xpath(OR.getProperty("search_results_posts_tab_link"))).click();
				waitForElementTobeVisible(ob,By.xpath(OR.getProperty("search_results_post_link")), 30);
				ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).click();
				
				waitForElementTobeVisible(ob,By.xpath(OR.getProperty("post_like_button")), 30);
				ob.findElement(By.xpath(OR.getProperty("post_like_button"))).click();
				Thread.sleep(3000);
				LoginTR.logOutApp();
				
				//Login using user2 and check for the notification
				LoginTR.enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
				LoginTR.clickLogin();
				Thread.sleep(7000);
				String text = ob.findElement(By.xpath(OR.getProperty("notificationForLike"))).getText();
				System.out.println(text);
				
				String expected_text = fn1 + " " + ln1;
				try {
					Assert.assertTrue(text.contains("TODAY") && text.contains(expected_text)
							&& text.contains("Liked your post") && text.contains(postString));
					test.log(LogStatus.PASS, "User receiving notification with correct content");
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

				closeBrowser();
				
				
				
			}catch(Throwable t){
				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
				closeBrowser();
			}
		}
		
		@AfterTest
		public void reportTestResult() {
			extent.endTest(test);

			if (status == 1)
				TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
						TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "PASS");
			else if (status == 2)
				TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
						TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "FAIL");
			else
				TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
						TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "SKIP");

		}
}
