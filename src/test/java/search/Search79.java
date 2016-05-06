package search;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Search79 extends TestBase {

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
						"Verify the changes taking place when user clicks on any CATEGORIES option in the search type ahead while ALL option is selected in the search drop down")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB79() throws Exception {

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

//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			 ob.navigate().to(host);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			new PageFactory().getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);
			Thread.sleep(2000);
			// Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("b");
			BrowserWaits.waitTime(1);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("i");
			BrowserWaits.waitTime(1);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("o");
			BrowserWaits.waitTime(1);
			Thread.sleep(8000);

			WebElement myE1 = ob.findElement(By.xpath(OR.getProperty("categoriesTile")));
			String text1 = myE1.getText();

			String[] arr1 = text1.split("\n");
			ArrayList<String> al1 = new ArrayList<String>();
			for (int i = 1; i < arr1.length; i++) {

				al1.add(arr1[i]);
			}

			int index = al1.get(2).indexOf(' ');
//			if(host.equals("https://projectne.thomsonreuters.com")){
//			
//				expected_text = al1.get(2).substring(0,3);
//				
//			}
//			else{
//				
//				expected_text = al1.get(2).substring(0,3);
//				
//			}
			
			String expected_text = al1.get(2).substring(0,3);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println(expected_text);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

			for (int i = 1; i <= 4; i++) {

				ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(Keys.ARROW_DOWN);
			}

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(Keys.ENTER);
			waitForPageLoad(ob);
			waitForAjax(ob);
			Thread.sleep(2000);

			String actual_text = ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).getAttribute("value");
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println(actual_text);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			

			String dd_text = ob.findElement(
					By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']")).getText();
			System.out.println(dd_text);

			if (!compareStrings("Articles", dd_text)) {

				test.log(LogStatus.FAIL, "ARTICLES option not getting selected in search drop down");// extent reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_ARTICLES_option_not_getting_selected_in_search_drop_down")));// screenshot

			}

			String lnp_text = ob.findElement(By.xpath("//li[@class='content-type-selector ng-scope active']"))
					.getText().substring(0, 8);
			System.out.println(lnp_text);

			if (!compareStrings("Articles", lnp_text)) {

				test.log(LogStatus.FAIL, "ARTICLES option not getting selected in left navigation pane");// extent
																											// reports
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_ARTICLES_option_not_getting_selected_in_left_navigation_pane")));// screenshot

			}

			JavascriptExecutor jse = (JavascriptExecutor) ob;

			for (int i = 1; i <= 5; i++) {

				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
				Thread.sleep(3000);

			}

			List<WebElement> tileTags = ob.findElements(By.tagName("h5"));
			int count = 0;
			for (int i = 0; i < tileTags.size(); i++) {

				if (tileTags.get(i).getText().equals("Article"))
					count++;
			}

			if (!compareNumbers(tileTags.size(), count)) {

				test.log(LogStatus.FAIL, "Items other than articles also getting displayed in the summary page");// extent
																													// report
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_items_other_than_articles_also_getting_displayed_in_the_summary_page")));// screenshot

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
