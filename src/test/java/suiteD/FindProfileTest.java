package suiteD;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class FindProfileTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	static String followBefore=null;
	static String followAfter=null;
	static boolean isFollowEnable=false;
	static boolean isFollowDisable=false;
	
	
	@BeforeTest
	public void beforeTest() {
		test = extent.startTest(this.getClass().getSimpleName(), "Find Profile Validation Test").assignCategory("Suite D");
		runmodes=TestUtil.getDataSetRunmodes(suiteDxls, this.getClass().getSimpleName());
	}
			
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
	 * Method for Find others Profile
	 * @param username
	 * @param password
	 * @param article
	 * @param completeArticle
	 * @param addComments
	 * @throws Exception, When not able to find others profiles
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	public void findOthersProfile() throws Exception  {
				try {
					LoginTR.searchArticle(CONFIG.getProperty("find_profile_name"));
					clickPeople();
					chooseOtherProfile(CONFIG.getProperty("find_profile_complete_name"));
					clickOtherProfileEdit();
					checkFollowOtherProfile();
					test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Error:"+t);
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_unable_to_find_others_profile")));
					closeBrowser();
				}
	}
	
	/**
	 * Method for Click People after searching an profile
	 * @throws Exception, When People are not present/Disabled
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
	
	
	/**
	 * Method for Search Other profiles
	 * @param profileName
	 * @throws Exception
	 */
	public void chooseOtherProfile(String profileName) throws Exception {
		List<WebElement> profiles=ob.findElements(By.cssSelector("div[class='webui-media-header h2']"));
		System.out.println("list of find profiles -->"+profiles.size());
		for(WebElement profile:profiles){
			if(profile.findElements(By.tagName("span")).get(0).getText().equalsIgnoreCase(profileName)){
				profile.findElements(By.tagName("span")).get(0).click();
				Thread.sleep(4000);
				break;
				}
			}
		if(!ob.findElement(By.cssSelector("div[class^='col-xs-11 col-md-8']")).getText().trim().equalsIgnoreCase(profileName)){
			test.log(LogStatus.FAIL,"Error:");
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			throw new Exception("Other Profiles details not accurate");
		}
	}
	
	/**
	 * Method for Validate Other Profiles Edit button is Enabled
	 * @throws Exception, When Edit button is Enabled
	 */
	public void clickOtherProfileEdit() throws Exception {
		String editOtherProfileEnable=ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-edit'")).getText();
		boolean isEditEnable=ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-edit'")).isDisplayed();
		if(editOtherProfileEnable.contains("before") && isEditEnable){
			test.log(LogStatus.FAIL,"Error:");//extent reports
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			throw new Exception("Other Profiles Edit should not be visible");
		}
	}
	/**
	 * Method for Follow Other Profile users
	 * @throws Exception
	 */
	public void checkFollowOtherProfile() throws Exception {
		String editOtherProfileEnable=ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-follow']")).getText();
		isFollowEnable=ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-follow']")).isDisplayed();
		
		System.out.println("follow Text before-->"+editOtherProfileEnable);
		System.out.println("IS editable before-->"+isFollowEnable);
		
		//if user is unfollow
		if(!isFollowEnable){
			ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-follow unfollow'")).click();
			Thread.sleep(4000);
		}
		
		ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-follow'")).click();
		
		String editOtherProfileDisable=ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-follow']")).getText();
		System.out.println("follow Text after-->"+editOtherProfileDisable);
		isFollowDisable=ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-follow']")).isDisplayed();
		System.out.println("is editable after-->"+isFollowDisable);
		
		if(isFollowEnable && isFollowDisable) {
			test.log(LogStatus.FAIL,"Error:");//extent reports
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			throw new Exception("Unable to Follow Other User");
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
		
	}
}
