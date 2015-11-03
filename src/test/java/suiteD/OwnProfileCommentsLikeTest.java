package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class OwnProfileCommentsLikeTest extends TestBase {
	
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
		test = extent.startTest(this.getClass().getSimpleName(), "Validate User Own Profile Comments Like Test").assignCategory("Suite D");
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
					test.log(LogStatus.FAIL,"Something Unexpected");
					//print full stack trace
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_profile_data_updation_not_done")));//screenshot
					closeBrowser();
				}
	}
	
	
	@Test(dependsOnMethods="testLoginTRAccount")
	public void validateOwnProfileCommentsLike() throws Exception  {
			try {
				ownProfileCommentsLike();
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
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_profile_data_updation_not_done")));//screenshot
				closeBrowser();
			}
	}
	
	
	/**
	 * Method for user Profile comments like
	 * @throws Exception, when comments like is not working as expected
	 */
	public void ownProfileCommentsLike() throws Exception  {
		ob.findElement(By.cssSelector(OR.getProperty("tr_profile_dropdown_css"))).click();
		BrowserWaits.waitUntilText("Profile");
		ob.findElement(By.linkText(OR.getProperty("tr_profile_link"))).click();
		Thread.sleep(4000);
		BrowserAction.scrollingPageDown();
		BrowserWaits.waitUntilText("Comments");
		Thread.sleep(6000);
		
		String totalProfileComments=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_APP_PROFILE_COMMENTS_CSS).getText();
		System.out.println("Total Profile Comments-->"+totalProfileComments);
		String totComments[]=totalProfileComments.split(" ");
		System.out.println("tc-->"+totComments[totComments.length-1]);
		if(Integer.parseInt(totComments[totComments.length-1]) > 0) {
			//click user own profile comments like button
			int beforeCommentLike=Integer.parseInt(getUserProfileComments().get(0).getText());
			//System.out.println("Before Comment like size-->"+beforeCommentLike);
			int afterCommentLike=0;
			String commnentLikeStatus=ob.findElements(By.cssSelector("span[class$='-liked ng-scope']")).get(0).getAttribute("ng-click");
		
			//comment is liked, if like the comment, size should Decrease  
			if(commnentLikeStatus.contains("NONE")) {
				Thread.sleep(2000);
				//System.out.println("Attribute-->"+getUserProfileComments().get(0).getAttribute("ng-click"));
				ob.findElements(By.cssSelector("span[class$='-liked ng-scope'] span")).get(0).click();
				Thread.sleep(2000);
				afterCommentLike=Integer.parseInt(getUserProfileComments().get(0).getText());
			   // System.out.println("After Comment like size-2222->"+afterCommentLike);
				if(!(beforeCommentLike>afterCommentLike)) {
					throw new Exception("Comments Like size not Decreased");
				}
			}
			
			//comment is not liked, if like the comment, size should increase 
			if(commnentLikeStatus.contains("UP")) {
				ob.findElements(By.cssSelector("span[class$='-liked ng-scope'] span")).get(0).click();
				Thread.sleep(4000);
				afterCommentLike=Integer.parseInt(getUserProfileComments().get(0).getText());
				//System.out.println("After Comment like size-->"+afterCommentLike);
				if(!(beforeCommentLike<afterCommentLike)) {
					throw new Exception("Comments Like size not increased");
				}
			 }
		}
	}
	
	/**
	 * Method for Get list of WebElements of user profile comments
	 * @return
	 * @throws Exception, Element not found
	 */
	public List<WebElement> getUserProfileComments() throws Exception {
		List<WebElement> commentsLike=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_OWN_PROFILE_COMMENTS_LIKE_XPATH);
		return commentsLike;
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
