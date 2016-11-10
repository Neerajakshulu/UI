package rcc;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class RCC0003 extends TestBase {

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
			loginAs("RCCTESTUSER007", "RCCTESTUSERPWD007");

			// OPQA-1577
			pf.getGroupsPage(ob).clickOnGroupsTab();

			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_DESC_MSG_TEXT_XPATH);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_DESC_MSG_TEXT_XPATH);
			BrowserWaits.waitTime(2);
			pf.getGroupDetailsPage(ob).clickOnEditButton();
			String titleUpdate = RandomStringUtils.randomAlphanumeric(50);

			pf.getGroupDetailsPage(ob).updateGroupTitle(titleUpdate);
			pf.getGroupsListPage(ob).clickOnCancelGroupButton(test);

			pf.getGroupsListPage(ob).ValidateTitleCancel(titleUpdate, test);

			BrowserWaits.waitTime(2);

			// OPQA-1571
			pf.getGroupsPage(ob).CilckGroupTab();
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_DESC_MSG_TEXT_XPATH);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_DESC_MSG_TEXT_XPATH);
			pf.getGroupDetailsPage(ob).clickOnEditButton();
			pf.getGroupDetailsPage(ob).updateGroupDescription(titleUpdate);
			pf.getGroupDetailsPage(ob).clickOnSaveButton();
			test.log(LogStatus.PASS,
					"user is able to edit group with group name of 50 characters and without any description.");
			BrowserWaits.waitTime(2);
			// OPQA-1573
			String desc50 = RandomStringUtils.randomAlphanumeric(51);

			pf.getGroupsPage(ob).CilckGroupTab();
			pf.getGroupsPage(ob).clickOnGroupsTabFirstRecord();
			pf.getGroupDetailsPage(ob).clickOnEditButton();
			pf.getGroupDetailsPage(ob).updateGroupDescription(desc50);
			pf.getGroupDetailsPage(ob).clickOnSaveButton();
			BrowserWaits.waitTime(2);
			pf.getGroupsPage(ob).CilckGroupTab();
			pf.getGroupsPage(ob).clickOnGroupsTabFirstRecord();
			String actualLength = ob.findElement(By.xpath(OnePObjectMap.RCC_DESC_MSG_TEXT_XPATH.toString())).getText();
			System.out.println(actualLength.length());
			pf.getGroupsListPage(ob).ValidateEditDescMoreThan50(actualLength.length(), desc50.length(), test);
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
