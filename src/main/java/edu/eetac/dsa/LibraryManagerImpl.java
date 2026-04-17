package edu.eetac.dsa;

import edu.eetac.dsa.models.CatalogedBook;
import edu.eetac.dsa.models.Loan;
import edu.eetac.dsa.models.Reader;
import edu.eetac.dsa.models.StoredBook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class LibraryManagerImpl implements LibraryManager {

    private static final Logger logger = LogManager.getLogger(LibraryManagerImpl.class);
    private static final int STACK_CAPACITY = 10;
    private static final String LOAN_STATUS_IN_PROGRESS = "En tramit";

    private static LibraryManagerImpl instance;

    private final Map<String, Reader> readers;
    private final Map<String, CatalogedBook> catalogedBooks;
    private final Map<String, StoredBook> storedBooks;
    private final Queue<Stack<StoredBook>> storageStacks;

    private LibraryManagerImpl() {
        this.readers = new HashMap<>();
        this.catalogedBooks = new HashMap<>();
        this.storedBooks = new HashMap<>();
        this.storageStacks = new LinkedList<>();
    }

    public static synchronized LibraryManagerImpl getInstance() {
        if (instance == null) {
            instance = new LibraryManagerImpl();
        }
        return instance;
    }

    @Override
    public void addOrUpdateReader(String id, String name, String surname, String dni, String birthDate, String birthPlace, String address) {
        Reader reader = readers.get(id);

        if (reader == null) {
            readers.put(id, new Reader(id, name, surname, dni, birthDate, birthPlace, address));
            logger.info("Nuevo lector registrado. id={}", id);
            return;
        }

        reader.updateData(name, surname, dni, birthDate, birthPlace, address);
        logger.info("Lector actualizado. id={}", id);
    }

    @Override
    public void storeBook(String id, String isbn, String title, String publisher, int publicationYear, int editionNumber, String author, String topic) {
        StoredBook storedBook = new StoredBook(id, isbn, title, publisher, publicationYear, editionNumber, author, topic);
        storedBooks.put(id, storedBook);

        Stack<StoredBook> currentStack = null;
        for (Stack<StoredBook> stack : storageStacks) {
            currentStack = stack;
        }

        if (currentStack == null || currentStack.size() >= STACK_CAPACITY) {
            currentStack = new Stack<>();
            storageStacks.offer(currentStack);
        }

        currentStack.push(storedBook);
        logger.info("Libro almacenado. id={}, isbn={}", id, isbn);
    }

    @Override
    public CatalogedBook catalogBook() {
        logger.info("Inicio catalogBook()");
        if (storageStacks.isEmpty()) {
            logger.error("No hay libros pendientes de catalogar");
            throw new RuntimeException("No hay libros pendientes de catalogar");
        }

        Stack<StoredBook> currentStack = storageStacks.peek();
        StoredBook storedBook = currentStack.pop();

        if (currentStack.isEmpty()) {
            storageStacks.poll();
        }

        CatalogedBook catalogedBook = catalogedBooks.get(storedBook.getIsbn());

        if (catalogedBook != null) {
            catalogedBook.addCopy();
            logger.info("Libro ya catalogado. isbn={}, copies={}", catalogedBook.getIsbn(), catalogedBook.getAvailableCopies());
        } else {
            catalogedBook = new CatalogedBook(
                storedBook.getId(),
                storedBook.getIsbn(),
                storedBook.getTitle(),
                storedBook.getPublisher(),
                storedBook.getPublicationYear(),
                storedBook.getEditionNumber(),
                storedBook.getAuthor(),
                storedBook.getTopic()
            );
            catalogedBooks.put(storedBook.getIsbn(), catalogedBook);
            logger.info("Nuevo libro catalogado. isbn={}", catalogedBook.getIsbn());
        }

        logger.info("Fin catalogBook(). isbn={}, availableCopies={}", catalogedBook.getIsbn(), catalogedBook.getAvailableCopies());
        return catalogedBook;
    }

    @Override
    public void lendBook(String loanId, String readerId, String bookId, String loanDate, String dueDate) {
        Reader reader = readers.get(readerId);
        if (reader == null) {
            throw new RuntimeException("El lector no existe: " + readerId);
        }

        CatalogedBook catalogedBook = findCatalogedBook(bookId);
        if (catalogedBook == null) {
            throw new RuntimeException("El libro catalogado no existe: " + bookId);
        }

        if (catalogedBook.getAvailableCopies() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles para el libro: " + bookId);
        }

        Loan loan = new Loan(loanId, readerId, catalogedBook.getId(), loanDate, dueDate, LOAN_STATUS_IN_PROGRESS);
        catalogedBook.lendCopy();
        reader.addLoan(loan);
        logger.info("Prestamo registrado. loanId={}, readerId={}, bookId={}", loanId, readerId, bookId);
    }

    @Override
    public List<Loan> getLoansByReader(String readerId) {
        Reader reader = readers.get(readerId);
        if (reader == null) {
            throw new RuntimeException("El lector no existe: " + readerId);
        }

        return new ArrayList<>(reader.getLoans());
    }

    public List<Loan> getLoansByeReader(String readerId) {
        return getLoansByReader(readerId);
    }

    @Override
    public CatalogedBook getCatalogedBookByIsbn(String isbn) {
        return catalogedBooks.get(isbn);
    }

    @Override
    public Reader getReader(String readerId) {
        return readers.get(readerId);
    }

    @Override
    public void clear() {
        readers.clear();
        catalogedBooks.clear();
        storedBooks.clear();
        storageStacks.clear();
        logger.info("Estado de LibraryManager reiniciado");
    }

    private CatalogedBook findCatalogedBook(String bookIdOrIsbn) {
        CatalogedBook byIsbn = catalogedBooks.get(bookIdOrIsbn);
        if (byIsbn != null) {
            return byIsbn;
        }

        for (CatalogedBook book : catalogedBooks.values()) {
            if (book.getId().equals(bookIdOrIsbn)) {
                return book;
            }
        }

        return null;
    }
}
