
-- Table structure for account_account
-- ----------------------------
DROP TABLE IF EXISTS `account_account`;
CREATE TABLE `account_account` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `username` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `gesture` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `phone_no` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `sys_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `session_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `real_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for account_log
-- ----------------------------
DROP TABLE IF EXISTS `account_log`;
CREATE TABLE `account_log` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `account_id` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `op_type` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `role_type` smallint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';

-- ----------------------------
-- Table structure for account_token
-- ----------------------------
DROP TABLE IF EXISTS `account_token`;
CREATE TABLE `account_token` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `account_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_access_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for account_user_basic_info
-- ----------------------------
DROP TABLE IF EXISTS `account_user_basic_info`;
CREATE TABLE `account_user_basic_info` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `active` smallint(6) DEFAULT '0',
  `phone_no` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `nick_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `icon` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `gender` smallint(6) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `city_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `province_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `company_name` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `position` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `qq` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `user_real_info_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for account_user_real_info
-- ----------------------------
DROP TABLE IF EXISTS `account_user_real_info`;
CREATE TABLE `account_user_real_info` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `real_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `id_no` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `id_end_time` datetime DEFAULT NULL,
  `id_card_front_img_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `id_card_back_img_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `authentication_time` datetime DEFAULT NULL,
  `approve_status` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='account_user_real_info';

-- ----------------------------
-- Table structure for course_approve_his
-- ----------------------------
DROP TABLE IF EXISTS `course_approve_his`;
CREATE TABLE `course_approve_his` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `course_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `approver_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `operate` tinyint(4) DEFAULT NULL,
  `reason` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for course_attachment
-- ----------------------------
DROP TABLE IF EXISTS `course_attachment`;
CREATE TABLE `course_attachment` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `course_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `course_attachment_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for course_basic
-- ----------------------------
DROP TABLE IF EXISTS `course_basic`;
CREATE TABLE `course_basic` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `active` smallint(6) DEFAULT '0',
  `course_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `thumbnail_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `main_category_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `sub_category_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `detail_category_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `descriptions` text COLLATE utf8_bin,
  `teacher_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `tags` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `public` smallint(6) DEFAULT NULL,
  `on_shelves` smallint(6) DEFAULT NULL,
  `approve_status` int(11) DEFAULT NULL,
  `access_count` int(11) DEFAULT '0',
  `chapter_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `class_hour` FLOAT (6) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for course_category
-- ----------------------------
DROP TABLE IF EXISTS `course_category`;
CREATE TABLE `course_category` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `active` smallint(6) DEFAULT '0',
  `code` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `descriptions` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `app_icon` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for course_chapter
-- ----------------------------
DROP TABLE IF EXISTS `course_chapter`;
CREATE TABLE `course_chapter` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `active` smallint(6) DEFAULT '0',
  `course_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `chapter_index` int(11) DEFAULT NULL,
  `second` int(11) DEFAULT NULL,
  `parent_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `descriptions` varchar(5000) COLLATE utf8_bin DEFAULT NULL,
  `tags` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `resource_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `resource_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for course_comment
-- ----------------------------
DROP TABLE IF EXISTS `course_comment`;
CREATE TABLE `course_comment` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `active` smallint(6) DEFAULT '0',
  `course_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `star` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `comment` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for course_exercise
-- ----------------------------
DROP TABLE IF EXISTS `course_exercise`;
CREATE TABLE `course_exercise` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `course_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `exercise_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for course_learning_state
-- ----------------------------
DROP TABLE IF EXISTS `course_learning_state`;
CREATE TABLE `course_learning_state` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `course_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `resource_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `start_point` int(11) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for exam_enroll
-- ----------------------------
DROP TABLE IF EXISTS `exam_enroll`;
CREATE TABLE `exam_enroll` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '标识',
  `exam_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '标识',
  `type` tinyint(4) DEFAULT NULL COMMENT '报名或签到',
  `date` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='考试报名和签到';

