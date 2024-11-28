package dev.dubhe.p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Connector implements Runnable, AutoCloseable {
    protected int port = -1;
    protected ServerSocket server;
    protected Socket socket2S;
    protected Socket socket2C;

    protected Connector() {
    }

    protected Connector(int port) {
        this();
        this.port = port;
    }

    protected void startServer() throws IOException {
        if (this.port == -1) {
            this.server = new ServerSocket();
            this.port = this.server.getLocalPort();
        } else this.server = new ServerSocket(this.port);
        System.out.printf("Server is started on 0.0.0.0:%s", this.port);
    }

    protected void connect2S(String host, int port) throws IOException {
        this.socket2S = new Socket(host, port);
        System.out.printf("connect to server %s:%s", host, port);
    }

    protected void connect2C(String host, int port) throws IOException {
        this.socket2C = new Socket(host, port);
        System.out.printf("connect to client %s:%s", host, port);
    }

    @Override
    public void close() throws Exception {
        if (this.server != null) this.server.close();
        if (this.socket2S != null) this.socket2S.close();
        if (this.socket2C != null) this.socket2C.close();
    }
}
