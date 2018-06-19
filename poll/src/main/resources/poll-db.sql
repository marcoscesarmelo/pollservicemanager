create database polldb;

use polldb;

create table owner (
	id int not null primary key auto_increment,
	username varchar(10) not null,
	password varchar(10) not null, 
	email varchar(50) not null
) ENGINE=INNODB;

create table poll (
	id int not null primary key auto_increment,
	description varchar(200) not null,
	initial_date datetime not null,
	final_date datetime not null,
	status int not null,
	owner_id int not null,
	foreign key (owner_id)
	references owner(id)
	on delete cascade
) ENGINE=INNODB;

create table alternative (
	id int not null primary key auto_increment,
	description varchar(50) not null,
	total int not null,
	poll_id int not null,
	foreign key (poll_id)
	references poll(id)
	on delete cascade
) ENGINE=INNODB;

insert into owner values(default, 'pollservicemanager', 'poll123', 'pollservicemanager@gmail.com');

commit;
