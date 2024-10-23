/*
SQLyog Ultimate v11.42 (64 bit)
MySQL - 8.0.21 : Database - bbs_cloud
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bbs_cloud` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bbs_cloud`;

/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` varchar(32) NOT NULL COMMENT '唯一索引',
  `name` varchar(100) NOT NULL COMMENT '活动名称',
  `content` varchar(200) NOT NULL COMMENT '活动内容',
  `status` int NOT NULL COMMENT '活动状态',
  `activity_type` int NOT NULL COMMENT '活动类型',
  `amount` int NOT NULL COMMENT '福袋或者红包数量',
  `quota` int DEFAULT NULL COMMENT '红包总额',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `start_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '终止时间',
  PRIMARY KEY (`id`),
  KEY `status` (`status`,`activity_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动表';

/*Data for the table `activity` */

insert  into `activity`(`id`,`name`,`content`,`status`,`activity_type`,`amount`,`quota`,`create_date`,`update_date`,`start_date`,`end_date`) values ('9b35d934344d460abebfd213f152d60c','积分兑换福袋活动','积分兑换福袋活动',3,3,20,NULL,'2023-11-18 18:54:21','2023-11-18 18:55:54','2023-11-18 00:00:00','2023-11-18 18:55:54'),('a10cece45771436ab7b34a9be71d0b27','红包活动','红包活动',3,2,20,100,'2023-11-18 18:51:39','2023-11-18 18:53:17','2023-11-18 00:00:00','2023-11-18 18:53:17'),('c94b67dd893f475097f9fee4dab8da41','积分兑换金币活动','积分兑换金币活动',3,4,0,2000,'2023-11-18 18:56:34','2023-11-18 18:57:36','2023-11-18 00:00:00','2023-11-18 18:57:36'),('fafe0c51a7354d328ffb8dced00e2cbf','福袋活动','福袋活动',3,1,20,NULL,'2023-11-18 18:40:17','2023-11-18 18:41:50','2023-11-18 00:00:00','2023-11-18 18:41:50');

/*Table structure for table `activity_gold` */

DROP TABLE IF EXISTS `activity_gold`;

CREATE TABLE `activity_gold` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `activity_id` varchar(32) DEFAULT NULL COMMENT '活动ID',
  `quota` int DEFAULT NULL COMMENT '金币总数量',
  `unused_quota` int DEFAULT NULL COMMENT '未使用金币总数量',
  `used_quota` int DEFAULT NULL COMMENT '已使用金币总数量',
  `status` int DEFAULT NULL COMMENT '金币使用记录状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activity_gold` */

insert  into `activity_gold`(`id`,`activity_id`,`quota`,`unused_quota`,`used_quota`,`status`) values ('755936104c23472ba5be0fbefc81a1cb','c94b67dd893f475097f9fee4dab8da41',2000,2000,0,1);

/*Table structure for table `backpack` */

DROP TABLE IF EXISTS `backpack`;

