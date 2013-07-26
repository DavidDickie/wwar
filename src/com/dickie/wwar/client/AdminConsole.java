package com.dickie.wwar.client;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.orderhelpers.ArmorHelperTest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminConsole extends DecoratorPanel {
	private final VerticalPanel verticalPanel = new VerticalPanel();
	private final HorizontalPanel horizontalPanel = new HorizontalPanel();
	private final Button btnNewButton = new Button("Close");
	private final DialogBox dialogBox2 = new DialogBox();
	private  boolean initialized = false;
	private final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
	private final Label lblEnterTheSuper = new Label("Enter the super secret password:  ");
	private final TextBox passwordTextBox = new TextBox();
	private final HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
	private final CheckBox chckbxLocEdit = new CheckBox("New check box");
	private final Label lblNewLabel = new Label("Game name: ");
	private final TextBox gameNameTextBox = new TextBox();
	private final HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
	private final HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
	private final ListBox playerListBox = new ListBox();
	private final VerticalPanel verticalPanel_2 = new VerticalPanel();
	private final CheckBox chckbxNpc = new CheckBox("NPC");
	private final HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
	private final Label lblNewLabel_1 = new Label("Name: ");
	private final TextBox playerNameTextBox = new TextBox();
	private final DecoratorPanel decoratorPanel = new DecoratorPanel();
	private final DecoratorPanel decoratorPanel_1 = new DecoratorPanel();
	private final DecoratorPanel decoratorPanel_2 = new DecoratorPanel();
	private final Button commitButton = new Button("Commit");
	
	
	public AdminConsole() {
		setSize("600", "400");
		
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		horizontalPanel_1.add(lblEnterTheSuper);
		passwordTextBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				if (passwordTextBox.getText().equals("Password")){
					commitButton.setEnabled(true);
					btnNewButton_1.setEnabled(true);
				}
			}
		});
		
		horizontalPanel_1.add(passwordTextBox);
		verticalPanel.add(decoratorPanel_1);
		decoratorPanel_1.setWidth("100%");
		decoratorPanel_1.setWidget(horizontalPanel_2);
		horizontalPanel_2.setWidth("100%");
		horizontalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		chckbxLocEdit.setHTML("Location Edit");
		
		horizontalPanel_2.add(chckbxLocEdit);
		verticalPanel.add(horizontalPanel_3);
		horizontalPanel_3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_3.add(lblNewLabel);
		horizontalPanel_3.add(gameNameTextBox);
		verticalPanel.add(decoratorPanel);
		decoratorPanel.setWidth("100%");
		decoratorPanel.setWidget(horizontalPanel_4);
		horizontalPanel_4.setWidth("100%");
		playerListBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int i = playerListBox.getSelectedIndex();
				if (i < 0)
					return;
				Player p = game.getPlayer(playerListBox.getItemText(i));
				chckbxNpc.setChecked(p.isNpr());
				playerNameTextBox.setText(p.getName());
				playerPasswordTextBox.setText(p.getPassword());
			}
		});
		horizontalPanel_4.add(playerListBox);
		playerListBox.setWidth("128px");
		playerListBox.setVisibleItemCount(5);
		verticalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		horizontalPanel_4.add(verticalPanel_2);
		verticalPanel_2.setWidth("100%");
		
		verticalPanel_2.add(chckbxNpc);
		horizontalPanel_5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		verticalPanel_2.add(horizontalPanel_5);
		horizontalPanel_5.setWidth("100%");
		
		horizontalPanel_5.add(lblNewLabel_1);
		playerNameTextBox.setText("");
		
		horizontalPanel_5.add(playerNameTextBox);
		playerNameTextBox.setWidth("100%");
		
		verticalPanel_2.add(horizontalPanel_6);
		
		horizontalPanel_6.add(lblPassword);
		
		horizontalPanel_6.add(playerPasswordTextBox);
		verticalPanel.add(decoratorPanel_2);
		decoratorPanel_2.setWidth("100%");
		
		decoratorPanel_2.setWidget(horizontalPanel_7);
		horizontalPanel_7.setSize("100%", "17px");
		
		horizontalPanel_7.add(htmlNewHtml);
		htmlNewHtml.setWidth("449px");
		commitButton.setStyleName("button green medium");
		horizontalPanel_7.add(commitButton);
		commitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Wwar.editMode = chckbxLocEdit.isChecked();
				if (!gameNameTextBox.getText().equals(game.getName())){
					changeGameName(gameNameTextBox.getText());
				}
				int i = playerListBox.getSelectedIndex();
				if (i >= 0){
					changePlayer(game.getPlayer(playerListBox.getItemText(i)));
				}
			}
		});
		commitButton.setEnabled(false);
		btnNewButton_1.setStyleName("button green medium");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				greetingService.saveGame(game.getName(), new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						htmlNewHtml.setText(caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						htmlNewHtml.setText(htmlNewHtml.getText() + "\nGame saved");
					}
				});
			}
		});
		btnNewButton_1.setEnabled(false);
		
		horizontalPanel_7.add(btnNewButton_1);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setWidth("100%");
		btnNewButton.setStyleName("button green medium");
		
		horizontalPanel.add(btnNewButton);
		btnRunTests.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ArmorHelperTest aht = new ArmorHelperTest();
				aht.test();
			}
		});
		btnRunTests.setStyleName("button green medium");
		
		horizontalPanel.add(btnRunTests);
		
	}
	
	public void setData() {
		chckbxLocEdit.setChecked(Wwar.editMode);
		for (Player player: game.getPlayers()){
			playerListBox.addItem(player.getName());
		}
		gameNameTextBox.setText(game.getName());
	}
	
	public Button getCloseButton(){
		return btnNewButton;
	}

	protected void changePlayer(Player player) {
		String oldName = player.getName();
		player.setName(playerNameTextBox.getText());
		player.setPassword(playerPasswordTextBox.getText());
		player.setNpr(chckbxNpc.isChecked());
		greetingService.setPlayer(game.getName(), oldName, player, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				htmlNewHtml.setText(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				htmlNewHtml.setText(htmlNewHtml.getText() + "\nPlayer changed");
			}
		});
	}

	private Game game;
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private final HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
	private final Label lblPassword = new Label("Password:");
	private final TextBox playerPasswordTextBox = new TextBox();
	private final HorizontalPanel horizontalPanel_7 = new HorizontalPanel();
	private final HTML htmlNewHtml = new HTML("New HTML", true);
	private final Button btnNewButton_1 = new Button("Save");
	private final Button btnRunTests = new Button("Run Tests");
	private void changeGameName(final String text) {
		
		greetingService.setGameName(game.getName(), text, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				htmlNewHtml.setText(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				htmlNewHtml.setText(htmlNewHtml.getText() + "\nGame name changed");
				game.setName(text);
				saveGame(text);
			}
		});		
	}		
	private void saveGame(final String text) {
		
		greetingService.saveGame(game.getName(),new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				htmlNewHtml.setText(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				htmlNewHtml.setText(htmlNewHtml.getText() + "\nGame name changed");
				game.setName(text);
			}
		});		
	}		
}
