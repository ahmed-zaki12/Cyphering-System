import javax.swing.JOptionPane;

public class Vigenere_Auto_Key extends VigenereCipher {
    public Vigenere_Auto_Key(String plaintext, String key) {
        super(plaintext, key);
        keyValidation();
    }

    @Override
    public void keyValidation() {
        while(true) {
            try {
                if (key.matches("\\d+")) {
                    throw new IllegalArgumentException("Key must contain only letters");
                }
                String cleanedKey = Helper.cleanString(key).toUpperCase();
                String cleanedInput = Helper.cleanString(input).toUpperCase();

                StringBuilder extendedKey = new StringBuilder(cleanedKey);

                for (int i = 0; extendedKey.length() < cleanedInput.length(); i++) {
                    extendedKey.append(cleanedInput.charAt(i));
                }

                vigenere_Key = extendedKey.toString();
                break;
            }
            catch(IllegalArgumentException e) {
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
    public void encrypt() {
        cipherText = ""; 
        String n = input.toUpperCase();
        int keyIndex = 0;

        for (int i = 0; i < n.length(); i++) {
            char currentChar = n.charAt(i);

            if (!Character.isLetter(currentChar)) {
                cipherText += currentChar;
            } else {
                char keyChar = vigenere_Key.charAt(keyIndex);
                char encryptedChar = (char)(((currentChar - 'A') + (keyChar - 'A')) % 26 + 'A');
                cipherText += encryptedChar;
                keyIndex++; 
            }
        }
    }

    @Override
    public void decrypt() {
        plainText = ""; 
        String n = input.toUpperCase();
        String cleanedKey = Helper.cleanString(key).toUpperCase();
        StringBuilder currentKey = new StringBuilder(cleanedKey);
        int keyIndex = 0;
    
        for (int i = 0; i < n.length(); i++) {
            char currentChar = n.charAt(i);
    
            if (!Character.isLetter(currentChar)) {
                plainText += currentChar;
            } else {
                char keyChar = currentKey.charAt(keyIndex);
                int decryptedIndex = (currentChar - 'A') - (keyChar - 'A');
                if (decryptedIndex < 0) decryptedIndex += 26;
                char decryptedChar = (char)(decryptedIndex + 'A');
                plainText += decryptedChar;
                // Append the decrypted character to the key for future use
                currentKey.append(decryptedChar);
                keyIndex++;
            }
        }
    }
}