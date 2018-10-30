package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class PUBLONS016 extends TestBase {

	String runmodes[] = null;
	static int count = -1;
	String[] tests;
	String[] tests_dec;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {

		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		String var = rowData.getTestcaseId();
		String dec = rowData.getTestcaseDescription();
		tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
		tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
		test = extent.startTest(tests[0], tests_dec[0]).assignCategory(
				"PUBLONS");
		test.log(LogStatus.INFO, tests[0]);

	}

	@Test
	public void testcaseA6() throws Exception {

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
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory(
						"PUBLONS");
				test.log(LogStatus.SKIP, "Skipping test case "
						+ this.getClass().getSimpleName()
						+ " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case"
					+ this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution starts--->");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-5817",
								"Verify that Forgot your password? Link is clickable on publons Landing page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution start");

				pf.getIamPage(ob).clickForgotPasswordLink();
				test.log(LogStatus.PASS,
						"Forgot password? Link is clickable on publons landing page");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Forgot password? Link is not clickable on publons landing page"
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this
										.getClass().getSimpleName()
										+ "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution end");
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest(
								"OPQA-5818",
								"Verify that the publons should be displayed on the forgot password page&&Verify that 'publons' should be moved within the white area and should be above 'Forgot Password' text")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution start");
				pf.getIamPage(ob).checkAppName(
						"Sign in to continue with Project Neon");
				test.log(LogStatus.PASS,
						"Application name displayed successfully in forgot password page");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Application name not displayed in forgot password page"
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this
										.getClass().getSimpleName()
										+ "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution end");
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest(
								"OPQA-5819",
								"Verify that the system is navigating to Forgot Password page or not, after clicking on Forgot your password? Link&Verify that,the system should support a publons password reset workflow")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution start");

				pf.getIamPage(ob).validateTextInForgotPasswordPage();
				test.log(
						LogStatus.PASS,
						"System is navigating to Forgot Password page, after clicking on Forgot password? Link");

			} catch (Throwable t) {
				test.log(
						LogStatus.FAIL,
						"System is not navigating to Forgot Password page, after clicking on Forgot password? Link"
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this
										.getClass().getSimpleName()
										+ "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution end");
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest(
								"OPQA-4246",
								"Verify that when Email address is known from password reset token,error message 'The email address is prepopulated.' should be displayed and email address field should be editable")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution start");
				ob.navigate().to(host);
				pf.getIamPage(ob).sendEamilToTextBox(
						"5j6b6y+dzzvwq8idhgf4@sharklasers.com");
				pf.getIamPage(ob).clickForgotPasswordLink();
				pf.getIamPage(ob).checkPrepopulatedText(
						"5j6b6y+dzzvwq8idhgf4@sharklasers.com");
				pf.getIamPage(ob).clickCancelButton();
				pf.getIamPage(ob).checkLoginPage();
				test.log(LogStatus.PASS, "Email field is prepopulated.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Email field is not prepopulated" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this
										.getClass().getSimpleName()
										+ "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution end");
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest(
								"OPQA-5837",
								"Verify that when Email address is not known from password reset token,email address field should be blank and user should be able to enter any email address")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution start");
				pf.getIamPage(ob).clickForgotPasswordLink();
				pf.getIamPage(ob).checkPrepopulatedText("");
				pf.getIamPage(ob).clickCancelButton();
				pf.getIamPage(ob).checkLoginPage();
				test.log(LogStatus.PASS, "Email field is prepopulated.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Email field is not prepopulated" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this
										.getClass().getSimpleName()
										+ "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName()
						+ " execution end");
				extent.endTest(test);
			}

			
			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-5838",
								"Verify that error message Please enter a valid email address.should be displayed in red color when user enters email address in wrong format")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).clickForgotPasswordLink();
				pf.getIamPage(ob).sendEamilToTextBox("abcd.com");
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_STEPUPAUTHMODAL_FORGOTPW_PAGE_CSS.toString())).click();
				pf.getIamPage(ob).checkErrorMessage("Please enter a valid email address.");
				pf.getIamPage(ob).clickCancelButton();
				pf.getIamPage(ob).checkLoginPage();
				test.log(LogStatus.PASS, "Email field is prepopulated.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Email field is not prepopulated" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			
			
			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-5841",
								"Verify that Cancel link working in forgot password page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).clickForgotPasswordLink();
				pf.getIamPage(ob).clickCancelButton();
				pf.getIamPage(ob).checkLoginPage();
				test.log(LogStatus.PASS, "Email field is prepopulated.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Email field is not prepopulated" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			
			
			
			ob.quit();
		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			extent = ExtentManager.getReporter(filePath);
			String var = rowData.getTestcaseId();
			String dec = rowData.getTestcaseDescription();
			String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
			String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
			logger.info(rowData.getTestcaseId());
			for (int i = 0; i < tests.length; i++) {
				logger.info(tests_dec[i]);
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory(
						"PUBLONS");
				test.log(LogStatus.SKIP, "Skipping test case "
						+ this.getClass().getSimpleName()
						+ " User Not created, hence skiping this test case");
				extent.endTest(test);
			}
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this
									.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

	}

	@AfterTest
	public void reportTestResult() {

		// extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(pubxls, "Test Cases",
		 * TestUtil.getRowNum(pubxls,this.getClass().getSimpleName()), "PASS");
		 * else if(status==2) TestUtil.reportDataSetResult(pubxls, "Test Cases",
		 * TestUtil.getRowNum(pubxls,this.getClass().getSimpleName()), "FAIL");
		 * else TestUtil.reportDataSetResult(pubxls, "Test Cases",
		 * TestUtil.getRowNum(pubxls,this.getClass().getSimpleName()), "SKIP");
		 */

	}

}
