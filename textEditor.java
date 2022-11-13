import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class textEditor  extends JFrame implements ActionListener, ChangeListener {
    JScrollPane scrollPane;
    JTextArea textArea;
    JComboBox fontBox;
    JSpinner spinner;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem save,exit;
    JLabel fontSizeLabel,fontStyleLabel;

    textEditor() {
        this.setSize(500, 500);
        this.setTitle("text editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        //TextArea
        textArea=new JTextArea();
        textArea.setSelectedTextColor(Color.blue);
        textArea.setBounds(30,30,200,200);
        textArea.setFont(new Font("Arial",Font.PLAIN,15));


        //Pane
        scrollPane =new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));

        //spinner
        spinner=new JSpinner();
        spinner.setPreferredSize(new Dimension(50,25));
        spinner.setValue(18); //default value
        spinner.addChangeListener(this);
//        spinner.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)spinner.getValue()));
//            }
//        });


        //add color option on your own
        //add font style option on your own
        //add autoSave to the same file
        //

        //font scroll bar

        String[] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox=new JComboBox<>(font);
        fontBox.addActionListener(this);
//        fontBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,(int)spinner.getValue()));
//            }
//        });

        //menuBar

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        save.addActionListener(this);

        fileMenu.add(save);
        fileMenu.add(exit);
        menuBar.add(fileMenu);


        //font size label and style label
        fontSizeLabel=new JLabel("Font Size");
        fontStyleLabel=new JLabel("Font Style");

        //add all the elements in the jFrame
        this.setJMenuBar(menuBar);
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
            if(response==0){
                File file=new File(fileChoose.getSelectedFile().getAbsolutePath());
                PrintWriter text;
                try {
                     text=new PrintWriter(file);
                    text.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                text.close();
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
