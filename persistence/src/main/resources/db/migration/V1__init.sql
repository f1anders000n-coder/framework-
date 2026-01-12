create table if not exists users (
    id bigserial primary key,
    username varchar(80) not null,
    password_hash varchar(200) not null,
    constraint uk_users_username unique (username)
);

create table if not exists user_roles (
    user_id bigint not null references users(id) on delete cascade,
    role varchar(30) not null,
    constraint pk_user_roles primary key (user_id, role)
);

create table if not exists books (
    id bigserial primary key,
    title varchar(200) not null,
    author varchar(200),
    description varchar(2000),
    created_at timestamptz not null
);

create index if not exists ix_books_created_at on books(created_at);

create table if not exists comments (
    id bigserial primary key,
    text varchar(2000) not null,
    created_at timestamptz not null,
    book_id bigint not null references books(id) on delete cascade,
    author_id bigint not null references users(id) on delete restrict
);

create index if not exists ix_comments_book_created_at on comments(book_id, created_at desc);
