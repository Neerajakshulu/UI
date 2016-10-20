package rcc;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class RCC010 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(),rowData.getTestcaseDescription()).assignCategory("RCC");
	}

	/**
	 * Method for wait TR Login Screen
	 * 
	 * @throws Exception, When TR Login screen not displayed
	 */
	@Test
	public void verifyingInvitations() throws Exception {

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
			String title= this.getClass().getSimpleName()+"_Group_"+ "_" + getCurrentTimeStamp();
			String desc= this.getClass().getSimpleName()+"_Group_"+ RandomStringUtils.randomAlphanumeric(100);
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("USERNAME010","USERPASSWORD010");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).clickOnCreateNewGroupButton();
			pf.getGroupsListPage(ob).createGroup(title,desc);
			boolean result=pf.getGroupDetailsPage(ob).inviteMembers("Jyothi Sree");
			if(result)
		    test.log(LogStatus.PASS,"Invitation has been send to the Neon user");
			else
		    test.log(LogStatus.FAIL,"Sending Invitation is failed due to user does not exist");
			pf.getLoginTRInstance(ob).logOutApp();
		
			loginAs("INVITEUSER01","INVITEUSERPWD");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			waitForAjax(ob);
			result=pf.getGroupInvitationPage(ob).verifyingInvitations(title);
		    if(result)
			    test.log(LogStatus.PASS,"Member has recieved the invitation from group owner");
				else
			    test.log(LogStatus.FAIL,"Member has not recieved the invitation from group owner");
		    
		      pf.getLoginTRInstance(ob).logOutApp();
		   
         	    loginAs("USERNAME010","USERPASSWORD010");
		    pf.getGroupsPage(ob).clickOnGroupsTab();
		    pf.getGroupsListPage(ob).clickOnGroupTitle(title);
		    pf.getGroupDetailsPage(ob).clickOnInviteOthersButton();
		    pf.getGroupDetailsPage(ob).cancelPendingInvitations("Jyothi Sree");
		    test.log(LogStatus.PASS,"Cancelation button is clicked");
		    waitForElementTobeVisible(ob,By.cssSelector("button[class='wui-modal__close-btn']"),30);
		    
		    
		    
		    
		    
		    
		    
		    
		    
            
			
			
			
			

		} catch (Throwable t) {
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
		/*
		 * if(status==1) TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
