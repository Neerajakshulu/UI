package wat;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ExtentManager;

/**
 * Class for Verify that updated(accepted/rejected) recommended paper last names exactly matches one of the last name variants on the author record.
 * @author UC202376
 *
 */
public class WAT186 extends TestBase {

	static int status = 1;

	/**
	 * Method to displaying JIRA ID's for test case in specified path of Extent Reports
	 * 
	 * @throws Exception, When Something unexpected
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
	 * @throws Exception, When WAT Login is not done
	 */
	@Test
	@Parameters({"username", "password"})
	public void testLoginWATApp(String username,
			String password) throws Exception {

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
	 * Method for Validate updated accepted Recommend paper last name match with author record last name 
	 * 
	 * @param lastName
	 * @throws Exception, When Something unexpected
	 */
	@Test(dependsOnMethods = {"testLoginWATApp"})
	@Parameters({"lastName","authorName"})
	public void validateActdrRecmPaperAuthrosMoreLink(String lastName,String authorName) throws Exception {

		try {
			test.log(LogStatus.INFO, "Search an Author using last name");
			pf.getSearchAuthClusterPage(ob).SearchAuthorCluster(lastName,test);
			pf.getSearchAuthClusterResultsPage(ob).waitForauthorClusterSearchResults(test);
			pf.getSearchAuthClusterResultsPage(ob).selectAuthorFromSearchResults(authorName,test);
			test.log(LogStatus.INFO,
					"Verify updated(accepted/rejected) recommended paper last names exactly matches one of the last name variants on the author record");
			pf.getAuthorRecordPage(ob).getintoCuration(test, "AcceptRecommendation");
			BrowserWaits.waitTime(4);
			pf.getAuthorRecordPage(ob).recommendPapersAuthorName();
			test.log(LogStatus.PASS, "Updated recommended paper last name matched last name variants of author record last name");
			pf.getBrowserActionInstance(ob).closeBrowser();
		} catch (Throwable t) {
			logFailureDetails(test, t, "Updated recommended paper last name not matched last name variants of author record last name","updated_recommend_paper_lastname_author_record_fail");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL or SKIP
	 */
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(profilexls, "Test Cases", TestUtil.getRowNum(profilexls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(profilexls,
		 * "Test Cases", TestUtil.getRowNum(profilexls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases", TestUtil.getRowNum(profilexls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */

	}

}
