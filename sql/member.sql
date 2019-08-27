
DROP TABLE IF EXISTS `wx_member`;
CREATE TABLE `wx_member` (
  `mid` bigint(22) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) DEFAULT NULL COMMENT '名称',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `post_code` varchar(45) DEFAULT NULL COMMENT '邮编',
  `mobile` varchar(45) DEFAULT NULL COMMENT '手机号码',
  `verify_mobile` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '手机验证 0:未验证 1:已验证',
  `wx_openid` varchar(80) DEFAULT NULL COMMENT '微信id',
  `wx_name` varchar(80) DEFAULT NULL COMMENT '微信用户名',
  `create_time` varchar(20) DEFAULT NULL  COMMENT '创建时间',
  `enable` TINYINT(2) NOT NULL DEFAULT '1' COMMENT '启用(0不启用 1启用)',
  UNIQUE KEY `mid` (`mid`),
  UNIQUE KEY `uidx_wx_openid` (`wx_openid`),
  KEY `idx_name` (`name`),
  KEY `idx_mobile` (`mobile`),
  KEY `idx_openid` (`wx_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';
DELIMITER ;;

