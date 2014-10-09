import com.marvel.quest.network.client.ClientApplication;

import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.context.Application;


public class GameClient extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;
	
	public GameClient() {
		super(800, 600);
	}

	public static void main(String[] args) throws Exception {
		GameClient game = new GameClient();
		game.init();
	}

	@Override
	public Application startApplication() {
		return new ClientApplication(w, h);
	}

}
