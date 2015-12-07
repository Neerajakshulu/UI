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
		test = extent
				.startTest(this.getClass().getSimpleName(),
						"Verify that user is able to Start/Stop following a user from profile search results page")
				.assignCategory("Suite D");
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
					LoginTR.waitForTRHomePage();
					LoginTR.enterTRCredentials(username, password);
					LoginTR.clickLogin();
				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Error: Login not happended");
					//print full stack trace
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_login_not_done")));//screenshot
					closeBrowser();
				}
				
	}
	
	/**
	 * Method for find and follow others profile
	 * @throws Exception 
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	public void followOthersProfile() throws Exception  {
			try {
				LoginTR.searchArticle(CONFIG.getProperty("find_profile_name"));
				clickPeople();
				followOtherProfile(CONFIG.getProperty("find_profile_complete_name"));
				test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
				LoginTR.logOutApp();
				closeBrowser();

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
			//closeBrowser();
		}
	}
	
	public void followOtherProfile(String profileName) throws Exception {
		List<WebElement> profiles=ob.findElements(By.cssSelector("h4[class='webui-media-heading']"));
		System.out.println("list of find profiles -->"+profiles.size());
		Assert.assertTrue(profiles.size()>0);
		
		for(WebElement profile:profiles){
			System.out.println("Header Name-->"+profile.findElement(By.tagName("a")).getText());
			
			if(profile.findElement(By.tagName("a")).getText().trim().equalsIgnoreCase(profileName)) {
				
				WebElement followUnFollow=profile.findElement(By.cssSelector("span button[class='btn btn-link']"));
				followBefore=followUnFollow.getText();
				System.out.println("FOLLOW BEFORE-->"+followBefore);
				followUnFollow.click();
				Thread.sleep(3000);
				followAfter=profile.findElement(By.cssSelector("span button[class='btn btn-link']")).getText();
				System.out.println("FOLLOW AFTER-->"+followAfter);
				break;
				
			   }
			}
			
			if(followBefore.equals(followAfter)){
				test.log(LogStatus.FAIL, "Follow and UnFollow behaviour not giving expected result");
				status=2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "Unable to follow the profile from search screen")));// screenshot
				throw new Exception("Follow and UnFollow behaviour not giving expected result");
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
		//closeBrowser();
	}
	
}
