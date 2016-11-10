package rcc;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class RCC0002 extends TestBase {

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
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("RCCTESTUSER006", "RCCTESTUSERPWD006");
			// OPQA-1570
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).clickOnGroupsTabFirstRecord();

			BrowserWaits.waitTime(2);
			pf.getGroupDetailsPage(ob).clickOnEditButton();
			String titleUpdate = RandomStringUtils.randomAlphanumeric(2);

			pf.getGroupDetailsPage(ob).updateGroupTitle(titleUpdate);

			pf.getGroupDetailsPage(ob).clickOnSaveButton();
			BrowserWaits.waitTime(5);
			// OPQA-1576
			pf.getGroupDetailsPage(ob).ValidateViewMode(test);
			pf.getGroupsListPage(ob).ValidateTitle(titleUpdate, test);
			pf.getGroupsPage(ob).clickOnGroupsTab();
			BrowserWaits.waitTime(5);
			pf.getGroupsListPage(ob).ValidateUpdatedTitle(titleUpdate, test);

			BrowserWaits.waitTime(2);
			// OPQA-1572

			String title1Char = RandomStringUtils.randomAlphanumeric(1);
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).clickOnGroupsTabFirstRecord();

			BrowserWaits.waitTime(2);
			pf.getGroupDetailsPage(ob).clickOnEditButton();

			pf.getGroupDetailsPage(ob).updateGroupTitle(title1Char);

			pf.getGroupsListPage(ob).ValidateSaveButtonDisabled(test);
			BrowserWaits.waitTime(2);
			closeBrowser();
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends ");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something went wrong");
			// print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.FAIL, errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.FAIL, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_login_not_done")));// screenshot
			closeBrowser();
		}
	}

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
