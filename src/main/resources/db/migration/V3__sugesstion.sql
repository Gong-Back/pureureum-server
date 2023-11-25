create table suggestion
(
    id             bigint       not null auto_increment COMMENT 'PK',
    created_date   timestamp    not null COMMENT '생성일',
    updated_date   timestamp    not null COMMENT '수정일',
    title          varchar(255) not null COMMENT '제목',
    content        varchar(255) not null COMMENT '내용',
    start_date     timestamp    not null COMMENT '시작일',
    end_date       timestamp    not null COMMENT '종료일',
    totalVoteCount int          not null default 0 COMMENT '총 투표 수',
    user_id        bigint       not null COMMENT '사용자 아이디',
    primary key (id)
) engine = InnoDB COMMENT = '제안하기 게시판';

alter table suggestion
    add constraint fk_suggestion_user_id foreign key (user_id) references users (id);

create table suggestion_vote
(
    id            bigint       not null auto_increment COMMENT 'PK',
    created_date  timestamp    not null COMMENT '생성일',
    content       varchar(255) not null COMMENT '내용',
    vote_count    int          not null default 0 COMMENT '투표 수',
    suggestion_id bigint       not null COMMENT '제안하기 게시판 아이디',
    primary key (id)
) engine = InnoDB COMMENT = '제안하기 게시판 투표 항목';

alter table suggestion_vote
    add constraint fk_suggestion_vote_suggestion_id foreign key (suggestion_id) references suggestion (id);

create table suggestion_vote_record
(
    id            bigint    not null auto_increment COMMENT 'PK',
    created_date  timestamp not null COMMENT '생성일',
    suggestion_id bigint    not null COMMENT '제안하기 게시판 아이디',
    user_id       bigint    not null COMMENT '사용자 아이디',
    primary key (id)
) engine = InnoDB COMMENT = '제안하기 게시판 투표 기록';

alter table suggestion_vote_record
    add constraint fk_suggestion_vote_record_suggestion_id foreign key (suggestion_id) references suggestion (id);
