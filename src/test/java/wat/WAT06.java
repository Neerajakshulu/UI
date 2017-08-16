package wat;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ExtentManager;
import util.OnePObjectMap;

/**
 * Class for testing Author cluster search functionality with only Last Name
 * 
 * @author UC225218
 *
 */
public class WAT06 extends TestBase {

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
	 * Method to search for an author cluster after successful login into WAT
	 * application
	 * 
	 * @param LastName,
	 *            FirstName, CountryName, OrgName
	 * 
	 */
	@Test(dependsOnMethods = { "testLoginWATApp" })
	@Parameters({ "FirstName" })
	public void testSearchAuthorClusterLastNameTypeahead(String lastname) throws Exception {

		try {
			// Verify whether control is in Author Search page
			Assert.assertEquals(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(), wos_title,
					"Control is not in WOS Author Search page");
			test.log(LogStatus.INFO, "Control is in WOS Author Search page");
			// Search for an author cluster with only Last name
			test.log(LogStatus.INFO, "Entering author name... ");

			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, "U");
			if (pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_XPATH))
					.isDisplayed()) {
				test.log(LogStatus.PASS, "Typeahead displayed for minimum 1 Letter");
			}
			pf.getBrowserActionInstance(ob).closeBrowser();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Typeahead not displayed for minimum 1 Letter");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}
	}
}