package iam;

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

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;

public class IAM006 extends TestBase {

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
		test = extent.startTest(var, "Verify LAST NAME field in new TR user registration page").assignCategory("IAM");
		// test.log(LogStatus.INFO, "****************************");
		// load the runmodes of the tests
		runmodes = testUtil.getDataSetRunmodes(iamxls, this.getClass().getSimpleName());
	}

	@Test(dataProvider = "getTestData")
	public void testcaseA6(String charLength,
			String validity) throws Exception {

		boolean suiteRunmode = testUtil.isSuiteRunnable(suiteXls, "IAM");
		boolean testRunmode = testUtil.isTestCaseRunnable(iamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;
			// testUtil.reportDataSetResult(iamxls, "Test Cases",
			// testUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {

			test.log(LogStatus.INFO, "Runmode for test set data set to no " + (count + 1));
			skip = true;
			// testUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count+2, "SKIP");
			throw new SkipException("Runmode for test set data set to no " + (count + 1));
		}

		try {

			String characterLength = charLength.substring(0, 2);
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts for data set #" + (count + 1)
					+ "--->");
			test.log(LogStatus.INFO, characterLength + " -- " + validity);

			System.out.println(characterLength);
			System.out.println(Integer.parseInt(characterLength));
			String last_name = generateRandomName(Integer.parseInt(characterLength));
			System.out.println(last_name);

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
			waitForElementTobeVisible(ob, By.linkText(OR.getProperty("TR_register_link")), 30);

			// Create new TR account
			ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).click();
			//
			waitForElementTobeVisible(ob, By.id(OR.getProperty("reg_lastName_textBox")), 30);
			ob.findElement(By.id(OR.getProperty("reg_lastName_textBox"))).sendKeys(last_name);
			ob.findElement(By.id(OR.getProperty("reg_firstName_textBox"))).click();

			List<WebElement> errorList = ob.findElements(By.id(OR.getProperty("reg_lastNameError_label")));

			if (validity.equalsIgnoreCase("YES")) {

				// verifying that error message is not getting displayed
				if (!compareNumbers(0, errorList.size())) {

					fail = true;// excel
					test.log(LogStatus.FAIL, "Error message getting displayed unnecessarily");// extent report
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_error_message_getting_displayed_unnecessarily_" + (count + 1))));
					closeBrowser();
					return;
				}

			}

			else {

				if (!compareNumbers(1, errorList.size())) {

					fail = true;// excel
					test.log(LogStatus.FAIL, "Error message not getting displayed");// extent report
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_error_message_not_getting_displayed_" + (count + 1))));
					closeBrowser();
					return;
				}

				String errorText = ob.findElement(By.id(OR.getProperty("reg_lastNameError_label"))).getText();
				if (!compareStrings("Please enter no more than 70 characters.", errorText)) {

					fail = true;// excel
					test.log(LogStatus.FAIL, "Error text is incorrect");// extent report
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_incorrect_error_text_" + (count + 1))));
					closeBrowser();
					return;

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
			testUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count + 2, "SKIP");

		else if (fail) {

			status = 2;
			testUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count + 2, "FAIL");
		} else
			testUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count + 2, "PASS");

		skip = false;
		fail = false;

	}

	@AfterTest
	public void reportTestResult() {

		extent.endTest(test);

		/*
		 * if(status==1) testUtil.reportDataSetResult(iamxls, "Test Cases",
		 * testUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * testUtil.reportDataSetResult(iamxls, "Test Cases",
		 * testUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * testUtil.reportDataSetResult(iamxls, "Test Cases",
		 * testUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */

	}

	@DataProvider
	public Object[][] getTestData() {
		return testUtil.getData(iamxls, this.getClass().getSimpleName());
	}

}
