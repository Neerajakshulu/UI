package search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Search11 extends TestBase {

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
						"Verify that sorting and filtering are retained when user navigates back to articles search results page from record view page")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB11() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Search");
		boolean testRunmode = TestUtil.isTestCaseRunnable(searchxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			String search_query = "biology";

			openBrowser();
			clearCookies();
			maximizeWindow();

			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			// ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);

			// Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.cssSelector("li[ng-click='vm.updateSearchType(\"ARTICLES\")']"), 30);

			// Clicking on Articles content result set
			ob.findElement(By.cssSelector("li[ng-click='vm.updateSearchType(\"ARTICLES\")']")).click();

			List<WebElement> filterPanelHeadingList;
			WebElement documentTypePanelHeading;
			// Capturing panel heading for filters
			filterPanelHeadingList = ob.findElements(By.cssSelector("div[class=panel-heading]"));
			documentTypePanelHeading = filterPanelHeadingList.get(0);

			// Expand the document type filter by clicking it again
			documentTypePanelHeading.click();
			List<WebElement> filterValues = ob.findElements(By.xpath(OR.getProperty("filter_checkbox")));
			filterValues.get(0).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("filter_checkbox")), 30);

			// Re-capturing filter values
			filterValues = ob.findElements(By.xpath(OR.getProperty("filter_checkbox")));
			filterValues.get(1).click();
			waitForElementTobeVisible(ob, By.cssSelector("button[class='btn search-sort-btn dropdown-toggle']"), 30);

			// ob.findElement(By.id(OR.getProperty("sortDropdown_button"))).click();
			ob.findElement(By.cssSelector("button[class='btn search-sort-btn dropdown-toggle']")).click();
			waitForElementTobeVisible(ob, By.cssSelector("a[event-action='citingsrcslocalcount:desc']"), 30);
			// ob.findElement(By.linkText(OR.getProperty("sortDropdown_timesCitedOption_link"))).click();
			ob.findElement(By.cssSelector("a[event-action='citingsrcslocalcount:desc']")).click();

			List<WebElement> searchResults = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
			ArrayList<String> al1 = new ArrayList<String>();
			for (int i = 0; i < searchResults.size(); i++) {

				al1.add(searchResults.get(i).getText());

			}
			jsClick(ob, searchResults.get(5));

			JavascriptExecutor js = (JavascriptExecutor) ob;
			js.executeScript("window.history.back();");
			List<WebElement> searchResults2 = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
			ArrayList<String> al2 = new ArrayList<String>();
			for (int i = 0; i < searchResults2.size(); i++) {

				al2.add(searchResults2.get(i).getText());

			}

			try {
				Assert.assertTrue(al1.equals(al2));
				test.log(
						LogStatus.PASS,
						"Search page maintains the sorting order state when user navigates back to articles search results page from record view page");
			} catch (Throwable t) {

				test.log(LogStatus.PASS,
						"Search does not maintain state when user navigates back to articles search results page from record view page");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				// test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
				// this.getClass().getSimpleName() + "_incorrect_filtered_search_results_getting_displayed")));//
				// screenshot

			}

			// String option =
			// ob.findElement(By.id(OR.getProperty("sortDropdown_button"))).getText();
			String option = ob.findElement(By.cssSelector("button[class='btn search-sort-btn dropdown-toggle']"))
					.getText();

			if (!compareStrings("Times Cited", option)) {

				test.log(LogStatus.PASS, "Incorrect sorting option getting displayed");// extent
																						// reports
				status = 2;// excel
				// test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
				// this.getClass().getSimpleName() + "_incorrect_sorting_option_getting_displayed")));// screenshot

			}

			// Finding the filter values
			filterValues = ob.findElements(By.xpath(OR.getProperty("filter_checkbox")));

			boolean filtering_condition = filterValues.get(0).isSelected() && filterValues.get(1).isSelected();

			try {
				Assert.assertTrue(filtering_condition);
				test.log(LogStatus.PASS,
						"Filters are retained when user navigates back to articles search results page from record view page");
			} catch (Throwable t) {

				test.log(LogStatus.PASS,
						"Filters are not retained when user navigates back to articles search results page from record view page");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				// test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
				// .getSimpleName()
				// +
				// "_filters_not_retained_when_user_navigates_back_to_articles_search_results_page_from_record_view_page")));//
				// screenshot

			}

			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.PASS, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			// test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			// captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		// if (status == 1)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "PASS");
		// else if (status == 2)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "FAIL");
		// else
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "SKIP");

	}

}
