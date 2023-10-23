
create table address
(
    city varchar(20),
    state varchar(20),
    street varchar(200),
    zip_code varchar(200)
);

create table customer
(
    email varchar(200),
    first_name varchar(20),
    last_name varchar(20),
    phone_number varchar(200),
    address_id varchar(200)
);


INSERT INTO `address` (`city`, `state`, `street`, `zip_code`)
VALUES ('Fairfield', 'Iowa', '1000 N 4th St', '52557');


INSERT INTO `customer` (`email`, `first_name`, `last_name`, `phone_number`,`address_id`)
VALUES ('mahisharew62@gmail.com', 'Mahlet', 'Borena', '1234567891','1')
 
