package suiteE;

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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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


public class TestCase_E1 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		
		test = extent.startTest(this.getClass().getSimpleName(), "To verify that user is able to add document to watchlist from search results page").assignCategory("Suite E");
		
	}
	
	@Test
	public void testcaseE1() throws Exception{
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "E Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteExls,this.getClass().getSimpleName());
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
		String first_name="mask";
		String last_name="man";
			
		
		//1--->Making a new user
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
//		ob.navigate().to(CONFIG.getProperty("testSiteName"));
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
//		email_list.get(0).click();
		Thread.sleep(2000);
		
		
		WebElement email_body=ob.findElement(By.xpath(OR.getProperty("email_body")));
		List<WebElement> links=email_body.findElements(By.tagName("a"));
		ob.get(links.get(0).getAttribute("href"));
		Thread.sleep(8000);
		
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(email);
		ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(password);
		ob.findElement(By.id(OR.getProperty("login_button"))).click();
		Thread.sleep(25000);
		
		
		
		//2--->Adding an article to watchlist
		ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
		ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
		Thread.sleep(4000);
		
		ob.findElement(By.xpath("//i[@class='webui-icon webui-icon-watch cursor-pointer watch-icon-inactive']")).click();
		String document_name=ob.findElement(By.xpath("//a[@class='searchTitle ng-binding']")).getText();
		
		
		//3--->verifying that particular article has been added to watchlist
		ob.findElement(By.xpath("//span[contains(text(),'Watchlist')]")).click();
		Thread.sleep(8000);
		
		List<WebElement> watchlist=ob.findElements(By.xpath("//a[@class='searchTitle ng-binding']"));
		
		int count = 0;
		for(int i=0;i<watchlist.size();i++){
			
			if(watchlist.get(i).getText().equals(document_name))
				count++;
			
		}
		
		if(!compareNumbers(1,count)){
			
			test.log(LogStatus.FAIL, "User not able to add document into watchlist from search results page");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_user_unable_to_add_document_into_watchlist_from_searchResults_page")));//screenshot
			
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
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "SKIP");
	
	}
	

		

}
