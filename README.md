
---

# ✈️ Yatra Flight Fare Comparison Script

This Java-based Selenium automation script scrapes and compares the **lowest flight fares** for the current and next month on [Yatra.com](https://www.yatra.com).

## 📌 Features

- Automatically opens Yatra's flight booking calendar  
- Closes promotional pop-up if displayed  
- Extracts flight fare data from calendar  
- Identifies the **lowest fare** for both current and next month  
- Compares and prints the better fare with a friendly message  

## 🛠 Tech Stack

- Java 8+  
- Selenium WebDriver  
- ChromeDriver  
- Maven (optional but recommended for dependency management)
  
---

## ⚙️ Setup Instructions

1. **Clone this repository:**
   ```bash
   git clone https://github.com/Aayush-Mishraa/Yatra-Calendar-Automation-Solution.git
   cd Yatra-Calendar-Automation-Solution
   ```

2. **Install dependencies:**
   - If using Maven, make sure your `pom.xml` includes:
     ```xml
     <dependency>
       <groupId>org.seleniumhq.selenium</groupId>
       <artifactId>selenium-java</artifactId>
       <version>4.0.0</version>
     </dependency>
     ```
   - Download [ChromeDriver](https://chromedriver.chromium.org/downloads) if not already present. Selenium 4+ automatically manages driver binaries, so manual path setup is **not required**.

3. **Configure ChromeDriver:**
   - **No manual path configuration needed!**  
     Selenium Manager will automatically find and use the correct ChromeDriver version matching your installed Chrome browser.

---

## 🚀 Usage

1. **Run the Java program:**
   - Open your terminal and navigate to `src/main/java/com/yatra/automation/`.
   - Compile and run:
     ```bash
     javac YatraAutomationScript3.java
     java com.yatra.automation.YatraAutomationScript3
     ```
   - Or use your IDE to run `YatraAutomationScript3.java`.

2. **View the results:**
   - The script will print the lowest fares for the current and next month, and indicate which is cheaper.

## 📂 Project Structure

```
src/
└── main/
    └── java/
        └── com/
            └── yatra/
                └── automation/
                    └── YatraAutomationScript3.java
```

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!  
Feel free to check the [issues page](https://github.com/Aayush-Mishraa/Yatra-Calendar-Automation-Solution/issues) if you want to contribute.

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---
Tip:
For troubleshooting, ensure your Chrome and ChromeDriver versions match. If you encounter element not found errors, check if Yatra.com has updated its UI or calendar widget.
