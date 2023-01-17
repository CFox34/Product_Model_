import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductGenerator {
    public static void main(String[] args) {
        ArrayList<Product> productOData = new ArrayList<>();
        ArrayList<String> productCSVData = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\productData.txt");

        boolean done = false;
        String ID = "";
        String Name = "";
        String Description = "";
        String CSVProductRecord = "";
        double Cost = 0;

        do {
            ID = SafeInput.getNonZeroLenString(in, "Enter the ID [6 digits]:");
            Name = SafeInput.getNonZeroLenString(in, "Enter the name of the product:");
            Description = SafeInput.getNonZeroLenString(in, "Enter the Description: ");
            Cost = SafeInput.getRangedDouble(in, "Enter the Cost of the product: ", 0, 9999);

            CSVProductRecord = ID + ", " + Name + ", " + Description +  ", " + Cost;
            productCSVData.add(CSVProductRecord);

            productOData.add(new Product(ID, Name, Description, Cost));


            done = SafeInput.getYNConfirm(in, "Are you done?");
        } while (!done);

        for (String p : productCSVData)
            System.out.print(p);
    }
}


