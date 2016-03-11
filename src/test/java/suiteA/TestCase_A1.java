package suiteA;



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
import org.openqa.selenium.interactions.Actions;
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


public class TestCase_A1 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that user is able to register for new TR account and login with that").assignCategory("Suite A");
		
	}
	
	@Test
	public void testcaseA1() throws Exception{
		WebElement element=null;
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
		
		String password="Transaction@2";
		String first_name="duster";
		String last_name="man";
		
		//Open Guerilla Mail and get email id
		ob.get("https://www.guerrillamail.com");
		String email=ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();
		System.out.println(email);
		
		//Navigate to TR login page
		ob.navigate().to(host);
//		
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
		
		System.out.println("Before clicking login");
		ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
		System.out.println("After clicking login");
//		
		waitForElementTobeVisible(ob, By.linkText(OR.getProperty("TR_register_link")), 30);
		
		//Create new TR account
		ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).click();
//		
		waitForElementTobeVisible(ob, By.id(OR.getProperty("reg_email_textBox")), 30);
		ob.findElement(By.id(OR.getProperty("reg_email_textBox"))).sendKeys(email);
		ob.findElement(By.id(OR.getProperty("reg_firstName_textBox"))).sendKeys(first_name);
		ob.findElement(By.id(OR.getProperty("reg_lastName_textBox"))).sendKeys(last_name);
		ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).sendKeys(password);
		ob.findElement(By.id(OR.getProperty("reg_confirmPassword_textBox"))).sendKeys(password);
		ob.findElement(By.id(OR.getProperty("reg_terms_checkBox"))).click();
		ob.findElement(By.xpath(OR.getProperty("reg_register_button"))).click();
//		
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("reg_accountConfirmationMessage_label")), 30);
		
		
		//Verify that confirmation email is sent
		String text=ob.findElement(By.xpath(OR.getProperty("reg_accountConfirmationMessage_label"))).getText();
		if(!StringContains(text,email)){
			
			test.log(LogStatus.FAIL, "Account activation email not sent");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_account_activation_email_not_sent")));//screenshot	
			
		}
		
		
		
		//Verify that account activation email has been received
		ob.get("https://www.guerrillamail.com");
		Thread.sleep(10000);
		List<WebElement> email_list=ob.findElements(By.xpath(OR.getProperty("email_list")));
		email_list.get(0).click();
		Thread.sleep(2000);
		String email_subject=ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText();
		if(!StringContains(email_subject,"Please confirm your email address for your new Project Neon User ID")){
			
			test.log(LogStatus.FAIL, "Account activation email not received");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_account_activation_email_not_received")));//screenshot	
			
		}
		
		//Activate the account
		System.out.println("Before capturing email body links");
		WebElement email_body=ob.findElement(By.xpath(OR.getProperty("email_body")));
		List<WebElement> links=email_body.findElements(By.tagName("a"));
		System.out.println(links.size());
		System.out.println("After capturing email body links");
//		links.get(0).click();
		ob.get(links.get(0).getAttribute("href"));
		Thread.sleep(8000);
//		waitForElementTobeVisible(ob, By.id(OR.getProperty("TR_email_textBox")), 30);
		
//		//Switch to 2nd window
//		Set<String> myset=ob.getWindowHandles();
//		Iterator<String> myIT=myset.iterator();
//		ArrayList<String> al=new ArrayList<String>();
//		for(int i=0;i<myset.size();i++){
//			
//			al.add(myIT.next());
//		}
//		ob.switchTo().window(al.get(1));
		
		
		//Verify that newly registered user credentials are working fine
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(email);
		ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(password);
		ob.findElement(By.id(OR.getProperty("login_button"))).click();
		Thread.sleep(10000);
		if(!checkElementPresence("help_link")){
			
			test.log(LogStatus.FAIL, "Newly registered user credentials are not working fine");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_newly_registered_user_credentials_are_not_working_fine")));//screenshot	
			
		}
		//Verify that profile image using below xpath is present or not
		String profile_name_xpath="//img[@title='"+first_name+" "+last_name+"']";
		element=ob.findElement(By.xpath(profile_name_xpath));
		if(element==null){
			
			test.log(LogStatus.FAIL, "Incorrect profile name getting displayed");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_incorrect_profile_name_getting_displayed")));//screenshot	
			
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
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
	
	}
	

	
	

}
