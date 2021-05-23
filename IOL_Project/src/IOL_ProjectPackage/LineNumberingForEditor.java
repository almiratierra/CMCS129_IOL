/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOL_ProjectPackage;

import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.text.Element;

/**
 *
 * @author tierr
 */
public class LineNumberingForEditor extends JTextArea {
    private JTextArea textArea;

    public LineNumberingForEditor(JTextArea textArea){
        this.textArea = textArea;
        Color light_blue = new Color(173, 216, 230);
        setBackground(light_blue);
        setEditable(false);
    }

    public void updateLineNumbers(){
        String lineNumbersText = getLineNumbersText();
        setText(lineNumbersText);
    }

    private String getLineNumbersText(){
        int caretPosition = textArea.getDocument().getLength();
        Element root = textArea.getDocument().getDefaultRootElement();
        StringBuilder lineNumbersTextBuilder = new StringBuilder();
        lineNumbersTextBuilder.append("1").append(System.lineSeparator());
        for (int elementIndex = 2; elementIndex < root.getElementIndex(caretPosition) + 2; elementIndex++){
            lineNumbersTextBuilder.append(elementIndex).append(System.lineSeparator());
        }
        return lineNumbersTextBuilder.toString();
    }
}
