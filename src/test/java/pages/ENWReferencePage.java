package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;

/**
 * This class contains all the method related to account page
 * @author uc205521
 *
 */
public class ENWReferencePage extends TestBase {

	PageFactory pf;

	public ENWReferencePage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}



	/**
	 * Method to delete record in  Endnote from Neon
	 * 
	 * 
	 */
	public void clearRecordEndnote() {
		
		ob.findElement(By.xpath("//a[@class='ne-app-switcher-flyout__toggle']/i")).click();
		
		ob.findElement(By.xpath("//li[@class='ne-app-switcher-flyout__list-item'][1]/a")).click();
		ob.findElement(By.xpath("//div[@id='idFolderLink_1']/a")).click();
		ob.findElement(By.cssSelector("input[id='idCheckAllRef']")).click();
		ob.findElement(By.cssSelector("input[id='idDeleteTrash']")).click();
		
		
	}

}
