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
 * Class to Verify that when error msg "No Result" is displayed, Add alternate
 * name should be disabled
 * 
 * 
 * 
 * @author UC225218
 *
 */
public class WAT47 extends TestBase {

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
	 * Method to Verify that when error msg "No Result" is displayed, Add
	 * alternate name should be disabled. *
	 * 
	 * @throws Exception,
	 *             When Something unexpected
	 * 
	 */
	@Test(dependsOnMethods = { "testLoginWATApp" })
	public void testAltNameLinkGreyedWhenErrors() throws Exception {
		String InvalidText = "uefuwieflief";
		try {
			// Verify whether control is in Author Search page
			Assert.assertEquals(pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(), wos_title,
					"Control is not in WOS Author Search page");
			test.log(LogStatus.INFO, "Control is in WOS Author Search page");
			test.log(LogStatus.INFO, "Entering invalid/ No result author last name... ");
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, InvalidText);
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
			test.log(LogStatus.INFO, "Trying to click find button... ");
			if (ele.size() != 0) {
				test.log(LogStatus.PASS,
						"Error message displayed when there is no typeahead suggestions are available for the user entered last name");
			} else {
				test.log(LogStatus.FAIL,
						"Error message not displayed when there is no typeahead suggestions are available for the user entered last name");
				throw new Exception(
						"Error message not displayed when there is no typeahead suggestions are available for the user entered last name");
			}
			if (!pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH))
					.isEnabled()) {
				test.log(LogStatus.PASS, "Add alternate name button not enabled when there is an error.");
			} else {
				test.log(LogStatus.FAIL, "Add alternate name button is enabled even when there is an error displayed.");
				throw new Exception("Add alternate name button is enabled even when there is an error displayed.");
			}
		} catch (Exception e) {
			logFailureDetails(test, e, "Add alternate name button functionality has issue", "Add_alt_btn_fail");
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