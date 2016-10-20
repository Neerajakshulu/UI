package iam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class IAM004 extends TestBase {

	static int status = 1;

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
	public void testcaseA4() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		String email = "neonfbook@gmail.com";
		String password = "1Pproject";

		try {
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to FB login page
			ob.navigate().to(host);
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("FB_login_button")), 30);
			ob.findElement(By.cssSelector(OR.getProperty("FB_login_button"))).click();
			//
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.name(OR.getProperty("FB_email_textBox")), 30);

			// Verify that existing FB credentials are working fine
			ob.findElement(By.name(OR.getProperty("FB_email_textBox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("FB_password_textBox"))).sendKeys(password);
			BrowserWaits.waitTime(4);
			// waitForElementTobeVisible(ob, By.name(OR.getProperty("FB_page_login_button")), 30);
			ob.findElement(By.id(OR.getProperty("FB_page_login_button"))).click();

			BrowserWaits.waitTime(4);
			// waitForElementTobeVisible(ob, By.xpath(OR.getProperty("ul_name")), 30);
			if (!checkElementPresence("ul_name")) {

				test.log(LogStatus.FAIL, "Existing FB user credentials are not working fine");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_existing_FB_User_credentials_not_working_fine")));// screenshot
				closeBrowser();

			}
			// Verify that profile name gets displayed correctly
			if (!checkElementPresence("header_label")) {

				test.log(LogStatus.FAIL, "Incorrect profile name getting displayed");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_incorrect_profile_name_getting_displayed")));// screenshot
				closeBrowser();

			}

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 30);
			ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("account_link"))).click();
			BrowserWaits.waitTime(3);

			try {
				List<WebElement> list = ob.findElements(By.xpath(
						"//div[@class='account-option-item ng-scope']/div/div[@class='account-option-item__text-container']/span"));
				if (list.size() == 1) {
					String str = list.get(0).getText();
					Assert.assertEquals(str, email);
					test.log(LogStatus.PASS, "Both Email ids are same");
				} else {
					logger.info("Accounts are linked");
				}
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Both Email ids are not same");// extent reports
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			}

			logout();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("login_banner")), 8);

			if (!checkElementPresence("login_banner")) {

				test.log(LogStatus.FAIL, "User not able to logout successfully");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_logout_successfully")));// screenshot
				closeBrowser();

			}

			closeBrowser();

		} catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
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
