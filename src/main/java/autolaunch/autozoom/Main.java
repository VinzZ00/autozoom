package autolaunch.autozoom;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
	Scanner sc = new Scanner(System.in);
	int x, y;
	String choose;
	
	

	
	public Main() throws JSONException, IOException, InterruptedException, AWTException {
		do {
			System.out.println(
					"Hello there Welcome, each time you perform a autologgin you will have input email and password of your Binusmaya");
			System.out.println("please don't move your mouse while we are in the autologin process");
			System.out.println("1. Start");
			System.out.println("2. Exit");
			System.out.print(" >> ");
			choose = sc.nextLine();
			
			switch (choose) {
			case "1":
//				WebDriver driver = null;
					startautolog();
				
				break;

			case "2":
				System.exit(0);
				break;
			}
		} while (!choose.equalsIgnoreCase("2"));

	}

	public static void main(String[] args) throws JSONException, IOException, InterruptedException, AWTException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\Workspace\\autozoom\\chrome\\chromedriver.exe");
		new Main();
	}
	
	public static void moveMouse(int x, int y, int maxTimes, Robot screenWin) {
	    for(int count = 0;(MouseInfo.getPointerInfo().getLocation().getX() != x || 
	            MouseInfo.getPointerInfo().getLocation().getY() != y) &&
	            count < maxTimes; count++) {
	        screenWin.mouseMove(x, y);
	    }
	}
	
	private void buttonfinder() throws InterruptedException, AWTException {
		
		Robot bot = new Robot();
		// TODO Auto-generated method stub
		WebDriver btnfind = new ChromeDriver();
    	btnfind.manage().window().maximize();
    	btnfind.get("https://zoom.us/test");
    	btnfind.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	
    	WebElement joinbtn = btnfind.findElement(By.cssSelector("#btnJoinTest"));
    	joinbtn.click();
    	
    	do {
			Thread.sleep(1000);
		} while (btnfind.getCurrentUrl().equals("https://zoom.us/test"));
    	
    	String conf;
		do {
			System.out.println("Trying to find the mouse location in 10 Seconds");
			System.out.println("Go hover your mouse under the join button, 10 second start from now");
			
			for (int i = 1; i < 11; i++) {
				System.out.println(i);
				Thread.sleep(1000);
			}
			
			java.awt.Point mousepinpoint;
				PointerInfo mouseinf = MouseInfo.getPointerInfo();
				mousepinpoint = mouseinf.getLocation();
			
			x = (int) mousepinpoint.getX();
			y = (int) mousepinpoint.getY();
			
			System.out.println("Stop, Is that the right position?");
			System.out.println(String.valueOf(x)+ " " + String.valueOf(y));
			conf = sc.nextLine();

			
	
			
			
		} while (!(conf.equalsIgnoreCase("y")));
		btnfind.quit();
	}
	
	

	private void startautolog() throws InterruptedException {
		
		
		
		
		System.out.println("Masukan Email anda (without @binus.ac.id)");
		String email = sc.nextLine();
		
		System.out.println("Masukan Password anda");
		String password = sc.nextLine();
		
		// TODO Auto-generated method stub
		
		//construct webdriver
		WebDriver driver = new ChromeDriver();
    
		//generate robot
		Robot bot = null;
		try {
			bot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("pembuatan robot");
		}
		
    	//Open Bimay
    	driver.get("https://myclass.apps.binus.ac.id/");
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	driver.manage().window().maximize();
    	((JavascriptExecutor)driver).executeScript("window.open()");
    	ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
    	
    	
    	
    	//input data
    	WebElement emailinput= driver.findElement(By.cssSelector("#Username"));
    	emailinput.sendKeys(email);
    	
    	WebElement passwordinput = driver.findElement(By.id("Password"));
    	passwordinput.sendKeys(password);
    	
    	WebElement submitbtn = driver.findElement(By.id("btnSubmit"));
    	submitbtn.click();
    
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	
    	//Open API and Read JSON file
			driver.switchTo().window(tabs.get(1));
			driver.get("https://myclass.apps.binus.ac.id/home/getViconSchedule");
    	
    	WebElement JSONFile;
		JSONFile = driver.findElement(By.cssSelector("body > pre"));
    	String JSONs = JSONFile.getText();
    	JSONArray JSONarr = new JSONArray(JSONs);
    	
    	
    	  
    	
    	    	
    	System.out.println("there Are " + JSONarr.length() + " list of class you will attend");
    	System.out.println("");
    	    	
    	
    	driver.quit();
//    	System.out.println("Today's date " + now);
    	for (int i = 0; i < JSONarr.length(); i++) {
        	//computer date and time
    		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));  
        	String nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
        	
    		//now's time
        	int nowhv = Integer.valueOf(nowv.substring(0,2));
        	int nowmv = Integer.valueOf(nowv.substring(3,5));
        	int nowsv = Integer.valueOf(nowv.substring(6,8));
        	
        	//binusmaya's time
    		String date = (String) JSONarr.getJSONObject(i).get("DisplayStartDate");
    		String sttime = (String) JSONarr.getJSONObject(i).get("StartTime");
    		int hour = Integer.valueOf(sttime.substring(0,2));
    		int minute = Integer.valueOf(sttime.substring(3,5));
    		int second = Integer.valueOf(sttime.substring(6,8));
    		String URL = (String) JSONarr.getJSONObject(i).get("MeetingUrl");
    		
    		System.out.println("class' date " + date);
    		System.out.println("your class' time " + sttime);

    		if (URL.equals("-")) {
    			System.out.println("==========================");
    			System.out.println();
				continue;
			}
    		
    		int minuteval = 0;
    		int hourval;

    		if (date.equalsIgnoreCase(now)) {
    			System.out.println("Your class date's => " + date );
    			System.out.println("");
    			
    			nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
            	nowhv = Integer.valueOf(nowv.substring(0,2));
            	nowmv = Integer.valueOf(nowv.substring(3,5));
            	nowsv = Integer.valueOf(nowv.substring(6,8));
            	
            	hourval = hour - nowhv;
            	
            
            	if (hourval == 0) {
					minuteval = minute - nowmv;
					System.out.println("your class will start in " + minuteval + " minutes");
					System.out.println("");
					
					if (minuteval > 5 ) {
						do {
							
						System.out.println("we will loop for 3 minutes untill the time comes");
						System.out.println("");
						System.out.println(minuteval + " Minutes left");
						System.out.println("");
						TimeUnit.MINUTES.sleep(3);
						
						nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
	    				
	    	        	nowmv = Integer.valueOf(nowv.substring(3,5));
	    	        	minuteval = minute - nowmv;
						
						} while (minuteval > 5);
						
						WebDriver log = new ChromeDriver();
						log.navigate().to(URL);
						
						bot.keyPress(KeyEvent.VK_LEFT);
						bot.delay(100);
						bot.keyRelease(KeyEvent.VK_LEFT);
						
						bot.keyPress(KeyEvent.VK_ENTER);
						bot.delay(100);
						bot.keyRelease(KeyEvent.VK_ENTER);
						
			    		log.quit();
			    		
					} else if (minuteval < 5 && minuteval > 0) {
						WebDriver log = new ChromeDriver();
						log.navigate().to(URL);
						
						bot.keyPress(KeyEvent.VK_LEFT);
						bot.delay(100);
						bot.keyRelease(KeyEvent.VK_LEFT);
						
						bot.keyPress(KeyEvent.VK_ENTER);
						bot.delay(100);
						bot.keyRelease(KeyEvent.VK_ENTER);
						
			    		log.quit();
					} else {
						System.out.println("The class has been started");
						System.out.println("");
						continue;
					}
					
            	} else if (hourval > 0 && hourval != 0) {
					do {
						System.out.println(hourval);
						System.out.println("We will loop for amount of time (5 minutes)");
						System.out.println("");
						TimeUnit.MINUTES.sleep(5);
						nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
						nowhv = Integer.valueOf(nowv.substring(0, 2));
						hourval = hour - nowhv;
					} while (hourval > 0 && hourval != 0);
					
					nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
					nowmv = Integer.valueOf(nowv.substring(3,5));
					minuteval = minute - nowmv;
					
					if (minuteval > 5 ) {
						do {
							
						System.out.println("we will loop for 3 minutes untill the time comes");
						System.out.println("");
						System.out.println(minuteval + " Minutes left");
						System.out.println("");
						TimeUnit.MINUTES.sleep(3);
						
						nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
	    	        	nowmv = Integer.valueOf(nowv.substring(3,5));
	    	        	minuteval = minute - nowmv;
						
						} while (minuteval > 5);
						
						WebDriver log = new ChromeDriver();
						log.navigate().to(URL);
						
						bot.keyPress(KeyEvent.VK_LEFT);
						bot.delay(100);
						bot.keyRelease(KeyEvent.VK_LEFT);
						
						bot.keyPress(KeyEvent.VK_ENTER);
						bot.delay(100);
						bot.keyRelease(KeyEvent.VK_ENTER);
						
			    		log.quit();
			    		
			    			
					} else if (minuteval < 5 && minuteval > 0) {
						WebDriver log = new ChromeDriver();
						log.navigate().to(URL);
						
						bot.keyPress(KeyEvent.VK_LEFT);
						bot.delay(100);
						bot.keyRelease(KeyEvent.VK_LEFT);
						
						bot.keyPress(KeyEvent.VK_ENTER);
						bot.delay(100);
						bot.keyRelease(KeyEvent.VK_ENTER);
						
			    		log.quit();
					} else {
						System.out.println("The class has been Started");
						System.out.println("");
						continue;
					}
				} else if (hourval < 0) {
    				System.out.println("the Class has been passed");
    				System.out.println("=====================================");
    				System.out.println("\n");
    				continue;
    			}
    		} else if (!date.equalsIgnoreCase(now)) {
				System.out.println("horray You don't have class for today!!!");
				System.out.println("");
				System.out.println("");
				
				break;
			}
    		
    	}
            	
            	
            	
            	
					
    			
    		
    		
