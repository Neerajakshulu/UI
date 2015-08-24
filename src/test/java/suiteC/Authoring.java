package suiteC;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;

public class Authoring  {

	static WebDriver driver=TestBase.getOb();
	static int commentSizeBeforeAdd;
	static int commentSizeAfterAdd;
	
	public static int getCommentCount()  {
		String commentSizeBeforeAdd=driver.findElement(By.cssSelector(TestBase.OR.getProperty("tr_cp_authoring_commentCount_css"))).getText();
		//System.out.println("comment size before adding the comment-->"+commentSizeBeforeAdd);
		String num[]=commentSizeBeforeAdd.split(" ");
		//System.out.println("num length-->"+num[num.length-1]);
		return Integer.parseInt(num[num.length-1]);
	}
	
	public static void enterArticleComment(String addComments) throws InterruptedException  {
		commentSizeBeforeAdd=getCommentCount();
		System.out.println("Before-->"+commentSizeBeforeAdd);
		WebElement commentArea=driver.findElement(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("Attribute-->"+commentArea.getAttribute("placeholder"));
		scrollingToElementofAPage();
		commentArea.sendKeys(addComments+RandomStringUtils.randomNumeric(3));
		Thread.sleep(4000);
	}
	
	
	public static void clickAddCommentButton() throws InterruptedException  {
		//LoginTR.waitUntilTextPresent(TestBase.OR.getProperty("tr_authoring_addcomment_css"), "Add Comment");
		scrollingToElementofAPage();
		driver.findElement(By.cssSelector("button[class^='btn webui-btn-primary comment-add-button']")).click();
		//driver.findElement(By.cssSelector(TestBase.OR.getProperty("tr_authoring_addcomment_css"))).click();
		Thread.sleep(6000);
		
		
	}
	
	public static void validateCommentAdd() throws Exception {
		commentSizeAfterAdd = getCommentCount();
		System.out.println("before-->"+commentSizeBeforeAdd);
		System.out.println("After-->"+commentSizeAfterAdd);
		if(!(commentSizeAfterAdd>commentSizeBeforeAdd))  {
			//TestBase.test.log(LogStatus.INFO, "Snapshot below: " + TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered comment not added")));
			throw new Exception("Entered Comment not updated");
		}
	}
	
	
	
	public static void updateComment() throws Exception {
		scrollingToElementofAPage();
		driver.findElement(By.cssSelector("button[class='webui-icon webui-icon-edit edit-comment-icon'][ng-click='editThis(comment.id)']")).click();
		List<WebElement> commentArea=driver.findElements(By.cssSelector("div[id^='taTextElement']"));
		System.out.println("no of comment areas enabled-->"+commentArea.size());
		commentArea.get(1).clear();
		commentArea.get(1).sendKeys(" comment updated ");
		Thread.sleep(4000);
		List<WebElement> subButtons=driver.findElements(By.cssSelector("button[class='btn webui-btn-primary comment-add-button']"));
		System.out.println("Buttons available---2--->"+subButtons);
		for(WebElement subButton:subButtons){
			System.out.println("Button Text-->"+subButton.getText());
			if(subButton.getText().trim().equalsIgnoreCase("submit")){
				subButton.click();
				break;
			}
		}
	}
	
	public  static void validateUpdatedComment(String updatedComments) throws Exception  {
		scrollingToElementofAPage();
		String commentText=driver.findElements(By.cssSelector("div[class='col-xs-7 comment-content'")).get(0).getText();
		System.out.println("Commentary Text-->"+commentText);
		if(!(commentText.contains(updatedComments) && commentText.contains("edited")))  {
			//TestBase.test.log(LogStatus.INFO, "Snapshot below: " + TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered comment not added")));
			AuthoringTest.status=2;
			throw new Exception("Updated "+updatedComments+" not present");
		}
	}
	
	public static void validateAppreciationComment() throws Exception  {
		List<WebElement> apprDivs=driver.findElements(By.cssSelector("div[class='col-xs-7 comment-content']"));
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
		String commentText=driver.findElements(By.cssSelector("div[class='col-xs-7 comment-content'")).get(0).getText();
		System.out.println("Commentary Text-->"+commentText);
		if(!commentText.contains(addComments))  {
			//TestBase.test.log(LogStatus.INFO, "Snapshot below: " + TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered comment not added")));
			throw new Exception("Entered "+addComments+" not present");
		}
	}
	
	public static void scrollingToElementofAPage() throws InterruptedException  {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0, 250);");
		Thread.sleep(4000);
		/*
		((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView();", element);*/
	}
	
	public static void scrollingToElement(WebElement element) throws InterruptedException  {
		((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", element);
	}
}
