package rcc;

import java.io.PrintWriter;
import java.io.StringWriter;

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

public class RCC090 extends TestBase {

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
	public void testGroupCreation90() throws Exception {

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
			loginAs("USERNAMERCC90", "PASSWORDRCC90");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).clickOnCreateNewGroupButton();
			try {
				pf.getGroupsListPage(ob).addCoverPhoto();
				test.log(LogStatus.PASS, "Group owner is able to add cover photo to a group");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "user is not able to add cover photo to a group");
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.FAIL, errors.toString());
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.FAIL, "Snapshot below: " + test
						.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_unable_to_add_photo")));

			}
			BrowserWaits.waitTime(5);
			pf.getGroupsListPage(ob).createGroup("Project Neon001");
			BrowserWaits.waitTime(5);
			pf.getGroupsPage(ob).clickOnGroupsTab();
			try {
				Assert.assertEquals(pf.getGroupsListPage(ob).isCoverPhotoDisplayed(), true);
				test.log(LogStatus.PASS, "User is able to see added cover photo for the newly created group");
				pf.getGroupsListPage(ob).clickOnGroupTitle("Project Neon001");
				pf.getGroupDetailsPage(ob).clickOnDeleteButton();
				pf.getGroupDetailsPage(ob).clickOnDeleteButtonInConfirmationMoadl();

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User is not able to see added cover photo for the newly created group");
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.FAIL, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_unable_to_view_added_photo")));
			}

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
			closeBrowser();
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends ");
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

	}
}
