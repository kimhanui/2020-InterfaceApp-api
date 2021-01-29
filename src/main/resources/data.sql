insert into posts(created_date_time, modified_date_time,title, content) values ('2020-08-13T03:17','2020-08-13T03:17:14','집회','대강당으로모이세여');

insert into member(created_date_time, modified_date_time,student_id , name , generation, contact,phone,state , department, first_dues ,second_dues,opening_meeting,final_meeting)
values('2020-08-13T03:17','2020-08-13T03:17',100100,'kim', 30 ,'kim@gmail.com','010-2222-2222','ATTENDING','우유배달학과','Y','Y','Y','Y');
insert into member(created_date_time, modified_date_time,student_id , name , generation, contact,phone, state,department, first_dues ,second_dues,opening_meeting,final_meeting)
values('2020-08-13T03:17','2020-08-13T03:17',200200,'lee', 30, 'kim@gmail.com','010-2222-2222','ATTENDING','우유배달학과','Y','Y','Y','Y' );
insert into member(created_date_time, modified_date_time,student_id , name , generation, contact,phone, state,department, first_dues ,second_dues,opening_meeting,final_meeting)
values('2020-08-13T03:17','2020-08-13T03:17',300300,'park', 30, 'kim@gmail.com','010-2222-2222','ATTENDING','우유배달학과','Y','Y','Y','Y');
insert into member(created_date_time, modified_date_time,student_id , name , generation, contact,phone, state,department, first_dues ,second_dues,opening_meeting,final_meeting)
values('2020-08-13T03:17','2020-08-13T03:17',400400,'yun', 29 , 'kim@gmail.com','010-2222-2222','ATTENDING','우유배달학과','Y','Y','Y','Y');
insert into member(created_date_time, modified_date_time,student_id , name , generation, contact,phone, state,department, first_dues ,second_dues,opening_meeting,final_meeting)
values('2020-08-13T03:17','2020-08-13T03:17',500500,'lim', 29 ,'kim@gmail.com' ,'010-2222-2222','ATTENDING','우유배달학과','Y','Y','Y','Y');
insert into member(created_date_time, modified_date_time,student_id , name , generation, contact,phone, state,department, first_dues ,second_dues,opening_meeting,final_meeting)
values('2020-08-13T03:17','2020-08-13T03:17',600600,'oh', 29, 'kim@gmail.com','010-2222-2222','ATTENDING','우유배달학과','Y','Y','Y','Y');

insert into meeting(passkey ,created_date_time,end_date_time,lat,lon) values('SSD2K','2020-11-11T00:00','2020-11-11T01:00', 33.33,22.22);
insert into meeting(passkey ,created_date_time,end_date_time,lat,lon) values('VJ5FG','2020-09-09T00:00','2020-09-09T01:00', 33.33,22.22);

insert into participant(student_id, name, generation, token) values ('100100', 'kim',30, 'TOKEN1');
insert into participant(student_id, name, generation, token) values ('200200', 'lee',30, 'TOKEN2');
insert into participant(student_id, name, generation, token) values ('300300', 'park',30, 'TOKEN3');
insert into participant(student_id, name, generation, token) values ('400400', 'yun',29, 'TOKEN4');
insert into participant(student_id, name, generation, token) values ('500500', 'lim',29, 'TOKEN5');
insert into participant(student_id, name, generation, token) values ('600600', 'oh',29, 'TOKEN6');

insert into attendance(meeting_id, participant_id, attendance_date_time) values(1,1,'2020-11-11T00:30');
insert into attendance(meeting_id, participant_id, attendance_date_time) values(1,2,'2020-11-11T00:30');
insert into attendance(meeting_id, participant_id, attendance_date_time) values(1,3,'2020-11-11T00:30');
insert into attendance(meeting_id, participant_id, attendance_date_time) values(2,4,'2020-09-09T00:30');
insert into attendance(meeting_id, participant_id, attendance_date_time) values(2,5,'2020-09-09T00:30');
insert into attendance(meeting_id, participant_id, attendance_date_time) values(2,6,'2020-09-09T00:30');

insert into login(role, pw) values('ADMIN','interfaceAdmin');
insert into login(role, pw) values('USER','interfaceUser');