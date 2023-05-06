package studio.rrprojects.decryptbot.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ChoreListController {
    private HashMap<Integer, ArrayList<String>> choreMap;

    public ChoreListController() {
        choreMap = new HashMap<>();
        choreMap.put(0, new ArrayList<>()); // Not Started
        choreMap.put(1, new ArrayList<>()); // Started
        choreMap.put(2, new ArrayList<>()); // Completed
    }

    public void addChore(String title) {
        choreMap.get(0).add(title); // Add to Not Started list by default
    }

    public boolean removeChore(String title) {
        // Remove from the corresponding status list in choreMap
        for (ArrayList<String> statusList : choreMap.values()) {
            if (statusList.remove(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean restartChore(String title) {
        return updateChoreStatus(title, 0);
    }

    public boolean startChore(String title) {
        return updateChoreStatus(title, 1);
    }

    public boolean completeChore(String title) {
        return updateChoreStatus(title, 2);
    }

    private boolean updateChoreStatus(String title, int statusCode) {
        ArrayList<String> currentStatusList = null;
        for (ArrayList<String> statusList : choreMap.values()) {
            if (statusList.remove(title)) {
                currentStatusList = statusList;
                break;
            }
        }
        if (currentStatusList != null) {
            choreMap.get(statusCode).add(title);
            return true;
        }
        return false;
    }

    public ArrayList<String> getChoresByStatus(int statusCode) {
        return choreMap.getOrDefault(statusCode, new ArrayList<>());
    }

    public HashMap<Integer, ArrayList<String>> getChoreMap() {
        return choreMap;
    }

    public String getRandomChoreAndStart() {
        ArrayList<String> notStartedChores = choreMap.get(0);
        if (notStartedChores.isEmpty()) {
            return null; // No chores available
        }
        Random random = new Random();
        int randomIndex = random.nextInt(notStartedChores.size());
        String randomChore = notStartedChores.get(randomIndex);
        notStartedChores.remove(randomIndex);
        choreMap.get(1).add(randomChore);
        return randomChore;
    }
}
