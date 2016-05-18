package notifications;

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

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Notifications0018 extends NotificationsTestBase {

	static int status = 1;

	PageFactory pf = new PageFactory();

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('F'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(var,
						"Verify that user is able to watch article from Recommended articles section on Home page.")
				.assignCategory("Notifications");

	}

	@Test
	public void testcaseF18() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Notifications");
		boolean testRunmode = TestUtil.isTestCaseRunnable(notificationxls, this.getClass().getSimpleName());
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

			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			BrowserWaits.waitTime(4);
			List<WebElement> element = ob
					.findElements(By.cssSelector("div[class='article-wrapper top-articles ng-scope']"));
			String actual = null;
			String watchstatus = null;
			for (WebElement elem : element) {
				if (!elem.getAttribute("ng-repeat").contains("article in vm.item.publication")) {
					List<WebElement> elment = elem
							.findElements(By.cssSelector("div[class='notification-publication'] h2 a"));
					actual = elment.get(0).getText();
					logger.info("Actual--> " + actual);
					List<WebElement> element1 = elem
							.findElements(By.cssSelector("div[class='notification-publication'] button"));
					element1.get(0).click();
					// allWatchLis=ob.findElements(By.cssSelector("div[class='select-watchlist ng-scope'] h4"));

					waitForElementTobeVisible(ob, By.xpath(OR.getProperty("selectWatchListInBucket")), 30);
					ob.findElement(By.xpath(OR.getProperty("selectWatchListInBucket"))).click();
					BrowserWaits.waitTime(4);
					ob.findElement(By.xpath(OR.getProperty("closeWatchListBucketDisplay"))).click();
					List<WebElement> element2 = elem
							.findElements(By.cssSelector("div[class='notification-publication'] button"));
					watchstatus = element2.get(0).getText();
					logger.info("Watch Status--> " + watchstatus);
					break;
				}
			}

			BrowserWaits.waitTime(4);
			// List<WebElement> elements=ob.findElements(By.cssSelector("li[class='ne-subnav-link-wrapper']"));
			// String str=elements.get(1).getText();
			// logger.info("Link Name : "+str);
			// elements.get(1).click();

			// List<WebElement> allWatchList=ob.findElements(By.cssSelector("div[class='col-xs-12 col-md-10
			// col-md-pull-2' ] h2 a"));

			/*
			 * for(WebElement ele : elements){ if(ele.getAttribute("href").contains("watchlist")){ ele. } }
			 */

			// String result=ob.findElement(By.cssSelector("div[class='col-xs-12 col-sm-8 col-md-9'] h2")).getText();
			// logger.info("Result--> "+result);
			try {
				Assert.assertEquals(watchstatus, "Stop Watching");
				test.log(LogStatus.PASS,
						"User is able to watch article from Recommended articles section on Home page.");
				logout();
				closeBrowser();
			} catch (Throwable t) {

				test.log(LogStatus.FAIL,
						"user is not able to watch article from Recommended articles section on Home page.");// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString()); // reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "user_is_not_able_to_watch_article_from_Recommended_articles_section_on_Home_page.")));// screenshot
				closeBrowser();
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL,
					"user is not able to watch article from Recommended articles section on Home page.");// extent
			// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.INFO,
					"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
							+ "user_is_not_able_to_watch_article_from_Recommended_articles_section_on_Home_page")));// screenshot
			closeBrowser();
		}

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "FAIL"); else TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
