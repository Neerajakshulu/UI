package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import base.TestBase;

/**
 * Class for Perform Browser Actions
 * @author CP00421684
 *
 */
public class BrowserAction extends TestBase{
	
	
	/**
	 * click on desired element
	 * @param locator
	 */
	public static void click(Object elementName) throws Exception{
		if ((elementName instanceof Enum)) {
		  getLocator(((Enum<?>)elementName).name(), elementName.toString()).click();
		}
		else{
			throw new Exception("All Locators Should be Declared as a Constants in Enum ");
		}
		
	}
	
	/**
	 * double click on desired element
	 * @param locator
	 */
	public static void doubleclick(Object elementName) throws Exception {
		Actions act = new Actions(ob);
		if ((elementName instanceof Enum)) {
		 act.doubleClick(getLocator(((Enum<?>)elementName).name(), elementName.toString()));
		}
		else{
			throw new Exception("All Locators Should be Declared as a Constants in Enum");
		}
	}
	
	
	/**
	 * Method for Clear field
	 * @param locator
	 */
	public static void clear(Object elementName) throws Exception {
		if ((elementName instanceof Enum)) { 
			  getLocator(((Enum<?>)elementName).name(), elementName.toString()).clear();
			}
			else{
				throw new Exception("All Locators Should be Declared as a Constants in Enum ");
			}
	}
	
	/**
	 * Method for click and then Clear the field
	 * @param locator
	 */
	public static void clickAndClear(Object elementName) throws Exception{
		if ((elementName instanceof Enum)) { 
			  getLocator(((Enum<?>)elementName).name(), elementName.toString()).clear();
			  getLocator(((Enum<?>)elementName).name(), elementName.toString()).click();
			}
		else{
			throw new Exception("All Locators Should be Declared as a Constants in Enum");
		}
	}
	
	/**
	 * Method for enter Filed value
	 * @param locator
	 */
	public static void enterFieldValue(Object elementName,String enterText) throws Exception {
		if ((elementName instanceof Enum)) { 
			getLocator(((Enum<?>)elementName).name(), elementName.toString()).sendKeys(enterText);
		}
		else{
			throw new Exception("Locator Should be Present in Enum");
		}
	}
	
	/**
	 * Method for Select Drop down value by Visible text
	 * @param locator
	 */
	public static void selectDropdownByText(Object elementName,String dropDownText) throws Exception{
		if ((elementName instanceof Enum)) { 
			getLocator(((Enum<?>)elementName).name(), elementName.toString()).click();
			WebElement ele=getLocator(((Enum<?>)elementName).name(), elementName.toString());
			Select selectRole=new Select(ele);
			selectRole.selectByVisibleText(dropDownText);
		}
		else{
			throw new Exception("Locators Should be Declared as Constant in Enum");
		}
	}
	
	/**
	 * Method for Select Drop down value by Option
	 * @param locator
	 */
	public static void selectDropdownByOption(Object elementName,String optionValue) throws Exception{
		if ((elementName instanceof Enum)) { 
			getLocator(((Enum<?>)elementName).name(), elementName.toString()).click();
			WebElement ele=getLocator(((Enum<?>)elementName).name(), elementName.toString());
			Select selectRole=new Select(ele);
			selectRole.selectByValue(optionValue);
		}else{
			throw new Exception("Locators Should be Declared as Constant in Enum");
		}
	}
	
	
	/**
	 * Method for Select Drop down value by Index
	 * @param locator
	 */
	public static void selectDropdownByIndex(Object elementName,int index) throws Exception {
		if ((elementName instanceof Enum)) { 
			WebElement ele=getLocator(((Enum<?>)elementName).name(), elementName.toString());
			Select selectRole=new Select(ele);
			selectRole.selectByIndex(index);
		}else{
			throw new Exception("Locators Should be Declared as Constant in Enum");
		}
	}
	
