package lev.pfilippov;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    private ArrayList<Stage> stages;
    public static boolean doWeHaveAWinner = false;
    public ArrayList<Stage> getStages() { return stages; }
    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}
