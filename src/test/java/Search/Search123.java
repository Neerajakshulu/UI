package Search;

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

public class Search123 extends TestBase {

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
				.startTest(var,
						"Verify that filtering is retained when user navigates back to PEOPLE search results page from profile page")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB123() throws Exception {

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

			openBrowser();
			clearCookies();
			maximizeWindow();

			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 50);
			// Searching for people
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("Sachin");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);

			waitForAjax(ob);
			List<WebElement> content_type_tiles = ob.findElements(By
					.xpath("//*[contains(@class,'content-type-selector ng-scope')]"));
			content_type_tiles.get(3).click();
			waitForAjax(ob);

			List<WebElement> filterPanelHeadingList;
			WebElement documentTypePanelHeading;
			// Capturing panel heading for filters
			filterPanelHeadingList = ob.findElements(By.cssSelector("div[class=panel-heading]"));
			documentTypePanelHeading = filterPanelHeadingList.get(0);

			// Expand the document type filter by clicking it again
			documentTypePanelHeading.click();
			Thread.sleep(4000);
			List<WebElement> filterValues = ob.findElements(By.xpath(OR.getProperty("filter_checkbox")));
			filterValues.get(0).click();
			Thread.sleep(8000);

			List<WebElement> searchResults = ob.findElements(By.xpath(OR
					.getProperty("tr_search_people_profilename_link_xpath")));
			System.out.println("Search Results-->" + searchResults.size());
			ArrayList<String> al1 = new ArrayList<String>();
			for (int i = 0; i < searchResults.size(); i++) {
				al1.add(searchResults.get(i).getText());
			}

			System.out.println("al1-->" + al1.size());
			jsClick(ob, searchResults.get(0));
			Thread.sleep(5000);

			JavascriptExecutor js = (JavascriptExecutor) ob;
			js.executeScript("window.history.back();");
			Thread.sleep(5000);
			List<WebElement> searchResults2 = ob.findElements(By.xpath(OR
					.getProperty("tr_search_people_profilename_link_xpath")));
			System.out.println("Search Results-->" + searchResults.size());
			ArrayList<String> al2 = new ArrayList<String>();
			for (int i = 0; i < searchResults2.size(); i++) {

				al2.add(searchResults2.get(i).getText());

			}

			System.out.println("al2--->" + al2.size());

			try {
				Assert.assertTrue(al1.equals(al2));
				test.log(
						LogStatus.PASS,
						"Correct filtered search results getting displayed when user navigates back to people search results page from record view page");
			} catch (Throwable t) {

				test.log(
						LogStatus.PASS,
						"Incorrect filtered search results getting displayed when user navigates back to people search results page from record view page");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				// test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
				// this.getClass().getSimpleName() + "_incorrect_filtered_search_results_getting_displayed")));//
				// screenshot

			}

			filterValues = ob.findElements(By.xpath(OR.getProperty("filter_checkbox")));

			boolean filtering_condition = filterValues.get(0).isSelected();

			try {
				Assert.assertTrue(filtering_condition);
				test.log(LogStatus.PASS,
						"Filters are retained when user navigates back to people search results page from record view page");
			} catch (Throwable t) {

				test.log(LogStatus.PASS,
						"Filters are not retained when user navigates back to people search results page from record view page");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				// test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
				// .getSimpleName()
				// +
				// "_filters_not_retained_when_user_navigates_back_to_people_search_results_page_from_record_view_page")));//
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
