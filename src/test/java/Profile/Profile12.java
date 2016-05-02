package Profile;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Profile12 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;
	static String followBefore = null;
	static String followAfter = null;
	PageFactory pf = new PageFactory();

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent Reports
	 * @throws Exception, When Something unexpected
	 */
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('D'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var,
				"Verify that user is able to follow other profile and test for count of users following me")
				.assignCategory("Profile");
		runmodes = TestUtil.getDataSetRunmodes(profilexls, this.getClass().getSimpleName());
	}

	/**
	 * Method for wait TR Login Screen
	 * 
	 * @throws Exception, When TR Login screen not displayed
	 */
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

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			test.log(LogStatus.INFO, "Runmode for test set data set to no " + count);
			skip = true;
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts for data set #" + count + "--->");

		try {
			openBrowser();
			clearCookies();
			maximizeWindow();

			ob.navigate().to(System.getProperty("host"));
			pf.getLoginTRInstance(ob).waitForTRHomePage();
			Thread.sleep(6000);
			pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
			pf.getLoginTRInstance(ob).clickLogin();
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
									+ "_TR_Login_Not_happended")));
			closeBrowser();
		}
	}

	/**
	 * Method for User Own profile following other user's profile
	 * 
	 * @param profileName
	 * @param profileFullName
	 * @throws Exception, user not able to follow other users profile
	 */
	@Test(dependsOnMethods = "testLoginTRAccount")
	// public void getFollowingUsers() throws Exception {
	// try {pf.getProfilePageInstance(ob).
	// test.log(LogStatus.INFO,"get users who iam following ");
	// pf.getHFPageInstance(ob).clickProfileImage();
	// pf.getProfilePageInstance(ob).clickProfileLink();
	// pf.getProfilePageInstance(ob).getFollowingCount();
	// } catch (Throwable t) {
	// test.log(LogStatus.FAIL,"Something Unexpected");
	// //print full stack trace
	// StringWriter errors = new StringWriter();
	// t.printStackTrace(new PrintWriter(errors));
	// test.log(LogStatus.INFO,errors.toString());
	// ErrorUtil.addVerificationFailure(t);
	// status=2;//excel
	// test.log(LogStatus.INFO, "Snapshot below: " +
	// test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_following_count")));
	// closeBrowser();
	// }
	// }
	/**
	 * Method for find and follow others profile
	 * @throws Exception 
	 */
	// @Test(dependsOnMethods="getFollowingUsers")
	@Parameters("profileName")
	public void followOthersProfile(String profileName) throws Exception {
		try {
			test.log(LogStatus.INFO, "Search for Profile and follow/unfollow that profile from Search Page itself");
			pf.getSearchProfilePageInstance(ob).enterSearchKeyAndClick(profileName);
			if (pf.getSearchProfilePageInstance(ob).getPeopleCount() > 0) {
				pf.getSearchProfilePageInstance(ob).clickPeople();
				pf.getSearchProfilePageInstance(ob).followProfileFromSeach();
			} else {
				throw new SkipException("No Profiles to follow from SEARCH PEOPLE page");
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Error:" + t);
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_unable_to_follow")));// screenshot
			closeBrowser();
		}
	}

	/**
	 * Method for User Own profile following other user's profile
	 * @param profileName
	 * @param profileFullName
	 * @throws Exception, user not able to follow other users profile
	 */
	@Test(dependsOnMethods = "followOthersProfile")
	public void validateFollowingUsersCount() throws Exception {
		try {
			test.log(LogStatus.INFO, "Following count should be increased/decreased");
			pf.getHFPageInstance(ob).clickProfileImage();
			pf.getProfilePageInstance(ob).clickProfileLink();
			pf.getProfilePageInstance(ob).validateFollowingCount();
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
									+ "_following_count")));
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
	}
}
