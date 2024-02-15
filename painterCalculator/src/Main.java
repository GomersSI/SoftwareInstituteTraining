import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        boolean running = true;
        painterCalculator painterCalculator = new painterCalculator();
        while (running){
            Scanner reader = new Scanner(System.in);
            int actionInt = -1;
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Current wall surface area : " + painterCalculator.totalArea() + "m^2");
            System.out.println("Current total cost : " + painterCalculator.calculateTotal());
            System.out.println("Total tins of paint required : " + painterCalculator.getTinsNeeded());
            System.out.println("What would you like to do :");
            System.out.println("1: Add a wall");
            System.out.println("2: Add a window or door");
            System.out.println("3: Remove a wall, window or door");
            System.out.println("4: Change paint");
            System.out.println("5: Close the program");
            String action = reader.next();
            try{
                actionInt = Integer.parseInt(action);
            }
            catch(Exception e){}
            switch(actionInt){
                case 1:
                    painterCalculator.addWall();
                    break;
                case 2:
                    painterCalculator.addWindowDoor();
                    break;
                case 3:
                    painterCalculator.removeWall();
                    break;
                case 4:
                    painterCalculator.addPaint();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Sorry that wasn't a valid option, please try again.");
                    break;
            }
        }
    }
}
