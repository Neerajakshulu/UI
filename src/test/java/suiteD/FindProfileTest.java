package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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
import util.BrowserAction;
import util.BrowserWaits;
import util.ErrorUtil;
import util.TestUtil;

/**
 * Class for find and follow others profile
 * @author UC202376
 *
 */
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
		
				try {
					openBrowser();
					clearCookies();
					maximizeWindow();
					ob.navigate().to(System.getProperty("host"));
					LoginTR.waitForTRHomePage();
					LoginTR.enterTRCredentials(username, password);
					LoginTR.clickLogin();
				} catch (Throwable e) {
					test.log(LogStatus.FAIL,"Error:"+e);
					ErrorUtil.addVerificationFailure(e);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_unable_to_find_others_profile")));
					closeBrowser();
				}
				
	}
	
	/**
	 * Method for find and follow others profile
	 * @throws Exception
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	public void findOthersProfile() throws Exception  {
				try {
					//searchArticle(CONFIG.getProperty("find_profile_name"));
					LoginTR.searchArticle(CONFIG.getProperty("find_profile_name"));
					clickPeople();
					chooseOtherProfile(CONFIG.getProperty("find_profile_complete_name"));
					clickOtherProfileEdit();
					checkFollowOtherProfile();
					test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
					LoginTR.logOutApp();
					closeBrowser();

				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Something Unexpected");
					//print full stack trace
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());
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
			Thread.sleep(4000);
		} catch (Exception e) {
			System.out.println("closing browser--->");
			test.log(LogStatus.FAIL,"Error:"+e);
			ErrorUtil.addVerificationFailure(e);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			//closeBrowser();
		}
	}
	
	
	/**
	 * Method for Search Other profiles
	 * @param profileName
	 * @throws Exception
	 */
	public void chooseOtherProfile(String profileName) throws Exception {
		List<WebElement> profiles=ob.findElements(By.cssSelector("h4[class='webui-media-heading']"));
		System.out.println("list of find profiles -->"+profiles.size());
		for(WebElement profile:profiles) {
			if(profile.findElement(By.tagName("a")).getText().trim().equalsIgnoreCase(profileName)) {
				profile.findElement(By.tagName("a")).click();
				//BrowserWaits.waitUntilText(profileName);
				Thread.sleep(6000);
				break;
				}
			}
		
		System.out.println("profile text-->"+ob.findElement(By.cssSelector("span[class='headline ng-binding']")).getText());
		if(!ob.findElement(By.cssSelector("span[class='headline ng-binding']")).getText().trim().equalsIgnoreCase(profileName)){
			test.log(LogStatus.FAIL,"Error:");
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_Profile details are not accurate")));//screenshot
			throw new Exception("Other Profiles details not accurate");
		}
	}
	
	/**
	 * Method for Validate Other Profiles Edit button is Enabled
	 * @throws Exception, When Edit button is Enabled
	 */
	public void clickOtherProfileEdit() throws Exception {
		String editOtherProfileEnable=ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-edit']")).getText();
		boolean isEditEnable=ob.findElement(By.cssSelector("span[class='webui-icon webui-icon-edit']")).isDisplayed();
		if(editOtherProfileEnable.contains("before") && isEditEnable){
			test.log(LogStatus.FAIL,"Error:");//extent reports
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_other profiles should not have edit option")));//screenshot
			throw new Exception("Other Profiles Edit should not be visible");
		}
	}
	/**
	 * Method for Follow Other Profile users
	 * @throws Exception
	 */
	public void checkFollowOtherProfile() throws Exception {
		try {
			
			followBefore=ob.findElement(By.xpath("//button[@class='btn btn-link fadeinout ng-scope follow']")).getAttribute("tooltip");
			
			System.out.println("profile tooltip-->"+followBefore);
			
			ob.findElement(By.xpath("//button[@class='btn btn-link fadeinout ng-scope follow']/descendant::span")).click();
			Thread.sleep(2000);
			
			followAfter=ob.findElement(By.xpath("//button[@class='btn btn-link fadeinout ng-scope follow']")).getAttribute("tooltip");
			
			System.out.println("Follow Status before and After-->"+followBefore+"--AFTER-->"+followAfter);
		
			Assert.assertNotEquals(followBefore, followAfter);
			
		} catch (Exception e) {
			test.log(LogStatus.FAIL,"Error:");
			ErrorUtil.addVerificationFailure(e);
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_Other Profiles Follow and Unfollow Operations not giving expected result")));//screenshot
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
	
	public  void searchArticle(String article) throws Exception {
		//waitUntilElementClickable("Watchlist");
		try {
			System.out.println("article name-->"+article);
			ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_search_box_css"))).sendKeys(article);
			Thread.sleep(4000);
			List<WebElement> searchElements=ob.findElement(By.cssSelector("ul[class='dropdown-menu ng-isolate-scope']")).findElements(By.tagName("li"));
			System.out.println("list of articles-->"+searchElements.size());
			for(WebElement searchElement:searchElements){
				String articleName=searchElement.findElement(By.tagName("a")).getText();
				System.out.println("article name-->"+articleName);
				if(searchElement.findElement(By.tagName("a")).getText().equalsIgnoreCase(articleName)){
					//System.out.println("name changes done");
					WebElement element = searchElement.findElement(By.tagName("a"));
					JavascriptExecutor executor = (JavascriptExecutor)ob;
					executor.executeScript("arguments[0].click();", element);
					//searchElement.findElement(By.tagName("a")).click();
					Thread.sleep(4000);
					BrowserAction.scrollingPageUp();
					break;
				}//if
			}//for
			Thread.sleep(4000);
		} catch (Throwable e) {
			test.log(LogStatus.FAIL,"Error:"+e);
			ErrorUtil.addVerificationFailure(e);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_unable_to_find_others_profile")));
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
	
}
