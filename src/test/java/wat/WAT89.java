package wat;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ExtentManager;
import util.OnePObjectMap;

/**
 * Class for Verify that user should able to access feedback survey page using 'Tell us what you think' link within the informational text on the author Search page
 * 
 * @author UC225218
 *
 */
public class WAT89 extends TestBase {

	static int status = 1;
	static String wos_title = "Web of Science: Author search";

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("WAT");
	}

	/**
	 * Method for login into WAT application using Steam ID
	 * 
	 * @throws Exception,
	 *             When WAT Login is not done
	 */
	@Test
	@Parameters({ "username", "password" })
	public void testLoginWATApp(String username, String password) throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->" + this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts.... ");

		try {
			openBrowser();
			clearCookies();
			maximizeWindow();
			test.log(LogStatus.INFO, "Login to WAT Applicaton using valid WAT Entitled user ");
			ob.navigate().to(host + CONFIG.getProperty("appendWATAppUrl"));
			pf.getLoginTRInstance(ob).loginToWAT(username, password, test);

		} catch (Throwable t) {
			logFailureDetails(test, t, "Login Fail", "login_fail");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	/**
	 * Method to Verify that user should able to access feedback survey page using 'Tell us what you think' link within the informational text on the author Search page
	 * 
	 * @throws Exception,
	 *             When Something unexpected
	 */
	@Test(dependsOnMethods = { "testLoginWATApp" })
	public void testFeedbackLink()
			throws Exception {
		try {
			// Verify whether control is in Author Search page
			Assert.assertEquals(
					pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
					wos_title, "Control is not in WOS Author Search page");
			test.log(LogStatus.INFO, "Control is in WOS Author Search page");
			Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_FEEDBACK_AUTHOR_SEARCH_PAGE_XPATH).isDisplayed(),"Feedback Link not available");
			test.log(LogStatus.PASS,"Feed back link available");
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_FEEDBACK_AUTHOR_SEARCH_PAGE_XPATH).click();
			pf.getBrowserWaitsInstance(ob).waitForPageLoad(ob);
			pf.getBrowserActionInstance(ob).switchToNewWindow(ob);
			Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_FEEDBACK_PAGE_TITLE_XPATH).isDisplayed(),"Navigation to feedback page failed");
			test.log(LogStatus.PASS,"Feed back link available and navigated to feedback page successfully from Author search page");
		} catch (Throwable t) {
			logFailureDetails(test, t, "Feedback link in Author search page is not available", "Feedbacklink_Fail");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}
	}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL
	 * or SKIP
	 */
	@AfterTest
	public void reportTestResult() {
		pf.getBrowserActionInstance(ob).closeBrowser();
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(profilexls,
		 * "Test Cases", TestUtil.getRowNum(profilexls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls, this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls, this.getClass().getSimpleName()),
		 * "SKIP");
		 */

	}
}