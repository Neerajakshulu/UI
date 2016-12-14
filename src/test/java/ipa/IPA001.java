package ipa;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ExtentManager;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class IPA001 extends TestBase {

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
	public void saveTechnologySearchData() throws Exception {

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
			String searchtype="technology";
			String searchTerm="oracle";
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host + CONFIG.getProperty("appendIPAAppUrl"));
			pf.getIpaPage(ob).loginToIPA("ipauser1@tr.com", "Neon@123");
		    pf.getSearchPageInstance(ob).SearchTermEnter(searchtype,searchTerm);
		     pf.getSearchPageInstance(ob).exploreSearch();
		      waitForAjax(ob);
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

	
	
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
	}
}
