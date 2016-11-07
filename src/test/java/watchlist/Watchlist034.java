package watchlist;

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

/**
 * Verify that deep linking is working correctly for particular watchlist page when user logs in using Social(FB or LI)
 * account
 * 
 * @author Amneet Singh
 *
 */
public class Watchlist034 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("Watchlist");
	}

	@Test
	public void watchlist034() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			// Opening browser
			openBrowser();
			maximizeWindow();
			clearCookies();

			// 1)Open Project Neon app.
			ob.navigate().to(host);
			loginAsSpecifiedUser(LOGIN.getProperty("LOGINUSERNAME1"), LOGIN.getProperty("LOGINPASSWORD1"));
			//get total watchlists
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("watchlist_link")), 60);
			
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("watchlist_link"))));
			BrowserWaits.waitTime(10);
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("watchlist_link"))));
			BrowserWaits.waitTime(10);
			
			waitForAllElementsToBePresent(ob, By.cssSelector("div[class='wui-card__content'] a"), 30);
			
			List<WebElement> tot_watchlists=ob.findElements(By.cssSelector("div[class='wui-card__content'] a"));
			logger.info("total watchlist-->"+tot_watchlists.size());
			
			for(int i=0;i<tot_watchlists.size(); i++) {
				ob.findElement(By.cssSelector("div[class='wui-card__content'] a")).click();
				waitForElementTobeClickable(ob, By.xpath(OR.getProperty("delete_button_image1")), 60);
				ob.findElement(By.xpath(OR.getProperty("delete_button_image1"))).click();
				waitForElementTobeClickable(ob, By.xpath(OR.getProperty("delete_button_in_popup1")), 60);
				ob.findElement(By.xpath(OR.getProperty("delete_button_in_popup1"))).click();
				BrowserWaits.waitTime(6);
				waitForAllElementsToBePresent(ob, By.cssSelector("div[class='wui-card__content'] a"), 30);
			}
			
			
			
			// 9)Close the browser.
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

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(suiteExls, "Test Cases" , TestUtil.getRowNum(suiteExls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(suiteExls,
		 * "Test Cases", TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */}

}
