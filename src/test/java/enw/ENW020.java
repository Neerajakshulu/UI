package enw;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

public class ENW020 extends TestBase {

	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");
	}

	@Test
	public void testcaseENW020() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		String url="https://dev-stable.1p.thomsonreuters.com/#/profile/";
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
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.WOS_HOMEPAGE_ENDNOTE_LINK.toString())));
			EndNoteSeesion(ob);
			System.out.println("Refreshing the browser");
			ob.navigate().refresh();
			System.out.println("CLicking the profile");
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())));
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.IMAGE_USER_XPATH.toString())));
			loginAs("MARKETUSEREMAIL", "MARKETUSERPASSWORD");
			BrowserWaits.waitTime(6);
			if (ob.getCurrentUrl().contains(url)) {
				if (ob.findElements(By.xpath(OnePObjectMap.IMAGE_ICON_PROFILE_IN_NEON_XPATH.toString())).size() > 0)
					test.log(LogStatus.PASS, "Expected page is displayed and  Navigating to the proper Page.");

			} else {
				test.log(LogStatus.FAIL, "Expected page is not displayed");
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

	private void EndNoteSeesion(WebDriver ob) {
		String newWindow = switchToNewWindow(ob);
		System.out.println("Before Success1");
		if (newWindow != null) {
			System.out.println("Before Success2");
			if (ob.getCurrentUrl().contains("app.qc.endnote.com") && ob.getTitle().contains("EndNote")) {
				System.out.println("Success with the url");
				logger.info("Neon Landing page is opened in the new window ");
			}
		} else {
			test.log(LogStatus.FAIL, "New window is not displayed and content is not matching");
		}

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
		closeBrowser();
	}
}
