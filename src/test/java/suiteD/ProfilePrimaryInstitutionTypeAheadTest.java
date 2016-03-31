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
import util.ExtentManager;
import util.TestUtil;

/**
 * Profile Primary Institution Typeahead Validation
 * @author UC202376
 *
 */
public class ProfilePrimaryInstitutionTypeAheadTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	PageFactory pf=new PageFactory();
	
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);
		String var=xlRead2(returnExcelPath('D'),this.getClass().getSimpleName(),1);
		test = extent
				.startTest(var,"Verify that user is able to add 'primary institution' using type ahead").assignCategory("Suite D");
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
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts ");
		
				try {
					openBrowser();
					clearCookies();
					maximizeWindow();
					
					ob.navigate().to(System.getProperty("host"));
					pf.getLoginTRInstance(ob).waitForTRHomePage();
					pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
					pf.getLoginTRInstance(ob).clickLogin();
				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Something Unexpected");
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
	 * Method for profile Primary institution Validation
	 * @param piTypeahead
	 * @throws Exception
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	@Parameters("piTypeahead")
	public void profilePITypeaheadUpdate(String piTypeahead) throws Exception  {
			try {
				test.log(LogStatus.INFO," Select Primary Institution from predefined Typeahead list");
				pf.getHFPageInstance(ob).clickProfileImage();
				pf.getProfilePageInstance(ob).clickProfileLink();
				pf.getProfilePageInstance(ob).selectProfilePITypeAhead(piTypeahead);
				boolean piStatus=pf.getProfilePageInstance(ob).validateProfilePI(piTypeahead);
				if(!piStatus){
					throw new Exception("Profile Primary Instituion is not updated with Predefined typeahead list values");
				}
				test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
				pf.getLoginTRInstance(ob).logOutApp();
				closeBrowser();
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,"Something Unexpected");
				status=2;//excel
				//print full stack trace
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO,errors.toString());
				ErrorUtil.addVerificationFailure(t);
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_PI_TYPEAHEAD_FAIL")));//screenshot
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
