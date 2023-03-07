package studio.rrprojects.decryptbot.generators.matrix;

import studio.rrprojects.decryptbot.constants.Matrix;
import studio.rrprojects.decryptbot.generators.matrix.containers.*;
import studio.rrprojects.decryptbot.utils.RollUtils;

import java.util.LinkedHashMap;


public class SecuritySheaf {

    private LinkedHashMap<Integer, String> alertLevelTable = new LinkedHashMap<>();

    public SecuritySheaf() {
        InitTables();
    }

    private void InitTables() {
        alertLevelTable.put(0, Matrix.NO_ALERT);
        alertLevelTable.put(1, Matrix.PASSIVE_ALERT);
        alertLevelTable.put(2, Matrix.ACTIVE_ALERT);
        alertLevelTable.put(3, Matrix.SHUTDOWN);
    }

    public void generateSheaf(int hostLevel, int securityRating, Boolean hasNastySurprises) {
        int alertLevel = 0; //0 = No Alert, 1 = Passive, 2 = Active, 3 = Shutdown
        int stepsSinceLastAlert = 0;
        int currentStep = 0;

        while (alertLevel < 3) { // Has not yet reached Shutdown Status
        /*
        Step 1: Trigger Step
         */
            currentStep += RollTriggerStep(hostLevel);
            SheafStep sheafStep = new SheafStep(currentStep);

        /*
        Step 2: Alert Table
         */
            AlertContainer alertContainer = RollAlertTable(alertLevel, stepsSinceLastAlert, false);
            boolean generateIC = true;

            if (alertContainer.isAlertStep) {
                stepsSinceLastAlert = 0;
                alertLevel += 1;

                System.out.println(currentStep + " -> Alert Status: " + alertLevelTable.get(alertLevel));

                sheafStep.setTitle(alertLevelTable.get(alertLevel));

                //If A host is Blue or Green or has reach shutdown it won't generate more IC on an Alert Step
                if (hostLevel <= 1 || alertLevel == 3) {
                    generateIC = false;

                } else {
                    //We need to generate IC to go with the alert
                    alertContainer = RollAlertTable(alertLevel, stepsSinceLastAlert, true);
                }

                // sheafStep.appendTitle()
            } else {
                stepsSinceLastAlert += 1;
            }

            if (generateIC) {
        /*
        Step 3: Generate IC
         */
                sheafStep.addIC(ProcessIC(alertContainer, securityRating));
            }

            System.out.println(currentStep + ": " + sheafStep.listIC());
        }

    }

    private ICProgram ProcessIC(AlertContainer alertContainer, int securityRating) {
        String level = alertContainer.levelIC;

        if (level.equalsIgnoreCase(Matrix.WHITE)) {
            return new WhiteIC(alertContainer.categoryIC, securityRating);
        } else if (level.equalsIgnoreCase(Matrix.GRAY)) {
            return new GrayIC(alertContainer.categoryIC, securityRating);
        } else {
            return new BlackIC(alertContainer.categoryIC, securityRating);
        }
    }

    private AlertContainer RollAlertTable(int alertLevel, int stepsSinceLastAlert, boolean limitToIC) {
        int rollResult = RollUtils.StandardRoll(1, 6);

        int finalResult;

        if (limitToIC) {
            finalResult = rollResult;
        } else {
            finalResult = rollResult + stepsSinceLastAlert;
        }


        if (alertLevel == 0) {
            //No Alert Table
            switch (finalResult) {
                case 1: case 2: case 3:
                    return new AlertContainer(Matrix.WHITE, Matrix.REACTIVE);
                case 4: case 5:
                    return new AlertContainer(Matrix.WHITE, Matrix.PROACTIVE);
                case 6: case 7:
                    return new AlertContainer(Matrix.GRAY, Matrix.REACTIVE);
                default:
                    return new AlertContainer();
            }

        } else if (alertLevel == 1) {
            //Passive Alert Table
            switch (finalResult) {
                case 1: case 2: case 3:
                    return new AlertContainer(Matrix.WHITE, Matrix.PROACTIVE);
                case 4: case 5:
                    return new AlertContainer(Matrix.GRAY, Matrix.REACTIVE);
                case 6: case 7:
                    return new AlertContainer(Matrix.GRAY, Matrix.PROACTIVE);
                default:
                    return new AlertContainer();
            }
        } else {
            //Active Alert Table
            switch (finalResult) {
                case 1: case 2: case 3:
                    return new AlertContainer(Matrix.GRAY, Matrix.PROACTIVE);
                case 4: case 5:
                    return new AlertContainer(Matrix.WHITE, Matrix.PROACTIVE);
                case 6: case 7:
                    return new AlertContainer(Matrix.BLACK, null);
                default:
                    return new AlertContainer();
            }
        }



    }

    private int RollTriggerStep(int hostLevel) {
        int baseStep = RollUtils.StandardRoll(1,3);

        //System.out.println("BaseStep: " + baseStep);

        int stepMod;
        switch (hostLevel) {
            case 0:
                return baseStep + 4;
            case 1:
                return baseStep + 3;
            case 2:
                return baseStep + 2;
            case 3:
                return baseStep + 1;
            default :
                return baseStep + 100;
        }
    }

    private class AlertContainer {
        private final String levelIC;
        private final String categoryIC;
        private final boolean isAlertStep;

        public AlertContainer(String levelIC, String categoryIC) {
            this.isAlertStep = false;
            this.levelIC = levelIC;
            this.categoryIC = categoryIC;
        }

        public AlertContainer() { //If Blank it is an Alert Step
            this.isAlertStep = true;
            this.levelIC = null;
            this.categoryIC = null;
        }

        @Override
        public String toString() {
            if (isAlertStep) {
                return Matrix.ALERT_STEP;
            }

            if (categoryIC == null) {
                return levelIC + " IC";
            }

            return categoryIC + " " + levelIC + " IC";
        }
    }
}
