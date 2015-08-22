package suiteC;

import java.util.concurrent.TimeUnit;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.TestUtil;

public class AuthoringTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	// Checking whether this test case should be skipped or not
		@BeforeTest
		public void beforeTest() {
			test = extent.startTest(this.getClass().getSimpleName(), "Validate Authoring Creating Comments").assignCategory("Suite C");
//			test.log(LogStatus.INFO, "****************************");
			//load the runmodes of the tests			
			runmodes=TestUtil.getDataSetRunmodes(suiteCxls, this.getClass().getSimpleName());
			System.out.println("Run modes-->"+runmodes.length);
		}
	
			
	@Test
	public void testLoginTRAccount() throws Exception  {
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteCxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition) {
			status=3;
//			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "SKIP");
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		
		
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")) {
			test.log(LogStatus.INFO, "Runmode for test set data set to no "+count);
			skip=true;
//			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "SKIP");
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts for data set #"+ count+"--->");
		//test.log(LogStatus.INFO,searchKey);
		
				//selenium code
				openBrowser();
				clearCookies();
				maximizeWindow();
				
				ob.get(CONFIG.getProperty("devStable_url"));
				ob.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				ob.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				//LoginTR.waitForTRHomePage();
	}
	
	@Test(dependsOnMethods="testLoginTRAccount",dataProvider="getTestData")
	public void performAuthoringCommentOperations(String username,
			String password,
			String article,
			String completeArticle, String addComments) throws Exception  {
		LoginTR.waitForTRHomePage();
		LoginTR.enterTRCredentials(username, password);
		LoginTR.clickLogin();
		LoginTR.searchArticle(article);
		LoginTR.chooseArticle(completeArticle);
		
		//Authoring.enterArticleComment(addComments);
		//Authoring.clickAddCommentButton();
		//Authoring.validateCommentAdd();
		//Authoring.validateViewComment(addComments);
		Authoring.updateComment();
		Authoring.validateUpdatedComment("comment updated");
	}
	
	
	
	//@Test(dependsOnMethods="performOperations")
	@AfterMethod
	public void reportDataSetResult() {
		if(skip)
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "SKIP");
		
		else if(fail) {
			
			status=2;
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "PASS");
		
		
		skip=false;
		fail=false;
		

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
		
		closeBrowser();
	}
	

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suiteCxls, this.getClass().getSimpleName()) ;
	}
}
