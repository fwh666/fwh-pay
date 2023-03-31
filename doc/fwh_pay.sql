CREATE TABLE `fwh_order_record`
(
    `id`                bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_no`          bigint                                                 DEFAULT NULL COMMENT '订单号',
    `out_trade_no`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付宝交易订单号',
    `recipient_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收件人邮箱',
    `mobile`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
    `status`            tinyint                                                DEFAULT NULL COMMENT '状态 0-创建 1-支付成功 2-支付失败 3-邮件发送成功 4-邮件发送失败',
    `account`           varchar(255) COLLATE utf8mb4_bin                       DEFAULT NULL COMMENT '售卖账号信息',
    `create_time`       timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `modified_time`     timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY                 `idx_outTradeNo` (`out_trade_no`) USING BTREE COMMENT '交易订单号索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='支付订单记录表';


CREATE TABLE `fwh_account`
(
    `id`            bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `account`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱账号',
    `password`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱账号密码',
    `status`        tinyint                                                DEFAULT '0' COMMENT '状态 0-未出售 1-已出售',
    `create_time`   timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='账号表';