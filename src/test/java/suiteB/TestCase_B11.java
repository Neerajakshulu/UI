package suiteB;



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

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;


public class TestCase_B11 extends TestBase {
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that sorting and filtering are retained when user navigates back to search results page from record view page").assignCategory("Suite B");
		
	}
	
	@Test
	public void testcaseB11() throws Exception{
		
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
		
		
			
			String search_query="biology";
			
			openBrowser();
			clearCookies();
			maximizeWindow();
			
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			Thread.sleep(8000);
			
			//login using TR credentials
			login();
			Thread.sleep(15000);
			
			//Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			
			ob.findElement(By.cssSelector("i[class='webui-icon pull-right dropchevron ng-scope webui-icon-caret-down']")).click();
			Thread.sleep(3000);
			
			List<WebElement> checkboxes=ob.findElements(By.xpath(OR.getProperty("filter_checkbox")));
//			System.out.println(checkboxes.size());
			
			checkboxes.get(0).click();
			Thread.sleep(8000);
			List<WebElement> checkbox=ob.findElements(By.xpath(OR.getProperty("filter_checkbox")));
			checkbox.get(1).click();
			Thread.sleep(8000);
			
			
			ob.findElement(By.id(OR.getProperty("sortDropdown_button"))).click();
			Thread.sleep(1000);
			ob.findElement(By.linkText(OR.getProperty("sortDropdown_timesCitedOption_link"))).click();
			Thread.sleep(6000);
			
			List<WebElement> searchResults=ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
			//System.out.println("search Results-->"+searchResults.size());
			ArrayList<String> al1=new ArrayList<String>();
			for(int i=0;i<searchResults.size();i++){
				
				al1.add(searchResults.get(i).getText());
				
			}
			jsClick(ob, searchResults.get(5));
			//searchResults.get(7).click();
			Thread.sleep(5000);
			
			
//			ob.navigate().back();
			JavascriptExecutor js = (JavascriptExecutor)ob;
			js.executeScript("window.history.back();");
			Thread.sleep(20000);
			List<WebElement> searchResults2=ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
			//System.out.println("search Results-->"+searchResults);
			ArrayList<String> al2=new ArrayList<String>();
			for(int i=0;i<searchResults2.size();i++){
				
				al2.add(searchResults2.get(i).getText());
				
			}
			
			
			int temp=0;
			for(int i=0;i<5;i++){
				
				if(al1.get(i).equals(al2.get(i))){
					
					temp++;
				}
			}
			

//			System.out.println(al1.size());
//			System.out.println(al2.size());
//			System.out.println(al1.equals(al2));
			
			if(!compareNumbers(5,temp)){
				
				test.log(LogStatus.FAIL, "Search does not maintain state when user navigates back to search results page from record view page");//extent reports
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_search_not_maintaining_state")));//screenshot	
				
			}
			
			
			
			
			String option=ob.findElement(By.id(OR.getProperty("sortDropdown_button"))).getText();
			if(!compareStrings("Times Cited",option)){
				
				test.log(LogStatus.FAIL, "Incorrect sorting option getting displayed");//extent reports
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_incorrect_sorting_option_getting_displayed")));//screenshot	
				
			}
			
			List<WebElement> checkboxes2=ob.findElements(By.xpath(OR.getProperty("filter_checkbox")));
//			System.out.println("------------>"+checkboxes2.size());
			
			boolean filtering_condition=checkboxes2.get(0).isSelected() && checkboxes2.get(1).isSelected();
//			System.out.println(filtering_condition);
			
			try{
				Assert.assertTrue(filtering_condition);
				test.log(LogStatus.PASS, "Filters are retained when user navigates back to search results page from record view page");
				}
				catch(Throwable t){
					
					test.log(LogStatus.FAIL, "Filters are not retained when user navigates back to search results page from record view page");//extent reports
					test.log(LogStatus.INFO, "Error--->"+t);
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_filters_not_retained_when_user_navigates_back_to_search_results_page_from_record_view_page")));//screenshot	
					
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
	

	
	

}
