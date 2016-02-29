package suiteA;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class TestCase_A25 extends TestBase {


static int status=1;

	
	
	@BeforeTest
	public void beforeTest() throws Exception{
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that Help link is working properly").assignCategory("Suite A");

	}
	
	@Test
	public void testCaseA25() throws Exception{
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
						
//			1)Create a new user
//			2)Login with new user and logout
				openBrowser();
				try{
					maximizeWindow();
					}
					catch(Throwable t){
						
						System.out.println("maximize() command not supported in Selendroid");
					}
				clearCookies();
				ob.navigate().to(host);
				Thread.sleep(8000);
				
				ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
				Thread.sleep(4000);
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(CONFIG.getProperty("defaultUsername").toUpperCase());
				ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(CONFIG.getProperty("defaultPassword"));
				ob.findElement(By.id(OR.getProperty("login_button"))).click();
				Thread.sleep(8000);
				ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
				Thread.sleep(5000);
				
				try{
					ob.findElement(By.xpath(OR.getProperty("help_link"))).click();
					Thread.sleep(8000);
					Assert.assertEquals(checkElementPresence("help_text_in_helpPage") && checkElementPresence("feedback_text_in_helpPage"),true);
					test.log(LogStatus.INFO," Help link is working as expected");
				}catch(Throwable t){
					test.log(LogStatus.FAIL," Help Link is not working");//extent reports
					//next 3 lines to print whole testng error in report
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());//extent reports
					ErrorUtil.addVerificationFailure(t);//testng
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
					closeBrowser();
				}
				
		}catch(Throwable t){
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
		closeBrowser();
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
	
	
}
