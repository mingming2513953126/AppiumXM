package com.xiaoM.appium.utils;

import java.net.URL;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;

/**
 * @author xiaoM
 * @since 2017-03-30
 */
public class AppiumServerUtils {
	String ipAddress;
	int port;
	String bp;
	AppiumDriverLocalService service;
	
	public AppiumServerUtils(){
	}
	public AppiumServerUtils(String ipAddress, int port,String bp){
		this.ipAddress = ipAddress;
		this.port = port;
		this.bp = bp;
	}
	
	/**
	 * 使用默认（即127.0.0.1:4723）
	 * @return
	 */
	public URL startAppiumServerByDefault() {
		AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}
	
	public URL startAppiumServerNoReset() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}

	public URL startServer() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress(ipAddress);
		builder.usingPort(port);
		builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, bp);
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}
	
	public URL startServer(String ipAddress,int port) {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress(ipAddress);
		builder.usingPort(port);
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}
	
	public void stopServer(){
		if(service!=null){
			 service.stop();
		}
	}
}
