create table profile
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp         not null COMMENT '생성일',
    content_type       varchar(255) not null COMMENT '파일 컨텐츠 타입',
    file_key           varchar(255) not null COMMENT '파일 키',
    original_file_name varchar(255) not null COMMENT '원본 파일 이름',
    primary key (id)
) COMMENT = '프로필 이미지';

create table `user`
(
    id              bigint      not null auto_increment COMMENT 'PK',
    created_date    timestamp        not null COMMENT '생성일',
    updated_date    timestamp        not null COMMENT '수정일',
    birthday        date        not null COMMENT '생일',
    email           varchar(30) not null COMMENT '이메일',
    gender          varchar(20) not null COMMENT '성별',
    has_citizenship bit         not null COMMENT '문화 시민증 보우 여부',
    name            varchar(20) not null COMMENT '이름',
    nickname        varchar(30) not null COMMENT '닉네임',
    social_type     varchar(20) not null COMMENT '소셜 로그인 타입',
    user_role       varchar(20) not null COMMENT '사용자 권한',
    profile_id      bigint      not null COMMENT '프로필 이미지 아이디',
    primary key (id)
) COMMENT = '사용자';

alter table `user`
    add constraint fk_user_profile_id foreign key (profile_id) references profile (id);

create table community
(
    id           bigint      not null auto_increment COMMENT 'PK',
    created_date timestamp        not null COMMENT '생성일',
    updated_date timestamp        not null COMMENT '수정일',
    content      TEXT        not null COMMENT '내용',
    title        varchar(50) not null COMMENT '제목',
    user_id      bigint      not null COMMENT '사용자 아이디',
    primary key (id)
) COMMENT = '커뮤니티';

alter table community
    add constraint fk_community_user_id foreign key (user_id) references `user` (id);

create table community_comment
(
    id                bigint       not null auto_increment COMMENT 'PK',
    created_date      timestamp         not null COMMENT '생성일',
    updated_date      timestamp         not null COMMENT '수정일',
    content           varchar(200) not null COMMENT '내용',
    parent_comment_id bigint       not null COMMENT '부모 댓글 아이디',
    user_id           bigint       not null COMMENT '사용자 아이디',
    community_id      bigint       not null COMMENT '커뮤니티 아이디',
    primary key (id)
) COMMENT = '커뮤니티 댓글';

alter table community_comment
    add constraint fk_community_comment_community_id foreign key (community_id) references community (id);

create table community_file
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp         not null COMMENT '생성일',
    content_type       varchar(255) not null COMMENT '파일 컨텐츠 타입',
    file_key           varchar(255) not null COMMENT '파일 키',
    file_type          varchar(20)  not null COMMENT '파일 타입',
    original_file_name varchar(255) not null COMMENT '원본 파일 이름',
    community_id       bigint       not null COMMENT '커뮤니티 아이디',
    primary key (id)
) COMMENT = '커뮤니티 파일';

alter table community_file
    add constraint fk_community_file_community_id foreign key (community_id) references community (id);

create table community_tag
(
    id           bigint       not null auto_increment COMMENT 'PK',
    created_date timestamp         not null COMMENT '생성일',
    name         varchar(255) not null COMMENT '태그 이름',
    community_id bigint       not null COMMENT '커뮤니티 아이디',
    primary key (id)
) COMMENT = '커뮤니티 태그';

alter table community_tag
    add constraint fk_community_tag_community_id foreign key (community_id) references community (id);

