package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Search87 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);

	String var=xlRead2(returnExcelPath('B'),this.getClass().getSimpleName(),1);
		test = extent
				.startTest(var,
						"Verify that record view page of a patent gets displayed when user clicks on article title in ALL search results page")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB87() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteBxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
//			ob.navigate().to(host);
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			new PageFactory().getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);
			
			String patent="Projection exposure apparatus and method which uses multiple diffraction gratings in order to produce a solid state device with fine patterns";
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(patent);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			ob.findElement(By.xpath(OR.getProperty("tab_patents_result"))).click();
			waitForAjax(ob);
			
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))).click();
//			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_patent_record_view_css")), 120);
//			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_patent_record_view_watch_share_css")), 120);
//			BrowserWaits.waitTime(4);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_patent_record_view_css")), 30);

			Thread.sleep(5000);
			String patentRVTitle=ob.findElement(By.cssSelector(OR.getProperty("tr_patent_record_view_css"))).getText();
			String patentRVTitleWatchLabel=ob.findElement(By.cssSelector(OR.getProperty("tr_patent_record_view_watch_share_css"))).getText();
			String patentRVShareLabel=ob.findElements(By.cssSelector(OR.getProperty("tr_patent_record_view_watch_share_css"))).get(1).getText();
			
			System.out.println("title-->"+patentRVTitle);
			System.out.println("watch-->"+patentRVTitleWatchLabel);
			System.out.println("share-->"+patentRVShareLabel);
			boolean patentRVStatus = StringUtils.containsIgnoreCase(patentRVTitle, patent)
					&& StringUtils.containsIgnoreCase(patentRVTitleWatchLabel, "watch")
					&& StringUtils.containsIgnoreCase(patentRVShareLabel, "Share");
			
			if(!patentRVStatus)
				throw new Exception("Page is not Navigating to Patent Record View Page");
			
			closeBrowser();

		} 
		
		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_patent_metadata_failed")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}


	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

//		if (status == 1)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
//		else if (status == 2)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
//		else
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}


}
