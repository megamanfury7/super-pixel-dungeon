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
package com.watabou.pixeldungeon;

import com.badlogic.gdx.Gdx;

enum Preferences {

	INSTANCE;
	
	public static final String KEY_LANDSCAPE	= "landscape";
	public static final String KEY_SCALE_UP		= "scaleup";
	public static final String KEY_MUSIC		= "music";
	public static final String KEY_SOUND_FX		= "soundfx";
	public static final String KEY_ZOOM			= "zoom";
	public static final String KEY_LAST_CLASS	= "last_class";
	public static final String KEY_DONATED		= "donated";
	public static final String KEY_INTRO		= "intro";
	public static final String KEY_BRIGHTNESS	= "brightness";
	
	private com.badlogic.gdx.Preferences prefs;
	
	private com.badlogic.gdx.Preferences get() {
		if (prefs == null) {
			prefs = Gdx.app.getPreferences("pixel-dungeon");
		}
		return prefs;
	}
	
	int getInt( String key, int defValue  ) {
		return get().getInteger(key, defValue);
	}
	
	boolean getBoolean( String key, boolean defValue  ) {
		return get().getBoolean( key, defValue );
	}
	
	String getString( String key, String defValue  ) {
		return get().getString( key, defValue );
	}
	
	void put( String key, int value ) {
		get().putInteger(key, value);
	}
	
	void put( String key, boolean value ) {
		get().putBoolean( key, value );
	}
	
	void put( String key, String value ) {
		get().putString(key, value);
	}
}