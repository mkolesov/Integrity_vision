package app;

import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created by Kolesov on 1/26/2018.
 */
public class FileReader {

    private String rootAdress = "src/main/resources/";
    private ArrayList<String> wordList;
    private HashMap<String, Set<String>> containsWord;
    private Set<String> concatenatedWords;
    private String longestWord = "";
    private String secondLongestWord = "";
    private Set<String> set;

    public void process(String fileName){
        wordList = readFromFile(rootAdress+fileName);
        Iterator<String> iterator = wordList.iterator();
        containsWord = new HashMap<String, Set<String>>();
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
        getRes();
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
                //
                if(!containsWord.containsKey(checkWord)){
                    HashSet <String> newSet = new HashSet<String>();
                    newSet.add(word);
                    containsWord.put(checkWord, newSet);
                } else {
                    containsWord.get(checkWord).add(word);
                }


                //Если в списке кандидатов уже есть это слово
//                if(containsWord.containsKey(checkWord)){
//                    String createrWord = containsWord.get(checkWord);
//                    //Проверка является заданное слово сокоренным кандидату или составным
//                    if (!word.contains(createrWord)& !createrWord.contains(word)){
//                        if (!concatenatedWords.contains(checkWord)){
//                            checkLenght(checkWord);
//                            concatenatedWords.add(checkWord);
//
//                        }
//                    }
//                } else {
//                    containsWord.put(checkWord, word);
//                }
            }
        }
    }

    private boolean rec(String word, String concat, int index){

        Iterator<String> setIterator1 = set.iterator();

        while ( setIterator1.hasNext()) {
            String s = setIterator1.next();
            if (word.indexOf(s, index) == index) {
                concat = concat.concat(s);
                if (word.equals(concat)) {
                    concatenatedWords.add(word);
                    checkLenght(word);
                    return true;
                }
                if (rec(word, concat, index + s.length())) {
                    return true;
                } else {
                    concat = concat.substring(0, index);
                }
            }

//            if (word.indexOf(s, index)<0){
//                continue;
//            }
        }
        return false;
    }

    private void getRes(){
        Iterator<String> mapIterator = containsWord.keySet().iterator();
        while (mapIterator.hasNext()) {
            String key = mapIterator.next();
            set = containsWord.get(key);
            rec(key, "", 0);
//            Set<String> set = containsWord.get(key);
//            Iterator<String> setIterator1 = set.iterator();
//
//            //new 2
//            while ( setIterator1.hasNext()) {
//                String s = setIterator1.next();


            //new stuff
            //if (key.equals("catsdogcat")){
//            if (key.equals("electroencephalographically")){
//                System.out.println("GOT YOu");
//            };
//            int [] mass = new int[key.length()];
//            Arrays.fill(mass, 0);
//            while ( setIterator1.hasNext()) {
//                String s = setIterator1.next();
//
//
//                int index = key.indexOf(s);
//                while (index>-1){
//                    Arrays.fill(mass, index, index+s.length(), 1);
//                    index = key.indexOf(s, index+s.length());
//                }
//
//
//            }
//
//            Arrays.sort(mass);
//            if (Arrays.binarySearch(mass, 0)<0){
//                concatenatedWords.add(key);
//                checkLenght(key);
//            }

            //old
//            List<String> TESTset = new ArrayList<String>();
//            while ( setIterator1.hasNext()) {
//                String word1 = setIterator1.next();
//                Iterator<String> setIterator2 = set.iterator();
//                boolean isInherit = false;
//                while (setIterator2.hasNext()) {
//                    String word2 = setIterator2.next();
//                    if (word2.contains(word1) & !word2.equals(word1) &
//                            key.indexOf(word1) == key.indexOf(word2)) {
//                        isInherit = true;
//                        break;
//                    }
//                }
//                if (!isInherit){
//                    TESTset.add(word1);
//                }
//            }
//            Comparator<String> my = new MyComparator();
//            Collections.sort(TESTset, my);
//            String tmp = key;
//            for (String s:TESTset){
//                tmp = tmp.replace(s, "");
//            }
//            if(tmp.length()==0){
//                concatenatedWords.add(key);
//                checkLenght(key);
//            }






//             if (key.equals("ethylenediaminetetraacetates")){
//                System.out.println("GOT YOu");
//            };
//            if(TESTset.size()>1){
//                concatenatedWords.add(key);
//                checkLenght(key);
//            }
        }

//            while ( setIterator1.hasNext()){
//                String word1 = setIterator1.next();
//                Iterator<String> setIterator2 = set.iterator();
//                boolean isInherit = false;
//                while ( setIterator2.hasNext()){
//                    String word2 = setIterator2.next();
//                    if (word2.contains(word1) & !word2.equals(word1)&
//                            key.indexOf(word1)==key.indexOf(word2)){
//                        isInherit = true;
//                        break;
//                    }
//                }
//                if (!isInherit){
//                    i++;
//                }
//                if (i>1){
//                    concatenatedWords.add(key);
//                    containsWord2.put(key, set);
//                }
//            }
//            System.out.println("test");
//        }
    }

    private ArrayList<String> readFromFile(String fileAdress){
        ArrayList<String> result = new ArrayList<String>();
        System.out.println("Reading file....");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileAdress, "r");
            String word = randomAccessFile.readLine();
            while (word != null && word.length()>0){
                result.add(word);
                word = randomAccessFile.readLine();
//                if(word.length()==27){
//                    System.out.println(27);
//                }
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
