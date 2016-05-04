package search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;

public class Search80 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('B'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(
						var,
						"Verify that following options get displayed in SORT BY drop down in ALL search results page:a)Relevanceb)Times Citedc)Date")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB80() throws Exception {

		boolean suiteRunmode = testUtil.isSuiteRunnable(suiteXls, "Search");
		boolean testRunmode = testUtil.isTestCaseRunnable(searchxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			openBrowser();
			clearCookies();
			maximizeWindow();

			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			new PageFactory().getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);

			// Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("bio");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);

			ob.findElement(By.id("single-button")).click();
			waitForElementTobeClickable(ob, By.xpath("//a[contains(@ng-click,'vm.sortElements')]"), 120);

			List<WebElement> mylist = ob.findElements(By.xpath("//a[contains(@ng-click,'vm.sortElements')]"));
			// System.out.println(mylist.size());
			//
			// for(int i=0;i<mylist.size();i++){
			//
			// System.out.println(mylist.get(i).getText());
			// }

			boolean cond1 = mylist.get(0).getText().equals("Relevance");
			boolean cond2 = mylist.get(1).getText().equals("Times Cited");
			boolean cond3 = mylist.get(2).getText().equals("Date");

			boolean master_cond = cond1 && cond2 && cond3;
			// System.out.println(master_cond);

			try {

				Assert.assertTrue(master_cond);
				test.log(LogStatus.PASS,
						"Correct sorting options present in SORT BY drop down in ALL search results page");// extent
																											// reports
			}

			catch (Throwable t) {

				test.log(LogStatus.FAIL,
						"Correct sorting options not present in SORT BY drop down in ALL search results page");// extent
																												// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_correct_sorting_options_not_present_in_SORT_BY_drop_down_in_ALL_search_results_page")));// screenshot

			}

			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		// if(status==1)
		// testUtil.reportDataSetResult(searchxls, "Test Cases",
		// testUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "PASS");
		// else if(status==2)
		// testUtil.reportDataSetResult(searchxls, "Test Cases",
		// testUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "FAIL");
		// else
		// testUtil.reportDataSetResult(searchxls, "Test Cases",
		// testUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "SKIP");

	}

}
