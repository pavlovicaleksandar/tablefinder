-- comment drop all objects when using postgres
DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS restaurants (
   id UUID PRIMARY KEY,
   name TEXT NOT NULL,
   description TEXT,
   image_url TEXT NOT NULL,
   number_of_ratings INTEGER NOT NULL,
   ratings_sum INTEGER NOT NULL,
   number_of_prices INTEGER NOT NULL,
   prices_sum INTEGER NOT NULL,
   manager_username TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS reservations (
    id UUID PRIMARY KEY,
    number_of_people INTEGER NOT NULL,
    date_and_time TIMESTAMP NOT NULL,
    user_id UUID NOT NULL,
    restaurant_id UUID NOT NULL,
    note_for_restaurant TEXT,
    note_from_restaurant TEXT,
    status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL,
    phone_number TEXT
);

CREATE TABLE IF NOT EXISTS reviews (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    restaurant_id UUID NOT NULL,
    username TEXT NOT NULL,
    rating INTEGER NOT NULL,
    price INTEGER NOT NULL,
    comment TEXT
);

CREATE TABLE IF NOT EXISTS tags (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS linked_tags (
    restaurant_id UUID NOT NULL,
    tag_id UUID NOT NULL,
    tag_name TEXT NOT NULL
);

INSERT INTO tags values ('33e20fd9-a530-40cb-8890-628561c85021', 'Serbian Cuisine');
INSERT INTO tags values ('11e20fd9-a530-40cb-8890-628561c85021', 'Greek Cuisine');
INSERT INTO tags values ('34e20fd9-a530-40cb-8890-628561c85021', 'Italian Cuisine');
INSERT INTO tags values ('35e20fd9-a530-40cb-8890-628561c85021', 'Chinese Cuisine');
INSERT INTO tags values ('36e20fd9-a530-40cb-8890-628561c85021', 'Mexican Cuisine');
INSERT INTO tags values ('37e20fd9-a530-40cb-8890-628561c85021', 'Indian Cuisine');
INSERT INTO tags values ('38e20fd9-a530-40cb-8890-628561c85021', 'Thai Cuisine');
INSERT INTO tags values ('39e20fd9-a530-40cb-8890-628561c85021', 'Vegan Options');
INSERT INTO tags values ('40e20fd9-a530-40cb-8890-628561c85021', 'Vegetarian Options');
INSERT INTO tags values ('41e20fd9-a530-40cb-8890-628561c85021', 'Gluten-Free Options');
INSERT INTO tags values ('43e20fd9-a530-40cb-8890-628561c85021', 'Outdoor Seating');
INSERT INTO tags values ('44e20fd9-a530-40cb-8890-628561c85021', 'Wi-Fi Access');
INSERT INTO tags values ('45e20fd9-a530-40cb-8890-628561c85021', 'Family-Friendly');
INSERT INTO tags values ('48e20fd9-a530-40cb-8890-628561c85021', 'Parking Available');
INSERT INTO tags values ('49e20fd9-a530-40cb-8890-628561c85021', 'Pet-Friendly');

INSERT INTO linked_tags values ('11e20fd9-a530-40cb-8890-628561c85021', '22e20fd9-a530-40cb-8890-628561c85021', 'Italian food');
INSERT INTO linked_tags values ('11e20fd9-a530-40cb-8890-628561c85021', '33e20fd9-a530-40cb-8890-628561c85021', 'Serbian food');

INSERT INTO restaurants values('11e20fd9-a530-40cb-8890-628561c85021', 'Dva Jelena',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce rutrum, metus vitae luctus sagittis, enim sapien molestie purus, et luctus urna erat et nisi. Donec tincidunt massa eu justo iaculis gravida. In metus eros, iaculis vitae auctor faucibus, pellentesque vitae lectus. Proin consequat lorem nunc, ut sagittis ex pellentesque sit amet. Proin pharetra, leo nec porttitor sagittis, turpis magna fermentum quam, sed dignissim odio eros id neque. Vivamus varius ullamcorper ligula. Nulla quam purus, consectetur ut orci eu, feugiat accumsan leo. Integer sit amet nisi vel orci porttitor tristique. Etiam id magna sit amet lectus placerat convallis. Aenean imperdiet, nulla.', 'https://i.postimg.cc/Rhm1HNmq/rsz-restoran-dva-jelena-31.jpg',
                               0, 0, 0, 0, 'manager3');
INSERT INTO restaurants values('22e20fd9-a530-40cb-8890-543561c85091', 'Vila Maska',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510',
                               0, 0, 0, 0, 'manager2');
INSERT INTO restaurants values('66e52fd9-a530-40cb-8890-543561c85091', 'Budha Bar',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://imageupload.io/ib/Ji9ELroSRAYPwDx_1692173549.jpeg',
                               0, 0, 0, 0, 'manager1');
INSERT INTO restaurants values('55e52fd9-a530-40cb-8890-543561c85091', 'Terminal',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://imageupload.io/ib/cpbbKQyWn4uig14_1692173729.jpeg',
                               0, 0, 0, 0, 'manager3');
INSERT INTO restaurants values('44e52fd9-a530-40cb-8890-543561c85091', 'Medzik',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510',
                               0, 0, 0, 0, 'manager1');
INSERT INTO restaurants values('33e52fd9-a530-40cb-8890-543561c85091', 'Kod Bore',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510',
                               0, 0, 0, 0, 'manager2');
INSERT INTO restaurants values('77e52fd9-a530-40cb-8890-543561c85091', 'Skok po skok',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510',
                               0, 0, 0, 0, 'manager1');
INSERT INTO restaurants values('88e52fd9-a530-40cb-8890-543561c85091', 'Babaroga',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510',
                               0, 0, 0, 0, 'manager3');
INSERT INTO restaurants values('99e52fd9-a530-40cb-8890-543561c85091', 'Mozzarela Osteria',
                               'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510',
                               0, 0, 0, 0, 'manager1');

INSERT INTO users values('1165694f-ceef-46c4-b2bf-823f68066cb7', 'admin', 'user@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Admin', '+381658735283');
INSERT INTO users values('22ba4c95-29c2-4672-b087-56e5f471a1df', 'manager1', 'user11@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Manager', '+381658735284');
INSERT INTO users values('33ba4c95-29c2-4672-b087-56e5f471a1df', 'manager2', 'user12@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Manager', '+381658735284');
INSERT INTO users values('44ba4c95-29c2-4672-b087-56e5f471a1df', 'manager3', 'user13@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Manager', '+381658735284');
INSERT INTO users values('55428af5-5158-4adc-a7f8-ce521719436c', 'guest1', 'user21@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735285');
INSERT INTO users values('66428af5-5158-4adc-a7f8-ce521719436c', 'guest2', 'user22@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735285');
INSERT INTO users values('67428af5-5158-4adc-a7f8-ce521719436c', 'guest3', 'user23@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735286');
INSERT INTO users values('68428af5-5158-4adc-a7f8-ce521719436c', 'guest4', 'user24@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735287');
INSERT INTO users values('69428af5-5158-4adc-a7f8-ce521719436c', 'guest5', 'user25@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735288');
INSERT INTO users values('70428af5-5158-4adc-a7f8-ce521719436c', 'guest6', 'user26@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735289');
INSERT INTO users values('71428af5-5158-4adc-a7f8-ce521719436c', 'guest7', 'user27@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735215');
INSERT INTO users values('72428af5-5158-4adc-a7f8-ce521719436c', 'guest8', 'user28@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735225');
INSERT INTO users values('73428af5-5158-4adc-a7f8-ce521719436c', 'guest9', 'user29@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'Guest', '+381658735235');

