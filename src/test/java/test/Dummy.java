package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class Dummy {

	public static void main(String[] args){
		
	

DesiredCapabilities desiredCapabilities = DesiredCapabilities.safari();
SafariOptions safariOptions = new SafariOptions();
safariOptions.setUseCleanSession(true);
desiredCapabilities.setCapability(SafariOptions.CAPABILITY, safariOptions);
WebDriver driver = new SafariDriver(desiredCapabilities);
driver.get("http://dev-snapshot.1p.thomsonreuters.com");
		
	}
}
