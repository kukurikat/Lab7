package repository;

<<<<<<< HEAD:src/repository/VegetableRepository.java
import model.FruitVegetable;
import model.LeafyVegetable;
import model.RootVegetable;
import model.Vegetable;

=======
import model.*;
import util.Logger;
>>>>>>> 5cd2274deabb4552569df7ef86e5c76c8f0d8693:src/main/java/repository/VegetableRepository.java
import java.util.ArrayList;

public class VegetableRepository {
    private ArrayList<Vegetable> vegetables;
    private Logger logger;

    public VegetableRepository() {
        this.vegetables = new ArrayList<>();
        this.logger = Logger.getInstance();
        initializeDefaultVegetables();
        logger.info("VegetableRepository initialized with " + vegetables.size() + " default vegetables");
    }

    private void initializeDefaultVegetables() {
        vegetables.add(new FruitVegetable(1, "Помідор", 0, 18));
        vegetables.add(new FruitVegetable(2, "Огірок", 0, 15));
        vegetables.add(new RootVegetable(3, "Морква", 0, 41));
        vegetables.add(new LeafyVegetable(4, "Капуста", 0, 25));
        vegetables.add(new RootVegetable(5, "Цибуля", 0, 40));
        vegetables.add(new FruitVegetable(6, "Перець", 0, 27));
        vegetables.add(new LeafyVegetable(7, "Салат", 0, 14));
    }

    public void addVegetable(Vegetable vegetable) {
        try {
            vegetables.add(vegetable);
            logger.info("Vegetable added: ID=" + vegetable.getId() + ", Name=" + vegetable.getName());
        } catch (Exception e) {
            logger.error("Failed to add vegetable: " + e.getMessage());
        }
    }

    public boolean removeVegetable(int id) {
        try {
            boolean removed = vegetables.removeIf(v -> v.getId() == id);
            if (removed) {
                logger.info("Vegetable removed: ID=" + id);
            } else {
                logger.warn("Attempted to remove non-existent vegetable: ID=" + id);
            }
            return removed;
        } catch (Exception e) {
            logger.error("Error removing vegetable ID=" + id + ": " + e.getMessage());
            return false;
        }
    }

    public Vegetable getVegetableById(int id) {
        for (Vegetable veg : vegetables) {
            if (veg.getId() == id) {
                return veg;
            }
        }
        logger.warn("Vegetable not found: ID=" + id);
        return null;
    }

    public boolean updateVegetable(int id, Vegetable updatedVegetable) {
        try {
            for (int i = 0; i < vegetables.size(); i++) {
                if (vegetables.get(i).getId() == id) {
                    vegetables.set(i, updatedVegetable);
                    logger.info("Vegetable updated: ID=" + id);
                    return true;
                }
            }
            logger.warn("Attempted to update non-existent vegetable: ID=" + id);
            return false;
        } catch (Exception e) {
            logger.error("Error updating vegetable ID=" + id + ": " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Vegetable> getAllVegetables() {
        return new ArrayList<>(vegetables);
    }

    public Vegetable findVegetableByName(String name) {
        for (Vegetable veg : vegetables) {
            if (veg.getName().equalsIgnoreCase(name)) {
                return veg;
            }
        }
        return null;
    }

    public int getVegetableCount() {
        return vegetables.size();
    }

    public void clear() {
        int count = vegetables.size();
        vegetables.clear();
        logger.info("VegetableRepository cleared: " + count + " vegetables removed");
    }
}