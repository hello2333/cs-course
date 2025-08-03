public class HelloGoodbye {
    public static void main(String[] args) {
        String split = " ";
        if (args.length != 2) {
            System.out.println("invalid param size");
            return;
        }

        System.out.println("Hello " + args[0] + " and " + args[1] + ".");
        System.out.println("Goodbye " + args[1] + " and " + args[0] + ".");
    }
}
