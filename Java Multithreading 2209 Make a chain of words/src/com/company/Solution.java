package com.company;

/*

2209 Make a chain of words

In the main method, read from the console the name of the file that contains words separated by a space.
In the getLine method using StringBuilder, arrange all the words in such a way that the last letter of this word matches the first letter of the next case-insensitive.
Each word should participate 1 time.
The getLine method should return any option.
Separate words with a space.
Display the resulting string on the screen.
Example body of the input file:
Kiev New York Amsterdam Vienna Melbourne
Result:
Amsterdam Melbourne New York Kiev Vienna

Requirements:
1. The main method should read the file name from the keyboard.
2. StringBuilder must be used in the getLine method
3. The getLine method should return an empty string (empty StringBuilder) if no parameters (words) were passed to it.
4. All words passed to the getLine method should be included in the resulting string, if possible.
5. The output to the screen should correspond to the condition of the task.


*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
Составить цепочку слов
*/
public class Solution {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String lineOfWords = null;
        try {
            String fileName = reader.readLine();
            reader.close();
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            lineOfWords = fileReader.readLine();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder result = getLine(lineOfWords.replace("\uFEFF", "").split(" "));
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {

        if (words.length == 0) return new StringBuilder();

        HashMap<StringBuilder, Character> map = new HashMap<>();
        HashMap<StringBuilder, Character> mapCopy = new HashMap<>();
        int lengthOfAllWords = 0;
        for (String s : words) {
            lengthOfAllWords += s.length();
            map.put(new StringBuilder(s), s.toLowerCase().charAt(s.length() - 1));
        }
        lengthOfAllWords += words.length - 1;
        boolean isAnswer = false;


        while (!isAnswer) {
            for (Map.Entry<StringBuilder, Character> pair : map.entrySet()) {
                StringBuilder str = pair.getKey();
                char lastLetter = pair.getValue();

                for (int i = 0; i < words.length; i++) {
                    if ((lastLetter == words[i].toLowerCase().charAt(0)) && (str.indexOf(words[i]) < 0)) {
                        StringBuilder str2 = str;
                        str2.append(" ").append(words[i]);
                        lastLetter = words[i].toLowerCase().charAt(words[i].length() - 1);
                        mapCopy.put(str2, lastLetter);
                        //i = 0;
                    }
                }
                if (str.length() == lengthOfAllWords) {
                    isAnswer = true;
                    return str;
                }
            }
            if (!mapCopy.isEmpty()) {
                map.putAll(mapCopy);
                mapCopy.clear();
            } else {
                int count = 0;
                for (Map.Entry<StringBuilder, Character> pair : map.entrySet()) {
                    int countWords = pair.getKey().toString().split(" ").length;
                    if (countWords > count) {
                        count = countWords;
                    }
                }
                for (Map.Entry<StringBuilder, Character> pair : map.entrySet()) {
                    int countWords = pair.getKey().toString().split(" ").length;
                    if (countWords == count) {
                        return pair.getKey();
                    }
                }

            }
        }


        return new StringBuilder("");
    }
}
