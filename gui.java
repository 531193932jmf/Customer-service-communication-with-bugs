package wlw2019.shiyan;
import wlw2019.url.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.portable.InputStream;

public class gui implements ActionListener{
	ServerSocket server;
	static int clientnum=0;
	Map<String,Socket> socketmap=new HashMap<String,Socket>();
Frame mainframe=new Frame("server");
Panel p1=new Panel();
TextField tfport=new TextField("4031",50);
Button btnlisten=new Button("  Listen  ");
TextArea tamsg=new TextArea(10,50);
Panel p2=new Panel();
TextField tsay=new TextField(50);
Button btsend=new Button("  Send  ");
Choice socketlist=new Choice();
public gui(){
	mainframe.add(p1,"North");
	btnlisten.addActionListener(this);
	
p1.add(new Label("站口"));
p1.add(tfport);
p1.add(btnlisten);p1.setBackground(Color.LIGHT_GRAY);
mainframe.add(tamsg,"Center");
	mainframe.add(p2,"South");
	socketlist.add("请选择。。。");
	p2.add(socketlist);
	p2.add(new Label("说话："));p2.add(tsay);p2.add(btsend);
	p2.setBackground(Color.LIGHT_GRAY);
	mainframe.pack();
	mainframe.setVisible(true);
		btsend.addActionListener(this);

}
public static void main(String []args){
	new gui();}


 class ServerRecvThread extends Thread{
	 Socket s;
	 public ServerRecvThread(Socket s)
	 {this.s=s;
	 this.run();
	 }
	 public void run(){
		try {
			while(true)
			{
		java.io.InputStream is=  s.getInputStream();
BufferedReader reader=new BufferedReader(new InputStreamReader(is));
		String hear=reader.readLine();
		tamsg.append("\n对方:"+hear+"\n"+"(收到请尽快回复)");
		if (hear.equals("")==false){
			
			break;
		   	
		}
		}	
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		 
	 }
}
 class serverlistenthread extends Thread{
	 public serverlistenthread()
	 {
		 this.run();
	 }
	 public void run(){
		 try {
			 while(true){
		Socket	s=server.accept();
		tamsg.append("\n(已连接，等待消息)\n");
		String clientname="client"+clientnum;
		clientnum++;
		socketmap.put(clientname, s);
		socketlist.add(clientname);
		Thread t=new ServerRecvThread(s);
		t.start();
		sleep(300);
		}
		 } catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	 }
 }

@Override
public void actionPerformed(ActionEvent e) {
	// TODO 自动生成的方法存根
	String command=e.getActionCommand();
	Object event=e.getSource();
	if(event==btnlisten){
		int port=Integer.parseInt(tfport.getText());
		try {
			server=new ServerSocket(port);
			tamsg.append("(等待连接。。。)"+port+"\n");
		Thread listenthread=new serverlistenthread();
		
		listenthread.start();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	
		
	}else if(event==btsend){
		String send=tsay.getText();
		if(send.equals(""))
		{
			tamsg.append(" \n ");
		}
		tamsg.append("\t\t\t\t\t"+"我:"+tsay.getText()+"\n");
		try {
			Socket s=socketmap.get(socketlist.getSelectedItem());
		java.io.OutputStream os = s.getOutputStream();
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os));
	writer.write(send);
	writer.newLine();
	writer.flush();

		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
}
	}
}