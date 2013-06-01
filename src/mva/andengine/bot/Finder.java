package mva.andengine.bot;

import java.util.LinkedList;
import java.util.Stack;

import android.graphics.Matrix;
import android.util.Pair;

public class Finder {
	private static int startX, startY, xFinish, yFinish, preX, preY;
	private static int[][] matrix;

	private final static int WALL = 1;
	private final static int EMPTY = 0;
	private static Stack<Pair<Integer, Integer>> steps;

	public static LinkedList<Pair<Integer, Integer>> findPath(int _startX, int _startY, int _finishX, int _finishY,
			int[][] _matrix) {
		startX = _startX;
		startY = _startY;
		xFinish = _finishX;
		yFinish = _finishY;
		matrix = _matrix;

		steps = new Stack<Pair<Integer, Integer>>();
		preX = startX;
		preY = startY;
		findMore(startX, startY);
		return null;

	}

	private static boolean findMore(int x, int y) {
		if (x == xFinish && y == yFinish) {
			return true;
		}

		if (notRightBorder(y + 1) && notWall(x, y + 1) && notRevers(x, y + 1)) {// right
			steps.push(new Pair<Integer, Integer>(x, y + 1));
			findMore(x, y + 1);
		}
		if (notDownBorder(x + 1) && notWall(x + 1, y) && notRevers(x + 1, y)) {// down
			steps.push(new Pair<Integer, Integer>(x + 1, y));
			findMore(x + 1, y);
		}
		if (notLeftBorder(y - 1) && notWall(x, y - 1) && notRevers(x, y - 1)) {// left
			steps.push(new Pair<Integer, Integer>(x, y - 1));
			findMore(x, y - 1);
		}
		if (notTopBorder(x - 1) && notWall(x - 1, y) && notRevers(x - 1, y)) {// top
			steps.push(new Pair<Integer, Integer>(x - 1, y));
			findMore(x - 1, y);
		}

		// else if

		return false;
	}

	private static boolean notRevers(int x, int y) {
		return !(x == preX && y == preY);
	}

	private static boolean notDownBorder(int i) {
		return !(i > matrix.length);
	}

	private static boolean notTopBorder(int i) {
		return i > 0;
	}

	private static boolean notLeftBorder(int x) {
		return x > 0;
	}

	private static boolean notWall(int x, int y) {
		return matrix[x][y] != 1;
	}

	private static boolean notRightBorder(int x) {
		return !(x > matrix[0].length);
	}

	private int diff(int x, int x1) {
		return Math.abs(Math.abs(x) - Math.abs(x1));
	}
}
