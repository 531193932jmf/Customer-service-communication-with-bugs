package wlw2019.shiyan;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
class policelisten implements DocumentListener
{
JTextArea inputtext,showtext;
public void setinputtext(JTextArea text){
	this.inputtext=text;
}
public void setshowtext(JTextArea text){showtext=text;}
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO �Զ����ɵķ������
		this.changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO �Զ����ɵķ������
this.changedUpdate(e);		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO �Զ����ɵķ������
 String str=inputtext.getText();
 String regex="[\\s\\d\\p{Punct}]+";
 String [] words=str.split(regex);
 Arrays.sort(words);
 //�ֵ����С��������
 showtext.setText(null);//��ա�
 for(String s:words){showtext.append(s+",");
 }
	}
	}

class windowdocument extends JFrame
{
JTextArea inputtext,showtext;
policelisten listen;
windowdocument(){
	init();
	this.setLayout(new FlowLayout());
	setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
void init()
{
inputtext=new JTextArea(15,18);
showtext=new JTextArea(15,18);
add(new JScrollPane(inputtext));
add(new JScrollPane(showtext));
listen=new policelisten();
listen.setinputtext(inputtext);
listen.setshowtext(showtext);
(inputtext.getDocument()).addDocumentListener(listen);//���ĵ�ע����������Ȼ���ĵ��Ķ�����������Ϊ-��-����
}
}
public class test6 {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
windowdocument win=new windowdocument();
win.setBounds(10, 10, 460, 360);
win.setTitle("����documentevent�¼�");

	}

}
