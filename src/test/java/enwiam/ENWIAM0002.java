package enwiam;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class ENWIAM0002 extends TestBase {
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
		// Deleting the links for arvindkandaswamy@gmail.com 
		
		try {
			String statuCode = deleteUserAccounts(LOGIN.getProperty("UserName18"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
						
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		
		//Deleting the links for aravind.attur@thomsonreuters.com
		try {
			String statuCode = deleteUserAccounts(LOGIN.getProperty("UserName20"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200"));	
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		
		
		
		try {
			
			openBrowser();
			maximizeWindow();
			clearCookies();
			loginToFb();			
		} 
		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Unexpected error");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		
		// Deleting the links for arvindkandaswamy@gmail.com 
				try {
					String statuCode = deleteUserAccounts(LOGIN.getProperty("UserName18"));
					Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
					
				} catch (Throwable t) {
					test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
					ErrorUtil.addVerificationFailure(t);
				}
				//Deleting the links for aravind.attur@thomsonreuters.com
				try {
					String statuCode = deleteUserAccounts(LOGIN.getProperty("UserName20"));
					Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
					
				} catch (Throwable t) {
					test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
					ErrorUtil.addVerificationFailure(t);
				}
				
				
				
				try {
					
					loginToLn();
					closeBrowser();
					pf.clearAllPageObjects();
				} 
				catch (Throwable t) {
					test.log(LogStatus.FAIL, "Unexpected error");// extent
					ErrorUtil.addVerificationFailure(t);
				}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}
	
	
	private void loginToFb() throws Exception{
		ob.navigate().to(host);
		String accountType="Facebook";
		pf.getEnwReferenceInstance(ob).loginWithFBCredentialsENW(ob,"aravind.attur@thomsonreuters.com","Facebook@123");
		//LOGIN.getProperty("UserName19"),  LOGIN.getProperty("Password19")

		pf.getENWReferencePageInstance(ob).yesAccount(LOGIN.getProperty("UserName19"),  LOGIN.getProperty("Password19"));
		try {
			ob.findElement(By.className("btn-common")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Continue button is not working");// extent
			//ErrorUtil.addVerificationFailure(t);
		}
		pf.getENWReferencePageInstance(ob).clickAccount();
		
			validateLinkedAccounts(2, accountType);
			
		
		pf.getENWReferencePageInstance(ob).logout();
		
	}
	private void loginToLn() throws Exception{
		ob.navigate().to(host);
		String accountType="LinkedIn";
		pf.getENWReferencePageInstance(ob).loginWithENWLnCredentials("aravind.attur@thomsonreuters.com","Linked@123");		
		pf.getENWReferencePageInstance(ob).yesAccount(LOGIN.getProperty("UserName19"),  LOGIN.getProperty("Password19"));
		try {
			ob.findElement(By.className("btn-common")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pf.getENWReferencePageInstance(ob).clickAccount();
		
			validateLinkedAccounts(2, accountType);
			
		
		pf.getENWReferencePageInstance(ob).logout();
		
	}
	private void validateLinkedAccounts(int accountCount, String linkName) throws Exception {
		try {

			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount("Neon", LOGIN.getProperty("UserName18")));
			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("UserName20")));
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
