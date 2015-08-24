package suiteD;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

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
		
				openBrowser();
				clearCookies();
				maximizeWindow();
				
				ob.get(CONFIG.getProperty("devSnapshot_url"));
				ob.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				ob.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				LoginTR.waitForTRHomePage();
				LoginTR.enterTRCredentials(username, password);
				LoginTR.clickLogin();
	}
	
	/**
	 * Method for find and follow others profile
	 * @throws IOException, When unable to follow others profile
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	public void followOthersProfile() throws IOException  {
			try {
				LoginTR.searchArticle(CONFIG.getProperty("profile_name"));
				clickPeople();
				followOtherProfile(CONFIG.getProperty("profile_complete_name"));
				test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,"Error:"+t);
				ErrorUtil.addVerificationFailure(t);
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
				closeBrowser();
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
			closeBrowser();
		}
	}
	
	public void followOtherProfile(String profileName) throws Exception {
		List<WebElement> profiles=ob.findElements(By.cssSelector("div[class='webui-media-header h2']"));
		//System.out.println("list of find profiles -->"+profiles.size());
		for(WebElement profile:profiles){
			if(profile.findElements(By.tagName("span")).get(0).getText().trim().equalsIgnoreCase(profileName)){
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
				
				if(followBefore.equalsIgnoreCase(followAfter)){
					status=2;
					throw new Exception("User is unable to follow another user");
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
	
	
}
