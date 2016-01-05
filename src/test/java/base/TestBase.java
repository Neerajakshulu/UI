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
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
	public static int xRows,xCols;
	public static String[][] xData;
	
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
	public void openBrowser() throws Exception{
		
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
		System.out.println("Selenium Browser Name-->"+System.getenv("SELENIUM_BROWSER"));
		desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));
		System.out.println("Selenium Version-->"+System.getenv("SELENIUM_VERSION"));
		System.out.println("Selenium Plaform-->"+System.getenv("SELENIUM_PLATFORM"));
		desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
		desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
//		desiredCapabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS,true);
		ob = new RemoteWebDriver(new URL("http://amneetsingh:f48a9e78-a431-4779-9592-1b49b6d406a4@ondemand.saucelabs.com:80/wd/hub"),
                desiredCapabilities);
		String waitTime=CONFIG.getProperty("defaultImplicitWait");
		String pageWait=CONFIG.getProperty("defaultPageWait");
		ob.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
		try{
			ob.manage().timeouts().implicitlyWait(Long.parseLong(pageWait), TimeUnit.SECONDS);
			}
		catch(Throwable t){
				
			System.out.println("Page Load Timeout not supported in safari driver");
		}
		
	}
	 
	
	// selenium RC/ Webdriver
	
//	Opening the desired browser
//	public void openBrowser(){
//
//		if(CONFIG.getProperty("browserType").equals("FF")){
//			ob = new FirefoxDriver();
//		}
//		else if (CONFIG.getProperty("browserType").equals("IE")){
//			System.setProperty("webdriver.ie.driver", "C:\\Users\\UC201214\\Desktop\\IEDriverServer.exe");
//			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
//			System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
//			ob = new InternetExplorerDriver(capabilities);
//		}
//		else if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")){
//			DesiredCapabilities capability = DesiredCapabilities.chrome();
//			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//			System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\compatibility issues\\chromedriver.exe");
//			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
//			ob= new ChromeDriver(capability);
//		}
//
//		else if (CONFIG.getProperty("browserType").equalsIgnoreCase("Safari")){
//
//			DesiredCapabilities desiredCapabilities = DesiredCapabilities.safari();
//			SafariOptions safariOptions = new SafariOptions();
//			safariOptions.setUseCleanSession(true);
//			desiredCapabilities.setCapability(SafariOptions.CAPABILITY, safariOptions);
//			ob = new SafariDriver(desiredCapabilities);
//		}
//
//
//
//		String waitTime=CONFIG.getProperty("defaultImplicitWait");
//		String pageWait=CONFIG.getProperty("defaultPageWait");
//		ob.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
//		try{
//			ob.manage().timeouts().pageLoadTimeout(Long.parseLong(pageWait), TimeUnit.SECONDS);
//		}
//		catch(Throwable t){
//
//			System.out.println("Page Load Timeout not supported in safari driver");
//		}
//
//	}
	
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
	    	   waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
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
				public static WebElement waitForElementTobeVisible(WebDriver driver, By locator, int time) {

					return new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfElementLocated(locator));
				}

				/**
				 * Method to wait till the element is present on the web page
				 * @param driver
				 * @param locator
				 * @param time
				 * @return
				 */
				public static WebElement waitForElementTobePresent(WebDriver driver, By locator, int time) {

					return new WebDriverWait(driver, time).until(ExpectedConditions.presenceOfElementLocated(locator));
				}

				/**
				 * Method to click on the specified element using java script executor.
				 * @param driver
				 * @param element
				 */
				public static void jsClick(WebDriver driver, WebElement element) {

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
				public static List<WebElement> waitForAllElementsToBePresent(WebDriver driver, By locator, int time) {
					return new WebDriverWait(driver, time).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
				}
				
				/**
				 * This method is to wait for all ajax calls to complete.
				 * @param driver
				 */
				public static void waitForAjax(WebDriver driver) {
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
				public static WebElement waitForElementTobeClickable(WebDriver driver, By locator, int time) {

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
	

					/**
					 * This is wait condition for multiple windows.
					 * @param driver
					 * @param numberOfWindows
					 * @return
					 */
					public static ExpectedCondition<Boolean> numberOfWindowsToBe(WebDriver driver,
							final int numberOfWindows) {
						return new ExpectedCondition<Boolean>() {
							@Override
							public Boolean apply(WebDriver driver) {
								driver.getWindowHandles();
								return driver.getWindowHandles().size() == numberOfWindows;
							}
						};
					}

					/**
					 * This method is to wait till specified number of windows are open.
					 * 
					 * @param driver
					 * @param numberOfWindows
					 */
					public static void  waitForNumberOfWindowsToEqual(WebDriver driver,
							final int numberOfWindows) {
						new WebDriverWait(driver, 60).until(numberOfWindowsToBe(driver,
								numberOfWindows));
					}
					
					/**
					 * Method to switch to new window.
					 * @param driver
					 * @return
					 */
					public static String switchToNewWindow(WebDriver driver) {
						String mainWindow = driver.getWindowHandle();
						Set<String> windows = driver.getWindowHandles();
						windows.remove(mainWindow);
						driver.switchTo().window(windows.iterator().next());
						return mainWindow;

					}

					/**
					 * Method to switch to main window
					 * @param driver
					 * @param mainWindowHandle
					 */
					public static void switchToMainWindow(WebDriver driver, String mainWindowHandle) {

						driver.switchTo().window(mainWindowHandle);

					}

					
					
					
//Method to 

//Method to return excel workbook path
public String returnExcelPath(char ch){
	
	if(ch=='A')
		return "src/test/resources/xls/A suite.xlsx";
	else if(ch=='B')
		return "src/test/resources/xls/B suite.xlsx";
	else if(ch=='C')
		return "src/test/resources/xls/C suite.xlsx";
	else if(ch=='D')
		return "src/test/resources/xls/D suite.xlsx";
	else if(ch=='E')
		return "src/test/resources/xls/E suite.xlsx";
	else if(ch=='F')
		return "src/test/resources/xls/F suite.xlsx";
	else
		return "No such excel file present";
	
	
	
}



//Method to read excel worksheet
public String xlRead(String sPath,int r,int c) throws Exception{
	File myxl = new File(sPath);
	FileInputStream myStream = new FileInputStream(myxl);
	
	XSSFWorkbook myWB = new XSSFWorkbook(myStream);
	XSSFSheet mySheet = myWB.getSheetAt(0);	// Referring to 1st sheet
	xRows = mySheet.getLastRowNum()+1;
	xCols = mySheet.getRow(0).getLastCellNum();
	System.out.println("Rows are " + xRows);
	System.out.println("Cols are " + xCols);
	xData = new String[xRows][xCols];
    for (int i = 0; i < xRows; i++) {
           XSSFRow row = mySheet.getRow(i);
            for (int j = 0; j < xCols; j++) {
               XSSFCell cell = row.getCell(j); // To read value from each col in each row
               String value = cellToString(cell);
               xData[i][j] = value;
               }
        }	
	return xData[r][c];
}

public String xlRead2(String sPath,String cellValue,int c) throws Exception{
	int r=0;
	File myxl = new File(sPath);
	FileInputStream myStream = new FileInputStream(myxl);
	
	XSSFWorkbook myWB = new XSSFWorkbook(myStream);
	XSSFSheet mySheet = myWB.getSheetAt(0);	// Referring to 1st sheet
	xRows = mySheet.getLastRowNum()+1;
	xCols = mySheet.getRow(0).getLastCellNum();
	xData = new String[xRows][xCols];
    for (int i = 0; i < xRows; i++) {
           XSSFRow row = mySheet.getRow(i);
            for (int j = 0; j < xCols; j++) {
               XSSFCell cell = row.getCell(j); // To read value from each col in each row
               String value = cellToString(cell);
               xData[i][j] = value;
               if( xData[i][j].equalsIgnoreCase(cellValue)){
            	   r=i;
               }
               }
        }	
	return xData[r][c];
}

public String cellToString(XSSFCell cell) {
	// This function will convert an object of type excel cell to a string value
        int type = cell.getCellType();
        Object result;
        switch (type) {
            case XSSFCell.CELL_TYPE_NUMERIC: //0
                result = cell.getNumericCellValue();
                break;
            case XSSFCell.CELL_TYPE_STRING: //1
                result = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA: //2
                throw new RuntimeException("We can't evaluate formulas in Java");
            case XSSFCell.CELL_TYPE_BLANK: //3
                result = "-";
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN: //4
                result = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_ERROR: //5
                throw new RuntimeException ("This cell has an error");
            default:
                throw new RuntimeException("We don't support this cell type: " + type);
        }
        return result.toString();
    }

}