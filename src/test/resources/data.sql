set foreign_key_checks =0;
insert into posts(createdDateTime, modifiedDateTime,title, content) values ('2020-08-13T03:17:14','2020-08-13T03:17:14','집회','대강당으로모이세여');

insert into member(createdDateTime, modifiedDateTime,studentId , name , groupNum, contact) values('2020-08-13T03:17','2020-08-13T03:17',100100,'kim', 30,'kim@gmail.com' );
insert into member(createdDateTime, modifiedDateTime,studentId , name , groupNum, contact) values('2020-08-13T03:17:14','2020-08-13T03:17:14',200200,'lee', 30, '010-2222-3333');
insert into member(createdDateTime, modifiedDateTime,studentId , name , groupNum, contact) values('2020-08-13T03:17:14','2020-08-13T03:17:14',300300,'park', 30, 'pp@kakao.com');
insert into member(createdDateTime, modifiedDateTime,studentId , name , groupNum, contact) values('2020-08-13T03:17:14','2020-08-13T03:17:14',400400,'yun', 30, '010-3333-4444');
insert into member(createdDateTime, modifiedDateTime,studentId , name , groupNum, contact) values('2020-08-13T03:17:14','2020-08-13T03:17:14',500500,'lim', 30,'010-4444-5555');
insert into member(createdDateTime, modifiedDateTime,studentId , name , groupNum, contact) values('2020-08-13T03:17:14','2020-08-13T03:17:14',600600,'oh', 30, '010-5555-6666');

insert into meeting(passkey ,createdDateTime,endDateTime) values('SSD2K','2020-11-11T00:00:00','2020-11-11T01:00:00');
insert into meeting(passkey ,createdDateTime,endDateTime) values('VJ5FG','2020-09-09T00:00:00','2020-09-09T01:00:00');

insert into meeting_member(MEETING_ID, MEMBER_ID) values(1,1);
insert into meeting_member(MEETING_ID, MEMBER_ID) values(1,2);
insert into meeting_member(MEETING_ID, MEMBER_ID) values(1,3);
insert into meeting_member(MEETING_ID, MEMBER_ID) values(2,4);
insert into meeting_member(MEETING_ID, MEMBER_ID) values(2,5);
insert into meeting_member(MEETING_ID, MEMBER_ID) values(2,6);