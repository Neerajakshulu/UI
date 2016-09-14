package iam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class IAM032 extends TestBase {
	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	static int time = 30;
	PageFactory pf = new PageFactory();

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verfiy that user is able to link the social accounts with neon account")
				.assignCategory("Authoring");

	}

	@Test
	public void testInitiatePostCreation() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "IAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(authoringxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		loginTofb();
		linkAccounts("Facebook");

		liginToLinkedIn();
		linkAccounts("LinkedIn");
	}

	private void liginToLinkedIn() throws Exception {
		openBrowser();
		maximizeWindow();
		clearCookies();

		// Navigate to TR login page and login with valid TR credentials
		ob.navigate().to(host);
		pf.getLoginTRInstance(ob).loginWithLinkedInCredentials(LOGIN.getProperty("USERNAME17"),
				LOGIN.getProperty("PASSWORD17"));
		pf.getLoginTRInstance(ob).logOutApp();
		closeBrowser();
		pf.clearAllPageObjects();

	}

	private void loginTofb() throws Exception {
		openBrowser();
		maximizeWindow();
		clearCookies();

		// Navigate to TR login page and login with valid TR credentials
		ob.navigate().to(host);
		pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("USERNAME17"),
				LOGIN.getProperty("PASSWORD17"));
		pf.getLoginTRInstance(ob).logOutApp();
		closeBrowser();
		pf.clearAllPageObjects();

	}

	private void linkAccounts(String accountType) throws Exception {
		
		try {
			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			// ob.get(CONFIG.getProperty("testSiteName"));

			loginAs("USERNAME17", "PASSWORD17");

			String linkName=pf.getLoginTRInstance(ob).clickOnLinkButtonInLoginPage();
			
			if (linkName == null) {
				test.log(LogStatus.FAIL,"Issue with link modal: Neither Facebook nor LinkedIn button is dispalyed");
				ErrorUtil.addVerificationFailure(new Exception("Link Modal is not dispalyed properly"));// testng
			} else if(!linkName.equalsIgnoreCase(accountType)) {
				test.log(LogStatus.FAIL, "User is asked to link the account again when user has already linked them");
				ErrorUtil.addVerificationFailure(new Exception("Wrong Link button is dispalyed"));// testng
				
			}
			 if (accountType.equalsIgnoreCase("Facebook")) {
				pf.getLoginTRInstance(ob).signInToFacebook(LOGIN.getProperty("USERNAME17"),
						LOGIN.getProperty("PASSWORD17"));
				test.log(LogStatus.PASS, "User is able to link " + accountType + " account to Neon account");
			} else if (accountType.equalsIgnoreCase("LinkedIn")) {
				pf.getLoginTRInstance(ob).signInToLinkedIn(LOGIN.getProperty("USERNAME17"),
						LOGIN.getProperty("PASSWORD17"));
				test.log(LogStatus.PASS, "User is able to link " + accountType + " account to Neon account");
			}

			pf.getHFPageInstance(ob).clickOnAccountLink();
			validateLinkedAccounts(2, accountType);
			pf.getLoginTRInstance(ob).logOutApp();
			try {

				loginAs("USERNAME17", "PASSWORD17");
				test.log(LogStatus.PASS,
						"User is not asked to link the account again when user has already linked them");
			} catch (Exception e) {
				test.log(LogStatus.FAIL, "User is asked to link the account again when user has already linked them");
				ErrorUtil.addVerificationFailure(e);// testng
				test.log(LogStatus.INFO, "Snapshot below: "
						+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "Linking_failed")));// screenshot
			}
			pf.getHFPageInstance(ob).clickOnAccountLink();
			validateLinkedAccounts(2, accountType);
			pf.getLoginTRInstance(ob).logOutApp();

		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			status = 2;// excel
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng

			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot

		} finally {
			closeBrowser();
			pf.clearAllPageObjects();

		}
	}

	private void validateLinkedAccounts(int accountCount, String linkName) throws Exception {
		try {

			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount("Neon", LOGIN.getProperty("USERNAME17")));
			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("USERNAME17")));
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
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
		 * if (status == 1) TestUtil.reportDataSetResult(authoringxls,
		 * "Test Cases", TestUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(authoringxls, "Test Cases",
		 * TestUtil.getRowNum(authoringxls, this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(authoringxls, "Test Cases"
		 * , TestUtil.getRowNum(authoringxls, this.getClass().getSimpleName()),
		 * "SKIP");
		 */

	}
}
