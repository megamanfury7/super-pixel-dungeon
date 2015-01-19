package com.watabou.pixeldungeon.windows;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.CheckBox;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.ui.Window;

public class WndShatteredSettings extends Window {

	
	private static final String TXT_SHATTEREDPIXELSIGNS = "Signs show Shattered text";
	private static final String TXT_SHATTEREDPIXELQUESTS = "Ghost gives Shattered quests";
	private static final int WIDTH		= 112;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP 		= 2;

	public WndShatteredSettings( ) {
		super();
		CheckBox btnSPS=new CheckBox( TXT_SHATTEREDPIXELSIGNS ) {
			@Override
			public void onClick(){
				super.onClick();
				PixelDungeon.shatteredpixelsigns( checked() );
				Sample.INSTANCE.play( Assets.SND_CLICK );
			}
		};
		btnSPS.setRect( 0, 0, WIDTH, BTN_HEIGHT );
		btnSPS.checked( PixelDungeon.shatteredpixelsigns() );
		add( btnSPS );
		CheckBox btnSPQ=new CheckBox( TXT_SHATTEREDPIXELQUESTS) {
			@Override
			public void onClick(){
				super.onClick();
				PixelDungeon.shatteredpixelquests( checked() );
				Sample.INSTANCE.play( Assets.SND_CLICK );
			}
		};
		btnSPQ.setRect( 0,btnSPS.bottom() + GAP, WIDTH, BTN_HEIGHT);
		btnSPQ.checked( PixelDungeon.shatteredpixelquests() );
		add( btnSPQ );
	}
}