//    		if (date.equalsIgnoreCase(now)) {
//    			System.out.println("Today's date " + now);
//    			System.out.println("Your Class date => " + date);
//    			
//    			nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
//            	nowhv = Integer.valueOf(nowv.substring(0,2));
//            	nowmv = Integer.valueOf(nowv.substring(3,5));
//            	nowsv = Integer.valueOf(nowv.substring(6,8));
//            	
//            	hourval = hour - nowhv;
//    			if (hourval == 0) {
//					minuteval = minute - nowmv;
//					System.out.println(minuteval);
//					if (minuteval <= 5) {
//						System.out.println("kelas ke " +  i);
//						System.out.println("=====================\n");
//						WebDriver autologdrive = new ChromeDriver();
//						autologdrive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//				    	autologdrive.manage().window().maximize();
//				    	((JavascriptExecutor)autologdrive).executeScript("window.open()");
////				    	((JavascriptExecutor)autologdrive).executeScript("window.open()");
////				    	ArrayList<String> tabss = new ArrayList<String>(autologdrive.getWindowHandles());
////						autologdrive.switchTo().window(tabss.get(2));
//						autologdrive.navigate().to(URL);
//						
//			    		
//						Thread.sleep(3000);
//						
//						bot.keyPress(KeyEvent.VK_LEFT);
//						bot.delay(100);
//						bot.keyRelease(KeyEvent.VK_LEFT);
//						
//						bot.keyPress(KeyEvent.VK_ENTER);
//						bot.delay(100);
//						bot.keyRelease(KeyEvent.VK_ENTER);
//						
//			    		autologdrive.quit();
//			    		continue;
//					}
//					else if (minuteval > 5) {
//						do {
//							System.out.println("Sorry the class haven't come yet, we will loop 5 minutes for each loop until the time has come");
//							System.out.println(minuteval);
//							TimeUnit.MINUTES.sleep(5);
//							
//		    	        	nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
//		    				
//		    	        	nowmv = Integer.valueOf(nowv.substring(3,5));
//		    	        	minuteval = minute - nowmv;
//		    	        					
//							} while (minuteval > 5);
//						
//						System.out.println("kelas ke " +  i);
//						System.out.println("=====================\n");
//						WebDriver autologdrive = new ChromeDriver();
//						autologdrive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//				    	autologdrive.manage().window().maximize();
////				    	((JavascriptExecutor)autologdrive).executeScript("window.open()");
////				    	((JavascriptExecutor)autologdrive).executeScript("window.open()");
////				    	ArrayList<String> tabss = new ArrayList<String>(autologdrive.getWindowHandles());
////						autologdrive.switchTo().window(tabss.get(2));
//						autologdrive.navigate().to(URL);
//						
//						Thread.sleep(3000);
//						
//						bot.keyPress(KeyEvent.VK_LEFT);
//						bot.delay(100);
//						bot.keyRelease(KeyEvent.VK_LEFT);
//						
//						bot.keyPress(KeyEvent.VK_ENTER);
//						bot.delay(100);
//						bot.keyRelease(KeyEvent.VK_ENTER);
//						
//					} else if (minuteval < 0) {
//						System.out.println("The class has started");
//						continue;
//					}
//				}
//    			
//    			else if (hourval != 0 && hourval > 0) {
//					do {
//						System.out.println("We will loop for 3 minutes in one loop");
//						nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
//						nowhv = Integer.valueOf(nowv.substring(0,2));
//						TimeUnit.MINUTES.sleep(3);
//						System.out.println(hourval);
//						
//						nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
//						nowhv = Integer.valueOf(nowv.substring(0,2));
//						hourval = hour - nowhv;
//					} while (hourval >  0);
//					
//					nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
//					nowmv = Integer.valueOf(nowv.substring(3,5));
//					
//					minuteval = minute - nowmv;
//					
//					if (minuteval <= 5 && minuteval > 0) {
//						System.out.println("kelas ke " +  i);
//						System.out.println("=====================\n");
//						WebDriver autologdrive = new ChromeDriver();
//						autologdrive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//				    	autologdrive.manage().window().maximize();
////				    	((JavascriptExecutor)autologdrive).executeScript("window.open()");
////				    	((JavascriptExecutor)autologdrive).executeScript("window.open()");
////				    	ArrayList<String> tabss = new ArrayList<String>(autologdrive.getWindowHandles());
////						autologdrive.switchTo().window(tabss.get(2));
//						autologdrive.navigate().to(URL);
//			    		
//						Thread.sleep(3000);
//						
//						bot.keyPress(KeyEvent.VK_LEFT);
//						bot.delay(100);
//						bot.keyRelease(KeyEvent.VK_LEFT);
//						
//						bot.keyPress(KeyEvent.VK_ENTER);
//						bot.delay(100);
//						bot.keyRelease(KeyEvent.VK_ENTER);
//						
//			    		autologdrive.quit();
//					} 
//					
//					else if (minuteval > 5) {
//						do {
//							System.out.println("Sorry the class haven't come yet, we will loop 5 minutes for each loop until the time has come");
//							System.out.println(minuteval);
//							TimeUnit.MINUTES.sleep(5);
//							
//		    	        	nowv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH mm ss"));
//		    				
//		    	        	nowmv = Integer.valueOf(nowv.substring(3,5));
//		    	        	minuteval = minute - nowmv;
//		    	        	
//								
//							} while (minuteval > 5);
//						
//						System.out.println("ini di bagian jam tak tepat, waktu tak tepat, looping hingga keduanya tepat");
//						
//						System.out.println("kelas ke " + i);
//						System.out.println("=====================\n");
//						WebDriver autologdrive = new ChromeDriver();
//						autologdrive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//				    	autologdrive.manage().window().maximize();
////				    	((JavascriptExecutor)autologdrive).executeScript("window.open()");
////				    	((JavascriptExecutor)autologdrive).executeScript("window.open()");
////				    	ArrayList<String> tabss = new ArrayList<String>(autologdrive.getWindowHandles());
////						autologdrive.switchTo().window(tabss.get(2));
//						autologdrive.navigate().to(URL);
//												
//						
//						Thread.sleep(3000);
//						
//						bot.keyPress(KeyEvent.VK_LEFT);
//						bot.delay(100);
//						bot.keyRelease(KeyEvent.VK_LEFT);
//						
//						bot.keyPress(KeyEvent.VK_ENTER);
//						bot.delay(100);
//						bot.keyRelease(KeyEvent.VK_ENTER);
//						
//			    		
//			    		autologdrive.quit();
//			    		
//					} else if (minuteval < 0) {
//						System.out.println("The class has started");
//						continue;
//					}
//					
//				}
//    			
//    			else if (hourval < 0) {
//    				System.out.println("the Class has been passed");
//    				System.out.println("=====================================");
//    				System.out.println("\n");
//    				continue;
//    			}
//    		
//    			
//    		} else {
//    			System.out.println("\n");
//    			System.out.println("horray you don't have class for today anymore");
//    			break;
//			}
//    	
//    		
//    	}
    
	}
}
