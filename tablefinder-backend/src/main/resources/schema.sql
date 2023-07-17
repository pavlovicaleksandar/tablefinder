DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS tables (
    id UUID PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS restaurants (
   id UUID PRIMARY KEY,
   name TEXT NOT NULL,
   description TEXT,
   image_url TEXT NOT NULL
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
    role TEXT NOT NULL
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

INSERT INTO restaurants values('11e20fd9-a530-40cb-8890-628561c85021', 'Bistro Bar', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce rutrum, metus vitae luctus sagittis, enim sapien molestie purus, et luctus urna erat et nisi. Donec tincidunt massa eu justo iaculis gravida. In metus eros, iaculis vitae auctor faucibus, pellentesque vitae lectus. Proin consequat lorem nunc, ut sagittis ex pellentesque sit amet. Proin pharetra, leo nec porttitor sagittis, turpis magna fermentum quam, sed dignissim odio eros id neque. Vivamus varius ullamcorper ligula. Nulla quam purus, consectetur ut orci eu, feugiat accumsan leo. Integer sit amet nisi vel orci porttitor tristique. Etiam id magna sit amet lectus placerat convallis. Aenean imperdiet, nulla.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');
INSERT INTO restaurants values('22e20fd9-a530-40cb-8890-543561c85091', 'Vila Maska', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');
INSERT INTO restaurants values('33e52fd9-a530-40cb-8890-543561c85091', 'Kod Bore', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');
INSERT INTO restaurants values('44e52fd9-a530-40cb-8890-543561c85091', 'Medzik', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');
INSERT INTO restaurants values('55e52fd9-a530-40cb-8890-543561c85091', 'Terminal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');
INSERT INTO restaurants values('66e52fd9-a530-40cb-8890-543561c85091', 'Budha Bar', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');
INSERT INTO restaurants values('77e52fd9-a530-40cb-8890-543561c85091', 'Skok po skok', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');
INSERT INTO restaurants values('88e52fd9-a530-40cb-8890-543561c85091', 'Babaroga', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');
INSERT INTO restaurants values('99e52fd9-a530-40cb-8890-543561c85091', 'Mozzarela Osteria', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.', 'https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510');

INSERT INTO users values('1465694f-ceef-46c4-b2bf-823f68066cb7', 'user1', 'user1@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin');
INSERT INTO users values('35ba4c95-29c2-4672-b087-56e5f471a1df', 'user2', 'user2@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin');
INSERT INTO users values('f2428af5-5158-4adc-a7f8-ce521719436c', 'user3', 'user3@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin');

