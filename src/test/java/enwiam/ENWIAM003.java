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

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class ENWIAM003 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		
//		extent = ExtentManager.getReporter(filePath);
//		rowData = testcase.get(this.getClass().getSimpleName());
//		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");
		
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('G'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verify that PASSWORD field in new TR user registration page")
				.assignCategory("ENWIAM");
				// test.log(LogStatus.INFO, "****************************");

		// load the runmodes of the tests
		runmodes = TestUtil.getDataSetRunmodes(enwiamxls, this.getClass().getSimpleName());
	}

	@Test(dataProvider = "getTestData")
	public void testcaseA12(String password, String strength, String checks, String validity) throws Exception {

//		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
//		boolean master_condition = suiteRunmode && testRunmode;
		
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "ENWIAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;
			// TestUtil.reportDataSetResult(iamxls, "Test Cases",
			// TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()),
			// "SKIP");
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {

			test.log(LogStatus.INFO, "Runmode for test set data set to no " + (count + 1));
			skip = true;
			// TestUtil.reportDataSetResult(iamxls,
			// this.getClass().getSimpleName(), count+2, "SKIP");
			throw new SkipException("Runmode for test set data set to no " + (count + 1));
		}

		try {

			test.log(LogStatus.INFO,
					this.getClass().getSimpleName() + " execution starts for data set #" + (count + 1) + "--->");
			test.log(LogStatus.INFO, password + " -- " + validity);

			String temp = checks.substring(0, 1);
			int tickMarks = Integer.parseInt(temp);
			logger.info("TickMarks : " + tickMarks);
			String email = generateRandomName(5) + "@abc.com";

			// selenium code
			openBrowser();
			//
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

//			ob.navigate().to(CONFIG.getProperty("enwUrl"));
			ob.get(host+CONFIG.getProperty("appendENWAppUrl"));

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("signup_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_link"))).click();
			BrowserWaits.waitTime(3);
			ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
			ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys(password);
			BrowserWaits.waitTime(3);
			List<WebElement> tm_list = ob
					.findElements(By.xpath(OR.getProperty("reg_passwordStrength_tickMark_labels")));
			logger.info("TickMark Size : " + tm_list.size());
			List<WebElement> listOfTags = ob
					.findElements(By.xpath("(//div[@class='col-xs-12 password-validator__container'])[2]/div"));
			logger.info("Total Tags : " + listOfTags.size());

			if (listOfTags.size() == tm_list.size()) {
				if (validity.equalsIgnoreCase("YES")) {
					ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).clear();
					ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).sendKeys(email);
					ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
					ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).sendKeys(password);
					ob.findElement(By.name(OR.getProperty("signup_firstName_textbox"))).clear();
					ob.findElement(By.name(OR.getProperty("signup_firstName_textbox"))).sendKeys("ricky");
					ob.findElement(By.name(OR.getProperty("signup_lastName_textbox"))).clear();
					ob.findElement(By.name(OR.getProperty("signup_lastName_textbox"))).sendKeys("behl");
					ob.findElement(By.xpath(OR.getProperty("signup_button"))).click();
					BrowserWaits.waitTime(4);
				}
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