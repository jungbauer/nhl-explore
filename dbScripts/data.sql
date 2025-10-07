-- insert conference data
insert into conference (id, name, abbrev) values ((select nextval('conference_id_seq')), 'Eastern', 'E');
insert into conference (id, name, abbrev) values ((select nextval('conference_id_seq')), 'Western', 'W');

-- insert division data
insert into division (id, name, abbrev) values ((select nextval('division_id_seq')), 'Atlantic', 'A');
insert into division (id, name, abbrev) values ((select nextval('division_id_seq')), 'Central', 'C');
insert into division (id, name, abbrev) values ((select nextval('division_id_seq')), 'Metropolitan', 'M');
insert into division (id, name, abbrev) values ((select nextval('division_id_seq')), 'Pacific', 'P');
