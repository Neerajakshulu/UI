package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import util.ErrorUtil;
import util.ExtentManager;
import base.TestBase;

public class PUBLONS028 extends TestBase{
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
	public void testcaseGmailLinking() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->" + this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		try {
			String usertrueid="1f91c5d0-dc33-11e8-9cd4-6d6c4e481925";
			String statuCode = delinkUserAccounts(usertrueid);

			if (!(statuCode.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"))) {
				// test.log(LogStatus.FAIL, "Delete accounts api call failed");
				throw new Exception("Delete API Call failed");
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");
		try {

			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
			pf.getPubPage(ob).loginWithGmailCredentialsWithOutLinking(LOGIN.getProperty("PUBLONSGMAILUSER"), LOGIN.getProperty("PUBLONSGMAILPWD"));	
			pf.getPubPage(ob).moveToAccountSettingPage();
			pf.getPubPage(ob).windowHandle();
			pf.getPubPage(ob).clickTab("Connected accounts");
			try{
			pf.getPubPage(ob).connectWithLN(LOGIN.getProperty("PUBLONSLNACCOUNT1"), LOGIN.getProperty("PUBLONSLNACCPASS1"));
				waitForAllElementsToBePresent(ob, By.cssSelector("span[class='ng-binding ng-scope']"), 60);
				List<WebElement> list = ob.findElements(By.cssSelector("span[class='ng-binding ng-scope']"));
				Assert.assertEquals(2, list.size(),"Account count is not matching in connected tab.");
				ob.close();
				test.log(LogStatus.PASS,"LinkedLn accounts connected and available in connected page.");
		
			}
			catch(Throwable t){
				test.log(LogStatus.FAIL,
						"LinkedLn accounts not connected with gmail user.");
				ErrorUtil.addVerificationFailure(t);// testng
				test.log(LogStatus.INFO, "Snapshot below: "
						+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "Connecting_failed")));// screenshot
				
				}
			
			
			
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
			closeBrowser();  }
			
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
		
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}

}
  