package dev.dubhe.p2p;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class Console implements AutoCloseable, Runnable {
    private Scanner scanner;
    private Thread thread;
    private final Stack<Runnable> runnableStack = new Stack<>();
    private boolean stop;

    public Console() {
        this.stop = true;
    }

    public void onInput(String str) {
        System.out.println(str);
        if ("stop".equals(str)) {
            System.out.println("Press enter key to exit.");
            this.stop();
        }
    }

    public void tick() {
        while (!this.stop) {
            try {
                runnableStack.pop().run();
            } catch (EmptyStackException e) {
                break;
            }
        }
    }

    @Override
    public void run() {
        this.stop = false;
        this.thread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            this.scanner = scanner;
            while (!this.stop) {
                if (!scanner.hasNext()) continue;
                final String str = scanner.nextLine();
                this.runnableStack.push(() -> this.onInput(str));
            }
        });
        this.thread.start();
    }

    @Override
    public void close() {
        if (this.scanner != null) {
            this.scanner.close();
            this.scanner = null;
        }
        if (this.thread != null) {
            this.thread.interrupt();
            this.thread = null;
        }
    }

    private void stop() {
        this.stop = true;
    }

    public boolean isStop() {
        return stop;
    }
}
