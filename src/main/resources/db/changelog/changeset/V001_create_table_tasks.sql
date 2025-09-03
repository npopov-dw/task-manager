create sequence tasks_id_seq;

create table tasks (
    id bigint primary key,
    name varchar(255) not null unique,
    description text,
    date_exec date,
    created_at timestamp not null,
    updated_at timestamp not null
);