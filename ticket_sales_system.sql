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

USE ticket_sales_system
GO

/* 
	创建数据表
*/
-- 创建车站表
CREATE TABLE T_STATION
(
	STA_NAME nvarchar(8) PRIMARY KEY NOT NULL,	-- 车站名称
	LONGITUDE numeric(10, 5) NOT NULL,			-- 车站的经度
	LATITUDE numeric(10, 5) NOT NULL,			-- 车站的纬度
);

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

-- 创建座位表
CREATE TABLE T_SEAT
(
	COACH_NO int NOT NULL,					-- 车厢号
	SEAT_NO char(3) NOT NULL,				-- 座位号
	CLASS nchar(3) NOT NULL,				-- 座位等级
	PRIMARY KEY(COACH_NO, SEAT_NO),
	CHECK(CLASS = '商务座' OR CLASS = '一等座' OR CLASS = '二等座'),
);

-- 创建乘客表
CREATE TABLE T_PASSENGER
(
	ID_NO char(18) PRIMARY KEY NOT NULL,	-- 身份证号
	NAME nvarchar(6) NOT NULL				-- 姓名
);

-- 创建用户表
CREATE TABLE T_USER
(
	ID_NO char(18) PRIMARY KEY NOT NULL,		-- 身份证号
	NAME nvarchar(6) NOT NULL,					-- 姓名
	PWD varchar(20) NOT NULL,					-- 密码
);

-- 创建添加乘客表
CREATE TABLE T_ADD_PASSENGER
(
	USER_ID_NO char(18) NOT NULL,				-- 用户身份证号
	PASS_ID_NO char(18) NOT NULL,				-- 乘客身份证号
	PRIMARY KEY(USER_ID_NO, PASS_ID_NO)
);

-- 创建管理员表
CREATE TABLE T_ADMIN
(
	ACCOUNT varchar(20) PRIMARY KEY NOT NULL,	-- 账号
	PWD varchar(20) NOT NULL,					-- 密码
);

-- 创建经停表
CREATE TABLE T_TRANSIT
(
	TRI_NO varchar(6) NOT NULL,					-- 车次
	STA_NAME nvarchar(8) NOT NULL,				-- 车站名
	STA_NO int NOT NULL,						-- 站点序号
	ARR_STA_TIME time NOT NULL,					-- 到达时间
	STANDING_TIME int,							-- 停留时间
	LEAVING_TIME time NOT NULL,					-- 发车时间
	DISTANCE decimal(7, 2),						-- 里程
	PRIMARY KEY(TRI_NO, STA_NAME),
	FOREIGN KEY(TRI_NO) REFERENCES T_TRAIN(TRI_NO),
	CHECK(LEAVING_TIME >= ARR_STA_TIME),
);

-- 创建车票表
CREATE TABLE T_TICKET
(
	TIC_NO varchar(20) NOT NULL,			-- 车票编号
	FARES decimal(5, 1) NOT NULL,			-- 票价
	ORI_STA nvarchar(8) NOT NULL,			-- 出发地
	DESTINATION nvarchar(8) NOT NULL,		-- 目的地
	DEP_DATE date NOT NULL,					-- 发车日期
	PASS_ID char(18),						-- 乘客身份证号
	TRI_NO varchar(6) NOT NULL,				-- 车次
	STATUS bit DEFAULT 0 NOT NULL,			-- 车票状态 0表示未售 1表示已售
	COACH_NO int,							-- 车厢号
	SEAT_NO char(3),						-- 座位号
	CLASS nchar(3),							-- 座位等级
	PRIMARY KEY(TIC_NO),					
	FOREIGN KEY(ORI_STA) REFERENCES T_STATION(STA_NAME),
	FOREIGN KEY(DESTINATION) REFERENCES T_STATION(STA_NAME),
	FOREIGN KEY(PASS_ID) REFERENCES T_PASSENGER(ID_NO),
	FOREIGN KEY(TRI_NO) REFERENCES T_TRAIN(TRI_NO),
	FOREIGN KEY(COACH_NO, SEAT_NO) REFERENCES T_SEAT(COACH_NO, SEAT_NO),
	CHECK(CLASS = '商务座' OR CLASS = '一等座' OR CLASS = '二等座'),
);

