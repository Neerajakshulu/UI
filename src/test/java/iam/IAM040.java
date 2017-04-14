package iam;

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
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class IAM040 extends TestBase {

	static int status = 1;
	static boolean fail = false;
	static boolean skip = false;
	static int time = 30;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IAM");
	}

	@Test
	public void testcaseA26() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).click();
			ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).click();
			BrowserWaits.waitTime(2);
			String emailErrorMessage = ob
					.findElement(By.cssSelector(OnePObjectMap.FORGOT_PASSWORD_PAGE_ERROR_MESSAGE_CSS.toString()))
					.getText();
			Assert.assertTrue(emailErrorMessage.contains("Please enter an email address."));
			ob.navigate().refresh();
			waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
			ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).click();
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).click();
			BrowserWaits.waitTime(2);
			String passwordErrorMessage = ob
					.findElement(By.cssSelector(OnePObjectMap.FORGOT_PASSWORD_PAGE_ERROR_MESSAGE_CSS.toString()))
					.getText();
			Assert.assertTrue(passwordErrorMessage.contains("Please enter a password."));

			ob.navigate().refresh();
			BrowserWaits.waitTime(2);
			waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys("abcd.com");
			BrowserWaits.waitTime(2);
			String emailInvalidErrorMessage = ob
					.findElement(By.cssSelector(OnePObjectMap.FORGOT_PASSWORD_PAGE_ERROR_MESSAGE_CSS.toString()))
					.getText();
			Assert.assertTrue(emailInvalidErrorMessage.contains("Please enter a valid email address."));
			closeBrowser();
		} catch (Throwable t) {
			status = 2;// excel-main testcase
			fail = true;// excel-dataset
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}
}
