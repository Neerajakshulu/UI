package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
import util.OnePObjectMap;
import util.TestUtil;

public class PUBLONS005 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("PUBLONS");
		runmodes = TestUtil.getDataSetRunmodes(pubxls, this.getClass().getSimpleName());
	}

	@Test(dataProvider = "getTestData")
	public void testcaseA5(String charLength,
			String suffix,
			String error,
			String validity) throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {

			test.log(LogStatus.INFO, "Runmode for test set data set to no " + (count + 1));
			skip = true;
			throw new SkipException("Runmode for test set data set to no " + (count + 1));
		}

		try {

			String characterLength = charLength.substring(0, 3);
			Double d = new Double(Double.parseDouble(characterLength));
			int i = d.intValue();
			logger.info("Char Length : " + characterLength);
			test.log(LogStatus.INFO,
					this.getClass().getSimpleName() + " execution starts for data set #" + (count + 1) + "--->");
			test.log(LogStatus.INFO, characterLength + " -- " + validity);

			logger.info("Length : " + characterLength);
			String email = generateRandomName(i) + suffix;
			logger.info(email);

			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
//			pf.getPubPage(ob).clickRegisterLink();
			/*pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.PUBLONS_LOGIN_PAGE_REGISTER_LINK_CSS);
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.PUBLONS_LOGIN_PAGE_REGISTER_LINK_CSS);*/
			
			ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_LOGIN_PAGE_REGISTER_LINK_CSS.toString())).click();
			ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).click();
			ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).clear();
			ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).click();

			if (email.contains(".com")) {
				waitForElementTobeVisible(ob, By.name(OR.getProperty("signup_email_texbox")), 30);

				JavascriptExecutor js = (JavascriptExecutor) ob;
				String text = (String) (js.executeScript("return arguments[0].value",
						ob.findElement(By.name(OR.getProperty("signup_email_texbox")))));
				
				
				//String text = ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).getText();
				logger.info("Text : " + text);

				if (validity.equalsIgnoreCase("YES")) {
					try {
						List<WebElement> l=ob.findElements(By.cssSelector(OnePObjectMap.PUBLONS_MAXLENGTH_ERROR_MESSAGE_CSS.toString()));
						if(l.size()==0){
						test.log(LogStatus.PASS, "Error message not displaying with"+email.length()+" characters");
						}
					} catch (Throwable t) {
						test.log(LogStatus.FAIL, "Sign up button is not enabled in Singn up page" + t);// extent
						StringWriter errors = new StringWriter();
						t.printStackTrace(new PrintWriter(errors));
						// test.log(LogStatus.INFO, errors.toString()); // reports
						test.log(LogStatus.INFO, "Error--->" + t);
						ErrorUtil.addVerificationFailure(t);
						status = 2;// excel
						test.log(LogStatus.INFO, "Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName())));// screenshot
					}

				}

				else {
					try {
						waitUntilText("Email address is too long.");
						String emailErrorMessage = ob
								.findElements(By.cssSelector(OnePObjectMap.PUBLONS_MAXLENGTH_ERROR_MESSAGE_CSS.toString())).get(0)
								.getText();
						Assert.assertTrue(emailErrorMessage.contains("Email address is too long."));
						test.log(LogStatus.PASS, "Error message displaying with"+email.length()+" characters");
					} catch (Throwable t) {
						test.log(LogStatus.FAIL, "Sign up Page is not opened successfully" + t);// extent
						StringWriter errors = new StringWriter();
						t.printStackTrace(new PrintWriter(errors));
						test.log(LogStatus.INFO, "Error--->" + t);
						ErrorUtil.addVerificationFailure(t);
						status = 2;// excel
						test.log(LogStatus.INFO, "Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName())));// screenshot
					}

				}
			}
			closeBrowser();

		}

		catch (Throwable t) {

			status = 2;// excel-main testcase
			fail = true;// excel-dataset
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO,
				this.getClass().getSimpleName() + " execution ends for data set #" + (count + 1) + "--->");
	}

	@AfterMethod
	public void reportDataSetResult() {
		if (skip)
			TestUtil.reportDataSetResult(pubxls, this.getClass().getSimpleName(), count + 2, "SKIP");

		else if (fail) {

			status = 2;
			TestUtil.reportDataSetResult(pubxls, this.getClass().getSimpleName(), count + 2, "FAIL");
		} else
			TestUtil.reportDataSetResult(pubxls, this.getClass().getSimpleName(), count + 2, "PASS");

		skip = false;
		fail = false;

	}

	@AfterTest
	public void reportTestResult() {

		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(pubxls, "Test Cases",
		 * TestUtil.getRowNum(pubxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(pubxls, "Test Cases",
		 * TestUtil.getRowNum(pubxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(pubxls, "Test Cases",
		 * TestUtil.getRowNum(pubxls,this.getClass().getSimpleName()), "SKIP");
		 */

	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(pubxls, this.getClass().getSimpleName());
	}

}
