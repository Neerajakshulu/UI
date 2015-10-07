package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;


public class WatchlistArticleDocInfoTest extends TestBase{
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
						"To verify that the following fields are getting displayed for each document in watchlist page: a)Times cited b)Comments c)Views ")
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
		String docsInfo[]={"Times Cited","Comments","Views"};
			
			
		openBrowser();
		maximizeWindow();
		clearCookies();
		
		
//		ob.navigate().to(CONFIG.getProperty("testSiteName"));
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
		
		ob.findElement(By.xpath("//i[@class='webui-icon webui-icon-watch cursor-pointer watch-icon-inactive']")).click();
		String document_name=ob.findElement(By.xpath("//a[@class='searchTitle ng-binding']")).getText();
//		System.out.println(document_name);
		
		ob.findElement(By.xpath("//span[contains(text(),'Watchlist')]")).click();
		Thread.sleep(8000);
		
		List<WebElement> watchlist=ob.findElements(By.xpath("//a[@class='searchTitle ng-binding']"));
//		System.out.println(watchlist.size());
		
		int count = 0;
		for(int i=0;i<watchlist.size();i++){
			
			if(watchlist.get(i).getText().equals(document_name))
				count++;
			
//			System.out.println(watchlist.get(i).getText());
		}
		
		if(!compareNumbers(1,count)){
			
			test.log(LogStatus.FAIL, "User not able to add document into watchlist from search results page");//extent reports
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_user_unable_to_add_document_into_watchlist_from_searchResults_page")));//screenshot
			
		}
		
		
		List<String> docInfoInput=Arrays.asList(docsInfo);
		List<String> watchLabels=new ArrayList<String>();
		//verify watchlist doc info
		List<WebElement> watchlistDocInfo=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_DOCINFO_CSS);
		for(WebElement watchlistDoc:watchlistDocInfo){
			watchLabels.add(watchlistDoc.findElement(By.tagName("e")).getText());
		}
		
		System.out.println("docInfoInput labels-->"+docInfoInput);
		System.out.println("watch list labels-->"+watchLabels);
		
		Assert.assertEquals(docInfoInput, watchLabels);
		
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
					this.getClass().getSimpleName() + "watchlist docinfo...Timescited,comments,views not present")));// screenshot
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
