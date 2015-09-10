package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;

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
import util.BrowserAction;
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;

public class AppHeaderFooterLinkValidationTest extends TestBase {
	
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
		test = extent.startTest(this.getClass().getSimpleName(), "Validate Application Header and Footer Links").assignCategory("Suite D");
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
	 * Method for Validate Application Header and Footer links
	 * Header Link - Help link
	 * Footer Links - Cookie Policy,Privacy Statement,Terms of Use 
	 * @throws Exception, When validation not done and Element Not found 
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	@Parameters("appHeadFooterLinks")
	public void validateAppHeaderFooterLinks(String appHeadFooterLinks) throws Exception  {
			try {
				
				String headerFooterLinks[]=appHeadFooterLinks.split("\\|");
				for(int i=0;i<headerFooterLinks.length;i++) {
					BrowserAction.scrollToElement(OnePObjectMap.HOME_PROJECT_NEON_APP_FOOTER_LINKS_CSS);
					
					if(headerFooterLinks[i].equalsIgnoreCase("Help")){
						ob.findElement(By.xpath("//span[contains(text(),'" +headerFooterLinks[i]+"')]")).click();
						BrowserWaits.waitUntilText(headerFooterLinks[i]);
						Thread.sleep(6000);
						String helpText=ob.findElement(By.cssSelector("h2[class='spacing-top uppercase']")).getText();
						Assert.assertEquals(headerFooterLinks[i].toUpperCase(), helpText);
					}
					else{
						ob.findElement(By.xpath("//a[contains(text(),'" +headerFooterLinks[i]+"')]")).click();
						BrowserWaits.waitUntilText(headerFooterLinks[i]);
						Thread.sleep(6000);
					}
					
					if(headerFooterLinks[i].equalsIgnoreCase("Cookie Policy")){
						String cookieText=ob.findElement(By.cssSelector("h3[class='uppercase spacing-top']")).getText();
						Assert.assertEquals(headerFooterLinks[i].toUpperCase(), cookieText);
					}
					
					else if (headerFooterLinks[i].equalsIgnoreCase("Privacy Statement")){
						String psText=ob.findElement(By.cssSelector("h3[class='uppercase']")).getText();
						Assert.assertEquals(headerFooterLinks[i].toUpperCase(), psText);
					}
					
					else if (headerFooterLinks[i].equalsIgnoreCase("Terms of Use")){
						String tcText=ob.findElement(By.cssSelector("h3[class='uppercase']")).getText();
						Assert.assertEquals(headerFooterLinks[i].toUpperCase(), tcText);
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
