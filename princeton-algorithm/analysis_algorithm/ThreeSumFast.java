
public class ThreeSumFast {

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
