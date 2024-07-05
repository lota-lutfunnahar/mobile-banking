Insert Into Product values ('1904','1904','','Bridge Finance', '','');
Insert Into Product values ('1922','1922','','MedEC - Others(CC-P/H), OD, Term Ln', '','');
Insert Into Product values ('1905','1905','','EDF Loan', '','');
Insert Into Product values ('13570','13570','','Trust Receipt', '','');
Insert Into Product values ('13573','13573','','Bridge Finance', '','');
Insert Into Product values ('13582','13582','','LOAN AGAINST ABS', '','');
Insert Into Product values ('6002','6002','','SNTD -Special Notice Time Dep', '','');
Insert Into Product values ('1001','1001','','CURRENT ACCOUNT', 'CURRENT ACCOUNT','');
Insert Into Product values ('334036b8-77e9-4478-8160-c8ff52f7c038','6608','','FIXED DEP 6MTH TO 12 MTH', '','');
Insert Into Product values ('89bff736-f657-4688-8ff8-b6aa5464ecec','6604','','Agrani Bank Pension Scheme-5Y', '','');
Insert Into Product values ('46642416-490e-499d-b549-ad016ed9b64d','1317912345','','AGRANI BANK MILLIONAIRE DEPOSIT', '','');
Insert Into Product values ('93f1f081-ba1a-488c-8a55-e48aaa58e5a8','eeeeeeeeeeeeee','','AGRANI BANK MILLIONAIRE DEPOSIT', '','');
Insert Into Product values ('255992f1-a8a5-44fe-b060-71676fd96ccf','54564','','BYE TEST', '','');
Insert Into Product values ('6cc75e1d-288e-4500-830a-71b5146de6bd','6465464','','HELLO TEST', '','');
Insert Into Product values ('d849671b-1f53-4a15-935b-8cee6be1e583','sjhd jdfhsj kjdsf skdjfs sdfsdf','','12312', '','');
Insert Into Product values ('70b0383d-2c96-465f-b89d-2fd8e20c0472','6019','','STUDENT ACCOUNT', '','');
Insert Into Product values ('0d14aea2-57f0-4a38-8256-d8c87964a5a6','6607','','FIXED DEPOSIT 3MTH TO 6MTH', '','');
Insert Into Product values ('564a747f-723b-4827-abba-a808de9d8ab9','6609','','FIXED DEP 12 MONTH', '','');
Insert Into Product values ('560e3c46-61fd-4f2d-8148-60fdae799b3b','6610','','FDR FOR MORE THAN 12 MONTHS', '','');
Insert Into Product values ('b46a6f1d-20b9-4efe-a45d-b36f092806ed','6605','','Agrani Bank Pension Scheme 10 YR', '','');
Insert Into Product values ('1be02a15-ff0c-4305-9e72-5be7f70f244d','1037','','Staff Loan- Bicycle', '','');
Insert Into Product values ('d02ce06b-1375-4902-84f4-0f3586d48842','6602','','ABS-5 Years', '','');
Insert Into Product values ('218da60a-4d8c-4417-8c2a-ddfcdfd1df66','6603','','ABS-10 Years', '','');
Insert Into Product values ('4b5c01c7-0c24-4848-99de-5d26cb692809','3999','','Loan Accounts', '','');
Insert Into Product values ('cd5a5525-196a-4686-8342-deee4d868077','1101','','NO FRILLS CURRENT ACCOUNT', '','');
Insert Into Product values ('11223344556677889900','11223344556677889900','','AGRANI BANK MILLIONAIRE DEPOSIT', '','');
Insert Into Product values ('6001','6001','SAVINGS ACCOUNT', '','','');


Insert Into Branch values ('BD0010870','BD0010870','MOHAMMADPUR MAGURA 0870', '', '', '', '');
Insert Into Branch values ('BD0014006', 'BD0014006','PRINCIPAL BR  DHAKA', '', '', '', '');
Insert Into Branch values ('BD0012412','BD0012412','CHAWKBAZAR  BARISAL', '', '', '', '');
Insert Into Branch values ('BD0010541','BD0010541','MISSION MORE BRANCH', '', '', '', '');
Insert Into Branch values ('BD0010559','BD0010559','PAISHARHAT BRANCH', '', '', '', '');
Insert Into Branch values ('4001','4001','PAISHARHAT BRANCH', '', '', '', '');

