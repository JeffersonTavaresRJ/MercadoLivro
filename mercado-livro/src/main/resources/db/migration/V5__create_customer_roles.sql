CREATE TABLE customer_roles (
  Customer_id int NOT NULL,
  Role varchar(50) NOT NULL,
  FOREIGN KEY (customer_id) references customer(id)
);