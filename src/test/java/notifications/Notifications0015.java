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

public class Notifications0015 extends NotificationsTestBase {

	static int status = 1;

	PageFactory pf = new PageFactory();

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('F'), this.getClass().getSimpleName(), 1);
		String dec = xlRead2(returnExcelPath('F'), this.getClass().getSimpleName(), 2);
		test = extent.startTest(var, dec).assignCategory("Notifications");

	}

	@Test
	public void notifications026() throws Exception {

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
			/*WebElement elements = ob.findElement(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_css")));
			WebElement elements1 = elements.findElement(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_username_css")));
			String actual = elements1.getText();
			System.out.println("Text : " + actual);
			elements1.click();*/
			ob.findElement(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_user_css")));
			List<WebElement> elements=ob.findElements(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_css")));
			String actual = null;
			for(WebElement ele: elements) {
				System.out.println("attribute value-->"+ele.getAttribute("ng-if"));
				if(!(ele.getAttribute("ng-if").contains("TopUserCommenters"))) {
					actual=ele.findElement(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_username_css"))).getText();
					System.out.println("Text : "+actual);
					ele.findElement(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_username_css"))).click();
					break;
					}
				

			}
			
			BrowserWaits.waitTime(4);
			String result = ob.findElement(By.cssSelector(OR.getProperty("tr_notification_profile_name_css"))).getText();
			System.out.println("Result " + result);

			try {
				Assert.assertEquals(actual, result);
				test.log(LogStatus.PASS, "User is displaying profile page by clicking author name in Recommended people to follow section on home page");
				closeBrowser();
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User is not displaying profile page by clicking author name in Recommended people to follow section on home page");// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString()); // reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "User_is_not_displaying_profile_page_by_clicking_author_name_in_Recommended_people_to_follow_section_on_home_page")));// screenshot
				closeBrowser();
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "User is not displaying profile page by clicking author name in Recommended people to follow section on home page");// extent
			// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "User_is_not_displaying_profile_page_by_clicking_author_name_in_Recommended_people_to_follow_section_on_home_page")));// screenshot
			 closeBrowser();
		}

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls,
		 * "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(notificationxls,
		 * "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
