package treasure;

import java.util.Random;

/**
 * Application that drives the Island and Game APIs through a random game.
 * You must fill in the setup and turn-playing logic so that you win the game,
 * even without knowing the island's layout in advance.
 *
 * @author Will Provost
 */
public class RandomGame {

	// Variables to keep track of both players' locations and coin counts
	private static Coordinates player1Position = new Coordinates(0, 0);
	private static Coordinates player2Position = new Coordinates(5, 5);
	private static int player1Coins = 0;
	private static int player2Coins = 0;
	private static String spell = "";
	private static boolean hasPlayer1PaidWizard = false;
	private static Island island;
	private static Game game;


	public static boolean playOneTurn() {
		// Player 1's turn
		// Player 1 moves
		player1Move();

		// Player 1 collects coins if there is one at the position
		if (isCoinAt(player1Position)) {
			collectCoin(player1Position);
		}

		// If Player 1 has enough coins, pay the wizard
		if (player1Coins >= island.getCoinsForWizard() && !hasPlayer1PaidWizard) {
			payWizard(player1Position);
		}

		// If Player 1 has paid the wizard, it can share the spell
		if (hasPlayer1PaidWizard && spell == null) {
			spell = payWizard(player1Position);
		}

		// Player 2's turn
		// Player 2 moves towards the treasure
		player2Move();

		// If Player 2 is at the treasure, claim the treasure
		if (isTreasureAt(player2Position) && hasPlayer1PaidWizard) {
			claimTreasure(player2Position);
			return false; // Game over
		}

		return true; // Continue playing
	}

	// Player 1 movement logic
	private static void player1Move() {
		// Randomly move Player 1 up, down, left, or right
		Random rand = new Random();
		int moveDirection = rand.nextInt(4);
		switch (moveDirection) {
			case 0: // Move up
				if (player1Position.row > 0) player1Position.row--;
				break;
			case 1: // Move down
				if (player1Position.row < island.getHeight() - 1) player1Position.row++;
				break;
			case 2: // Move left
				if (player1Position.col > 0) player1Position.col--;
				break;
			case 3: // Move right
				if (player1Position.col < island.getWidth() - 1) player1Position.col++;
				break;
		}
		game.playerEnters(player1Position.row, player1Position.col);
		game.showContents(player1Position.row, player1Position.col);
	}

	// Player 2 movement logic
	private static void player2Move() {
		// Randomly move Player 2 up, down, left, or right
		Random rand = new Random();
		int moveDirection = rand.nextInt(4);
		switch (moveDirection) {
			case 0: // Move up
				if (player2Position.row > 0) player2Position.row--;
				break;
			case 1: // Move down
				if (player2Position.row < island.getHeight() - 1) player2Position.row++;
				break;
			case 2: // Move left
				if (player2Position.col > 0) player2Position.col--;
				break;
			case 3: // Move right
				if (player2Position.col < island.getWidth() - 1) player2Position.col++;
				break;
		}
		game.playerEnters(player2Position.row, player2Position.col);
		game.showContents(player2Position.row, player2Position.col);
	}

	// Check if there's a coin at the given position
	private static boolean isCoinAt(Coordinates position) {
		return island.getContents(position.row, position.col) == Island.Contents.COIN;
	}

	// Collect a coin and update Player 1's coin count
	private static void collectCoin(Coordinates position) {
		island.takeCoin(position.row, position.col);
		player1Coins++;
		game.showMessage("Player 1 collected a coin. Total coins: " + player1Coins);
	}

	// Pay the wizard and get the spell
	private static String payWizard(Coordinates position) {
		spell = island.payWizard(position.row, position.col, player1Coins);
		hasPlayer1PaidWizard = true;
		return game.showMessage("Player 1 paid the wizard. Spell acquired!");
	}

	// Check if there's a treasure at the given position
	private static boolean isTreasureAt(Coordinates position) {
		return island.getContents(position.row, position.col) == Island.Contents.TREASURE;
	}

	// Player 2 claims the treasure
	private static void claimTreasure(Coordinates position) {
		island.claimTreasure(position.row, position.col, spell);
		game.showMessage("Player 2 claimed the treasure!");
	}

	/**
	 * Creates a random 6x6 island, and sets up the game viewer.
	 * You take it from there ...
	 */
	public static void main(String[] args) {
		// Initialize the game with a random island
		island = new Island();
		Game game = new Game(island);

		game.onEachTurnCall(() -> {
			// Call the playOneTurn function on each turn
			return playOneTurn();
		});
	}
}
