package suiteF;

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
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class TestCase_F17 extends TestBase {
	static int status = 1;
	PageFactory pf = new PageFactory();
	// Following is the list of status:
		// 1--->PASS
		// 2--->FAIL
		// 3--->SKIP
		// Checking whether this test case should be skipped or not
		@BeforeTest
		public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);
			String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
					Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
			test = extent.startTest(var, "Verify that Featured Post is at the top of event stream after login and that feature post should be top in post tab of trending section")
					.assignCategory("Suite F");

		}
		
		
		@Test
		public void testcaseF17() throws Exception {
			boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "F Suite");
			boolean testRunmode = TestUtil.isTestCaseRunnable(suiteFxls, this.getClass().getSimpleName());
			boolean master_condition = suiteRunmode && testRunmode;

			if (!master_condition) {

				status = 3;// excel
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

			}

			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
			try{
				openBrowser();
				maximizeWindow();
				clearCookies();
				ob.navigate().to(host);
				//Logging in with User2
				pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
				pf.getLoginTRInstance(ob).clickLogin();
				Thread.sleep(8000);
				
				List<WebElement> listOfNotifications=ob.findElements(By.xpath(OR.getProperty("all_notifications_in_homepage")));
				String text=listOfNotifications.get(0).getText();
				System.out.println(text);
				Assert.assertTrue(text.contains("Featured post"));
				test.log(LogStatus.PASS, "Featured post is at the top of the home page");
				List<WebElement> listOfPostsLinks=ob.findElements(By.xpath(OR.getProperty("all_posts_in_trending_now")));
				String expectedTitle=listOfPostsLinks.get(0).getText();
				System.out.println(expectedTitle);
				
				try{
					Assert.assertTrue(text.contains(expectedTitle));
					test.log(LogStatus.PASS, "Featured post is same as the post in trending section");
					pf.getLoginTRInstance(ob).logOutApp();
					closeBrowser();
				}catch(Throwable t){
					test.log(LogStatus.FAIL, "Featured Post title is not same as the post in the trending section");// extent
					// reports
					test.log(LogStatus.INFO, "Error--->" + t);
					ErrorUtil.addVerificationFailure(t);
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_Featured Post title is not same as the post in the trending section")));// screenshot
					closeBrowser();
				}
				
			}catch(Throwable t){
				test.log(LogStatus.FAIL, "Featured Post is not at the top");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_Featured Post is not at the top")));// screenshot
				closeBrowser();
			}
		}
		
		
		@AfterTest
		public void reportTestResult() {
			extent.endTest(test);

		/*	if (status == 1)
				TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
						TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "PASS");
			else if (status == 2)
				TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
						TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "FAIL");
			else
				TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
						TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "SKIP");
*/
		}
}

