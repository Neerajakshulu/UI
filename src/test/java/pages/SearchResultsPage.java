package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
		BrowserWaits.waitTime(10);
		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(4).click();
		waitForAjax(ob);
	}
	/**
	 * Method to click on articles tab in search results page
	 * @throws Exception
	 */
	public void clickOnArticleTab() throws Exception {
		BrowserWaits.waitTime(5);
		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(1).click();
		waitForAjax(ob);
	}
	
	/**
	 * Method to click on Patents tab in search results page
	 * @throws Exception
	 */
	public void clickOnPatentsTab() throws Exception {
		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(2).click();
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
							.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS.toString()))
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

}
