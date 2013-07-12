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
import com.dickie.wwar.shared.Spell;
import com.dickie.wwar.shared.OrderEngine;
import com.dickie.wwar.shared.Player;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.HTMLPanel;

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

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private final MapPanel mapPanel = new MapPanel();
	final DialogBox dialogBox = new DialogBox();
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

	private Player activePlayer = null;
	private final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
	private final Button btnRedraw = new Button("Draw");
	private final TextBox passthroughTextBox = new TextBox();
	private final Label lblAvailableRedraws = new Label("Available draws:");
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
		verticalPanel.setBorderWidth(4);
		horizontalPanel.add(verticalPanel);
		verticalPanel.setWidth("100%");
		verticalPanel.add(decoratorPanel_1);
		decoratorPanel_1.setWidth("100%");
		decoratorPanel_1.setWidget(verticalPanel_1);
		verticalPanel_1.setWidth("100%");

		verticalPanel_1.add(lblNewLabel_2);
		gameNameTextBox.setAlignment(TextAlignment.CENTER);
		gameNameTextBox.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		gameNameTextBox.setText("demo");

		verticalPanel_1.add(gameNameTextBox);
		verticalPanel_1.setCellVerticalAlignment(gameNameTextBox,
				HasVerticalAlignment.ALIGN_MIDDLE);
		gameNameTextBox.setSize("180px", "10px");
		verticalPanel_1.add(lblPlayername);

		verticalPanel_1.add(playerNameTextBox);
		playerNameTextBox.setSize("180px", "10px");

		verticalPanel_1.add(lblPassword);
		passwordTextBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					if (passwordTextBox.getText().equals("Edit-me")) {
						editMode = true;
						return;
					}
					editMode = false;
					setActivePlayer(playerNameTextBox.getText(),
							passwordTextBox.getText());
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
		verticalPanel_2.add(handListBox);
		handListBox.setWidth("100%");
		handListBox.setVisibleItemCount(5);
		horizontalPanel_1
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		verticalPanel_2.add(horizontalPanel_1);

		horizontalPanel_1.add(lblAvailableRedraws);
		lblAvailableRedraws.setWidth("97px");

		horizontalPanel_1.add(passthroughTextBox);
		passthroughTextBox.setSize("40px", "13px");
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
		horizontalPanel_4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
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
		horizontalPanel_3
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel_3.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("100%");
		horizontalPanel_3.add(btnExecute);
		btnExecute.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doOrders();
			}
		});
		btnExecute.setWidth("100%");

		horizontalPanel_3.add(lblNewLabel_1);
		lblNewLabel_1.setWidth("100%");

		verticalPanel.add(decoratorPanel_4);
		decoratorPanel_4.setWidth("100%");

		decoratorPanel_4.setWidget(verticalPanel_4);
		verticalPanel_4.setWidth("100%");

		verticalPanel_4.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("100%");
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
		locTextArea.setWidth("100%");

		verticalPanel.add(horizontalPanel_5);
		horizontalPanel_5.setWidth("100%");
		btnPlayers.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				StringBuffer sb = new StringBuffer();
				sb.append("<table><tr><td>Player</td><td>Spells</td><td>Cards</td><td>Towns</td><td>Total</td></tr>");
				for (Player pl : game.getPlayers()){
					int towns = 0;
					for (Location loc: game.getLocations()){
						if (loc.isLocked()){
							if (loc.getLockedBy().equals(pl.getName())){
								towns++;
							}
						} else {
							List<Mover> ms = game.getMoversAtLocation(loc);
							if (ms.size() > 0 && ms.get(0).getOwnerName().equals(pl.getName())){
								towns++;
							}
						}
					}
					int numCards = pl.getHand().size() + pl.getDrawPile().size() + pl.getDiscardPile().size();
					sb.append("<tr><td>").append(pl.getName()).append("</td><td>").
							append(pl.getKnownSpells().size()).append("</td><td>").
							append(numCards).append("</td><td>").
							append(towns).append("</td><td>"). 
							append(numCards + pl.getKnownSpells().size()*2 + towns*3).append("</td></tr>");
				}
				sb.append("</table>");
				displayMessage(sb.toString());
			}
		});

		horizontalPanel_5.add(btnPlayers);
		btnPlayers.setHeight("30");
		btnResults.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TurnReportPanel.messageDialogBox(game.getMessages());
			}
		});

		horizontalPanel_5.add(btnResults);
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
				gameNameTextBox.setText(game.getName());
			}
		});

		horizontalPanel_5.add(btnAdmin);

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

		// Create the popup dialog box

		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);

		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<br><b>Response:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});
		greetingService.greetServer(gameNameTextBox.getText(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				dialogBox.setText("Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(caught.getMessage());
				dialogBox.center();
				closeButton.setFocus(true);

			}

			@Override
			public void onSuccess(String result) {
				getGame();
			}

		});

	}

	private void getData(String player, int x, int y) {
		locTextArea.setText(game.getData(player, x, y));
	}

	private Game game = null;
	private OrderEngine oe = null;

	private void getGame() {
		greetingService.getGame(gameNameTextBox.getText(), new AsyncCallback<Game>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				dialogBox.setText("Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("bad shit");
				dialogBox.center();
				closeButton.setFocus(true);
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
				}
				TurnReportPanel.messageDialogBox(game.getMessages());
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
		greetingService.getLocation(gameNameTextBox.getText(), x, y,
				new AsyncCallback<Location>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Failure");
						dialogBox.center();
						closeButton.setFocus(true);
					}

					@Override
					public void onSuccess(Location result) {
						lastLocation = result;
					}
				});
	}

	private void updateLocation(String oldLocName, Location newLoc) {
		greetingService.editLocation(gameNameTextBox.getText(), oldLocName, newLoc,
				new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Failure");
						dialogBox.center();
						closeButton.setFocus(true);
					}

					@Override
					public void onSuccess(Void v) {
						mapPanel.canvas.clear();
						mapPanel.initialized=false;
						getGame();
						drawMap();
					}
				});
	}

	Order orderInProgress;

	private void doOrders() {
		if (activePlayer == null) {
			dialogBox.setText("Failure");
			serverResponseLabel.addStyleName("serverResponseLabelError");
			serverResponseLabel.setHTML("<b>you have not logged in yet</b>");
			dialogBox.center();
			closeButton.setFocus(true);
			return;
		}
		btnRedraw.setEnabled(false);
		orderInProgress = new Order();
		if (golemListBox.getSelectedIndex() < 0) {
			this.displayMessage("Select someone first");
			return;
		}
		String name = golemListBox
				.getItemText(golemListBox.getSelectedIndex());
		if (name.indexOf("[") > -1){
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
			} else if (orderInProgress.getSpell().name.equals("Discard")){
				if (handListBox.getSelectedIndex() < 0){
					this.displayMessage("Select a card to discard first");
					return;
				}
				orderInProgress.setCardType(Card.CardType.valueOf(handListBox.getItemText(
						handListBox.getSelectedIndex())));
				executeSpell();
			} else {
				executeSpell();
			}
		} else if (spell.affects == 'P') {
			getOrderInfo(true, true);
		}
	}

	private void sendOrder(List<Order> orders) {
		greetingService.doOrders(gameNameTextBox.getText(), activePlayer.getName(), orders,
				new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel
								.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(caught.getMessage());
						dialogBox.center();
						closeButton.setFocus(true);
					}

					@Override
					public void onSuccess(Boolean result) {
						dialogBox.setText("Orders processed");
						serverResponseLabel
								.addStyleName("serverResponseLabelError");
						if (result.booleanValue()) {
							serverResponseLabel
									.setHTML("<b>Orders accepted; turn ran</b>");
							getGame();
						} else {
							serverResponseLabel
									.setHTML("<b>Orders accepted</b>");
						}

						dialogBox.center();
						closeButton.setFocus(true);
						oe.clearSingletons();
					}
				});

	}

	private void setActivePlayer(String player, String password) {
		activePlayer = game.getPlayer(player);
		if (activePlayer == null
				|| !activePlayer.getPassword().equals(password)) {

			dialogBox.setText("Error");
			serverResponseLabel.addStyleName("serverResponseLabelError");
			if (activePlayer == null) {
				serverResponseLabel.setHTML("Bad playername");
			} else {
				activePlayer = null;
				serverResponseLabel.setHTML("Bad password");
			}
			dialogBox.center();
			closeButton.setFocus(true);
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
		dialogBox.setText("Notification");
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(message);
		serverResponseLabel.setWidth("200px");
		dialogBox.center();
		closeButton.setFocus(true);
	}

	private ArrayList<Order> queuedOrders = new ArrayList<Order>();
	private final HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
	private final Button btnFinishTurn = new Button("New button");
	private final Label lblNewLabel_1 = new Label("                ");
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
	private final TextBox gameNameTextBox = new TextBox();

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
			Location loc = activePlayer.getLocation();
			for (Player player : game.getPlayers()) {
				if (player.equals(activePlayer)) {
					continue;
				}
				if (player.getLocation().equals(loc)) {
					targetComboBox.addItem(player.getName());
				}
			}
			for (Golum gol : game.getGolums()) {
				if (gol.getOwner().equals(activePlayer)) {
					continue;
				}
				if (gol.getLocation().equals(loc)) {
					targetComboBox.addItem(gol.toString());
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
