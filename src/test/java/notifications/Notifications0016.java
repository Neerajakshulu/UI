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
import pages.ProfilePage;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Notifications0016 extends NotificationsTestBase{
	
	static int status = 1;
	
	PageFactory pf = new PageFactory();
	
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('F'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var,
				"Verify that user is able to follow people from Recommended people to follow section on home page.")
				.assignCategory("Notifications");

	}
	
	@Test
	public void notifications026() throws Exception {
		
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Notifications");
		boolean testRunmode = TestUtil.isTestCaseRunnable(notificationxls, this.getClass().getSimpleName());
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
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			BrowserWaits.waitTime(4);
			//((JavascriptExecutor)ob).executeScript("arguments[0].scrollIntoView(true);",ob.findElement(By.cssSelector("div[class='col-xs-12 notification-event ng-scope'] span[class='webui-icon webui-icon-checkmark unfollow']")));
			//ob.findElement(By.cssSelector("div[class='col-xs-12 notification-event ng-scope'] span[class='webui-icon webui-icon-checkmark unfollow']")).click();
			//WebElement elements=ob.findElement(By.cssSelector("div[class='col-xs-12 notification-event ng-scope']"));
			ob.findElement(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_user_css"))).click();
			List<WebElement> elements=ob.findElements(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_css")));
			String actual = null;
			for(WebElement ele: elements) {
				System.out.println("attribute value-->"+ele.getAttribute("ng-if"));
				if(!(ele.getAttribute("ng-if").contains("TopUserCommenters"))) {
					actual=ele.findElement(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_username_css"))).getText();
					System.out.println("Text : "+actual);
					break;
				}
			}
			
			//List<WebElement> str=ob.findElements(By.cssSelector("div[class='col-xs-12 profile-info'] span a"));
			//String actual=str.get(0).getText();
			//String actual=elements.findElement(By.cssSelector(OR.getProperty("tr_notification_recommended_people_follow_username_css"))).getText();
			//System.out.println("Text : "+actual);
			BrowserWaits.waitTime(4);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 30);
			ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
			//BrowserWaits.waitTime(3);
			ProfilePage page=new ProfilePage(ob);
			page.clickProfileLink();
			page.clickFollowingTab();
			
			WebElement elem=ob.findElement(By.cssSelector(OR.getProperty("tr_notification_following_css")));
			List<WebElement> elemn=elem.findElements(By.tagName("div"));
			WebElement elem1=elemn.get(0);
			
			String result=elem1.findElement(By.cssSelector(OR.getProperty("tr_notification_following_topusername"))).getText();
			logger.info("Result "+result);
			
			
			try {
				Assert.assertEquals(actual, result);
				test.log(LogStatus.PASS, "Recommended people to follow section user displaying on following section");
				closeBrowser();
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "Recommended people to follow section user not displaying on following section");// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString()); // reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_recommended_people_to_follow_section_user_not_displaying_on_following_section")));// screenshot
				closeBrowser();
			}
			
			
			
		}
		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Recommended people to follow section user not displaying on following section");// extent
			// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_recommended_people_to_follow_section_user_not_displaying_on_following_section")));// screenshot
			closeBrowser();
		}
		
	}
	
	
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(notificationxls,
		 * "Test Cases", TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
