package vlad.skiba.javacore.consoleshop;

import vlad.skiba.javacore.consoleshop.exception.ConnectionException;
import vlad.skiba.javacore.consoleshop.settings.ServerConnectionSettings;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.Socket;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class Client {

    private static final ClientMenuDisplayHelper clientMenuDisplayHelper = new ClientMenuDisplayHelper();

    public static void main(String[] args) {
        connectToServer();
    }

    private static void connectToServer() {
        try (Socket socket = new Socket(ServerConnectionSettings.HOST, ServerConnectionSettings.PORT);
             ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
             Scanner userInputScanner = new Scanner(System.in)) {
            clientMenuDisplayHelper.displayClientMenu(serverOutputStream, serverInputStream, userInputScanner);
        } catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("Problem with client connection to server. Check server connection settings! ", ex);
        }
    }

}