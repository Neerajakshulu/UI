package wat;

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
 * Class for Verify that clicking on the text "Name Search" in the error in
 * ORCid Search page takes the user to Name search tab
 * 
 * @author UC225218
 *
 */
public class WAT52 extends TestBase {

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
	 * Method to Verify that clicking on the text "Name Search" in the error in
	 * ORCid Search page takes the user to Name search tab
	 * 
	 * @throws Exception,
	 *             When Something unexpected
	 */
	@Test(dependsOnMethods = { "testLoginWATApp" })
	public void testORCIDToNameSearch() throws Exception {
		String InvalidORCid = "1111-6235-234-2222";
		try {
			// Verify whether control is in Author Search page
			Assert.assertEquals(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(), wos_title,
					"Control is not in WOS Author Search page");
			test.log(LogStatus.INFO, "Control is in WOS Author Search page");

			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ORCID_SEARCH_BTN_XPATH);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_ORCID_LOGO_XPATH);

			Assert.assertTrue(
					pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_LOGO_XPATH).isDisplayed());
			test.log(LogStatus.INFO, "ORCiD logo present");

			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_TEXTBOC_XPATH).sendKeys(InvalidORCid);
			// pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_ORCID_SEARCH_ERROR_TEXT_XPATH);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH);
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_SEARCH_ERROR_TEXT_XPATH)
					.isDisplayed()) {
				test.log(LogStatus.INFO, "Error displayed when the ORCid or format is invalid");
			} else {
				throw new Exception("Error not displayed when the ORCid or format is invalid");
			}

			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ORCID_TO_NAME_SEARCH_LINK_XPATH);
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_NAME_SEARCH_BUTTON_XPATH).isDisplayed()) {
				test.log(LogStatus.PASS, "Successfully navigated to Name search page");
			} else {
				throw new Exception("Didnt navigate to Name search page");
			}

		} catch (Throwable t) {
			logFailureDetails(test, t, "Didnt navigate to Name search page", "ORCid_to_name_search_navigation_issue");
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