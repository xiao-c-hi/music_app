-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (x86_64)
--
-- Host: mysql-public.ixuea.com    Database: music_api_sp3
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ad`
--

DROP TABLE IF EXISTS `ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ad` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(190) DEFAULT NULL COMMENT '标题',
  `icon` varchar(190) NOT NULL COMMENT '封面',
  `uri` varchar(190) DEFAULT NULL COMMENT '广告地址',
  `style` tinyint DEFAULT '0' COMMENT '类型',
  `position` int DEFAULT '0' COMMENT '显示位置',
  `sort` int DEFAULT '0' COMMENT '排序，值越大，越靠前',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_ad_on_user_id` (`user_id`),
  CONSTRAINT `fk_ad_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `telephone` varchar(20) NOT NULL COMMENT '电话',
  `province` varchar(20) NOT NULL COMMENT '省',
  `province_code` varchar(20) NOT NULL COMMENT '省编码',
  `city` varchar(20) NOT NULL COMMENT '市',
  `city_code` varchar(20) NOT NULL COMMENT '市编码',
  `area` varchar(20) NOT NULL COMMENT '区',
  `area_code` varchar(20) NOT NULL COMMENT '区编码',
  `detail` varchar(190) NOT NULL COMMENT '剩余的地址部分',
  `default` tinyint NOT NULL DEFAULT '0' COMMENT '是否是默认地址',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_address_on_user_id` (`user_id`),
  CONSTRAINT `fk_address_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL COMMENT '主键',
  `count` int NOT NULL DEFAULT '1' COMMENT '数量',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_cart_on_product_id_and_user_id` (`product_id`,`user_id`),
  KEY `index_cart_on_product_id` (`product_id`),
  KEY `index_cart_on_user_id` (`user_id`),
  CONSTRAINT `fk_cart_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_cart_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(190) NOT NULL COMMENT '内容',
  `parent_id` bigint DEFAULT NULL COMMENT '父级id',
  `icon` varchar(190) DEFAULT NULL COMMENT '内容',
  `sort` int DEFAULT '0' COMMENT '排序，正序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collect`
--

