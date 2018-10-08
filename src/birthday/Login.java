package birthday;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Login {

	String userName;
	String password;
	String idUserNameField;
	String idPasswordField;
	WebDriver driver;

	public Login(String userName, String password, String idUserNameField, String idPasswordField) {

		this.userName = userName;
		this.password = password;
		this.idUserNameField = idUserNameField;
		this.idPasswordField = idPasswordField;
	}

	void setUserName(String name) {
		userName = name;
	}

	void setPassword(String pwd) {
		password = pwd;
	}

	void login(WebDriver driver1) throws InterruptedException {
		driver = driver1;
		if (checkIfLoggedIn(idUserNameField)) {
			System.out.println("Already Logged In");
		} else {
			System.out.println("Going in Login New");
			if (loginNew()) {
				System.out.println("Login Successful");
			} else {
				System.out.println("Login Not Successful");
			}
		}

	}

	private boolean loginNew() throws InterruptedException {
		// TODO Auto-generated method stub
		driver.findElement(By.id(idUserNameField)).sendKeys(userName);
		driver.findElement(By.id(idPasswordField)).sendKeys(password);
		driver.findElement(By.id(idPasswordField)).sendKeys(Keys.ENTER);
		System.out.println("Enter Pressed");
		Actions action = new Actions(driver);
		Thread.sleep(5);
		action.click().perform();
		
		
		if (driver.findElements(By.xpath("//a[@href = 'https://www.facebook.com/?ref=logo']")).size() == 0)
			return false;

		return true;

	}

	private boolean checkIfLoggedIn(String idUserNameField) {

		if (driver.findElements(By.id(idUserNameField)).size() > 0)
			return false;

		return true;
	}

}
