package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import util.TestUtil;

public class TestCase_B15 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		extent = ExtentManager.getReporter(filePath);
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent
				.startTest(var,
						"Verify that Times cited and Comments fields are getting displayed for each document in search results page")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB15() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteBxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
//			ob.navigate().to(host);
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			login();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("biology", Keys.ENTER);
			waitForAjax(ob);
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 60);
			List<WebElement> resultList = ob.findElements(By.xpath(OR.getProperty("tr_search_results_item_xpath")));
			List<WebElement> timeCiteList;
			List<WebElement> viewsList;
			List<WebElement> commentsList;
			int timeCiteCount = 0, viewsCount = 0, commentsCount = 0;
			waitForAjax(ob);
			timeCiteList = ob.findElements(By.xpath(OR.getProperty("tr_timecited_search_results_xpath")));
			if (timeCiteList.size() != 0) {
				for (WebElement timeCite : timeCiteList) {
					if (!timeCite.isDisplayed())
						timeCiteCount++;
				}
			} else {
				viewsCount = -1;
			}

			try {
				Assert.assertTrue(resultList.size() == timeCiteList.size() && timeCiteCount == 0);
				test.log(LogStatus.PASS, "Time cite field is displayed for all articles");
			} catch (Throwable t) {
				if (timeCiteCount == -1)
					test.log(LogStatus.FAIL, "Time cite field is not displayed for any articles");
				test.log(LogStatus.FAIL,
						String.format("Time cite field is not displayed for %d articles", timeCiteCount));
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "TimeCite for search results validation failed")));// screenshot

			}
//			viewsList = ob.findElements(By.xpath(OR.getProperty("tr_views_search_results_xpath")));
//			if (viewsList.size() != 0) {
//				for (WebElement views : viewsList) {
//					if (!views.isDisplayed())
//						viewsCount++;
//				}
//			} else {
//				viewsCount = -1;
//			}
//
//			try {
//				Assert.assertTrue(resultList.size() == viewsList.size() && viewsCount == 0);
//				test.log(LogStatus.PASS, "views field is displayed for all articles");
//			} catch (Throwable t) {
//				if (viewsCount == -1)
//					test.log(LogStatus.FAIL, "views field is not displayed for any articles");
//				else
//					test.log(LogStatus.FAIL, String.format("views field is not displayed %d documents", viewsCount));
//				test.log(LogStatus.INFO, "Error--->" + t);
//				ErrorUtil.addVerificationFailure(t);
//				status = 2;
//				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
//						this.getClass().getSimpleName() + "Viewsfor search results validation failed")));// screenshot
//
//			}
			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_comments_search_results_xpath")));
			if (commentsList.size() != 0) {
				for (WebElement comments : commentsList) {
					if (!comments.isDisplayed())
						commentsCount++;
				}
			} else {
				commentsCount = -1;
			}

			try {
				Assert.assertTrue(resultList.size() == commentsList.size() && commentsCount == 0);
				test.log(LogStatus.PASS, "Comments field is displayed for all documents");
			} catch (Throwable t) {
				if (commentsCount == -1)
					test.log(LogStatus.FAIL, "Comments field is not displayed for any articles");
				else
					test.log(LogStatus.FAIL,
							String.format("Comments field is not displayed for %d articles", commentsCount));
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Comments for search results validation failed")));// screenshot

			}
//			logout();
			closeBrowser();
		}

		catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something went wrong");// extent reports
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

//		if (status == 1)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
//		else if (status == 2)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
//		else
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}
}