-- 创建购票表
CREATE TABLE T_PURCHASE
(
	TIC_NO varchar(20) NOT NULL,								-- 车票编号
	BUY_TIME datetime DEFAULT GETDATE() NOT NULL,				-- 购票时间
	BUY_PLACE nvarchar(8) NOT NULL,								-- 购票地点
	PURCHASER_ID char(18) NOT NULL,								-- 购票人身份证号
	PASSENGER_ID char(18) NOT NULL,								-- 乘客身份证号
	PRIMARY KEY(TIC_NO),	
	FOREIGN KEY(BUY_PLACE) REFERENCES T_STATION(STA_NAME),
	FOREIGN KEY(PURCHASER_ID) REFERENCES T_USER(ID_NO),
	FOREIGN KEY(PASSENGER_ID) REFERENCES T_PASSENGER(ID_NO),
);

CREATE NONCLUSTERED INDEX IN_TRAIN ON T_TRANSIT(TRI_NO);
CREATE NONCLUSTERED INDEX IN_ID ON T_PURCHASE(PURCHASER_ID, PASSENGER_ID );
CREATE NONCLUSTERED INDEX IN_ID ON T_TICKET(ORI_STA, DESTINATION, DEP_DATE);


-- 创建视图VW_INFO 显示用户乘客信息
CREATE VIEW VW_INFO
AS
	SELECT T_USER.ID_NO AS USER_ID, T_USER.NAME AS USER_NAME, T_PASSENGER.ID_NO, T_PASSENGER.NAME
	FROM T_USER, T_PASSENGER, T_ADD_PASSENGER
	WHERE T_USER.ID_NO = T_ADD_PASSENGER.USER_ID_NO
		AND T_ADD_PASSENGER.PASS_ID_NO = T_PASSENGER.ID_NO

-- 创建视图VW_TICKET 显示车票信息
CREATE VIEW VW_TICKET
AS
	SELECT T_TICKET.TIC_NO, BUY_TIME, BUY_PLACE, PURCHASER_ID, T_USER.NAME AS BUYER_NAME, PASSENGER_ID, T_PASSENGER.NAME AS PASS_NAME, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS
	FROM T_TICKET, T_PURCHASE, T_PASSENGER, T_USER
	WHERE T_TICKET.TIC_NO = T_PURCHASE.TIC_NO
		AND T_PASSENGER.ID_NO = PASSENGER_ID
		AND T_USER.ID_NO = PURCHASER_ID

-- 创建函数 根据经纬度计算两地距离
CREATE FUNCTION F_DISTANCE
(@V_LONA decimal(10, 5),
 @V_LATA decimal(10, 5),
 @V_LONB decimal(10, 5),
 @V_LATB decimal(10, 5))
RETURNS decimal(10, 2)
AS
BEGIN
	DECLARE @V_DISTANCE decimal(10, 2), @V_TEMP decimal(10, 5), @V_DIFFLON decimal(10, 5), @V_DIFFLAT decimal(10, 5)
	SELECT @V_DIFFLAT = @V_LATA / 180 * PI() - @V_LATB / 180 * PI()
	SELECT @V_DIFFLON= @V_LONA / 180 * PI() - @V_LONB / 180 * PI()
	SELECT @V_TEMP = POWER(SIN(@V_DIFFLAT / 2), 2) + COS(@V_LATA / 180 * PI()) * COS(@V_LATB / 180 * PI()) * POWER(SIN(@V_DIFFLON / 2), 2)
	SELECT @V_TEMP = 2 * ASIN(SQRT(@V_TEMP))
	SELECT @V_DISTANCE = 6378.14 * @V_TEMP
	RETURN @V_DISTANCE
END

-- 创建触发器TRG_BUY_TICKET 
-- 当卖出车票时，实现车票信息中空余座位的自动更新和乘客身份证号的录入
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_BUY_TICKET' AND type='TR')
DROP TRIGGER TRG_BUY_TICKET
GO
CREATE TRIGGER TRG_BUY_TICKET
ON T_PURCHASE
AFTER INSERT
AS
	DECLARE @V_TIC_NO varchar(20), @V_ID_NO char(18)
	SELECT @V_TIC_NO = TIC_NO, @V_ID_NO = PASSENGER_ID
	FROM INSERTED
	UPDATE T_TICKET
	SET PASS_ID = @V_ID_NO
	WHERE TIC_NO = @V_TIC_NO
	UPDATE T_TICKET
	SET STATUS = 1
	WHERE TIC_NO = @V_TIC_NO
GO

