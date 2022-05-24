package com.nology;


import com.opencsv.bean.CsvBindByPosition;

public class Book {
    @CsvBindByPosition(position = 0)
    private String Number;
    @CsvBindByPosition(position = 1)
    private String Title;
    @CsvBindByPosition(position = 2)
    private String Author;
    @CsvBindByPosition(position = 3)
    private String Genre;
    @CsvBindByPosition(position = 4)
    private String Subgenre;
    @CsvBindByPosition(position = 5)
    private String Publisher;
    @CsvBindByPosition(position = 6)
    private String Availability;
    @CsvBindByPosition(position = 7)
    private String NoOfTimesLoaned;

    public Book(String number, String title, String author, String genre, String subgenre, String publisher, String availability, String noOfTimesLoaned) {
        Number = number;
        Title = title;
        Author = author;
        Genre = genre;
        Subgenre = subgenre;
        Publisher = publisher;
        Availability = availability;
        NoOfTimesLoaned = noOfTimesLoaned;
    }

    @Override
    public String toString() {
        return
                 Number +"," + "\""+
                 Title + "," + "\""+
                 Author + "," + "\""+
                 Genre + "," + "\""+
                 Subgenre + "," + "\""+
                 Publisher + "," + "\""+
                 Availability + "," + "\""+
                 NoOfTimesLoaned + ","+ "\"";
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getSubgenre() {
        return Subgenre;
    }

    public void setSubgenre(String subgenre) {
        Subgenre = subgenre;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    public String getNoOfTimesLoaned() {
        return NoOfTimesLoaned;
    }

    public void setNoOfTimesLoaned(String noOfTimesLoaned) {
        NoOfTimesLoaned = String.valueOf(noOfTimesLoaned);
    }
}
