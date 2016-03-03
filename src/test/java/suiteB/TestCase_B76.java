package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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

public class TestCase_B76 extends TestBase {
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
						"Verify that record view of a post gets displayed when user clicks on any post option in the search type ahead while POSTS option is selected in the search drop down")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB76() throws Exception {

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
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			Thread.sleep(8000);

			// login using TR credentials
			login();
			Thread.sleep(15000);
			
			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).click();
			Thread.sleep(2000);
			ob.findElement(By.xpath("//a[contains(text(),'Posts')]")).click();
			Thread.sleep(2000);
			
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("bio");
			Thread.sleep(2000);
			
			
			WebElement myE=ob.findElement(By.xpath(OR.getProperty("postsTile")));
			String text=myE.getText();
			
			String[] arr=text.split("\n");
			
			ArrayList<String> al=new ArrayList<String>();
			for(int i=1;i<arr.length;i++){
				
				al.add(arr[i]);
			}
			
			String expected_text=al.get(1);
			System.out.println(expected_text);
			
			
			for(int i=1;i<=2;i++){
				
				ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(Keys.ARROW_DOWN);
			}
			
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(Keys.ENTER);
			Thread.sleep(4000);
			
			String actual_text=ob.findElement(By.tagName("h2")).getText();
			System.out.println(actual_text);
			
			if(!compareStrings(expected_text,actual_text)){
				
				
				test.log(LogStatus.FAIL, "Record view page of a post does not get displayed when user clicks on any post option in the search type ahead while POSTS option is selected in the search drop down");// extent report
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_record_view_page_of_a_post_not_getting_displayed")));// screenshot

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
