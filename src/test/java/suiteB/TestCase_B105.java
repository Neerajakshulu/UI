package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B105 extends TestBase {
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
						"Verify that record view page of a post gets displayed when user clicks on article title in POSTs search results page||"
						+ "Verify that following fields get displayed correctly for a post in record view page:"
						+ "a)Title b)Creation date and time c)Last edited date and time d)Author e)Author details f)Post content g)Likes count h)Comments count")
				.assignCategory("Suite B");	

	}

	@Test
	public void testcaseB105() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteBxls, this.getClass().getSimpleName());
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
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			ob.navigate().to(host);
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			new PageFactory().getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);
			
			String post="sample post";
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(post);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			ob.findElement(By.xpath(OR.getProperty("tab_posts_result"))).click();
			waitForAjax(ob);
			
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))).click();
			waitForAjax(ob);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_patent_record_view_watch_share_css")), 120);

			
			String patentRVTitle=ob.findElement(By.cssSelector(OR.getProperty("tr_patent_record_view_css"))).getText();
			String patentRVTitleWatchLabel=ob.findElement(By.cssSelector(OR.getProperty("tr_patent_record_view_watch_share_css"))).getText();
			String patentRVShareLabel=ob.findElements(By.cssSelector(OR.getProperty("tr_patent_record_view_watch_share_css"))).get(1).getText();
			
			List<WebElement> postCreationAndEdit=ob.findElements(By.xpath("//div[@class='row timestamp-wrapper']/div/span[@class='meta']"));
			boolean postEditCreateDate;
			if(postCreationAndEdit.size()>=2){
				 postEditCreateDate=postCreationAndEdit.get(0).getText().equalsIgnoreCase("EDITED") && postCreationAndEdit.get(1).getText().equalsIgnoreCase("POSTED");
			}
			else {
				 postEditCreateDate=postCreationAndEdit.get(0).getText().equalsIgnoreCase("POSTED");
			}
			
			//System.out.println("post creationstatus-->"+postEditCreateDate);
			
			String postAuthor=ob.findElement(By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath"))).getText();
			String postAuthorMetaData=ob.findElement(By.xpath(OR.getProperty("tr_search_people_profile_description_xpath"))).getText();
			
			//System.out.println("post author-->"+postAuthor);
			//System.out.println("post author metadata-->"+postAuthorMetaData);
			
			List<WebElement> postSocialShare=ob.findElements(By.cssSelector(("div[class='doc-info'] span[class*='stat-count']")));
			
			int postCommentCount=Integer.parseInt(postSocialShare.get(0).getText());
			int postLikeCount=Integer.parseInt(postSocialShare.get(1).getText());
			
			boolean socialShareStatus=(postCommentCount>=0 && postLikeCount>=0);
			
			//System.out.println("post comment count-->"+postCommentCount);
			//System.out.println("post like count-->"+postLikeCount);
			
			boolean patentRVStatus = StringUtils.containsIgnoreCase(patentRVTitle, post)
					&& StringUtils.containsIgnoreCase(patentRVTitleWatchLabel, "watch")
					&& StringUtils.containsIgnoreCase(patentRVShareLabel, "Share");

			if(!patentRVStatus)
				throw new Exception("Page is not Navigating to Post Record View Page");
			
			boolean postFieldsStatus=postEditCreateDate&&(!postAuthor.isEmpty())&&(!postAuthorMetaData.isEmpty())&&socialShareStatus;
			System.out.println("post fields status-->"+postFieldsStatus);
			if(!postFieldsStatus)
				throw new Exception("Following post fields not displayed in record view page a)Title b)Creation date and time "
						+ "c)Last edited date and time d)Author e)Author details f)Post content g)Likes count h)Comments count");

			
			closeBrowser();

		} 
		
		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_patent_recordview_failed")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}


	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

//		if (status == 1)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
//		else if (status == 2)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
//		else
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}


}
