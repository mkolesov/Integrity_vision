package app;

/**
 * Created by Kolesov on 1/26/2018.
 */
public class myApp {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        fileReader.process("words.txt");
        //fileReader.process("test.txt");
        System.out.println("Longest concateneted word: "+fileReader.getLongestWord());
        System.out.println("Second longest concateneted word: "+fileReader.getSecondLongestWord());
        System.out.println("Count of concateneted words: "+fileReader.getConcatenetedWordsCount());
    }

}
