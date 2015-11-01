/*
 * Author: Jules de Courtenay
 * Description: A program which takes txt file input and creates a Mad-Lib story out of it
 * Version, Date: V 1.0, 11/10/15
 */

package HandIn;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Jules
 */
public class NewMain extends JFrame{

    FileManager fm;
    String sentenceFilePath;
    String nounFilePath;
    String verbFilePath;
    String outputFilePath;
    
    JPanel northPanel;
    JPanel southPanel;
    JPanel eastPanel;
    JPanel westPanel;
    JPanel centerPanel;
    
    JLabel titleLabel;
    JLabel sentenceTick;
    JLabel nounTick;
    JLabel verbTick;
    JLabel outputTick;
    
    Font titleFont;
    
    JButton exitButton;
    JButton okayButton;
    JButton sentenceButton;
    JButton nounButton;
    JButton verbButton;
    JButton outputButton;
    JButton createNewFilesButton;
    
    JTextArea outputArea;
    JScrollPane scroller;
    
    JFileChooser sfc;
    JFileChooser nfc;
    JFileChooser vfc;
    
    public static void main(String[] args) {
        NewMain main = new NewMain();
        main.sfc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Notepad Files only", "txt");
        main.sfc.addChoosableFileFilter(filter);
        main.createGUI();
        main.setVisible(true);
    }
    
    public void createGUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450,300);
        setLocationRelativeTo(null);        
        setTitle("Mad-Lib");
        
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
 
        //c.add(Container, BorderLayout.CENTER);
        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        titleFont = new Font("Calibri", Font.BOLD, 20);
        titleLabel = new JLabel("Produce a Mad-Lib Sentence!");
        titleLabel.setFont(titleFont);
        northPanel.add(titleLabel);
        c.add(northPanel, BorderLayout.NORTH);
        
        westPanel = new JPanel();
        c.add(westPanel, BorderLayout.WEST);
        
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        createNewFilesButton = new CreateNewFilesButton("Create New Files");
        okayButton = new OkayButton("Okay");
        eastPanel.add(Box.createRigidArea(new Dimension(0,(getHeight()/4))));
        eastPanel.add(createNewFilesButton);
        eastPanel.add(Box.createRigidArea(new Dimension(0,10)));
        eastPanel.add(okayButton);
        eastPanel.add(Box.createVerticalGlue());
        eastPanel.add(Box.createRigidArea(new Dimension(0,10)));
        c.add(eastPanel, BorderLayout.EAST);
        
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4,2));
        sentenceButton = new SentenceButton("Find Sentence File");
        nounButton = new NounButton("Find Noun File");
        verbButton = new VerbButton("Find Verb File");
        outputButton = new OutputButton("Find Output File");
        centerPanel.add(sentenceButton);
        sentenceTick = new JLabel(new ImageIcon("x.png"));
        centerPanel.add(sentenceTick);
        centerPanel.add(nounButton);
        nounTick = new JLabel(new ImageIcon("x.png"));
        centerPanel.add(nounTick);
        centerPanel.add(verbButton);
        verbTick = new JLabel(new ImageIcon("x.png"));
        centerPanel.add(verbTick);
        centerPanel.add(outputButton);
        outputTick = new JLabel(new ImageIcon("x.png"));
        centerPanel.add(outputTick);
        c.add(centerPanel, BorderLayout.CENTER);
        
        southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        outputArea = new JTextArea(3,25);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        scroller = new JScrollPane(outputArea);
        exitButton = new ExitButton("Exit");
        southPanel.add(scroller);
        southPanel.add(exitButton);
        c.add(southPanel, BorderLayout.SOUTH);
    }
    
    
    public class OkayButton extends JButton implements ActionListener{
        /*
        When pressed, an object of this button class will generate a mad lib sentence using the 3 files chosen by the user.
        The button only becomes enabled once the three files have been chosen.
        It will output the finished string to the outputText component.
        */
        public OkayButton(String title){
            setText(title);
            setFont(new Font("Calibri", Font.BOLD, 16));
            setForeground(Color.RED);
            setEnabled(false);
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            fm = new FileManager(sentenceFilePath, nounFilePath, verbFilePath, outputFilePath);
            String output = fm.createFinalSentence();
            outputArea.setText(output);
            fm.fileWrite(output);
        }
    }
    
    public class CreateNewFilesButton extends JButton implements ActionListener{
        public CreateNewFilesButton(String title){
            setText(title);
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            CreateNewFiles newForm = new CreateNewFiles();
        }
    }
    
    public class SentenceButton extends JButton implements ActionListener{
        /*
        A button class which opens a JFileChooser so the user can choose a .txt file with a sentence in it.
        The sentence must have some % and # characters.
        */
        public SentenceButton(String title){
            setText(title);
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            int response = sfc.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION){
                sentenceFilePath = sfc.getSelectedFile().getAbsolutePath();
                sentenceTick.setIcon(new ImageIcon("tick.png"));
                setEnabled(false);
                nounButton.setEnabled(true);
            }
            
        }
    }
    
    public class NounButton extends JButton implements ActionListener{
        public NounButton(String title){
            setText(title);
            addActionListener(this);
            setEnabled(false);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            int response = sfc.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION){
                nounFilePath = sfc.getSelectedFile().getAbsolutePath();
                nounTick.setIcon(new ImageIcon("tick.png"));
                setEnabled(false);
                verbButton.setEnabled(true);
            }              
        }
    }
    
    public class VerbButton extends JButton implements ActionListener{
        public VerbButton(String title){
            setText(title);
            addActionListener(this);
            setEnabled(false);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            int response = sfc.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION){
                verbFilePath = sfc.getSelectedFile().getAbsolutePath();
                verbTick.setIcon(new ImageIcon("tick.png"));
                setEnabled(false);
                outputButton.setEnabled(true);
            }   
        }
    }
    
    public class OutputButton extends JButton implements ActionListener{
        
        public OutputButton(String title){
            setText(title);
            addActionListener(this);
            setEnabled(false);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            int response = sfc.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION){
                outputFilePath = sfc.getSelectedFile().getAbsolutePath();
            }
            okayButton.setEnabled(true);
            setEnabled(false);
            outputTick.setIcon(new ImageIcon("tick.png"));
        }
    }
    public class ExitButton extends JButton implements ActionListener{
        /*
        Button class to close the application
        */
        public ExitButton(String title){
            setText(title);
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
    

}