-- 创建触发器TRG_REFUND_TIC
-- 当删除购票信息时 即进行退票时 修改车票的状态和乘客身份证号
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_REFUND_TIC' AND type='TR')
DROP TRIGGER TRG_REFUND_TIC 
GO
CREATE TRIGGER TRG_REFUND_TIC 
ON T_PURCHASE
AFTER DELETE
AS
	DECLARE @V_TIC_NO varchar(20)
	SELECT @V_TIC_NO = TIC_NO
	FROM DELETED
	UPDATE T_TICKET
	SET STATUS = 0
	WHERE TIC_NO = @V_TIC_NO
	UPDATE T_TICKET
	SET PASS_ID = NULL
	WHERE TIC_NO = @V_TIC_NO
GO

-- 创建触发器TRG_TRAIN
-- 当增加列车信息时，根据始发站与终点站距离自动计算平均速度, 根据发车时间、到达时间、经过天数自动计算运行时间（以分钟为单位）
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_TRAIN' AND type='TR')
DROP TRIGGER TRG_TRAIN
GO
CREATE TRIGGER TRG_TRAIN
ON T_TRAIN
AFTER INSERT
AS
	DECLARE @V_TRI_NO varchar(6), @V_DEP_TIME time, @V_ARR_TIME time, @V_TIME int, @V_DAYS int, @V_RUN_TIME int, @V_STA1 nvarchar(8), @V_STA2 nvarchar(8), @V_LON1 decimal(10, 5), @V_LON2 decimal(10, 5), @V_LAT1 decimal(10, 5), @V_LAT2 decimal(10, 5)
	SELECT @V_TRI_NO = TRI_NO, @V_DEP_TIME = DEP_TIME, @V_ARR_TIME = ARR_TIME, @V_DAYS = DAYS, @V_STA1 = DEP_STA, @V_STA2 = TERMINUS, @V_TRI_NO = TRI_NO
	FROM INSERTED
	SELECT @V_LON1 = LONGITUDE, @V_LAT1 = LATITUDE
	FROM T_STATION
	WHERE STA_NAME = @V_STA1
	SELECT @V_LON2 = LONGITUDE, @V_LAT2 = LATITUDE
	FROM T_STATION
	WHERE STA_NAME = @V_STA2
	UPDATE T_TRAIN
	SET RUN_TIME = 1440 * @V_DAYS + DATEDIFF(MINUTE, @V_DEP_TIME, @V_ARR_TIME)
	WHERE TRI_NO = @V_TRI_NO
	UPDATE T_TRAIN
	SET SPEED = dbo.F_DISTANCE(@V_LON1, @V_LAT1, @V_LON2, @V_LAT2) / RUN_TIME * 60
	WHERE TRI_NO = @V_TRI_NO
GO

-- 创建触发器TRG_TRANSIT
-- 当增加经停信息时，根据始发站与本站距离自动计算列车里程
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_TRANSIT' AND type='TR')
DROP TRIGGER TRG_TRANSIT
GO
CREATE TRIGGER TRG_TRANSIT
ON T_TRANSIT
AFTER INSERT
AS
	DECLARE @V_TRI_NO varchar(6), @V_ARR_STA_TIME time, @V_LEAVING_TIME time, @V_TIME int, @V_RUN_TIME int, @V_STA1 nvarchar(8), @V_STA2 nvarchar(8), @V_LON1 decimal(10, 5), @V_LON2 decimal(10, 5), @V_LAT1 decimal(10, 5), @V_LAT2 decimal(10, 5)
	SELECT @V_TRI_NO = TRI_NO, @V_STA1 = STA_NAME, @V_ARR_STA_TIME = ARR_STA_TIME, @V_LEAVING_TIME = LEAVING_TIME
	FROM INSERTED
	SELECT @V_STA2 = DEP_STA
	FROM T_TRANSIT, T_TRAIN
	WHERE T_TRAIN.TRI_NO = T_TRANSIT.TRI_NO
		AND T_TRAIN.TRI_NO = @V_TRI_NO
		AND T_TRANSIT.TRI_NO = @V_TRI_NO
	SELECT @V_LON1 = LONGITUDE, @V_LAT1 = LATITUDE
	FROM T_STATION
	WHERE STA_NAME = @V_STA1
	SELECT @V_LON2 = LONGITUDE, @V_LAT2 = LATITUDE
	FROM T_STATION
	WHERE STA_NAME = @V_STA2
	UPDATE T_TRANSIT
	SET STANDING_TIME = DATEDIFF(MINUTE, ARR_STA_TIME, LEAVING_TIME)
	WHERE TRI_NO = @V_TRI_NO
		AND STA_NAME = @V_STA1
	UPDATE T_TRANSIT
	SET DISTANCE = dbo.F_DISTANCE(@V_LON1, @V_LAT1, @V_LON2, @V_LAT2)
	WHERE TRI_NO = @V_TRI_NO
		AND STA_NAME = @V_STA1
