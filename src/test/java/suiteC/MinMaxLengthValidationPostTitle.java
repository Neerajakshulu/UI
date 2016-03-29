package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.TestUtil;

public class MinMaxLengthValidationPostTitle extends TestBase{
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	static int time=30;
	PageFactory pf=new PageFactory();

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "CREATE POST: Verfiy that proper error messages are displyed for min max length validation of post title")
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
			//ob.get(CONFIG.getProperty("testSiteName"));
			loginAs("USERNAME1","PASSWORD1");
			test.log(LogStatus.INFO, "Logged in to NEON");
			pf.getHFPageInstance(ob).clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			pf.getProfilePageInstance(ob).clickOnPublishPostButton();
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
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}
	
	@Test(dependsOnMethods="testInitiatePostCreation",dataProvider="getTestData")
	public void testMinMaxLengthValidation(String titleMinError,String minCharCount,String maxCharCount) throws Exception {
	
	pf.getProfilePageInstance(ob).enterPostTitle(RandomStringUtils.randomAlphabetic(Integer.parseInt(minCharCount.substring(0,1))));
	test.log(LogStatus.INFO, "Entered Post Title of length:"+minCharCount);
	
	try {
		Assert.assertFalse(pf.getProfilePageInstance(ob).validatePublishButton());
		test.log(LogStatus.PASS, "Publish button is disabled for the post content less than min character count");
	} catch (Throwable t) {
		test.log(LogStatus.PASS, "Publish button is not disabled for the post content less than min character count");
		test.log(LogStatus.INFO, "Error--->" + t);
		ErrorUtil.addVerificationFailure(t);
		status = 2;
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
				this.getClass().getSimpleName() + "Post_title_validation_failed")));// screenshot

	}
	pf.getProfilePageInstance(ob).enterPostTitle(RandomStringUtils.randomAlphabetic(Integer.parseInt(maxCharCount.substring(0,3))));
	test.log(LogStatus.INFO, "Entered Post Title of length:"+maxCharCount);
	try {
		Assert.assertTrue(pf.getProfilePageInstance(ob).getLengthOfTitleFromPostCreationModal()==200);
		test.log(LogStatus.PASS, "Max char count validation for post title is successful");
	} catch (Throwable t) {
		test.log(LogStatus.FAIL, "Max char count validation for post title failed");
		test.log(LogStatus.INFO, "Error--->" + t);
		ErrorUtil.addVerificationFailure(t);
		status = 2;
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
				this.getClass().getSimpleName() + "Post_title_validation_failed")));// screenshot

	}
	pf.getProfilePageInstance(ob).clickOnPostCancelButton();
	pf.getProfilePageInstance(ob).clickOnPostCancelDiscardButton();
	logout();
	closeBrowser();
	}
	@Test(dependsOnMethods="testMinMaxLengthValidation")
	public void reportDataSetResult() {
		/*if(skip)
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "SKIP");
		
		else if(fail) {
			
			status=2;
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "PASS");
		*/
		
		skip=false;
		fail=false;

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*if (status == 1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "SKIP");*/

	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suiteCxls, this.getClass().getSimpleName()) ;
	}
}
