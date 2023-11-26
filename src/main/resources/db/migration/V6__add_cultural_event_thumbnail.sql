alter table cultural_event
    add column file_id bigint null COMMENT '썸네일 파일 아이디 (FK)';

alter table cultural_event
    add constraint fk_cultural_event_file_id foreign key (file_id) references file (id);
