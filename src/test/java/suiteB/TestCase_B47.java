package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B47 extends TestBase {
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
						"Verify that user is able to select any of the content types present in search drop down")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB47() throws Exception {

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
			//ob.navigate().to(CONFIG.getProperty("testSiteName"));
			//Thread.sleep(8000);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			// login using TR credentials
			login();
			//Thread.sleep(15000);
			waitForElementTobeVisible(ob, By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']"), 30);
			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).click();
			//Thread.sleep(2000);
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'Articles')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'Articles')]")).click();
			//Thread.sleep(2000);
			BrowserWaits.waitTime(2);
			String text1=ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).getText();
			System.out.println(text1);
			
			if(!compareStrings("Articles",text1)){
				
				test.log(LogStatus.FAIL, "User unable to select ARTICLES option from search drop down");// extent report
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_select_ARTICLES_option_from_search_drop_down")));// screenshot

			}
			
			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).click();
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'Patents')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'Patents')]")).click();
			//Thread.sleep(2000);
			BrowserWaits.waitTime(2);
			String text2=ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).getText();
			System.out.println(text2);
			
			if(!compareStrings("Patents",text2)){
				
				test.log(LogStatus.FAIL, "User unable to select PATENTS option from search drop down");// extent report
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_select_PATENTS_option_from_search_drop_down")));// screenshot

			}
			
			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).click();
			//Thread.sleep(2000);
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'People')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'People')]")).click();
			//Thread.sleep(2000);
			BrowserWaits.waitTime(2);
			String text3=ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).getText();
			System.out.println(text3);
			
			if(!compareStrings("People",text3)){
				
				test.log(LogStatus.FAIL, "User unable to select PEOPLE option from search drop down");// extent report
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_select_PEOPLE_option_from_search_drop_down")));// screenshot

			}
			
			
			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).click();
			//Thread.sleep(2000);
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'Posts')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'Posts')]")).click();
			//Thread.sleep(2000);
			BrowserWaits.waitTime(2);
			String text4=ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).getText();
			System.out.println(text4);
			
			if(!compareStrings("Posts",text4)){
				
				test.log(LogStatus.FAIL, "User unable to select POSTS option from search drop down");// extent report
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_select_POSTS_option_from_search_drop_down")));// screenshot

			}
			
			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).click();
			//Thread.sleep(2000);
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'All')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'All')]")).click();
			//Thread.sleep(2000);
			BrowserWaits.waitTime(2);
			String text5=ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).getText();
			System.out.println(text5);
			
			if(!compareStrings("People",text3)){
				
				test.log(LogStatus.FAIL, "User unable to select ALL option from search drop down");// extent report
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_select_ALL_option_from_search_drop_down")));// screenshot

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
