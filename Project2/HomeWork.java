import java.util.Scanner;

public class HomeWork {
    public static void main (String[] arg) {
        System.out.println("Расскажите о вашем друге");
        System.out.println("Как зовут вашего друга?");

        Scanner in = new Scanner(System.in);
        String name = in.nextLine();

        System.out.println("Сколько лет вашему другу?");
        String age = in.nextLine();

        System.out.println("Моему другу " + name + " сейчас " + age + " лет");
    }
}
