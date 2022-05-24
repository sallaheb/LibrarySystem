package com.nology;

import com.opencsv.*;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, CsvValidationException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        // Reading csv
        // Adding Data to List array
        String CsvFile = "C:\\Users\\709887M2A\\nology\\LibrarySystem\\CSVdata.csv";

        List<Book> BookList = new ArrayList<>();
        OpneCsvtoReadAndStoreData(CsvFile, BookList);


        // user List instantiated to add user
        List<Users> userList = new ArrayList<>();
        Users NewUser0 = new Users("dave", "2342","davefile.csv", new ArrayList<>());
        userList.add(NewUser0);


        // Admin List instantiated to add Admin
        List<Admins> adminList = new ArrayList<>();
        Admins NewAdmin0 = new Admins("andrew", "2468");
        adminList.add(NewAdmin0);
        String AdminFileAv =  "C:\\Users\\709887M2A\\nology\\LibrarySystem\\AdminReportAvailableBooks.csv";


        // setting up scanner and logic for library

        Scanner s = new Scanner(System.in);
        boolean libraryIsRunning = true;


        while (libraryIsRunning) {


            int person = 0;
            int Options;

            System.out.println("Hello and Welcome"
                    + "\n if you are new user please type: 1"
                    + "\n if you are already a user please type: 2"
                    + "\n if you are an admin please type: 3");


               person = s.nextInt();


            switch (person) {
                case 1:
                    System.out.println("Create an account"
                            + "\n Please enter a username for your account");
                    String username = s.next();
                    System.out.println("");
                    System.out.println("Please enter your login_password");
                    String login_password = s.next();
                    System.out.println("");
                    userList.add(new Users(username, login_password, username + "file.csv", new ArrayList<>()));
                    System.out.println("You have successfully created an account.You can now log in using the main page");
                    break;

                case 2:
                    List<Users> cuurentUser= new ArrayList<>();

                    for (Users loggedUser : userList) {
                        System.out.println("please enter your username correctly(case sensitive)");
                        String name = s.next();
                        System.out.println("");
                        System.out.println("Please enter your login_password(case-sensitive)");
                        String passCode = s.next();

                        if (loggedUser.getName().equals(name) && loggedUser.getPassword().equals(passCode)) {
                            cuurentUser.add(loggedUser);

                            System.out.println("Please select the following options: " + loggedUser.getName() +
                                    "\n To view available books type:  1 " +
                                    "\n To Borrow a new book type : 2) " +
                                    "\n To view your loaned books type:3 " +
                                    "\n  To Return a book type: 4");
                        }
                    }
                    Options = s.nextInt();

                               switch (Options) {

                                case 1:
                                    cuurentUser.get(0).ListOfAvailableBooks(BookList);
                                    break;
                                case 2:
                                    // Reads user file and add data onto array object
                                    cuurentUser.get(0).OpenCsvToReadAndStoreUserBooksOnLoan( cuurentUser.get(0).getFileName(),  cuurentUser.get(0));
                                    // Take user input
                                    System.out.println("Please type in the full name of the book you would like to loan from the available list (case-sensitive)");
                                    String BookTitle = s.next();
                                    System.out.println(BookTitle);

                                    // Method to Loan a book and add update arrays objects of general bookList and user specific books
                                    cuurentUser.get(0).LoanABook(BookList, cuurentUser.get(0), BookTitle);
                                    // Method to update changes in user specific files // overwriting user data
                                    cuurentUser.get(0).OpenCsvMethodToWriteUserBooksOnLoan(cuurentUser.get(0));
                                    //Method to update changes in the general bookList files  // overwriting general original data
                                    OpneCsvtoWrtieAndStoreData(CsvFile, BookList);
                                    System.out.println(s.nextLine());
                                    break;
                                case 3:
                                    // Read user file and add data onto array object before viewing user specific books on loan
                                    cuurentUser.get(0).OpenCsvToReadAndStoreUserBooksOnLoan(cuurentUser.get(0).getFileName(), cuurentUser.get(0));
                                    cuurentUser.get(0).ListOfCurrentLoans();
                                    break;
                                case 4:
                                    // Read user file and add data onto array object before returning user specific books currently on loan
                                    cuurentUser.get(0).OpenCsvToReadAndStoreUserBooksOnLoan(cuurentUser.get(0).getFileName(), cuurentUser.get(0));

                                    // Take user input
                                    System.out.println("Please type in the full name of the book you would like to return from the current list of borrowed books (case-sensitive)");
                                    String BorrowedBookTitle = s.nextLine();
                                    // Method to Loan a book and update arrays objects of general bookList and user specific books
                                    cuurentUser.get(0).ReturnABook(BookList, cuurentUser.get(0), BorrowedBookTitle);
                                    // Method to update changes in user specific files // overwriting user data
                                    cuurentUser.get(0).OpenCsvMethodToWriteUserBooksOnLoan(cuurentUser.get(0));
                                    //Method to update changes in the general bookList files  // overwriting general original data
                                    OpneCsvtoWrtieAndStoreData(CsvFile, BookList);
                                    break;
                            }
                           break;
                case 3:
                    for (Admins AdminUser : adminList) {

                        System.out.println("please enter your AdminUsername correctly(case sensitive)");
                        String AdminUsername = s.next();
                        System.out.println("");
                        System.out.println("Please enter your login_password(case-sensitive)");
                        String AdminPassCode = s.next();
                        System.out.println("");

                        if (AdminUser.getName().equals(AdminUsername) && AdminUser.getPassword().equals(AdminPassCode)) {
                            System.out.println("");
                            System.out.println("Please select the following options: " +
                                    "\n To view available books type:  1 " +
                                    "\n To view loaned books type: 2 " +
                                    "\n To create a report of available books type:  3 " +
                                    "\n To create a report of loaned books type:  4 ");

                            int select = s.nextInt();
                            System.out.println("");

                            switch (select) {
                                case 1:
                                    AdminUser.ListOfAvailableBooks(BookList);
                                    break;
                                case 2:
                                    AdminUser.ListOfBooksOnLoan(BookList);
                                    break;
                                case 3:
                                    // read and store data before printing report
                                    AdminUser.writingFileForAvailableBooks(BookList, AdminFileAv);
                                    break;
                                case 4:
                                    AdminUser.writingFileForLoanedBooks(BookList);
                                    break;
                            }
                            break;

                        }
                    }
                    break;
                default:
                    System.out.println("sorry try again");
            }

        }

    }


    private static void OpneCsvtoWrtieAndStoreData(String CsvFile, List<Book> BookList) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        File file = new File(CsvFile);
        Writer writer = new FileWriter(file);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(BookList);
        System.out.println("File has been created");
        writer.close();


//        File file = new File(CsvFile);
//        FileWriter fw = new FileWriter(file);
//        BufferedWriter bw = new BufferedWriter(fw);
//
//        bw.write("Number,Title,Author,Genre,Subgenre, Publisher,Availability,noOfTimesLoaned");
//        bw.newLine();
//
//        for(int i=0;i<BookList.size();i++)
//        {
//            bw.write(BookList.get(i++).toString()+  "\n" + BookList.get(i).toString());
//            bw.newLine();
//        }
//        bw.close();
//        fw.close();
//        System.out.println("CSV file created succesfully.");

    }

    private static void OpneCsvtoReadAndStoreData(String FilePath, List<Book> BookList) throws IOException, CsvValidationException {


        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').withQuoteChar('\"').build();

        // Reading file //

        CSVReader reader = new CSVReaderBuilder(new FileReader(FilePath)).withCSVParser(csvParser).withSkipLines(1).build();

        // Line values for book rows //
        String[] Line;
        while ((Line = reader.readNext()) != null) {
           BookList.add(new Book(Line[0], Line[1], Line[2], Line[3], Line[4], Line[5],Line[6],Line[7]));
        }
    }
}


