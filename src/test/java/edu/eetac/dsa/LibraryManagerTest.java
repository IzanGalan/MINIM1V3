package edu.eetac.dsa;

import edu.eetac.dsa.models.CatalogedBook;
import edu.eetac.dsa.models.Loan;
import edu.eetac.dsa.models.Reader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LibraryManagerTest {

    private LibraryManager lm;

    @Before
    public void setUp() {
        lm = LibraryManagerImpl.getInstance();
        lm.clear();

        lm.addOrUpdateReader("R1", "Roger", "Saurina", "12345678A", "2000-01-01", "Barcelona", "Carrer Major 1");
        lm.addOrUpdateReader("R2", "Anna", "Garcia", "87654321B", "1999-05-10", "Lleida", "Carrer Nou 2");
    }

    @After
    public void tearDown() {
        lm.clear();
    }

    @Test
    public void testAddOrUpdateReader() {
        Reader reader = lm.getReader("R1");

        Assert.assertNotNull(reader);
        Assert.assertEquals("Roger", reader.getName());

        lm.addOrUpdateReader("R1", "Roger Updated", "Saurina", "12345678A", "2000-01-01", "Barcelona", "Nova Adreca");

        reader = lm.getReader("R1");

        Assert.assertEquals("Roger Updated", reader.getName());
        Assert.assertEquals("Nova Adreca", reader.getAddress());
    }

    @Test
    public void testStoreAndCatalogBook() {
        lm.storeBook("B1", "ISBN1", "Book One", "Anaya", 2020, 1, "Author1", "Topic1");

        CatalogedBook catalogedBook = lm.catalogBook();

        Assert.assertNotNull(catalogedBook);
        Assert.assertEquals("ISBN1", catalogedBook.getIsbn());
        Assert.assertEquals(1, catalogedBook.getAvailableCopies());
    }

    @Test
    public void testCatalogBookSameIsbnIncrementsCopies() {
        lm.storeBook("B1", "ISBN1", "Book One", "Anaya", 2020, 1, "Author1", "Topic1");
        lm.storeBook("B2", "ISBN1", "Book One Copy", "Anaya", 2020, 1, "Author1", "Topic1");

        lm.catalogBook();
        CatalogedBook catalogedBook = lm.catalogBook();

        Assert.assertNotNull(catalogedBook);
        Assert.assertEquals("ISBN1", catalogedBook.getIsbn());
        Assert.assertEquals(2, catalogedBook.getAvailableCopies());
    }

    @Test
    public void testLendBook() {
        lm.storeBook("B1", "ISBN1", "Book One", "Anaya", 2020, 1, "Author1", "Topic1");
        CatalogedBook catalogedBook = lm.catalogBook();

        lm.lendBook("L1", "R1", catalogedBook.getId(), "2026-04-17", "2026-05-17");

        CatalogedBook updatedBook = lm.getCatalogedBookByIsbn("ISBN1");
        Assert.assertEquals(0, updatedBook.getAvailableCopies());

        List<Loan> loans = lm.getLoansByReader("R1");
        Assert.assertEquals(1, loans.size());
        Assert.assertEquals("L1", loans.get(0).getId());
    }

    @Test
    public void testGetLoansByReader() {
        lm.storeBook("B1", "ISBN1", "Book One", "Anaya", 2020, 1, "Author1", "Topic1");
        CatalogedBook catalogedBook = lm.catalogBook();

        lm.lendBook("L1", "R1", catalogedBook.getId(), "2026-04-17", "2026-05-17");

        List<Loan> loans = lm.getLoansByReader("R1");

        Assert.assertNotNull(loans);
        Assert.assertEquals(1, loans.size());
        Assert.assertEquals("R1", loans.get(0).getReaderId());
    }
}