GO

-- 创建触发器TRG_DROP_STA 
-- 当删除车站信息时 将车票、列车、经停表中包含车站信息的元组全部删除
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_DROP_STA' AND type='TR')
DROP TRIGGER TRG_DROP_STA
GO
CREATE TRIGGER TRG_DROP_STA
ON T_STATION
AFTER DELETE
AS
	DECLARE @V_STA_NAME nvarchar(8)
	SELECT @V_STA_NAME = STA_NAME
	FROM DELETED
	BEGIN
		DELETE
		FROM T_TRAIN
		WHERE DEP_STA = @V_STA_NAME OR TERMINUS = @V_STA_NAME
		DELETE
		FROM T_TRANSIT
		WHERE STA_NAME = @V_STA_NAME
		DELETE 
		FROM T_TICKET
		WHERE ORI_STA = @V_STA_NAME OR DESTINATION = @V_STA_NAME
	END
GO

-- 创建触发器TRG_UPDATE_STA
-- 当更新车站信息时 将车票、列车、经停表中包含车站信息的元组全部更新
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_UPDATE_STA' AND type='TR')
DROP TRIGGER TRG_DROP_STA
GO
CREATE TRIGGER TRG_UPDATE_STA
ON T_STATION
AFTER UPDATE
AS
	DECLARE @V_FOR_NAME nvarchar(8), @V_LAT_NAME nvarchar(8)
	SELECT @V_FOR_NAME = STA_NAME
	FROM DELETED
	SELECT @V_LAT_NAME = STA_NAME
	FROM INSERTED
	BEGIN
		UPDATE T_TRAIN
		SET DEP_STA = @V_LAT_NAME
		WHERE DEP_STA = @V_FOR_NAME
		UPDATE T_TRAIN
		SET TERMINUS = @V_LAT_NAME
		WHERE TERMINUS = @V_FOR_NAME
		UPDATE T_TRANSIT
		SET STA_NAME = @V_LAT_NAME
		WHERE STA_NAME = @V_FOR_NAME
		UPDATE T_TICKET
		SET ORI_STA = @V_LAT_NAME
		WHERE ORI_STA = @V_FOR_NAME
		UPDATE T_TICKET
		SET DESTINATION = @V_LAT_NAME
		WHERE DESTINATION = @V_FOR_NAME
	END
GO

-- 创建触发器TRG_DROP_TRI
-- 当删除列车信息时 将车票、列车、经停表中包含车站信息的元组全部删除
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_DROP_TRI' AND type='TR')
DROP TRIGGER TRG_DROP_TRI
GO
CREATE TRIGGER TRG_DROP_TRI
ON T_TICKET
AFTER DELETE
AS
	DECLARE @V_TRI_NO varchar(6)
	SELECT @V_TRI_NO = TRI_NO
	FROM DELETED
	DELETE
	FROM T_TRANSIT
	WHERE TRI_NO = @V_TRI_NO
	DELETE
	FROM T_TICKET
	WHERE TRI_NO = @V_TRI_NO
GO

-- 创建触发器TRG_USER
-- 当增加用户信息时 将该用户信息添加至该用户对应乘车人中
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_USER' AND type='TR')
DROP TRIGGER TRG_USER
GO
CREATE TRIGGER TRG_USER
ON T_USER
AFTER INSERT
AS
	DECLARE @V_ID char(18), @V_NAME nvarchar(6)
	SELECT @V_ID = ID_NO, @V_NAME = NAME
	FROM INSERTED
	IF NOT EXISTS(SELECT * FROM T_PASSENGER WHERE ID_NO = @V_ID )
	BEGIN
		INSERT INTO T_PASSENGER(ID_NO, NAME)
		VALUES(@V_ID, @V_NAME)
	END
	INSERT INTO T_ADD_PASSENGER(USER_ID_NO, PASS_ID_NO)
	VALUES(@V_ID, @V_ID)
GO

-- 创建触发器TRG_DROP_USER
-- 当删除用户信息时 将该用户的乘车人从T_ADD_PASSENGER中删除
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_DROP_USER' AND type='TR')
DROP TRIGGER TRG_DROP_USER
GO
CREATE TRIGGER TRG_DROP_USER
ON T_USER
AFTER DELETE
AS
	DECLARE @V_ID char(18)
	SELECT @V_ID = ID_NO
	FROM DELETED
	DELETE 
	FROM T_ADD_PASSENGER
	WHERE USER_ID_NO = @V_ID
