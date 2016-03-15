package suiteF;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class TestCase_F6 extends TestBase {
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
		test = extent.startTest(var, "Verify that user is able to receive notification when my friend is following some other user.")
				.assignCategory("Suite F");

	}

	@Test
	public void testcaseF6() throws Exception {
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
			//Create User 3
			fn3 = generateRandomName(8);
			ln3 = generateRandomName(10);
			System.out.println(fn3 + " " + ln3);
			user3 = createNewUser(fn3, ln3);
			Thread.sleep(2000);
			LoginTR.logOutApp();
			LoginTR.enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			LoginTR.clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")),30);
			//User1 searches User3
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(fn3 + " " + ln3);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0,-500)");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("profilesTabHeading_link")),30);
			ob.findElement(By.xpath(OR.getProperty("profilesTabHeading_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_follow_button")), 40);
			//User1 follows User3
			ob.findElement(By.xpath(OR.getProperty("search_follow_button"))).click();
			Thread.sleep(3000);
			LoginTR.logOutApp();
			//User2 Logging in
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")),20);

			LoginTR.enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			LoginTR.clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 50);
			String text = ob.findElement(By.xpath(OR.getProperty("following_friend_notification"))).getText();
			System.out.println(text);
			try {
				Assert.assertTrue(text.contains("TODAY") && text.contains(fn1 + " " + ln1)
						&& text.contains("is now following") && text.contains(fn3 + " " + ln3));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
				LoginTR.logOutApp();
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
