import java.util.Scanner;

interface ConsoleInterface {
    void showMenu();
    void doOperation(int input);
}

class FullConsole implements ConsoleInterface {
    public void showMenu(){
        System.out.println("1. Menu option 1");
        System.out.println("2. Menu option 2");
        System.out.println("3. Menu option 3");
        System.out.println("4. Menu option 4");
    }

    public void doOperation(int input){
        switch (input) {
            case 1:
                System.out.println("Performing operation 1...");
                break;
            case 2:
                System.out.println("Performing operation 2...");
                break;
            case 3:
                System.out.println("Performing operation 3...");
                break;
            case 4:
                System.out.println("Performing operation 4...");
                break;
            default:
                System.out.println("Sorry, invalid input.");
                break;
        }
    }
}

class PartialConsole implements ConsoleInterface {
    public void showMenu(){
        System.out.println("2. Menu option 2");
        System.out.println("3. Menu option 3");
    }

    public void doOperation(int input){
        switch (input) {
            case 2:
                System.out.println("Performing operation 2...");
                break;
            case 3:
                System.out.println("Performing operation 3...");
                break;
            default:
                System.out.println("Sorry, invalid input.");
                break;
        }
    }
}

class RestrictedConsole implements ConsoleInterface {
    public void showMenu(){
        System.out.println("3. Menu option 3");
    }

    public void doOperation(int input){
        switch (input) {
            case 3:
                System.out.println("Performing operation 3...");
                break;
            default:
                System.out.println("Sorry, invalid input.");
                break;
        }
    }
}

class ConsoleProxy implements ConsoleInterface {
    private ConsoleInterface console;
    private int permission;

    public ConsoleProxy(int permission){
        this.permission = permission;
        // 根据用户权限选择实际上使用的控制台对象
        if(permission == 1){
            console = new FullConsole();
        }else if(permission == 2){
            console = new PartialConsole();
        }else if(permission == 3){
            console = new RestrictedConsole();
        }
    }

    public void showMenu(){
        console.showMenu(); // 实际上调用被代理对象的方法
    }

    public void doOperation(int input){
        console.doOperation(input); // 实际上调用被代理对象的方法
    }
}

class ConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int userPermission = 2; // 假设当前用户权限为 2

        // 创建代理对象并调用方法
        ConsoleInterface console = new ConsoleProxy(userPermission);

        console.showMenu();

        int input = scanner.nextInt();

        console.doOperation(input);

        scanner.close();
    }
}