INSERT INTO permission (permissionoid, method, url, permissionname) VALUES ('sdsdfsdfsdf', 'POST', '/v1/login/password', 'PASSWORD.CREATE');
INSERT INTO permission (permissionoid, method, url, permissionname) VALUES ('sdfdsfds', 'PUT', '/v1/login/password', 'PASSWORD.UPDATE');
INSERT INTO permission (permissionoid, method, url, permissionname) VALUES ('sdfdFGDR3242sfds', 'POST', '/v1/registration/casa', 'REGISTRATION.REQUEST');
INSERT INTO permission (permissionoid, method, url, permissionname) VALUES ('sdfdsfFGDFWAW232er3ds', 'POST', '/v1/registration/confirmation', 'REGISTRATION.CREATE');
--
--INSERT INTO ibuser (ibuseroid, userid, branchoid, password, role, status, createdon, editedon, currentversion) VALUES('56dfxdfdsfdsfabd-d438-4135-a008-ca5ff2b812c1', 'mushfika.faria.nova', '4001',NULL, NULL, 'UserIdGenerated', '2020-03-08 14:52:55.000', NULL, '1');
--
--INSERT INTO ibuser (ibuseroid, userid, branchoid, password, role, status, createdon, editedon, currentversion) VALUES('56dffabd-d438-4135-a008-ca5ff2b812c1', 'md.hafizur.rahman', '4001', NULL,NULL, 'PasswordCreated', '2020-03-08 14:52:55.000', NULL, '1');
INSERT INTO ibconsumer (ibconsumeroid, ibuseroid, fullname, photoid, photoidtype, dateofbirth, mobileno, createdon, editedon, currentversion) VALUES('91fe3b9c-da15-41fc-9d2c-e3c4d2bac59e', '56dffabd-d438-4135-a008-ca5ff2b812c1', 'MD. HAFIZUR RAHMAN', '', NULL, '1990-04-29', '01912016260', '2020-03-08 14:52:55.248', NULL, '1');
INSERT INTO customer (customeroid, bankcustomerid, mnemonic, fullname, mobileno, dateofbirth, bankoid, status, createdon, editedon, currentversion, gender, ibconsumeroid) VALUES('17fe925a-69b5-469e-a9b0-389b6db2a627', '26662344', '26662344', 'MD. HAFIZUR RAHMAN', '01912016260', '1990-04-29', '010', 'Active', '2020-03-08 14:52:55.000', NULL, '1', 'MALE', '91fe3b9c-da15-41fc-9d2c-e3c4d2bac59e');
INSERT INTO account (accountoid, bankaccountno, mnemonic, accounttitle, productoid, productname, producttype, currency, lastknownbalance, lastknownbalanceasof, jointholderjson, cbsaccountstatus, bankoid, branchoid, currentversion, cbscurrentversion, status, editedon, createdon, postingrestrict) VALUES('37161c1e-ec9d-482f-8e20-9d87d3dd85d2', '0200008628060', '26662344', 'MD. HAFIZUR RAHMAN', '6001', 'SAVINGS ACCOUNT', NULL, 'BDT', 542365821.020000, '2020-03-08 14:52:55.000', NULL, 'ACTIVE', '010', 'BD0014006', '1', '1', 'Active', NULL, '2020-03-08 14:52:55.000', '');
INSERT INTO account (accountoid, bankaccountno, mnemonic, accounttitle, productoid, productname, producttype, currency, lastknownbalance, lastknownbalanceasof, jointholderjson, cbsaccountstatus, bankoid, branchoid, currentversion, cbscurrentversion, status, editedon, createdon, postingrestrict) VALUES('f1b86b3b-ce1e-451e-b8e9-ae02e7fd924d', '0200008627060', '26662344', 'MD. HAFIZUR RAHMAN', '6001', 'SAVINGS ACCOUNT', NULL, 'BDT', 50000.000000, '2020-03-08 14:52:55.000', NULL, 'ACTIVE', '010', 'BD0014006', '1', '1', 'Active', NULL, '2020-03-08 14:52:55.000', '');
INSERT INTO customeraccount (customeroid, accountoid) VALUES('17fe925a-69b5-469e-a9b0-389b6db2a627', '37161c1e-ec9d-482f-8e20-9d87d3dd85d2');
INSERT INTO customeraccount (customeroid, accountoid) VALUES('17fe925a-69b5-469e-a9b0-389b6db2a627', 'f1b86b3b-ce1e-451e-b8e9-ae02e7fd924d');
--
--INSERT INTO ibuser (ibuseroid, userid, branchoid, password, role, status, createdon, editedon, currentversion) VALUES('fec58c18-c7eb-4689-8a54-3327e5af4838', 'mushfika.faria', '4001','$2a$10$U6nuIwaRV0uBMjsFZpi6Ee8ftMVgbqypHHUWklD0UET4DJ44XVoru', NULL, 'PasswordCreated', null, null, '1');
INSERT INTO ibconsumer(ibconsumeroid, ibuseroid, fullname, photoid, photoidtype, dateofbirth, mobileno, createdon, editedon, currentversion) VALUES('0e6d5cb7-ed1f-4252-9d62-5707b08169b2', 'fec58c18-c7eb-4689-8a54-3327e5af4838', 'MUSHFIKA.FARIA', '', NULL, '1994-10-25', '01717433473', null, NULL, '1');
INSERT INTO customer(customeroid, ibconsumeroid, bankcustomerid, mnemonic, gender, fullname, mobileno, dateofbirth, bankoid, status, createdon, editedon, currentversion) VALUES('9cf4843f-1b5d-4038-bc78-8b14f55354de', '0e6d5cb7-ed1f-4252-9d62-5707b08169b2', '26662345', '26662345', 'FEMALE', 'MUSHFIKA.FARIA', '01717433473', '1994-10-25', '010', 'Active', null, NULL, '1');
INSERT INTO account(accountoid, bankaccountno, mnemonic, accounttitle, productoid, productname, producttype, currency, lastknownbalance, lastknownbalanceasof, jointholderjson, postingrestrict, cbsaccountstatus, bankoid, branchoid, currentversion, cbscurrentversion, status, editedon, createdon) VALUES('57944276-76e7-4321-9bd0-183e0371d58a', '0200008627061', '26662345', 'MUSHFIKA.FARIA', '6001', 'SAVINGS ACCOUNT', NULL, 'BDT', 542365821.020000, null, NULL, '', 'ACTIVE', '010', 'BD0014006', '1', '1', 'Active', NULL, null);
INSERT INTO account (accountoid, bankaccountno, mnemonic, accounttitle, productoid, productname, producttype, currency, lastknownbalance, lastknownbalanceasof, jointholderjson, postingrestrict, cbsaccountstatus, bankoid, branchoid, currentversion, cbscurrentversion, status, editedon, createdon) VALUES('ae466720-29d8-40bc-91dc-d14832db7828', '0200008627060', '26662345', 'MUSHFIKA.FARIA', '6001', 'SAVINGS ACCOUNT', NULL, 'BDT', 50000.000000, null, NULL, '', 'ACTIVE', '010', 'BD0014006', '1', '1', 'Active', NULL, null);
INSERT INTO customeraccount (customeroid, accountoid) VALUES('9cf4843f-1b5d-4038-bc78-8b14f55354de', '57944276-76e7-4321-9bd0-183e0371d58a');

