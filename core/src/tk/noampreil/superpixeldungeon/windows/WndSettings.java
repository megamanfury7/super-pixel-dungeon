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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;
import tk.noampreil.superpixeldungeon.Assets;
import tk.noampreil.superpixeldungeon.PixelDungeon;
import tk.noampreil.superpixeldungeon.scenes.PixelScene;
import tk.noampreil.superpixeldungeon.ui.CheckBox;
import tk.noampreil.superpixeldungeon.ui.RedButton;
import tk.noampreil.superpixeldungeon.ui.Window;

public class WndSettings extends Window {
	
	private static final String TXT_ZOOM_IN			= "+";
	private static final String TXT_ZOOM_OUT		= "-";
	private static final String TXT_ZOOM_DEFAULT	= "Default Zoom";

	private static final String TXT_SCALE_UP		= "Scale up UI";
	
	private static final String TXT_MUSIC	= "Music";
	
	private static final String TXT_SOUND	= "Sound FX";

	private static final String TXT_BINDINGS	= "Key bindings";
	
	private static final String TXT_BRIGHTNESS	= "Brightness";
	
	private static final String TXT_SWITCH_PORT	= "Switch to portrait";
	private static final String TXT_SWITCH_LAND	= "Switch to landscape";

	private static final String TXT_SWITCH_FULL = "Switch to fullscreen";
	private static final String TXT_SWITCH_WIN = "Switch to windowed";

	private static final String TXT_SHATTEREDPIXELOPTIONS = "(En/Dis)able Shattered features";
	private static final int WIDTH		= 112;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP 		= 2;
	
	private RedButton btnZoomOut;
	private RedButton btnZoomIn;
	
	public WndSettings( boolean inGame ) {
		super();
		
		
		if (inGame) {
			int w = BTN_HEIGHT;
			
			btnZoomOut = new RedButton( TXT_ZOOM_OUT ) {
				@Override
				protected void onClick() {
					zoom( Camera.main.zoom - 1 );
				}
			};
			add( btnZoomOut.setRect( 0, 0, w, BTN_HEIGHT) );
			
			btnZoomIn = new RedButton( TXT_ZOOM_IN ) {
				@Override
				protected void onClick() {
					zoom( Camera.main.zoom + 1 );
				}
			};
			add( btnZoomIn.setRect( WIDTH - w, 0, w, BTN_HEIGHT) );
			
			add( new RedButton( TXT_ZOOM_DEFAULT ) {
				@Override
				protected void onClick() {
					zoom( PixelScene.defaultZoom );
				}
			}.setRect( btnZoomOut.right(), 0, WIDTH - btnZoomIn.width() - btnZoomOut.width(), BTN_HEIGHT ) );
			
			updateEnabled();
			
		} else {
			
			CheckBox btnScaleUp = new CheckBox( TXT_SCALE_UP ) {
				@Override
				protected void onClick() {
					super.onClick();
					PixelDungeon.scaleUp( checked() );
				}
			};
			btnScaleUp.setRect( 0, 0, WIDTH, BTN_HEIGHT );
			btnScaleUp.checked( PixelDungeon.scaleUp() );
			add( btnScaleUp );
			RedButton btnSP=new RedButton( TXT_SHATTEREDPIXELOPTIONS ) {
				@Override
				protected void onClick(){
					super.onClick();
					Sample.INSTANCE.play( Assets.SND_CLICK );
					parent.add( new WndShatteredSettings() );
				}
			};
			btnSP.setRect(0,btnScaleUp.bottom() + GAP,WIDTH,BTN_HEIGHT);
			add(btnSP);
		}
		
		CheckBox btnMusic = new CheckBox( TXT_MUSIC ) {
			@Override
			protected void onClick() {
				super.onClick();
				PixelDungeon.music( checked() );
			}
		};
		btnMusic.setRect( 0, btnSP.bottom() + GAP, WIDTH, BTN_HEIGHT );
		btnMusic.checked( PixelDungeon.music() );
		add( btnMusic );
		
		CheckBox btnSound = new CheckBox( TXT_SOUND ) {
			@Override
			protected void onClick() {
				super.onClick();
				PixelDungeon.soundFx( checked() );
				Sample.INSTANCE.play( Assets.SND_CLICK );
			}
		};
		btnSound.setRect( 0, btnMusic.bottom() + GAP, WIDTH, BTN_HEIGHT );
		btnSound.checked( PixelDungeon.soundFx() );
		add( btnSound );
		
		
		}
		Application.ApplicationType type = Gdx.app.getType();

		Button lastBtn = btnSound;
		if (!inGame) {
		/*	if (type == Application.ApplicationType.Android || type == Application.ApplicationType.iOS) {
				RedButton btnOrientation = new RedButton(orientationText()) {
					@Override
					protected void onClick() {
						PixelDungeon.landscape(!PixelDungeon.landscape());
					}
				};
				btnOrientation.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
				add(btnOrientation);

				lastBtn = btnOrientation;
			} else*/ if (type == Application.ApplicationType.Desktop) {

				RedButton btnResolution = new RedButton(resolutionText()) {
					@Override
					protected void onClick() {
						PixelDungeon.fullscreen(!PixelDungeon.fullscreen());
					}
				};
				btnResolution.enable( PixelDungeon.instance.getPlatformSupport().isFullscreenEnabled() );
				btnResolution.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
				add(btnResolution);

				lastBtn = btnResolution;
			}
		} else {
		
			CheckBox btnBrightness = new CheckBox( TXT_BRIGHTNESS ) {
				@Override
				protected void onClick() {
					super.onClick();
					PixelDungeon.brightness( checked() );
				}
			};
			btnBrightness.setRect( 0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT );
			btnBrightness.checked(PixelDungeon.brightness());
			add( btnBrightness );
			
			lastBtn = btnBrightness;
			
		}

		if (type == Application.ApplicationType.Desktop) {

			RedButton btnKeymap = new RedButton(TXT_BINDINGS) {
				@Override
				protected void onClick() {
					parent.add(new WndKeymap());
				}
			};
			btnKeymap.setRect(0, lastBtn.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnKeymap);

			lastBtn = btnKeymap;
		}

		resize(WIDTH, (int) lastBtn.bottom());
	}
	
	private void zoom( float value ) {

		Camera.main.zoom( value );
		PixelDungeon.zoom( (int)(value - PixelScene.defaultZoom) );

		updateEnabled();
	}
	
	private void updateEnabled() {
		float zoom = Camera.main.zoom;
		btnZoomIn.enable( zoom < PixelScene.maxZoom );
		btnZoomOut.enable( zoom > PixelScene.minZoom );
	}
	
	private String orientationText() {
		return PixelDungeon.landscape() ? TXT_SWITCH_PORT : TXT_SWITCH_LAND;
	}

	private String resolutionText() {
		return Gdx.graphics.isFullscreen() ? TXT_SWITCH_WIN : TXT_SWITCH_FULL;
	}
}
