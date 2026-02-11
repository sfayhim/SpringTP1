-- Données de base pour le projet Pharmacie
-- Dispensaire (Etablissements de santé qui passent commande de médicaments)
-- Le fichier est chargé au démarrage de l''application

-- Insertion des catégories de médicaments
INSERT INTO CATEGORIE (CODE, LIBELLE, DESCRIPTION) VALUES
(DEFAULT, 'Antalgiques et Antipyrétiques', 'Médicaments contre la douleur et la fièvre'), -- code : 1
(DEFAULT, 'Anti-inflammatoires', 'Médicaments réduisant l''inflammation'), -- code : 2
(DEFAULT, 'Antibiotiques', 'Médicaments pour traiter les infections bactériennes'),
(DEFAULT, 'Antihypertenseurs', 'Médicaments pour traiter l''hypertension artérielle'),
(DEFAULT, 'Antidiabétiques', 'Médicaments pour traiter le diabète'),
(DEFAULT, 'Antihistaminiques', 'Médicaments pour traiter les allergies'),
(DEFAULT, 'Vitamines et Compléments', 'Suppléments nutritionnels'),
(DEFAULT, 'Médicaments Cardiovasculaires', 'Médicaments pour le cœur et la circulation'),
(DEFAULT, 'Médicaments Gastro-intestinaux', 'Médicaments pour les troubles digestifs'),
(DEFAULT, 'Médicaments Respiratoires', 'Médicaments pour les troubles respiratoires');


-- Catégorie 1: Antalgiques et Antipyrétiques
INSERT INTO MEDICAMENT (NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE, UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO, INDISPONIBLE, imageURL) VALUES
('Morphine 10mg', 1, 'Boîte de 14 comprimés', 25.80, 80, 0, 15, false, 'https://images.unsplash.com/photo-1550572017-edd951aa8f72?w=400'),
('Doliprane Effervescent 1g', 1, 'Boîte de 8 comprimés', 3.50, 280, 0, 30, false, 'https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=400'),
('Efferalgan Vitamine C', 1, 'Boîte de 16 comprimés', 4.20, 220, 0, 25, false, 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?w=400');

-- Catégorie 2: Anti-inflammatoires
INSERT INTO MEDICAMENT (NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE, UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO, INDISPONIBLE, imageURL) VALUES
('Étodolac 400mg', 2, 'Boîte de 14 comprimés', 12.50, 110, 0, 15, false, 'https://images.unsplash.com/photo-1471864190281-a93a3070b6de?w=400'),
('Flurbiprofène 100mg', 2, 'Boîte de 30 comprimés', 10.80, 130, 0, 16, false, 'https://images.unsplash.com/photo-1550572017-edd951aa8f72?w=400');

-- Catégorie 3: Antibiotiques (2 médicaments indisponbibles)
INSERT INTO MEDICAMENT (NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE, UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO, INDISPONIBLE, imageURL) VALUES
('Lévofloxacine 500mg', 3, 'Boîte de 7 comprimés', 15.80, 160, 0, 18, true, 'https://images.unsplash.com/photo-1628771065518-0d82f1938462?w=400'),
('Clindamycine 300mg', 3, 'Boîte de 16 gélules', 13.20, 140, 0, 16, true, 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=400');


-- Insertion des dispensaires (Etablissements de santé)
INSERT INTO DISPENSAIRE (CODE, NOM, CONTACT, FONCTION, ADRESSE, CODE_POSTAL, VILLE, REGION, PAYS, TELEPHONE, FAX) VALUES
(DEFAULT, 'Centre de Santé Nord', 'Dr. Martin Dubois', 'Directeur', '12 Avenue des Hôpitaux', '75018', 'Paris', 'Île-de-France', 'France', '01.45.67.89.01', '01.45.67.89.02'),
(DEFAULT, 'Dispensaire Sud', 'Dr. Sophie Laurent', 'Responsable', '45 Rue de la Santé', '13001', 'Marseille', 'PACA', 'France', '04.91.23.45.67', '04.91.23.45.68'),
(DEFAULT, 'Clinique de l''Est', 'Dr. Pierre Bernard', 'Chef de service', '8 Boulevard Médical', '67000', 'Strasbourg', 'Grand Est', 'France', '03.88.12.34.56', '03.88.12.34.57'),
(DEFAULT, 'Centre Hospitalier Ouest', 'Dr. Marie Petit', 'Directrice', '23 Rue des Soins', '44000', 'Nantes', 'Pays de la Loire', 'France', '02.40.11.22.33', '02.40.11.22.34'),
(DEFAULT, 'Dispensaire Central', 'Dr. Jean Moreau', 'Pharmacien chef', '67 Avenue Centrale', '69001', 'Lyon', 'Auvergne-Rhône-Alpes', 'France', '04.78.90.12.34', '04.78.90.12.35');


-- Insertion des commandes
INSERT INTO COMMANDE (NUMERO, SAISIELE, ENVOYELE, PORT, REMISE, DESTINATAIRE, ADRESSE, CODE_POSTAL, VILLE, REGION, PAYS, DISPENSAIRE_CODE) VALUES
(DEFAULT, PARSEDATETIME('2026-01-15', 'yyyy-MM-dd'), PARSEDATETIME('2026-01-20', 'yyyy-MM-dd'), 15.50, 5.00, 'Centre de Santé Nord', '12 Avenue des Hôpitaux', '75018', 'Paris', 'Île-de-France', 'France', 1),
(DEFAULT, PARSEDATETIME('2026-01-25', 'yyyy-MM-dd'), NULL, 18.00, 10.00, 'Dispensaire Sud', '45 Rue de la Santé', '13001', 'Marseille', 'PACA', 'France', 2),
(DEFAULT, PARSEDATETIME('2026-02-01', 'yyyy-MM-dd'), PARSEDATETIME('2026-02-05', 'yyyy-MM-dd'), 12.00, 0.00, 'Clinique de l''Est', '8 Boulevard Médical', '67000', 'Strasbourg', 'Grand Est', 'France', 3),
(DEFAULT, PARSEDATETIME('2026-02-08', 'yyyy-MM-dd'), NULL, 20.00, 15.00, 'Centre Hospitalier Ouest', '23 Rue des Soins', '44000', 'Nantes', 'Pays de la Loire', 'France', 4),
(DEFAULT, PARSEDATETIME('2026-02-10', 'yyyy-MM-dd'), NULL, 16.50, 8.00, 'Dispensaire Central', '67 Avenue Centrale', '69001', 'Lyon', 'Auvergne-Rhône-Alpes', 'France', 5);


-- Insertion des lignes de commande (détails des commandes)
INSERT INTO LIGNE (ID, QUANTITE, COMMANDE_NUMERO, MEDICAMENT_REFERENCE) VALUES
(DEFAULT, 50, 1, 1),  -- 50 Morphine pour Commande 1
(DEFAULT, 100, 1, 2), -- 100 Doliprane pour Commande 1
(DEFAULT, 75, 1, 4),  -- 75 Étodolac pour Commande 1
(DEFAULT, 80, 2, 2),  -- 80 Doliprane pour Commande 2
(DEFAULT, 60, 2, 3),  -- 60 Efferalgan pour Commande 2
(DEFAULT, 40, 3, 5),  -- 40 Flurbiprofène pour Commande 3
(DEFAULT, 90, 3, 2),  -- 90 Doliprane pour Commande 3
(DEFAULT, 30, 4, 1),  -- 30 Morphine pour Commande 4
(DEFAULT, 120, 4, 2), -- 120 Doliprane pour Commande 4
(DEFAULT, 45, 5, 4);  -- 45 Étodolac pour Commande 5
