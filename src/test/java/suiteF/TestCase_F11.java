package suiteF;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;
import base.TestBase;

public class TestCase_F11 extends TestBase {
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
		test = extent.startTest(var, "Verify that user receives a notification if someone likes his comment on a post")
				.assignCategory("Suite F");

	}
	
	@Test
	public void testcaseF11() throws Exception {
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
			openBrowser();
			maximizeWindow();
			clearCookies();
			
			ob.navigate().to(host);
			//Logging in with User1
			LoginTR.enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			LoginTR.clickLogin();
			//searching for posts
			LoginTR.searchArticle("POST for");
			
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0,-500)");
			//Thread.sleep(2000);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("search_results_posts_tab_link")), 30);
			test.log(LogStatus.INFO,"User1 Commenting on a post");
			ob.findElement(By.xpath(OR.getProperty("search_results_posts_tab_link"))).click();
			//Thread.sleep(5000);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("search_results_post_link")), 30);
			String postLinkCommented=ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).getText();
			ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).click();
			//Thread.sleep(4000);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("document_comment_textbox")), 30);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("TestCase_F11:like this Post");
			Thread.sleep(3000);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("document_addComment_button")), 30);
			Thread.sleep(3000);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			Thread.sleep(2000);
			LoginTR.logOutApp();
			
			//create a new user and comment on the same post in user1's watchlist
			
			fn3 = generateRandomName(8);
			ln3 = generateRandomName(10);
			System.out.println(fn3 + " " + ln3);
			user3 = createNewUser(fn3, ln3);
			//Thread.sleep(5000);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("searchBox_textBox")), 30);
			//searching the post and commenting on it
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(postLinkCommented);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			
			jse.executeScript("scroll(0,-500)");
			//Thread.sleep(2000);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("search_results_posts_tab_link")), 30);
			test.log(LogStatus.INFO,"someother user Liking the comment made by user1 on a post");
			ob.findElement(By.xpath(OR.getProperty("search_results_posts_tab_link"))).click();
			//Thread.sleep(5000);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("search_results_post_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).click();
			//Thread.sleep(4000);
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("document_commentLike_button")),30);
			Thread.sleep(4000);
			jsClick(ob,ob.findElement( By.xpath(OR.getProperty("document_commentLike_button"))));
			Thread.sleep(1000);
			LoginTR.logOutApp();
			
			//Login with user1 and check for Notification
			LoginTR.enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			LoginTR.clickLogin();
			
			String text = ob.findElement(By.xpath(OR.getProperty("notificationForLike"))).getText();
			System.out.println(text);


			try {
				Assert.assertTrue(text.contains("TODAY") && text.contains("Liked your comment") && text.contains("TestCase_F11:like this Post") && text.contains(fn3 + " " + ln3));
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

			closeBrowser();
			
			
			
		}catch(Throwable t){
			test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
			// reports
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
