CREATE TABLE `business_test` (
                                `id` varchar(50) NOT NULL COMMENT 'ID',
                                `name` varchar(255) NOT NULL COMMENT '名称',
                                `test1` varchar(255) NOT NULL COMMENT 'jdbc连接',
                                `test2` varchar(255) NOT NULL COMMENT '账号',
                                `test3` varchar(255) NOT NULL COMMENT '密码',
                                `test4` varchar(255) DEFAULT NULL COMMENT '创建者',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='数据库管理';