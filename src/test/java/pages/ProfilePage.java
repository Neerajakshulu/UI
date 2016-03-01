package pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;
import org.openqa.selenium.JavascriptExecutor;

public class ProfilePage  extends TestBase {
	
	/**
	 * Search results people count
	 */
	static int peopleCount=0;
	static String PARENT_WINDOW_HANDLE=null;
	static String profileTitle;
	static String profileMetadata;
	static String followUnfollowLableBefore;
	static String followUnfollowLableAfter;
	static String metadata[];
	static int followingBefore;
	static int followersBefore;
	static String watchTextBefore;
	static List<WebElement> topicTypeahead;
	static List<WebElement> profileTabsRecords;
	
	/**
	 * Method for Validate Profile Search with last name
	 * @param lastName
	 * @throws Exception
	 */
	public static void validateProfileLastName(String lastName) throws Exception {
		List<WebElement> profilesLastname=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		if(profilesLastname.size()>0){
			for(WebElement profileLastname:profilesLastname) {
				if(!StringUtils.containsIgnoreCase(profileLastname.getText(), lastName)) {
					throw new Exception("Profile serach not verifying with Last Name");
				}
			}
		}
		else
			System.out.println("Profile Search Results are not available with \t"+lastName+ "\t last Name");
		
	}
	