GO

-- 创建触发器TRG_ADD_TRAIN
-- 当增加列车信息时 向T_TRANSIT中添加数据
IF EXISTS(SELECT name FROM sysobjects WHERE name='TRG_ADD_TRAIN' AND type='TR')
DROP TRIGGER TRG_ADD_TRAIN
GO
CREATE TRIGGER TRG_ADD_TRAIN
ON T_TRAIN
AFTER INSERT
AS
	DECLARE @V_DEP_STA nvarchar(8), @V_TERMINUS nvarchar(8), @V_DEP_TIME time, @V_ARR_TIME time, @V_TRI_NO varchar(6)
	SELECT @V_DEP_STA = DEP_STA, @V_TERMINUS = TERMINUS, @V_DEP_TIME = DEP_TIME, @V_ARR_TIME = ARR_TIME, @V_TRI_NO = TRI_NO
	FROM INSERTED
	INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
	VALUES(@V_TRI_NO, @V_DEP_STA, 1, @V_DEP_TIME, @V_DEP_TIME);
	INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
	VALUES(@V_TRI_NO, @V_TERMINUS, 2, @V_ARR_TIME, @V_ARR_TIME);
GO

-- 创建存储过程SP_SUM_TICKET_EMPTY
-- 以发车日期为输入参数查询该车次空余车票总量
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_SUM_TICKET_EMPTY' AND type='p')
DROP PROCEDURE SP_SUM_TICKET_EMPTY
GO
CREATE PROCEDURE SP_SUM_TICKET_EMPTY
@V_DEP_DATE date
AS
	SELECT COUNT(*) AS TIC_NUM
	FROM T_TICKET
	WHERE STATUS = 0
		AND DEP_DATE = @V_DEP_DATE
GO

-- 创建存储过程SP_SUM_TICKET_ALL_TICKET
-- 以发车日期为输入参数查询该车次车票总量
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_SUM_TICKET_ALL_TICKET' AND type='p')
DROP PROCEDURE SP_SUM_TICKET_ALL_TICKET
GO
CREATE PROCEDURE SP_SUM_TICKET_ALL_TICKET
@V_DEP_DATE date
AS
	SELECT COUNT(*) AS TIC_NUM
	FROM ticket_sales_system.dbo.T_TICKET
	WHERE DEP_DATE = @V_DEP_DATE
GO

-- 创建存储过程SP_SUM_ALL_FARES
-- 以发车日期为输入参数查询该车次销售金额
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_SUM_ALL_FARES' AND type='p')
DROP PROCEDURE SP_SUM_ALL_FARES
GO
CREATE PROCEDURE SP_SUM_ALL_FARES
@V_DEP_DATE date
AS
	SELECT SUM(FARES) AS FARES_ALL
	FROM T_TICKET
	WHERE STATUS = 1
		AND DEP_DATE = @V_DEP_DATE
GO

-- 创建存储过程SP_SUM_FARES
-- 以车次号、发车日期为输入参数查询该车次销售金额
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_SUM_FARES' AND type='p')
DROP PROCEDURE SP_SUM_FARES
GO
CREATE PROCEDURE SP_SUM_FARES
@V_TRI_NO varchar(6), @V_DEP_DATE date
AS
	SELECT SUM(FARES) AS FARES_ALL
	FROM T_TICKET
	WHERE STATUS = 1
		AND DEP_DATE = @V_DEP_DATE
		AND TRI_NO = @V_TRI_NO
GO

-- 创建存储过程SP_SUM_TICKET_ALL
-- 以车次及发车日期为输入参数查询该车次车票总量
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_SUM_TICKET_ALL' AND type='p')
DROP PROCEDURE SP_SUM_TICKET_ALL
GO
CREATE PROCEDURE SP_SUM_TICKET_ALL
@V_TRI_NO varchar(6), @V_DEP_DATE date
AS
	SELECT COUNT(*) AS TIC_NUM
	FROM ticket_sales_system.dbo.T_TICKET
	WHERE TRI_NO = @V_TRI_NO
		AND DEP_DATE = @V_DEP_DATE
GO

