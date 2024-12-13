package Vocabulary;

import java.util.List;

public class VocabularyAutomation {
    public static void main(String[] args) {
    	
    	

        try {
            VocabularyProcessor processor = new VocabularyProcessor();
            List<VocabularyProcessor.Vocabulary> unprocessedWords = processor.getUnprocessedWords(5);

            if (unprocessedWords.isEmpty()) {
                System.out.println("All words are already processed.");
                return;
            }

            System.out.println("Processing words and meanings:");
            StringBuilder emailContent = new StringBuilder();

            for (VocabularyProcessor.Vocabulary vocab : unprocessedWords) {
                System.out.println(vocab); // Print word and meaning
                emailContent.append(vocab).append("\n"); // Append to email content
            }

            // Mark words as processed
            processor.markAsProcessed(unprocessedWords);
            System.out.println("Batch processed successfully.");

            // Send email with processed words and meanings
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    



}
