alter table suggestion_vote_record
    add column suggestion_vote_id bigint not null COMMENT '제안하기 게시판 투표 항목 아이디 (FK)';

alter table suggestion_vote_record
    add constraint fk_suggestion_vote_record_suggestion_vote_id foreign key (suggestion_vote_id) references suggestion_vote (id);

CREATE INDEX idx_suggestion_id_and_user_id_on_suggestion_vote_record ON suggestion_vote_record (suggestion_id, user_id);
