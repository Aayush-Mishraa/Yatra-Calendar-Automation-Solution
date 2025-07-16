package com.yatra.automation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YatraAutomationScript {

    public static void main(String[] args) throws InterruptedException {
        // Set Chrome options to disable browser notifications
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");

        // Initialize WebDriver with Chrome options
        WebDriver wd = new ChromeDriver(chromeOptions);

        // Create WebDriverWait to manage explicit waits (20 seconds)
        WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(20));

        // Open Yatra website and maximize the window
        wd.get("https://www.yatra.com");
        wd.manage().window().maximize();

        // Handle and close pop-up if it appears
        closePopUp(wait);

        // Click the departure date field to open the calendar
        clickOnDepatureDate(wait);

        // Access current and next month calendar blocks
        WebElement currentMonthWebElement = selectTheMonthFromCalendar(wait, 0); // Index 0 = current month
        WebElement nextMonthWebElement = selectTheMonthFromCalendar(wait, 1);    // Index 1 = next month

        // Wait for calendar contents to fully load (optional but useful)
        Thread.sleep(3000);

        // Extract lowest prices for current and next month
        String lowestPriceForCurrentMonth = getMeLowestPrice(currentMonthWebElement);
        String lowestPriceForNextMonth = getMeLowestPrice(nextMonthWebElement);

        // Display both price results
        System.out.println(lowestPriceForCurrentMonth);
        System.out.println(lowestPriceForNextMonth);

        // Compare and print which month offers a better price
        compareTwoMonthsPrices(lowestPriceForCurrentMonth, lowestPriceForNextMonth);
    }

    // Clicks on the departure date input field to open date picker
    public static void clickOnDepatureDate(WebDriverWait wait) {
        By departureDateButtonLocator = By.xpath("//div[@aria-label=\"Departure Date inputbox\" and @role=\"button\"]");
        WebElement departureDateButton = wait.until(ExpectedConditions.elementToBeClickable(departureDateButtonLocator));
        departureDateButton.click();
    }

    // Closes pop-up if visible; skips if not present
    public static void closePopUp(WebDriverWait wait) {
        By popUpLocator = By.xpath("//div[contains(@class, \"style_popup\")][1]");
        try {
            WebElement popUpElement = wait.until(ExpectedConditions.visibilityOfElementLocated(popUpLocator));
            WebElement crossButton = popUpElement.findElement(By.xpath(".//img[@alt=\"cross\"]"));
            crossButton.click();
        } catch (TimeoutException e) {
            System.out.println("Pop-up not shown on the screen!");
        }
    }

    // Scans all price values for a given calendar month and returns the lowest
    public static String getMeLowestPrice(WebElement monthWebElement) {
        By priceLocator = By.xpath(".//span[contains(@class,\"custom-day-content\")]");
        List<WebElement> priceElements = monthWebElement.findElements(priceLocator);

        int lowestPrice = Integer.MAX_VALUE;
        WebElement priceElement = null;

        // Loop through all visible prices to find the lowest
        for (WebElement price : priceElements) {
            String priceString = price.getText();
            if (!priceString.isEmpty()) {
                // Remove currency symbol and commas
                priceString = priceString.replace("₹", "").replace(",", "");
                int priceInt = Integer.parseInt(priceString);
                if (priceInt < lowestPrice) {
                    lowestPrice = priceInt;
                    priceElement = price;
                }
            }
        }

        // Get the associated date for the lowest price using relative XPath
        WebElement dateElement = priceElement.findElement(By.xpath(".//../.."));
        String result = dateElement.getAttribute("aria-label") + " --- Price is Rs" + lowestPrice;
        return result;
    }

    // Selects the calendar month container by index (0 for current, 1 for next)
    public static WebElement selectTheMonthFromCalendar(WebDriverWait wait, int index) {
        By calendarMonthsLocator = By.xpath("//div[@class=\"react-datepicker__month-container\"]");
        List<WebElement> calendarMonthsList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(calendarMonthsLocator));
        System.out.println("Calendar containers found: " + calendarMonthsList.size());
        return calendarMonthsList.get(index);
    }

    // Compares two extracted price strings and prints which month is cheaper
    public static void compareTwoMonthsPrices(String currentMonthPrice, String nextMonthPrice) {
        int currentMonthRSIndex = currentMonthPrice.indexOf("Rs");
        int nextMonthRSIndex = nextMonthPrice.indexOf("Rs");

        String currentPriceStr = currentMonthPrice.substring(currentMonthRSIndex + 2).trim();
        String nextPriceStr = nextMonthPrice.substring(nextMonthRSIndex + 2).trim();

        int current = Integer.parseInt(currentPriceStr);
        int next = Integer.parseInt(nextPriceStr);

        if (current < next) {
            System.out.println("Current month has the lowest price: Rs" + current);
        } else if (current == next) {
            System.out.println("Both months have the same lowest price: Rs" + current + ". Choose either!");
        } else {
            System.out.println("Next month has the lowest price: Rs" + next);
            
        }
    }
}