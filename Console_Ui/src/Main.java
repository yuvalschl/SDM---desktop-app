public class Main {
    public static void main(String[] args) {
        ConsoleUi ui = new ConsoleUi();
        int n = ui.getAndValidateChoice(6);
        System.out.println(n);
    }
}
