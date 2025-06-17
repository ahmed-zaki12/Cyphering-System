import javax.swing.JOptionPane;

public class Hill_Cipher extends Cipher {

    public Hill_Cipher(String Plaintxt, String key) {
        super(Plaintxt, key);
        keyValidation();
    }
    @Override
    public void keyValidation(){
        while(true) {
            try {
                if (key.length() != 4) {
                    throw new IllegalArgumentException("Key length must be 4 characters for Hill Cipher.");
                }
                for(int i = 0; i < key.length(); i++) {
                    char c = key.charAt(i);
                    if(!Character.isDigit(c)) {
                        throw new IllegalArgumentException("Key must contains only digits");
                    }
                }
                break;
            }
            catch (IllegalArgumentException e) {
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
    public int[][] KeyMatrix() {
        int[][] matrix = new int[2][2];

        for (int i = 0; i < 4; i++) {
            matrix[i / 2][i % 2] = Character.getNumericValue(key.charAt(i));
        }

        return matrix;
    }
    public int[][] getHillCpherKey(){
        int[][] keyMatrix = KeyMatrix();
        return keyMatrix;
    }
    public String encryptInput(){
        String str = Helper.cleanString(input).toUpperCase();
        String encryptedText = "";
        int[][] keyMatrix = KeyMatrix();

        int originalLength = str.length();

        if (str.length() % 2 != 0) {
            int lastChar = str.charAt(str.length() - 1) - 'A';
        
            int x = ((lastChar - (keyMatrix[0][0] * lastChar)) * modInverse(keyMatrix[0][1], 26)) % 26;
            if (x < 0) x += 26;
            str += (char)(x + 'A');
        }

        int index1, index2, result1, result2;
        for (int i = 0; i < str.length(); i += 2) {
            index1 = str.charAt(i) - 'A';
            index2 = str.charAt(i + 1) - 'A';

            result1 = ((keyMatrix[0][0] * index1) + (keyMatrix[0][1] * index2)) % 26;
            result2 = ((keyMatrix[1][0] * index1) + (keyMatrix[1][1] * index2)) % 26;
            encryptedText += (char) (result1 + 'A');
            encryptedText += (char) (result2 + 'A');
        }

        if (originalLength % 2 != 0) {
            encryptedText = encryptedText.substring(0, encryptedText.length() - 1);
        }

        return encryptedText;
    }
    @Override
    public void encrypt(){
        String encryptedText = encryptInput();
        int counter = 0;
        for(int i = 0; i < input.length(); i++){
            if(Character.isLetter(input.charAt(i))){
                cipherText += encryptedText.charAt(counter);
                counter++;
            }
            else{
                cipherText += input.charAt(i);
            }
        }
    }
    private int[][] getInverseKeyMatrix() {
        int[][] keyMatrix = KeyMatrix();
        int a = keyMatrix[0][0];
        int b = keyMatrix[0][1];
        int c = keyMatrix[1][0];
        int d = keyMatrix[1][1];


        int det = ((a * d) - (b * c)) % 26;
        if (det < 0) det += 26;

        int detInv = -1;
        for (int i = 0; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInv = i;
                break;
            }
        }

        if (detInv == -1) {
            throw new ArithmeticException("Matrix is not invertible modulo 26");
        }

        int[][] inverseMatrix = new int[2][2];

        inverseMatrix[0][0] = ((d * detInv) % 26 + 26) % 26;
        inverseMatrix[0][1] = (((-b) * detInv) % 26 + 26) % 26;
        inverseMatrix[1][0] = (((-c) * detInv) % 26 + 26) % 26;
        inverseMatrix[1][1] = ((a * detInv) % 26 + 26) % 26;

        return inverseMatrix;
    }
    public String decryptInput() {
        String str = Helper.cleanString(input).toUpperCase();
        String decryptedText = "";

        int originalLength = str.length();

        if (str.length() % 2 != 0) {
            int lastChar = str.charAt(str.length() - 1) - 'A';
            int[][] invMatrix = getInverseKeyMatrix();

            int x = ((lastChar - (invMatrix[0][0] * lastChar)) * modInverse(invMatrix[0][1], 26)) % 26;
            if (x < 0) x += 26;
            str += (char)(x + 'A');
        }

        int[][] invMatrix = getInverseKeyMatrix();

        for (int i = 0; i < str.length(); i += 2) {
            int index1 = str.charAt(i) - 'A';
            int index2 = str.charAt(i + 1) - 'A';

            int result1 = ((invMatrix[0][0] * index1) + (invMatrix[0][1] * index2)) % 26;
            int result2 = ((invMatrix[1][0] * index1) + (invMatrix[1][1] * index2)) % 26;

            if (result1 < 0) result1 += 26;
            if (result2 < 0) result2 += 26;

            decryptedText += (char) (result1 + 'A');
            decryptedText += (char) (result2 + 'A');
        }


        if (originalLength % 2 != 0) {
            decryptedText = decryptedText.substring(0, decryptedText.length() - 1);
        }

        return decryptedText;
    }
    @Override
    public void decrypt() {
        String decryptedLetters = decryptInput();
        plainText = "";
        int counter = 0;

        for (int i = 0; i < input.length(); i++) {
            if (Character.isLetter(input.charAt(i))) {
                plainText += decryptedLetters.charAt(counter);
                counter++;
            } else {
                plainText += input.charAt(i);
            }
        }
    }

    @Override
    public String getCipherText(){
        return cipherText;
    }
    @Override
    public String getPlainText(){
        return plainText;
    }

    // Helper method to find modular multiplicative inverse
    private int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }
}
