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

//Verify that, Users who navigate from WOS to ENW with a valid WOS session, should be taken to ENW in a signed in state
public class ENW020 extends TestBase {

	static int status = 1;
	//String url = "https://dev-stable.1p.thomsonreuters.com/#/profile/";
	String ENWURL = "https://dev-stable.1p.thomsonreuters.com/#/login?referrer=%252F%23%252Fprofile%3Fapp%3Dendnote&app=endnote&pageview=";
	String NeonUrlProfile = "https://dev-stable.1p.thomsonreuters.com/#/profile/";

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
			// String uRL_webofScience = ob.getCurrentUrl();
			// if (!uRL_webofScience.contains("error-qa.newisiknowledge.com")) {
			// BrowserWaits.waitTime(6);
			// NavigateToENW();
			// } else {

			BrowserWaits.waitTime(5);
			loginToWOS("MARKETUSEREMAIL", "MARKETUSERPASSWORD");
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WOS_HEADER_XPATH);
			BrowserWaits.waitTime(6);
			NavigateToENW();
			BrowserWaits.waitTime(10);
			String neon_Profile_URL = ob.getCurrentUrl();
			if (neon_Profile_URL.contains(ENWURL)) {
				logger.info("URL:" + ob.getCurrentUrl());
				pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin1(LOGIN.getProperty("MARKETUSEREMAIL"),
						(LOGIN.getProperty("MARKETUSERPASSWORD")));
				BrowserWaits.waitTime(3);
				String ExpectedNeonUrl=ob.getCurrentUrl();
				Assert.assertEquals(ExpectedNeonUrl,NeonUrlProfile );
//				if (!ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).isDisplayed()) {
//					test.log(LogStatus.FAIL, "Expected page is Not displayed ");
//					Assert.assertEquals(true, false);
//				} else {
					test.log(LogStatus.PASS, "Neon Profile page is displayed and Navigating to the proper page.");
//				}
			} else {
				test.log(LogStatus.FAIL, "Neon Profile page is not displayed");
				Assert.assertEquals(true, false);
			}
			logout();
			closeBrowser();

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

	private void NavigateToENW() throws InterruptedException {
		ob.findElement(By
				.xpath("//div[@class='navBar clearfix']//ul[@class='globalLinks nav-list']//a[@id='HS_EndNoteLink_signedin']"))
				.click();
		try {
			EndNoteSeesion(ob);
			try {
				if (ob.findElements(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).size() != 0) {
					ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).click();
				}
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH);
				BrowserWaits.waitTime(5);
				jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())));
				BrowserWaits.waitTime(5);
				jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.IMAGE_USER_XPATH.toString())));
				BrowserWaits.waitTime(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void EndNoteSeesion(WebDriver ob) {
		String newWindow = switchToNewWindow(ob);
		if (newWindow != null) {
			if (ob.getCurrentUrl().contains("app.qc.endnote.com") && ob.getTitle().contains("EndNote")) {
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
