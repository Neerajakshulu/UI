package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class TestCase_B126 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent
				.startTest(var,
						"Verify that all fields get displayed correctly for an article in record view page")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB126() throws Exception {

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
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("argentina");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			List<WebElement> content_type_tiles=ob.findElements(By.xpath("//*[contains(@class,'content-type-selector ng-scope')]"));
			content_type_tiles.get(1).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);
			String title1=ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			Thread.sleep(5000);
			waitForPageLoad(ob);
			waitForAjax(ob);
			waitForElementTobeVisible(ob, By.cssSelector("h2[class^='record-heading']"), 40);
			
			String title2=ob.findElement(By.cssSelector("h2[class^='record-heading']")).getText();
			
			
			if(!compareStrings(title1,title2)){
				
				test.log(LogStatus.FAIL, "Clicking on article title is not redirected to correct record page");//extent reports
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_search_drop_down_option_not_retained")));//screenshot	
				
				
			}else{
				test.log(LogStatus.PASS, "Article title is matching");
				
			}
			checkIfFieldIsDisplyed(By.xpath("//div[@class='doc-info' and contains(.,'Times Cited')]/descendant::span[@class='badge ng-binding']"), "Times Cited");
			
			checkIfFieldIsDisplyed(By.xpath("//div[@class='doc-info' and contains(.,'Cited References')]/descendant::span[@class='badge ng-binding']"), "Cited References");
			
			checkIfFieldIsDisplyed(By.xpath("//div[@class='doc-info' and contains(.,'Comments')]/descendant::span[@class='badge ng-binding']"), "Comments");
			
			checkIfFieldIsDisplyed(By.linkText("Details"), "Details Link");
			checkIfFieldIsDisplyed(By.xpath("//h3[contains(.,'Abstract')]"), "Abstract Heading");
			
			checkIfFieldIsDisplyed(By.xpath("//h3[contains(.,'Abstract')]/following-sibling::p[@class='ng-binding']"), "Abstract Content");
			checkIfFieldIsDisplyed(By.cssSelector("div[class='full-record'] span[class='meta ng-binding']"), "Publication details");
			
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
	
	private void checkIfFieldIsDisplyed(By locator, String fieldName) throws Exception{
		try{
			
			if(ob.findElement(locator).isDisplayed()){
				test.log(LogStatus.PASS, fieldName+" is displayed in article record view page");	
			}else throw new Exception();
			
		}catch(Exception e){
			
			test.log(LogStatus.FAIL, fieldName+" is displayed in article record view page");
			test.log(LogStatus.INFO, "Error--->" + e);
			ErrorUtil.addVerificationFailure(e);
			status = 2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "Record_view_page_field_verification_failed")));// screenshot
		}
		
		
		
	}

}
