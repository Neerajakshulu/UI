package enw;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
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

public class ENW036 extends TestBase {
	
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
	public void testcaseENW002() throws Exception {
		
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
		
			openBrowser();
			maximizeWindow();
			clearCookies();
			
			ob.get(host);
			String expectedGroupAuthors="TON Team";
			
			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("grpAuthUserenw036"),
					LOGIN.getProperty("grpAuthPwdenw036"));
			pf.getLoginTRInstance(ob).clickLogin();
			
			pf.getAuthoringInstance(ob).searchArticle("Dissipation and emission of p-mode in the quiet sun from acoustic imaging with TON data");
			pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
			pf.getAuthoringInstance(ob).chooseArticle();
			BrowserWaits.waitTime(4);
			
			waitForAjax(ob);
			pf.getSearchResultsPageInstance(ob).clickSendToEndnoteSearchPage();
			
			pf.getHFPageInstance(ob).clickOnEndNoteLink();
			BrowserWaits.waitTime(2);
			test.log(LogStatus.PASS, "User navigate to End note");

			try {
				String text = ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()))
						.getText();
				if (text.equalsIgnoreCase("Continue")) {
					ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BrowserWaits.waitTime(4);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RECORD_LINK_XPATH);
		
			try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_SHOWALLFILEDS_LINK_XPATH);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			String actualGroupAuthors=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.ENW_RECORD_AUTHOR_VALUE_CSS).getText();
			try
			{
			Assert.assertEquals(expectedGroupAuthors,actualGroupAuthors);
			test.log(LogStatus.PASS,
					" Group Author for article record displayed correctly");
			}
			
			catch (Throwable t) {

				test.log(LogStatus.FAIL,
						" Group Author for article record is not displayed correctly");// extent
																															// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_GROUPAuthor_not_displayed")));// screenshot
				ErrorUtil.addVerificationFailure(t);
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

	}
	
}
