package Logic;

import java.util.Random;

public class mailLogic {


    public String appendFormatAndNumbers(String input) {
        String SemiRandom = Long.toString(System.currentTimeMillis());
        String Formatting = "@thisisatest.cz";
        return input + SemiRandom + Formatting;
    }

    public String appendLong(String input) {
        StringBuilder SB = new StringBuilder(input); //Används istället för bara string för att inte behöva skapa en massa olika strings, reducerar clutter
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charCount = 105;
        for (int i = 0; i < charCount; i++) {
            SB.append(characters.charAt(random.nextInt(characters.length())));
        }
        return SB.toString();
    }

}