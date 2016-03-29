package suiteB;

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
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B36 extends TestBase {
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
		test = extent
				.startTest(var,
						"Verify that the following fields get displayed in the SORT BY drop down when ARTICLES is selected as content type in the left navigation pane: a)Relevance b)Times cited c)Publication Date(Newest) d)Publication Date(Oldest)")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB8() throws Exception {

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

			String search_query = "biology";

			openBrowser();
			clearCookies();
			maximizeWindow();

//			 ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);

			// Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.cssSelector("li[ng-click='vm.updateSearchType(\"ARTICLES\")']"), 30);

			// Clicking on Articles content result set
			ob.findElement(By.cssSelector("li[ng-click='vm.updateSearchType(\"ARTICLES\")']")).click();

			// Clicking on the sort by drop down
			ob.findElement(By.cssSelector("button[class='btn search-sort-btn dropdown-toggle']")).click();

			// Finding out the types by which we can sort the Article content
			// results
			List<WebElement> content_types = ob
					.findElements(By.cssSelector("a[event-category='searchresult-ck-sort']"));
			String text1 = content_types.get(0).getText();
			String text2 = content_types.get(1).getText();
			String text3 = content_types.get(2).getText();
			String text4 = content_types.get(3).getText();

			// Comparing the the label of the type of sort item
			if (!text1.equalsIgnoreCase("Relevance") || !text2.equalsIgnoreCase("Times Cited")
					|| !text3.equalsIgnoreCase("Publication Date (Newest)")
					|| !text4.equalsIgnoreCase("Publication Date (Oldest)")) {

				test.log(LogStatus.FAIL, "Fields mismatch in the Sort by drop down for Article cotent set");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO,
						"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
								+ "Fields_mismatch_in_the_Sort_by_drop_down_for_article_cotent_set")));// screenshot

			} else {
				test.log(LogStatus.PASS, "All fileds are displayed in the sort by drop down");
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
