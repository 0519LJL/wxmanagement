
DROP TABLE IF EXISTS `wx_member_qrcode`;
CREATE TABLE `wx_member_qrcode` (
  `qrid` bigint(22) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(80) NOT NULL COMMENT '微信id',
	`type` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '二维码类型 0:推荐二维码 1商品二维码',
  `path` varchar(500) DEFAULT NULL COMMENT '二维码路径',
	`create_time` datetime DEFAULT NOW() COMMENT '创建时间',
  UNIQUE KEY `qrid` (`qrid`),
  KEY `idx_mid` (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员二维码表';
DELIMITER ;;
