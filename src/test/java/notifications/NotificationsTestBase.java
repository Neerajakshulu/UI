package notifications;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeSuite;

import base.TestBase;

public class NotificationsTestBase extends TestBase {
	
	@BeforeSuite
	public void beforeSuite() throws Exception {

		initialize();
 		if(!host.equalsIgnoreCase("https://projectne.thomsonreuters.com")){

			if (testUtil.isSuiteRunnable(suiteXls, "Notifications")) {

				if (count == 0) {

					if (flag < 3) {

						flag++;

						try {
							openBrowser();
							maximizeWindow();
							clearCookies();
							fn1 = generateRandomName(8);
							ln1 = generateRandomName(10);
							System.out.println(fn1 + " " + ln1);
							user1 = createNewUser(fn1, ln1);
							System.out.println("User1:" + user1);
							Thread.sleep(3000);
							logout();
							closeBrowser();

							// 2)Create User2 and follow User1
							openBrowser();
							maximizeWindow();
							clearCookies();
							fn2 = generateRandomName(8);
							ln2 = generateRandomName(10);
							System.out.println(fn2 + " " + ln2);
							user2 = createNewUser(fn2, ln2);
							System.out.println("User2:" + user2);
							Thread.sleep(3000);

							waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
							ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(fn1 + " " + ln1);
							ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

							JavascriptExecutor jse = (JavascriptExecutor) ob;
							jse.executeScript("scroll(0,-500)");
							waitForElementTobeVisible(ob, By.xpath(OR.getProperty("profilesTabHeading_link")), 30);
							ob.findElement(By.xpath(OR.getProperty("profilesTabHeading_link"))).click();
							waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_follow_button")), 40);
							ob.findElement(By.xpath(OR.getProperty("search_follow_button"))).click();

							Thread.sleep(3000);

							logout();
							closeBrowser();
							System.out.println("User count:" + count);
							count++;

						}

						catch (Throwable t) {

							System.out.println("There is some problem in the creation of users");
							System.out.println(t);

							StringWriter errors = new StringWriter();
							t.printStackTrace(new PrintWriter(errors));

							test.addScreenCapture(captureScreenshot("UserCreationError" + this.getClass().getSimpleName()
									+ "_user_creation_error_screenshot"));// screenshot

						}

					}
				}

			}
		}
	}

}
