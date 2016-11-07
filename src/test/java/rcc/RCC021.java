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

public class RCC021 extends TestBase {

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
	public void testGroupCreation() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");
		String gdDocDesc=RandomStringUtils.randomAlphanumeric(250);
		String gdDoctitle=RandomStringUtils.randomAlphanumeric(30);
		String title = null;
		try {
			String modalLabel = "Remove attached file";
			String modalInfoText = "Are you sure you wish to remove this attached file?";
			title = this.getClass().getSimpleName() + "_Group_" + "_" + getCurrentTimeStamp();
			String desc = this.getClass().getSimpleName() + "_Group_" + RandomStringUtils.randomAlphanumeric(100);
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("USERNAME1", "PASSWORD1");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).clickOnCreateNewGroupButton();
			pf.getGroupsListPage(ob).createGroup(title, desc);
			test.log(LogStatus.INFO, "Group is created successfully: " + title);
			pf.getGroupDetailsPage(ob).clickPostsTab();

			pf.getHFPageInstance(ob).searchForText("test");
			pf.getSearchResultsPageInstance(ob).clickOnPostTab();
			String recordTitle = pf.getSearchResultsPageInstance(ob).getPostsTitle();
			pf.getSearchResultsPageInstance(ob).addDocumentToGroup(title);
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).switchToGroupTab();
			
			pf.getGroupsListPage(ob).clickOnGroupTitle(title);
			pf.getGroupDetailsPage(ob).clickPostsTab();

			pf.getGroupDetailsPage(ob).clickOnAttachFileForRecord(recordTitle, "post");
			pf.getGroupDetailsPage(ob).signInToGoogle("kavyahr31", "leela@123");
			pf.getGroupDetailsPage(ob).selectGDdoc("doc12.docx");
			pf.getGroupDetailsPage(ob).updateItemLevelGoogleDoc(recordTitle, "post", "doc12.docx", gdDoctitle, gdDocDesc);
			
			try {
				Assert.assertEquals(pf.getGroupDetailsPage(ob).getItemLevelGoogleDocDesc(recordTitle, "post", gdDoctitle),gdDocDesc);
				test.log(LogStatus.PASS,
						"GD doc desc and title updated correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc desc and title is not updated correctly for post",
						"_GD_title_dec_not_updated");

			}
			
			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).validateTimeStamp(pf.getGroupDetailsPage(ob).getItemLevelGoogleDocTimestamp(recordTitle, "post", gdDoctitle)));
				test.log(LogStatus.PASS,
						"GD doc timestamp is displayed correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc timestamp is not displayed correctly for post",
						"_GD_title_dec_not_updated");

			}

			pf.getGroupDetailsPage(ob).clickOnOpenInGoogleDriveLinkItemLevel(recordTitle, "post", gdDoctitle);
			try {
				pf.getGroupDetailsPage(ob).validateGDUrl();
				test.log(LogStatus.PASS,
						"GD doc is opened correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc is not opened correctly for post",
						"_GD_title_dec_not_updated");

			}
			

			pf.getGroupDetailsPage(ob).clickOnInviteOthersButton();
			pf.getGroupDetailsPage(ob).inviteMembers("testing Reddy");
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();

			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("USERNAME16", "PASSWORD16");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupInvitationPage(ob).acceptInvitation(title);
			pf.getGroupDetailsPage(ob).clickPostsTab();

			try {
				Assert.assertEquals(pf.getGroupDetailsPage(ob).getItemLevelGoogleDocDesc(recordTitle, "post", gdDoctitle),gdDocDesc);
				test.log(LogStatus.PASS,
						"GD doc desc and title updated correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc desc and title is not updated correctly for post",
						"_GD_title_dec_not_updated");

			}
			
			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).validateTimeStamp(pf.getGroupDetailsPage(ob).getItemLevelGoogleDocTimestamp(recordTitle, "post", gdDoctitle)));
				test.log(LogStatus.PASS,
						"GD doc timestamp is displayed correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc timestamp is not displayed correctly for post",
						"_GD_title_dec_not_updated");

			}

			pf.getGroupDetailsPage(ob).clickOnOpenInGoogleDriveLinkItemLevel(recordTitle, "post", gdDoctitle);
			try {
				pf.getGroupDetailsPage(ob).validateGDUrl();
				test.log(LogStatus.PASS,
						"GD doc is opened correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc is not opened correctly for post",
						"_GD_title_dec_not_updated");

			}
			
			pf.getGroupDetailsPage(ob).clickOnItemLevelRemoveGoogleDoc(recordTitle, "post", gdDoctitle);
			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).verifyConfirmationModalContents(modalLabel, modalInfoText,
						"Remove"));
				test.log(LogStatus.PASS, "Gd doc remove confirmation modal validation success");

			} catch (Throwable t) {
				logFailureDetails(test, t, "Gd doc remove confirmation modal validation Failed",
						"_Gd_doc_remove_confirmation_validation_failed");
			}
			pf.getGroupDetailsPage(ob).clickOnCancelButtonINConfirmationModal();

			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).isItemLevelGDRecordPresent(recordTitle, "post", gdDoctitle));
				test.log(LogStatus.PASS, "Gd doc is not removed when user cancels remove GD doc action");
			} catch (Throwable t) {
				logFailureDetails(test, t, "GD doc is removed when user cancels remove GD doc action",
						"Group_remove_cancel_failed");
			}
			pf.getGroupDetailsPage(ob).clickOnItemLevelRemoveGoogleDoc(recordTitle, "post", gdDoctitle);
			pf.getGroupDetailsPage(ob).clickOnCloseButtonINConfirmationModal();

			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).isItemLevelGDRecordPresent(recordTitle, "post", gdDoctitle));
				test.log(LogStatus.PASS, "Gd doc is not removed when user cancels remove GD doc action");
			}catch (Throwable t) {
				logFailureDetails(test, t, "Gd doc is removed when user cancels remove GD doc action",
						"Group_remove_close_failed");
			}
			pf.getGroupDetailsPage(ob).clickOnItemLevelRemoveGoogleDoc(recordTitle, "post", gdDoctitle);
			pf.getGroupDetailsPage(ob).clickOnSubmitButtonINConfirmationModal();
			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).isItemLevelGDRecordPresent(recordTitle, "post", gdDoctitle));
				test.log(LogStatus.PASS, "Gd doc is removed when user submits remove GD doc action");
			}catch (Throwable t) {
				logFailureDetails(test, t, "Gd doc is not removed when user submits remove Gd doc action",
						"Group_remove_submit_failed");
			}
			String gdOld=gdDoctitle;
			gdDoctitle=RandomStringUtils.randomAlphanumeric(30);
			pf.getGroupDetailsPage(ob).clickOnAttachFileForRecord(recordTitle, "post");
			pf.getGroupDetailsPage(ob).signInToGoogle("kavyahr31", "leela@123");
			pf.getGroupDetailsPage(ob).selectGDdoc("doc12.docx");
			pf.getGroupDetailsPage(ob).updateItemLevelGoogleDoc(recordTitle, "post", "doc12.docx", gdDoctitle, gdDocDesc);
			
			try {
				Assert.assertEquals(pf.getGroupDetailsPage(ob).getItemLevelGoogleDocDesc(recordTitle, "post", gdDoctitle),gdDocDesc);
				test.log(LogStatus.PASS,
						"GD doc desc and title updated correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc desc and title is not updated correctly for post",
						"_GD_title_dec_not_updated");

			}
			
			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).validateTimeStamp(pf.getGroupDetailsPage(ob).getItemLevelGoogleDocTimestamp(recordTitle, "post", gdDoctitle)));
				test.log(LogStatus.PASS,
						"GD doc timestamp is displayed correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc timestamp is not displayed correctly for post",
						"_GD_title_dec_not_updated");

			}

			pf.getGroupDetailsPage(ob).clickOnOpenInGoogleDriveLinkItemLevel(recordTitle, "post", gdDoctitle);
			try {
				pf.getGroupDetailsPage(ob).validateGDUrl();
				test.log(LogStatus.PASS,
						"GD doc is opened correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc is not opened correctly for post",
						"_GD_title_dec_not_updated");

			}
			
		
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();

			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("USERNAME1", "PASSWORD1");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsListPage(ob).clickOnGroupTitle(title);
			pf.getGroupDetailsPage(ob).clickPostsTab();
			
			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).isItemLevelGDRecordPresent(recordTitle, "post", gdOld));
				test.log(LogStatus.PASS, "Removed Gd doc is not available in the list for posts");
			}catch (Throwable t) {
				logFailureDetails(test, t, "Removed Gd doc is available in the list for posts",
						"Group_remove_submit_failed");
			}
			try {
				Assert.assertEquals(pf.getGroupDetailsPage(ob).getItemLevelGoogleDocDesc(recordTitle, "post", gdDoctitle),gdDocDesc);
				test.log(LogStatus.PASS,
						"GD doc desc and title updated correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc desc and title is not updated correctly for post",
						"_GD_title_dec_not_updated");

			}
			
			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).validateTimeStamp(pf.getGroupDetailsPage(ob).getItemLevelGoogleDocTimestamp(recordTitle, "post", gdDoctitle)));
				test.log(LogStatus.PASS,
						"GD doc timestamp is displayed correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc timestamp is not displayed correctly for post",
						"_GD_title_dec_not_updated");

			}

			pf.getGroupDetailsPage(ob).clickOnOpenInGoogleDriveLinkItemLevel(recordTitle, "post", gdDoctitle);
			try {
				pf.getGroupDetailsPage(ob).validateGDUrl();
				test.log(LogStatus.PASS,
						"GD doc is opened correctly for post");
			} catch (Throwable t) {
				logFailureDetails(test, t,
						"GD doc is not opened correctly for post",
						"_GD_title_dec_not_updated");

			}
			
			pf.getGroupDetailsPage(ob).clickOnItemLevelRemoveGoogleDoc(recordTitle, "post", gdDoctitle);
			pf.getGroupDetailsPage(ob).clickOnSubmitButtonINConfirmationModal();
			try {
				Assert.assertTrue(pf.getGroupDetailsPage(ob).isItemLevelGDRecordPresent(recordTitle, "post", gdDoctitle));
				test.log(LogStatus.PASS, "Gd doc is removed when user submits remove GD doc action");
			}catch (Throwable t) {
				logFailureDetails(test, t, "Gd doc is not removed when user submits remove Gd doc action",
						"Group_remove_submit_failed");
			}
	
			
			pf.getUtility(ob).deleteGroup(title);
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
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

		} 
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");
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
