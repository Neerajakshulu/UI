package suiteF;

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
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class TestCase_F2 extends TestBase {
	static int status = 1;
	PageFactory pf = new PageFactory();

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);
		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent
				.startTest(var,
						"Verify that user receives a notificatication when someone he is following comments on an article")
				.assignCategory("Suite F");

	}

	@Test
	public void testcaseF2() throws Exception {

		Assert.assertEquals("time", "time");
		
//		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "F Suite");
//		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteFxls, this.getClass().getSimpleName());
//		boolean master_condition = suiteRunmode && testRunmode;
//
//		if (!master_condition) {
//
//			status = 3;// excel
//			test.log(LogStatus.SKIP,
//					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
//			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
//
//		}
//
//		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
//		try {
//
//			openBrowser();
//			maximizeWindow();
//			clearCookies();
//
//			// 1)Make user1 comment on some article and logout
//			ob.navigate().to(host);
//			Thread.sleep(8000);
//
//			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
//			Thread.sleep(4000);
//			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(user1);
//			ob.findElement(By.id(OR.getProperty("TR_password_textBox")))
//					.sendKeys(CONFIG.getProperty("defaultPassword"));
//			ob.findElement(By.id(OR.getProperty("login_button"))).click();
//			Thread.sleep(15000);
//
//			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("biology");
//			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
//			Thread.sleep(4000);
//
//			String document_title = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
//			System.out.println(document_title);
//			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
//			Thread.sleep(5000);
//			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("green tea");
//			Thread.sleep(5000);
//			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
//			//ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))).click();
//			Thread.sleep(10000);
//			pf.getLoginTRInstance(ob).logOutApp();
//			Thread.sleep(5000);
//
//			// 2)Login with user2 and verify that he receives a correct
//			// notification
//			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
//			Thread.sleep(4000);
//			ob.findElement(By.id("userid")).clear();
//			ob.findElement(By.id("userid")).sendKeys(user2);
//			ob.findElement(By.id("password")).sendKeys(CONFIG.getProperty("defaultPassword"));
//			ob.findElement(By.id(OR.getProperty("login_button"))).click();
//			Thread.sleep(15000);
//
//			/*if (!checkElementPresence("notification")) {
//
//				test.log(LogStatus.FAIL, "User not receiving notification");// extent
//																			// reports
//				status = 2;// excel
//				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
//						captureScreenshot(this.getClass().getSimpleName() + "_user_not_receiving_notification")));// screenshot
//				closeBrowser();
//				return;
//			}*/
//			String text = ob.findElement(By.xpath(OR.getProperty("notificationCommentEvent"))).getText();
//			System.out.println(text);
//			try {
//				Assert.assertTrue(
//						/*text.contains("TODAY") &&*/ text.contains(fn1 + " " + ln1) && text.contains("commented on") && text.contains(document_title));
//				test.log(LogStatus.PASS, "User receiving notification with correct content");
//			} catch (Throwable t) {
//
//				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
//				StringWriter errors = new StringWriter();
//				t.printStackTrace(new PrintWriter(errors));
//				test.log(LogStatus.INFO, errors.toString());												// reports
//				test.log(LogStatus.INFO, "Error--->" + t);
//				ErrorUtil.addVerificationFailure(t);
//				status = 2;// excel
//				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
//						this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
//
//			}
//
//			closeBrowser();
//
//		} catch (Throwable t) {
//			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
//																		// reports
//			// next 3 lines to print whole testng error in report
//			StringWriter errors = new StringWriter();
//			t.printStackTrace(new PrintWriter(errors));
//			test.log(LogStatus.INFO, errors.toString());// extent reports
//			ErrorUtil.addVerificationFailure(t);// testng
//			status = 2;// excel
//			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
//					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
//			closeBrowser();
//		}
//
//		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
/*
		if (status == 1)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "SKIP");
*/
	}

}
