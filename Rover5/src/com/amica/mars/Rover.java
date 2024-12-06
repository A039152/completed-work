package com.amica.mars;

import java.util.LinkedList;
import java.util.Queue;

/**
 * In this version, all state and behavior are encapsulated as
 * instance variables and instance methods. There is also an enhanced
 * interface that allows the caller to send a command string and then
 * direct the rover to execute one command at a time.
 * 
 * @author Will Provost
 */
public class Rover {

	public enum Direction { NORTH, EAST, SOUTH, WEST }

	private final String id;
	private int x;
	private int y;
	private Direction direction;
	private StringBuffer commands = new StringBuffer();
	
	public Rover() {
		this("DefaultRover",0, 0, Direction.NORTH);
	}
	
	public Rover(String id, int x, int y, Direction direction) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public String getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void turnLeft() {
		direction = Direction.values()[(direction.ordinal() + 3) % 4];
	}
	
	public void turnRight() {
		direction = Direction.values()[(direction.ordinal() + 1) % 4];
	}
	
	public void move() {
		if (direction == Direction.NORTH) {
			++y;
		} else if (direction == Direction.EAST) {
			++x;
		} else if (direction == Direction.SOUTH) {
			--y;
		} else if (direction == Direction.WEST) {
			--x;
		}  
	}

	public void move(int distance) {
		for (int i = 0; i < distance; ++i) {
			move();
		}
	}
	
	public String getStatus() {

		String dirString = direction.toString().toLowerCase();

		return String.format("The rover is now at (%d,%d), and facing %s.",
				x, y, dirString);
	}
	
	public void receiveCommands(String newCommands) {
		for (int c = 0; c < newCommands.length(); ++c) {
			char command = newCommands.charAt(c);
			if (Character.isDigit(command)) {
				int repeat = Math.min(Character.getNumericValue(command), 100);
				commands.append("M".repeat(repeat));
			} else {
				commands.append(command);
			}
		}
	}
	
	public boolean isBusy() {
		return commands.length() != 0;
	}
	
	public void takeNextStep() {
		if (isBusy()) {
			char command = commands.charAt(0);
			System.out.println("Processing command: " + command); // Debug log
			commands.deleteCharAt(0); // Remove the executed command
			execute(command);
			System.out.println("Remaining commands: " + commands.toString()); // Debug log
		}
	}
	protected void execute(char command) {
		if (command == 'L') {
			turnLeft();
		} else if (command == 'R') {
			turnRight();
		} else if (command == 'M') {
			move();
		} else {
			System.out.println("Unrecognized command: " + command);
		}
	}


}
