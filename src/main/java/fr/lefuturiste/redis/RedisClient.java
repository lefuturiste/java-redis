package fr.lefuturiste.redis;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * TODO: Unit testing?
 * TODO: Make available on the maven repository ?
 * TODO: namespace
 * TODO: flush
 * TODO: count key
 * TODO: expiration
 * TODO: JSON support
 * TODO: injection protection from user input
 */
public class RedisClient {
    private final Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public RedisClient() throws IOException {
        this(6379);
    }

    public RedisClient(int port) throws IOException {
        this("127.0.0.1", port);
    }

    public RedisClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.output = new PrintWriter(this.socket.getOutputStream(), true);
    }

    public boolean auth(String password) throws IOException {
        this.output.println("AUTH " + password);
        String result = this.input.readLine();
        return result.equals("+OK");
    }

    /*
     * Get a specific key, return the string value associated with this key or return null if the key doesn't exist
     */
    public String get(String key) throws IOException {
        this.output.println("GET " + key);
        String address = this.input.readLine();
        if (address.equals("$-1")) {
            return null;
        }
        return this.input.readLine();
    }

    public boolean set(String key, String value) throws IOException {
        this.output.println("SET " + key + " \"" + value + "\"");
        return this.input.readLine().equals("+OK");
    }

    public boolean del(String key) throws IOException {
        this.output.println("DEL " + key);
        return this.input.readLine().equals(":1");
    }

    public boolean setJson(String key, JSONObject object) throws IOException {
        String jsonString = object.toString(0);
        jsonString = jsonString.replaceAll("'", "");
        this.output.println("SET " + key + " '" + jsonString + "'");
        return this.input.readLine().equals("+OK");
    }

    public JSONObject getJson(String key) throws IOException {
        if (this.get(key) == null) {
            return null;
        }
        String result = this.get(key);
        return new JSONObject(result);
    }

    public boolean ping() throws IOException {
        this.output.println("PING");
        return this.input.readLine().equals("+PONG");
    }

    public Boolean exists(String key) throws IOException {
        this.output.println("EXISTS " + key);
        return this.input.readLine().equals(":1");
    }

    public Socket getSocket() {
        return this.socket;
    }
}
