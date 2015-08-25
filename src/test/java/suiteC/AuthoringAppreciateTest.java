package suiteC;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class AuthoringAppreciateTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	@BeforeTest
	public void beforeTest() {
		test = extent.startTest(this.getClass().getSimpleName(), "Validate Authoring Appreciation Functionality").assignCategory("Suite C");
		//test.log(LogStatus.INFO, "****************************");
		//load the runmodes of the tests			
		runmodes=TestUtil.getDataSetRunmodes(suiteCxls, this.getClass().getSimpleName());
		System.out.println("Run modes-->"+runmodes.length);
	}
	
	/**
	 * Method for validating TR Login Screen		
	 * @throws Exception, When TR Login Home screen not displaying
	 */
	@Test
	public void testAuthoringTestAccount() throws Exception  {
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteCxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition) {
			status=3;
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		
		
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")) {
			test.log(LogStatus.INFO, "Runmode for test set data set to no "+count);
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts for data set #"+ count+"--->");
		
				openBrowser();
				clearCookies();
				maximizeWindow();
				
				ob.navigate().to(System.getProperty("host"));
				ob.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				ob.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				AuthoringTest.waitForTRHomePage();
				//authoringAppreciation(username, password, article, completeArticle, addComments);
	}
	
	@Test(dependsOnMethods="testAuthoringTestAccount")
	@Parameters({"username","password","article","completeArticle"})
	public void authoringAppreciation(String username,String password,
			String article,String completeArticle) throws Exception  {

		try {
			AuthoringTest.enterTRCredentials(username, password);
			AuthoringTest.clickLogin();
			AuthoringTest.searchArticle(article);
			AuthoringTest.chooseArticle(completeArticle);
			validateAppreciationComment();
			test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
			closeBrowser();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL,"Error:"+t);//extent reports
			ErrorUtil.addVerificationFailure(t);//testng
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_profile_data_updation_not_done")));//screenshot
			closeBrowser();
		}
	}
	
	
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "SKIP");
		
		//closeBrowser();
		
	}
	
	/**
	 * Method for Validate the Article Appreciation functionality
	 * @throws Exception, When Validation not done
	 */
	public  void validateAppreciationComment() throws Exception  {
		List<WebElement> apprDivs=ob.findElements(By.cssSelector("div[class='col-xs-7 comment-content']"));
		List<WebElement> apprSubDivs=apprDivs.get(0).findElements(By.cssSelector("div.row")).get(0).findElements(By.cssSelector("div[class^='col-xs-']"));
		System.out.println("app sub divs-->"+apprSubDivs.size());
		scrollingToElementofAPage();
		int apprEarCount=Integer.parseInt(apprSubDivs.get(1).getText());
		System.out.println("Before count-->"+apprEarCount);
		
		String attrStatus=apprSubDivs.get(0).findElement(By.tagName("button")).getAttribute("ng-click");
		System.out.println("Attribute Status-->"+attrStatus);
		
		if(attrStatus.contains("NONE")) {
			scrollingToElementofAPage();
			apprSubDivs.get(0).findElement(By.tagName("button")).click();
			Thread.sleep(4000);
			int apprAftCount=Integer.parseInt(apprSubDivs.get(1).getText());
			System.out.println("Already liked  After count-->"+apprAftCount);
			   if(!(apprAftCount<apprEarCount)) {
				   //status=2;
				   test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Comment Appreciation not happended")));
				   throw new Exception("Comment Appreciation not happended");
			   }
		} 
		else if (attrStatus.contains("UP")) {
			scrollingToElementofAPage();
			apprSubDivs.get(0).findElement(By.tagName("button")).click();
			Thread.sleep(4000);
			int apprAftCount=Integer.parseInt(apprSubDivs.get(1).getText());
			System.out.println("Not liked --After count-->"+apprAftCount);
			   if(!(apprAftCount>apprEarCount)) {
				   status=2;
				   test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Comment Appreciation not happended")));
				   throw new Exception("Comment Appreciation not happended");
			   }
			}
	}
	
	/**
	 * Method for Scrolling down to the page
	 * @throws InterruptedException, When scroll not done
	 */
	public static void scrollingToElementofAPage() throws InterruptedException  {
		JavascriptExecutor jse = (JavascriptExecutor)ob;
		jse.executeScript("scroll(0, 250);");
		Thread.sleep(4000);
		
	}
	
	
}
