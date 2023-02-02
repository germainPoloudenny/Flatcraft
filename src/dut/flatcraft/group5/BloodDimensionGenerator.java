package dut.flatcraft.group5;

import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;
import dut.flatcraft.map.MapGenerator;
import dut.flatcraft.map.SimpleGameMap;

public class BloodDimensionGenerator implements MapGenerator {
	private MapGenerator decorated;
	private static boolean inDarkDimension = false;
	protected static final int[] portalPosition = new int[2];
	private static GameMap oldMap;
	private static GameMap bloodMap;

	public BloodDimensionGenerator(MapGenerator decorated) {
		this.decorated = decorated;

	}

	public GameMap generate(int width, int height, CellFactory factory) {
		GameMap map = decorated.generate(width, height, factory);
		bloodMap = new SimpleGameMap(width, height);
		portalPosition[0] = map.getWidth() / 4;

		portalPosition[1] = height / 2 - 1;
		map.setAt(portalPosition[1], portalPosition[0], factory.createPortal());

		int halfHeigh = map.getHeight() / 2;
		for (int i = 0; i < halfHeigh; i++) {

			for (int j = 0; j < bloodMap.getWidth(); j++) {
				bloodMap.setAt(i, j, factory.createDarkSky());

			}
		}

		for (int i = halfHeigh; i < map.getHeight(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				bloodMap.setAt(i, j, factory.createBloodSoil());
			}
		}

		return map;
	}

	public static GameMap enterBloodDimension(GameMap map, CellFactory factory) {
		oldMap = map.cloneMap();
		inDarkDimension = true;

		for (int i = 0; i < map.getHeight(); i++) {

			for (int j = 0; j < map.getWidth(); j++) {
				map.setAt(i, j, bloodMap.getAt(i, j));

			}
		}

		map.setAt(portalPosition[1], portalPosition[0], factory.createPortal());

		return map;
	}

	public static GameMap leaveBloodDimension(GameMap map, CellFactory factory) {

		inDarkDimension = false;

		bloodMap = map.cloneMap();
		for (int i = 0; i < map.getHeight(); i++) {

			for (int j = 0; j < map.getWidth(); j++) {

				map.setAt(i, j, oldMap.getAt(i, j));

			}
		}
		oldMap = map.cloneMap();
		map.setAt(portalPosition[1], portalPosition[0], factory.createPortal());

		return map;

	}

	public static GameMap portalInteractions(GameMap map, CellFactory factory) {
		if (!inDarkDimension)
			return enterBloodDimension(map, factory);
		else
			return leaveBloodDimension(map, factory);

	}
	public static boolean getDimension(){
		return inDarkDimension;
	}

}
