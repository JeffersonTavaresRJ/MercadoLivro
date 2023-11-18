CREATE TABLE book (
  Id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Name varchar(255) NOT NULL,
  Price decimal(10,2) NOT NULL,
  Status varchar(255) not null,
  Customer_id int not null,
  FOREIGN KEY (Customer_id) REFERENCES Customer(Id)
)