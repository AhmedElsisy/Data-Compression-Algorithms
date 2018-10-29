import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static ArrayList<Integer> tagsIndx = new ArrayList<Integer>();   // contains index of tag
    public static ArrayList<Character> tagsNextchar = new ArrayList<Character>();   // contains next char of tag
    public static void main(String args[]) {
        int o;
        String option = JOptionPane.showInputDialog("Enter 0 if you want to compress a String , 1 to decompress tags");
        o = Integer.parseInt(option);
        if(o == 0){
            String line = JOptionPane.showInputDialog("Enter String you want to compress");
            compress(line);
            String tags = "";
            for(int i = 0 ; i < tagsIndx.size() ; ++i){
                String cur = "<" + Integer.toString(tagsIndx.get(i)) + " , " + tagsNextchar.get(i) + ">";
                tags = tags + cur;
                tags = tags + "\n";
            }
            JOptionPane.showMessageDialog(null , tags);
        }
        else{
            String no_Tags = JOptionPane.showInputDialog("Enter Number of Tags you want to Enter");
            int n = Integer.parseInt(no_Tags);
            for(int i = 0 ; i < n ; ++i){
                String tag = JOptionPane.showInputDialog((i + 1) + " tag (Enter in form <number , 'character'>) ");
                int in , j = 0;
                char c;
                String tmp = "";
                for(int k = 1 ; k < tag.length() ; ++k){
                    if(tag.charAt(k) == ' '){
                        j = k + 4;
                        break;
                    }
                    tmp = tmp + tag.charAt(k);
                }
                in = Integer.parseInt(tmp);
                c = tag.charAt(j);
                tagsNextchar.add(c);
                tagsIndx.add(in);
            }
            JOptionPane.showMessageDialog(null , decompress());
        }
    }

    public static void compress(String s){
        HashMap<String , Integer> dictionary = new HashMap<String, Integer>();
        dictionary.put("" , 0);
        for(int i = 0 ; i < s.length() ; ++i){
            String b = "";
            int j = i;
            while (j < s.length()){
                if(!dictionary.containsKey(b))
                    break;
                b += s.charAt(j);
                ++j;
            }
            dictionary.put(b , dictionary.size());
            char lastone = b.charAt(b.length() - 1);
            b = b.substring(0 , b.length() - 1); // remove last char
            int indx = dictionary.get(b);  // index of string in dictionary
            tagsIndx.add(indx); // <0,'a'>
            tagsNextchar.add(lastone);
            i = j - 1;
        }
    }

    public static String decompress(){
        ArrayList<String> dictionary = new ArrayList<String>();
        dictionary.add("");
        String ret = "";
        for(int i = 0 ; i < tagsIndx.size() ; ++i){
            String sub = dictionary.get(tagsIndx.get(i));
            sub += tagsNextchar.get(i);
            dictionary.add(sub);
            ret += sub;
        }
        return  ret;
    }
}
