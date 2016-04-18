package Authoring;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import pages.PageFactory;
import util.BrowserWaits;
import util.OnePObjectMap;
import base.TestBase;

/**
 * 
 * This class contains common methods that used in authoring tests.
 *
 */
public class Authoring extends TestBase {

	static int commentSizeBeforeAdd;
	static int commentSizeAfterAdd;
	PageFactory pf;
	static int time = 15;

	public Authoring(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	/**
	 * Method to return comment count from article record view
	 * @return
	 * @throws InterruptedException
	 */
	public int getCommentCount() throws InterruptedException {
		BrowserWaits.waitTime(15);
		waitForPageLoad(ob);
		waitForAjax(ob);
		scrollingToElementofAPage();
		String commentSizeBeforeAdd = ob
				.findElement(By.cssSelector(OR.getProperty("tr_cp_authoring_commentCount_css"))).getText()
				.replaceAll(",", "").trim();
		return Integer.parseInt(commentSizeBeforeAdd);
	}

	/**
	 * Method to enter the text into comment text area 
	 * @param addComments
	 * @throws InterruptedException
	 */
	public void enterArticleComment(String addComments) throws InterruptedException {
		commentSizeBeforeAdd = getCommentCount();
		System.out.println("Before-->" + commentSizeBeforeAdd);
		WebElement commentArea = ob.findElement(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("Attribute-->" + commentArea.getAttribute("placeholder"));
		commentArea.click();
		commentArea.sendKeys(addComments + RandomStringUtils.randomNumeric(3));
		Thread.sleep(2000);// after entering the comments wait for submit button to get enabled or disabled
	}

	public void enterArticleComments(String addComments) throws InterruptedException {
		commentSizeBeforeAdd = getCommentCount();
		System.out.println("Before-->" + commentSizeBeforeAdd);
		WebElement commentArea = ob.findElement(By.cssSelector("div[id^='taTextElement']"));
		scrollingToElementofAPage();
		System.out.println("Attribute-->" + commentArea.getAttribute("placeholder"));
		BrowserWaits.waitTime(20);
		commentArea.click();
		commentArea.clear();
		commentArea.sendKeys(addComments);
		Thread.sleep(2000);// after entering the comments wait for submit button to get enabled or disabled
	}

	public void clickAddCommentButton() throws InterruptedException {
		scrollingToElementofAPage();
		waitForElementTobeClickable(ob, By.xpath("//button[@class='btn webui-btn-primary comment-add-button']"), 60);
		WebElement addCommentElement = ob.findElement(By
				.xpath("//button[@class='btn webui-btn-primary comment-add-button']"));
		JavascriptExecutor executor = (JavascriptExecutor) ob;
		executor.executeScript("arguments[0].click();", addCommentElement);
		waitForAjax(ob);
	}

	public void validateCommentAdd() throws Exception {
		commentSizeAfterAdd = getCommentCount();
		System.out.println("before-->" + commentSizeBeforeAdd);
		System.out.println("After-->" + commentSizeAfterAdd);
		if (!(commentSizeAfterAdd > commentSizeBeforeAdd)) {
			System.out.println("before-->" + commentSizeBeforeAdd);
			System.out.println("After-->" + commentSizeAfterAdd);
			throw new Exception("Entered Comment not updated");
		}
	}

	public void updateComment(String steComment) throws Exception {
		BrowserWaits.waitTime(10);
		scrollingToElementofAPage();
		waitForElementTobeVisible(
				ob,
				By.cssSelector("button[class='webui-icon webui-icon-edit edit-comment-icon'][ng-click='editThis(comment.id)']"),
				40);
		WebElement editCommentElement = ob
				.findElement(By
						.cssSelector("button[class='webui-icon webui-icon-edit edit-comment-icon'][ng-click='editThis(comment.id)']"));
		JavascriptExecutor exe = (JavascriptExecutor) ob;
		exe.executeScript("arguments[0].click();", editCommentElement);

		List<WebElement> commentArea = ob.findElements(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("no of comment areas enabled-->" + commentArea.size());
		commentArea.get(1).clear();
		commentArea.get(1).sendKeys(steComment);
		BrowserWaits.waitTime(5);
		List<WebElement> subButtons = ob.findElements(By.cssSelector("button[class='btn webui-btn-primary']"));
		System.out.println("Buttons available---2--->" + subButtons);
		for (WebElement subButton : subButtons) {
			System.out.println("Button Text-->" + subButton.getText());
			if (subButton.getText().trim().equalsIgnoreCase("submit")) {
				JavascriptExecutor executor = (JavascriptExecutor) ob;
				executor.executeScript("arguments[0].click();", subButton);
				waitForPageLoad(ob);
				break;
			}
		}
	}

	public void validateUpdatedComment(String updatedComments) throws Exception {
		scrollingToElementofAPage();
		String commentText = ob.findElements(By.cssSelector("div[class='col-xs-12 col-sm-7'")).get(0).getText();
		System.out.println("Commentary Text-->" + commentText);
		if (!(commentText.contains(updatedComments) && commentText.contains("edited"))) {
			new Authoring1().status = 2;
			throw new Exception("Updated " + updatedComments + " not present");
		}
	}

	public void validateAppreciationComment() throws Exception {
		List<WebElement> apprDivs = ob.findElements(By.cssSelector("div[class='col-xs-12 col-sm-7']"));
		List<WebElement> apprSubDivs = apprDivs.get(0).findElements(By.cssSelector("div.row")).get(0)
				.findElements(By.cssSelector("div[class^='col-xs-']"));
		System.out.println("app sub divs-->" + apprSubDivs.size());
		scrollingToElementofAPage();
		apprSubDivs.get(1).getText();
		int apprEarCount = Integer.parseInt(apprSubDivs.get(1).getText().replaceAll(",", "").trim());
		System.out.println("Before count-->" + apprEarCount);

		String attrStatus = apprSubDivs.get(0).findElement(By.tagName("button")).getAttribute("ng-click");
		System.out.println("Attribute Status-->" + attrStatus);

		if (attrStatus.contains("NONE")) {
			scrollingToElementofAPage();
			apprSubDivs.get(0).findElement(By.tagName("button")).click();
			Thread.sleep(4000);// After clicking on like button wait for status to change and count update
			int apprAftCount = Integer.parseInt(apprSubDivs.get(1).getText().replaceAll(",", "").trim());
			System.out.println("Already liked  After count-->" + apprAftCount);
			if (!(apprAftCount < apprEarCount)) {
				throw new Exception("Comment Appreciation not happended");
			}
		} else if (attrStatus.contains("UP")) {
			scrollingToElementofAPage();
			apprSubDivs.get(0).findElement(By.tagName("button")).click();
			Thread.sleep(4000);// After clicking on Unlike button wait for status to change and count update
			int apprAftCount = Integer.parseInt(apprSubDivs.get(1).getText().replaceAll(",", "").trim());
			System.out.println("Not liked --After count-->" + apprAftCount);
			if (!(apprAftCount > apprEarCount)) {
				throw new Exception("Comment Appreciation not happended");
			}
		}
	}

	public void validateViewComment(String addComments) throws Exception {
		String commentText = ob.findElements(By.cssSelector("div[class='col-xs-12 watching-article-comments']")).get(0)
				.getText();
		System.out.println("Commentary Text-->" + commentText);
		if (!commentText.contains(addComments)) {
			throw new Exception("Entered " + addComments + " not present");
		}
	}

	public void scrollingToElementofAPage() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) ob;
		jse.executeScript("scroll(0, 250);");
	}

	public void scrollingToElement(WebElement element) throws InterruptedException {
		((JavascriptExecutor) ob).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * Method to Validate Prevent Bot Comment
	 * 
	 * @throws Exception
	 */
	public void validatePreventBotComment() throws Exception {
		waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS);
		String preventBotText = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS).getText();
		pf.getBrowserWaitsInstance(ob).waitUntilText(preventBotText);
		Assert.assertEquals("We are still processing your previous comment. Please try again.", preventBotText);
	}

