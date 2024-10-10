package lambpaktest;

import java.util.Set;

import org.openqa.selenium.WebDriver;

//import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
//import org.testng.annotations.AfterClass;

//import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

import lambpakPageObject.LambdaTestHomePage;
import lambpakUtil.Baseclass;

public class LambdaTestIntegrationTest3 extends Baseclass {

	public WebDriver driver;
	public LambdaTestHomePage homePage;

	@BeforeMethod
	public ExtentReports setUp() {
		try {

			driver = intializationBrowser("MicrosoftEdge");

			// Additional setup steps if needed
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to set up the browser: " + e.getMessage());
		}
		return null;
	}

	@Test
	public void testLambdaTestIntegration() {
		try {
			// 1-2. Navigate to https://www.lambdatest.com/ and perform an explicit wait.
			homePage = new LambdaTestHomePage(driver);
			homePage.navigateToHomePage();
			homePage.waitForPageToLoad();

			// 3-5. Scroll to 'SEE ALL INTEGRATIONS', click, and save window handles.
			homePage.scrollToSeeAllIntegrations();
			homePage.clickSeeAllIntegrations();

			@SuppressWarnings("unused")
			String firstWindowHandle = driver.getWindowHandle();
			homePage.switchToNewTab();
			System.out.println("Window Handles: " + driver.getWindowHandles());
			Set<String> allWindowHandles = driver.getWindowHandles();

			for (String windowHandle : allWindowHandles) {
				if (windowHandle.equals(firstWindowHandle)) {
					driver.switchTo().window(windowHandle);
					break;
				}
			}

			String childWindowHandle = "";
			for (String handle : allWindowHandles) {
				if (!handle.equals(firstWindowHandle)) {
					childWindowHandle = handle;
					break;
				}
			}

			// 6. Verify the URL.
			System.out.println(homePage.getCurrentUrl());
			Assert.assertEquals(homePage.getCurrentUrl(), "https://www.lambdatest.com/integrations");

			// 7. Scroll to 'Codeless Automation'.
			homePage.scrollToCodelessAutomation();

			// 8-9. Click 'LEARN MORE' for Testing Whiz and verify the title.
			homePage.clickLearnMoreForTestingWhiz();
			System.out.println(homePage.getPageTitle());
			if (homePage.getPageTitle().equals("TestingWhiz Integration With LambdaTest")) {
				System.out.println(homePage.getPageTitle() + "is expected");
			} else {
				throw new AssertionError("Title is not as expected");
			}

			// 10-11. Close the current window and print the current window count.
			for (String handle : allWindowHandles) {
				if (handle.equals(firstWindowHandle)) {
					driver.switchTo().window(handle); // Close the previous window
					driver.close();
					break;
				}
			}

			int windowCount = allWindowHandles.size() - 1;
			System.out.println("Number of open windows: " + windowCount);

			// 12-13. Set the URL to https://www.lambdatest.com/blog, click 'Community', and
			driver.switchTo().window(childWindowHandle);                                                                                            
			homePage.setUrlToBlog();
			homePage.clickCommunityLink();
			System.out.println(homePage.getCurrentUrlAfterClickingCommunityLink());
			if (!driver.getCurrentUrl().equals(homePage.getCurrentUrlAfterClickingCommunityLink())) {
				throw new AssertionError("URL is not as expected");
			} else {
				System.out.println("URL is expected");
			}
			Assert.assertEquals(homePage.getCurrentUrlAfterClickingCommunityLink(),
					"https://community.lambdatest.com/");

			// 14. Close the current browser window.
			homePage.closeBrowser();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}