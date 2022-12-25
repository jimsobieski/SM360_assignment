-- Dealer table
CREATE TABLE Dealer (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL
);
CREATE INDEX idx_dealer_id ON Dealer (id);

-- Listing table
CREATE TABLE Listing (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  dealerId UUID NOT NULL,
  vehicle VARCHAR(255) NOT NULL,
  price NUMERIC NOT NULL,
  createdAt DATE NOT NULL,
  state VARCHAR(255) NOT NULL,
  CONSTRAINT fk_dealer FOREIGN KEY (dealerId) REFERENCES Dealer(id)
);
CREATE INDEX idx_listing_id ON Listing (id);
CREATE INDEX idx_listing_dealerId ON Listing (dealerId);


