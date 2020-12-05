package com.bookstore.data.Repository;

import com.bookstore.data.model.Book;
import com.bookstore.web.exceptions.BookDoesNotExistException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    default Book saveBook(Book book) throws BookDoesNotExistException {
        if (isValid(book)){
            save(book);
        }
        return book;
    }

    private boolean isValid(Book book) throws BookDoesNotExistException {
        if (!bookHasStore(book)){
            throw new BookDoesNotExistException("store cannot be null");
        }
        if (!bookHasTitle(book)){
            throw new BookDoesNotExistException("title cannot be null");
        }
        if (!bookHasAuthor(book)){
            throw new BookDoesNotExistException("Author cannot be null");
        }
        return true;
    }

    private boolean bookHasStore(Book book){
        if (book.getStore() == null){
            return false;
        }
        return true;
    }

    private boolean bookHasTitle(Book book){
        if (book.getTitle() == null){
            return false;
        }
        return true;
    }

    private boolean bookHasAuthor(Book book){
        if (book.getAuthor() == null){
            return false;
        }
        return true;
    }


}

