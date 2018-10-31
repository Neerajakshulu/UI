package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class PUBLONS029 extends TestBase{
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;
	static String followBefore = null;
	static String followAfter = null;

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent Reports
	 * 
	 * @throws Exception , When Something unexpected
	 */

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("PUBLONS");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception , When TR Login is not done
	 */
	@Test
	public void testcaseEvicteduser() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->" + this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
			try{
				pf.getPubPage(ob).loginWithGmailCredentialsWithOutLinking(LOGIN.getProperty("PUBLONSEVICTEDUSER"),LOGIN.getProperty("PUBLONSBLOCKEDPASS"));
				test.log(LogStatus.PASS,"User trying to login using evicted account.");
				pf.getDraSSOPageInstance(ob).checkEvictedUserErrorMessage();
				test.log(LogStatus.PASS,"User can't login using Gmail account when matching STeAM account is in evicted state and verified error message.");
			}
			catch(Throwable t){
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Error message is not macthing when matching STeAM account is in evicted state and trying to login using Gmail");}
				try{
					ob.navigate().to(host);
					pf.getPubPage(ob).loginWithFBCredentialsWithOutLinking(LOGIN.getProperty("PUBLONSEVICTEDUSER"),LOGIN.getProperty("PUBLONSBLOCKEDPASS"));
					test.log(LogStatus.PASS,"User trying to login using evicted account.");
					pf.getDraSSOPageInstance(ob).checkEvictedUserErrorMessage();
					test.log(LogStatus.PASS,"User can't login using Facebook account when matching STeAM account is in evicted state and verified error message.");
				}
				catch(Throwable t){
					t.printStackTrace();
					test.log(LogStatus.FAIL, "Error message is not macthing when matching STeAM account is in evicted state and trying to login using Facebook");
				}
				closeBrowser();
			
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
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}


}



