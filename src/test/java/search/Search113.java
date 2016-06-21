package search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Search113 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest113() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('B'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(var,
						"Verify that MORE and LESS links are working correctly in INVENTOR filter in PATENTS search results page")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB() throws Exception {
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
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("biology");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_PATENTS_CSS.toString()), 30);
			Thread.sleep(2000);

			ob.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_PATENTS_CSS.toString())).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);
			Thread.sleep(3000);

			List<WebElement> filter_list=ob.findElements(By.xpath("//div[@class='panel-heading']"));
//			System.out.println(filter_list.size());
			
			filter_list.get(0).click();
			Thread.sleep(2000);
			
	//	System.out.println(ob.findElement(By.xpath("/button[@class='search-result-refine-menu__load-button ng-scope']")).getText());
			ob.findElement(By.xpath("//button[@class='search-result-refine-menu__load-button ng-scope']")).click();
			Thread.sleep(2000);
			
//			System.out.println(ob.findElement(By.xpath("//button[@class='load-more-button ng-scope']")).getText());
			
			String temp1=ob.findElement(By.xpath("//button[@class='search-result-refine-menu__load-button ng-scope']")).getText();
			if(!compareStrings("Less",temp1)){
				
				test.log(LogStatus.FAIL, "MORE link not working");// extent
				// reports
				status = 2;// excel
				test.log(
				LogStatus.INFO,
				"Snapshot below: "
				+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
				+ "_more_link_not_working")));// screenshot

			}
			
		
			ob.findElement(By.xpath("//button[@class='search-result-refine-menu__load-button ng-scope']")).click();
			Thread.sleep(2000);
			
			String temp2=ob.findElement(By.xpath("//button[@class='search-result-refine-menu__load-button ng-scope']")).getText();
			if(!compareStrings("More",temp2)){
				
				test.log(LogStatus.FAIL, "LESS link not working");// extent
				// reports
				status = 2;// excel
				test.log(
				LogStatus.INFO,
				"Snapshot below: "
				+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
				+ "_less_link_not_working")));// screenshot

			}
			
			closeBrowser();

		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something went wrong");// extent reports
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

		// if(status==1)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "PASS");
		// else if(status==2)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "FAIL");
		// else
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "SKIP");

	}

}
