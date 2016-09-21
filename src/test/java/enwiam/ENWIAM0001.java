package enwiam;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;


public class ENWIAM0001 extends TestBase {
	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	static int time = 30;
        PageFactory pf=new PageFactory();
        
       
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");

	}

	@Test
	public void testLogin() throws Exception {
		
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		

		
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			String statuCode = deleteUserAccounts(LOGIN.getProperty("UserName18"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
						
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		
		
		try {
			
			openBrowser();
			maximizeWindow();
			clearCookies();
			loginTofb();
			loginToLn();
			loginToFacebook();
			loginToLinkedIn();
			} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

		//Linking Facebook with the existing STeAM
		
	private void loginTofb() throws Exception {
		

		// Navigate to TR login page and login with valid TR credentials
		ob.navigate().to(host);
		String accountType="Facebook";
		
		pf.getEnwReferenceInstance(ob).loginWithFBCredentialsENW(ob,"arvindkandaswamy@gmail.com","darshiniyogi@123");
		pf.getENWReferencePageInstance(ob).didYouKnow(LOGIN.getProperty("Password19"));
		//LOGIN.getProperty("Password19")
		ob.findElement(By.className("btn-common")).click();
		pf.getENWReferencePageInstance(ob).clickAccount();
		pf.getENWReferencePageInstance(ob).closeOnBoardingModal();
		pf.getENWReferencePageInstance(ob).logout();
		
		waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("FB_login_button")), 30);
		ob.findElement(By.cssSelector(OR.getProperty("FB_login_button"))).click();
		try {
			ob.findElement(By.className("btn-common")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Continue button is not working");// extent
			//ErrorUtil.addVerificationFailure(t);
		}
		BrowserWaits.waitTime(3);
		pf.getENWReferencePageInstance(ob).clickAccount();
		validateLinkedAccounts(2,accountType);
		pf.getENWReferencePageInstance(ob).logout();
		
	}
	
	//Linking LinkedIn with the existing STeAM
private void loginToLn() throws Exception {
		

		// Navigate to TR login page and login with valid TR credentials
		
		String accountType="LinkedIn";
		
		pf.getENWReferencePageInstance(ob).loginWithENWLnCredentials("arvindkandaswamy@gmail.com", "darshiniyogi");
		pf.getENWReferencePageInstance(ob).didYouKnow(LOGIN.getProperty("Password19"));
		try {
			String text = ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()))
					.getText();
			if (text.equalsIgnoreCase("Continue")) {
				ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Continue button is not working");// extent
			//ErrorUtil.addVerificationFailure(t);
		}
		
		pf.getENWReferencePageInstance(ob).clickAccount();
		validateLinkedAccounts(3,accountType);
		pf.getENWReferencePageInstance(ob).logout();
		
		}

//Signing into Facebook account again to ensure that linking modal is not displaying

private void loginToFacebook() throws Exception{
	String accountType="Facebook";
	
	waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("FB_login_button")), 30);
	ob.findElement(By.cssSelector(OR.getProperty("FB_login_button"))).click();
	try {
		String text = ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()))
				.getText();
		if (text.equalsIgnoreCase("Continue")) {
			ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		test.log(LogStatus.FAIL, "Continue button is not working");// extent
		//ErrorUtil.addVerificationFailure(t);
	}
	pf.getENWReferencePageInstance(ob).clickAccount();
	validateLinkedAccounts(3,accountType);
	pf.getENWReferencePageInstance(ob).logout();
	
}
//Checking whether the linking modals are displaying or not while signing into LinkedIn
private void loginToLinkedIn() throws Exception{
	
	pf.getENWReferencePageInstance(ob).loginWithENWLnCredentials("arvindkandaswamy@gmail.com", "darshiniyogi");
	try {
		String text = ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()))
				.getText();
		if (text.equalsIgnoreCase("Continue")) {
			ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	pf.getENWReferencePageInstance(ob).clickAccount();
	
	pf.getENWReferencePageInstance(ob).logout();
	closeBrowser();
	pf.clearAllPageObjects();
}

//Validating the linked accounts with STeAM
	
	private void validateLinkedAccounts(int accountCount, String linkName) throws Exception {
		try {

			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount("Neon", LOGIN.getProperty("UserName40")));
			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("UserName40")));
			test.log(LogStatus.PASS , "The account are matching");
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			System.out.println(accountCount);
			test.log(LogStatus.PASS,
					"Linked accounts are available in accounts page : Neon and " + linkName + " accounts");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL,
					"Linked accounts are not available in accounts page : Neon and " + linkName + " accounts");
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "Linking_failed")));// screenshot
		}
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