-- ----------------------------
-- Table structure for exam_notice
-- ----------------------------
DROP TABLE IF EXISTS `exam_notice`;
CREATE TABLE `exam_notice` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active` smallint(6) DEFAULT '0' COMMENT '活性状态: 0激活 1未激活',
  `notice_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '考试名称',
  `exam_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `thumbnail_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '缩略图Id',
  `description` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `begin_time` datetime DEFAULT NULL COMMENT '考试开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '考试结束时间',
  `enroll_start_time` datetime DEFAULT NULL COMMENT '报名开始时间',
  `enroll_end_time` datetime DEFAULT NULL COMMENT '报名结束时间',
  `duration` int(11) DEFAULT NULL COMMENT '考试时长min',
  `pass_score` int(11) DEFAULT NULL COMMENT '几个线',
  `public` smallint(6) DEFAULT NULL COMMENT '是否公开考试，公开考试全平台可见考试通知',
  `type` smallint(4) DEFAULT NULL COMMENT '1,集中考试;2,普通考试',
  `need_enroll` smallint(6) DEFAULT NULL COMMENT '是否允许报名',
  `position` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '考试地点',
  `content` text COLLATE utf8_bin COMMENT '通知内容',
  `instructions` text COLLATE utf8_bin COMMENT '考试须知，开始开始前的提醒',
  `total_join_num` int(11) DEFAULT NULL COMMENT '报考人数',
  `org_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '组织结构',
  `publish` smallint(6) DEFAULT NULL COMMENT '发布',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='考试通知';

-- ----------------------------
-- Table structure for exam_notice_user
-- ----------------------------
DROP TABLE IF EXISTS `exam_notice_user`;
CREATE TABLE `exam_notice_user` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '考试id',
  `exam_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '考试id',
  `read_status` smallint(6) DEFAULT '0' COMMENT '1已读  0未读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户读通知';

-- ----------------------------
-- Table structure for exam_paper_template
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_template`;
CREATE TABLE `exam_paper_template` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `exam_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '考试id',
  `paper_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '试卷ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='考试报名和签到';

-- ----------------------------
-- Table structure for exam_score
-- ----------------------------
DROP TABLE IF EXISTS `exam_score`;
CREATE TABLE `exam_score` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `exam_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `question_set_ins_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `max_score` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for exam_user_paper
-- ----------------------------
DROP TABLE IF EXISTS `exam_user_paper`;
CREATE TABLE `exam_user_paper` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `user_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户',
  `exam_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '考试',
  `question_set_ins_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '实例化',
  `start_time` datetime DEFAULT NULL COMMENT '模板id',
  `submit_time` datetime DEFAULT NULL COMMENT '用户',
  `score` float DEFAULT NULL COMMENT '用户得分',
  `pass` smallint(6) DEFAULT NULL COMMENT '是否通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户考试数据记录';

-- ----------------------------
-- Table structure for file_storage
-- ----------------------------
DROP TABLE IF EXISTS `file_storage`;
CREATE TABLE `file_storage` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT NULL,
  `file_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `suffix` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `storage_group` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `storage_path` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `owner_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `video_time_len` int(20) DEFAULT NULL,
  `transform_file_path` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '转换后的文件存储路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for org_group
-- ----------------------------
DROP TABLE IF EXISTS `org_group`;
CREATE TABLE `org_group` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `code` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `org_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for org_group_user_map
-- ----------------------------
DROP TABLE IF EXISTS `org_group_user_map`;
CREATE TABLE `org_group_user_map` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `active` smallint(6) DEFAULT '0',
  `group_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for org_org
