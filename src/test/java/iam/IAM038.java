package iam;

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

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class IAM038 extends TestBase {

	static int status = 1;
	String document_title = null;
	String document_url = null;

	PageFactory pf = new PageFactory();
	String postString = null;
	public int screen = 0;
	String[] tests;
	String[] tests_dec;

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
	public void testcaseF20() throws Exception {
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
		try {
			openBrowser();
			maximizeWindow();
			clearCookies();

			String password = "Neon@1234";
			String first_name = "duster";
			String last_name = "man";

			String email = createNewUser(first_name, last_name);
			logger.info("Email Address : " + email);
			// ob.navigate().to(host);
			// login();

//			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 30);
//			ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
//			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_link")), 30);
//			ob.findElement(By.xpath(OR.getProperty("account_link"))).click();
			
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS);
			jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS.toString())));
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.ACCOUNT_LINK_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.ACCOUNT_LINK_CSS.toString())).click();

			try {
				test = extent.startTest(tests[0], tests_dec[0]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				waitForElementTobeVisible(ob,
						By.xpath(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_CANCEL_BUTTON_XPATH.toString()), 30);
				ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_CANCEL_BUTTON_XPATH.toString()))
						.click();
				String str = ob.findElement(By.xpath(OR.getProperty("change_password_link"))).getText();
				Assert.assertEquals(str, "Change password");
				test.log(LogStatus.PASS, "Cancle button successfully clicked in account page.");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Required fields are enter properly");
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
				test = extent.startTest(tests[1], tests_dec[1]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("Neon@123");
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("!");
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[11]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
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
				test = extent.startTest(tests[2], tests_dec[2]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				// jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				// ob.findElement(By.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString())).sendKeys("Neon@123");
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("1");
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[9]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				test.log(LogStatus.PASS, "Password field not allowed one number");

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
				test = extent.startTest(tests[3], tests_dec[3]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				// jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				// ob.findElement(By.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString())).sendKeys("Neon@123");
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("a");
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[8]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
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
				test = extent.startTest(tests[5], tests_dec[5]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				// jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.clear();
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.sendKeys(password);
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.clear();
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys(password);
				BrowserWaits.waitTime(2);
				ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_SUBMIT_BUTTON_XPATH.toString()))
						.click();
				BrowserWaits.waitTime(2);
				String str = ob
						.findElement(
								By.cssSelector("div[class='account-option-item__change-password-container'] div p"))
						.getText();
				Assert.assertEquals(str, "Incorrect password. Please try again.");
				test.log(LogStatus.PASS, "Valid error massage displayed");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Valid error message not displayed");
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
				test = extent.startTest(tests[6], tests_dec[6]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				// jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.clear();
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("Neon@123");
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.clear();
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("Neon@123");
				BrowserWaits.waitTime(2);
				ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_SUBMIT_BUTTON_XPATH.toString()))
						.click();
				BrowserWaits.waitTime(2);
				String str = ob
						.findElement(
								By.cssSelector("div[class='account-option-item__change-password-container'] div p"))
						.getText();
				Assert.assertEquals(str, "New password should not match current password");
				test.log(LogStatus.PASS, "Valid error massage displayed");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Valid error message not displayed");
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
				test = extent.startTest(tests[4], tests_dec[4]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				// jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				// ob.findElement(By.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString())).sendKeys("Neon@123");
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.clear();
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("Neon@123");
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.clear();
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys(password);
				BrowserWaits.waitTime(2);
				ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_SUBMIT_BUTTON_XPATH.toString()))
						.click();
				BrowserWaits.waitTime(2);
				String str = ob.findElement(By.xpath(OR.getProperty("change_password_link"))).getText();
				Assert.assertEquals(str, "Change password");
				test.log(LogStatus.PASS, "New Password field allowed min 8 characters and changed password.");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "New Password field not allowed min 8 characters and changed password.");
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			logout();

			try {
				test = extent.startTest(tests[8], tests_dec[8]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.get("https://www.guerrillamail.com");
				BrowserWaits.waitTime(12);
				List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list")));
				WebElement myE = email_list.get(0);
				JavascriptExecutor executor = (JavascriptExecutor) ob;
				executor.executeScript("arguments[0].click();", myE);
				Thread.sleep(2000);

				String email_subject = ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText();
				logger.info("Email Subject Text : " + email_subject);
				if (!StringContains(email_subject, "Project Neon password changed")) {

					test.log(LogStatus.FAIL, "Email for changing password not received");// extent reports
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_password_change_email_not_received")));// screenshot
				}
				test.log(LogStatus.PASS,
						"User receive a conformation mail when user changed password in account setting page.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"User not receive a conformation mail when user changed password in account setting page.");
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}

			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.name("loginEmail"), 180);
			BrowserWaits.waitTime(2);
			ob.findElement(By.name("loginEmail")).sendKeys(email);
			ob.findElement(By.name("loginPassword")).sendKeys(password);
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
//			BrowserWaits.waitTime(3);
//			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 30);
//			ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
//			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_link")), 30);
//			ob.findElement(By.xpath(OR.getProperty("account_link"))).click();
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS);
			jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS.toString())));
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.ACCOUNT_LINK_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.ACCOUNT_LINK_CSS.toString())).click();
			BrowserWaits.waitTime(2);

			try {
				test = extent.startTest(tests[7], tests_dec[7]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.clear();
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString()))
						.sendKeys(password);
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.clear();
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("Neon@123");
				BrowserWaits.waitTime(2);
				ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_SUBMIT_BUTTON_XPATH.toString()))
						.click();
				BrowserWaits.waitTime(2);
				String str = ob
						.findElement(
								By.cssSelector("div[class='account-option-item__change-password-container'] div p"))
						.getText();
				Assert.assertEquals(str, "New password should not match previous 4 passwords");
				test.log(LogStatus.PASS, "Valid error massage displayed");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Valid error message not displayed");
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
				test = extent.startTest(tests[9], tests_dec[9]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				// jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				// ob.findElement(By.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString())).sendKeys("Neon@123");
				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys("1");
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob, By.xpath("(//div[@class='popover-content'])[2]"), 30);
				test.log(LogStatus.PASS,
						"Password rules are displaying when New STeAM password does not meet password requirements in account setting page");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Password rules are not displaying when New STeAM password does not meet password requirements in account setting page");
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
				test = extent.startTest(tests[10], tests_dec[10]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
				// jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
				// ob.findElement(By.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_OLD_PASSWORD_FIELD_CSS.toString())).sendKeys("Neon@123");

				String name = "N@1";
				String maxPassword = name + generateRandomName(93);
				logger.info("Last Name : " + maxPassword);

				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.clear();

				ob.findElement(By
						.cssSelector(OnePObjectMap.ACCOUNT_PAGE_CHANGE_PASSWORD_LINK_NEW_PASSWORD_FIELD_CSS.toString()))
						.sendKeys(maxPassword);
				BrowserWaits.waitTime(2);
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[12]//div[@class='col-xs-1 password-validator__icon fa color-c5-red fa-times']"),
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
				test = extent.startTest(tests[11], tests_dec[11]).assignCategory("IAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.findElement(By.cssSelector("div[class='account-option-item__forgot-password'] a")).click();
				BrowserWaits.waitTime(6);
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
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}

			ob.navigate().back();
			BrowserWaits.waitTime(3);
			logout();
		} catch (Throwable t) {
			if (test == null) {
				extent = ExtentManager.getReporter(filePath);
				String var = rowData.getTestcaseId();
				String dec = rowData.getTestcaseDescription();
				String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
				String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
				for (int i = 0; i < tests.length; i++) {
					test = extent.startTest(tests[i], tests_dec[i]).assignCategory("IAM");
					test.log(LogStatus.FAIL, "FAIL - " + t.getMessage());
					extent.endTest(test);
				}
			} else {
				test.log(LogStatus.FAIL, "Something unexpected happened" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			}
		} finally {
			closeBrowser();
		}
	}

	@AfterTest
	public void reportTestResult() {
		// extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "FAIL"); else TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
