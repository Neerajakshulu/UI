package suiteE;

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
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E34 extends TestBase {
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
		test = extent.startTest(var, "Verify that no one can see the private watchlists of a user on user's profile page").assignCategory("Suite E");

	}

	@Test
	public void testPrivateWatchlistNotVisibleFromOthersProfile() throws Exception {

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

			// 1)Create User1 and logout
			openBrowser();
			maximizeWindow();
			clearCookies();
			fn1 = generateRandomName(8);
			ln1 = generateRandomName(10);
			System.out.println(fn1 + " " + ln1);
			user1 = createNewUser(fn1, ln1);
			// Navigate to the watch list landing page
			ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();

			// Creating 4 private and 1 public watch list
			String newWatchlistName = "New Watchlist";
			for (int i = 1; i <= 5; i++) {
				ob.findElement(By.xpath(OR.getProperty("createWatchListButton"))).click();
				Thread.sleep(2000);
				ob.findElement(By.xpath(OR.getProperty("newWatchListNameTextBox"))).sendKeys(newWatchlistName + i);
				ob.findElement(By.xpath(OR.getProperty("newWatchListDescriptionTextArea")))
						.sendKeys("This is my newly created watch list");
				if (i == 5)
					ob.findElement(By.xpath(OR.getProperty("newWatchListPublicCheckBox"))).click();
				// Clicking on Create button
				ob.findElement(By.xpath(OR.getProperty("newWatchListCreateButton"))).click();
				Thread.sleep(4000);
			}
			LoginTR.logOutApp();
			closeBrowser();
			// 2)Login as User2 and navigate to the user1 profile page
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
			LoginTR.enterTRCredentials("Prasenjit.Patra@thomsonreuters.com", "Techm@2015");
			LoginTR.clickLogin();
			Thread.sleep(15000);
			// Searching for article
			selectSearchTypeFromDropDown("People");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(fn1 + " " + ln1);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			// Navigating to the first user profile page
			// ob.findElement(By.xpath("//a[@event-category='searchresult-ck-profile']")).click();
			ob.findElement(By.linkText(fn1 + " " + ln1)).click();
			Thread.sleep(5000);
			// Navigating to the watch list tab
			ob.findElement(By.xpath("//a/span[contains(text(),'Watchlists')]")).click();
			Thread.sleep(8000);
			List<WebElement> watchlists = ob.findElements(By.xpath("//a[contains(@ui-sref,'watchlist-detail')]"));
			int count = 0;
			for (int i = 0; i <= 4; i++) {
				for (WebElement watchlist : watchlists) {
					if (watchlist.getText().equals(newWatchlistName + i)) {
						count++;
						break;
					}
				}
			}

			try {
				Assert.assertEquals(0, count);
				test.log(LogStatus.PASS, "Others can not see the private watchlists of a user on user's profile page");
			} catch (Error e) {
				status = 2;
				test.log(LogStatus.FAIL, "Others able to see the private watchlists of a user on user's profile page");
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
