Spring Security Test Assignment
===============================

REST API backend with authorization and authentication.

## Project description

Create a library application. The application must allow management of both 
lenders and books. Information on all books in the library must be publicly 
available. The usual loan term is four weeks. If the book is less than three months old, 
the loan is due for one week. If there are less than five copies of the book, 
the term of the loan is one week. These dates should be easy to set in the application.

- [Requirements](#requirements)
- [Technologies used](#technologies-used)
- [Endpoints](#endpoints) – API endpoints
- [How to use](#how-to-use?) – Contributing authors


## Requirements

### User
Must be able to see the overall state of the library.
The general state should include information such as which books are in the 
library, many copies remaining and how long they can be borrowed for.

### Employee
Must be able to see a report of overdue lenders. The report must include both 
the name of the lender, the book that has passed its due date.
- Must be able to search for a book.
- Must be able to lend the book. 
- Must be able to accept the book.
- Must be able to add lenders.
- Must be able to look for lenders.

### Admin
- Must be able to add lenders. 
- Must be able to look for lenders. 
- Must be able to search for a book.
- Must be able to add a book.
- Must be able to delete the book.

## Technologies used

- Java 11
- REST API
- HyperSQL
- Spring Security
- JUnit


## Endpoints

### Users

- GET **All users**: `/api/users`
- GET **All overdue lenders**: `/api/users/debts`
- GET **User by id**: `/api/users/<id>`
- POST **New user from request body**: `/api/users`
- POST **Lend book by id to user by id**: `/api/users/<user_id>/books/<book_id>`
- PUT **Update book to be returned**: `/api/users/<user_id>/books/<book_id>`
- DELETE **Delete user by id**: `/api/users/delete/<id>`


### Books

- GET **All books**: `/api/books`
- GET **Book by id**: `/api/books/<id>`
- POST **New book from request body**: `/api/books`
- DELETE **Delete book by id**: `/api/books/delete/<id>`


## How to use?

To run the program
```
cd SecurityTestAssignment
./gradlew appRun
```

Access ```http://localhost:8080/api/books```


Login credentials for "EMPLOYEE":

```
userName: "employee"
password: "password"
```

Login credentials for "ADMIN":

```
userName: "admin"
password: "password"
```
