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

public class WndShatteredPlantSettings extends Window {


	private static final String TXT_SHATTEREDPIXELBW="Generate blindweed";
	private static final int WIDTH		= 112;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP 		= 2;

	public WndShatteredPlantSettings( ) {
		super();
		CheckBox btnSPBW=new CheckBox( TXT_SHATTEREDPIXELBW ) {
			@Override
			public void onClick(){
				super.onClick();
				PixelDungeon.shatteredpixelBW( checked() );
				Sample.INSTANCE.play( Assets.SND_CLICK );
			}
		};
		btnSPBW.setRect( 0, 0, WIDTH, BTN_HEIGHT );
		btnSPBW.checked( PixelDungeon.shatteredpixelBW());
		add( btnSPBW );
	}
}
