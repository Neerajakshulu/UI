package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E14 extends TestBase {
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
		test = extent.startTest(var, "Verify that user is able to unwatch an Patent from Record view page")
				.assignCategory("Suite E");

	}

	@Test
	@Parameters({ "patentName" })
	public void testUnwatchPatentFromPatentsRecordViewPage(String patentName) throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "E Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteExls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			// String search_query = "biology";

			// Making a new user
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			createNewUser("mask", "man");
			// ob.navigate().to(host);
			// LoginTR.enterTRCredentials("Prasenjit.Patra@thomsonreuters.com",
			// "Techm@2015");
			// LoginTR.clickLogin();
			// Thread.sleep(15000);

			// Searching for patent
			ob.findElement(By.xpath("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']"))
					.click();
			waitForElementTobeVisible(ob, By.xpath("//ul[@class='dropdown-menu']"), 5);
			ob.findElement(By.linkText("Patents")).click();
			Thread.sleep(2000);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(patentName);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(8000);

			// Navigating to record view page
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			Thread.sleep(8000);
			// Watching the record
			ob.findElement(By.xpath(OR.getProperty("document_watchlist_button"))).click();

			// Wait until select a watch list model loads
			waitForElementTobeVisible(ob, By.xpath("//div[@class='select-watchlist-modal ng-scope']"), 5);
			// Select the first watch list from the model
			waitForElementTobeClickable(ob,
					By.xpath("//button[@class='pull-left btn webui-icon-btn watchlist-toggle-button']"), 5);
			// Adding the item into watch list
			ob.findElement(By.xpath("//button[@class='pull-left btn webui-icon-btn watchlist-toggle-button']")).click();
			Thread.sleep(4000);
			// Selecting the watch list name
			String selectedWatchlistName = ob.findElement(By.xpath("//h4[@class='select-watchlist-title ng-binding']"))
					.getText();
			// Closing the select a model
			ob.findElement(By.xpath("//button[@class='close']")).click();
			Thread.sleep(4000);

			// Unwatching the patent
			ob.findElement(By.xpath(OR.getProperty("document_watchlist_button"))).click();

			// Wait until select a watch list model loads
			waitForElementTobeVisible(ob, By.xpath("//div[@class='select-watchlist-modal ng-scope']"), 5);
			// Select the first watch list from the model
			waitForElementTobeClickable(ob,
					By.xpath("//button[@class='pull-left btn webui-icon-btn watchlist-toggle-button']"), 5);
			Thread.sleep(4000);
			// removing the item into watch list
			ob.findElement(By.xpath("//button[@class='pull-left btn webui-icon-btn watchlist-toggle-button']")).click();
			// Closing the select a model
			ob.findElement(By.xpath("//button[@class='close']")).click();
			Thread.sleep(4000);

			// Selecting the document name
			String documentName = ob.findElement(By.xpath("//h2[@class='record-heading ng-binding']")).getText();

			// Navigate to the watch list landing page
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();
			// ob.findElement(By.xpath("//a[@href='#/watchlist']")).click();
			Thread.sleep(8000);

			// Getting all the watch lists
			List<WebElement> watchLists = ob.findElements(By.xpath("// a[@class='ng-binding']"));
			// Finding the particular watch list and navigating to it
			for (int i = 0; i < watchLists.size(); i++) {
				if (watchLists.get(i).getText().equals(selectedWatchlistName)) {
					watchLists.get(i).click();
					Thread.sleep(4000);
					break;
				}
			}

			try {

				WebElement defaultMessage = ob
						.findElement(By.xpath("//div[@class='row'][@ng-show='vm.supportingCopyIsVisible']"));

				if (defaultMessage.isDisplayed()) {

					test.log(LogStatus.PASS,
							"User is able to remove an patent from watchlist in Patent record view page");// extent
				} else {
					test.log(LogStatus.FAIL,
							"User not able to remove an patent from watchlist in Patent record view page");// extent
					// reports
					status = 2;// excel
					test.log(LogStatus.INFO,
							"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_user_unable_to_remove_patent_from_watchlist_in_Patent_record_view_page")));// screenshot
				}
			} catch (NoSuchElementException e) {

				List<WebElement> watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
				int count = 0;
				for (int i = 0; i < watchedItems.size(); i++) {

					if (watchedItems.get(i).getText().equals(documentName))
						count++;

				}
				Assert.assertEquals(count, 0);
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

		if (status == 1)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "SKIP");

	}

}
