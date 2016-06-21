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
 * 
 * @author CP00421684
 *
 */
public class BrowserWaits extends TestBase {

	/**
	 * wait until Element Present
	 * 
	 * @param ElementPath
	 * @return boolean
	 */
	static int time = 120;
	static String locatorType;
	static String locatorText;

	// WebDriver ob;
	public BrowserWaits(WebDriver ob) {
		this.ob = ob;
	}

	public void IsElementPresent(Object elementName) throws Exception {
		if ((elementName instanceof Enum)) {
			locatorType = ((Enum<?>) elementName).name();
			locatorText = elementName.toString();
		}
		int count = 0;
		System.out.println("locator Type -->" + locatorType);
		System.out.println("locator Text -->" + locatorText);

		try {
			if (locatorType.endsWith("_CSS")) {
				count = ob.findElements(By.cssSelector(locatorText)).size();
				System.out.println("CSS locator and cound is-->" + count);
			} else if (locatorType.endsWith("_XPATH")) {
				count = ob.findElements(By.xpath(locatorText)).size();
			} else if (locatorType.endsWith("_LINK")) {
				count = ob.findElements(By.linkText(locatorText)).size();
			} else if (locatorType.endsWith("_PLINK")) {
				count = ob.findElements(By.partialLinkText(locatorText)).size();
			} else if (locatorType.endsWith("_ID")) {
				count = ob.findElements(By.id(locatorText)).size();
			} else if (locatorType.endsWith("_TAG")) {
				count = ob.findElements(By.tagName(locatorText)).size();
			} else if (locatorType.endsWith("_CLASS")) {
				count = ob.findElements(By.className(locatorText)).size();
			} else {
				count = ob.findElements(By.name(locatorText)).size();
			}
		} catch (NoSuchElementException ne) {
			throw new NoSuchElementException("Failed to find element [Locator = {" + locatorText + "}]");
		}

		if (!(count > 0)) {
			throw new Exception("Unable to find Element...Element is not present");
		}

	}

	/**
	 * wait until desired element is displayed
	 * 
	 * @param locator
	 */
	public void waitUntilElementIsDisplayed(Object elementName) {
		WebElement element=getLocator(((Enum<?>) elementName).name(), elementName.toString());
		try {
			(new WebDriverWait(ob, time)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver d) {
					try {
						return Boolean.valueOf(element != null && element.isDisplayed());
					} catch (Exception e) {
						return Boolean.valueOf(false);
					}
				}
			});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find element [Locator = {" + locatorText
					+ "}], after waiting for " + time + "ms");
		}

	}
	
	
	/**
	 * wait until desired element is Clickable
	 * 
	 * @param locator
	 */
	public void waitUntilElementIsClickable(Object elementName) {
		WebElement element=getLocator(((Enum<?>) elementName).name(), elementName.toString());
			try {
				(new WebDriverWait(ob, time)).until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver d) {
						try {
							return Boolean.valueOf(element != null && element.isEnabled());
						} catch (Exception e) {
							return Boolean.valueOf(false);
						}
					}
				});
			} catch (TimeoutException te) {
				throw new TimeoutException("Failed to find element [Locator = {" + locatorText
						+ "}], after waiting for " + time + "ms");
			}
	}

	/**
	 * wait until desired element is not displayed
	 * 
	 * @param locator
	 */
	public void waitUntilElementIsNotDisplayed(Object elementName) {
		WebElement element=getLocator(((Enum<?>) elementName).name(), elementName.toString());
		try {
			(new WebDriverWait(ob, time)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver d) {
					try {
						return Boolean.valueOf(element != null && !element.isDisplayed());
					} catch (Exception e) {
						return Boolean.valueOf(false);
					}
				}
			});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find element [Locator = {" + locatorText
					+ "}], after waiting for " + time + "ms");
		}

	}

	/**
	 * Wait for Expected PageTitle
	 * 
	 * @param title
	 */
	public void waitUntilPageTitle(final String title) {
		try {
			(new WebDriverWait(ob, time)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver d) {
					try {
						return Boolean.valueOf(d.getTitle().trim().equals(title));
					} catch (Exception e) {
						return Boolean.valueOf(false);
					}
				}
			});
		} catch (TimeoutException te) {
			throw new TimeoutException("Page title mismatch. Expected: " + title + "; actual: " + ob.getTitle()
					+ ", after waiting for " + time + "ms");
		}
	}

	public void waitUntilText(final String... text) {
		for (String each : text) {
			waitUntilText(each);
		}
	}

	/**
	 * wait for until expected text present
	 * 
	 * @param text
	 */
	public void waitUntilText(final String text) {
		try {
			(new WebDriverWait(ob, time)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver d) {
					try {
						return Boolean.valueOf(d.getPageSource().contains(text));
					} catch (Exception e) {
						return Boolean.valueOf(false);
					}
				}
			});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find text: " + text + ", after waiting for " + time + "ms");
		}
	}

	/*********************** Wait until specified text is not present **********************/

	public void waitUntilNotText(final String... text) throws Exception {
		for (String each : text) {
			waitUntilNotText(each, time);
		}
	}

	public void waitUntilNotText(final String text,
			final int time) {
		try {
			(new WebDriverWait(ob, time)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver d) {
					try {
						return Boolean.valueOf(!d.getPageSource().contains(text));
					} catch (Exception e) {
						return Boolean.valueOf(false);
					}
				}
			});
		} catch (TimeoutException te) {
			throw new TimeoutException("Text: " + text + " is present which is not expected, after waiting for " + time
					+ "ms");
		}
	}

	/**
	 * wait for provided time
	 * 
	 * @param secs
	 */
	public static void waitTime(final int secs) throws InterruptedException {
		Thread.sleep(secs * 1000);
	}
	
	public WebElement getLocator(String locatorType,
			String locatorText) {
		WebElement ele = null;
		// System.out.println("Locator Type-->"+locatorType);
		// System.out.println("Locator Text-->"+locatorText);

		try {
			if (locatorType.endsWith("_CSS")) {
				ele = ob.findElement(By.cssSelector(locatorText));
			} else if (locatorType.endsWith("_XPATH")) {
				ele = ob.findElement(By.xpath(locatorText));
			} else if (locatorType.endsWith("_LINK")) {
				ele = ob.findElement(By.linkText(locatorText));
			} else if (locatorType.endsWith("_PLINK")) {
				ele = ob.findElement(By.partialLinkText(locatorText));
			} else if (locatorType.endsWith("_ID")) {
				ele = ob.findElement(By.id(locatorText));
			} else if (locatorType.endsWith("_CLASS")) {
				ele = ob.findElement(By.className(locatorText));
			} else if (locatorType.endsWith("_TAG")) {
				ele = ob.findElement(By.tagName(locatorText));
			} else {
				ele = ob.findElement(By.name(locatorText));
			}

		} catch (NoSuchElementException nse) {
			throw new NoSuchElementException("Unable to handle the locator type: " + locatorType
					+ ". Locator name should end with _ID/_NAME/" + "_CLASS/_CSS/_LINK/_PLINK/_TAG/_XPATH");
		}
		return ele;

	}

}
