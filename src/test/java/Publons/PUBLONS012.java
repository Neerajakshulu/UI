package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class PUBLONS012 extends TestBase {
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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("PUBLONS");
	}

	@Test
	public void testcaseA12() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		// String userName="neon.user2@tr.com";
		// String pass="Neon@123";

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
			
			// Registration Page

			try {
				pf.getPubPage(ob).clickRegisterLink();
				ob.findElement(By.xpath(OR.getProperty("termsofuse_link"))).click();
				pf.getPubPage(ob).switchToWindow(2);
				String CurrentUrl = ob.getCurrentUrl();
				logger.info("Current URl is --->" + CurrentUrl);

				String Title = ob.findElement(By.xpath(OR.getProperty("reg_PageHeading_label_for_termsOfUse")))
						.getText();
				logger.info("Title is" + Title);
				Assert.assertEquals("Terms of Use", Title);

				test.log(LogStatus.PASS, "Getting expected page of Terms of Use for Registration page");
				ob.close();

				pf.getPubPage(ob).switchToWindow(1);
				
				fluentwaitforElement(ob, By.xpath(OR.getProperty("privacy_statement_link").toString()), 60);
				ob.findElement(By.xpath(OR.getProperty("privacy_statement_link"))).click();

				pf.getPubPage(ob).switchToWindow(2);
				waitForPageLoad(ob);
				String CurrentUrl1 = ob.getCurrentUrl();
				logger.info("Current URl is--->" + CurrentUrl1);
				Assert.assertEquals("https://clarivate.com/legal/privacy-statement/", CurrentUrl1);

				String Titleofcurrentwindow = ob.getTitle();
				logger.info("Title is-->" + Titleofcurrentwindow);
				Assert.assertEquals("Privacy notice - Clarivate", Titleofcurrentwindow);
				ob.close();
				test.log(LogStatus.PASS, "Getting expected page of Privacy notice for Registration page");

				pf.getPubPage(ob).switchToWindow(1);

				

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Terms of Use is not displayed in Registration Page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));

			} finally {
				test.log(LogStatus.INFO,
						" Terms of Use and Privicy Notice is displayed in Registration Page and redirecting to exptected page ");
				extent.endTest(test);
			}
			
			// *SIGN IN PAGE
			try {
				ob.navigate().to(host);
				ob.findElement(By.xpath(OR.getProperty("termsofuse_link"))).click();
				pf.getPubPage(ob).switchToWindow(2);
				String CurrentUrl = ob.getCurrentUrl();
				logger.info("Current URl is --->" + CurrentUrl);

				String Title = ob.findElement(By.xpath(OR.getProperty("reg_PageHeading_label_for_termsOfUse")))
						.getText();
				logger.info("Title is" + Title);
				Assert.assertEquals("Terms of Use", Title);
				ob.close();
				test.log(LogStatus.PASS, "Getting expected page of Terms of Use for Signin page");

				pf.getPubPage(ob).switchToWindow(1);
				ob.findElement(By.xpath(OR.getProperty("privacy_statement_link"))).click();

				pf.getPubPage(ob).switchToWindow(2);
				waitForPageLoad(ob);
				String CurrentUrl1 = ob.getCurrentUrl();
				logger.info("Current URl is--->" + CurrentUrl1);
				Assert.assertEquals("https://clarivate.com/legal/privacy-statement/", CurrentUrl1);

				String Titleofcurrentwindow = ob.getTitle();
				logger.info("Title is-->" + Titleofcurrentwindow);
				Assert.assertEquals("Privacy notice - Clarivate", Titleofcurrentwindow);
				test.log(LogStatus.PASS, "Getting expected page of Privacy notice for Signin page");
				ob.close();

				pf.getPubPage(ob).switchToWindow(1);
				closeBrowser();
				
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "Terms of Use is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));

			} finally {
				test.log(LogStatus.INFO,
						" Terms of Use and Privicy Notice is displayed in Login page and redirecting to exptected page ");
				extent.endTest(test);
			}

			

			

		} catch (Throwable t) {
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
	}
}
