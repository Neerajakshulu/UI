package suiteA;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class IAM020 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verify that CREATE A NEW PROJECT NEON ACCOUNT button is working correctly")
				.assignCategory("IAM");

	}

	@Test
	public void testcaseA20() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "A Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteAxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to LI login page
			ob.navigate().to(host);
			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			//
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("new_TR_User_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("new_TR_User_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("reg_register_button")), 15);

			if (!checkElementPresence("reg_register_button")) {

				test.log(LogStatus.FAIL, "CREATE A NEW PROJECT NEON ACCOUNT button not working correctly");// extent
																											// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_CREATE_A_NEW_PROJECT_NEON_ACCOUNT_button_not_working")));// screenshot

			}

			closeBrowser();

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();

		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
