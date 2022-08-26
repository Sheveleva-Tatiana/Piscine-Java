package game;

import com.beust.jcommander.JCommander;

public class Program {
    public static void main(String[] args) {
        try {
            Arguments jArgs = new Arguments();
            JCommander jCommander = JCommander.newBuilder().addObject(jArgs).build();
            jCommander.parse(args);
            String profile = jArgs.getProfile();
            Painter painter = new Painter("/application-" + profile + ".properties", profile);
            Map.createMap(jArgs.getEnemiesCount(), jArgs.getWallsCount(), jArgs.getSize());
            Logic.makeLogic(new Data(), Map.getMap(), new Player(), painter, profile.equals("production"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
