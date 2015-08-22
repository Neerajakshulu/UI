package suiteA;

import java.io.IOException;
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



public class TestCase_A7 extends TestBase{
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	// Checking whether this test case should be skipped or not
		@BeforeTest
		public void beforeTest(){
			
			test = extent.startTest(this.getClass().getSimpleName(), "To verify that Linkedin login is working corectly").assignCategory("Suite A");
//			test.log(LogStatus.INFO, "****************************");
			//load the runmodes of the tests			
			runmodes=TestUtil.getDataSetRunmodes(suiteAxls, this.getClass().getSimpleName());	
		}
	
			
	@Test(dataProvider="getTestData")
	public void testcaseA2(
								String username,
								String password,
								String account_name,
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
			
			test.log(LogStatus.INFO, "Runmode for test set data set to no "+count);
			skip=true;
//			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "SKIP");
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts for data set #"+ count+"--->");
		test.log(LogStatus.INFO,username +" -- "+password);
		
		// selenium code
				openBrowser();
				clearCookies();
				maximizeWindow();
				
				
				ob.get(CONFIG.getProperty("testSiteName"));
				ob.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				ob.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				
				ob.findElement(By.xpath(OR.getProperty("sign_in_button"))).click();
				Thread.sleep(2000);
				ob.findElement(By.xpath(OR.getProperty("linkedin_link"))).click();
				Thread.sleep(2000);
				
				
				ob.findElement(By.name(OR.getProperty("username_textbox"))).sendKeys(username);
				ob.findElement(By.name(OR.getProperty("password_textbox"))).sendKeys(password);
				ob.findElement(By.name(OR.getProperty("submit_button"))).click();
				Thread.sleep(2000);
				
				
				
				if(validity.equalsIgnoreCase("YES")){
					
				
				String name=ob.findElement(By.xpath(OR.getProperty("profile_name_image"))).getAttribute("alt");
				
				//checking whether the name displayed is correct or not
				try{
					
					Assert.assertTrue(name.equals(account_name),"Either login is unsuccessful or profile name is incorrect");
					test.log(LogStatus.PASS,"Login is successful and profile name is correct");
				}
				
				catch(Throwable t){
					
					fail=true;//excel
					test.log(LogStatus.FAIL,"Either login is unsuccessful or profile name is incorrect");
					ErrorUtil.addVerificationFailure(t);
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"either_login_is_unsuccessful_or_profile_name_is_incorrect_"+count)));
					closeBrowser();
					return;
					
				}
				
			
				
				}
				
				else
				{
					
					if(!checkElementPresence("login_error_banner")){
						
						fail=true;//excel
						test.log(LogStatus.FAIL,"Either unexpected login happened or error did not get displayedt");
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"either_unexpected_login_happened_or_error_did _not_get _displayed"+count)));
						closeBrowser();
						
						
					}
					
				}
				
				
				
				closeBrowser();

				
				test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution ends for data set #"+ count+"--->");
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
