DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS tables (
    id UUID PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS restaurants (
   id UUID PRIMARY KEY,
   name TEXT NOT NULL,
   description TEXT
);

CREATE TABLE IF NOT EXISTS reservations (
    id UUID PRIMARY KEY,
    number_of_people INTEGER NOT NULL,
    user_id UUID NOT NULL,
    restaurant_id UUID NOT NULL,
    note_for_restaurant TEXT
);

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);

INSERT INTO restaurants values('11e20fd9-a530-40cb-8890-628561c85021', 'Bistro Bar', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce rutrum, metus vitae luctus sagittis, enim sapien molestie purus, et luctus urna erat et nisi. Donec tincidunt massa eu justo iaculis gravida. In metus eros, iaculis vitae auctor faucibus, pellentesque vitae lectus. Proin consequat lorem nunc, ut sagittis ex pellentesque sit amet. Proin pharetra, leo nec porttitor sagittis, turpis magna fermentum quam, sed dignissim odio eros id neque. Vivamus varius ullamcorper ligula. Nulla quam purus, consectetur ut orci eu, feugiat accumsan leo. Integer sit amet nisi vel orci porttitor tristique. Etiam id magna sit amet lectus placerat convallis. Aenean imperdiet, nulla.');
INSERT INTO restaurants values('22e20fd9-a530-40cb-8890-543561c85091', 'Vila Maska', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');
INSERT INTO restaurants values('33e52fd9-a530-40cb-8890-543561c85091', 'Kod Bore', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');
INSERT INTO restaurants values('44e52fd9-a530-40cb-8890-543561c85091', 'Medzik', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');
INSERT INTO restaurants values('55e52fd9-a530-40cb-8890-543561c85091', 'Terminal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');
INSERT INTO restaurants values('66e52fd9-a530-40cb-8890-543561c85091', 'Budha Bar', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');
INSERT INTO restaurants values('77e52fd9-a530-40cb-8890-543561c85091', 'Skok po skok', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');
INSERT INTO restaurants values('88e52fd9-a530-40cb-8890-543561c85091', 'Babaroga', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');
INSERT INTO restaurants values('99e52fd9-a530-40cb-8890-543561c85091', 'Mozzarela Osteria', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');

INSERT INTO users values('11e52fd9-a530-40cb-8890-543561c85091', 'user1', 'user1@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin');
INSERT INTO users values('22e20fd9-a530-40cb-8890-628561c85021', 'user2', 'user2@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin');
INSERT INTO users values('33e20fd9-a530-40cb-8890-628561c85021', 'user3', 'user3@example.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin');

