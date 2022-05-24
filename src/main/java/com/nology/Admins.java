package com.nology;

import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategyBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admins {
    private String name;
    private String password;
    private List<Books> reportLoanedBooks = new ArrayList<>();
    private List<Books> reportAvailableBooks = new ArrayList<>();


    public Admins(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Books> getReportLoanedBooks() {
        return reportLoanedBooks;
    }

    public List<Books> getReportAvailableBooks() {
        return reportAvailableBooks;
    }

    public void ListOfBooksOnLoan(@NotNull List<Books> BookList) {
        //the list that contains the String arrays
        List<Books> report = getReportLoanedBooks();
        for (Books book: BookList) {
            if (book.getAvailability().contains("No")) {
                Collections.addAll(report, book);
            }
        }

        String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(report); // convert to JSON
        System.out.println(JsonConvertedBookList);
    }

    public void writingFileForLoanedBooks(List<Books> BookList) throws IOException {
        List<Books> report = getReportLoanedBooks();

        for (Books book: BookList) {
            if (book.getAvailability().equals("No")) {
                Collections.addAll(report, book);
            }
        }

        File file = new File("AdminReportUnavailableBooks.csv");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("Number,Title,Author,Genre,Subgenre, Publisher,Availability,noOfTimesLoaned");
        bw.newLine();

        for(int i=0;i<report.size();i++)
        {
            bw.write(report.get(i++).toString()+"," + "\n" + report.get(i).toString());
            bw.newLine();
        }
        bw.close();
        fw.close();
        System.out.println("CSV file created succesfully.");

    }

    public void ListOfAvailableBooks(@NotNull List<Books> BookList) {

        //the list that contains the String arrays
        List<Books> report = getReportAvailableBooks();
        for (Books book: BookList) {
            if (book.getAvailability().contains("YES")) {
                Collections.addAll(report, book);
            }
        }
        String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(report); // convert to JSON
        System.out.println(JsonConvertedBookList);

    }

    public  void  writingFileForAvailableBooks ( List<Books> BookList, String AdminFileAv) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Books> report = getReportAvailableBooks();

        for (Books book: BookList) {
            if (book.getAvailability().contains("YES")) {
                Collections.addAll(report, book);
                System.out.println(book.getTitle());
            }
        }

        File file = new File(AdminFileAv);
        Writer writer = new FileWriter(file);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(report);
        System.out.println("File has been created");
        writer.close();

    }
}
