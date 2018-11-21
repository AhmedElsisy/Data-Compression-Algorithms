import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static ArrayList<Integer> a = new ArrayList<Integer>(); // compressed tags are stored here
    public static HashMap<String , Integer> dictionary = new HashMap<String, Integer>();

    public static void main(String[] args) {
        // LZW Compression and De-compression

        Scanner sc = new Scanner(System.in);
        String s = sc.next();

        compress(s);

        // now Arraylist (a) contains compressed tags of input String

        String decompressedString = decompress(); // Function decompress() decompress tags in Arraylist (a)

        System.out.println(decompressedString);

    }


    public static String decompress(){
        String ret = "";

        HashMap<Integer , String> mp = new HashMap<Integer, String>(); // dictionary int to string <tagindx , String>
        for(char x = 'a' ; x <= 'z' ; ++x){
            String tmp = "";
            tmp += x;
            mp.put((int)x , tmp);
        }

        for(char x = 'A' ; x <= 'Z' ; ++x){
            String tmp = "";
            tmp += x;
            mp.put((int)x , tmp);
        }

        int tagindx = 127;

        String prev = "";
        for(int x : a){
            String cur = "";

            // not found in dictionary
            if(x > tagindx){
                // in this case we concatenate previous step and first Symbol in previous step
                cur = prev + prev.charAt(0);
                ret += cur;
                mp.put(++tagindx , cur);
            }
            else{
                cur = mp.get(x);
                ret += cur;
                if(prev.isEmpty()){     // first tag add nothing to dictionary
                    prev = cur;
                    continue;
                }
                // concatenate previous step and first Symbol in current step
                String add = prev + cur.charAt(0);
                mp.put(++tagindx , add);
            }

            prev = cur;
        }

        return ret;
    }

    public static void compress(String s){
        dictionary.clear();
        fillDict(); // fill dictionary with alphabet a -> z and A -> Z
        int n = s.length() , tagindx = 128;

        for(int i = 0 ; i < n ;){
            String word = "";
            int j = i;
            while (j < n && dictionary.containsKey(word)){
                word += s.charAt(j);
                j++;
            }

            // final substring "bbb"
            if(dictionary.containsKey(word)){
                a.add(dictionary.get(word));
                break;
            }

            String known = word.substring(0 , word.length() - 1);
            a.add(dictionary.get(known));
            dictionary.put(word , tagindx++);
            i = j - 1;
        }
    }

    public static void fillDict(){
        dictionary.put("" , 1);
        for(char x = 'a' ; x <= 'z' ; ++x) {
            String tmp = "";
            tmp += x;
            dictionary.put(tmp, (int) x);
        }

        for(char x = 'A' ; x <= 'Z' ; ++x) {
            String tmp = "";
            tmp += x;
            dictionary.put(tmp, (int) x);
        }
    }
}
