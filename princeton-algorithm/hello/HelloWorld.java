/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

public class HelloWorld {
    int[] a;
    public HelloWorld() {
        a = new int[10];
    }

    public void PrintA() {
        for (int i = 0; i < a.length; i ++) {
            System.out.println("Hello, World " + a[i]);
        }
    }
    public static void main(String[] args) {
        HelloWorld h = new HelloWorld();
        h.PrintA();
        System.out.println("Hello, World% c");
    }
}
