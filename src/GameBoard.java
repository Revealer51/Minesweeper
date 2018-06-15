import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GameBoard extends javax.swing.JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4037606594065192538L;
	public Grid[][] board;
	private int bombCount;
	private int z;

	public GameBoard(int z) {
		this.z = z;
		int row, column, bombCount;
		if (z == 0) {
			row = 8;
			column = 8;
			bombCount = 10;
		} else if (z == 2) {
			row = 16;
			column = 30;
			bombCount = 99;
		} else {
			row = 16;
			column = 16;
			bombCount = 40;
		}
		board = new Grid[row][column];
		GridLayout a = new GridLayout(row, column);
		setLayout(a);
		setSize(board[0].length * 10, board.length * 10);
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				board[r][c] = new Grid();

				board[r][c].setPreferredSize(new Dimension(50, 50));
				this.add(board[r][c]);
				board[r][c].addMouseListener(board[r][c]);
				board[r][c].setVisible(true);
			}
		}
		this.bombCount = bombCount;
	}

	private static void find(Grid[][] board, int r, int c) {
		if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
		} else if (board[r][c].found) {
		} else if (board[r][c].number != 0) {
			board[r][c].setText("" + board[r][c].number);
			board[r][c].setEnabled(false);
			board[r][c].found = true;
		} else if (board[r][c].number == 0) {
			board[r][c].setEnabled(false);
			board[r][c].found = true;
			find(board, r, c - 1);
			find(board, r - 1, c - 1);
			find(board, r - 1, c);
			find(board, r - 1, c + 1);
			find(board, r, c + 1);
			find(board, r + 1, c + 1);
			find(board, r + 1, c);
			find(board, r + 1, c - 1);
		} else
			System.out.println("Something went wrong");
	}

	// complicated stuff that makes sure the first click isn't a bomb.
	public void initiation() {
		Random rand = new Random();
		while (true) {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[0].length; c++) {
					if (!board[r][c].isEnabled()) {

						int bombs = 0;
						// Randomize the mine field.
						while (bombs != bombCount) {
							int index = rand.nextInt(board.length * board[0].length);
							int row = index / board[0].length;
							int col = index % board[0].length;
							if (!board[row][col].isBomb && !(row == r && col == c)) {
								board[index / board[0].length][index % board[0].length].isBomb = true;
								bombs++;
							}
						}
						for (int a = 0; a < board.length; a++) {
							for (int z = 0; z < board[0].length; z++) {
								if (!board[a][z].isBomb) {
									for (int d = a - 1; d <= a + 1; d++) {
										for (int b = z - 1; b <= z + 1; b++) {
											if (d >= 0 && b >= 0 && d < board.length && b < board[0].length) {
												if (board[d][b].isBomb)
													board[a][z].number++;
											}
										}
									}
								}
							}
						}
						find(board, r, c);
						board[r][c].found = true;
						return;
					}
				}
			}
		}
	}

	private static boolean checkGame(Grid[][] a) {
		for (int r = 0; r < a.length; r++) {
			for (int c = 0; c < a[0].length; c++) {
				if (a[r][c].isBomb) {
					if (!a[r][c].isFlagged)
						return false;
				} else {
					if (a[r][c].isFlagged || a[r][c].isEnabled())
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public void run() {
		int a = 0;
		initiation();
		// TODO Auto-generated method stub

		while (true) {
			try {
				Thread.sleep(10);
				for (int r = 0; r < board.length; r++) {
					for (int c = 0; c < board[0].length; c++) {
						// Check if a cell HAS JUST BEEN clicked, and HAS NOT YET BEEN found.
						if (!board[r][c].found && (board[r][c].explode || !board[r][c].isEnabled())
								&& !board[r][c].isFlagged) {
							if (board[r][c].explode) {
								board[r][c].setIcon(new ImageIcon("explode.png"));
								ImageIcon bomb = new ImageIcon("bomb.png");
								ImageIcon wrong = new ImageIcon("wrong.png");
								for (int x = 0; x < board.length; x++) {
									for (int y = 0; y < board[0].length; y++) {
										board[x][y].found = true;
										if (board[x][y].isFlagged) {
											if (!board[x][y].isBomb) {
												board[x][y].setIcon(wrong);
											}
										} else if (!board[x][y].isFlagged && board[x][y].isBomb)
											board[x][y].setIcon(bomb);
										board[r][c].setIcon(new ImageIcon("explode.png"));
										board[r][c].found = true;
									}
								}
								throw new InterruptedException("Game Over");

							} else {
								find(board, r, c);
								if (checkGame(board)) {
									int b = JOptionPane.showOptionDialog(null, "You win! Try again?",
											"Minesweeper Message", JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE, null, null, null);
								}
							}
						}
					}
				}
			} catch (InterruptedException e) {
				a = JOptionPane.showOptionDialog(null, "You blew up! Try again?", "Minesweeper Message",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			}

		}
	}

}
