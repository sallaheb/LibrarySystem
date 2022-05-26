package com.nology;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<Book> getBooksFromCsv(String fileName) throws IOException, CsvValidationException {

        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').withQuoteChar('\"').build();

        // Reading file //
        CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withCSVParser(csvParser).build();

        // Line values for book rows //
        String[] Line;
        List<Book> books = new ArrayList<>();

        while ((Line = reader.readNext()) != null) {
            //user.getBooksOnLoan().add(new Book(Line[0],Line[1],Line[2],Line[3],Line[4],Line[5],Line[6],Line[7]));
            books.add(new Book(Line[0],Line[1],Line[2],Line[3],Line[4],Line[5],Line[6],Line[7]));
        }
       // Remove header without using skip on CSV reader
        books.removeIf(book -> book.getTitle().equals("Number"));

        return books;
    }

    public static List<Book> writeBooksToCsv(String File, List<Book> books) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        File file = new File(File);
        Writer writer = new FileWriter(file);

        HeaderColumnNameMappingStrategy<Book> strategy = new HeaderColumnNameMappingStrategyBuilder<Book>().build();
        strategy.setType(Book.class);
        strategy.setColumnOrderOnWrite(new ComparableComparator());

        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
        beanToCsv.write(books);
        System.out.println("File has been created");
        writer.close();

        return books;
    }
}
