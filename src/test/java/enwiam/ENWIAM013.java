package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class ENWIAM013 extends TestBase {

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

		// extent = ExtentManager.getReporter(filePath);
		// String var = xlRead2(returnExcelPath('G'),
		// this.getClass().getSimpleName(), 1);
		// test = extent
		// .startTest(
		// var,
		// "Verify that user is able to change his TR password by using FORGOT
		// YOUR PASSWORD link and that he is able to login with his new
		// password")
		// .assignCategory("ENWIAM");

	}

	@Test
	public void testcaseA16() throws Exception {

		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		// boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "ENWIAM");
		// boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls,
		// this.getClass().getSimpleName());
		// boolean master_condition = suiteRunmode && testRunmode;

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

			// 1)Create a new user
			// 2)Login with new user and logout
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();
			// String email=createNewUser(first_name, last_name);

			String email = createENWNewUser(first_name, last_name);
			logger.info("Email Address : " + email);

			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_AGREE_BUTTON_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_AGREE_BUTTON_CSS.toString())).click();

			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()),
					30);
			String continueText = ob
					.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).getText();
			if (continueText.equalsIgnoreCase("Continue")) {
				ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
			}

			logoutEnw();
			BrowserWaits.waitTime(4);

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1934",
								"Verify that Forgot your password? Link is clickable on NEON Landing page and End note landing page")
						.assignCategory("ENWIAM");
				String forgotPassText = ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).getText();
				logger.info("Fogot Password Text : " + forgotPassText);
				Assert.assertEquals(forgotPassText, "Forgot password?");
				ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).click();
				test.log(LogStatus.PASS, "Forgot password? Link is clickable on EndNote landing page");
				BrowserWaits.waitTime(4);

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
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1935",
								"Verify that the system is navigating to Forgot Password page or not, after clicking on Forgot your password? Link")
						.assignCategory("ENWIAM");
				String resertPassPage = ob
						.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_RESET_PASSWORD_PAGE_CSS.toString()))
						.getText();
				Assert.assertEquals(resertPassPage, "Reset your password");
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
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1945",
								"Verify that Upon initiation, the Neon and ENW reset password workflow shall bring the user to the send email verification page where a verification email can be sent to an email address entered by the user.")
						.assignCategory("ENWIAM");
				// String
				// emailVeficationPage=ob.findElement(By.cssSelector("input[class='button']")).getText();
				// Assert.assertEquals(emailVeficationPage, "Send verification
				// email");
				waitForElementTobeVisible(ob, By.id(OR.getProperty("email_Address")), 30);
				ob.findElement(By.id(OR.getProperty("email_Address"))).sendKeys(email);
				ob.findElement(By.xpath(OR.getProperty("verification_email_button"))).click();
				BrowserWaits.waitTime(3);
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("confomr_message")), 30);

				String text = ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText();
				logger.info("Email Address : " + text);

				String expected_text = "An email with password reset instructions has been sent to " + email;
				logger.info("Expected Email : " + expected_text);

				String checkEmail1 = ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText();
				logger.info("Email Address : " + checkEmail1);
				String checkEmail = "Please check your email";
				Assert.assertEquals(text.contains(expected_text), checkEmail.contains(checkEmail1));
				test.log(LogStatus.PASS,
						"ENW reset password workflow bring the user to the send email verification page where a verification email can be sent to an email address entered by the user.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"ENW reset password workflow not bring the user to the send email verification page where a verification email can be sent to an email address entered by the user."
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1946",
								"Verify that the Neon and ENW reset password workflow shall be able to send a verification email to the user")
						.assignCategory("ENWIAM");
				BrowserWaits.waitTime(3);
				// ob.close();
				// ob.switchTo().window(al.get(0));
				// Thread.sleep(2000);
				ob.get("https://www.guerrillamail.com");
				BrowserWaits.waitTime(12);
				List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list")));
				WebElement myE = email_list.get(0);
				JavascriptExecutor executor = (JavascriptExecutor) ob;
				executor.executeScript("arguments[0].click();", myE);
				// email_list.get(0).click();
				Thread.sleep(2000);

				String email_subject = ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText();
				logger.info("Email Subject Text : " + email_subject);
				Assert.assertEquals(email_subject, "EndNote&trade;_password_change_request");
				test.log(LogStatus.PASS, "ENW reset password workflow able to send a verification email to the user");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"ENW reset password workflow not able to send a verification email to the user" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1947",
								"Verify that Upon clicking the link to reset password in the Neon and ENW reset verification email, the user shall be sent to the password reset page to reset the applicable STeAM user")
						.assignCategory("ENWIAM");

				WebElement reset_link_element = ob
						.findElement(By.xpath(OR.getProperty("email_body_password_reset_link")));
				String reset_link_url = reset_link_element.getAttribute("href");
				ob.get(reset_link_url);
				test.log(LogStatus.PASS,
						"After clicking the link to reset password ENW reset verification email, the user sent to the password reset page to reset the applicable STeAM user");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"After clicking the link to reset password ENW reset verification email, the user not sent to the password reset page to reset the applicable STeAM user"
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}
			

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1950",
								"Verify Password must have at least one special character from !@#$%^*()~`{}[]|")
						.assignCategory("ENWIAM");
				waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);

				ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("!");
				String specialChar = ob.findElement(By.cssSelector("span[id='hasChar']")).getCssValue("background");
				Assert.assertEquals(specialChar, "rgb(30, 86, 21)");

				test.log(LogStatus.PASS, "Password field allow one special character from !@#$%^*()~`{}[]|");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password field not allow one special character from !@#$%^*()~`{}[]|" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1951", "Verify Password must contain at least one number is ALWAYS enforced.")
						.assignCategory("ENWIAM");
				waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);

				ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("1");
				String specialChar = ob.findElement(By.cssSelector("span[id='hasNum']")).getCssValue("background");
				Assert.assertEquals(specialChar, "rgb(30, 86, 21)");

				test.log(LogStatus.PASS, "Password field allowed one number");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password field not allowed one number" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1953",
								"Verify Password must have at least one alphabet character either upper or lower case is ALWAYS enforced.")
						.assignCategory("ENWIAM");
				waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);

				ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("P");
				String alphaCahr = ob.findElement(By.cssSelector("span[id='hasChar']")).getCssValue("background");
				Assert.assertEquals(alphaCahr, "rgb(30, 86, 21)");

				ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("p");
				String alphaCahr1 = ob.findElement(By.cssSelector("span[id='hasChar']")).getCssValue("background");
				Assert.assertEquals(alphaCahr1, "rgb(30, 86, 21)");

				test.log(LogStatus.PASS, "Password field allowed one alphabet character");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password field not allowed one alphabet character" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1948",
								"Verify that the Password minimum length of 8 characters is ALWAYS enforced")
						.assignCategory("ENWIAM");
				waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);

				ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("Neon@1234");
				String alphaCahr = ob.findElement(By.cssSelector("span[id='minLength']")).getCssValue("background");
				ob.findElement(By.id(OR.getProperty("confirmPassword_textBox"))).sendKeys("Neon@1234");
				ob.findElement(By.id(OR.getProperty("update_password"))).click();
				Assert.assertEquals(alphaCahr, "rgb(30, 86, 21)");
				test.log(LogStatus.PASS, "Password field allowed 8 or more characters");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password field not  allowed 8 or more characters" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1954",
								"Verify Upon completion of establishing a new password, a user who wants to go to ENW shall be presented a confirmation page with an optional link back to ENW Landing page")
						.assignCategory("ENWIAM");
				BrowserWaits.waitTime(3);
				String checkEmail2 = ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText();
				logger.info("Email Address : " + checkEmail2);

				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("confomr_message")), 30);

				String text = ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText();
				logger.info("Expected Text : " + text);

				String expected_text = "Your password has been updated";
				String expectedText = "Your password has been successfully updated. A confirmation has been sent to your email address.";

				Assert.assertTrue(checkEmail2.contains(expected_text) && text.contains(expectedText));
				BrowserWaits.waitTime(2);
				ob.findElement(By.cssSelector("input[class='button']")).click();
				test.log(LogStatus.PASS,
						"After reset new password,  user go to ENW confirmation page with an optional link back to ENW Landing page");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"After resetting new password,  user not go to ENW confirmation page with an optional link back to ENW Landing page"
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}

			
			
			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1937",
								"Verify that As a Neon or ENW user, I want to be able to reset my STeAM Password from the EndNote Web landing page.")
						.assignCategory("ENWIAM");
				BrowserWaits.waitTime(2);
				ob.findElement(By.cssSelector("input[class='button']")).click();
				BrowserWaits.waitTime(4);
				ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
				ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(email);
				ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).sendKeys("Neon@1234");
				ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
				Thread.sleep(10000);

				if (!checkElementPresence("header_label")) {

					test.log(LogStatus.FAIL, "User unable to login with changed password");// extent
																							// reports
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_user_unable_to_login_with_changed_password")));// screenshot

				}

				test.log(LogStatus.PASS,
						"User successfylly login ENW after resetting password");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"User not successfylly login ENW after resetting password"
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				extent.endTest(test);
			}

			
			
			
			// ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).click();
			//
			// BrowserWaits.waitTime(4);
			// waitForElementTobeVisible(ob,
			// By.id(OR.getProperty("email_Address")), 30);
			// ob.findElement(By.id(OR.getProperty("email_Address"))).sendKeys(email);
			// ob.findElement(By.xpath(OR.getProperty("verification_email_button"))).click();
			//
			// BrowserWaits.waitTime(3);
			// waitForElementTobeVisible(ob,
			// By.xpath(OR.getProperty("confomr_message")), 30);
			//
			// String text =
			// ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText();
			// logger.info("Email Address : "+text);
			//
			// String expected_text = "An email with password reset instructions
			// has been sent to "+email;
			// logger.info("Expected Email : "+expected_text);
			//
			// String checkEmail1 =
			// ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText();
			// logger.info("Email Address : "+checkEmail1);
			// String checkEmail="Please check your email";
			// if (!StringContains(text,
			// expected_text)&&!StringContains(checkEmail, checkEmail1)) {
			//
			// test.log(LogStatus.FAIL, "Email for password change not sent");//
			// extent reports
			// status = 2;// excel
			// test.log(
			// LogStatus.INFO,
			// "Snapshot below: "
			// +
			// test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
			// + "_password_change_email_not_sent")));// screenshot
			//
			// }
			// BrowserWaits.waitTime(3);
			// //ob.close();
			// //ob.switchTo().window(al.get(0));
			// //Thread.sleep(2000);
			// ob.get("https://www.guerrillamail.com");
			// BrowserWaits.waitTime(12);
			// List<WebElement> email_list =
			// ob.findElements(By.xpath(OR.getProperty("email_list")));
			// WebElement myE = email_list.get(0);
			// JavascriptExecutor executor = (JavascriptExecutor) ob;
			// executor.executeScript("arguments[0].click();", myE);
			// // email_list.get(0).click();
			// Thread.sleep(2000);
			//
			// String email_subject =
			// ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText();
			// logger.info("Email Subject Text : "+email_subject);
			// if (!StringContains(email_subject,
			// "EndNote&trade;_password_change_request")) {
			//
			// test.log(LogStatus.FAIL, "Email for changing password not
			// received");// extent reports
			// status = 2;// excel
			// test.log(
			// LogStatus.INFO,
			// "Snapshot below: "
			// +
			// test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
			// + "_password_change_email_not_received")));// screenshot
			//
			// }

			// WebElement reset_link_element =
			// ob.findElement(By.xpath(OR.getProperty("email_body_password_reset_link")));
			// String reset_link_url = reset_link_element.getAttribute("href");
			// ob.get(reset_link_url);
			//
			// waitForElementTobeVisible(ob,
			// By.id(OR.getProperty("newPassword_textBox")), 30);
			//
			// ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("Neon@1234");
			// ob.findElement(By.id(OR.getProperty("confirmPassword_textBox"))).sendKeys("Neon@1234");
			// ob.findElement(By.id(OR.getProperty("update_password"))).click();
			//

			// String checkEmail2 =
			// ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText();
			// logger.info("Email Address : "+checkEmail2);
			//
			// waitForElementTobeVisible(ob,
			// By.xpath(OR.getProperty("confomr_message")), 30);
			//
			// text =
			// ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText();
			// logger.info("Expected Text : "+text);
			//
			// expected_text = "Your password has been updated";
			// String expectedText="Your password has been successfully updated.
			// A confirmation has been sent to your email address.";
			//
			// if (!StringContains(checkEmail2,
			// expected_text)&&!StringContains(text, expectedText)) {
			//
			// test.log(LogStatus.FAIL, "Password not changed successfully");//
			// extent reports
			// status = 2;// excel
			// test.log(
			// LogStatus.INFO,
			// "Snapshot below: "
			// +
			// test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
			// + "_password_not_changed_successfully")));// screenshot
			//
			// }
//			BrowserWaits.waitTime(2);
//			ob.findElement(By.cssSelector("input[class='button']")).click();
//			BrowserWaits.waitTime(4);
//			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
//			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(email);
//			ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).sendKeys("Neon@1234");
//			ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
//			Thread.sleep(10000);
//
//			if (!checkElementPresence("header_label")) {
//
//				test.log(LogStatus.FAIL, "User unable to login with changed password");// extent
//																						// reports
//				status = 2;// excel
//				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
//						this.getClass().getSimpleName() + "_user_unable_to_login_with_changed_password")));// screenshot
//
//			}
			logoutEnw();
			ob.quit();
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
		// extent.endTest(test);

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
