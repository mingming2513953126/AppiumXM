# AppiumXM
AppiumXM自动化测试框架基于 Appium + testNG + maven 二次封装，采用java语言进行开发，适用于Android、iOS自动化测试，采用Excel关键字驱动实现无需编写代码即可进行自动化测试，支持jenkins持续集成，支持Android/iOS在真机或模拟器进行自动化测试，支持H5、Hybrid 、Native测试
# 环境要求
Appium  >=1.6.3<p>
maven   >=3.3.9<p>
OSX  >=10.11.6<p>
Xcode  >=8.2.1<p>
# demo
**测试用例**<p>
![image](https://github.com/xiaoMGitHub/AppiumXM/blob/master/%E7%94%A8%E4%BE%8B.png)<p>
**测试步骤描述**<p>
![image](https://github.com/xiaoMGitHub/AppiumXM/blob/master/%E6%B5%8B%E8%AF%95%E7%94%A8%E4%BE%8B%E6%AD%A5%E9%AA%A4.png)<p>
**测试报告（普通）**<p>
![image](https://github.com/xiaoMGitHub/AppiumXM/blob/master/report1.png)<p>
**测试报告（性能监控）**<p>
![image](https://github.com/xiaoMGitHub/AppiumXM/blob/master/report2.png)<p>
# 存在问题
1、测试报告展示测试结束时间有误<p>
2、其他未知问题
# 如何运行？
1、Devices文件夹下添加相应的设备信息<p>
2、testcase文件夹下编辑测试步骤流程<p>
3、支持操作：看源码。。。<p>
4、安装testng插件运行StartTest.xml或定位到工程目录下运行mvn test，ant编译中文会乱码不建议<p>
5、多设备参照StartTest.xml注释<p>
6、持续集成，Jenkins + mvn 亲测运行完美<p>
7、iOS真机需自行前往 /usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent 对 WebDriverAgent.xcodeproj 进行重签名（个人证书即可）
# 缺陷
1、灵活性真的不高。。。
