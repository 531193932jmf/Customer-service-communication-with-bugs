package wlw2019.shiyan; 
import java.io.*;   
import java.util.Vector;   
public class Inventory{   
	@SuppressWarnings("rawtypes")
	Vector inv=new Vector();
	@SuppressWarnings("rawtypes")
	Vector O= new Vector();        
	@SuppressWarnings("rawtypes")
	Vector R = new Vector();        
	@SuppressWarnings("rawtypes")
	Vector A = new Vector();           
	@SuppressWarnings("rawtypes")
	Vector D = new Vector();     
	@SuppressWarnings("rawtypes")
	Vector ship= new Vector();   
	@SuppressWarnings("rawtypes")
	Vector error = new Vector();   
	@SuppressWarnings("unchecked")
	public void duquwenjian(String filename){
		try {
			BufferedReader read=new BufferedReader(new FileReader(filename));
		String s=null;
		while((s=read.readLine())!=null){
			String []name=s.split("\t");
			inv.add(new Inv(name[0],Integer.parseInt(name[1]),name[2],name[3]));
		}
		read.close();
		
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void caozuo(String filename){
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(filename));
			String s=null;
			while((s=br.readLine())!=null){
				String [] name=s.split("\t");
				char m=name[0].charAt(0);//首字母
				if(m=='O'){
					O.add(new Off(name[1],Integer.parseInt(name[2]),name[3]));
				}else if(m=='A'){
					A.add(new Add(name[1],name[2],name[3]));
				}
				else if(m=='R'){
					R.add(new Rece(name[1],Integer.parseInt(name[2])));
				}
				else if(m=='D'){
					D.add(new Delete(name[1]));
				}
				
			}
			
			br.close();
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
public void add(){
	for(int i=0;i<A.size();i++){
		Inv name=new Inv((Add)A.get(i));
		inv.add(name);
	}
}
	public void dealrece(){
		for(int i=0;i<R.size();i++){
			for(int j=0;j<inv.size();j++){
				if(((Rece)R.get(i)).item==((Inv)inv.get(j)).item)
				{
					((Inv)inv.get(j)).plus(((Rece)R.get(i)).quantity);
				}
				
			}
			
		}
		
	}
	@SuppressWarnings("unchecked")
	public void erroff(){
		for(int i=0;i<O.size();i++)
			for(int j=i+1;j<O.size();j++){//比较每一行
			    if( ((Off)O.get(i)).item == ((Off)O.get(j)).item ){   
                    if( ((Off)O.get(i)).quan > ((Off)O.get(j)).quan){   
                        Off Name = (Off)O.get(i);   
                        O.set(i,(Off)O.get(j));   
                        O.set(j,Name); 
                    }   
                }   
			}
	}
	@SuppressWarnings("unchecked")
	public void offer(){//记录货物信息
		for(int i=0;i<O.size();i++)
			for(int j=0;j<inv.size();j++){   
			     if( ((Off)O.get(i)).item .equals(((Inv)inv.get(j)).item) ){   
	                    if( ((Inv)inv.get(j)).quan >= ((Off)O.get(i)).quan )
	                    {     
	                        ((Inv)inv.get(j)).cut( ((Off)O.get(i)).quan );   
	                        ship.add( (Off)O.get(i) );   
	                    }
	                     
	                    else
	                    {   
	                        Err Name = new Err( (Off)O.get(i) );   
	                        error.add(Name);   
	                    }   
	                }  
			}
	}
	
	@SuppressWarnings("unchecked")
	public void delete(){
		for(int i=0; i<D.size(); i++){   
            for(int j=0; j<inv.size(); j++){   
                if( ((Delete)D.get(i)).item .equals(((Inv)inv.get(j)).item) ){   
                    if(((Inv)inv.get(j)).quan == 0){   
                        inv.remove(j);   
                        break;   
                    }   
                    else{   
                        error.add(new Err("0", ((Inv)inv.get(j)).item, ((Inv)inv.get(j)).quan ));   
                        break;   
                    }   
                }   
            }   
        }   	
	}
	@SuppressWarnings("unchecked")
	public void errship(){//相同相加
		
		  for(int i=0; i<ship.size(); i++)   
	            for(int j=i+1; j<ship.size(); j++){   
	                if( ((Off)ship.get(i)).custom == ((Off)ship.get(j)).custom && ((Off)ship.get(i)).item == ((Off)ship.get(j)).item)
	                {   
	                    Off name = new Off( ((Off)ship.get(i)).item, ((Off)ship.get(i)).quan+((Off)ship.get(j)).quan, ((Off)ship.get(i)).custom );   
	                    ship.set(i,name);   //取代特定位置的元素。
	                    ship.remove(j);   //移除特定位置元素。
	                    j--;   
	                }   
	            }   
	}
	public void errinv(){
		for(int i=0; i<inv.size(); i++)   
            for(int j=i+1; j<inv.size(); j++){   
                if(Integer.parseInt(((Inv)inv.get(i)).item) > Integer.parseInt(((Inv)inv.get(j)).item)){   
                    Inv Name = (Inv)inv.get(i);   
                    inv.set(i,(Inv)inv.get(j));   
                    inv.set(j,Name);   
                }   
            }   
		
	}
	public void write(String []filename){
		
		try{   
             
               
            BufferedWriter ibw = new BufferedWriter(new FileWriter(filename[0]));   
            BufferedWriter sbw = new BufferedWriter(new FileWriter(filename[1]));   
            BufferedWriter ebw = new BufferedWriter(new FileWriter(filename[2]));   
               
            for(int i=0; i<inv.size(); i++){   
                Inv Name = (Inv)inv.get(i);   
                String s = Name.item +"\t"+ Name.quan +"\t"+ Name.sup +"\t"+ Name.description;   
                ibw.write(s);
                ibw.newLine();   
            }   
               
            for(int i=0; i<ship.size(); i++){   
                Off Name = (Off)ship.get(i);   
                String s = Name.custom +"\t"+Name.item +"\t"+ Name.quan ;   
                sbw.write(s);   sbw.newLine();   
             
            }   
               
            for(int i=0; i<error.size(); i++){   
                Err Name = (Err)error.get(i);   
                String s = Name.custom +"\t"+ Name.item +"\t"+ Name.quan;   
                ebw.write(s);   ebw.newLine();   
            }   
            ibw.close();   
            sbw.close();   
            ebw.close();   
        }   
        catch(Exception e){   
        }   
    }
	public static void main(String[] arg){   
	
	
Inventory invent=new Inventory();
String a="D:/java/jdk8/Inventory.txt";
String b="D:/java/jdk8/Transactions.txt";
invent.duquwenjian(a);
invent.caozuo(b);
invent.offer();
invent.dealrece();
invent.add();

invent.delete();

invent.erroff();
invent.errship();
invent.errinv();
String []file=new String[3];
file[0]="D:/java/jdk8/Newlnventory.txt";
file[1]="D:/java/jdk8/shipping.txt";
file[2]="D:/java/jdk8/Error.txt";
invent.write(file);

	}
}

 class Inv{
	 String item;
	 int quan;
	 String sup;
	 String description;
	 public Inv(String item,int quan,String sup,String description){
		 
		 this.item=item;
		 this.quan=quan;
		 this.sup=sup;
		 this.description=description;
	 }
	public Inv(Add add) {
		this.item=add.item;
		this.description=add.description;
		this.quan=0;
		this.sup=add.sup;
		// TODO 自动生成的构造函数存根
	}
	 public void plus(int q){
		 this.quan+=q;
	 }
	 public void cut(int q){
		 this.quan-=q;
	 }
 }
 
 class Off{//货物类，无货物信息而已。
	 String item;
	 int quan;
	 String custom;
	 public Off(String item, int quan,String custom){
		 this.item=item;
		 this.quan=quan;
		 this.custom=custom;
	 }
	 
 }
 class Add{//添加类
	 String item;
	 String sup;
	 String description;
	 Add(String i,String sup, String description){
		 this.description=description;
		 this.item=i;
		 this.sup=sup;
	 }
 }
 
 class Rece{//接受类
	 String item;
	 int quantity;
	 public Rece(String i,int q){
		 this.item=i;
		 this.quantity=q;
	 }
	 
	 
 }
 class Delete{//删除类
	 String item;
	 Delete(String i){
		 this.item=i;
	 } 
 }
 class Err{//错误类
	 String custom;
	 String item;
	 int quan;
	
	 Err(String i,String s,int q){
		 this.custom=i;
		 this.item=s;
		 this.quan=q;
	 }
	 Err(Off o){
		 this.custom=o.custom;
		 this.item=o.item;
		 this.quan=o.quan;
	 }
 }
