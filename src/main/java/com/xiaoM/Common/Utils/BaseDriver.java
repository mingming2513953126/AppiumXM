package com.xiaoM.Common.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.xiaoM.Report.utils.TestListener;
import com.xiaoM.appium.utils.AppiumServerUtils;
import com.xiaoM.appium.utils.PortProber;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseDriver {
	public Log log=new Log(this.getClass());
	AppiumDriver <MobileElement> driver ;
	AppiumServerUtils  AppiumServer = null;
	public URL url;
	public AppiumDriver <MobileElement> setUpApp(String device,String devicesPath) throws IOException {
		String driverName = null;
		try {
			Object[][] testBase = IOMananger.readExcelData(device,devicesPath);
			driverName = testBase[1][2].toString();
			String ipAddress = testBase[2][2].toString();
			String platformName =testBase[3][2].toString();
			String deviceId =testBase[4][2].toString();
			String deviceName =testBase[5][2].toString();
			String sdkVersion =testBase[6][2].toString();
			int Port = PortProber.getFreePort();
			String bootstrapPort = String.valueOf(PortProber.getFreePort());
			log.info("设备： "+driverName+" "+"开始执行测试");
			log.info("设备： "+driverName+" "+"启动appium server");
			log.info("设备： "+driverName+" "+"配置信息：Mobile Driver:"+driverName);
			log.info("设备： "+driverName+" "+"Appium Server:"+"http://"+ipAddress+":"+Port+"/wd/hub");
			log.info("设备： "+driverName+" "+"设备Id："+deviceId);
			try {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				if(TestListener.DeviceType.equals("Android")){
					AppiumServer = new AppiumServerUtils(ipAddress,Port,bootstrapPort);
					url = AppiumServer.startServer();
					File appDir=new File(TestListener.ProjectPath,"apps");
					File app =new File(appDir,TestListener.appName);
					String appMainPackage =TestListener.PackageName;
					String appActivity =TestListener.Activity;
					capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, sdkVersion);
					capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
					capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appMainPackage);
					capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
					capabilities.setCapability(MobileCapabilityType.NO_RESET, TestListener.ResetApp);
					capabilities.setCapability(MobileCapabilityType.UDID, deviceId);
					capabilities.setCapability("unicodeKeyboard", "True");
					capabilities.setCapability("resetKeyboard", "True");
					capabilities.setCapability("noSign", "True");	
				}else{
					AppiumServer = new AppiumServerUtils();
					url = AppiumServer.startServer(ipAddress,Port);
					String bundleId = TestListener.bundleId;
					capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, sdkVersion);
					capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
					capabilities.setCapability(MobileCapabilityType.UDID, deviceId);
					capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID,bundleId);
				}	
				driver = new AppiumDriver<MobileElement>(url, capabilities);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("设备： "+driverName+" "+"appium环境配置失败");
			}
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"读取TestBase配置文件失败");
			e.printStackTrace();
		}
		return driver;
	}


	public AppiumDriver <MobileElement> setUpWap(String device,String devicesPath) throws IOException {
		String driverName =null;
		try {
			Object[][] testBase = IOMananger.readExcelData(device,devicesPath);
			driverName = testBase[1][2].toString();
			String ipAddress = testBase[2][2].toString();
			String platformName =testBase[3][2].toString();
			String deviceId =testBase[4][2].toString();
			String deviceName =testBase[5][2].toString();
			String sdkVersion =testBase[6][2].toString();
			String Browser = testBase[7][2].toString();
			int Port = PortProber.getFreePort();
			String bootstrapPort = String.valueOf(PortProber.getFreePort());
			log.info("设备： "+driverName+" "+"开始执行测试");
			log.info("设备： "+driverName+" "+"启动appium server");
			try {
					log.info("设备： "+driverName+" "+"配置信息：Mobile Driver:"+driverName);
					log.info("设备： "+driverName+" "+"Appium Server:"+"http://"+ipAddress+":"+Port+"/wd/hub");
					log.info("设备： "+driverName+" "+"设备Id："+deviceId);
					DesiredCapabilities capabilities = new DesiredCapabilities();
					if(TestListener.DeviceType.equals("Android")){
						AppiumServer = new AppiumServerUtils(ipAddress,Port,bootstrapPort);
						url = AppiumServer.startServer();
						capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
						capabilities.setCapability(CapabilityType.BROWSER_NAME, Browser);// Browser
						capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
						capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
						capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, sdkVersion);
						capabilities.setCapability(MobileCapabilityType.UDID, deviceId);
						capabilities.setCapability("unicodeKeyboard", "True");
						capabilities.setCapability("resetKeyboard", "True");
					}else{
						AppiumServer = new AppiumServerUtils();
						url = AppiumServer.startServer(ipAddress,Port);
						capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
						capabilities.setCapability(CapabilityType.BROWSER_NAME, Browser);// Browser
						capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
						capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
						capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, sdkVersion);
						capabilities.setCapability(MobileCapabilityType.UDID, deviceId);
					}
					driver = new AppiumDriver <MobileElement>(url, capabilities);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("设备： "+driverName+" "+"appium环境配置失败");
			}
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"读取TestBase配置文件失败");
			e.printStackTrace();
		}
		return driver;
	}
}
