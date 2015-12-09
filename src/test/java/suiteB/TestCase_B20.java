package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B20 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() {

		test = extent
				.startTest(this.getClass().getSimpleName(),
						"To verify that user is able to sort the documents by TIMES CITED field")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB20() throws Exception {
		List<Long> timeCitedCountListBeforeSort = new ArrayList<Long>();
		List<WebElement> timeCitedWEList;
		List<Long> timeCitedCountListAfterSort = new ArrayList<Long>();
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

			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			Thread.sleep(8000);
			login();
			Thread.sleep(15000);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("cat dog mammal");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 40);
			((JavascriptExecutor)ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
			waitForAjax(ob);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_results_sortby_button_css")),20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_sortby_button_css"))).click();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_results_sortby_menu_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_sortby_timescited_css"))).click();
			waitForAjax(ob);
			timeCitedWEList = ob.findElements(By.xpath(OR.getProperty("tr_timecited_count_search_results_xpath")));

			for (WebElement element : timeCitedWEList) {
				timeCitedCountListBeforeSort.add(Long.parseLong(element.getText().trim()));
			}

			timeCitedCountListAfterSort.addAll(timeCitedCountListBeforeSort);
			Collections.sort(timeCitedCountListAfterSort);

			try {
				Assert.assertTrue(timeCitedCountListBeforeSort.size()>0);
				Assert.assertEquals(timeCitedCountListBeforeSort, timeCitedCountListAfterSort);
				test.log(LogStatus.PASS, "User is able to sort the search results");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Sort by functionality is not working as expected");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "sortby_ is_not_ working_ for_ search_ results")));// screenshot
			}

			logout();
			closeBrowser();

		}

		catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something went wrong");// extent reports
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
