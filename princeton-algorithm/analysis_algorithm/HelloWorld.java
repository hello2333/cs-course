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

    public int BinarySearch(int[] a, int start, int end, int t, boolean asc) {
        System.out.println("start=" + start + ", end=" + end + ", asc=" + asc);
        int l = start;
        int r = end;
        int m = (r + l) / 2;
        while (true) {
            if (a[m] == t) {
                return m;
            }
            if (l >= r) {
                return -1;
            }
            if (a[m] > t && asc) {
                r = m - 1;
            } else if (a[m] > t && !asc)  {
                l = m + 1;
            }


            if (a[m] < t && asc) {
                l = m + 1;
            } else if (a[m] < t && !asc){
                r = m - 1;
            }

            m = (r + l) / 2;
            System.out.println("left=" + l + ", right=" + r);
        }
    }

    public int BionicSearchGood(int[] a, int start, int end, int t) {
        int l = start;
        int r = end;
        int m = (l + r) / 2;
        if (a[m] == t) {
            return m;
        }

        if (l >= r) {
            return -1;
        }
        if (a[m] > a[m-1] && a[m] < a[m+1]) {
            return BionicSearchGood(a, m + 1, r, t);
        }

        if (a[m] < a[m-1] && a[m] > a[m+1]) {
            return BionicSearchGood(a, l, m - 1, t);
        }

        int left_ret = BinarySearch(a, l, m - 1, t, true);
        int right_ret = BinarySearch(a, m + 1, r, t, false);
        return left_ret > right_ret? left_ret : right_ret;
    }

    public int BionicSearchByBinarySearch(int[] a, int t) {
        int max_index = 0;
        /* method 1:
        for (int i = 1; i < a.length; i ++) {
            if (a[i] < a[i - 1]) {
                max_index = i - 1;
                break;
            }
        }*/

        // method 2: 3lgN
        int r = 0;
        int l = a.length - 1;
        int m = 0;
        while (r != l) {
            m = (r + l) / 2;
            if (a[m] > a[m - 1] && a[m] > a[m + 1]) {
                max_index = m;
                break;
            }
            if (a[m] > a[m - 1] && a[m] < a[m + 1]) {
                l = m + 1;
                continue;
            }
            if (a[m] < a[m - 1] && a[m] > a[m + 1]) {
                r = m - 1;
                continue;
            }
        }
        if (max_index == 0) {
            max_index = r;
        }

        int left_ret = BinarySearch(a, 0, max_index, t, true);
        int right_ret = BinarySearch(a, max_index + 1, a.length - 1, t, false);
        return left_ret > right_ret? left_ret : right_ret;
    }

    public int BionicSearch(int[] a, int t) {
        int r = 0;
        int l = a.length - 1;
        int m = (r + l) / 2;
        while (true) {
            if (a[m] == t) {
                return m;
            }

            if (r == l) {
                return -1;
            }

            if (a[m] < t && a[m] < a[m + 1]) {
                r = m + 1;
                m = (r + l) / 2;
                continue;
            }

            if (a[m] < t && a[m] > a[m + 1]) {
                return -1;
            }

            if (a[m] > t && a[m] < a[m + 1]) {
                l = m - 1;
                m = (r + l) / 2;
                continue;
            }

            if (a[m] > t && a[m] > a[m + 1]) {
                r = m + 1;
                m = (r + l) / 2;
                continue;
            }
        }
    }
    public static void main(String[] args) {
        HelloWorld h = new HelloWorld();
        h.PrintA();
        int[] bionic_array = {1,7,13,25,73,99,52,47,38,29,15,3};
        for (int i = 0; i < bionic_array.length; i ++) {
            //int index = h.BionicSearchByBinarySearch(bionic_array, bionic_array[i]);
            int index = h.BionicSearchGood(bionic_array, 0, bionic_array.length - 1, bionic_array[i]);
            System.out.println("index= " + index);
        }
        int index = h.BionicSearchByBinarySearch(bionic_array, 452);
        System.out.println("index= " + index);
    }
}
