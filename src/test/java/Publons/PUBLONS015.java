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

import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class PUBLONS015 extends TestBase {

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
			
			
					
			try {
				test = extent
						.startTest("OPQA-5842",
								"Verify that password change text fields displaying or not when ever click Password link in Account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_XPATH);
				String populatText = ob.findElement(By.xpath(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_XPATH.toString())).getText();
				logger.info("PopulatText--->" + populatText);
				
				Assert.assertEquals(populatText, "Enter current password");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_XPATH);
				String populatText1 = ob.findElement(By.xpath(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_XPATH.toString())).getText();
				logger.info("PopulatText--->" + populatText1);
				Assert.assertEquals(populatText1, "Enter new password");
				
				
				test.log(LogStatus.PASS,
						"Text fields are displaying");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Text fields are not displaying");
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
						.startTest("OPQA-5843",
								"Verify that Forgot password?  link displaying in Account setting page when ever click Password link in Account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_FORGOT_PASSWORD_LINK_CSS);
				waitUntilText("Forgot password?");
				
				test.log(LogStatus.PASS,
						"Forgot password?  link displaying in Account setting page");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Forgot password?  link not displaying in Account setting page");
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
						.startTest("OPQA-5844",
								"Verify that Clear link displaying in Account setting page when ever click Password link in Account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_CLEAR_LINK_CSS);
				waitUntilText("Clear");
				
				test.log(LogStatus.PASS,
						"Clear link displaying in Account setting page");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Clear link not displaying in Account setting page");
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
						.startTest("OPQA-5845",
								"Verify that submit button disable or not when ever enter wrong format password or empty fields")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_DISABLED_BUTTON_CSS);
				test.log(LogStatus.PASS,
						"Submit button disabled");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Submit button not disabled");
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
						.startTest("OPQA-5846",
								"Verify that Please enter current password. error message when ever current password text field empty")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).click();
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).click();
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_EMPTY_FIELD_ERROR_MESSAGE_CSS);
				String actualText=ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_EMPTY_FIELD_ERROR_MESSAGE_CSS.toString())).getText();
				Assert.assertEquals(actualText, "Please enter current password.");
				
				test.log(LogStatus.PASS,
						"Please enter current password. error message displaying");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Please enter current password. error message not displaying");
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
						.startTest("OPQA-5847",
								"Verify Password must have at least one special character from !@#$%^*()~`{}[] in account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).sendKeys("Neon@123");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys("!");
				
				waitUntilText("Forgot password?");
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
						.startTest("OPQA-5848",
								"Verify Password must contain at least one number is ALWAYS enforced in account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
								
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys("1");
				
				waitUntilText("Forgot password?");
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[3]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				test.log(LogStatus.PASS, "Password field allowed one number");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Password field not allowed one number");
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
						.startTest("OPQA-5849",
								"Verify Password must have at least one alphabet character either upper or lower case is ALWAYS enforced in account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
								
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys("a");
				
				waitUntilText("Forgot password?");
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[2]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				test.log(LogStatus.PASS, "Password field allowed one alphabet character");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Password field not allowed one alphabet character");
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
						.startTest("OPQA-5850",
								"Verify that the Password minimum length of 8 characters is ALWAYS enforced in account setting page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
								
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys("ada@12");
				
				waitUntilText("Forgot password?");
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[1]//div[@class='col-xs-1 password-validator__icon fa color-c5-red fa-times']"),
						30);
				test.log(LogStatus.PASS, "Password minimum length of 8 characters message displaying in account setting page.");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Password minimum length of 8 characters message not displaying in account setting page.");
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
						.startTest("OPQA-5851",
								"Verify that error message Incorrect password. Please try again. when enter Incorrect existing STeAM password in old password field for account setting page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).sendKeys("Neon@123456");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys("Neon@1234");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_SUBMIT_BUTTON_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_SUBMIT_BUTTON_CSS.toString())).click();
								
				waitUntilText("Incorrect password. Please try again.");
				waitForElementTobeVisible(ob,
						By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_PASSWORD_VALIDATION_CSS.toString()),
						30);
				test.log(LogStatus.PASS,
						"Incorrect password. Please try again. error message displaying");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Incorrect password. Please try again. error message not displaying");
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
						.startTest("OPQA-5852",
								"Verify that error message New password should not match current password. when enter Old and New password are same in account setting page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).sendKeys("Neon@123");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys("Neon@123");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_SUBMIT_BUTTON_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_SUBMIT_BUTTON_CSS.toString())).click();
				
				waitUntilText("New password should not match current password");
				waitForElementTobeVisible(ob,
						By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_PASSWORD_VALIDATION_CSS.toString()),
						30);
				test.log(LogStatus.PASS,
						"Incorrect password. Please try again. error message displaying");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Incorrect password. Please try again. error message not displaying");
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
						.startTest("OPQA-5855",
								"Verify that Password rules are displaying when New STeAM password does not meet password requirements in account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
								
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys("Neon");
				
				waitForElementTobeVisible(ob,
						By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_PASSWORD_RULES_XPATH.toString()),
						30);
				test.log(LogStatus.PASS,
						"Password rules are displaying");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Password rules are not displaying");
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
						.startTest("OPQA-5856",
								"Verify Password Maximum Length of 95 characters is ALWAYS enforced in account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				String name = "N@1";
				String maxPassword = name + generateRandomName(93);
				logger.info("Last Name : " + maxPassword);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys(maxPassword);
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
				test = extent
						.startTest("OPQA-5858",
								"Verify that text fields are clear when ever click Clear link in Account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).sendKeys("Neon@123");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys("Neon@123");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_CLEAR_LINK_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_CLEAR_LINK_CSS.toString())).click();
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_XPATH);
				String populatText = ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_XPATH.toString())).getText();
				logger.info("PopulatText--->" + populatText);
				
				Assert.assertEquals(populatText, "Enter current password");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_XPATH);
				String populatText1 = ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_XPATH.toString())).getText();
				logger.info("PopulatText--->" + populatText);
				Assert.assertEquals(populatText1, "Enter new password");
				
				
				test.log(LogStatus.PASS,
						"Text cleared in text fields");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Text not cleared in text fields");
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
						.startTest("OPQA-5857",
								"Verify that Forgot password? link working correctly when click Password link in Account setting page and that he is able to login with his new password")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
								
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_FORGOT_PASSWORD_LINK_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_FORGOT_PASSWORD_LINK_CSS.toString())).click();
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_LOGIN_PAGE_FORGOT_PASSWORD_OK_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.NEON_LOGIN_PAGE_FORGOT_PASSWORD_OK_CSS.toString())).click();
				
				test.log(LogStatus.PASS,
						"Forgot password? link working correctly");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Forgot password? link not working correctly");
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
						.startTest("OPQA-5854",
								"Verify that user receive a conformation mail when user changed password in account setting page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).sendKeys("Neon@123");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys(newPassword);
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_SUBMIT_BUTTON_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_SUBMIT_BUTTON_CSS.toString())).click();
				
				ob.close();
				
				pf.getPubPage(ob).navigateMainWindow();
				logout();
				ob.get("https://www.guerrillamail.com");
				waitUntilText("Project Neon password changed");
				List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list")));
				WebElement myE = email_list.get(0);
				JavascriptExecutor executor = (JavascriptExecutor) ob;
				executor.executeScript("arguments[0].click();", myE);
				waitUntilText("Project Neon password changed");
				String email_subject = ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText();
				logger.info("Email Subject Text : " + email_subject);
				if (!StringContains(email_subject, "Project Neon password changed")) {

					test.log(LogStatus.FAIL, "Email for changing password not received");// extent reports
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_password_change_email_not_received")));// screenshot
				}
				test.log(LogStatus.PASS,
						"User receiving a conformation mail when user changed password in account setting page.");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"User not receiving a conformation mail when user changed password in account setting page.");
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
						.startTest("OPQA-5853",
								"Verify that error message New password should not match previous 4 passwords. when enter new password match with previous four passwords.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				
				ob.navigate().to(host);
				
				pf.getIamPage(ob).login(email, "Neon@1234");
				waitUntilText("Newsfeed","Watchlists", "Groups");

				pf.getPubPage(ob).moveToAccountSettingPage();
				pf.getPubPage(ob).windowHandle();
				pf.getPubPage(ob).clickTab("Password");
				
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString())).sendKeys("Neon@1234");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).click();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).clear();
				ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString())).sendKeys(newPassword);
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_SUBMIT_BUTTON_CSS);
				ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_SUBMIT_BUTTON_CSS.toString())).click();
				
				waitUntilText("New password should not match previous 4 passwords.");
				waitForElementTobeVisible(ob,
						By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_PAGE_PASSWORD_VALIDATION_CSS.toString()),
						30);
								
				test.log(LogStatus.PASS,
						"New password should not match previous 4 passwords. error message displaying");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Incorrect password. Please try again. error message not displaying");
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
