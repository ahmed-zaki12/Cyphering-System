public abstract class VigenereCipher extends Cipher {
    protected String vigenere_Key = "";

    public VigenereCipher(String Plaintext, String Key) {
        super(Plaintext, Key);
    }

    public String getVigenere_Key() {
        return vigenere_Key;
    }

    public abstract void keyValidation();
    public abstract void encrypt();
    public abstract void decrypt();

    @Override
    public String getCipherText() {
        return cipherText;
    }

    @Override
    public String getPlainText() {
        return plainText;
    }
}
