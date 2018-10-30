package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class PUBLONS018 extends TestBase {

	static int status = 1;
	static int count = -1;
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
		test = extent.startTest(tests[0], tests_dec[0]).assignCategory("PUBLONS");
		test.log(LogStatus.INFO, tests[0]);
	}

	@Test
	public void testcaseA1() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		String newPassword = "Neon@1234";

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
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("PUBLONS");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		
		openBrowser();
		maximizeWindow();
		clearCookies();
		pf.getIamPage(ob).openGurillaMail();
		String email = pf.getIamPage(ob).getEmail();
		pf.getIamPage(ob).openCCURL(
				"http://steam-stablea.dev-shared.com:8080/steam-admin-app/");
		pf.getIamPage(ob).loginCustomerCare("mahesh.morsu@thomsonreuters.com",
				"Neon@123");
		pf.getIamPage(ob).openMenuPanel();
		pf.getIamPage(ob).clickUserManagement();
		pf.getIamPage(ob).clickCreateUser();
		pf.getIamPage(ob).closeMenuPanel();
		pf.getIamPage(ob).openMainPanel();
		pf.getIamPage(ob).registerNewUser(email);
		pf.getIamPage(ob).closeMainPanel();
		pf.getIamPage(ob).openHeaderPanel();
		pf.getIamPage(ob).logoutCustomerCare();
		pf.getIamPage(ob).closeHeaderPanel();
		pf.getIamPage(ob).checkCCLoginPage();

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution starts--->");
		try {
			ob.navigate().to(host);
			pf.getIamPage(ob).login(email, "Neon@123");
			waitForElementTobeVisible(ob,
					By.xpath(OR.getProperty("signup_done_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_done_button")))
					.click();
			waitUntilText("learn about you", "Join");
			waitForElementTobeVisible(ob,
					By.xpath(OR.getProperty("signup_join_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_join_button")))
					.click();
			waitUntilText("Newsfeed","Watchlists", "Groups");

			pf.getPubPage(ob).moveToAccountSettingPage();
			pf.getPubPage(ob).windowHandle();
			pf.getPubPage(ob).clickTab("Password");
			
			
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_FORGOT_PASSWORD_LINK_CSS);
			ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_FORGOT_PASSWORD_LINK_CSS.toString())).click();
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_LOGIN_PAGE_FORGOT_PASSWORD_OK_CSS);
			ob.findElement(By.cssSelector(OnePObjectMap.NEON_LOGIN_PAGE_FORGOT_PASSWORD_OK_CSS.toString())).click();
			
			
			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-5823",
								"Verify that the platform password reset service should send a platform forget password email with branding that corresponds with the originating application as per wireframe")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).openGurillaMail();
				pf.getBrowserWaitsInstance(ob).waitUntilText("Project Neon password reset");
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
						.startTest("OPQA-5824",
								"Verify that When the password reset token in the email is valid, upon clicking the password reset link in the the platform forget password email, the user shall be taken to the External Password Reset Page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).clickResetYourPasswordLink();
				pf.getBrowserWaitsInstance(ob).waitUntilText("Reset your Project Neon password");
				pf.getIamPage(ob).checkExternalPasswordPageText("Reset your Project Neon password", "Enter a new password below");
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
						.startTest("OPQA-5825",
								"Verify that publons should be moved within the white area and should be above 'Reset your Project Neon password' text and center aligned")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).checkForgotPasswordPageCALogo();
				pf.getIamPage(ob).checkAppName("Reset your Project Neon password");
				test.log(LogStatus.PASS, "Company name displayed successfully in forgot password page");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Company name not displayed in forgot password page" + t);// extent
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
						.startTest("OPQA-5826",
								"Verify Password must have at least one special character from !@#$%^*()~`{}[]| in reset password page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox("!");
				pf.getBrowserWaitsInstance(ob).waitUntilText("Reset your Project Neon password");
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[5]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				pf.getIamPage(ob).checkTextBox("!@#$%^*()~`{}[]|");
				pf.getBrowserWaitsInstance(ob).waitUntilText("Reset your Project Neon password");
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
						.startTest("OPQA-5827",
								"Verify  Password must contain at least one number is ALWAYS enforced in password reset page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).checkTextBox("1");
				pf.getBrowserWaitsInstance(ob).waitUntilText("Reset your Project Neon password");
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
						.startTest("OPQA-5828",
								"Verify Password must have at least one alphabet character either upper or lower case is ALWAYS enforced in reset password page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox("a");
				pf.getBrowserWaitsInstance(ob).waitUntilText("Reset your Project Neon password");
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
						.startTest("OPQA-5829",
								"Verify Password Maximum Length of 95 characters is ALWAYS enforced in reset password page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				String name = "N@1";
				String maxPassword = name + generateRandomName(93);
				logger.info("Last Name : " + maxPassword);
				pf.getIamPage(ob).checkTextBox(maxPassword);
				pf.getBrowserWaitsInstance(ob).waitUntilText("Reset your Project Neon password");
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
						.startTest("OPQA-5830",
								"Verify that the Password minimum length of 8 characters is ALWAYS enforced in reset password page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox("Neon@");
				
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[1]//div[@class='col-xs-1 password-validator__icon fa color-c5-red fa-times']"),
						30);
				test.log(LogStatus.PASS, "Password minimum length of 8 characters is ALWAYS enforced error message displayed in reset password page.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password minimum length of 8 characters is ALWAYS enforced error message not displayed in reset password page." + t);// extent
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
						.startTest("OPQA-5831",
								"Verify that when reset Password Token already used user should be taken to sign in screen")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox(newPassword);
				pf.getIamPage(ob).clickResetButton();
				pf.getIamPage(ob).checkLoginPage();
				pf.getIamPage(ob).login(email, newPassword);
				pf.getBrowserWaitsInstance(ob).waitUntilText("Newsfeed","Watchlists","Groups");
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
						.startTest("OPQA-5832",
								"Verify that upon successful submission of a password change, The user should receive a password change confirmation email to the user's primary email address with branding that corresponds with the application that the user completed the password change")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).openGurillaMail();
				pf.getBrowserWaitsInstance(ob).waitUntilText("Project Neon password changed");
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
						.startTest("OPQA-5833",
								"Verify that the password change confirmation email should reference the fact that credentials are shared across all products.")
						.assignCategory("PUBLONS");
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
						.startTest("OPQA-5834",
								"Verify that when the password reset token in the email is expired or already used, upon clicking the password reset link in the the platform forget password email, the user should be taken to the External Invalid Password Reset Token Page.")
						.assignCategory("PUBLONS");
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
						.startTest("OPQA-5835",
								"Verify that the email address on the External Invalid Password Reset Token Page should be pre-populated with the email address that matches the email that the forgot password email was sent.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				waitUntilText("Password reset link has expired");
				pf.getIamPage(ob).checkInvalidPasswordResetPage();
				test.log(LogStatus.PASS, "Invalid password reset page is opend successfylly");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Invalid password reset page is opend successfylly" + t);// extent
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
						.startTest("OPQA-5836",
								"Verify that user who clicks the submit button on the the External Invalid Password Reset Token page, should be taken to the target application sign in page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

				pf.getIamPage(ob).checkPrepopulatedText(email);
				pf.getIamPage(ob).clickResendEmailButton();
				test.log(LogStatus.PASS, "User Navigate to login page");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User Navigate to login page" + t);// extent
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
						.startTest("OPQA-5839",
								"Verify that error message New password should not match current password. when enter Old and New password are same in reset password page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).openGurillaMail();
				BrowserWaits.waitTime(22);
				pf.getIamPage(ob).clickReceivedMail("Project Neon");
				pf.getIamPage(ob).checkApplicationName("Project Neon");
				pf.getIamPage(ob).clickResetYourPasswordLink();
				pf.getIamPage(ob).checkExternalPasswordPageText("Reset your password", "Enter a new password below");
				pf.getIamPage(ob).checkTextBox(newPassword);
				pf.getIamPage(ob).clickResetButton();
				waitUntilText("New password should not match current password");
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
						.startTest("OPQA-5840",
								"Verify that error message New password should not match previous 4 passwords. when enter new password match with previous four passwords in reset password page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getIamPage(ob).checkTextBox("Neon@123");
				pf.getIamPage(ob).clickResetButton();
				waitUntilText("New password should not match previous 4 passwords");
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
			
			closeBrowser();

		} catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			t.printStackTrace();
			// next 3 lines to print whole testng error in report
			extent = ExtentManager.getReporter(filePath);
			String var = rowData.getTestcaseId();
			String dec = rowData.getTestcaseDescription();
			String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
			String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
			logger.info(rowData.getTestcaseId());
			for (int i = 0; i < tests.length; i++) {
				logger.info(tests_dec[i]);
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("PUBLONS");
				test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
						+ " User Not created, hence skiping this test case");
				extent.endTest(test);
			}
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution ends--->");
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
