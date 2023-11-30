package hooks;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

import java.util.Properties;

public class AppHooks {
   private ConfigReader configReader;
   private Properties properties;
   DriverFactory driverFactory;
   WebDriver driver;



    @Before(order = 0)
    public void getProperty()
    {
        configReader = new ConfigReader();
        properties = configReader.initProp();
    }

    @Before(order = 1)
    public void launchBrowser() {
        String browserName = properties.getProperty("browser");
        driverFactory = new DriverFactory();
        driver = driverFactory.initDriver(browserName);
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if(scenario.isFailed()) {
            String screenShotName = scenario.getName().replace(" ","_");
            byte[] source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(source,"image/png",screenShotName);
        }

    }

    @After(order = 0)
    public void quitBrowser() {
        driver.quit();
    }


}
