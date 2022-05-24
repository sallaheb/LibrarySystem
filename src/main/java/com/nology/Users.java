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
    private List<Books> BooksOnLoan = new ArrayList<>();

    public Users(String name, String password, String fileName, List<Books> booksOnLoan) {
        this.name = name;
        this.password = password;
        this.fileName = fileName;
        BooksOnLoan = booksOnLoan;

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

    public List<Books> getBooksOnLoan() {
        return BooksOnLoan;
    }

    public void LoanABook(@NotNull List<Books> BookList, Users user, String BookTitle) {
        for (Books book : BookList) {
            if (book.getTitle().contains(BookTitle) && book.getAvailability().equals("YES")) {
                user.getBooksOnLoan().add(book);
                book.setAvailability("No");
                System.out.println(book.getTitle() + "has been borrowed");
                book.setNoOfTimesLoaned(String.valueOf(Integer.parseInt(book.getNoOfTimesLoaned()) + 1));;
            }
        }
    }

    public void ReturnABook(@NotNull List<Books> BookList, Users user, String BookTitle) {
        List<Books> BorrowedBooks = user.getBooksOnLoan();

        for (Books book : BorrowedBooks) {
            if (book.getTitle().contains(BookTitle) ) {
                user.getBooksOnLoan().remove(book);
            } else {
                System.out.println("Book Name Not Found");
            }
        }

        for (Books bookItem : BookList) {
            if (bookItem.getTitle().contains(BookTitle) ) {
                bookItem.setAvailability("No");
                System.out.println(bookItem.getTitle() + "has been returned");
            }
        }
    }



    public void ListOfAvailableBooks(@NotNull List<Books> BookList) {

        //the list that contains the String arrays
        List<Books> report = new ArrayList<>();
        for (Books book: BookList) {
            System.out.println(book.getTitle());
            if (book.getAvailability().contains("YES")) {
                Collections.addAll(report, book);
                System.out.println(book.getTitle());
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
           user.getBooksOnLoan().add(new Books(Line[0],Line[1],Line[2],Line[3],Line[4],Line[5],Line[6],Line[7]));
        }

    }

     public void OpenCsvMethodToWriteUserBooksOnLoan(@NotNull Users user) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Books> BorrowedBooks = user.getBooksOnLoan();

         File file = new File(user.getFileName());
         Writer writer = new FileWriter(file);

         HeaderColumnNameMappingStrategy<Books> strategy = new HeaderColumnNameMappingStrategyBuilder<Books>().build();
         strategy.setType(Books.class);
         strategy.setColumnOrderOnWrite(new ComparableComparator());
         StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
         beanToCsv.write(BorrowedBooks);
         System.out.println("File has been created");
         writer.close();
    }

    public void ListOfCurrentLoans() {
         List<Books>CurrentLoans = getBooksOnLoan();
         String JsonConvertedBookList = new GsonBuilder().setPrettyPrinting().create().toJson(CurrentLoans); // convert to JSON
         System.out.println(JsonConvertedBookList);
    }
}
