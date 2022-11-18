package com.swygbro.healthit.common.util;

public class BmiUtils {

    private static final int[][] BMI_CALORIE = {{60, 59}, {58, 57}, {56, 52}, {50, 50}, {44, 44}};

    public static int convertBmiToKcal(final int gender, final double bmi) {
        final int maxKcal;

        if (bmi > 39) {
            maxKcal = (int) (bmi * BMI_CALORIE[0][gender]);
        } else if (bmi > 29) {
            maxKcal = (int) (bmi * BMI_CALORIE[1][gender]);
        } else if (bmi > 24) {
            maxKcal = (int) (bmi * BMI_CALORIE[2][gender]);
        } else if (bmi > 19) {
            maxKcal = (int) (bmi * BMI_CALORIE[3][gender]);
        } else {
            maxKcal = (int) (bmi * BMI_CALORIE[4][gender]);
        }

        return maxKcal / 3;
    }

}
