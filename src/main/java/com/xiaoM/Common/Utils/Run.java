package com.xiaoM.Common.Utils;

import java.net.URL;

import com.xiaoM.Report.utils.TestListener;
import com.xiaoM.appium.utils.AppiumElementAction;
import com.xiaoM.appium.utils.AppiumResourceMonitoring;
import com.xiaoM.appium.utils.AppiumScreenShot;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Run extends BaseDriver {
	public Log log=new Log(this.getClass());
	AppiumDriver <MobileElement> driver ;
	String driverName = null;
	public URL url;
	public  void testCase(String CaseType,String CaseName,String device) throws Exception {
		String devicesPath =null;
		if(TestListener.DeviceType.equals("Android")){
			devicesPath = TestListener.ProjectPath+"/devices/AndroidDevices.xlsx";//选择设备
		}else{
			devicesPath = TestListener.ProjectPath+"/devices/iOSDevices.xlsx";//选择设备
		}	
		try {
			switch (CaseType) {
			case "Wap":
				driver = setUpWap(device,devicesPath);
				break;
			default:
				driver = setUpApp(device,devicesPath);
				break;
			}
			Object[][] testBase = IOMananger.readExcelData(device,devicesPath);
			driverName = testBase[1][2].toString();
			String deviceId =testBase[4][2].toString();
			String sdkVersion = testBase[6][2].toString();
			log.info("设备： "+driverName+" 执行用例："+CaseName);
			Object[][] testStart = IOMananger.readExcelData(CaseName,TestListener.CasePath);
			if(CaseType.equals("RM")){
				AppiumResourceMonitoring RM = new AppiumResourceMonitoring();
				RM.driverStartApp(driver, deviceId, driverName);
				for(int a=1;a<testStart.length;a++){
					if(testStart[a][0].equals("YES")){
						AppiumElementAction action = new AppiumElementAction();
						action.executeAppiumAction(driver,testStart[a], deviceId,driverName,sdkVersion);
					}	
				}
				RM.driverStop(deviceId, driverName, sdkVersion);
			}else{
				for(int a=1;a<testStart.length;a++){
					if(testStart[a][0].equals("YES")){
						AppiumElementAction action = new AppiumElementAction();
						action.executeAppiumAction(driver,testStart[a],deviceId,driverName, sdkVersion);
					}	
				}
			}
			log.info("设备： "+driverName+" 执行用例："+CaseName+"成功");
			TestListener.mobileSuccessMessageList.add(driverName+"(系统版本："+sdkVersion+")-"+CaseName);
		} catch (Exception | Error e) {
			TestListener.FailCasesName.add(CaseName);
			AppiumScreenShot screenShot=new AppiumScreenShot(driver);
			screenShot.setscreenName(driverName,CaseName);
			screenShot.takeScreenshot();
			log.error("设备： "+driverName+" 执行用例："+CaseName+"出错，正在关闭driver，准备执行下一条用例");
			throw e;
		} finally {
			TestListener.deviceLists.add(device);
			driver.quit();
			AppiumServer.stopServer();
		}
	}
}
