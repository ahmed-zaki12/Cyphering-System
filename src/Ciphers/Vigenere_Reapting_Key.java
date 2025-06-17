import javax.swing.JOptionPane;

public class Vigenere_Reapting_Key extends VigenereCipher {
    
    public Vigenere_Reapting_Key(String Plaintext, String key) {
        super(Plaintext, key);
        keyValidation();
    }

    @Override
    public void keyValidation() {
        while(true) {
            try {
                if (key.matches("\\d+")) {
                    throw new IllegalArgumentException("Key must contain only letters");
                }
                vigenere_Key = Helper.cleanString(key).toUpperCase();
                StringBuilder extendedKey = new StringBuilder(vigenere_Key);

                int originalLength = vigenere_Key.length();
                int inputLength = input.length();

                for (int i = 0; extendedKey.length() < inputLength; i++) {
                    extendedKey.append(vigenere_Key.charAt(i % originalLength));
                }
                vigenere_Key = extendedKey.toString();
                break;
            }
            catch(IllegalArgumentException e) {
                String newKey = JOptionPane.showInputDialog(null,
                    "Enter text not number\nPlease enter a new key:",
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
    public void encrypt() {
        String new_input = input.toUpperCase();
        for (int i = 0; i < input.length(); i++) {
            if(!Character.isLetter(new_input.charAt(i))){
                cipherText += new_input.charAt(i);
            }
            else{
                char c = (char)(((new_input.charAt(i) - 'A') + (vigenere_Key.charAt(i) - 'A')) % 26 + 'A');
                cipherText += c;
            }
        }
    }
    @Override
    public void decrypt() {
        String new_input = input.toUpperCase();
        for (int i = 0; i < input.length(); i++) {
            if(!Character.isLetter(new_input.charAt(i))){
                plainText += new_input.charAt(i);
            }else{
                int decryptedIndex = (new_input.charAt(i) - 'A') - (vigenere_Key.charAt(i) - 'A');
                if (decryptedIndex < 0) decryptedIndex += 26;
                char c = (char)(decryptedIndex + 'A');
                plainText += c;
            }
        }
    }
}
