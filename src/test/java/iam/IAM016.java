package iam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

public class IAM016 extends TestBase {

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
		test = extent.startTest(tests[0], tests_dec[0]).assignCategory("IAM");
		test.log(LogStatus.INFO, tests[0]);

		// extent = ExtentManager.getReporter(filePath);
		// rowData = testcase.get(this.getClass().getSimpleName());
		// test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IAM");
	}

	@Test
	public void testcaseA16() throws Exception {

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
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("IAM");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			String newPassword = "Neon@1234";
			openBrowser();
			maximizeWindow();
			clearCookies();
			String email = createNewUser("duster", "man");

			logout();
			BrowserWaits.waitTime(4);

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1934",
								"Verify that Forgot your password? Link is clickable on NEON Landing page and End note landing page")
						.assignCategory("IAM");
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
						.assignCategory("IAM");
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
						.assignCategory("IAM");
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
						.assignCategory("IAM");
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
						.assignCategory("IAM");
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
						.assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).openGurillaMail();
				pf.getIamPage(ob).clickReceivedMail("Project Neon");
				pf.getIamPage(ob).checkApplicationName("Project Neon");
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
						.assignCategory("IAM");
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
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-4261",
								"Verify that External Password Reset Page should have a new password field where the user enters their new password.")
						.assignCategory("IAM");
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
						.assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkLoginPage();
				pf.getIamPage(ob).login(email, newPassword);
				logout();
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
						.assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).openGurillaMail();
				pf.getIamPage(ob).checkChangedPasswordMailSubject("Project Neon");
				pf.getIamPage(ob).checkApplicationName("Project Neon");

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
						.assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.navigate().to(System.getProperty("host") + CONFIG.getProperty("appendENWAppUrl"));
				pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin(email, newPassword);
				logoutEnw();
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
						.assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).openGurillaMail();
				pf.getIamPage(ob).checkAlreadyUsedMailSubject("Project Neon");
				pf.getIamPage(ob).checkApplicationName("Project Neon");
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
						.assignCategory("IAM");
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
						.assignCategory("IAM");
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
						.assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.navigate().to(host);
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
						.assignCategory("IAM");
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
						.assignCategory("IAM");
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
			ob.quit();
			// String email = createNewUser("duster", "man");

			/*
			 * logout(); BrowserWaits.waitTime(4);
			 * ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).click(); BrowserWaits.waitTime(4);
			 * waitForElementTobeVisible(ob, By.id(OR.getProperty("email_Address")), 30);
			 * ob.findElement(By.id(OR.getProperty("email_Address"))).sendKeys(email);
			 * ob.findElement(By.xpath(OR.getProperty("verification_email_button"))).click(); BrowserWaits.waitTime(3);
			 * waitForElementTobeVisible(ob, By.xpath(OR.getProperty("confomr_message")), 30); String text =
			 * ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText(); logger.info("Email Address : " +
			 * text); String expected_text = "An email with password reset instructions has been sent to " + email;
			 * logger.info("Expected Email : " + expected_text); String checkEmail1 =
			 * ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText(); logger.info(
			 * "Email Address : " + checkEmail1); String checkEmail = "Please check your email"; if
			 * (text.contains(expected_text) && checkEmail.contains(checkEmail1)) { test.log(LogStatus.PASS,
			 * "Email for password change sent"); } else { test.log(LogStatus.FAIL, "Email for password change not sent"
			 * ); } BrowserWaits.waitTime(3); ob.get("https://www.guerrillamail.com"); BrowserWaits.waitTime(12);
			 * List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list"))); WebElement myE =
			 * email_list.get(0); JavascriptExecutor executor = (JavascriptExecutor) ob;
			 * executor.executeScript("arguments[0].click();", myE); Thread.sleep(2000); String email_subject =
			 * ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText(); logger.info(
			 * "Email Subject Text : " + email_subject); if (email_subject.contains(
			 * "Project Neon password change request")) { test.log(LogStatus.PASS,
			 * "Email for changing password received"); } else { test.log(LogStatus.FAIL,
			 * "Email for changing password not received"); } WebElement reset_link_element = ob
			 * .findElement(By.cssSelector(OnePObjectMap.EMAIL_BODY_PASSWORD_RESET_LINK_CSS.toString())); String
			 * reset_link_url = reset_link_element.getAttribute("href"); ob.get(reset_link_url);
			 * waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);
			 * ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("Neon@1234");
			 * ob.findElement(By.id(OR.getProperty("confirmPassword_textBox"))).sendKeys("Neon@1234");
			 * ob.findElement(By.id(OR.getProperty("update_password"))).click(); String checkEmail2 =
			 * ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText(); logger.info(
			 * "Email Address : " + checkEmail2); waitForElementTobeVisible(ob,
			 * By.xpath(OR.getProperty("confomr_message")), 30); text =
			 * ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText(); logger.info("Expected Text : " +
			 * text); expected_text = "Your password has been updated"; String expectedText =
			 * "Your password has been successfully updated. A confirmation has been sent to your email address."; if
			 * (checkEmail2.contains(expected_text) && text.contains(expectedText)) { test.log(LogStatus.PASS,
			 * "Password changed successfully"); } else { test.log(LogStatus.FAIL, "Password not changed successfully");
			 * } BrowserWaits.waitTime(2); // 4)login with changed password
			 * ob.findElement(By.cssSelector("input[class='button']")).click(); BrowserWaits.waitTime(4);
			 * ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
			 * ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(email);
			 * ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).sendKeys("Neon@1234");
			 * ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click(); Thread.sleep(10000);
			 * pf.getBrowserWaitsInstance(ob)
			 * .waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS); logout(); ob.quit();
			 */
		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
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
