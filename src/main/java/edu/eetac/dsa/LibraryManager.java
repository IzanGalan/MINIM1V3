package edu.eetac.dsa;

import edu.eetac.dsa.models.*;

import java.util.List;

public interface LibraryManager {

    void addOrUpdateReader(String id, String name, String surname, String dni, String birthDate, String birthPlace, String address);

    void storeBook(String id, String isbn, String title, String publisher, int publicationYear, int editionNumber, String author, String topic);

    CatalogedBook catalogBook();

    void lendBook(String loanId, String readerId, String bookId, String loanDate, String dueDate);

    List<Loan> getLoansByReader(String readerId);

    default List<Loan> getLoansByeReader(String readerId) {
        return getLoansByReader(readerId);
    }

    CatalogedBook getCatalogedBookByIsbn(String isbn);

    Reader getReader(String readerId);

    void clear();
}
