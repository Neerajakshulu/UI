package notifications;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;


public class Notifications024 extends TestBase {
	static int status = 1;
	PageFactory pf = new PageFactory();

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('F'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(var,
						"Verify that user is able to view Most viewed documents in home page")
						.assignCategory("Notifications");

	}

	@Test
	public void notifications024() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Notifications");
		boolean testRunmode = TestUtil.isTestCaseRunnable(notificationxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		int scrollCount=0;
		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			// Login with someother user and comment on the article in watchlist of the above user
			ob.navigate().to(host);
			pf.getLoginTRInstance(ob).enterTRCredentials(CONFIG.getProperty("defaultUsername"), CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			Thread.sleep(2000);
			test.log(LogStatus.INFO," Scrolling down to find most viewed documents--->");
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			while(scrollCount<15){
				jse.executeScript("scroll(0,10000)");
				Thread.sleep(3500);
				scrollCount++;
			}

			// Login with first user and check if notification is present
			try{
				List<WebElement> elements=ob.findElements(By.xpath("//ne-most-viewed-documents"));
				Assert.assertTrue(elements.size()>=1);
				test.log(LogStatus.INFO,"user is able to see most viewed documents in home page");
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
				pf.getLoginTRInstance(ob).logOutApp();
				closeBrowser();
			}catch(Throwable t){
				test.log(LogStatus.FAIL, "user is not able to see most viewed documents in home page");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_user is not able to see most viewed documents in home pagee")));// screenshot
				closeBrowser();
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something happened");// extent
			// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_Something happened")));// screenshot
			closeBrowser();
		}
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(notificationxls,
		 * "Test Cases", TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}
}

