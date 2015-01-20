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

public class WndShatteredSettings extends Window {

	
	private static final String TXT_SHATTEREDPIXELSIGNS = "Signs show Shattered text";
	private static final String TXT_SHATTEREDPIXELQUESTS = "Ghost gives Shattered quests";
	private static final String TXT_SHATTEREDPIXELPLANTS = "(En/dis)able plants";
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
		CheckBox btnSPP=new RedButton( TXT_SHATTEREDPIXELPLANTS) {
			@Override
			protected void onClick(){
				super.onClick();
				Sample.INSTANCE.play( Assets.SND_CLICK );
				parent.add( new wndShatteredPlantSettings());
			}
		};
		btnSPBW.setRect( 0,btnSPQ.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add( btnSPBW );
	}
}
