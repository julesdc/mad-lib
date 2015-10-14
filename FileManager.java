package programming.task.pkg3.madlib;

/*
 * Author: Jules de Courtenay
 * Description: A class which imports the File, FileReader, PrintWriter
 * Version, Date: V 1.0, 12/10/15
 */

/**
 *
 * @author Jules
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class FileManager {

    private final String sentence, noun, verb;
    private final CharSequence percent = "%", hash = "#";
//constructor
    public FileManager(String sentence, String noun, String verb){
        this.sentence = sentence;
        this.noun = noun;
        this.verb = verb;
    }
    
    //method to return the final sentence with random verbs and nouns
    public String createFinalSentence(){
        String finalSentence;
        if(getInitialSentence() != null){
            finalSentence = getInitialSentence();            
            finalSentence = fitNoun(finalSentence);
            finalSentence = fitVerb(finalSentence);
            return finalSentence;
        }else{
            return "An error has occured with input. Please try again.";
        }
    }
    
    //repalces the % in the sentence with a random verb
    public String fitVerb(String sentence){
        return sentence.replaceAll("[%]", getRandomWord(readVerbFile()));
    }
    
    //replaces the # in the sentence with a random noun
    public String fitNoun(String sentence){
        return sentence.replaceAll("[#]", getRandomWord(readNounFile()));
    }
    
    //returns a word from the specified noun or verb file
    public String getRandomWord(FileReader fr){
        ArrayList<String> words = new ArrayList<>();        
        Scanner s = new Scanner(fr);
        while (s.hasNextLine()){
            words.add(s.nextLine());            
        }     
        int rnd = new Random().nextInt(words.size());
        return words.get(rnd);
    }
        
    //method which returns the sentence in the file entered by the user (String sentence). It will contain a # and a %
    public String getInitialSentence(){
        if (fileRead(sentence) != null){
            if (sentence.contains(percent) && sentence.contains(hash)){
                FileReader fr = fileRead(sentence);
                Scanner s = new Scanner(fr);
                return s.nextLine();
            }else{
                JOptionPane.showMessageDialog(null,  "Sentence does not contain #s or %s. Error 2005", "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }else{
            JOptionPane.showMessageDialog(null,  "Can't read sentence file. Error 2004", "Error!", JOptionPane.ERROR_MESSAGE);
            return null;   
        }
    }
    
    //returns the entire string of the noun file
    public FileReader readNounFile(){
        if (fileRead(noun) != null){
            return fileRead(noun);
        }else{
            JOptionPane.showMessageDialog(null,  "Can't read noun file. Error 2003", "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    //returns the entire string of the verb file
    public FileReader readVerbFile(){
        if (fileRead(verb) != null){
            return fileRead(verb);
        }else{
            JOptionPane.showMessageDialog(null,  "Can't read verb file. Error 2002", "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    //returns a fileReader object of the specifed file
    public FileReader fileRead(String s){
        File f = new File(s);
        try{
            FileReader fr = new FileReader(f);
            if (new Scanner(fr).hasNextLine()){
                return fr;
            }else{
                JOptionPane.showMessageDialog(null,  "File Empty. Error 2000", "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }catch(FileNotFoundException e){
            return null;
        }
    }
    
    //writes a specified string to a specifed file. Writing appends and does not use flush
    public void fileWrite(String path, String write){
        try{
            PrintWriter pr = new PrintWriter(new FileWriter(new File(path), true), false);
            pr.println(write);
            pr.close();
        }catch(IOException e){
            System.out.println("File not found. Error 2001.");
        }
        
    }
}
