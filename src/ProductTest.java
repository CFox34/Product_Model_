import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductTest {

    private static final String FILENAME = "productData.txt";

    public static void main(String[] args) {
        ArrayList<Product> products = readFile(FILENAME);
        Scanner sc = new Scanner(System.in);
        int choice;
        do
        {
            displayMenu();
            choice = Integer.parseInt(sc.nextLine().trim());
            switch(choice)
            {
                case 1:
                {
                    System.out.println("\nADD PRODUCT:\n"
                            + "------------");
                    addProduct(products);
                    break;
                }
                case 2:
                {
                    System.out.println("\nUPDATE PRODUCT:\n"
                            + "---------------");
                    updateProduct(products);
                    break;
                }
                case 3:
                {
                    System.out.println("\nDISPLAY ALL PRODUCTS:\n"
                            + "---------------------");
                    displayALLProducts(products);
                    break;
                }
                case 4:
                {
                    System.out.println("\nSAVE PRODUCTS TO FILE:\n"
                            + "----------------------");
                    writeToFile(products, FILENAME);
                    break;
                }
                case 0:
                {
                    System.out.println("Thanks..Goodbye!\n");
                    System.exit(0);
                }
                default:
                    System.out.println("\nInvalid selection!\n");
            }
        }while(choice != 0);
    }

    private static ArrayList<Product> readFile(String filename)
    {
        ArrayList<Product> res = new ArrayList<>();
        Scanner fileReader;
        try
        {
            fileReader = new Scanner(new File(filename));
            while(fileReader.hasNextLine())
            {
                String[] data = fileReader.nextLine().trim().split(",");
                String ID = data[0];
                String name = data[1];
                String description = data[2];
                double cost = Double.parseDouble(data[3]);
                res.add(new Product(ID, name, description, cost));
            }
            fileReader.close();
        }catch(FileNotFoundException fnfe){
            System.out.println(filename + " could not be found! Exiting..");
            System.exit(0);
        }
        return res;
    }

    private static int getIndex(ArrayList<Product> prods, String id)
    {
        int index = -1;
        for(int i = 0; i < prods.size(); i++)
        {
            if(prods.get(i).getID().equals(id))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    private static void addProduct(ArrayList<Product> prods)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product id: ");
        String ID = sc.nextLine().trim();
        int index = getIndex(prods, ID);
        if(index == -1)
        {
// product not present, good to add
            System.out.print("Enter product name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter product description: ");
            String desc = sc.nextLine().trim();
            System.out.print("Enter product cost: $");
            double cost = Double.parseDouble(sc.nextLine().trim());
            prods.add(new Product(ID, name, desc, cost));
            System.out.println(name + " is added to list successfully.\n");
        }
        else
            System.out.println("Sorry, this product is already present!\n");
    }

    private static void updateProduct(ArrayList<Product> prods)
    {
        if(prods.isEmpty())
        {
            System.out.println("No products added to list!\n");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product id: ");
        String ID = sc.nextLine().trim();
        int index = getIndex(prods, ID);
        if(index == -1)
            System.out.println("Sorry, no such product found!\n");
        else
        {
            System.out.print("Enter new product name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter new product description: ");
            String desc = sc.nextLine().trim();
            System.out.print("Enter new product cost: $");
            double cost = Double.parseDouble(sc.nextLine().trim());
            prods.get(index).setName(name);
            prods.get(index).setDescription(desc);
            prods.get(index).setCost(cost);
            System.out.println("Data successfully updated for product ID " + ID + ".\n");
        }
    }

    private static void displayALLProducts(ArrayList<Product> prods)
    {
        if(prods.isEmpty())
        {
            System.out.println("No products added to list!\n");
            return;
        }
        for(Product p : prods)
            System.out.println(p);
        System.out.println();
    }

    private static void writeToFile(ArrayList<Product> prods, String filename)
    {
        if(prods.isEmpty())
        {
            System.out.println("No products added to list!\n");
            return;
        }
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(new File(filename));
            pw = new PrintWriter(fw);
            for(Product p : prods)
                pw.write(p.toFileStr() + System.lineSeparator());
            pw.flush();
            fw.close();
            pw.close();
            System.out.println("All products have been successfully written to " + filename + ".\n");
        } catch (IOException ex) {
            System.out.println("Error in writing product data to " + filename + "! Exiting..");
            System.exit(0);
        }
    }

    private static void displayMenu()
    {
        System.out.print("Choose from the following options:\n"
                + "1. Add product\n"
                + "2. Update product\n"
                + "3. Display all products\n"
                + "4. Save products to file\n"
                + "0. Exit\n"
                + "Your selection >> ");
    }
}
