package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class PUBLONS015 extends TestBase {

	static int status = 1;
	static int count = -1;
	String[] tests;
	String[] tests_dec;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		String var = rowData.getTestcaseId();
		String dec = rowData.getTestcaseDescription();
		tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
		tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
		test = extent.startTest(tests[0], tests_dec[0]).assignCategory("PUBLONS");
		test.log(LogStatus.INFO, tests[0]);
	}

	@Test
	public void testcaseA1() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		String newPassword = "Neon@1234";

		if (!master_condition) {
			status = 3;// excel
			extent = ExtentManager.getReporter(filePath);
			String var = rowData.getTestcaseId();
			String dec = rowData.getTestcaseDescription();
			String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
			String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
			logger.info(rowData.getTestcaseId());
			for (int i = 0; i < tests.length; i++) {
				logger.info(tests_dec[i]);
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("PUBLONS");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		
		openBrowser();
		maximizeWindow();
		clearCookies();
		pf.getIamPage(ob).openGurillaMail();
		String email = pf.getIamPage(ob).getEmail();
		pf.getIamPage(ob).openCCURL(
				"http://steam-stablea.dev-shared.com:8080/steam-admin-app/");
		pf.getIamPage(ob).loginCustomerCare("mahesh.morsu@thomsonreuters.com",
				"Neon@123");
		pf.getIamPage(ob).openMenuPanel();
		pf.getIamPage(ob).clickUserManagement();
		pf.getIamPage(ob).clickCreateUser();
		pf.getIamPage(ob).closeMenuPanel();
		pf.getIamPage(ob).openMainPanel();
		pf.getIamPage(ob).registerNewUser(email);
		pf.getIamPage(ob).closeMainPanel();
		pf.getIamPage(ob).openHeaderPanel();
		pf.getIamPage(ob).logoutCustomerCare();
		pf.getIamPage(ob).closeHeaderPanel();
		pf.getIamPage(ob).checkCCLoginPage();

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution starts--->");
		try {
			ob.navigate().to(host);
			pf.getIamPage(ob).login("fhyd3i+42jpcp3anrij0@sharklasers.com", "Neon@123");
			waitForElementTobeVisible(ob,
					By.xpath(OR.getProperty("signup_done_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_done_button")))
					.click();
			waitUntilText("learn about you", "Join");
			waitForElementTobeVisible(ob,
					By.xpath(OR.getProperty("signup_join_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_join_button")))
					.click();
			waitUntilText("Newsfeed","Watchlists", "Groups");

			pf.getPubPage(ob).moveToAccountSettingPage();
			pf.getPubPage(ob).windowHandle();
			pf.getPubPage(ob).clickTab("Password");
			
			
					
			try {
				test = extent
						.startTest("OPQA-5842",
								"Verify that password change text fields displaying or not when ever click Password link in Account setting page")
						.assignCategory("PUBLONS");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
				JavascriptExecutor js = (JavascriptExecutor) ob;
				String populatText = (String) (js.executeScript("return arguments[0].value",
						ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS.toString()))));
				logger.info("PopulatText--->" + populatText);
				
				Assert.assertEquals(populatText, "Enter current password");
				
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
				JavascriptExecutor js1 = (JavascriptExecutor) ob;
				String populatText1 = (String) (js.executeScript("return arguments[0].value",
						ob.findElement(By.cssSelector(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS.toString()))));
				logger.info("PopulatText--->" + populatText);
				Assert.assertEquals(populatText1, "Enter new password");
				
				
				test.log(LogStatus.PASS,
						"Text fields are displaying");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Text fields are not displaying");
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			logout();

			test.log(LogStatus.PASS, "Expected Error message getting");
			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this
									.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution ends--->");
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
