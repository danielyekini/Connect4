package GameMechanics;
import java.util.Scanner;

public class Input {

    private Scanner scan;

    public Input() {
        this.scan = new Scanner(System.in);
    }

    public String String(String input_string){
        System.out.print(input_string);
        return scan.nextLine();
    }

    public char Character(String input_string){
        System.out.print(input_string);
        return scan.next().charAt(0);
    }

    public int Integer(String input_string){
        System.out.print(input_string);
        return scan.nextInt();
    }

    public double Double(String input_string){
        System.out.print(input_string);
        return scan.nextDouble();
    }
}
