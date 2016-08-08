package search;

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

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Search63 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('B'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(
						var,
						"Verify that no search results get displayed if search engine doesn't interpret the query and that a proper message gets displayed regarding that")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB63() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Search");
		boolean testRunmode = TestUtil.isTestCaseRunnable(searchxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			String search_query = "tyitutyigtiugiuuioyrfuy";

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			ob.navigate().to(host);
			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_SORT_LEFT_NAV_PANE_CSS.toString()), 30);
			

			List<WebElement> mylist = ob.findElements(By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_SORT_LEFT_NAV_PANE_CSS.toString()));

			mylist.get(0).click();
			waitForElementTobeVisible(ob, By.xpath("//*[@ng-show='noResults']"), 30);

			String actual_text1 = ob.findElement(By.xpath("//*[@ng-show='noResults']")).getText();
			String expected_text1 = "Your search for tyitutyigtiugiuuioyrfuy found no matches.\nSuggestions:\nMake sure all words are spelled correctly.\nTry different keywords.\nTry more general keywords.";

			if (!compareStrings(expected_text1, actual_text1)) {

				test.log(LogStatus.FAIL, "Proper message not getting displayed for ALL option");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_proper_message_not_getting_displayed_for_ALL_option")));// screenshot

			}

			mylist.get(1).click();

			String actual_text2 = ob.findElement(By.xpath("//*[@ng-show='noResults']")).getText();
			String expected_text2 = "Your search for tyitutyigtiugiuuioyrfuy found no matches in Articles.\nSuggestions:\nMake sure all words are spelled correctly.\nTry different keywords.\nTry more general keywords.";

			if (!compareStrings(expected_text2, actual_text2)) {

				test.log(LogStatus.FAIL, "Proper message not getting displayed for ARTICLES option");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_proper_message_not_getting_displayed_for_ARTICLES_option")));// screenshot

			}

			mylist.get(2).click();
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath("//*[@ng-show='noResults']"), 30);
			String actual_text3 = ob.findElement(By.xpath("//*[@ng-show='noResults']")).getText();
			String expected_text3 = "Your search for tyitutyigtiugiuuioyrfuy found no matches in Patents.\nSuggestions:\nMake sure all words are spelled correctly.\nTry different keywords.\nTry more general keywords.";

			if (!compareStrings(expected_text3, actual_text3)) {

				test.log(LogStatus.FAIL, "Proper message not getting displayed for PATENTS option");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_proper_message_not_getting_displayed_for_PATENTS_option")));// screenshot

			}

			mylist.get(3).click();
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath("//*[@ng-show='noResults']"), 30);
			String actual_text4 = ob.findElement(By.xpath("//*[@ng-show='noResults']")).getText();
			String expected_text4 = "Your search for tyitutyigtiugiuuioyrfuy found no matches in People.\nSuggestions:\nMake sure all words are spelled correctly.\nTry different keywords.\nTry more general keywords.";

			if (!compareStrings(expected_text4, actual_text4)) {

				test.log(LogStatus.FAIL, "Proper message not getting displayed for PEOPLE option");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_proper_message_not_getting_displayed_for_PEOPLE_option")));// screenshot

			}

			mylist.get(4).click();
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath("//*[@ng-show='noResults']"), 30);
			String actual_text5 = ob.findElement(By.xpath("//*[@ng-show='noResults']")).getText();
			String expected_text5 = "Your search for tyitutyigtiugiuuioyrfuy found no matches in Posts.\nSuggestions:\nMake sure all words are spelled correctly.\nTry different keywords.\nTry more general keywords.";

			if (!compareStrings(expected_text5, actual_text5)) {

				test.log(LogStatus.FAIL, "Proper message not getting displayed for POSTS option");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_proper_message_not_getting_displayed_for_POSTS_option")));// screenshot

			}

			String tile1 = mylist.get(0).getText().substring(3);
			String tile2 = mylist.get(1).getText().substring(8);
			String tile3 = mylist.get(2).getText().substring(7);
			String tile4 = mylist.get(3).getText().substring(6);
			String tile5 = mylist.get(4).getText().substring(5);

			// System.out.println(tile1);
			// System.out.println(tile2);
			// System.out.println(tile3);
			// System.out.println(tile4);
			// System.out.println(tile5);

			boolean cond1 = tile1.equals("0");
			boolean cond2 = tile2.equals("0");
			boolean cond3 = tile3.equals("0");
			boolean cond4 = tile4.equals("0");
			boolean cond5 = tile5.equals("0");

			boolean masterCond = cond1 && cond2 && cond3 && cond4 && cond5;
			System.out.println(masterCond);

			try {

				Assert.assertTrue(masterCond);
				test.log(
						LogStatus.PASS,
						"Result count getting displayed correctly in the left navigation pane for all the content types when search query is not properly interpreted by the system");// extent
																																														// report
			}

			catch (Throwable t) {

				test.log(
						LogStatus.FAIL,
						"Result count not getting displayed correctly in the left navigation pane for all the content types when search query is not properly interpreted by the system");// extent
																																															// report

				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_result_count_not_getting_displayed_correctly_in_the_left_navigation_pane")));// screenshot

			}

			closeBrowser();

		}

		catch (Throwable t) {
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
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
	extent.endTest(test);
		//
		// if (status == 1)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "PASS");
		// else if (status == 2)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "FAIL");
		// else
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "SKIP");

	}

}