	/**
	 * Method for Validate Profile Search with Role/Primary Institution/Country
	 * @param metaData
	 * @throws Exception
	 */
	public static void validateProfileMetaData(String metaData) throws Exception {
		List<WebElement> profilesLastname=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		if(profilesLastname.size()>0){
			List<WebElement> profilesMetaData=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_PROFILE_METADATA_TAG);
			System.out.println("Profile metadata--->"+profilesMetaData.size());
			for(WebElement profileMetaData:profilesMetaData) {
				System.out.println("Meta Data-->"+profileMetaData.getText());
				if(!StringUtils.containsIgnoreCase(profileMetaData.getText(), metaData)) {
					throw new Exception("Profile search not verifying with Role/Primary Institution/Country	");
				}
			}
		}
		else
			System.out.println("No Profile Search Results are not available with \t"+metaData+ "\t role/Primary Institution/Country");
	}
	
	
	/**
	 * Method for Click People after searching an profile
	 * @throws Exception, When People are not present/Disabled
	 */
	public static void clickPeople() throws Exception {
			BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(2).click();
			BrowserWaits.waitTime(8);
	}
	
	
	/**
	 * Method for Validate Profile Search with Interests and Skills
	 * @param lastName
	 * @throws Exception
	 */
	public static void validateProfileInterest(String interestAndSkill) throws Exception {
		List<WebElement> profilesname=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		if(profilesname.size()>0){
			for(WebElement profilename:profilesname) {
				profilename.findElement(By.tagName("a")).click();
				BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_INTEREST_AND_SKILLS_CSS);
				Thread.sleep(6000);
				break;
			}
			
			List<WebElement> interestsSkills=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_INTEREST_AND_SKILLS_CSS);
			List<String> interests=new ArrayList<String>();
			for(WebElement intSkill:interestsSkills) {
				interests.add(intSkill.getText());
			}
			
			System.out.println("interests and skills-->"+interests);
			
			if(!interests.contains(interestAndSkill)) {
				throw new Exception("Profile Search not happening with Interests and Skill "+interestAndSkill);
			}
		}
		else
			System.out.println("Profile Search Results are not available with \t"+interestAndSkill+ "\t Interests and Skills");
		
	}
	
	/**
	 * Method for Validate Apps links for redirecting different pages
	 * @param appLinks
	 * @throws Exception, When App links not present
	 */
	public static void validateAppsLinks(String appLinks) throws Exception  {
			String []totalAppLinks=appLinks.split("\\|");
			for(int i=0;i<totalAppLinks.length;i++) {
				BrowserAction.click(OnePObjectMap.HOME_ONEP_APPS_LINK);
				PARENT_WINDOW_HANDLE = ob.getWindowHandle();
				ob.findElement(By.linkText(totalAppLinks[i])).click();
				Thread.sleep(6000);
				ob.manage().window().maximize();
				Set<String> child_window_handles= ob.getWindowHandles();
				 for(String child_window_handle:child_window_handles) {
					 if(!child_window_handle.equals(PARENT_WINDOW_HANDLE)) {
						 ob.switchTo().window(child_window_handle);
						 String appLinkText=BrowserAction.getElement(OnePObjectMap.HOME_ONEP_APPS_PAGE_TITLE_HEADER_CSS).getText();
						 Assert.assertEquals(totalAppLinks[i], appLinkText);
						 ob.close();
						 ob.switchTo().window(PARENT_WINDOW_HANDLE);
				} //if
			 } //for
			} //for
		}
	
	
	/**
	 * Method for click on profile
	 * @throws Exception, When profile link not present
	 */
	public static void clickProfileLink() throws Exception {
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_LINK);
			BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS);
			BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TITLE_CSS);
			BrowserWaits.waitUntilText("Interests and Skills","Posts","Comments","Followers","Following");
			BrowserWaits.waitTime(6);
	}
	
	/**
	 * Method for get profile title
	 * @throws Exception
	 */
	public static void getProfileTitle() throws Exception {
		profileTitle=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TITLE_CSS).getText();
		System.out.println("profile title-->"+profileTitle);
	}
	
	/**
	 * Method for get Profile Meta Data
	 * @throws Exception
	 */
	public static void getProfileMetadata() throws Exception {
		profileMetadata=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_METADATA_CSS).getText();
		System.out.println("profile metadata-->"+profileMetadata);
	}
	
	/**
	 * Method for click on first profile of search people page
	 * @throws Exception
	 */
	public static void clickProfile() throws Exception {
		getProfileTitle();
		getProfileMetadata();
		BrowserAction.jsClick(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TITLE_CSS);
		BrowserWaits.waitTime(8);
	}
	
	
	/**
	 * Method for Validate Profile Title and Profile Metadata
	 * @throws Exception, When Profile title or metadata mismatches
	 */
	public static void validateProfileTitleAndMetadata() throws Exception {
		String title=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TITLE_CSS).getText();
		Assert.assertEquals(profileTitle, title);
		String role=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ROLE_METADATA_CSS).getText();
		String priInstitution=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PRIMARYINSTITUTION_METADATA_CSS).getText();
		String location=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_LOCATION_METADATA_CSS).getText();
		
		if(!(profileMetadata.contains(role)&&profileMetadata.contains(priInstitution)&&profileMetadata.contains(location))){
			throw new Exception("Profile Metadata not matching");
		}
		
	}
	
	
	/**
	 * Method for Validate user should not edit other profiles
	 * @throws Exception, When Other profiles having edit option
	 */
	public static void validateOtherProfileEdit() throws Exception {
		boolean otherProfileEdit=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS).isDisplayed();
		System.out.println("profile edit-->"+otherProfileEdit);
		if(otherProfileEdit) {
			throw new Exception("Edit option should not available for others profile");
		}
				
	}
	
	/**
	 * Method for follow/unfollow other profile from their profile page
	 * @throws Exception, When user not able to follow
	 */
	public static void followOtherProfileFromProfilePage() throws Exception {
		WebElement followUnFollowCheck=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TICKMARK_CSS);
		followUnfollowLableBefore=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TOOLTIP_CSS).getAttribute("tooltip");
		System.out.println("Follow/Unfollow Label Before-->"+followUnfollowLableBefore);
		followUnFollowCheck.click();
		Thread.sleep(4000);
		followUnfollowLableAfter=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TOOLTIP_CSS).getAttribute("tooltip");
		System.out.println("Follow/Unfollow Label After-->"+followUnfollowLableAfter);
		
		if(followUnfollowLableBefore.equalsIgnoreCase(followUnfollowLableAfter)){
			throw new Exception("unable to follow other profile from search screen");
		}
	}
	
	
	/**
	 * Method for Validate user should have edit option to edit profile
	 * and profile name should match with profile image title
	 * @throws Exception, When Other profiles having edit option
	 */
	public static void validateOwnrProfile() throws Exception {
		boolean otherProfileEdit=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS).isDisplayed();
		//System.out.println("profile edit-->"+otherProfileEdit);
		if(!otherProfileEdit) {
			throw new Exception("Edit option should be available for own profile");
		}
		
		String profileName=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TITLE_CSS).getText();
		//System.out.println("profile name-->"+profileName);
		
		String profileImageText=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS).findElement(By.tagName("img")).getAttribute("title");
		//System.out.println("profile image neame-->"+profileImageText);
		Assert.assertEquals(profileName, profileImageText);
		
	}
	
	/**
	 * Method for Validate Edit Cancel Button
	 * @throws Exception, When user not able to click cancel
	 */
	public static void clickEditCancel() throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CANCEL_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_UPDATE_CSS);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CANCEL_CSS);
	}
	
	
	
	/**
	 * Method for Validate user should have edit option to edit profile
	 * and profile name should match with profile image title
	 * @throws Exception, When Other profiles having edit option
	 */
	public static void editUserOwnProfileMetadata(String profileMetadata) throws Exception {
		
		metadata=profileMetadata.split("\\|");
		boolean otherProfileEdit=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS).isDisplayed();
		//System.out.println("profile edit-->"+otherProfileEdit);
		if(!otherProfileEdit) {
			throw new Exception("Edit option should be available for own profile");
		}
		
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS);
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_TITLE_CSS);
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_TITLE_CSS, metadata[0]);
		
		
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_ROLE_CSS);
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_ROLE_CSS, metadata[1]);
		
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS);
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS, metadata[2]);
		
		
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS);
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS, metadata[3]+RandomStringUtils.randomAlphabetic(3));
	}
	
	
	/**
	 * Method for Validate Edit update Button
	 * @throws Exception, When user not able to click update button
	 */
	public static void clickEditUpdate() throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_UPDATE_CSS);
		BrowserWaits.waitTime(6);
	}
	
	
	
	/**
	 * Method for Validate profile metadata
	 * @throws Exception, When data not matching
	 */
	public static void validateProfileMetadata() throws Exception {
		String country=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_LOCATION_METADATA_CSS).get(0).getText();
		System.out.println("country-->"+country);
		if(country.contains(metadata[3])){
			throw new Exception("profile meta data not updated");
		}
	}
	
	/**
	 * Method for Click profile comment tab
	 * @throws Exception, comment tab is not click able
	 */
	public static void clickCommentsTab() throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_COMMENTS_CSS);
		BrowserWaits.waitTime(8);
	}
	
	/**
	 * Method for Click profile Following tab
	 * @throws Exception, Following tab is not click able
	 */
	public static void clickFollowingTab() throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_FOLLOWING_CSS);
		BrowserWaits.waitTime(8);
	}
	
	/**
	 * Method for Click profile Followers tab
	 * @throws Exception, Followers tab is not click able
	 */
	public static void clickFollowersTab() throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_FOLLOWERS_CSS);
		BrowserWaits.waitTime(8);
	}
	
	/**
	 * Method for Click Posts Following tab
	 * @throws Exception, Posts tab is not click able
	 */
	public static void clickPostsTab() throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_POSTS_CSS);
		BrowserWaits.waitTime(12);
	}
	
	
	/**
	 * Method for validate Like own profile comment
	 * @throws Exception, comment like not done
	 */
	public static void commentAppreciation() throws Exception {
		String tooltipBeforeAppreciate=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_COMMENT_APPRECIATE_CSS).getAttribute("tooltip");
		String countBeforeAppreciate=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_COMMENT_APPRECIATE_CSS).getText();
		//System.out.println("Appreciate tooltip-->"+tooltipBeforeAppreciate);
		//System.out.println("Appreciate count-->"+countBeforeAppreciate);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_COMMENT_APPRECIATE_CSS);
		BrowserWaits.waitTime(4);
		String tooltipAfterAppreciate=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_COMMENT_APPRECIATE_CSS).getAttribute("tooltip");
		String countAfterAppreciate=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_COMMENT_APPRECIATE_CSS).getText();
		//System.out.println("Appreciate tooltip after-->"+tooltipAfterAppreciate);
		//System.out.println("Appreciate count after-->"+countAfterAppreciate);
		if(tooltipBeforeAppreciate.equalsIgnoreCase(tooltipAfterAppreciate) && countBeforeAppreciate.equalsIgnoreCase(countAfterAppreciate)){
			throw new Exception("comment appreciation not happend");
		}
	}
	
	/**
	 * Method for Click profile Following tab
	 * @throws Exception, Following tab is not click able
	 */
	public static int getFollowingCount() throws Exception {
		String followingCountBefore=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_FOLLOWING_CSS).getText();
		String followingCount[]=followingCountBefore.split(" ");
		followingBefore=Integer.parseInt(followingCount[1]);
		return followingBefore;
	}
	
	
	/**
	 * Method for Validate Following count
	 * @throws Exception, validation fails
	 */
	public static void validateFollowingCount() throws Exception {
		int followingAfter=getFollowingCount();
		if((followingBefore>followingAfter)||(followingBefore<followingAfter)) {
			throw new Exception("Following count should increase or decrease");
		}
	}
	
	
	
	/**
	 * Method to click on Publish A Post button in the profile page
	 */
	public static void clickOnPublishPostButton() {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS.toString()))
				.click();
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS);
	}

	/**
	 * Method to validate various error messages while creating the post
	 * @param expErrorMsg
	 * @return
	 */
	public static boolean validatePostErrorMessage(String expErrorMsg) {
		boolean result = false;
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_ERROR_CSS);
		String actErrorMessage = ob
				.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_ERROR_CSS.toString()))
				.getText();
		if (expErrorMsg.equalsIgnoreCase(actErrorMessage)) {
			result = true;
		}
		return result;
	}

	/**
	 * Method to enter the specified text to post title box in post creation modal
	 * @param tilte
	 * @throws InterruptedException 
	 */
	public static void enterPostTitle(String tilte) throws InterruptedException {
		BrowserWaits.waitTime(5);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_TITLE_CSS);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_TITLE_CSS.toString()))
				.clear();
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_TITLE_CSS.toString()))
				.sendKeys(tilte);
	}

	/**
	 * Method to enter the specified text to post content box in post creation modal
	 * @param tilte
	 * @throws Exception 
	 */
	public static void enterPostContent(String content) throws Exception {
		BrowserWaits.waitTime(5);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CONTENT_CSS);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CONTENT_CSS.toString()))
				.clear();
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CONTENT_CSS.toString()))
				.sendKeys(content);

	}

	/**
	 * Method to click on publish button in post creation modal
	 * @throws Exception 
	 */
	public static void clickOnPostPublishButton() throws Exception {
		BrowserWaits.waitTime(5);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_PUBLISH_CSS);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_PUBLISH_CSS.toString()))
				.click();
		
	}

	/**
	 * Method to click on cancel button in post creation modal
	 */
	public static void clickOnPostCancelButton() {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_CSS);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_CSS.toString()))
				.click();
	}

	/**
	 * Method to get the count of posts in a profile
	 * @return
	 * @throws InterruptedException 
	 */
	public static int getPostsCount() throws InterruptedException {
		BrowserWaits.waitTime(10);
		waitForAjax(ob);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_COUNT_CSS);
		int count = Integer.parseInt(
				ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_COUNT_CSS.toString()))
						.getText());
		return count;
	}
	
	
	public static int getDraftPostsCount() throws InterruptedException {
		BrowserWaits.waitTime(10);
		waitForAjax(ob);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_DRAFT_POST_COUNT_CSS);
		int count = Integer.parseInt(
				ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_DRAFT_POST_COUNT_CSS.toString()))
						.getText());
		return count;
	}
	
	/**
	 * Method to get the count of Comments count in a profile
	 * @return
	 * @throws InterruptedException 
	 */
	public static int getCommentsCount() throws Exception {
		BrowserWaits.waitTime(10);
		waitForAjax(ob);
		int count = 0;
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_COMMENTS_COUNT_CSS);
		String commentsCount=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_COMMENTS_COUNT_CSS).getText();
		if(commentsCount.contains(",")) {
			count=Integer.parseInt(commentsCount.replace(",", ""));
		}
		else{
			count=Integer.parseInt(commentsCount);
		}
		return count;
	}
	
	
	/**
	 * Method to get the title of the most recent post in the profile.
	 * @return
	 */
	public static String getFirstPostTitle() {
		waitForAjax(ob);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS);
		String postTitle = ob
				.findElements(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS.toString())).get(0)
				.getText();
		return postTitle;
	}

	
	/**
	 * Method for Click profile Followers tab
	 * @throws Exception, Following tab is not click able
	 */
	public static int getFollowersCount() throws Exception {
		String followerCountBefore=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_FOLLOWERS_CSS).getText();
		String followerCount[]=followerCountBefore.split(" ");
		followersBefore=Integer.parseInt(followerCount[1]);
		System.out.println("FOLLOWERSBEFORE-->"+followersBefore);
		return followersBefore;
	}
	
	/**
	 * Method for Validate Followers count
	 * @throws Exception, validation fails
	 */
	public static void validateFollowersCount() throws Exception {
		int followerAfter=getFollowersCount();
		if((followingBefore == followerAfter)) {
			throw new Exception("Followers count should increase or decrease while other follow/unfollow");
		}
	}
	
	/**
	 * Method for Add Topics into Interest and Skills
	 * @throws Exception, unable to add topic
	 */
	public static void addTopicForInterestAndSkills(String topics) throws Exception {
		List<WebElement> addedTopics=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_REMOVE_TOPIC_CSS);
		if(addedTopics.size()>0) {
			for(WebElement addedTopic:addedTopics) {
				addedTopic.click();
				BrowserWaits.waitTime(2);
			}
		}
		String topicLists[]=topics.split("\\|");
		for(String topicList:topicLists) {
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_CSS, topicList);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_TYPEAHEAD_CSS);
		List<WebElement> topicTypeahead=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_TYPEAHEAD_CSS);
		BrowserWaits.waitTime(2);
		BrowserAction.jsClick(topicTypeahead.get(Integer.parseInt(RandomStringUtils.randomNumeric(1))));
		//topicTypeahead.get(Integer.parseInt(RandomStringUtils.randomNumeric(1))).click();
		BrowserWaits.waitTime(2);
		}
		
		List<WebElement> newlyAddedTopics=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_REMOVE_TOPIC_CSS);
		if(!(newlyAddedTopics.size() == topicLists.length)) {
			throw new Exception("Topics not added for Interests and Skills");
		}
	}
	
