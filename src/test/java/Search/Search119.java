package Search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Search119 extends TestBase {

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
						"Verify that following things are retained when user navigates back to ALL search results page from record view page:a)Sortingb)Left navigation pane content typec)Search drop down content type")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB109() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Search");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteBxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			// ob.navigate().to(host);
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);

			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']"))
					.click();
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'Patents')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'Patents')]")).click();
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("argentina");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			waitForElementTobeVisible(ob, By.id("single-button"), 30);
			ob.findElement(By.id("single-button")).click();
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'Times Cited')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'Times Cited')]")).click();

			Thread.sleep(5000);
			List<WebElement> searchResults1 = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
			ArrayList<String> al1 = new ArrayList<String>();
			ArrayList<String> al2 = new ArrayList<String>();

			for (int i = 0; i < searchResults1.size(); i++) {

				al1.add(searchResults1.get(i).getText());
				// System.out.println(al1.get(i));
			}

			// System.out.println("al1 size="+al1.size());

			searchResults1.get(0).click();
			Thread.sleep(5000);
			ob.navigate().back();
			Thread.sleep(5000);

			List<WebElement> searchResults2 = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));

			for (int i = 0; i < searchResults2.size(); i++) {

				al2.add(searchResults2.get(i).getText());
				// System.out.println(al2.get(i));
			}

			// System.out.println("al2 size="+al2.size());

			try {

				Assert.assertTrue(al1.equals(al2));
				test.log(LogStatus.PASS,
						"Sorting is retained when user navigates back to ALL search results page from record view page");// extent
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Sorting is not retained when user navigates back to ALL search results page from record view page");// extent
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_sorting_not_retained")));// screenshot
			}

			String text = ob.findElement(By.id("single-button")).getText();
			// System.out.println(text);

			if (!compareStrings("Times Cited", text)) {

				test.log(LogStatus.FAIL, "Incorrect attribute present in SORT BY drop down");// extent
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_incorrect_attribute_present_in_SORT_BY__drop_down")));// screenshot

			}

			String text1 = ob.findElement(
					By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).getText();
			// System.out.println("Text1="+text1);

			if (!compareStrings("Patents", text1)) {

				test.log(LogStatus.FAIL,
						"Search drop down option not retained when user navigates back to ALL search results page from record view page");// extent
																																			// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_search_drop_down_option_not_retained")));// screenshot

			}

			String text3 = ob.findElement(By.xpath("//li[@class='content-type-selector ng-scope active']")).getText();
			// System.out.println(text3);

			try {

				Assert.assertTrue(text3.contains("Patents"));
				test.log(
						LogStatus.PASS,
						"Content type in the left navigation pane getting retained correctly when user navigates back to search results page from record view page");// extent
			} catch (Throwable t) {

				test.log(
						LogStatus.FAIL,
						"Content type in the left navigation pane not getting retained when user navigates back to search results page from record view page");// extent
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_content_type_in_left_navigation_pane_not_getting_retained")));// screenshot

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

		// if (status == 1)
		// TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
		// TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
		// else if (status == 2)
		// TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
		// TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
		// else
		// TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
		// TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}

}
