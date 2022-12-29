CREATE TABLE Profile (
  code VARCHAR(15) PRIMARY KEY,
  maxPublications INT NOT NULL
);
CREATE INDEX idx_profile_code ON Profile(code);

INSERT INTO Profile (code, maxPublications) VALUES 
('NORMAL', 5),
('PREMIUM', 15), 
('ULTRA', 50);

ALTER TABLE Dealer
ADD COLUMN profileCode VARCHAR(15),
ADD FOREIGN KEY (profileCode) REFERENCES Profile(code);

CREATE INDEX idx_dealer_profile_code ON Dealer(profileCode);

UPDATE Dealer
SET profileCode = 'NORMAL';

ALTER TABLE Dealer
ALTER COLUMN profileCode SET NOT NULL;



