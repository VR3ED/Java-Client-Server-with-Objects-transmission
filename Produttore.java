import java.util.concurrent.ThreadLocalRandom;
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
import java.util.Random;

public class Produttore extends Thread {
	SharedBuffer thebuf;
	Random rand;
	TheData td;
	int myId;
	
	//aggiunte
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	InetAddress addr;
	//
	
	public Produttore(int id, SharedBuffer b){
		myId=id;
		this.thebuf=b;
		rand=new Random();
		
		//aggiunte
		try {
			addr = InetAddress.getByName(null);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		//
		
		this.start();
	}
	
	public void run(){
		String[] str;
		int n = 0;
		int m = 0;
		str=new String[3];
		
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
		
		while(true) {
			for(int i=1; i<=100; ++i){
				n = rand.nextInt(3);
				for(int j=0; j<=n; j++) {
					m=rand.nextInt(100);
					str[j]="item_"+m;
				}
				for(int j=n; j<=2; j++) {
					str[j]="boh";
				}
				try{
					td=new TheData();
					td.compose(n, str[0], str[1], str[2]);
					
					//Aggiunte
					out.println("SET");
					inviaOggeto(td);
					System.out.println(in.readLine());
					//
					
					//PARTI ELIMINATE
					//thebuf.setData(td); --> elimino questa riga perchè questa operazione verrà presa in carico dal server
					//System.out.println("Prod_"+myId+" added "+ td);
					Thread.sleep(ThreadLocalRandom.current().nextInt(10, 100));
				} catch(InterruptedException e) {} catch (IOException e) {
					e.printStackTrace();
				}
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
