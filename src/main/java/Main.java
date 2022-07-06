import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите путь к целевой папке или файлу для копирования");
        String source = sc.nextLine();
        System.out.println("Введите путь куда скопировать папку или файл");
        String destination = sc.nextLine();
        FileUtils.copyFolder(source, destination);
    }
}
