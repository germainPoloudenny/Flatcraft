package dut.flatcraft.group5;

import java.awt.event.KeyEvent;

import dut.flatcraft.CellFactory;
import dut.flatcraft.Main;
import dut.flatcraft.map.MapGenerator;
import dut.flatcraft.player.Coordinate;
import dut.flatcraft.ui.MyGrid;

public class MyGridAdaptater extends MyGrid {

	public MyGridAdaptater(int height, int width, CellFactory factory, MapGenerator generator) {
		super(height, width, factory, generator);

	}

	protected boolean dimensionSwitch() {
		Coordinate playerPos = player.getPosition();
		if (playerPos.getX() == BloodDimensionGenerator.portalPosition[0]
				&& playerPos.getY() == BloodDimensionGenerator.portalPosition[1]) {

			Main.frame.setVisible(false);
			removeAll();
			map = BloodDimensionGenerator.portalInteractions(map, factory);

			for (int i = 0; i < map.getHeight(); i++) {
				for (int j = 0; j < map.getWidth(); j++) {
					add(map.getAt(i, j).getUI());

				}
			}

			player.teleportation();

			Main.frame.setVisible(true);
			return true;
		}
		return false;

	}

	public void keyPressed(KeyEvent e) {
		boolean needsUpdate = false;
		boolean needsToCheckVisible = false;
		Coordinate old = player.getPosition();
		Coordinate oldNext = player.getDirection().getNext();
		switch (e.getKeyCode()) {
		case KeyEvent.VK_KP_UP:
		case KeyEvent.VK_UP:
			player.up();
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_DOWN:
		case KeyEvent.VK_DOWN:
			player.down();
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_LEFT:
		case KeyEvent.VK_LEFT:
			if (e.isShiftDown()) {
				player.previousInHand();
			} else {
				player.left();
			}
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_RIGHT:
		case KeyEvent.VK_RIGHT:
			if (e.isShiftDown()) {
				player.nextInHand();
			} else {
				player.right();
			}
			needsUpdate = true;
			break;
		case KeyEvent.VK_SPACE:
			needsUpdate = player.next();
			needsToCheckVisible = true;
			break;
		case KeyEvent.VK_CONTROL:
			digOrFill();
			needsUpdate = true;
			needsToCheckVisible = true;
			break;
		case KeyEvent.VK_E:
			if (execute()) {
				needsUpdate = true;
				needsToCheckVisible = true;
			}
			break;
		// Start - QD Implementation
		case KeyEvent.VK_D:

			player.moveRight();
			needsUpdate = true;
			needsToCheckVisible = true;
			break;
		case KeyEvent.VK_Q:
			player.moveLeft();
			needsUpdate = true;
			needsToCheckVisible = true;
			break;
		// End - QD Implementation
		case KeyEvent.VK_F1:
			displayHelp();
			break;
		case KeyEvent.VK_M:
			player.decHealth();
			break;
		case KeyEvent.VK_P:
			player.incHealth();
			break;
		default:
			// do nothing
		}
		if (dimensionSwitch()) {
			needsUpdate = true;
			needsToCheckVisible = true;
		}
		if (needsUpdate)

		{
			e.consume();
			checkPhysics();
			paint(old);
			paint(oldNext);
			paintPlayer(needsToCheckVisible);
		}

	}
}
