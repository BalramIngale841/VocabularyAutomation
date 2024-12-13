package Vocabulary;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VocabularyProcessor {

   
    private static final String FILE_PATH = System.getenv("WORKSPACE") + "/Vocabulary.xlsx";


    // Class to store word and meaning
    public static class Vocabulary {
        private String word;
        private String meaning;

        public Vocabulary(String word, String meaning) {
            this.word = word;
            this.meaning = meaning;
        }

        public String getWord() {
            return word;
        }

        public String getMeaning() {
            return meaning;
        }

        @Override
        public String toString() {
            return word + " - " + meaning;
        }
    }

    // Method to fetch next batch of unprocessed words
    public List<Vocabulary> getUnprocessedWords(int batchSize) throws Exception {
        FileInputStream fis = new FileInputStream(FILE_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        List<Vocabulary> vocabList = new ArrayList<>();
        int count = 0;

        for (Row row : sheet) {
            Cell statusCell = row.getCell(2); // Status column
            if (statusCell == null || statusCell.getStringCellValue().isEmpty()) {
                String word = row.getCell(0).getStringCellValue(); // Word column
                String meaning = row.getCell(1).getStringCellValue(); // Meaning column
                vocabList.add(new Vocabulary(word, meaning));
                count++;
                if (count == batchSize) break; // Stop after batchSize words
            }
        }
        workbook.close();
        fis.close();

        return vocabList;
    }

    // Method to update processed status in Excel
    public void markAsProcessed(List<Vocabulary> processedWords) throws Exception {
        FileInputStream fis = new FileInputStream(FILE_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            String word = row.getCell(0).getStringCellValue(); // Word column
            for (Vocabulary vocab : processedWords) {
                if (vocab.getWord().equals(word)) {
                    Cell statusCell = row.createCell(2, CellType.STRING);
                    statusCell.setCellValue("Processed");
                }
            }
        }

        fis.close();

        FileOutputStream fos = new FileOutputStream(FILE_PATH);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    
}
