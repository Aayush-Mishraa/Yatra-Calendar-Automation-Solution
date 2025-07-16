package com.yatra.automation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YatraAutomationScript3 {

	public static void main(String[] args) throws InterruptedException {
		// launch the driver
		// WebDriver is like a remote control for the browser.
		// ChromeDriver is the tool that works specifically with the Chrome browser.
		// We create a browser object using ChromeDriver (child class),
		// but we use WebDriver (parent interface) to control it — this is called
		// "polymorphism" in coding.
		// The 'wd' variable is the name we give to this browser control.
		// WebDriver is often abstract, meaning it only tells us *what* needs to be
		// done,
		// and child classes li ke ChromeDriver handle *how* it's done.
		// flexibility across multiple browser drivers.

		// ChromeOptions chromeOptions = new ChromeOptions();
		// p 

		// Set Chrome options to handle browser-level features
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-notifications"); // Disable browser notifications

		// Initialize WebDriver with Chrome options
		WebDriver wd = new ChromeDriver(chromeOptions);

		// Navigate to Yatra and maximize the browser window
		wd.get("https://www.yatra.com");
		wd.manage().window().maximize();

		// Initialize WebDriverWait to manage synchronization and avoid flaky locators
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(20));

		// Define locator using By class (By is abstract with static locator methods)
		By departureDateLocator = By.xpath("//div[@aria-label='Departure Date inputbox' and @role='button']");

		// Wait until the departure date button is clickable and store it as a
		// WebElement
		WebElement departureDateButton = wait.until(ExpectedConditions.elementToBeClickable(departureDateLocator));

		// Click the button to trigger the date picker (synchronized interaction)
		departureDateButton.click();
		WebElement currentMonthWebElement = selectTheMonthFromCalender(wait, 0);// current month
		WebElement nextMonthWebElement = selectTheMonthFromCalender(wait, 1);// next month
		Thread.sleep(2000);
		// By dateLocator = By.xpath("//div[contains(@class,
		// \"react-datepicker__day\")]");
		String lowestPriceForCurrentMonth = getMeLowestPrice(currentMonthWebElement);
		String lowestPriceForNextMonth = getMeLowestPrice(nextMonthWebElement);
		System.out.println(lowestPriceForCurrentMonth);
		System.out.println(lowestPriceForNextMonth);
		compareTwoMonthsPrice(lowestPriceForCurrentMonth, lowestPriceForNextMonth);
	}

	public static String getMeLowestPrice(WebElement monthWebElement) {
		By dateLocator = By.xpath("//span[contains(@class,\"custom-day-content\")]");
		// on the below line we called that the chain element

		List<WebElement> junePriceList = monthWebElement.findElements(dateLocator);
		int lowestPrice = Integer.MAX_VALUE;
		WebElement priceElement = null;
		for (WebElement price : junePriceList) {

			// tell which is the lowest price
			String priceString = price.getText();
			if (priceString.length() > 0) {
				priceString = priceString.replace("₹", "").replace(",", "");
				// find the samllest price
				// covert the string into integer

				int priceInt = Integer.parseInt(priceString);
				if (priceInt < lowestPrice) {
					lowestPrice = priceInt;
					priceElement = price;

				}

			}
		}

		WebElement dateElement = priceElement.findElement(By.xpath(".//../.."));
		String result = dateElement.getAttribute("aria-label") + "--- Price is Rs " + lowestPrice;
		return result;
	}

	public static WebElement selectTheMonthFromCalender(WebDriverWait wait, int index) throws InterruptedException {

		By calendarMonthsLocatore = By.xpath("//div[@class=\"react-datepicker__month-container\"]");
		// List<WebElement> calendarMonthsWebElements =
		// wd.findElements(calendarMonthsLocatore);

		List<WebElement> calendarMonthsList = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(calendarMonthsLocatore));
		System.out.println(calendarMonthsList.size());
		WebElement monthCalendarWebElement = calendarMonthsList.get(index);
		Thread.sleep(2000);
		return monthCalendarWebElement;
	}

	public static void compareTwoMonthsPrice(String currentMonthPrice, String nextMonthPrice) {
		int currentMonthRSIndex = currentMonthPrice.indexOf("Rs");
		int nextMonthRSIndex = nextMonthPrice.indexOf("Rs");
		System.out.println(currentMonthRSIndex);
		System.out.println(nextMonthRSIndex);

		String currentPrice = currentMonthPrice.substring(currentMonthRSIndex + 2);

		String nextPrice = nextMonthPrice.substring(nextMonthRSIndex + 2);
		int current = Integer.parseInt(currentPrice);
		int next = Integer.parseInt(nextPrice);
		if (current < next) {
			System.out.println("The Lowest price for the two month is" + current);
		} else if (current == next) {
			System.out.println("Price is same for both months! Choose whatever you prefer");
		} else {
		}
		System.out.println("The Lowest price for the two month is" + next);
	}

}
