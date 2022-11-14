import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class textEditor  extends JFrame implements ActionListener, ChangeListener {
    JScrollPane scrollPane;
    JTextArea textArea;
    JComboBox fontBox;
    JSpinner spinner;
    JMenuBar menuBar;
    JMenu fileMenu,helpMenu;
    JMenuItem save,open,exit;
    JLabel fontSizeLabel,fontStyleLabel;
    JButton btn;
    textEditor() {
        this.setSize(500, 600);    //set the size of the editor frame because it doesn't have pre-defined size
        this.setTitle("text editor");  //editor title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //top right exit on click
        this.setLocationRelativeTo(null);  //the screen will appear in middle
        this.setLayout(new FlowLayout());  //to set the layout of the frame

        //TextArea
        textArea=new JTextArea();  //creating a textarea for typing the words
        textArea.setSelectedTextColor(Color.blue);
        textArea.setLineWrap(true); //words cannot exceed the dimensions
        textArea.setWrapStyleWord(true);
        textArea.setBounds(30,30,200,200);
        textArea.setFont(new Font("Arial",Font.PLAIN,20));


        //Pane
        scrollPane =new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // to make scroll bar constant

        //spinner
        spinner=new JSpinner();
        spinner.setPreferredSize(new Dimension(50,25));
        spinner.setValue(20); //default value
        spinner.addChangeListener(this);

        //colour scroll bar
        btn=new JButton("Change Colour");
        btn.addActionListener(this);

        //font scroll bar

        String[] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox=new JComboBox<>(font);
        fontBox.addActionListener(this);

        //menuBar

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");
        open =new JMenuItem("Open");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        save.addActionListener(this);
        open.addActionListener(this);

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        //font size label and style label

        fontSizeLabel=new JLabel("Font Size");
        fontStyleLabel=new JLabel("Font Style");

        //add all the elements in the jFrame

        this.setJMenuBar(menuBar);
        this.add(btn);
        this.add(fontSizeLabel);
        this.add(spinner);
        this.add(fontStyleLabel);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==fontBox){
            textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,(int)spinner.getValue()));
        }
        if(e.getSource()==exit){
            System.exit(2);
        }
        if(e.getSource()==save){
            JFileChooser fileChoose=new JFileChooser();
            fileChoose.setCurrentDirectory(new File("."));
            int response=fileChoose.showSaveDialog(null);
            if(response==JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut;
                file=new File(fileChoose.getSelectedFile().getAbsolutePath());
                try {
                     fileOut=new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                fileOut.close();
            }
        }
        if(e.getSource()==btn){
            Color color=JColorChooser.showDialog(null,"Select a colour",Color.black);
            textArea.setForeground(color);
        }
        if(e.getSource()==open){
            JFileChooser fileChoose=new JFileChooser();
            fileChoose.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter=new FileNameExtensionFilter("Text files","java","txt");
            fileChoose.setFileFilter(filter);
            int response=fileChoose.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file=new File(fileChoose.getSelectedFile().getAbsolutePath());
                Scanner fileIn;
                try {
                    fileIn=new Scanner(file);
                    if(file.isFile()){
                        while(fileIn.hasNextLine()){
                            String line =fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                fileIn.close();
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource()==spinner){
            textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)spinner.getValue()));
        }
    }
}
