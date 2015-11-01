/*
 * Author: Jules de Courtenay
 * Description: Gives the user the ability to create their own skeleton sentences.
 * Version: V 1.0
 * Date: 25/10/15
 */

package HandIn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateNewFiles extends JFrame{
    //instance variables
    JLabel titleLbl;
    JLabel sentLbl;
    
    Font titleFont;
    Font smallFont;

    JTextArea sentTxt;
    JTextArea nounTxt;
    JTextArea verbTxt;
    
    JScrollPane sentScroller;
    JScrollPane nounScroller;
    JScrollPane verbScroller;
    
    JPanel northPane;
    JPanel centrePane;
    JPanel nounPane;
    JPanel verbPane;
    JPanel southPane;
    JPanel southClosePane;
    JPanel southCreatePane;
    
    JButton closeButton;
    JButton createButton;
    
    String sentence;
    
    private final CharSequence percent = "%", hash = "#";
    //constructor
    public CreateNewFiles(){
        setTitle("Create New Files");
        setSize(300,350);
        setLocationRelativeTo(null);
        createGUI();
        setVisible(true);
    }

    //methods
    
    private void createGUI(){
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        titleFont = new Font("Calibri", Font.BOLD, 20);
        smallFont = new Font("Calibri", Font.PLAIN, 12);
        
        northPane = new JPanel();
        northPane.setLayout(new FlowLayout());

        titleLbl = new JLabel("Enter New Sentence and Words");
        titleLbl.setFont(titleFont);
        northPane.add(titleLbl);
        c.add(northPane, BorderLayout.NORTH);
        
        southPane = new JPanel();
        southPane.setLayout(new GridLayout(1,3));
        createButton = new CreateButton("Create");
        southCreatePane = new JPanel();
        southCreatePane.setLayout(new FlowLayout());
        southCreatePane.add(createButton);
        southPane.add(southCreatePane);
        southPane.add(new JLabel());
        southClosePane = new JPanel();
        southClosePane.setLayout(new FlowLayout());
        closeButton = new CloseButton("Close");
        southClosePane.add(closeButton);
        southPane.add(southClosePane);
        c.add(southPane, BorderLayout.SOUTH);
        
        centrePane = new JPanel();
        centrePane.setLayout(new GridLayout(2,2,5,10));
        sentLbl = new JLabel("<html>Enter a new MadLib sentence here: (must contain at least one # (nouns) and one % (verbs))</html>");
        sentLbl.setFont(smallFont);
        centrePane.add(sentLbl);
        sentTxt = new JTextArea(3,10);
        sentTxt.setLineWrap(true);
        sentTxt.setWrapStyleWord(true);
        sentScroller = new JScrollPane(sentTxt);
        centrePane.add(sentScroller);
        nounTxt = new JTextArea(4,9);
        nounScroller = new JScrollPane(nounTxt);
        nounPane = new JPanel();
        nounPane.setBorder(BorderFactory.createTitledBorder("Nouns (1/line)"));
        nounPane.add(nounScroller);
        verbTxt = new JTextArea(4,9);
        verbScroller = new JScrollPane(verbTxt);
        verbPane = new JPanel();
        verbPane.setBorder(BorderFactory.createTitledBorder("Verbs (1/line)"));
        verbPane.add(verbScroller);
        centrePane.add(nounPane);
        centrePane.add(verbPane);
        c.add(centrePane, BorderLayout.CENTER);
        
        c.add(new JLabel("  "), BorderLayout.WEST);
        c.add(new JLabel("  "), BorderLayout.EAST);
    }    
    
    public void fileWrite(){
        String nounString = nounTxt.getText();
        String verbString = verbTxt.getText();
        String nouns[] = nounString.split("\n");
        String verbs[] = verbString.split("\n");
        /*
        writes a specified string to a specifed file. Writing appends and does not use flush    
        */
        try{
            PrintWriter sentencePr = new PrintWriter(new FileWriter(new File("sentence.txt"), false), false);
            PrintWriter nounPr = new PrintWriter(new FileWriter(new File("nouns.txt"), true), false);
            PrintWriter verbPr = new PrintWriter(new FileWriter(new File("verbs.txt"), true), false);
            sentencePr.println(sentence);
            for (String noun : nouns) {
                nounPr.println(noun);
            }
            for (String verb : verbs) {
                verbPr.println(verb);
            }
            sentencePr.close();
            nounPr.close();
            verbPr.close();
            JOptionPane.showMessageDialog(this, "You have successfully created a new mad-lib skeleton. Find the files where this program is saved to create their mad-libs.", "Success", JOptionPane.INFORMATION_MESSAGE);
            close();
        }catch(IOException e){
            System.out.println("Error writing to file. Error 2001.");
        }
    }
    
    public class CreateButton extends JButton implements ActionListener{
        public CreateButton(String title){
            setText(title);
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            sentence = sentTxt.getText();
            if(!sentence.equals("") && !nounTxt.getText().equals("") && !verbTxt.getText().equals("")){
                if(sentence.contains(hash) && sentence.contains(percent)){
                                fileWrite();
                }else{
                    JOptionPane.showMessageDialog(this, "Please enter a sentence that contains at least one # and one %. Error 1002", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Please enter data in all fields. Error 1001", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class CloseButton extends JButton implements ActionListener{
        public CloseButton(String title){
            setText(title);
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            close();
        }
    }
    
    public void close(){
        setVisible(false);
    }
}
