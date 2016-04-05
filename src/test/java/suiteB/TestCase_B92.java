package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class TestCase_B92 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);

		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent
				.startTest(var,
						"Verify that following fields get displayed correctly for a person in ALL search results page: a)Person name b)Person details")
						.assignCategory("Suite B");

	}

	@Test
	public void testcaseB92() throws Exception {

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
			//below user email and password
			//email:3ewhun+6c4irdugxznv8@sharklasers.com
			//password:Tr@12345
			String userName="STQABLR";
			String description="Software Tester, Thomson Reuters, India";

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
//			ob.navigate().to(host);
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			Thread.sleep(3000);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 180);
			// Searching for patents
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(userName);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath")),180);
			String profileName = ob.findElement(By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath"))).getText();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_profile_description_xpath")),50);
			String profileDescription=ob.findElement(By.xpath(OR.getProperty("tr_search_people_profile_description_xpath"))).getText();
			
			if (profileName.equals(userName) && profileDescription.equals(description)) {
				test.log(LogStatus.PASS, "Person name and description are displayed as expected");
			} else {
				status = 2;
				test.log(LogStatus.FAIL, "Person name and description are not displayed as expected");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_Person_name_and_description_not_displayed")));// screenshot
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

//		if (status == 1)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
//		else if (status == 2)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
//		else
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}

}
