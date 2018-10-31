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
import util.OnePObjectMap;
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
			String usertrueid="70709110-dcca-11e8-9cd4-6d6c4e481925";
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
			pf.getPubPage(ob).loginWithGmailCredentialsWithOutLinking(LOGIN.getProperty("PUBLONSGM&LNUSER"), LOGIN.getProperty("PUBLONSGM&LNPASS"));	
			test.log(LogStatus.PASS,"Logging with Gmail account .");
			//Click on the linking model.
			waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_PASSWORD_TEXT_XPATH.toString()), 60);
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_PASSWORD_TEXT_XPATH, LOGIN.getProperty("PUBLONSGM&LNPASS"));
			waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH.toString()), 60);
			//jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH.toString())));
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH);
			Thread.sleep(5000);
			pf.getPubPage(ob).moveToAccountSettingPage();
			pf.getPubPage(ob).windowHandle();
			pf.getPubPage(ob).clickTab("Connected accounts");
			test.log(LogStatus.PASS,"After click on the Account setting page , moved to the connected tab.");
			try{
			pf.getPubPage(ob).connectWithLN(LOGIN.getProperty("PUBLONSLNACCOUNT1"), LOGIN.getProperty("PUBLONSLNACCPASS1"));
				waitForAllElementsToBePresent(ob, By.cssSelector("span[class='ng-binding ng-scope']"), 60);
				List<WebElement> list = ob.findElements(By.cssSelector("span[class='ng-binding ng-scope']"));
				Assert.assertEquals(3, list.size(),"Account count is not matching in connected tab.");
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
			 pf.getPubPage(ob).navigateMainWindow();
			 pf.getLoginTRInstance(ob).logOutApp();
			 //Now Close the old Linkedln Session.
			 ob.navigate().to("https://linkedin.com");
			 Thread.sleep(3000);
			 waitForElementTobeClickable(ob, By.xpath("//img[@class='nav-item__profile-member-photo nav-item__icon ghost-person']"), 60);
			 jsClick(ob, ob.findElement(By.xpath("//img[@class='nav-item__profile-member-photo nav-item__icon ghost-person']")));
			 waitForElementTobeClickable(ob, By.xpath("//a[text()='Sign out']"),60);
			 jsClick(ob, ob.findElement(By.xpath("//a[text()='Sign out']")));
			 test.log(LogStatus.INFO, "Sucessfully Close active linkedln account session.");
			//Now Again try to login with LinkedLn with different Account and verify error message.
			 try{
				 String unableToLinkmsg ="Unable to link";
				 String ErrorMessageWithEmail ="We're sorry. We are unable to link your accounts. To get help, please send email to community.info@clarivate.com";
				 String Useremailid ="community.info@clarivate.com";
				 ob.navigate().to(host);
				//Now Sign again with different linkedln account.
				 pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_LINKEDLN_SIGN_IN_BUTTON_CSS);
					pf.getBrowserActionInstance(ob).click(OnePObjectMap.LOGIN_PAGE_LINKEDLN_SIGN_IN_BUTTON_CSS);
					pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_LI_EMAIL_TEXT_BOX_ID);
					pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_LI_EMAIL_TEXT_BOX_ID, LOGIN.getProperty("PUBLONSGM&LNUSER"));
					pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_LI_PASSWORD_TEXT_BOX_ID, LOGIN.getProperty("PUBLONSGM&LNPASS"));
					pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_LI_EMAIL_TEXT_BOX_SIGNIN_ID);
				 //Verify error message when the matching LinkedLn account is already linked to the provider of the signed in user.
				 pf.getPubPage(ob).vrfyErrorMsgAccountAlreadyLinked(unableToLinkmsg, ErrorMessageWithEmail, Useremailid);
				 test.log(LogStatus.PASS, "Error message matching when the matching Linkedln account is already linked to the signed user.");
				 ob.close();
			 }
			catch(Throwable t){
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Error message Not matched when the matching LinkedLn account is already linked.");// extent	
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
  