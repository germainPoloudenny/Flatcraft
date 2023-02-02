package dut.flatcraft.map;

import java.util.NoSuchElementException;

import dut.flatcraft.Cell;
import dut.flatcraft.GameMap;
import dut.flatcraft.player.Coordinate;

/**
 * Utility class to store the cells in a two-dimensional array.
 * 
 * @author leberre
 *
 */
public class SimpleGameMap implements GameMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cell[][] elements;
	private final int width;
	private final int height;

	/**
	 * Create a storage of the given size
	 * 
	 * @param width  the width (a positive integer)
	 * @param height the height (a positive integer)
	 */
	public SimpleGameMap(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("The dimensions should be strictly positive !");
		}
		elements = new Cell[height][width];
		this.width = width;
		this.height = height;
	}

	@Override
	public void setAt(int i, int j, Cell c) {
		if (j >= width || i >= height) {
			throw new IllegalArgumentException("Incorrect cell location");
		}
		elements[i][j] = c;
		c.getUI().repaint();
	}

	@Override
	public Cell getAt(int i, int j) {
		if (j >= width || i >= height) {
			throw new IllegalArgumentException("Incorrect cell location");
		}
		return elements[i][j];
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public Coordinate findCell(Cell cell) {
		for (int i = 0; i < elements.length; i++) {
			for (int j = 0; j < elements[i].length; j++) {
				if (elements[i][j] == cell) {
					return new Coordinate(j, i, width, height);
				}
			}
		}
		throw new NoSuchElementException();
	}

	public SimpleGameMap cloneMap() {
		SimpleGameMap clonedMap = new SimpleGameMap(width, height);

		for (int i = 0; i < height; i++) {

			for (int j = 0; j < width; j++) {

				clonedMap.setAt(i, j, this.getAt(i, j));

			}
		}
		return clonedMap;

	}

}
