package com.translationtool;

import com.darkprograms.speech.translator.GoogleTranslate;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author Kyle Cui
 */
public class ImageTranslator extends javax.swing.JFrame {

    // Variables declaration - do not modify    
    private final JFileChooser openFileChooser;
    private javax.swing.JButton fileChooser;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel languageConstant;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JTextPane readBox;
    private javax.swing.JLabel readImageConstant;
    private javax.swing.JTextPane translatedBox;
    private javax.swing.JLabel translationConstant;
    private static final Tesseract TESS = new Tesseract();

    // End of variables declaration      
    public static void main(String[] args) throws TesseractException {
        TESS.setDatapath("src/main/tessdata"); //the directory for the traineddata files for using the Tesseract API (download from https://github.com/tesseract-ocr/tessdata and store in the tessdata folder)

        //<editor-fold defaultstate="collapsed" desc=" ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImageTranslator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ImageTranslator().setVisible(true);
        });

    }

    public ImageTranslator() {
        initComponents();
        openFileChooser = new JFileChooser();
        openFileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpeg", "jpg", "png")); //shows only png or jpegs by default on launch of filechooser UI
    }

    @SuppressWarnings("unchecked") //builds the GUI for the application
    private void initComponents() {

        fileChooser = new javax.swing.JButton();
        translationConstant = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        readBox = new javax.swing.JTextPane();
        readImageConstant = new javax.swing.JLabel();
        languageConstant = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        translatedBox = new javax.swing.JTextPane();
        languageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Image Translator");

        fileChooser.setText("Open File");
        fileChooser.addActionListener((java.awt.event.ActionEvent evt) -> {
            fileChooserActionPerformed(evt);
        });

        translationConstant.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        translationConstant.setText("Translated Text");

        readBox.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                readBoxKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(readBox);

        readImageConstant.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        readImageConstant.setText("Text Read From Image");

        languageConstant.setText("Language:");

        jScrollPane2.setViewportView(translatedBox);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(fileChooser)
                                        .addGap(29, 29, 29)
                                        .addComponent(languageConstant)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(languageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(translationConstant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(readImageConstant, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fileChooser)
                                .addComponent(languageConstant)
                                .addComponent(languageLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(readImageConstant, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(translationConstant, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                       

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {      //this is where we do most of the work for the application                                       
        int returnValue = openFileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String imagePath = openFileChooser.getSelectedFile().getAbsolutePath(); //generates the path to the selected file as a string
            String readText;
            try {
                readText = returnTess(imagePath);
                readText = readText.replace("\n", "").replace("\r", ""); //removes newlines (helps the Google Translate API) 
                readBox.setText(readText);
                detectLanguage(readText);
                translatedBox.setText(GoogleTranslate.translate("en", readText));
            } catch (TesseractException | IOException ex) {
                Logger.getLogger(ImageTranslator.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            readBox.setText("No file chosen.");
        }
    }

    private String returnTess(String inputPath) throws TesseractException { //wrapper class for tess

        String read = TESS.doOCR(new File(inputPath));
        return read;
    }

    private void detectLanguage(String inputText) { //wrapper for detecting languages
        try {
            String lang = GoogleTranslate.detectLanguage(inputText);
            languageLabel.setText(lang);
        } catch (IOException ex) {
            Logger.getLogger(ImageTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //for entering text, then translating 
    private void readBoxKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String readText = readBox.getText();
                readText = readText.replace("\n", "").replace("\r", "");
                translatedBox.setText(GoogleTranslate.translate("en", readText));
                detectLanguage(readText);
            } catch (IOException ex) {
                Logger.getLogger(ImageTranslator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