-- 创建存储过程SP_QUERY_CLASS
-- 以车次、发车日期、座位等级为输入参数查询该车次空余车票总量
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_QUERY_CLASS' AND type='p')
DROP PROCEDURE SP_QUERY_CLASS
GO
CREATE PROCEDURE SP_QUERY_CLASS
@V_TRI_NO varchar(6), @V_DEP_DATE date, @V_CLASS nchar(3), @V_ORI_STA nvarchar(8), @V_DESTINATION nvarchar(8)
AS
	SELECT COUNT(*) AS TIC_NUM
	FROM T_TICKET
	WHERE STATUS = 0
		AND TRI_NO = @V_TRI_NO
		AND DEP_DATE = @V_DEP_DATE
		AND CLASS = @V_CLASS
		AND ORI_STA = @V_ORI_STA
		AND DESTINATION = @V_DESTINATION
GO

-- 创建存储过程SP_NULL_NUM
-- 以车次、发车日期、输入参数查询无座座位数量
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_NULL_NUM' AND type='p')
DROP PROCEDURE SP_NULL_NUM
GO
CREATE PROCEDURE SP_NULL_NUM
@V_TRI_NO varchar(6), @V_DEP_DATE date, @V_ORI_STA nvarchar(8), @V_DESTINATION nvarchar(8)
AS
	SELECT COUNT(*) AS TIC_NUM
	FROM T_TICKET
	WHERE STATUS = 0
		AND TRI_NO = @V_TRI_NO
		AND DEP_DATE = @V_DEP_DATE
		AND CLASS IS NULL
		AND ORI_STA = @V_ORI_STA
		AND DESTINATION = @V_DESTINATION
GO

-- 创建存储过程SP_QUERY_FARE
-- 以车次、发车日期、座位等级为输入参数查询票价
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_QUERY_FARE' AND type='p')
DROP PROCEDURE SP_QUERY_FARE
GO
CREATE PROCEDURE SP_QUERY_FARE
@V_TRI_NO varchar(6), @V_DEP_DATE date, @V_CLASS nchar(3), @V_ORI_STA nvarchar(8), @V_DESTINATION nvarchar(8)
AS
	SELECT DISTINCT FARES
	FROM T_TICKET
	WHERE STATUS = 0
		AND TRI_NO = @V_TRI_NO
		AND DEP_DATE = @V_DEP_DATE
		AND CLASS = @V_CLASS
		AND ORI_STA = @V_ORI_STA
		AND DESTINATION = @V_DESTINATION
GO

-- 创建存储过程SP_NULL_FARE
-- 以车次、发车日期、输入参数查询无座座位票价
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_NULL_FARE' AND type='p')
DROP PROCEDURE SP_NULL_FARE
GO
CREATE PROCEDURE SP_NULL_FARE
@V_TRI_NO varchar(6), @V_DEP_DATE date, @V_ORI_STA nvarchar(8), @V_DESTINATION nvarchar(8)
AS
	SELECT DISTINCT FARES
	FROM T_TICKET
	WHERE STATUS = 0
		AND TRI_NO = @V_TRI_NO
		AND DEP_DATE = @V_DEP_DATE
		AND CLASS IS NULL
		AND ORI_STA = @V_ORI_STA
		AND DESTINATION = @V_DESTINATION
GO

SELECT DISTINCT FARES
	FROM T_TICKET
	WHERE STATUS = 0
		AND TRI_NO = 'G7262'
		AND DEP_DATE = '2021-7-9'
		AND CLASS IS NULL

-- 创建存储过程SP_SEAT
-- 按座位等级统计座位总量
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_SEAT' AND type='p')
DROP PROCEDURE SP_SEAT
GO
CREATE PROCEDURE SP_SEAT
AS
	SELECT CLASS AS 等级, COUNT(*) AS 数量
	FROM T_SEAT
	GROUP BY CLASS
GO

-- 创建存储过程SP_QUERY_TICKET
-- 按出发地、目的地、发车日期查询有票的列车信息
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_QUERY_TICKET' AND type='p')
DROP PROCEDURE SP_QUERY_TICKET
GO
CREATE PROCEDURE SP_QUERY_TICKET
@V_ORI_STA nvarchar(8), @V_DESTINATION nvarchar(8), @V_DEP_DATE date
AS
	SELECT DISTINCT T_TICKET.TRI_NO, ORI_STA, T1.LEAVING_TIME, DESTINATION, T2.ARR_STA_TIME
	FROM T_TICKET, T_TRANSIT AS T1, T_TRANSIT AS T2
	WHERE ORI_STA = @V_ORI_STA
		AND DESTINATION = @V_DESTINATION
		AND DEP_DATE = @V_DEP_DATE
		AND STATUS = 0
		AND T_TICKET.TRI_NO = T1.TRI_NO
		AND T_TICKET.TRI_NO = T2.TRI_NO
		AND T1.STA_NAME = @V_ORI_STA
		AND T2.STA_NAME = @V_DESTINATION
