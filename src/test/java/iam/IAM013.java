package iam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class IAM013 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verify that TERMS OF USE and PRIVACY STATEMENT links are working correctly")
				.assignCategory("IAM");

	}

	@Test
	public void testcaseA13() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "IAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(iamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		// Verify that TERMS OF USE and PRIVACY STATEMENT links are working correctly in Singn In Page

		openBrowser();
		try {
			maximizeWindow();
		} catch (Throwable t) {

			System.out.println("maximize() command not supported in Selendroid");
		}

		ob.navigate().to(host);
		BrowserWaits.waitTime(6);
		termofUserAndPrivacyStatement();
		
		try {
			// Verify that TERMS OF USE and PRIVACY STATEMENT links are working correctly in Singn Up Page
			BrowserWaits.waitTime(2);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("signup_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_link"))).click();
			BrowserWaits.waitTime(4);
			termofUserAndPrivacyStatement();

		} catch (Throwable t) {

			test.log(LogStatus.FAIL, "Sign Up Button is not Clickable");// extent reports
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_signup_button_not_clickable")));// screenshot
			closeBrowser();
		}

		ob.get(host+CONFIG.getProperty("appendENWAppUrl"));
		BrowserWaits.waitTime(6);
		termofUserAndPrivacyStatement();
		
		try {
			// Verify that TERMS OF USE and PRIVACY STATEMENT links are working correctly in Singn Up Page
			BrowserWaits.waitTime(2);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("signup_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_link"))).click();
			BrowserWaits.waitTime(4);
			termofUserAndPrivacyStatement();

		} catch (Throwable t) {

			test.log(LogStatus.FAIL, "Sign Up Button is not Clickable");// extent reports
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_signup_button_not_clickable")));// screenshot
			closeBrowser();
		}

		
		closeBrowser();

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	private void termofUserAndPrivacyStatement() throws Exception {

		try {
			clearCookies();

			waitForElementTobeVisible(ob, By.linkText(OR.getProperty("reg_TermsOfUse_link")), 30);

			ob.findElement(By.linkText(OR.getProperty("reg_TermsOfUse_link"))).click();
			Thread.sleep(3000);

			String headerModalText = null;
			headerModalText = ob.findElement(By.xpath(OR.getProperty("reg_PageHeading_label"))).getText();
			logger.info("Modal Text : " + headerModalText);

			if (!checkElementPresence("reg_PageHeading_label")) {

				test.log(LogStatus.FAIL,
						"Either TERMS OF USE link is not working or the page is not getting displayed correctly");// extent
																													// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_issue_with_termsOfUse_link")));// screenshot

			}
			//BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OR.getProperty("close_PageHeading_label"))).click();
			BrowserWaits.waitTime(2);
			//waitForElementTobeVisible(ob, By.linkText(OR.getProperty("reg_PricayStatement_link")), 30);
			WebElement myE = ob.findElement(By.linkText(OR.getProperty("reg_PricayStatement_link")));
			myE.click();

			BrowserWaits.waitTime(3);
			headerModalText = ob.findElement(By.xpath(OR.getProperty("reg_PageHeading_label"))).getText();
			logger.info("Modal Text : " + headerModalText);

			if (!checkElementPresence("reg_PageHeading_label")) {

				test.log(LogStatus.FAIL,
						"Either PRICAY STATEMENT link is not working or the page is not getting displayed correctly");// extent
																														// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_issue_with_privacyStatement_link")));// screenshot

			}
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OR.getProperty("close_PageHeading_label"))).click();
			// closeBrowser();
			BrowserWaits.waitTime(2);

		} catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS");
		 * else if(status==2) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL");
		 * else TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
