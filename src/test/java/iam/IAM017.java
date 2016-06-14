package iam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class IAM017 extends TestBase {

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
		test = extent.startTest(var, "Verify that name of a user is truncated using ellipse if the name is very long")
				.assignCategory("IAM");

	}

	@Test
	public void testcaseA17() throws Exception {

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

		try {

			String password = "Transaction@2";
			String first_name = "firstfirstfirstfirstfiirstfiirstfirstfirst";
			String last_name = "lastlastlastlastlast";

			// 1)Create a new user
			// 2)Login with new user and logout
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			try {
				String email = createNewUser(first_name, last_name);
				logger.info("Email Address : " + email);
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User is not created");
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
				closeBrowser();
			}
			/*
			 * ob.get("https://www.guerrillamail.com"); String email =
			 * ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();
			 * System.out.println(email); ob.navigate().to(host);
			 * waitForElementTobeVisible(ob,
			 * By.xpath(OR.getProperty("signup_link")), 30);
			 * 
			 * // Create new Steam account
			 * ob.findElement(By.xpath(OR.getProperty("signup_link"))).click();
			 * // waitForElementTobeVisible(ob,
			 * By.name(OR.getProperty("signup_email_texbox")), 30);
			 * ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).
			 * clear();
			 * ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).
			 * sendKeys(email);
			 * ob.findElement(By.name(OR.getProperty("signup_password_textbox"))
			 * ).clear();
			 * ob.findElement(By.name(OR.getProperty("signup_password_textbox"))
			 * ).sendKeys(password);
			 * ob.findElement(By.name(OR.getProperty("signup_firstName_textbox")
			 * )).clear();
			 * ob.findElement(By.name(OR.getProperty("signup_firstName_textbox")
			 * )).sendKeys(first_name);
			 * ob.findElement(By.name(OR.getProperty("signup_lastName_textbox"))
			 * ).clear();
			 * ob.findElement(By.name(OR.getProperty("signup_lastName_textbox"))
			 * ).sendKeys(last_name);
			 * ob.findElement(By.xpath(OR.getProperty("signup_button"))).click()
			 * ; BrowserWaits.waitTime(4); waitForElementTobeVisible(ob,
			 * By.cssSelector(OR.getProperty("signup_confom_sent_mail")), 30);
			 * 
			 * String text = ob.findElement(By.cssSelector(OR.getProperty(
			 * "signup_confom_sent_mail"))).getText(); if (!StringContains(text,
			 * email)) {
			 * 
			 * test.log(LogStatus.FAIL, "Account activation email not sent");//
			 * extent // reports status = 2;// excel test.log(LogStatus.INFO,
			 * "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() +
			 * "_account_activation_email_not_sent")));// screenshot
			 * 
			 * }
			 * 
			 * ob.findElement(By.xpath(OR.getProperty(
			 * "signup_conformatin_button"))).click();
			 * ob.get("https://www.guerrillamail.com"); Thread.sleep(10000);
			 * List<WebElement> email_list =
			 * ob.findElements(By.xpath(OR.getProperty("email_list")));
			 * email_list.get(0).click(); Thread.sleep(2000); String
			 * email_subject =
			 * ob.findElement(By.xpath(OR.getProperty("email_subject_label"))).
			 * getText(); if (!StringContains(email_subject,
			 * "Please activate your Project Neon account")) {
			 * 
			 * test.log(LogStatus.FAIL, "Account activation email not received"
			 * ); status = 2;// excel test.log(LogStatus.INFO,
			 * "Snapshot below: " + test.addScreenCapture(
			 * captureScreenshot(this.getClass().getSimpleName() +
			 * "_account_activation_email_not_received")));// screenshot
			 * 
			 * }
			 * 
			 * System.out.println("Before capturing email body links");
			 * WebElement email_body =
			 * ob.findElement(By.xpath(OR.getProperty("email_body")));
			 * List<WebElement> links =
			 * email_body.findElements(By.tagName("a"));
			 * System.out.println(links.size()); System.out.println(
			 * "After capturing email body links");
			 * ob.get(links.get(0).getAttribute("href")); Thread.sleep(8000);
			 * ob.findElement(By.xpath(OR.getProperty(
			 * "signup_conformatin_button"))).click(); BrowserWaits.waitTime(4);
			 * ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear
			 * (); ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).
			 * sendKeys(email);
			 * ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).
			 * sendKeys(password);
			 * ob.findElement(By.cssSelector(OR.getProperty("login_button"))).
			 * click();
			 */

			/*
			 * ob.get("https://www.guerrillamail.com"); String email =
			 * ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();
			 * // ob.navigate().to(CONFIG.getProperty("testSiteName"));
			 * ob.navigate().to(host); // waitForElementTobeVisible(ob,
			 * By.xpath(OR.getProperty("TR_login_button")), 30);
			 * ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click
			 * (); // waitForElementTobeVisible(ob,
			 * By.linkText(OR.getProperty("TR_register_link")), 30);
			 * 
			 * System.out.println(email);
			 * 
			 * ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).
			 * click(); // waitForElementTobeVisible(ob,
			 * By.id(OR.getProperty("reg_email_textBox")), 30);
			 * ob.findElement(By.id(OR.getProperty("reg_email_textBox"))).
			 * sendKeys(email);
			 * ob.findElement(By.id(OR.getProperty("reg_firstName_textBox"))).
			 * sendKeys(first_name);
			 * ob.findElement(By.id(OR.getProperty("reg_lastName_textBox"))).
			 * sendKeys(last_name);
			 * ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).
			 * sendKeys(password);
			 * ob.findElement(By.id(OR.getProperty("reg_confirmPassword_textBox"
			 * ))).sendKeys(password);
			 * ob.findElement(By.id(OR.getProperty("reg_terms_checkBox"))).click
			 * ();
			 * ob.findElement(By.xpath(OR.getProperty("reg_register_button"))).
			 * click(); Thread.sleep(10000);
			 * 
			 * ob.get("https://www.guerrillamail.com"); List<WebElement>
			 * email_list =
			 * ob.findElements(By.xpath(OR.getProperty("email_list")));
			 * WebElement myE = email_list.get(0); JavascriptExecutor executor =
			 * (JavascriptExecutor) ob;
			 * executor.executeScript("arguments[0].click();", myE); //
			 * email_list.get(0).click(); Thread.sleep(2000);
			 * 
			 * WebElement email_body =
			 * ob.findElement(By.xpath(OR.getProperty("email_body")));
			 * List<WebElement> links =
			 * email_body.findElements(By.tagName("a")); links.get(0).click();
			 * // ob.get(links.get(0).getAttribute("href")); Thread.sleep(8000);
			 * 
			 * Set<String> myset = ob.getWindowHandles(); Iterator<String> myIT
			 * = myset.iterator(); ArrayList<String> al = new
			 * ArrayList<String>(); for (int i = 0; i < myset.size(); i++) {
			 * 
			 * al.add(myIT.next()); } ob.switchTo().window(al.get(1));
			 * Thread.sleep(5000);
			 * 
			 * ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).
			 * sendKeys(email);
			 * ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).
			 * sendKeys(password);
			 * ob.findElement(By.id(OR.getProperty("login_button"))).click();
			 */

			BrowserWaits.waitTime(6);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 60);

			String actual_name = ob.findElement(By.xpath(OR.getProperty("header_label") + "//img"))
					.getAttribute("title");
			// System.out.println(actual_name);
			String expected_name = "firstfirstfirstfirstfiirstfiirstfirstfirst lastlas ...";
			if (!compareStrings(expected_name, actual_name)) {

				test.log(LogStatus.FAIL, "Long name is not getting ellipsed correctly");// extent
																						// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_long_name_not_getting_ellipsed_correctly")));// screenshot

			}
			logout();
			closeBrowser();
		}

		catch (Throwable t) {

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
