
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class Program {
	public static final String TITLE = "Minesweeper 0.1";
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int ROW = 10;
	public static final int COLUMN = 10;

	public static void main(String args[]) {
		javax.swing.JFrame frame = new javax.swing.JFrame(TITLE);
		GameBoard panel = new GameBoard(Program.myShowMessage("Please select desired difficulty."));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		panel.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		Runnable r = (Runnable)panel;
		new Thread(r).start();
	}

	public static int myShowMessage(String message) {
		Object[] options = {"Beginner", "Intermediate", "Expert"};
		return JOptionPane.showOptionDialog(null, message, "Minesweeper Message", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, null);
	}
	
	
}
