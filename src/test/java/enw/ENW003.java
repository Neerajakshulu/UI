package enw;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

public class ENW003 extends TestBase {
	
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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");

	}

	@Test
	public void testcaseENW003() throws Exception {
		
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			String statuCode = deleteUserAccounts(CONFIG.getProperty("sfbLIusername"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		
		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			
			ob.get(host);
			
			String expectedSuccessMessage="Sent To EndNote";
			  
			   pf.getLoginTRInstance(ob).enterTRCredentials(CONFIG.getProperty("sfbLIusername"),CONFIG.getProperty("sfbpwrd"));
			   pf.getLoginTRInstance(ob).clickLogin();
			   BrowserWaits.waitTime(6);
	         	test.log(LogStatus.PASS, "User is able to log in with steam credentials");
			   pf.getLoginTRInstance(ob).logOutApp();
		
			   pf.getLoginTRInstance(ob).loginWithFBCredentials(CONFIG.getProperty("sfbLIusername"),CONFIG.getProperty("sfbpwrd"));
			   
			   pf.getLoginTRInstance(ob).clickNotnowButtonLinkingModal();
			   BrowserWaits.waitTime(5);
			   pf.getLoginTRInstance(ob).closeOnBoardingModal();
			   
			   pf.getAuthoringInstance(ob).searchArticle(CONFIG.getProperty("article"));
			   
			   pf.getSearchResultsPageInstance(ob).clickSendToEndnoteSearchPage();
			   
             ob.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_LINKIINGMODAl_CLOSE_BUTTON_CSS.toString())).click();
			   BrowserWaits.waitTime(5);
              pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
			   
			   pf.getSearchResultsPageInstance(ob).clickSendToEndnoteSearchPage();
			   new Actions(ob).moveByOffset(200, 200).click().build().perform();
			   
			   BrowserWaits.waitTime(8);
			   pf.getSearchResultsPageInstance(ob).clickSendToEndnoteSearchPage();
			   pf.getSearchResultsPageInstance(ob).linkSteamAcctWhileSendToEndnoteSearchPage();
			   
		
			   pf.getLoginTRInstance(ob).logOutApp();
			
			   try
				{
				Assert.assertEquals(expectedSuccessMessage,pf.getSearchResultsPageInstance(ob).ValidateSendToEndnoteSearchPage());
				test.log(LogStatus.PASS,
						" Record sent successfully from Search Results Page after linking with steam account");
				}
				
				catch (Throwable t) {
					t.printStackTrace();
					test.log(LogStatus.FAIL,
							" Record is not sent to Endnote from Search Results Page after linking with steam account");// extent
																																// reports
					status = 2;// excel
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot

				}
	
			closeBrowser();
			
			
			
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS");
		 * else if(status==2) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL");
		 * else TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}
}