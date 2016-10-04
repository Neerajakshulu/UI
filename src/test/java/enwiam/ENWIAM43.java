package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class ENWIAM43 extends TestBase {

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	static int time = 30;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(),
				rowData.getTestcaseDescription()).assignCategory("ENWIAM");
	}

	@Test
	public void testcaseh4() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case "
					+ this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"
					+ this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		
		try {
			String statuCode = deleteUserAccounts(LOGIN
					.getProperty("sru_fbusername03"));
			logger.info("status code-->"+statuCode);
			if(!(statuCode.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"))) {
				throw new Exception("Delete API Call failed");
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution starts--->");
		try {
			openBrowser();
			maximizeWindow();
			ob.navigate().to(host);
			pf.getLoginTRInstance(ob).loginWithFBCredentials(
					LOGIN.getProperty("sru_fbusername03"),
					LOGIN.getProperty("sru_fbpwd03"));
			test.log(LogStatus.PASS, "user has logged in with social account");
			pf.getHFPageInstance(ob).clickOnEndNoteLink();
			test.log(LogStatus.PASS, "User navigate to End note");
			BrowserWaits.waitTime(2);
			pf.getENWReferencePageInstance(ob).yesAccount(
					LOGIN.getProperty("NonActivated_SteamUsername"), LOGIN.getProperty("NonActivated_SteamPw"));
						
			waitForElementTobeVisible(ob,
					By.xpath(OnePObjectMap.ACCOUNT_NOTACTIVATED_MSG_XPATH
							.toString()), 30);
			
			WebElement message_displayed = ob.findElement(By
					.xpath(OnePObjectMap.ACCOUNT_NOTACTIVATED_MSG_XPATH
							.toString()));
			
						
			String actual_msg=message_displayed.getText();
			String expected_msg="Your account registration has not yet been confirmed";
			
			try{
				Assert.assertEquals(actual_msg, expected_msg);
				test.log(LogStatus.PASS, "User is able to see message - Your account registration has not yet been confirmed.");
				
				try{
				waitForElementTobeVisible(ob,
						By.xpath(OnePObjectMap.OK_BUTTON_ACCOUNT_NOTACTIVATED_XPATH
								.toString()), 30);
				ob.findElement(By
						.xpath(OnePObjectMap.OK_BUTTON_ACCOUNT_NOTACTIVATED_XPATH
								.toString())).click();
				
				}catch(Throwable t){
					t.printStackTrace();
					test.log(LogStatus.FAIL,
							"User is able to click on OK button");
					ErrorUtil.addVerificationFailure(t);
				}
				
				
			}catch(Throwable t){
				
				t.printStackTrace();
				test.log(LogStatus.FAIL,
						"User is able to see Activation not confirmed message");
				ErrorUtil.addVerificationFailure(t);

			}
			
			BrowserWaits.waitTime(2);
			logout();
			closeBrowser();
			pf.clearAllPageObjects();

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "Facebook is not linked with ENW ");// extent
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this
									.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();

		}
		
		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
	}
}
