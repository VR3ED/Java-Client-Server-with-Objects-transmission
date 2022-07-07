import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSlave extends Thread {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private SharedBuffer sb;
	
	public ServerSlave(Socket socket, SharedBuffer sb) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader( socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		this.sb = sb;
		start();
	}
	
	public void run() {
		boolean finito=false;
		
		try {
			while(!finito) {
				String str = in.readLine();
				if(str.equals("END")) {
					finito = true;
				}
				else if(str.equals("GET")) {
					TheData oggetto = sb.getData();
					inviaOggeto(oggetto);
					out.println("oggetto"+ oggetto.toString() +  " inserito correttamente");
				}else if(str.equals("SET")){
					TheData oggetto = riceviOggetto();
					sb.setData(oggetto);
					out.println("oggetto"+ oggetto.toString() +  " inserito correttamente");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//METODO PER INVIARE OGGETTI THEDATA
	private void inviaOggeto(TheData oggetto) throws IOException {
		ObjectOutputStream x = new ObjectOutputStream(socket.getOutputStream());
		x.writeObject(oggetto);
	}
	
	//METODO PER RICEVERE OGGETTI THEDATA
	private TheData riceviOggetto() throws ClassNotFoundException, IOException {
		// get the input stream from the connected socket
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        // read the list of messages from the socket
        return (TheData) objectInputStream.readObject();
	}
	
}
