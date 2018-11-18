package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {

    public static void main(String[] args) {

        // onnistunut kirjautuminen

        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");

        sleep(2);

        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        sleep(2);

        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("login"));

        sleep(2);
        element.submit();

        sleep(3);

        // uloskirjautuminen
        clickLinkWithText("logout", driver);
        sleep(2);

        // epäonnistunut kirjautuminen: oikea käyttäjätunnus, väärä salasana
        clickLinkWithText("login", driver);
        sleep(2);
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("wrongwrong");
        element = driver.findElement(By.name("login"));
        sleep(2);
        element.submit();
        sleep(3);

        // epäonnistunut kirjautuminen: ei-olemassaoleva käyttäjätunnus
        element = driver.findElement(By.name("username"));
        element.sendKeys("olematon");
        element = driver.findElement(By.name("password"));
        element.sendKeys("somepword");
        element = driver.findElement(By.name("login"));
        sleep(2);
        element.submit();
        sleep(3);

        // uuden käyttäjätunnuksen luominen
        clickLinkWithText("back to home", driver);
        sleep(2);
        clickLinkWithText("register new user", driver);
        sleep(2);
        element = driver.findElement(By.name("username"));
        element.sendKeys("arto" + new Random().nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys("artoPWarto5");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("artoPWarto5");
        element = driver.findElement(By.name("signup"));
        sleep(2);
        element.submit();
        sleep(3);

        // uuden käyttäjätunnuksen luomisen jälkeen tapahtuva ulkoskirjautuminen sovelluksesta
        clickLinkWithText("continue to application mainpage", driver);
        sleep(2);
        clickLinkWithText("logout", driver);
        sleep(2);

        driver.quit();
    }

    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }

    private static void clickLinkWithText(String text, WebDriver driver) {
        int trials = 0;
        while( trials++<5 ) {
            try{
                WebElement element = driver.findElement(By.linkText(text));
                element.click();
                break;
            } catch(Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }
}
