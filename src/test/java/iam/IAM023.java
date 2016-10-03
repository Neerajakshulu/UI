package iam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

public class IAM023 extends TestBase {

	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IAM");
	}

	@Test
	public void testCaseA23() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			// 1)Create a new user
			// 2)Login with new user and logout
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();
			String email = createNewUser("disco", "dancer");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 30);
			ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("account_link"))).click();
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("change_password_link"))));
			waitForElementTobeVisible(ob, By.id(OR.getProperty("email_Address")), 30);
			ob.findElement(By.id(OR.getProperty("email_Address"))).sendKeys(email);
			ob.findElement(By.xpath(OR.getProperty("verification_email_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("confomr_message")), 30);

			String text = ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText();
			logger.info("Email Address : " + text);

			String expected_text = "An email with password reset instructions has been sent to " + email;
			logger.info("Expected Email : " + expected_text);

			String checkEmail1 = ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText();
			logger.info("Email Address : " + checkEmail1);
			String checkEmail = "Please check your email";
			if (!StringContains(text, expected_text) && !StringContains(checkEmail, checkEmail1)) {

				test.log(LogStatus.FAIL, "Email for password change not sent");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_password_change_email_not_sent")));// screenshot

			}
			BrowserWaits.waitTime(3);
			ob.get("https://www.guerrillamail.com");
			BrowserWaits.waitTime(12);
			List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list")));
			WebElement myE = email_list.get(0);
			JavascriptExecutor executor = (JavascriptExecutor) ob;
			executor.executeScript("arguments[0].click();", myE);
			Thread.sleep(2000);

			String email_subject = ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).getText();
			logger.info("Email Subject Text : " + email_subject);
			if (!StringContains(email_subject, "Project Neon password change request")) {

				test.log(LogStatus.FAIL, "Email for changing password not received");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_password_change_email_not_received")));// screenshot

			}

			WebElement reset_link_element = ob.findElement(By.xpath(OR.getProperty("email_body_password_reset_link")));
			String reset_link_url = reset_link_element.getAttribute("href");
			ob.get(reset_link_url);
			//
			waitForElementTobeVisible(ob, By.id(OR.getProperty("newPassword_textBox")), 30);

			ob.findElement(By.id(OR.getProperty("newPassword_textBox"))).sendKeys("Neon@1234");
			ob.findElement(By.id(OR.getProperty("confirmPassword_textBox"))).sendKeys("Neon@1234");
			ob.findElement(By.id(OR.getProperty("update_password"))).click();
			//
			BrowserWaits.waitTime(3);
			String checkEmail2 = ob.findElement(By.xpath(OR.getProperty("check_confrom_message"))).getText();
			logger.info("Email Address : " + checkEmail2);

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("confomr_message")), 30);

			text = ob.findElement(By.xpath(OR.getProperty("confomr_message"))).getText();
			logger.info("Expected Text : " + text);

			expected_text = "Your password has been updated";
			String expectedText = "Your password has been successfully updated. A confirmation has been sent to your email address.";

			if (!StringContains(checkEmail2, expected_text) && !StringContains(text, expectedText)) {

				test.log(LogStatus.FAIL, "Password not changed successfully");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_password_not_changed_successfully")));// screenshot

			}

			BrowserWaits.waitTime(2);
			// 4)login with changed password
			ob.findElement(By.cssSelector("input[class='button']")).click();
			BrowserWaits.waitTime(4);
			logout();
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).sendKeys("Neon@1234");
			ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
			Thread.sleep(10000);

			if (!checkElementPresence("header_label")) {

				test.log(LogStatus.FAIL, "User unable to login with changed password");// extent reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_unable_to_login_with_changed_password")));// screenshot

			}
			logout();
			ob.quit();
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
		closeBrowser();
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
