package suiteA;



import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;


public class TestCase_A2 extends TestBase{
	static int status=1;

	//	Following is the list of status:
	//		1--->PASS
	//		2--->FAIL
	//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){

		test = extent.startTest(this.getClass().getSimpleName(), "Verify that existing TR user is able to login successfully and that case-sensitivity of email id doesn't have any effect on login process").assignCategory("Suite A");

	}

	@Test
	public void testcaseA2() throws Exception{

		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "A Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteAxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		ArrayList<String> cases=new ArrayList<String>();
		cases.add("smallCase");
		cases.add("upperCase");
		if(!master_condition){

			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports

		}

		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		Iterator<String> iterator=cases.iterator();
		while(iterator.hasNext()){
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
				ob.navigate().to(host);
				Thread.sleep(8000);
				
				//if :checking if user can login with uppercase email address
				if(iterator.next().equals("upperCase")){
					waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
		    	   	ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
					Thread.sleep(4000);
					ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
					ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(CONFIG.getProperty("defaultUsername").toUpperCase());
					ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(CONFIG.getProperty("defaultPassword"));
					ob.findElement(By.id(OR.getProperty("login_button"))).click();
				}else{//else: checking if user can login successfully using smallcase email address
					
					login();		
				}


				Thread.sleep(15000);

				//Verify that login is successful
				if(!checkElementPresence("help_link")){

					test.log(LogStatus.FAIL, "Existing TR user credentials are not working fine");//extent reports
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_existing_TR_credentials_not_working_fine")));//screenshot	

				}

				//Verify that profile name gets displayed correctly
				if(!checkElementPresence("TR_profile_name_xpath")){

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
