package vlad.skiba.javacore.consoleshop.main;

import vlad.skiba.javacore.consoleshop.exception.ApplicationGenericException;
import vlad.skiba.javacore.consoleshop.helper.CatalogInputOutputHelper;
import vlad.skiba.javacore.consoleshop.helper.CatalogSortingHelper;
import vlad.skiba.javacore.consoleshop.helper.OrdersMenuDisplayHelper;
import vlad.skiba.javacore.consoleshop.helper.SumOfTopFiveProductsHelper;
import vlad.skiba.javacore.consoleshop.helper.impl.CatalogInputOutputHelperImpl;
import vlad.skiba.javacore.consoleshop.helper.impl.CatalogSortingHelperImpl;
import vlad.skiba.javacore.consoleshop.helper.impl.OrdersMenuDisplayHelperImpl;
import vlad.skiba.javacore.consoleshop.helper.impl.SumOfTopFiveProductsHelperImpl;
import vlad.skiba.javacore.consoleshop.model.Catalog;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.parser.Order;
import vlad.skiba.javacore.consoleshop.parser.SortingCriteriaXMLParser;
import vlad.skiba.javacore.consoleshop.repository.impl.CategoryRepositoryImpl;
import vlad.skiba.javacore.consoleshop.repository.impl.ProductRepositoryImpl;
import vlad.skiba.javacore.consoleshop.server.Server;
import vlad.skiba.javacore.consoleshop.service.CategoryService;
import vlad.skiba.javacore.consoleshop.service.OrderService;
import vlad.skiba.javacore.consoleshop.service.ProductService;
import vlad.skiba.javacore.consoleshop.service.impl.CategoryServiceImpl;
import vlad.skiba.javacore.consoleshop.service.impl.OrderServiceImpl;
import vlad.skiba.javacore.consoleshop.service.impl.ProductServiceImpl;
import vlad.skiba.javacore.consoleshop.service.impl.ServerServiceImpl;
import vlad.skiba.javacore.consoleshop.settings.ServerConnectionSettings;

