package edu.bbte.idde.kzim2149;

import edu.bbte.idde.kzim2149.dao.Dao;
import edu.bbte.idde.kzim2149.dao.AbstractDaoFactory;
import edu.bbte.idde.kzim2149.dao.PCPartsDaoI;
import edu.bbte.idde.kzim2149.dao.mem.InMemoryPCPartsDao;
import edu.bbte.idde.kzim2149.model.PCPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryPCPartsDao.class);

    public static void printMenu() {
        LOG.info("1. Add");
        LOG.info("2. Remove");
        LOG.info("3. Update");
        LOG.info("4. List");
        LOG.info("5. Search");
        LOG.info("6. Exit");
        LOG.info("Choose: ");
    }

    public static void main(String[] args) throws IOException {
        PCPartsDaoI myDB = AbstractDaoFactory.getInstance().getPCPartsDao();
        String input;

        printMenu();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = 0;
        while (choice != 6) {
            input = getInput(bufferedReader);
            if (input == null) {
                continue;
            }
            choice = choose(myDB, input, bufferedReader);
            if (choice == 0) {
                continue;
            }
        }
    }

    private static int choose(PCPartsDaoI myDB, String input, BufferedReader bufferedReader) throws IOException {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            LOG.warn("Invalid choice!");
            return 0;
        }
        switch (choice) {
            case 1:
                addModel(myDB, bufferedReader);
                break;
            case 2:
                removeModel(myDB, bufferedReader);
                break;
            case 3:
                updateModel(myDB, bufferedReader);
                break;
            case 4:
                listModels(myDB);
                break;
            case 5:
                searchModelById(myDB, bufferedReader);
                break;
            case 6:
                break;
            default:
                LOG.info("Wrong choice!");
                break;
        }
        if (choice != 6) {
            printMenu();
        }
        return choice;
    }

    private static String getInput(BufferedReader bufferedReader) throws IOException {
        String input;
        input = bufferedReader.readLine();
        if (input == null) {
            LOG.warn("Wrong choice!");
            LOG.info("Choose: ");
            return null;
        }
        while (input == null || !input.matches("[0-9]+")) {
            LOG.warn("Wrong choice!");
            LOG.info("Choose: ");
            input = bufferedReader.readLine();
        }
        return input;
    }

    private static void searchModelById(PCPartsDaoI myDB, BufferedReader bufferedReader) throws IOException {
        String id;
        id = readId(bufferedReader);
        if (id == null) {
            LOG.info("Id must be a number!");
            return;
        }
        Long longId;
        try {
            longId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            LOG.warn("Invalid id: " + id);
            return;
        }
        PCPart result = myDB.findById(longId);
        if (result != null) {
            LOG.info(result.toString());
            return;
        }
        LOG.info("Not found!");
    }

    private static void listModels(Dao myDB) {
        Collection<PCPart> list = myDB.findAll();
        for (PCPart model : list) {
            LOG.info(model.toString());
        }
    }

    private static PCPart updateModel(Dao myDB, BufferedReader bufferedReader) throws IOException {
        String id = readId(bufferedReader);
        if (id == null) {
            return null;
        }

        Long longId;
        try {
            longId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            LOG.warn("Invalid id: " + id);
            return null;
        }

        if (myDB.findById(longId) == null) {
            LOG.warn("Not found!");
            return null;
        }
        String name = readName(bufferedReader);

        String producer = readProducer(bufferedReader);

        String type = readType(bufferedReader);

        String price = readPrice(bufferedReader);

        String weight = readWeight(bufferedReader);
        if (checkNullInputs(name, producer, type, price, weight)) {
            return null;
        }

        PCPart updatedModel = new PCPart(name, producer, type, Integer.parseInt(price), Integer.parseInt(weight));
        myDB.update(longId, updatedModel);
        return updatedModel;
    }

    private static boolean checkNullInputs(String name, String producer, String type, String price, String weight) {
        return name == null || producer == null || type == null || price == null || weight == null;
    }

    private static String readPrice(BufferedReader bufferedReader) throws IOException {
        String price;
        LOG.info("Price: ");
        price = bufferedReader.readLine();
        if (price == null) {
            LOG.info("Price must be a number!");
            return null;
        }
        while (price == null || !price.matches("[0-9]+")) {
            LOG.info("Price must be a number!");
            LOG.info("Price: ");
            price = bufferedReader.readLine();
        }
        return price;
    }

    private static String readWeight(BufferedReader bufferedReader) throws IOException {
        String weight;
        LOG.info("Weight: ");
        weight = bufferedReader.readLine();
        if (weight == null) {
            LOG.info("Weight must be a number!");
            return null;
        }
        while (weight == null || !weight.matches("[0-9]+")) {
            LOG.info("Weight must be a number!");
            LOG.info("Weight: ");
            weight = bufferedReader.readLine();
        }
        return weight;
    }

    private static String readType(BufferedReader bufferedReader) throws IOException {
        String type;
        LOG.info("Type: ");
        type = bufferedReader.readLine();
        if (type == null) {
            LOG.warn("Type must be a string!");
            return null;
        }
        return type;
    }

    private static String readProducer(BufferedReader bufferedReader) throws IOException {
        String producer;
        LOG.info("Producer: ");
        producer = bufferedReader.readLine();
        if (producer == null) {
            LOG.warn("Producer must be a string!");
            return null;
        }
        return producer;
    }

    private static String readName(BufferedReader bufferedReader) throws IOException {
        String name;
        LOG.info("Name: ");
        name = bufferedReader.readLine();
        if (name == null) {
            LOG.info("Name must be a string!");
            return null;
        }
        return name;
    }

    private static String readId(BufferedReader bufferedReader) throws IOException {
        String id;
        LOG.info("Id: ");
        id = bufferedReader.readLine();
        if (id == null || !id.matches("[0-9]+")) {
            LOG.warn("Id must be a number!");
            return null;
        }
        return id;
    }

    private static void removeModel(Dao myDB, BufferedReader bufferedReader) throws IOException {
        String id;
        LOG.info("Id: ");
        id = bufferedReader.readLine();

        Long longId;
        try {
            longId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            LOG.warn("Invalid id: " + id);
            return;
        }

        myDB.delete(longId);
    }

    private static void addModel(Dao myDB, BufferedReader bufferedReader) throws IOException {
        String name;
        name = readName(bufferedReader);

        String producer;
        producer = readProducer(bufferedReader);

        String type;
        type = readType(bufferedReader);

        String price;
        price = readPrice(bufferedReader);

        String weight;
        weight = readWeight(bufferedReader);

        myDB.insert(new PCPart(name, producer, type, Integer.parseInt(price), Integer.parseInt(weight)));
    }
}
