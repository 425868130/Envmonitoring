# 环境监测系统
## 项目简介
本项目是一个环境监测系统的简易模拟软件，共分为三部分：采集终端，查询终端，服务端。  
主要功能为：  
1.采集终端可以将采集到的环境信息发送到管理后台   
2.查询终端可以查询到后台的环境信息  
3.服务端可以将多个采集终端的信息汇总（加权平均）、存储  
程序的采集终端通过UDP向服务端发送环境数据，查询端则通过建立TCP连接来同服务器交换数据，本系统下服务端采用单例模式只能开启一个，而采集端和查询端则不限制启动数量。同时程序还实现了查询终端的断线自动重连，服务端的暂停和启动功能。


* 技术关键词：Java Swing、Socket、TCP、UDP、线程池、JSON

## 部署说明
数据库连接配置文件路径：src/utils/JDBCUtils.java  
数据库脚本文件：项目根目录下的mysql.sql  
服务端启动类：src/server/ServerControl.java  
查询终端启动类：src/queryClient/QueryClient.java  
采集终端启动类：src/collectionClient/Client.java  
由于项目依赖包较少所以没有采用Maven，请自行添加[gson.jar][1]以及[mysql-connector-java.jar][2]两个依赖包。  
项目启动后必须先通过采集终端发送环境数据到服务器端，查询终端才能查询到数据，其中气温折线图则至少需要两个间隔一小时的数据才能开始绘制。  

## 界面预览
查询终端  
![查询终端][3]  
采集终端  
![采集终端][4]  
服务端  
![服务端][5]  

[1]:http://my-dist.oss-cn-hangzhou.aliyuncs.com/java/EnvMonitoring/gson-2.8.0.jar
[2]:http://my-dist.oss-cn-hangzhou.aliyuncs.com/java/EnvMonitoring/mysql-connector-java-5.1.41-bin.jar
[3]:http://my-dist.oss-cn-hangzhou.aliyuncs.com/java/EnvMonitoring/search.png?x-oss-process=style/pic-70-peaktop
[4]:http://my-dist.oss-cn-hangzhou.aliyuncs.com/java/EnvMonitoring/client.png?x-oss-process=style/pic-70-peaktop
[5]:http://my-dist.oss-cn-hangzhou.aliyuncs.com/java/EnvMonitoring/server.png  
  
更新时间：2017-11-10 16:37:24