package application;

import java.util.*;

/**
 * @author vladimir.tikhomirov
 */
public class Main {
    public static void main(String[] args) {

        Object x = new Vector().elements();
        System.out.print((x instanceof Enumeration)+",");
        System.out.print((x instanceof Iterator)+",");
        System.out.print((x instanceof ListIterator)+"\n");


        int x2 = 11 & 9;
        int y = x2 ^ 3;
        System.out.println( y | 12 );

        TaskLibrary taskLibrary = new TaskLibrary();
        Set<Integer> integers = taskLibrary.duplicateNumbers(new int[]{1, 3, 2, 2, 3});
        System.out.println(integers);
    }


}
