package Profile;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Profile8 extends TestBase {

	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent Reports
	 * @throws Exception, When Something unexpected
	 */
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('D'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(
						var,
						"Verify that below Project Neon Header and Footer links working as expected \n 1.Cookie Policy \n 2.Privacy Statement \n 3.Terms of Use \n 4.Help \n 5.Account ")
				.assignCategory("Profile");
	}

	/**
	 * Method for wait TR Login Screen
	 * 
	 * @throws Exception, When TR Login screen not displayed
	 */
	@Test
	@Parameters({"username", "password"})
	public void testLoginTRAccount(String username,
			String password) throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Profile");
		boolean testRunmode = TestUtil.isTestCaseRunnable(profilexls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->"+this.getClass().getSimpleName()+"-->"+master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts for data set ");

		try {
			openBrowser();
			clearCookies();
			maximizeWindow();

			ob.navigate().to(System.getProperty("host"));
			pf.getLoginTRInstance(ob).waitForTRHomePage();
			pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
			pf.getLoginTRInstance(ob).clickLogin();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Login_not_done");// extent reports
			// print stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_Login is not done")));
			closeBrowser();
		}
	}

	/**
	 * Method for Validate Application Header and Footer links Header Link - Help, Account Footer Links - Cookie
	 * Policy,Privacy Statement,Terms of Use
	 * 
	 * @throws Exception, When validation not done and Element Not found
	 */
	@Test(dependsOnMethods = "testLoginTRAccount")
	@Parameters("appHeadFooterLinks")
	public void validateAppHeaderFooterLinks(String appHeadFooterLinks) throws Exception {
		try {
			test.log(LogStatus.INFO, "Help,Account and Footer Link Validation");
			pf.getHFPageInstance(ob).validateLinks(appHeadFooterLinks);
			pf.getHFPageInstance(ob).helpLinkValidation();
			pf.getHFPageInstance(ob).accountLinkValidation();
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "UnExpected Error");
			// print stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status = 2;
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_HeaderFooters_links_are_not_working")));
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
		}
	}
	
	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL or SKIP
	 */
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
		/*
		 * if (status == 1) TestUtil.reportDataSetResult(profilexls, "Test Cases", TestUtil.getRowNum(profilexls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(profilexls,
		 * "Test Cases", TestUtil.getRowNum(profilexls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases", TestUtil.getRowNum(profilexls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
