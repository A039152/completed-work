package com.amica.mars;

public class TestProgram3 {
    public static void main(String[] args) {
        Recorder recorder = new Recorder();

        SoilSampler soilSampler = new SoilSampler("SS1", 0, 0, Rover.Direction.NORTH);
        Photographer photographer = new Photographer("P1", 1, 1, Rover.Direction.EAST);
        GroundPounder groundPounder = new GroundPounder("GP1", -1, -1, Rover.Direction.SOUTH);

        soilSampler.setSubscriber(recorder);
        photographer.setSubscriber(recorder);
        groundPounder.setSubscriber(recorder);

        soilSampler.receiveCommands("MMM");
        photographer.receiveCommands("MRML");
        groundPounder.receiveCommands("MPPM");

        while (soilSampler.isBusy() || photographer.isBusy() || groundPounder.isBusy()) {
            if (soilSampler.isBusy()) {
                soilSampler.takeNextStep();
            }
            if (photographer.isBusy()) {
                photographer.takeNextStep();
            }
            if (groundPounder.isBusy()) {
                groundPounder.takeNextStep();
            }
        }

        System.out.println("\nFiltered Reports (GroundPounder):");
        recorder.getReports().forEach(System.out::println);
        recorder.getReports().stream()
                .filter(report -> report.getRoverId().equals("GP1"))
                .forEach(System.out::println);

        // Step 8: Filter and print reports from the eastern half of the grid
        System.out.println("\nFiltered Reports (Eastern Half of the Grid):");
        recorder.getReports().stream()
                .filter(report -> report.getX() > 0)
                .forEach(System.out::println);
    }
}
