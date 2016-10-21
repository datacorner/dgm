SET DEFINE OFF;

--truncate table DIM_METRICTYPE;
truncate table DIM_ORIGINE;

--INSERT INTO DIM_METRICTYPE (MTY_PK, MTY_NAME, MTY_DATETIME_LOAD) VALUES ( 0, 'Unknown', SYSDATE);
--INSERT INTO DIM_METRICTYPE (MTY_PK, MTY_NAME, MTY_DATETIME_LOAD) VALUES ( 1, 'Rule', SYSDATE);
--INSERT INTO DIM_METRICTYPE (MTY_PK, MTY_NAME, MTY_DATETIME_LOAD) VALUES ( 2, 'Field', SYSDATE);

Insert into DIM_ORIGINE (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY) Values (0, 'Unknown', SYSDATE, 'UNKN');
Insert into DIM_ORIGINE (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY) Values (1, 'Informatica Data Quality Scorecards', SYSDATE, 'IDQSC');
Insert into DIM_ORIGINE (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY) Values (2, 'Informatica Data Quality Profiles', SYSDATE, 'IDQPL');
Insert into DIM_ORIGINE (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY) Values (3, 'External', SYSDATE, 'EXT');

Insert into APP_PARAMS	 (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL, PARAM_DISPLAY) Values('infacmd', 'C:\Informatica\10.1.0\server\bin\infacmd.bat', 0, NULL, '0',  'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('infadomain', 'Domain_infaw2k12', 0, NULL, 'Informatica Domain',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('infadis', 'DIS', 0, NULL, 'Informatica DIS',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('infamrs', 'mrs', 0, NULL, '0',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('infauser', 'Administrator', 0, NULL, 'informatica User',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY)    ('infapwd', 'Administrator', 0, NULL, 'informatica Password',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values    'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('infaapp', 'App_DGM', 0, NULL, '0',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('scheduled_wf', 'scheduled_wf', 0, NULL, 'Scheduled workflow',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('emaildefault', 'admin@localhost.fr', 0, NULL, 'Email by default',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('scheduletimeout', 'null', 30, NULL, 'Schedule timeout in minutes',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('ACTIVE_SCHEDULER', 'No value', 0, NULL, NULL,     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values   ('INFORMATICA', 'yes', 0, NULL, 'Metadata manager integration',     'Y');
Insert into APP_PARAMS   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,     PARAM_DISPLAY) Values('bgtermmask', 'http://infaw2k12:8085/analyst/?wstate=(%27$obj%27:%27{0}@com.informatica.bg.core.models.BGTermInfo%27,%27$ws%27:bgRequirementsWS)', NULL, TO_TIMESTAMP('31/05/2016 06:26:26.000000','DD/MM/YYYY HH24:MI:SS.FF'), NULL, 'N');

COMMIT;
