-- ----------------------------
-- Table structure for "C##NEWO"."${tablename}"
-- ----------------------------
-- DROP TABLE "C##NEWO"."${tablename}";
CREATE TABLE "C##NEWO"."${tablename}" (
<#list fieldList as var>
	<#if var[1] == 'Integer'>
	"${var[0]}" NUMBER(10) NULL ,
	<#else>
	"${var[0]}" VARCHAR2(50 BYTE) NULL ,
	</#if>
</#list>
	"id" VARCHAR2(50 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

<#list fieldList as var>
COMMENT ON COLUMN "C##NEWO"."${tablename}"."${var[0]}" IS '${var[2]}';
</#list>
COMMENT ON COLUMN "C##NEWO"."${tablename}"."id" IS 'id';

-- ----------------------------
-- Indexes structure for table ${tablename}
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##NEWO"."${tablename}"

-- ----------------------------

ALTER TABLE "C##NEWO"."${tablename}" ADD CHECK ("id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##NEWO"."${tablename}"
-- ----------------------------
ALTER TABLE "C##NEWO"."${tablename}" ADD PRIMARY KEY ("id");
