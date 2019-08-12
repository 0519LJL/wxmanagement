
DROP TABLE IF EXISTS `tb_wx_access_token`;
CREATE TABLE `tb_wx_access_token` (
  `Id` int(11) NOT NULL,
  `access_token` varchar(255) DEFAULT NULL COMMENT '接口调用凭证',
  `expires_in` int(11) DEFAULT NULL COMMENT 'access_token接口调用凭证超时时间，单位（秒）',
  `start_time` bigint(20) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL COMMENT '用户刷新access_token',
  `openid` varchar(50) DEFAULT NULL COMMENT '授权用户唯一标识',
  `scope` varchar(80) DEFAULT NULL COMMENT '用户授权的作用域，使用逗号（,）分隔',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

