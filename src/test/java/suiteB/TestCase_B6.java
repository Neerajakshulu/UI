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
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;


public class TestCase_B6 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that type ahead functionality is working correctly").assignCategory("Suite B");
		
	}
	
	@Test
	public void testcaseB6() throws Exception{
		
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
		
		
			
			String search_query="bio";
			
			openBrowser();
			clearCookies();
			maximizeWindow();
			
//			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//			System.out.println(System.getProperty("host"));
//			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			
			
			ob.navigate().to(host);
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
//		
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			
			//login using TR credentials
			login();
//			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			
			//Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
//		
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);
			
//			System.out.println(ob.findElement(By.xpath(OR.getProperty("typeAhead_dropDown"))).getText());
			
			
			List<WebElement> myList=ob.findElements(By.xpath(OR.getProperty("typeAhead_dropDown")));
//			System.out.println("Size="+myList.size());
//			for(int i=0;i<myList.size();i++){
//				
//				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//				System.out.println(myList.get(i).getText());
//				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//			}
			
			
			try{
				
				Assert.assertTrue(myList.get(0).isDisplayed());
			}
			
			catch(Throwable t){
				
				test.log(LogStatus.PASS,"Search drop down not getting displayed");//extent reports
				ErrorUtil.addVerificationFailure(t);//testng
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_search_dropdown_not_getting_displayed")));//screenshot
				
			}
			
			
			
			
			//Verify that 10 options are contained in search drop down
//			List<WebElement> options=ob.findElements(By.xpath(OR.getProperty("search_dropDown_options_link")));
//			if(!compareNumbers(10,options.size())){
//				
//
//				test.log(LogStatus.FAIL, "10 options not getting displayed in search drop down");//extent reports
//				status=2;//excel
//				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_ten_options_not_getting_displayed_in_search_drop_down")));//screenshot	
//				
//			}
			
			
			closeBrowser();
		
			
		}
		catch(Throwable t){
			test.log(LogStatus.PASS,"Something unexpected happened");//extent reports
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
		
//		if(status==1)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "PASS");
//		else if(status==2)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "FAIL");
//		else
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "SKIP");
	
	}
	

	
	

}

