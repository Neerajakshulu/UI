package Publons;

import java.awt.Robot;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

public class PUBLONS032 extends TestBase {

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
	public void loginwithaliasaccount() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		String userName = "fkt1js+ex178m8iwpxmg@sharklasers.com";
		String pass = "Cmty@123";

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
			ob.navigate().to(host);
			pf.getIamPage(ob).login(userName, pass);
			pf.getBrowserWaitsInstance(ob).waitUntilText("Newsfeed",
					"Watchlists", "Groups");
			pf.getPubPage(ob).moveToAccountSettingPage();
			pf.getPubPage(ob).moveToSpecificWindow(1);
			pf.getPubPage(ob).clickTab("email");

			test.log(LogStatus.PASS, "User is able to login with alias account");
			waitForAllElementsToBePresent(
					ob,
					By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_LIST_OF_EMAIL_ROW_TABLE_CSS
							.toString()), 90);
			List<WebElement> list2 = ob
					.findElements(By
							.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_LIST_OF_EMAIL_ROW_TABLE_CSS.toString()));
			String email = null;
			for (WebElement el : list2) {
				String text = el.findElements(By.tagName("div")).get(2)
						.getText();
				if (text.contains("Make Primary")) {
					email = el.findElements(By.tagName("div")).get(0).getText();
					el.findElements(By.tagName("div")).get(2).click();
					test.log(LogStatus.PASS,
							"Make a primary button is clickable");
					System.out.println("prim"+email);
				}

			}
			for (WebElement we : list2) {
				String text = we.findElements(By.tagName("div")).get(3)
						.getText();
				String actualEmail = we.findElements(By.tagName("div")).get(0)
						.getText();

				if (text.contains("Primary") && email.contains(actualEmail)) {
					test.log(LogStatus.PASS, "Primary email is updated");

				}
				System.out.println("prim"+text);
				System.out.println("actual"+actualEmail);

			}
			pf.getPubPage(ob).moveToSpecificWindow(0);
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();

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
}