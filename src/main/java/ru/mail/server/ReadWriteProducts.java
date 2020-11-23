package ru.mail.server;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ru.mail.server.entity.Product;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteProducts {


    public  static ArrayList<Product> getData() {
        ArrayList<Product> arrayList = null;
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data/products.json"));
            JsonElement jsonElement = jsonParser.parse(br);

            Type type = new TypeToken<List<Product>>() {
            }.getType();
            arrayList = gson.fromJson(jsonElement, type);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public  static void writeData(Product newProduct) throws IOException {
        ArrayList<Product> arrayList = getData();
        arrayList.add(newProduct);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter("src/main/resources/data/products.json")) {
            gson.toJson(arrayList, writer);
        }

    }
}
