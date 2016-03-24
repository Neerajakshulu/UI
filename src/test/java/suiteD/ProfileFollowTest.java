package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.TestUtil;

/**
 * Class for follow/unfollow  profile from search page itself
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
	PageFactory pf=new PageFactory();
	
	@BeforeTest
	public void beforeTest() throws  Exception {
		
		String var=xlRead2(returnExcelPath('D'),this.getClass().getSimpleName(),1);
		//System.out.println("column name-->"+var);
		test = extent.startTest(var,
						"1.Verify that user is able to Start/Stop following a user from profile search results page \n"
					   +"2.Verify that user is able to search for profiles")
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
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts ");
		
				try {
					openBrowser();
					clearCookies();
					maximizeWindow();
					test.log(LogStatus.INFO,"Login to Applicaton with TR valid Credentials");
					ob.navigate().to(System.getProperty("host"));
					/*LoginTR lr=new LoginTR(ob);
					lr.waitForTRHomePage();
					lr.enterTRCredentials(username, password);
					lr.clickLogin();*/
					
					
					pf.getLoginTRInstance(ob).waitForTRHomePage();
					pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
					pf.getLoginTRInstance(ob).clickLogin();
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
	@Parameters("profileName")
	public void followOthersProfile(String profileName) throws Exception  {
			try {
				test.log(LogStatus.INFO,"Search for Profile and follow/unfollow that profile from Search Page itself");
				pf.getSearchProfilePageInstance(ob).enterSearchKeyAndClick(profileName);
				if(pf.getSearchProfilePageInstance(ob).getPeopleCount()>0) {
					pf.getSearchProfilePageInstance(ob).clickPeople();
					pf.getSearchProfilePageInstance(ob).followProfileFromSeach();
				}
				
				pf.getLoginTRInstance(ob).logOutApp();
				closeBrowser();

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,"Error:"+t);
				status=2;//excel
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO,errors.toString());
				ErrorUtil.addVerificationFailure(t);
				
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_unable_to_follow")));//screenshot
				closeBrowser();
			}
	}
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
		/*if(status==1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "SKIP");*/
	}
	
}
