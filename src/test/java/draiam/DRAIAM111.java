package draiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;

public class DRAIAM111 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("DRAIAM");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception , When TR Login is not done
	 */
	@Test
	public void testcaseDRA1() throws Exception {
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
			ob.navigate().to(host + CONFIG.getProperty("dra_sso_url"));
			pf.getDraSSOPageInstance(ob).loginDRAApp(LOGIN.getProperty("DRASSOUSERNAME"),LOGIN.getProperty("DRASSOPASSWORD"));
			pf.getDraPageInstance(ob).logoutDRA();
			waitUntilText("Drug Research Advisor","Sign in");
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
