package app;

import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created by Kolesov on 1/26/2018.
 */
public class FileReader {

    private String rootAdress = "src/main/resources/";
    private ArrayList<String> wordList;
    private HashMap<String, String> containsWord;;
    private Set<String> concatenatedWords;
    private String longestWord = "";
    private String secondLongestWord = "";

    public void process(String fileName){
        wordList = readFromFile(rootAdress+fileName);
        Iterator<String> iterator = wordList.iterator();
        containsWord = new HashMap<String, String>();
        concatenatedWords = new HashSet<String>();
        System.out.println("Starting processing....");
        int i = 0;
        int currentPercent = 0;
        int percent = wordList.size()/100;
        System.out.print("Current progress: "+currentPercent+"% ");
        while (iterator.hasNext()){
            processWord(iterator.next());
            i++;
            if (i == percent){
                currentPercent++;
                System.out.print(currentPercent+"% ");
                i=0;
            }
        }
        System.out.println("Finished.");
    }

    private void checkLenght(String word){
        if (word.length() > secondLongestWord.length()){
            secondLongestWord = word;
        }
        if (secondLongestWord.length() > longestWord.length()){
            String tmp = longestWord;
            longestWord = secondLongestWord;
            secondLongestWord = tmp;
        }
    }

    private void processWord(String word){
        Iterator<String> secondIterator = wordList.iterator();
        while (secondIterator.hasNext()){
            String checkWord = secondIterator.next();
            //Если проверяемое слово содержит заданное слово и не является им
            if (checkWord.contains(word) & !checkWord.equals(word)){
                //Если в списке кандидатов уже есть это слово
                if(containsWord.containsKey(checkWord)){
                    String createrWord = containsWord.get(checkWord);
                    //Проверка является заданное слово сокоренным кандидату или составным
                    if (!word.contains(createrWord)& !createrWord.contains(word)){
                        if (!concatenatedWords.contains(checkWord)){
                            checkLenght(checkWord);
                            concatenatedWords.add(checkWord);

                        }
                    }
                } else {
                    containsWord.put(checkWord, word);
                }
            }
        }
    }

    private ArrayList<String> readFromFile(String fileAdress){
        ArrayList<String> result = new ArrayList<String>();
        System.out.println("Reading file....");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileAdress, "r");
            String word = randomAccessFile.readLine();
            while (word != null){
                result.add(word);
                word = randomAccessFile.readLine();
            }
            randomAccessFile.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getLongestWord() {
        return longestWord;
    }

    public String getSecondLongestWord() {
        return secondLongestWord;
    }

    public int getConcatenetedWordsCount(){
        return concatenatedWords.size();
    }
}
