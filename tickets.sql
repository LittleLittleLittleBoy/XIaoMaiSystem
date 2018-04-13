/*
 Navicat Premium Data Transfer

 Source Server         : tickets
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : tickets

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 28/03/2018 08:38:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admininfo
-- ----------------------------
DROP TABLE IF EXISTS `admininfo`;
CREATE TABLE `admininfo` (
  `id` int(11) NOT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admininfo
-- ----------------------------
BEGIN;
INSERT INTO `admininfo` VALUES (0, 10);
COMMIT;

-- ----------------------------
-- Table structure for distributionticket
-- ----------------------------
DROP TABLE IF EXISTS `distributionticket`;
CREATE TABLE `distributionticket` (
  `ticketid` varchar(255) NOT NULL,
  `showid` int(11) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  `seat1` int(11) DEFAULT '0',
  `seat2` int(11) DEFAULT '0',
  `seat3` int(11) DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `price` double DEFAULT NULL,
  `issuccess` tinyint(1) DEFAULT NULL,
  `ispayed` tinyint(1) DEFAULT NULL,
  `iscancel` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ticketid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `email` varchar(255) NOT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `isok` tinyint(1) DEFAULT '0',
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
BEGIN;
INSERT INTO `login` VALUES ('0000001', '10d164a78e7674e429382300fb89eec7', 1, 1, '');
INSERT INTO `login` VALUES ('1075778700@qq.com', '10d164a78e7674e429382300fb89eec7', 0, 1, '3cf51b634eecba5d2138a0e5f1b425f8');
INSERT INTO `login` VALUES ('1150170525@qq.com', '10d164a78e7674e429382300fb89eec7', 0, 1, 'afda316b169eefa02ddfdba673c6cc5d');
INSERT INTO `login` VALUES ('admin', '10d164a78e7674e429382300fb89eec7', 2, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for moneyInfo
-- ----------------------------
DROP TABLE IF EXISTS `moneyInfo`;
CREATE TABLE `moneyInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromuser` varchar(255) DEFAULT NULL,
  `fromtype` int(11) DEFAULT NULL,
  `touser` varchar(255) DEFAULT NULL,
  `totype` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='资金变化';

-- ----------------------------
-- Records of moneyInfo
-- ----------------------------
BEGIN;
INSERT INTO `moneyInfo` VALUES (25, '1150170525@qq.com', 0, '0000001', 1, 700, '2018-03-26 03:08:00');
INSERT INTO `moneyInfo` VALUES (26, '1150170525@qq.com', 0, 'admin', 2, 5, '2018-03-26 03:08:00');
INSERT INTO `moneyInfo` VALUES (27, '1150170525@qq.com', 0, '0000001', 1, 1658.88, '2018-03-26 03:13:13');
INSERT INTO `moneyInfo` VALUES (28, '1150170525@qq.com', 0, 'admin', 2, 5, '2018-03-26 03:13:13');
INSERT INTO `moneyInfo` VALUES (29, '0000001', 1, '1150170525@qq.com', 0, 1161.216, '2018-03-26 03:13:17');
COMMIT;

-- ----------------------------
-- Table structure for offticket
-- ----------------------------
DROP TABLE IF EXISTS `offticket`;
CREATE TABLE `offticket` (
  `ticketid` varchar(255) NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `showid` int(11) DEFAULT NULL,
  PRIMARY KEY (`ticketid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线下订单';

-- ----------------------------
-- Records of offticket
-- ----------------------------
BEGIN;
INSERT INTO `offticket` VALUES ('0d4640df4b5dcfff014d2c33ac8d8899', '3,4;', 6);
INSERT INTO `offticket` VALUES ('69f526289f727b8132243f284c3b247d', '2,4;', 6);
COMMIT;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `theaterid` char(7) NOT NULL,
  `roomid` varchar(255) NOT NULL,
  `roominfo` varchar(255) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  `col` int(11) DEFAULT NULL,
  `isdelete` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`theaterid`,`roomid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='剧院的房间信息';

-- ----------------------------
-- Records of room
-- ----------------------------
BEGIN;
INSERT INTO `room` VALUES ('0000001', '贵宾一厅', '1111111;0111110;0111110;0111100;0222220;0222000;0333330;', 7, 7, 0);
COMMIT;

-- ----------------------------
-- Table structure for showinfo
-- ----------------------------
DROP TABLE IF EXISTS `showinfo`;
CREATE TABLE `showinfo` (
  `showid` int(11) NOT NULL AUTO_INCREMENT,
  `theaterid` char(7) DEFAULT NULL,
  `roomid` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `price1` double DEFAULT NULL,
  `price2` double DEFAULT NULL,
  `price3` double DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text,
  PRIMARY KEY (`showid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='演出信息';

-- ----------------------------
-- Records of showinfo
-- ----------------------------
BEGIN;
INSERT INTO `showinfo` VALUES (6, '0000001', '贵宾一厅', '环太平洋：雷霆再起', 100, 200, 300, '话剧', '2018-03-31 02:30:00', '假装是一个话剧');
INSERT INTO `showinfo` VALUES (7, '0000001', '贵宾一厅', '欢笑平阳', 200, 300, 400, '音乐会', '2018-03-28 03:10:00', '好像是一个音乐会');
COMMIT;

-- ----------------------------
-- Table structure for theater
-- ----------------------------
DROP TABLE IF EXISTS `theater`;
CREATE TABLE `theater` (
  `theaterid` char(7) NOT NULL,
  `name` varchar(255) NOT NULL,
  `place` varchar(255) DEFAULT NULL,
  `money` double DEFAULT '0',
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`theaterid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='剧院信息';

-- ----------------------------
-- Records of theater
-- ----------------------------
BEGIN;
INSERT INTO `theater` VALUES ('0000001', '红旗小影院', '中华南大街', 1197.6640000000002, '1150170525@qq.com');
COMMIT;

-- ----------------------------
-- Table structure for theaterinfo
-- ----------------------------
DROP TABLE IF EXISTS `theaterinfo`;
CREATE TABLE `theaterinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isread` tinyint(1) DEFAULT '0',
  `isok` tinyint(1) DEFAULT '0',
  `theaterid` char(7) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of theaterinfo
-- ----------------------------
BEGIN;
INSERT INTO `theaterinfo` VALUES (13, 1, 1, '0000001', '{\"pass\":\"123\",\"name\":\"红旗影院\",\"place\":\"中华南大街\",\"email\":\"1150170525@qq.com\"}', 0, '2018-03-26 02:53:57');
INSERT INTO `theaterinfo` VALUES (14, 1, 1, '0000001', '{\"col\":\"7\",\"row\":\"7\",\"roominfo\":\"1111111;0111110;0111110;0111100;0222220;0222000;0333330;\",\"roomid\":\"贵宾一厅\"}', 2, '2018-03-26 03:02:06');
INSERT INTO `theaterinfo` VALUES (15, 1, 0, '0000001', '{\"name\":\"红旗小影院\"}', 1, '2018-03-26 03:14:06');
INSERT INTO `theaterinfo` VALUES (16, 1, 1, '0000001', '{\"name\":\"红旗小影院\"}', 1, '2018-03-26 03:14:32');
COMMIT;

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `ticketid` varchar(255) NOT NULL,
  `showid` varchar(255) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `number` int(11) DEFAULT '1',
  `issuccess` tinyint(1) DEFAULT '0',
  `iscancel` tinyint(1) DEFAULT '0',
  `ispayed` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ticketid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线上购票';

-- ----------------------------
-- Records of ticket
-- ----------------------------
BEGIN;
INSERT INTO `ticket` VALUES ('07eeacc2f638ea32d50ceeb4cea5efdc', '7', '1150170525@qq.com', '1,1;1,2;5,2;5,3;7,2;7,3;', 1658.88, '2018-03-26 03:12:30', 6, 0, 1, 1);
INSERT INTO `ticket` VALUES ('6ba1775d459d8614141a6f66bbc69927', '6', '1150170525@qq.com', '1,2;1,3;2,3;5,3;7,3;', 600, '2018-03-26 03:04:27', 5, 0, 1, 0);
INSERT INTO `ticket` VALUES ('7569ce1c48c000493a98fac435d29ecc', '6', '1150170525@qq.com', '1,2;1,3;1,4;2,3;3,3;6,3;', 700, '2018-03-26 03:07:04', 6, 1, 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `email` varchar(255) NOT NULL,
  `name` varchar(30) DEFAULT '匿名用户',
  `score` int(11) DEFAULT '0',
  `money` double DEFAULT '0',
  `ismember` tinyint(1) DEFAULT '1',
  `total` int(11) DEFAULT '0',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息';

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES ('1075778700@qq.com', '默认用户', 0, 0, 0, 0);
INSERT INTO `user_info` VALUES ('1150170525@qq.com', '小明', 700, 99998792.33600001, 1, 700);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
