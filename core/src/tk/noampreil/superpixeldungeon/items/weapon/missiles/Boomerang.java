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
package tk.noampreil.superpixeldungeon.items.weapon.missiles;
import tk.noampreil.superpixeldungeon.Dungeon;
import tk.noampreil.superpixeldungeon.actors.Char;
import tk.noampreil.superpixeldungeon.actors.hero.Hero;
import tk.noampreil.superpixeldungeon.items.Item;
import tk.noampreil.superpixeldungeon.items.weapon.Weapon;
import tk.noampreil.superpixeldungeon.items.weapon.enchantments.Piercing;
import tk.noampreil.superpixeldungeon.items.weapon.enchantments.Swing;
import tk.noampreil.superpixeldungeon.sprites.ItemSpriteSheet;
import tk.noampreil.superpixeldungeon.sprites.MissileSprite;

public class Boomerang extends MissileWeapon {

	{
		name = "boomerang";
		image = ItemSpriteSheet.BOOMERANG;
		
		STR = 10;
		
		MIN = 1;
		MAX = 4;
		
		stackable = false;
	}
	
	@Override
	public boolean isUpgradable() {
		return true;
	}
	
	@Override
	public Item upgrade() {
		return upgrade( false );
	}
	
	@Override
	public Item upgrade( boolean enchant ) {
		MIN += 1;
		MAX += 2;
		super.upgrade( enchant );
		
		updateQuickslot();
		
		return this;
	}
	
	@Override
	public Item degrade() {
		MIN -= 1;
		MAX -= 2;
		return super.degrade();
	}
	
	@Override
	public Weapon enchant( Enchantment ench ) {
		while (ench instanceof Piercing || ench instanceof Swing) {
			ench = Enchantment.random();
		}
		
		return super.enchant( ench );
	}
	
	@Override
	public void proc( Char attacker, Char defender, int damage ) {
		super.proc( attacker, defender, damage );
		if (attacker instanceof Hero && ((Hero)attacker).rangedWeapon == this) {
			circleBack( defender.pos, (Hero)attacker );
		}
	}
	
	@Override
	protected void miss( int cell ) {
		circleBack( cell, curUser );
	}
	
	private void circleBack( int from, Hero owner ) {
		
		((MissileSprite)curUser.sprite.parent.recycle( MissileSprite.class )).
			reset( from, curUser.pos, curItem, null );
		
		if (throwEquiped) {
			owner.belongings.weapon = this;
			owner.spend( -TIME_TO_EQUIP );
		} else 
		if (!collect( curUser.belongings.backpack )) {
			Dungeon.level.drop( this, owner.pos ).sprite.drop();
		}
	}
	
	private boolean throwEquiped;
	
	@Override
	public void cast( Hero user, int dst ) {
		throwEquiped = isEquipped( user );
		super.cast( user, dst );
	}
	
	@Override
	public String desc() {
		return 
			"Thrown to the enemy this flat curved wooden missile will return to the hands of its thrower.";
	}
}
