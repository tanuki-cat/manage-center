-- changeset Joy:20231120_1000
-- comment sys_company表添加字段
CREATE TABLE `sys_company`
(
    `company_id`  bigint(20) NOT NULL COMMENT '主键ID',
    `name`        varchar(255) DEFAULT NULL COMMENT '公司名称',
    `user_name`   varchar(255) DEFAULT NULL COMMENT '公司联系人',
    `user_mobile` varchar(11)  DEFAULT NULL COMMENT '公司联系人移动电话',
    `create_by`   varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime     DEFAULT NULL COMMENT '创建日期',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司管理';

-- changeset Joy:20231120_1102
-- comment sys_visit_log表添加字段
CREATE TABLE `sys_visit_log`
(
    `log_id`       bigint(20) NOT NULL COMMENT '主键ID',
    `company_id`   bigint(20) DEFAULT NULL COMMENT '公司id',
    `company_name` varchar(255)  DEFAULT NULL COMMENT '公司名称',
    `content`      varchar(3000) DEFAULT NULL COMMENT '日志内容',
    `create_name`  varchar(255)  DEFAULT NULL COMMENT '创建者名字',
    `create_by`    varchar(255)  DEFAULT NULL COMMENT '创建者',
    `update_by`    varchar(255)  DEFAULT NULL COMMENT '更新者',
    `create_time`  datetime      DEFAULT NULL COMMENT '创建日期',
    `update_time`  datetime      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拜访日志';
