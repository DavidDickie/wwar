package com.dickie.wwar.client;

import java.util.List;

import com.dickie.wwar.shared.PlayerMessage;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TurnReportPanel extends DecoratorPanel {
	private static final VerticalPanel verticalPanel = new VerticalPanel();
	private static final HTML panel = new HTML("Turn results");
	private static final HorizontalPanel horizontalPanel = new HorizontalPanel();
	private static final Button btnNewButton = new Button("Close");
	private static final ScrollPanel scrollPanel = new ScrollPanel();
	private static final DialogBox dialogBox2 = new DialogBox();
	private static final TurnReportPanel trp = new TurnReportPanel();
	private static  boolean initialized = false;
	
	
	public TurnReportPanel() {
		
		setWidget(verticalPanel);
		
		verticalPanel.add(scrollPanel);
		scrollPanel.setSize("600", "600");
		scrollPanel.setWidget(panel);
		panel.setSize("100%", "100%");
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		
		horizontalPanel.add(btnNewButton);
		
	}
	
	private static void showMessages (List<PlayerMessage> msgs){
		StringBuffer sb = new StringBuffer();
		sb.append("<table><tr><td>");
		for (PlayerMessage pm : msgs){
			sb.append(pm.getMessage()).append("</td></tr>\n<tr><td>");
		}
		sb.append("------------END-----------</td></tr></table>");
		panel.setHTML(sb.toString());
	}
	
	public static void messageDialogBox(List<PlayerMessage> msgs){
		showMessages(msgs);
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox2.hide();
			}
		});

		dialogBox2.setText("Turn results");
		dialogBox2.center();
		if (!initialized){
			initialized = true;
			dialogBox2.add(trp);
		}
		btnNewButton.setEnabled(true);
		btnNewButton.setFocus(true);
	}

}