-- ----------------------------
DROP TABLE IF EXISTS `org_org`;
CREATE TABLE `org_org` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `code` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `parent_org_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for org_role
-- ----------------------------
DROP TABLE IF EXISTS `org_role`;
CREATE TABLE `org_role` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `active` smallint(6) DEFAULT '0',
  `code` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `org_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `chief_role_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for pub_check_code
-- ----------------------------
DROP TABLE IF EXISTS `pub_check_code`;
CREATE TABLE `pub_check_code` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `phone_no` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `sys_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for pub_sys_info
-- ----------------------------
DROP TABLE IF EXISTS `pub_sys_info`;
CREATE TABLE `pub_sys_info` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `sys_name` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `sys_host` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `sys_port` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- ----------------------------
-- Table structure for qb_question
-- ----------------------------
DROP TABLE IF EXISTS `qb_question`;
CREATE TABLE `qb_question` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active` smallint(6) DEFAULT '0' COMMENT '活性状态: 0激活 1未激活',
  `type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '试题类型',
  `has_children` int(11) DEFAULT NULL COMMENT '子',
  `parent_id` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '父',
  `sub_index` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `main_category_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '主分类',
  `sub_category_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '次分类',
  `public` smallint(4) DEFAULT NULL COMMENT '是否公开',
  `statement` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '题干',
  `tags` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '标签',
  `answer` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '答案',
  `analysis` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '分析',
  `context_a` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '选项A',
  `context_b` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '选项B',
  `context_c` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '选项C',
  `context_d` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '选项D',
  `context_e` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '选项E',
  `context_f` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '选项E',
  `context_g` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '选项F',
  `context_h` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '选项H',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='qb_question';

-- ----------------------------
-- Table structure for qb_question_type
-- ----------------------------
DROP TABLE IF EXISTS `qb_question_type`;
CREATE TABLE `qb_question_type` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `question_type` smallint(4) DEFAULT NULL COMMENT '类型',
  `question_type_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '类型名称',
  `question_type_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '编码',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='qb_question_type';

-- ----------------------------
-- Table structure for question_set_instance
-- ----------------------------
DROP TABLE IF EXISTS `question_set_instance`;
CREATE TABLE `question_set_instance` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `question_set_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '模板id',
  `type` int(4) DEFAULT NULL,
  `user_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='模板实例关系';