CREATE TABLE `backpack` (
  `id` varchar(32) NOT NULL COMMENT '唯一索引',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `gold` int NOT NULL COMMENT '金币数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `backpack` */

insert  into `backpack`(`id`,`user_id`,`gold`) values ('69767d26c0ed434ea0635e39a166cf13','3cf432b4cd4a41ceb06952333a176cbd',0);

/*Table structure for table `backpack_gift` */

DROP TABLE IF EXISTS `backpack_gift`;

CREATE TABLE `backpack_gift` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `backpack_id` varchar(32) NOT NULL COMMENT '用户背包ID',
  `gift_type` int NOT NULL COMMENT '礼物类型',
  `amount` int NOT NULL COMMENT '礼物数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `backpack_gift` */

insert  into `backpack_gift`(`id`,`backpack_id`,`gift_type`,`amount`) values ('0ae6e83df7794180842937a194aa8c1c','69767d26c0ed434ea0635e39a166cf13',6,0),('14841cb075564684a5f8d1b33fbaf7de','69767d26c0ed434ea0635e39a166cf13',8,0),('60cfb7001af34e22bd14d7e868654315','69767d26c0ed434ea0635e39a166cf13',2,0),('633e98a8d39243cbac220fd4af5ba56f','69767d26c0ed434ea0635e39a166cf13',3,0),('77781608a7ab432596a0989a2b5cabe6','69767d26c0ed434ea0635e39a166cf13',9,0),('94808f19147b4717b93e17662deb2956','69767d26c0ed434ea0635e39a166cf13',5,0),('9489f6994a3b41a08e3e8d833f6dc7a0','69767d26c0ed434ea0635e39a166cf13',1,0),('a6803525886b49c0a284262e4c7cc941','69767d26c0ed434ea0635e39a166cf13',7,0),('d77deb8a11a041bea1bb17f02be46248','69767d26c0ed434ea0635e39a166cf13',4,0),('e446d60f0b664a7ab67a7f4f27123bca','69767d26c0ed434ea0635e39a166cf13',10,0);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `essay_id` varchar(32) NOT NULL COMMENT '文章ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `entity_id` varchar(32) NOT NULL COMMENT '实体ID',
  `entity_type` int NOT NULL COMMENT '实体类型',
  `content` varchar(200) NOT NULL COMMENT '内容',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `status` int NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

/*Table structure for table `essay` */

DROP TABLE IF EXISTS `essay`;

CREATE TABLE `essay` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `title` varchar(32) NOT NULL COMMENT '帖子标题',
  `content` varchar(500) NOT NULL COMMENT '帖子内容',
  `status` int NOT NULL COMMENT '帖子状态',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `comment_count` int NOT NULL COMMENT '评论数量',
  `like_count` int NOT NULL COMMENT '点赞数量',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `essay` */

/*Table structure for table `essay_topic` */

DROP TABLE IF EXISTS `essay_topic`;

CREATE TABLE `essay_topic` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一索引',
  `essay_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章ID',
  `rule` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '话题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `essay_topic` */

/*Table structure for table `like_record` */

DROP TABLE IF EXISTS `like_record`;

CREATE TABLE `like_record` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `essay_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '实体ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `like_record` */

/*Table structure for table `login_ticket` */

DROP TABLE IF EXISTS `login_ticket`;

CREATE TABLE `login_ticket` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `expired` datetime NOT NULL COMMENT '有效时间',
  `status` int NOT NULL COMMENT '令牌状态',
  `ticket` varchar(32) NOT NULL COMMENT '令牌',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='令牌表';

/*Data for the table `login_ticket` */

insert  into `login_ticket`(`id`,`user_id`,`expired`,`status`,`ticket`) values ('122c29f79a494fbc94aecbab62d0b92d','3cf432b4cd4a41ceb06952333a176cbd','2023-11-21 13:49:33',0,'5d31b4dfae70400c9b91ad1cb136b23a');

/*Table structure for table `lucky_bag` */

DROP TABLE IF EXISTS `lucky_bag`;

CREATE TABLE `lucky_bag` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `activity_id` varchar(32) NOT NULL COMMENT '活动ID',
  `gift_type` int NOT NULL COMMENT '礼物类型',
  `status` int NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='福袋表';

/*Data for the table `lucky_bag` */

insert  into `lucky_bag`(`id`,`activity_id`,`gift_type`,`status`) values ('01444781e4b94a89a3e744efb845cbb0','fafe0c51a7354d328ffb8dced00e2cbf',5,3),('12e3a9d84cb24cf8b64fc45900936aa8','9b35d934344d460abebfd213f152d60c',9,3),('1fd8b27541844f6d90691254a0783aa2','9b35d934344d460abebfd213f152d60c',4,3),('240444a97bd8441782736b257f2ec181','fafe0c51a7354d328ffb8dced00e2cbf',2,3),('2b559eff452c49ea8e60257ca08e9759','fafe0c51a7354d328ffb8dced00e2cbf',7,3),('424e0597106d4dfbbcadeae51c725706','9b35d934344d460abebfd213f152d60c',8,3),('4581f86281e84b608ec760d8b16c1cea','fafe0c51a7354d328ffb8dced00e2cbf',6,3),('4f6af9b285d847f081e9c9e495c6dc64','9b35d934344d460abebfd213f152d60c',5,3),('55f70d633e1249bd85d97f1f38f2a7fc','fafe0c51a7354d328ffb8dced00e2cbf',5,3),('67dafd5a995144d08a4b881ad64c3934','9b35d934344d460abebfd213f152d60c',8,3),('6f6521352560405bbc1a04e250fdf8b4','9b35d934344d460abebfd213f152d60c',10,3),('6faa5586f2eb4f708ebeb5df6c797391','9b35d934344d460abebfd213f152d60c',7,3),('7288a2177157432a8e57fc2002870241','9b35d934344d460abebfd213f152d60c',10,3),('73f4fa6869d7465d86d854ce0f16bbbf','fafe0c51a7354d328ffb8dced00e2cbf',1,3),('82bc6855641242dc8fcd288e18bffadd','9b35d934344d460abebfd213f152d60c',2,3),('8a8aa64f26294e91874a4bc6ecd02650','9b35d934344d460abebfd213f152d60c',9,3),('8ed9190cd03140daac5ccd8522b3fd6a','9b35d934344d460abebfd213f152d60c',2,3),('9118f0d8347d4486b620cb29da1b802a','fafe0c51a7354d328ffb8dced00e2cbf',7,3),('93f1ea1cb8e24cee98a98426a48bd166','9b35d934344d460abebfd213f152d60c',7,3),('9bb2d357a0bc4b2da73c074c9f98c825','fafe0c51a7354d328ffb8dced00e2cbf',7,3),('a661a7ddd88145dc8a423b26e8280eb0','fafe0c51a7354d328ffb8dced00e2cbf',8,3),('a822a2205cc94d5ebb758547118b1f34','fafe0c51a7354d328ffb8dced00e2cbf',6,3),('a967b37069a848b681ce958fd5275106','9b35d934344d460abebfd213f152d60c',7,3),('b2f6d2bd9eef4273bccb61441662ff58','fafe0c51a7354d328ffb8dced00e2cbf',8,3),('b3443012639f4147a7fce5adea317f3c','fafe0c51a7354d328ffb8dced00e2cbf',8,3),('bce95c8352ea4863b705411f2eb5e58e','fafe0c51a7354d328ffb8dced00e2cbf',10,3),('c2f517eb81664617bafbfb13857d4be0','9b35d934344d460abebfd213f152d60c',5,3),('c43ae45db5084f06a0be3ef32709703e','fafe0c51a7354d328ffb8dced00e2cbf',3,3),('cd17709e23704a23a2374b1159ccd405','fafe0c51a7354d328ffb8dced00e2cbf',10,3),('cd7c9ff41b3343dd861d7c7554bcff39','9b35d934344d460abebfd213f152d60c',1,3),('d115adab985a4cd5992e9b417b7ce907','fafe0c51a7354d328ffb8dced00e2cbf',6,3),('d8395083f68344468e4f53499bc340c9','fafe0c51a7354d328ffb8dced00e2cbf',6,3),('da865fd9a6464cdca8e2266d18756941','9b35d934344d460abebfd213f152d60c',2,3),('dc65edc297df4247808b67fcedec4a9e','9b35d934344d460abebfd213f152d60c',5,3),('df9dec6bfc4341438d27346c16afc4ae','9b35d934344d460abebfd213f152d60c',6,3),('e3421516788f407692bcb533d54eddad','fafe0c51a7354d328ffb8dced00e2cbf',8,3),('ed8d5f884731439c89e80001b9649e95','fafe0c51a7354d328ffb8dced00e2cbf',2,3),('ee9023d3a18a431295213d208bd69e13','fafe0c51a7354d328ffb8dced00e2cbf',10,3),('eff2627fb1f4434db2937d7ae4bf8ae8','9b35d934344d460abebfd213f152d60c',3,3),('fed59050b62540289a3f99457a7b0e66','9b35d934344d460abebfd213f152d60c',6,3);

/*Table structure for table `lucky_bag_record` */

DROP TABLE IF EXISTS `lucky_bag_record`;

CREATE TABLE `lucky_bag_record` (
  `id` varchar(32) NOT NULL COMMENT '抢福袋记录ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `activity_id` varchar(32) DEFAULT NULL COMMENT '活动ID',
  `lucky_bag_id` varchar(32) DEFAULT NULL COMMENT '福袋ID',
  `gift_type` int DEFAULT NULL COMMENT '礼物类型',
  `create_date` datetime DEFAULT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `lucky_bag_record` */

insert  into `lucky_bag_record`(`id`,`user_id`,`activity_id`,`lucky_bag_id`,`gift_type`,`create_date`) values ('1','11','fafe0c51a7354d328ffb8dced00e2cbf','11',1,'2023-11-23 20:08:28');

/*Table structure for table `play_tour_record` */

DROP TABLE IF EXISTS `play_tour_record`;

CREATE TABLE `play_tour_record` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `send_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送人',
  `take_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接收人',
  `gift_type` int NOT NULL COMMENT '礼物类型',
  `entity_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '打赏内容ID',
  `entity_type` int NOT NULL COMMENT '打赏内容类型',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `play_tour_record` */

/*Table structure for table `recharge_record` */

DROP TABLE IF EXISTS `recharge_record`;

CREATE TABLE `recharge_record` (
  `id` varchar(32) NOT NULL COMMENT '订单号',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `gold` int NOT NULL COMMENT '金币数量',
  `price` int NOT NULL COMMENT '充值额度',
  `desc` varchar(10) NOT NULL COMMENT '描述',
  `status` int NOT NULL COMMENT '订单状态',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `finishDate` datetime DEFAULT NULL COMMENT '完成时间',
  `cancelDate` datetime DEFAULT NULL COMMENT '取消时间',
  `deleteDate` datetime DEFAULT NULL COMMENT '删除时间',
  `trade_no` varchar(28) DEFAULT NULL COMMENT '支付宝订单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `recharge_record` */

/*Table structure for table `red_packet` */

DROP TABLE IF EXISTS `red_packet`;

CREATE TABLE `red_packet` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `activity_id` varchar(32) NOT NULL COMMENT '活动ID',
  `gold` int NOT NULL COMMENT '红包额度',
  `status` int NOT NULL COMMENT '红包状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包表';

/*Data for the table `red_packet` */

insert  into `red_packet`(`id`,`activity_id`,`gold`,`status`) values ('17d9a60f6b75409da7a3c718a9241875','a10cece45771436ab7b34a9be71d0b27',5,3),('1f30466887aa4921906aab1bd4f2b44f','a10cece45771436ab7b34a9be71d0b27',5,3),('2887b38443614de9996ec4450cabf3c5','a10cece45771436ab7b34a9be71d0b27',5,3),('3650755f666140a0b283089835b243e0','a10cece45771436ab7b34a9be71d0b27',5,3),('3d5b37a55aa3468784ef187363b2357c','a10cece45771436ab7b34a9be71d0b27',5,3),('3dff310e5a21425eb8f10c5745d41f63','a10cece45771436ab7b34a9be71d0b27',5,3),('538fa9559d2343039fccd1ccd9c31842','a10cece45771436ab7b34a9be71d0b27',5,3),('5c268908f4b249c2817bbc62f576a85d','a10cece45771436ab7b34a9be71d0b27',5,3),('6203afedd2c748e4aed7a697dcee85b3','a10cece45771436ab7b34a9be71d0b27',5,3),('77524283d5c84cf58512310a888e01d1','a10cece45771436ab7b34a9be71d0b27',5,3),('7966c35b8f5b46e9b1463d732ff36eb1','a10cece45771436ab7b34a9be71d0b27',5,3),('90655af5075b442bbeb9017c09d0ad3b','a10cece45771436ab7b34a9be71d0b27',5,3),('946385b7d0e74e3aa2664b33e9384904','a10cece45771436ab7b34a9be71d0b27',5,3),('a808cc186f93482eacb10067085625b8','a10cece45771436ab7b34a9be71d0b27',5,3),('cd24794b67df4861a3fd187e2668c12a','a10cece45771436ab7b34a9be71d0b27',5,3),('d880bbc136444782ab536d329352fbe0','a10cece45771436ab7b34a9be71d0b27',5,3),('e7a1e710b5d54e1a977ffbcbef1b96ce','a10cece45771436ab7b34a9be71d0b27',5,3),('ee12135019c147dabf9015acaa2d2efa','a10cece45771436ab7b34a9be71d0b27',5,3),('f17ef1e4e7a746ab8c324173475d527a','a10cece45771436ab7b34a9be71d0b27',5,3),('fa0c19f4abb0419aad0b6b204f2993f9','a10cece45771436ab7b34a9be71d0b27',5,3);

/*Table structure for table `red_packet_record` */

DROP TABLE IF EXISTS `red_packet_record`;

CREATE TABLE `red_packet_record` (
  `id` varchar(32) NOT NULL COMMENT '抢红包记录ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `activity_id` varchar(32) DEFAULT NULL COMMENT '活动ID',
  `red_packet_id` varchar(32) DEFAULT NULL COMMENT '红包ID',
  `gold` int DEFAULT NULL COMMENT '金币数量',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `red_packet_record` */

/*Table structure for table `score_card` */

DROP TABLE IF EXISTS `score_card`;

CREATE TABLE `score_card` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `score` int NOT NULL COMMENT '积分数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `score_card` */

insert  into `score_card`(`id`,`user_id`,`score`) values ('fe1dd44c8ffb48189384beac3d9cc9a9','3cf432b4cd4a41ceb06952333a176cbd',0);

/*Table structure for table `score_consume_record` */

DROP TABLE IF EXISTS `score_consume_record`;

CREATE TABLE `score_consume_record` (
  `id` varchar(32) NOT NULL COMMENT '积分消费记录',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动ID',
  `type` int NOT NULL COMMENT '消费类型',
  `score_consume` int NOT NULL COMMENT '积分消费数量',
  `gold` int DEFAULT NULL COMMENT '兑换金币数量',
  `lucky_bag_id` varchar(32) DEFAULT NULL COMMENT '兑换福袋ID',
  `gift_type` int DEFAULT NULL COMMENT '礼物类型',
  `create_date` datetime NOT NULL COMMENT '消费时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `score_consume_record` */

/*Table structure for table `service_gift` */

DROP TABLE IF EXISTS `service_gift`;

CREATE TABLE `service_gift` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一索引',
  `gift_type` int NOT NULL COMMENT '礼物类型',
  `amount` int NOT NULL COMMENT '礼物总数量',
  `used_amount` int NOT NULL COMMENT '礼物已使用数量',
  `unused_amount` int NOT NULL COMMENT '礼物未使用数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gift_type` (`gift_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='礼物表';

/*Data for the table `service_gift` */

insert  into `service_gift`(`id`,`gift_type`,`amount`,`used_amount`,`unused_amount`) values ('01944b76038a480690a31c66e1501c99',1,200,1,199),('2c237bdab0ac4ff8b8309d8d387fea39',4,200,1,199),('2e420dbe41a945b1b0cc9f49276d1a32',7,200,3,197),('5396cd6253b242a3a9c63f83fd153580',2,200,3,197),('56a7b0421f8b416e8a33de89e555efaf',10,200,2,198),('59166cc6a1c14d7e96e67e4c60ea6a3e',3,200,1,199),('d637d060395e497897b0d0911f4d1a11',9,200,2,198),('db2b2dfd70d64107a0f55635ea6a3889',8,200,2,198),('e3c648b8ef2c4bbdaab65f0bc011480e',5,200,3,197),('f8c8e83b64db43f3bd5d28e021f4ce99',6,200,2,198);

/*Table structure for table `service_gold` */

DROP TABLE IF EXISTS `service_gold`;

CREATE TABLE `service_gold` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `gold` int NOT NULL COMMENT '总额',
  `used_gold` int NOT NULL COMMENT '已用额度',
  `unused_gold` int NOT NULL COMMENT '未用额度',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统金钱表';

/*Data for the table `service_gold` */

insert  into `service_gold`(`id`,`name`,`gold`,`used_gold`,`unused_gold`) values ('951f28b252bc4fd5859271fa1da2c485','SERVICE_GOLD_NAME',30000,2000,28000);

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test` */

insert  into `test`(`id`) values ('bbbbbbbbbbb'),('idasdf');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL COMMENT '唯一主键',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(6) NOT NULL COMMENT '密码后缀',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`salt`) values ('3cf432b4cd4a41ceb06952333a176cbd','test_078ce1497aa11191','C53BBBAE589E7D8EB972C01C912EA390','30cdc4');

/*Table structure for table `user_log_record` */

DROP TABLE IF EXISTS `user_log_record`;

CREATE TABLE `user_log_record` (
  `id` varchar(32) NOT NULL COMMENT '唯一索引',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `message` varchar(300) NOT NULL COMMENT '操作日志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_log_record` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
