package base;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import util.ErrorUtil;
import util.Xls_Reader;


public class TestBase {
	public static Properties CONFIG=null;
	public static Properties OR=null;
	public static Xls_Reader suiteXls=null;
	public static Xls_Reader suiteAxls=null;
	public static Xls_Reader suiteBxls=null;
	public static Xls_Reader suiteCxls=null;
	public static Xls_Reader suiteDxls=null;
	public static Xls_Reader suiteExls=null;
	public static Xls_Reader suiteFxls=null;
	
	public static boolean isInitalized=false;
	public static WebDriver ob=null;
	public static ExtentReports extent=null;
	public static ExtentTest test=null;
	
	public static String host=null;
	public static String user1,user2;
	public static String fn1,fn2,ln1,ln2;
	
	@BeforeSuite
	public void beforeSuite() throws Exception{
		
		initialize();
	}
	
	@AfterSuite
	public void afterSuite(){
		
		extent.flush();
		
	}
	
	
	// initializing the Tests
	public void initialize() throws Exception{
		// logs
		if(!isInitalized){
		//extent-reports
		extent = getInstance();
		// config
		CONFIG = new Properties();
		//FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties");
		FileInputStream ip = new FileInputStream("src/test/resources/properties/config.properties");
		CONFIG.load(ip);
			
		OR = new Properties();
		ip = new FileInputStream("src/test/resources/properties/OR.properties");
		OR.load(ip);
		
		//Getting url
		host=System.getProperty("host");

//		System.out.println(host);
		// xls file
		suiteAxls = new Xls_Reader("src/test/resources/xls/A suite.xlsx");
		suiteBxls = new Xls_Reader("src/test/resources/xls/B suite.xlsx");
		suiteCxls = new Xls_Reader("src/test/resources/xls/C suite.xlsx");
		suiteDxls = new Xls_Reader("src/test/resources/xls/D suite.xlsx");
		suiteExls = new Xls_Reader("src/test/resources/xls/E suite.xlsx");
		suiteFxls = new Xls_Reader("src/test/resources/xls/F suite.xlsx");
		suiteXls = new Xls_Reader("src/test/resources/xls/Suite.xlsx");
		isInitalized=true;
		}
		
	
		
	}
	
	public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports("testReports/test_report.html", true);
            
            // optional
            extent.config()
                .documentTitle("Automation Report")
                .reportName("Regression")
                .reportHeadline("1-P PLATFORM");
               
            // optional
            extent
                .addSystemInfo("Selenium Version", "2.43")
                .addSystemInfo("Environment", "stage");
        }
        return extent;
    }

	
	
	//Opening via Sauce Labs
//	public void openBrowser() throws Exception{
//		
//		
//		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//		desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
//		System.out.println("Selenium Browser Name-->"+System.getenv("SELENIUM_BROWSER"));
//		desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));
//		System.out.println("Selenium Version-->"+System.getenv("SELENIUM_VERSION"));
//		System.out.println("Selenium Plaform-->"+System.getenv("SELENIUM_PLATFORM"));
//		desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
//		desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
////		desiredCapabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS,true);
//		ob = new RemoteWebDriver(new URL("http://amneetsingh:f48a9e78-a431-4779-9592-1b49b6d406a4@ondemand.saucelabs.com:80/wd/hub"),
//                desiredCapabilities);
//		String waitTime=CONFIG.getProperty("defaultImplicitWait");
//		String pageWait=CONFIG.getProperty("defaultPageWait");
//		ob.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
//		try{
//			ob.manage().timeouts().implicitlyWait(Long.parseLong(pageWait), TimeUnit.SECONDS);
//			}
//		catch(Throwable t){
//				
//			System.out.println("Page Load Timeout not supported in safari driver");
//		}
//		
//	}
	

	
	
	 
	
	// selenium RC/ Webdriver
	
