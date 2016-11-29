package dra;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class DRA0012 extends TestBase {

	static int status = 1;
	static boolean fail = false;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");

	}

	@Test
	public void testcaseDRA0012() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		// static boolean fail = false;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
		
			openBrowser();
			clearCookies();
			maximizeWindow();
			steamLocked();
			loginTofbSuspended();
			ob.close();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent

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
	private void steamLocked() throws Exception {
		String str = "Your account has been locked.";
		String locked = "";
		ob.navigate().to(host + CONFIG.getProperty("appendDRAAppUrl"));

		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString())).clear();
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString()))
				.sendKeys(LOGIN.getProperty("DRAUSER0012Locked"));

		for (int i = 0; i <= 9; i++) {
			ob.findElement(By.name("loginPassword")).sendKeys("asdfgh");
			ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS.toString()))
					.sendKeys("asdfgh");
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
			Thread.sleep(2000);
		}
		BrowserWaits.waitTime(2);
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.ENW_UNVERIFIED_MESSAGE_BUTTON_CSS.toString()), 30);
		locked = ob.findElement(By.cssSelector(OnePObjectMap.ENW_UNVERIFIED_MESSAGE_BUTTON_CSS.toString())).getText();	
		BrowserWaits.waitTime(2);
		if (locked.equalsIgnoreCase(str)) {
			test.log(LogStatus.PASS, "The locked string is displayed, the account got locked on ENW");
		}

		else {
			test.log(LogStatus.FAIL, "The locked string is not displayed, the account is not locked on ENW");
		}
		
		BrowserWaits.waitTime(5);

	}
	
	private void loginTofbSuspended() throws Exception {

		ob.navigate().to(host + CONFIG.getProperty("appendDRAAppUrl"));

		String str = "Your account has been evicted.";
		pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("ENWIAM00015UserSuspended"),
				LOGIN.getProperty("ENWIAM00015SuspendedPWD"));
		pf.getENWReferencePageInstance(ob).didYouKnow(LOGIN.getProperty("ENWIAM00015SuspendedPWD"));
		BrowserWaits.waitTime(2);
		String evict = ob.findElement(By.xpath("//h2[contains(text(),'Your account has been evicted.')]")).getText();
		if (evict.equalsIgnoreCase(str)) {
			test.log(LogStatus.PASS, "The evicted string is displayed, the account got evicted");

		} else {
			test.log(LogStatus.FAIL, "The evicted string is not displayed");

		}
		BrowserWaits.waitTime(3);
		ob.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
	}


	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}
}
