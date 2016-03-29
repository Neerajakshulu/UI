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
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E43 extends TestBase {
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
						"Verify that user is able to watch a patent to a particular watchlist from notification in home page||Verify that user is able to unwatch a patent from watchlist from notification in home page")
				.assignCategory("Suite E");

	}

	@Test
	public void testWatchUnwatchPatentFromHomePage() throws Exception {

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

			openBrowser();
			maximizeWindow();
			clearCookies();
//			ob.get(host);
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			// 1)Login as user1 and comment on some patent
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			waitForElementTobeVisible(ob, By.id(OR.getProperty("TR_email_textBox")), 30);
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(user1);
			ob.findElement(By.id(OR.getProperty("TR_password_textBox")))
					.sendKeys(CONFIG.getProperty("defaultPassword"));
			ob.findElement(By.id(OR.getProperty("login_button"))).click();

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_type_dropdown")), 30);
			selectSearchTypeFromDropDown("Patents");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("biology");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("searchResults_links")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("document_comment_textbox")), 30);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox")))
					.sendKeys("Automation Script Comment: TestCase_E43");
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("document_addComment_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));

			Thread.sleep(2000);
			logout();

			// 2)Login with user2 and and try to watch the patent from
			// notification panel
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			waitForElementTobeClickable(ob, By.id("userid"), 30);
			ob.findElement(By.id("userid")).clear();
			ob.findElement(By.id("userid")).sendKeys(user2);
			ob.findElement(By.id("password")).sendKeys(CONFIG.getProperty("defaultPassword"));
			ob.findElement(By.id(OR.getProperty("login_button"))).click();
			waitForElementTobeVisible(ob, By.xpath("(//span[@class='ne-profile-object-title']/a)[1]"), 30);

			if (!(ob.findElements(By.xpath("(//span[@class='ne-profile-object-title']/a)[1]")).size() == 1)) {

				test.log(LogStatus.FAIL, "User not receiving notification");// extent
																			// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_user_not_receiving_notification")));// screenshot
				closeBrowser();
				return;
			}
			// Watching the patent to a particular watch list
			WebElement watchButton = ob
					.findElement(By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"));
			String selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);

			// Selecting the document name
			String documentName = ob
					.findElement(By.xpath("(" + OR.getProperty("document_link_in_home_page") + ")[" + 2 + "]"))
					.getText();

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(selectedWatchlistName);

			List<WebElement> watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));

			int count = 0;
			for (int i = 0; i < watchedItems.size(); i++) {

				if (watchedItems.get(i).getText().equals(documentName))
					count++;

			}

			if (compareNumbers(1, count)) {
				test.log(LogStatus.PASS, "User is able to add a patent into watchlist from home page");

			} else {
				test.log(LogStatus.FAIL, "User not able to add a patent into watchlist from home page");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_unable_to_add_patent_into_watchlist_from_home_page")));// screenshot
				return;
			}

			// Navigating to the home page
			ob.findElement(By.xpath(OR.getProperty("home_link"))).click();
			waitForElementTobeClickable(ob, By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"),
					30);

			// Unwatching the patent to a particular watch list
			watchButton = ob.findElement(By.xpath("(" + OR.getProperty("search_watchlist_image") + ")[" + 2 + "]"));
			selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);

			// Selecting the document name
			documentName = ob.findElement(By.xpath("(" + OR.getProperty("document_link_in_home_page") + ")[" + 2 + "]"))
					.getText();

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(selectedWatchlistName);

			try {

				WebElement defaultMessage = ob.findElement(By.xpath(OR.getProperty("default_message_watchlist")));

				if (defaultMessage.isDisplayed()) {

					test.log(LogStatus.PASS, "User is able to remove a patent from watchlist in home page");// extent
				} else {
					test.log(LogStatus.FAIL, "User not able to remove a patent from watchlist in home page");// extent
					// reports
					status = 2;// excel
					test.log(LogStatus.INFO,
							"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_user_unable_to_remove_patent_from_watchlist_in_home_page")));// screenshot
				}
			} catch (NoSuchElementException e) {

				watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
				count = 0;
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
