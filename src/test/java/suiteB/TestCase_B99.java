package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.TestUtil;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class TestCase_B99 extends TestBase {
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
						"Verify that following options get displayed in SORT BY drop down in PEOPLE search results page: a)Relevance b)Registration Date and search results are"+
						"sorted by Relevance by Default.")
						.assignCategory("Suite B");

	}

	@Test
	public void testcaseB99() throws Exception {

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
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			ob.navigate().to(host);
			Thread.sleep(3000);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 50);
			// Searching for people
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("S");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_tab_xpath")),8);
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_tab_xpath"))).click();
			
			//checking for Default sort option
			String defaultSort=ob.findElement(By.xpath(OR.getProperty("tr_search_people_sortBy_dropdown_xpath"))).getText();
			System.out.println(defaultSort);
			
			//checking for different options available in sort
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_sortBy_dropdown_xpath"))).click();
			String text=ob.findElement(By.xpath("//ul[@class='dropdown-menu' and @role='menu']")).getText();
			System.out.println(text);
			
			if (defaultSort.equals("Relevance")) {
				test.log(LogStatus.PASS, "Relevance is the default sort in people results page");
				
			} else {
				status = 2;
				test.log(LogStatus.FAIL, "Relevance is not the default sort in people results page as expected");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_something_wrong_happened")));// screenshot
			}
			
			if(text.contains("Relevance") && text.contains("Registration Date")){
				test.log(LogStatus.PASS, "Relevance and Registration Date options are present in the sort button");
			}else{
				status = 2;
				test.log(LogStatus.FAIL, "PRelevance and Registration Date options are not displayed as expected");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_something_wrong_happened")));// screenshot
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