//	Opening the desired browser
	public void openBrowser(){
		
		if(CONFIG.getProperty("browserType").equals("FF")){
		     ob = new FirefoxDriver();
		}
		else if (CONFIG.getProperty("browserType").equals("IE")){
//			 System.setProperty("webdriver.ie.driver", "C:\\Users\\UC201214\\Desktop\\IEDriverServer.exe");
			 DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			 capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			 System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
			 ob = new InternetExplorerDriver(capabilities);
		}
		else if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")){
//			 System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\compatibility issues\\chromedriver.exe");
			 System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			 ob = new ChromeDriver();
		}
		
		else if (CONFIG.getProperty("browserType").equalsIgnoreCase("Safari")){
			
			DesiredCapabilities desiredCapabilities = DesiredCapabilities.safari();
			SafariOptions safariOptions = new SafariOptions();
			safariOptions.setUseCleanSession(true);
			desiredCapabilities.setCapability(SafariOptions.CAPABILITY, safariOptions);
			ob = new SafariDriver(desiredCapabilities);
		}
	
		
		
		String waitTime=CONFIG.getProperty("defaultImplicitWait");
		String pageWait=CONFIG.getProperty("defaultPageWait");
		ob.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
		try{
		ob.manage().timeouts().pageLoadTimeout(Long.parseLong(pageWait), TimeUnit.SECONDS);
		}
		catch(Throwable t){
			
			System.out.println("Page Load Timeout not supported in safari driver");
		}
		
	}
	
	//Closing the browser
	public void closeBrowser(){
		
		ob.quit();
	}
	
	// compare titles
		public boolean compareTitle(String expectedVal){
			try{
				Assert.assertEquals(ob.getTitle() , expectedVal  );
				test.log(LogStatus.PASS, "Correct page title getting displayed");
				
				}catch(Throwable t){
					test.log(LogStatus.INFO, "Error--->"+t);
					ErrorUtil.addVerificationFailure(t);
					return false;
				}
			return true;
		}
		
		// compareStrings
		 public boolean compareStrings(String expectedString, String actualString){
				try{
					Assert.assertEquals(actualString,expectedString   );
					test.log(LogStatus.PASS, "Strings matching");
					}catch(Throwable t){
						test.log(LogStatus.INFO, "Error--->"+t);
						ErrorUtil.addVerificationFailure(t);
						return false;
					}
				return true;
			}		
		 
		// compareStrings by ignoring case
				 public boolean compareStringsIgnoringCase(String expectedString, String actualString){
					 
						try{
							Assert.assertEquals(actualString.toLowerCase(),expectedString.toLowerCase()   );
							test.log(LogStatus.PASS, "Strings matching");
							}catch(Throwable t){
								test.log(LogStatus.INFO, "Error--->"+t);
								ErrorUtil.addVerificationFailure(t);
								return false;
							}
						return true;
					}	

		
		// compare numbers
	       public boolean compareNumbers(int expectedVal, int actualValue){
				try{
					Assert.assertEquals(actualValue,expectedVal   );
					test.log(LogStatus.PASS, "Numbers are matching");
					}catch(Throwable t){
						test.log(LogStatus.INFO, "Error--->"+t);
						ErrorUtil.addVerificationFailure(t);	
						return false;
					}
				return true;
			}		
	    //Check whether a particular element is present or not(detecting element via xpath)
	       public boolean checkElementPresence(String xpathkey){
				int count =ob.findElements(By.xpath(OR.getProperty(xpathkey))).size();
				System.out.println("Count is "+count);
				try{
				Assert.assertEquals(count, 1);
				test.log(LogStatus.PASS, "Particular element is present");
				}catch(Throwable t){
					test.log(LogStatus.INFO, "Error--->"+t);
					ErrorUtil.addVerificationFailure(t);
					System.out.println("Error:"+t);
					return false;
			    }
				return true;
			}
	     
	       
	       //Check whether a particular element is present or not(detecting element via link text)
	       public boolean checkElementPresence_link_text(String linkKey){
				int count =ob.findElements(By.linkText(OR.getProperty(linkKey))).size();
				System.out.println("Count is "+count);
				try{
				Assert.assertEquals(count, 1);
				test.log(LogStatus.PASS, "Particular element is present");
				}catch(Throwable t){
					test.log(LogStatus.INFO, "Error--->"+t);
					ErrorUtil.addVerificationFailure(t);
					System.out.println("Error:"+t);
					return false;
			    }
				return true;
			}
	       
	     //Check whether a particular element is present or not(detecting element via id)
	       public boolean checkElementPresence_id(String id){
				int count =ob.findElements(By.id(OR.getProperty(id))).size();
				System.out.println("Count is "+count);
				try{
				Assert.assertEquals(count, 1);
				test.log(LogStatus.PASS, "Particular element is present");
				}catch(Throwable t){
					test.log(LogStatus.INFO, "Error--->"+t);
					ErrorUtil.addVerificationFailure(t);
					System.out.println("Error:"+t);
					return false;
			    }
				return true;
			}
	       
	     //Check whether a particular element is present or not(detecting element via name)
	       public boolean checkElementPresence_name(String name_key){
				int count =ob.findElements(By.name(OR.getProperty(name_key))).size();
				System.out.println("Count is "+count);
				try{
				Assert.assertEquals(count, 1);
				test.log(LogStatus.PASS, "Particular element is present");
				}catch(Throwable t){
					test.log(LogStatus.INFO, "Error--->"+t);
					ErrorUtil.addVerificationFailure(t);
					System.out.println("Error:"+t);
					return false;
			    }
				return true;
			}
	       
	       
	     //Check whether a particular element has disappeared or not(via xpath)
	       public boolean checkElementDisappearance(String xpathKey){
	    	   
	    	   int count=ob.findElements(By.xpath(OR.getProperty(xpathKey))).size();
	    	   System.out.println("Count is"+count);
	    	   try{
	    		   
	    		   Assert.assertEquals(count, 0);
	    		   test.log(LogStatus.PASS, "Particular element is not present....So,everything is working fine");
	    	   }
	    	   catch(Throwable t){
	    		    test.log(LogStatus.INFO, "Error--->"+t);
	    		    ErrorUtil.addVerificationFailure(t);
					System.out.println("Error:"+t);
					return false;
	    	   }
	    	   
	    	   return true;
	       }
	       
	       //maximizing window
	       public void maximizeWindow(){
	    	   ob.manage().window().maximize();
	       }
	       
	       //Clearing all cookies
	       public void clearCookies(){
	    	   
	    	   ob.manage().deleteAllCookies();
	       }
	       
	       //logging in
	       public void login() throws Exception{
				
	    	   	ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
				Thread.sleep(4000);
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(CONFIG.getProperty("defaultUsername"));
				ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(CONFIG.getProperty("defaultPassword"));
				ob.findElement(By.id(OR.getProperty("login_button"))).click();
	    	 
			}
			
	       //logging out
			public void logout() throws Exception{
				
				ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
				Thread.sleep(2000);
				ob.findElement(By.xpath(OR.getProperty("signOut_link"))).click();
			}	 
			
			//capturing screenshot
			public String captureScreenshot(String filename) throws Exception{
				File myImg = ((TakesScreenshot)ob).getScreenshotAs(OutputType.FILE);
//				Thread.sleep(5000);
				String myP=System.getProperty("user.dir")+"/screenshots/"+filename+".jpg";
			    FileUtils.copyFile(myImg, new File(myP));
//			    Thread.sleep(5000);
			    return myP;

			}
			
			//Cleaning up watchlist
			public void cleanWatchlist() throws Exception{
				
				ob.findElement(By.xpath(OR.getProperty("watchlist_link"))).click();
				Thread.sleep(4000);
				
				List<WebElement> mylist=ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
				for(int i=0;i<mylist.size();i++){
					
					ob.findElement(By.xpath(OR.getProperty("watchlist_watchlist_image"))).click();
					Thread.sleep(2000);
					ob.findElement(By.xpath(OR.getProperty("watchlist_remove_button"))).click();
					Thread.sleep(2000);
					
				}
				
			}
			
			
			//Creates a new TR user
			public String createNewUser(String first_name,String last_name) throws Exception{
				
				String password="Transaction@2";
				
				
				ob.get("https://www.guerrillamail.com");
				String email=ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();
//				ob.navigate().to(CONFIG.getProperty("testSiteName"));
				ob.navigate().to(host);
				Thread.sleep(8000);
				ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
				Thread.sleep(4000);
				
				
				ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).click();
				Thread.sleep(2000);
				ob.findElement(By.id(OR.getProperty("reg_email_textBox"))).sendKeys(email);
				ob.findElement(By.id(OR.getProperty("reg_firstName_textBox"))).sendKeys(first_name);
				ob.findElement(By.id(OR.getProperty("reg_lastName_textBox"))).sendKeys(last_name);
				ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).sendKeys(password);
				ob.findElement(By.id(OR.getProperty("reg_confirmPassword_textBox"))).sendKeys(password);
				ob.findElement(By.id(OR.getProperty("reg_terms_checkBox"))).click();
				ob.findElement(By.xpath(OR.getProperty("reg_register_button"))).click();
				Thread.sleep(10000);
				
				
				ob.get("https://www.guerrillamail.com");
				List<WebElement> email_list=ob.findElements(By.xpath(OR.getProperty("email_list")));
				WebElement myE=email_list.get(0);
				JavascriptExecutor executor = (JavascriptExecutor)ob;
				executor.executeScript("arguments[0].click();", myE);
