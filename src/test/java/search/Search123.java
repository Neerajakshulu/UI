package search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Search123 extends TestBase {

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
				.startTest(var,
						"Verify that filtering is retained when user navigates back to PEOPLE search results page from profile page")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB123() throws Exception {

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

			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 50);
			// Searching for people
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("amneet");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);

			ob.findElement(By.xpath("//li[@class='content-type-selector ng-scope' and contains(text(),'People')]")).click();
			Thread.sleep(3000);

			ob.findElement(By.xpath("//span[contains(text(),'Institutions')]")).click();
			Thread.sleep(2000);
			
			boolean condition11;
			
			if(!host.contains("stable")){
			ob.findElement(By.xpath("(//input[@type='checkbox'])[2]")).click();
			Thread.sleep(3000);
			
			ob.findElement(By.xpath("//a[@class='ng-binding ng-scope']")).click();
			Thread.sleep(3000);
			
			ob.navigate().back();
			Thread.sleep(6000);
			
			condition11=ob.findElement(By.xpath("(//input[@type='checkbox'])[2]")).isSelected();
			System.out.println(condition11);
			}
			
			else{
				
				
				ob.findElement(By.xpath("//input[@type='checkbox']")).click();
				Thread.sleep(3000);
				
				ob.findElement(By.xpath("//a[@class='ng-binding ng-scope']")).click();
				Thread.sleep(3000);
				
				ob.navigate().back();
				Thread.sleep(6000);
				
				condition11=ob.findElement(By.xpath("//input[@type='checkbox']")).isSelected();
				System.out.println(condition11);
				
			}
			
			
			
			try{
				
				Assert.assertTrue(condition11);
			}
			
			catch(Throwable t){
				
				test.log(LogStatus.FAIL, "Filtering not retained when user navigates back to PEOPLE search results page from profile page");// extent
				// reports
				
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
				captureScreenshot(this.getClass().getSimpleName() + "_filtering_not_retained")));// screenshot
				
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
