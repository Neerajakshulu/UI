package search;

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
import util.ExtentManager;

public class Search106 extends TestBase {

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
	public void testcaseB106() throws Exception {

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

			// login using TR credentials
			login();

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 120);
			// waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);

			String post = "posts";
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(post);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			pf.getSearchResultsPageInstance(ob).clickOnPostTab();
			waitForElementTobeClickable(ob, By.xpath("//button[@id='single-button']"), 4);
			ob.findElement(By.xpath("//button[@id='single-button']")).click();
			BrowserWaits.waitTime(5);
			waitForElementTobeVisible(ob,
					By.xpath(
							"//div[@class='search-sort-dropdown dropdown open']/ul[@class='dropdown-menu search-sort-dropdown__menu']"),
					4);
			Thread.sleep(2000);
			List<WebElement> postDropdownmenus = ob
					.findElement(By.cssSelector("div[class='search-sort-dropdown dropdown open']"))
					.findElements(By.tagName("li"));
			String postExpectedDropdown = "Create Date (Newest)|Create Date (Oldest)|Relevance";
			List<String> postDropdowndata = new ArrayList<String>();
			for (WebElement postDropdownmenu : postDropdownmenus) {
				postDropdowndata.add(postDropdownmenu.getText().trim());
			}

			String dropDownInputs[] = postExpectedDropdown.split("\\|");
			List<String> postExpectedDropdowndata = Arrays.asList(dropDownInputs);

			if (!postDropdowndata.containsAll(postExpectedDropdowndata)) {
				throw new Exception("Post dropdown menu options not displayed");
			}

			// System.out.println("post first dropdown-->"+postDropdowndata.get(0));
			if (!postDropdowndata.get(0).equalsIgnoreCase("Create Date (Newest)")) {
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
