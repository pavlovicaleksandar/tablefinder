CREATE TABLE IF NOT EXISTS tables (
    id UUID PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS restaurants (
   id UUID PRIMARY KEY,
   name TEXT NOT NULL,
   description TEXT
);

INSERT INTO restaurants values('27e20fd9-a530-40cb-8890-628561c85021', 'Bistro Bar', 'carski restoran');
INSERT INTO restaurants values('59e20fd9-a530-40cb-8890-543561c85091', 'Vila Maska', 'top restoran');

