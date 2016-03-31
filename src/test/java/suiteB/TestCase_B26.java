package suiteB;



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

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;


public class TestCase_B26 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		extent = ExtentManager.getReporter(filePath);
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that following sections get displayed in type ahead:a)Categoriesb)Articlesc)Patentsd)People").assignCategory("Suite B");
		
	}
	
	@Test
	public void testcaseB26() throws Exception{
		
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
		
		
			
			
			
			openBrowser();
			clearCookies();
			maximizeWindow();
			
			ob.navigate().to(host);
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			
			//login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			
			//Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("b");
			Thread.sleep(1000);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("i");
			Thread.sleep(2000);
			
			List<WebElement> headings=ob.findElements(By.xpath(OR.getProperty("sectionHeading_label")));
//			if(!compareNumbers(4,headings.size())){
//				
//				test.log(LogStatus.FAIL, "More than 4 sections getting displayed in the typeahead");//extent reports
//				status=2;//excel
//				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_more_than_four_sections_getting_displayed_in_the_typeahead")));//screenshot
//			}
//			
//			for(int i=0;i<headings.size();i++){
//				
//				System.out.println(headings.get(i).getText());
//			}
			
			
			
			boolean condition1=headings.get(0).getText().equals("Categories");
			boolean condition2=headings.get(1).getText().equals("Articles");
			boolean condition3=headings.get(2).getText().equals("Patents");
			boolean condition4=headings.get(3).getText().equals("People");
			
			boolean final_condition=condition1 && condition2 && condition3 && condition4;
//			System.out.println(final_condition);
			
			try{
				
				Assert.assertTrue(final_condition,"Typeahead sections not getting displayed correctly");
				test.log(LogStatus.PASS,"Typeahead sections getting displayed correctly");//extent reports
			}
			catch(Throwable t){
				test.log(LogStatus.FAIL,"Typeahead sections not getting displayed correctly");//extent reports
				//next 3 lines to print whole testng error in report
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO,errors.toString());//extent reports
				ErrorUtil.addVerificationFailure(t);//testng
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_typeahead_sections_not_getting_displayed_correctly")));//screenshot
				
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
		
//		if(status==1)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "PASS");
//		else if(status==2)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "FAIL");
//		else
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "SKIP");
	
	}
	

	
	

}
