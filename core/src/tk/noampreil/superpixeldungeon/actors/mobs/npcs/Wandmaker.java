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
package tk.noampreil.superpixeldungeon.actors.mobs.npcs;

import java.util.ArrayList;

import com.watabou.noosa.audio.Sample;
import tk.noampreil.superpixeldungeon.Assets;
import tk.noampreil.superpixeldungeon.Dungeon;
import tk.noampreil.superpixeldungeon.Journal;
import tk.noampreil.superpixeldungeon.actors.Actor;
import tk.noampreil.superpixeldungeon.actors.Char;
import tk.noampreil.superpixeldungeon.actors.blobs.Blob;
import tk.noampreil.superpixeldungeon.actors.blobs.ToxicGas;
import tk.noampreil.superpixeldungeon.actors.buffs.Buff;
import tk.noampreil.superpixeldungeon.actors.buffs.Roots;
import tk.noampreil.superpixeldungeon.actors.mobs.Mob;
import tk.noampreil.superpixeldungeon.effects.CellEmitter;
import tk.noampreil.superpixeldungeon.effects.Speck;
import tk.noampreil.superpixeldungeon.items.Heap;
import tk.noampreil.superpixeldungeon.items.Item;
import tk.noampreil.superpixeldungeon.items.bags.Bag;
import tk.noampreil.superpixeldungeon.items.potions.PotionOfStrength;
import tk.noampreil.superpixeldungeon.items.quest.CorpseDust;
import tk.noampreil.superpixeldungeon.items.wands.Wand;
import tk.noampreil.superpixeldungeon.items.wands.WandOfAmok;
import tk.noampreil.superpixeldungeon.items.wands.WandOfAvalanche;
import tk.noampreil.superpixeldungeon.items.wands.WandOfBlink;
import tk.noampreil.superpixeldungeon.items.wands.WandOfDisintegration;
import tk.noampreil.superpixeldungeon.items.wands.WandOfFirebolt;
import tk.noampreil.superpixeldungeon.items.wands.WandOfLightning;
import tk.noampreil.superpixeldungeon.items.wands.WandOfPoison;
import tk.noampreil.superpixeldungeon.items.wands.WandOfRegrowth;
import tk.noampreil.superpixeldungeon.items.wands.WandOfSlowness;
import tk.noampreil.superpixeldungeon.items.wands.WandOfTelekinesis;
import tk.noampreil.superpixeldungeon.levels.PrisonLevel;
import tk.noampreil.superpixeldungeon.levels.Room;
import tk.noampreil.superpixeldungeon.levels.Terrain;
import tk.noampreil.superpixeldungeon.plants.Plant;
import tk.noampreil.superpixeldungeon.scenes.GameScene;
import tk.noampreil.superpixeldungeon.sprites.ItemSpriteSheet;
import tk.noampreil.superpixeldungeon.sprites.WandmakerSprite;
import tk.noampreil.superpixeldungeon.utils.GLog;
import tk.noampreil.superpixeldungeon.utils.Utils;
import tk.noampreil.superpixeldungeon.windows.WndQuest;
import tk.noampreil.superpixeldungeon.windows.WndWandmaker;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Wandmaker extends NPC {

	{	
		name = "old wandmaker";
		spriteClass = WandmakerSprite.class;
	}
	
	private static final String TXT_BERRY1	=
		"Oh, what a pleasant surprise to meet a decent person in such place! I came here for a rare ingredient - " +
		"a _Rotberry seed_. Being a magic user, I'm quite able to defend myself against local monsters, " +
		"but I'm getting lost in no time, it's very embarrassing. Probably you could help me? I would be " +
		"happy to pay for your service with one of my best wands.";
	
	private static final String TXT_DUST1	=
		"Oh, what a pleasant surprise to meet a decent person in such place! I came here for a rare ingredient - " +
		"_corpse dust_. It can be gathered from skeletal remains and there is an ample number of them in the dungeon. " +
		"Being a magic user, I'm quite able to defend myself against local monsters, but I'm getting lost in no time, " +
		"it's very embarrassing. Probably you could help me? I would be happy to pay for your service with one of my best wands.";
	
	private static final String TXT_BERRY2	=
		"Any luck with a Rotberry seed, %s? No? Don't worry, I'm not in a hurry.";
	
	private static final String TXT_DUST2	=
		"Any luck with corpse dust, %s? Bone piles are the most obvious places to look.";
	
	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public String defenseVerb() {
		return "absorbed";
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public void interact() {
		
		sprite.turnTo( pos, Dungeon.hero.pos );
		if (Quest.given) {
			
			Item item = Quest.alternative ?
				Dungeon.hero.belongings.getItem( CorpseDust.class ) :
				Dungeon.hero.belongings.getItem( Rotberry.Seed.class );
			if (item != null) {
				GameScene.show( new WndWandmaker( this, item ) );
			} else {
				tell( Quest.alternative ? TXT_DUST2 : TXT_BERRY2, Dungeon.hero.className() );
			}
			
		} else {
			tell( Quest.alternative ? TXT_DUST1 : TXT_BERRY1 );
			Quest.given = true;
			
			Quest.placeItem();
			
			Journal.add( Journal.Feature.WANDMAKER );
		}
	}
	
	private void tell( String format, Object...args ) {
		GameScene.show( new WndQuest( this, Utils.format( format, args ) ) );
	}
	
	@Override
	public String description() {
		return 
			"This old but hale gentleman wears a slightly confused " +
			"expression. He is protected by a magic shield.";
	}
	
	public static class Quest {
		
		private static boolean spawned;
		
		private static boolean alternative;
		
		private static boolean given;
		
		public static Wand wand1;
		public static Wand wand2;
		
		public static void reset() {
			spawned = false;

			wand1 = null;
			wand2 = null;
		}
		
		private static final String NODE		= "wandmaker";
		
		private static final String SPAWNED		= "spawned";
		private static final String ALTERNATIVE	= "alternative";
		private static final String GIVEN		= "given";
		private static final String WAND1		= "wand1";
		private static final String WAND2		= "wand2";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				
				node.put( ALTERNATIVE, alternative );
				
				node.put(GIVEN, given );
				
				node.put( WAND1, wand1 );
				node.put( WAND2, wand2 );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {

			Bundle node = bundle.getBundle( NODE );
			
			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
				
				alternative	=  node.getBoolean( ALTERNATIVE );
				
				given = node.getBoolean( GIVEN );
				
				wand1 = (Wand)node.get( WAND1 );
				wand2 = (Wand)node.get( WAND2 );
			} else {
				reset();
			}
		}
		
		public static void spawn( PrisonLevel level, Room room ) {
			if (!spawned && Dungeon.depth > 6 && Random.Int( 10 - Dungeon.depth ) == 0) {
				
				Wandmaker npc = new Wandmaker();
				do {
					npc.pos = room.random();
				} while (level.map[npc.pos] == Terrain.ENTRANCE || level.map[npc.pos] == Terrain.SIGN);
				level.mobs.add( npc );
				Actor.occupyCell( npc );
				
				spawned = true;
				alternative = Random.Int( 2 ) == 0;
				
				given = false;
				
				switch (Random.Int( 5 )) {
				case 0:
					wand1 = new WandOfAvalanche();
					break;
				case 1:
					wand1 = new WandOfDisintegration();
					break;
				case 2:
					wand1 = new WandOfFirebolt();
					break;
				case 3:
					wand1 = new WandOfLightning();
					break;
				case 4:
					wand1 = new WandOfPoison();
					break;
				}
				wand1.random().upgrade();
				
				switch (Random.Int( 5 )) {
				case 0:
					wand2 = new WandOfAmok();
					break;
				case 1:
					wand2 = new WandOfBlink();
					break;
				case 2:
					wand2 = new WandOfRegrowth();
					break;
				case 3:
					wand2 = new WandOfSlowness();
					break;
				case 4:
					wand2 = new WandOfTelekinesis();
					break;
				}
				wand2.random().upgrade();
			}
		}
		
		public static void placeItem() {
			if (alternative) {
				
				ArrayList<Heap> candidates = new ArrayList<Heap>();
				for (Heap heap : Dungeon.level.heaps.values()) {
					if (heap.type == Heap.Type.SKELETON && !Dungeon.visible[heap.pos]) {
						candidates.add( heap );
					}
				}
				
				if (candidates.size() > 0) {
					Random.element( candidates ).drop( new CorpseDust() );
				} else {
					int pos = Dungeon.level.randomRespawnCell();
					while (Dungeon.level.heaps.get( pos ) != null) {
						pos = Dungeon.level.randomRespawnCell();
					}
					
					Heap heap = Dungeon.level.drop( new CorpseDust(), pos );
					heap.type = Heap.Type.SKELETON;
					heap.sprite.link();
				}
				
			} else {
				
				int shrubPos = Dungeon.level.randomRespawnCell();
				while (Dungeon.level.heaps.get( shrubPos ) != null) {
					shrubPos = Dungeon.level.randomRespawnCell();
				}
				Dungeon.level.plant( new Rotberry.Seed(), shrubPos );
				
			}
		}
		
		public static void complete() {
			wand1 = null;
			wand2 = null;
			
			Journal.remove( Journal.Feature.WANDMAKER );
		}
	}
	
	public static class Rotberry extends Plant {
		
		private static final String TXT_DESC = 
			"Berries of this shrub taste like sweet, sweet death.";
		
		{
			image = 7;
			plantName = "Rotberry";
		}
		
		@Override
		public void activate( Char ch ) {
			super.activate( ch );
			
			GameScene.add( Blob.seed( pos, 100, ToxicGas.class ) );
			
			Dungeon.level.drop( new Seed(), pos ).sprite.drop();
			
			if (ch != null) {
				Buff.prolong( ch, Roots.class, TICK * 3 );
			}
		}
		
		@Override
		public String desc() {
			return TXT_DESC;
		}
		
		public static class Seed extends Plant.Seed {
			{
				plantName = "Rotberry";
				
				name = "seed of " + plantName;
				image = ItemSpriteSheet.SEED_ROTBERRY;
				
				plantClass = Rotberry.class;
				alchemyClass = PotionOfStrength.class;
			}
			
			@Override
			public boolean collect( Bag container ) {
				if (super.collect( container )) {
					
					if (Dungeon.level != null) {
						for (Mob mob : Dungeon.level.mobs) {
							mob.beckon( Dungeon.hero.pos );
						}
						
						GLog.w( "The seed emits a roar that echoes throughout the dungeon!" );
						CellEmitter.center( Dungeon.hero.pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
						Sample.INSTANCE.play( Assets.SND_CHALLENGE );
					}
					
					return true;
				} else {
					return false;
				}
			}
			
			@Override
			public String desc() {
				return TXT_DESC;
			}
		}
	}
}
