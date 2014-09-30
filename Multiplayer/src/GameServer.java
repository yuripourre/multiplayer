import server.ActionServerListener;
import br.com.etyllica.network.examples.action.kryo.KryoActionServer;
import br.com.etyllica.network.realtime.ServerActionListener;
import br.com.etyllica.network.server.Server;


public class GameServer {

	public static final int ACTION_TCP_PORT = 4455;
	public static final int ACTION_UDP_PORT = 4466;
	
	public static void main(String[] args) throws Exception {
		
		final int interval = 100;
		ActionServerListener serverListener = new ActionServerListener(interval);
		
		Server server = new KryoActionServer(ACTION_TCP_PORT, ACTION_UDP_PORT, serverListener);
		
		server.init();
		server.prepare();
		server.bind();
		
		new Thread(serverListener).start();
	}
	
}
