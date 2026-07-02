DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS recipe;
DROP TABLE IF EXISTS category;

CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE recipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    difficulty VARCHAR(50),
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reviewer_name VARCHAR(255) NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    recipe_id INT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipe(id) ON DELETE CASCADE
);

-- Insertion d'un jeu de données de test
INSERT INTO category (name) VALUES ('Dessert'), ('Plat Principal');

INSERT INTO recipe (name, difficulty, category_id) VALUES
('Fondant au Chocolat', 'Facile', 1),
('Boeuf Bourguignon', 'Difficile', 2),
('Tarte Tatin', 'Moyen', 1),
('Poulet Yassa', 'Moyen', 2);

INSERT INTO review (reviewer_name, rating, comment, recipe_id) VALUES
('Jean-Pierre', 5, 'Le fondant au chocolat est absolument divin ! Très coulant et pas trop sucré.', 1),
('Sophie L.', 4, 'Très bonne recette, mais attention au temps de cuisson qui varie selon les fours.', 1),
('Marc-Antoine', 3, 'Pas mal, mais il manquait un peu de beurre à mon goût pour le rendre plus onctueux.', 1);
