package suiteF;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_F8 extends TestBase {
	static int status = 1;
	PageFactory pf = new PageFactory();
	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent.startTest(var, "Verify that user able to recevie a notification when other user commented on his post")
				.assignCategory("Suite F");

	}

	@Test
	public void testcaseF8() throws Exception {
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
			String postString="PostCreationTest"+RandomStringUtils.randomNumeric(10);
			openBrowser();
			maximizeWindow();
			clearCookies();
			//CREATE A NEW USER AND PUBLISH A POST
			fn3 = generateRandomName(8);
			ln3 = generateRandomName(10);
			System.out.println(fn3 + " " + ln3);
			user3 = createNewUser(fn3, ln3);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("home_page_publish_post_link")),3000);
			ob.findElement(By.xpath(OR.getProperty("home_page_publish_post_link"))).click();
			pf.getProfilePageInstance(ob).enterPostTitle(postString);
			test.log(LogStatus.INFO, "Entered Post Title");
			pf.getProfilePageInstance(ob).enterPostContent(postString);
			test.log(LogStatus.INFO, "Entered Post Content");
			pf.getProfilePageInstance(ob).clickOnPostPublishButton();
			test.log(LogStatus.INFO, "Published the post");
			Thread.sleep(3000);
			pf.getLoginTRInstance(ob).logOutApp();

			//USER1 WILL COMMENT ON THE POST CREATED
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(postString);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(2000);

			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0,-500)");
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("search_results_posts_tab_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("search_results_posts_tab_link"))).click();
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("search_results_post_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).click();
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("document_comment_textbox")), 30);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("Very Nice Post");
			Thread.sleep(5000);
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("document_addComment_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			Thread.sleep(6000);
			pf.getLoginTRInstance(ob).logOutApp();
			
			//LOGIN WITH USER3 AND CHECK FOR THE NOTIFICATION

			pf.getLoginTRInstance(ob).enterTRCredentials(user3, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			Thread.sleep(5000);

			String text = ob.findElement(By.xpath(OR.getProperty("notificationDocumentComment"))).getText();
			System.out.println(text);

			try {
				Assert.assertTrue(text.contains("New comments on your post") && text.contains("TODAY")
						&& text.contains(postString) && text.contains(fn1 + " " + ln1) && text.contains("Very Nice Post"));
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
			// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
			closeBrowser();
		}

	}



	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*if (status == 1)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "SKIP");
*/
	}
}
