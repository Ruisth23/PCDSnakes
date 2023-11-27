package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static environment.BoardPosition.isValid;

public class Obstacle extends GameElement {


	public static final int NUM_MOVES=3;
	public static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves = NUM_MOVES;
	private Board board;
	private int x;
	private int y;
	public Obstacle(Board board) {
		super();
		this.board = board;
	}

	public int getRemainingMoves() {
		return remainingMoves;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void move() throws InterruptedException {
		if(remainingMoves > 0) {
			BoardPosition newPosition = getRandomUnoccupiedPosition();

			if (newPosition != null) {
				Cell newCell = setPosition(newPosition);
				//newCell.setGameElement(this);
				remainingMoves--;
				board.setChanged();
			}
		}
	}

	private BoardPosition getRandomUnoccupiedPosition() {
		// Obter uma posição aleatória sem ocupação
		Random random = new Random();

		List<BoardPosition> allPositions = new ArrayList<>();
		for (int x = 0; x < Board.NUM_COLUMNS; x++) {
			for (int y = 0; y < Board.NUM_ROWS; y++) {
				allPositions.add(new BoardPosition(x, y));
			}
		}

		// Remove as posições ocupadas por obstáculos
		for (Obstacle obstacle : board.getObstacles()) {
			allPositions.remove(new BoardPosition(obstacle.getX(), obstacle.getY()));
		}

		// Remove as posições ocupadas por cobras
		for (Snake snake : board.getSnakes()) {
			for (Cell cell : snake.getCells()) {
				allPositions.remove(cell.getPosition());
			}
		}

		// Se houver posições disponíveis, retorna uma posição aleatória
		if (!allPositions.isEmpty()) {
			int randomIndex = random.nextInt(allPositions.size());
			return allPositions.get(randomIndex);
		}

		// Se não houver posições disponíveis, retorna null
		return null;

		// Exemplo de criação de uma nova posição aleatória
		//return new BoardPosition(board.getRandomPosition().getX(), board.getRandomPosition().getY());
	}

	private Cell setPosition(BoardPosition newPosition) {
		x = newPosition.getX();
		y = newPosition.getY();

		return board.getCell(newPosition);
	}
}
