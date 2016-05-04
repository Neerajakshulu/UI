package search;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;

public class Search107 extends TestBase {

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
						"Verify that left navigation pane content type is retained when user navigates back to ALL search results page from record view page")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB107() throws Exception {

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

			// Navigating to the NEON login page
			ob.navigate().to(host);
			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("argentina");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			waitForElementTobeVisible(ob, By.xpath("//li[contains(text(),'Patents')]"), 30);

			ob.findElement(By.xpath("//li[contains(text(),'Patents')]")).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			Thread.sleep(5000);

			ob.navigate().back();
			Thread.sleep(5000);
			String text = ob.findElement(By.xpath("//li[@class='content-type-selector ng-scope active']")).getText();
			System.out.println(text);

			try {

				Assert.assertTrue(text.contains("Patents"));
				test.log(
						LogStatus.PASS,
						"Content type in the left navigation pane getting retained correctly when user navigates back to search results page from record view page");// extent
			} catch (Throwable t) {

				test.log(
						LogStatus.FAIL,
						"Content type in the left navigation pane not getting retained when user navigates back to search results page from record view page");// extent
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_content_type_in_left_navigation_pane_not_getting_retained")));// screenshot

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
		// testUtil.reportDataSetResult(searchxls, "Test Cases",
		// testUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "PASS");
		// else if (status == 2)
		// testUtil.reportDataSetResult(searchxls, "Test Cases",
		// testUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "FAIL");
		// else
		// testUtil.reportDataSetResult(searchxls, "Test Cases",
		// testUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "SKIP");

	}

}
