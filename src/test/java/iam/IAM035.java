package iam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class IAM035 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IAM");
	}

	@Test
	public void testcaseA4() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		openBrowser1();

		try {
			ob.get("https://www.guerrillamail.com");
			BrowserWaits.waitTime(2);
			if (CONFIG.getProperty("browserType").equals("IE")) {
				Runtime.getRuntime().exec("C:/Users/uc204155/Desktop/IEScript.exe");
				BrowserWaits.waitTime(4);
			}
			String email1 = ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();
			logger.info("Email Id:" + email1);
			String traillingSpaceEmail = " " + email1;
			logger.info("After Trailling space :" + traillingSpaceEmail);

			String str = createTraillingSpaceNeonUser("duster", "man", traillingSpaceEmail);
			logger.info("After Trailling user :" + str);
			continueToLandingNeonPage();
			BrowserWaits.waitTime(3);

			/*
			 * openBrowser1(); ob.get("https://www.guerrillamail.com"); BrowserWaits.waitTime(2); if
			 * (CONFIG.getProperty("browserType").equals("IE")) {
			 * Runtime.getRuntime().exec("C:/Users/uc204155/Desktop/IEScript.exe"); BrowserWaits.waitTime(4); } String
			 * email2 = ob.findElement(By.id(OR.getProperty("email_textBox"))).getText(); logger.info("Email Id:" +
			 * email2); String traillingSpaceEmail1 = email2 + " "; logger.info("Trailing space1 :" +
			 * traillingSpaceEmail1); String str1 = createTraillingSpaceNeonUser("duster", "man", traillingSpaceEmail1);
			 * logger.info("After Trailling user :" + str1); continueToLandingNeonPage();
			 */
		} catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
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

	private void continueToLandingNeonPage() throws Exception {
		BrowserWaits.waitTime(10);

		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("ul_name")), 30);
		if (!checkElementPresence("ul_name")) {

			test.log(LogStatus.FAIL, "New user credentials are not working fine");// extent reports
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_existing_FB_User_credentials_not_working_fine")));// screenshot
			closeBrowser();

		}
		logout();
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_LANDING_PAGE_LOGGIN_BANNER_CSS);
		/*waitForElementTobeVisible(ob, By.xpath(OR.getProperty("login_banner")), 8);

		if (!checkElementPresence("login_banner")) {

			test.log(LogStatus.FAIL, "User not able to logout successfully");// extent reports
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_logout_successfully")));// screenshot
			closeBrowser();

		}*/
		closeBrowser();

	}

	private void openBrowser1() throws Exception {
		openBrowser();
		try {
			maximizeWindow();
		} catch (Throwable t) {

			System.out.println("maximize() command not supported in Selendroid");
		}
		clearCookies();

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