create table culture_content
(
    id              bigint       not null auto_increment COMMENT 'PK',
    created_date    timestamp         not null COMMENT '생성일',
    updated_date    timestamp         not null COMMENT '수정일',
    city            varchar(20)  not null COMMENT '시',
    county          varchar(20)  not null COMMENT '군',
    detail          varchar(100) not null COMMENT '상세 주소',
    district        varchar(20)  not null COMMENT '읍',
    jibun           varchar(100) not null COMMENT '지번',
    latitude        varchar(20)  not null COMMENT '위도',
    longitude       varchar(20)  not null COMMENT '경도',
    comment_enabled bit          not null COMMENT '의견 나누기 사용 여부',
    content         TEXT         not null COMMENT '내용',
    end_date        date         not null COMMENT '종료일',
    introduction    varchar(255) not null COMMENT '소개',
    start_date      date         not null COMMENT '시작일',
    title           varchar(255) not null COMMENT '제목',
    like_count      integer      not null COMMENT '좋아요 수',
    status          varchar(20)  not null COMMENT '상태',
    user_id         bigint       not null COMMENT '사용자 아이디',
    primary key (id)
) COMMENT = '문화 콘텐츠';

alter table culture_content
    add constraint fk_culture_content_user_id foreign key (user_id) references `user` (id);

create table culture_content_comment
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp         not null COMMENT '생성일',
    updated_date       timestamp         not null COMMENT '수정일',
    content            varchar(200) not null COMMENT '내용',
    hate_count         integer      not null COMMENT '싫어요 수',
    like_count         integer      not null COMMENT '좋아요 수',
    parent_id          bigint       not null COMMENT '부모 댓글 아이디',
    user_id            bigint       not null COMMENT '사용자 아이디',
    culture_content_id bigint       not null COMMENT '문화 콘텐츠 아이디',
    primary key (id)
) COMMENT = '문화 콘텐츠 댓글';

alter table culture_content_comment
    add constraint fk_culture_content_comment_culture_content_id foreign key (culture_content_id) references culture_content (id);

alter table culture_content_comment
    add constraint fk_culture_content_comment_user_id foreign key (user_id) references `user` (id);

create table culture_content_file
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp         not null COMMENT '생성일',
    content_type       varchar(255) not null COMMENT '파일 컨텐츠 타입',
    file_key           varchar(255) not null COMMENT '파일 키',
    file_type          varchar(20)  not null COMMENT '파일 타입',
    original_file_name varchar(255) not null COMMENT '원본 파일 이름',
    culture_content_id bigint       not null COMMENT '문화 콘텐츠 아이디',
    primary key (id)
) COMMENT = '문화 콘텐츠 파일';

alter table culture_content_file
    add constraint fk_culture_content_file_culture_content_id foreign key (culture_content_id) references culture_content (id);

create table dashboard
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp         not null COMMENT '생성일',
    updated_date       timestamp         not null COMMENT '수정일',
    culture_content_id bigint       not null COMMENT '문화 콘텐츠 아이디',
    introduction       varchar(255) not null COMMENT '소개글',
    primary key (id)
) COMMENT = '대시보드';

create table dashboard_user
(
    id           bigint      not null auto_increment COMMENT 'PK',
    created_date timestamp        not null COMMENT '생성일',
    role         varchar(20) not null COMMENT '권한',
    user_id      bigint      not null COMMENT '사용자 아이디',
    dashboard_id bigint      not null COMMENT '대시보드 아이디',
    primary key (id)
) COMMENT = '대시보드 사용자';

alter table dashboard_user
    add constraint fk_dashboard_user_dashboard_id foreign key (dashboard_id) references dashboard (id);

alter table dashboard_user
    add constraint fk_dashboard_user_user_id foreign key (user_id) references `user` (id);

create table dashboard_board
(
    id                bigint      not null auto_increment COMMENT 'PK',
    created_date      timestamp        not null COMMENT '생성일',
    updated_date      timestamp        not null COMMENT '수정일',
    content           TEXT        not null COMMENT '내용',
    is_notice         bit         not null COMMENT '공지 여부',
    title             varchar(50) not null COMMENT '제목',
    dashboard_user_id bigint      not null COMMENT '대시보드 사용자 아이디',
    dashboard_id      bigint      not null COMMENT '대시보드 아이디',
    primary key (id)
) COMMENT = '대시보드 게시판';

