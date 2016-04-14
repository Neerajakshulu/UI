package IAM;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class IAM004 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verify that existing FB user is able to login and logout successfully")
				.assignCategory("IAM");

	}

	@Test
	public void testcaseA4() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "IAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteAxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		String email = "amneetsingh500@gmail.com";
		String password = "Transaction@2";

		try {
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to FB login page
			// ob.navigate().to(host);
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			//
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("FB_login_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("FB_login_button"))).click();
			//
			waitForElementTobeVisible(ob, By.name(OR.getProperty("FB_email_textBox")), 30);

			// Verify that existing FB credentials are working fine
			ob.findElement(By.name(OR.getProperty("FB_email_textBox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("FB_password_textBox"))).sendKeys(password);
			ob.findElement(By.name(OR.getProperty("FB_page_login_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("apps")), 20);
			if (!checkElementPresence("apps")) {

				test.log(LogStatus.FAIL, "Existing FB user credentials are not working fine");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_existing_FB_User_credentials_not_working_fine")));// screenshot

			}

			// Verify that profile name gets displayed correctly
			if (!checkElementPresence("header_label")) {

				test.log(LogStatus.FAIL, "Incorrect profile name getting displayed");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_incorrect_profile_name_getting_displayed")));// screenshot

			}

			logout();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("login_banner")), 8);

			if (!checkElementPresence("login_banner")) {

				test.log(LogStatus.FAIL, "User not able to logout successfully");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_user_unable_to_logout_successfully")));// screenshot

			}

			closeBrowser();

		} catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
