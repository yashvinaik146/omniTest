package com.omni.projectframeowrk.com_omni_project;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	public WebDriver driver;
	String uploadFile = "uploadFile" + RandomStringUtils.randomAlphanumeric(3);

	@Before
	public void startBrowser() {
		System.setProperty("webdriver.gecko.driver", "D:\\automation\\geckodriver-v0.26.0-win64\\geckodriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void uploadDocumentToCollection() throws AWTException {

		/**Open Application*/
		driver.get("https://omniustest.omnius.com/trainer/ui/");

		/**Login to Application*/
		loginToApplication("testuser", "testuser");
		
		verifyCollection();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		addCollection();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		addCollectionFolder(uploadFile);
		
		searchCollection(uploadFile);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		/**Upload Document to Collection Folder*/
		clickUploadDocument();
		
	}
	
	@Test
	public void sortCollection() {
		/**Open Application*/
		driver.get("https://omniustest.omnius.com/trainer/ui/");

		/**Login to Application*/
		loginToApplication("testuser", "testuser");
		
		/**Verify Collection Header*/
		verifyCollection();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		/**Sort Collection*/
		assertSortingOfAssetID();

	}
	
	@Test
	public void searchCollection() {
		/**Open Application*/
		driver.get("https://omniustest.omnius.com/trainer/ui/");

		/**Login to Application*/
		loginToApplication("testuser", "testuser");
		
		/**Verify Collection Header*/
		verifyCollection();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		/**Add Collection*/
		addCollection();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		addCollectionFolder(uploadFile);
		
		/**Search Collection*/
		searchCollection(uploadFile);

	}
	
	public void loginToApplication(String userName, String passWord) {
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement password = driver.findElement(By.xpath("//input[@id='password']"));

		WebElement login = driver.findElement(By.xpath("//input[@id='kc-login']"));
		username.click();
		username.sendKeys(userName);
		password.click();
		password.sendKeys(passWord);
		login.click();
		
		/**Verifying Login to Application*/
		String actualUrl = "https://omniustest.omnius.com/trainer/ui/";
		String expectedUrl = driver.getCurrentUrl();
		if (actualUrl.equalsIgnoreCase(expectedUrl)) {
			System.out.println("Successful login to Application");
		} else {
			System.out.println("Test failed");
		}
		
	}
	
	public void verifyCollection() {
		WebElement annotate = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("section:nth-child(1) .picnicButtonContentIconTop:nth-child(2) > helper")));

		annotate.click();
		
		/*Verify Collection Header**/
		WebElement collectionHeader = driver.findElement(By.xpath("//heading6[contains(text(),'Collections')]"));
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		assertTrue("Collection header is present", collectionHeader.isDisplayed());
		
	}
	
	public void addCollection() {
		WebElement clickAddColectionIcon = driver.findElement(By.xpath(
				"//button[@class='picnicButtonSizeSmall picnicButtonShapeRound picnicButtonColorGreen picnicButtonContentIconOnly picnicButton']"));

		clickAddColectionIcon.click();
	}
	
	public void addCollectionFolder(String uploadFile) {
				
			driver.findElement(By.cssSelector(".picnicTextBox")).sendKeys(uploadFile);				
			driver.findElement(By.xpath("//button[@class='picnicButtonColorGreen picnicButton']")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void searchCollection(String collectionName) {
		driver.findElement(By.xpath("//echo-table-standalone[@class='echoTableHeightFull picnicHeightLimiter echoTableSearchable echoTableSelectable']//input[@placeholder='Search...']"
				+ "")).sendKeys(collectionName);
		System.out.println(collectionName);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		new WebDriverWait(driver, 20).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//block[1]")));
		driver.findElement(By.xpath("//block[1]")).click();
	}
	
	public void clickUploadDocument() throws AWTException {
		driver.findElement(By.cssSelector(".picnicButtonColorGreen:nth-child(1)")).click();
		typeFileToUpload();
	}
	
	
	public void typeFileToUpload() throws AWTException{
		StringSelection ss = new StringSelection("C:\\Users\\Yash\\Downloads\\TestDocumentOmni.docx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot = new Robot();
		robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(50);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);
	}
	

	public List<String> getDscSortedList(List<WebElement> elementList){
		List<String> values = new ArrayList<String>();
		for(WebElement element : elementList){
		values.add(element.getText());
		}
		Collections.sort(values);
		return values;
		}

		public void assertSortingOfAssetID(){
		List<WebElement> elements = driver.findElements(By.xpath("//echo-table-standalone[@class='echoTableHeightFull picnicHeightLimiter echoTableSearchable echoTableSelectable']//main//table/tbody/tr/td//block"));
		List<String> values = getDscSortedList(elements);

		/** Click on  id header to sort */
		driver.findElement(By.xpath("//chunk[@class='tableHeaderIconSort']")).click();
		
		List<WebElement> sortedElements = driver.findElements(By.xpath("//echo-table-standalone[@class='echoTableHeightFull picnicHeightLimiter echoTableSearchable echoTableSelectable']//main//table/tbody/tr/td//block"));
		List<String> sortedvalues = new ArrayList<String>();
		for(WebElement element : sortedElements){
		sortedvalues.add(element.getText());
		}
		assertTrue(values.equals(sortedvalues));
		}

	@After
	public void endTest() {
		driver.quit();

	}
}
