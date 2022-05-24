package com.nology;

import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategyBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Users {
    private String name;
    private String password;
    private String fileName;
    private List<Book> bookOnLoan = new ArrayList<>();

    public Users(String name, String password, String fileName, List<Book> bookOnLoan) {
        this.name = name;
        this.password = password;
        this.fileName = fileName;
        this.bookOnLoan = bookOnLoan;

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

    public void LoanABook(@NotNull List<Book> BookList, Users user, String BookTitle) {
        for (Book book : BookList) {
            if (book.getTitle().contains(BookTitle) && book.getAvailability().equals("YES")) {
                user.getBooksOnLoan().add(book);
                book.setAvailability("No");
                System.out.println(book.getTitle() + "has been borrowed");
                book.setNoOfTimesLoaned(String.valueOf(Integer.parseInt(book.getNoOfTimesLoaned()) + 1));;
            }
        }
    }

    public void ReturnABook(@NotNull List<Book> BookList, Users user, String BookTitle) {
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


    public void ListOfAvailableBooks(@NotNull List<Book> BookList) {

        //the list that contains the String arrays
        List<Book> report = new ArrayList<>();
        for (Book book: BookList) {
            System.out.println(book.getAvailability());
            if (book.getAvailability().contains("YES")) {
                Collections.addAll(report, book);
            }
        }
        String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(report); // convert to JSON
        System.out.println(JsonConvertedBookList);
    }


    public void OpenCsvToReadAndStoreUserBooksOnLoan(String fileName, Users user) throws IOException, CsvValidationException {
        // Reading file //
        CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build();

        // Line values for book rows //
        String[] Line;
        while ((Line = reader.readNext()) != null) {
           user.getBooksOnLoan().add(new Book(Line[0],Line[1],Line[2],Line[3],Line[4],Line[5],Line[6],Line[7]));
        }

    }

     public void OpenCsvMethodToWriteUserBooksOnLoan(@NotNull Users user) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
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