	public void selectArtcleWithComments() {
		waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 180);
		List<WebElement> itemList;

		while (true) {
			itemList = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
			int commentsCount, itr = 1;
			String strCmntCt;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < itemList.size(); i++) {
				strCmntCt = itemList.get(i)
						.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_comments_count_css")))
						.getText().replaceAll(",", "").trim();
				commentsCount = Integer.parseInt(strCmntCt);
				if (commentsCount != 0) {
					jsClick(ob,
							itemList.get(i).findElement(
									By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))));
					isFound = true;
					break;
				}

			}

			if (isFound)
				break;
			itr++;
			((JavascriptExecutor) ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
			waitForAjax(ob);
		}
	}

	public void waitForTRHomePage() throws InterruptedException {
		waitForPageLoad(ob);
	}

	public void enterTRCredentials(String userName,
			String password) {
		ob.findElement(By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css"))).click();
		waitForElementTobeVisible(ob, By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css")), 60);
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css"))).clear();
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css"))).sendKeys(userName);
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_password_css"))).sendKeys(password);
	}

	public void clickLogin() throws InterruptedException {
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_login_css"))).click();
		waitForPageLoad(ob);
	}

	public void searchArticle(String article) throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(article);
		ob.findElement(By.cssSelector("i[class='webui-icon webui-icon-search']")).click();
		waitForPageLoad(ob);
	}

	public void chooseArticle(String linkName) throws InterruptedException {
		BrowserWaits.waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("searchResults_links")), 180);
		jsClick(ob, ob.findElement(By.xpath(OR.getProperty("searchResults_links"))));
		waitForPageLoad(ob);
	}

	public void waitUntilTextPresent(String locator,
			String text) {
		try {
			WebDriverWait wait = new WebDriverWait(ob, time);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(locator), text));
		} catch (TimeoutException e) {
			throw new TimeoutException("Failed to find element Locator , after waiting for " + time + "ms");
		}
	}

}
