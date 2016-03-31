package suiteA;



import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
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


public class TestCase_A14 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that user is not able to submit new TR user registration form without filling in the required fields").assignCategory("Suite A");
		
	}
	
	@Test
	public void testcaseA14() throws Exception{
		
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
		
		
		
		
		//Navigate to TR login page and login with valid TR credentials
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
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("reg_register_button")), 30);
		
	
		ob.findElement(By.xpath(OR.getProperty("reg_register_button"))).click();
		waitForElementTobeVisible(ob, By.id(OR.getProperty("reg_emailError_label")),5);
		
		boolean email_error=ob.findElement(By.id(OR.getProperty("reg_emailError_label"))).isDisplayed();
		boolean firstName_error=ob.findElement(By.id(OR.getProperty("reg_firstNameError_label"))).isDisplayed();
		boolean lastName_error=ob.findElement(By.id(OR.getProperty("reg_lastNameError_label"))).isDisplayed();
		boolean password_error=ob.findElement(By.id(OR.getProperty("reg_passwordError_label"))).isDisplayed();
		boolean confirmPassword_error=ob.findElement(By.id(OR.getProperty("reg_confirmPasswordError_label"))).isDisplayed();
		boolean termsAndConditions_error=ob.findElement(By.id(OR.getProperty("reg_termsAndConditionsError_label"))).isDisplayed();
		
//		System.out.println(email_error);
//		System.out.println(firstName_error);
//		System.out.println(lastName_error);
//		System.out.println(password_error);
//		System.out.println(confirmPassword_error);
//		System.out.println(termsAndConditions_error);
		
		boolean master_error_condition=email_error && firstName_error && lastName_error && password_error && confirmPassword_error && termsAndConditions_error;
//		System.out.println(master_error_condition);
		
		try{
			
			Assert.assertTrue(master_error_condition, "User able to submit new TR user registration form without filling in the required fields");
			test.log(LogStatus.PASS, "User not able to submit new TR user registration form without filling in the required fields");
		}
		
		catch(Throwable t){
			
			test.log(LogStatus.FAIL, "User able to submit new TR user registration form without filling in the required fields");
			test.log(LogStatus.INFO, "Error--->"+t);
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_user_able_to_submit_new_TR_user_registration_form_without_filling_required_fields")));//screenshot
			ErrorUtil.addVerificationFailure(t);
			status=2;//excel
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
		
		/*if(status==1)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
	*/
	}
	

	
	

}
