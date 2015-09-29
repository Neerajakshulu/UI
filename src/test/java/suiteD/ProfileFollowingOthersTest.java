package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
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
import util.OnePObjectMap;
import util.TestUtil;

public class ProfileFollowingOthersTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	static String followBefore=null;
	static String followAfter=null;
	
	
	@BeforeTest
	public void beforeTest() {
		test = extent.startTest(this.getClass().getSimpleName(),
				"Validate User Own Profile following other profile").assignCategory("Suite D");
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
					Thread.sleep(6000);
					LoginTR.enterTRCredentials(username, password);
					LoginTR.clickLogin();
				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Something Unexpected");
					//print full stack trace
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_TR_Login_Not_happended")));
					closeBrowser();
				}
	}
	
	/**
	 * Method for User Own profile following other user's profile
	 * @param profileName
	 * @param profileFullName
	 * @throws Exception, user not able to follow other users profile
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	@Parameters({"profileName","profileFullName"})
	public void followOthersProfile(String profileName,String profileFullName) throws Exception  {
			try {
				
				follwingOthersProfile(profileName,profileFullName);
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
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_profile_Interests and Skills not Updated")));
				closeBrowser();
			}
	}
	
	
	/**
	 * Method for follow others users profile
	 * @throws Exception, user not able to follow other users profile
	 */
	public void follwingOthersProfile(String profileName,String profileFullName) throws Exception  {
		ob.findElement(By.cssSelector(OR.getProperty("tr_profile_dropdown_css"))).click();
		BrowserWaits.waitUntilText("Profile");
		ob.findElement(By.linkText(OR.getProperty("tr_profile_link"))).click();
		Thread.sleep(4000);
		
		String following=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_FOLLOWING_CSS).get(2).getText();
		String followArr[]=following.split(" ");
		int totalBeforeFollowing=Integer.parseInt(followArr[followArr.length-1]);
		//System.out.println("Following Total-->"+totalBeforeFollowing);
		
		LoginTR.searchArticle(profileName);
		new ProfileFollowTest().clickPeople();
		
		List<WebElement> profiles=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		//System.out.println("list of find profiles -->"+profiles.size());
		Assert.assertTrue(profiles.size()>0);
		
		for(WebElement profile:profiles){
			if(profile.findElement(By.tagName("a")).getText().trim().equalsIgnoreCase(profileFullName)){
				List<WebElement> followProfiles = profile.findElements(By.tagName("span")).get(0).findElements(By.tagName("button"));
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
						break;
					}
				}
			}
		}
		
		ob.findElement(By.cssSelector(OR.getProperty("tr_profile_dropdown_css"))).click();
		BrowserWaits.waitUntilText("Profile");
		ob.findElement(By.linkText(OR.getProperty("tr_profile_link"))).click();
		Thread.sleep(4000);
		
		String followingAfter=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_FOLLOWING_CSS).get(2).getText();
		String followArrAfter[]=followingAfter.split(" ");
		int totalAfterFollowing=Integer.parseInt(followArrAfter[followArrAfter.length-1]);
		System.out.println("Following AfterTotal-->"+totalAfterFollowing);
		
		Assert.assertNotEquals(totalBeforeFollowing, followingAfter);
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
