package Authoring;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

public class Authoring25 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;
	static boolean master_condition;

	static int time = 30;
	PageFactory pf = new PageFactory();

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("Authoring");
		runmodes = TestUtil.getDataSetRunmodes(authoringxls, this.getClass().getSimpleName());
	}

	@Test
	public void testOpenApplication() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			test.log(LogStatus.INFO, "Runmode for test set data set to no " + count);
			skip = true;
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		try {
			// selenium code
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(System.getProperty("host"));
			// ob.get(CONFIG.getProperty("testSiteName"));
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "UnExpected Error");
			// print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(e);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_Article_Search_not_happening")));
			// closeBrowser();
		}
	}

	@Test(dependsOnMethods = "testOpenApplication")
	@Parameters({"username", "password", "article", "completeArticle"})
	public void chooseArtilce(String username,
			String password,
			String article,
			String completeArticle) throws Exception {
		try {
			// waitForTRHomePage();
			loginAs("USERNAME12", "PASSWORD12");
			pf.getSearchResultsPageInstance(ob).searchArticle(article);
			pf.getSearchResultsPageInstance(ob).chooseArticle();
			pf.getPostCommentPageInstance(ob).enterArticleComments("test");
			pf.getPostCommentPageInstance(ob).clickAddCommentButton();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "UnExpected Error");
			// print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(e);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_Article_Search_not_happening")));
			// closeBrowser();
		}
	}

	@Test(dependsOnMethods = "chooseArtilce", dataProvider = "getTestData")
	public void unSupportedTagsCommentsCheck(String htmlTags,
			String errorMessage) throws Exception {
		try {

			test.log(LogStatus.INFO, this.getClass().getSimpleName()
					+ "  UnSupported HTML Tags execution starts for data set #" + (count + 1) + "--->");
			//BrowserWaits.waitTime(10);
			pf.getPostCommentPageInstance(ob).updateComment(test, htmlTags);
			//BrowserWaits.waitTime(5);
			waitForElementTobeVisible(ob,
					By.cssSelector(OnePObjectMap.RECORD_VIEW_PAGE_COMMENTS_EDIT_ERROR_MESSAGE_CSS.toString()), 200);
			String unSupporteTagErrorMessage = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.RECORD_VIEW_PAGE_COMMENTS_EDIT_ERROR_MESSAGE_CSS).getText();
			if (!unSupporteTagErrorMessage.equalsIgnoreCase(errorMessage)) {
				throw new Exception("UnSupported_HTML_tags_doesnot_allow_comments_validation");
			}

		} catch (Exception e) {
			test.log(LogStatus.FAIL, "UnExpected Error");
			status = 2;
			fail = true;
			// print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(e);
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_UnSupported_HTML_tags_doesnot_allow_comments_validation")));
			// closeBrowser();
		} finally {
			reportDataSetResult();
			++count;
			jsClick(ob, ob.findElement(By.cssSelector("button[ng-click^='cancelEdit']")));

		}

	}

	public void reportDataSetResult() {
		/*
		 * if(skip) { TestUtil.reportDataSetResult(authoringxls, this.getClass().getSimpleName(), count+2, "SKIP"); }
		 * else if(fail) { status=2; TestUtil.reportDataSetResult(authoringxls, this.getClass().getSimpleName(),
		 * count+2, "FAIL"); } else { TestUtil.reportDataSetResult(authoringxls, this.getClass().getSimpleName(),
		 * count+2, "PASS"); }
		 */

		skip = false;
		fail = false;

	}

	@AfterTest
	public void reportTestResult() {

		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(authoringxls, "Test Cases" ,
		 * TestUtil.getRowNum(authoringxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(authoringxls, "Test Cases",
		 * TestUtil.getRowNum(authoringxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(authoringxls, "Test Cases" ,
		 * TestUtil.getRowNum(authoringxls,this.getClass().getSimpleName()), "SKIP");
		 */

		closeBrowser();
	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(authoringxls, this.getClass().getSimpleName());
	}
}
