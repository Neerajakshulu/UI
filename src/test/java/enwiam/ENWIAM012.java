package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class ENWIAM012 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");

//		extent = ExtentManager.getReporter(filePath);
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//		String var = xlRead2(returnExcelPath('G'), this.getClass().getSimpleName(), 1);
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//		test = extent.startTest(var, "Verify that user is able to login with existing LI id and logout successfully")
//				.assignCategory("ENWIAM");
	}

	@Test
	public void testcaseA3() throws Exception {
		
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

//		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "ENWIAM");
//		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
//		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			String email = "linkedinloginid@gmail.com";
			String password = "Neon@1234";

			// Navigate to LI login page
//			ob.navigate().to(CONFIG.getProperty("enwUrl"));
			ob.get(host+CONFIG.getProperty("appendENWAppUrl"));
			
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("LI_login_button")), 30);
			ob.findElement(By.cssSelector(OR.getProperty("LI_login_button"))).click();
			
			waitForElementTobeVisible(ob, By.name(OR.getProperty("LI_email_textBox")), 30);

			// Verify that existing LI user credentials are working fine
			ob.findElement(By.name(OR.getProperty("LI_email_textBox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("LI_password_textBox"))).sendKeys(password);
			// BrowserWaits.waitTime(2);
			ob.findElement(By.name(OR.getProperty("LI_allowAccess_button"))).click();
			BrowserWaits.waitTime(10);
			//waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.ENW_HOME_AGREE_CSS.toString()),30);
			/*String agreeButton = ob
					.findElement(By.cssSelector(OnePObjectMap.ENW_HOME_AGREE_CSS.toString())).getAttribute("title");
			if(agreeButton.equals("I Agree")){
				ob.findElement(By.cssSelector(OnePObjectMap.ENW_HOME_AGREE_CSS.toString())).click();
			}*/

			String text=ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).getText();
			if(text.equalsIgnoreCase("Continue")){
				ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
			}
			//waitForElementTobeVisible(ob, By.xpath(OR.getProperty("ul_name")), 30);
			if (!checkElementPresence("ul_name")) {

				test.log(LogStatus.FAIL, "Existing LI user credentials are not working fine");// extent
																								// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_existing_LI_User_credentials_not_working_fine")));// screenshot
				closeBrowser();

			}

			
			logoutEnw();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("login_banner")), 8);
			if (!checkElementPresence("login_banner")) {

				test.log(LogStatus.FAIL, "User not able to logout successfully");// extent
																					// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_logout_successfully")));// screenshot
				closeBrowser();

			}

			closeBrowser();

		}

		catch (Throwable t) {

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
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS");
		 * else if(status==2) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL");
		 * else TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
