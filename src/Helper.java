public class Helper {

    public static String cleanString(String str) {
        String result = "";
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(Character.isLetter(c)) {
                result += c;
            }
        }
        return result;
    }
}
