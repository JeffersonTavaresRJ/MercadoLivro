CREATE TABLE purchase (
  Id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Customer_id int NOT NULL,
  Nfe varchar(255),
  Price decimal(15,2) NOT NULL,
  created_at datetime not null,
  FOREIGN KEY(Customer_id) REFERENCES customer(id)
);

CREATE TABLE purchase_book (
  Purchase_id int NOT NULL,
  Book_id int NOT NULL,
  FOREIGN KEY(Purchase_id) REFERENCES purchase(id),
  FOREIGN KEY(Book_id) REFERENCES book(id),
  PRIMARY KEY(Purchase_id, Book_id)
);