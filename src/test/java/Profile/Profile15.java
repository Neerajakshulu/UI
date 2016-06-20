package Profile;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

/**
 * Class for profile search with last name
 * 
 * @author UC202376
 *
 */
public class Profile15 extends TestBase {

	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;
	
	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent Reports
	 * @throws Exception, When Something unexpected
	 */
	
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('D'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verify Profile Search with last name").assignCategory("Profile");
	}

	@Test
	@Parameters({"username", "password"})
	public void testLoginTRAccount(String username,
			String password) throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Profile");
		boolean testRunmode = TestUtil.isTestCaseRunnable(profilexls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;System.out.println("checking master condition status-->"+this.getClass().getSimpleName()+"-->"+master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts for data set ");

		try {
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(System.getProperty("host"));

			pf.getLoginTRInstance(ob).waitForTRHomePage();
			pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
			pf.getLoginTRInstance(ob).clickLogin();
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Error:Login_not_done");
			ErrorUtil.addVerificationFailure(e);
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_unable_to_find_others_profile")));
			closeBrowser();
		}

	}

	/**
	 * Method for find other profile with their last name
	 * 
	 * @throws Exception
	 */
	@Test(dependsOnMethods = "testLoginTRAccount")
	@Parameters("lastName")
	public void findOthersProfileWithLastName(String lastName) throws Exception {
		try {
			pf.getSearchProfilePageInstance(ob).enterSearchKeyAndClick(lastName);
			if (pf.getSearchProfilePageInstance(ob).getPeopleCount() > 0) {
				pf.getSearchProfilePageInstance(ob).clickPeople();
				test.log(LogStatus.INFO, "validate populated search profile results having provided last name");
				pf.getProfilePageInstance(ob).validateProfileLastName(lastName);
			}
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something Unexpected");
			// print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_unable_to_find_others_profile_with_their_lastname")));
			closeBrowser();
		}
	}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL or SKIP
	 */
	@AfterTest
	public void reportTestResult() {

		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "SKIP");
		 */
		// closeBrowser();
	}

}
