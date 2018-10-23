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
 * Class to "Verify that for a Combined author The left-to-right order of the
 * metrics in the Author Metadata area of the Author Record should be: h-index
 * Sum of Times Cited Citing Articles"
 * 
 * @author UC225218
 *
 */
public class WAT143 extends TestBase {

	static int status = 1;
	int i;
	String text = "H-INDEX";

	/**
	 * Method to displaying JIRA ID's for test case in specified path of Extent
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
	 * Method for login into WAT application using TR ID
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

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");

		try {
			openBrowser();
			clearCookies();
			maximizeWindow();
			test.log(LogStatus.INFO, "Logging into WAT Applicaton using valid WAT Entitled user ");
			ob.navigate().to(host + CONFIG.getProperty("appendWATAppUrl"));
			pf.getLoginTRInstance(ob).loginToWAT(username, password, test);
			pf.getSearchAuthClusterPage(ob).validateAuthorSearchPage(test);

		} catch (Throwable t) {
			logFailureDetails(test, t, "Login Fail", "login_fail");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	/**
	 * Method for Search Author cluster Results
	 * 
	 * @param lastName,countryName,orgName
	 * @throws Exception,
	 *             When Something unexpected
	 */
	@Test(dependsOnMethods = { "testLoginWATApp" })
	@Parameters({ "lastName" })
	public void searchAuthorCluster(String lastName) throws Exception {
		try {
			test.log(LogStatus.INFO, "Search an Author using last name");
			pf.getSearchAuthClusterPage(ob).SearchAuthorCluster(lastName, test);
			pf.getSearchAuthClusterResultsPage(ob).waitForauthorClusterSearchResults(test);
			test.log(LogStatus.INFO, "Combine two Authors");
			pf.getSearchAuthClusterResultsPage(ob).combineAuthorCard(test);
			test.log(LogStatus.INFO, "Verify System must display default avatar for combined Author");
			pf.getAuthorRecordPage(ob).defaultAvatar(test);
			test.log(LogStatus.INFO, "Combined Author Search Results are displayed");
		} catch (Throwable t) {
			logFailureDetails(test, t, "Combined Author Search Results are not displayed", "Search_Fail");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	/**
	 * Method to "Verify that for a Combined author The left-to-right order of
	 * the metrics in the Author Metadata area of the Author Record should be:
	 * h-index Sum of Times Cited Citing Articles"
	 * 
	 * @throws Exception,
	 *             When Something unexpected
	 */
	@Test(dependsOnMethods = { "searchAuthorCluster" })
	public void testMetricsForCombinedAuthor() throws Exception {
		try {
			test.log(LogStatus.INFO, "Checking wheather H-Index metrics is displayed");
			Assert.assertTrue(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_METADATA_HINDEX_XPATH).isDisplayed(),
					"H-Index metrics is not displayed");
			Assert.assertEquals(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_METADATA_HINDEX_XPATH).getText().trim(), text.trim(),
					"H-Index metrics text is not matching");
			test.log(LogStatus.INFO, "H-Index metrics is displayed and text is present as expected");
			test.log(LogStatus.INFO, "Checking wheather Sum of times cited metrics is displayed");
			Assert.assertTrue(
					pf.getBrowserActionInstance(ob)
							.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_METADATA_SUMOFTIMESCITED_XPATH).isDisplayed(),
					"Sum of times cited metrics is not displayed");
			Assert.assertTrue(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_METADATA_SUMOFTIMESCITED_XPATH).getText()
					.contains("SUM OF TIMES CITED"), "Sum of Times Cited metrics text is not matching");
			test.log(LogStatus.INFO, "Sum of Times Cited metrics is displayed and text is present as expected");
			test.log(LogStatus.INFO, "Checking wheather Citing articles metrics is displayed");
			Assert.assertTrue(
					pf.getBrowserActionInstance(ob)
							.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_METADATA_CITINGARTICLES_XPATH).isDisplayed(),
					"Citing articles metrics is not displayed");
			Assert.assertTrue(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_METADATA_CITINGARTICLES_XPATH).getText()
					.contains("CITING ARTICLES"), "Citing Articles metrics text is not matching");
			test.log(LogStatus.INFO, "Citing Articles metrics is displayed and text is present as expected");
			test.log(LogStatus.PASS, "All 3 metrics are displayed one after the other as expected");
			pf.getBrowserActionInstance(ob).closeBrowser();
		} catch (Throwable t) {
			logFailureDetails(test, t, "Metrics in Combined author page is not displayed as expected",
					"Metric_display_issue");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}
	}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL
	 * or SKIP
	 */
	@AfterTest
	public void reportTestResult() {
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
