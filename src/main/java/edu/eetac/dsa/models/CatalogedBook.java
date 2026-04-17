package edu.eetac.dsa.models;

public class CatalogedBook {
    private String id;
    private String isbn;
    private String title;
    private String publisher;
    private int publicationYear;
    private int editionNumber;
    private String author;
    private String topic;
    private int availableCopies;

    public CatalogedBook(String id, String isbn, String title, String publisher, int publicationYear, int editionNumber, String author, String topic) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.editionNumber = editionNumber;
        this.author = author;
        this.topic = topic;
        this.availableCopies = 1;
    }
    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
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

    public int getAvailableCopies() {
        return availableCopies;
    }
    public void addCopy(){
        this.availableCopies++;
    }
    public void lendCopy(){
        this.availableCopies--;
    }

}
