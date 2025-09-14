insert into tasks (
    id,         name,        description,
    date_exec,  created_at,  updated_at
)
values (3, 'Task 1', 'Description task 1', '2025-09-05'::date, now(), now()),
    (5, 'task 5', 'description 5', '2025-09-14'::date, now(), now());