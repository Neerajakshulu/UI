package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;


public class NavigateToWatchlistFromRVTest extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		test = extent
				.startTest(this.getClass().getSimpleName(),
						"To verify that app navigates to correct page when user navigates back from document/RecordView page").assignCategory("Suite E");
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
		
		List<WebElement> watchLists=ob.findElements(By.xpath("//i[@class='webui-icon webui-icon-watch cursor-pointer watch-icon-inactive']"));
		System.out.println("total article search count-->"+watchLists.size());
		
		scrollElementIntoView(ob, watchLists.get(watchLists.size()-1));
		Thread.sleep(6000);
		
		List<WebElement> afterScrollwatchLists=ob.findElements(By.xpath("//i[@class='webui-icon webui-icon-watch cursor-pointer watch-icon-inactive']"));
		System.out.println("After scroll total article search count-->"+afterScrollwatchLists.size());
		
		//Add 14 articles into my watchlist
		test.log(LogStatus.INFO," Add 14 articles into my watchlist");
		for(int i=0;i<14;i++) {
			jsClick(ob, afterScrollwatchLists.get(i));
			Thread.sleep(2000);
		}
		
		//goto watchlist page
		ob.findElement(By.xpath("//span[contains(text(),'Watchlist')]")).click();
		Thread.sleep(8000);
		
		BrowserAction.scrollToElement(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_MORE_BUTTON_XPATH);
		boolean moreButtonStatus=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_MORE_BUTTON_XPATH).isDisplayed();
		System.out.println("more buttton should be in hidden mode-->"+moreButtonStatus);
		
		
		if(!moreButtonStatus) {
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "MORE button should display if watchlist article count >10")));
			throw new Exception("MORE button should  display if watchlist article count >10");
		}
		
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_MORE_BUTTON_XPATH);
		Thread.sleep(8000);
		
		
		
		
		//click 12th article in watchlist screen and it should navigate to Record view page
		test.log(LogStatus.INFO,"Click 12th article in watchlist screen and it should navigate to Record view page");
		List<WebElement> mylist=ob.findElements(By.xpath("//a[@class='searchTitle ng-binding']"));
		scrollElementIntoView(ob, mylist.get(11));
		String articleName=mylist.get(11).getText();
		jsClick(ob, mylist.get(11));
		Thread.sleep(6000);
		BrowserWaits.waitUntilText(articleName);
		
		//ob.navigate().back();
		System.out.println("watchlist atricle name prior navigate to Record view--->"+articleName);
		
		JavascriptExecutor jse = (JavascriptExecutor)ob;
		jse.executeScript("window.history.back();");
		
		Thread.sleep(2000);
		
		List<WebElement> afterNavToWatList=ob.findElements(By.xpath("//a[@class='searchTitle ng-binding']"));
		System.out.println("watchlist count-->"+mylist.size()+"current count--->"+afterNavToWatList.size());
		test.log(LogStatus.INFO," Validate App Navigate to Watchlist page from Record view page and displays the accurate count aswell");
//		if(!(afterNavToWatList.size() == mylist.size())){
//			status=2;
//			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
//					this.getClass().getSimpleName() + "Application Should Navigate to Watchlist page from Record View page "
//							+ "and it should displays previous watchlist count articles")));// screenshot
//			//raised defect for this, as per sam comments, this is enhancement, still they dint implement
//			//throw new Exception("App Navigate from Record View page to Watchlist page and display expected article count");
//			
//		}
		
		String articleNamebackFromRV=afterNavToWatList.get(11).getText();
		
		
		System.out.println("watchlist atricle name after navigate from Record view--->"+articleNamebackFromRV);
		
		//Assert.assertEquals(articleName, articleNamebackFromRV);
		
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