-- ----------------------------
-- Table structure for study_plan_basic
-- ----------------------------
DROP TABLE IF EXISTS `study_plan_basic`;
CREATE TABLE `study_plan_basic` (
  `id` varchar(40) NOT NULL,
  `deleted` smallint(6) DEFAULT NULL,
  `creator_id` varchar(40) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_id` varchar(40) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `active` smallint(6) DEFAULT NULL,
  `plan_name` varchar(128) DEFAULT NULL COMMENT '学习计划名称',
  `start_time` datetime DEFAULT NULL COMMENT '发布时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `thumbnail_id` varchar(40) DEFAULT NULL COMMENT '图片源',
  `plan_description` text COMMENT '学习计划描述',
  `required_hours` smallint(6) DEFAULT NULL COMMENT '课程必修学时数',
  `total_hours` smallint(6) DEFAULT NULL COMMENT '课程总学时',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for study_plan_detail
-- ----------------------------
DROP TABLE IF EXISTS `study_plan_detail`;
CREATE TABLE `study_plan_detail` (
  `id` varchar(40) NOT NULL,
  `deleted` smallint(6) DEFAULT NULL,
  `type` smallint(6) DEFAULT NULL COMMENT '类型（1：练习，2：课程）',
  `plan_id` varchar(40) DEFAULT NULL COMMENT '学习计划ID',
  `associate_id` varchar(40) DEFAULT NULL COMMENT '关联ID',
  `is_required` smallint(6) DEFAULT NULL COMMENT '是否必修（1：是，0：否）',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for suggestion
-- ----------------------------
DROP TABLE IF EXISTS `suggestion`;
CREATE TABLE `suggestion` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '用户',
  `pic_ids` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '图片id',
  `suggestion` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '意见内容',
  `type` smallint(4) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='意见反馈';

-- ----------------------------
-- Table structure for sys_ad_prompt
-- ----------------------------
DROP TABLE IF EXISTS `sys_ad_prompt`;
CREATE TABLE `sys_ad_prompt` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `ad_index` int(11) DEFAULT NULL COMMENT '个人风采图片ID',
  `descriptions` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '介绍',
  `resource_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '最高职称',
  `resource_type` int(6) DEFAULT NULL COMMENT '最高学历',
  `web_ad_img_Id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证照片正面图片id',
  `mobil_ad_img_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证照片背面图片id',
  `bg_color` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证照片正面图片id',
  `is_valid` smallint(6) DEFAULT NULL COMMENT '身份证照片背面图片id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='sys_ad_prompt';

-- ----------------------------
-- Table structure for teacher_antecedents
-- ----------------------------
DROP TABLE IF EXISTS `teacher_antecedents`;
CREATE TABLE `teacher_antecedents` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active` smallint(6) DEFAULT '0' COMMENT '活性状态: 0激活 1未激活',
  `teacher_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '讲师id',
  `type` smallint(6) DEFAULT NULL COMMENT '类型',
  `org_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '学校名称/公司名称/项目名称',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `title` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '学历名称/公司职务/项目角色/职称名称',
  `title_img_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '图片id',
  `descriptions` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='teacher_antecedents';

-- ----------------------------
-- Table structure for teacher_brief
-- ----------------------------
DROP TABLE IF EXISTS `teacher_brief`;
CREATE TABLE `teacher_brief` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active` smallint(6) DEFAULT '0' COMMENT '活性状态: 0激活 1未激活',
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `user_real_info_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '用户实名信息id',
  `intro_pic_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '个人风采图片ID',
  `introduction` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '介绍',
  `highest_title` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '最高职称',
  `highest_edu` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '最高学历',
  `approve_status` tinyint(4) DEFAULT NULL COMMENT '身份证照片背面图片id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='teacher_brief';

-- ----------------------------
-- Table structure for tp_paper_ques_species
-- ----------------------------
DROP TABLE IF EXISTS `tp_paper_ques_species`;
CREATE TABLE `tp_paper_ques_species` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active` smallint(6) DEFAULT '0' COMMENT '活性状态: 0激活 1未激活',
  `question_set_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `type` smallint(4) DEFAULT NULL COMMENT '类型',
  `descriptions` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '简介',
  `species_index` int(11) DEFAULT NULL COMMENT '排序',
  `question_num` int(11) DEFAULT NULL COMMENT '体量',
  `species_score` float DEFAULT '0' COMMENT '总分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='大题';

-- ----------------------------
-- Table structure for tp_question_in
-- ----------------------------
DROP TABLE IF EXISTS `tp_question_in`;
CREATE TABLE `tp_question_in` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active` smallint(6) DEFAULT '0' COMMENT '活性状态: 0激活 1未激活',
  `question_set_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '题集试卷',
  `question_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '题目',
  `species_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '大题',
  `question_index` int(11) DEFAULT NULL COMMENT '排序',
  `score` float DEFAULT '0' COMMENT '分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='小题';

-- ----------------------------
-- Table structure for tp_question_set
-- ----------------------------
DROP TABLE IF EXISTS `tp_question_set`;
CREATE TABLE `tp_question_set` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `creator_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active` smallint(6) DEFAULT '0' COMMENT '活性状态: 0激活 1未激活',
  `name` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `type` smallint(4) DEFAULT NULL COMMENT '类型',
  `public` smallint(6) DEFAULT NULL COMMENT '公开',
  `descriptions` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '简介',
  `tags` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '标签',
  `question_num` int(11) DEFAULT NULL,
  `strategy` smallint(6) DEFAULT NULL,
  `total_score` float(11,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='题集试卷';

-- ----------------------------
-- Table structure for tp_user_answer
-- ----------------------------
DROP TABLE IF EXISTS `tp_user_answer`;
CREATE TABLE `tp_user_answer` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标识',
  `deleted` smallint(6) DEFAULT '0' COMMENT '逻辑删除: 0可见 1删除',
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '用户',
  `question_set_ins_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '实例id',
  `question_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '题目id',
  `user_answer` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '答案',
  `is_right` smallint(6) DEFAULT NULL COMMENT '交卷时间',
  `real_score` float DEFAULT NULL COMMENT '该题实际得分',
  `submit_time` datetime DEFAULT NULL COMMENT '交卷时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户答题';

-- ----------------------------
-- Table structure for user_operate
-- ----------------------------
DROP TABLE IF EXISTS `user_operate`;
CREATE TABLE `user_operate` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `deleted` smallint(6) DEFAULT '0',
  `active` smallint(6) DEFAULT '0',
  `object_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `user_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';
SET FOREIGN_KEY_CHECKS=1;
