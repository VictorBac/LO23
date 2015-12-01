package fr.utc.lo23.client.network.main;

public class Console {
    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void logn(String msg) {
        System.out.print(msg);
    }

    public static void err(String msg) {
        System.err.print(msg);
    }
}