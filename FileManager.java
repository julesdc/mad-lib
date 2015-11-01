package HandIn;

/*
 * Author: Jules de Courtenay
 * Description: A class which is constructed by four file paths and produces a mad-lib sentence. 
 * The sentence can be written to a text file.
 * Version, Date: V 1.0, 12/10/15
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class FileManager {
    
    //instance variables
    private final String sentence, noun, verb, output;
    private final CharSequence percent = "%", hash = "#";
    
    //constructor
    public FileManager(String sentence, String noun, String verb, String output){
        this.sentence = sentence;
        this.noun = noun;
        this.verb = verb;
        this.output = output;
    }
    
    public String createFinalSentence() {
        /*
        the action method of this class. replaces the sentence from the sentence file with
        the finalized mad-lib sentence. Every time there is an error in any method they will return null,
        the null value will come up the hierarchy to this method where it is finally handled with the
        return statement
        */
        String finalSentence;
        if(getInitialSentence() != null){
            finalSentence = getInitialSentence();            
            finalSentence = fitWord(finalSentence, "#", noun, "noun");
            finalSentence = fitWord(finalSentence, "%", verb, "verb");
            return finalSentence;
        }else{
            return "An error has occured with input. Please try again.";
        }
    }
    
    public String fitWord(String sentence, String character, String path, String name){
        /*
        replaces all instances of "regex" in "sentence" with random words taken from the 
        file at "path", producing an error message of "name"
        */ 
        String newStr[] = sentence.split(character);
        String returnString = newStr[0];
        for(int i = 1; i < newStr.length; i++){
            returnString += getRandomWord(readWordFile(path,name)) + newStr[i];
        }
        return returnString;
    }
    
    public String getRandomWord(File f){
        /*
        picks one random word from the specfied file and returns it.
        */
        ArrayList<String> words = new ArrayList<>(); 
        try{
            Scanner s = new Scanner(f);
            while (s.hasNextLine()){
                words.add(s.nextLine());            
            }     
            int rnd = new Random().nextInt(words.size());
            s.close();
            return words.get(rnd);
        }catch(FileNotFoundException e){
            //The word file cannot be found so can't make a scanner of it.
            JOptionPane.showMessageDialog(null,  "Can't read word file. Error 2006", "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
        
    //method which returns the sentence in the file entered by the user (String sentence). It will contain a # and a %
    public String getInitialSentence(){
        /*
        returns a line of text taken from the sentence file (see constructor).
        this sentence is checked to contain both # and % so that it can be a proper mad-lib sentence
        */
        File sentenceFile = readFile(sentence);
        if (sentenceFile != null){
            try{
                Scanner s = new Scanner(sentenceFile);
                String sent = s.nextLine();
                if (sent.contains(percent) || sent.contains(hash)){
                    s.close();
                    return sent;
                }else{
                    //doesn't contain # or %
                    JOptionPane.showMessageDialog(null,  "Sentence does not contain #s or %s. Error 2005", "Error!", JOptionPane.ERROR_MESSAGE);
                    s.close();
                    return null;
                }
            }catch(FileNotFoundException e){
                //can't find the sentence file
                JOptionPane.showMessageDialog(null,  "Can't read sentence file. Error 2004-a", "Error!", JOptionPane.ERROR_MESSAGE);
                return null;  
            }
        }else{
            //see readFile() method and find "return null;"
            JOptionPane.showMessageDialog(null,  "Can't read sentence file. Error 2004-b", "Error!", JOptionPane.ERROR_MESSAGE);
            return null;   
        }
    }
    
    public File readWordFile(String path, String name){
        /*
        returns a file object if the file exists at the specified path. This will be used for the noun or verb files
        */
        if (readFile(path) != null){
            return readFile(path);
        }else{
            //tells the user which file was input incorrectly
            JOptionPane.showMessageDialog(null,  "Can't read " + name + " file. Error 2003", "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public File readFile(String s){
        /*
        returns a File object of the file at the specified file path
        */
        File f = new File(s);
        try{
            Scanner fileScanner = new Scanner(f);
            if (fileScanner.hasNextLine()){
                fileScanner.close();
                return f;
            }else{
                //the file is empty
                fileScanner.close();
                JOptionPane.showMessageDialog(null,  "File Empty. Error 2000", "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }catch(FileNotFoundException e){
            //can't find the file
            return null;
        }
    }
    
    public void fileWrite(String write){
        /*
        writes a specified string to a specifed file. Writing appends and does not use flush    
        */
        try{
            PrintWriter pr = new PrintWriter(new FileWriter(new File(output), true), false);
            pr.println(write);
            pr.close();
        }catch(IOException e){
            //thrown by printWriter object instantiation and .close() method
            System.out.println("Error writing to file. Error 2001.");
        }
        
    }
}
