/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package tk.noampreil.superpixeldungeon.levels.painters;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import tk.noampreil.superpixeldungeon.Dungeon;
import tk.noampreil.superpixeldungeon.actors.blobs.WaterOfAwareness;
import tk.noampreil.superpixeldungeon.actors.blobs.WaterOfHealth;
import tk.noampreil.superpixeldungeon.actors.blobs.WaterOfTransmutation;
import tk.noampreil.superpixeldungeon.actors.blobs.WellWater;
import tk.noampreil.superpixeldungeon.levels.Level;
import tk.noampreil.superpixeldungeon.levels.Room;
import tk.noampreil.superpixeldungeon.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class MagicWellPainter extends Painter {

	private static final Class<?>[] WATERS = 
		{WaterOfAwareness.class, WaterOfHealth.class, WaterOfTransmutation.class};
	
	public static void paint( Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.EMPTY );
		
		Point c = room.center();
		set( level, c.x, c.y, Terrain.WELL );
		
		@SuppressWarnings("unchecked")
		Class<? extends WellWater> waterClass = 
			Dungeon.depth >= Dungeon.transmutation ?
			WaterOfTransmutation.class :		
			(Class<? extends WellWater>)Random.element( WATERS );
			
		if (waterClass == WaterOfTransmutation.class) {
			Dungeon.transmutation = Integer.MAX_VALUE;
		}
		
		WellWater water = (WellWater)level.blobs.get( waterClass );
		if (water == null) {
			try {
				water = ClassReflection.newInstance(waterClass);
			} catch (Exception e) {
				water = null;
			}
		}
		water.seed( c.x + Level.WIDTH * c.y, 1 );
		level.blobs.put( waterClass, water );
		
		room.entrance().set( Room.Door.Type.REGULAR );
	}
}
