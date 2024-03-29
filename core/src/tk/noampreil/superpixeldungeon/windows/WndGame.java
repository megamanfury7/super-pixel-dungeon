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
package tk.noampreil.superpixeldungeon.windows;

import java.io.IOException;

import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.watabou.noosa.Game;
import tk.noampreil.superpixeldungeon.Badges;
import tk.noampreil.superpixeldungeon.Dungeon;
import tk.noampreil.superpixeldungeon.PixelDungeon;
import tk.noampreil.superpixeldungeon.scenes.GameScene;
import tk.noampreil.superpixeldungeon.scenes.InterlevelScene;
import tk.noampreil.superpixeldungeon.scenes.RankingsScene;
import tk.noampreil.superpixeldungeon.scenes.TitleScene;
import tk.noampreil.superpixeldungeon.ui.Icons;
import tk.noampreil.superpixeldungeon.ui.RedButton;
import tk.noampreil.superpixeldungeon.ui.Window;

public class WndGame extends Window {
	
	private static final String TXT_SETTINGS	= "Settings";
	private static final String TXT_CHALLEGES	= "Challenges";
	private static final String TXT_RANKINGS	= "Rankings";
	private static final String TXT_START		= "Start New Game";
	private static final String TXT_MENU		= "Main Menu";
	private static final String TXT_EXIT		= "Exit Pixel Dungeon";
	private static final String TXT_RETURN		= "Return to Game";
	
	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP		= 2;
	
	private int pos;
	
	public WndGame() {
		
		super();
		
		addButton( new RedButton( TXT_SETTINGS ) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show( new WndSettings( true ) );
			}
		} );
		
		if (Dungeon.challenges > 0) {
			addButton( new RedButton( TXT_CHALLEGES ) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show( new WndChallenges( Dungeon.challenges, false ) );
				}
			} );
		}
		
		if (!Dungeon.hero.isAlive()) {
			
			RedButton btnStart;
			addButton( btnStart = new RedButton( TXT_START ) {
				@Override
				protected void onClick() {
					Dungeon.hero = null;
					PixelDungeon.challenges( Dungeon.challenges );
					InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
					InterlevelScene.noStory = true;
					Game.switchScene( InterlevelScene.class );
				}
			} );
			btnStart.icon( Icons.get( Dungeon.hero.heroClass ) );
			
			addButton( new RedButton( TXT_RANKINGS ) {
				@Override
				protected void onClick() {
					InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
					Game.switchScene( RankingsScene.class );
				}
			} );
		}
				
		addButton( new RedButton( TXT_MENU ) {
			@Override
			protected void onClick() {
				try {
					Dungeon.saveAll();
				} catch (IOException e) {
					//
				}
				Game.switchScene( TitleScene.class );
			}
		} );
		
		addButton( new RedButton( TXT_EXIT ) {
			@Override
			protected void onClick() {
				// On iOS the game doesn't get saved "automatically"
				// (GameScene.pause() is not called?) so we need to do it "manually"
                if (SharedLibraryLoader.isIos) {
					Game.scene().pause();
                }
				Game.instance.finish();
			}
		} );
		
		addButton( new RedButton( TXT_RETURN ) {
			@Override
			protected void onClick() {
				hide();
			}
		} );
		
		resize( WIDTH, pos );
	}
	
	private void addButton( RedButton btn ) {
		add( btn );
		btn.setRect( 0, pos > 0 ? pos += GAP : 0, WIDTH, BTN_HEIGHT );
		pos += BTN_HEIGHT;
	}
}
