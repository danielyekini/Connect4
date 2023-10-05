import GameMechanics.GameControl;
import GameGUI.GUI;

public class App {
    public static void main(String[] args) {
        GameControl control = new GameControl();
        control.start();
        System.out.println("back to main");
        
    }
}
