package edu.eetac.dsa.models;

public class StoredBook {
    private final String id;
    private final String isbn;
    private final String title;
    private final String publisher;
    private final int publicationYear;
    private final int editionNumber;
    private final String author;
    private final String topic;

    public StoredBook(String id, String isbn, String title, String publisher, int publicationYear, int editionNumber, String author, String topic) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.editionNumber = editionNumber;
        this.author = author;
        this.topic = topic;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getPublisher() {
        return publisher;
    }
    public int getPublicationYear() {
        return publicationYear;
    }
    public int getEditionNumber() {
        return editionNumber;
    }
    public String getAuthor() {
        return author;
    }
    public String getTopic() {
        return topic;
    }




}
