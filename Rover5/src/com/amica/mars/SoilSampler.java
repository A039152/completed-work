package com.amica.mars;

public class SoilSampler extends ReportingRover {
    public SoilSampler(String id, int x, int y, Direction direction) {
        super(id, x, y, direction);
    }

    @Override
    public void move() {
        super.move();
        report("Sampled some soil!");
    }
}
