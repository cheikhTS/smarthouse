CREATE TABLE ACTION (ID INTEGER NOT NULL, ARGS VARCHAR(255), METHOD VARCHAR(255), NAME VARCHAR(255), idEquipment INTEGER, idTask INTEGER, PRIMARY KEY (ID))
CREATE TABLE AREA (ID INTEGER NOT NULL, NAME VARCHAR(255), idHome INTEGER NOT NULL, PRIMARY KEY (ID))
CREATE TABLE DRIVERIDENTIFIER (ID INTEGER NOT NULL, DRIVERCLASS VARCHAR(255), DRIVERNAME VARCHAR(255), DRIVERPATH VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE EQUIPMENT (ID INTEGER NOT NULL, NAME VARCHAR(255), PROPERTIES VARCHAR(255), idDriver INTEGER NOT NULL, idRoom INTEGER NOT NULL, PRIMARY KEY (ID))
CREATE TABLE HOME (ID INTEGER NOT NULL, NAME VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE ROOM (ID INTEGER NOT NULL, NAME VARCHAR(255), idArea INTEGER NOT NULL, PRIMARY KEY (ID))
CREATE TABLE SCENARIO (ID INTEGER NOT NULL, ENABLED TINYINT(1) default 0, NAME VARCHAR(255), idHome INTEGER NOT NULL, PRIMARY KEY (ID))
CREATE TABLE TASK (ID INTEGER NOT NULL, NAME VARCHAR(255), idScenario INTEGER NOT NULL, PRIMARY KEY (ID))
CREATE TABLE ATRIGGER (ID INTEGER NOT NULL, TYPE_ENTITE VARCHAR(31), DATEEXPIRATION DATE, DATESTART DATETIME, NAME VARCHAR(255), STARTTIME BIGINT, idTask INTEGER, NUMBEROFREPEAT INTEGER, VALIDDAYS VARCHAR(255), VALIDMONTHS VARCHAR(255), PRIMARY KEY (ID))
ALTER TABLE ACTION ADD CONSTRAINT FK_ACTION_idTask FOREIGN KEY (idTask) REFERENCES TASK (ID)
ALTER TABLE ACTION ADD CONSTRAINT FK_ACTION_idEquipment FOREIGN KEY (idEquipment) REFERENCES EQUIPMENT (ID)
ALTER TABLE AREA ADD CONSTRAINT FK_AREA_idHome FOREIGN KEY (idHome) REFERENCES HOME (ID)
ALTER TABLE EQUIPMENT ADD CONSTRAINT FK_EQUIPMENT_idDriver FOREIGN KEY (idDriver) REFERENCES DRIVERIDENTIFIER (ID)
ALTER TABLE EQUIPMENT ADD CONSTRAINT FK_EQUIPMENT_idRoom FOREIGN KEY (idRoom) REFERENCES ROOM (ID)
ALTER TABLE ROOM ADD CONSTRAINT FK_ROOM_idArea FOREIGN KEY (idArea) REFERENCES AREA (ID)
ALTER TABLE SCENARIO ADD CONSTRAINT FK_SCENARIO_idHome FOREIGN KEY (idHome) REFERENCES HOME (ID)
ALTER TABLE TASK ADD CONSTRAINT FK_TASK_idScenario FOREIGN KEY (idScenario) REFERENCES SCENARIO (ID)
ALTER TABLE ATRIGGER ADD CONSTRAINT FK_ATRIGGER_idTask FOREIGN KEY (idTask) REFERENCES TASK (ID)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
