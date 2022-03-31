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
	Socket s;//�洢��ͬ�ͻ��ˡ�
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
		 btnconnect.addActionListener(this);//�����߳�һ
		
	p1.add(new Label("������"));p1.add(tfip);
	p1.add(new Label("�˿ڿ�"));
	p1.add(tfport);
	p1.add(btnconnect);p1.setBackground(Color.LIGHT_GRAY);

	mainframe.add(tamsg,"Center");

		mainframe.add(p2,"South");

		p2.add(new Label("˵����"));p2.add(tsay);p2.add(btsend);
		p2.setBackground(Color.LIGHT_GRAY);
		mainframe.pack();
		
		mainframe.setVisible(true);
		
		btsend.addActionListener(this);//�����̶߳���
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	try {	// TODO �Զ����ɵķ������
		String connand=e.getActionCommand();
		Object event=e.getSource();
		if(event==btnconnect){
			tamsg.append("�����ӣ���˵��\n");
			String ip=tfip.getText();
			String port=tfport.getText();//�˿�Ϊ����
		
			 s=new Socket(ip,Integer.parseInt(port));//�ŵ������Ǳ�֤���connect����ܷ��ͣ�
			 //���ŵ������if�������connectҲ���ԣ�����connect��ûһ�����ˣ�һ��send�㶨��
			//����һ��������������������	����Ҫ�ȷ����ݡ�
			 Thread t=new clientthread(s);
			 t.start();//�������������������send�󱻴�ͨ������ġ���������̡߳�����
			//���̣߳�ִ��btsend.addlisten...->�ڶ���if->��ͨ��
		}else if(event==btsend){
				
			String send=tsay.getText();
		if(send.equals("")){
			
			return;
		}
		tamsg.append("\t\t\t\t\t"+"��:"+tsay.getText()+"\n");
		OutputStream os=s.getOutputStream();
		
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os));
		
		writer.write(send);
		writer.newLine();
		writer.flush();
		
			
		}
		} 
			 catch (Exception e1) {
				// TODO �Զ����ɵ� catch ��
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
						//ԭ��ֻ�ܽ���һ��
					java.io.InputStream is= s.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(is));
				String hear=reader.readLine();//��������
				
				tamsg.append("�Է�:"+hear+"\n");
					}
				} catch (Exception e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				 
			 }
		
	}
	public static void main(String [] args){
		
		new clientsocket();
	}
}
