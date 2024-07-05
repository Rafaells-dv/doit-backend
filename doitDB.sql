create schema doit;

use doit;

create table task (
	id int unique auto_increment primary key,
    title varchar(100)
);

create table item (
	id int unique auto_increment primary key,
    description varchar(240),
    task_id int,
    foreign key (task_id) references task(id) on delete cascade
);