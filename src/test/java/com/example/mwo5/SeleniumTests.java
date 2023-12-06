package com.example.mwo5;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumTests {

    public WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless"); // Dodaj opcję headless

        driver = new ChromeDriver(chromeOptions);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void shouldFail(){
        Assertions.assertEquals(10,10);
    }

    @Test
    public void shouldGetMoviesList() {
        driver.get("http://localhost:8080/api/v1/movies/all");
        WebElement table = driver.findElement(By.className("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        Assertions.assertNotNull(table);
        Assertions.assertFalse(rows.isEmpty());
    }

    @Test
    public void shouldAddMovie() {
        driver.get("http://localhost:8080/api/v1/movies/all");
        List<WebElement> moviesBefore = driver.findElements(By.tagName("tr"));
        int numberOfMovies = moviesBefore.size();

        driver.get("http://localhost:8080/api/v1/movies/add");
        WebElement titleInput = driver.findElement(By.id("title"));
        titleInput.sendKeys("Nowy film");
        WebElement releaseInput = driver.findElement(By.id("release"));
        releaseInput.sendKeys("2023");
        WebElement submitButton = driver.findElement(By.className("add-movie-button"));
        submitButton.click();

        driver.get("http://localhost:8080/api/v1/movies/all");
        List<WebElement> moviesAfter = driver.findElements(By.tagName("tr"));
        WebElement addedMovieTitle = driver.findElement(By.xpath("//td[contains(text(),'Nowy film')]"));
        WebElement addedMovieRelease = driver.findElement(By.xpath("//td[contains(text(),'2023')]"));

        Assertions.assertEquals(numberOfMovies + 1, moviesAfter.size());
        Assertions.assertTrue(addedMovieTitle.isDisplayed());
        Assertions.assertTrue(addedMovieRelease.isDisplayed());
    }

    @Test
    public void shouldDeleteMovie() {
        driver.get("http://localhost:8080/api/v1/movies/all");
        List<WebElement> moviesBefore = driver.findElements(By.tagName("tr"));
        int numberOfMovies = moviesBefore.size();

        List<WebElement> deleteButtons = driver.findElements(By.className("delete-button"));
        System.out.println(deleteButtons.get(deleteButtons.size()-1));
        WebElement button = deleteButtons.get(deleteButtons.size()-1);
        button.click();

        driver.get("http://localhost:8080/api/v1/movies/all");
        List<WebElement> moviesAfter = driver.findElements(By.tagName("tr"));
        Assertions.assertEquals(numberOfMovies - 1, moviesAfter.size());
    }

    @Test
    public void shouldUpdateMovie() {
        driver.get("http://localhost:8080/api/v1/movies/all");
        List<WebElement> movies = driver.findElements(By.tagName("tr"));
        WebElement lastMovie = movies.get(movies.size()-1);
        String lastMovieDetails = lastMovie.getText();

        WebElement updateButton = lastMovie.findElement(By.className("update-button"));
        updateButton.click();
        WebElement titleInput = driver.findElement(By.id("title"));
        titleInput.clear();
        titleInput.sendKeys("Zaktualizowany tytuł");
        WebElement submitButton = driver.findElement(By.className("update-movie-button"));
        submitButton.click();

        List<WebElement> moviesAfter = driver.findElements(By.tagName("tr"));
        WebElement updatedMovie = moviesAfter.get(moviesAfter.size()-1);
        String updatedMovieDetails = updatedMovie.getText();
        Assertions.assertNotEquals(lastMovieDetails, updatedMovieDetails);
    }
}
