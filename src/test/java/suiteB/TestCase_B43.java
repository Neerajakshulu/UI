package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B43 extends TestBase {
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
						"Verify that user is able to expand and collapse the Categories filter in ARTICLES content type")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB8() throws Exception {

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

			String search_query = "biology";

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			ob.navigate().to(host);
			Thread.sleep(8000);

			// login using TR credentials
			login();
			Thread.sleep(15000);

			// Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);

			// Clicking on Articles content result set
			ob.findElement(By.cssSelector("li[ng-click='vm.updateSearchType(\"ARTICLES\")']")).click();

			// Check the filter is collapsed by default
			collapseFilter();
			Thread.sleep(2000);

			// Check if the filter expanded
			expandFilter();
			Thread.sleep(2000);

			// Check if filter is collapsible
			collapseFilter();
			test.log(LogStatus.PASS, "Categories filter is collapsible");

			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	private void expandFilter() {
		List<WebElement> filterPanelHeadingList;
		List<WebElement> filterPanelBodyList;
		WebElement documentTypePanelBody;
		WebElement documentTypePanelHeading;
		// Capturing panel heading after expanding document type filter
		filterPanelHeadingList = ob.findElements(By.cssSelector("div[class=panel-heading]"));
		documentTypePanelHeading = filterPanelHeadingList.get(2);
		WebElement upArrow = documentTypePanelHeading
				.findElement(By.cssSelector("i[class='webui-icon pull-right droparrow webui-icon-arrow-up']"));

		if (upArrow != null) {
			test.log(LogStatus.PASS, "Up arrow is visible for Categories filter");
		}

		filterPanelBodyList = ob.findElements(By.cssSelector("div[class='panel-collapse in']"));
		documentTypePanelBody = filterPanelBodyList.get(0);

		if (documentTypePanelBody.isDisplayed()) {
			test.log(LogStatus.PASS, "Categories filter values are displayed");
		}

		// Collapse the document type filter by clicking it again
		documentTypePanelHeading.click();

	}

	private void collapseFilter() {
		// Finding out the types filer in refine panel
		List<WebElement> filterPanelHeadingList = ob.findElements(By.cssSelector("div[class=panel-heading]"));
		WebElement documentTypePanelHeading = filterPanelHeadingList.get(2);
		WebElement downArrow = documentTypePanelHeading
				.findElement(By.cssSelector("i[class='webui-icon pull-right droparrow webui-icon-arrow-down']"));

		if (downArrow != null) {
			test.log(LogStatus.PASS, "Down arrow is visible for Categories filter");
		}
		List<WebElement> filterPanelBodyList = ob.findElements(By.cssSelector("div[class='panel-collapse collapse']"));
		WebElement documentTypePanelBody = filterPanelBodyList.get(2);

		if (!documentTypePanelBody.isDisplayed()) {
			test.log(LogStatus.PASS, "Categories filter values are not displayed");
		}
		// Expanding the document type filter by clicking it
		documentTypePanelHeading.click();
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
