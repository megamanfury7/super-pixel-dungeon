import tk.noampreil.superpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;
import tk.noampreil.superpixeldungeon.actors.Char;
import tk.noampreil.superpixeldungeon.effects.Speck;
public class FetidRat1Sprite extends MobSprite {

	private Emitter cloud;

    public FetidRat1Sprite() {
        super();

        texture( Assets.RAT );

        TextureFilm frames = new TextureFilm( texture, 16, 15 );

        idle = new Animation( 2, true );
        idle.frames( frames, 32, 32, 32, 33 );

        run = new Animation( 10, true );
        run.frames( frames, 38, 39, 40, 41, 42 );

        attack = new Animation( 15, false );
        attack.frames( frames, 34, 35, 36, 37, 32 );

        die = new Animation( 10, false );
        die.frames( frames, 43, 44, 45, 46 );

        play( idle );
    }

	@Override
	public void link( Char ch ) {
		super.link( ch );

		if (cloud == null) {
			cloud = emitter();
			cloud.pour( Speck.factory( Speck.STENCH ), 0.7f );
		}
	}

	@Override
	public void update() {

		super.update();

		if (cloud != null) {
			cloud.visible = visible;
		}
	}

	@Override
	public void die() {
		super.die();

		if (cloud != null) {
			cloud.on = false;
		}
	}
}
