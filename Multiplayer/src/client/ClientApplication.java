package client;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.KeyState;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.network.examples.action.ActionServerApplication;
import br.com.etyllica.network.examples.action.kryo.KryoActionClient;
import br.com.etyllica.network.examples.action.model.ActionClient;
import br.com.etyllica.network.examples.action.model.State;
import br.com.etyllica.network.realtime.ClientActionListener;
import br.com.etyllica.network.realtime.model.KeyAction;
import br.com.etyllica.network.realtime.model.Message;

public class ClientApplication extends Application implements ClientActionListener {

	private Map<Integer, State> players = new LinkedHashMap<Integer, State>();
	
	private ActionClient client;

	//Message Stuff
	private int sender = -1;
	private String messageText = "";

	public ClientApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		String host = "127.0.0.1";

		client = new KryoActionClient(host, ActionServerApplication.ACTION_TCP_PORT, ActionServerApplication.ACTION_UDP_PORT, this);
		client.init();
		client.prepare();

		try {
			client.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loading = 100;
	}

	public void update(long now) {
		//System.out.println("Update");
		//if(loading>99)
		//client.update();
	}

	@Override
	public void draw(Graphic g) {

		g.setColor(Color.CYAN);
		g.fillRect(0, 0, w, h);

		g.setColor(Color.BLACK);	
		
		for(State state: players.values()) {
			g.drawRect(state.x, state.y, 50, 50);
			
			if(sender == state.id) {
				g.setColor(Color.BLACK);
				g.drawString(messageText, state.x, 80);
			}			
			
		}		

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_1)) {
			client.sendMessage("Hi!");
		}

		else if(event.isKeyDown(KeyEvent.TSK_2)) {
			client.sendMessage("Help Me!");
		}

		else if(event.isKeyDown(KeyEvent.TSK_3)) {
			client.sendMessage("Run!!");
		}

		//Ignore Repeat Keys
		if(event.getState()!=KeyState.TYPED)
			client.sendKeyAction(new KeyAction(event.getKey(), event.getState()));

		return GUIEvent.NONE;
	}

	@Override
	public void updateStates(State[] states) {

		for(State state: states) {
			
			int id = state.id;
			
			if(!players.containsKey(id)) {
				players.put(id, state);
			}
			
			//Update State
			State oldState = players.get(id);
			oldState.x = state.x;
			oldState.y = state.y;			
			
		}
	}

	@Override
	public void receiveMessage(Message message) {
		sender = message.sender;
		messageText = message.text;
	}

}
