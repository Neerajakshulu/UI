package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.HeaderFooterLinksPage;
import pages.ProfilePage;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class VerifyDraftPostDisplayInUserOwnProfile extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify that Draft Post tab is displayed only in the users own profile and only when the user has at least one draft post")
				.assignCategory("Suite C");

	}
	
	
	@Test
	public void testEditDraftsFromModalWindow() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteCxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			String postString="PostCreationTest"+RandomStringUtils.randomNumeric(10);
			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			//ob.get(CONFIG.getProperty("testSiteName"));

			LoginTR.enterTRCredentials(CONFIG.getProperty("defaultUsername"), CONFIG.getProperty("defaultPassword"));
			LoginTR.clickLogin();
			Thread.sleep(8000);
			test.log(LogStatus.INFO, "Logged in to NEON");
			HeaderFooterLinksPage.clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			int postCountBefore=ProfilePage.getDraftPostsCount();
			test.log(LogStatus.INFO, "Post count:"+postCountBefore);
			Thread.sleep(5000);
			ProfilePage.clickOnPublishPostButton();
			ProfilePage.enterPostTitle(postString);
			test.log(LogStatus.INFO, "Entered Post Title");
			ProfilePage.enterPostContent(postString);
			test.log(LogStatus.INFO, "Entered Post Content");
			ProfilePage.clickOnPostCancelButton();
			ProfilePage.clickOnPostCancelKeepDraftButton();
			test.log(LogStatus.INFO, "Saved the Post as a draft");
			int postCountAfter=ProfilePage.getDraftPostsCount();
			test.log(LogStatus.INFO, "Post count:"+postCountAfter);
			ProfilePage.clickOnDraftPostsTab();
			String postTitle=ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_DRAFT_POST_FIRST_TITLE_XPATH.toString()))
					.getText();
			try {
				Assert.assertTrue(postCountAfter==(postCountBefore+1) && postString.equals(postTitle));
				test.log(LogStatus.PASS, "Draft Post section is present in the user profile");
				ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SECTION_HEADING_LABEL.toString())).click();
				Thread.sleep(4000);
				ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SELECT_PEOPLE_FOR_SEARCH_IN_DROPDOWN_XPATH.toString())).click();
				Thread.sleep(3000);
				test.log(LogStatus.INFO, "Searching for someother users");
				ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_TEXTBOX_XPATH.toString())).sendKeys("Sachin Traveller");
				Thread.sleep(2000);
				ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_BUTTON_XPATH.toString())).click();
				Thread.sleep(4000);
				ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_PEOPLE_LINK.toString())).click();
				
				boolean isPresent=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_DRAFT_POST_COUNT_CSS.toString())).isDisplayed();
				Assert.assertEquals(false, isPresent);
				test.log(LogStatus.INFO, "Draft posts are not visible");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Draft Post section is not present in the user profile/ it is displayed in other user profile");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Post_count_validation_failed")));// screenshot

			}
			 Thread.sleep(5000);					
			logout();
			closeBrowser();
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
}
