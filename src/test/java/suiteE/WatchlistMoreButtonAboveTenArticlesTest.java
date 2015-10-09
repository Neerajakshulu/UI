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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.BrowserAction;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;


public class WatchlistMoreButtonAboveTenArticlesTest extends TestBase{
	static int status=1;
	static String watchListHeaderCount;
	static int totalwatchListHeaderCount;
	
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		test = extent
				.startTest(this.getClass().getSimpleName(),
						"To verify that MORE button is present in watchlist page if total search results is more than 10, and To verify that MORE button is working correctly in watchlist page ")
				.assignCategory("Suite E");
	}
	
	@Test
	@Parameters({"userName","password"})
	public void unWatchArticleSearchScreen(String userName,String password) throws Exception{
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "E Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteExls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition){
			
			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		try{
			
		String search_query="finger prints";
			
			
		openBrowser();
		maximizeWindow();
		clearCookies();
		
		
		ob.navigate().to(host);
		Thread.sleep(8000);
		
		//login using TR credentials
		LoginTR.enterTRCredentials(userName, password);
		LoginTR.clickLogin();
		
		Thread.sleep(15000);
		
		cleanWatchlist();
		
		test.log(LogStatus.INFO," Search for an article with "+search_query);
		//Type into the search box and get search results
		ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
		ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
		Thread.sleep(4000);
		
		boolean searchMoreButton=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_SEARCH_MORE_BUTTON_CSS).isDisplayed();
		System.out.println("10 More button status-->"+searchMoreButton);
		if(searchMoreButton){
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_SEARCH_MORE_BUTTON_CSS);
			Thread.sleep(2000);
		}
		
		List<WebElement> watchLists=ob.findElements(By.xpath("//i[@class='webui-icon webui-icon-watch cursor-pointer watch-icon-inactive']"));
		System.out.println("total article search count-->"+watchLists.size());
		//Add 14 articles into my watchlist
		test.log(LogStatus.INFO," Add 14 articles into my watchlist");
		for(int i=0;i<14;i++) {
			watchLists.get(i).click();
			Thread.sleep(2000);
		}
		
		//goto watchlist page
		ob.findElement(By.xpath("//span[contains(text(),'Watchlist')]")).click();
		Thread.sleep(8000);
		
		//validate MORE button getting displayed if watchlist article count above 10
		test.log(LogStatus.INFO," validate MORE button getting displayed if watchlist article count above 10");
		List<WebElement> watchListMore=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_MORE_BUTTON_XPATH);
		System.out.println("More buttons size-->"+watchListMore.size());
		
		boolean moreButtonStatus=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_MORE_BUTTON_XPATH).isDisplayed();
		System.out.println("more buttton should be in hidden mode-->"+moreButtonStatus);
		
		if(!moreButtonStatus) {
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "MORE button should display if watchlist article count >10")));
			throw new Exception("MORE button should  display if watchlist article count >10");
		}
		
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_MORE_BUTTON_XPATH);
		Thread.sleep(3000);
		
		
		List<WebElement> totArticle=ob.findElements(By.xpath("//a[@class='searchTitle ng-binding']"));
		watchListHeaderCount=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_ARTICLE_COUNT_CSS).getText();
		totalwatchListHeaderCount=Integer.parseInt(watchListHeaderCount);
		
		System.out.println("watchlist header count-->"+totalwatchListHeaderCount);
		System.out.println("watchlist article count-->"+totArticle.size());
		
		test.log(LogStatus.INFO," watchlist header count and watchlist whole article count should match");
		Assert.assertEquals(totArticle.size(), totalwatchListHeaderCount);
		
		//again clearing the watchlist due to firest cleanwatchlist method not clearing all watchlist articles due to app issue,
		cleanWatchlist();
		
		LoginTR.logOutApp();
		closeBrowser();
		
		}
		catch(Throwable t){
			test.log(LogStatus.FAIL,"Something unexpected happened");//extent reports
			//next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());//extent reports
			ErrorUtil.addVerificationFailure(t);//testng
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution ends--->");
	}
	

	@AfterTest
	public void reportTestResult(){
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteExls, "Test Cases", TestUtil.getRowNum(suiteExls,this.getClass().getSimpleName()), "SKIP");
	
	}
	

		

}
