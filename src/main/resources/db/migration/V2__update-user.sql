create table file
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp    not null COMMENT '생성일',
    content_type       varchar(255) not null COMMENT '파일 컨텐츠 타입',
    file_key           varchar(255) not null COMMENT '파일 키',
    original_file_name varchar(255) not null COMMENT '원본 파일 이름',
    primary key (id)
) engine = InnoDB COMMENT = '파일' CHAR SET utf8mb4;

alter table users
    add column password varchar(255) not null COMMENT '비밀번호';

alter table users drop column social_type;

alter table profile drop column created_date;

alter table profile drop column content_type;

alter table profile drop column file_key;

alter table profile drop column original_file_name;

alter table profile
    add column file_id bigint not null COMMENT '파일 아이디';

alter table profile
    add constraint fk_profile_file_id foreign key (file_id) references file (id);

create table user_badge
(
    id      bigint not null auto_increment COMMENT 'PK',
    file_id bigint not null COMMENT '파일 아이디 (FK)',
    user_id bigint not null COMMENT '사용자 아이디 (FK)',
    primary key (id)
) engine = InnoDB COMMENT = '사용자 뱃지' CHAR SET utf8mb4;

alter table user_badge
    add constraint fk_user_badge_file_id foreign key (file_id) references file (id);

alter table user_badge
    add constraint fk_user_badge_user_id foreign key (user_id) references users (id);
