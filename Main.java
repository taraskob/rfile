package rfile;
import java.util.*;
import java.text.*;
public class Main implements EventHandler {
	static RFile file=new RFile();
	static Contr contr=new Contr(file);

	public static void main(String[] args){
		file.setFparam(args[0]);
		file.rfal();
		
		System.out.println(file.toString());

		Thread t=new Thread(contr);
		t.start();
	}
	@Override
	public void onChange(){
		System.out.println(file.toString());
	}


}
