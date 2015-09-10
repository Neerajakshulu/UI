package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;
import util.BrowserAction;

public class ApplicationLinksValidationTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	static String followBefore=null;
	static String followAfter=null;
	static String profileHeadingName;
	static String profileDetailsName;
	static String PARENT_WINDOW_HANDLE=null;
	
	@BeforeTest
	public void beforeTest() {
		test = extent.startTest(this.getClass().getSimpleName(), "View Profile Test").assignCategory("Suite D");
		runmodes=TestUtil.getDataSetRunmodes(suiteDxls, this.getClass().getSimpleName());
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
					enterTRCredentials(username, password);
					clickLogin();
				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Something Unexpected");//extent reports
					//print stack trace
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());
					ErrorUtil.addVerificationFailure(t);//testng
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_Login is not done")));
					closeBrowser();
				}
	}
	
	/**
	 * Method for Validate Application links
	 * @throws Exception, When validation not done and Element Not found 
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	@Parameters("appLinks")
	public void validateAppLinks(String appLinks) throws Exception  {
			try {
				String []totalAppLinks=appLinks.split("\\|");
				for(int i=0;i<totalAppLinks.length;i++) {
					BrowserAction.click(OnePObjectMap.HOME_ONEP_APPS_CSS);
					PARENT_WINDOW_HANDLE = ob.getWindowHandle();
					ob.findElement(By.linkText(totalAppLinks[i])).click();
					Thread.sleep(6000);
					maximizeWindow();
					Set<String> child_window_handles= ob.getWindowHandles();
					 for(String child_window_handle:child_window_handles) {
						 if(!child_window_handle.equals(PARENT_WINDOW_HANDLE)) {
							 ob.switchTo().window(child_window_handle);
							 String appLinkText=ob.findElement(By.cssSelector("h1[class='heading-1']")).getText();
							 Assert.assertEquals(totalAppLinks[i], appLinkText);
							 ob.close();
							 ob.switchTo().window(PARENT_WINDOW_HANDLE);
					}
				 }
				}
				LoginTR.logOutApp();
				closeBrowser();
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,"UnExpected Error");
				//print stack trace
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO,errors.toString());
				ErrorUtil.addVerificationFailure(t);
				status=2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_Application links are not working")));
				LoginTR.logOutApp();
				closeBrowser();
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
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css"))).clear();
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
