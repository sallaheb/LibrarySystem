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

public class Admin {
    private String name;
    private String password;
    private List<Book> reportLoanedBooks = new ArrayList<>();
    private List<Book> reportAvailableBooks = new ArrayList<>();
    private Book books;


    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getReportLoanedBooks() {
        return reportLoanedBooks;
    }

    public List<Book> getReportAvailableBooks() {
        return reportAvailableBooks;
    }

    public void ListOfBooksOnLoanJSON() {
        //List of Books in JSON
        String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(reportLoanedBooks); // convert to JSON
        System.out.println(JsonConvertedBookList);
    }

    public void ListOfAvailableBooksJSON() {
        //List of Books in JSON
        String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(reportAvailableBooks); // convert to JSON
        System.out.println(JsonConvertedBookList);
    }

    public void addAvailableBooks(List<Book> books) {
        books.stream()
                .filter(book -> book.getAvailability().contains("YES"))
                .forEach(book -> Collections.addAll(reportAvailableBooks, book));
    }
    public void addBooksOnLoan(List<Book> books) {
        books.stream()
                .filter(book -> book.getAvailability().contains("No"))
                .forEach(book -> Collections.addAll(reportLoanedBooks, book));
    }
}
