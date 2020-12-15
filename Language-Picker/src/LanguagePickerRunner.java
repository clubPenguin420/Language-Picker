import java.io.IOException;
import java.util.Scanner;


/**
 * The runner class
 * <p>
 * This class is the runner, so you run this file to run the picker
 * </p>
 * @author Shrideep Gaddad
 * @version 1.0.0
 */
public class LanguagePickerRunner {

    /**
     *
     * Main
     * 
     * @param args  the args
     */
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        LanguagePicker buddy;
        int choice;

        do{
            System.out.println("------------ Language Picker v1.0.0------------");
            System.out.println();
            System.out.println("Hello newcomer, I see you are here to start your journey into programming!\n" +
                    "This program will help guide you into picking your first language!\n" +
                    "Please enter the number corresponding to the choice when answering a question\n");

            System.out.println("Press ENTER to start the program :D");
            keyboard.nextLine();

            System.out.println("What do you want to do in programming?");
            System.out.println("1. Web Programming\n2. Desktop Apps\n3. Games\n4. Robotics/Electronics");
            while(true){
                System.out.println();
                choice = keyboard.nextInt();
                if(choice >= 1 && choice <= 4){
                    break;
                }
                else{
                    System.out.println("Whoops! That was not a valid choice, please try again.");
                }
            }
            buddy = new LanguagePicker(choice);
            System.out.println(buddy);
            System.out.println();
            System.out.println("Do you want to run the program again? Type 'y' for yes and 'n' for no");
            if(keyboard.next().charAt(0) == 'y'){
                continue;
            }
            else{
                break;
            }
        }
        while(true);
        System.out.println("Thanks for using this tool and I hope that you have a fun time programming!!!");


    }
}
