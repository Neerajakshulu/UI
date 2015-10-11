package suiteB;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;


public class TestCase_B12 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		
		test = extent.startTest(this.getClass().getSimpleName(), "To verify that the addition of total articles count and total profiles count is equal to total search results count").assignCategory("Suite B");
		
	}
	
	@Test
	public void testcaseB8() throws Exception{
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteBxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition){
			
			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		try{
		
		
			
			String search_query="mota";
			
			openBrowser();
			clearCookies();
			maximizeWindow();
			
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
//			ob.navigate().to(host);
			Thread.sleep(8000);
			
			//login using TR credentials
			login();
			Thread.sleep(15000);
			
			//Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			
			String text1=ob.findElement(By.xpath(OR.getProperty("articlesTabHeading_link"))).getText().substring(9);
			String text2=ob.findElement(By.xpath(OR.getProperty("profilesTabHeading_link"))).getText().substring(7);
			String text3=ob.findElement(By.xpath(OR.getProperty("totalCountHeading_label"))).getText().substring(16);
			
			String temp1=getPurifiedString(text1);
			String temp2=getPurifiedString(text2);
			String temp3=getPurifiedString(text3);
			
			
			int article_count=Integer.parseInt(temp1);
			int profile_count=Integer.parseInt(temp2);
			int total_count=Integer.parseInt(temp3);
			
			
//			System.out.println(article_count);
//			System.out.println(profile_count);
//			System.out.println(total_count);
			
			
			if(!compareNumbers(total_count,article_count+profile_count)){
				
				test.log(LogStatus.FAIL, "Sum of total article count and total profile count is not equal to total search results count");//extent reports
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_total_search_results_count_not_equal_to_sum_of_article_count_and_profile_count")));//screenshot
			
				
			}
			
			closeBrowser();
			
			
			
		}
		catch(Throwable t){
			test.log(LogStatus.FAIL,"Something unexpected happened");//extent reports
			//next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());//extent reports
			ErrorUtil.addVerificationFailure(t);//testng
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			closeBrowser();
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution ends--->");
	}
	

	@AfterTest
	public void reportTestResult(){
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "SKIP");
	
	}
	
	
	public String getPurifiedString(String text){
		
		String[] arr=text.split(",");
		String purifiedString="";
		
		for(int i=0;i<arr.length;i++){
			
			
			purifiedString=purifiedString + arr[i];
		}
		
		return purifiedString;
	}
	

	
	

}
