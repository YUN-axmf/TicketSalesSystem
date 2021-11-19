# 基于Java Swing的车票实名销售系统



## 问题描述

### 1. 基本信息及处理 

系统包含如下基本信息： 

1. 车次信息：车次、类型（高铁、动车）、起点、终点、发车时间、到达时间、运行时间 

2. 站点信息：车次、站点序号、站点信息、到达时间、发车时间、里程（起点—本站点） 

3. 座位信息：车次、车厢号、等级（一等座、二等座）、座位号 

4. 车票信息：车次、发车日期、车厢号、等级、空余座位号 

5. 车票购买信息：车次、发车日期、起点站、终到站、车厢号、座位号、票价、身份证号、 购买点、购买日期时间 

6. 顾客信息:包括身份证号、姓名等实名认证信息 

7. 车票信息查询：可以按条件查询车次基本信息、指定日期指定车次的车票空余情况、指定乘客的客票购买信息等，并尽可能实现多条件组合查询。 

8. 车票购买：客户可以根据车次、日期、起始站及终到站查询车票信息并购买，可以为他人购买车票，但必须是实名售票。买到的车票中必须包含座位信息，或者注明“无座” 。系统提前 11 天开始预售票。 

9. 退票：还没有乘坐的车票，可以退票 
10. 统计、汇总指定日期指定车次的车票销售（销售金额、销售车票数目）情况。 

备注：请务必参考 12306 网站



### 2. 数据库设计

按照数据库设计的步骤完成。设计者可以在上述信息的基础上适当扩充一些基本信息需求。 



### 3. 数据完整性设计及物理设计

1. 给每个表实施主键及外键约束。 

2. 设定缺省约束。如购买日期时间为系统当前日期时间。 

3. 设置非空约束。如系统基本信息均非空。 

4. 实施CHECK约束，如到站时间必须早于发车时间等。 

5. 实施CHECK约束，约束车次类型为：高铁、动车 

6. 设计触发器，当卖出车票时，实现车票信息中空余座位的自动更新。 

7. 数据库物理设计：为表创建适当索引，并在查询中能让索引起作用。 



### 4. 创建存储过程

1. 设计一个存储过程，以车次及发车日期为输入参数查询该车次空余车票总量，并输出。 

2. 按车次、类型及座位等级统计座位总量。 



### 5. 应用程序设计 

本课程设计，数据库部分和应用程序部分各占50%。

1. 开发工具：JAVA或其他 

2. 应用系统要求：
   -  应该有登录界面
   - 交互界面简洁友好
   - 有容错处理，比如日期输入，不能输入2021-5-35，对输入的不合理数据系统要有相应的处理，而不是程序中断运行
   - 有多种输入形式，如输入、下拉、单选、复选等
   - 汇总统计，必须同时包含明细信息和汇总信息，以报表形式给出



## 逻辑结构设计



### 模式设计

#### 1. 车站表

车站（站名，经度，纬度），其中站名是主键

| 名称   | 字段名称  | 数据类型       | 主键 | 非空 | 约束 |
| ------ | --------- | -------------- | ---- | ---- | ---- |
| 车站名 | STA_NAME  | nvarchar       | Yes  | Yes  |      |
| 经度   | LONGITUDE | numeric(10, 5) | No   | Yes  |      |
| 纬度   | LATITUDE  | numeric(10, 5) | No   | Yes  |      |



## 数据库的物理设计



#### 聚集索引

所有表的主键均为聚集索引



#### 非聚集索引

```sql
CREATE NONCLUSTERED INDEX IN_TRAIN ON T_TRANSIT(TRI_NO);
CREATE NONCLUSTERED INDEX IN_ID ON T_PURCHASE(PURCHASER_ID, PASSENGER_ID );
CREATE NONCLUSTERED INDEX IN_ID ON T_TICKET(ORI_STA, DESTINATION, DEP_DATE);
```



## 数据库设计实现及运行

### 

### 数据库的创建

```sql
/* 
	创建数据库 
*/
CREATE DATABASE ticket_sales_system
ON
(
	NAME = 'ticket_sales_system_data',
	FILENAME = 'E:\Data\ticket_sales_system_data.mdf',
	SIZE = 30MB, MAXSIZE = 300MB,
	FILEGROWTH = 5%
)
LOG ON
(
	NAME = 'ticket_sales_system_log',
	FILENAME = 'E:\Data\ticket_sales_system_log.ldf',
	SIZE = 20MB, MAXSIZE = 30MB,
	FILEGROWTH = 6%
)
GO
```



### 数据表的创建

1. 车站表

   ```sql
   -- 创建车站表
   CREATE TABLE T_STATION
   (
   	STA_NAME nvarchar(8) PRIMARY KEY NOT NULL,	-- 车站名称
   	LONGITUDE numeric(10, 5) NOT NULL,			-- 车站的经度
   	LATITUDE numeric(10, 5) NOT NULL,			-- 车站的纬度
   );
   ```

   

2. 列车表

   ```sql
   -- 创建列车表
   CREATE TABLE T_TRAIN
   (
   	TRI_NO varchar(6) PRIMARY KEY NOT NULL,				-- 车次
   	TRI_TYPE nchar(2) NOT NULL,							-- 列车类型 
   	DEP_TIME time NOT NULL,								-- 发车时间
   	ARR_TIME time NOT NULL,								-- 到达时间
   	RUN_TIME int,										-- 运行时间
   	DAYS int NOT NULL,									-- 经过天数
   	DEP_STA nvarchar(8) NOT NULL,						-- 始发站
   	TERMINUS nvarchar(8) NOT NULL,						-- 终点站
   	SPEED int,											-- 平均车速
   	FOREIGN KEY(DEP_STA) REFERENCES T_STATION(STA_NAME),
   	FOREIGN KEY(TERMINUS) REFERENCES T_STATION(STA_NAME),
   	CHECK(TRI_TYPE = '高铁' OR TRI_TYPE = '动车' OR TRI_TYPE = '普列'),
   	CHECK(SPEED > 0) 
   );
   ```

   

3. 座位表

   ```sql
   CREATE TABLE T_SEAT
   (
   	COACH_NO int NOT NULL,					-- 车厢号
   	SEAT_NO char(3) NOT NULL,				-- 座位号
   	CLASS nchar(3) NOT NULL,				-- 座位等级
   	PRIMARY KEY(COACH_NO, SEAT_NO),
   	CHECK(CLASS = '商务座' OR CLASS = '一等座' OR CLASS = '二等座'),
   );
   ```

   

4. 乘客表

   ```sql
   -- 创建乘客表
   CREATE TABLE T_PASSENGER
   (
   	ID_NO char(18) PRIMARY KEY NOT NULL,	-- 身份证号
   	NAME nvarchar(6) NOT NULL				-- 姓名
   );
   ```

