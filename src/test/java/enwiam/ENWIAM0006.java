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

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

//OPQA-2352= Verify that "Already have an account" overlay to link with Facebook should display,
//if there is an existing neon identity with the same email and social account, while sign into LinkedIn on Neon as first user.

//OPQA-2354= "Verify that,user sign into Neon after clicking ""not now"" button on ""Already have an account"" overlay and 
//afterwards verify that link is not made and system remembers not to ask the user again to link,while signing into LinkedIn on 
//Neon as first user.(To link with Facebook while sign into Linkedin)".

//OPQA-2353 = Verify that user should go back to Neon login page after clicking [X] close button(at top right corner)  on 
//"Already have an account" overlay to link with Facebook, while signing into LinkedIn on Neon as first user.

//OPQA-2335= Verify that "Already have an account" overlay should display to link with LinkedIn,if there is an existing neon 
//identity with the same email and social account,while signing into Facebook on Neon as first user.

//OPQA-2339= Verify that  user can sign into Neon after clicking "not now" button on "Already have an account" overlay 
//and afterwards verify that link is not made and system remembers not to ask the user again to link while signing into FaceBook.

//OPQA-2337= Verify that user should go back to Neon login page after clicking [X] close button(at top right corner)  on 
//"Already have an account" overlay to link with LinkedIn,after signing into Facebook on Neon as first user.

public class ENWIAM0006 extends TestBase {
	
	// Following is the list of status:
		// 1--->PASS
		// 2--->FAIL
		// 3--->SKIP
		// Checking whether this test case should be skipped or not
		static boolean fail = false;
		static boolean skip = false;
		static int status = 1;

		static int time = 30;
		PageFactory pf=new PageFactory();
		
		@BeforeTest
		public void beforeTest() throws Exception {
			extent = ExtentManager.getReporter(filePath);
			rowData = testcase.get(this.getClass().getSimpleName());
			test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");

		}
		
		
		@Test
		public void testLogin() throws Exception {
			
			boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
			boolean master_condition = suiteRunmode && testRunmode;

			if (!master_condition) {

				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

			}

			
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
			try {
				String statuCode = deleteUserAccounts(LOGIN.getProperty("UserName18"));
				Assert.assertTrue(statuCode.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"));
							
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
				ErrorUtil.addVerificationFailure(t);
			}
			
			try {
				
				openBrowser();
				maximizeWindow();
				clearCookies();
				loginTofb();
				loginToLinkedIn();
				loginToFacebook();
			    closeBrowser();
				pf.clearAllPageObjects();
				
			} 
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "Something unexpected happened");// extent
				// reports
				// next 3 lines to print whole testng error in report
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
				closeBrowser();
			}
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
		}
		
		private void loginTofb() throws Exception {
			

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);	
		//	pf.getEnwReferenceInstance(ob).loginWithFBCredentialsENW(ob,"arvindkandaswamy@gmail.com","darshiniyogi@123");
			
			pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("UserName18"),LOGIN.getProperty("Password18"));
			pf.getLoginTRInstance(ob).logOutApp();
			}
		
           private void loginToLinkedIn() throws Exception {
			
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.LOGIN_PAGE_LI_SIGN_IN_BUTTON_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_LI_SIGN_IN_BUTTON_CSS.toString())).click();
			pf.getLoginTRInstance(ob).signInToLinkedIn(LOGIN.getProperty("UserName18"),LOGIN.getProperty("Password18"));
			ob.findElement(By.cssSelector("i[class='fa fa-close']")).click();
			Thread.sleep(3000);
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.LOGIN_PAGE_LI_SIGN_IN_BUTTON_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_LI_SIGN_IN_BUTTON_CSS.toString())).click();
			pf.getLoginTRInstance(ob).signInToLinkedIn(LOGIN.getProperty("UserName18"),LOGIN.getProperty("Password18"));
			ob.findElement(By.xpath("//a[contains(text(),'Not now')]")).click();
			Thread.sleep(3000);
			pf.getLoginTRInstance(ob).closeOnBoardingModal();
			Thread.sleep(3000);
			pf.getLoginTRInstance(ob).checkLinking();
			validateLinkedAccounts(1, "LinkedIn");
			pf.getLoginTRInstance(ob).logOutApp();
			}

     private void loginToFacebook() throws Exception {
	
	waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS.toString()), 30);
	ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS.toString())).click();
	ob.findElement(By.xpath("//a[contains(text(),'Not now')]")).click();
	Thread.sleep(3000);
	pf.getLoginTRInstance(ob).checkLinking();
	validateLinkedAccounts(1, "Facebook");
	pf.getLoginTRInstance(ob).logOutApp();
	}

     
  // Validating the linked accounts with STeAM

 	private void validateLinkedAccounts(int accountCount,
 			String linkName) throws Exception {
 		try {

 			Assert.assertTrue(
 					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("UserName18")));
 			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
 			System.out.println(accountCount);
 			test.log(LogStatus.PASS,
 					 linkName + "Account is not linked after clicking 'Not now'");

 		} catch (Throwable t) {
 			test.log(LogStatus.FAIL,
 					linkName +" Account is not linked after clicking 'Not now'");
 			ErrorUtil.addVerificationFailure(t);// testng
 			test.log(LogStatus.INFO, "Snapshot below: "
 					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "Linking_failed")));// screenshot
 		}
 	}
		
		
			@AfterTest
			public void reportTestResult() {
				extent.endTest(test);

				
			}

}



