package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;
import base.TestBase;

public class ENWIAM100 extends TestBase {
	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	static int time = 30;
	PageFactory pf = new PageFactory();

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('G'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verfiy that user is able to login social accounts with neon and link to end note")
				.assignCategory("ENWIAM");

	}
	
	@Test
	public void testcaseG100() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "ENWIAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(iamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		try {
			String statuCode = deleteUserAccounts(CONFIG.getProperty("enwsoclogin"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			String statuCode2 = deleteUserAccounts(CONFIG.getProperty("myacc"));
			Assert.assertTrue(statuCode2.equalsIgnoreCase("200"));
			
			
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try{
			openBrowser();
			maximizeWindow();
			 ob.navigate().to(host);
			 pf.getLoginTRInstance(ob).loginWithFBCredentials(CONFIG.getProperty("enwsoclogin"),CONFIG.getProperty("enwsocpwd"));
			 test.log(LogStatus.PASS,"user has logged in with social account");
			 pf.getHFPageInstance(ob).clickOnEndNoteLink();
			 test.log(LogStatus.PASS,"User navigate to End note"); 
			 pf.getHFPageInstance(ob).linkToEndnote(CONFIG.getProperty("myacc"),CONFIG.getProperty("mypwd"));
			 test.log(LogStatus.PASS,"User linked with steam account");
		    //ob.findElement(By.cssSelector(OnePObjectMap.ENW_HOME_AGREE_CSS.toString())).click();
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString()),30);
				String text = ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).getText();
				if (text.equalsIgnoreCase("Continue")) {
				ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
						
		
				logoutEnw();
					closeBrowser();
					 pf.clearAllPageObjects();
			
					 openBrowser();
						maximizeWindow();
						 ob.navigate().to(host);
						 pf.getLoginTRInstance(ob).loginWithFBCredentials(CONFIG.getProperty("enwsoclogin"),CONFIG.getProperty("enwsocpwd"));
						 test.log(LogStatus.PASS,"user has logged in with social account");
						 pf.getLoginTRInstance(ob).checkLinking();
						 Assert.assertTrue(
									pf.getAccountPageInstance(ob).verifyLinkedAccount("FaceBook", CONFIG.getProperty("myacc")));
						 test.log(LogStatus.PASS,"Linked accounts are available in accounts page: Neon");
						 pf.getHFPageInstance(ob).clickOnEndNoteLink();
						 test.log(LogStatus.PASS,"User navigate to End note"); 
					 
						 waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString()),30);
						 text = ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).getText();
							if (text.equalsIgnoreCase("Continue")) {
							ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
									
					}
							logoutEnw();
							closeBrowser();
							 pf.clearAllPageObjects();
					 
		}
		}
		
		catch(Throwable t)
		{
			
			test.log(LogStatus.FAIL, "Facebook is not linked with ENW ");// extent
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
			captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
			
		}
		
		try {
			String statuCode = deleteUserAccounts(CONFIG.getProperty("enwsoclogin"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			String statuCode2 = deleteUserAccounts(CONFIG.getProperty("myacc"));
			Assert.assertTrue(statuCode2.equalsIgnoreCase("200"));
			
			
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		
	
	try{
		openBrowser();
		maximizeWindow();
		 ob.navigate().to(host);
		 pf.getLoginTRInstance(ob).loginWithLinkedInCredentials(CONFIG.getProperty("enwsoclogin"),CONFIG.getProperty("enwsocpwd"));
		 test.log(LogStatus.PASS,"user  logged in with Linkedin account");
		 pf.getHFPageInstance(ob).clickOnEndNoteLink();
		 test.log(LogStatus.PASS,"User navigate to End note"); 
		BrowserWaits.waitTime(5);
		 pf.getHFPageInstance(ob).linkToEndnote(CONFIG.getProperty("myacc"),CONFIG.getProperty("mypwd"));
		 test.log(LogStatus.PASS,"User linked with steam account");
		 BrowserWaits.waitTime(2);
		// ob.findElement(By.cssSelector(OnePObjectMap.ENW_HOME_AGREE_CSS.toString())).click();
		waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString()),
			30);
			String text = ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString()))
			.getText();
			if (text.equalsIgnoreCase("Continue")) {
			ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
			
	}
			logoutEnw();
			closeBrowser();
			 pf.clearAllPageObjects();
			 openBrowser();
				maximizeWindow();
				 ob.navigate().to(host);
		      pf.getLoginTRInstance(ob).loginWithLinkedInCredentials(CONFIG.getProperty("enwsoclogin"),CONFIG.getProperty("enwsocpwd"));
				 test.log(LogStatus.PASS,"user  logged in with Linkedin account");
				 pf.getLoginTRInstance(ob).checkLinking();
				 BrowserWaits.waitTime(5);
				 Assert.assertTrue(
							pf.getAccountPageInstance(ob).verifyLinkedAccount("Linkedin", CONFIG.getProperty("enwsoclogin")));
				 test.log(LogStatus.PASS,"Linked accounts are available in accounts page: Neon");
				 BrowserWaits.waitTime(5);
				 pf.getHFPageInstance(ob).clickOnEndNoteLink();
				 test.log(LogStatus.PASS,"User navigate to End note"); 
			 
				 waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString()),30);
				 text = ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).getText();
					if (text.equalsIgnoreCase("Continue")) {
					ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
							
			}
					logoutEnw();
					closeBrowser();
			 
			
	
	}
	
	catch(Throwable t)
	{
		
		test.log(LogStatus.FAIL, "Linkedin account is not linked with ENW ");// extent
		StringWriter errors = new StringWriter();
		t.printStackTrace(new PrintWriter(errors));
		test.log(LogStatus.INFO, errors.toString());// extent reports
		ErrorUtil.addVerificationFailure(t);// testng
		status = 2;// excel
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
		captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
		closeBrowser();
		
	}

}
	
	

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(authoringxls,
		 * "Test Cases", TestUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(authoringxls, "Test Cases",
		 * TestUtil.getRowNum(authoringxls, this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(authoringxls, "Test Cases"
		 * , TestUtil.getRowNum(authoringxls, this.getClass().getSimpleName()),
		 * "SKIP");
		 */

	}

}
 