package suiteF;



import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
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


public class TestCase_F2 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		
		test = extent.startTest(this.getClass().getSimpleName(), "To verify that user receives a notificatication when someone he is following comments on an article").assignCategory("Suite F");
		
	}
	
	@Test
	public void testcaseF2() throws Exception{
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "F Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteFxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition){
			
			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		try{
			
		String search_query="india";
		String email="motadon100@gmail.com";
		String password="Transaction@2";
			
			
		openBrowser();
		maximizeWindow();
		clearCookies();
		
//		ob.navigate().to(CONFIG.getProperty("testSiteName"));
		ob.navigate().to(System.getProperty("host"));
		Thread.sleep(8000);
		
		ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
		Thread.sleep(4000);
		ob.findElement(By.id("userid")).clear();
		ob.findElement(By.id("userid")).sendKeys(email);
		ob.findElement(By.id("password")).sendKeys(password);
		ob.findElement(By.id(OR.getProperty("login_button"))).click();
		Thread.sleep(15000);
		
		ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
		ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
		Thread.sleep(4000);
		
		String document_title=ob.findElement(By.xpath("//a[@class='searchTitle ng-binding']")).getText();
		System.out.println(document_title);
		ob.findElement(By.xpath("//a[@class='searchTitle ng-binding']")).click();
		Thread.sleep(4000);
		ob.findElement(By.cssSelector("div[id^='taTextElement']")).click();
		Thread.sleep(2000);
		
		 Robot robot = new Robot();
		 robot.keyPress(KeyEvent.VK_SHIFT);
		    robot.keyPress(KeyEvent.VK_H);
		    robot.keyRelease(KeyEvent.VK_H);
		    robot.keyPress(KeyEvent.VK_E);
		    robot.keyRelease(KeyEvent.VK_E);
		    robot.keyPress(KeyEvent.VK_L);
		    robot.keyRelease(KeyEvent.VK_L);
		    robot.keyPress(KeyEvent.VK_L);
		    robot.keyRelease(KeyEvent.VK_L);
		    robot.keyPress(KeyEvent.VK_O);
		    robot.keyRelease(KeyEvent.VK_O);
		    robot.keyRelease(KeyEvent.VK_SHIFT);
		
		ob.findElement(By.xpath("//button[@class='btn webui-btn-primary comment-add-button']")).click();
		Thread.sleep(2000);
		
		ob.findElement(By.xpath("//i[@class='webui-icon webui-icon-caret-down']")).click();
		Thread.sleep(2000);
		ob.findElement(By.xpath("//a[contains(text(),'Sign out')]")).click();
		Thread.sleep(8000);
		
		ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
		Thread.sleep(4000);
		ob.findElement(By.id("userid")).clear();
		ob.findElement(By.id("userid")).sendKeys(CONFIG.getProperty("defaultUsername"));
		ob.findElement(By.id("password")).sendKeys(CONFIG.getProperty("defaultPassword"));
		ob.findElement(By.id(OR.getProperty("login_button"))).click();
		Thread.sleep(15000);
		
		String text=ob.findElement(By.xpath("//*[@class='clearfix webui-media-wrapper']")).getText();
		System.out.println(text);
		
		try{
		Assert.assertTrue(text.contains("mota don") && text.contains("TODAY") && text.contains(document_title));
		}
		catch(Throwable t){
			
			
			test.log(LogStatus.FAIL,"User not receiving notification when he is following comments on an article.....Error:"+t);//extent reports
			ErrorUtil.addVerificationFailure(t);//testng
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_user_not_receiving_notification_when_someone_he_is_following_comments_on_article")));//screenshot
			
			
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
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases", TestUtil.getRowNum(suiteFxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases", TestUtil.getRowNum(suiteFxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases", TestUtil.getRowNum(suiteFxls,this.getClass().getSimpleName()), "SKIP");
	
	}
	

		

}
