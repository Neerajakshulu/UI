package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.HeaderFooterLinksPage;
import pages.ProfilePage;
import util.ErrorUtil;
import util.TestUtil;

public class PostContentProfanityWordCheckTest extends TestBase{
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	static int time=30;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verfiy that profanity words are not allowed in post content")
				.assignCategory("Suite C");
		runmodes=TestUtil.getDataSetRunmodes(suiteCxls, this.getClass().getSimpleName());
	}

	
	@Test
	public void testInitiatePostCreation() throws Exception {
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteCxls,this.getClass().getSimpleName());
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
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			loginAs("USERNAME1","PASSWORD1");
			test.log(LogStatus.INFO, "Logged in to NEON");
			HeaderFooterLinksPage.clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			ProfilePage.clickOnPublishPostButton();
			test.log(LogStatus.INFO, "Initiated post creation");
			} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			status = 2;// excel
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng

			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}
	
	}
	
	@Test(dependsOnMethods="testInitiatePostCreation",dataProvider="getTestData")
	public void testMinMaxLengthValidation(String profanityWord,String errorMessage) throws Exception {
	ProfilePage.enterPostTitle("Test");
	test.log(LogStatus.INFO, "Entered Post title");
	ProfilePage.enterPostContent(profanityWord);
	test.log(LogStatus.INFO, "Entered profanity word in Post Content : "+profanityWord);
	ProfilePage.clickOnPostPublishButton();
	try {
		Assert.assertTrue(ProfilePage.validatePostErrorMessage(errorMessage));
		test.log(LogStatus.PASS, "Proper error message is displayed for profanity check for post content");
	} catch (Throwable t) {
		test.log(LogStatus.FAIL, "Proper error message is not displayed for profanity check for post content");
		test.log(LogStatus.INFO, "Error--->" + t);
		ErrorUtil.addVerificationFailure(t);
		status = 2;
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
				this.getClass().getSimpleName() + "Post_title_validation_failed")));// screenshot

	}
		
	try {
		Assert.assertTrue(ProfilePage.validateProfanityWordsMaskedForPostContent(profanityWord));
		test.log(LogStatus.PASS, "Profanity words are masked for post content");
	} catch (Throwable t) {
		test.log(LogStatus.FAIL, "Profanity words are masked for post content");
		test.log(LogStatus.INFO, "Error--->" + t);
		ErrorUtil.addVerificationFailure(t);
		status = 2;
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
				this.getClass().getSimpleName() + "Post_title_validation_failed")));// screenshot

	}
	}
	
	@Test(dependsOnMethods="testMinMaxLengthValidation")
	public void logOut() throws Exception{
		ProfilePage.clickOnPostCancelButton();
		logout();
		closeBrowser();
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}
	@Test(dependsOnMethods="logOut")
	public void reportDataSetResult() {
		if(skip)
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "SKIP");
		
		else if(fail) {
			
			status=2;
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "PASS");
		
		
		skip=false;
		fail=false;

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "SKIP");

	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suiteCxls, "PostProfanityWordCheckTest") ;
	}
}
