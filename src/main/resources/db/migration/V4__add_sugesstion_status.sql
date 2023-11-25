alter table suggestion
    add column status varchar(30) not null;

CREATE INDEX idx_end_date_on_cultural_event ON suggestion (end_date);
