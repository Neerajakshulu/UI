package search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class Search82 extends TestBase {

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
				.startTest(var,
						"Verify that search results are sorted correctly by TIMES CITED field in SORT BY drop down in ALL search results page")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB82() throws Exception {

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

			openBrowser();
			clearCookies();
			maximizeWindow();

			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
		
			// login using TR credentials
			login();
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);

			// Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("bio");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);

			ob.findElement(By.id("single-button")).click();
			waitForElementTobeClickable(ob, By.xpath("//a[contains(text(),'Times Cited')]"), 120);

			ob.findElement(By.xpath("//a[contains(text(),'Times Cited')]")).click();
			waitForAjax(ob);

			JavascriptExecutor jse = (JavascriptExecutor) ob;

			for (int i = 1; i <= 5; i++) {

				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
				waitForAjax(ob);

			}

			List<WebElement> mylist = ob.findElements(By.xpath("//span[@class='h5 orange-counter ng-binding']"));
			ArrayList<Integer> al1 = new ArrayList<Integer>();
			ArrayList<Integer> al2 = new ArrayList<Integer>();

			for (int i = 0; i < mylist.size(); i++) {

				if (i % 2 == 0) {

					al1.add(convertStringToInt(mylist.get(i).getText()));

				}
			}

			al2.addAll(al1);

			Collections.sort(al2);
			Collections.reverse(al2);

			// for(int i=0;i<al1.size();i++){
			//
			// System.out.println(al1.get(i));
			// }
			//
			// System.out.println("#########################################");
			//
			// for(int i=0;i<al2.size();i++){
			//
			// System.out.println(al2.get(i));
			// }

			// if(al1.equals(al2))
			// System.out.println("pass");
			// else
			// System.out.println("fail");

			try {

				Assert.assertTrue(al1.equals(al2));
				test.log(LogStatus.PASS,
						"Search results are sorted correctly by TIMES CITED field in SORT BY drop down in ALL search results page");// extent
																																	// reports
			}

			catch (Throwable t) {

				test.log(LogStatus.FAIL,
						"Search results are not sorted correctly by TIMES CITED field in SORT BY drop down in ALL search results page");// extent
																																		// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_search_results_are_not_sorted_correctly_by_TIMES_CITED_field_in_ALL_search_results_page")));// screenshot

			}

			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
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

		// if(status==1)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "PASS");
		// else if(status==2)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "FAIL");
		// else
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls,this.getClass().getSimpleName()), "SKIP");

	}

}
