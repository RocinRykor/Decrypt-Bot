package studio.rrprojects.decryptbot.generators.matrix.containers;

import java.util.ArrayList;

public class SheafStep {
    private final int currentStep;
    private String title;
    private ArrayList<ICProgram> icList = new ArrayList();
    private boolean isConstruct = false;
    private boolean isPartyCluster = false;

    public SheafStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addIC(ICProgram icProgram) {
        icList.add(icProgram);
    }

    public boolean isConstruct() {
        return isConstruct;
    }

    public void setConstruct(boolean construct) {
        isConstruct = construct;
    }

    public boolean isPartyCluster() {
        return isPartyCluster;
    }

    public void setPartyCluster(boolean partyCluster) {
        isPartyCluster = partyCluster;
    }

    public ArrayList<ICProgram> getICList() {
        return icList;
    }

    public String listIC() {
        if (icList. size() == 0) {
            return "";
        } else if (icList.size() == 1) {
            return icList.get(0).toString();
        } else {
            return icList.toString();
        }
    }
}
