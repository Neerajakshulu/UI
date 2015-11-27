package suiteC;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

public class Authoring  extends TestBase {

	static int commentSizeBeforeAdd;
	static int commentSizeAfterAdd;
	
	public static int getCommentCount() throws InterruptedException  {
		Thread.sleep(10000);
		String commentSizeBeforeAdd=ob.findElement(By.cssSelector(OR.getProperty("tr_cp_authoring_commentCount_css"))).getText();
		//System.out.println("comment size before adding the comment-->"+commentSizeBeforeAdd);
		String num[]=commentSizeBeforeAdd.split(" ");
		//System.out.println("num length-->"+num[num.length-1]);
		return Integer.parseInt(num[num.length-1]);
	}
	
	public static void enterArticleComment(String addComments) throws InterruptedException  {
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
		Thread.sleep(3000);
	}
	
	
	
	public static void enterArticleComments(String addComments) throws InterruptedException  {
		commentSizeBeforeAdd=getCommentCount();
		System.out.println("Before-->"+commentSizeBeforeAdd);
		WebElement commentArea=ob.findElement(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("Attribute-->"+commentArea.getAttribute("placeholder"));
		commentArea.click();
		commentArea.clear();
		//scrollingToElementofAPage();
		commentArea.sendKeys(addComments);
		Thread.sleep(3000);
	}
	
	
	public static void clickAddCommentButton() throws InterruptedException  {
		scrollingToElementofAPage();
		WebElement addCommentElement=ob.findElement(By.xpath("//button[@class='btn webui-btn-primary comment-add-button']"));
		//ob.findElement(By.cssSelector("button[class^='btn webui-btn-primary comment-add-button']")).click();
		JavascriptExecutor executor = (JavascriptExecutor)ob;
		executor.executeScript("arguments[0].click();", addCommentElement);
		Thread.sleep(5000);
	}
	
	public static void validateCommentAdd() throws Exception {
		commentSizeAfterAdd = getCommentCount();
		System.out.println("before-->"+commentSizeBeforeAdd);
		System.out.println("After-->"+commentSizeAfterAdd);
		if(!(commentSizeAfterAdd>commentSizeBeforeAdd))  {
			throw new Exception("Entered Comment not updated");
		}
	}
	
	
	
	public static void updateComment() throws Exception {
		scrollingToElementofAPage();
		
		WebElement editCommentElement=ob.findElement(By.cssSelector("button[class='webui-icon webui-icon-edit edit-comment-icon'][ng-click='editThis(comment.id)']"));
		JavascriptExecutor exe= (JavascriptExecutor)ob;
		exe.executeScript("arguments[0].click();", editCommentElement);
		
		
		List<WebElement> commentArea=ob.findElements(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("no of comment areas enabled-->"+commentArea.size());
		commentArea.get(1).clear();
		commentArea.get(1).sendKeys("comment updated");
		Thread.sleep(4000);
		List<WebElement> subButtons=ob.findElements(By.cssSelector("button[class='btn webui-btn-primary']"));
		System.out.println("Buttons available---2--->"+subButtons);
		for(WebElement subButton:subButtons){
			System.out.println("Button Text-->"+subButton.getText());
			if(subButton.getText().trim().equalsIgnoreCase("submit")){
				JavascriptExecutor executor = (JavascriptExecutor)ob;
				executor.executeScript("arguments[0].click();", subButton);
				//subButton.click();
				break;
			}
		}
	}
	
	public  static void validateUpdatedComment(String updatedComments) throws Exception  {
		scrollingToElementofAPage();
		String commentText=ob.findElements(By.cssSelector("div[class='col-xs-12 col-sm-7'")).get(0).getText();
		System.out.println("Commentary Text-->"+commentText);
		if(!(commentText.contains(updatedComments) && commentText.contains("edited")))  {
			//TestBase.test.log(LogStatus.INFO, "Snapshot below: " + TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered comment not added")));
			AuthoringTest.status=2;
			throw new Exception("Updated "+updatedComments+" not present");
		}
	}
	
	public static void validateAppreciationComment() throws Exception  {
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
	
	public static void validateViewComment(String addComments) throws Exception  {
		String commentText=ob.findElements(By.cssSelector("div[class^='col-xs-12 ng-scope col-sm-7']")).get(0).getText();
		System.out.println("Commentary Text-->"+commentText);
		if(!commentText.contains(addComments))  {
			//TestBase.test.log(LogStatus.INFO, "Snapshot below: " + TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered comment not added")));
			throw new Exception("Entered "+addComments+" not present");
		}
	}
	
	public static void scrollingToElementofAPage() throws InterruptedException  {
		JavascriptExecutor jse = (JavascriptExecutor)ob;
		jse.executeScript("scroll(0, 250);");
		Thread.sleep(4000);
	}
	
	public static void scrollingToElement(WebElement element) throws InterruptedException  {
		((JavascriptExecutor) ob).executeScript(
                "arguments[0].scrollIntoView(true);", element);
	}
	
	/**
	 * Method for Validate Prevent Bot Comment
	 * @throws Exception
	 */
	public static void validatePreventBotComment() throws Exception  {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS);
		String preventBotText=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS).getText();
		//System.out.println("Prevent Bot--->"+preventBotText);
		BrowserWaits.waitUntilText(preventBotText);
		Assert.assertEquals("We are still processing your previous comment. Please try again.", preventBotText);
	}
}
