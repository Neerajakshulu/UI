package enw;

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
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

public class ENW020 extends TestBase {
	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");
	}

	@Test
	public void testcaseENW013() throws Exception {
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
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
			ob.navigate().to("http://ua-qa.newisiknowledge.com/");
			loginToWOS("MARKETUSEREMAIL", "MARKETUSERPASSWORD");
			if (ob.findElement(By.xpath(OnePObjectMap.WOS_SESSION_ALREADYEXISTS.toString())).isEnabled()) {
				ob.findElement(By.xpath(OnePObjectMap.WOS_SESSION_ALREADYEXISTS.toString())).click();
				Thread.sleep(3000);
			}
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.WOS_HOMEPAGE_ENDNOTE_LINK.toString())));
			String newWindow = switchToNewWindow(ob);
			if (newWindow != null) {
				if (ob.getCurrentUrl().contains("app.qc.endnote.com") && ob.getTitle().contains("EndNote")) {
					System.out.println(
							"Print URL and Title:" + ob.getCurrentUrl().toString() + "" + ob.getTitle().toString());
					logger.info("ENDNOTE application is opened in the new browser and Content Available");
				}
			} else {
				test.log(LogStatus.FAIL, "New browser is not displayed and content is not matching");
				Assert.assertEquals(true, false);
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
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
