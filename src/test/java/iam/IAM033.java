package iam;

import java.io.PrintWriter;
import java.io.StringWriter;




import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;


public class IAM033 extends TestBase {
	
	static int status = 1;
	static boolean fail = false;
	static boolean skip = false;
	static int time = 30;
	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	
	 PageFactory pf = new PageFactory();
	 
	 @BeforeTest
		public void beforeTest() throws Exception {
			extent = ExtentManager.getReporter(filePath);
			String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
			test = extent.startTest(var, " Sign-in with Social and link existing steam  account with matching email, Sign-in with Social account which already has linked Staem account")
					.assignCategory("IAM");

		}
	@Test
	public void testcaseA13() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "IAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(iamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		// Verify that TERMS OF USE and PRIVACY STATEMENT links are working correctly in Singn In Page
		
		try {
			String statuCode = deleteUserAccounts(CONFIG.getProperty("fbusername"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		
				
		try {
			openBrowser();
			maximizeWindow();
		   ob.navigate().to(host);
		   pf.getLoginTRInstance(ob).enterTRCredentials(CONFIG.getProperty("fbusername"),CONFIG.getProperty("fbpwrd"));
		   pf.getLoginTRInstance(ob).clickLogin();
         	test.log(LogStatus.PASS, "User is able to log in with steam credentials");
		   pf.getLoginTRInstance(ob).logOutApp();
	
		   pf.getLoginTRInstance(ob).loginWithFBCredentials(CONFIG.getProperty("fbusername"),CONFIG.getProperty("fbpwrd"));
		   pf.getLoginTRInstance(ob).socialLinking();
		   test.log(LogStatus.PASS,"User has logged in with facebook credentials");
//		   pf.getLoginTRInstance(ob).checkLinking();
//		   Assert.assertTrue(
//					pf.getAccountPageInstance(ob).verifyLinkedAccount("Facebook",CONFIG.getProperty("fbusername")));
//		  // pf.getAccountPageInstance(ob).verifyLinkedAccount("Face Book",CONFIG.getProperty("fbusername"));
		   test.log(LogStatus.PASS, "Facebook account is linked with steam account");
		   pf.getLoginTRInstance(ob).logOutApp();
		   
	}
		catch(Throwable t) {
			test.log(LogStatus.FAIL, "Linking with face book  is not performed properly");// extent
																	
			
		}finally{
				closeBrowser();
			   pf.clearAllPageObjects();
		}
		try{
			openBrowser();
			maximizeWindow();
		   ob.navigate().to(host);
			  pf.getLoginTRInstance(ob).loginWithLinkedInCredentials(CONFIG.getProperty("fbusername"),CONFIG.getProperty("fbpwrd"));
			   pf.getLoginTRInstance(ob).socialLinking();
			   test.log(LogStatus.PASS,"User has logged in with Linkedin credentials");
			   pf.getLoginTRInstance(ob).checkLinking();
			   test.log(LogStatus.PASS, "Linkedin account is linked with steam account");
			   pf.getLoginTRInstance(ob).logOutApp();  
			   
		}
		catch(Throwable t)
		{
			test.log(LogStatus.FAIL, "Linking with face book  is not performed properly");// extent
			closeBrowser();
		}finally{
			closeBrowser();
			   pf.clearAllPageObjects();
		}
		try{
			openBrowser();
			maximizeWindow();
		   ob.navigate().to(host);
		   pf.getLoginTRInstance(ob).loginWithFBCredentials(CONFIG.getProperty("fbusername"),CONFIG.getProperty("fbpwrd"));
		   pf.getLoginTRInstance(ob).checkLinking();
		   Assert.assertTrue(pf.getAccountPageInstance(ob).verifyLinkedAccount("Facebook",CONFIG.getProperty("fbusername")));
		   test.log(LogStatus.PASS, "Linked accounts are available in accounts page");
		     	  pf.getLoginTRInstance(ob).logOutApp();
		}
		catch(Throwable t)
		{
			test.log(LogStatus.FAIL, "Linking with face book  is not performed properly for second time");// extent
			
		}finally{
			closeBrowser();
			   pf.clearAllPageObjects();
		}
		try{
			openBrowser();
			maximizeWindow();
		   ob.navigate().to(host);
		   pf.getLoginTRInstance(ob).loginWithLinkedInCredentials(CONFIG.getProperty("fbusername"),CONFIG.getProperty("fbpwrd"));
		   pf.getLoginTRInstance(ob).checkLinking();
		   Assert.assertTrue(pf.getAccountPageInstance(ob).verifyLinkedAccount("Linkedin",CONFIG.getProperty("fbusername")));
		   test.log(LogStatus.PASS, "Linked accounts are available in accounts page ");
		     	  pf.getLoginTRInstance(ob).logOutApp();
		     	 closeBrowser();
		}
	catch(Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}
	
	
		@AfterTest
		public void reportTestResult() {
			extent.endTest(test);

			/*
			 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
			 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS");
			 * else if(status==2) TestUtil.reportDataSetResult(iamxls, "Test Cases",
			 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL");
			 * else TestUtil.reportDataSetResult(iamxls, "Test Cases",
			 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
			 */
		}


}
