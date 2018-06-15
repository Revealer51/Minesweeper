import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;

public class Grid extends JButton implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isBomb;
	public int number = 0;
	public boolean isFlagged = false;
	public boolean found = false;
	public boolean explode = false;
	// private MouseEvent click = new MouseEvent(paintingChild, number,
	// multiClickThreshhold, number, number, number, number, isBomb);

	public Grid() {
		setVisible(true);
	}

	public boolean getIsFlagged() {
		return isFlagged;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON1 && isEnabled() && !isFlagged) {
			if (!isBomb) {
				setEnabled(false);

				if (number == 0) {
				} else
					setText("" + number);

			} else {
				explode = true;
				}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
		if (!isFlagged && isEnabled()) {
			isFlagged = true;
			setIcon(new ImageIcon("flag.png"));
		} else if (isFlagged) {
			isFlagged = false;
			setIcon(null);
		}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
