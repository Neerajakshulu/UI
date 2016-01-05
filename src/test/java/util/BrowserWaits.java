package util;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;

/**
 * Class for Perform Browser Waits
 * @author CP00421684
 *
 */
public class BrowserWaits extends TestBase{
	
	/**
	 * wait until Element Present
	 * @param ElementPath
	 * @return boolean
	 */
	static int time=60;
	static String locatorType;
	static String locatorText;
	public static void IsElementPresent(Object elementName) throws Exception {
		if ((elementName instanceof Enum)) {
			 locatorType=((Enum<?>)elementName).name();
			 locatorText= elementName.toString();
			}
		int count=0;
		System.out.println("locator Type -->"+locatorType);
		System.out.println("locator Text -->"+locatorText);
		
		try {
			if(locatorType.endsWith("_CSS")){
			 count = ob.findElements(By.cssSelector(locatorText)).size();
			 System.out.println("CSS locator and cound is-->"+count);
			}else if(locatorType.endsWith("_XPATH")){
			 count = ob.findElements(By.xpath(locatorText)).size();
			}else if(locatorType.endsWith("_LINK")){
			 count = ob.findElements(By.linkText(locatorText)).size();
			}else if(locatorType.endsWith("_PLINK")){
				 count = ob.findElements(By.partialLinkText(locatorText)).size();
			}else if(locatorType.endsWith("_ID")){
				 count = ob.findElements(By.id(locatorText)).size();
			}else if(locatorType.endsWith("_TAG")){
				 count = ob.findElements(By.tagName(locatorText)).size();
			}else if(locatorType.endsWith("_CLASS")){
				 count = ob.findElements(By.className(locatorText)).size();
			}else {
				 count = ob.findElements(By.name(locatorText)).size();
			}
		} catch (NoSuchElementException ne) {
			throw new NoSuchElementException("Failed to find element [Locator = {"
					+ locatorText+ "}]");
		}
		
		if(!(count > 0)){
			throw new Exception("Unable to find Element...Element is not present");
			}
		
		
	}
	
	/**
	 * wait until desired element is displayed
	 * @param locator
	 */
	public static void waitUntilElementIsDisplayed(Object elementName) {
		if ((elementName instanceof Enum)) {
			 locatorType=((Enum<?>)elementName).name();
			 locatorText= elementName.toString();
			}
		
		if(locatorType.endsWith("_CSS")){
		try {
			(new WebDriverWait(ob, time))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							try {
								final WebElement element = d
										.findElement(By.cssSelector(locatorText));
								return Boolean.valueOf(element != null
										&& element.isDisplayed());
							} catch (Exception e) {
								return Boolean.valueOf(false);
							}
						}
					});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find element [Locator = {"
					+ locatorText + "}], after waiting for " + time
					+ "ms");
		}
	}else if(locatorType.endsWith("_XPATH")){
		try {
			(new WebDriverWait(ob, time))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							try {
								final WebElement element = d
										.findElement(By.xpath(locatorText));
								return Boolean.valueOf(element != null
										&& element.isDisplayed());
							} catch (Exception e) {
								return Boolean.valueOf(false);
							}
						}
					});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find element [Locator = {"
					+ locatorText + "}], after waiting for " + time
					+ "ms");
		}
	} else if(locatorType.endsWith("_LINK")){
		try {
			(new WebDriverWait(ob, time))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							try {
								final WebElement element = d
										.findElement(By.linkText((locatorText)));
								return Boolean.valueOf(element != null
										&& element.isDisplayed());
							} catch (Exception e) {
								return Boolean.valueOf(false);
							}
						}
					});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find element [Locator = {"
					+ locatorText + "}], after waiting for " + time
					+ "ms");
		}
	}
		
	}
	
	/**
	 * Wait for Expected PageTitle
	 * @param title
	 */
	public static void waitUntilPageTitle(final String title) {
		try {
			(new WebDriverWait(ob, time))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							try {
								return Boolean.valueOf(d.getTitle().trim()
										.equals(title));
							} catch (Exception e) {
								return Boolean.valueOf(false);
							}
						}
					});
		} catch (TimeoutException te) {
			throw new TimeoutException("Page title mismatch. Expected: "
					+ title + "; actual: " + ob.getTitle()
					+ ", after waiting for " + time + "ms");
		}
	}
	
	
	public static void waitUntilText(final String... text) {
		for (String each : text) {
			waitUntilText(each);
		}
	}
	
	/**
	 * wait for until expected text present
	 * @param text
	 */
	public static void waitUntilText(final String text) {
		try {
			(new WebDriverWait(ob, time))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							try {
								return Boolean.valueOf(d.getPageSource()
										.contains(text));
							} catch (Exception e) {
								return Boolean.valueOf(false);
							}
						}
					});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find text: " + text
					+ ", after waiting for " + time + "ms");
		}
	}
	
	/**
	 * wait for provided time
	 * @param secs
	 */
	public static void waitTime(final int secs) throws InterruptedException {
		Thread.sleep(secs*1000);
	}

}
