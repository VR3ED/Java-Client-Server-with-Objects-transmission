import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

public class Consumatore extends Thread {
	SharedBuffer thebuf;
	int Id;
	
	//aggiunte
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	InetAddress addr;
	//
	
	public Consumatore(int id, SharedBuffer b){
		this.thebuf=b;
		this.Id=id;
		
		//aggiunte
		try {
			addr = InetAddress.getByName(null);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		//
		
	}
	
	
	public void run(){
		TheData td;
		
		//AGGIUNTE
		try {
			socket = new Socket(addr, 8080);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter( new BufferedWriter (new OutputStreamWriter(socket.getOutputStream())),true );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//
		
		while(true){
			try{
				
				//Aggiunte
				out.println("GET");
				TheData oggetto = riceviOggetto();
				System.out.println(in.readLine());
				//				
				
				//parti tolte
				//td=thebuf.getData();
				//System.out.println("Cons_"+Id+" read "+td);
				Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
			} catch(InterruptedException e) {} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		x.close();
	}
	
	//METODO PER RICEVERE OGGETTI THEDATA
	private TheData riceviOggetto() throws ClassNotFoundException, IOException {
		// get the input stream from the connected socket
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        // read the list of messages from the socket
        TheData v = (TheData) objectInputStream.readObject();
        objectInputStream.close();
        return v;
	}
	
}
