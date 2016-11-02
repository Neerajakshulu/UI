package enw;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class ENW000011 extends TestBase {

	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");

	}

	@Test
	public void testcaseENW000011() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);

			try {

				ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
				ob.findElement(By.name(OR.getProperty("TR_email_textBox")))
						.sendKeys(LOGIN.getProperty("UsersteamENW000011"));
				ob.findElement(By.name(OR.getProperty("TR_password_textBox")))
						.sendKeys(LOGIN.getProperty("PWDuserENW000011"));
				ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();

				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
				pf.getHFPageInstance(ob).clickOnAccountLink();
				String actualEmail = ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_ACTUAL_EMAIL_XPATH.toString()))
						.getText();
				System.out.println(actualEmail);
				try {
					Assert.assertEquals(LOGIN.getProperty("UsersteamENW000011"), actualEmail);
					test.log(LogStatus.PASS, " Email id getting displayed in Account Setting page is correct");
				}

				catch (Throwable t) {

					test.log(LogStatus.FAIL, "Email id getting displayed in Account Setting page is incorrect");// extent
					// reports
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this
							.getClass().getSimpleName()
							+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
					ErrorUtil.addVerificationFailure(t);
				}

				String accountType = "Neon";
				validateLinkedAccounts(1, accountType);

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.INFO, "Snapshot below: " + test
						.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_user_not_registered")));// screenshot
				ErrorUtil.addVerificationFailure(t);
				closeBrowser();
			}

			logout();
			ob.close();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
			// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			ErrorUtil.addVerificationFailure(t);

			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	public void validateLinkedAccounts(int accountCount, String linkName) throws Exception {
		try {

			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount("Neon", LOGIN.getProperty("UsersteamENW000011")));
			Assert.assertFalse(pf.getAccountPageInstance(ob).verifyLinkedAccount("Facebook",
					LOGIN.getProperty("UsersteamENW000011")));
			Assert.assertFalse(pf.getAccountPageInstance(ob).verifyLinkedAccount("LinkedIn",
					LOGIN.getProperty("UsersteamENW000011")));
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			test.log(LogStatus.PASS,
					"Only one account is present in account setting page and primary account text is not displayed");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Multiple accounts are present");
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "Linking_failed")));// screenshot
		}
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}
}