GO

-- 创建存储过程SP_QUERY_TICKET_HIGH
-- 按出发地、目的地、发车日期查询有票的高铁或动车信息
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_QUERY_TICKET_HIGH' AND type='p')
DROP PROCEDURE SP_QUERY_TICKET_HIGH
GO
CREATE PROCEDURE SP_QUERY_TICKET_HIGH
@V_ORI_STA nvarchar(8), @V_DESTINATION nvarchar(8), @V_DEP_DATE date
AS
	SELECT DISTINCT T_TICKET.TRI_NO, ORI_STA, T1.LEAVING_TIME, DESTINATION, T2.ARR_STA_TIME
	FROM T_TRAIN, T_TICKET, T_TRANSIT AS T1, T_TRANSIT AS T2
	WHERE ORI_STA = @V_ORI_STA
		AND DESTINATION = @V_DESTINATION
		AND DEP_DATE = @V_DEP_DATE
		AND STATUS = 0
		AND T_TICKET.TRI_NO = T1.TRI_NO
		AND T_TICKET.TRI_NO = T2.TRI_NO
		AND T1.STA_NAME = @V_ORI_STA
		AND T2.STA_NAME = @V_DESTINATION
		AND T_TICKET.TRI_NO = T_TRAIN.TRI_NO
		AND (TRI_TYPE = '高铁' OR TRI_TYPE = '动车')
GO

-- 创建存储过程SP_QUERY_TRANSIT_DES_STA
-- 根据列车号获取出发站后的经停站名
IF EXISTS (SELECT name FROM sysobjects WHERE name='SP_QUERY_TRANSIT_DES_STA' AND type='p')
DROP PROCEDURE SP_QUERY_TRANSIT_DES_STA
GO
CREATE PROCEDURE SP_QUERY_TRANSIT_DES_STA
@V_TRI_NO varchar(6), @V_ORI_STA nvarchar(8)
AS
	SELECT STA_NAME
	FROM T_TRANSIT
	WHERE TRI_NO = @V_TRI_NO
		AND STA_NO > (SELECT DISTINCT STA_NO
					  FROM T_TRANSIT
					  WHERE STA_NAME = @V_ORI_STA
						AND TRI_NO = @V_TRI_NO)
GO

-- 向T_STATION中插入数据
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('上海', 121.47, 31.23),
	  ('北京', 116.4, 39.9),
	  ('天津', 117.2, 39.1),
	  ('香港', 114.1, 22.2),
	  ('广州', 113.2, 23.2),
	  ('珠海', 113.5, 22.3),
	  ('深圳', 114.1, 22.6),
	  ('杭州', 120.2, 30.3),
	  ('南京', 118.8, 32.1),
	  ('合肥', 117.28, 31.86),
	  ('大同', 113.3, 40.09);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('巢湖', 117.86, 31.60);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('包头', 109.84, 40.66);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('呼和浩特', 111.67, 40.82);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('集宁', 113.11, 41.03);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('张家口', 114.88, 40.81);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('徐州', 117.18, 34.26);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('镇江', 119.45, 32.20);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('常州', 119.95, 31.77);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('无锡', 120.3, 31.58);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('苏州', 120.62, 31.3);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('嘉兴', 120.75, 30.76);
INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE)
VALUES('海宁', 120.68, 30.51);

-- 向T_TRAIN中插入数据
INSERT INTO T_TRAIN(TRI_NO, TRI_TYPE, DEP_TIME, ARR_TIME, DAYS, DEP_STA, TERMINUS)
VALUES('Z284', '普列', '06:54:00', '12:15:00', 1, '包头', '杭州');
INSERT INTO T_TRAIN(TRI_NO, TRI_TYPE, DEP_TIME, ARR_TIME, DAYS, DEP_STA, TERMINUS)
VALUES('G7262', '高铁', '07:18:00', '09:59:00', 0, '巢湖', '上海');
INSERT INTO T_TRAIN(TRI_NO, TRI_TYPE, DEP_TIME, ARR_TIME, DAYS, DEP_STA, TERMINUS)
VALUES('G7071', '高铁', '07:07:00', '12:35:00', 0, '合肥', '上海');

