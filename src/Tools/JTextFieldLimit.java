package Tools;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//Classe utile per limitare la stringa d'inserimento di un JTextField

/**Classe utile per limitare le stringhe di inserimento in un JTextField
 */
public class JTextFieldLimit extends PlainDocument {
    private int limit;

    /**
     * Viene impostato un limite desiderato per limitare il JTextField
     */
    public JTextFieldLimit(int limit){
        super();
        this.limit = limit;
    }

    /**
     * Non permette di scrivere nel field se il limite è stato raggiunto
     */
    public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
