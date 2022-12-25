-- Data created with ChatGPT

-- Insert 1 Listing for Dealer 1
INSERT INTO Listing (dealerId, vehicle, price, createdAt, state)
VALUES ((SELECT id FROM Dealer WHERE name = 'Acme Motors'), '2019 Toyota Camry', 27000, '2022-01-01', 'published');

-- Insert 2 Listings for Dealer 2
INSERT INTO Listing (dealerId, vehicle, price, createdAt, state)
VALUES ((SELECT id FROM Dealer WHERE name = 'Ferrari Dealership'), '2021 Ferrari 488 Pista', 350000, '2022-02-01', 'published'),
((SELECT id FROM Dealer WHERE name = 'Ferrari Dealership'), '2020 Ferrari 812 Superfast', 325000, '2022-03-01', 'draft');

-- Insert 3 Listings for Dealer 3
INSERT INTO Listing (dealerId, vehicle, price, createdAt, state)
VALUES ((SELECT id FROM Dealer WHERE name = 'Lamborghini Showroom'), '2020 Lamborghini Huracan Evo', 300000, '2022-04-01', 'published'),
((SELECT id FROM Dealer WHERE name = 'Lamborghini Showroom'), '2022 Lamborghini Aventador SVJ', 500000, '2022-05-01', 'published'),
((SELECT id FROM Dealer WHERE name = 'Lamborghini Showroom'), '2019 Lamborghini Urus', 200000, '2022-06-01', 'draft');

-- Insert 4 Listings for Dealer 4
INSERT INTO Listing (dealerId, vehicle, price, createdAt, state)
VALUES ((SELECT id FROM Dealer WHERE name = 'Porsche Centre'), '2021 Porsche 911 GT3', 175000, '2022-07-01', 'published'),
((SELECT id FROM Dealer WHERE name = 'Porsche Centre'), '2020 Porsche Panamera', 115000, '2022-08-01', 'published'),
((SELECT id FROM Dealer WHERE name = 'Porsche Centre'), '2019 Porsche Macan', 60000, '2022-09-01', 'draft');