DROP TABLE IF EXISTS `collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collect` (
  `id` bigint NOT NULL COMMENT '主键',
  `sheet_id` bigint NOT NULL COMMENT '歌单id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_collect_on_sheet_id_and_user_id` (`sheet_id`,`user_id`),
  KEY `index_collect_sheet_id` (`sheet_id`),
  KEY `index_collect_user_id` (`user_id`),
  CONSTRAINT `fk_collect_sheet_id` FOREIGN KEY (`sheet_id`) REFERENCES `sheet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_collect_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL COMMENT '主键',
  `content` varchar(190) NOT NULL COMMENT '内容',
  `parent_id` bigint DEFAULT NULL COMMENT '被回复评论id',
  `video_id` bigint DEFAULT NULL COMMENT '视频id',
  `sheet_id` bigint DEFAULT NULL COMMENT '歌单id',
  `song_id` bigint DEFAULT NULL COMMENT '音乐id',
  `feed_id` bigint DEFAULT NULL COMMENT '动态id',
  `goods_id` bigint DEFAULT NULL,
  `likes_count` int DEFAULT '0' COMMENT '点赞数',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `media` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_comment_on_parent_id` (`parent_id`),
  KEY `index_comment_on_video_id` (`video_id`),
  KEY `index_comment_on_sheet_id` (`sheet_id`),
  KEY `index_comment_on_song_id` (`song_id`),
  KEY `index_comment_on_feed_id` (`feed_id`),
  KEY `index_comment_on_user_id` (`user_id`),
  CONSTRAINT `fk_comment_feed_id` FOREIGN KEY (`feed_id`) REFERENCES `feed` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_sheet_id` FOREIGN KEY (`sheet_id`) REFERENCES `sheet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_song_id` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_video_id` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` bigint NOT NULL COMMENT '主键',
  `valid` int DEFAULT '0' COMMENT '是否有效',
  `coupon_activity_id` bigint NOT NULL COMMENT '活动id',
  `used` tinyint NOT NULL DEFAULT '0' COMMENT '是否使用了',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `expire` datetime NOT NULL COMMENT '过期时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_coupon_on_user_id` (`user_id`),
  KEY `index_coupon_on_coupon_activity_id` (`coupon_activity_id`),
  CONSTRAINT `fk_coupon_coupon_activity_id` FOREIGN KEY (`coupon_activity_id`) REFERENCES `coupon_activity` (`id`),
  CONSTRAINT `fk_coupon_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon_activity`
--

DROP TABLE IF EXISTS `coupon_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_activity` (
  `id` bigint NOT NULL COMMENT '主键',
  `valid` bigint DEFAULT '0' COMMENT '是否有效',
  `title` varchar(30) NOT NULL COMMENT '标题',
  `start` datetime NOT NULL COMMENT '开始时间',
  `end` datetime NOT NULL COMMENT '结束时间',
  `condition` decimal(10,2) NOT NULL COMMENT '满多少元才能使用',
  `price` decimal(10,2) NOT NULL COMMENT '减多少',
  `detail` varchar(190) DEFAULT NULL COMMENT '描述',
  `quantity` int DEFAULT '-1' COMMENT '数量',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_activity_on_user_id` (`user_id`),
  CONSTRAINT `fk_activity_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feed`
--

DROP TABLE IF EXISTS `feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feed` (
  `id` bigint NOT NULL COMMENT '主键',
  `content` varchar(190) NOT NULL COMMENT '内容',
  `province` varchar(50) DEFAULT NULL COMMENT '省',
  `province_code` varchar(30) DEFAULT NULL COMMENT '省编码',
  `city` varchar(50) DEFAULT NULL COMMENT '市',
  `city_code` varchar(30) DEFAULT NULL COMMENT '市编码',
  `area` varchar(50) DEFAULT NULL COMMENT '区',
  `area_code` varchar(30) DEFAULT NULL COMMENT '区编码',
  `position` varchar(50) DEFAULT NULL COMMENT '位置名称',
  `address` varchar(190) DEFAULT NULL COMMENT '详细地址',
  `longitude` decimal(10,7) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,7) DEFAULT NULL COMMENT '纬度',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_feed_on_user_id` (`user_id`),
  CONSTRAINT `fk_feed_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend` (
  `id` bigint NOT NULL COMMENT '主键',
  `follower_id` bigint NOT NULL COMMENT '当前用户id',
  `followed_id` bigint NOT NULL COMMENT '对方id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_friend_on_follower_id_and_followed_id` (`follower_id`,`followed_id`),
  KEY `index_friend_on_follower_id` (`follower_id`),
  KEY `index_friend_on_followed_id` (`followed_id`),
  CONSTRAINT `fk_followed_id` FOREIGN KEY (`followed_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_follower_id` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `label`
--

DROP TABLE IF EXISTS `label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `label` (
  `id` bigint NOT NULL COMMENT '主键',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  `sheet_id` bigint NOT NULL COMMENT '歌单id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_label_on_tag_id_and_sheet_id_and_user_id` (`tag_id`,`sheet_id`,`user_id`),
  KEY `index_label_on_tag_id` (`tag_id`),
  KEY `index_label_on_sheet_id` (`sheet_id`),
  KEY `index_label_on_user_id` (`user_id`),
  CONSTRAINT `fk_label_sheet_id` FOREIGN KEY (`sheet_id`) REFERENCES `sheet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_label_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_label_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `like`
--

DROP TABLE IF EXISTS `like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like` (
  `id` bigint NOT NULL COMMENT '主键',
  `comment_id` bigint DEFAULT NULL COMMENT '评论id',
  `feed_id` bigint DEFAULT NULL COMMENT '动态id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_like_on_comment_id_and_user_id` (`comment_id`,`user_id`),
  UNIQUE KEY `index_like_on_feed_id_and_user_id` (`feed_id`,`user_id`),
  KEY `index_like_on_comment_id` (`comment_id`),
  KEY `index_like_on_feed_id` (`feed_id`),
  KEY `index_like_on_user_id` (`user_id`),
  CONSTRAINT `fk_like_comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `fk_like_feed_id` FOREIGN KEY (`feed_id`) REFERENCES `feed` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_like_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` bigint NOT NULL COMMENT '主键',
  `status` int DEFAULT '0' COMMENT '状态',
  `total_price` decimal(10,2) NOT NULL COMMENT '总价',
  `price` decimal(10,2) NOT NULL COMMENT '还需支付价格',
  `source` int DEFAULT '0' COMMENT '订单来源',
  `origin` int DEFAULT '0' COMMENT '支付来源',
  `channel` int DEFAULT '0' COMMENT '支付渠道',
  `number` varchar(190) NOT NULL COMMENT '订单号',
  `other` varchar(190) DEFAULT NULL COMMENT '第三方订单号',
  `product` text NOT NULL COMMENT '商品信息',
  `addr` text NOT NULL COMMENT '地址信息',
  `coup` text COMMENT '优惠券信息',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_order_on_number` (`number`),
  KEY `index_order_on_user_id` (`user_id`),
  CONSTRAINT `fk_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `icon` varchar(190) NOT NULL COMMENT '封面',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `highlight` text NOT NULL COMMENT '亮点',
  `detail` text NOT NULL COMMENT '描述',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `spec` text,
  `sku` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_product_on_user_id` (`user_id`),
  CONSTRAINT `fk_product_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relation`
--

DROP TABLE IF EXISTS `relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation` (
  `id` bigint NOT NULL COMMENT '主键',
  `song_id` bigint NOT NULL COMMENT '音乐id',
  `sheet_id` bigint NOT NULL COMMENT '歌单id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_relation_on_song_id_and_sheet_id_and_user_id` (`song_id`,`sheet_id`,`user_id`),
  KEY `index_relation_on_song_id` (`song_id`),
  KEY `index_relation_on_sheet_id` (`sheet_id`),
  KEY `index_relation_on_user_id` (`user_id`),
  CONSTRAINT `fk_relation_sheet_id` FOREIGN KEY (`sheet_id`) REFERENCES `sheet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_relation_song_id` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_relation_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource` (
  `id` bigint NOT NULL COMMENT '主键',
  `uri` varchar(190) NOT NULL COMMENT '地址',
  `style` tinyint DEFAULT '0' COMMENT '类型，0：图片；10：视频',
  `feed_id` bigint NOT NULL COMMENT '动态id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_resource_on_uri` (`uri`),
  KEY `index_resource_on_feed_id` (`feed_id`),
  KEY `index_resource_on_user_id` (`user_id`),
  CONSTRAINT `fk_resource_feed_id` FOREIGN KEY (`feed_id`) REFERENCES `feed` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_resource_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(30) DEFAULT NULL COMMENT '名称，例如：管理员',
  `value` varchar(30) DEFAULT NULL COMMENT '唯一标识，例如：admin',
  `rules` text COMMENT '规则名称，值为规则唯一值，逗号分割',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态，0：正常；10：禁用',
  `sort` int DEFAULT '1000000' COMMENT '排序，越小越靠前，如果相同，根据菜单创建倒序',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_rule_value` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rule` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(30) DEFAULT NULL COMMENT '名称，例如：用户管理，添加用户',
  `name` varchar(100) DEFAULT NULL,
  `icon` varchar(30) DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(190) DEFAULT NULL COMMENT '跳转的网址，如果没有值，就是默认处理',
  `component` varchar(190) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL COMMENT '请求方式，例如：post',
  `condition` varchar(190) DEFAULT NULL COMMENT '条件,有值表示要验证条件',
  `parent_id` bigint DEFAULT NULL COMMENT '父级id，用来实现多层级',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态，0：正常；10：禁用',
  `hide_menu` tinyint NOT NULL DEFAULT '0' COMMENT '是否显示，0：显示；1：隐藏',
  `order_no` int DEFAULT '1000000' COMMENT '排序，越小越靠前，如果相同，根据菜单创建倒序',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `value` varchar(30) DEFAULT NULL COMMENT '唯一标识，例如：user:add',
  `uri` varchar(190) DEFAULT NULL COMMENT '资源地址，例如：/admins/users',
  `current_active_menu` varchar(190) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `id` bigint NOT NULL COMMENT '主键',
  `platform` tinyint NOT NULL DEFAULT '0' COMMENT '登录平台',
  `device` varchar(100) DEFAULT NULL COMMENT '设备名称',
  `push` varchar(100) DEFAULT NULL COMMENT '推送标识',
  `ip` varchar(45) DEFAULT NULL COMMENT '登录IP',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_session_on_platform_and_user_id` (`platform`,`user_id`),
  KEY `index_session_on_user_id` (`user_id`),
  CONSTRAINT `fk_session_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sheet`
--

DROP TABLE IF EXISTS `sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sheet` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(190) NOT NULL COMMENT '标题',
  `icon` varchar(190) DEFAULT NULL COMMENT '封面',
  `detail` varchar(190) DEFAULT NULL COMMENT '描述',
  `clicks_count` int NOT NULL DEFAULT '0' COMMENT '点击数',
  `collects_count` int NOT NULL DEFAULT '0' COMMENT '收藏数',
  `comments_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `songs_count` int NOT NULL DEFAULT '0',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_sheet_on_user_id` (`user_id`),
  CONSTRAINT `fk_sheet_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `song`
--

DROP TABLE IF EXISTS `song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `song` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `icon` varchar(190) NOT NULL COMMENT '封面',
  `uri` varchar(190) NOT NULL COMMENT '音乐地址',
  `style` int DEFAULT NULL COMMENT '歌词类型',
  `lyric` text COMMENT '歌词内容',
  `clicks_count` int NOT NULL DEFAULT '0' COMMENT '点击数',
  `comments_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `singer_id` bigint NOT NULL COMMENT '歌手id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_song_on_user_id` (`user_id`),
  KEY `fk_song_singer_id` (`singer_id`),
  CONSTRAINT `fk_song_singer_id` FOREIGN KEY (`singer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_song_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(190) NOT NULL COMMENT '标题',
  `parent_id` bigint DEFAULT NULL COMMENT '上级标签id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_tag_on_title_and_user_id` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `icon` varchar(190) NOT NULL COMMENT '封面',
  `detail` varchar(190) DEFAULT NULL COMMENT '描述',
  `joins_count` varchar(190) DEFAULT '0' COMMENT '参与人数',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_topic_on_user_id` (`user_id`),
  CONSTRAINT `fk_topic_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL COMMENT '主键',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `icon` varchar(190) DEFAULT NULL COMMENT '头像',
  `detail` varchar(190) DEFAULT NULL COMMENT '描述',
  `gender` int NOT NULL DEFAULT '0' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `email` varchar(190) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `province` varchar(50) DEFAULT NULL COMMENT '省',
  `province_code` varchar(50) DEFAULT NULL COMMENT '省编码',
  `city` varchar(50) DEFAULT NULL COMMENT '市',
  `city_code` varchar(50) DEFAULT NULL COMMENT '市编码',
  `area` varchar(50) DEFAULT NULL COMMENT '区',
  `area_code` varchar(50) DEFAULT NULL COMMENT '区编码',
  `password` varchar(190) DEFAULT NULL COMMENT '密码',
  `email_confirm` varchar(190) DEFAULT NULL COMMENT '邮箱确认token',
  `email_confirm_at` datetime DEFAULT NULL COMMENT '邮箱确认时间',
  `email_confirm_send_at` datetime DEFAULT NULL COMMENT '邮箱确认发送时间',
  `role` varchar(190) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1' COMMENT '状态，0：正常；10：禁用',
  `note` varchar(190) DEFAULT NULL COMMENT '内部备注，后台使用',
  `room_id` bigint DEFAULT NULL,
  `songs_count` int NOT NULL DEFAULT '0' COMMENT '音乐数',
  `sheets_count` int NOT NULL DEFAULT '0' COMMENT '歌单数',
  `videos_count` int NOT NULL DEFAULT '0' COMMENT '视频数',
  `comments_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `likes_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `followings_count` int NOT NULL DEFAULT '0' COMMENT '好友数',
  `followers_count` int NOT NULL DEFAULT '0' COMMENT '粉丝数',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `qq_id` varchar(190) DEFAULT NULL,
  `wechat_id` varchar(190) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_id_UNIQUE` (`room_id`),
  UNIQUE KEY `index_user_on_email` (`email`),
  UNIQUE KEY `index_user_on_phone` (`phone`),
  UNIQUE KEY `qq_id_UNIQUE` (`qq_id`),
  UNIQUE KEY `wechat_id_UNIQUE` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `version`
--

DROP TABLE IF EXISTS `version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `version` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(50) NOT NULL COMMENT '版本号',
  `code` int NOT NULL DEFAULT '0' COMMENT 'int类型版本号',
  `detail` varchar(190) DEFAULT NULL COMMENT '描述',
  `uri` varchar(190) DEFAULT NULL COMMENT '下载地址',
  `platform` tinyint NOT NULL DEFAULT '0' COMMENT '平台',
  `force` tinyint NOT NULL DEFAULT '0' COMMENT '是否强制更新，0：否；1：是',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `clicks_count` int NOT NULL DEFAULT '0' COMMENT '点击数',
  `likes_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `comments_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `uri` varchar(190) NOT NULL COMMENT '视频地址',
  `icon` varchar(190) DEFAULT NULL COMMENT '封面',
  `duration` int DEFAULT '0' COMMENT '视频时长',
  `width` int DEFAULT '-1' COMMENT '视频宽',
  `height` int DEFAULT '-1' COMMENT '视频高',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_video_on_user_id` (`user_id`),
  CONSTRAINT `fk_video_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-10  8:19:43
