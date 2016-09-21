package enwiam;


import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import Authoring.LoginTR;
import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;



/*
 * Checking linked STeAM with LinkedIn account on ENW
 */
public class ENWIAM0004 extends TestBase {
	static int status = 1;
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(var, "Verify that deep linking is working correctly for help page using FB and LI accounts")
				.assignCategory("IAM");

	}

	@Test
	public void testcase0004() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "IAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(iamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			//String statuCode = deleteUserAccounts(LOGIN.getProperty("USERNAME17"));
			//Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			loginTofb();
			//linkAccounts("Facebook");
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	private void loginTofb() throws Exception {
		openBrowser();
		maximizeWindow();
		clearCookies();
         String userName="arvindkandaswamy@gmail.com";
         String passWord="darshiniyogi";
        		 String accountType="LinkedIn";
		// Navigate to TR login page and login with valid TR credentials
		//ob.navigate().to(host);
        		 ob.get(host+CONFIG.getProperty("appendENWAppUrl"));
		pf.getENWReferencePageInstance(ob).loginWithENWLnCredentials(userName,passWord);
		//(LOGIN.getProperty("USERNAME17"),	LOGIN.getProperty("PASSWORD17"));
		pf.getENWReferencePageInstance(ob).clickAccount();
		pf.getENWReferencePageInstance(ob).closeOnBoardingModal();
		validateLinkedAccounts(2,accountType);
		pf.getENWReferencePageInstance(ob).logout();
		closeBrowser();
		pf.clearAllPageObjects();

	}

	//Validating the linked Account
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
