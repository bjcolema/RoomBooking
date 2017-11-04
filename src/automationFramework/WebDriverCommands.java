package automationFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
public class WebDriverCommands {
	static ArrayList<String> rooms = new ArrayList<String>();
	public static void main(String[] args) throws InterruptedException {
		
		LocalDate ld = LocalDate.now();
		ld = ld.plusDays(3);
		int day = ld.getDayOfMonth();
		System.setProperty("webdriver.gecko.driver", "D:\\Files\\Documents\\Eclipse\\RoomBooking\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		try{
            for(String line : Files.readAllLines(Paths.get("Data/NamesAndEmails.csv"))){
           	String[] arr = line.split(",");
            	bookRoom(arr[1], arr[2], arr[0], arr[3], arr[4], arr[5], "" + day, driver);
            }
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("ERROR");
        }
		driver.close();
		for(String s: rooms) {
			System.out.println(s);
		}
	}
	public static void bookRoom(String firstname, String lastname, String ubid, String time1, String time2, String room, String day, WebDriver driver) throws InterruptedException {
		System.out.println(firstname + " " + lastname  + " " +  ubid + " " + time1 + " " + time2 + " " + room + " " + day);
		try {
		
		String url = "http://booking.lib.buffalo.edu/booking/silverman";
		driver.get(url);
		Thread.sleep(500);
		if(Integer.parseInt(day) <=3) {
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/section/div/div/div[2]/div[1]/div[3]/div/div/div/a[2]/span")).click();
		}
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/section/div/div/div[2]/div[1]/div[3]/div/div/table/tbody/tr/td/a[text()='" + day + "']")).click();
		Thread.sleep(750);
		driver.findElement(By.xpath("//*[contains(@title, 'Room " + room + ", " + time1 + " to ')]")).click();
		driver.findElement(By.xpath("//*[contains(@title, 'Room " + room + ", " + time2 + " to ')]")).click();
		driver.findElement(By.id("rm_tc_cont")).click();
		driver.switchTo().activeElement().sendKeys(firstname, Keys.TAB, lastname, Keys.TAB, ubid + "@buffalo.edu");
		driver.findElement(By.id("s-lc-rm-sub")).click();
		rooms.add("Sucessfully booked rooms for" + ubid + " @ " + time1 + " " + time2);
		}
		catch(NoSuchElementException e) {
			System.out.println(e);
			rooms.add("FAILED TO BOOK ROOMS FOR " + ubid + " @ " + time1 + " " + time2);
		}
	}
}
//JavascriptExecutor js = (JavascriptExecutor)driver; 
//js.executeScript("document.getElementById('s-lc-rm-tg-scroll').scrollLeft += 500", "");
