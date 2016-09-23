package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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

public class ENWIAM009 extends TestBase {

	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {

		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");
	}

	@Test
	public void testcaseA21() throws Exception {

		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("Test --" + suiteRunmode + "--" + testRunmode);
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
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("ENWIAM");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		// test.log(LogStatus.INFO, this.getClass().getSimpleName() + "
		// execution starts--->");

		openBrowser();
		try {
			maximizeWindow();
			clearCookies();
			String email = "userendnote@gmail.com";
			String password = "Neon@123";

			ob.get("https://www.guerrillamail.com");
			BrowserWaits.waitTime(2);
			if (CONFIG.getProperty("browserType").equals("IE")) {
				Runtime.getRuntime().exec("C:/Users/uc204155/Desktop/IEScript.exe");
				BrowserWaits.waitTime(4);
			}
			String email1 = ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();

			// ob.navigate().to(CONFIG.getProperty("enwUrl"));
			ob.get(host + CONFIG.getProperty("appendENWAppUrl"));

			waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(email);
			ob.findElement(By.name(OR.getProperty("TR_password_textBox"))).sendKeys(password);
			ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
			BrowserWaits.waitTime(8);
			String text = ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()))
					.getText();
			if (text.equalsIgnoreCase("Continue")) {
				ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
			}
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENDNOTE_LOGOUT_HEADER_LABLE_XPATH.toString()), 30);
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.ENDNOTE_LOGOUT_HEADER_LABLE_XPATH.toString())));
			BrowserWaits.waitTime(2);
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENDNOTE_ACCOUNT_LINK_XPATH.toString()), 30);
			ob.findElement(By.xpath(OnePObjectMap.ENDNOTE_ACCOUNT_LINK_XPATH.toString())).click();
			BrowserWaits.waitTime(6);
			String str = ob.findElement(By.xpath(OR.getProperty("account_email_preference_link"))).getText();
			String emaiText = "View additional email preferences";
			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1963",
								"Verify that 'View additional email preferences' should be displayed in the account setting page.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				Assert.assertEquals(str, emaiText);
				test.log(LogStatus.PASS,
						"View additional email preferences link displayed in the account setting page.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"View additional email preferences link is not displayed in the account setting page." + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1964",
								"Verify that after clicking 'View additional email preferences' user should be redirected to 'Intelectual property and Science Page' and should be able to enter email id in email Address field to access your preference center.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.findElement(By.xpath(OR.getProperty("account_email_preference_link"))).click();

				// Assert.assertEquals(str, emaiText);

				Set<String> myset = ob.getWindowHandles();
				Iterator<String> myIT = myset.iterator();
				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < myset.size(); i++) {

					al.add(myIT.next());
				}

				ob.switchTo().window(al.get(1));

				String str1 = ob
						.findElement(By.xpath(OnePObjectMap.ENDNOTE_ACCOUNT_PAGE_PREFERENCE_LINK_XPATH.toString()))
						.getText();
				String preText = "ACCESS YOUR PREFERENCE CENTER";

				Assert.assertEquals(str1, preText);
				ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_ACCESS_CENTER_EMAIL_FIELD_CSS.toString()))
						.sendKeys(email1);
				ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_ACCESS_CENTER_SUBMIT_BUTTON_CSS.toString()))
						.click();
				BrowserWaits.waitTime(2);
				String verification = ob
						.findElement(
								By.cssSelector(OnePObjectMap.ENDNOTE_ACCESS_CENTER_VERIFICATION_MAIL_CSS.toString()))
						.getText();
				String verificationEmail = ob.findElement(By.cssSelector("strong font[color='#ff9100']")).getText();
				Assert.assertEquals(verification.contains("PLEASE CHECK YOUR INBOX/SPAM FOLDER"),
						verificationEmail.contains(email1));

				test.log(LogStatus.PASS,
						"After clicking 'View additional email preferences' user redirected to 'Intelectual property and Science Page' and successfylly enterd email id in textbox.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"After clicking 'View additional email preferences' user not redirected to 'Intelectual property and Science Page' and successfylly not enterd email id in textbox."
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1965",
								"Verify that user should receive an email verification mail containing a direct link to 'Preference Center' Page, where user can update email preferences for the 'Intellectual Property & Science business of Thomson Reuters.'")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.get("https://www.guerrillamail.com");
				BrowserWaits.waitTime(14);
				List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list")));
				WebElement myE = email_list.get(0);
				JavascriptExecutor executor = (JavascriptExecutor) ob;
				executor.executeScript("arguments[0].click();", myE);
				BrowserWaits.waitTime(3);
				String emailSubject = ob.findElement(By.cssSelector("h3[class='email_subject']")).getText();
				Assert.assertEquals(emailSubject, "Your Preference Center Access Link");
				test.log(LogStatus.PASS,
						"User received an email verification mail containing a direct link to 'Preference Center' Page, where user can update email preferences for the 'Intellectual Property & Science business of Thomson Reuters.'");

				if (ob.findElement(By.cssSelector("h3[class='email_subject']")).getText()
						.equalsIgnoreCase("Your Preference Center Access Link")) {

				}

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"User not received an email verification mail containing a direct link to 'Preference Center' Page, where user can update email preferences for the 'Intellectual Property & Science business of Thomson Reuters.'"
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}

			try {
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1966",
								"Verify that after updating the preferences ,the system should update the URL that supports the 'View additional email preferences' link .")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				ob.findElement(By.xpath("//font[contains(text(),'ENGLISH')]")).click();
				BrowserWaits.waitTime(2);

				Set<String> myset = ob.getWindowHandles();
				Iterator<String> myIT = myset.iterator();
				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < myset.size(); i++) {

					al.add(myIT.next());
				}

				ob.switchTo().window(al.get(2));

				String currentUrl1 = null;
				String currentUrl = ob.getCurrentUrl();
				ob.findElement(By.cssSelector("input[name='jobtitle']")).sendKeys("Software");
				Select jobrole = new Select(ob.findElement(By.id("field33")));
				jobrole.selectByVisibleText("Engineers");
				Select country = new Select(ob.findElement(By.id("field6")));
				country.selectByVisibleText("English");
				ob.findElement(By.cssSelector("input[name='optout']")).click();
				ob.findElement(By.cssSelector("input[class='button']")).click();
				BrowserWaits.waitTime(3);
				String str1 = ob.findElement(By.xpath("(//span[@class='remove-absolute'])[2]")).getText();
				if (str1.equalsIgnoreCase("OPTED OUT IN ERROR?")) {
					ob.findElement(By.cssSelector("input[type='submit']")).click();
					String text1 = "THANK YOU FOR OPTING IN";
					String text2 = ob.findElement(By.cssSelector("span[class='remove-absolute'] span span")).getText();
					currentUrl1 = ob.getCurrentUrl();
					Assert.assertEquals(text1, text2);
				} else {
					currentUrl1 = ob.getCurrentUrl();
					String currentText = ob
							.findElement(By.cssSelector("td[class='valign-able DynamicContent'] span span")).getText();
					String finalText = "THANK YOU";
					Assert.assertEquals(currentText, finalText);
				}
				Assert.assertNotEquals(currentUrl, currentUrl1);
				test.log(LogStatus.PASS,
						"After updating the preferences ,the system update the URL that supports the 'View additional email preferences' link .");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"After updating the preferences ,the system not update the URL that supports the 'View additional email preferences' link ."
								+ t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution start");
				extent.endTest(test);
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Preference page not opened");// extent
																	// reports
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "unable_to_open_preference_page")));// screenshot
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);
		}
		closeBrowser();
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		// extent.endTest(test);

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
