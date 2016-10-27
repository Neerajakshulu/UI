package rcc;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;


public class RCC011 extends TestBase{
	
	static int status = 1;

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent Reports
	 * 
	 * @throws Exception, When Something unexpected
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
	 * @throws Exception, When TR Login screen not displayed
	 */
	@Test
	public void testRCC011() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");

		try {
			String title = this.getClass().getSimpleName() + "_Group_" + "_" + getCurrentTimeStamp();
			String desc = this.getClass().getSimpleName() + "_Group_" + RandomStringUtils.randomAlphanumeric(100);
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("GROUPUSERNAME010", "GROUPUSERPASSWORD010");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).clickOnCreateNewGroupButton();
			pf.getGroupsListPage(ob).createGroup(title, desc);
			test.log(LogStatus.PASS, "Group is created by the owner ");
			boolean result = pf.getGroupDetailsPage(ob).inviteMembers("Jyothi Sree");
			if (result)
				test.log(LogStatus.PASS, "Invitation has been send to the Neon user");
			else
				test.log(LogStatus.FAIL, "Sending Invitation is failed due to user does not exist");
			 result = pf.getGroupDetailsPage(ob).inviteMembers("Test User");
			if (result)
				test.log(LogStatus.PASS, "Invitation has been send to the another Neon user");
			else
				test.log(LogStatus.FAIL, "Sending Invitation is failed due to user does not exist for second user");
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("INVITEUSER01", "INVITEUSERPWD");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			waitForAjax(ob);
			//pf.getGroupsPage(ob).switchToInvitationTab();
			try{
				pf.getGroupInvitationPage(ob).acceptInvitation(title);
				test.log(LogStatus.PASS,"User1 has accepted the invitation");
			}
			
			catch(Exception e){
				test.log(LogStatus.FAIL, "Invitation is not present in the invitation tab of user1");
				ErrorUtil.addVerificationFailure(e);
			}
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("GPUSERNAME11","GPUSERPASSWORD11");
			test.log(LogStatus.PASS,"User2 has login with valid credentials");
			try{
				pf.getGroupInvitationPage(ob).acceptInvitation(title);
				test.log(LogStatus.PASS,"User2 has accepted the invitation");
				int count=pf.getGroupDetailsPage(ob).getMembersCounts();
				if(count==2)
					test.log(LogStatus.PASS,"Members count in incremented ");
			}
			
			catch(Exception e){
				test.log(LogStatus.FAIL, "Invitation is not present in the invitation tab of user2");
				ErrorUtil.addVerificationFailure(e);
			}
			
			

}
		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something went wrong");
			// print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.FAIL, errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(
					LogStatus.FAIL,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_login_not_done")));// screenshot
			closeBrowser();
		}
	}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL or SKIP
	 * 
	 * @throws Exception
	 */
	@AfterTest
	public void reportTestResult() throws Exception {
		extent.endTest(test);
		
	}

	}
	
