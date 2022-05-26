package com.nology;

import com.google.gson.GsonBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategyBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String name;
    private String password;
    private String fileName;
    private List<Book> bookOnLoan = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    public User(String name, String password, String fileName, List<Book> bookOnLoan) {
        this.name = name;
        this.password = password;
        this.fileName = fileName;
        this.bookOnLoan = bookOnLoan;

    }

    public List<Book> getBooks() {
        return books;
    }

    public String getName() {
        return name;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getBooksOnLoan() {
        return bookOnLoan;
    }

    public void ListOfCurrentLoans() {
        List<Book>CurrentLoans = getBooksOnLoan();
        String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(CurrentLoans); // convert to JSON
        System.out.println(JsonConvertedBookList);
    }

    public void LoanABook(@NotNull List<Book> BookList, User user, String BookTitle) {
        for (Book book : BookList) {
            if (book.getTitle().contains(BookTitle) && book.getAvailability().equals("YES")) {
                user.getBooksOnLoan().add(book);
                book.setAvailability("No");
                System.out.println(book.getTitle() + "has been borrowed");
                book.setNoOfTimesLoaned(String.valueOf(Integer.parseInt(book.getNoOfTimesLoaned()) + 1));;
            }
        }
    }

    public void ReturnABook(@NotNull List<Book> BookList, User user, String BookTitle) {
        List<Book> borrowedBooks = user.getBooksOnLoan();

        for (Book book : borrowedBooks) {
            if (book.getTitle().contains(BookTitle) ) {
                user.getBooksOnLoan().remove(book);
            } else {
                System.out.println("Book Name Not Found");
            }
        }

        for (Book bookItem : BookList) {
            if (bookItem.getTitle().contains(BookTitle) ) {
                bookItem.setAvailability("No");
                System.out.println(bookItem.getTitle() + "has been returned");
            }
        }
    }



    public void addBooks(List<Book> books) {
        this.books.addAll(books);
    }


     public void OpenCsvMethodToWriteUserBooksOnLoan(@NotNull User user) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Book> borrowedBooks = user.getBooksOnLoan();

         File file = new File(user.getFileName());
         Writer writer = new FileWriter(file);

         HeaderColumnNameMappingStrategy<Book> strategy = new HeaderColumnNameMappingStrategyBuilder<Book>().build();
         strategy.setType(Book.class);
         strategy.setColumnOrderOnWrite(new ComparableComparator());
         StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
         beanToCsv.write(borrowedBooks);
         System.out.println("File has been created");
         writer.close();
    }


}
