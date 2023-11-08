package br.com.senai.cardapiosmktplaceview.util;

import java.math.BigDecimal;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;

public class EditDocDecimal extends PlainDocument{
	
	private static final long serialVersionUID = 1L;
	
	private static final SimpleAttributeSet nullAttribute = new SimpleAttributeSet();
	
	public EditDocDecimal() {
		super();
	}
	
	@Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {

        String original = getText(0, getLength());

        // Permite apenas digitar até 16 caracteres (9.999.999.999,99)
        if (original.length() < 16 && !str.equals("")) {
            
            StringBuffer mascarado = new StringBuffer();
            if (a != nullAttribute) {

                //limpa o campo
                remove(-1, getLength());

                mascarado.append((original + str).replaceAll("[^0-9]", ""));

                for (int i = 0; i < mascarado.length(); i++) {
                    if (!Character.isDigit(mascarado.charAt(i))) {
                        mascarado.deleteCharAt(i);
                    }
                }

                if (!mascarado.toString().equals("")){

                    Long number = Long.parseLong(mascarado.toString());

                    mascarado.replace(0, mascarado.length(), number.toString());

                    if (mascarado.length() < 3) {
                        if (mascarado.length() == 1) {
                            mascarado.insert(0, "0");
                            mascarado.insert(0, ",");
                            mascarado.insert(0, "0");
                        } else if (mascarado.length() == 2) {
                            mascarado.insert(0, ",");
                            mascarado.insert(0, "0");
                        }
                    } else {
                        mascarado.insert(mascarado.length() - 2, ",");
                    }

                    if (mascarado.length() > 6) {
                        mascarado.insert(mascarado.length() - 6, '.');
                        if (mascarado.length() > 10) {
                            mascarado.insert(mascarado.length() - 10, '.');
                            if (mascarado.length() > 14) {
                                mascarado.insert(mascarado.length() - 14, '.');
                            }
                        }
                    }

                    super.insertString(0, mascarado.toString(), a);
                }

            }else {
                if (original.length() == 0) {
                    super.insertString(0, "0,00", a);
                }
            }
        }
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {

        if ( len == getLength() ) {

            super.remove(0, len);
            if (offs != -1){
                insertString(0, "", nullAttribute);
            }

        }else{
            String original = getText(0, getLength()).substring(0, offs) +
                    getText(0, getLength()).substring(offs+len);

            super.remove(0, getLength());
            insertString(0,original,null);
        }
    }

    public BigDecimal getBigDecimalValue(){
        try{
            if (getLength() > 0){
                String valor = getText(0, getLength());
                valor = valor.replace(".", "");
                valor = valor.replace(",", ".");
                return new BigDecimal(valor);
            }
            return new BigDecimal(0);
        }catch (Exception e) {
            e.printStackTrace();
            return new BigDecimal(-1);
        }
        
    }
	
}
