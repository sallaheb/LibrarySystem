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
        //Removing first-line from list
        String CsvFile = "C:\\Users\\709887M2A\\nology\\LibrarySystem\\CSVdata.csv";
        List<Book> BookList = new ArrayList<>();
        OpenCsvToReadAndStoreData(CsvFile, BookList);
        BookList.removeIf(book -> book.getTitle().equals("Number"));

        // user List instantiated to add user
        List<User> userList = new ArrayList<>();
        User NewUser = new User("dave", "2342", "davefile.csv", new ArrayList<>());
        userList.add(NewUser);


        // Admin List instantiated to add Admin
        // File Paths created to store reports
        List<Admin> adminList = new ArrayList<>();
        Admin NewAdmin = new Admin("andrew", "2468");
        adminList.add(NewAdmin);
        String AdminFileAv = "C:\\Users\\709887M2A\\nology\\LibrarySystem\\AdminReportAvailableBooks.csv";
        String AdminFileNotAv = "C:\\Users\\709887M2A\\nology\\LibrarySystem\\AdminReportUnavailableBooks.csv";

        // setting up scanner and logic for library //
        Scanner s = new Scanner(System.in);
        boolean libraryIsRunning = true;
        while (libraryIsRunning) {


            int person = 0;
            int Options =0;

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
                    userList.add(new User(username, login_password, username + "file.csv", new ArrayList<>()));
                    System.out.println("You have successfully created an account.You can now log in using the main page");
                    break;

                case 2:
                    for (User loggedUser : userList) {
                        int attempts = 0;
                        System.out.println("please enter your username correctly(case sensitive)");
                        String name = s.next();
                        System.out.println("");
                        System.out.println("Please enter your login_password(case-sensitive)");
                        String passCode = s.next();

                        while (attempts < 4) {
                            attempts++;
                            if (!loggedUser.getName().equals(name) || !loggedUser.getPassword().equals(passCode)) {
                                if (attempts == 3) {
                                    System.out.println("you have exceeded no. attempts");
                                    s.close();
                                    libraryIsRunning = false;
                                    break;
                                } else {
                                    System.out.println("wrong entry");
                                    System.out.println("please enter your AdminUsername correctly(case sensitive)");
                                    name = s.next();
                                    System.out.println("Please enter your login_password(case-sensitive)");
                                    passCode = s.next();}

                            } else break;
                        }

                        if (loggedUser.getName().equals(name) && loggedUser.getPassword().equals(passCode)) {
                            
                            boolean isAuthenticated = true;

                            do {
                                System.out.println("Please select the following options: " + loggedUser.getName() +
                                        "\n To Borrow a new book type : 1) " +
                                        "\n To view your loaned books type:2 " +
                                        "\n  To Return a book type: 3" +
                                        "\n  To exit menu type: 4");

                                Options = s.nextInt();

                                switch (Options) {

                                    case 1:
                                        BorrowABook(loggedUser, s, CsvFile);
                                        break;
                                    case 2:
                                        ViewYourBooksOnLoan(loggedUser);
                                        break;
                                    case 3:
                                        ReturnABook(loggedUser, s, CsvFile);
                                        break;
                                    case 4:
                                        isAuthenticated = false;
                                        break;
                                }

                            } while (isAuthenticated);
                        }
                    }

                    break;
                case 3:
                    for (Admin AdminUser : adminList) {
                        int attempts = 0;
                        System.out.println("please enter your AdminUsername correctly(case sensitive)");
                        String name = s.next();
                        System.out.println("");
                        System.out.println("Please enter your login_password(case-sensitive)");
                        String passCode = s.next();
                        System.out.println("");
                        while (attempts < 4) {
                            attempts++;
                            if (!AdminUser.getName().equals(name) || !AdminUser.getPassword().equals(passCode)) {
                                if (attempts == 3) {
                                    System.out.println("you have exceeded no. attempts");
                                    s.close();
                                    libraryIsRunning = false;
                                    break;
                                } else {
                                    System.out.println("wrong entry");
                                    System.out.println("please enter your AdminUsername correctly(case sensitive)");
                                    name = s.next();
                                    System.out.println("Please enter your login_password(case-sensitive)");
                                    passCode = s.next();}

                                } else break;
                        }

                        if (AdminUser.getName().equals(name) && AdminUser.getPassword().equals(passCode)) {

                            boolean isAuthorised = true;

                            do {
                                System.out.println("Please select the following options: " +
                                        "\n To view available books type:  1 " +
                                        "\n To view loaned books type: 2 " +
                                        "\n To create a report of available books type:  3 " +
                                        "\n To create a report of loaned books type:  4 +" +
                                        "\n To exit menu type: 5");

                                int select = s.nextInt();
                                System.out.println("");

                                switch (select) {
                                    case 1:
                                        ViewAllAvailableBooks(CsvFile, AdminUser);
                                        break;
                                    case 2:
                                        ViewAllBooksOnLoan(CsvFile, AdminUser);
                                        break;
                                    case 3:
                                        // read and store data before printing report
                                        CreateAdminReportAvailableBooks(CsvFile,AdminFileAv,AdminUser);
//                                        AdminUser.writingFileForAvailableBooks(BookList, AdminFileAv);
                                        break;
                                    case 4:
                                        // read and store data before printing report
                                        CreateAdminReportLoanedBooks(CsvFile,AdminFileNotAv,AdminUser);
//                                        AdminUser.writingFileForLoanedBooks(BookList, AdminFileNotAv);
                                        break;
                                    case 5:
                                        isAuthorised = false;
                                }

                            } while (isAuthorised);
                        }
                    }
                    break;
                default:
                    System.out.println("try again");;
            }
        }
    }

    private static void CreateAdminReportLoanedBooks(String CsvFile, String AdminFileNotAv, Admin AdminUser) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, CsvValidationException {
        List<Book> books = CsvReader.getBooksFromCsv(CsvFile);
        AdminUser.addBooksOnLoan(books);
        List<Book> LoanedBooks = AdminUser.getReportLoanedBooks();
        CsvReader.writeBooksToCsv(AdminFileNotAv,LoanedBooks);
    }

    private static void CreateAdminReportAvailableBooks(String CsvFile, String AdminFileAv, Admin AdminUser) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, CsvValidationException {
        List<Book> books = CsvReader.getBooksFromCsv(CsvFile);
        AdminUser.addAvailableBooks(books);
        List<Book> AvailableBooks = AdminUser.getReportAvailableBooks();
        CsvReader.writeBooksToCsv(AdminFileAv,AvailableBooks);
    }

    private static void ViewAllBooksOnLoan(String CsvFile, Admin AdminUser) throws CsvValidationException, IOException {
        List<Book> books = CsvReader.getBooksFromCsv(CsvFile);
        AdminUser.addBooksOnLoan(books);
        AdminUser.ListOfBooksOnLoanJSON();
    }

    private static void ViewAllAvailableBooks(String CsvFile, Admin AdminUser) throws CsvValidationException, IOException {
        List<Book> books = CsvReader.getBooksFromCsv(CsvFile);
        AdminUser.addAvailableBooks(books);
        AdminUser.ListOfAvailableBooksJSON();
    }

    private static void ViewYourBooksOnLoan(User loggedUser) throws CsvValidationException, IOException {
        // Read user file and add data onto array object before viewing user specific books on loan
        List<Book> books = CsvReader.getBooksFromCsv(loggedUser.getFileName());
        loggedUser.addBooks(books);

       // loggedUser.getBooksFromCsv(loggedUser.getFileName(), loggedUser);
        loggedUser.ListOfCurrentLoans();
    }

    private static void ReturnABook(User loggedUser,Scanner s, String CsvFile) throws CsvValidationException, IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        // Read user file and add data onto array object before returning user specific books currently on loan
        List<Book> books = CsvReader.getBooksFromCsv(loggedUser.getFileName());
        loggedUser.addBooks(books);
        //loggedUser.getBooksFromCsv(loggedUser.getFileName(), loggedUser);

        // Take user input
        System.out.println("Please type in the full name of the book you would like to return from the current list of borrowed books (case-sensitive)");
        String BorrowedBookTitle = s.nextLine();
        // Method to Loan a book and update arrays objects of general bookList and user specific books
        loggedUser.ReturnABook(books, loggedUser, BorrowedBookTitle);
        // Method to update changes in user specific files // overwriting user data
        loggedUser.OpenCsvMethodToWriteUserBooksOnLoan(loggedUser);
        //Method to update changes in the general bookList files  // overwriting general original data
        OpenCsvtoWrtieAndStoreData(CsvFile, books);
    }

    private static void BorrowABook(User loggedUser, Scanner s, String CsvFile) throws CsvValidationException, IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        // Reads user file and add data onto array object
        List<Book> books = CsvReader.getBooksFromCsv(loggedUser.getFileName());
        loggedUser.addBooks(books);

       // loggedUser.getBooksFromCsv(loggedUser.getFileName(), loggedUser);
        // Take user input
        System.out.println("Please type in the full name of the book you would like to loan from the available list (case-sensitive)");
        String BookTitle = s.next();
        System.out.println(BookTitle);

        // Method to Loan a book and add update arrays objects of general bookList and user specific books
        loggedUser.LoanABook(books, loggedUser, BookTitle);
        // Method to enable data persistence overwriting
        List<Book> LoanedBooks = loggedUser.getBooksOnLoan();
        CsvReader.writeBooksToCsv(loggedUser.getFileName(),LoanedBooks);
//        loggedUser.OpenCsvMethodToWriteUserBooksOnLoan(loggedUser);
        //Method to update changes in the general bookList files// Data persistence
        List<Book> MainBookList = loggedUser.getBooks();
        CsvReader.writeBooksToCsv(CsvFile,MainBookList);
//        OpenCsvtoWrtieAndStoreData(CsvFile, books);
        System.out.println(s.nextLine());
    }


    private static void OpenCsvtoWrtieAndStoreData(String CsvFile, List<Book> BookList) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        File file = new File(CsvFile);
        Writer writer = new FileWriter(file);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(BookList);
        System.out.println("File has been created");
        writer.close();

    }

    private static void OpenCsvToReadAndStoreData(String FilePath, List<Book> BookList) throws IOException, CsvValidationException {

        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').withQuoteChar('\"').build();

        // Reading file //

        CSVReader reader = new CSVReaderBuilder(new FileReader(FilePath)).withCSVParser(csvParser).build();

        // Line values for book rows //
        String[] Line;
        while ((Line = reader.readNext()) != null) {
            BookList.add(new Book(Line[0], Line[1], Line[2], Line[3], Line[4], Line[5], Line[6], Line[7]));
        }
    }
}


