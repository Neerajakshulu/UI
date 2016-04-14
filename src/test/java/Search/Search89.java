package Search;

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

import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

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

		String var = xlRead2(returnExcelPath('B'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verify that DETAILS link is working correctly in record view page of a patent")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB89() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Search");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteBxls, this.getClass().getSimpleName());
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

			// Navigating to the NEON login page
			ob.navigate().to(host);
			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			// Searching for patents
			selectSearchTypeFromDropDown("Patents");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("bio");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);

			// Navigating to record view page
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			Thread.sleep(8000);

			String titleName = "";
			try {

				boolean titlePresent = ob.findElements(By.xpath("//div/h2[@class='record-heading ng-binding']")).size() != 0;
				titleName = ob.findElement(By.xpath("//div/h2[@class='record-heading ng-binding']")).getText();
				if (titlePresent) {
					test.log(LogStatus.PASS, "Title is present in patent record view page");
				} else {
					status = 2;
					test.log(LogStatus.FAIL, "Title is not present in patent record view page");
				}
			} catch (NoSuchElementException e) {
				status = 2;
				test.log(LogStatus.FAIL, "Title is not displayed present in patent record view page");
			}

			try {

				List<WebElement> detailsLink = ob.findElements(By.linkText("Details"));
				// Clicking on the details link
				ob.findElement(By.linkText("Details")).click();
				Thread.sleep(15000);

				if (detailsLink.size() != 0) {

					test.log(LogStatus.PASS, "Details link is present in the record view page");
				} else {
					test.log(LogStatus.FAIL, "Deatils link is displayed multiple times in the record view page");
				}
			} catch (NoSuchElementException e) {

				status = 2;
				test.log(LogStatus.FAIL, "Details link is not displayed");// extent
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

				String titleNameOriginal = ob.findElement(By.xpath("//div[@id='PAT.TIOR0']")).getText();
				if (titleNameOriginal.equalsIgnoreCase(titleName)) {
					test.log(LogStatus.PASS, "Original title name is same as the title displayed in NEON");
				} else {
					status = 2;
					test.log(LogStatus.FAIL, "Original title name is not same as the title displayed in NEON");
				}
			} catch (NoSuchElementException e) {

				status = 2;
				test.log(LogStatus.FAIL, "Details link is not working properly");// extent
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

		// if (status == 1)
		// TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
		// TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
		// else if (status == 2)
		// TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
		// TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
		// else
		// TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
		// TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}

}
