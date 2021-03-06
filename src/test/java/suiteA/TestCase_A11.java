package suiteA;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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



public class TestCase_A11 extends TestBase{
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	// Checking whether this test case should be skipped or not
		@BeforeTest
		public void beforeTest() throws Exception{
			String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
			test = extent.startTest(var, "Verify CONFIRM PASSWORD field in new TR user registration page").assignCategory("Suite A");
//			test.log(LogStatus.INFO, "****************************");
			//load the runmodes of the tests			
			runmodes=TestUtil.getDataSetRunmodes(suiteAxls, this.getClass().getSimpleName());	
		}
	
			
	@Test(dataProvider="getTestData")
	public void testcaseA11(
								String password,
								String validity
						  ) throws Exception{
		
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "A Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteAxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition){
			
			status=3;
//			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		
		}
		
		
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			
			test.log(LogStatus.INFO, "Runmode for test set data set to no "+(count+1));
			skip=true;
//			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "SKIP");
			throw new SkipException("Runmode for test set data set to no "+(count+1));
		}

		
		try{
		
			test.log(LogStatus.INFO,password +" -- "+validity);	
		
		// selenium code
		openBrowser();
//		
		try{
		maximizeWindow();
		}
		catch(Throwable t){
			
			System.out.println("maximize() command not supported in Selendroid");
		}
		clearCookies();
		
		
		//Navigate to TR login page
//		ob.get(CONFIG.getProperty("testSiteName"));
		ob.navigate().to(host);
//		
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
		
		ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
//	
		waitForElementTobeVisible(ob, By.linkText(OR.getProperty("TR_register_link")), 30);
		
		//Create new TR account
		ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).click();
//
		waitForElementTobeVisible(ob, By.id(OR.getProperty("reg_password_textBox")), 30);
		ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).sendKeys("Transaction@2");
		ob.findElement(By.id(OR.getProperty("reg_confirmPassword_textBox"))).sendKeys(password);
		ob.findElement(By.id(OR.getProperty("reg_lastName_textBox"))).click();
		
		List<WebElement> errorList=ob.findElements(By.id(OR.getProperty("reg_confirmPasswordError_label")));		
				
				if(validity.equalsIgnoreCase("YES")){
					
				
				//verifying that error message is not getting displayed
				if(!compareNumbers(0,errorList.size())){
					
					fail=true;//excel
					test.log(LogStatus.FAIL,"Error message getting displayed unnecessarily");//extent report
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_error_message_getting_displayed_unnecessarily_"+(count+1))));
					closeBrowser();
					return;
				}
				
				
				}
				
				else
				{
					
					if(!compareNumbers(1,errorList.size())){
						
						fail=true;//excel
						test.log(LogStatus.FAIL,"Error message not getting displayed");//extent report
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_error_message_not_getting_displayed_"+(count+1))));
						closeBrowser();
						return;
					}
					
					String errorText=ob.findElement(By.id(OR.getProperty("reg_confirmPasswordError_label"))).getText();
					if(!compareStrings("Passwords do not match.",errorText)){
						
						fail=true;//excel
						test.log(LogStatus.FAIL,"Error text is incorrect");//extent report
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_incorrect_error_text_"+(count+1))));
						closeBrowser();
						return;
						
					}
					
				}
				
				closeBrowser();
				
		}
		
		catch(Throwable t){
			
			
			status=2;//excel-main testcase
			fail=true;//excel-dataset
			ErrorUtil.addVerificationFailure(t);//testng
			test.log(LogStatus.FAIL,"Something unexpected happened");//extent reports
			//next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());//extent reports
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			closeBrowser();
		}
				
			
				
				test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution ends for data set #"+ (count+1)+"--->");
	}
	
	@AfterMethod
	public void reportDataSetResult(){
		if(skip)
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "SKIP");
		
		else if(fail){
			
			status=2;
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "PASS");
		
		
		skip=false;
		fail=false;
		

	}
	
	@AfterTest
	public void reportTestResult(){
		
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
		
	
	}

	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteAxls, this.getClass().getSimpleName()) ;
	}

}
