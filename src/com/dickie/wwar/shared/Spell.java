package com.dickie.wwar.shared;

import java.util.Arrays;
import java.util.List;

import com.dickie.wwar.shared.orderhelpers.*;

public class Spell implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public Spell() {

	};

	public Spell(String name, int spellOrder, boolean startSpell,
			boolean multiples, boolean deferred, boolean invisible,
			char affects, Card.CardType[] cards, OrderHelper oh) {
		this.name = name;
		this.affects = affects;
		this.multiples = multiples;
		this.deffered = deferred;
		this.startSpell = startSpell;
		this.cards = cards;
		this.oh = oh;
		this.spellOrder = spellOrder;
		this.invisible = invisible;
	}

	public String name;
	public boolean startSpell;
	public boolean multiples = false;
	public boolean deffered = false;
	public char affects;
	public Card.CardType[] cards;
	private OrderHelper oh;
	private int spellOrder;

	public List<Card.CardType> getCards() {
		return Arrays.asList(cards);
	}

	public int getSpellOrder() {
		return spellOrder;
	}

	public void setSpellOrder(int spellOrder) {
		this.spellOrder = spellOrder;
	}

	private boolean invisible;

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	public OrderHelper getOh() {
		return oh;
	}

	public void addOrderHelper(OrderHelper oh) {
		this.oh = oh;
	}

	static Spell s = new Spell();

	public static Spell[] spells() {
		return s.spells;
	}

	public boolean equals(Object s) {
		if (s instanceof Spell) {
			return (name.equals(((Spell) s).name));
		}
		return false;
	}

	public String toString() {
		return name;
	}

	public static Spell getSpell(String s) {
		for (Spell spell : spells) {
			if (spell.name.equals(s)) {
				return spell;
			}
		}
		return null;
	}

	public static Spell[] spells = { // start, multiple, deferred, invisible

			new Spell("Draw", 1, true, false, false, true, 'S',
					new Card.CardType[] {}, new DrawHelper()),
			new Spell("Buy Spell", 1, true, false, false, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Mana }, new BuySpellHelper()),
			new Spell("Pick up resource", 0, true, false, true, false, 'S',
					new Card.CardType[] {}, new PickUpResourceHelper()),
			new Spell("Walk", 5, true, false, true, false, 'L',
					new Card.CardType[] {}, new WalkOrderHelper()),
			new Spell("Discard", 0, true, true, false, false, 'S',
					new Card.CardType[] {}, new DiscardHelper()),
			new Spell("Heal", 2, true, true, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water }, new HealHelper()),
			new Spell("Damage", 4, true, true, true, false, 'P',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Fire }, new DamageHelper()),
			new Spell(
					"Move",
					5,
					true,
					false,
					true,
					false,
					'L',
					new Card.CardType[] { Card.CardType.Mana, Card.CardType.Air },
					new MoveOrderHelper()),
			new Spell("Armor", 2, true, true, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Earth }, new ArmorHelper()),
			new Spell("RemoteDamage", 4, false, true, true, false, 'L',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Fire, Card.CardType.Air },
					new RemoteDamageHelper()),
			new Spell("Vision", 2, false, true, false, false, 'L',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Air },
					new VisionHelper()),
			new Spell("Block Path", 2, false, true, false, false, 'C',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Earth, Card.CardType.Air },
					new BlockPathHelper()),
			new Spell("Familiar", 2, false, true, true, false, 'L',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Fire },
					new FamilliarHelper()),
			new Spell("One Draw", 1, false, true, false, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Earth },
					new OneDrawHelper()),
			new Spell("Trap Path", 2, false, true, false, false, 'C',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Fire, Card.CardType.Earth },
					new TrapPathHelper()),
			new Spell("Summon Golem", 2, false, true, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Earth, Card.CardType.Air,
							Card.CardType.Fire }, new SummonGolemHelper()),
			new Spell("Increase Depth", 2, false, true, false, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Air,
							Card.CardType.Fire }, new IncreaseDepthHelper()),
			new Spell("Increase Passthrough", 2, false, true, false, false,
					'S', new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Earth,
							Card.CardType.Fire },
					new IncreasePassthroughHelper()),
			new Spell("Teleport", 2, false, false, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Earth,
							Card.CardType.Air }, new TeleportHelper()),
			new Spell("Spirit", 2, true, false, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Air,
							Card.CardType.Fire, Card.CardType.Earth },
					new SpiritHelper()),
			new Spell("Lock", 2, true, false, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Mana, Card.CardType.Spirit,
							Card.CardType.Spirit }, new LockHelper()) };
}
