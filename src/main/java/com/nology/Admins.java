package com.nology;

import com.google.gson.GsonBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admins {
    private String name;
    private String password;
    private List<Book> reportLoanedBooks = new ArrayList<>();
    private List<Book> reportAvailableBooks = new ArrayList<>();


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

    public void writingFileForLoanedBooks(List<Book> BookList) throws IOException {
        List<Book> report = getReportLoanedBooks();

        for (Book book: BookList) {
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
