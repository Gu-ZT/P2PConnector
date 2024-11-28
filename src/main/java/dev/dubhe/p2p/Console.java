package dev.dubhe.p2p;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class Console implements AutoCloseable {
    private final Scanner scanner;
    private final Thread thread;
    private final Stack<Runnable> runnableStack = new Stack<>();
    private boolean stop = false;

    public Console() {
        this.scanner = new Scanner(System.in);
        this.thread = new Thread(() -> {
            while (!this.stop) {
                this.runnableStack.push(() -> this.onInput(this.scanner.nextLine()));
            }
        });
        this.thread.start();
    }

    public void onInput(String str) {
        System.out.println(str);
        if ("stop".equals(str)) this.stop();
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
    public void close() {
        this.scanner.close();
        this.thread.interrupt();
    }

    public void stop() {
        this.stop = true;
    }

    public boolean isStop() {
        return stop;
    }
}
