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

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Search44 extends TestBase {

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
		test = extent.startTest(var,
				"Verify that user is able to expand and collapse the Institutions filter in ARTICLES content type")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB8() throws Exception {

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

			String search_query = "biology";

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			ob.navigate().to(host);
			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			// Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath("//li[@class='content-type-selector ng-scope' and contains(text(),'Articles')]"), 30);
			Thread.sleep(2000);
			ob.findElement(By.xpath("//li[@class='content-type-selector ng-scope' and contains(text(),'Articles')]")).click();
			waitForElementTobeVisible(ob, By.xpath("//span[@class='h6 agg-category-title ng-binding' and contains(text(),'Institutions')]"), 30);
			Thread.sleep(2000);
			ob.findElement(By.xpath("//span[@class='h6 agg-category-title ng-binding' and contains(text(),'Institutions')]")).click();
			Thread.sleep(3000);
			
			if(!checkElementPresence("filter_up_icon")){
				
				test.log(LogStatus.FAIL, "Filter not getting expended");// extent report
				status = 2;// excel
				test.log(
				LogStatus.INFO,
				"Snapshot below: "
				+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
				+ "_filter_not_expanding")));// screenshot	
				
				
			}
			
			
			ob.findElement(By.xpath("//span[@class='h6 agg-category-title ng-binding' and contains(text(),'Institutions')]")).click();
			Thread.sleep(3000);
			
			List<WebElement> mylist=ob.findElements(By.xpath(OR.getProperty("filter_up_icon")));
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println(mylist.size());
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			
			try{
				
				Assert.assertEquals(mylist.size(), 0);
				
				test.log(LogStatus.PASS, "Filter getting collapsed");// extent report
				
			}
			
			catch(Throwable t){
				
				test.log(LogStatus.FAIL, "Filter not getting collapsed");// extent report
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(
				LogStatus.INFO,
				"Snapshot below: "
				+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
				+ "_filter_not_getting_collapsed")));// screenshot
				
				
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
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	private void expandFilter() throws Exception {
		List<WebElement> filterPanelHeadingList;
		List<WebElement> filterPanelBodyList;
		WebElement documentTypePanelBody;
		WebElement documentTypePanelHeading;
		// Capturing panel heading after expanding document type filter
		filterPanelHeadingList = ob.findElements(By.cssSelector("div[class=panel-heading]"));
		documentTypePanelHeading = filterPanelHeadingList.get(3);
		WebElement upArrow = documentTypePanelHeading.findElement(By
				.cssSelector("i[class='webui-icon pull-right droparrow webui-icon-arrow-up']"));

		if (upArrow != null) {
			test.log(LogStatus.PASS, "Up arrow is visible for Institutions filter");
		}

		filterPanelBodyList = ob.findElements(By.cssSelector("div[class='panel-collapse in']"));
		documentTypePanelBody = filterPanelBodyList.get(0);

		if (documentTypePanelBody.isDisplayed()) {
			test.log(LogStatus.PASS, "Institutions filter values are displayed");
		}

		// Collapse the document type filter by clicking it again
		documentTypePanelHeading.click();
		Thread.sleep(2000);

	}

	private void collapseFilter() {
		// Finding out the types filer in refine panel
		List<WebElement> filterPanelHeadingList = ob.findElements(By.cssSelector("div[class=panel-heading]"));
		WebElement documentTypePanelHeading = filterPanelHeadingList.get(3);
		WebElement downArrow = documentTypePanelHeading.findElement(By
				.cssSelector("i[class='webui-icon pull-right droparrow webui-icon-arrow-down']"));

		if (downArrow != null) {
			test.log(LogStatus.PASS, "Down arrow is visible for Institutions filter");
		}
		List<WebElement> filterPanelBodyList = ob.findElements(By.cssSelector("div[class='panel-collapse collapse']"));
		WebElement documentTypePanelBody = filterPanelBodyList.get(3);

		if (!documentTypePanelBody.isDisplayed()) {
			test.log(LogStatus.PASS, "Institutions filter values are not displayed");
		}
		// Expanding the document type filter by clicking it
		documentTypePanelHeading.click();
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

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
