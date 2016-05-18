package notifications;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.TestUtil;

public class NotificationsTestBase extends TestBase {

	protected static final String TOKENIZER_DOUBLE_PIPE = "||";
	PageFactory pf = new PageFactory();
	int count = 0;

	@BeforeSuite
	public void beforeSuite() throws Exception {
		logger.info("Notification UI automation starting time - " + new Date());
		initialize();
		logger.info("Notification UI automation Running environment - " + host);
		if (!StringUtils.containsIgnoreCase(host, "https://projectne.thomsonreuters.com")) {
			createNewUsers();
			if (StringUtils.containsIgnoreCase(host, "https://dev-stable.1p.thomsonreuters.com")) {
			if (user1 == null) {
				user1 = "3sgor3+a7lpxeq96gte0@sharklasers.com";
				fn1 = "prjleohp";
				ln1 = "prjleohpai";
				count++;
			}
			if (user2 == null) {
				user2 = "3sgpav+ak12q8xmmb280@sharklasers.com";
				fn2 = "ucbvpwab";
				ln2 = "ucbvpwabqs";
				count++;
			}
			if (user3 == null) {
				user3 = "3sgpjq+7gysrhor017d0@sharklasers.com";
				fn3 = "usylbleh";
				ln3 = "usylbleheq";
				count++;
			}
			}
			if(count<3)
			followUsers();
		}
	}

	@AfterSuite
	public void afterSuite() {
		extent.flush();
		logger.info("Notification UI automation ending time - " + new Date());
	}

	public void followUsers() {
		// user1 following user2
		try {
			if (user1 != null && user2 != null) {
				openBrowser();
				maximizeWindow();
				clearCookies();
				ob.navigate().to(host);
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
				// login with user1
				pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
				pf.getLoginTRInstance(ob).clickLogin();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
				ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(fn2 + " " + ln2);
				ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
				JavascriptExecutor jse = (JavascriptExecutor) ob;
				jse.executeScript("scroll(0,-500)");
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("profilesTabHeading_link")), 30);
				ob.findElement(By.xpath(OR.getProperty("profilesTabHeading_link"))).click();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_follow_button")), 40);
				ob.findElement(By.xpath(OR.getProperty("search_follow_button"))).click();
				BrowserWaits.waitTime(3);
				logout();
				// closeBrowser();
			} else {
				throw new Exception("User creation problem hence throwing exception");
			}
		} catch (Throwable t) {
			logger.info(t.getMessage());
		}

		try {
			if (user2 != null && user3 != null) {
				// openBrowser();
				// maximizeWindow();
				// clearCookies();
				// ob.navigate().to(host);
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);
				// login with user2
				pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
				pf.getLoginTRInstance(ob).clickLogin();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
				ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(fn3 + " " + ln3);
				ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
				JavascriptExecutor jse = (JavascriptExecutor) ob;
				jse.executeScript("scroll(0,-500)");
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("profilesTabHeading_link")), 30);
				ob.findElement(By.xpath(OR.getProperty("profilesTabHeading_link"))).click();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_follow_button")), 40);
				ob.findElement(By.xpath(OR.getProperty("search_follow_button"))).click();
				BrowserWaits.waitTime(3);
				logout();
				closeBrowser();
			} else {
				throw new Exception("User creation problem hence throwing exception");
			}
		} catch (Throwable t) {
			logger.info(t.getMessage());
		}
	}

	public void createNewUsers() {
		if (!StringUtils.containsIgnoreCase(host, "https://projectne.thomsonreuters.com")) {
			if (TestUtil.isSuiteRunnable(suiteXls, "Notifications")) {
				while (flag < 3) {
					try {
						if (user1 != null && user2 != null && user2 != null) {
							flag = 3;
						}
						// 1)Creating User1
						if (user1 == null) {
							logger.info("Started user1 creation");
							openBrowser();
							maximizeWindow();
							clearCookies();
							fn1 = generateRandomName(8);
							ln1 = generateRandomName(10);
							user1 = createNewUser(fn1, ln1);
							logger.info("User1 Mail id:" + user1);
							logger.info("User1 Name:" + fn1 + " " + ln1);
							BrowserWaits.waitTime(3);
							logout();
							closeBrowser();
							logger.info("user1 created successfully");
						}
						// Creating User2
						if (user2 == null) {
							logger.info("Started user2 creation");
							openBrowser();
							maximizeWindow();
							clearCookies();
							fn2 = generateRandomName(8);
							ln2 = generateRandomName(10);
							user2 = createNewUser(fn2, ln2);
							logger.info("User2 Mail id:" + user2);
							logger.info("User2 Name:" + fn2 + " " + ln2);
							BrowserWaits.waitTime(3);
							logout();
							closeBrowser();
							logger.info("user2 created successfully");
						}
						// Creating User3
						if (user3 == null) {
							logger.info("Started user3 creation");
							openBrowser();
							maximizeWindow();
							clearCookies();
							fn3 = generateRandomName(8);
							ln3 = generateRandomName(10);
							user3 = createNewUser(fn3, ln3);
							logger.info("User3 Mail id:" + user3);
							logger.info("User3 Name:" + fn3 + " " + ln3);
							BrowserWaits.waitTime(3);
							logout();
							closeBrowser();
							logger.info("user3 created successfully");
						}
					} catch (Throwable t) {
						logger.error("There is some problem in the creation of users");
						logger.error(t);
						t.printStackTrace();
						// StringWriter errors = new StringWriter();
						// t.printStackTrace(new PrintWriter(errors));
						try {
							test.addScreenCapture(captureScreenshot("UserCreationError"
									+ this.getClass().getSimpleName() + "_user_creation_error_screenshot"));// screenshot
						} catch (Throwable t1) {
							logger.error("There is some problem in the taking sereenshoot");
							logger.error(t);
							t.printStackTrace();
						}
						closeBrowser();
					}
					flag++;
				}

			}
		}

	}

}
