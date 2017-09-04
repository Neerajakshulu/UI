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
import util.BrowserWaits;
import util.ExtentManager;
import util.OnePObjectMap;

/**
 * Class to Verify that "Select an organization where this author has published." text is mentioned on top of org
 * dropdown
 * 
 * @author UC225218
 *
 */
public class WAT42 extends TestBase {

	static int status = 1;
	static String wos_title = "Web of Science: Author search";
	static String Org_drpdwn_text = "Select an organization where this author has published.";

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent Reports
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
	 * Method for login into WAT application using Steam ID
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
	 * Method to Verify that "Select an organization where this author has published." text is mentioned on top of org
	 * dropdown
	 * 
	 * @param LastName, FirstName, CountryName, OrgName
	 * @throws Exception, When Something unexpected
	 */
	@Test(dependsOnMethods = {"testLoginWATApp"})
	@Parameters({"LastName", "CountryName"})
	public void testCountryDropdownStaticText(String LastName,
			String CountryName) throws Exception {
		try {
			// Verify whether control is in Author Search page
			Assert.assertEquals(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(), wos_title,
					"Control is not in WOS Author Search page");
			test.log(LogStatus.INFO, "Control is in WOS Author Search page");

			// Search for an author cluster with only Last name
			test.log(LogStatus.INFO, "Entering author name... ");
			pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");
			BrowserWaits.waitTime(2);
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH)
					.isEnabled()) {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);

				List<WebElement> ele = pf.getBrowserActionInstance(ob)
						.getElements(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
				if (ele.size() != 0) {
					pf.getSearchAuthClusterPage(ob).selectCountryofAuthor(CountryName, test);
					pf.getBrowserWaitsInstance(ob)
							.waitUntilElementIsClickable(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
					Assert.assertEquals(
							pf.getBrowserActionInstance(ob)
									.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_PAGE_ORG_DROPDOWN_TEXT_XPATH).getText(),
							Org_drpdwn_text);
				} else {
					test.log(LogStatus.INFO,
							"Country name selection is not required as the searched user resulted in less than 50 clusters... ");
				}

			} else {
				throw new Exception("FIND button not clicked");
			}
			test.log(LogStatus.PASS, "Text above org dropdown matches the expectation.");
			pf.getBrowserActionInstance(ob).closeBrowser();
			
		} catch (AssertionError t) {
			logFailureDetails(test, t, "Text above country org dosent match the expectation.", "Text_not_match");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL or SKIP
	 */
	@AfterTest
	public void reportTestResult() {
		pf.getBrowserActionInstance(ob).closeBrowser();
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