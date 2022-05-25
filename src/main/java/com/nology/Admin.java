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

    public void ListOfBooksOnLoan(@NotNull List<Book> BookList) {
        //the list that contains the String arrays
        List<Book> report = getReportLoanedBooks();
        for (Book book: BookList) {
            if (book.getAvailability().contains("No")) {
                Collections.addAll(report, book);
            }
        }

        String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(report); // convert to JSON
        System.out.println(JsonConvertedBookList);
    }

    public void writingFileForLoanedBooks(List<Book> BookList, String AdminFileNotAv) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Book> report = getReportLoanedBooks();

        for (Book book: BookList) {
            if (book.getAvailability().contains("No")) {
                Collections.addAll(report, book);
            }
        }

        File file = new File(AdminFileNotAv);
        Writer writer = new FileWriter(file);

        HeaderColumnNameMappingStrategy<Book> strategy = new HeaderColumnNameMappingStrategyBuilder<Book>().build();
        strategy.setType(Book.class);
        strategy.setColumnOrderOnWrite(new ComparableComparator());

        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
        beanToCsv.write(report);
        System.out.println("File has been created");
        writer.close();

    }

    public void ListOfAvailableBooks(@NotNull List<Book> BookList) {

        //the list that contains the String arrays
        List<Book> report = getReportAvailableBooks();
        for (Book book: BookList) {
            if (book.getAvailability().contains("YES")) {
                Collections.addAll(report, book);
            }
        }
        String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(report); // convert to JSON
        System.out.println(JsonConvertedBookList);

    }

    public  void  writingFileForAvailableBooks (List<Book> BookList, String AdminFileAv) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Book> report = getReportAvailableBooks();

        for (Book book: BookList) {
            if (book.getAvailability().contains("YES")) {
                Collections.addAll(report, book);
            }
        }

        File file = new File(AdminFileAv);
        Writer writer = new FileWriter(file);

        HeaderColumnNameMappingStrategy<Book> strategy = new HeaderColumnNameMappingStrategyBuilder<Book>().build();
        strategy.setType(Book.class);
        strategy.setColumnOrderOnWrite(new ComparableComparator());

        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
        beanToCsv.write(report);
        System.out.println("File has been created");
        writer.close();

    }
}