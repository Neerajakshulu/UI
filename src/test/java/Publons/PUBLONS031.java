package Publons;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class PUBLONS031 extends TestBase {

	static int status = 1;
	Robot robot;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(),
				rowData.getTestcaseDescription()).assignCategory("PUBLONS");
	}

	@Test
	public void aliasaccount() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		String userName = "fha7q5+f5dsr9533bbm0@sharklasers.com";
		String pass = "Pub@1234";
		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case "
					+ this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"
					+ this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution starts--->");
		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			pf.getIamPage(ob).openGurillaMail();
			String email = pf.getIamPage(ob).getEmail();

			openNewWindow();
			ArrayList<String> tabs = new ArrayList<String>(
					ob.getWindowHandles());
			ob.switchTo().window(tabs.get(1));
			ob.navigate().to(host);
			pf.getIamPage(ob).login(userName, pass);
			pf.getBrowserWaitsInstance(ob).waitUntilText("Newsfeed",
					"Watchlists", "Groups");
			pf.getPubPage(ob).moveToAccountSettingPage();
			pf.getPubPage(ob).moveToSpecificWindow(2);
			pf.getPubPage(ob).clickTab("email");
			List<WebElement> list1 = ob.findElements(By
					.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_LIST_OF_EMAILS_CSS.toString()));
			ob.findElement(
					By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_ADD_EMIAL_LINK_CSS
							.toString())).click();
			ob.findElement(
					By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_ADDING_EMAIL_ADDRESS_LABEL
							.toString())).click();
			ob.findElement(
					By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_ADDING_EMAIL_ADDRESS_CANCEL_BUTTON_CSS
							.toString())).click();
			test.log(LogStatus.PASS,
					"Cancel Button is working");
			ob.findElement(
					By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_ADD_EMIAL_LINK_CSS
							.toString())).click();
			ob.findElement(
					By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_ADDING_EMAIL_ADDRESS_LABEL
							.toString())).click();
			ob.findElement(
					By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_ADDING_EMAIL_ADDRESS_LABEL
							.toString())).sendKeys(email);
			ob.findElement(
					By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_ADDING_EMAIL_ADDRESS_SUBMIT_BUTTON_CSS
							.toString())).click();
			test.log(LogStatus.PASS,
					"Submit Button is clicked to add new account");
			List<WebElement> list2 = ob.findElements(By
					.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_LIST_OF_EMAILS_CSS.toString()));
			if (list2.size() == list1.size() + 1)
				test.log(LogStatus.PASS,
						"Alias Email account is added in account setting page");
			else
				test.log(LogStatus.FAIL,"There is an issue in adding alias account in account setting page");
			pf.getPubPage(ob).moveToSpecificWindow(0);
			pf.getPubPage(ob).userVerification();

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

	public void openNewWindow() throws AWTException {
		robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_T);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_T);
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}

}