package enw;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class ENW001 extends TestBase {
	
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
	public void testcaseENW001() throws Exception {
		
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			List<String> list =Arrays.asList(new String[]{"Articles","Patents","Posts"});
			openBrowser();
			maximizeWindow();
			clearCookies();
			
			ob.get(host);
			String expectedSuccessMessage="Sent To EndNote";
			
		//	ob.navigate().to(System.getProperty("host"));
			loginAs("MARKETUSEREMAIL1", "MARKETUSERPASSWORD1");
			
			pf.getAuthoringInstance(ob).searchArticle(CONFIG.getProperty("article"));
			
			for(int i=0; i<list.size();i++)
			{
				String recordType=list.get(i);
				
				if (recordType.equalsIgnoreCase("Articles"))
				{
					pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
					pf.getAuthoringInstance(ob).chooseArticle();
					
					
				}else if(recordType.equalsIgnoreCase("Patents"))
				{
					pf.getSearchResultsPageInstance(ob).clickOnPatentsTab();
					pf.getAuthoringInstance(ob).chooseArticle();
				
				}else
				{
					pf.getSearchResultsPageInstance(ob).clickOnPostTab();
					pf.getSearchResultsPageInstance(ob).clickOnFirstPostTitle();
					
				}
					
			
			
			waitForAjax(ob);
			
		
			try
			{
			Assert.assertEquals(expectedSuccessMessage,pf.getpostRVPageInstance(ob).clickSendToEndnoteRecordViewPage());
			test.log(LogStatus.PASS,
					recordType+" record sent successfully");
			}
			
			catch (Throwable t) {

				test.log(LogStatus.FAIL,
						recordType+	" record is not sent to Endnote");// extent
																															// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);
			}
        
			ob.navigate().back();
			//clearing the record from Endnote
			//pf.getENWReferencePageInstance(ob).clearRecordEndnote();
			
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
