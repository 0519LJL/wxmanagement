DROP TABLE IF EXISTS `wx_msg_record`;
CREATE TABLE `wx_msg_record` (
  `msgid` bigint(22) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(80) NOT NULL COMMENT '微信id',
	`type` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '消息类型 0:关注/取消 1消息',
  `comment` varchar(500) DEFAULT NULL COMMENT '内容',
	`create_time` datetime DEFAULT NOW() COMMENT '创建时间',
  UNIQUE KEY `qrid` (`msgid`),
  KEY `idx_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息记录表';
DELIMITER ;;