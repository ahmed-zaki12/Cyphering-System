import javax.swing.JOptionPane;

public class CaesarCipher extends Cipher {
    private int modifiedKey;

    public CaesarCipher(String input, String key) {
        super(input, key);
        keyValidation();
    }

    @Override
    public void keyValidation() {
        while(true) {
            try {
                modifiedKey = Integer.parseInt(key);
                if (modifiedKey < 0) { 
                    throw new IllegalArgumentException("The key must be positive integer");
                }
                modifiedKey = modifiedKey % 26;
                break;
            } catch (NumberFormatException e) {
                String newKey = JOptionPane.showInputDialog(null,
                    "Error: The key must be a number\nPlease enter a new key:",
                    "Invalid Key",
                    JOptionPane.WARNING_MESSAGE);
                
                if (newKey == null) {
                    throw new IllegalArgumentException("Operation cancelled by user");
                }
                key = newKey;
            } catch (IllegalArgumentException e) {
                String newKey = JOptionPane.showInputDialog(null,
                    "Error: " + e.getMessage() + "\nPlease enter a new key:",
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
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(Character.isLetter(c)) {
                char sum = (char)(c + modifiedKey);
                if(Character.isUpperCase(c)) {
                    if(sum > 'Z'){
                        sum -= 26;

                    }
                    cipherText += sum;
                }
                else if(Character.isLowerCase(c)) {
                    if(sum > 'z'){
                        sum -= 26;
                    }
                    cipherText += sum;
                }
            }
            else{
                cipherText += c;
            }
        }
    }
    @Override
    public String getCipherText() {
        return cipherText;
    }
    @Override
    public void decrypt() {
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(Character.isLetter(c)) {
                char sum = (char)(c - modifiedKey);
                if(Character.isUpperCase(c)) {
                    if(sum < 'A'){
                        sum += 26;

                    }
                    plainText += sum;
                }
                else if(Character.isLowerCase(c)) {
                    if(sum < 'a'){
                        sum += 26;
                    }
                    plainText += sum;
                }
            }
            else{
                plainText += c;
            }
        }
    }
    @Override
    public String getPlainText() {
        return plainText;
    }
}
