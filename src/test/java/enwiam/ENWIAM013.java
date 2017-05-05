package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
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

public class ENWIAM013 extends TestBase {

	static int status = 1;
	String[] tests;
	String[] tests_dec;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {

		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		String var = rowData.getTestcaseId();
		String dec = rowData.getTestcaseDescription();
		tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
		tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
		test = extent.startTest(tests[0], tests_dec[0]).assignCategory("ENWIAM");
		test.log(LogStatus.INFO, tests[0]);

		// extent = ExtentManager.getReporter(filePath);
		// rowData = testcase.get(this.getClass().getSimpleName());
		// test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");
	}

	@Test
	public void testcaseA16() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;// excel
			extent = ExtentManager.getReporter(filePath);
			String var = rowData.getTestcaseId();
			String dec = rowData.getTestcaseDescription();
			String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
			String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
			logger.info("length : " + tests.length);
			logger.info("doc length : " + tests_dec.length);
			logger.info(rowData.getTestcaseId());
			for (int i = 0; i < tests.length; i++) {
				logger.info(tests_dec[i]);
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("ENWIAM");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			String first_name = "disco";
			String last_name = "dancer";
			String newPassword = "Neon@1234";
			openBrowser();
			maximizeWindow();
			clearCookies();

			String email = createENWNewUser(first_name, last_name);
			logger.info("Email Address : " + email);

			pf.getIamPage(ob).checkAgreeAndContinueButton();

			logoutEnw();
			BrowserWaits.waitTime(4);

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1934",
								"Verify that Forgot your password? Link is clickable on NEON Landing page and End note landing page")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).clickForgotPasswordLink();
				// ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).click();
				test.log(LogStatus.PASS, "Forgot password? Link is clickable on EndNote landing page");
				// BrowserWaits.waitTime(4);

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Forgot password? Link is not clickable on EndNote landing page" + t);// extent
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
						.startTest("OPQA-1935&OPQA-3687",
								"Verify that the system is navigating to Forgot Password page or not, after clicking on Forgot your password? Link&Verify that,the system should support a ENW password reset workflow with the following configurations")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).validateTextInForgotPasswordPage();
				test.log(LogStatus.PASS,
						"System is navigating to Forgot Password page, after clicking on Forgot password? Link");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"System is not navigating to Forgot Password page, after clicking on Forgot password? Link"
								+ t);// extent
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
						.startTest("OPQA-4230",
								"Verify that system should not inform user that entered email is not found.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).sendEamilToTextBox("absssaa112222cddffdd@tr.com");
				pf.getIamPage(ob).clickSendEmailButton();
				pf.getIamPage(ob).checkEmailSentText("absssaa112222cddffdd@tr.com");
				pf.getIamPage(ob).clickOkButton();
				pf.getIamPage(ob).checkLoginPage();

				test.log(LogStatus.PASS, "System not inform user that entered email is not found.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "System inform user that entered email is not found." + t);// extent
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
						.startTest("OPQA-4229",
								"Verify that user should be able to enter email address in Forgot password page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).clickForgotPasswordLink();
				pf.getIamPage(ob).validateTextInForgotPasswordPage();
				pf.getIamPage(ob).sendEamilToTextBox(email);
				test.log(LogStatus.PASS, "User entered email address in Forgot password page successfylly.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User not entered email address in Forgot password page successfylly." + t);// extent
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
						.startTest("OPQA-4231",
								"Verify that  forget password service should send a forgot password email when the email entered is registered in the system")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).clickSendEmailButton();
				pf.getIamPage(ob).checkEmailSentText(email);
				pf.getIamPage(ob).clickOkButton();
				pf.getIamPage(ob).checkLoginPage();
				test.log(LogStatus.PASS, "Forget password service sent email to the registered email in the system");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Forget password service not sent email to the registered email in the system" + t);// extent
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
						.startTest("OPQA-4232",
								"Verify that the platform password reset service should send a platform forget password email with branding that corresponds with the originating application as per wireframe")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).openGurillaMail();
				pf.getIamPage(ob).clickReceivedMail("EndNote");
				pf.getIamPage(ob).checkENWApplicationName("EndNote");
				test.log(LogStatus.PASS,
						"User receive a email with branding that corresponds with the originating application");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"User receive not email with branding that corresponds with the originating application" + t);// extent
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
						.startTest("OPQA-4636",
								"Verify that When the password reset token in the email is valid, upon clicking the password reset link in the the platform forget password email, the user shall be taken to the External Password Reset Page")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).clickResetYourPasswordLink();
				pf.getIamPage(ob).checkExternalPasswordPageText("Reset your password", "Enter a new password below");
				test.log(LogStatus.PASS, "Reset your password page is opened successfylly");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Reset your password page is not opened successfylly" + t);// extent
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
				test = extent
						.startTest("OPQA-1950",
								"Verify Password must have at least one special character from !@#$%^*()~`{}[]| in reset password page")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox("!");
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[5]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				pf.getIamPage(ob).checkTextBox("!@#$%^*()~`{}[]|");
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[5]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				test.log(LogStatus.PASS,
						"Password field allow one special character from !@#$%^*()~`{}[]| in account page");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Password field not allow one special character from !@#$%^*()~`{}[]| in account page");
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
				test = extent
						.startTest("OPQA-1951",
								"Verify  Password must contain at least one number is ALWAYS enforced in password reset page")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).checkTextBox("1");
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[3]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				test.log(LogStatus.PASS, "Password field allowed one number");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password field not allowed one number");
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
				test = extent
						.startTest("OPQA-1953",
								"Verify Password must have at least one alphabet character either upper or lower case is ALWAYS enforced in reset password page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox("a");
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[2]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				test.log(LogStatus.PASS, "Password field allowed one alphabet character");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password field not allowed one alphabet character");
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
				test = extent
						.startTest("OPQA-1949",
								"Verify Password Maximum Length of 95 characters is ALWAYS enforced in reset password page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				String name = "N@1";
				String maxPassword = name + generateRandomName(93);
				logger.info("Last Name : " + maxPassword);
				pf.getIamPage(ob).checkTextBox(maxPassword);
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[6]//div[@class='col-xs-1 password-validator__icon fa color-c5-red fa-times']"),
						30);
				test.log(LogStatus.PASS, "Password field allowed only 95 characters");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password field allowed more than 95 characters");
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
						.startTest("OPQA-4261&&OPQA-1948",
								"Verify that External Password Reset Page should have a new password field where the user enters their new password.&&Verify that the Password minimum length of 8 characters is ALWAYS enforced in reset password page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox(newPassword);
				pf.getIamPage(ob).clickResetButton();
				test.log(LogStatus.PASS, "New password text box is available in External Password reset Page");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Reset your password page is not opened successfylly" + t);// extent
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
						.startTest("OPQA-4244",
								"Verify that when reset Password Token already used user should be taken to sign in screen")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkLoginPage();
				// pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin(email, newPassword);
				pf.getIamPage(ob).login(email, newPassword);
				pf.getIamPage(ob).checkAgreeAndContinueButton();
				logoutEnw();
				test.log(LogStatus.PASS, "New password text box is available in External Password reset Page");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Reset your password page is not opened successfylly" + t);// extent
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
						.startTest("OPQA-4264",
								"Verify that upon successful submission of a password change, The user should receive a password change confirmation email to the user's primary email address with branding that corresponds with the application that the user completed the password change")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).openGurillaMail();
				pf.getIamPage(ob).checkChangedPasswordMailSubject("EndNote");
				pf.getIamPage(ob).checkENWApplicationName("EndNote");

				test.log(LogStatus.PASS, "User received password changed conformation mail");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User not received password changed conformation mail" + t);// extent
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
						.startTest("OPQA-4265",
								"Verify that the password change confirmation email should reference the fact that credentials are shared across all products.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.navigate().to(System.getProperty("host"));
				// pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin(email, newPassword);
				// logoutEnw();
				pf.getLoginTRInstance(ob).waitForTRHomePage();
				pf.getLoginTRInstance(ob).enterTRCredentials(email, newPassword);
				pf.getLoginTRInstance(ob).clickLogin();
				// pf.getIamPage(ob).login(email, newPassword);
				// pf.getIamPage(ob).checkAgreeAndContinueButton();
				logout();
				pf.getIamPage(ob).checkLoginPage();
				test.log(LogStatus.PASS, "User login and logout successfylly other applications");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User not login and logout successfylly other applications" + t);// extent
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
						.startTest("OPQA-4237",
								"Verify that when the password reset token in the email is expired or already used, upon clicking the password reset link in the the platform forget password email, the user should be taken to the External Invalid Password Reset Token Page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).openGurillaMail();
				pf.getIamPage(ob).checkAlreadyUsedMailSubject("EndNote");
				pf.getIamPage(ob).checkENWApplicationName("EndNote");
				pf.getIamPage(ob).clickResetYourPasswordLink();
				test.log(LogStatus.PASS, "User login and logout successfylly other applications");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User not login and logout successfylly other applications" + t);// extent
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
						.startTest("OPQA-4239",
								"Verify that the email address on the External Invalid Password Reset Token Page should be pre-populated with the email address that matches the email that the forgot password email was sent.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).checkInvalidPasswordResetPage();
				test.log(LogStatus.PASS, "Invalid password reset pagee is opend successfylly");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Invalid password reset pagee is opend successfylly" + t);// extent
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
						.startTest("OPQA-4240",
								"Verify that user who clicks the submit button on the the External Invalid Password Reset Token page, should be taken to the target application sign in page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).checkPrepopulatedText(email);
				pf.getIamPage(ob).clickResendEmailButton();
				test.log(LogStatus.PASS, "Invalid password reset pagee is opend successfylly");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Invalid password reset pagee is opend successfylly" + t);// extent
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
						.startTest("OPQA-4246",
								"Verify that when Email address is known from password reset token,error message 'The email address is prepopulated.' should be displayed and email address field should be editable")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				logger.info("5j6b6y+dzzvwq8idhgf4@sharklasers.com");
				pf.getIamPage(ob).sendEamilToTextBox("5j6b6y+dzzvwq8idhgf4@sharklasers.com");
				pf.getIamPage(ob).clickForgotPasswordLink();
				pf.getIamPage(ob).checkPrepopulatedText("5j6b6y+dzzvwq8idhgf4@sharklasers.com");
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
						.startTest("OPQA-4248",
								"Verify that when Email address is not known from password reset token,email address field should be blank and user should be able to enter any email address")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				BrowserWaits.waitTime(3);
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
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-4252",
								"Verify that error message Please enter a valid email address.should be displayed in red color when user enters email address in wrong format")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				BrowserWaits.waitTime(3);
				pf.getIamPage(ob).clickForgotPasswordLink();
				pf.getIamPage(ob).sendEamilToTextBox("abcd.com");
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
						.startTest("OPQA-5399",
								"Verify that error message New password should not match current password. when enter Old and New password are same in reset password page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).openGurillaMail();
				pf.getIamPage(ob).clickReceivedMail("EndNote");
				pf.getIamPage(ob).checkENWApplicationName("EndNote");
				pf.getIamPage(ob).clickResetYourPasswordLink();
				pf.getIamPage(ob).checkExternalPasswordPageText("Reset your password", "Enter a new password below");
				pf.getIamPage(ob).checkTextBox(newPassword);
				pf.getIamPage(ob).clickResetButton();
				BrowserWaits.waitTime(3);
				String str = ob
						.findElement(
								By.cssSelector(OnePObjectMap.PASSWORD_RESET_PAGE_PASSWORD_ERROR_MESSAGE_CSS.toString()))
						.getText();

				Assert.assertEquals(str, "New password should not match current password");
				test.log(LogStatus.PASS, "Valid error massage displayed");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "New password should match current password" + t);// extent
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
						.startTest("OPQA-5400",
								"Verify that error message New password should not match previous 4 passwords. when enter new password match with previous four passwords in reset password page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox("Neon@123");
				pf.getIamPage(ob).clickResetButton();
				BrowserWaits.waitTime(3);
				String str = ob
						.findElement(
								By.cssSelector(OnePObjectMap.PASSWORD_RESET_PAGE_PASSWORD_ERROR_MESSAGE_CSS.toString()))
						.getText();
				Assert.assertEquals(str, "New password should not match previous 4 passwords");
				test.log(LogStatus.PASS, "Valid error massage displayed");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "New password should match current password" + t);// extent
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
			/*
			 * try { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1934",
			 * "Verify that Forgot your password? Link is clickable on NEON Landing page and End note landing page")
			 * .assignCategory("ENWIAM"); test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start"
			 * ); String forgotPassText = ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).getText();
			 * logger.info("Fogot Password Text : " + forgotPassText); Assert.assertEquals(forgotPassText,
			 * "Forgot password?"); ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).click();
			 * test.log(LogStatus.PASS, "Forgot password? Link is clickable on EndNote landing page");
			 * BrowserWaits.waitTime(4); } catch (Throwable t) { test.log(LogStatus.FAIL,
			 * "Forgot password? Link is not clickable on EndNote landing page" + t);// extent StringWriter errors = new
			 * StringWriter(); t.printStackTrace(new PrintWriter(errors)); test.log(LogStatus.INFO,
			 * errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);// testng status = 2;// excel
			 * test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1935||OPQA-3687",
			 * "Verify that the system is navigating to Forgot Password page or not, after clicking on Forgot your password? Link||Verify that,the system should support a ENW password reset workflow with the following configurations"
			 * ) .assignCategory("ENWIAM"); test.log(LogStatus.INFO, this.getClass().getSimpleName() +
			 * " execution start"); pf.getIamPage(ob).validateTextInForgotPasswordPage(); String resertPassPage = ob
			 * .findElement(By.cssSelector(OnePObjectMap.ENDNOTE_RESET_PASSWORD_PAGE_CSS.toString())) .getText();
			 * Assert.assertEquals(resertPassPage, "Reset your password"); test.log(LogStatus.PASS,
			 * "System is navigating to Forgot Password page, after clicking on Forgot password? Link"); } catch
			 * (Throwable t) { test.log(LogStatus.FAIL,
			 * "System is not navigating to Forgot Password page, after clicking on Forgot password? Link" + t);//
			 * extent StringWriter errors = new StringWriter(); t.printStackTrace(new PrintWriter(errors));
			 * test.log(LogStatus.INFO, errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);//
			 * testng status = 2;// excel test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-4230",
			 * "Verify that system should not inform user that entered email is not found.") .assignCategory("ENWIAM");
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
			 * pf.getIamPage(ob).sendEamilToTextBox("absssaa112222cddffdd@tr.com");
			 * pf.getIamPage(ob).clickSendEmailButton(); pf.getIamPage(ob).checkEmailSentText(email);
			 * pf.getIamPage(ob).clickOkButton(); pf.getIamPage(ob).checkLoginPage(); test.log(LogStatus.PASS,
			 * "Verify that system should not inform user that entered email is not found."); } catch (Throwable t) {
			 * test.log(LogStatus.FAIL,
			 * "ENW reset password workflow not bring the user to the send email verification page where a verification email can be sent to an email address entered by the user."
			 * + t);// extent StringWriter errors = new StringWriter(); t.printStackTrace(new PrintWriter(errors));
			 * test.log(LogStatus.INFO, errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);//
			 * testng status = 2;// excel test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1945",
			 * "Verify that Upon initiation, the Neon and ENW reset password workflow shall bring the user to the send email verification page where a verification email can be sent to an email address entered by the user."
			 * ) .assignCategory("ENWIAM"); test.log(LogStatus.INFO, this.getClass().getSimpleName() +
			 * " execution start"); // String //
			 * emailVeficationPage=ob.findElement(By.cssSelector("input[class='button']")).getText(); //
			 * Assert.assertEquals(emailVeficationPage, "Send verification // email");
			 * pf.getIamPage(ob).sendEamilToTextBox("5fu4a1+9p55m06bssnmg@sharklasers.com");
			 * pf.getIamPage(ob).clickSendEmailButton(); pf.getIamPage(ob).checkEmailSentText(email);
			 * pf.getIamPage(ob).clickOkButton(); pf.getIamPage(ob).checkLoginPage(); waitForElementTobeVisible(ob,
			 * By.id(OR.getProperty("email_Address")), 30);
			 * ob.findElement(By.id(OR.getProperty("email_Address"))).sendKeys(email);
			 * ob.findElement(By.xpath(OR.getProperty("verification_email_button"))).click(); BrowserWaits.waitTime(3);
			 * waitForElementTobeVisible(ob, By.xpath(OR.getProperty("confomr_message")), 30); String text =
			 * ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText(); logger.info("Email Address : " +
			 * text); String expected_text = "An email with password reset instructions has been sent to " + email;
			 * logger.info("Expected Email : " + expected_text); String checkEmail1 =
			 * ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText(); logger.info(
			 * "Email Address : " + checkEmail1); String checkEmail = "Please check your email";
			 * Assert.assertEquals(text.contains(expected_text), checkEmail.contains(checkEmail1));
			 * test.log(LogStatus.PASS,
			 * "ENW reset password workflow bring the user to the send email verification page where a verification email can be sent to an email address entered by the user."
			 * ); } catch (Throwable t) { test.log(LogStatus.FAIL,
			 * "ENW reset password workflow not bring the user to the send email verification page where a verification email can be sent to an email address entered by the user."
			 * + t);// extent StringWriter errors = new StringWriter(); t.printStackTrace(new PrintWriter(errors));
			 * test.log(LogStatus.INFO, errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);//
			 * testng status = 2;// excel test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1946",
			 * "Verify that the Neon and ENW reset password workflow shall be able to send a verification email to the user"
			 * ) .assignCategory("ENWIAM"); test.log(LogStatus.INFO, this.getClass().getSimpleName() +
			 * " execution start"); BrowserWaits.waitTime(3); // ob.close(); // ob.switchTo().window(al.get(0)); //
			 * Thread.sleep(2000); pf.getIamPage(ob).openGurillaMail(); pf.getIamPage(ob).clickReceivedMail("EndNote");
			 * ob.get("https://www.guerrillamail.com"); BrowserWaits.waitTime(12); List<WebElement> email_list =
			 * ob.findElements(By.xpath(OR.getProperty("email_list"))); WebElement myE = email_list.get(0);
			 * JavascriptExecutor executor = (JavascriptExecutor) ob; executor.executeScript("arguments[0].click();",
			 * myE); // email_list.get(0).click(); Thread.sleep(4000); String email_subject =
			 * ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText(); logger.info(
			 * "Email Subject Text : " + email_subject); Assert.assertEquals(email_subject,
			 * "EndNote&trade;_password_change_request"); test.log(LogStatus.PASS,
			 * "ENW reset password workflow able to send a verification email to the user"); } catch (Throwable t) {
			 * test.log(LogStatus.FAIL, "ENW reset password workflow not able to send a verification email to the user"
			 * + t);// extent StringWriter errors = new StringWriter(); t.printStackTrace(new PrintWriter(errors));
			 * test.log(LogStatus.INFO, errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);//
			 * testng status = 2;// excel test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1947",
			 * "Verify that Upon clicking the link to reset password in the Neon and ENW reset verification email, the user shall be sent to the password reset page to reset the applicable STeAM user"
			 * ) .assignCategory("ENWIAM"); test.log(LogStatus.INFO, this.getClass().getSimpleName() +
			 * " execution start"); pf.getIamPage(ob).clickResetPasswordLink(); // WebElement reset_link_element = ob //
			 * .findElement(By.xpath(OR.getProperty("email_body_password_reset_link"))); // String reset_link_url =
			 * reset_link_element.getAttribute("href"); // ob.get(reset_link_url); test.log(LogStatus.PASS,
			 * "After clicking the link to reset password ENW reset verification email, the user sent to the password reset page to reset the applicable STeAM user"
			 * ); } catch (Throwable t) { test.log(LogStatus.FAIL,
			 * "After clicking the link to reset password ENW reset verification email, the user not sent to the password reset page to reset the applicable STeAM user"
			 * + t);// extent StringWriter errors = new StringWriter(); t.printStackTrace(new PrintWriter(errors));
			 * test.log(LogStatus.INFO, errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);//
			 * testng status = 2;// excel test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1950",
			 * "Verify Password must have at least one special character from !@#$%^*()~`{}[]|")
			 * .assignCategory("ENWIAM"); pf.getIamPage(ob).checkAllowedCharacters("!",4); test.log(LogStatus.INFO,
			 * this.getClass().getSimpleName() + " execution start"); waitForElementTobeVisible(ob,
			 * By.id(OR.getProperty("newPassword_textBox")), 30);
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).clear();
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("!"); BrowserWaits.waitTime(2);
			 * String specialChar = ob.findElement(By.cssSelector("span[id='hasSym']")).getAttribute("style");
			 * logger.info("Color : " + specialChar); Assert.assertTrue(specialChar.contains("rgb(30, 86, 21)"));
			 * test.log(LogStatus.PASS, "Password field allow one special character from !@#$%^*()~`{}[]|"); } catch
			 * (Throwable t) { test.log(LogStatus.FAIL,
			 * "Password field not allow one special character from !@#$%^*()~`{}[]|" + t);// extent StringWriter errors
			 * = new StringWriter(); t.printStackTrace(new PrintWriter(errors)); test.log(LogStatus.INFO,
			 * errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);// testng status = 2;// excel
			 * test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1951",
			 * "Verify Password must contain at least one number is ALWAYS enforced.") .assignCategory("ENWIAM");
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
			 * waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).clear();
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("1"); BrowserWaits.waitTime(2);
			 * String number = ob.findElement(By.cssSelector("span[id='hasNum']")).getAttribute("style"); logger.info(
			 * "Color : " + number); Assert.assertTrue(number.contains("rgb(30, 86, 21)")); test.log(LogStatus.PASS,
			 * "Password field allowed one number"); } catch (Throwable t) { test.log(LogStatus.FAIL,
			 * "Password field not allowed one number" + t);// extent StringWriter errors = new StringWriter();
			 * t.printStackTrace(new PrintWriter(errors)); test.log(LogStatus.INFO, errors.toString());// extent reports
			 * ErrorUtil.addVerificationFailure(t);// testng status = 2;// excel test.log(LogStatus.INFO,
			 * "Snapshot below: " + test.addScreenCapture( captureScreenshot(this.getClass().getSimpleName() +
			 * "_something_unexpected_happened"))); } finally { test.log(LogStatus.INFO, this.getClass().getSimpleName()
			 * + " execution end"); extent.endTest(test); } try { extent = ExtentManager.getReporter(filePath); test =
			 * extent .startTest("OPQA-1953",
			 * "Verify Password must have at least one alphabet character either upper or lower case is ALWAYS enforced."
			 * ) .assignCategory("ENWIAM"); test.log(LogStatus.INFO, this.getClass().getSimpleName() +
			 * " execution start"); waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).clear();
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("P"); BrowserWaits.waitTime(2);
			 * String alphaCahr = ob.findElement(By.cssSelector("span[id='hasChar']")).getAttribute("style");
			 * logger.info("Color : " + alphaCahr); Assert.assertTrue(alphaCahr.contains("rgb(30, 86, 21)"));
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).clear();
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("p"); BrowserWaits.waitTime(2);
			 * String alphaCahr1 = ob.findElement(By.cssSelector("span[id='hasChar']")).getAttribute("style");
			 * logger.info("Color : " + alphaCahr1); Assert.assertTrue(alphaCahr1.contains("rgb(30, 86, 21)"));
			 * test.log(LogStatus.PASS, "Password field allowed one alphabet character"); } catch (Throwable t) {
			 * test.log(LogStatus.FAIL, "Password field not allowed one alphabet character" + t);// extent StringWriter
			 * errors = new StringWriter(); t.printStackTrace(new PrintWriter(errors)); test.log(LogStatus.INFO,
			 * errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);// testng status = 2;// excel
			 * test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1948",
			 * "Verify that the Password minimum length of 8 characters is ALWAYS enforced") .assignCategory("ENWIAM");
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
			 * waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).clear();
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("Neon@1234");
			 * BrowserWaits.waitTime(2); String eightChar =
			 * ob.findElement(By.cssSelector("span[id='minLength']")).getAttribute("style"); logger.info("Color : " +
			 * eightChar); Assert.assertTrue(eightChar.contains("rgb(30, 86, 21)"));
			 * ob.findElement(By.id(OR.getProperty("confirmPassword_textBox"))).sendKeys("Neon@1234");
			 * ob.findElement(By.id(OR.getProperty("update_password"))).click(); test.log(LogStatus.PASS,
			 * "Password field allowed 8 or more characters"); } catch (Throwable t) { test.log(LogStatus.FAIL,
			 * "Password field not  allowed 8 or more characters" + t);// extent StringWriter errors = new
			 * StringWriter(); t.printStackTrace(new PrintWriter(errors)); test.log(LogStatus.INFO,
			 * errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);// testng status = 2;// excel
			 * test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1954",
			 * "Verify Upon completion of establishing a new password, a user who wants to go to ENW shall be presented a confirmation page with an optional link back to ENW Landing page"
			 * ) .assignCategory("ENWIAM"); test.log(LogStatus.INFO, this.getClass().getSimpleName() +
			 * " execution start"); BrowserWaits.waitTime(3); String checkEmail2 =
			 * ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText(); logger.info(
			 * "Email Address : " + checkEmail2); waitForElementTobeVisible(ob,
			 * By.xpath(OR.getProperty("confomr_message")), 30); String text =
			 * ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText(); logger.info("Expected Text : " +
			 * text); String expected_text = "Your password has been updated"; String expectedText =
			 * "Your password has been successfully updated. A confirmation has been sent to your email address.";
			 * Assert.assertTrue(checkEmail2.contains(expected_text) && text.contains(expectedText));
			 * BrowserWaits.waitTime(2); ob.findElement(By.cssSelector("input[class='button']")).click();
			 * test.log(LogStatus.PASS,
			 * "After reset new password,  user go to ENW confirmation page with an optional link back to ENW Landing page"
			 * ); } catch (Throwable t) { test.log(LogStatus.FAIL,
			 * "After resetting new password,  user not go to ENW confirmation page with an optional link back to ENW Landing page"
			 * + t);// extent StringWriter errors = new StringWriter(); t.printStackTrace(new PrintWriter(errors));
			 * test.log(LogStatus.INFO, errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);//
			 * testng status = 2;// excel test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); } try
			 * { extent = ExtentManager.getReporter(filePath); test = extent .startTest("OPQA-1937",
			 * "Verify that As a Neon or ENW user, I want to be able to reset my STeAM Password from the EndNote Web landing page."
			 * ) .assignCategory("ENWIAM"); test.log(LogStatus.INFO, this.getClass().getSimpleName() +
			 * " execution start"); // BrowserWaits.waitTime(2); //
			 * ob.findElement(By.cssSelector("input[class='button']")).click(); BrowserWaits.waitTime(4);
			 * ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
			 * ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(email);
			 * ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).sendKeys("Neon@1234");
			 * ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click(); Thread.sleep(10000); try { String
			 * text1 = ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).getText();
			 * logger.info("Text : " + text1); if (text1.equalsIgnoreCase("Continue")) {
			 * ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())) .click(); } }
			 * catch (Exception e) { } if (!checkElementPresence("ul_name")) { test.log(LogStatus.FAIL,
			 * "Newly registered user credentials are not working fine");// extent status = 2;// excel
			 * test.log(LogStatus.INFO, "Snapshot below: " +
			 * test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() +
			 * "_newly_registered_user_credentials_are_not_working_fine")));// screenshot closeBrowser(); }
			 * test.log(LogStatus.PASS, "User successfylly login ENW after resetting password"); } catch (Throwable t) {
			 * test.log(LogStatus.FAIL, "User not successfylly login ENW after resetting password" + t);// extent
			 * StringWriter errors = new StringWriter(); t.printStackTrace(new PrintWriter(errors));
			 * test.log(LogStatus.INFO, errors.toString());// extent reports ErrorUtil.addVerificationFailure(t);//
			 * testng status = 2;// excel test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened"))); } finally {
			 * test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end"); extent.endTest(test); }
			 */

			// logoutEnw();

		}

		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");
			extent = ExtentManager.getReporter(filePath);
			String var = rowData.getTestcaseId();
			String dec = rowData.getTestcaseDescription();
			String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
			String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
			logger.info("length : " + tests.length);
			logger.info("doc length : " + tests_dec.length);
			logger.info(rowData.getTestcaseId());
			for (int i = 0; i < tests.length; i++) {
				logger.info(tests_dec[i]);
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("ENWIAM");
				test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
						+ " User Not created, hence skiping this test case");
				extent.endTest(test);
			}

			// extent
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
		// extent.endTest(test);

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
