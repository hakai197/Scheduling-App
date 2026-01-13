DROP TABLE IF EXISTS employee CASCADE; 
DROP TABLE IF EXISTS posts CASCADE; 
DROP TABLE IF EXISTS weeklyPayout CASCADE; 


CREATE TABLE employee (
    employee_id SERIAL,
    employee_name VARCHAR (50)NOT NULL, 
    shift VARCHAR (2) NOT NULL, 
    overtime_eligible BOOLEAN,
    PRIMARY KEY (employee_id) 
); 

CREATE TABLE post (
    post_id SERIAL, 
    post_location VARCHAR (20)NOT NULL, 
    shift_fill VARCHAR (2) NOT NULL,
    PRIMARY KEY (post_id)
); 

CREATE TABLE weeklyPayout (
    hourly_wage
)