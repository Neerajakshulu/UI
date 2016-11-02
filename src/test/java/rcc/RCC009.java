package rcc;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;

public class RCC009 extends TestBase {

	static int status = 1;

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent
	 * Reports
	 * 
	 * @throws Exception,
	 *             When Something unexpected
	 */
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("RCC");
	}

	/**
	 * Method for wait TR Login Screen
	 * 
	 * @throws Exception,
	 *             When TR Login screen not displayed
	 */
	@Test
	public void verifyingInvitations() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");
		try {
			String groupTitle = this.getClass().getSimpleName() + "_Group_" + "_" + getCurrentTimeStamp();
			String desc = this.getClass().getSimpleName() + "_Group_" + RandomStringUtils.randomAlphanumeric(100);
			// login with Owneruser
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("Owneruser1", "Owneruser1Password");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).clickOnCreateNewGroupButton();
			pf.getGroupsListPage(ob).createGroup(groupTitle, desc);

			// Inviting user2
			boolean result = pf.getGroupDetailsPage(ob).inviteMembers("Asif Alberto");
			pf.getGroupDetailsPage(ob).clickOnSendInvitation();
			if (result)
				test.log(LogStatus.PASS, "Invitation has been send to the Neon user1");
			else {
				test.log(LogStatus.FAIL, "Sending Invitation is failed due to neon user1 does not exist");
				throw new Exception("Sending Invitation is failed due to user1 does not exist");
			}
			// Inviting user3
			boolean result1 = pf.getGroupDetailsPage(ob).inviteMembers("Mohammed Asif");
			pf.getGroupDetailsPage(ob).clickOnSendInvitation();

			if (result1)
				test.log(LogStatus.PASS, "Invitation has been send to the Neon user2");
			else {
				test.log(LogStatus.FAIL, "Sending Invitation is failed due to neon user2 does not exist");
				throw new Exception("Sending Invitation is failed due to user2 does not exist");
			}
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();
			// logging in with user2 to accept invitation
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("InviteUser2", "InviteUser2password");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			try {
				pf.getGroupInvitationPage(ob).acceptInvitation(groupTitle);
				test.log(LogStatus.INFO, "Invitation has been send to the Neon user2 and accepted");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Error in sending invitation");
				test.log(LogStatus.FAIL, "Snapshot below: " + test
						.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_Group_desc_mismatch")));// screenshot
				ErrorUtil.addVerificationFailure(t);
			}

			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();
			// logging in with user3 to accept invitation
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("InviteUser3", "InviteUser3Password");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupInvitationPage(ob).acceptInvitation(groupTitle);
			test.log(LogStatus.INFO, "Invitation has been send to the Neon user3 and accepted");
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();
			// logging in with owneruser1 and check the members count in the
			// list page and details page

			/*openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("Owneruser1", "Owneruser1Password");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsListPage(ob).clickOnGroupTitle(groupTitle);
			pf.getGroupDetailsPage(ob).getMembersCounts();
			test.log(LogStatus.INFO, "Member count is fine with owner");
*/
			// logging in with User and check the members count in the list page
			// and details page

			/**
			 * openBrowser(); clearCookies(); maximizeWindow();
			 * 
			 * ob.navigate().to(host); loginAs("InviteUser2",
			 * "InviteUser2password"); pf.getGroupsPage(ob).clickOnGroupsTab();
			 * pf.getGroupsListPage(ob).verifyMembersCount2(2, groupTitle);
			 * test.log(LogStatus.INFO,"Member count is fine with owner");
			 */
			// As user 2 leave the group and check the pop up buttons
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("InviteUser2", "InviteUser2password");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsListPage(ob).clickOnGroupTitle(groupTitle);
			pf.getGroupDetailsPage(ob).clickonLeaveGroupButton();
			test.log(LogStatus.INFO, "Clicked on leave group button Pop Up opened");

			try {
				pf.getGroupDetailsPage(ob).verifyLeaveGroupPopupMessage(test);
				test.log(LogStatus.PASS, "verified on leave group popup message");

				Assert.assertTrue(pf.getGroupDetailsPage(ob).verifyleavegroupPopupButtons());
				test.log(LogStatus.PASS, "verified on leave group popup buttons");

				Assert.assertTrue(pf.getGroupDetailsPage(ob).clickonCancelButtononPopup());
				test.log(LogStatus.PASS, "Cancel button is working fine");
				ob.navigate().refresh();
				pf.getGroupDetailsPage(ob).clickonLeaveGroupButton();
				Assert.assertTrue(pf.getGroupDetailsPage(ob).clickonCloseButtononPopup());
				test.log(LogStatus.PASS, "Close button is working fine");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Error in verifying the pop up");
				test.log(LogStatus.FAIL, "Snapshot below: " + test
						.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_Group_desc_mismatch")));// screenshot
				ErrorUtil.addVerificationFailure(t);
			}

			// leaving the group and verifying the group list
			try {
				ob.navigate().refresh();
				pf.getGroupDetailsPage(ob).clickonLeaveGroupButton();
				pf.getGroupDetailsPage(ob).clickOnLeaveGroupButtoninPopUp();
				Assert.assertFalse(pf.getGroupsListPage(ob).checkForGroup(groupTitle));
				test.log(LogStatus.PASS, "After clicking the on the leave group button, Group is not present");
				logout();
				closeBrowser();
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Error in verifying the pop up");
				test.log(LogStatus.FAIL, "Snapshot below: " + test
						.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_Group_desc_mismatch")));// screenshot
				ErrorUtil.addVerificationFailure(t);
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something went wrong");
			// print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.FAIL, errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.FAIL, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_login_not_done")));// screenshot
			closeBrowser();
		}
	}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL
	 * or SKIP
	 * 
	 * @throws Exception
	 */
	@AfterTest
	public void reportTestResult() throws Exception {
		extent.endTest(test);
		/*
		 * if(status==1) TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()),
		 * "PASS"); else if(status==2) TestUtil.reportDataSetResult(profilexls,
		 * "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()),
		 * "SKIP");
		 */
	}
}
