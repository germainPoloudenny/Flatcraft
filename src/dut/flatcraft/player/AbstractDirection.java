package dut.flatcraft.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * The direction where the player is looking
 * 
 * @author leberre
 */
public abstract class AbstractDirection implements Direction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Coordinate c;
	protected final double angle;

	/**
	 * 
	 * @param c     a coordinate
	 * @param angle a double
	 */
	public AbstractDirection(Coordinate c, double angle) {
		this.c = c;
		this.angle = angle;
	}

	@Override
	public void paint(Graphics g) {
		int dx = c.getX() * 40 + 20;
		int dy = c.getY() * 40 + 17;
		int dw = 20;
		int dh = 6;
		Rectangle rect = new Rectangle(dx, dy, dw, dh);
		Path2D.Double path = new Path2D.Double();
		path.append(rect, false);

		AffineTransform t = new AffineTransform();
		t.rotate(angle, dx, dy + 3.0);
		path.transform(t);

		Rectangle nextRect = new Rectangle(getNext().getX() * 40+1, getNext().getY() * 40+1, 38, 38);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.PINK);
		g2d.fill(path);
		g2d.setColor(Color.YELLOW);
		g2d.draw(nextRect);
	}

	@Override
	public Coordinate nextForResource() {
		return new Coordinate(getNext());
	}
}
