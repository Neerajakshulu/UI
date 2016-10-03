package iam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

public class IAM028 extends TestBase {

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
	public void testcaseA1() throws Exception {
		WebElement element = null;

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

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

			String first_name = "duster";
			String last_name = "man";

			boolean registationStatus = registrationForm(first_name, last_name);
			if (registationStatus) {
				BrowserWaits.waitTime(2);
				ob.get("https://www.guerrillamail.com");
				BrowserWaits.waitTime(12);
				ob.navigate().to(host);
				pf.getLoginTRInstance(ob).enterTRCredentials(email, CONFIG.getProperty("defaultPassword"));
				BrowserWaits.waitTime(2);
				ob.findElement(
						By.xpath("//button[@class='wui-btn login-button button-color-primary wui-btn--primary']"))
						.click();
			}

			BrowserWaits.waitTime(3);
			String textMessage = ob.findElement(By.cssSelector(OR.getProperty("reg_errorMessage"))).getText();
			logger.info("Text Message : " + textMessage);
			if (!textMessage.contains("Please activate your account")) {
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_your_account_not_display_activate_page")));// screenshot
				closeBrowser();
			}

			ob.findElement(By.cssSelector(OR.getProperty("resend_activation"))).click();
			BrowserWaits.waitTime(2);
			ob.findElement(By.xpath(OR.getProperty("signup_conformatin_button"))).click();

			boolean userAction = userActivation();
			if (userAction) {
				pf.getLoginTRInstance(ob).enterTRCredentials(email, CONFIG.getProperty("defaultPassword"));
				pf.getLoginTRInstance(ob).clickLogin();
			}

			if (!checkElementPresence("ul_name")) {

				test.log(LogStatus.FAIL, "Newly registered user credentials are not working fine");// extent
																									// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_newly_registered_user_credentials_are_not_working_fine")));// screenshot
				closeBrowser();

			}

			if (!checkElementPresence("help_link")) {

				test.log(LogStatus.FAIL, "Newly registered user credentials are not working fine");// extent
																									// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_newly_registered_user_credentials_are_not_working_fine")));// screenshot

			}
			// Verify that profile image using below xpath is present or not
			String profile_name_xpath = "//img[@title='" + first_name + " " + last_name + "']";
			element = ob.findElement(By.xpath(profile_name_xpath));
			if (element == null) {

				test.log(LogStatus.FAIL, "Incorrect profile name getting displayed");// extent
																						// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_incorrect_profile_name_getting_displayed")));// screenshot

			}
			logout();
			closeBrowser();

		} catch (Throwable t) {
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
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}
}
