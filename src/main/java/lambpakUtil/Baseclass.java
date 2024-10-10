package lambpakUtil;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Baseclass {

	public static WebDriver driver;
	public static ExtentTest test;
	public static ExtentReports extent = null;

	public static final String GRID_HUB_URL = "http://192.168.70.227:4444/wd/hub";
	public static final String REPORTS_PATH = System.getProperty("user.dir") + "\\Lambda2\\Reports.html";
	public static final String SCREENSHOTS_PATH = System.getProperty("user.dir") + "\\screenshots\\";
	DesiredCapabilities capabilities;


	public WebDriver intializationBrowser(String browserName) throws MalformedURLException {
		
		  String hubURL = "https://hub.lambdatest.com/wd/hub";

		capabilities = new DesiredCapabilities(); if (browserName.equals("chrome")) {
		  ChromeOptions chromeOptions = new ChromeOptions();
		  capabilities.setCapability("browserName", "Chrome");
		  capabilities.setCapability("goog:chromeOptions", chromeOptions); } else if
		  (browserName.equals("firefox")) { FirefoxOptions firefoxOptions = new
		  FirefoxOptions(); capabilities.setCapability("browserName", "firefox");
		  capabilities.setCapability("moz:firefoxOptions", firefoxOptions); } else if
		  (browserName.equals("MicrosoftEdge")) { EdgeOptions edgeOptions = new
		  EdgeOptions(); capabilities.setCapability("browserName", "MicrosoftEdge");
		  capabilities.setCapability("ms:edgeOptions", edgeOptions); } else if
		  (browserName.equals("ie") || browserName.equals("internet explorer")) {
		  InternetExplorerOptions ieOptions = new InternetExplorerOptions();
		  capabilities.setCapability("browserName", "internet explorer");
		  capabilities.setCapability("ie:ieOptions", ieOptions); }
		  
		  HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		  ltOptions.put("user", "rootqat"); ltOptions.put("accessKey",
		  "Iqjmon32WEuPHsYfQHveACFfDx1QFS5vWdP2bl8a43YvMPgMQo"); ltOptions.put("build",
		  "Selenium"); ltOptions.put("name", this.getClass().getName());
		  
		  capabilities.setCapability("LT:Options", ltOptions);
		  
		  driver = new RemoteWebDriver(new URL(hubURL), capabilities);
		  
		  //driver = new RemoteWebDriver(new URL(GRID_HUB_URL), capabilities);
		  
		  return driver;
	}

	@BeforeSuite
	public ExtentReports setUp() {
		extent = new ExtentReports();
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(REPORTS_PATH);
		htmlReporter.config().setReportName("Selenium Advance");
		htmlReporter.config().setDocumentTitle("Selenium Advance");
		extent.setSystemInfo("Engineer", "Vishakha Patil");
		extent.attachReporter(htmlReporter);
		return extent;
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(SCREENSHOTS_PATH + testCaseName + ".jpg");
		FileUtils.copyFile(source, file);
		return SCREENSHOTS_PATH + testCaseName + ".jpg";
	}

	
	  @AfterMethod public void afterEachMethod(ITestResult result) throws
	  IOException { String testName = result.getName(); String screenshotpath =
	  getScreenshot(testName, driver); test = extent.createTest(testName);
	  
	  if (result.getStatus() == ITestResult.FAILURE) { test.log(Status.FAIL,
	  MarkupHelper.createLabel(testName + " Test Case Failed.", ExtentColor.RED));
	  test.fail("Test Case Failed",
	  MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build()); }
	  else if (result.getStatus() == ITestResult.SKIP) { test.log(Status.SKIP,
	  MarkupHelper.createLabel(testName + " Test Case Skipped.",
	  ExtentColor.ORANGE)); test.skip("Test Case Skipped",
	  MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build()); }
	  else if (result.getStatus() == ITestResult.SUCCESS) { test.log(Status.PASS,
	  MarkupHelper.createLabel(testName + " Test Case Passed.",
	  ExtentColor.GREEN)); test.pass("Test Case Passed",
	  MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build()); } }
	 

	@AfterClass
	public void tearDown() {
		extent.flush();
		driver.quit();
	}
}
