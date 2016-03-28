package suiteC;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.OnePObjectMap;

public class Authoring  extends TestBase {

	static int commentSizeBeforeAdd;
	static int commentSizeAfterAdd;
	PageFactory pf;
	
	public Authoring(WebDriver ob) {
		this.ob=ob;
		pf=new PageFactory();
	}
	
	public  int getCommentCount() throws InterruptedException  {
		waitForPageLoad(ob);
		String commentSizeBeforeAdd=ob.findElement(By.cssSelector(OR.getProperty("tr_cp_authoring_commentCount_css"))).getText();
		//System.out.println("comment size before adding the comment-->"+commentSizeBeforeAdd);
			//System.out.println("num length-->"+num[num.length-1]);
		return Integer.parseInt(commentSizeBeforeAdd);
	}
	
	public  void enterArticleComment(String addComments) throws InterruptedException  {
		commentSizeBeforeAdd=getCommentCount();
		System.out.println("Before-->"+commentSizeBeforeAdd);
		WebElement commentArea=ob.findElement(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("Attribute-->"+commentArea.getAttribute("placeholder"));
		
		// Instantiating JavascriptExecutor
	   // JavascriptExecutor js = (JavascriptExecutor)ob;
        //js.executeScript("arguments[0].setAttribute('value','"+addComments+"');", commentArea);
		commentArea.click();
		//scrollingToElementofAPage();
		commentArea.sendKeys(addComments+RandomStringUtils.randomNumeric(3));
		Thread.sleep(2000);
	}
	
	
	
	public  void enterArticleComments(String addComments) throws InterruptedException  {
		commentSizeBeforeAdd=getCommentCount();
		System.out.println("Before-->"+commentSizeBeforeAdd);
		WebElement commentArea=ob.findElement(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("Attribute-->"+commentArea.getAttribute("placeholder"));
		commentArea.click();
		commentArea.clear();
		//scrollingToElementofAPage();
		commentArea.sendKeys(addComments);
		Thread.sleep(2000);
	}
	
	
	public void clickAddCommentButton() throws InterruptedException  {
		scrollingToElementofAPage();
		waitForElementTobeClickable(ob, By.xpath("//button[@class='btn webui-btn-primary comment-add-button']"), 60);
		WebElement addCommentElement=ob.findElement(By.xpath("//button[@class='btn webui-btn-primary comment-add-button']"));
		//ob.findElement(By.cssSelector("button[class^='btn webui-btn-primary comment-add-button']")).click();
		JavascriptExecutor executor = (JavascriptExecutor)ob;
		executor.executeScript("arguments[0].click();", addCommentElement);
		waitForAjax(ob);
	}
	
	public void validateCommentAdd() throws Exception {
		commentSizeAfterAdd = getCommentCount();
		System.out.println("before-->"+commentSizeBeforeAdd);
		System.out.println("After-->"+commentSizeAfterAdd);
		if(!(commentSizeAfterAdd>commentSizeBeforeAdd))  {
			throw new Exception("Entered Comment not updated");
		}
	}
	
	
	
	public void updateComment(String steComment) throws Exception {
		scrollingToElementofAPage();
		waitForElementTobeVisible(ob, By.cssSelector("button[class='webui-icon webui-icon-edit edit-comment-icon'][ng-click='editThis(comment.id)']"), 40);
		WebElement editCommentElement=ob.findElement(By.cssSelector("button[class='webui-icon webui-icon-edit edit-comment-icon'][ng-click='editThis(comment.id)']"));
		JavascriptExecutor exe= (JavascriptExecutor)ob;
		exe.executeScript("arguments[0].click();", editCommentElement);
		
		
		List<WebElement> commentArea=ob.findElements(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("no of comment areas enabled-->"+commentArea.size());
		commentArea.get(1).clear();
		commentArea.get(1).sendKeys(steComment);
		BrowserWaits.waitTime(5);
		List<WebElement> subButtons=ob.findElements(By.cssSelector("button[class='btn webui-btn-primary']"));
		System.out.println("Buttons available---2--->"+subButtons);
		for(WebElement subButton:subButtons){
			System.out.println("Button Text-->"+subButton.getText());
			if(subButton.getText().trim().equalsIgnoreCase("submit")){
				JavascriptExecutor executor = (JavascriptExecutor)ob;
				executor.executeScript("arguments[0].click();", subButton);
				
				break;
			}
		}
	}
	
	public   void validateUpdatedComment(String updatedComments) throws Exception  {
		scrollingToElementofAPage();
		String commentText=ob.findElements(By.cssSelector("div[class='col-xs-12 col-sm-7'")).get(0).getText();
		System.out.println("Commentary Text-->"+commentText);
		if(!(commentText.contains(updatedComments) && commentText.contains("edited")))  {
			//TestBase.test.log(LogStatus.INFO, "Snapshot below: " + TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered comment not added")));
			new AuthoringTest().status=2;
			throw new Exception("Updated "+updatedComments+" not present");
		}
	}
	
	public void validateAppreciationComment() throws Exception  {
		List<WebElement> apprDivs=ob.findElements(By.cssSelector("div[class='col-xs-12 col-sm-7']"));
		List<WebElement> apprSubDivs=apprDivs.get(0).findElements(By.cssSelector("div.row")).get(0).findElements(By.cssSelector("div[class^='col-xs-']"));
		System.out.println("app sub divs-->"+apprSubDivs.size());
		scrollingToElementofAPage();
		apprSubDivs.get(1).getText();
		int apprEarCount=Integer.parseInt(apprSubDivs.get(1).getText());
		System.out.println("Before count-->"+apprEarCount);
		
		String attrStatus=apprSubDivs.get(0).findElement(By.tagName("button")).getAttribute("ng-click");
		System.out.println("Attribute Status-->"+attrStatus);
		
		if(attrStatus.contains("NONE")) {
			scrollingToElementofAPage();
			apprSubDivs.get(0).findElement(By.tagName("button")).click();
			Thread.sleep(4000);
			int apprAftCount=Integer.parseInt(apprSubDivs.get(1).getText());
			System.out.println("Already liked  After count-->"+apprAftCount);
			   if(!(apprAftCount<apprEarCount)) {
				   throw new Exception("Comment Appreciation not happended");
			   }
		} 
		else if (attrStatus.contains("UP")) {
			scrollingToElementofAPage();
			apprSubDivs.get(0).findElement(By.tagName("button")).click();
			Thread.sleep(4000);
			int apprAftCount=Integer.parseInt(apprSubDivs.get(1).getText());
			System.out.println("Not liked --After count-->"+apprAftCount);
			   if(!(apprAftCount>apprEarCount)) {
				   throw new Exception("Comment Appreciation not happended");
			   }
			}
	}
	
	public void validateViewComment(String addComments) throws Exception  {
		String commentText=ob.findElements(By.cssSelector("div[class='col-xs-12 watching-article-comments']")).get(0).getText();
		System.out.println("Commentary Text-->"+commentText);
		if(!commentText.contains(addComments))  {
			//TestBase.test.log(LogStatus.INFO, "Snapshot below: " + TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered comment not added")));
			throw new Exception("Entered "+addComments+" not present");
		}
	}
	
	public void scrollingToElementofAPage() throws InterruptedException  {
		JavascriptExecutor jse = (JavascriptExecutor)ob;
		jse.executeScript("scroll(0, 250);");
	}
	
	public void scrollingToElement(WebElement element) throws InterruptedException  {
		((JavascriptExecutor) ob).executeScript(
                "arguments[0].scrollIntoView(true);", element);
	}
	
	/**
	 * Method for Validate Prevent Bot Comment
	 * @throws Exception
	 */
	public void validatePreventBotComment() throws Exception  {
		waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS);
		String preventBotText=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS).getText();
		//System.out.println("Prevent Bot--->"+preventBotText);
		pf.getBrowserWaitsInstance(ob).waitUntilText(preventBotText);
		Assert.assertEquals("We are still processing your previous comment. Please try again.", preventBotText);
	}
	
	public void selectArtcleWithComments(){
	waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 80);
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
			if (commentsCount !=0) {
				jsClick(ob,itemList.get(i).findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))));
				isFound = true;
				break;
			}

		}

		if (isFound)
			break;
		itr++;
		((JavascriptExecutor)ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
		waitForAjax(ob);
	}
	}
}
