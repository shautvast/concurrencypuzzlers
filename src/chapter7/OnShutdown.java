package chapter7;

public class OnShutdown {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("going down");
            }
        });
    }
}
