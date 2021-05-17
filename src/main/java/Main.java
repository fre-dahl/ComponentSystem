import ecs.Scene;

public class Main {

    public static void main(String[] args) {

        Window.get().start(new WinConfig(), new Scene("Example", 1000) {

            @Override
            public void create() {

            }

            @Override
            public void cleanUp() {

            }
        });

    }
}
