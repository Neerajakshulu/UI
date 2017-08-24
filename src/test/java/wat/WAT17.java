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
 * Class for testing - Typeahead works during multiple alternative name search.
 * 
 * @author UC225218
 *
 */
public class WAT17 extends TestBase {

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
	 * Method for testing Typeahead functionality during multiple alternative
	 * name search.
	 * 
	 * @param LastName
	 * @throws Exception
	 * 
	 */
	@Test(dependsOnMethods = { "testLoginWATApp" })
	@Parameters({ "LastName", "FirstName" })
	public void testTypeaheadMultipleAltName(String LastName, String FirstName) throws Exception {
		String Lastname2 = "UPADHYA";
		String Lastname3 = "BHAT";
		String Firstname2 = "KAPIL DEV";
		String Firstname3 = "SANDESH";
		try {
			// Verify whether control is in Author Search page
			Assert.assertEquals(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(), wos_title,
					"Control is not in WOS Author Search page");
			test.log(LogStatus.INFO, "Control is in WOS Author Search page");

			// Last and first name 1st time
			test.log(LogStatus.INFO, "Entering author Last name... ");
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
			pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
			pf.getSearchAuthClusterPage(ob).enterAuthorFirstName(FirstName, test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH);

			// Last and first name 2nd time
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME2_XPATH);
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME2_XPATH).clear();
			pf.getSearchAuthClusterPage(ob).enterAuthorLastName(OnePObjectMap.WAT_AUTHOR_LASTNAME2_XPATH, Lastname2,
					test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME2_XPATH);
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME2_XPATH).clear();
			pf.getSearchAuthClusterPage(ob).enterAuthorFirstName(OnePObjectMap.WAT_AUTHOR_FIRSTNAME2_XPATH, Firstname2,
					test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH);

			// Last and first name 3rd time
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME3_XPATH);
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME3_XPATH).clear();
			pf.getSearchAuthClusterPage(ob).enterAuthorLastName(OnePObjectMap.WAT_AUTHOR_LASTNAME3_XPATH, Lastname3,
					test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME3_XPATH);
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME3_XPATH).clear();
			pf.getSearchAuthClusterPage(ob).enterAuthorFirstName(OnePObjectMap.WAT_AUTHOR_FIRSTNAME3_XPATH, Firstname3,
					test);

			//// Only First name last time
			pf.getSearchAuthClusterPage(ob).enterAuthorFirstName(FirstName, test);
			test.log(LogStatus.PASS, "Firstname Typeahead displayed during multiple Alternate name search");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Firstname Typeahead not displayed during multiple Alternate name search");
			logFailureDetails(test, t, "Firstname Typeahead not displayed during multiple Alternate name search",
					"Alt_name_typeahead_fail");
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
