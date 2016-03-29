package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.TestUtil;

public class VerifyFlagSetByOtherUsers extends TestBase {
	private static final String PASSWORD = "Welcome123";
	private static final String USER_NAME = "kavya.revanna@thomsonreuters.com";
	private static final String PROFILE_NAME = "amneet singh";
	private static final String PASSWORD1 = "Welcome01";
	private static final String USER_NAME1 = "chinna.putha@thomsonreuters.com";

	static int status = 1;
	PageFactory pf=new PageFactory();
	
	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify that only the user who set the flag can see the comment has flagged")
				.assignCategory("Suite C");

	}

	@Test
	public void testFlagSetByOtherUsers() throws Exception {
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
			//ob.get(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			login();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 40);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("Synergistic");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 40);
			String articleTitle = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			String comment = "testFlag";
			pf.getAuthoringInstance(ob).enterArticleComment(comment);
			pf.getAuthoringInstance(ob).clickAddCommentButton();
			pf.getLoginTRInstance(ob).logOutApp();
			ob.navigate().to(host);
			//ob.get(CONFIG.getProperty("testSiteName"));
			loginAsOther(USER_NAME, PASSWORD);
			BrowserWaits.waitTime(15);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 60);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(articleTitle);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			ob.findElement(By.linkText(articleTitle)).click();
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 40);

			List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			System.out.println(commentsList.size());
			String commentText;
			WebElement flagWe;
			for (int i = 0; i < commentsList.size(); i++) {
				commentText = commentsList.get(i).getText();
				if (!commentText.contains("Kavya Revanna") && !commentText.contains("Comment deleted")) {
					flagWe = commentsList.get(i)
							.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")));
					if (flagWe.getAttribute("class").contains("flag-inactive")) {
						scrollElementIntoView(ob, flagWe);
						jsClick(ob,flagWe);
						break;
					}
				}

			}
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")),180);
			jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));
			jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))));

			pf.getLoginTRInstance(ob).logOutApp();
			//ob.navigate().to(host);
			ob.get(CONFIG.getProperty("testSiteName"));
			loginAsOther(USER_NAME1, PASSWORD1);
			BrowserWaits.waitTime(15);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 80);
			searchAnArticle(articleTitle);
			BrowserWaits.waitTime(10);
			waitForElementTobeVisible(ob, By.cssSelector("h2 span[class='stat-count ng-binding']"), 180);
			String commentCount=ob.findElement(By.cssSelector("h2 span[class='stat-count ng-binding']")).getText();
			
			if(commentCount.equals("0")){
				searchAnArticle(articleTitle);
			}
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 180);
			
			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			boolean flag=false;
			for (WebElement we : commentsList) {
				commentText = we.getText();
				if (commentText.contains(PROFILE_NAME) && commentText.contains(comment)) {

					try {

						if (we.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
								.getAttribute("class").contains("flag-inactive")) {
							test.log(LogStatus.PASS, "Flag set by other user is not visible to the current user");
							flag=true;
						} else {
							throw new Exception("Flag Validation Failed");
						}

					} catch (Throwable t) {
						test.log(LogStatus.FAIL, "Flag set by other user is visible to the current user");
						test.log(LogStatus.INFO, "Error--->" + t);
						ErrorUtil.addVerificationFailure(t);
						status = 2;
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
								this.getClass().getSimpleName() + "Flag_validation_for_comments_failed")));// screenshot

					}
					if(!flag){
						test.log(LogStatus.FAIL, "Commment is available");
						test.log(LogStatus.INFO, "Error--->");
						status = 2;
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
								this.getClass().getSimpleName() + "Flag_validation_for_comments_failed")));// screenshot
					}
					break;
				}
			}
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
		}

		pf.getLoginTRInstance(ob).logOutApp();
		closeBrowser();
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	private void searchAnArticle(String articleTitle) throws InterruptedException, Exception {
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(articleTitle);
		ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
		int count=0;
		boolean found=false;
		while(count<10){
		
			try{
				ob.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				ob.findElement(By.linkText(articleTitle)).click();
				found=true;
				break;
				
			}catch(Exception e){
				
				count++;
			}
		
		((JavascriptExecutor)ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
		waitForAjax(ob);
		}
		if(!found)throw new Exception("Could not fiind the specified article:"+articleTitle);
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*if (status == 1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "SKIP");*/

	}

	private void loginAsOther(String username, String pwd) throws Exception {
		jsClick(ob,ob.findElement(By.xpath(OR.getProperty("TR_login_button"))));
		waitForElementTobeVisible(ob, By.id(OR.getProperty("TR_email_textBox")), 30);
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(username);
		ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(pwd);
		jsClick(ob,ob.findElement(By.id(OR.getProperty("login_button"))));

	}

}