INSERT INTO customeraccount (customeroid, accountoid) VALUES('9cf4843f-1b5d-4038-bc78-8b14f55354de', 'ae466720-29d8-40bc-91dc-d14832db7828');

INSERT INTO transaction (transactionoid, transtype, transamount, chargeamount, vatamount, cbstransactiondate, transstatus, failurereason, bankoid, requestid, cbsreferenceno, narration, remarks, traceid, debitedaccountoid, debitedaccount, debitedaccountcustomeroid, debitedaccountcbscustomerid, debitedaccountbranchoid, creditedaccountoid, creditedaccount, creditedaccountcustomeroid, creditedaccountcbscustomerid, creditedaccountbranchoid, createdby, createdon, offsetotp) VALUES('gdffdgfdgghg-dsfrdsd-234234-sfgfd', 'own', 200.000000, 0.000000, 0.000000, NULL, 'OtpSent', NULL, 'dfdff-231321-dsvffdf', NULL, NULL, NULL, 'transfer money', '1234567y', 'ae466720-29d8-40bc-91dc-d14832db7828', '0200008627060', '9cf4843f-1b5d-4038-bc78-8b14f55354de', '26662345', 'BD0014006', '57944276-76e7-4321-9bd0-183e0371d58a', '0200008627061', '9cf4843f-1b5d-4038-bc78-8b14f55354de', '26662345', '0200008627061', 'NOVA', '2020-03-08 18:20:25', '212345');



INSERT INTO permission (permissionoid,permissionname,url,method) VALUES
('2194df62-44d1-4ea8-adbb-002409e9cfc4','ALL','/v1/geodata/upazillas/**','GET')
,('a7003236-ccbf-4077-b706-fd652ffd4a96','ALL','/v1/geodata/unions/**','GET');

INSERT INTO geodata (geodataoid,bbscode,divisioncode,districtcode,upazillacode,unioncode,divisionname,districtname,upazillaname,unionname,municipalityname) VALUES
('30260230','30260230','30','26','02','30','Dhaka','Dhaka','Adabor','Ward No-30','Dhaka North City Corporation')
,('30260233','30260233','30','26','02','33','Dhaka','Dhaka','Adabor','Ward No-33','Dhaka North City Corporation')
,('30260417','30260417','30','26','04','17','Dhaka','Dhaka','Badda','Ward No-17','Dhaka North City Corporation')
,('30260421','30260421','30','26','04','21','Dhaka','Dhaka','Badda','Ward No-21','Dhaka North City Corporation')
,('30260413','30260413','30','26','04','13','Dhaka','Dhaka','Badda','Badda',NULL)
,('30260419','30260419','30','26','04','19','Dhaka','Dhaka','Badda','Beraid',NULL)
,('30260482','30260482','30','26','04','82','Dhaka','Dhaka','Badda','Satarkul',NULL)
,('30260527','30260527','30','26','05','27','Dhaka','Dhaka','Bangshal','Ward No-27','Dhaka South City Corporation')
,('30260530','30260530','30','26','05','30','Dhaka','Dhaka','Bangshal','Ward No-30','Dhaka South City Corporation')
,('30260531','30260531','30','26','05','31','Dhaka','Dhaka','Bangshal','Ward No-31','Dhaka South City Corporation')
;
