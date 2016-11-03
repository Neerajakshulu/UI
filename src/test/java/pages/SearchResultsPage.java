package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import util.BrowserWaits;
import util.OnePObjectMap;
import base.TestBase;

/**
 * This class contains all the methods related to search results page.
 * @author uc205521
 *
 */
public class SearchResultsPage extends TestBase {

	PageFactory pf;

	public SearchResultsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	/**
	 * Method to click on post tab in search results page
	 * @throws Exception
	 */
	public void clickOnPostTab() throws Exception {
		BrowserWaits.waitTime(5);
		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.SEARCH_RESULT_PAGE_SORT_LEFT_NAV_PANE_CSS).get(4).click();
		waitForAjax(ob);
	}
	/**
	 * Method to click on articles tab in search results page
	 * @throws Exception
	 */
	public void clickOnArticleTab() throws Exception {
		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.SEARCH_RESULT_PAGE_SORT_LEFT_NAV_PANE_CSS).get(1).click();
		waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(OnePObjectMap.NEON_TO_ENW_BACKTOENDNOTE_PAGELOAD_CSS);
	}
	
	/**
	 * Method to click on Patents tab in search results page
	 * @throws Exception
	 */
	public void clickOnPatentsTab() throws Exception {
		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.SEARCH_RESULT_PAGE_SORT_LEFT_NAV_PANE_CSS).get(2).click();
		waitForAjax(ob);
	}
	/**
	 * Method to click on Patents tab in search results page
	 * @throws Exception
	 */
	public void clickOnPeopleTab() throws Exception {
		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.SEARCH_RESULT_PAGE_SORT_LEFT_NAV_PANE_CSS).get(3).click();
		waitForAjax(ob);
	}
	
	
	/**
	 * Method to click on posts of other users than the current user in search results page 
	 * @param currentUserName
	 */
	public void viewOtherUsersPost(String currentUserName) {
		waitForElementTobePresent(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()), 180);
		List<WebElement> records;
		
		while (true) {
			records = ob.findElements(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()));
			int itr = 1;
			String profileName;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				
				profileName = records.get(i)
						.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_AUTHOR_CSS.toString()))
						.getText();
				String title = records.get(i).findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_TITLE_CSS.toString()))
						.getText();
				if (!title.contains("Post removed by Community Manager") && !title.contains("Post removed by member"))
				{
				if (!profileName.equalsIgnoreCase(currentUserName)) {
					jsClick(ob,
							records.get(i).findElement(
									By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_TITLE_CSS.toString())));
					isFound = true;
					break;
				}
				}
			}

			if (isFound)
				break;
			itr++;
			((JavascriptExecutor) ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
			waitForAjax(ob);
		}
	}

	/**
	 * Method to capture the post details in search results page
	 * @return
	 * @throws InterruptedException
	 */
	public List<String> getAuthorDetailsOfPost() throws InterruptedException {
		waitForElementTobePresent(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()), 180);
		List<WebElement> records;
		List<String> authorDetails = new ArrayList<String>();
		while (true) {
			records = ob.findElements(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()));
			int itr = 1;
			String title, profileName, profileDetails;

			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				title = records.get(i).findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_TITLE_CSS.toString()))
						.getText();
				if (!title.contains("Post removed by Community Manager") && !title.contains("Post removed by member")) {
					profileName = records.get(i)
							.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_AUTHOR_CSS.toString()))
							.getText().trim();
					profileDetails = records.get(i)
							.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_AUTHOR_DETAILS_CSS.toString()))
							.getText().trim();

					authorDetails.add(title);
					authorDetails.add(profileName);
					String[] arr = profileDetails.split(",");
					for (int j = 0; j < arr.length; j++) {
						arr[j] = arr[j].trim();

					}
					authorDetails.addAll(Arrays.asList(arr));
					System.out.println(authorDetails);

					isFound = true;
					jsClick(ob,
							records.get(i).findElement(
									By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_TITLE_CSS.toString())));
					waitForPageLoad(ob);
					break;
				}

			}

			if (isFound)
				break;
			itr++;
			((JavascriptExecutor) ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
			waitForAjax(ob);
		}

		return authorDetails;

	}

	/**
	 * Method to click on specified post title in search results page.
	 * @param title
	 */
	public void clickOnPostTitle(String title) {

		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()), 180);
		List<WebElement> records;

		while (true) {
			records = ob.findElements(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()));
			int itr = 1;
			String postTitle;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				postTitle = records.get(i)
						.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_TITLE_CSS.toString())).getText();
				if (postTitle.equals(title)) {
					jsClick(ob,
							records.get(i).findElement(
									By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_TITLE_CSS.toString())));
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

	public void clickOnFirstPostTitle() {

		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()), 180);
		WebElement record = ob.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()));

		jsClick(ob, record.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_POST_TITLE_CSS.toString())));

	}

	/**
	 * Method to click on people name in search results page.
	 * @param title
	 * @throws Exception
	 */
	public void clickOnPeopleName(String title) throws Exception {
		List<WebElement> records;
		waitForAjax(ob);
		while (true) {
			records = ob.findElements(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()));
			int itr = 1;
			String profileTitle;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				profileTitle = records.get(i)
						.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_PEOPLE_TITLE_CSS.toString())).getText();
				if (profileTitle.equals(title)) {
					jsClick(ob,
							records.get(i).findElement(
									By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_PEOPLE_TITLE_CSS.toString())));
					waitForPageLoad(ob);
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
	
	/**
	 * Method to access the article which has comments added to it.
	 */
	public void searchForArticleWithComments() {
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()), 180);
		List<WebElement> itemList;

		while (true) {
			itemList = ob.findElements(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()));
			int commentsCount, itr = 1;
			String strCmntCt;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < itemList.size(); i++) {
				try{
				strCmntCt = itemList.get(i)
						.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_COMMENTS_COUNT_CSS.toString()))
						.getText().replaceAll(",", "").trim();
				}catch(Exception e){
					continue;
				}
				commentsCount = Integer.parseInt(strCmntCt);
				if (commentsCount != 0) {
					jsClick(ob,
							itemList.get(i).findElement(
									By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_TITLE_CSS.toString())));

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

	public boolean isSearchPageDisplayed()

	{ 
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()), 180);
		List<WebElement> itemList = ob.findElements(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_CSS.toString()));
		if(itemList.size()>0)
		return true;
		else
			return false;
	}
	
	public void clickSendToEndnoteSearchPage() throws InterruptedException {
		WebElement button = ob
				.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_SENDTOENDNOTE_BUTTON_CSS.toString()));
		button.click();

		BrowserWaits.waitTime(5);

	}
	
	/**
	 * Method for get the Send to EndNote label text
	 * @return String
	 * @throws Exception, When label not getting fetched
	 */
	public String ValidateSendToEndnoteSearchPage() throws Exception {

		List<WebElement> list = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.SEARCH_RESULTS_PAGE_SENDTOENDNOTE_BUTTON_CSS)
				.findElements(By.tagName("span"));

		for (WebElement we : list) {
			if (we.isDisplayed()) {
				return we.getText();

			}
		}

		return "";
	}

	public void linkSteamAcctWhileSendToEndnoteSearchPage() throws InterruptedException {
		 ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_STEAMLINKING_WHILE_SENDTOENW_BUTTON_CSS.toString())).sendKeys(CONFIG.getProperty("sfbpwrd003"));
		   
		 ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SOCIAL_LINKING_ONBOARDING_MODAL_BUTTON_CSS.toString())).click();
		   

	}
	
	public void linkDiffSteamAcctWhileSendToEndnoteSearchPage(ExtentTest test) throws InterruptedException {
		
		waitForElementTobeVisible(ob, By.cssSelector("div[class='modal-content ng-scope']"), 30);
		ob.findElement(By.cssSelector("button[class='wui-btn wui-btn--secondary button-color-secondary']")).click();
		waitForElementTobeVisible(ob, By.cssSelector("div[class='modal-content ng-scope']"), 30);
		ob.findElement(By.cssSelector(
				OnePObjectMap.HOME_PROJECT_NEON_DIFFSTEAMLINKING_EMAIL_WHILE_SENDTOENW_BUTTON_CSS.toString()))
				.sendKeys("falak.guddu@gmail.com");
		ob.findElement(By.cssSelector(
				OnePObjectMap.HOME_PROJECT_NEON_DIFFSTEAMLINKING_PWD_WHILE_SENDTOENW_BUTTON_CSS.toString()))
				.sendKeys("9595far7202#");

		ob.findElement(
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SOCIAL_LINKING_ONBOARDING_MODAL_BUTTON_CSS.toString()))
				.click();
		waitForElementTobeVisible(ob, By.cssSelector("button[class='wui-icon-btn ng-isolate-scope']"), 30);
		test.log(LogStatus.PASS, "User linked with steam account");

	}
	
	public void addArticleToGroup(String groupTitle) throws Exception {
		addDocumentToGroup(groupTitle);
	}
	
	//Get first article search results title
	public String getArticleTitle() throws Exception {
		clickOnArticleTab();
		String article = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.SEARCH_RESULTS_PAGE_ARCTICLE_OR_PATENT_RESULTS_CSS).getText();
		logger.info("Article Title-->"+article);
		return article;
	}
	
	//Get first Patent search results title
	public String getPatentsTitle() throws Exception {
		clickOnPatentsTab();
		String patent = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.SEARCH_RESULTS_PAGE_ARCTICLE_OR_PATENT_RESULTS_CSS).getText();
		logger.info("Patent Title-->"+patent);
		return patent;
	}
	
	
	
	public void addDocumentToGroup(String groupTitle) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_ADD_TO_GROUP_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_ADD_TO_GROUP_CSS);
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_ADD_TO_GROUP_DROPDOWN_CSS);
		List<WebElement> groupDropdownListItems=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.SEARCH_RESULTS_PAGE_ITEM_ADD_TO_GROUP_DROPDOWN_LIST_CSS);
		for(WebElement groupDropdownListItem: groupDropdownListItems) {
			logger.info("Group Title-->"+groupDropdownListItem.findElement(By.tagName("span")).getText());
			if(groupDropdownListItem.findElement(By.tagName("span")).getText().equalsIgnoreCase(groupTitle)) {
				//checking it's already added to group or not, if added no need to add 
				String addedToGroup=groupDropdownListItem.findElement(By.tagName("button")).getAttribute("class");
				logger.info("Document is already added to group");
				if(!(StringUtils.contains(addedToGroup, "--active"))) {
					groupDropdownListItem.findElement(By.tagName("button")).click();
					BrowserWaits.waitTime(4);
					pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(OnePObjectMap.NEON_TO_ENW_BACKTOENDNOTE_PAGELOAD_CSS);
					String addedGroup=groupDropdownListItem.findElement(By.tagName("button")).getAttribute("class");
					logger.info("Added to group or not-->"+addedGroup);
					if(!(StringUtils.contains(addedGroup, "--active"))) {
						throw new Exception("Document not added to Group from Search results page");
					}
					break;
				} else {
					throw new Exception("no group availble from Add to Group dropdown with "+groupTitle);
				}
			}
				
		}
	}
	

}
