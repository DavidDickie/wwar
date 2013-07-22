package com.dickie.wwar.client;

import java.util.ArrayList;
import java.util.List;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Connection;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderEngine;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.Spell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Wwar implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	public static boolean editMode;
	private static Location lastLocation = null;
	private ArrayList<String> redraw = new ArrayList<String>();
	private DisplayHtmlDialog popupDialog = DisplayHtmlDialog.getInstance();

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private final MapPanel mapPanel = new MapPanel();

	final Button closeButton = new Button("Close");
	final Label textToServerLabel = new Label();
	final HTML serverResponseLabel = new HTML();

	private final HorizontalPanel horizontalPanel = new HorizontalPanel();
	private final VerticalPanel verticalPanel = new VerticalPanel();
	private final DecoratorPanel decoratorPanel = new DecoratorPanel();
	private final Label lblPlayername = new Label("Playername:");
	private final VerticalPanel verticalPanel_1 = new VerticalPanel();
	private final TextBox playerNameTextBox = new TextBox();
	private final Label lblPassword = new Label("Password:");
	private final TextBox passwordTextBox = new TextBox();
	private final Label lblYouAreNot = new Label("You are not logged in");
	private final DecoratorPanel decoratorPanel_1 = new DecoratorPanel();
	private final DecoratorPanel decoratorPanel_2 = new DecoratorPanel();
	private final VerticalPanel verticalPanel_2 = new VerticalPanel();
	private final Label lblYourHand = new Label("Your Hand:");
	private final ListBox handListBox = new ListBox();
	private final Label lblDrawPileDistribution = new Label("Draw Pile:");
	private final DecoratorPanel decoratorPanel_3 = new DecoratorPanel();
	private final DecoratorPanel decoratorPanel_4 = new DecoratorPanel();
	private final VerticalPanel verticalPanel_3 = new VerticalPanel();
	private final VerticalPanel verticalPanel_4 = new VerticalPanel();
	private final Label lblEnterSpells = new Label("Orders:");
	private final Label lblGolums = new Label("Order for:");
	private final ListBox golemListBox = new ListBox();
	private final ListBox spellComboBox = new ListBox();
	private final Button btnExecute = new Button("Execute");
	private final ListBox playerNameComboBox = new ListBox();
	private Player activePlayer = null;
	private final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
	private final Button btnRedraw = new Button("Draw");
	private final TextBox passthroughTextBox = new TextBox();
	private final Label lblAvailableRedraws = new Label("Available:");
	private final HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
	private final TextArea locTextArea = new TextArea();

	public MapPanel getMapPanel() {
		return mapPanel;
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		drawPileListBox.setVisibleItemCount(5);

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("800px", "800px");

		rootPanel.add(horizontalPanel);
		horizontalPanel.setSize("1024", "800");
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setBorderWidth(4);
		horizontalPanel.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		verticalPanel.add(decoratorPanel_1);
		decoratorPanel_1.setWidth("100%");
		decoratorPanel_1.setWidget(verticalPanel_1);
		verticalPanel_1.setWidth("100%");

		verticalPanel_1.add(lblNewLabel_2);

		verticalPanel_1.add(playerNameComboBox);
		playerNameComboBox.setWidth("100%");
		verticalPanel_1.add(lblPlayername);

		verticalPanel_1.add(playerNameTextBox);
		playerNameTextBox.setSize("180px", "10px");

		verticalPanel_1.add(lblPassword);
		passwordTextBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					getGame();
				}
			}
		});
		passwordTextBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
			}
		});

		verticalPanel_1.add(passwordTextBox);
		passwordTextBox.setSize("180px", "10px");

		verticalPanel_1.add(lblYouAreNot);
		verticalPanel.add(decoratorPanel_2);
		decoratorPanel_2.setWidth("100%");

		decoratorPanel_2.setWidget(verticalPanel_2);
		verticalPanel_2.setSize("100%", "296px");

		verticalPanel_2.add(lblYourHand);
		handListBox.setMultipleSelect(true);
		verticalPanel_2.add(handListBox);
		handListBox.setWidth("100%");
		handListBox.setVisibleItemCount(5);
		horizontalPanel_1
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		verticalPanel_2.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("100%");

		horizontalPanel_1.add(lblAvailableRedraws);
		lblAvailableRedraws.setWidth("67px");

		horizontalPanel_1.add(passthroughTextBox);
		passthroughTextBox.setSize("40px", "13px");
		btnRedraw.setStyleName("button gray medium");
		btnRedraw.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (activePlayer.getPassthrough() == 0) {
					displayMessage("You have no redraw points left");
					return;
				}
				if (drawPileListBox.getSelectedIndex() == -1) {
					displayMessage("Select a card in the draw pile");
					return;
				}
				orderInProgress = new Order();
				orderInProgress.setCardType(Card.CardType
						.valueOf(drawPileListBox.getItemText(drawPileListBox
								.getSelectedIndex())));
				orderInProgress.setSpell(Spell.getSpell("Draw"));
				orderInProgress.setPlayer(activePlayer);
				orderInProgress.setOrderType(Order.OrderType.Magic);
				executeSpell();
			}
		});

		horizontalPanel_1.add(btnRedraw);
		btnRedraw.setHeight("26px");
		horizontalPanel_4
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_2.add(horizontalPanel_4);
		horizontalPanel_4.add(lblGold);
		horizontalPanel_4.add(goldIntegerBox);
		goldIntegerBox.setWidth("46px");
		horizontalPanel_4.add(lblDamage);

		horizontalPanel_4.add(damageIntegerBox);
		damageIntegerBox.setWidth("44px");

		verticalPanel_2.add(lblDrawPileDistribution);

		verticalPanel_2.add(drawPileListBox);
		drawPileListBox.setWidth("100%");

		verticalPanel.add(decoratorPanel_3);
		decoratorPanel_3.setWidth("100%");

		decoratorPanel_3.setWidget(verticalPanel_3);
		verticalPanel_3.setWidth("100%");

		verticalPanel_3.add(lblEnterSpells);
		lblEnterSpells.setWidth("100%");

		verticalPanel_3.add(spellComboBox);
		spellComboBox.setWidth("100%");
		verticalPanel_3.add(lblGolums);
		lblGolums.setWidth("100%");
		verticalPanel_3.add(golemListBox);
		golemListBox.setVisibleItemCount(5);
		golemListBox.setSize("100%", "60px");
		verticalPanel_3.add(btnExecute);
		btnExecute.setStyleName("button pink medium");
		btnExecute.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doOrders();
			}
		});
		btnExecute.setWidth("100%");

		verticalPanel.add(decoratorPanel_4);
		decoratorPanel_4.setWidth("100%");

		decoratorPanel_4.setWidget(verticalPanel_4);
		verticalPanel_4.setWidth("100%");

		verticalPanel_4.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("100%");
		btnFinishTurn.setStyleName("button blue medium");
		horizontalPanel_2.add(btnFinishTurn);
		btnFinishTurn.setWidth("100%");

		btnFinishTurn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				List<Order> temp = queuedOrders;
				queuedOrders = new ArrayList<Order>();
				sendOrder(temp);
			}
		});
		btnFinishTurn.setText("Finish Turn");

		verticalPanel.add(locTextArea);
		locTextArea.setWidth("195px");
		horizontalPanel_5
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel.add(horizontalPanel_5);
		horizontalPanel_5.setWidth("100%");
		btnPlayers.setStyleName("button white medium");
		btnPlayers.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TurnReportPanel.showPlayers(game);
			}
		});

		horizontalPanel_5.add(btnPlayers);
		btnPlayers.setSize("55", "");

		Button spellBtn = new Button("Spells");
		spellBtn.setStyleName("button white medium");
		spellBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TurnReportPanel.showSpells(activePlayer);
			}
		});
		horizontalPanel_5.add(btnResults);
		btnResults.setStyleName("button white medium");
		btnResults.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TurnReportPanel.showMessages(game.getMessages());
			}
		});
		btnResults.setWidth("55");
		horizontalPanel_5.add(spellBtn);
		spellBtn.setWidth("55");
		horizontalPanel_6
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel.add(horizontalPanel_6);
		horizontalPanel_6.setWidth("100%");
		btnAdmin.setStyleName("button white medium");
		horizontalPanel_6.add(btnAdmin);
		btnAdmin.setEnabled(false);
		btnAdmin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				AdminConsole ac = new AdminConsole();
				ac.setGame(game);
				ac.setData();
				final DialogBox acDialogBox = new DialogBox();
				acDialogBox.setText("Admin Console");
				acDialogBox.center();
				acDialogBox.add(ac);
				ac.getCloseButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						acDialogBox.hide();
					}
				});

			}
		});
		initGameSelection();
		
		btnAdmin.setWidth("55");

		horizontalPanel.add(decoratorPanel);
		decoratorPanel.setWidget(mapPanel);
		mapPanel.setSize("800px", "800px");
		mapPanel.getCanvas().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (editMode) {
					if (lastLocation == null) {
						lblYouAreNot
								.setText("Name in Playername, click new location");

						getLocation((event.getX() - mapPanel.getCenter())
								/ mapPanel.getScale(),
								(event.getY() - mapPanel.getCenter())
										/ mapPanel.getScale());
					} else {
						Location newLoc = new Location();
						newLoc.setName(playerNameTextBox.getText());
						newLoc.setX((event.getX() - mapPanel.getCenter())
								/ mapPanel.getScale());
						newLoc.setY((event.getY() - mapPanel.getCenter())
								/ mapPanel.getScale());
						updateLocation(lastLocation.getName(), newLoc);
						lastLocation = null;

					}
				} else if (locationSelect) {
					if (game == null)
						return;
					Location location = game.getLocation(
							(event.getX() - mapPanel.getCenter())
									/ mapPanel.getScale(),
							(event.getY() - mapPanel.getCenter())
									/ mapPanel.getScale());
					if (location == null) {
						displayMessage("Select a location");
						return;
					}
					orderInProgress.setLocation(location);
					RootPanel.get().getElement().getStyle()
							.setCursor(Cursor.AUTO);
					executeSpell();
					if (orderInProgress.getSpell().name.equals("Move")
							|| orderInProgress.getSpell().name.equals("Walk")) {

						drawMap();
					}
					locationSelect = false;
				} else if (pathSelect) {
					if (game == null)
						return;
					Connection con = game.getConnection(
							(event.getX() - mapPanel.getCenter())
									/ mapPanel.getScale(),
							(event.getY() - mapPanel.getCenter())
									/ mapPanel.getScale());
					if (con == null) {
						displayMessage("Select a path");
						return;
					}
					orderInProgress.setConnection(con);
					RootPanel.get().getElement().getStyle()
							.setCursor(Cursor.AUTO);
					executeSpell();
					drawMap();

					pathSelect = false;
				} else {
					if (activePlayer != null) {
						getData(activePlayer.getName(),
								(event.getX() - mapPanel.getCenter())
										/ mapPanel.getScale(),
								(event.getY() - mapPanel.getCenter())
										/ mapPanel.getScale());
					}
				}
			}

		});

	}

	private void initGameSelection() {
		greetingService.greetServer("", new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				popupDialog.display("Error on server",
						caught.getMessage());
				
			}

			@Override
			public void onSuccess(String result) {
				String[] games = result.split(";");
				for (int i = 0; i < games.length; i++){
					playerNameComboBox.addItem(games[i]);
				}
				
			}
			
		});
		
	}

	private void getData(String player, int x, int y) {
		locTextArea.setText(game.getData(player, x, y));
	}

	private Game game = null;
	private OrderEngine oe = null;

	private void getGame() {
		greetingService.getGame(playerNameComboBox
				.getItemText(playerNameComboBox.getSelectedIndex()),
				new AsyncCallback<Game>() {
					public void onFailure(Throwable caught) {
						popupDialog.display("Error on server",
								caught.getMessage());

					}

					@Override
					public void onSuccess(Game result) {
						btnAdmin.setEnabled(true);
						game = result;
						oe = new OrderEngine();
						oe.setGame(game);
						if (activePlayer != null) {
							setActivePlayer(activePlayer.getName(),
									activePlayer.getPassword());
						} else if (playerNameTextBox.getText() != null
								&& !playerNameTextBox.getText().equals("")) {
							setActivePlayer(playerNameTextBox.getText(),
									passwordTextBox.getText());
						}
						TurnReportPanel.showMessages(game.getMessages());
					}
				});

	}

	private void drawMap() {
		if (!mapPanel.isInitialized()) {
			mapPanel.drawMapBackground();
			List<Location> locs = game.getLocations();
			List<Connection> cons = game.getConnections();
			for (Connection con : cons) {
				mapPanel.drawLine(con.getX1(), con.getY1(), con.getX2(),
						con.getY2());
			}

			for (Location loc : locs) {
				mapPanel.drawLocation(loc, activePlayer, game, false, false);
			}
		} else {
			List<Location> locs = game.getLocations();

			for (Location loc : locs) {
				if (loc.isChanged() || redraw.contains(loc.getName())) {
					mapPanel.drawLocation(loc, activePlayer, game, false, false);
				}
			}

		}
	}

	private void getLocation(int x, int y) {
		greetingService.getLocation(playerNameComboBox
				.getItemText(playerNameComboBox.getSelectedIndex()), x, y,
				new AsyncCallback<Location>() {
					public void onFailure(Throwable caught) {
						popupDialog.display("Error on server",
								caught.getMessage());
					}

					@Override
					public void onSuccess(Location result) {
						lastLocation = result;
						playerNameTextBox.setText(lastLocation.getName());
					}
				});
	}

	private void updateLocation(final String oldLocName, final Location newLoc) {
		greetingService.editLocation(playerNameComboBox
				.getItemText(playerNameComboBox.getSelectedIndex()),
				oldLocName, newLoc, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						popupDialog.display("Error on server",
								caught.getMessage());
					}

					@Override
					public void onSuccess(Void v) {
						mapPanel.canvas.clear();
						mapPanel.initialized = false;
						Location l = game.getLocation(oldLocName);
						l.setName(newLoc.getName());
						l.setX(newLoc.getX());
						l.setY(newLoc.getY());
						drawMap();
					}
				});
	}

	Order orderInProgress;

	private void doOrders() {
		if (activePlayer == null) {
			popupDialog.display("Yo, dumbass",
					"<b>you have not logged in yet</b>");
			return;
		}
		btnRedraw.setEnabled(false);
		orderInProgress = new Order();
		if (golemListBox.getSelectedIndex() < 0) {
			this.displayMessage("Select someone first");
			return;
		}
		String name = golemListBox.getItemText(golemListBox.getSelectedIndex());
		if (name.indexOf("[") > -1) {
			orderInProgress.setGolum(game.getGolum(name));
		} else {
			orderInProgress.setPlayer(activePlayer);
		}
		orderInProgress.setOrderType(Order.OrderType.Magic);
		addSpellToOrder();
	}

	public void addSpellToOrder() {
		Spell spell = orderInProgress.getSpell(spellComboBox
				.getItemText(spellComboBox.getSelectedIndex()));
		orderInProgress.setSpell(spell);
		if (spell.affects == 'L') {
			selectLocation();
		} else if (spell.affects == 'C') {
			selectConnection();
		} else if (spell.affects == 'S') {
			if (orderInProgress.getSpell().name.equals("Buy Spell")) {
				buySpell();
			} else if (orderInProgress.getSpell().name.equals("Discard")) {
				if (handListBox.getSelectedIndex() < 0) {
					this.displayMessage("Select a card to discard first");
					return;

				}
				// ADD THIS CHANGE
				ArrayList<String> disCards = new ArrayList<String>();
				for (int i = 0, l = handListBox.getItemCount(); i < l; i++) {
					if (handListBox.isItemSelected(i)) {
						disCards.add(handListBox.getItemText(i));
					}
				}
				for (String s : disCards) {
					Order order = new Order();
					order.setPlayer(activePlayer);
					order.setSpell(orderInProgress.getSpell());
					order.setCardType(Card.CardType.valueOf(s));
					orderInProgress = order;
					executeSpell();
				}
				// ADD THIS CHANGE
			} else if (orderInProgress.getSpell().name.equals("Trade")) {
				if (handListBox.getSelectedIndex() < 0) {
					this.displayMessage("Select a card to trade first");
					return;
				}
				orderInProgress.setCardType(Card.CardType.valueOf(handListBox
						.getItemText(handListBox.getSelectedIndex())));
				executeSpell();
			} else {
				executeSpell();
			}
		} else if (spell.affects == 'P') {
			getOrderInfo(true, true);
		}
	}

	private void sendOrder(List<Order> orders) {
		greetingService.doOrders(playerNameComboBox
				.getItemText(playerNameComboBox.getSelectedIndex()),
				activePlayer.getName(), orders, new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						popupDialog.display("Error on server",
								caught.getMessage());
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result.booleanValue()) {
							popupDialog.display("Orders processed",
									"<b>Orders accepted; turn ran</b>");
							getGame();
						} else {
							popupDialog.display("Orders processed",
									"<b>Orders accepted</b>");
						}
						;
						oe.clearSingletons();
					}
				});

	}

	private void setActivePlayer(String player, String password) {
		activePlayer = game.getPlayer(player);
		if (activePlayer == null
				|| !activePlayer.getPassword().equals(password)) {
			if (activePlayer == null) {
				popupDialog.display("Error", "Bad playername");
			} else {
				activePlayer = null;
				popupDialog.display("Error", "Bad password");
			}
			return;
		}
		lblYouAreNot.setText("You are logged in");
		handListBox.clear();
		for (Card card : activePlayer.getHand()) {
			handListBox.addItem(card.getType().toString());
		}
		drawPileListBox.clear();
		for (Card card : activePlayer.getDrawPile()) {
			drawPileListBox.addItem(card.getType().toString());
		}
		passthroughTextBox.setText(Integer.toString(activePlayer
				.getPassThroughPoints()));
		if (activePlayer.getPassThroughPoints() == 0) {
			btnRedraw.setEnabled(false);
		}
		spellComboBox.clear();
		Order order = new Order();
		for (Spell spell : order.castable(activePlayer.getHand())) {
			if (spell.isInvisible()) {
				continue;
			} 
			if (spell.name.equals("Create Golem")){
				if (!activePlayer.getLocation().getType().equals(Location.LocType.Mystical) ||
				!containsTwoMana(activePlayer.getHand())){
					continue;
				}				
			}

			if (spell.startSpell
					|| activePlayer.getKnownSpells().contains(spell)) {

				spellComboBox.addItem(spell.name);
			}
		}
		golemListBox.clear();
		golemListBox.addItem(activePlayer.getName());
		for (Golum golum : game.getGolums()) {
			if (golum.getOwner().equals(activePlayer)) {
				golemListBox.addItem(golum.getName());
			}
		}
		goldIntegerBox.setValue(Integer.valueOf(activePlayer.getGold()));
		damageIntegerBox.setValue(Integer.valueOf(activePlayer.getDamage()));
		drawMap();
		redraw.clear();
	}

	private boolean containsTwoMana(List<Card> hand) {
		boolean one = false;
		for (Card card: hand){
			if (card.getType().equals(Card.CardType.Mana)){
				if (one){
					return true;
				} else {
					one = true;
				}
			}
		}
		return false;
	}

	public boolean locationSelect = false;
	public boolean pathSelect = false;

	public void selectLocation() {
		RootPanel.get().getElement().getStyle().setCursor(Cursor.CROSSHAIR);
		locationSelect = true;
	}

	public void selectConnection() {
		RootPanel.get().getElement().getStyle().setCursor(Cursor.CROSSHAIR);
		pathSelect = true;
	}

	public void displayMessage(String message) {
		popupDialog.display("Notification", message);
	}

	private ArrayList<Order> queuedOrders = new ArrayList<Order>();
	private final Button btnFinishTurn = new Button("Finish Turn");
	private final HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
	private final Label lblGold = new Label("Gold: ");
	private final IntegerBox goldIntegerBox = new IntegerBox();
	private final Label lblDamage = new Label("Damage:  ");
	private final IntegerBox damageIntegerBox = new IntegerBox();
	private final ListBox drawPileListBox = new ListBox();
	private final HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
	private final Button btnPlayers = new Button("Players");
	private final Button btnAdmin = new Button("Admin");
	private final Button btnResults = new Button("Results");
	private final Label lblNewLabel_2 = new Label("Game:");
	private final HorizontalPanel horizontalPanel_6 = new HorizontalPanel();

	public void executeSpell() {

		if (!redraw.contains(orderInProgress.getMover().getLocation())) {
			redraw.add(orderInProgress.getMover().getLocation().getName());
		}
		if (orderInProgress.getLocation() != null) {
			redraw.add(orderInProgress.getLocation().getName());
		}

		String response = oe.executeSpell(false, orderInProgress);
		if (response != null) {
			displayMessage(response);
			return;
		}

		handListBox.clear();
		drawPileListBox.clear();
		spellComboBox.clear();
		for (Card card : activePlayer.getHand()) {
			handListBox.addItem(card.getType().toString());
		}
		for (Card card : activePlayer.getDrawPile()) {
			drawPileListBox.addItem(card.getType().toString());
		}
		for (Spell spell : orderInProgress.castable(activePlayer.getHand())) {
			if (spell.isInvisible()) {
				continue;
			}
			if (spell.startSpell
					|| activePlayer.getKnownSpells().contains(spell)) {

				spellComboBox.addItem(spell.name);
			}
		}
		queuedOrders.add(orderInProgress);

	}

	private void getOrderInfo(boolean target, boolean number) {
		final DialogBox dialogBox2 = new DialogBox();
		Button closeButton2 = new Button("Execute");
		Button closeButton3 = new Button("Cancel");
		HTML serverResponseLabel2 = new HTML();

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(serverResponseLabel2);
		final ListBox targetComboBox = new ListBox();
		if (target) {

			dialogVPanel.add(targetComboBox);
			// ADD THIS CHANGE
			Location loc = orderInProgress.getMover().getLocation();
			for (Location l : game.getConnectedLocations(loc)) {
				for (Player player : game.getPlayers()) {
					if (player.equals(activePlayer)) {
						continue;
					}
					if (player.getLocation().equals(l)) {
						targetComboBox.addItem(player.getName());
					}
				}
				for (Golum gol : game.getGolums()) {
					if (gol.getOwner().equals(activePlayer)) {
						continue;
					}
					if (gol.getLocation().equals(l)) {
						targetComboBox.addItem(gol.toString());
					}
				}
			}

		}
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		HorizontalPanel dialogHPanel = new HorizontalPanel();
		dialogHPanel.add(closeButton2);
		dialogHPanel.add(closeButton3);
		dialogVPanel.add(dialogHPanel);
		dialogBox2.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Mover m = game.getMover(targetComboBox
						.getItemText(targetComboBox.getSelectedIndex()));
				if (m != null) {
					if (m instanceof Player)
						orderInProgress.setDamagePlayer((Player) m);
					else
						orderInProgress.setDamageGolum((Golum) m);
					executeSpell();
				}
				dialogBox2.hide();
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});
		closeButton3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox2.hide();
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});
		dialogBox2.setText("Spell parameters");
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel
				.setHTML("<br><b>Enter information for your spell</b>");
		dialogBox2.center();
	}

	public void buySpell() {
		final DialogBox dialogBox2 = new DialogBox();
		Button closeButton2 = new Button("Execute");
		Button closeButton3 = new Button("Cancel");
		HTML serverResponseLabel2 = new HTML();

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(serverResponseLabel2);
		final ListBox targetComboBox = new ListBox();

		for (Spell spell : Spell.spells) {
			if (spell.startSpell
					|| activePlayer.getKnownSpells().contains(spell)
					|| spell.cards.length > activePlayer.getGold()
					|| spell.isInvisible()) {
				continue;
			}
			targetComboBox.addItem(spell.name);
		}
		dialogVPanel.add(targetComboBox);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		HorizontalPanel dialogHPanel = new HorizontalPanel();
		dialogHPanel.add(closeButton2);
		dialogHPanel.add(closeButton3);
		dialogVPanel.add(dialogHPanel);
		dialogBox2.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				orderInProgress.setPurchasedSpell(targetComboBox
						.getItemText(targetComboBox.getSelectedIndex()));
				executeSpell();
				dialogBox2.hide();
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});
		closeButton3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox2.hide();
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});
		dialogBox2.setText("Spell parameters");
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel
				.setHTML("<br><b>Enter information for your spell</b>");
		dialogBox2.center();
	}
}
