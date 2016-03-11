package suiteA;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.TestUtil;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class TestCase_A22 extends TestBase {
	static int status=1;

	@BeforeTest
	public void beforeTest() throws Exception{
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify the checkbox for \"Receive email notifications for likes,comments and other activity\" is working correctly").assignCategory("Suite A");

	}

	@Test
	public void testCaseA22() throws Exception{
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "A Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteAxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		if(!master_condition){

			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
			
		}
		
		
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");

		openBrowser();
		try{
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
//			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			login();
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("header_label")), 30);
			ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
//		
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("account_link"))).click();
			waitForElementTobeVisible(ob, By.xpath("//span[@class='webui-custom-checkbox']"), 30);
			test.log(LogStatus.INFO,"The Check box for changing email preferences is visible");
//	
			
			String cssValue=ob.findElement(By.xpath("(//span[@class='webui-custom-checkbox'])")).getCssValue("background"); 
			
			
			if(cssValue.contains("rgb(255, 128, 0)")){
				test.log(LogStatus.INFO,"check box is selected");
			}else{
				test.log(LogStatus.INFO,"check box is not selected by default");
				status=2;
			}
			//unchecking the check box n checking if it is working
			ob.findElement(By.xpath("//span[@class='webui-custom-checkbox']")).click();
			cssValue=ob.findElement(By.xpath("(//span[@class='webui-custom-checkbox'])")).getCssValue("background");
			
			Thread.sleep(5000);
			if(cssValue.contains("rgb(255, 255, 255)")){
				test.log(LogStatus.INFO, "unchecking is working fine");
				//restoring the check status 
				ob.findElement(By.xpath("//span[@class='webui-custom-checkbox']")).click();
			}else{
				test.log(LogStatus.INFO,"Unchecking is not working");
				status=2;
			}
			logout();
		}catch(Throwable t){
			test.log(LogStatus.FAIL, "unexpected_something happpened");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"unable_to_open_preference_page")));//screenshot	
			
			System.out.println("maximize() command not supported in Selendroid");
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
