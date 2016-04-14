package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class Search38 extends TestBase {

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
		test = extent.startTest(var,
				"Verify that only articles get displayed when user chooses ARTICLES as content type").assignCategory(
				"Search suite");

	}

	@Test
	public void testcaseB8() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "B Suite");
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

			String search_query = "biology";

			openBrowser();
			clearCookies();
			maximizeWindow();

			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
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
			Thread.sleep(4000);

			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0, 250);");
			Thread.sleep(4000);

			// Finding out time cited values for the displayed articles in
			// article result page
			List<WebElement> resultTypeList = ob.findElements(By.xpath("//div[@class='row']/div[1]/h5"));

			boolean flag = false;

			for (WebElement e : resultTypeList) {

				if (!e.getText().equalsIgnoreCase("Article")) {
					flag = true;
					break;
				}
			}

			// Check if there are different results rather than article
			if (flag) {

				test.log(LogStatus.FAIL, "All the results are not article");// extent
				// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "all_the_results_are_not_article")));// screenshot

			} else {
				test.log(LogStatus.PASS, "Article results are displayed");
			}

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
