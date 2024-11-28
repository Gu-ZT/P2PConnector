package dev.dubhe.p2p;

public class P2PConnector {
    public static void main(String[] args) {
        try (Console console = new Console()) {
            console.run();
            System.out.println(111);
            while (!console.isStop()) console.tick();
        }
    }
}