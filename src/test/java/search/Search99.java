package search;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
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

public class Search99 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription())
				.assignCategory("Search suite");
	}

	@Test
	public void testcaseB99() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
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
			ob.navigate().to(host);
			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			Thread.sleep(3000);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 50);

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("John");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 50);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			BrowserWaits.waitTime(4);
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_PEOPLE_CSS.toString()), 50);
			Thread.sleep(2000);
			ob.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_PEOPLE_CSS.toString())).click();
			waitForAjax(ob);
			// checking for Default sort option
			// String defaultSort = ob.findElement(By.xpath(OR.getProperty("tr_search_people_sortBy_dropdown_xpath")))
			// .getText();
			String defaultSort = ob.findElement(By.xpath("//button[@id='single-button']")).getText();

			// checking for different options available in sort
			waitForElementTobeVisible(ob, By.xpath("//button[@id='single-button']"), 35);
			ob.findElement(By.xpath("//button[@id='single-button']")).click();
			Thread.sleep(3000);
			String text = ob
					.findElement(By.xpath("//ul[@class='dropdown-menu search-sort-dropdown__menu' and @role='menu']"))
					.getText();

			if (defaultSort.equals("Sort by: Relevance")) {
				test.log(LogStatus.PASS, "Relevance is the default sort in people results page");

			} else {
				status = 2;
				test.log(LogStatus.FAIL, "Relevance is not the default sort in people results page as expected");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_wrong_happened")));// screenshot
			}

			if (text.contains("Relevance") && text.contains("Registration Date")) {
				test.log(LogStatus.PASS, "Relevance and Registration Date options are present in the sort button");
			} else {
				status = 2;
				test.log(LogStatus.FAIL, "PRelevance and Registration Date options are not displayed as expected");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_wrong_happened")));// screenshot
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
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
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
