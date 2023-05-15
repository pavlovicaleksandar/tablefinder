CREATE TABLE IF NOT EXISTS tables (
    id UUID PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS restaurants (
   id UUID PRIMARY KEY,
   name TEXT NOT NULL,
   description TEXT
);

INSERT INTO restaurants values('27e20fd9-a530-40cb-8890-628561c85021', 'Bistro Bar', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce rutrum, metus vitae luctus sagittis, enim sapien molestie purus, et luctus urna erat et nisi. Donec tincidunt massa eu justo iaculis gravida. In metus eros, iaculis vitae auctor faucibus, pellentesque vitae lectus. Proin consequat lorem nunc, ut sagittis ex pellentesque sit amet. Proin pharetra, leo nec porttitor sagittis, turpis magna fermentum quam, sed dignissim odio eros id neque. Vivamus varius ullamcorper ligula. Nulla quam purus, consectetur ut orci eu, feugiat accumsan leo. Integer sit amet nisi vel orci porttitor tristique. Etiam id magna sit amet lectus placerat convallis. Aenean imperdiet, nulla.');
INSERT INTO restaurants values('59e20fd9-a530-40cb-8890-543561c85091', 'Vila Maska', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec euismod mi. Vivamus pulvinar eros at felis euismod ullamcorper. Aenean nulla ex, gravida in molestie vitae, elementum congue odio. Vestibulum id interdum ex, ut fringilla nulla. Curabitur turpis orci, vestibulum id ante sed, dapibus mattis turpis. Donec fermentum mauris nec.');

