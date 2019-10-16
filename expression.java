package ruangong2;

import java.awt.*;
import java.util.Random;

public class expression {
	Random random=new Random();
	int tag=1;
	String expression1=new String();  //放表达式
	int fhnum=0;  //放表达式的符号个数
	int i=1;
	int row1=(int) (random.nextInt(4));       //第一个运算符的种类 0：+ 、1：-、2：×、3：÷
	int row2=(int) (random.nextInt(4));       //第二个运算符的种类 0：+ 、1：-、2：×、3：÷
	int row3=(int) (random.nextInt(4));       //第三个运算符的种类 0：+ 、1：-、2：×、3：÷
	int kuh1=(int) (random.nextInt(2));       //括号 0：有括号，1：无括号
	int kuh2=(int) (random.nextInt(2));       //两个运算符时，有括号的话是加在哪个位置
    int kuh3=(int) (random.nextInt(3));       //三个运算符时，有一个括号的话是加在哪个位置'
    int kuh4=(int) (random.nextInt(5));       //三个运算符时，有二个括号的话是加在哪个些位置
    
	public expression(int b) {
		char fuhao1=change2(row1);  //随机数控制符号种类
		char fuhao2=change2(row2);
		char fuhao3=change2(row3);
		
		int range=b;    //题目中的数值范围	
		int number=(int) (random.nextInt(3)+1);     //随机数，控制运算符个数
		int tag1=(int) (random.nextInt(2));         //利用分母是否等于1控制生成的随机数是整数还是分数
		int tag2=(int) (random.nextInt(2)); 
		int tag3=(int) (random.nextInt(2)); 
		int tag4=(int) (random.nextInt(2)); 
		
		int z1,m1,z2,m2,z3,m3,z4,m4;
		boolean t;
		do {
		     z1=(int) (random.nextInt(range));			 //分子1
		     m1=(int) (random.nextInt(range)+1);	         //分母1
		     if(tag1==1) m1=1;
		     t=bijiao(z1,m1,range);  
		}while(!t);
		do {
			z2=(int) (random.nextInt(range));			//分子2
		    m2=(int) (random.nextInt(range)+1);			//分母2
		    if(tag2==1) m2=1;
		    t=bijiao(z2,m2,range);
		}while(!t);
		do{
			z3=(int) (random.nextInt(range));			//分子3
			m3=(int) (random.nextInt(range)+1);			//分母3
			if(tag3==1) m3=1;
		    t=bijiao(z3,m3,range);
		}while(!t);
		do {
			z4=(int) (random.nextInt(range));			//分子4
		    m4=(int) (random.nextInt(range)+1);			//分母4
		    if(tag4==1) m4=1;
		    t=bijiao(z4,m4,range);
		}while(!t);
		
		fhnum=number;
		if(number==1) {//只有一个运算符的情况
             expression1=change(z1,m1)+" "+fuhao1+" "+change(z2,m2)+" "+"=";
			 } 
	    if(number==2) {//有两个运算符的情况
	    	if(kuh1==0) {//表达式中无括号
	    		expression1=change(z1,m1)+" "+fuhao1+" "+change(z2,m2)+" "+fuhao2+" "+change(z3,m3)+" "+"=";
	    	}
	    	else {//表达式中有括号
	    		if(kuh2==0) {//随机数kuh2为0时括号在前面
	    			expression1="("+" "+change(z1,m1)+" "+fuhao1+" "+change(z2,m2)+" "+")"+" "+fuhao2+" "+change(z3,m3)+" "+"=";
	    		}
	    		else if(kuh2==1) {//随机数kuh2为1时括号在后面
	    			expression1=change(z1,m1)+" "+fuhao1+" "+"("+" "+change(z2,m2)+" "+fuhao2+" "+change(z3,m3)+" "+")"+" "+"=";
	    		}
	    	}
			 }
		if(number==3) {//有三个运算符的情况
			if(kuh1==0) {//表达式中无括号
				expression1=change(z1,m1)+" "+fuhao1+" "+change(z2,m2)+" "+fuhao2+" "+change(z3,m3)+" "+fuhao3+" "+change(z4,m4)+" "+"=";
			}
			else if(kuh1==1) {//表达式中有括号
				if(kuh2==0) {//表达式有一对括号
					switch(kuh3) {//利用kuh3的值控制括号的位置
					case 0:
						expression1="("+" "+change(z1,m1)+" "+fuhao1+" "+change(z2,m2)+" "+")"+" "+fuhao2+" "+change(z3,m3)+" "+fuhao3+" "+change(z4,m4)+" "+"=";
					case 1:
						expression1=change(z1,m1)+" "+fuhao1+" "+"("+" "+change(z2,m2)+" "+fuhao2+" "+change(z3,m3)+" "+")"+" "+fuhao3+" "+change(z4,m4)+" "+"=";
					case 2:
						expression1=change(z1,m1)+" "+fuhao1+" "+change(z2,m2)+" "+fuhao2+" "+"("+" "+change(z3,m3)+" "+fuhao3+" "+change(z4,m4)+" "+")"+" "+"=";
					}
				}else if(kuh2==1) {//表达式有两对括号
				    switch(kuh4) {//利用kuh4的值控制括号的位置
				    case 0:
				    	expression1="("+" "+"("+" "+change(z1,m1)+" "+fuhao1+" "+change(z2,m2)+" "+")"+" "+fuhao2+" "+change(z3,m3)+" "+")"+" "+fuhao3+" "+change(z4,m4)+" "+"=";
				    case 1:
				    	expression1="("+" "+change(z1,m1)+" "+fuhao1+" "+change(z2,m2)+" "+")"+" "+fuhao2+" "+"("+" "+change(z3,m3)+" "+fuhao3+" "+change(z4,m4)+" "+")"+" "+"=";
				    case 2:
				    	expression1="("+" "+change(z1,m1)+" "+fuhao1+" "+"("+" "+change(z2,m2)+" "+fuhao2+" "+change(z3,m3)+" "+")"+" "+")"+" "+fuhao3+" "+change(z4,m4)+" "+"=";
				    case 3:
				    	expression1=change(z1,m1)+" "+fuhao1+" "+"("+" "+"("+" "+change(z2,m2)+" "+fuhao2+" "+change(z3,m3)+" "+")"+" "+fuhao3+" "+change(z4,m4)+" "+")"+" "+"=";
				    case 4:
				    	expression1=change(z1,m1)+" "+fuhao1+" "+"("+" "+change(z2,m2)+" "+fuhao2+" "+"("+" "+change(z3,m3)+" "+fuhao3+" "+change(z4,m4)+" "+")"+" "+")"+" "+"=";
					}
					
				}
			}
	
		}

	}
	
	//函数change()用于将随机生成的数值转换为自然数或真分数的形式
	public static String change(int z,int m) {
		if(z>=m) {
			if(z%m==0)
			{
				int a=z/m;
				return a+"";
			}
			else
			{
				int b=z/m;
				int c=z%m;
				return b+"'"+c+"/"+m;
			}
		}else {
			if(z==0) return 0+"";
			else return z+"/"+m;
		}
			
	}
	
	//定义一个将随机数字转为对应符号字符的函数
	public static char change2(int a){
		int b=a;
		switch(b) {
		case 0:return '+';
		case 1:return '-';
		case 2:return '*';
		case 3:return '÷';
		default :return ' ';
		}
	}
	//定义一个判断生成的数值是否在用户规定范围内的函数
	public static boolean bijiao(int a,int b,int range) {
		int c=a;
		int w=b;
		int i=range;
		int l=i*w;
		if(l>=a) {
			return true;
		}return false;
	}
	

	
	
}
