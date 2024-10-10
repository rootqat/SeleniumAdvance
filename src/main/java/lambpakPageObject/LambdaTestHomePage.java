package lambpakPageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LambdaTestHomePage {

	private WebDriver driver;
	private WebDriverWait wait;
	private Duration timeout;
	private String mainWindowHandle;

	private static final By SEE_ALL_INTEGRATIONS_LINK = By.xpath(
			"//a[contains(.,'Explore all Integrations')]");
	private static final By CODELESS_AUTOMATION_LINK = By.xpath("//a[contains(text(),'Codeless Automation')]");
	private static final By LEARN_MORE_LINK = By
			.xpath("//a[contains(text(),'Integrate Testing Whiz with LambdaTest')]");
	private static final By COMMUNITY_LINK = By.xpath("//div[@class='menu-menu-1-container']/ul/li/a[contains(.,'Community')]");
	private static final By PAGE_TITLE = By.xpath("//h1[.='TestingWhiz Integration With LambdaTest']");

	public LambdaTestHomePage(WebDriver driver) {
		timeout = Duration.ofSeconds(15);
		this.driver = driver;
		this.wait = new WebDriverWait(driver, timeout);
		mainWindowHandle = driver.getWindowHandle();
	}

	private WebElement waitForElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void navigateToHomePage() {
		driver.get("https://www.lambdatest.com/");
	}

	public void waitForPageToLoad() {
		wait.until(ExpectedConditions.titleContains("LambdaTest"));
	}

	public void scrollToSeeAllIntegrations() {
		WebElement seeAllIntegrationsLink = waitForElement(SEE_ALL_INTEGRATIONS_LINK);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
				seeAllIntegrationsLink);
	}

	public void clickSeeAllIntegrations() {
		WebElement seeAllIntegrationsLink = waitForElement(SEE_ALL_INTEGRATIONS_LINK);
		seeAllIntegrationsLink.click();
	}

	public void switchToNewTab() {
		driver.switchTo().newWindow(WindowType.TAB);
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public void scrollToCodelessAutomation() {
		WebElement codelessAutomationElement = waitForElement(CODELESS_AUTOMATION_LINK);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", codelessAutomationElement);
	}

	public void clickLearnMoreForTestingWhiz() {
		WebElement learnMoreLink = waitForElement(LEARN_MORE_LINK);
		learnMoreLink.click();
	}

	public String getPageTitle() {
		WebElement pageTitle = waitForElement(PAGE_TITLE);
		return pageTitle.getText();
	}

	public void closeCurrentWindow() {
		driver.close();
	}

	public void switchToTab(String WindowHandle) {

		driver.switchTo().window(WindowHandle);
	}

	public int getCurrentWindowCount() {
		return driver.getWindowHandles().size();
	}

	public void setUrlToBlog() {
		driver.get("https://www.lambdatest.com/blog");
	}

	public void clickCommunityLink() {
		WebElement communityLink = waitForElement(COMMUNITY_LINK);
		communityLink.click();
	}

	public String getCurrentUrlAfterClickingCommunityLink() {
		return driver.getCurrentUrl();
	}

	public void closeBrowser() {
		driver.quit();
	}
}
