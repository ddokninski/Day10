package org.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class SendContactMsgTest extends TestDataProvider {

    @ParameterizedTest
    @MethodSource("testDataProviderForSiiPage")
    @DisplayName("Testing sii.pl title")
    @Tag("regression")
    @Tag("sii.pl")
    void shouldValidateCorrectTitleSii(String url, String expectedMsg) throws InterruptedException {
        checkPageTitle(url, expectedMsg);
    }

    private void checkPageTitle(String url, String expectedMsg) throws InterruptedException {
        //GIVEN
        driver.get(url);
        log.info("url application = " + url);
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector("a[class='sii-a-button -small -secondary js-contact-button']")).click();
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Jan");
        driver.findElement(By.cssSelector("input[name='surname']")).sendKeys("Kowalski");
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys("mailtestowy@wp.pl");
        driver.findElement(By.cssSelector("div[class='sii-m-date-selector__custom-select']")).click();
        driver.findElement(By.xpath("//a[.='Szukam pracy']")).click();
        driver.findElement(By.cssSelector("#message")).sendKeys("wiadomość");
        WebElement submitButton = driver.findElement(By.cssSelector("#submit_btn"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='sii-m-message']")));

        //WHEN
        String successMsg = driver.findElement(By.cssSelector("div[class='sii-m-message-success  '] > h4")).getText();
        log.info("successMsg = " + successMsg);

        //THEN
        assertThat(successMsg).isEqualTo(expectedMsg);
        log.info("assert done");
    }
}