public static int getLengthOfTitleFromPostCreationModal() {
		
		int count=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_TITLE_CSS.toString())).getAttribute("value").length();
		return count;
	}

	public static void clickOnFirstPost(){
		waitForAjax(ob);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS);
		jsClick(ob,ob.findElements(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS.toString())).get(0));
	}

	public static boolean validateProfanityWordsMaskedForPostTitle(String profanityWord) throws InterruptedException   {
		BrowserWaits.waitTime(4);
		String title=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_TITLE_CSS.toString())).getAttribute("value");
		
		return (!title.contains(profanityWord) && title.contains("**"));
	}
	
public static boolean validateProfanityWordsMaskedForPostContent(String profanityWord) throws InterruptedException {
		BrowserWaits.waitTime(4);
		String title=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CONTENT_CSS.toString())).getText();
		
		return (!title.contains(profanityWord) && title.contains("**"));
	}

	/**
	 * Method to click on publish button in post creation modal
	 * @throws Exception 
	 */
	public static void clickPublishAPost() throws Exception {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAGLIST_PUBLISH_A_POST_BUTTON_CSS);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAGLIST_PUBLISH_A_POST_BUTTON_CSS);
		BrowserWaits.waitUntilText("Publish A Post","Give an update, pose a question, share an interesting find.");
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_CSS);
	}
	
	
	/**
	 * Method to click on publish button cancel button
	 * @throws Exception 
	 */
	public static void clickPublishAPostCancel() throws Exception {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_CSS);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_CSS);
		BrowserAction.click((OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_DISCARD_CSS));
		BrowserWaits.waitUntilNotText("Publish A Post","Give an update, pose a question, share an interesting find.");
	}

	/**
	 * Method to validate Post Title
	 * @throws Exception, When Validation not done 
	 */
	public static void validatePostTitle(String postTitle) throws Exception {
		String enteredPost=getFirstPostTitle();
		Assert.assertEquals(enteredPost, postTitle);
	}
	
	
	/**
	 * Method to validate Published post counts
	 * @throws Exception, When Validation not done 
	 */
	public static void validatePostCount(int postCount) throws Exception {
		int totPosts=getPostsCount();
		if(totPosts == postCount){
			throw new Exception("Post count not getting updated");
		}
	}
	
	
	/**
	 * Method for get Profile information
	 * @throws Exception, When unable to get info
	 */
	public static List<String> getProfileTitleAndMetadata() throws Exception {
		List<String> profileInfo= new ArrayList<String>();
		profileInfo.add(BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TITLE_CSS).getText());
		profileInfo.add(BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ROLE_METADATA_CSS).getText());
		profileInfo.add(BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PRIMARYINSTITUTION_METADATA_CSS).getText());
		profileInfo.add(BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_LOCATION_METADATA_CSS).getText());
		return profileInfo;
	}
	
	
	/**
	 * Method to get the title of the most recent post in the profile.
	 * @return
	 */
	public static void  clickFirstPostTitle() throws Exception {
		waitForAjax(ob);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS);
		BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS).click();
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS);
	}
	
	/**
	 * Method for validate profile posts, posts are more than 10, by default 10 posts should display
	 * @throws Exception, When Validation not done
	 */
	public static void  validateProfilePostTab() throws Exception {
		int totPosts=getPostsCount();
		if(totPosts>=10){
			List<WebElement> postsTimeStamp=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_DETAILS_TIMESTAMP_CSS);
			List<WebElement> postLike=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_DETAILS_LIKE_XPATH);
			List<WebElement> postComments=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_DETAILS_COMMENTS_XPATH);
			List<WebElement> postWatch=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_DETAILS_WATCH_CSS);
			if(!(postsTimeStamp.size()==10 && postLike.size() ==10 && postComments.size() == 10 && postWatch.size()==10)){
				throw new Exception("Post's count by default should be 10 if Post tab having more than 10 posts");
			}
			
		}
		
	}
	
	
	public static void addPostToWatchlist() throws Exception {
		watchTextBefore=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_DETAILS_WATCH_CSS).findElement(By.tagName("span")).getText();
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_DETAILS_WATCH_CSS);
		BrowserWaits.waitTime(2);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_POST_WATCH_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_POST_WATCH_CLOSE_CSS);
		
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_POST_WATCH_CSS);
		BrowserWaits.waitTime(2);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_POST_WATCH_CLOSE_CSS);
		BrowserWaits.waitTime(2);
	}
	
	
	public static void postWatchLabelValidation() throws Exception {
		String watchTextAfter=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_DETAILS_WATCH_CSS).findElement(By.tagName("span")).getText();
		System.out.println("watch text before-->"+watchTextBefore);
		System.out.println("watch text after-->"+watchTextAfter);
		if(watchTextBefore.equalsIgnoreCase(watchTextAfter)){
			throw new Exception("post watch label not getting changed");
		}
	}
	
