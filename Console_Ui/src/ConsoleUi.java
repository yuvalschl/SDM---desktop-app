public class ConsoleUi {

    private Menu menu = new Menu();
    public enum Echoice{
        Yaron ,
        Dvir
    }

    public void stam(){

        System.out.println(Echoice.Dvir+" "+Echoice.Yaron.ordinal());
    }

}
