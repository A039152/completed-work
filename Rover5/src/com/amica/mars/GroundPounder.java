package com.amica.mars;

public class GroundPounder extends ReportingRover {
    public GroundPounder(String id, int x, int y, Direction direction) {
        super(id, x, y, direction);
    }

    @Override
    protected void execute(char command) {
        if (command == 'P') {
            report("Detected subsurface anomaly.");
        } else {
            super.execute(command); // Delegate to superclass for other commands
        }
    }
}