	public static WebElement getLocator(String locatorType,String locatorText) {
		WebElement ele = null;
		//System.out.println("Locator Type-->"+locatorType);
		//System.out.println("Locator Text-->"+locatorText);
		
		try {
			if(locatorType.endsWith("_CSS")){
			ele=ob.findElement(By.cssSelector(locatorText));
			}else if(locatorType.endsWith("_XPATH")){
				ele=ob.findElement(By.xpath(locatorText));
			}else if(locatorType.endsWith("_LINK")){
				ele=ob.findElement(By.linkText(locatorText));
			}else if(locatorType.endsWith("_PLINK")){
				ele=ob.findElement(By.partialLinkText(locatorText));
			}else if(locatorType.endsWith("_ID")){
				ele=ob.findElement(By.id(locatorText));
			}else if(locatorType.endsWith("_CLASS")){
				ele=ob.findElement(By.className(locatorText));
			}else if(locatorType.endsWith("_TAG")){
				ele=ob.findElement(By.tagName(locatorText));
			}else{
				ele=ob.findElement(By.name(locatorText));
			}
			
		} catch (NoSuchElementException nse) {
			throw new NoSuchElementException(
					"Unable to handle the locator type: " + locatorType
							+ ". Locator name should end with _ID/_NAME/"
							+ "_CLASS/_CSS/_LINK/_PLINK/_TAG/_XPATH");
		}
		return ele;
		
	}
	
	
	public static List<WebElement> getLocators(String locatorType,String locatorText) {
		List<WebElement> ele = null;
		
		try {
			if(locatorType.endsWith("_CSS")){
			ele=ob.findElements(By.cssSelector(locatorText));
			}else if(locatorType.endsWith("_XPATH")){
				ele=ob.findElements(By.xpath(locatorText));
			}else if(locatorType.endsWith("_LINK")){
				ele=ob.findElements(By.linkText(locatorText));
			}else if(locatorType.endsWith("_PLINK")){
				ele=ob.findElements(By.partialLinkText(locatorText));
			}else if(locatorType.endsWith("_ID")){
				ele=ob.findElements(By.id(locatorText));
			}else if(locatorType.endsWith("_CLASS")){
				ele=ob.findElements(By.className(locatorText));
			}else if(locatorType.endsWith("_TAG")){
				ele=ob.findElements(By.tagName(locatorText));
			}else{
				ele=ob.findElements(By.name(locatorText));
			}
			
		} catch (NoSuchElementException nse) {
			throw new NoSuchElementException(
					"Unable to handle the locator type: " + locatorType
							+ ". Locator name should end with _ID/_NAME/"
							+ "_CLASS/_CSS/_LINK/_PLINK/_TAG/_XPATH");
		}
		return ele;
		
	}
	
	public static void scrollToElement(Object elementName) throws Exception {
		if ((elementName instanceof Enum)) { 
			((JavascriptExecutor) ob).executeScript("arguments[0].scrollIntoView(true);",
					getLocator(((Enum<?>) elementName).name(), elementName.toString()));
		}
		else{
			throw new Exception("Locator Should be Present in Enum");
		}
	}
	
	
	public static void scrollingPageDown() throws InterruptedException  {
		JavascriptExecutor jse = (JavascriptExecutor)ob;
		jse.executeScript("scroll(0, 250);");
	}
	
	/**
	 * Method for get web element
	 * @param locator
	 */
	public static WebElement getElement(Object elementName) throws Exception{
		if ((elementName instanceof Enum)) {
			return  getLocator(((Enum<?>)elementName).name(), elementName.toString());
		}
		else{
			throw new Exception("All Locators Should be Declared as a Constants in Enum ");
		}
	}
	
	
	/**
	 * Method for get web elements
	 * @param locator
	 */
	public static List<WebElement> getElements(Object elementName) throws Exception{
		if ((elementName instanceof Enum)) {
			return  getLocators(((Enum<?>)elementName).name(), elementName.toString());
		}
		else{
			throw new Exception("All Locators Should be Declared as a Constants in Enum ");
		}
		
		
	}
}
