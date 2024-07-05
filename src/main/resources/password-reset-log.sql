
CREATE TABLE public."permission" (
     permissionoid varchar(128) NOT NULL,
     permissionname varchar(128) NOT NULL,
     url varchar(128) NOT NULL,
     "method" varchar(128) NOT NULL,
     topmenuid varchar(256) NULL,
     leftmenuid varchar(256) NULL,
     createdon timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT pk_permission PRIMARY KEY (permissionoid)
);

CREATE TABLE public."role" (
       roleoid varchar(128) NOT NULL,
       rolename varchar(128) NOT NULL,
       status varchar(32) NOT NULL,
       createdby varchar(128) NOT NULL DEFAULT 'System'::character varying,
       createdon timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       editedby varchar(128) NULL,
       editedon timestamp NULL,
       "domain" varchar(256) NULL,
       roledescription varchar(256) NULL,
       CONSTRAINT pk_role PRIMARY KEY (roleoid)
);


CREATE TABLE public.ibuser (
    ibuseroid varchar(128) NOT NULL,
    userid varchar(128) NOT NULL,
    "password" varchar(128) NULL,
    status varchar(32) NOT NULL,
    createdon timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    editedon timestamp NULL,
    currentversion varchar(32) NOT NULL,
    "role" varchar(128) NULL,
    resetrequired varchar(32) NOT NULL DEFAULT 'YES'::character varying,
    username varchar(256) NULL,
    email varchar(256) NULL,
    mobileno varchar(64) NULL,
    bankoid varchar(128) NULL,
    branchoid varchar(128) NULL,
    blocktime timestamp NULL,
    blockstatus varchar(32) NULL,
    userphotopath varchar(1024) NULL,
    blockedby varchar(128) NULL,
    blockerrole varchar(128) NULL,
    blockedon timestamp NULL,
    fingerprintrequiredforlogin varchar(16) NULL DEFAULT 'No'::character varying,
    loginperiodstarttime varchar(32) NULL,
    loginperiodendtime varchar(32) NULL,
    logindisablestartdate timestamp NULL,
    logindisableenddate timestamp NULL,
    templogindisablestartdate timestamp NULL,
    templogindisableenddate timestamp NULL,
    lastlogintime timestamp NULL,
    lastlogouttime timestamp NULL,
    usertype varchar(64) NULL,
    typeoid varchar(128) NULL,
    traceid varchar(128) NULL,
    createdby varchar(128) NOT NULL DEFAULT 'System'::character varying,
    editedby varchar(128) NULL,
    approvedby varchar(128) NULL,
    approvedon timestamp NULL,
    remarkedby varchar(128) NULL,
    remarkedon timestamp NULL,
    isapproverremarks varchar(32) NULL,
    approverremarks text NULL,
    isnewrecord varchar(32) NULL,
    activatedby varchar(128) NULL,
    activatedon timestamp NULL,
    closedby varchar(128) NULL,
    closedon timestamp NULL,
    closingremark text NULL,
    unblockedby varchar(128) NULL,
    unblockedon timestamp NULL,
    deletedby varchar(128) NULL,
    deletedon timestamp NULL,
    deletionremark text NULL,
    CONSTRAINT ck_isapproverremarks_ibuser CHECK ((((isapproverremarks)::text = 'Yes'::text) OR ((isapproverremarks)::text = 'No'::text))),
    CONSTRAINT ck_isnewrecord_ibuser CHECK ((((isnewrecord)::text = 'Yes'::text) OR ((isnewrecord)::text = 'No'::text))),
    CONSTRAINT pk_ibuser PRIMARY KEY (ibuseroid)
);
CREATE UNIQUE INDEX index_userid_ibuser ON public.ibuser USING btree (userid);


CREATE TABLE public.passwordresetlog (
     passwordresetlogoid varchar(128) NOT NULL,
     userid varchar(128) NOT NULL,
     ibuseroid varchar(128) NOT NULL,
     oldpassword varchar(128) NOT NULL,
     newpassword varchar(128) NOT NULL,
     createdby varchar(128) NOT NULL,
     createdon timestamp NOT NULL DEFAULT now(),
     editedby varchar(128) NULL,
     editedon timestamp NULL,
     CONSTRAINT pk_passwordresetlog PRIMARY KEY (passwordresetlogoid),
     CONSTRAINT fk_ibuseroid_passwordresetlog FOREIGN KEY (ibuseroid) REFERENCES public.ibuser(ibuseroid)
);

CREATE TABLE public.userrole (
     ibuseroid varchar(128) NOT NULL,
     roleoid varchar(128) NOT NULL,
     CONSTRAINT fk_ibuseroid_ibuserrole FOREIGN KEY (ibuseroid) REFERENCES public.ibuser(ibuseroid),
     CONSTRAINT fk_roleoid_ibuserrole FOREIGN KEY (roleoid) REFERENCES public."role"(roleoid)
);



