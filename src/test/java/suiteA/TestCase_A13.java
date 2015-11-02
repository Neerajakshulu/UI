package suiteA;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
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


public class TestCase_A13 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		
		test = extent.startTest(this.getClass().getSimpleName(), "To verify that TERMS OF USE and PRIVACY STATEMENT links are working correctly").assignCategory("Suite A");
		
	}
	
	@Test
	public void testcaseA13() throws Exception{
		
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
		ob.get(CONFIG.getProperty("testSiteName"));
//		ob.navigate().to(host);
		Thread.sleep(8000);
		
		ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
		Thread.sleep(4000);
		
		//Create new TR account
		ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).click();
		Thread.sleep(2000);

		ob.findElement(By.linkText(OR.getProperty("reg_TermsOfUse_link"))).click();
		Thread.sleep(5000);
		
		Set<String> myset1=ob.getWindowHandles();
		Iterator<String> myIT1=myset1.iterator();
		ArrayList<String> al1=new ArrayList<String>();
		
		for(int i=0;i<myset1.size();i++){
			
			al1.add(myIT1.next());
			
		}
		
		ob.switchTo().window(al1.get(1));
		String pageHeading=ob.findElement(By.xpath(OR.getProperty("reg_PageHeading_label"))).getText();
//		System.out.println(pageHeading);
		
		if(!compareStrings("TERMS OF USE",pageHeading)){
			
			test.log(LogStatus.FAIL, "Either TERMS OF USE link is not working or the page is not getting displayed correctly");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_issue_with_termsOfUse_link")));//screenshot	
			
		}
		
		ob.switchTo().window(al1.get(0));
		WebElement myE=ob.findElement(By.linkText(OR.getProperty("reg_PricayStatement_link")));
		if(System.getenv("SELENIUM_BROWSER").equalsIgnoreCase("internet explorer")){
			
			JavascriptExecutor executor = (JavascriptExecutor)ob;
			executor.executeScript("arguments[0].click();", myE);
		}
		else{
			
			myE.click();
		}
		Thread.sleep(5000);
		
		al1.clear();
		myset1=ob.getWindowHandles();
		myIT1=myset1.iterator();
//		System.out.println(myset1.size());
		for(int i=0;i<myset1.size();i++){
			
			al1.add(myIT1.next());
		}
		
		ob.switchTo().window(al1.get(2));
		String pageHeading2=ob.findElement(By.xpath(OR.getProperty("reg_PageHeading_label"))).getText();
//		System.out.println(pageHeading2);
		
//		System.out.println(al1.size());
		if(!compareStrings("PRIVACY STATEMENT",pageHeading2)){
			
			test.log(LogStatus.FAIL, "Either PRICAY STATEMENT link is not working or the page is not getting displayed correctly");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_issue_with_privacyStatement_link")));//screenshot	
			
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
