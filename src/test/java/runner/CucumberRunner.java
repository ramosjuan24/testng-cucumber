package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @autor   Ing. Juan Carlos Ramos
 * @version v1.0
 * @serialData 000000000134
 */

@CucumberOptions(strict = true, monochrome = true, features = "src/test/resources/feature", glue = "stepdefinition", tags = {"@TestngScenario"})
public class CucumberRunner extends AbstractTestNGCucumberTests {

    public static WebDriver driver = null;
    public static String urlData;
    public String carga = null;
    private int timeout;
    public static Properties config = null;
    public String urlDriverChrome = null;
    public String urlDriverLinux = null;
    public String urlDriverMac = null;
    public static String baseUrl = null;
    public static String rutaEvidencia;
    public static String fechaEjecucion2;
    public static String fechaEjecucion;
    public static String timeout_Element;
    private final static Logger LOGGER = Logger.getLogger("utilities.Log");
    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    public void setUp() throws Exception {
        openBrowser();
        maximizeWindow();
        implicitWait(30);
        deleteAllCookies();
        setEnv();
    }

    public void leerProperties() {

        try {
            config = new Properties();
            DateTime timeStamp = new DateTime();
            fechaEjecucion = timeStamp.toString("yyyyMMdd-HHmmss");
            fechaEjecucion2 = timeStamp.toString("yyyyMMdd");
            config.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            urlDriverChrome = config.getProperty("urlChromeDriverWindows");
            urlDriverLinux = config.getProperty("urlChromeDriverLinux");
            urlDriverMac = config.getProperty("urlChromeDriverMac");
            carga = config.getProperty("TIME_OUT_CARGA_PAGINA");
            rutaEvidencia = config.getProperty("TEST_NAME");
            urlData = config.getProperty("URLDATA");
            timeout_Element = config.getProperty("TIMEOUT_POR_ELEMENTO");

        } catch (FileNotFoundException e) {
            System.out.println("Error, El archivo no exite");
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("Error, No se puede leer el archivo");
        }
    }

    public void configureDriverPath() throws IOException {
        if(System.getProperty("os.name").startsWith("Linux")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/geckodriver";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            //String chromeDriverPath = System.getProperty("user.dir") + urlDriverLinux;
            String chromeDriverPath = urlDriverLinux;
            LOGGER.info("Ruta chromedriver linux: " + urlDriverLinux);
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
        if(System.getProperty("os.name").startsWith("Mac")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/geckodriver";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + urlDriverMac;
            LOGGER.info("Ruta chromedriver Mac: " + urlDriverMac);
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
        if(System.getProperty("os.name").startsWith("Windows")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + urlDriverChrome;
            LOGGER.info("Ruta chromedriver Windows: " + urlDriverChrome);
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
    }

    public void openBrowser() throws Exception {
        // loads the config options
        leerProperties();
        // configures the driver path
        configureDriverPath();

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

        if (config.getProperty("browserType").equals("firefox")) {
            driver = new FirefoxDriver();
        } else if (config.getProperty("browserType").equals("chrome")) {

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("password_manager_enabled", false);

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);
            options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
            //options.addArguments("--headless");
            options.addArguments("--window-size=1366,768");
            options.addArguments("disable-infobars");
            options.addArguments("--incognito");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--allow-insecure-localhost");
            driver = new ChromeDriver(options);
        }
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void implicitWait(int time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public void explicitWait(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 3000);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public WebElement waitForElement(By locator) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, getTimeout());

        try {

            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        } catch (ElementNotVisibleException e) {
            LOGGER.warn("No se interactua con el elemento de la pagina " + e.getMessage());
            Assert.fail("Elemento no encontrado" + e.getMessage());
            throw e;
        }
    }

    public void pageLoad(int time) {
        driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void setEnv() throws Exception {
        leerProperties();
        baseUrl = config.getProperty("siteUrl");
    }

    private int getTimeout() {

        if (timeout == 0) {
            try {
                timeout = Integer.parseInt(timeout_Element);;
                if (timeout == 0) {
                    timeout = 5;
                }
            } catch (Exception error) {
                timeout = 5;
            }
        }
        return timeout;
    }

    /**
     * Método para capturar evidencia de flujos automatizados
     * recibe el @nombre de la imagen
     */

    public void sacarPantallazo(String nombre) throws Exception {

        try {

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            //File screenshot = driver.getScreenshotAs(OutputType.FILE);

            File directory = new File("build/reports/");

            if (!directory.exists()) {
                FileUtils.forceMkdir(directory);

            }
            DateTime timeStamp = new DateTime();
            //Se guardan la carpeta screenshots dentro de carpeta definida en TEST_NAME
            directory = new File(directory.getPath()  + "/" + rutaEvidencia + "/" +  fechaEjecucion2);

            if (!directory.exists()) {
                FileUtils.forceMkdir(directory);
            }
            directory = new File(directory.getPath()  + "/" + rutaEvidencia + "/" +  fechaEjecucion + "/screenshots");
            if (!directory.exists()) {
                FileUtils.forceMkdir(directory);
            }
            File filename = new File(directory.getPath() + "/" + timeStamp.toString("yyyyMMdd-HHmmss") + "-" + nombre + ".png");
            FileUtils.copyFile(screenshot, filename);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception while saving the file " + e);
        }
    }

    //@AfterSuite(alwaysRun = true)
    @AfterClass
    public void quit() throws IOException, InterruptedException {
        driver.quit();
    }

}