public static void addExternalLinkToPostContent(String url) throws Exception{
		
		BrowserWaits.waitTime(5);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_INSERT_LINK_BUTTON_CSS);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_INSERT_LINK_BUTTON_CSS);
		BrowserWaits.waitTime(5);
		Alert alert=ob.switchTo().alert();
		alert.sendKeys(url);
		alert.accept();
		BrowserWaits.waitTime(5);
	}

	public static boolean validateProfileDetails(List<String> details) throws Exception {
		BrowserWaits.waitTime(6);
		List<String> expected=getProfileTitleAndMetadata();
		
		return (expected.toString().equals(details.toString()));
	}
	
	/*
	* Method to click on cancel button in post creation modal
	 */
	public static void clickOnPostCancelDiscardButton() {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_DISCARD_XPATH);
		ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_DISCARD_XPATH.toString()))
				.click();
	}

	public static void clickOnPostCancelKeepDraftButton() {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_KEEP_DRAFT_XPATH);
		ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_KEEP_DRAFT_XPATH.toString()))
				.click();
	}
	
	public static void clickOnDraftPostsTab() {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_DRAFT_POST_COUNT_CSS);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_DRAFT_POST_COUNT_CSS.toString()))
				.click();
	}
	
	public static boolean validatePublishButton() {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_PUBLISH_CSS);
		
		return 	ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_PUBLISH_CSS.toString())).isEnabled();
		
	}
	
	/**
	 * Method for Validate profile Primary Institution typeahead options should display while enter min 2 characters
	 * @throws Exception, When Typeahead options not occured
	 */
	public static void primaryInstitutionTypeaheadOptions(String oneChar,String twoChar) throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CANCEL_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_UPDATE_CSS);
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS);
		//enter single character check typeahead options should not display
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS,oneChar);
		BrowserWaits.waitTime(2);
		BrowserWaits.waitUntilElementIsNotDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS);
		
		//enter two characters check typeahead options should  display
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS,twoChar);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS);
		BrowserWaits.waitTime(4);
		List<WebElement> piTypeaheads=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS).findElements(By.tagName("li"));
		if(!(piTypeaheads.size()>0))
			throw new Exception("Primary Instituion Type ahead options are not displayed while enter two characters");
			
	}
	/**
	 * Method for Validate profile Primary Institution
	 * @throws Exception, When Typeahead options not occured
	 */
	public static void selectProfilePITypeAhead(String typeAheadOption) throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CANCEL_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_UPDATE_CSS);
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS);
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS,typeAheadOption);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS);
		BrowserWaits.waitTime(4);
		List<WebElement> piTypeaheads=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS).findElements(By.tagName("li"));
		for(WebElement typeAhead:piTypeaheads) {
			if(StringUtils.containsIgnoreCase(typeAhead.getText(),typeAheadOption.trim()))
			{
				typeAhead.click();
				BrowserWaits.waitTime(2);
				break;
			}
		}
		clickEditUpdate();
	}
	
	public static boolean validateProfilePI(String typeAheadOption) throws Exception {
		BrowserWaits.waitTime(6);
		String actualPI=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PRIMARYINSTITUTION_METADATA_CSS).getText();
		return (StringUtils.containsIgnoreCase(actualPI,typeAheadOption));
	}
	
	/**
	 * Method for Select Country from predefined type ahead list
	 * @throws Exception, When Type ahead options not occurred
	 */
	public static void selectProfileCountryTypeAhead(String countyTypeahead,String fullCountry) throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CANCEL_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_UPDATE_CSS);
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS);
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS,countyTypeahead);
		BrowserWaits.waitTime(4);
		List<WebElement> countyTypeaheads=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS).get(1).findElements(By.tagName("li"));
		for(WebElement typeAhead:countyTypeaheads) {
			if(typeAhead.getText().equalsIgnoreCase(fullCountry))
			{
				typeAhead.click();
				BrowserWaits.waitTime(2);
				break;
			}
		}
		clickEditUpdate();
	}
	
	/**
	 * Method for validate selected profile typeahead country
	 * @param country
	 * @return
	 * @throws Exception
	 */
	public static boolean validateProfileCountry(String country) throws Exception {
		BrowserWaits.waitTime(6);
		String actualCountry=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_COUNTRY_METADATA_CSS).getText();
		return (actualCountry.trim().equalsIgnoreCase(country));
	} 
	
	
	/**
	 * Method for Validate profile Country typeahead options should display while enter min 2 characters
	 * @throws Exception, When Typeahead options not occurred
	 */
	public static void countryTypeaheadOptionsMinChars(String oneChar,String twoChar) throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CANCEL_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_UPDATE_CSS);
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS);
		//enter single character check typeahead options should not display
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS,oneChar);
		BrowserWaits.waitTime(2);
		List<WebElement> countryTyeahead=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS).get(1).findElements(By.tagName("li"));
		if(countryTyeahead.size()>0){
			throw new Exception("Country typeahead options should display while enter min 2 chars");
		}

		//enter two characters check typeahead options should  display
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS,twoChar);
		BrowserWaits.waitTime(4);
		List<WebElement> piTypeaheads=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS).get(1).findElements(By.tagName("li"));
		if(!(piTypeaheads.size()>0))
			throw new Exception("Country Type ahead options are not displayed while enter two characters");
			
	}
	
	/**
	 * Method for Validate topic typeahead options should display while enter min 2 characters
	 * @throws Exception, When Typeahead options not displayed
	 */
	public static void topicTypeaheadOptionsMinChars(String oneChar,String twoChar) throws Exception {
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_CSS, oneChar);
		//System.out.println("topic typeahed options-->"+topicTypeahead.size());
		topicTypeahead=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_TYPEAHEAD_CSS);
		if(topicTypeahead.size()>0){
			throw new Exception("topic typeahead options should display only while enter min 2 characters");
		}
		
		BrowserAction.clickAndClear(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_CSS);
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_CSS, twoChar);
		topicTypeahead=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_TYPEAHEAD_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_TYPEAHEAD_CSS);
		//System.out.println("topic typeahed options-->"+topicTypeahead.size());
		if(!(topicTypeahead.size()>0)){
			throw new Exception("topic typeahead options should display while enter min 2 characters");
		}
	}
	
	/**
	 * Method for validate profile tab focus, tab focus should be POST tab only
	 * @throws Exception, When tab focus on other than POST tab
	 */
	public static void profileTabFocus() throws Exception {
		List<WebElement> profileTabs=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TABS_CSS);
		String tabFocus=profileTabs.get(0).getAttribute("class");
		if(!tabFocus.contains("active")) {
			throw new Exception("Tab focus should be on POST Tab");
		}
	}
	
	
	public static void postTabScroll() throws Exception {
		int totalPosts=getPostsCount();
		if(totalPosts>10){
			profileTabInfiniteScroll("Post");
		}
	}
	
	public static void commentsTabScroll() throws Exception {
		clickCommentsTab();
		int totalComments=getCommentsCount();
		if(totalComments>10){
			profileTabInfiniteScroll("Comments");
		}
	}
	
	public static void followersTabScroll() throws Exception {
		clickFollowersTab();
		int totalFollowers=getFollowersCount();
		if(totalFollowers>10){
			profileTabInfiniteScroll("Followers");
		}
	}
	
	public static void followingTabScroll() throws Exception {
		clickFollowingTab();
		int totalFollowing=getFollowingCount();
		if(totalFollowing>10){
			profileTabInfiniteScroll("Following");
		}
	}
	
	/**
	 * Method for validate profile tab focus, tab focus should be POST tab only
	 * @throws Exception, When tab focus on other than POST tab
	 */
	public static void profileTabInfiniteScroll(String tabName) throws Exception {
		if(tabName.contains("Followers")||tabName.contains("Following")){
			profileTabsRecords=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_TILE_CSS);
		}
		else {
			profileTabsRecords=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TABS_RECORDS_CSS);
		}
		int beforeScroll=profileTabsRecords.size();
		((JavascriptExecutor) ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
		 waitForAjax(ob);
		 BrowserWaits.waitTime(4);
		 
		 if(tabName.contains("Followers")||tabName.contains("Following")){
				profileTabsRecords=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_TILE_CSS);
			}
			else {
				profileTabsRecords=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TABS_RECORDS_CSS);
			}
		 
		 //System.out.println("before scroll-->"+beforeScroll);
		 int firstScroll=profileTabsRecords.size();
		 //System.out.println(" first scroll-->"+firstScroll);
		 if(!(firstScroll>beforeScroll)){
			 throw new Exception("Records/Records Count should be increase while do page scrolldown");
		 }
	}
	
	public static int getPostLikeCount() throws Exception {
		String likeCount=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_LIKE_CSS).getText();
		return Integer.parseInt(likeCount);
	}
	
	/**
	 * Method to validate Post TimeStamp
	 * @throws Exception, When Post doesn't have any title
	 */
	public static void validatePostTimeStamp() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd MMMMMMMM YYYY");
		//get current date time with Date()
		Date date = new Date();
		String current_date=dateFormat.format(date).toString();
		String postCreationDate = BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_POST_TIMESTAMP_XPATH)
				.getText();
		System.out.println("current date-->"+current_date);
		System.out.println("post creation date-->"+postCreationDate);
		if(!postCreationDate.equalsIgnoreCase(current_date)){
			throw new Exception("Post creation date and System date should match");
		}
		
	}
	
	/**
	 * Method for validate comments time stamp
	 * @throws Exception, When comments doesn't have any time stamp
	 */
	public static void commentsTabTimeStamp() throws Exception {
		clickCommentsTab();
		List<WebElement> commentTs=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_COMMENT_TIMESTAMP_CSS);
		if(!(commentTs.size()>0)){
			throw new Exception("None of the comments are having time stamp");
		}
		String timeStamp=commentTs.get(0).getText();
		//System.out.println("timestamp-->"+timeStamp);
		if(!(timeStamp.contains("TODAY")||timeStamp.contains("2016")||timeStamp.contains("AM")||timeStamp.contains("PM"))){
			throw new Exception("Comments timestamp not displaying");
		}
	}
	
}

	

