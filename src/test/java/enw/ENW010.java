package enw;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class ENW010 extends TestBase {

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
	public void testcaseENW010() throws Exception {
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

			ob.get(host + CONFIG.getProperty("appendENWAppUrl"));
			String actual_result = "";
			loginAs("NONMARKETUSEREMAIL", "NONMARKETUSERPASSWORD");
			if (ob.findElement(By.xpath(OnePObjectMap.ENW_CONTINUE_DIALOG_BOX_XPATH.toString())).isEnabled()) {
				// ob.findElement(By.xpath(OnePObjectMap.ENW_CONTINUE_BUTTON.toString())).click();
				ob.findElement(By.xpath(OR.getProperty("ENW_CONTINUE_BUTTON"))).click();
			}
			ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())).click();

			actual_result = ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_NAME_XPATH.toString())).getText();
			if (ob.findElement(By.className("inactiveLink")) == null) {
				logger.info("User name NOT hyperlinked to Profile page");
				logger.info("NOT hyperlinked:" + actual_result);
				actual_result = "test case is failed";
				Assert.assertEquals(true, false);
			}
			logger.info("Actual result displayed as :" + actual_result); // Image_User_XPATH
			WebElement ImageFile = ob.findElement(By.xpath(OnePObjectMap.IMAGE_USER_XPATH.toString()));
			Boolean ImagePresent = (Boolean) ((JavascriptExecutor) ob).executeScript(
					"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
					ImageFile);
			if (!ImagePresent) {
				logger.info("Image not displayed.");
			} else {
				logger.info("Image is displayed.");
			}
			try {

				Assert.assertTrue(ImagePresent);

				test.log(LogStatus.PASS, " Image is present and User name is not hyper linked");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, " Image is not present and User is hyperlinked");// extent
				ErrorUtil.addVerificationFailure(t); // reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "mage is not present and User is hyperlinked")));// screenshot
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
