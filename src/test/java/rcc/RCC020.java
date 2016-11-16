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
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

public class RCC020 extends TestBase {
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
			// login with Owneruser and search for the article
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("RCCTESTUSER020", "RCCTESTUSERPWD020");
			BrowserWaits.waitTime(10);
			pf.getGroupsPage(ob).clickOnGroupsTab();
			int beforecreation = pf.getGroupsPage(ob).getGroupsCount();
			System.out.println(beforecreation);

			//searching the specified search
			pf.getHFPageInstance(ob).searchForText("test");
			pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
			pf.getSearchResultsPageInstance(ob).onlyAddArticleToGroup();
			
			//making sure that before creating the group, group is already not present 
			Assert.assertFalse(pf.getSearchResultsPageInstance(ob).checkgroupNameinResultsPage(groupTitle));
			test.log(LogStatus.PASS,"verified and the group is not present in the list" );
			ob.navigate().refresh();
			
			
			
			
			//creating the group and adding the article
			pf.getHFPageInstance(ob).clickOnHomeLink();
			pf.getHFPageInstance(ob).searchForText("test");
			pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
			pf.getSearchResultsPageInstance(ob).onlyAddArticleToGroup();
			pf.getSearchResultsPageInstance(ob).createGroupInSearchResults(groupTitle);
			pf.getSearchResultsPageInstance(ob).clickoncreateButton();
			test.log(LogStatus.INFO, "Group creation successfull");
			Assert.assertTrue(pf.getSearchResultsPageInstance(ob).checkgroupNameinResultsPage(groupTitle));
			test.log(LogStatus.PASS, "verified the group name ");
			// pf.getSearchResultsPageInstance(ob).verifyGroupCreationIcon();
			
			//verifying the group is present in the group list
			pf.getGroupsPage(ob).clickOnGroupsTab();
			Assert.assertTrue(pf.getGroupsListPage(ob).checkForGroup(groupTitle));
			test.log(LogStatus.PASS, "Group is present in the grouplist");

			int aftercreation = pf.getGroupsPage(ob).getGroupsCount();
			System.out.println(aftercreation);
			Assert.assertEquals(beforecreation + 1, aftercreation);
			test.log(LogStatus.PASS, "Group count is incresed");
			
			
			
			//creating new group in groups page and adding article
			/*pf.getGroupsPage(ob).clickOnCreateNewGroupButton();
			pf.getGroupsListPage(ob).createGroup(groupTitle, desc);
			pf.getHFPageInstance(ob).searchForText("test");
			pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
			pf.getSearchResultsPageInstance(ob).onlyAddArticleToGroup();
			pf.getSearchResultsPageInstance(ob).addArticletoExistingGroup(groupTitle);*/
			
			

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
