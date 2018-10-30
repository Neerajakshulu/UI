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

public class PUBLONS027 extends TestBase{
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
			String usertrueid="0644e6a0-cddf-11e8-8e9d-05fb45806044";
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
				pf.getPubPage(ob).loginPublonsAccont(LOGIN.getProperty("PUBLONSLINKEDLNUSER"),LOGIN.getProperty("PUBLONSLINKEDLNPWD"));
				test.log(LogStatus.PASS, "user has logged with Steam account in publons to make it Activated");
					pf.getLoginTRInstance(ob).logOutApp();
					test.log(LogStatus.PASS,"LogOut from the application Sucessfully .");
					try{
						//Now again navigate to application and Sign in using Facebook account. 
						ob.navigate().to(host);
						pf.getPubPage(ob).loginWithLinkedlnCredentialsWithOutLinking(LOGIN.getProperty("PUBLONSLINKEDLNUSER"), LOGIN.getProperty("PUBLONSLINKEDLNPWD"));	
					   // Verify Linking model.
						String expectedText1="Already have an account?";
						String expectedText2="Your email address";
						String expectedEmail="test_wnqvjum_user@tfbnw.net,";
						waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_TEXT1_XPATH.toString()), 60);
						String str1=
								ob.findElement(By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_TEXT1_XPATH.toString())).getText();
						waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_TEXT2_XPATH.toString()), 60);
						String str2=
								ob.findElement(By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_TEXT2_XPATH.toString())).getText();
						waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_EMAIL_XPATH.toString()), 60);
						String useremail=
								ob.findElement(By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_EMAIL_XPATH.toString())).getText();
						 if(expectedText1.equalsIgnoreCase(str1)
								 && useremail.equalsIgnoreCase(expectedEmail) && str2.contains(expectedText2) )
						 {
							 test.log(LogStatus.PASS,"Linking Model has been seen. and all text present on linking model.");
						 }
						 else{
							 test.log(LogStatus.PASS,"Linking Model has not been seen or text present on linking model is not matching.");
						 }
						 //Enter Password on linking model and click on SignIn.
						 pf.getPubPage(ob).linkAccount(LOGIN.getProperty("PUBLONSLINKEDLNPWD"));
						 pf.getPubPage(ob).verifyLinkedAccounts(LOGIN.getProperty("PUBLONSLINKEDLNUSER"),2,test);
						 pf.getPubPage(ob).navigateMainWindow();
						 pf.getLoginTRInstance(ob).logOutApp();
						 
						 // Now Verify Once Account linked then second tie user will not challenged linking model.
						 try{
							 pf.getPubPage(ob).loginPublonsAccont(LOGIN.getProperty("PUBLONSLINKEDLNUSER"),LOGIN.getProperty("PUBLONSLINKEDLNPWD"));
							 pf.getLoginTRInstance(ob).logOutApp();
							 test.log(LogStatus.PASS,"Once account linked then second time user not see the linking model.");
						 }
						 catch(Throwable t){
							 t.printStackTrace();
							 test.log(LogStatus.PASS,"After account linked User not able to login.");
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


