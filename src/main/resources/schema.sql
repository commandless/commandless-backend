CREATE TABLE keyword (
    id serial primary key,
    label varchar(36) unique not null
);

CREATE TABLE recipe (
    id serial primary key,
    command_id integer not null references recipe(id),
    description text not null,
    inputs jsonb not null
);

CREATE TABLE keyword_recipe (
    id serial primary key,
    keyword_id integer not null references keyword(id),
    recipe_id integer not null references recipe(id)
);

CREATE TABLE command (
    id serial primary key,
    inputs jsonb not null,
    bin varchar(128) not null,
    npm varchar(128),
    github varchar(128)
);
