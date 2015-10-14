/*
 * Author: Jules de Courtenay
 * Description: A program which takes txt file input and creates a Mad-Lib story out of it
 * Version, Date: V 1.0, 11/10/15
 */

package programming.task.pkg3.madlib;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class Main extends JFrame implements ActionListener{

    JTextField sntcePathTxt;
    JTextField verbsPathTxt;
    JTextField nounsPathTxt;
    JTextArea outputTxt;
    
    JScrollPane scroller;
    
    JLabel sntceLBL;
    JLabel verbsLBL;
    JLabel nounsLBL;
    JLabel outputLBL;
    
    JPanel sntcePane;
    JPanel wordsPane;
    JPanel innerWordsPane1;
    JPanel innerWordsPane2;
    JPanel outputPane;
    JPanel buttonPane;
    
    Container c;
    
    JButton okayBTN;
    JButton exitBTN;
    
    Color cl;
    
    public static void main(String[] args) {
        Main m = new Main();
        m.createGUI();
        m.setVisible(true);
        
        
    }
        
    @Override
    public void actionPerformed(ActionEvent e){
        Object btn = e.getSource();
        if(btn == exitBTN){
            System.exit(0);
        }else{ if (btn == okayBTN){
            if(sntcePathTxt.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You need to enter a correct path to the sentence text box.", "Error!", JOptionPane.ERROR_MESSAGE);
            }else{ if (nounsPathTxt.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You need to enter a correct path to the nouns text box.", "Error!", JOptionPane.ERROR_MESSAGE);
            }else{ if (verbsPathTxt.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You need to enter a correct path to the verbs text box.", "Error!", JOptionPane.ERROR_MESSAGE);
            }else{
                FileManager running = new FileManager(sntcePathTxt.getText(), nounsPathTxt.getText(), verbsPathTxt.getText());
                outputTxt.setText(running.createFinalSentence());
            }}}}
        }
    }
    
    private void createGUI(){
        setSize(400,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Mad-Libs, bro!");
        setBackground(Color.CYAN);
        
        c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        
        cl = new Color(123,113,255);
        sntcePane = new JPanel();
        sntcePane.setBorder(BorderFactory.createTitledBorder(null, "Mad-Lib Sentences!", TitledBorder.CENTER, WIDTH, new Font("Calibri", Font.BOLD, 15), Color.BLACK));
        sntcePane.setBackground(cl);
        sntceLBL = new JLabel("Enter the path to the Sentence file:");
        sntcePathTxt = new JTextField(15);
        sntcePane.add(sntceLBL);
        sntcePane.add(sntcePathTxt);
        
        wordsPane = new JPanel();
        wordsPane.setBorder(BorderFactory.createTitledBorder("For the verbs and nouns"));
        wordsPane.setBackground(cl);
        wordsPane.setLayout(new GridLayout(2,1));
        innerWordsPane1 = new JPanel();
        innerWordsPane1.setBackground(cl);
        innerWordsPane2 = new JPanel();
        innerWordsPane2.setBackground(cl);
        nounsLBL = new JLabel("Enter the path to the nouns file:");
        verbsLBL = new JLabel("Enter the path to the verbs file:");
        nounsPathTxt = new JTextField(15);
        verbsPathTxt = new JTextField(15);
        wordsPane.add(innerWordsPane1);
        wordsPane.add(innerWordsPane2);
        innerWordsPane1.add(nounsLBL);
        innerWordsPane2.add(verbsLBL);
        innerWordsPane1.add(nounsPathTxt);
        innerWordsPane2.add(verbsPathTxt);
        
        outputPane = new JPanel();
        outputPane.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPane.setBackground(cl);
        outputLBL = new JLabel("Click Okay once the paths are in.");
        outputTxt = new JTextArea(2,30);
        scroller = new JScrollPane(outputTxt);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //outputTxt.setEditable(false);
        outputPane.add(outputLBL);
        outputPane.add(outputTxt);
        
        buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(1,5));
        buttonPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buttonPane.setBackground(cl);
        okayBTN = new JButton("Okay!");
        okayBTN.addActionListener(this);
        exitBTN = new JButton("Exit");
        exitBTN.addActionListener(this);
        buttonPane.add(okayBTN);
        buttonPane.add(exitBTN);
        
        c.add(sntcePane);
        c.add(wordsPane);
        c.add(outputPane);
        c.add(buttonPane);
    }
}
