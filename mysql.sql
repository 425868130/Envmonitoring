/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : javanet

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-09 19:59:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for atmosphere
-- ----------------------------
DROP TABLE IF EXISTS `atmosphere`;
CREATE TABLE `atmosphere` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contaminants` varchar(20) NOT NULL,
  `pm` int(11) NOT NULL,
  `humidity` int(11) NOT NULL,
  `windDirection` varchar(20) NOT NULL,
  `windPower` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of atmosphere
-- ----------------------------
INSERT INTO `atmosphere` VALUES ('1', '2017-05-21 17:15:41', 'co3', '3', '70', '东风', '2');
INSERT INTO `atmosphere` VALUES ('2', '2017-05-21 17:22:16', '123', '213', '23', '东风', '1');
INSERT INTO `atmosphere` VALUES ('3', '2017-05-21 17:37:05', '21312', '213', '2312', '东风', '1');
INSERT INTO `atmosphere` VALUES ('4', '2017-05-21 17:38:47', '21312', '213', '2312', '南风', '1');
INSERT INTO `atmosphere` VALUES ('5', '2017-05-21 17:38:48', 'co', '12', '213', '北风', '1');
INSERT INTO `atmosphere` VALUES ('6', '2017-05-21 17:39:54', 'co', '12', '213', '东南风', '1');
INSERT INTO `atmosphere` VALUES ('7', '2017-05-21 17:40:15', 'co', '12', '213', '西北风', '1');
INSERT INTO `atmosphere` VALUES ('8', '2017-05-21 17:41:05', '21312', '213', '2312', '南风', '1');
INSERT INTO `atmosphere` VALUES ('9', '2017-05-21 17:41:15', 'co', '12', '213', '北风', '1');
INSERT INTO `atmosphere` VALUES ('10', '2017-05-21 17:43:18', 'co', '12', '213', '北风', '1');
INSERT INTO `atmosphere` VALUES ('11', '2017-06-22 11:10:13', 'SO2', '1', '2', '东（E）', '1');

-- ----------------------------
-- Table structure for climate
-- ----------------------------
DROP TABLE IF EXISTS `climate`;
CREATE TABLE `climate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `temperature` float NOT NULL,
  `weather` varchar(20) NOT NULL,
  `precipitation` int(11) NOT NULL,
  `ultravioletRays` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of climate
-- ----------------------------
INSERT INTO `climate` VALUES ('1', '2017-05-21 01:38:47', '21', '多云', '0', '1');
INSERT INTO `climate` VALUES ('2', '2017-05-21 01:50:47', '20', '多云', '0', '1');
INSERT INTO `climate` VALUES ('3', '2017-05-21 02:30:50', '18', '多云', '0', '0');
INSERT INTO `climate` VALUES ('4', '2017-05-21 02:38:48', '18', '多云', '0', '1');
INSERT INTO `climate` VALUES ('5', '2017-05-21 03:30:30', '19', '阴', '0', '1');
INSERT INTO `climate` VALUES ('6', '2017-05-21 04:39:54', '20', '阴', '0', '1');
INSERT INTO `climate` VALUES ('7', '2017-05-21 05:40:15', '22', '阴', '0', '1');
INSERT INTO `climate` VALUES ('8', '2017-05-21 06:40:15', '23', '阴', '0', '1');
INSERT INTO `climate` VALUES ('9', '2017-05-21 07:41:05', '23', '阴', '0', '2');
INSERT INTO `climate` VALUES ('10', '2017-05-21 08:41:15', '24', '多云', '0', '3');
INSERT INTO `climate` VALUES ('11', '2017-05-21 09:43:18', '25', '晴', '0', '5');
INSERT INTO `climate` VALUES ('12', '2017-05-21 10:01:35', '25', '晴', '0', '5');
INSERT INTO `climate` VALUES ('13', '2017-05-21 11:02:01', '26', '晴', '0', '5');
INSERT INTO `climate` VALUES ('14', '2017-05-21 12:02:17', '27', '晴', '0', '6');
INSERT INTO `climate` VALUES ('15', '2017-05-21 13:02:47', '29', '晴', '0', '6');
INSERT INTO `climate` VALUES ('16', '2017-05-21 14:02:58', '30', '晴', '0', '6');
INSERT INTO `climate` VALUES ('17', '2017-05-21 15:04:20', '29', '晴', '0', '7');
INSERT INTO `climate` VALUES ('18', '2017-05-21 16:04:37', '29', '晴', '0', '7');
INSERT INTO `climate` VALUES ('19', '2017-05-21 17:04:53', '28', '多云', '0', '6');
INSERT INTO `climate` VALUES ('20', '2017-05-21 18:56:50', '25', '阴', '0', '5');
INSERT INTO `climate` VALUES ('21', '2017-05-21 19:57:20', '25', '阴', '0', '3');
INSERT INTO `climate` VALUES ('22', '2017-05-21 20:58:00', '24', '阴', '0', '3');
INSERT INTO `climate` VALUES ('23', '2017-05-21 21:58:40', '22', '多云', '0', '2');
INSERT INTO `climate` VALUES ('24', '2017-05-21 22:59:05', '21', '多云', '0', '1');
INSERT INTO `climate` VALUES ('25', '2017-05-21 23:30:28', '21', '多云', '0', '1');
INSERT INTO `climate` VALUES ('26', '2017-05-21 00:00:01', '21', '多云', '0', '1');
INSERT INTO `climate` VALUES ('27', '2017-06-22 11:10:13', '2', '雷阵雨', '23', '112');

