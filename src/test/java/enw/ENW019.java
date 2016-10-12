package enw;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
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

public class ENW019 extends TestBase {
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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");
	}

	@Test
	public void testcaseENW019() throws Exception {
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		String expected_URL = "Thank You";
		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to("https://dev-stable.1p.thomsonreuters.com/");
			loginAs("MARKETUSEREMAIL", "MARKETUSERPASSWORD");
			pf.getHFPageInstance(ob).clickProfileImage();
			ob.findElement(By.partialLinkText("Help & Feedback")).click();
			BrowserWaits.waitTime(2);
			jsClick(ob,ob.findElement(By.xpath(OnePObjectMap.ENW_SEND_FEEDBACK_LINK_XPATH.toString())));
			BrowserWaits.waitTime(3);
			if (ob.findElements(By.xpath(OnePObjectMap.SEND_FEEDBACK_COUNTRY_SELECTION_XPATH.toString())).size() > 0) {
				Select Country = new Select(ob.findElement(By.xpath(OnePObjectMap.COUNTRY_SELECT_IN_NEON.toString())));
				Country.selectByVisibleText("India");
				ob.findElement(By.xpath(OnePObjectMap.COMMON_FEEDBACK_COMMENTS_XPATH.toString())).sendKeys("testing");
				BrowserWaits.waitTime(2);
				jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.COMMON_FEEDBACK_SUBMIT_BTN_XPATH.toString())));
				BrowserWaits.waitTime(3);
				//jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.COMMON_FEEDBACK_SUBMIT_BTN_XPATH.toString())));
//				jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.COMMON_FEEDBACK_SUBMIT_BTN_XPATH.toString())));
//				Thread.sleep(3000);
				String str = ob.findElement(By.xpath(OnePObjectMap.FEEDBACK_THANKU_PAGE.toString())).getText();
				Assert.assertEquals(expected_URL, str);
				test.log(LogStatus.PASS, " Feedback has  been sent successfully.");
			} else {
				test.log(LogStatus.FAIL, "Feedback has not sent .");
				Assert.assertEquals(true, false);
			}

			try {
				test.log(LogStatus.PASS, "Feedback has  been sent successfully");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Feedback has not sent.");// extent
				ErrorUtil.addVerificationFailure(t); // reports
				status = 2;// excel
				test.log(LogStatus.INFO,
						"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
								+ "Feedback New window is not displayed and content is not matching")));// screenshot
			}
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
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
		closeBrowser();
	}

}
