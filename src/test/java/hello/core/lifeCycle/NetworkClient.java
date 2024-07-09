package hello.core.lifeCycle;

import lombok.Setter;

@Setter
public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("Calling constructor " + url);

    }

    // Call when service starts
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // Call when service ends
    public void disconnect() {
        System.out.println("disconnect: " + url);

    }

    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("initializing connect msg");
    }

    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
