create table profile
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp    not null COMMENT '생성일',
    content_type       varchar(255) not null COMMENT '파일 컨텐츠 타입',
    file_key           varchar(255) not null COMMENT '파일 키',
    original_file_name varchar(255) not null COMMENT '원본 파일 이름',
    primary key (id)
) engine = InnoDB COMMENT = '프로필 이미지';

create table users
(
    id              bigint      not null auto_increment COMMENT 'PK',
    created_date    timestamp   not null COMMENT '생성일',
    updated_date    timestamp   not null COMMENT '수정일',
    birthday        date        not null COMMENT '생일',
    email           varchar(30) not null COMMENT '이메일',
    gender          varchar(10) not null COMMENT '성별',
    has_citizenship bit         not null COMMENT '문화 시민증 보우 여부',
    name            varchar(20) not null COMMENT '이름',
    nickname        varchar(30) not null COMMENT '닉네임',
    social_type     varchar(20) not null COMMENT '소셜 로그인 타입',
    user_role       varchar(20) not null COMMENT '사용자 권한',
    profile_id      bigint      not null COMMENT '프로필 이미지 아이디',
    primary key (id)
) engine = InnoDB COMMENT = '사용자';

alter table users
    add constraint fk_user_profile_id foreign key (profile_id) references profile (id);

create table cultural_event
(
    id                       bigint       not null auto_increment COMMENT 'PK',
    created_date             timestamp    not null COMMENT '생성일',
    updated_date             timestamp    not null COMMENT '수정일',
    cultural_event_id        varchar(100) not null COMMENT '서비스 ID',
    class_name               varchar(30)  not null COMMENT '분류명',
    region                   varchar(100) not null COMMENT '지역',
    state                    varchar(30)  not null COMMENT '서비스 상태',
    content                  varchar(255) not null COMMENT '내용',
    payment_method           varchar(50)  not null COMMENT '결제 방법',
    place_name               varchar(100) not null COMMENT '장소명',
    target                   varchar(255) not null COMMENT '서비스 대상',
    service_url              varchar(100) not null COMMENT '바로가기 URL',
    latitude                 varchar(20)  not null COMMENT '위도',
    longitude                varchar(20)  not null COMMENT '경도',
    service_start_date_time  timestamp    not null COMMENT '서비스 개시 시작 일시',
    service_end_date_time    timestamp    not null COMMENT '서비스 개시 종료 일시',
    register_start_date_time timestamp    not null COMMENT '접수 시작 일시',
    register_end_date_time   timestamp    not null COMMENT '접수 종료 일시',
    primary key (id)
) engine = InnoDB COMMENT = '문화행사';

alter table cultural_event
    add constraint uk_cultural_event_id_on_cultural_event unique (cultural_event_id);

CREATE INDEX idx_service_start_date_time_on_cultural_event ON cultural_event (service_start_date_time);

CREATE INDEX idx_service_register_start_date_time_on_cultural_event ON cultural_event (register_start_date_time);
