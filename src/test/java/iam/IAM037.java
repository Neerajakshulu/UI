package iam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class IAM037 extends TestBase {

	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IAM");

	}

	@Test
	public void testcaseENW000012() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			String password = "Neon@123";
			String first_name = "duster";
			String last_name = "man";

			String statuCode = deleteUserAccounts(LOGIN.getProperty("NEONSOCIALUSERNAME"));
			logger.info("User Status : " + statuCode);
			if (statuCode.equalsIgnoreCase("200")) {
				logger.info("User Deleted Successfully");
			} else if (statuCode.equalsIgnoreCase("400")) {
				logger.info("User Already Deleted Successfully");
			} else {
				test.log(LogStatus.FAIL, "Bad request Error..Server down");
			}

			openBrowser();
			clearCookies();
			maximizeWindow();
			String email = createNewUser(first_name, last_name);
			logger.info("Email Address : " + email);
			logout();
			BrowserWaits.waitTime(3);

			ob.navigate().to(host);

			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("LI_login_button")), 30);
			ob.findElement(By.cssSelector(OR.getProperty("LI_login_button"))).click();
			waitForElementTobeVisible(ob, By.name(OR.getProperty("LI_email_textBox")), 30);
			ob.findElement(By.name(OR.getProperty("LI_email_textBox")))
					.sendKeys(LOGIN.getProperty("NEONSOCIALUSERNAME"));
			ob.findElement(By.name(OR.getProperty("LI_password_textBox")))
					.sendKeys(LOGIN.getProperty("NEONFSOCIALSERNAME"));
			ob.findElement(By.name(OR.getProperty("LI_allowAccess_button"))).click();
			BrowserWaits.waitTime(4);
			ob.findElement(By.xpath(OR.getProperty("signup_conformatin_button"))).click();
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OR.getProperty("signup_done_button"))).click();
			BrowserWaits.waitTime(3);

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 30);
			ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("account_link"))).click();
			BrowserWaits.waitTime(3);
			// logout();
			BrowserWaits.waitTime(3);
			String actualEmail = ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_PAGE_LINKEDIN_MAIL_XPATH.toString()))
					.getText();
			System.out.println(actualEmail);

			try {
				Assert.assertEquals(LOGIN.getProperty("NEONSOCIALUSERNAME"), actualEmail);
				test.log(LogStatus.PASS, " Email id getting displayed in Account Setting page is correct");
			}

			catch (Throwable t) {

				test.log(LogStatus.FAIL, "Email id getting displayed in Account Setting page is incorrect");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

			}

			String linkText = ob.findElement(By.xpath(OnePObjectMap.LINK_ACCOUNT_BUTTON_ACCOUNT_PAGE_XPATH.toString()))
					.getText();
			logger.info("" + linkText);
			try {
				Assert.assertEquals(linkText, "Link accounts");
				test.log(LogStatus.PASS, "Link button is displayed in Account setting page.");
			}

			catch (Throwable t) {

				test.log(LogStatus.FAIL, "Link button is not displayed in Account setting page.");// extent
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

			}

			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.LINK_ACCOUNT_BUTTON_ACCOUNT_PAGE_XPATH.toString()),
					30);
			ob.findElement(By.xpath(OnePObjectMap.LINK_ACCOUNT_BUTTON_ACCOUNT_PAGE_XPATH.toString())).click();
			BrowserWaits.waitTime(2);
			ob.findElement(By.xpath(OnePObjectMap.ENW_LINK_ACCOUNTS_EMAIL_XPATH.toString())).sendKeys(email);
			ob.findElement(By.xpath(OnePObjectMap.ENW_LINK_ACCOUNTS_PASSWORD_XPATH.toString())).sendKeys(password);
			ob.findElement(By.xpath(OnePObjectMap.ENW_LINK_ACCOUNTS_DONE_BUTTON_XPATH.toString())).click();

			BrowserWaits.waitTime(3);
			String actualEmail1 = ob.findElement(By.xpath(OnePObjectMap.LINKED_STEAM_ACCOUNT_XPATH.toString()))
					.getText();

			logger.info("linked steam account : " + actualEmail1);
			try {
				Assert.assertEquals(email, actualEmail1);
				test.log(LogStatus.PASS, " Email id getting displayed in Account Setting page is correct");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Email id getting displayed in Account Setting page is incorrect");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

			}
			BrowserWaits.waitTime(5);
			logout();
			BrowserWaits.waitTime(4);
			ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).click();
			BrowserWaits.waitTime(4);
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
			if (!StringContains(text, expected_text) && !StringContains(checkEmail, checkEmail1)) {

				test.log(LogStatus.FAIL, "Email for password change not sent");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_password_change_email_not_sent")));// screenshot

			}

			BrowserWaits.waitTime(3);
			ob.get("https://www.guerrillamail.com");
			BrowserWaits.waitTime(12);
			List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list")));
			WebElement myE = email_list.get(0);
			JavascriptExecutor executor = (JavascriptExecutor) ob;
			executor.executeScript("arguments[0].click();", myE);
			Thread.sleep(2000);

			String email_subject = ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText();
			logger.info("Email Subject Text : " + email_subject);
			if (!StringContains(email_subject, "Project Neon password change request")) {

				test.log(LogStatus.FAIL, "Email for changing password not received");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_password_change_email_not_received")));// screenshot

			}

			WebElement reset_link_element = ob.findElement(By.xpath(OR.getProperty("email_body_password_reset_link")));
			String reset_link_url = reset_link_element.getAttribute("href");
			ob.get(reset_link_url);
			waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);

			ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("Neon@1234");
			ob.findElement(By.id(OR.getProperty("confirmPassword_textBox"))).sendKeys("Neon@1234");
			ob.findElement(By.id(OR.getProperty("update_password"))).click();

			String checkEmail2 = ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText();
			logger.info("Email Address : " + checkEmail2);

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("confomr_message")), 30);

			text = ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText();
			logger.info("Expected Text : " + text);

			expected_text = "Your password has been updated";
			String expectedText = "Your password has been successfully updated. A confirmation has been sent to your email address.";

			if (!StringContains(checkEmail2, expected_text) && !StringContains(text, expectedText)) {

				test.log(LogStatus.FAIL, "Password not changed successfully");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_password_not_changed_successfully")));// screenshot

			}
			BrowserWaits.waitTime(2);
			// 4)login with changed password
			ob.findElement(By.cssSelector("input[class='button']")).click();
			BrowserWaits.waitTime(4);
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).sendKeys("Neon@1234");
			ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
			Thread.sleep(10000);
			if (!checkElementPresence("header_label")) {

				test.log(LogStatus.FAIL, "User unable to login with changed password");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_unable_to_login_with_changed_password")));// screenshot

			}

			ob.close();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			ErrorUtil.addVerificationFailure(t);
			closeBrowser();

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}
}
