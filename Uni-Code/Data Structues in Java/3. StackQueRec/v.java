import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class v
{
    public static void main(String[] args)
    {
        String a = "(4.5+   7)/65*(64.6)";
        a = a.replaceAll("\\s", "");
        String[] t = a.split("(?=[-+*/()])|(?<=[^-+*/][-+*/])|(?<=[()])");
        
        for(int i = 0; i < t.length; i++){
            System.out.println(t[i]);
        }
    }
}