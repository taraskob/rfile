package rfile;

import java.util.*;

class Contr  implements Runnable,EventHandler{
	private RFile myFile= new RFile();
	public Contr (RFile myFile){
		this.myFile=myFile;
	}

	public void run() {
		while(true){ 
			myFile.compare();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void onChange() {

	}
}
