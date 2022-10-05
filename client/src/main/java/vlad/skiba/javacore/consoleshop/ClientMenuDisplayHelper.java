package vlad.skiba.javacore.consoleshop;

import vlad.skiba.javacore.consoleshop.exception.ApplicationGenericException;
import vlad.skiba.javacore.consoleshop.helper.CatalogInputOutputHelper;
import vlad.skiba.javacore.consoleshop.helper.impl.CatalogInputOutputHelperImpl;
import vlad.skiba.javacore.consoleshop.model.Catalog;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.order.Order;

import java.util.stream.Collectors;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class ClientMenuDisplayHelper {

    private static String separator = "-----------------------------------------------------------------------------\n";
    private static final String MENU = separator + "Client menu: \n" + separator + "1. Display catalog " +
            "\n2. Create order \n3. Display orders \n4. Disconnect client and exit from client menu";
    private static final CatalogInputOutputHelper CATALOG_HELPER = new CatalogInputOutputHelperImpl();

    public void displayClientMenu(ObjectOutputStream serverOutputStream, ObjectInputStream serverInputStream,
                                  Scanner userInputScanner) throws IOException, ClassNotFoundException {
        boolean isContinue = true;
        while (isContinue) {
            System.out.println(MENU);
            System.out.print(separator + "Enter client menu item: \n" + separator);
            int clientChoice = userInputScanner.nextInt();
            try {
                isContinue = handleChoice(serverOutputStream, serverInputStream, userInputScanner, clientChoice);
            } catch (ApplicationGenericException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private boolean handleChoice(ObjectOutputStream serverOutputStream, ObjectInputStream serverInputStream,
                                 Scanner userInputScanner, int clientChoice) throws IOException, ClassNotFoundException {
        switch (clientChoice) {
            case 1:
                serverOutputStream.writeObject(clientChoice);
                displayCatalog(serverInputStream);
                return true;
            case 2:
                serverOutputStream.writeObject(clientChoice);
                createOrder(serverOutputStream, userInputScanner);
                return true;
            case 3:
                serverOutputStream.writeObject(clientChoice);
                displayOrders(serverInputStream);
                return true;
            case 4:
                serverOutputStream.writeObject(clientChoice);
                return false;
            default:
                throw new ApplicationGenericException("Incorrect input... Reenter client menu item! ");
        }
    }

    private void displayCatalog(ObjectInputStream serverInputStream) throws IOException, ClassNotFoundException {
        Catalog catalog = (Catalog) serverInputStream.readObject();
        Map<String, List<Product>> unsortedCatalog = catalog.getCategories()
                .stream()
                .collect(Collectors.toMap(Category::getName, Category::getProducts));
        CATALOG_HELPER.displayCatalog(unsortedCatalog);
    }

    private void createOrder(ObjectOutputStream serverOutputStream, Scanner userInputScanner) throws IOException {
        System.out.println("Enter the name of the product for which you want to create an order: ");
        String productName = userInputScanner.nextLine();
        serverOutputStream.writeObject(productName);
    }

    private void displayOrders(ObjectInputStream serverInputStream) throws IOException, ClassNotFoundException {
        List<Order> orders = (List<Order>) serverInputStream.readObject();
        StringBuilder message = new StringBuilder();
        for (Order order : orders) {
            message.append(order.toString());
        }
        System.out.printf(separator + "List of orders: \n" + separator + "\n%s", message);
    }

}