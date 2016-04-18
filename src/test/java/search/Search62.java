package search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.openqa.selenium.By;
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

public class Search62 extends TestBase {

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
						"Verify that 10 people suggestions get displayed in the search type ahead when user searches using PEOPLE option in the search drop down and that the searched keyword is present in all the suggestions")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB62() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Search");
		boolean testRunmode = TestUtil.isTestCaseRunnable(searchxls, this.getClass().getSimpleName());
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
			waitForElementTobeVisible(ob,
					By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']"), 30);

			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']"))
					.click();
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'People')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'People')]")).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("j");
			Thread.sleep(1000);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("o");
			Thread.sleep(1000);

			WebElement myE = ob.findElement(By.xpath(OR.getProperty("peopleTile")));
			String text = myE.getText();

			String[] arr = text.split("\n");

			ArrayList<String> al = new ArrayList<String>();
			for (int i = 1; i < arr.length; i++) {

				al.add(arr[i]);
			}

			for (int i = 0; i < al.size(); i++) {

				System.out.println(al.get(i));
			}

			if (!compareNumbers(10, al.size())) {

				test.log(LogStatus.FAIL, "More or less than 10 people suggestions are getting displayed");// extent
																											// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_more_or_less_than_ten_people_suggestions_getting_displayed")));// screenshot
			}

			int count = 0;
			for (int i = 0; i < al.size(); i++) {

				if (!al.get(i).toLowerCase().contains("j"))
					count++;
			}

			if (!compareNumbers(0, count)) {

				test.log(LogStatus.FAIL, "People suggestion does not contain the typed keyword");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_people_suggestion_does_not_contain_typed_keyword")));// screenshot
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
