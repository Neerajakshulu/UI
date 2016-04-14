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

public class SearchResultsPage extends TestBase {

	PageFactory pf;

	public SearchResultsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void clickOnPostTab() throws Exception {
		BrowserWaits.waitTime(10);
		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(3).click();
		waitForAjax(ob);
		// BrowserWaits.waitTime(6);
	}

	public void clickOnArticleTab() throws Exception {

		waitForAjax(ob);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(0).click();
		waitForAjax(ob);
		// BrowserWaits.waitTime(6);
	}

	public void viewOtherUsersPost(String currentUserName) {

		waitForElementTobePresent(ob, By.cssSelector(OR.getProperty("tr_search_results_item_css")), 180);
		List<WebElement> records;

		while (true) {
			records = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
			int itr = 1;
			String profileName;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				profileName = records.get(i)
						.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_post_author_css")))
						.getText();
				if (!profileName.equalsIgnoreCase(currentUserName)) {
					jsClick(ob,
							records.get(i).findElement(
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

	public List<String> getAuthorDetailsOfPost() throws InterruptedException {
		waitForElementTobePresent(ob, By.cssSelector(OR.getProperty("tr_search_results_item_css")), 180);
		List<WebElement> records;
		List<String> authorDetails = new ArrayList<String>();
		while (true) {
			records = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
			int itr = 1;
			String title, profileName, profileDetails;

			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				title = records.get(i).findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css")))
						.getText();
				if (!title.contains("Post removed by Community Manager") && !title.contains("Post removed by member")) {
					profileName = records.get(i)
							.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_profile_name_css")))
							.getText().trim();
					profileDetails = records.get(i)
							.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_profile_details_css")))
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
									By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))));
					// BrowserWaits.waitTime(6);
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

	public void clickOnPostTitle(String title) {

		waitForAllElementsToBePresent(ob, By.cssSelector(OR.getProperty("tr_search_results_item_css")), 180);
		List<WebElement> records;

		while (true) {
			records = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
			int itr = 1;
			String postTitle;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				postTitle = records.get(i)
						.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))).getText();
				if (postTitle.equals(title)) {
					jsClick(ob,
							records.get(i).findElement(
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

	public void clickOnPeopleName(String title) throws Exception {
		List<WebElement> records;
		waitForAjax(ob);
		while (true) {
			records = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
			int itr = 1;
			String profileTitle;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				profileTitle = records.get(i)
						.findElement(By.cssSelector(OR.getProperty("tr_search_results_profile_title_css"))).getText();
				if (profileTitle.equals(title)) {
					jsClick(ob,
							records.get(i).findElement(
									By.cssSelector(OR.getProperty("tr_search_results_profile_title_css"))));
					// BrowserWaits.waitTime(6);
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

}
