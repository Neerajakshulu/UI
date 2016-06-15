package iam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
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

public class IAM007 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(var,
						"Verify that user is not able to login using FB option for different negative combinations of username/password")
				.assignCategory("IAM");
		// test.log(LogStatus.INFO, "****************************");
		// load the runmodes of the tests
		runmodes = TestUtil.getDataSetRunmodes(iamxls, this.getClass().getSimpleName());
	}

	@Test(dataProvider = "getTestData")
	public void testcaseA7(String email,
			String password) throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "IAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(iamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;
			// TestUtil.reportDataSetResult(iamxls, "Test Cases",
			// TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {

			test.log(LogStatus.INFO, "Runmode for test set data set to no " + (count + 1));
			skip = true;
			// TestUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count+2, "SKIP");
			throw new SkipException("Runmode for test set data set to no " + (count + 1));
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts for data set #" + (count + 1)
				+ "--->");
		test.log(LogStatus.INFO, email + " -- " + password);

		try {

			// selenium code
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			ob.navigate().to(host);
			//
			/*waitForElementTobeVisible(ob, By.xpath(OR.getProperty("FB_login_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("FB_login_button"))).click();
			waitForElementTobeVisible(ob, By.name(OR.getProperty("FB_email_textBox")), 30);
			ob.findElement(By.name(OR.getProperty("FB_email_textBox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("FB_password_textBox"))).sendKeys(password);
			ob.findElement(By.name(OR.getProperty("FB_page_login_button"))).click();*/
			
			BrowserWaits.waitTime(4);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("FB_login_button")), 30);
			ob.findElement(By.cssSelector(OR.getProperty("FB_login_button"))).click();
			waitForElementTobeVisible(ob, By.name(OR.getProperty("FB_email_textBox")), 30);
			ob.findElement(By.name(OR.getProperty("FB_email_textBox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("FB_password_textBox"))).sendKeys(password);
			waitForElementTobeVisible(ob, By.name(OR.getProperty("FB_page_login_button")), 30);
			ob.findElement(By.name(OR.getProperty("FB_page_login_button"))).click();
			BrowserWaits.waitTime(5);

			if (!checkElementPresence_name("FB_page_login_button")) {

				fail = true;// excel
				test.log(LogStatus.FAIL, "Unexpected login happened");// extent report
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_unexpected_login_happened_" + (count + 1))));

			}

			closeBrowser();

		}

		catch (Throwable t) {

			status = 2;// excel-main testcase
			fail = true;// excel-dataset
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends for data set #" + (count + 1)
				+ "--->");
	}

	@AfterMethod
	public void reportDataSetResult() {
		if (skip)
			TestUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count + 2, "SKIP");

		else if (fail) {

			status = 2;
			TestUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count + 2, "FAIL");
		} else
			TestUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count + 2, "PASS");

		skip = false;
		fail = false;

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

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(iamxls, this.getClass().getSimpleName());
	}

}
