package watchlist;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

/**
 * Verify that user is able to add a Post from Posts content search results page to a particular watchlist||Verify that
 * user is able to unwatch a Post from watchlist page||Verify that user is able to unwatch a Post from Posts content
 * search results page
 * 
 * @author Prasenjit Patra
 *
 */
public class Watchlist008 extends TestBase {

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
	@Parameters({"postName2"})
	public void testWatchPostFromPostsContentSearchResult(String postName2) throws Exception {

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
			// runOnSauceLabsFromLocal("Windows","Chrome");

			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			ob.navigate().to(host);

			// login();

			loginAsSpecifiedUser(LOGIN.getProperty("LOGINUSERNAME1"), LOGIN.getProperty("LOGINPASSWORD1"));

			// Create watch list
			String newWatchlistName = this.getClass().getSimpleName() + "_" + getCurrentTimeStamp();
			logger.info("New WatchList Name : " + newWatchlistName);
			createWatchList("private", newWatchlistName, "This is my test watchlist.");

			// Searching for Post

			//ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("Post for Testing RecordView0adpdH");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(postName2);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			waitForElementTobeClickable(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_POSTS_CSS.toString()), 60);
			ob.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_POSTS_CSS.toString())).click();
			Thread.sleep(3000);
			List<WebElement> watchButtonList = ob.findElements(By.xpath(OR.getProperty("search_watchlist_image")));
			// Watching 2 articles to a particular watch list
			for (int i = 0; i < 1; i++) {

				WebElement watchButton = watchButtonList.get(i);
				watchButton.click();
				Thread.sleep(5000);
				ob.findElement(By.linkText(newWatchlistName)).click();
				Thread.sleep(3000);
				ob.findElement(By.xpath("//input[@type='text']")).click();
			}

			// Selecting the document name
			String firstdocumentName = ob
					.findElement(By.xpath("//div[@class='wui-content-title wui-content-title--medium ng-binding']"))
					.getText();
			logger.info("First Document Name : " + firstdocumentName);

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(newWatchlistName);
			waitForPageLoad(ob);

			List<WebElement> watchedItems = ob
					.findElements(By.xpath("//div[@class='wui-content-title wui-content-title--medium ng-binding']"));

			int count = 0;
			for (int i = 0; i < watchedItems.size(); i++) {

				if (watchedItems.get(i).getText().equals(firstdocumentName))
					count++;

			}

			if (!compareNumbers(1, count)) {

				test.log(LogStatus.FAIL,
						"User not able to add a patent into watchlist from Patent content search results page");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO,
						"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
								+ "_user_unable_to_add_patent_into_watchlist_from_Patent_content_searchResults_page")));// screenshot

			}
			deleteParticularWatchlist(newWatchlistName);
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
		 */
	}

}
