package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class ENWIAM016 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('G'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");
		runmodes = TestUtil.getDataSetRunmodes(enwiamxls, this.getClass().getSimpleName());
		
//		extent = ExtentManager.getReporter(filePath);
//		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
//		test = extent.startTest(var, "Verify that to validate PASSWORD field in new Neon user registration page with maximum length.").assignCategory("IAM");
//		// test.log(LogStatus.INFO, "****************************");
//		// load the runmodes of the tests
//		runmodes = TestUtil.getDataSetRunmodes(iamxls, this.getClass().getSimpleName());
	}

	@Test(dataProvider = "getTestData")
	public void testcaseA6(String charLength, String validity) throws Exception {
		
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

//		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "IAM");
//		boolean testRunmode = TestUtil.isTestCaseRunnable(iamxls, this.getClass().getSimpleName());
//		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {

			test.log(LogStatus.INFO, "Runmode for test set data set to no " + (count + 1));
			skip = true;
			throw new SkipException("Runmode for test set data set to no " + (count + 1));
		}

		try {

			String characterLength = charLength.substring(0, 2);
			Double d=new Double(Double.parseDouble(characterLength));
			int i=d.intValue();
			test.log(LogStatus.INFO,
					this.getClass().getSimpleName() + " execution starts for data set #" + (count + 1) + "--->");
			test.log(LogStatus.INFO, characterLength + " -- " + validity);

			logger.info("Character Length : " + characterLength);
			String name="N@1";
			String password = name+generateRandomName(i);
			logger.info("Last Name : " + password);
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				logger.info("maximize() command not supported in Selendroid");
			}
			clearCookies();

			ob.get(host + CONFIG.getProperty("appendENWAppUrl"));
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("signup_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_link"))).click();
			ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
			ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys(password);
			ob.findElement(By.name(OR.getProperty("signup_firstName_textbox"))).click();
			BrowserWaits.waitTime(3);

			String passLength = null;

			if (validity.equalsIgnoreCase("YES")) {
				passLength = ob.findElement(By.xpath("(//h6[@class='col-xs-11 password-validator__text'])[14]"))
						.getText();
				if (passLength.contains("Password is too long")) {
					test.log(LogStatus.FAIL, "Error message not getting displayed");// extent
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_error_message_not_getting_displayed_" + (count + 1))));
					closeBrowser();
				}

			}

			else {
			
				passLength = ob.findElement(By.xpath("(//h6[@class='col-xs-11 password-validator__text'])[14]"))
						.getText();
				logger.info("PassWord : "+passLength);
				if(!passLength.contains("Password is too long")){
					test.log(LogStatus.FAIL, "Error message not getting displayed");// extent
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_error_message_not_getting_displayed_" + (count + 1))));
					closeBrowser();
				}
				ob.findElement(By.xpath(
						"(//div[@class='col-xs-12 password-validator__container'])[2]/div//div[@class='col-xs-1 password-validator__icon fa color-c5-red fa-times']"));
				BrowserWaits.waitTime(3);
				
			}

			closeBrowser();

		}

		catch (Throwable t) {

			status = 2;// excel-main testcase
			fail = true;// excel-dataset
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO,
				this.getClass().getSimpleName() + " execution ends for data set #" + (count + 1) + "--->");
	}

	@AfterMethod
	public void reportDataSetResult() {
		if (skip)
			TestUtil.reportDataSetResult(enwiamxls, this.getClass().getSimpleName(), count + 2, "SKIP");

		else if (fail) {

			status = 2;
			TestUtil.reportDataSetResult(enwiamxls, this.getClass().getSimpleName(), count + 2, "FAIL");
		} else
			TestUtil.reportDataSetResult(enwiamxls, this.getClass().getSimpleName(), count + 2, "PASS");

		skip = false;
		fail = false;

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

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(enwiamxls, this.getClass().getSimpleName());
	}

}
