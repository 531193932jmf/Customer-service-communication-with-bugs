package wlw2019.shiyan;

import java.awt.*;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Scanner;

import org.omg.CORBA.portable.InputStream;

public class clientsocket implements ActionListener {
	Frame mainframe=new Frame("client");
	Socket s;//存储不同客户端。
	Panel p1=new Panel();
	TextField tfip=new TextField("172.28.130.220",20);
	TextField tfport=new TextField("4031",20);
	Button btnconnect=new Button("  connect  ");

	TextArea tamsg=new TextArea(10,50);

	Panel p2=new Panel();
	TextField tsay=new TextField(50);
	Button btsend=new Button("  Send  ");

	public clientsocket(){
		mainframe.add(p1,"North");
		 btnconnect.addActionListener(this);//里面线程一
		
	p1.add(new Label("服务器"));p1.add(tfip);
	p1.add(new Label("端口口"));
	p1.add(tfport);
	p1.add(btnconnect);p1.setBackground(Color.LIGHT_GRAY);

	mainframe.add(tamsg,"Center");

		mainframe.add(p2,"South");

		p2.add(new Label("说话："));p2.add(tsay);p2.add(btsend);
		p2.setBackground(Color.LIGHT_GRAY);
		mainframe.pack();
		
		mainframe.setVisible(true);
		
		btsend.addActionListener(this);//里面线程二。
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	try {	// TODO 自动生成的方法存根
		String connand=e.getActionCommand();
		Object event=e.getSource();
		if(event==btnconnect){
			tamsg.append("已连接，请说话\n");
			String ip=tfip.getText();
			String port=tfport.getText();//端口为整型
		
			 s=new Socket(ip,Integer.parseInt(port));//放到这里是保证点击connect后才能发送，
			 //若放到下面的if里，无需点击connect也可以，这样connect就没一点用了，一键send搞定。
			//还有一个问题是无阻塞函数。	这样要先放内容。
			 Thread t=new clientthread(s);
			 t.start();//里面有阻塞函数，点击send后被打通才输出的。阻塞这个线程。进行
			//主线程，执行btsend.addlisten...->第二个if->打通。
		}else if(event==btsend){
				
			String send=tsay.getText();
		if(send.equals("")){
			
			return;
		}
		tamsg.append("\t\t\t\t\t"+"我:"+tsay.getText()+"\n");
		OutputStream os=s.getOutputStream();
		
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os));
		
		writer.write(send);
		writer.newLine();
		writer.flush();
		
			
		}
		} 
			 catch (Exception e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
	}

	class clientthread extends Thread{
		
		private Socket s;
		public clientthread(Socket s){
			this.s=s;
		}
		 public void run(){
			 
				try {
					while(true){
						//原本只能接受一次
					java.io.InputStream is= s.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(is));
				String hear=reader.readLine();//阻塞函数
				
				tamsg.append("对方:"+hear+"\n");
					}
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				 
			 }
		
	}
	public static void main(String [] args){
		
		new clientsocket();
	}
}
