package app;

import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created by Kolesov on 1/26/2018.
 */
public class FileReader {

    private String rootAdress = "src/main/resources/";
    private ArrayList<String> wordList;
    private HashMap<String, Set<String>> containsWordInside;
    private Set<String> concatenatedWords;
    private String longestWord = "";
    private String secondLongestWord = "";
    private Set<String> currentWordInnerSet;

    public void process(String fileName){
        wordList = readFromFile(rootAdress+fileName);
        processWordsList();
    }

    private void processWordsList(){
        System.out.println("Processing:");
        System.out.println("    -Finding words usages:");
        Iterator<String> iterator = wordList.iterator();
        containsWordInside = new HashMap<String, Set<String>>();
        int i = 0;
        int currentPercent = 0;
        int percent = wordList.size()/100;
        System.out.print("      Current progress: "+currentPercent+"% ");
        while (iterator.hasNext()){
            findWordUsage(iterator.next());
            i++;
            if (i == percent){
                currentPercent++;
                System.out.print(currentPercent+"% ");
                i=0;
            }
        }
        System.out.println("Finished.");
        System.out.print("  -Finding concateneted words.....");
        testConcatenetion();
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

    private void findWordUsage(String word){
        /*
        * Тут можно было бы ускорить поиск
        * отсортировав по длине слов весь список от малых к большим
        * а затем искать только в возрастающих по длине
        * но уже так оставлю, со старта не догадался ;)
        * */
        Iterator<String> secondIterator = wordList.iterator();
        while (secondIterator.hasNext()){
            String checkWord = secondIterator.next();
            //Если проверяемое слово содержит заданное слово и не является им
            if (checkWord.contains(word) & !checkWord.equals(word)){
                //Если слова нет в списке кандидатов - добавляем его и список слов в нем
                if(!containsWordInside.containsKey(checkWord)){
                    HashSet <String> newSet = new HashSet<String>();
                    newSet.add(word);
                    containsWordInside.put(checkWord, newSet);
                }
                //Если есть, то пополняем список
                else {
                    containsWordInside.get(checkWord).add(word);
                }
            }
        }
    }

    private boolean recursive(String word, String concatenatedWord, int index){
        Iterator<String> setIterator = currentWordInnerSet.iterator();
        while ( setIterator.hasNext()) {
            String currentTestingWord = setIterator.next();
            //Если текущее слово находится по заданному индексу
            if (word.indexOf(currentTestingWord, index) == index) {
                concatenatedWord = concatenatedWord.concat(currentTestingWord);
                if (word.equals(concatenatedWord)) {
                    concatenatedWords.add(word);
                    checkLenght(word);
                    return true;
                }
                if (recursive(word, concatenatedWord, index + currentTestingWord.length())) {
                    return true;
                } else {
                    concatenatedWord = concatenatedWord.substring(0, index);
                }
            }
        }
        return false;
    }

    private void testConcatenetion(){
        concatenatedWords = new HashSet<String>();
        Iterator<String> mapIterator = containsWordInside.keySet().iterator();
        while (mapIterator.hasNext()) {
            String key = mapIterator.next();
            currentWordInnerSet = containsWordInside.get(key);
            recursive(key, "", 0);
        }
    }

    private ArrayList<String> readFromFile(String fileAdress){
        ArrayList<String> result = new ArrayList<String>();
        System.out.print("Reading file.......");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileAdress, "r");
            String word = randomAccessFile.readLine();
            while (word != null && word.length()>0){
                result.add(word);
                word = randomAccessFile.readLine();
            }
            randomAccessFile.close();
            System.out.println("Finished.");
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
