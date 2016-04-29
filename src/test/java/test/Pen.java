package test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
 
public class Pen {
 
	
	
  public WebDriver driver;

 
	  @Test
	  public void pen() throws Exception{
	  runOnSauceLabsFromLocal("Windows","Chrome");
 
    
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
//    driver.get("http://www.google.com");
    
    driver.get("https://dev-snapshot.1p.thomsonreuters.com");
    Thread.sleep(5000);
    driver.findElement(By.xpath("//button[@title='Sign in with Project Neon']")).click();
    Thread.sleep(5000);
    driver.findElement(By.id("userid")).sendKeys("amneet.singh@thomsonreuters.com");
    driver.findElement(By.id("password")).sendKeys("Transaction@2");
    driver.findElement(By.id("ajax-submit")).click();
    Thread.sleep(180000);
    
    
    
    
    
 
    driver.quit();
  }
  
  public void runOnSauceLabsFromLocal(String os,String browser) throws MalformedURLException{
	  
	  String username = "amneetsingh";
	  String access_key = "f48a9e78-a431-4779-9592-1b49b6d406a4";
	  String url = "http://" + username + ":" + access_key + "@ondemand.saucelabs.com:80/wd/hub";
	  
	  DesiredCapabilities caps = null;
	  
	  if(os.equals("Windows")){
		  
		  if(browser.equals("Chrome")){
			  
			  caps = DesiredCapabilities.chrome();
			  caps.setCapability("platform", "Windows 7");
			  caps.setCapability("version", "48.0");
		  }
		  
		  if(browser.equals("FF")){
			  
			  caps = DesiredCapabilities.firefox();
			  caps.setCapability("platform", "Windows 7");
			  caps.setCapability("version", "44.0");
			  
			  
		  }
		  
		  if(browser.equals("IE")){
			  
			  caps = DesiredCapabilities.internetExplorer();
			  caps.setCapability("platform", "Windows 8.1");
			  caps.setCapability("version", "11.0");
			  
		  }
		  
		  
	  }
	  
	  if(os.equals("Mac")){
		  
		  
		  if(browser.equals("Chrome")){
			  
			  
			  caps = DesiredCapabilities.chrome();
			  caps.setCapability("platform", "OS X 10.11");
			  caps.setCapability("version", "48.0");
		  }
		  
		  if(browser.equals("FF")){
			  
			  
			  caps = DesiredCapabilities.firefox();
			  caps.setCapability("platform", "OS X 10.11");
			  caps.setCapability("version", "44.0");
		  }
		  
		  
		  if(browser.equals("Safari")){
			  
			  
			  caps = DesiredCapabilities.safari();
			  caps.setCapability("platform", "OS X 10.11");
			  caps.setCapability("version", "9.0");
		  }
	  }
	  
	  
	  
	  if(os.equals("iOS")){
		  
		  
		  caps = DesiredCapabilities.iphone();
		  caps.setCapability("platform", "OS X 10.10");
		  caps.setCapability("version", "9.2");
		  caps.setCapability("deviceName","iPhone 6");
		  caps.setCapability("deviceOrientation", "portrait");
	  }
	  
	  if(os.equals("Android")){
		  
		  
		  caps = DesiredCapabilities.android();
		  caps.setCapability("platform", "Linux");
		  caps.setCapability("version", "5.1");
		  caps.setCapability("deviceName","Android Emulator");
		  caps.setCapability("deviceType","phone");
		  caps.setCapability("deviceOrientation", "portrait");
	  }
	  
	  driver = new RemoteWebDriver(new URL(url), caps);
	  
  }

}