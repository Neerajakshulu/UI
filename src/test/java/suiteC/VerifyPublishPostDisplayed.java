package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

public class VerifyPublishPostDisplayed extends TestBase{
	static int status = 1;
	PageFactory pf=new PageFactory();
	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify that Publish a Post option is displayed in Home page and all Record view pages like Article,Post ,Patent")
				.assignCategory("Suite C");

	}
	
	
	@Test
	public void testEditDraftsFromModalWindow() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteCxls, this.getClass().getSimpleName());
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

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			//ob.get(CONFIG.getProperty("testSiteName"));

			pf.getLoginTRInstance(ob).enterTRCredentials(CONFIG.getProperty("defaultUsername"), CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			test.log(LogStatus.INFO, "Logged in to NEON");
			
			//checking for articles
			test.log(LogStatus.INFO, "Checking for article record view");
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_TEXTBOX_XPATH.toString())).clear();
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_TEXTBOX_XPATH.toString())).sendKeys("Bio");
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_BUTTON_XPATH.toString())).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tab_articles_result")), 40);
			ob.findElement(By.xpath(OR.getProperty("tab_articles_result"))).click();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_RESULTS_ARTICLES_LINK.toString()), 40);
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_RESULTS_ARTICLES_LINK.toString())).click();
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS.toString()), 40);
			boolean articlePublishButton=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS.toString())).isDisplayed();
			
			//checking for patents
			test.log(LogStatus.INFO, "Checking for patents record view");
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_TEXTBOX_XPATH.toString())).clear();
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_TEXTBOX_XPATH.toString())).sendKeys("Bio");
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_BUTTON_XPATH.toString())).click();
			BrowserWaits.waitTime(10);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tab_patents_result")), 60);
			ob.findElement(By.xpath(OR.getProperty("tab_patents_result"))).click();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_RESULTS_PATENTS_LINK.toString()), 40);
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_RESULTS_PATENTS_LINK.toString())).click();
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS.toString()), 40);
			boolean patentsPublishButton=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS.toString())).isDisplayed();
			
			//checking for posts
			test.log(LogStatus.INFO, "Checking for posts record view");
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_TEXTBOX_XPATH.toString())).clear();
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_TEXTBOX_XPATH.toString())).sendKeys("Post for");
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_BUTTON_XPATH.toString())).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tab_posts_result")), 40);
			ob.findElement(By.xpath(OR.getProperty("tab_posts_result"))).click();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_RESULTS_POSTS_LINK.toString()), 40);
			ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_SEARCH_RESULTS_POSTS_LINK.toString())).click();
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS.toString()), 40);
			boolean postsPublishButton=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS.toString())).isDisplayed();
			
			try{
				Assert.assertTrue(articlePublishButton && patentsPublishButton && postsPublishButton);
				test.log(LogStatus.INFO, "Publish a post button is displyed in Article,Patent,Post record view");
			}catch(Throwable t){
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Publish button is not present");// extent
																			// reports
				// next 3 lines to print whole testng error in report
				status = 2;// excel
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng

				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
				closeBrowser();
			}
			
				
			logout();
			closeBrowser();
		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			status = 2;// excel
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng

			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}
	
	
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	/*	if (status == 1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "SKIP");*/

	}
	
}
