package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class PUBLONS030 extends TestBase{
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
	public void testcaseBlockeduserMatchingAccount() throws Exception {
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
			pf.getDraPageInstance(ob).steamLockedDRA(LOGIN.getProperty("PUBLONSBLOCKEDUSER"));
			test.log(LogStatus.PASS,"User account has been blocked sucessfully.");
			pf.getPubPage(ob).checkBlockedUserErrorMessage();
			test.log(LogStatus.PASS,"blocked user error message verified sucessfully");
			try{
				ob.navigate().to(host);
				pf.getPubPage(ob).loginWithGmailCredentialsWithOutLinking(LOGIN.getProperty("PUBLONSBLOCKEDUSER"),LOGIN.getProperty("PUBLONSBLOCKEDPASS"));
				//Click on the linking model.
				waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_PASSWORD_TEXT_XPATH.toString()), 60);
				pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_PASSWORD_TEXT_XPATH, LOGIN.getProperty("PUBLONSBLOCKEDPASS"));
				waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH.toString()), 60);
				//jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH.toString())));
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH);
				pf.getPubPage(ob).checkBlockedUserErrorMessage();
				test.log(LogStatus.PASS,"blocked user error message verified sucessfully");
			}
			catch(Throwable t){
				
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
