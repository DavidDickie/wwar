package com.dickie.wwar.shared;

import java.util.Arrays;
import java.util.List;

import com.dickie.wwar.shared.orderhelpers.*;

public class Spell implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public Spell() {

	};

	public Spell(String name, String desc, int spellOrder, boolean startSpell,
			boolean multiples, boolean deferred, boolean invisible,
			char affects, Card.CardType[] cards, OrderHelper oh) {
		this.name = name;
		this.description = desc;
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
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

			new Spell("Draw", "Put a card from your draw pile into your hand",
					1, true, false, false, true, 'S',
					new Card.CardType[] {}, new DrawHelper()),
			new Spell("Buy Spell", "Purchase a new spell",
					1, true, false, false, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Mana }, new BuySpellHelper()),
			new Spell("Pick up resource", "Pick up any resources in the wizards current location",
					0, true, false, true, false, 'S',
					new Card.CardType[] {}, new PickUpResourceHelper()),
			new Spell("Trade", "Spend a gold to convert a Element card to a Mana card",
					0, true, false, false, false, 'S',
							new Card.CardType[] {}, new TradeHelper()),
			new Spell("Walk", "Normal movement to adjacent location",
					5, true, false, true, false, 'L',
					new Card.CardType[] {}, new WalkOrderHelper()),
			new Spell("Discard", "Discard a card from your hand to the discard pile",
					0, true, true, false, false, 'S',
					new Card.CardType[] {}, new DiscardHelper()),
			new Spell("Heal", "Remove a point of damage",
					2, true, true, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water }, new HealHelper()),
			new Spell("Damage", "Do a point of damage to a Golem or Wizard in an adjacent location",
					4, true, true, true, false, 'P',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Fire }, new DamageHelper()),
			new Spell(
					"Move",
					"Move magically to an adjacent location; occurs before normal movement, ignores blocks",
					5, true,false, true, false, 'L', new Card.CardType[] { Card.CardType.Mana, Card.CardType.Air },
					new MoveOrderHelper()),
			new Spell("Armor", "Add armor, which will absorb a point of damage, to a wizard or Golem",
					2, true, true, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Earth }, new ArmorHelper()),
			new Spell("Remote Damage", "1 Damage to all enemy wizards and golems in adject locations",
					4, false, true, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Fire, Card.CardType.Air },
					new RemoteDamageHelper()),
			new Spell("Vision", "See cards in all adjoining locations",
					2, false, true, false, false, 'L',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Air },
					new VisionHelper()),
			new Spell("Block Path", "Prevent normal movement between to locations",
					2, false, true, false, false, 'C',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Earth, Card.CardType.Air },
					new BlockPathHelper()),
			new Spell("Familiar", "Town local reports cards in town",
					2, false, true, true, false, 'L',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Fire },
					new FamilliarHelper()),
			new Spell("One Draw", "Add a point to your draw points to use later",
					1, false, true, false, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Earth },
					new OneDrawHelper()),
			new Spell("Trap Path", "Cause 1 point of damage to the next wizard or golem to use the path",
					2, false, true, false, false, 'C',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Fire, Card.CardType.Earth },
					new TrapPathHelper()),
			new Spell("Summon Golem", "Create a new golem at the wizard's location",
					2, false, true, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Earth, Card.CardType.Air,
							Card.CardType.Fire }, new SummonGolemHelper()),
			new Spell("Create Golem", "Create a new golem at the wizard's location",
					2, false, false, true, false, 'S',
					new Card.CardType[] {Card.CardType.Mana, Card.CardType.Mana }, new CreateGolumHelper()),				
			new Spell("Increase Depth", "Increase the number of cards in your hand and draw pile by 1",
					2, false, true, false, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Air,
							Card.CardType.Fire }, new IncreaseDepthHelper()),
			new Spell("Increase Passthrough", "Get 1 additional draw point every turn",
					2, false, true, false, false,
					'S', new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Earth,
							Card.CardType.Fire },
					new IncreasePassthroughHelper()),
			new Spell("Teleport", "Move anywhere; avoid blocks and traps",
					2, false, false, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Earth,
							Card.CardType.Air }, new TeleportHelper()),
			new Spell("Spirit", "Create a spirit card to lock a town or a city",
					2, true, false, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Water, Card.CardType.Air,
							Card.CardType.Fire, Card.CardType.Earth },
					new SpiritHelper()),
			new Spell("Lock", "Lock a town or a city",
					2, true, false, true, false, 'S',
					new Card.CardType[] { Card.CardType.Mana,
							Card.CardType.Mana, Card.CardType.Spirit,
							Card.CardType.Spirit }, new LockHelper()) };
}
