package ipa;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ExtentManager;
import util.OnePObjectMap;

public class IPA111 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);

		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory(
				"IPA");
	}

	@Test
	public void testcaseB10() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			String dtitle = this.getClass().getSimpleName() + "_Save_Title" + "_" + getCurrentTimeStamp();
			String ddesc = this.getClass().getSimpleName() + "_Save_Desc_" + RandomStringUtils.randomAlphanumeric(100);
			String searchtype="company";
			String searchTerm="samsung";
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host + CONFIG.getProperty("appendIPAppUrl"));
			pf.getIpaPage(ob).loginToIPA("ipauser1@tr.com", "Neon@123");
		     SearchTermEnter(searchtype,searchTerm);
		     exploreSearch();
		     pf.getIpaPage(ob).clickOnSaveButton();
		     pf.getIpaPage(ob).SaveDataInfo(dtitle,ddesc);
		     test.log(LogStatus.PASS,"Title and desc has been entered to save data");
		     pf.getIpaPage(ob).clickOnSaveData();
		     test.log(LogStatus.PASS,"Searched data has been saved");
		     BrowserWaits.waitTime(2);
		     pf.getIpaSavedSearchpage(ob).clickOnSavedWork();
		     test.log(LogStatus.PASS,"navigated to saved data page");
		     Assert.assertTrue(pf.getIpaSavedSearchpage(ob).validateSavedDataInfo(dtitle,searchtype));
		     test.log(LogStatus.PASS,"Save data tile search type and title are Matching");
		     
		     pf.getIpaSavedSearchpage(ob).clickOnTitle(dtitle);
		     test.log(LogStatus.PASS,"Explored the saved search");
		    BrowserWaits.waitTime(3);
		     closeBrowser();
			
			

		} catch (Exception e) {
			logFailureDetails(test, "User is not able tpo login", "Screenshot for login");
			closeBrowser();
		}
	}

	public void SearchTermEnter(String searchType,
			String searchTerm) throws Exception {
		boolean switched = false;
		if (searchType.equalsIgnoreCase("company")) {
			if (!ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_COMPANY_LINK_CSS.toString() + ">hr"))
					.getAttribute("class").contains("__active")) {
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.NEON_IPA_COMPANY_LINK_CSS);
				switched = ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_COMPANY_LINK_CSS.toString() + ">hr"))
						.getAttribute("class").contains("__active");
			} else
				switched = true;
		} else {
			if (!ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_TECHNOLOGY_LINK_CSS.toString() + ">hr"))
					.getAttribute("class").contains("__active")) {
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.NEON_IPA_TECHNOLOGY_LINK_CSS);
				switched = ob
						.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_TECHNOLOGY_LINK_CSS.toString() + ">hr"))
						.getAttribute("class").contains("__active");
			} else
				switched = true;
		}
		if (!switched)
			throw new Exception("Desired Seach Type Not selected");

		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_SEARCH_TEXTBOX_CSS, searchTerm);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_SUGGESTION_COINTAINER_CSS);
	}
	
	public void exploreSearch() throws Exception {
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.NEON_IPA_EXPLORE_BUTTON_CSS);

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_DASH_PATENTS_FOUND_CNT_CSS);
		try {
			ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_DASH_PATENTS_FOUND_CNT_CSS.toString()));
		} catch (Exception e) {
			throw new Exception("Explore not working");
		}
	}
	
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
	}
}