INSERT INTO public.ibuser (ibuseroid,userid,"password",status,createdon,editedon,currentversion,"role",resetrequired,username,email,mobileno,bankoid,branchoid,blocktime,blockstatus,userphotopath,blockedby,blockerrole,blockedon,fingerprintrequiredforlogin,loginperiodstarttime,loginperiodendtime,logindisablestartdate,logindisableenddate,templogindisablestartdate,templogindisableenddate,lastlogintime,lastlogouttime,usertype,typeoid,traceid,createdby,editedby,approvedby,approvedon,remarkedby,remarkedon,isapproverremarks,approverremarks,isnewrecord,activatedby,activatedon,closedby,closedon,closingremark,unblockedby,unblockedon,deletedby,deletedon,deletionremark) VALUES
                          ('80b4c22a-886d-4ea0-85d1-e8b6e8c14df9','0bb8ac068a5ace6c2703b9ba984fc8516f8e503747f2d0f2fc9f6a3460abc282','$2a$10$Vk1l8QfpMRmgHIojH2XW.OOrjDBHnkhwV8ZiZda1vXLiBB6Dw1WAm','PasswordCreated','2021-11-21 13:11:35.422983',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('367bcc69-5edf-41ea-8a26-921df4377a63','f36310cec82878f5744aabebdf542d07ce6f78742b8fa9d2f0b4d7a77f0ec7f3','$2a$10$N1WOpdyu2GySMwkEwhdxx.dJPZ3/nRf9fTt8T23TMmQ/9DsBghd26','PasswordCreated','2021-09-26 17:56:59.648373',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('9ea9e845-1e8b-4b1e-851d-7ce349341550','36b7af36cd7384af019af70a80bff43a0f7b7fc306a2d1b72b3cdd0e8f3dac71','$2a$10$c3ZrZowyyJige33GyhuzYO5iFpbrLwDbzjVm5ySB2bnxUoW.eF5t2','PasswordCreated','2021-09-26 17:59:59.103658',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('5f3d1dda-6241-4f58-949e-be5a7e1ca7ab','340a5652775152b1501af5d24c4713b7bf7ee50a213b7a922ccd96d5ca8fc0de','$2a$10$9ypA.L3nCA5GHaXN0Yfy3.gogNAZ/dXLy1BT2vpHG4RR0KJ.gwwX6','PasswordCreated','2021-11-21 13:14:29.318628',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('2ac57d93-b548-44b4-a628-40ace2380aa6','4c6b978a24387ef280ee12398c895df831a31c2090796fc26497c1ea9a8bd54e','$2a$10$Ll0wK9zemEWw.M7H20ulKOXgfSsbQwU8krDxMP8iz3JtXK7LKkm36','PasswordCreated','2021-11-23 15:20:15.493409',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('8cf40e01-d732-469f-8ced-9098ff1f622c','3bde89f93330c3fc4ac5b2c943e5135afc0f23c8472b4f60274136c41000169a','$2a$10$AW4Viwaxixj9IGhD3ijDjeqW74sQbWxvmcouoCgnqMv.D0Q691b9y','PasswordCreated','2021-11-23 15:35:44.015898',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('abf7d470-af72-4ba2-9633-2181ae115391','b5657a6d53cb539ffb000d817742b9439de75909cc7889556e4aeb40b4fd8b4b','$2a$10$On/B3mt/agidgSib9oGsT.menzIw4dQOtrlTr4A5ynX7jcLtZs5PO','PasswordCreated','2022-01-26 13:31:18.034622',NULL,'1',NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('09e54524-89de-407a-92ee-500c26eb1c66','5ce05252b6fa0ef14632387038441aac0c0491e328bfe9aa2ed64a3e3284633f','$2a$10$kUFCa5XLUxLghDaaWaWVwOiFka74P9lnAAJEi085oLhxrd7HjXJ6O','PasswordCreated','2021-09-26 18:09:03.094448',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('d258561e-df24-4ee4-bfa2-46af5529f02e','5e9fcf930aeaaf66b8a365ab3f9cc3388615b0265ffa722b85a2e94abfc7598d','$2a$10$bc8XtfH2yEVFtrF4eutYV.FpdkIhKKeOcVw0KcRbjfaIUTf.e9hAa','PasswordCreated','2021-09-26 18:09:40.977232',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('78a62218-39db-444b-a18c-87a3b8cf56b4','80c642165a4d97056a07a8a58b1038fff9454213f31a7e399b1cc8a53e74aac5','$2a$10$yHnLRJo0G6q5Wq186oxzMO8NI7UjKs6FDxxSfDNQSu7LMVuJEO8Re','PasswordCreated','2021-08-23 19:36:11.10897',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

INSERT INTO public.ibuser (ibuseroid,userid,"password",status,createdon,editedon,currentversion,"role",resetrequired,username,email,mobileno,bankoid,branchoid,blocktime,blockstatus,userphotopath,blockedby,blockerrole,blockedon,fingerprintrequiredforlogin,loginperiodstarttime,loginperiodendtime,logindisablestartdate,logindisableenddate,templogindisablestartdate,templogindisableenddate,lastlogintime,lastlogouttime,usertype,typeoid,traceid,createdby,editedby,approvedby,approvedon,remarkedby,remarkedon,isapproverremarks,approverremarks,isnewrecord,activatedby,activatedon,closedby,closedon,closingremark,unblockedby,unblockedon,deletedby,deletedon,deletionremark) VALUES
                          ('8fd6ab02-e207-45e2-ba68-966d4d34c398','amiya.tisha.977484121','$2a$10$LXRHRiKySJuK6aN4dXmv1u3.zOHnvJqYM5V43t7ZSv3FbcNhiQZ5G','Active','2021-09-02 17:31:29.97355','2021-09-02 17:31:50.991982','1',NULL,'Yes','MUSHFIKA FARIA A. BAREQ','','01717433474',NULL,'4001',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'BankStaff','03737501-5e45-4f03-baf9-3d2115336530',NULL,'mushfika.faria222','mushfika.faria222','mushfika.faria.782','2021-09-02 17:33:10.075109',NULL,NULL,'No',NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('bad1712c-ea0f-40f0-b22b-6e1dd985663c','f7d962c8b4513c8e58a8fb2e0c4688f88cbaf7ce5081562d90f2913b604e58d9','$2a$10$xAnFa2KSv32haUrgOoBF0.32hCRfuz8Z7.0Vd3n8vvBuqJRlj6vXC','PasswordCreated','2021-10-10 12:43:01.117875',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('b984de96-60a2-4009-966f-bcd275689617','''01737711756''','$2a$10$SXeaBDdR5UgRsfofm.nYWuIXlFDJ90eFhdk0XO3WMyB/b22gqRATm','Active','2021-12-19 17:30:39.059722','2022-01-04 18:52:19.522195','1',NULL,'No','Rahman Doer',NULL,'01737711756',NULL,'4001',NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System','01737711756',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('545179db-ccdd-4e99-98c8-cd9c4aa56c81','3644975f07549a897e4b5aa44b0b3ad252ea06ae5a0c2dbfb61d1383260da4b4','$2a$10$vA8dMluKhPBB8TbFSwTfouiCpe19ji/RICUUjEuw0Ufk3m4nj9tWC','PasswordCreated','2021-05-25 16:42:00.702794',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('2b1249ef-0aad-41da-b3ed-9e20fbda2e97','4cdb55519bad243eb88b0baa343234bb0552870a3d36066cb4b238e15b921b0a','$2a$10$C9sBSNORKZNnEpDMDtqcCOrH.XI1x4WOtcnvMMowg1OuLw/sjRN6m','PasswordCreated','2021-06-16 13:15:26.927499',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('f4e1bf06-6551-465e-aba8-a4ce76035ce2','20d8bd338fcc2c681a77e4fbfc1fa36edfcd2f0e61641efb85cb931cfa9f5eb6','$2a$10$TJNi5bEvn4GUJPQK/F/4q.c7VHCz5FdWQZELmz8D/aDoUzj/JPj8a','PasswordCreated','2021-08-23 23:22:14.869656',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('f64e93ea-15f9-4e58-918a-946c69e658bd','amiya.tisha.454974551','$2a$10$3EcUgbZUpByTpSiGqsFZ3eH7.2kVu1ljg43YhDkpx3MIJ4l4v5JSm','Active','2021-05-01 00:12:09.668','2021-12-20 14:10:32.679842','1',NULL,'Yes','MUSHFIKA FARIA',NULL,'01717433473',NULL,'4001',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'DoerStaff','DS110829-20190506123142231',NULL,'mushfika.faria222','mushfika.faria222','mushfika.faria.782','2021-12-20 14:11:27.57154','mushfika.faria.782','2021-12-20 14:09:35.722195','No','Remark for Yes','No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('36e73061-c4c5-48c5-9e7c-cdfd5e376942','4c68980d33c27caa83e310caf3671bd5bcfef803ff305c1cc9686836cd770e74','$2a$10$oS/XXZz1jlVyvatgCAZ0bu9rXoCnAu34i1aVbyM3dvT2Gs2md8OLO','PasswordCreated','2021-11-09 15:21:15.201155',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('4f855930-fdc6-4ea2-addc-cb3c0d95705a','7fee604d0c2cf59285ce87c4481115b82490c1695a97f16888baa2c26d0a3322','$2a$10$.h/FnRX17H984XqlJHAd5.vNCUNoCEsV/kHPkQoHrse9AgHUb6r9e','PasswordCreated','2021-08-04 19:40:40.939045',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('86d8802e-3068-4d2a-bf6b-89f645e68939','5f2ea06c38a1177741ec42b45068295e57bcf2460d5f92bbd25ca85652871a56','$2a$10$NpKX/mUzmU9fleDz84lkj.d2pEGmifXIhxz1gBpYZq.ynB5Ap6Y5S','PasswordCreated','2021-08-26 13:12:08.901595',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

INSERT INTO public.ibuser (ibuseroid,userid,"password",status,createdon,editedon,currentversion,"role",resetrequired,username,email,mobileno,bankoid,branchoid,blocktime,blockstatus,userphotopath,blockedby,blockerrole,blockedon,fingerprintrequiredforlogin,loginperiodstarttime,loginperiodendtime,logindisablestartdate,logindisableenddate,templogindisablestartdate,templogindisableenddate,lastlogintime,lastlogouttime,usertype,typeoid,traceid,createdby,editedby,approvedby,approvedon,remarkedby,remarkedon,isapproverremarks,approverremarks,isnewrecord,activatedby,activatedon,closedby,closedon,closingremark,unblockedby,unblockedon,deletedby,deletedon,deletionremark) VALUES
                          ('6c0cb837-712b-451b-95e9-0195f611e193','2220f1caf9bb815051a65504fb1f521997b099f64b27e078b6cdb2e69f309599','$2a$10$5EQXynu3t8ZWaaHkrCHPju1rq4QlhihE5m2h5Bd2zrdx8nUlTLb2q','PasswordCreated','2021-08-26 13:17:31.904652',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('9314216e-1648-4703-9c52-640301b43a97','6a98763119c3b6915330af8dfbff380d00f937cd625b9d2c4f8d7a0943507016','$2a$10$dhjZixTmOIsIwXOm5BBImOQuU7X69cIxPqcmvXtnmQGSaYoiUluIS','PasswordCreated','2021-08-26 13:17:47.73397',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('b248ad03-0068-409c-bd71-5783c582f6c7','4b39010ed5d2ca9842950ff17543191cbe4981ef1904cd2552f86610552a3a34','$2a$10$KKgYmFTxq8ylEctMh8RFH.2sywJ0TTJsro/ZpuxlQOIrkoqFGwqy2','PasswordCreated','2021-08-20 00:09:39.672728',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('66b1e248-8ef5-4fc4-bd6f-47e1f9fe41e7','8986742d031a6d930c81dc07bd9d2e3dc797e90625af4c14264ac44bf9fa78bd','$2a$10$ITkiMDMYbjF/yegsdveYHOplhfYRSpeO2VgCABJxamYOdCKnmILH6','PasswordCreated','2021-08-26 13:17:58.905581',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('51ee8ebd-97e6-43bb-a295-e22b50cb3e34','6f7b5ab3399532e5ce4ac6d6d5abeb2a7b93390ef71a66ee9789405392ee5dea','$2a$10$UJCtryL4r0eWr8RW2FvF0uDGcXk0FquqO2Mto/ORinKkXvyrQj/s6','PasswordCreated','2021-11-21 13:57:59.776979',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('95ca703d-f41f-4fd6-a961-e661795dbc18','Xza5fScnyh','asdf12','Active','2022-01-26 13:40:14.005718',NULL,'1',NULL,'No','Mushfika Faria',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'DoerStaff','89ec0a61-7795-4768-b482-6972ca37603e',NULL,'mushfika.faria','mushfika.faria','mushfika.faria','2022-01-26 13:40:14.497566',NULL,NULL,'No',NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('72148760-be5f-4fbd-a4a9-7d32892ccffc','amiya.tisha.109493551','$2a$10$MNyFgVEv/WNY.nSNxOslx.SjfxETvbMkSgJzFtusaf4OdRkMnhmga','Active','2021-09-05 10:45:48.059945','2021-09-05 10:46:09.336688','1',NULL,'Yes','MUSHFIKA FARIA A. BAREQ','','01717433474',NULL,'4001',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'BankStaff','03737501-5e45-4f03-baf9-3d2115336530',NULL,'mushfika.faria222','mushfika.faria222','mushfika.faria.782','2021-09-05 10:47:25.195129',NULL,NULL,'No',NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('955c11f8-1325-45a3-8333-cf43f3057d9b','45DjS3KCOf','asdf12','Active','2022-01-26 13:40:14.60129',NULL,'0',NULL,'No','Mushfika Faria',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'DoerStaff','89ec0a61-7795-4768-b482-6972ca37603e',NULL,'mushfika.faria',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Yes',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('f10be497-e7a5-4320-8ae5-8871cbc6d9ea','8EviSPJosv','asdf12','Active','2022-01-26 13:40:14.702367',NULL,'1',NULL,'No','Mushfika Faria',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'DoerStaff','89ec0a61-7795-4768-b482-6972ca37603e',NULL,'mushfika.faria','mushfika.faria','mushfika.faria','2022-01-26 13:40:14.759961',NULL,NULL,'No',NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('f14b213a-e3e8-46ca-9c03-2889d856ece4','XgZ5wEJjm3','asdf12','Active','2022-01-26 13:40:15.085159',NULL,'0',NULL,'No','Mushfika Faria',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'DoerStaff','89ec0a61-7795-4768-b482-6972ca37603e',NULL,'mushfika.faria',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Yes',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

INSERT INTO public.ibuser (ibuseroid,userid,"password",status,createdon,editedon,currentversion,"role",resetrequired,username,email,mobileno,bankoid,branchoid,blocktime,blockstatus,userphotopath,blockedby,blockerrole,blockedon,fingerprintrequiredforlogin,loginperiodstarttime,loginperiodendtime,logindisablestartdate,logindisableenddate,templogindisablestartdate,templogindisableenddate,lastlogintime,lastlogouttime,usertype,typeoid,traceid,createdby,editedby,approvedby,approvedon,remarkedby,remarkedon,isapproverremarks,approverremarks,isnewrecord,activatedby,activatedon,closedby,closedon,closingremark,unblockedby,unblockedon,deletedby,deletedon,deletionremark) VALUES
                          ('7f94b7b0-46c7-46b9-b2b8-5678cb1ec736','01844074058','$2a$10$vvbZOu5GCtS56FQFOh1hFeKUJiJ8ZaO9vNzJgZkOtixWNqbM4IuhS','PasswordCreated','2021-10-21 16:54:13.962552',NULL,'1',NULL,'Yes','Ejaj',NULL,'01844074058',NULL,'4001',NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('c111df72-8ca2-47a0-8703-50a78a42d991','e6a5217b7d09b19dd72b7993fa4347c693e2be90b6e4c7b946e1e82bacf8dcd1','$2a$10$dekhaszOfXLXHl03kfo4rOlja3FsT.sSwDxC/OMDNeNDetzd6.ojm','PasswordCreated','2021-12-05 10:46:38.64158',NULL,'1',NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('90df81f6-e9e3-45fc-9ee8-0e7a6dd3b356','cd316d0bcc8251dcab72937baa3f76f324042f69bd6610e4a85301a36c377e86','$2a$10$hnCPa.Lsf01ovv21VY.cF.ma7CCYLRuzoAn9PFw2zsrnmb5t.boE6','PasswordCreated','2021-05-23 19:47:24.954',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('5c16944b-8f0d-4046-9d4c-c6f425eb0865','57390fbe2cb6061819a6cc585b753fd85ac326a106a117bfda339a5b375eefaa','$2a$10$MFc20u8Tp49z9ceSy3lI3OtsZb2ywDi32DWtoiv3AoKohFmZfE352','PasswordCreated','2021-05-23 19:47:44.042',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('9b4acea9-e329-4db0-a101-68df6df204a9','identity.verifier.sprint.naumy','$2a$10$g99oBna0B.gpwXWabCdUK.PhsECNLBrmmsS.w16NFaZmeyjYe4Lbe','Active','2021-07-29 16:28:08.632384','2021-07-29 16:47:59.631452','2',NULL,'Yes','MD MAZHARUL HOQUE NAUMY','','01308097607',NULL,'4001',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'BankStaff','ad95ab32-a97f-44e9-886d-e15a71866504',NULL,'naumy.maker','naumy.maker','naumy.approver','2021-07-29 16:48:24.624833',NULL,NULL,'No',NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('3dc2ae0d-4060-4c34-bf5c-d93cf789baaa','central.approver.sprint.naumy','$2a$10$3bwT5wcv13DabQFKhMOZHeatlGxkZvGBjhV1R6LA8b8lllGAxIe3C','Active','2021-07-29 16:29:31.425939','2021-07-29 16:48:05.149349','2',NULL,'Yes','MD MAZHARUL HOQUE NAUMY','','01308097607',NULL,'4001',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'BankStaff','ad95ab32-a97f-44e9-886d-e15a71866504',NULL,'naumy.maker','naumy.maker','naumy.approver','2021-07-29 16:48:28.875009',NULL,NULL,'No',NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('1b215998-6951-40cd-bc81-75c69112ae5d','87005eda30dccbd51d9a11aa047295c8486f695c5ff4b23daf3bbe8813e32e10','$2a$10$ynNbBbyVM1pnNryGEES9gur7qsBKT4JE.H8NYQx2WQNctP8CVgm0C','PasswordCreated','2021-08-05 13:03:01.321827',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('2a30e3df-3858-435e-914e-c1ac55ef100f','30fe1051d8b3425fcf0d5c60b62eb2ab08cd58545b32d10cf7600989b554f062','$2a$10$CZPme/oXhSIB53Tdp5KSGuztPxGs.t15XITw2p1CdahnoT7k7ujwe','PasswordCreated','2021-08-05 13:10:39.785549',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                          ('dcf16474-7d9b-4814-b806-3713a26d2201','3209a60894f4403d29b6254fba4c98092c82abcc6baec3c1fa46992fab20ca04','$2a$10$iIrM7K3vpag2mgKBOqcRF.7YKTTEAgWBvHrBVbiO21reeZ74rKIBW','PasswordCreated','2021-08-05 13:12:44.929039',NULL,'1',NULL,'NO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'System',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                         ('dc77c805-f932-4350-99b0-0c2a7624713f','amiya.tisha.611265452','$2a$10$1CVDLY2BhdhE.zU/LuyKA.ln64eUSpMqAJJpHs9rVUoybdl3bjO3a','Active','2021-12-20 12:56:39.388404','2021-12-20 14:58:47.413331','1',NULL,'Yes','MUSHFIKA FARIA A. BAREQ','','01717433474',NULL,'4001',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9:00','20:00',NULL,NULL,NULL,NULL,NULL,NULL,'BankStaff','03737501-5e45-4f03-baf9-3d2115336530',NULL,'mushfika.faria222','mushfika.faria222','mushfika.faria.782','2021-12-20 15:15:40.150414','mushfika.faria.782','2021-12-20 14:53:38.314056','No','Remark for Yes','No',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);


-- INSERT INTO public.userrole (ibuseroid,roleoid) VALUES
--                                                     ('dc77c805-f932-4350-99b0-0c2a7624713f','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571'),
--                                                     ('4c972128-8250-4ad2-ab0f-29d2afd19998','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571'),
--                                                     ('aeac70f6-42f6-4da5-a6e8-43b6ad7fcf3f','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571'),
--                                                     ('da2bd5f2-8563-4cb4-9cc5-535fa03df1be','45jie04b-857w-3470-3hb4-557533f6b571'),
--                                                     ('c7be974b-38d6-43d5-957c-e82ee6bb70b1','45jie04b-857w-3470-3hb4-557533f6b571'),
--                                                     ('805ebad8-1e77-4465-91d7-7d6de7f79d52','d1b3d435-9131-48d1-bf65-e84244e585f3'),
--                                                     ('e85b8bf0-8b1d-48e7-bb72-305152654a3c','d1b3d435-9131-48d1-bf65-e84244e585f3'),
--                                                     ('6f567764-6c73-4d44-9192-b870974c8189','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('cfcafe58-cfcb-4f92-898a-9296db45814f','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('8a938be0-6135-4900-97af-05cd651723ac','b8242342e6-e349-4422-9428-f7324354a');
-- INSERT INTO public.userrole (ibuseroid,roleoid) VALUES
--                                                     ('bad1712c-ea0f-40f0-b22b-6e1dd985663c','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('7f94b7b0-46c7-46b9-b2b8-5678cb1ec736','d1b3d435-9131-48d1-bf65-e84244e585f3'),
--                                                     ('1bc6d0ba-a05b-45ac-89f9-ad171232fff9','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('40b45aed-93b1-4d4b-aa9f-6deda3875d90','45jie04b-857w-3470-3hb4-557533f6b571'),
--                                                     ('c111df72-8ca2-47a0-8703-50a78a42d991','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('36e73061-c4c5-48c5-9e7c-cdfd5e376942','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('62101313-b3bd-4776-ab65-d5826e3e0762','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('7bbaee37-c083-4853-ad22-f32c1543f8f1','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('dbf1e3df-87a8-4438-8b93-c002d3024ff7','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('b8756e7e-24be-4dc8-ab67-dbdbb2c88bd3','b8242342e6-e349-4422-9428-f7324354a');
-- INSERT INTO public.userrole (ibuseroid,roleoid) VALUES
--                                                     ('6c2a7d2e-9964-4db8-86d0-4622cd2850ea','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('e64d07cb-e443-4525-93fe-31ac1e1fb158','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('c56cb667-024e-4308-8951-c75e7b3ea3ce','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('be190d21-d599-443b-ae57-887c7c802053','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('b5039490-49e9-447a-bbac-17718327914e','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571'),
--                                                     ('8fd6ab02-e207-45e2-ba68-966d4d34c398','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571'),
--                                                     ('72148760-be5f-4fbd-a4a9-7d32892ccffc','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571'),
--                                                     ('88adaf3f-5994-4266-9b49-618d5843e55a','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571'),
--                                                     ('9e5d7db0-2985-4af4-878b-6e525789a08c','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571'),
--                                                     ('6e4c92c2-3bcd-4ea3-8041-ac4f6f40faf9','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571');
-- INSERT INTO public.userrole (ibuseroid,roleoid) VALUES
--                                                     ('2359723d-9183-481d-ba81-af32a1b197ae','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('24908fce-14bf-4eaa-9951-64d35f500e06','d1b3d435-9131-48d1-bf65-e84244e585f3'),
--                                                     ('86f39530-eff8-46a0-b03c-ea5fc83ebdc2','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('2ac57d93-b548-44b4-a628-40ace2380aa6','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('8cf40e01-d732-469f-8ced-9098ff1f622c','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('99319772-ef90-41b9-9d0b-d1028abab19f','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('bb3fb3d4-3685-49bb-b5d1-9cef9555cb35','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('a7a29198-cb82-4770-9ff1-04301bf42417','d1b3d435-9131-48d1-bf65-e84244e585f3'),
--                                                     ('b984de96-60a2-4009-966f-bcd275689617','d1b3d435-9131-48d1-bf65-e84244e585f3'),
--                                                     ('f64e93ea-15f9-4e58-918a-946c69e658bd','45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571');
-- INSERT INTO public.userrole (ibuseroid,roleoid) VALUES
--                                                     ('682bbc97-d35c-4e47-9734-74fe2d51255b','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('bfb8911c-9450-430b-9425-386b62930f5c','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('0d291b65-9a4f-4198-a7aa-464489f7b278','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('1dbd76a7-be30-4510-88e5-5f93a7ec7414','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('9b4acea9-e329-4db0-a101-68df6df204a9','45f696e0-45bf-4c9a-9aa3-0869a80526cf'),
--                                                     ('1a093d99-9e1f-47d6-9a2a-9f5dddb17775','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('3dc2ae0d-4060-4c34-bf5c-d93cf789baaa','b89d99e6-e279-48b2-9d28-f732af17554a'),
--                                                     ('38bdad82-73c2-497c-9e69-18e4bf9ab334','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('b3e53326-100d-4f30-9bf1-5691030661bf','b8242342e6-e349-4422-9428-f7324354a'),
--                                                     ('a49c33ab-a458-4e83-91e8-4272b6c68c18','b8242342e6-e349-4422-9428-f7324354a');

INSERT INTO public."role" (roleoid,rolename,status,createdby,createdon,editedby,editedon,"domain",roledescription) VALUES
           ('d1b3d435-9131-48d1-bf65-e84244e585f3','IBUSER','Active','System','2020-05-13 17:59:15.394165',NULL,NULL,'STAFF',NULL),
           ('45jie04b-857w-3470-3hb4-557533f6b571','MAKER','Active','System','2020-12-10 17:59:15',NULL,NULL,'STAFF',NULL),
           ('47afe04b-812a-4370-96b4-574608f6b572','ADMIN','Active','System','2020-05-13 17:59:15.394165',NULL,NULL,'STAFF',NULL),
           ('45jSDie04b-dffdw-34w0-3hb4-5ere533f6b571','APPROVER','Active','System','2020-12-10 17:59:15',NULL,NULL,'STAFF',NULL),
           ('45f696e0-45bf-4c9a-9aa3-0869a80526cf','BRANCH IDENTITY VERIFIER','Active','System','2021-02-09 15:19:15',NULL,NULL,'STAFF',NULL),
           ('b89d99e6-e279-48b2-9d28-f732af17554a','CENTRAL IDENTITY APPROVER','Active','System','2021-02-09 15:19:15',NULL,NULL,'STAFF',NULL),
           ('b8242342e6-e349-4422-9428-f7324354a','SELF_APPLICANT','Active','System','2021-03-01 10:57:06.432624',NULL,NULL,NULL,NULL);

-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('4db843a5dsfdsfd-245a-4dsfdsabc-8cdsfds58-03c00dfdsfds85ba115','SESSION.LOGOUT','/v1/user/logout','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('56b898f6-2332-4048-9c37-bfd660767b14','ALL','/v1/branches','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('0b28e734-ff88-4b1d-ab76-ed338a4a6e62','ALL','/v1/user/password/recovery/request/**','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('9a28f72e-af9d-4665-b6c6-1066b49f2770','PASSWORD.UPDATE','/v1/login/password','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('a3eca3bc-d829-454e-8d92-9673dd57f9fe','ALL','/v1/user/password/recovery/verification','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('8b5ce2f2-35ae-4dbb-8f65-4064d8e666d5','ALL','/v1/user/password/recovery/confirmation','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('112123e6fff603d-96b3-4a9a-aad8-e454ffggea6dad271','ALL','/v1/user/password/recovery/resend-otp/request/SMS','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('4db843a5-245a-4abc-8c58-03c0085ba115','ALL','/v1/login/password','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('50b4339c-f8a6-4028-acd2-08021a4a49a6','CUSTOMER.READ','/v1/customer','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e6SSD603d-96b3-4afs9a-aad8-e454fgfddea6dad271','ALL','/v1/user/profile/info','GET',NULL,NULL,'2020-12-24 16:43:11.94438');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('08f88617-31c5-41a0-9168-a4929bf4420b','ALL','/v1/banks/010/self/account','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('50b4339c-f8a6-4028-acd2-08021a4a49a6-k006-mobile-no','ALL','/v1/user/mobile/verify/**','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('50b4339c-f8a6-4028-acd2-08021a4a49a6-k006-nid','ALL','/v1/user/nid/verify/**','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13eeer6603d-96bwew3-4a9a-aad8-e4wew54ea6dad271','ALL','/v1/user/mobile/send','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13eeer03d-96bwew3-4a9a-aad8-e4wew5d271','ALL','/v1/user/mobile/verify','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13eedfsder03d-96fdbwew3-4a9a-aad8-e4wedfdsw5d271','ALL','/v1/user/mobile/resend','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('1dfsder03d-96fdbwdfew3-4a9a-aad8-e4w5d271','ALL','/v1/banks/010/self/draft/person','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('54fsder03d-fgg453-4a9a-aad8-4fewfe','ALL','/v1/banks/010/self/draft/image','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('67ffsder03d-fgg2321453-4a9a-aad8-4fet433wfe','ALL','/v1/banks/010/self/draft/account','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('f08f88617-31dfc5-41a0-9168-a4929bf4gger420b','ALL','/v1/banks/010/self/new/account','POST',NULL,NULL,'2020-12-24 16:43:11.94438');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('d599f645-9d6b-4764-a0da-45e66c1bf84a','ALL','/v1/geodata/divisions','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('e34cf5f6-e6b7-4005-9622-f2cc977842eb','ALL','/v1/geodata/districts/**','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('2194df62-44d1-4ea8-adbb-002409e9cfc4','ALL','/v1/geodata/upazillas/**','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('a7003236-ccbf-4077-b706-fd652ffd4a96','ALL','/v1/geodata/unions/**','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('a702303236-ccbf-407dd7-b706-dddfesr43','ALL','/v1/geodata/postcodes/**','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('a702we4236-ccbf-407dd7-b706-dht5fesr43','ALL','/v1/images/**','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13dsfsde6603d-96b3-4a9a-aad8-e454ea6dad2rewe71','ALL','/v1/declaration/conditions','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13dsfsde6603d-96b3-4a9a-aad8-e454erweea6dad271','ALL','/v1/declaration/terms','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('d1b3sdfdsd435-9131-48dsfd1-bf65-e842sdfds44e585f3','SESSIONS.UPDATE','/v1/user/sessions','PUT','','','2020-12-24 16:43:11.94438'),
--                                                                                                                ('e995d487-d66f-400b-940f-127f24a16aed','UNAUTH.NEW.READ','/v1/users/unauth/new','GET','User_Management','/new-list','2020-12-24 12:43:11');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('9a28f72e-af9d-4665-b6c6-1066b49f2770-k002','BENEFICIARIES.READ','/v1/beneficiaries*','GET','Beneficiaries_TopMenu','get_list','2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e66DFSD03d-96b3-4a9a-bbb8-h454DDSFDea6gad271','UNAUTH.USERS.APPROVAL','/v1/users/unauth/approval','GET','User_Management','/approval-list','2020-12-24 12:43:11'),
--                                                                                                                ('2194df62-44d1-4ea8-adbb-002409DFDSe9cfc4','UNATH.USER.APPROVAL','/v1/users/unauth/approval/*','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('2194dfdfsdf62-44d1-4ea8-adbb-002409DFDSe9cfc4','UNATH.USER.EDITABLE','/v1/users/unauth/editable/*','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('9a28dsfds72e-af9d-4665dsfds-b6c6-1066b49fdfdsf2770','SESSIONS.READ','/v1/user/sessions','GET','','','2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e66DFSD03d-96b3-4a9a-bbd8-e454DDSFDea6gad271','USERS.OID.READ','/v1/users/*','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e66DFSD03d-96b3-DFD4a9a-aad8-e454DDSfgfdgFDea6dad271','UNAUTH.USER.LOCK','/v1/users/unauth/new/lock','PUT','','','2020-12-24 16:43:11.94438'),
--                                                                                                                ('fadfe637-ba63-4be9-8b16-7420bfc4fe64-k013','TRANS.INTRA.PRE.CREATE','/v1/banks/35/accounts/*/*/transaction-request-types/intra/transaction-requests-pre-process','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('er32-734-ff88-4b1d-ab76-ed338a4a6e62-k-014','TRANS.INTRA.TRAN.CREATE','/v1/banks/35/accounts/*/*/transaction-request-types/intra/transaction-requests','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('8b5ce2f2-35ae-4dbb-8f65-4064d8e666d5-k011','TRANS.OTP.RESEND.CREATE','/v1/banks/35/accounts/*/*/transaction-resend-otp-request','POST',NULL,NULL,'2020-12-24 16:43:11.94438');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('a3eca3bc-d829-454e-8d92-9673dd57f9fe-k010','TRANS.OWN.PRE.CREATE','/v1/banks/35/accounts/*/*/transaction-request-types/own/transaction-requests-pre-process','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('e3c4fd56-8d2c-4fb3-ab1a-42d9e4aa0081-k012','TRANS.OWN.TRAN.CREATE','/v1/banks/35/accounts/*/*/transaction-request-types/own/transaction-requests','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('47SSafe04b-81SD2a-437XXZ0-96b4-5746df08f6b572','CHARGES.READ','/v1/banks/35/charges/ft-own','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('fadfe637-ba63-4be9-8b16-7420bfc4fe64-f001','ACCOUNTS.OID.TITLE.GET','/v1/banks/35/accounts/*/title-info','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('fadfe637-ba63-4be9-8b16-7420bfc4fe64','ACCOUNTS.OID.MINI.GET','/v1/banks/35/accounts/*/minimal-info','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('d1b3d435-9131-48d1-bf65-e84244e585f3','.UNAUTH.REGISTRATION.PASSWORD','/v1/registrations/unauth/password/**','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('47afe04b-812a-4370-96b4-574608f6b572','.UNAUTH.REGISTRATION.USERID','/v1/registrations/unauth/user-id/**','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('47afe04b-812a-4370-96b4-574608f6b572-k008','STEMENT.TRANS.XLS.READ','/v1/banks/35/accounts/*/xls/transactions','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('d1b3d435-9131-48d1-bf65-e84244e585f3-k007','STEMENT.TRANS.READ','/v1/banks/35/accounts/*/grid/transactions*','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('0b28e734-ff88-4b1d-ab76-ed338a4a6e62-k-009','STEMENT.TRANS.PDF.READ','/v1/banks/35/accounts/*/pdf/transactions','GET',NULL,NULL,'2020-12-24 16:43:11.94438');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('ddsf898f6-23df32-4048-9c37-bfdsfdd660767b14-k005','TRANS.PDF.READ','/v1/banks/35/accounts/*/pdf/transaction','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('qwq8b5csse2f2-35ae-4dssbb-8f65-406ghghts666d5','TRANSACTIONS.READ','/v1/banks/35/transactions','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('aswweca3bc-d829-45xs4e-8d92-9673dd572wdef9fe','REGISTRATIONS.READ','/v1/banks/35/registrations','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('50b4339c-f8a6-4028-acd2-08021a4a49a6-k006','BENEFICIARIES.RECENT.READ','/v1/beneficiaries/*/recent/*','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e6603d-96b3-4a9a-aad8-e454ea6dad27-k004','BENEFICIARIES.UPDATE','/v1/beneficiaries/*/*','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('56b898f6-2332-4048-9c37-bfd660767b14-k005','BENEFICIARIES.DELETE','/v1/beneficiaries/*/*','DELETE',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('749fb7a2-f49d-4954-9bf9-21470deefe68-k003','BENEFICIARIES.OID.READ','/v1/beneficiaries/*/*','GET','Beneficiaries_TopMenu','get_oid','2020-12-24 16:43:11.94438'),
--                                                                                                                ('4db843a5-245a-4abc-8c58-03c0085ba115-k001','BENEFICIARIES.CREATE','/v1/beneficiaries/*','POST','Beneficiaries_TopMenu','create_new','2020-12-24 16:43:11.94438'),
--                                                                                                                ('2194dfdfsdf62-44d1-4ea8-adbb-002406765yfSe9cfc4','UNATH.USER.REMARK','/v1/users/unauth/approver-remark/*','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('2194dfdfsdf62-44d1-4ea8-adbb-002406765yfSe9defsdcfc4','UNATH.USER.EDIT','/v1/users/unauth/*','PUT',NULL,NULL,'2020-12-24 16:43:11.94438');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('219546-sdf6sdsds1-4ea8-adbb-002406765yfSe9defsdcfc4','USER.OID.CREATE','/v1/users/*','POST',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('213432dfsdf-6sdsds1-4ea8-adbb-002406765yfSe9defsdcfc4','USER.OID.EDIT','/v1/users/*','PUT',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e66DFSD03d-96b3-4a9a-aad8-e454DDSFDea6gad271','USERS.READ','/v1/users','GET','User_Management','/all-list','2020-12-24 10:43:11'),
--                                                                                                                ('e995d487-d66f-400b-940f-127f24a16cvd','UNAUTH.OID.READ','/v1/users/unauth/*','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e66DFSD03d-96b3-4a9a-aad8-e454DDSFDea6dad271','UNAUTH.USER.CREATE','/v1/users/unauth/','POST','','','2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e66DFSD03d-96b3-4a9a-bbb8-j454DDSFDea6gad271','UNAUTH.USERS.MY.APPROVAL','/v1/users/unauth/my-approval','GET','User_Management','/my-approval','2020-12-24 16:43:11.94438'),
--                                                                                                                ('13e66DFSD03d-96b3-4a9a-bbb8-e454DDSFDea6gad271','UNAUTH.USERS.MY.EDITABLE','/v1/users/unauth/my-editable','GET','User_Management','/my-locked-list','2020-12-24 16:43:11.94438'),
--                                                                                                                ('e99SS5d487-d6SS6f-400b-940f-127f2SSS4a16aed','USERS.READ','/v1/users','GET','User_Management','/maker-all-list','2020-12-24 10:43:11'),
--                                                                                                                ('2a0b509d-9660-406f-8505-1bc51860a53f','CUSTOMER.REGISTRATION.NEW','/v1/customers/registration-verifier','GET','Customer_Registration','/identity-verifier-list','2021-02-09 10:43:11'),
--                                                                                                                ('c9d51fce-8298-4dfb-970f-377d89b7eeaf','CUSTOMER.REGISTRATION.APPROVAL','/v1/customers/registration-approval','GET','Customer_Registration','/central-approver-list','2021-02-09 10:43:11');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('a1796ff0-1aec-457a-8586-0c83375723a2','ROLES.READ','/v1/roles*','GET',NULL,NULL,'2021-02-09 17:43:11'),
--                                                                                                                ('a17fgfdf96ff0-1aec-457a-8586-0c833fgfdfg75723a2','STAFFS.READ','/v1/staffs*','GET',NULL,NULL,'2021-02-09 17:43:11'),
--                                                                                                                ('e981c762-01d2-445e-8a8f-4ceccc324692','BANK.STAFFS.READ','/v1/users/bank-staff','GET','Bank_Staff_Management','/bank-staff-all-list','2020-12-23 10:43:11'),
--                                                                                                                ('44690a8f-26e9-4b0f-8c12-75e5c6a40739','REGISTRATION.READ','/v1/registrations','GET','Customer_Registration','/registration-all-list','2020-12-24 16:43:11'),
--                                                                                                                ('515281ab-1b32-424e-864c-e89152c22351','UNAUTH.BANK.STAFFS.NEW.READ','/v1/bank-staffs/unauth/new','GET','Bank_Staff_Management','/bank-staff-new-list','2020-12-23 12:43:11'),
--                                                                                                                ('ec814d5a-1317-4277-9326-2a062ada6bdc','UNAUTH.REGISTRATION.VERIFY','/v1/registrations/unauth/mobile/verify','PUT',NULL,NULL,'2021-02-09 17:43:11'),
--                                                                                                                ('3b7f973a-7802-43f3-b558-ff814d7d40e3','UNAUTH.REGISTRATION.RESEND','/v1/registrations/unauth/mobile/resend','PUT',NULL,NULL,'2021-02-09 17:43:11'),
--                                                                                                                ('e3c4fd56-8d2c-4fb3-ab1a-42d9e4aa0081','ACCOUNTS.GET','/v1/banks/35/customer/accounts','GET',NULL,NULL,'2020-12-24 16:43:11.94438'),
--                                                                                                                ('65167466-b8ad-4392-a998-2091e8ff222e','UNAUTH.REGISTRATION.READ','/v1/registrations/unauth/new','GET',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('23df147b-a2f9-46bc-a765-518d15ca959c','UNAUTH.REGISTRATION.OID.READ','/v1/registrations/unauth/**','GET',NULL,NULL,'2020-12-24 16:43:11');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('0cab5435-2e2f-4034-9879-b6659b9020a5','UNAUTH.REGISTRATION.NEW.LOCK','/v1/registrations/unauth/new/lock ','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('ba39cf2a-7e20-4a7b-8313-bfa43f915947','UNAUTH.REGISTRATION.EDITABLE','/v1/registrations/unauth/editable','GET',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('c749b3c9-2b20-44fd-93e5-238c4e8c311d','UNAUTH.REGISTRATION.EDITABLE.LOCK.UNLOCK','/v1/registrations/unauth/editable/**','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('359398a6-7cdc-4684-af84-a5921db10ee9','UNAUTH.REGISTRATION.VERIFIER.REMARK','/v1/registrations/unauth/verifier-remark/','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('0bbacd52-e9f2-48b2-be33-d677a12a2f17','UNAUTH.REGISTRATION.MY.EDITABLE','/v1/registrations/unauth/my-editable','GET',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('9b7848e5-4104-4159-81c2-0d43bd214baf','UNAUTH.REGISTRATION.APPROVAL','/v1/registrations/unauth/approval','GET',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('a0c90395-d46d-4eab-81c5-8450844ed092','UNAUTH.REGISTRATION.MY.APPROVAL','/v1/registrations/unauth/my-approval','GET',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('44aebbce-9e58-4bf2-a94f-54eae1e4098e','UNAUTH.REGISTRATION.APPROVER.REMARK','/v1/registrations/unauth/approver-remark/','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('3bc6be2e-b5de-41b4-81a9-0c40e63918bb','REGISTRATION.CREATE','/v1/registrations/**','POST',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('1a1c91bc-6252-4db0-b2e1-42cfc6e0c93b','REGISTRATION.OID.READ','/v1/registrations/**','GET',NULL,NULL,'2020-12-24 16:43:11');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('5d1b1ac0-23b4-4811-b58e-81a40c6aa914','UNAUTH.REGISTRATION.APPROVAL.LOCK.UNLOCK','/v1/registrations/unauth/approval/**','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('9d2bc137-506b-4d6c-9488-255f9bf438f0','UNAUTH.REGISTRATION.UPDATE','/v1/registrations/unauth/my-editable/**','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('78090b79-b121-4c20-9d71-748d6bfe3546','REGISTRATION.UPDATE','/v1/registrations/my-approval/**','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('5445af4554b-542a-434570-9645b4-5746b572','.UNAUTH.REGISTRATION.IMAGE','/v1/registrations/unauth/image/**','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('4rrr137-54b-4d43-94438-255f934338f0','UNAUTH.REGISTRATION.VERIFY','/v1/registrations/unauth/verified/**','PUT',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('de14b967-29fc-4f8f-be93-25b96a99459f','UNAUTH.REGISTRATION.CREATE','/v1/registrations/unauth','POST','Customer_Registration','/create-new','2020-12-24 16:43:11'),
--                                                                                                                ('84f8e881-bc4a-4d47-bfd5-7608862ceacd','BANK.STAFFS.EDITABLE.READ','/v1/users/bank-staff/unauth/editable','GET','Bank_Staff_Management','/bank-staff-maker-all-list','2020-12-23 10:43:11'),
--                                                                                                                ('548c9bfd-8620-495d-822b-951f5ea6b044','CLIENT.DEVICE.REGISTER','/v1/banks/35/client-device','POST',NULL,NULL,'2021-04-06 18:30:27.032915'),
--                                                                                                                ('646e9079-b8ac-4482-b963-c2af1ece93c6','MINISTATEMENT.READ','/v1/banks/35/accounts/*/transactions/mini-statement','GET',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('45348f4-23452-43448-4537-bf35453467b14','ALL','/v1/branches/**','GET',NULL,NULL,'2020-12-24 16:43:11');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('34FDE8f4-23452-43448-4537-65Y6557b14','REGISTRATION.FORM.PDF','/v1/registrations/form/pdf/*','GET',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('6456079-b8ac-4482-b963-c2af1eceEWWc6','QUESTION.MINISTATEMENT.READ','/v1/banks/35/accounts/*/transactions/last-transactions','GET',NULL,NULL,'2020-12-24 16:43:11'),
--                                                                                                                ('814434596922-6d34563-4e75-8c43f9-6834548e629e2149','SERACH.ACCOUNT','/v1/banks/35/accounts','POST',NULL,NULL,'2021-02-09 17:43:11'),
--                                                                                                                ('8196922-6d63-4e75-8c43f9-68e629e2149','ALL','/v1/banks/35/accounts/self','POST',NULL,NULL,'2021-02-09 17:43:11'),
--                                                                                                                ('df6f5d09-9ab5-47bb-9eb4-e00f9ff460d2','ALL','/v1/banks/35/registrations/users/**','GET',NULL,NULL,'2021-02-09 17:43:11'),
--                                                                                                                ('ad18aa8e-1a17-431e-9f13-70304a186e66','ALL','/v1/banks/35/registrations/accounts/**','GET',NULL,NULL,'2021-02-09 17:43:11'),
--                                                                                                                ('edfaffc1-f0bb-4774-8a58-7394a3a4e1ff','ALL','/v1/banks/35/client-device/**/**','GET',NULL,NULL,'2021-04-06 18:32:58.501175'),
--                                                                                                                ('17be5aa7-a0fc-41b5-9b08-88a166dfbe77','UNAUTH.BANK.STAFF.CREATE','/v1/users/bank-staff/unauth','POST',NULL,NULL,'2021-04-11 15:07:44'),
--                                                                                                                ('b86acb05-5eea-444f-8afd-a6faba38691e','UNAUTH.BANK.STAFF.READ','/v1/users/bank-staff/unauth/new','GET',NULL,NULL,'2021-04-12 13:14:13.612889'),
--                                                                                                                ('75e388bd-db38-44a5-aa14-40a536293398','UNAUTH.BANK.STAFF.NEW.LOCK','/v1/users/bank-staff/unauth/new/lock','PUT',NULL,NULL,'2021-04-11 21:33:05.328639');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('21a3f292-dc0d-4117-8a8b-aa2c858d6ef5','UNAUTH.BANK.STAFF.EDITABLE.LOCK.UNLOCK.EDIT','/v1/users/bank-staff/unauth/editable/**','PUT',NULL,NULL,'2021-04-11 22:08:26.433749'),
--                                                                                                                ('462b2922-f49f-4b57-b4db-53844be29c16','UNAUTH.BANK.STAFF.APPROVAL.LOCK.UNLOCK','/v1/users/bank-staff/unauth/approval/*','PUT',NULL,NULL,'2021-04-12 13:52:13.229358'),
--                                                                                                                ('8a8cb98e-0f06-4d19-83c4-5990c818dd17','BANK.STAFFS.READ','/v1/bank-staffs','GET',NULL,NULL,'2020-12-24 10:43:11'),
--                                                                                                                ('d062a79a-744c-442f-a6de-72280cd308f7','BANK.STAFFS.READ','/v1/users/bank-staff/**','GET',NULL,NULL,'2021-04-13 14:28:29.490533'),
--                                                                                                                ('8693f539-4b15-44af-8a01-bf3975a2da4f','PERSONS.PHOTOID.READ','/v1/persons/**','GET',NULL,NULL,'2021-04-15 16:34:23.71189'),
--                                                                                                                ('b5392463-cfb1-4c19-9e53-26ea7fb03d91','BANK.STAFF.CREATE','/v1/users/bank-staff/live/**','POST',NULL,NULL,'2021-04-15 15:26:46.932675'),
--                                                                                                                ('c6870a44-c3ea-49fc-8d19-be61e48c759d','BANK.STAFF.UPDATE','/v1/users/bank-staff/live/**','PUT',NULL,NULL,'2021-04-15 15:26:46.949941'),
--                                                                                                                ('0d10415a-b577-45b7-8865-ca4ab8604426','UNAUTH.BANK.STAFFS.APPROVAL.READ','/v1/bank-staffs/unauth/approval','GET','Bank_Staff_Management','/bank-staff-approval-list','2020-12-23 12:43:11'),
--                                                                                                                ('8e02f0e9-9b5c-4c7b-bdd8-a001a21cc39fag','ALL','/v1/bkb/branches','GET','','','2021-11-17 13:07:12.450671'),
--                                                                                                                ('9dbe748a-349d-4448-ba1c-fd5666789413','UNAUTH.BANK.STAFF.READ','/v1/users/bank-staff/unauth/**','GET',NULL,NULL,'2021-04-11 15:15:01');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('13fb213e-4b30-407d-ae35-0d1eadc49e16','UNAUTH.BANK.STAFF.APPROVER.REMARK','/v1/users/bank-staff/unauth/approver-remark/*','PUT',NULL,NULL,'2021-04-13 12:25:22'),
--                                                                                                                ('67ae7a76-f528-437f-90d6-fd82efc8bf6b','UNAUTH.BANK.STAFF.EDIT','/v1/users/bank-staff/unauth/*','PUT',NULL,NULL,'2021-04-18 15:44:58.807347'),
--                                                                                                                ('88d4a92f-b02c-402a-9095-a27693c5c427','BANK.STAFF.EMPLOYEEID.READ','/v1/users/bank-staff/employee/*','GET',NULL,NULL,'2021-06-15 20:13:16.634549'),
--                                                                                                                ('5dc5eb76-564a-4017-9369-1da569374416','CLIENT.DEVICE.READ.BY.DEVICEID','/v1/banks/35/client-device/registered/**','GET',NULL,NULL,'2021-06-22 13:11:05.701126'),
--                                                                                                                ('af233ebf-6f17-4e86-a95f-1b00edf3ba4f','CLIENT.DEVICE.UPDATE','/v1/banks/35/client-device','PUT',NULL,NULL,'2021-06-22 18:15:10.412921'),
--                                                                                                                ('4db5dfbe-55bf-4872-9f79-930ae99008d3','BKASH.ACCOUNT.READ','/v1/validate/**','GET',NULL,NULL,'2021-07-08 17:23:19.615715'),
--                                                                                                                ('103c6d80-8f76-42c2-9c3a-5de674fe71ee','TRANS.BKASH.PRE.CREATE','/v1/banks/35/accounts/*/*/transaction-request-types/bkash/transaction-requests-pre-process','POST',NULL,NULL,'2021-07-08 19:18:12.44344'),
--                                                                                                                ('749ce1bf-d15a-418d-8de8-20fc8d827cf3','TRANS.BKASH.TRAN.CREATE','/v1/banks/35/accounts/*/*/transaction-request-types/bkash/transaction-requests','POST',NULL,NULL,'2021-07-08 20:57:59.769074'),
--                                                                                                                ('8c2f15d6-b4db-4c65-950f-43b2020af72b','TRANS.HISTORY.READ','/v1/banks/35/transactions/transfer-history','GET',NULL,NULL,'2021-08-12 13:34:59.169711'),
--                                                                                                                ('8e02f0e9-9b5c-4c7b-bdd8-a001a21cc40c','ALL','/v1/bkb/regions','GET','','','2021-11-21 12:30:02.087015');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--                                                                                                                ('8e02f0e9-9b5c-4c7b-bdd8-sdfasd01dfjasdjf673','ALL','/v1/security-questions/{ibUserOid}','GET',NULL,NULL,'2021-11-22 20:45:37.862322'),
--                                                                                                                ('613a1710-3c50-465e-bf7d-d8a785d24be2','USERS.READ','/v1/reconciliation/transaction','GET','Reconcile_transaction','/reconcile-all-list','2020-12-24 10:43:11'),
--                                                                                                                ('9afe371d-65d8-4040-ae88-b6ef4fe3c7c3','UNAUTH.NEW.READ','/v1/reconciliation/transaction/unauth/new','GET','Reconcile_transaction','/reconcile-new-list','2020-12-24 12:43:11'),
--                                                                                                                ('d7f86993-d5e6-4ba6-8db2-78c0a73540d4','UNAUTH.USERS.APPROVAL','/v1/reconciliation/transaction/unauth/approval','GET','Reconcile_transaction','/reconcile-approval-list','2020-12-24 12:43:11'),
--                                                                                                                ('8cd42e2f-d8df-4c2f-9dfe-6edf5e2f116f','BKASH.TRANS.PENDING','/v1/banks/35/*/transactions','GET',NULL,NULL,'2021-09-27 17:17:22.637259'),
--                                                                                                                ('8easdfa-9b5sdfc-4c7sdb-bdsdd8-a00d1a21c42d','ALL','/v1/security-questions/verify','POST',NULL,NULL,'2021-11-22 20:46:39.286982'),
--                                                                                                                ('888119dc-e0a2-4d62-aa2e-59668cea146d','ALL','/v1/banks/35/call-back/bkash/ft-request-process','POST',NULL,NULL,'2021-10-13 16:43:11.944'),
--                                                                                                                ('8e02f0e9-9b5c-4c7b-bdd8-a001a21cc39d','CLIENT.DEVICE.CHANGE.REQUEST','/v1/banks/{bankId}/client-device/change-request','POST',NULL,NULL,'2021-10-19 17:18:13.348718'),
--                                                                                                                ('e220d51e-40d9-45af-b2c6-2a413be3194b','ALL','/v1/banks/**/faq','GET',NULL,NULL,'2021-11-29 19:56:25.322516'),
--                                                                                                                ('a55931e8-e1b6-4060-9690-d62c66f57ddb','TRANS.ROBI.PRE.CREATE','/v1/banks/35/accounts/transaction-request-types/transaction-requests-pre-process','POST',NULL,NULL,'2020-12-24 16:43:11.944');
-- INSERT INTO public."permission" (permissionoid,permissionname,url,"method",topmenuid,leftmenuid,createdon) VALUES
--     ('36ff520b-c6ff-426f-b7d1-496b71b9ac0e','ALL','/v1/banks/35/accounts/transaction-request-types/transaction-requests','POST',NULL,NULL,'2020-12-15 16:43:11.944');
--
--
