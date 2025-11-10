package service;

import model.*;
import repository.SaladRepository;
import repository.VegetableRepository;
import util.Logger;
import java.util.ArrayList;

public class SaladService {
    private VegetableRepository vegetableRepository;
    private SaladRepository saladRepository;
    private Logger logger;

    public SaladService(VegetableRepository vegetableRepository, SaladRepository saladRepository) {
        this.vegetableRepository = vegetableRepository;
        this.saladRepository = saladRepository;
        this.logger = Logger.getInstance();
        logger.info("SaladService initialized");
    }

    public Salad createSalad(String name, int id) {
        try {
            Salad salad = new Salad(id, name);
            saladRepository.addSalad(salad);
            logger.info("Salad created: ID=" + id + ", Name=" + name);
            return salad;
        } catch (Exception e) {
            logger.critical("Failed to create salad: " + name, e);
            throw e;
        }
    }

    public void addVegetableToSalad(Salad salad, int vegetableId, double weight) {
        try {
            Vegetable template = vegetableRepository.getVegetableById(vegetableId);
            if (template == null) {
                logger.warn("Attempted to add non-existent vegetable ID=" + vegetableId + " to salad ID=" + salad.getId());
                return;
            }

            Vegetable vegetable;
            if (template instanceof FruitVegetable) {
                vegetable = new FruitVegetable(template.getId(), template.getName(),
                        weight, template.getCaloriesPer100g());
            } else if (template instanceof RootVegetable) {
                vegetable = new RootVegetable(template.getId(), template.getName(),
                        weight, template.getCaloriesPer100g());
            } else {
                vegetable = new LeafyVegetable(template.getId(), template.getName(),
                        weight, template.getCaloriesPer100g());
            }
            salad.addVegetable(vegetable);
            logger.info("Vegetable added to salad: SaladID=" + salad.getId() +
                    ", VegetableID=" + vegetableId + ", Weight=" + weight);
        } catch (Exception e) {
            logger.critical("Failed to add vegetable to salad", e);
            throw e;
        }
    }

    public void removeVegetableFromSalad(Salad salad, int index) {
        try {
            if (index < 0 || index >= salad.getVegetableCount()) {
                logger.warn("Invalid index for vegetable removal: index=" + index +
                        ", saladID=" + salad.getId());
                return;
            }
            salad.removeVegetable(index);
            logger.info("Vegetable removed from salad: SaladID=" + salad.getId() +
                    ", Index=" + index);
        } catch (Exception e) {
            logger.error("Error removing vegetable from salad: " + e.getMessage());
        }
    }

    public void displaySalad(Salad salad) {
        if (salad == null) {
            logger.warn("Attempted to display null salad");
            System.out.println("Салат не знайдено!");
            return;
        }
        System.out.println("\n=== Інформація про салат ===");
        System.out.println(salad);
        System.out.println("\nОвочі в салаті:");
        if (salad.getVegetables().isEmpty()) {
            System.out.println("Салат порожній");
        } else {
            for (int i = 0; i < salad.getVegetables().size(); i++) {
                System.out.println((i + 1) + ". " + salad.getVegetables().get(i));
            }
        }
        logger.info("Salad displayed: ID=" + salad.getId());
    }

    public void displayAllVegetablesInSalad(Salad salad) {
        if (salad == null || salad.getVegetables().isEmpty()) {
            System.out.println("Салат порожній!");
            logger.warn("Attempted to display vegetables from empty salad");
            return;
        }
        System.out.println("\n=== Овочі в салаті '" + salad.getName() + "' ===");
        for (int i = 0; i < salad.getVegetables().size(); i++) {
            System.out.println((i + 1) + ". " + salad.getVegetables().get(i));
        }
    }

    public double calculateCalories(Salad salad) {
        try {
            double calories = salad.calculateTotalCalories();
            logger.info("Calories calculated for salad ID=" + salad.getId() +
                    ": " + calories + " kcal");
            return calories;
        } catch (Exception e) {
            logger.error("Error calculating calories: " + e.getMessage());
            return 0.0;
        }
    }

    public void sortSaladByCalories(Salad salad, String order) {
        try {
            salad.sortVegetablesByCalories();
            logger.info("Salad sorted by calories: ID=" + salad.getId());
        } catch (Exception e) {
            logger.error("Error sorting salad by calories: " + e.getMessage());
        }
    }

    public void sortSaladByWeight(Salad salad) {
        try {
            salad.sortVegetablesByWeight();
            logger.info("Salad sorted by weight: ID=" + salad.getId());
        } catch (Exception e) {
            logger.error("Error sorting salad by weight: " + e.getMessage());
        }
    }

    public ArrayList<Vegetable> findVegetablesByCalorieRange(Salad salad, double min, double max) {
        try {
            ArrayList<Vegetable> result = salad.findVegetablesByCalorieRange(min, max);
            logger.info("Found " + result.size() + " vegetables in calorie range [" +
                    min + "-" + max + "] for salad ID=" + salad.getId());
            return result;
        } catch (Exception e) {
            logger.error("Error finding vegetables by calorie range: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void deleteSalad(int id) {
        try {
            boolean removed = saladRepository.removeSalad(id);
            if (removed) {
                logger.info("Salad deleted: ID=" + id);
            } else {
                logger.warn("Attempted to delete non-existent salad: ID=" + id);
            }
        } catch (Exception e) {
            logger.error("Error deleting salad ID=" + id + ": " + e.getMessage());
        }
    }

    public Salad getSaladById(int id) {
        try {
            Salad salad = saladRepository.getSaladById(id);
            if (salad == null) {
                logger.warn("Salad not found: ID=" + id);
            }
            return salad;
        } catch (Exception e) {
            logger.error("Error getting salad by ID=" + id + ": " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Salad> getAllSalads() {
        try {
            ArrayList<Salad> salads = saladRepository.getAllSalads();
            logger.info("Retrieved all salads: count=" + salads.size());
            return salads;
        } catch (Exception e) {
            logger.error("Error getting all salads: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean deleteSaladInt() {
        return false;
    }
}