import java.util.stream.Collectors;
import java.net.ServerSocket;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.net.Socket;
import java.util.*;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class Main {

    private static String separator = "-----------------------------------------------------------------------------\n";
    private static final String MENU = separator + "Program's menu: \n" + separator + "1. Create catalog " +
            "\n2. Create and add category to catalog \n3. Create and add product to existing category " +
            "\n4. Display catalog \n5. Display sorted catalog " +
            "\n6. Display sum of the top five most expensive products in category \n7. Display orders menu " +
            "\n8. Start server \n9. Exit ";
    private static final Path PATH_TO_XML_FILE = Paths.get("application/src/main/resources/sorting-criteria.xml");
    private static final CategoryService CATEGORY_SERVICE = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private static final ProductService PRODUCT_SERVICE = new ProductServiceImpl(new ProductRepositoryImpl());
    private static final CatalogInputOutputHelper CATALOG_HELPER = new CatalogInputOutputHelperImpl();
    private static final CatalogSortingHelper CATALOG_SORTING_HELPER = new CatalogSortingHelperImpl();
    private static final OrderService ORDER_SERVICE = new OrderServiceImpl(CATEGORY_SERVICE);
    private static final SumOfTopFiveProductsHelper SUM_OF_TOP_FIVE_PRODUCTS_HELPER = new SumOfTopFiveProductsHelperImpl();
    private static final OrdersMenuDisplayHelper ORDERS_MENU_DISPLAY_HELPER = new OrdersMenuDisplayHelperImpl(ORDER_SERVICE);
    private static final Scanner USER_INPUT_SCANNER = new Scanner(System.in);
    private static Catalog catalog;

    public static void main(String[] args) {
        boolean isContinue = true;
        while (isContinue) {
            System.out.println(MENU);
            System.out.print(separator + "Enter menu item: \n" + separator);
            int choice = USER_INPUT_SCANNER.nextInt();
            try {
                isContinue = handleChoice(choice);
            } catch (ApplicationGenericException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                handleCreateCatalog();
                return true;
            case 2:
                handleCreateCategory();
                return true;
            case 3:
                handleCreateProduct();
                return true;
            case 4:
                handleDisplayCatalog();
                return true;
            case 5:
                handleDisplaySortedCatalog();
                return true;
            case 6:
                handleDisplaySumOfTopFiveProductsPrices();
                return true;
            case 7:
                handleDisplayOrdersMenu();
                return true;
            case 8:
                handleCreateMultiClientsConnectionToServer();
                return true;
            case 9:
                return false;
            default:
                throw new ApplicationGenericException("Incorrect input... Reenter menu item! ");
        }
    }

    private static void checkCatalogExistence() {
        if (catalog == null) {
            throw new ApplicationGenericException("The catalog doesn't exist! Please, add it first! ");
        }
    }

    private static void initializeCatalogWithDataFromDB() {
        catalog.setCategories(CATEGORY_SERVICE.findAll());
    }

    private static Optional<Category> findCategoryByName(String categoryName) {
        return catalog.getCategories()
                .stream()
                .filter(category -> Objects.equals(categoryName, category.getName()))
                .findFirst();
    }

    private static void displaySortingCriteria(LinkedHashMap<String, Order> sortingCriteriaMap) {
        System.out.println("Sorting criteria: ");
        System.out.print(separator);
        sortingCriteriaMap.forEach(
                (fieldName, order) -> System.out.printf("Field: %s \nOrder: %s \n%s", fieldName, order.toString(), separator)
        );
    }

    private static void displayCategory(Category category) {
        System.out.print(separator);
        System.out.printf("Category \"%s\" contains %d products. \n",
                category.getName(), category.getProducts().size());
        System.out.printf("Sum of the top five most expensive products in category \"%s\" = %.1f \n",
                category.getName(), SUM_OF_TOP_FIVE_PRODUCTS_HELPER.sumOfTopFiveProductsPrices(category));
        System.out.print(separator);
    }

    private static void handleCreateCatalog() {
        catalog = CATALOG_HELPER.createCatalog();
        checkCatalogExistence();
        initializeCatalogWithDataFromDB();
        System.out.printf("Catalog \"%s\" was successfully created! \n", catalog.getCatalogName());
    }

    private static void handleCreateCategory() {
        checkCatalogExistence();
        Category category = CATALOG_HELPER.createCategory();
        findCategoryByName(category.getName()).ifPresent(
                ignored -> {
                    throw new ApplicationGenericException("Category already exists! ");
                }
        );
        CATEGORY_SERVICE.save(category);
        catalog.getCategories().add(category);
        System.out.println("Category was successfully created and added to Catalog! ");
    }

    private static void handleCreateProduct() {
        checkCatalogExistence();
        System.out.println("Enter category name: ");
        String categoryName = USER_INPUT_SCANNER.next();
        findCategoryByName(categoryName).ifPresentOrElse(
                category -> {
                    Product product = CATALOG_HELPER.createProduct(category.getId());
                    PRODUCT_SERVICE.save(product);
                    category.getProducts().add(product);
                },
                () -> {
                    throw new ApplicationGenericException("Category doesn't exists. Please, add it first! ");
                }
        );
        System.out.printf("Product was successfully added to Category \"%s\" \n", categoryName);
    }

    private static void handleDisplayCatalog() {
        checkCatalogExistence();
        Map<String, List<Product>> unsortedCatalog = catalog.getCategories()
                .stream()
                .collect(Collectors.toMap(Category::getName, Category::getProducts));
        CATALOG_HELPER.displayCatalog(unsortedCatalog);
    }

    private static void handleDisplaySortedCatalog() {
        checkCatalogExistence();
        LinkedHashMap<String, Order> sortingCriteriaMap = SortingCriteriaXMLParser.parse(PATH_TO_XML_FILE);
        displaySortingCriteria(sortingCriteriaMap);
        Map<String, List<Product>> sortedCatalog = CATALOG_SORTING_HELPER.sortCatalogByCriteria(catalog, sortingCriteriaMap);
        System.out.print("Sorted ");
        CATALOG_HELPER.displayCatalog(sortedCatalog);
    }

    private static void handleDisplaySumOfTopFiveProductsPrices() {
        checkCatalogExistence();
        for (Category category : catalog.getCategories()) {
            displayCategory(category);
        }
    }

    private static void handleDisplayOrdersMenu() {
        ORDERS_MENU_DISPLAY_HELPER.displayOrdersMenu();
    }

    private static void handleCreateMultiClientsConnectionToServer() {
        try (ServerSocket serverSocket = new ServerSocket(ServerConnectionSettings.PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread serverThread = new Thread(new Server(new ServerServiceImpl(ORDER_SERVICE), clientSocket, catalog));
                serverThread.start();
            }
        } catch (IOException ex) {
            throw new ApplicationGenericException("Problem with server starting. Check server connection settings! ", ex);
        }
    }

}