alter table suggestion
    add column file_id bigint not null COMMENT '제안하기 썸네일';

alter table suggestion
    add constraint fk_suggestion_file_id foreign key (file_id) references file (id);

