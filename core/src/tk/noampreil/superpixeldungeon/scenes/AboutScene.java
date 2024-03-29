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
package tk.noampreil.superpixeldungeon.scenes;

import com.badlogic.gdx.Gdx;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.*;
import tk.noampreil.superpixeldungeon.PixelDungeon;
import tk.noampreil.superpixeldungeon.effects.Flare;
import tk.noampreil.superpixeldungeon.ui.Archs;
import tk.noampreil.superpixeldungeon.ui.ExitButton;
import tk.noampreil.superpixeldungeon.ui.Icons;
import tk.noampreil.superpixeldungeon.ui.Window;

public class AboutScene extends PixelScene {

	private static final String TXT = 
		"Super Pixel Dungeon: Noam Preil.\n" +
		"Some features included were requested on wikia.\n" +
		"If you have any ideas, feel free to share!\n" +
		"Original code & graphics: Watabou\n" +
		"Original LibGDX port: Arcnor\n" +
		"Music: Cube_Code\n\n" + 
		"This game is inspired by Brian Walker's Brogue. " +
		"Try it on Windows, Mac OS or Linux - it's awesome! ;)\n\n" +
		"Please visit official website for additional info:";
	
	private static final String LNK = "super-pixel-dungeon.wikia.com;
	
	@Override
	public void create() {
		super.create();
		
		BitmapTextMultiline text = createMultiline( TXT, 8 );
		text.maxWidth = Math.min( Camera.main.width, 120 );
		text.measure();
		add( text );
		
		text.x = align( (Camera.main.width - text.width()) / 2 );
		text.y = align( (Camera.main.height - text.height()) / 2 );
		
		BitmapTextMultiline link = createMultiline( LNK, 8 );
		link.maxWidth = Math.min( Camera.main.width, 120 );
		link.measure();
		link.hardlight( Window.TITLE_COLOR );
		add( link );
		
		link.x = text.x;
		link.y = text.y + text.height();
		
		TouchArea hotArea = new TouchArea( link ) {
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				Gdx.net.openURI("http://" + LNK);
			}
		};
		add( hotArea );
		
		Image wata = Icons.WATA.get();
		wata.x = align( text.x + (text.width() - wata.width) / 2 );
		wata.y = text.y - wata.height - 8;
		add( wata );
		
		new Flare( 7, 64 ).color( 0x112233, true ).show( wata, 0 ).angularSpeed = +20;
		
		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );
		
		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		PixelDungeon.switchNoFade( TitleScene.class );
	}
}
