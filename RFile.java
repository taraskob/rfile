package rfile;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class RFile {
	private ArrayList<String> altxt=new ArrayList<String>();
	private ArrayList<String> altxttmp=new ArrayList<String>();
	private Date date=new Date();
	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.YY kk:mm:ss");
	private List<EventHandler> listener=new ArrayList<>();
	private String fparam;
	private Lock lock=new ReentrantLock();


	public void setFparam(String fparam){
		lock.lock();
		this.fparam=fparam;
		lock.unlock();
	}
	
	public ArrayList<String> rfal() {
		lock.lock();

		try(BufferedReader reader=new BufferedReader(new FileReader(fparam));) {
			altxt.removeAll(altxt);
			String line=reader.readLine();
			altxt.add(line);
			while(line!=null){
				line=reader.readLine();
				altxt.add(line);
			}
		}catch (FileNotFoundException e) {
			System.out.println("FileNotFound...");
		}catch(IOException e) {
			System.out.println("I/O error...");
		}
		altxt.remove(null);
		lock.unlock();
		return altxt;
	}
	
	@Override
	public String toString(){
		lock.lock();
		String str=sdf.format(getDate())+"\n";
		for(String item:altxt){str+=item+"\n";
		}
		lock.unlock();
		return str; 
	}

	public void compare(){
		lock.lock();
		altxttmp.removeAll(altxttmp);
		altxttmp.addAll(altxt);
		ArrayList<String> altxt=rfal();
		if(!isEqual(altxttmp)){
			System.out.println(this.toString());
			lock.unlock();
			onChange();
		}
	}
	
	public boolean isEqual(ArrayList<String> str){
		if(altxt.size()!=str.size()){
			return false;}
		else{
			for(int i=0;i<altxt.size();i++){
				if(! altxt.get(i).equals(str.get(i)))
					return false;
			}
			return true;
		}
	}

	public void addListener(EventHandler eventHandler){
		listener.add(eventHandler);
	}

	private void onChange(){
		for(EventHandler item:listener){
			item.onChange();	
		}
	}

	public Date getDate() {
		date=new Date();
		return date;
	}
}


