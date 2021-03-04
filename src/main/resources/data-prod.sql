-- drop table if exists attendance
--
-- drop table if exists calendar
--
-- drop table if exists login
--
-- drop table if exists meeting
--
-- drop table if exists member
--
-- drop table if exists participant
--
-- drop table if exists posts

create table attendance (
                            id bigint not null auto_increment,
                            attendance_date_time datetime,
                            meeting_id bigint,
                            participant_id bigint,
                            primary key (id)
) engine=InnoDB

create table calendar (
                          id bigint not null auto_increment,
                          created_date_time datetime,
                          modified_date_time datetime,
                          content varchar(255),
                          date date not null,
                          title varchar(255),
                          primary key (id)
) engine=InnoDB

create table login (
                       id bigint not null auto_increment,
                       pw varchar(255),
                       role varchar(255),
                       primary key (id)
) engine=InnoDB

create table meeting (
                         id bigint not null auto_increment,
                         created_date_time datetime,
                         end_date_time datetime,
                         lat double precision,
                         lon double precision,
                         passkey varchar(255),
                         primary key (id)
) engine=InnoDB

create table member (
                        id bigint not null auto_increment,
                        created_date_time datetime,
                        modified_date_time datetime,
                        contact varchar(255),
                        department varchar(255),
                        generation bigint not null,
                        final_meeting varchar(255) not null,
                        first_dues varchar(255) not null,
                        opening_meeting varchar(255) not null,
                        second_dues varchar(255) not null,
                        name varchar(20) not null,
                        phone varchar(255),
                        state varchar(255),
                        student_id bigint not null,
                        primary key (id)
) engine=InnoDB

create table participant (
                             id bigint not null auto_increment,
                             created_date_time datetime,
                             generation bigint,
                             name varchar(255),
                             student_id bigint,
                             token varchar(255),
                             primary key (id)
) engine=InnoDB

create table posts (
                       id bigint not null auto_increment,
                       created_date_time datetime,
                       modified_date_time datetime,
                       content TEXT not null,
                       title varchar(500) not null,
                       primary key (id)
) engine=InnoDB

alter table member
    add constraint UK_adou32rjalvngxrw20hj2nsla unique (student_id)

alter table participant
    add constraint UK_anevoi9xt8w7liikacqnucvjg unique (student_id)

alter table participant
    add constraint UK_3fmxvhk2wf9aoxc6la5l7ylja unique (token)

alter table attendance
    add constraint FK89vocls01emx9t1qgvre1cnve
        foreign key (meeting_id)
            references meeting (id)

alter table attendance
    add constraint FK8e0qxhwb9cqx14glphxbghwmc
        foreign key (participant_id)
            references participant (id)