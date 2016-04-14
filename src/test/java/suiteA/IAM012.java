package suiteA;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class IAM012 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verify that PASSWORD field in new TR user registration page").assignCategory(
				"IAM");
		// test.log(LogStatus.INFO, "****************************");

		// load the runmodes of the tests
		runmodes = TestUtil.getDataSetRunmodes(suiteAxls, this.getClass().getSimpleName());
	}

	@Test(dataProvider = "getTestData")
	public void testcaseA12(String password,
			String strength,
			String checks,
			String validity) throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "A Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteAxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;
			// TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
			// TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {

			test.log(LogStatus.INFO, "Runmode for test set data set to no " + (count + 1));
			skip = true;
			// TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "SKIP");
			throw new SkipException("Runmode for test set data set to no " + (count + 1));
		}

		try {

			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts for data set #" + (count + 1)
					+ "--->");
			test.log(LogStatus.INFO, password + " -- " + validity);

			String temp = checks.substring(0, 1);
			int tickMarks = Integer.parseInt(temp);
			String email = generateRandomName(5) + "@abc.com";

			// selenium code
			openBrowser();
			//
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to TR login page
			// ob.get(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			//
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			//
			waitForElementTobeVisible(ob, By.linkText(OR.getProperty("TR_register_link")), 30);

			// Create new TR account
			ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).click();
			//
			waitForElementTobeVisible(ob, By.id(OR.getProperty("reg_password_textBox")), 30);
			ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).sendKeys(password);

			List<WebElement> tm_list = ob.findElements(By.xpath(OR.getProperty("reg_passwordStrength_tickMark_label")));
			// System.out.println(tm_list.size());
			if (!compareNumbers(tickMarks, tm_list.size())) {

				fail = true;// excel
				test.log(LogStatus.FAIL, "Password strength checking functionality not working correctly");// extent
																											// report
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_password_strength_checking_functionality_not_working_correctly_"
										+ (count + 1))));

			}

			String password_strength = ob.findElement(By.id(OR.getProperty("reg_passwordStrength_label"))).getText();
			if (!compareStrings(strength, password_strength)) {

				fail = true;// excel
				test.log(LogStatus.FAIL, "Password strength not displayed correctly");// extent report
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_password_strength_not_displayed_correctly_" + (count + 1))));

			}

			ob.findElement(By.id(OR.getProperty("reg_email_textBox"))).sendKeys(email);
			ob.findElement(By.id(OR.getProperty("reg_firstName_textBox"))).sendKeys("ricky");
			ob.findElement(By.id(OR.getProperty("reg_lastName_textBox"))).sendKeys("behl");
			ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).clear();
			ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).sendKeys(password);
			ob.findElement(By.id(OR.getProperty("reg_confirmPassword_textBox"))).sendKeys(password);
			ob.findElement(By.id(OR.getProperty("reg_terms_checkBox"))).click();
			ob.findElement(By.xpath(OR.getProperty("reg_register_button"))).click();
			Thread.sleep(5000);

			if (validity.equalsIgnoreCase("YES")) {

				// Verifying that confirmation email is sent

				if (!checkElementPresence("reg_accountConfirmationMessage_label")) {

					fail = true;// excel
					test.log(LogStatus.FAIL,
							"User not able to register himself even when the password strength is strong");// extent
																											// report
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_user_unable_to_register_himself_even_when_password_strength_is_strong_"
											+ (count + 1))));

				}

			}

			else {

				if (!checkElementPresence("reg_passwordStrengthMessageOnSubmit_label")) {

					fail = true;// excel
					test.log(LogStatus.FAIL,
							"Either password strength message getting displayed is incorrect or unexpected login happened");// extent
																															// report
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_password_strength_message_incorrect_or_unexpected_login_" + (count + 1))));

				}

			}

			closeBrowser();

		}

		catch (Throwable t) {

			status = 2;// excel-main testcase
			fail = true;// excel-dataset
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends for data set #" + (count + 1)
				+ "--->");
	}

	@AfterMethod
	public void reportDataSetResult() {
		if (skip)
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count + 2, "SKIP");

		else if (fail) {

			status = 2;
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count + 2, "FAIL");
		} else
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count + 2, "PASS");

		skip = false;
		fail = false;

	}

	@AfterTest
	public void reportTestResult() {

		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(suiteAxls, "Test Cases",
		 * TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suiteAxls, this.getClass().getSimpleName());
	}

}
