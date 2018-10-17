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

//THIS TEST IS COMMENTED AS OF NOW AS THE SEARCH RESULTS LINK and recommendation block IS NOT AVAILABLE IN AUTHOR RECORD PAGE 

/*
*//**
 * Class to Verify that If user opens curation on the same author after
 * cancelling curation in previous attempt(via Suggest Updates or by doing
 * accept/reject on a recommendation)the Bulk Remove panel should have no values
 * checked
 * 
 * @author UC225218
 *
 *//*
public class WAT222 extends TestBase {

	static int status = 1;
	String classValue = "wui-checkbox__hidden ng-pristine ng-untouched ng-valid";
	String[] recommendationArray;

	*//**
	 * Method to displaying JIRA ID's for test case in specified path of Extent
	 * Reports
	 * 
	 * @throws Exception,
	 *             When Something unexpected
	 *//*
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("WAT");
	}

	*//**
	 * Method for login into WAT application using TR ID
	 * 
	 * @throws Exception,
	 *             When WAT Login is not done
	 *//*
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
			test.log(LogStatus.INFO, "Logging into WAT Applicaton through WoS Application.");
			pf.getWatPageInstance(ob).loginToWOSWAT(test);

		} catch (Throwable t) {
			logFailureDetails(test, t, "Login Fail", "login_fail");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	*//**
	 * Method for Search Author cluster Results
	 * 
	 * @param lastName,countryName,orgName
	 * @throws Exception,
	 *             When Something unexpected
	 *//*
	@Test(dependsOnMethods = { "testLoginWATApp" })
	@Parameters({ "LastName", "CountryName1", "CountryName2", "OrgName1", "OrgName2" })
	public void searchAuthorCluster(String LastName, String CountryName1, String CountryName2, String OrgName1,
			String OrgName2) throws Exception {
		try {
			test.log(LogStatus.INFO, "Entering author name... ");
			pf.getSearchAuthClusterPage(ob).searchAuthorClusterOnlyLastName(LastName, CountryName1, CountryName2,
					OrgName1, OrgName2, test);
			test.log(LogStatus.INFO, "Author Search Results are displayed");
		} catch (Throwable t) {
			logFailureDetails(test, t, "Author Search Results are not displayed", "Search_Fail");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	*//**
	 * Method to Verify that after Canceling a curation, recommendations that
	 * were accepted/rejected during that curation should remain eligible to be
	 * recommended to this user during the current user session
	 * 
	 * @param LastName,
	 *            FirstName, CountryName, OrgName
	 * @throws Exception,
	 *             When Something unexpected
	 *//*
	@Test(dependsOnMethods = { "searchAuthorCluster" })
	public void testReenterCurationSameAuthor() throws Exception {
		try {
			test.log(LogStatus.INFO, "Clicking first author card");
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULT_FIRST_CARD_XPATH).click();
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_RECORD_DEFAULT_AVATAR_CSS);
			test.log(LogStatus.INFO,
					"Checking whether undo option for removed Publication is available before getting into curation mode in Author record page");
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PUBLICATION_UNDO_LINK_XPATH)
					.isDisplayed())
				throw new Exception(
						"Undo option for removed Publication is available before getting into curation mode in Author record page");
			test.log(LogStatus.PASS,
					"Undo option for removed Publication is not available before getting into curation mode in Author record page");
			test.log(LogStatus.INFO, "Getting into curation mode by clicking Suggest Update button");
			pf.getAuthorRecordPage(ob).getintoCuration(test, "SuggestUpdate");
			test.log(LogStatus.INFO, "Rejecting first Recommendation");
			pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_REJECT_FIRST_RECOMMENDATION_XPATH).click();
			waitForPageLoad(ob);
			test.log(LogStatus.INFO, "Accepting Next Recommendation");
			pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ACCEPT_FIRST_RECOMMENDATION_XPATH).click();
			waitForPageLoad(ob);
			test.log(LogStatus.INFO, "Clicking cancel curation");
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_CANCEL_UPDATE_BTN_XPATH)
					.click();
			test.log(LogStatus.INFO, "Going back to search results page by clicking search results link");
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH).click();
			waitForPageLoad(ob);
			test.log(LogStatus.INFO, "Clicking Same author card which was selected before");
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULT_FIRST_CARD_XPATH).click();
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_RECORD_DEFAULT_AVATAR_CSS);
			waitForPageLoad(ob);
			test.log(LogStatus.INFO, "Getting into curation mode again by clicking Suggest Update button");
			pf.getAuthorRecordPage(ob).getintoCuration(test, "SuggestUpdate");
			
			test.log(LogStatus.INFO,
					"Checking whether previously checked bulk removal selection is still available in Author record page");
			Assert.assertEquals(
					pf.getBrowserActionInstance(ob)
							.getElement(
									OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_IN_CURATION_FILTER_AUTHOR_BULK_FIRST_CHECKBOX_UNCHECKED_XPATH)
							.getAttribute("class"),
					classValue,
					"The Publications List dosent contains all publications or the Bulk Remove panels dosent have all values un-checked after returning from a cancelled curation");
			List<WebElement> all_checkboxes = pf.getBrowserActionInstance(ob).getElements(
					OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_IN_CURATION_FILTER_AUTHOR_BULK_ALL_CHECKBOX_UNCHECKED_XPATH);
			for (WebElement checkBox : all_checkboxes) {
				if (!checkBox.getAttribute("class").equals(classValue)) {
					throw new Exception(
							"Previously checked bulk removal selection is still available in Author record page");
				}
			}
			
			test.log(LogStatus.PASS,
					"Previously Accepted/Rejected recommendation is available after a cancelled curation");
			pf.getBrowserActionInstance(ob).closeBrowser();
		} catch (Throwable t) {
			logFailureDetails(test, t,
					"Previously Accepted/Rejected recommendation is not available after a cancelled curation",
					"Curation_issue");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	*//**
	 * updating Extent Report with test case status whether it is PASS or FAIL
	 * or SKIP
	 *//*
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		
		 * if (status == 1) TestUtil.reportDataSetResult(profilexls,
		 * "Test Cases", TestUtil.getRowNum(profilexls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls, this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls, this.getClass().getSimpleName()),
		 * "SKIP");
		 

	}

}
*/