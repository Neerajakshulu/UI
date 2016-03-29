package suiteA;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import util.Xls_Reader;


public class TestCase_A3 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//		String p1=returnExcelPath(this.getClass().getSimpleName().charAt(9));
//		int p2=Integer.parseInt(this.getClass().getSimpleName().charAt(10)+"");
//		System.out.println(1);
//		System.out.println(xlRead(p1,p2,1));
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
//		System.out.println(xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),this.getClass().getSimpleName().charAt(10),1));
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		test = extent.startTest(var, "Verify that user is able to login with existing LI id and logout successfully").assignCategory("Suite A");
	}
	
	@Test
	public void testcaseA3() throws Exception{
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "A Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteAxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition){
			
			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		
		try{
		
		openBrowser();
		try{
			maximizeWindow();
			}
			catch(Throwable t){
				
				System.out.println("maximize() command not supported in Selendroid");
			}
		clearCookies();
		
		
		
		String email="amneetsingh100@gmail.com";
		String password="Transaction@2";
		
		
		//Navigate to LI login page
//		ob.navigate().to(host);
		ob.navigate().to(CONFIG.getProperty("testSiteName"));
//		
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("LI_login_button")), 30);
		ob.findElement(By.xpath(OR.getProperty("LI_login_button"))).click();
//		
		waitForElementTobeVisible(ob, By.name(OR.getProperty("LI_email_textBox")), 30);
		
		//Verify that existing LI user credentials are working fine
		ob.findElement(By.name(OR.getProperty("LI_email_textBox"))).sendKeys(email);
		ob.findElement(By.name(OR.getProperty("LI_password_textBox"))).sendKeys(password);
		ob.findElement(By.name(OR.getProperty("LI_allowAccess_button"))).click();
		Thread.sleep(15000);
		if(!checkElementPresence("apps")){
			
			test.log(LogStatus.FAIL, "Existing LI user credentials are not working fine");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_existing_LI_User_credentials_not_working_fine")));//screenshot	
			
			
		}
		
		//Verify that profile name gets displayed correctly
		
		if(!checkElementPresence("header_label")){
			
			test.log(LogStatus.FAIL, "Incorrect profile name getting displayed");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_incorrect_profile_name_getting_displayed")));//screenshot	
			
			
		}
		
		logout();
		Thread.sleep(5000);
		
		if(!checkElementPresence("login_banner")){
			
			test.log(LogStatus.FAIL, "User not able to logout successfully");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_user_unable_to_logout_successfully")));//screenshot	
			
			
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
		
	/*	if(status==1)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
	*/
	}
	

	
	

}
