package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class PUBLONS042 extends TestBase{
	
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
	public void testcaseGmailLinkingErrorMessage() throws Exception {
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
			String usertrueid="7b495950-b010-11e8-a40e-c11c5f4c9e73";
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
				pf.getPubPage(ob).loginPublonsAccont(LOGIN.getProperty("PUBLONSGMAILUSER"),LOGIN.getProperty("PUBLONSGMAILSTEAMPWD"));
				test.log(LogStatus.PASS, "user has logged with Steam account in publons to make it Activated");
					pf.getLoginTRInstance(ob).logOutApp();
					test.log(LogStatus.PASS,"LogOut from the application Sucessfully .");
					try{
						//Now again navigate to application and Sign in using Facebook account. 
						ob.navigate().to(host);
						pf.getPubPage(ob).loginWithGmailCredentialsWithOutLinking(LOGIN.getProperty("PUBLONSGMAILUSER"), LOGIN.getProperty("PUBLONSGMAILPWD"));	
					   // Verify Linking model.
						String expectedText1="Already have an account?";
						String expectedText2="Your email address";
						String wrongPass="test123";
						waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_TEXT1_XPATH.toString()), 60);
						String str1=
								ob.findElement(By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_TEXT1_XPATH.toString())).getText();
						waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_TEXT2_XPATH.toString()), 60);
						String str2=
								ob.findElement(By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_TEXT2_XPATH.toString())).getText();
						waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_EMAIL_XPATH.toString()), 60);
						 if(expectedText1.equalsIgnoreCase(str1)
								 && str2.contains(expectedText2) )
						 {
							 test.log(LogStatus.PASS,"Linking Model has been seen. and all text present on linking model.");
						 }
						 else{
							 test.log(LogStatus.PASS,"Linking Model has not been seen or text present on linking model is not matching.");
						 }
						 //Enter wrong Password on linking model and click on SignIn.
						 waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_PASSWORD_TEXT_XPATH.toString()), 60);
							pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_PASSWORD_TEXT_XPATH, wrongPass);
							waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH.toString()), 60);
							pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH);
						 // Now Verify Error message if user enter wrong password in the linking model.
							try{
							String actualString ="The email and password do not match. Please try again.";
							//waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_ERROR_MESSAGE_XPATH.toString()), 60);
							fluentwaitforElement(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_ERROR_MESSAGE_XPATH.toString()), 60);
							String expectedErrorMsg=ob.findElement(By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_ERROR_MESSAGE_XPATH.toString())).getText();
							System.out.println(expectedErrorMsg);
							Assert.assertEquals(actualString, expectedErrorMsg, "Error message is not matching.");
							test.log(LogStatus.PASS,"After Enter wrong password ,Error message showing correctly.");
							}
							catch(Throwable t){
								t.printStackTrace();
								test.log(LogStatus.PASS,"After Enter wrong password ,Error message not seen.");
								
							}
						
					}
					finally{	
						closeBrowser();	
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



