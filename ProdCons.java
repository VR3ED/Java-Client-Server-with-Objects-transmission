
public class ProdCons {
	public static void main(String[] args) {
		
		SharedBuffer buf=new SharedBuffer();
	    new Produttore(1, buf).start();
	    new Produttore(2, buf).start();
	    new Consumatore(1, buf).start();
	    new Consumatore(2, buf).start();
	}
}
