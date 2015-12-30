package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.BrowserAction;
import util.BrowserWaits;
import util.ErrorUtil;
import util.TestUtil;

public class ProfileFollowerTest extends TestBase {
	private static final String PASSWORD = "Welcome123";
	private static final String USER_NAME = "kavya.revanna@thomsonreuters.com";
	private static final String PROFILE_NAME = "Kavya Revanna";
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('D'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var,
				"Verify that user is able to test for count of users following me").assignCategory("Suite D");
	}

	@Test
	public void testFollowersTab() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "D Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteDxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			Thread.sleep(8000);
			login();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(PROFILE_NAME, Keys.ENTER);
			Thread.sleep(3000);
			BrowserAction.scrollingPageUp();
			waitForElementTobePresent(ob, By.xpath(OR.getProperty("tr_search_people_tab_xpath")), 20);
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_tab_xpath"))).click();
			try {

				ob.findElement(By.xpath(
						OR.getProperty("tr_search_people_profile_unfollow_link").replace("PROFILE_NAME", PROFILE_NAME)))
						.click();

			} catch (Exception e1) {
				test.log(LogStatus.INFO, "user is not a follower");
			}
			LoginTR.logOutApp();
			ob.navigate().to(host);
			Thread.sleep(3000);
			loginAsOther(USER_NAME, PASSWORD);
			Thread.sleep(15000);
			ob.findElement(By.cssSelector(OR.getProperty("tr_profile_dropdown_css"))).click();
			BrowserWaits.waitUntilText("Profile");
			ob.findElement(By.linkText(OR.getProperty("tr_profile_link"))).click();
			int followersBefore = -1;
			try {
				waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_profile_followers_tab_css")), 10);
				String strFllowersBefore = ob
						.findElement(By.cssSelector(OR.getProperty("tr_profile_followers_tab_css"))).getText();
				strFllowersBefore = strFllowersBefore.substring(strFllowersBefore.indexOf(" ")).trim();
				followersBefore = Integer.parseInt(strFllowersBefore);
			} catch (Exception e2) {
				followersBefore = 0;
			}
			
			LoginTR.logOutApp();
			
			ob.navigate().to(host);
			ob.get(CONFIG.getProperty("testSiteName"));
			login();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(PROFILE_NAME, Keys.ENTER);
			Thread.sleep(3000);
			BrowserAction.scrollingPageUp();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_tab_xpath")), 20);
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_tab_xpath"))).click();
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_people_tab_xpath")), 20);
			jsClick(ob, ob.findElement(By.xpath(
					OR.getProperty("tr_search_people_profile_follow_link").replace("PROFILE_NAME", PROFILE_NAME))));
			
			LoginTR.logOutApp();
			
			Thread.sleep(5000);
			ob.navigate().to(host);
			loginAsOther(USER_NAME, PASSWORD);
			Thread.sleep(15000);
			ob.findElement(By.cssSelector(OR.getProperty("tr_profile_dropdown_css"))).click();
			BrowserWaits.waitUntilText("Profile");
			ob.findElement(By.linkText(OR.getProperty("tr_profile_link"))).click();
			ob.navigate().refresh();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_profile_followers_tab_css")), 20);
			String strFllowersAfter = ob.findElement(By.cssSelector(OR.getProperty("tr_profile_followers_tab_css")))
					.getText();
			strFllowersAfter = strFllowersAfter.substring(strFllowersAfter.indexOf(" ")).trim();
			int followersAfter = Integer.parseInt(strFllowersAfter);

			try {
				Assert.assertTrue(followersAfter == followersBefore + 1);
				test.log(LogStatus.PASS, "followers count validation success!!!");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,"followers count validation Failed");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "followers count validation")));// screenshot

			}
			closeBrowser();

		}

		catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
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
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "SKIP");

	}

	public void loginAsOther(String username, String pwd) throws Exception {
		ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
		Thread.sleep(4000);
		waitForElementTobeVisible(ob, By.id(OR.getProperty("TR_email_textBox")), 30);
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(username);
		ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(pwd);
		ob.findElement(By.id(OR.getProperty("login_button"))).click();

	}

}
