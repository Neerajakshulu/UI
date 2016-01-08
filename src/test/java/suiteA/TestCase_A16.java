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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
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


public class TestCase_A16 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that user is able to change his TR password by using FORGOT YOUR PASSWORD link and that he is able to login with his new password").assignCategory("Suite A");
		
	}
	
	@Test
	public void testcaseA16() throws Exception{
		
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
		
			String search_query="kernel";
			String password="Transaction@2";
			String first_name="disco";
			String last_name="dancer";
		
//		1)Create a new user
//		2)Login with new user and logout
			openBrowser();
			try{
				maximizeWindow();
				}
				catch(Throwable t){
					
					System.out.println("maximize() command not supported in Selendroid");
				}
			clearCookies();
			
			ob.get("https://www.guerrillamail.com");
			String email=ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			Thread.sleep(8000);
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			Thread.sleep(4000);
			
			
			ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).click();
			Thread.sleep(2000);
			ob.findElement(By.id(OR.getProperty("reg_email_textBox"))).sendKeys(email);
			ob.findElement(By.id(OR.getProperty("reg_firstName_textBox"))).sendKeys(first_name);
			ob.findElement(By.id(OR.getProperty("reg_lastName_textBox"))).sendKeys(last_name);
			ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).sendKeys(password);
			ob.findElement(By.id(OR.getProperty("reg_confirmPassword_textBox"))).sendKeys(password);
			ob.findElement(By.id(OR.getProperty("reg_terms_checkBox"))).click();
			ob.findElement(By.xpath(OR.getProperty("reg_register_button"))).click();
			Thread.sleep(10000);
			
			
			ob.get("https://www.guerrillamail.com");
			List<WebElement> email_list=ob.findElements(By.xpath(OR.getProperty("email_list")));
			WebElement myE=email_list.get(0);
			JavascriptExecutor executor = (JavascriptExecutor)ob;
			executor.executeScript("arguments[0].click();", myE);
//			email_list.get(0).click();
			Thread.sleep(2000);
			
			
			WebElement email_body=ob.findElement(By.xpath(OR.getProperty("email_body")));
			List<WebElement> links=email_body.findElements(By.tagName("a"));
			links.get(0).click();
//			ob.get(links.get(0).getAttribute("href"));
			Thread.sleep(8000);
			
			Set<String> myset=ob.getWindowHandles();
			Iterator<String> myIT=myset.iterator();
			ArrayList<String> al=new ArrayList<String>();
			for(int i=0;i<myset.size();i++){
				
				al.add(myIT.next());
			}
			ob.switchTo().window(al.get(1));
			Thread.sleep(5000);
			
			
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(email);
			ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(password);
			ob.findElement(By.id(OR.getProperty("login_button"))).click();
			Thread.sleep(25000);
			
			logout();
			Thread.sleep(4000);
		

//		3)Change the password
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			Thread.sleep(4000);
			
			
			

		ob.findElement(By.linkText(OR.getProperty("TR_forgot_password_link"))).click();
		Thread.sleep(4000);
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(email);
		ob.findElement(By.xpath(OR.getProperty("TR_forgot_password_submit_button"))).click();
		Thread.sleep(4000);
		
		String text=ob.findElement(By.xpath(OR.getProperty("reg_accountConfirmationMessage_label"))).getText();
		String expected_text="A reset password email has been sent to the address on file. Please check your email and follow link to reset your password. The link is valid for 24 hours. You may need to check your spam folder or unblock customer.access@thomsonreuters.com. If you have any questions, please contact: ProjectNeon@thomsonreuters.com";
		if(!StringContains(text,expected_text)){
			
			test.log(LogStatus.FAIL, "Email for password change not sent");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_password_change_email_not_sent")));//screenshot	
			
		}
		Thread.sleep(15000);
		ob.close();
		ob.switchTo().window(al.get(0));
		Thread.sleep(5000);
//		ob.get("https://www.guerrillamail.com");
		email_list=ob.findElements(By.xpath(OR.getProperty("email_list")));
		myE=email_list.get(0);
		executor.executeScript("arguments[0].click();", myE);
//		email_list.get(0).click();
		Thread.sleep(2000);
		
		String email_subject=ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText();
		if(!StringContains(email_subject,"Thomson Reuters Reset Password")){
			
			test.log(LogStatus.FAIL, "Email for changing password not received");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_password_change_email_not_received")));//screenshot	
			
		}
		
		WebElement reset_link_element=ob.findElement(By.xpath(OR.getProperty("email_body_password_reset_link")));
		String reset_link_url=reset_link_element.getAttribute("href");
	
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,"n");
		ob.findElement(By.linkText(reset_link_url)).sendKeys(selectLinkOpeninNewTab);
		Thread.sleep(8000);
		
		myset=ob.getWindowHandles();
		myIT=myset.iterator();
		al.clear();
		for(int i=0;i<myset.size();i++){
			
			al.add(myIT.next());
		}
		Thread.sleep(8000);
		ob.switchTo().window(al.get(1));
		Thread.sleep(2000);
		ob.get(reset_link_url);
		Thread.sleep(5000);
		
		ob.findElement(By.xpath(OR.getProperty("TR_newPassword_textBox"))).sendKeys("Transaction@3");
		ob.findElement(By.xpath(OR.getProperty("TR_confirmPassword_textBox"))).sendKeys("Transaction@3");
		ob.findElement(By.xpath(OR.getProperty("TR_forgot_password_submit_button"))).click();
		Thread.sleep(5000);
		
		text=ob.findElement(By.xpath(OR.getProperty("reg_accountConfirmationMessage_label"))).getText();
		expected_text="Password reset successful.";
		if(!StringContains(text,expected_text)){
			
			test.log(LogStatus.FAIL, "Password not changed successfully");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_password_not_changed_successfully")));//screenshot	
			
		}
		
		
		
//		4)login with changed password		
		ob.findElement(By.linkText(OR.getProperty("TR_projectNeon_link"))).click();
		Thread.sleep(8000);
		ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
		Thread.sleep(4000);
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(email);
		ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys("Transaction@3");
		ob.findElement(By.id(OR.getProperty("login_button"))).click();
		Thread.sleep(25000);
		if(!checkElementPresence("help_link")){
			
			test.log(LogStatus.FAIL, "User unable to login with changed password");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_user_unable_to_login_with_changed_password")));//screenshot	
			
		}

		ob.quit();
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
