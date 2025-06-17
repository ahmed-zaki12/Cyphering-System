public abstract class Cipher{
    protected String input;
    protected String key;
    protected String cipherText = "";
    protected String plainText = "";

    public Cipher(){
        input = "";
        key = "";
    }
    public Cipher(String input, String key){
        this.input = input;
        this.key = key;
    }
    public String getInput(){
        return input;
    }
    public String getKey(){
        return key;
    }


    public abstract void keyValidation();
    public abstract void encrypt();
    public abstract void decrypt();
    public abstract String getCipherText();
    public abstract String getPlainText();
    
}
