/**
 * CREATE Script for init of DB
 */

-- Create 4 previous insurance modules

insert into insurance_module (id, deleted, minimum_coverage, maximum_coverage, risk, name) values (1l, false, 0,
3000, 0.30, 'Bike');

insert into insurance_module (id, deleted, minimum_coverage, maximum_coverage, risk, name) values (2l, false, 500,
10000, 0.05, 'Jewelry');

insert into insurance_module (id, deleted, minimum_coverage, maximum_coverage, risk, name) values (3l, false, 500,
6000, 0.35, 'Electronics');

insert into insurance_module (id, deleted, minimum_coverage, maximum_coverage, risk, name) values (4l, false, 0,
20000, 0.30, 'Sports Equipament'); 