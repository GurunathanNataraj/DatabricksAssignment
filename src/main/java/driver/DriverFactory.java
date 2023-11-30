package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver initDriver(String browser) {
        if(browser.equals("chrome")) {
            //WebDriverManager.chromedriver().driverVersion("119.0.6045.160").setup();
            System.setProperty("webdriver.chrome.driver","src\\test\\resources\\drivers\\chromedriver.exe");
            tlDriver.set(new ChromeDriver());
        }else if(browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            tlDriver.set(new FirefoxDriver());
        }else {
            System.out.println("Please pass the correct browser value : "+browser);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }
}
