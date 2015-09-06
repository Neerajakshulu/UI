package suiteE;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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


public class TestCase_E2 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		
		test = extent.startTest(this.getClass().getSimpleName(), "To verify that user is able to add document to watchlist from document page").assignCategory("Suite E");
		
	}
	
	@Test
	public void testcaseE2() throws Exception{
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "E Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteExls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition){
			
			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		try{
			
		String search_query="kernel";
			
			
		openBrowser();
		maximizeWindow();
		clearCookies();
		
		ob.navigate().to(System.getProperty("host"));
		Thread.sleep(8000);
		
		//login using TR credentials
		login();
		Thread.sleep(15000);
		
		cleanWatchlist();
		
		//Type into the search box and get search results
		ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
		ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
		Thread.sleep(4000);
		
		
		String document_name=ob.findElement(By.xpath("//a[@class='searchTitle ng-binding']")).getText();
//		System.out.println(document_name);
		ob.findElement(By.xpath("//a[@class='searchTitle ng-binding']")).click();
		Thread.sleep(4000);
		
		try{
		ob.findElement(By.xpath("//button[@class='btn btn-default activity-block-btn']")).click();
		Thread.sleep(2000);
		}
		catch(Throwable t){
		
			ob.findElement(By.xpath("//span[@class='webui-icon webui-icon-watch watch']")).click();
			Thread.sleep(2000);
			
		}
		
		
		ob.findElement(By.xpath("//span[contains(text(),'Watchlist')]")).click();
		Thread.sleep(4000);
		
		List<WebElement> watchlist=ob.findElements(By.xpath("//a[@class='searchTitle ng-binding']"));
//		System.out.println(watchlist.size());
		
		int count = 0;
		for(int i=0;i<watchlist.size();i++){
			
			if(watchlist.get(i).getText().equals(document_name))
				count++;
			
//			System.out.println(watchlist.get(i).getText());
		}
		
		if(!compareNumbers(1,count)){
			
			test.log(LogStatus.FAIL, "User not able to add document into watchlist from document page");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_user_unable_to_add_document_into_watchlist_from_document_page")));//screenshot	
		}
		
		
		closeBrowser();
		
		}
		catch(Throwable t){
			test.log(LogStatus.FAIL,"Error:"+t);//extent reports
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
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "SKIP");
	
	}
	

		

}