alter table dashboard_board
    add constraint fk_dashboard_board_dashboard_user_id foreign key (dashboard_user_id) references dashboard_user (id);

alter table dashboard_board
    add constraint fk_dashboard_board_dashboard_id foreign key (dashboard_id) references dashboard (id);

create table dashboard_board_comment
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp         not null COMMENT '생성일',
    updated_date       timestamp         not null COMMENT '수정일',
    content            varchar(200) not null COMMENT '내용',
    parent_id          bigint       not null COMMENT '부모 댓글 아이디',
    dashboard_user_id  bigint       not null COMMENT '대시보드 사용자 아이디',
    dashboard_board_id bigint       not null COMMENT '대시보드 게시판 아이디',
    primary key (id)
) COMMENT = '대시보드 게시판 댓글';

alter table dashboard_board_comment
    add constraint fk_dashboard_board_comment_dashboard_user_id foreign key (dashboard_user_id) references dashboard_user (id);

alter table dashboard_board_comment
    add constraint fk_dashboard_board_comment_dashboard_board_id foreign key (dashboard_board_id) references dashboard_board (id);

create table dashboard_board_file
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp         not null COMMENT '생성일',
    content_type       varchar(255) not null COMMENT '파일 컨텐츠 타입',
    file_key           varchar(255) not null COMMENT '파일 키',
    file_type          varchar(20)  not null COMMENT '파일 타입',
    original_file_name varchar(255) not null COMMENT '원본 파일 이름',
    dashboard_board_id bigint       not null COMMENT '대시보드 게시판 아이디',
    primary key (id)
) COMMENT = '대시보드 게시판 파일';

alter table dashboard_board_file
    add constraint fk_dashboard_board_file_dashboard_board_id foreign key (dashboard_board_id) references dashboard_board (id);

create table dashboard_calendar
(
    id           bigint       not null auto_increment COMMENT 'PK',
    created_date timestamp         not null COMMENT '생성일',
    updated_date timestamp         not null COMMENT '수정일',
    content      varchar(255) not null COMMENT '내용',
    date         datetime(6)  not null COMMENT '날짜',
    dashboard_id bigint       not null COMMENT '대시보드 아이디',
    primary key (id)
) comment = '대시보드 캘린더';

alter table dashboard_calendar
    add constraint fk_dashboard_calendar_dashboard_id foreign key (dashboard_id) references dashboard (id);

create table dashboard_gallery
(
    id                 bigint       not null auto_increment COMMENT 'PK',
    created_date       timestamp         not null COMMENT '생성일',
    updated_date       timestamp         not null COMMENT '수정일',
    content_type       varchar(255) not null COMMENT '파일 컨텐츠 타입',
    file_key           varchar(255) not null COMMENT '파일 키',
    original_file_name varchar(255) not null COMMENT '원본 파일 이름',
    dashboard_user_id  bigint       not null COMMENT '대시보드 사용자 아이디',
    dashboard_id       bigint       not null COMMENT '대시보드 아이디',
    primary key (id)
) COMMENT = '대시보드 갤러리';

alter table dashboard_gallery
    add constraint fk_dashboard_board_gallery_dashboard_user_id foreign key (dashboard_user_id) references dashboard_user (id);

alter table dashboard_gallery
    add constraint fk_dashboard_gallery_dashboard_id foreign key (dashboard_id) references dashboard (id);

create table temp_social_auth
(
    id           bigint       not null auto_increment COMMENT 'PK',
    created_date timestamp         not null COMMENT '생성일',
    birthday     varchar(255) COMMENT '생일',
    email        varchar(255) not null COMMENT '이메일',
    gender       varchar(255) COMMENT '성별',
    name         varchar(255) COMMENT '이름',
    social_type  varchar(255) COMMENT '소셜 로그인 타입',
    primary key (id)
) COMMENT = '임시 소셜 로그인 정보';
