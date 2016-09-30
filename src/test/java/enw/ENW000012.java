package enw;

import static com.jayway.restassured.RestAssured.given;

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
import util.TestUtil;

public class ENW000012 extends TestBase {

	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");

	}

	@Test
	public void testcaseENW000012() throws Exception {

		boolean testRunmode = TestUtil.isTestCaseRunnable(enwxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			
			String statuCode = deleteUserAccounts(LOGIN.getProperty("UserENW000010"));
		logger.info("User Status : "+statuCode);
		if(statuCode.equalsIgnoreCase("200")){
			logger.info("User Deleted Successfully");
		}
			//Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			


	
			String statuCode1 = deleteUserAccounts(LOGIN.getProperty("UserENWsteam000010"));
			logger.info("User Status : "+statuCode1);
			if(statuCode1.equalsIgnoreCase("200")){
				logger.info("User Deleted Successfully");
			}
			
			
		
		openBrowser();
		clearCookies();
		maximizeWindow();
		ob.navigate().to(host);
		
		

		try {
			waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(LOGIN.getProperty("UserENWsteam000010"));
			ob.findElement(By.name(OR.getProperty("TR_password_textBox")))
					.sendKeys(LOGIN.getProperty("PWDuserENW000012"));
			ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
			ob.navigate().refresh();
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(LOGIN.getProperty("UserENWsteam000010"));
			ob.findElement(By.name(OR.getProperty("TR_password_textBox")))
					.sendKeys(LOGIN.getProperty("PWDuserENW000012"));
			ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
			BrowserWaits.waitTime(6);
			ob.findElement(By.xpath(OR.getProperty("signup_conformatin_button"))).click();
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OR.getProperty("signup_done_button"))).click();
			BrowserWaits.waitTime(3);
		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.INFO, "Snapshot below: " + test
					.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_user_not_registered")));// screenshot
			closeBrowser();
		}
		
		logout();
		BrowserWaits.waitTime(4);
		//ob.navigate().to(host);
			// login();
			pf.getLoginTRInstance(ob).loginWithLinkedInCredentials(LOGIN.getProperty("UserENW000012"),
					LOGIN.getProperty("PWDuserENW000012"));
			BrowserWaits.waitTime(3);

			// login();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.header_label.toString()), 30);
			ob.findElement(By.xpath(OnePObjectMap.header_label.toString())).click();

			//
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.account_link.toString()), 30);
			ob.findElement(By.xpath(OnePObjectMap.account_link.toString())).click();

			BrowserWaits.waitTime(2);
			// OPQA-1905
			String actualEmail = ob.findElement(By.xpath(OnePObjectMap.actualEmail.toString())).getText();
			System.out.println(actualEmail);

			try {
				Assert.assertEquals(LOGIN.getProperty("UserENW000012"), actualEmail);
				test.log(LogStatus.PASS, " Email id getting displayed in Account Setting page is correct");
			}

			catch (Throwable t) {

				test.log(LogStatus.FAIL, "Email id getting displayed in Account Setting page is incorrect");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

			}
			// OPQA-1910
			BrowserWaits.waitTime(2);
			String passwordText = ob.findElement(By.xpath(OnePObjectMap.Text_compare_password.toString())).getText();
			System.out.println(passwordText);
			try {
				Assert.assertEquals(passwordText, "Password is associated with your LinkedIn account.");
				test.log(LogStatus.PASS,
						"Message 'Password is associated with your LinkedIn account.' is dispalyed correctly in account setting page");
			}

			catch (Throwable t) {

				test.log(LogStatus.FAIL,
						"Message 'Password is associated with your LinkedIn account.' is not dispalyed correctly in account setting page");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

			}

			String LInkText = ob.findElement(By.xpath(OnePObjectMap.accountlink.toString())).getText();
			System.out.println(LInkText);
			try {
				Assert.assertEquals(LInkText, "Link accounts");
				test.log(LogStatus.PASS, "Link button is displayed in Account setting page.");
			}

			catch (Throwable t) {

				test.log(LogStatus.FAIL, "Link button is not displayed in Account setting page.");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

			}

			BrowserWaits.waitTime(4);
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.accountlink.toString()),30);
			ob.findElement(By.xpath(OnePObjectMap.accountlink.toString())).click();
			

			BrowserWaits.waitTime(4);
			waitForElementTobeVisible(ob, By.name(OnePObjectMap.Link_login.toString()),30);
			Thread.sleep(4);
			ob.findElement(By.name("email")).sendKeys(LOGIN.getProperty("UserENWsteam000010"));
			ob.findElement(By.name("password")).sendKeys(LOGIN.getProperty("PWDuserENW000010"));
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.DoneButtonClick.toString()),30);
			ob.findElement(By.xpath(OnePObjectMap.DoneButtonClick.toString())).click();
		
			BrowserWaits.waitTime(5);
			String actualEmail1 = ob.findElement(By.xpath(OnePObjectMap.actualEmail1.toString())).getText();

			System.out.println(actualEmail1);
			try {
				Assert.assertEquals(LOGIN.getProperty("UserENWsteam000010"), actualEmail1);
				test.log(LogStatus.PASS, " Email id getting displayed in Account Setting page is correct");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Email id getting displayed in Account Setting page is incorrect");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

			}
			// OPQA-1912
			BrowserWaits.waitTime(5);
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.LinkedIn_icon.toString()), 8);
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.Steam_icon.toString()),8);
			try {
				if (checkElementPresence("LinkedIn_icon") && checkElementPresence("Steam_icon")) {
					test.log(LogStatus.PASS, "LinkedIn authentication account is linked to the Neon account");
					System.out.println("pass");
				}

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "LinkedIn authentication account is not linked to the Neon account");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);
				System.out.println("fail");

			}

			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.Radiobutton2.toString()),30);
			ob.findElement(By.xpath(OnePObjectMap.Radiobutton2.toString())).click();


			BrowserWaits.waitTime(3);
			String TextcompareAfterLink = ob.findElement(By.xpath(OnePObjectMap.Text_compareAfterLink.toString()))
					.getText();

			System.out.println(TextcompareAfterLink);
			try {
				Assert.assertEquals(TextcompareAfterLink,
						"Project Neon has linked your accounts. You can sign in with any of the accounts you already use.");
				test.log(LogStatus.PASS,
						"Message 'Project Neon has linked your accounts.' is displayed correctly after linking from account setting page ");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Message 'Project Neon has linked your accounts.' is not displayed after linking from account setting page ");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

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

	

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}
}
