CREATE TABLE Accounts (
  account_id INT AUTO_INCREMENT,
  holder_name VARCHAR(50) NOT NULL,
  balance DECIMAL(19,4) NOT NULL,
  PRIMARY KEY (account_id)
);

CREATE TABLE Transactions (
 transaction_id INT AUTO_INCREMENT,
 from_account_id INT NOT NULL,
 to_account_id INT NOT NULL,
 amount DECIMAL(19,4) NOT NULL,
 PRIMARY KEY (transaction_id),
 FOREIGN KEY (from_account_id) REFERENCES Accounts(account_id),
 FOREIGN KEY (to_account_id) REFERENCES Accounts(account_id)
);