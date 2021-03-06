package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B106 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {

		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent
				.startTest(var,
						"Verify that following options get displayed in SORT BY drop down in POSTS search results page: a)Relevance b)Create Date(Newest) c)Create Date(Oldest)"
						+ "Verify that search results are sorted by CREATE DATE(NEWEST) by default in POSTS search results page")
				.assignCategory("Suite B");	

	}

	@Test
	public void testcaseB106() throws Exception {

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
			BrowserWaits.waitUntilText("Sign in with Project Neon");

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);
			
			String post="sample post";
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(post);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			ob.findElement(By.xpath(OR.getProperty("tab_posts_result"))).click();
			waitForAjax(ob);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_results_item_title_css")), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_results_sortby_button_css")), 120);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_sortby_button_css"))).click();
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_results_sortby_menu_css")), 120);
			List<WebElement> postDropdownmenus=ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_sortby_menu_css"))).findElements(By.tagName("li"));
			String postExpectedDropdown="Create Date (Newest)|Create Date (Oldest)|Relevance";
			List<String> postDropdowndata=new ArrayList<String>();
			for(WebElement postDropdownmenu:postDropdownmenus){
				postDropdowndata.add(postDropdownmenu.getText().trim());
			}
			
			String dropDownInputs[]=postExpectedDropdown.split("\\|");
			List<String> postExpectedDropdowndata=Arrays.asList(dropDownInputs);
			
			if(!postDropdowndata.containsAll(postExpectedDropdowndata)){
				throw new Exception("Post dropdown menu options not displayed");
			}
			
			//System.out.println("post first dropdown-->"+postDropdowndata.get(0));
			if(!postDropdowndata.get(0).equalsIgnoreCase("Create Date (Newest)")){
				throw new Exception("Create Date (Newest) is the by default POST Sorted by search result");
			}
			
			
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
					captureScreenshot(this.getClass().getSimpleName() + "_patent_recordview_failed")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}


	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}


}
