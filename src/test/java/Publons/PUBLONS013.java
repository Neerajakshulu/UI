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

public class PUBLONS013 extends TestBase{

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
		test = extent.startTest(tests[0], tests_dec[0]).assignCategory("PUBLONS");
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
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("PUBLONS");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
			pf.getPubPage(ob).clickRegisterLink();
			waitUntilText("By registering");

			try {
				test = extent
						.startTest("OPQA-5771",
								"Verify Password must have at least one special character from !@#$%^*()~`{}[] in Registration  page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).click();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys("!");
				waitUntilText("Password rules");
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[11]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
						30);
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys("!@#$%^*()~`{}[]|");
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
				test = extent
						.startTest("OPQA-5772",
								"Verify Password must contain at least one number is ALWAYS enforced in Registration  page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");

//				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).click();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys("1");
				waitUntilText("Password rules");
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[9]//div[@class='col-xs-1 password-validator__icon fa text-success fa-check']"),
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
						.startTest("OPQA-5773",
								"Verify Password must have at least one alphabet character either upper or lower case is ALWAYS enforced in Registration  page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
//				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).click();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys("a");
				waitUntilText("Password rules");
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
				test = extent
						.startTest("OPQA-5774",
								"Verify that the Password minimum length of 8 characters is ALWAYS enforced in Registration  page.")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys("abc@12");
				waitUntilText("Password rules");
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[7]//div[@class='col-xs-1 password-validator__icon fa color-c5-red fa-times']"),
						30);
				test.log(LogStatus.PASS, "Password minimum length of 8 characters message displaying in password rules");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password minimum length of 8 characters not displaying in password rules");
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
						.startTest("OPQA-6000",
								"Verify that Should not have leading and trailing spaces error message at the time of entering password")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys(" ");
				waitUntilText("Password rules");
				waitForElementTobeVisible(ob,
						By.xpath(
								"(//div[@class='row password-validator__item ng-scope'])[10]//div[@class='col-xs-1 password-validator__icon fa color-c5-red fa-times']"),
						30);
				test.log(LogStatus.PASS, "Trailing spaces not allsowed");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Trailing spaces allsowed");
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
						.startTest("OPQA-5775",
								"Verify Password Maximum Length of 95 characters is ALWAYS enforced in Registration  page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				String name = "N@1";
				String maxPassword = name + generateRandomName(92);
				logger.info("Last Name : " + maxPassword);
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys(maxPassword);
				waitUntilText("Already a member?");
				test.log(LogStatus.PASS, "Password rules not displayed when ever enter below 95 characters");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Password rules displayed when ever enter below 95 characters");
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
						.startTest("OPQA-5776",
								"Verify that error message Password is too long whenever enter more than 95 characters")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				String name = "N@1";
				String maxPassword = name + generateRandomName(93);
				logger.info("Last Name : " + maxPassword);
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys(maxPassword);
				waitUntilText("Password rules");
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
				test = extent
						.startTest("OPQA-5777",
								"Verify that View password rules on the right error message at the time of entering password")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
				ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys("A");
				waitUntilText("Password rules");
				waitForElementTobeVisible(ob,
						By.xpath("//span[@class='password-instruction__container']"),
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
			

			ob.quit();
		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
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

	}


	@AfterTest
	public void reportTestResult() {

		//extent.endTest(test);

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
