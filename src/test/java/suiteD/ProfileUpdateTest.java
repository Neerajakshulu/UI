package suiteD;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.AuthoringProfileCommentsTest;
import util.ErrorUtil;
import util.TestUtil;

public class ProfileUpdateTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	static String followBefore=null;
	static String followAfter=null;
	static String profileHeadingName;
	static String profileDetailsName;
	
	@BeforeTest
	public void beforeTest() {
		test = extent.startTest(this.getClass().getSimpleName(), "User Own Profile Update Validation").assignCategory("Suite D");
		runmodes=TestUtil.getDataSetRunmodes(suiteDxls, this.getClass().getSimpleName());
		System.out.println("Run modes-->"+runmodes.length);
	}
	
	/**
	 * Method for wait TR Login Screen		
	 * @throws Exception, When TR Login screen not displayed
	 */
	@Test
	@Parameters({"username","password"})
	public void testLoginTRAccount(String username,String password) throws Exception  {
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "D Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteDxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition) {
			status=3;
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		
		
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")) {
			test.log(LogStatus.INFO, "Runmode for test set data set to no "+count);
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts for data set #"+ count+"--->");
		
				try {
					openBrowser();
					clearCookies();
					maximizeWindow();
					
					ob.navigate().to(System.getProperty("host"));
					waitForTRHomePage();
					Thread.sleep(6000);
					enterTRCredentials(username, password);
					clickLogin();
				} catch (Throwable e) {
					test.log(LogStatus.FAIL,"Error:"+e);//extent reports
					ErrorUtil.addVerificationFailure(e);//testng
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_profile_data_updation_not_done")));//screenshot
					closeBrowser();
				}
	}
	
	
	@Test(dependsOnMethods="testLoginTRAccount")
	@Parameters("profileInfo")
	public void profileDataUpdate(String profileInfo) throws Exception  {
			try {
				editUserOwnProfile(profileInfo);
				test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
				closeBrowser();
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,"Error:"+t);//extent reports
				ErrorUtil.addVerificationFailure(t);//testng
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_profile_data_updation_not_done")));//screenshot
				closeBrowser();
			}
	}
	
	
	/**
	 * Method for Scrolling down to the page
	 * @throws InterruptedException, When scroll not done
	 */
	public static void scrollingToElementofAPage() throws InterruptedException  {
		JavascriptExecutor jse = (JavascriptExecutor)ob;
		jse.executeScript("scroll(0, 250);");
		Thread.sleep(4000);
		
	}
	
	/**
	 * Method for Validate Profile, whether Profile is displayed my own details or not
	 * @throws Exception, when details are not matching
	 */
	public void editUserOwnProfile(String profleInfo) throws Exception  {
		
		String profileDetailsUpdate[]=profleInfo.split("\\|");
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_profile_dropdown_css"))).click();
		AuthoringProfileCommentsTest.waitUntilText("Profile");
		
		ob.findElement(By.linkText(TestBase.OR.getProperty("tr_profile_link"))).click();
		AuthoringProfileCommentsTest.waitUntilText("Comments");
		Thread.sleep(6000);
		
		boolean isEditEnable=ob.findElements(By.cssSelector("span[class='webui-icon webui-icon-edit'")).get(0).isDisplayed();
		System.out.println("profile edit Enabled-->"+isEditEnable);
		
		if(isEditEnable){
			ob.findElements(By.cssSelector("span[class='webui-icon webui-icon-edit'")).get(0).click();
			//clear and enter title or role
			ob.findElement(By.cssSelector("input[placeholder='Add your title or role']")).clear();
			ob.findElement(By.cssSelector("input[placeholder='Add your title or role']")).sendKeys(profileDetailsUpdate[0]);
			//clear and enter Primary Institution
			ob.findElement(By.cssSelector("input[placeholder='Add your primary institution']")).clear();
			ob.findElement(By.cssSelector("input[placeholder='Add your primary institution']")).sendKeys(profileDetailsUpdate[1]);
			//clear and enter country
			ob.findElement(By.cssSelector("input[placeholder='Add your country']")).clear();
			ob.findElement(By.cssSelector("input[placeholder='Add your country']")).sendKeys(profileDetailsUpdate[2]);
			
			boolean isUpdateEnable=ob.findElement(By.cssSelector("button[class='btn webui-btn-primary pull-right']")).isDisplayed();
			if(isUpdateEnable){
				ob.findElement(By.cssSelector("button[class='btn webui-btn-primary pull-right']")).click();
				Thread.sleep(6000);
			}
			else{
				status=2;
				test.log(LogStatus.FAIL,"Update button shoulb Enable:");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_update_button_should_be_enalbe")));
				throw new Exception("update button should  be enable");
			}
			
			//Validate Profile details updated or not
			ob.navigate().refresh();
			Thread.sleep(6000);
			String title=ob.findElement(By.cssSelector("div[class='meta ng-binding'][ng-show='role']")).getText();
			String primaryInstitution=ob.findElement(By.cssSelector("div[class='meta ng-binding'][ng-show='primaryInstitution']")).getText();
			String country=ob.findElement(By.cssSelector("div[class='meta ng-binding'][ng-show='location']")).getText();
			//System.out.println("Profile Entered Values-->"+title+"-->"+primaryInstitution+"-->"+country);
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "taking screenshot")));
			
			if (!(title.equalsIgnoreCase((profileDetailsUpdate[0]))
					&& primaryInstitution.equalsIgnoreCase(profileDetailsUpdate[1])
					&& country.equalsIgnoreCase(profileDetailsUpdate[2]))) {
				status=2;
				test.log(LogStatus.FAIL,"Entered Profile Details are not updated");
				throw new Exception("Entered Profile details are not updated");
			}
		}
		
		else{
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_Profile Details Edit should be Enable")));
			throw new Exception("Entered Profile details are not updated");
		}
	}
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "SKIP");
		//closeBrowser();
	}
	
	
	/**
	 * Method for wait TR Home Screen
	 * @throws InterruptedException 
	 */
	public  void waitForTRHomePage() throws InterruptedException {
		Thread.sleep(4000);
		//ob.waitUntilTextPresent(TestBase.OR.getProperty("tr_home_signInwith_projectNeon_css"),"Sign in with Project Neon");
	}
	
	/**
	 * Method for enter Application Url and enter Credentials
	 * @throws InterruptedException 
	 */
	public  void enterTRCredentials(String userName, String password) throws InterruptedException {
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_home_signInwith_projectNeon_css"))).click();
		Thread.sleep(10000);
		//waitUntilTextPresent(TestBase.OR.getProperty("tr_signIn_header_css"),"Thomson Reuters ID");
		//waitUntilTextPresent(TestBase.OR.getProperty("tr_signIn_login_css"),"Sign in");
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css"))).sendKeys(userName);
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_password_css"))).sendKeys(password);
	}
	
	public  void clickLogin() throws InterruptedException {
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_login_css"))).click();
		Thread.sleep(6000);
		//waitUntilTextPresent(TestBase.OR.getProperty("tr_home_css"), "Home");
		//waitUntilElementClickable("Home");
	}
	
}
