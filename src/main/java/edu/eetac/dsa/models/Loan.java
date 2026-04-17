package edu.eetac.dsa.models;

public class Loan {
    private final String id;
    private final String readerId;
    private final String bookId;
    private final String loanDate;
    private final String dueDate;
    private final String status;

    public Loan(String id, String readerId, String bookId, String loanDate, String dueDate, String status) {
        this.id = id;
        this.readerId = readerId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getReaderId() {
        return readerId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }
}
