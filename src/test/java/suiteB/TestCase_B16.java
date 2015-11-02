package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
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

public class TestCase_B16 extends TestBase {
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
						"To verify that 10 MORE button is not present in search results page if the total search results is less than or equal to 10")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB16() throws Exception {

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
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 60);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("cat dog purse", Keys.ENTER);
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 40);
			List<WebElement> resultList = ob.findElements(By.xpath(OR.getProperty("tr_search_results_item_xpath")));
			Assert.assertTrue(resultList.size() <= 10);

			if (checkElementIsDisplayed(ob, By.cssSelector(OR.getProperty("tr_search_results_more_button_css")))) {

				test.log(LogStatus.FAIL, "More link is not working as expected");// extent
																					// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "More_link_not_working")));// screenshot

			} else {
				test.log(LogStatus.PASS, "More link is not displayed for results less than 10");
			}
			logout();
			closeBrowser();
		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "Search is not returning less than 10 results.");// extent
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
