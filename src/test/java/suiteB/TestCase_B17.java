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

public class TestCase_B17 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{

		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent
				.startTest(var,
						"Verify that user is able to sort the articles by TIMES CITED field in ARTICLES content type")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB17() throws Exception {
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

			String search_query="cat dog";
			
			openBrowser();
			clearCookies();
			maximizeWindow();
			
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			
			//login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			
			//Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath("//*[contains(@class,'content-type-selector ng-scope')]"), 30);
			

			List<WebElement> content_type_tiles=ob.findElements(By.xpath("//*[contains(@class,'content-type-selector ng-scope')]"));
			content_type_tiles.get(1).click();
			waitForElementTobeVisible(ob, By.id("single-button"), 30);
			
			ob.findElement(By.id("single-button")).click();
			waitForElementTobeVisible(ob, By.xpath("//a[@class='ng-binding' and contains(text(),'Times Cited')]"), 30);
			ob.findElement(By.xpath("//a[@class='ng-binding' and contains(text(),'Times Cited')]")).click();
			waitForElementTobeVisible(ob, By.xpath("//*[@class='h6 doc-info']"), 30);
			
			List<WebElement> times_cited_labels=ob.findElements(By.xpath("//*[@class='h6 doc-info']"));
			ArrayList<Integer> counts=new ArrayList<Integer>();
			String temp;
			for(int i=0;i<times_cited_labels.size();i++){
				
				temp=times_cited_labels.get(i).getText().substring(0,times_cited_labels.get(0).getText().indexOf(" "));
				counts.add(Integer.parseInt(temp));
//				System.out.println(counts.get(i));
			}
			
			ArrayList<Integer> mylist=new ArrayList<Integer>();
			mylist.addAll(counts);
//			System.out.println(mylist);
			
			Collections.sort(mylist);
			Collections.reverse(mylist);
			
			try{
				
				Assert.assertTrue(counts.equals(mylist));
			}
			
			catch(Throwable t){
				
				test.log(LogStatus.FAIL, "Articles are not sorted correctly as per TIMES CITED field in ARTICLES content type");// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_incorrect_TIMES_CITED_sorting_in_ARTICLES_content_type")));// screenshot
				
				
			}
			
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
