package search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
import util.OnePObjectMap;

public class Search89 extends TestBase {

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
	public void testcaseB89() throws Exception {
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
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			// Searching for patents
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("bio");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_PATENTS_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_PATENTS_CSS.toString())).click();
			waitForAjax(ob);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);
			Thread.sleep(2000);

			// Navigating to record view page

			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			BrowserWaits.waitTime(7);

			String titleName = "";
			try {

				boolean titlePresent = ob
						.findElements(By
								.xpath("//div/h2[@class='wui-content-title wui-content-title--ne-publication ng-binding']"))
						.size() != 0;
				titleName = ob
						.findElement(By
								.xpath("//div/h2[@class='wui-content-title wui-content-title--ne-publication ng-binding']"))
						.getText();
				if (titlePresent) {
					test.log(LogStatus.PASS, "Title is present in patent record view page");
				} else {
					status = 2;
					test.log(LogStatus.FAIL, "Title is not present in patent record view page");
				}
			} catch (NoSuchElementException e) {
				status = 2;
				test.log(LogStatus.FAIL, "Title is not displayed present in patent record view page");
				ErrorUtil.addVerificationFailure(e);
			}

			try {
				waitForAjax(ob);
				BrowserWaits.waitTime(3);
				List<WebElement> detailsLink = ob
						.findElements(By.cssSelector("a[class='wui-btn wui-btn--secondary wui-btn--view-in-ti']"));
				// Clicking on the details link
				jsClick(ob,
						ob.findElement(By.cssSelector("a[class='wui-btn wui-btn--secondary wui-btn--view-in-ti']")));
				     waitForAjax(ob);
				if (detailsLink.size() != 0) {

					test.log(LogStatus.PASS, "View in Thomson Innovation link is present in the record view page");
				} else {
					test.log(LogStatus.FAIL,
							"View in Thomson Innovation is displayed multiple times in the record view page");
				}
			} catch (NoSuchElementException e) {

				status = 2;
				test.log(LogStatus.FAIL, "View in Thomson Innovation link is not displayed");// extent
				ErrorUtil.addVerificationFailure(e);
				return;
			}

			try {
				// Switching tab
				List<String> tabs = new ArrayList<String>(ob.getWindowHandles());
				ob.switchTo().window(tabs.get(1));
				boolean titlePresent = ob.findElements(By.xpath("//div[@id='PAT.TIOR0']")).size() == 1;
				if (titlePresent) {

					test.log(LogStatus.PASS, "Original title name is displayed");
				} else {
					status = 2;
					test.log(LogStatus.FAIL, "Original title name is not displayed");
				}
				BrowserWaits.waitTime(7);
				String titleNameOriginal = ob.findElement(By.xpath("//div[@id='PAT.TIOR0']")).getText();
				BrowserWaits.waitTime(4);
				if (titleNameOriginal.equalsIgnoreCase(titleName)) {
					test.log(LogStatus.PASS, "Original title name is same as the title displayed in NEON");
				} else {
					status = 2;
					test.log(LogStatus.FAIL, "Original title name is not same as the title displayed in NEON");
				}
			} catch (NoSuchElementException e) {

				status = 2;
				test.log(LogStatus.FAIL, "View in Thomson Innovation link is not working properly");// extent
				ErrorUtil.addVerificationFailure(e);
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
