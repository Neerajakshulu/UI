package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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


public class WatchlistDeleteArticleTest extends TestBase{
	static int status=1;
	static String watchListCountBeforeDelete;
	static int totalWatchCountBeforeDelete;
	static String watchListCountAfterDelete;
	static int totalWatchCountAfterDelete;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		test = extent
				.startTest(this.getClass().getSimpleName(),
						"To verify that document count gets decreased in the watchlist page when a document is deleted from watchlist")
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
		
		//Type into the search box and get search results
		ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
		ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
		Thread.sleep(4000);
		
		
		List<WebElement> watchLists=ob.findElements(By.xpath("//i[@class='webui-icon webui-icon-watch cursor-pointer watch-icon-inactive']"));
		
		//Add 4 articles into my watchlist
		for(int i=0;i<4;i++) {
			watchLists.get(i).click();
			Thread.sleep(2000);
		}
		
		//goto watchlist page
		ob.findElement(By.xpath("//span[contains(text(),'Watchlist')]")).click();
		Thread.sleep(8000);
		
		//get the count of the total no.of articles present in watchlist page
		watchListCountBeforeDelete=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_ARTICLE_COUNT_CSS).getText();
		totalWatchCountBeforeDelete=Integer.parseInt(watchListCountBeforeDelete);
		System.out.println("watchlist articles total count-->"+totalWatchCountBeforeDelete);
		
		//delete one article from watchlist
		List<WebElement> mylist=ob.findElements(By.xpath("//a[@class='searchTitle ng-binding']"));
		System.out.println("watch list article count-->"+mylist.size());
		if(mylist.size()>0) {
			ob.findElement(By.xpath("//i[@class='webui-icon webui-icon-watch watch-icon-active cursor-pointer']")).click();
			Thread.sleep(2000);
			ob.findElement(By.xpath("//button[contains(text(),'Remove')]")).click();
			Thread.sleep(2000);
		}
		
		//get the count after article delete from watchlist
		watchListCountAfterDelete=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_ARTICLE_COUNT_CSS).getText();
		totalWatchCountAfterDelete=Integer.parseInt(watchListCountAfterDelete);
		System.out.println("watchlist articles total count After-->"+totalWatchCountAfterDelete);
		
		if(!(totalWatchCountBeforeDelete>totalWatchCountAfterDelete)){
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "   article deletion not happening from watchlist screen ")));
			throw new Exception("article deletion not happening from watchlist screen");
		}
		
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
					this.getClass().getSimpleName() + "   something unexpected Error")));// screenshot
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