-- 向T_TRANSIT中插入数据
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('G7262', '巢湖', 1, '07:18:00', '07:18:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('G7262', '合肥', 2, '07:46:00', '08:03:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('G7262', '南京', 3, '08:54:00', '08:56:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('G7262', '上海', 4, '09:59:00', '09:59:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '包头', 1, '06:45:00', '06:45:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '呼和浩特', 2, '08:51:00', '08:57:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '集宁', 3, '10:42:00', '10:49:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '大同', 4, '12:27:00', '12:39:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '张家口', 5, '15:06:00', '15:17:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '北京', 6, '18:31:00', '19:10:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '天津', 7, '20:26:00', '20:40:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '徐州', 8, '02:14:00', '02:35:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '南京', 9, '05:23:00', '05:37:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '镇江', 10, '06:13:00', '06:17:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '常州', 11, '06:55:00', '07:00:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '无锡', 12, '07:25:00', '07:30:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '苏州', 13, '07:58:00', '08:01:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '上海', 14, '09:45:00', '10:20:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '嘉兴', 15, '11:01:00', '11:04:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '海宁', 16, '11:22:00', '11:25:00');
INSERT INTO T_TRANSIT(TRI_NO, STA_NAME, STA_NO, ARR_STA_TIME, LEAVING_TIME)
VALUES('Z284', '杭州', 17, '12:15:00', '12:15:00');

-- 向T_TICKET中插入数据
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000001', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '1A', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000002', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '2A', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000003', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '3A', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000004', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '4A', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000005', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '5A', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000006', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '1B', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000007', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '2B', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000008', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '3B', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000009', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '4B', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000010', 217, '合肥', '上海', '2021-7-9', 'G7262', 3, '5B', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000011', 358.5, '合肥', '上海', '2021-7-9', 'G7262', 2, '1A', '一等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000012', 358.5, '合肥', '上海', '2021-7-9', 'G7262', 2, '2A', '一等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000013', 358.5, '合肥', '上海', '2021-7-9', 'G7262', 2, '3A', '一等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000014', 358.5, '合肥', '上海', '2021-7-9', 'G7262', 2, '4A', '一等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000015', 358.5, '合肥', '上海', '2021-7-9', 'G7262', 2, '5A', '一等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000016', 738, '合肥', '上海', '2021-7-9', 'G7262', 1, '1A', '商务座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000017', 738, '合肥', '上海', '2021-7-9', 'G7262', 1, '2A', '商务座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000018', 738, '合肥', '上海', '2021-7-9', 'G7262', 1, '3A', '商务座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000019', 738, '合肥', '上海', '2021-7-9', 'G7262', 1, '4A', '商务座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000020', 738, '合肥', '上海', '2021-7-9', 'G7262', 1, '5A', '商务座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000021', 217, '合肥', '上海', '2021-7-9', 'G7262', NULL, NULL, NULL);
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000022', 217, '合肥', '上海', '2021-7-9', 'G7262', NULL, NULL, NULL);
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000023', 217, '合肥', '上海', '2021-7-9', 'G7262', NULL, NULL, NULL);
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000024', 206, '大同', '上海', '2021-7-10', 'Z284', 3, '1A', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000025', 206, '大同', '上海', '2021-7-10', 'Z284', 3, '2A', '二等座');
INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS)
VALUES('ZH000000026', 206, '大同', '上海', '2021-7-10', 'Z284', 3, '3A', '二等座');

-- 向T_PURCHASE中插入数据
INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID)
VALUES('ZH000000001', '上海', '340103200107111026', '340103200107111026');
INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID)
VALUES('ZH000000002', '上海', '340103200107111026', '340103200107111027');
INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID)
VALUES('ZH000000003', '上海', '340103200107111026', '340103200107111028');
INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID)
VALUES('ZH000000011', '上海', '340103200107111026', '340103200107111029');
INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID)
VALUES('ZH000000016', '上海', '340103200107111026', '340103200107111030');
INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID)
VALUES('ZH000000012', '合肥', '340103200107111027', '340103200107111026');

-- 向T_SEAT中插入数据
INSERT INTO (COACH_NO, SEAT_NO, CLASS)
VALUES(1, '1A', '商务座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(2, '1A', '一等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '1A', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '1B', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(1, '2A', '商务座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(2, '2A', '一等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '2A', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '2B', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(1, '3A', '商务座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(2, '3A', '一等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '3A', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '3B', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(1, '4A', '商务座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(2, '4A', '一等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '4A', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '4B', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(1, '5A', '商务座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(2, '5A', '一等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '5A', '二等座');
INSERT INTO T_SEAT(COACH_NO, SEAT_NO, CLASS)
VALUES(3, '5B', '二等座');

-- 向T_ADMIN中插入数据
INSERT INTO T_ADMIN(ACCOUNT, PWD)
VALUES('admin', 'admin');


