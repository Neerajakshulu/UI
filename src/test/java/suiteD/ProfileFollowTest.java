package suiteD;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import util.TestUtil;

/**
 * Class for follow profile in search page itself
 * @author UC202376
 *
 */
public class ProfileFollowTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	static String followBefore=null;
	static String followAfter=null;
	
	@BeforeTest
	public void beforeTest() {
		test = extent.startTest(this.getClass().getSimpleName(), "Profile Follow Validation Test").assignCategory("Suite D");
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
					
					ob.get(CONFIG.getProperty("devStable_url"));
					ob.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
					ob.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					waitForTRHomePage();
					enterTRCredentials(username, password);
					clickLogin();
				} catch (Throwable e) {
					test.log(LogStatus.FAIL,"Error: Login not happended"+e);
					ErrorUtil.addVerificationFailure(e);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_login_not_done")));//screenshot
				}
	}
	
	/**
	 * Method for find and follow others profile
	 * @throws IOException, When unable to follow others profile
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	public void followOthersProfile() throws IOException  {
			try {
				LoginTR.searchArticle(CONFIG.getProperty("find_profile_name"));
				clickPeople();
				followOtherProfile(CONFIG.getProperty("find_profile_complete_name"));
				test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,"Error:"+t);
				ErrorUtil.addVerificationFailure(t);
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
				//closeBrowser();
			}
	}
	/**
	 * Method for Click People link while searching an profile
	 * @throws Exception, When people link doesn't exist or disabled
	 */
	public void clickPeople() throws Exception {
		try {
			ob.findElement(By.xpath("//a[contains(text(), 'People')]")).click();
			Thread.sleep(2000);
		} catch (Exception e) {
			test.log(LogStatus.FAIL,"Error:"+e);
			ErrorUtil.addVerificationFailure(e);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			//closeBrowser();
		}
	}
	
	public void followOtherProfile(String profileName) throws Exception {
		List<WebElement> profiles=ob.findElements(By.cssSelector("div[class='webui-media-header h2']"));
		System.out.println("list of find profiles -->"+profiles.size());
		Assert.assertTrue(profiles.size()>0);
		
		
		
		for(WebElement profile:profiles){
			if(profile.findElement(By.cssSelector("span[class='webui-media-heading']")).findElement(By.tagName("a")).getText().trim().equalsIgnoreCase(profileName)){
				System.out.println("available text-->"+profile.findElement(By.cssSelector("span[class='webui-media-heading']")).findElement(By.tagName("a")).getText());
				List<WebElement> followProfiles = profile.findElements(By.tagName("span")).get(1).findElements(By.tagName("a"));
				for(WebElement followProfile:followProfiles){
					if(followProfile.isDisplayed()){
						followBefore=followProfile.getText();
						followProfile.click();
						Thread.sleep(2000);
						break;
					}
				}
				
				for(WebElement followProfile:followProfiles){
					if(followProfile.isDisplayed()){
						followAfter=followProfile.getText();
					}
				}
				//System.out.println("Before Follow Status-->"+followBefore);
				//System.out.println("After Follow Status-->"+followAfter);
				try{
					Assert.assertNotEquals(followBefore, followAfter);
					test.log(LogStatus.PASS, "Follow profile from Search Screen is working fine");
					}catch(Throwable t){
						test.log(LogStatus.INFO, "Error--->"+t);
						ErrorUtil.addVerificationFailure(t);	
						status=2;
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
							captureScreenshot(this.getClass().getSimpleName() + "Unable to follow the profile from search screen")));// screenshot
						
					}
			  }
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
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "SKIP");
		closeBrowser();
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