//				email_list.get(0).click();
				Thread.sleep(8000);
				
				
				WebElement email_body=ob.findElement(By.xpath(OR.getProperty("email_body")));
				List<WebElement> links=email_body.findElements(By.tagName("a"));
				
				
				ob.get(links.get(0).getAttribute("href"));
				Thread.sleep(8000);
				
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(email);
				ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(password);
				ob.findElement(By.id(OR.getProperty("login_button"))).click();
				Thread.sleep(25000);
				
				
				return email;
				
			}

			
			
			
			// verifies whether a particular string contains another string or not
			 public boolean StringContains(String MainString, String ToBeCheckedString){
					try{
						Assert.assertTrue(MainString.contains(ToBeCheckedString),"MainString doesn't contain ToBeCheckedString");
						test.log(LogStatus.PASS, "MainString doesn't contain ToBeCheckedString");
						}catch(Throwable t){
							test.log(LogStatus.INFO, "Error--->"+t);
							ErrorUtil.addVerificationFailure(t);			
							return false;
						}
					return true;
				}	
			 
			 //This method has been specially designed for REPORTS module to reach upto dropdown irrespective of the instance
			 public void ReportSteps(String URL) throws Exception{
					
					
					switch(URL){
					
					//ACTIMIZE
					case "https://actimize-stage.pramata.com":
						System.out.println("We have arrived directly to the drop down...So,nothing to do....");
						break;
						
					//CALLAWAYGOLF
					case "https://callawaygolf-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
					
					//CENTURYLINK
					case "https://centurylink-stage.pramata.com":
						ob.findElement(By.xpath("//input[@value='document']")).click();
						Thread.sleep(1000);
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//CISCO
					case "https://cisco-stage.pramata.com":
						ob.findElement(By.xpath("//input[@value='document']")).click();
						Thread.sleep(1000);
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
					
					//CTL
					case "https://ctl-stage.pramata.com":
//						ob.findElement(By.linkText("Select All")).click();
//						Thread.sleep(2000);
//						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
//						Thread.sleep(3000);
						break;
						
					//EDIFECS
					case "https://edifecs-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//EXPRESSSCRIPTS
					case "https://expressscripts-stage.pramata.com":
						String xp1="//html/body/div[3]/div/div/div/div[2]/div/div/div/form/input[";
						String xp2="]";
						String xp3;
						
						for(int i=1;i<=3;i++){
							
							xp3=xp1+i+xp2;
							
							if(!ob.findElement(By.xpath(xp3)).isSelected())
								ob.findElement(By.xpath(xp3)).click();
							
						}
						
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//FICO
					case "https://fico-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//INTELLIGRATED
					case "https://intelligrated-stage.pramata.com":
						System.out.println("We have arrived directly to the drop down...So,nothing to do....");
						break;
						
					//LOCKTON
					case "https://lockton-stage.pramata.com":
						String xp4="//html/body/div[3]/div/div/div/div[2]/div/div/div/form/input[";
						String xp5="]";
						String xp6;
						
						for(int i=1;i<=2;i++){
							
							xp6=xp4+i+xp5;
							
							if(!ob.findElement(By.xpath(xp6)).isSelected())
								ob.findElement(By.xpath(xp6)).click();
							
						}
						
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//NOVELIS
					case "https://novelis-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//NYBC
					case "https://nybc-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//PTC
					case "https://ptc-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//SEPRACOR
					case "https://sepracor-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//TRUVENHEALTH
					case "https://truvenhealth-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//VALSPAR-STAGE
					case "https://valspar-stage.pramata.com":
						String xp7="//html/body/div[3]/div/div/div/div[2]/div/div/div/form/input[";
						String xp8="]";
						String xp9;
						
						for(int i=1;i<=2;i++){
							
							xp9=xp7+i+xp8;
							
							if(!ob.findElement(By.xpath(xp9)).isSelected())
								ob.findElement(By.xpath(xp9)).click();
							
						}
						
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//WPAHS
					case "https://wpahs-stage.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//SANDBOX
					case "https://sfdc-sandbox.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//VALSPAR-TEST
					case "https://valspar-test.pramata.com":
						String xp77="//html/body/div[3]/div/div/div/div[2]/div/div/div/form/input[";
						String xp88="]";
						String xp99;
						
						for(int i=1;i<=2;i++){
							
							xp99=xp77+i+xp88;
							
							if(!ob.findElement(By.xpath(xp99)).isSelected())
								ob.findElement(By.xpath(xp99)).click();
							
						}
						
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//LOAD-TEST
					case "https://load-test.pramata.com":
						ob.findElement(By.xpath("//input[@value='document']")).click();
						Thread.sleep(1000);
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//LINODE-TEST
					case "https://linode-test.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//DIGITALOCEAN-TEST
					case "https://digitalocean-test.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//CISCO-PRODUCTION
					case "https://cisco.pramata.com":
						ob.findElement(By.xpath("//input[@value='document']")).click();
						Thread.sleep(1000);
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//CTL-PRODUCTION
					case "https://ctl.pramata.com":
						break;
						
					//MEDTRONIC-PRODUCTION
					case "https://medtronic.pramata.com":
						break;
						
					//LOCKTON
					case "https://lockton-test.pramata.com":
						String xp101="//html/body/div[3]/div/div/div/div[2]/div/div/div/form/input[";
						String xp102="]";
						String xp103;
						
						for(int i=1;i<=2;i++){
							
							xp103=xp101+i+xp102;
							
							if(!ob.findElement(By.xpath(xp103)).isSelected())
								ob.findElement(By.xpath(xp103)).click();
							
						}
						
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//CALLAWAYGOLF-PRODUCTION
					case "https://callawaygolf.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//NYBC-PRODUCTIONS
					case "https://nybc.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
						
					//PTC-PRODUCTION
					case "https://ptc.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//NYBC-TEST
					case "https://nybc-test.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//CISCO-PRODUCTION
					case "https://qa-cisco.pramata.com":
						ob.findElement(By.xpath("//input[@value='document']")).click();
						Thread.sleep(1000);
						ob.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
						Thread.sleep(2000);
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//INTELLIGRATED-PRODUCTION
					case "https://intelligrated.pramata.com":
						System.out.println("We have arrived directly to the drop down...So,nothing to do....");
						break;
					
						
					//ACTIMIZE
					case "https://actimize.pramata.com":
						System.out.println("We have arrived directly to the drop down...So,nothing to do....");
						break;
					
					//EDIFECS-PRODUCTION
					case "https://edifecs.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					//CTL-OLD
					case "https://ctl-old.pramata.com":
						break;
						
					//MEDTRONIC-TEST
					case "https://medtronic-test.pramata.com":
						break;
						
					//INTELLIGRATED-TEST
					case "https://intelligrated-test.pramata.com":
						ob.findElement(By.linkText("Select All")).click();
						Thread.sleep(2000);
						ob.findElement(By.xpath("//*[@id='model_continue']")).click();
						Thread.sleep(3000);
						break;
						
					}
			}
			 	//select an option from REPORTS dropdown
				public void report_dropdown_select_option(String option){
				
					WebElement myE=ob.findElement(By.xpath("//*[@id='criteria[1][type_id]']"));
					Select mylist=new Select(myE);
					mylist.selectByVisibleText(option);
				
			}
				//To verify that a date falls between 2 particular dates
				public boolean checkDate(String date,String minDate,String maxDate){
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
					
					try{
						
					Date dateToBeChecked=formatter.parse(date);
					Date lowerValue=formatter.parse(minDate);
					Date upperValue=formatter.parse(maxDate);
					
					if(dateToBeChecked.equals(lowerValue) || dateToBeChecked.equals(upperValue) || (dateToBeChecked.after(lowerValue) && dateToBeChecked.before(upperValue)))
						return true;
					else
						return false;
					
					}
					catch (ParseException e) {
						e.printStackTrace();
					}
					return true;
				}
				
				//This method returns a name with specified character length
				public String generateRandomName(int numberOfCharacters){
					
					Random rand =new Random(System.currentTimeMillis());
					
					int min=97;
					int max=122;
					char ch;
					int num;
					
					String random_name="";
					
					for(int i=1;i<=numberOfCharacters;i++){
						
						num=min+rand.nextInt(max-min+1);
						ch=(char)num;
						random_name=random_name+ch;
						
					}
					
					return random_name;
					
				}
				
				
				
	//Added by Chinna

				public static WebDriver getOb() {
					return ob;
				}
				
				public static void setOb(WebDriver ob) {
					TestBase.ob = ob;
				}
				public static Properties getOR() {
					return OR;
				}
				
				public static void setOR(Properties oR) {
					OR = oR;
				}
				public static void scrollingToElementofAPage() throws InterruptedException  {
					JavascriptExecutor jse = (JavascriptExecutor)ob;
					jse.executeScript("scroll(0, 250);");
					Thread.sleep(4000);
				}
				
				
 //Added by Kavya		
				
				/**
				 * Method to wait till the element is visible on the web page
				 * @param driver
				 * @param locator
				 * @param time
				 * @return
				 */
				public WebElement waitForElementTobeVisible(WebDriver driver, By locator, int time) {

					return new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfElementLocated(locator));
				}

				/**
				 * Method to wait till the element is present on the web page
				 * @param driver
				 * @param locator
				 * @param time
				 * @return
				 */
				public WebElement waitForElementTobePresent(WebDriver driver, By locator, int time) {

					return new WebDriverWait(driver, time).until(ExpectedConditions.presenceOfElementLocated(locator));
				}

				/**
				 * Method to click on the specified element using java script executor.
				 * @param driver
				 * @param element
				 */
				public void jsClick(WebDriver driver, WebElement element) {

					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				}

				/**
				 * Method to scroll the specified element to view.
				 * @param driver
				 * @param element
				 */
				public static void scrollElementIntoView(WebDriver driver, WebElement element) {
					JavascriptExecutor jse = (JavascriptExecutor) ob;
					jse.executeScript("arguments[0].scrollIntoView(true);", element);

				}

				/**
				 * Method to wait till all the elements are present on the web page
				 * @param driver
				 * @param locator
				 * @param time
				 * @return
				 */
				public List<WebElement> waitForAllElementsToBePresent(WebDriver driver, By locator, int time) {
					return new WebDriverWait(driver, time).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
				}
				
				/**
				 * This method is to wait for all ajax calls to complete.
				 * @param driver
				 */
				public void waitForAjax(WebDriver driver) {
					try {
						for (int i = 0; i < 60; i++) {

							JavascriptExecutor js = (JavascriptExecutor) driver;
							//check for the pending request count and break if count is zero.
							if ((Long) js
									.executeScript("return angular.element(document.body).injector().get(\'$http\').pendingRequests.length") == 0) {
								break;
							}
							Thread.sleep(1000);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				/**
				 * Method to wait till the element is clickable on the web page
				 * @param driver
				 * @param locator
				 * @param time
				 * @return
				 */
				public WebElement waitForElementTobeClickable(WebDriver driver, By locator, int time) {

					return new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(locator));
				}
				
				 public boolean checkElementIsDisplayed(WebDriver driver,By locator){
				    	boolean result=false;
				    	   try{
				    	 
				    		   result=ob.findElement(locator).isDisplayed();
				    		  
				    	   }catch(Exception e){
				    		   return false;
				    	   }
				    	   return result; 
				       }
	


}