-- ----------------------------
-- Table structure for waterquality
-- ----------------------------
DROP TABLE IF EXISTS `waterquality`;
CREATE TABLE `waterquality` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dissolvedOxygen` float NOT NULL,
  `ph` float NOT NULL,
  `turbidity` float NOT NULL,
  `conductivity` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of waterquality
-- ----------------------------
INSERT INTO `waterquality` VALUES ('1', '2017-05-21 17:41:05', '13', '13', '13', '12');
INSERT INTO `waterquality` VALUES ('2', '2017-05-21 17:41:15', '12', '23', '23', '28');
INSERT INTO `waterquality` VALUES ('3', '2017-05-21 17:43:18', '10', '23', '23', '28');
INSERT INTO `waterquality` VALUES ('4', '2017-06-22 11:10:13', '1', '2', '32', '12');

-- ----------------------------
-- Procedure structure for PGetAVG_atmosphere
-- ----------------------------
DROP PROCEDURE IF EXISTS `PGetAVG_atmosphere`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PGetAVG_atmosphere`(IN `IN_Data` varchar(20))
BEGIN
	#空气表--获取pm2.5、获取湿度、获取风力
SELECT AVG(atmosphere.pm) avg_pm,AVG(atmosphere.humidity) avg_humidity,AVG(atmosphere.windPower) avg_windPower
FROM atmosphere
WHERE date_format(atmosphere.time,'%Y-%m-%d') = IN_Data;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PGetAVG_climate
-- ----------------------------
DROP PROCEDURE IF EXISTS `PGetAVG_climate`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PGetAVG_climate`(IN `IN_Data` varchar(20))
BEGIN
	#气候表--获取降水量、紫外线强度
SELECT AVG(climate.precipitation) avg_precipitation,AVG(climate.ultravioletRays) avg_ultravioletRays
FROM climate
WHERE date_format(climate.time,'%Y-%m-%d') = IN_Data;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PGetAVG_contaminants
-- ----------------------------
DROP PROCEDURE IF EXISTS `PGetAVG_contaminants`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PGetAVG_contaminants`(IN `IN_Data` varchar(20))
BEGIN
	#空气表--获取主要污染物
select contaminants,MAX(num) as count from
(select atmosphere.contaminants as contaminants,count(*) as num
from atmosphere
WHERE date_format(atmosphere.time,'%Y-%m-%d') = IN_Data
GROUP BY atmosphere.contaminants 
ORDER BY COUNT(*) desc)temp;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PGetAVG_temperature
-- ----------------------------
DROP PROCEDURE IF EXISTS `PGetAVG_temperature`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PGetAVG_temperature`(IN `IN_Data` varchar(20))
BEGIN
	#气候表--获取当天每小时的平均温度
SELECT HOUR(climate.time) AS hour,AVG(climate.temperature) as avg_temperature from climate
WHERE date_format(climate.time,'%Y-%m-%d') = IN_Data
GROUP BY hour
ORDER BY hour ASC;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PGetAVG_waterquality
-- ----------------------------
DROP PROCEDURE IF EXISTS `PGetAVG_waterquality`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PGetAVG_waterquality`(IN `IN_Data` varchar(20))
BEGIN
	#水质表--获取ph值、溶解氧、电导率、浑浊度
SELECT AVG(waterquality.ph) avg_ph,AVG(waterquality.dissolvedOxygen) avg_dissolvedOxygen,AVG(waterquality.conductivity) avg_conductivity,AVG(waterquality.turbidity) avg_turbidity
FROM waterquality
WHERE date_format(waterquality.time,'%Y-%m-%d') = IN_Data;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PGetAVG_weather
-- ----------------------------
DROP PROCEDURE IF EXISTS `PGetAVG_weather`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PGetAVG_weather`(IN `IN_Data` varchar(20))
BEGIN
	#气候表--获取天气
select weather,MAX(num) as count from
(select climate.weather as weather,count(*) as num
from climate
WHERE date_format(climate.time,'%Y-%m-%d') = IN_Data
GROUP BY climate.weather 
ORDER BY COUNT(*) desc)temp;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PGetAVG_windDirection
-- ----------------------------
DROP PROCEDURE IF EXISTS `PGetAVG_windDirection`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PGetAVG_windDirection`(IN `IN_Data` varchar(20))
BEGIN
	#空气表--获取风向
select wind,MAX(num) as count from
(select atmosphere.windDirection as wind,count(*) as num
from atmosphere
WHERE date_format(atmosphere.time,'%Y-%m-%d') = IN_Data
GROUP BY atmosphere.windDirection 
ORDER BY COUNT(*) desc)temp;
END
;;
DELIMITER ;
