import javax.swing.JOptionPane;

public class MonoalphabeticCipher extends Cipher {
    private String mono_modifiedKey = "";

    public MonoalphabeticCipher(String input, String Key) {
        super(input, Key);
        keyValidation();
    }

    public String getMono_modifiedKey() {
        return mono_modifiedKey;
    }

    @Override
    public void keyValidation() {
        mono_modifiedKey = new_Key();
        mono_modifiedKey = mono_modifiedKey.toUpperCase();

        for(char c = 'A'; c <= 'Z'; c++) {
            if(!mono_modifiedKey.contains(String.valueOf(c))) {
                mono_modifiedKey += c;
            }
        }
    }

    private String new_Key() {
        while(true) {
            try {
                if (!key.matches("[a-zA-Z]+")) {
                    throw new IllegalArgumentException("Key must contain only letters");
                }
                return key;
            } catch(IllegalArgumentException e) {
                String newKey = JOptionPane.showInputDialog(null,
                    "Invalid key: " + e.getMessage() + "\nPlease enter a new key:",
                    "Invalid Key",
                    JOptionPane.WARNING_MESSAGE);
                
                if (newKey == null) {
                    throw new IllegalArgumentException("Operation cancelled by user");
                }
                key = newKey;
            }
        }
    }

    @Override
    public void encrypt(){
        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);
            if(Character.isLetter(c)){
                c = Character.toUpperCase(c);
                int index = c - 'A';
                cipherText += mono_modifiedKey.charAt(index);
            }else{
                cipherText += c;
            }

        }
    }
    @Override
    public String getCipherText() {
        return cipherText;
    }
    @Override
    public void decrypt(){
        for(int i =0; i <input.length(); i++){
            char c = input.charAt(i);
            if(Character.isLetter(c)){
                int index = mono_modifiedKey.indexOf(c);
                plainText += (char) (index + 'A');
            }else{
                plainText += c;
            }

        }
    }
    @Override
    public String getPlainText() {
        return plainText;
    }
}
