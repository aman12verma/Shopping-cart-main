import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingApp {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Product> storeInventory = new ArrayList<>();
    static ArrayList<Product> cart = new ArrayList<>();

    public static void main(String[] args) {
        displayMainMenu();
    }

    static void displayMainMenu() {
        System.out.println("Welcome to Tezo company \uD83D\uDED2 shopping cart.");
        System.out.println("Choose from the following options:");
        System.out.println("\nMain Menu");
        System.out.println("1. Manage Store");
        System.out.println("2. Sell Items");
        System.out.println("3. Exit");

        System.out.print("\nEnter your option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                manageStore();
                break;
            case "2":
                sellItemsMenu();
                break;
            case "3":
                System.out.println("Exiting the program.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                displayMainMenu();
        }
    }

    static void manageStore() {
        System.out.println("\nManage Store Menu");
        System.out.println("1. Add Product(s)");
        System.out.println("2. Back");
        System.out.println("3. Exit");

        System.out.print("\nEnter your option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                addProduct();
                break;
            case "2":
                displayMainMenu();
                break;
            case "3":
                System.out.println("Exiting the program.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                manageStore();
        }
    }

    static void addProduct() {
        System.out.print("\nEnter Product details:\nID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());

        Product product = new Product(id, name, price, qty, qty);
        storeInventory.add(product);

        System.out.println("Product added successfully!");

        System.out.print("\nDo you want to add another product? (Y/N): ");
        String answer = scanner.nextLine();

        if (answer.toUpperCase().equals("Y")) {
            addProduct();
        } else {
            manageStore();
        }
    }

    static void sellItemsMenu() {
        System.out.println("\nSell Items Menu");
        System.out.println("1. Add to Cart");
        System.out.println("2. Checkout");
        System.out.println("3. Back");
        System.out.println("4. Exit");
        System.out.println("5. Remove");

        System.out.print("\nEnter your option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                addToCart();
                break;
            case "2":
                checkout();
                break;
            case "3":
                displayMainMenu();
                break;
            case "4":
                System.out.println("Exiting the program.");
                break;
            case "5":
                cart.clear();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                sellItemsMenu();
        }
    }

    static void addToCart() {
        System.out.println("\nProduct List:");
        System.out.println("ID   Product   Price   quantity   orgQ");

        for (Product product : storeInventory) {
            System.out.printf("%s    %s    %.2f     %d       %d%n",
                    product.getId(), product.getName(), product.getPrice(), product.getQty(), product.getOrgQ());
        }

        System.out.print("\nSelect item (Enter ID): ");
        String selectedId = scanner.nextLine();

        Product selectedProduct = storeInventory.stream()
                .filter(product -> product.getId().equals(selectedId))
                .findFirst()
                .orElse(null);

        if (selectedProduct != null) {
            System.out.print("Enter quantity to add to cart: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity > 0 && quantity <= selectedProduct.getQty()) {
                Product cartItem = new Product(
                        selectedProduct.getId(), selectedProduct.getName(),
                        selectedProduct.getPrice(), quantity, quantity);

                selectedProduct.setQty(selectedProduct.getQty() - cartItem.getQty());

                cart.add(cartItem);
                System.out.println(selectedProduct.getName() + " added to cart!");

                System.out.print("\nDo you want to add another item to cart? (Y/N): ");
                String answer = scanner.nextLine();

                if (answer.toUpperCase().equals("Y")) {
                    addToCart();
                } else {
                    sellItemsMenu();
                }
            } else {
                System.out.println("Invalid quantity. Please enter a valid quantity.");
                addToCart();
            }
        } else {
            System.out.println("Invalid product ID. Please try again.");
            addToCart();
        }
    }

    static void checkout() {
        System.out.println("\nCheckout");
        System.out.println("ID   Product    Quantity   Price");

        for (Product item : cart) {
            System.out.printf("%s   %s        %d         %.2f%n",
                    item.getId(), item.getName(), item.getQuantity(), item.getPrice());
        }

        double totalPrice = cart.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
        System.out.printf("%nTotal Price: %.2f%n", totalPrice);

        System.out.print("\nDo you want to add another item to cart? (Y/N): ");
        String answer = scanner.nextLine();

        if (answer.toUpperCase().equals("Y")) {
            addToCart();
        } else {
            sellItemsMenu();
        }
    }
}

class Product {
    private String id;
    private String name;
    private double price;
    private int qty;
    private int orgQ;

    public Product(String id, String name, double price, int qty, int orgQ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.orgQ = orgQ;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public int getOrgQ() {
        return orgQ;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQuantity() {
        return qty;
    }
}
