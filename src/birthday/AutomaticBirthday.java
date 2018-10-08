package birthday;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class AutomaticBirthday {

	static WebDriver driver;
	static Actions action;

	String birthdayIcon;
	String logoutArrowIcon;
	String logoutButton;
	String textAreaCheck;
	String textAreaWrite;

	boolean initialize() {

		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		try {
			driver = new ChromeDriver();
		} catch (Exception e) {
			System.out.println(
					"Unable to initialize Chrome Driver. Make sure you have chromedriver.exe in a 'Drivers' folder in current directory. For eg, if your current directory is C:, then place the Chrome Driver in C:\\drivers");
			return false;
		}

		driver.get("https://www.facebook.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		action = new Actions(driver);

		InputStream input = null;
		Properties props = new Properties();

		try {
			input = new FileInputStream("selectors.properties"); // File Not Found
			props.load(input); // IO

			logoutArrowIcon = props.getProperty("Logout_Arrow_ID");
			logoutButton = props.getProperty("Logout_Icon_xpath");
			birthdayIcon = props.getProperty("Birthday_Icon_xpath");
			textAreaCheck = props.getProperty("TextArea_Check_xpath");
			textAreaWrite = props.getProperty("TextArea_Write_xpath");

			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("selectors.properties not found");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		AutomaticBirthday b = new AutomaticBirthday();
		if (b.initialize()) {
			if (b.login()) {
				b.wish();
			}
		//	b.logout();
		}

		//

		// b.logout();
		// System.out.println(System.getProperty("user.dir"));

	}

	private void logout() {
	//	action.click(). perform();

		System.out.println("In Logout");
		System.out.println("Logout ID : "+logoutArrowIcon);
		
		try {
			Thread.sleep(1000);
			action.sendKeys(Keys.ESCAPE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		action.moveToElement(driver.findElement(By.id(logoutArrowIcon))).click().perform();
		
		System.out.println("Clicked on icon");
		WebElement e1 = driver.findElement(By.xpath(logoutButton));
		
		// System.out.println("Disp : "+e1.isDisplayed());
		// System.out.println("Enabl : "+e1.isEnabled());

		action.moveToElement(e1).click().perform();

		System.out.println("Logout Successful");
		
		driver.close();
	}

	void wish() throws InterruptedException {

		System.out.println("In Wish");
		action.sendKeys(Keys.ESCAPE);
		
		// System.out.println("Enable : " +
		// driver.findElement(By.xpath("//a[contains(@ajaxify,'birthday')]
		// ")).isEnabled());
		// System.out.println( "Displayed : " +
		// driver.findElement(By.xpath("//a[contains(@ajaxify,'birthday')]
		// ")).isDisplayed());
		try {
			WebElement icon = null;
		
			try {
			 icon = driver.findElement(By.xpath(birthdayIcon));
			}catch(NoSuchElementException e){
				System.out.println("No Birthdays");
				logout();
				return;
			}
			
			// action = new Actions(driver);
			// action.click().perform();
			action.moveToElement(icon).click().perform();

			// System.out.println("Clicked on Birthday icon");

			// WebDriverWait wait = new WebDriverWait(driver, 100);

			// System.out.println("Before Wait");
			Thread.sleep(5000);

			// System.out.println("After Wait");

			List<WebElement> textAreas = driver.findElements(By.xpath(textAreaCheck));

			System.out.println("Size : "+textAreas.size());
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				System.out.print(i + " ");
				if (textAreas.get(0).isDisplayed()) {
					break;
				}
			}
			List<WebElement> postButtons = driver.findElements(By.xpath(textAreaWrite));

			// System.out.println(textAreas);
			// System.out.println(postButtons);

			// System.out.println("Size : " + textAreas.size());

			for (int i = 0; i < textAreas.size() * 4; i++) {
				action.sendKeys(Keys.TAB).perform();
				// System.out.print("Tab pressed : " + i + "\t");
			}

			// System.out.println("Here");
			for (int i = 0; i < textAreas.size(); i++) {
				WebElement textArea = textAreas.get(i);
				WebElement postButton = postButtons.get(i);

				String name = textArea.getAttribute("title").split(" ")[2].split("'")[0];
				System.out.println(name);

				action.click(textArea);
				action.sendKeys(textArea, "Hey " + name + "! a very happy birthday to you :) ").perform();

				action.moveToElement(postButton).click().perform();
				action.pause(3000).perform();
				// Thread.sleep(3);
			}
			System.out.println("Wish successful");
			logout();
		} catch (NoSuchElementException ne) {
			System.out.println("Element not found\n");
			ne.printStackTrace();

		}

	}

	private Login loadCredentials() {
		InputStream input = null;
		Properties props = new Properties();
		try {

			input = new FileInputStream("credentials.properties");
			props.load(input);

			String userName = props.getProperty("Login_ID").trim();
			// System.out.println(userName);
			String password = props.getProperty("Password").trim();
			// System.out.println(password);
			Login ob = new Login(userName, password, "email", "pass");
			return ob;
		} catch (NullPointerException e) {
			System.out.println("Error while locating credentials.properties");
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("credentials.properties file not found");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while locating credentials.properties");
			e.printStackTrace();
			return null;
		}
	}

	private boolean login() {

		try {
			Login ob = loadCredentials();
			if (ob != null) {
				ob.login(driver);
				return true;
			} else {
				System.out.println(
						"There was an error in retrieving your credentials. Please check credentials.properties");
				return false;
			}
		} catch (InterruptedException e) {

			e.printStackTrace();
			return false;
		}
	}

}
