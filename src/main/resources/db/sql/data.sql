INSERT INTO "user" (id, firstName, lastName, userName, email, password)
VALUES (1, 'mati', 'kott', 'admin', 'mati@admin.ee', 'password');
INSERT INTO "user" (id, firstName, lastName, userName, email, password)
VALUES (2, 'sigrid', 'narep', 'user', 'sigrid@user.ee', 'password');
INSERT INTO "user" (id, firstName, lastName, userName, email, password)
VALUES (3, 'kati', 'laane', 'employee', 'kati@employee.ee', 'password');

INSERT INTO role (id, name) VALUES (1, 'admin');
INSERT INTO role (id, name) VALUES (2, 'user');
INSERT INTO role (id, name) VALUES (3, 'employee');

INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('Moby Dick', 'Herman Melville', 'Lorem ipsum...', 20, DATE '2019-11-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('War and Peace', 'Leo Tolstoy', 'Lorem ipsum...', 7, DATE '2020-01-18', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('Hamlet', 'William Shakespeare', 'Lorem ipsum...', 11, DATE '2020-02-01', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('The Odysseys', 'Homer', 'Lorem ipsum...', 24, DATE '2020-01-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('The Adventures of Huckleberry Finn', 'Mark Twain', 'Lorem ipsum...', 3, DATE '2020-01-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('Alice''s Adventures in Wonderland', 'Lewis Carroll', 'Lorem ipsum...', 2, DATE '2019-09-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('Pride and Prejudice', 'Jane Austen', 'Lorem ipsum...', 12, DATE '2018-10-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('To the Lighthouse', 'Virginia Woolf', 'Lorem ipsum...', 4, DATE '2018-09-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('The Sound and the Fury', 'William Faulkner', 'Lorem ipsum...', 18, DATE '2020-01-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('Nineteen Eighty Four', 'George Orwell', 'Lorem ipsum...', 5, DATE '2019-10-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('Invisible Man', 'Ralph Ellison', 'Lorem ipsum...', 9, DATE '2019-01-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('To Kill a Mockingbird', 'Harper Lee', 'Lorem ipsum...', 23, DATE '2019-12-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('Great Expectations', 'Charles Dickens', 'Lorem ipsum...', 1, DATE '2019-07-02', TRUE);
INSERT INTO book (title, author, description, amount, added_to_stock, available)
VALUES ('Jane Eyre', 'Charlotte Bronte', 'Lorem ipsum...', 12, DATE '2019-05-02', TRUE);


INSERT INTO user_roles (role_id, user_id) VALUES (1, 1);
INSERT INTO user_roles (role_id, user_id) VALUES (2, 2);
INSERT INTO user_roles (role_id, user_id) VALUES (3, 3);

INSERT INTO user_books (book_id, user_id, took_on, due_date) VALUES (1, 2, DATE '2020-01-01', DATE '2020-02-01');
INSERT INTO user_books (book_id, user_id, took_on, due_date) VALUES (6, 2, DATE '2020-02-01', DATE '2020-03-01');


