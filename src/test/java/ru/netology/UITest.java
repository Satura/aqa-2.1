package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class UITest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp(){
        /*String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
        }
        if (os.contains("nux")) {
            System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
        }
        if (os.contains("nix")) {
            System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
        }
        if (os.contains("ntu")) {
            System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
        }
        //System.out.println (System.getProperty("os.name"));
        //System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        driver = new FirefoxDriver(firefoxOptions);*/

        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless", "--disable-gpu"));
        System.setProperty("webdriver.chrome.driver", "artifacts/chromedriver");

    }

    @AfterAll
    static void tearDown() {
        driver.quit();
        driver = null;
    }

    // Задача #1

    @Test
    void shouldTestV1() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Иванова Анна");
        driver.findElement(By.name("phone")).sendKeys("+71234567890");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",actual);
    }

    // Задача #2

    @Test
    void shouldTestWrongName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Popov Oleg");
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void shouldTestEmptyName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.tagName("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void shouldTestWrongPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Попов Олег");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("87");
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void shouldTestNotAgreement() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Попов Олег");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79012345678");
        driver.findElement(By.tagName("button")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector(".input_invalid>.checkbox__box")).isDisplayed());
    }
}
