package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E35 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent.startTest(var, "Verify that user is able to convert his public watchlist to private")
				.assignCategory("Suite E");

	}

	@Test
	public void testChangeStatusPublicToPrivate() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "E Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteExls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			// String search_query = "biology";

			// Making a new user
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// createNewUser("mask", "man");
			ob.navigate().to(host);
			LoginTR.enterTRCredentials("Prasenjit.Patra@thomsonreuters.com", "Techm@2015");
			LoginTR.clickLogin();
			Thread.sleep(15000);

			// Navigate to the watch list landing page
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();

			// Creating a new watch list
			ob.findElement(By.xpath(OR.getProperty("createWatchListButton"))).click();
			Thread.sleep(2000);
			String newWatchlistName = "New Watchlist";
			ob.findElement(By.xpath(OR.getProperty("newWatchListNameTextBox"))).sendKeys(newWatchlistName);
			ob.findElement(By.xpath(OR.getProperty("newWatchListDescriptionTextArea")))
					.sendKeys("This is my newly created watch list");
			ob.findElement(By.xpath(OR.getProperty("newWatchListPublicCheckBox"))).click();
			// Clicking on Create button
			ob.findElement(By.xpath(OR.getProperty("newWatchListCreateButton"))).click();
			Thread.sleep(4000);

			// Making the public watch list to public
			ob.findElement(By.xpath(OR.getProperty("newWatchListPublicCheckBox"))).click();

			boolean watchListStatus = ob.findElement(By.xpath(OR.getProperty("newWatchListPublicCheckBox")))
					.isSelected();
			if (!watchListStatus) {
				test.log(LogStatus.PASS, "User is able to change the public watch list to private");
			} else {
				test.log(LogStatus.FAIL, "User is not able to change the public watch list to private");
			}

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
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "SKIP");

	}

}
