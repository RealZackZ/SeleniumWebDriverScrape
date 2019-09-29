package web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumScrape1 {
	
/*
Code output

Getting 5 Records
Last Name:	FirstName:	Middle Name(s)
DOE			JOHN		DOE		 
 
Age:				Race:		Sex:		Arrest Date:
900 YEARS OLD		WHITE		MALE		8/28/2100		 
 
Charge Status Docket # Bond Amount
CHARGE - F TRIAL CE-0000000 ANY BOND $100,000,000

It took: 0 day(s), 0h, 0min, 8.97s
Average age = 53
 */


	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("http://s2c.forsythsheriff.org/jailinmates.aspx");

		System.out.println(driver.getTitle());
		
		
		
		//String numberOfRecords2 = driver.findElement(By.id("pager_right")).getText(); 
		//int lastindex = numberOfRecords2.indexOf(" ", 10);
	    //String numberOfRecords3 = numberOfRecords2.substring(10, lastindex);
	    //int realNumberOfRecords = Integer.parseInt(numberOfRecords3); // ^^Get number of inmates records
	    
		int testRecords = 5;
		
		String [][] data = new String[testRecords][6]; // Name, Age, Race, Sex, Arrest Date, Charges
		System.out.println("Getting " + testRecords + " Records");
		long startTime = System.currentTimeMillis();	//Start time for getting records
		
		for(int i = 0; i < testRecords; i++) {
			WebElement loadall = driver.findElement(By.xpath("//*[@id=\"pager_center\"]/table/tbody/tr/td[5]/select/option[5]"));
			loadall.click();	//Click Get all Button

			java.util.concurrent.TimeUnit.MILLISECONDS.sleep(500); //Sleep program so web page has time to load.

			WebElement row = driver.findElement(By.id(String.valueOf(i)));
			row.click();
			
			
			System.out.println("Last Name:	FirstName:	Middle Name(s)");
			data [i][0] = driver.findElement(By.id("mainContent_CenterColumnContent_lblName")).getText();
			//Substring names
			int lastname = data [i][0].indexOf(",");
			int firstName  = data [i][0].indexOf(" ", lastname +3);
			int middleName  = data [i][0].indexOf(" ", firstName);
			if (firstName == -1) {	//For those with no middle name.
				firstName = data[i][0].length();
				data [i][0] =data [i][0].substring(0, lastname) +"		"+ data [i][0].substring(lastname + 2, firstName) 
						+"		"+ "N0 MIDDLE NAME";
			}
			else {
				data [i][0] =data [i][0].substring(0, lastname) +"		"+ data [i][0].substring(lastname + 2, firstName) +
				 "		"+ data [i][0].substring(middleName + 1, data[i][0].length());
				
			}
			data [i][1] = driver.findElement(By.id("mainContent_CenterColumnContent_lblAge")).getText();
			data [i][2] = driver.findElement(By.id("mainContent_CenterColumnContent_lblRace")).getText();
			data [i][3] = driver.findElement(By.id("mainContent_CenterColumnContent_lblSex")).getText();
			data [i][4] = driver.findElement(By.id("mainContent_CenterColumnContent_lblArrestDate")).getText();
			data [i][5] = driver.findElement(By.id("mainContent_CenterColumnContent_dgMainResults")).getText();

			for (int j = 0; j < data[i].length; j++) {
				if (j == 1) {
					System.out.println(" ");
					System.out.println(" ");
					System.out.println("Age:			Race:		Sex:		Arrest Date:");
				}
				if (j == 5) {
					System.out.println(" ");
					System.out.println(" ");
				}
				System.out.print(data[i][j] + "		");

			}
			
			System.out.println("  ");
			System.out.println("  ");
		
			driver.navigate().back();

		
	
		}

		int trimAge = 0;
		String trimAge2 = " ";
		int age = 0;
		int agesAdded = 0;
		
		for (int i = 0; i < data.length; i++) {
			trimAge = data[i][1].indexOf(" ");
			trimAge2 = data[i][1].substring(0, trimAge);
			age = Integer.parseInt(trimAge2);
			agesAdded += age;
		}
		
		System.out.println("It took: " + convertTime(System.currentTimeMillis() - startTime));
		System.out.println("Average age = " + agesAdded/3);
		
		
		driver.close();	
		driver.quit();
		
		
	}
	public static String convertTime(long startTime) {
		int days = 0, hours = 0, minutes = 0, seconds = 0, millis = 0;
			        
		int day = 86400000;
		int hour = 3600000;
		int minute = 60000;
		int second = 1000;
			    
			       
		if(startTime >= day) {
		     days = (int) (startTime / day);
		     millis = (int) (startTime % day);
		} 
		else 
			millis = (int) startTime;
			           
		if(millis >= hour) {
		     hours = millis / hour;
		     millis = millis% hour;
		}
			       
		if(millis >= minute) {
			 minutes = millis / minute;
			 millis = millis % minute;
		}
		
		if(millis >= second) {
			seconds = millis / second;
			millis = millis % second;
		}
		
		String roundMillis = String.valueOf(millis);
		String roundMillis2 = roundMillis.substring(0, 2);

			      
		return (days  + " day(s), " + hours + "h, " + minutes + "min, " + seconds + "." + roundMillis2 + "s");
	}

}
