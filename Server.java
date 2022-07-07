import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static final int PORT = 8080;
	ServerSocket ss;
	SharedBuffer sb;
	
	public Server() throws IOException{
		ss = new ServerSocket(PORT);
		System.out.println("server avviato");
	}
	
	private void exec() throws IOException  {
		while(true) {
			Socket socket = ss.accept();	
			try {
				new ServerSlave(socket, sb);
			} catch (IOException e) {
				e.printStackTrace();
				socket.close();
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		 new Server().exec();
	}
}
