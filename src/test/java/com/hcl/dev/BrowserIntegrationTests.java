package com.hcl.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest
class BrowserIntegrationTests {

	private static WebDriver chromedriver;
	private static WebDriver iedriver;

	@Value("${azure.app.baseurl}")
	private String appUrl;

	@BeforeAll
	public static void createAndStartService() throws IOException {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.iedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("start-maximized");
		chromeOptions.addArguments("enable-automation");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--disable-infobars");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--disable-browser-side-navigation");
		chromeOptions.addArguments("--disable-gpu");
		chromedriver = new ChromeDriver(chromeOptions);
		InternetExplorerOptions ieOptions = new InternetExplorerOptions();
//		iedriver = new InternetExplorerDriver(ieOptions);
		
	}

	@AfterAll
	public static void createAndStopService() {
		chromedriver.quit();
//		iedriver.quit();
	}

	@Test
	public void shouldLoginChrome() {
		shouldLogin(chromedriver);
	}
	
	private void shouldLogin(WebDriver driver) {
		driver.get(appUrl);
		WebElement link = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/a"));
		link.click();
		link = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/ul/li[1]"));
		link.click();
		link = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[2]/div[3]/a"));
		link.click();
		WebElement userName = driver
				.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[3]/div/form/div[1]/input"));
		userName.sendKeys("admin");
		WebElement password = driver
				.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[3]/div/form/div[2]/input"));
		password.sendKeys("admin123");
		link = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[3]/div/form/div[3]/button[2]"));
		link.click();
		Awaitility.await().until(() -> driver.findElements(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[1]/h3")).size() > 0);
		
		WebElement heading = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[1]/h3"));
		assertEquals("Welcome to Season Parking ApplyNew", heading.getText());
			
	}

}
