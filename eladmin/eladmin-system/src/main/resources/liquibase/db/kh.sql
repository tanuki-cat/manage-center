-- liquibase formatted sql
-- changeset Joy:20231120_1000
-- comment sys_company表添加字段
CREATE TABLE `sys_company`
(
    `company_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
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
    `log_id`       bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `company_id`   bigint(20) DEFAULT NULL COMMENT '公司id',
    `company_name` varchar(255)  DEFAULT NULL COMMENT '公司名称',
    `content`      varchar(3000) DEFAULT NULL COMMENT '日志内容',
    `nick_name`  varchar(255)  DEFAULT NULL COMMENT '创建者名字',
    `create_by`    varchar(255)  DEFAULT NULL COMMENT '创建者',
    `update_by`    varchar(255)  DEFAULT NULL COMMENT '更新者',
    `create_time`  datetime      DEFAULT NULL COMMENT '创建日期',
    `update_time`  datetime      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拜访日志';

-- changeset Joy:20231122_1409
-- comment sys_project表添加字段
CREATE TABLE `sys_project`
(
    `project_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `company_id`   bigint(20) DEFAULT NULL COMMENT '公司id',
    `company_name` varchar(255)  DEFAULT NULL COMMENT '公司名称',
    `project_name` varchar(255)  DEFAULT NULL COMMENT '项目名称',
    `project_desc` varchar(255)  DEFAULT NULL COMMENT '项目描述',
    `project_amount`  decimal(18,0)  DEFAULT NULL COMMENT '项目金额',
    `project_status`  int(8)  DEFAULT NULL COMMENT '项目状态',
    `nick_name`  varchar(255)  DEFAULT NULL COMMENT '创建者名字',
    `create_by`    varchar(255)  DEFAULT NULL COMMENT '创建者',
    `update_by`    varchar(255)  DEFAULT NULL COMMENT '更新者',
    `create_time`  datetime      DEFAULT NULL COMMENT '创建日期',
    `update_time`  datetime      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目管理';

CREATE TABLE `sys_project_schedule`
    (
        `schedule_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
        `project_id`   bigint(20) DEFAULT NULL COMMENT '项目id',
        `schedule_status`  int(8)  DEFAULT NULL COMMENT '进度状态',
        `assign_user`  varchar(255)  DEFAULT NULL COMMENT '指派人',
        `nick_name`  varchar(255)  DEFAULT NULL COMMENT '创建者名字',
        `create_by`    varchar(255)  DEFAULT NULL COMMENT '创建者',
        `update_by`    varchar(255)  DEFAULT NULL COMMENT '更新者',
        `create_time`  datetime      DEFAULT NULL COMMENT '创建日期',
        `update_time`  datetime      DEFAULT NULL COMMENT '更新时间',
        PRIMARY KEY (`schedule_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目进度管理';

-- changeset Joy:20231218_2045
-- comment sys_project_schedule表添加字段
ALTER TABLE `sys_project_schedule`
    ADD COLUMN `assign_user_id` bigint(20) NULL COMMENT '指派人员Id' AFTER `assign_user`;

-- changeset Joy:20231220_1946
-- comment sys_project_schedule表添加字段
ALTER TABLE `sys_project`
    ADD COLUMN `user_name` varchar(255) NULL COMMENT '公司联系人' AFTER `company_name`,
    ADD COLUMN `user_mobile` varchar(11) NULL COMMENT '公司联系人移动电话' AFTER `user_name`,
    ADD COLUMN `amount_percent` varchar(255) NULL COMMENT '金额百分比' AFTER `user_mobile`;

ALTER TABLE `sys_project_schedule`
    ADD COLUMN `amount_percent` varchar(255) NULL COMMENT '金额百分比' AFTER `assign_user_id`,
    ADD COLUMN `schedule_desc` varchar(255) NULL COMMENT '进度描述' AFTER `amount_percent`;

-- changeset lychee:20231221_1817
-- comment sys_project_schedule表添加指派作废的status字段
ALTER TABLE `sys_project_schedule`
    ADD COLUMN `assign_status` int(2) NOT NULL DEFAULT 0 COMMENT '指派状态 1- 作废 0- 指派' AFTER `schedule_desc`;

-- changeset lychee:20231221_2017
-- comment sys_project_schedule,sys_project 修改字段
ALTER TABLE `sys_project_schedule`
   MODIFY COLUMN `amount_percent` int(8) NOT NULL DEFAULT 0 COMMENT '金额百分比'  AFTER `assign_user_id`;

ALTER TABLE `sys_project`
   MODIFY COLUMN `amount_percent` int(8) NOT NULL DEFAULT 0 COMMENT '金额百分比'  AFTER `user_mobile`;

-- changeset lychee:20231221_1058
-- comment 新建sys_company_ext表
CREATE TABLE `sys_company_ext`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `company_id`  bigint(20) NOT NULL COMMENT '公司Id',
    `info_ext`    json DEFAULT NULL COMMENT '公司扩展信息',
    `create_by`   varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime     DEFAULT NULL COMMENT '创建日期',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `company_id` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司扩展信息表';

-- changeset lychee:20240101_1827
-- comment 修改项目表添加到期时间
ALTER TABLE `sys_project`
    ADD COLUMN `end_time` varchar(255) NULL COMMENT '到期时间' AFTER `project_amount`;

CREATE TABLE `sys_patent`
(
    `patent_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `company_id`   bigint(20) DEFAULT NULL COMMENT '公司id',
    `company_name` varchar(255)  DEFAULT NULL COMMENT '公司名称',
    `invention` varchar(255)  DEFAULT NULL COMMENT '发明专利',
    `utility_model` varchar(255)  DEFAULT NULL COMMENT '实用新型专利',
    `appearance` varchar(255)  DEFAULT NULL COMMENT '外观专利',
    `software_works` varchar(255)  DEFAULT NULL COMMENT '软件著作',
    `copyright` varchar(255)  DEFAULT NULL COMMENT '版权',
    `progress` varchar(255)  DEFAULT NULL COMMENT '专利进度',
    `filing_time` varchar(255)  DEFAULT NULL COMMENT '专利申报时间',
    `write_time` varchar(255)  DEFAULT NULL COMMENT '专利写好时间',
    `authorization_time` varchar(255)  DEFAULT NULL COMMENT '授权下证时间',
    `patent_num` varchar(255)  DEFAULT NULL COMMENT '专利数',
    `forewarn_time` varchar(255)  DEFAULT NULL COMMENT '年费预警时间',
    `patent_status`  int(8)  DEFAULT NULL COMMENT '状态',
    `nick_name`  varchar(255)  DEFAULT NULL COMMENT '创建者名字',
    `create_by`    varchar(255)  DEFAULT NULL COMMENT '创建者',
    `update_by`    varchar(255)  DEFAULT NULL COMMENT '更新者',
    `create_time`  datetime      DEFAULT NULL COMMENT '创建日期',
    `update_time`  datetime      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`patent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='专利表';

CREATE TABLE `sys_patent_schedule`
(
    `schedule_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `patent_id`   bigint(20) DEFAULT NULL COMMENT '项目id',
    `schedule_status`  int(8)  DEFAULT NULL COMMENT '进度状态',
    `assign_user`  varchar(255)  DEFAULT NULL COMMENT '指派人',
    `assign_user_id` bigint(20) NULL COMMENT '指派人员Id' ,
    `assign_status` int(2) NOT NULL DEFAULT 0 COMMENT '指派状态 1- 作废 0- 指派',
    `progress` varchar(255)  DEFAULT NULL COMMENT '专利进度',
    `filing_time` varchar(255)  DEFAULT NULL COMMENT '专利申报时间',
    `write_time` varchar(255)  DEFAULT NULL COMMENT '专利写好时间',
    `authorization_time` varchar(255)  DEFAULT NULL COMMENT '授权下证时间',
    `patent_num` varchar(255)  DEFAULT NULL COMMENT '专利数',
    `forewarn_time` varchar(255)  DEFAULT NULL COMMENT '年费预警时间',
    `nick_name`  varchar(255)  DEFAULT NULL COMMENT '创建者名字',
    `create_by`    varchar(255)  DEFAULT NULL COMMENT '创建者',
    `update_by`    varchar(255)  DEFAULT NULL COMMENT '更新者',
    `create_time`  datetime      DEFAULT NULL COMMENT '创建日期',
    `update_time`  datetime      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目进度管理';