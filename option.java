package ruangong2;

import java.util.Stack;

public class option {
    
	int tagg=1;                                  //减运算过程中是否出现负数或除运算过程中分母是否为0的标记
	String result=" ";                          //用于存放表达式的运算结果
	String f1=" ";                              //用于存放通分后的第一个分数
	String f2=" ";                              //用于存放通分后的第二个分数
	
	public String ZTH(String s){                //用于将中缀表达式转换为后缀表达式的函数
		String h=" ";                           
		Stack<String> os=new Stack<String>();   //利用栈存储运算符，越往栈顶的优先级越高
		String[] ex=s.split(" ");
		int l=ex.length;
		for(int i=0;i<l-1;i++)                  //l-1，因为表达式最后一个是=，不用加进去
		{
			String part=ex[i];
			switch(part){                       
			                                    
			case "+":
			case "-":
				while(os.size()!=0){
					String s1=os.pop();
					if(s1.equals("(")){
						os.push(s1);            //将(重新压进栈，然后退出循环
						break;
					}
					h=h+s1+" ";
				}
				os.push(part);
				break;
			
			case "*":                                
			case "÷":
				while(os.size()!=0){
					String s2=os.pop();
					if(s2.equals("+")||s2.equals("-")||s2.equals("(")){
						os.push(s2);
						break;
					}else{
						h=h+s2+" ";
					}
				}
				os.push(part);
				break;
				
			case "(":                                     
				os.push(part);
				break;
			
			case ")":                        //遇到）就把栈中的运算符弹出直到遇到（
				while(os.size()!=0){                
					String s3=os.pop();
					if(s3.equals("(")){
						break;
					}else{
						h=h+s3+" ";
					}
				}
				break;
				
			default:                       //part为数值的情况
				h=h+part+" ";
				break;
						
			}
		}
		while(os.size()!=0){             //将最后栈中剩余的运算符一并弹出
			h=h+os.pop()+" ";
		}
		return h;
	}
	
	public void Compute(String s){           //利用后缀表达式计算表达式的结果
		tagg=1;                                 //初始化tagg
		Stack<String> cs=new Stack<String>();  //用于计算的栈
		result=" ";                     //初始化，用于保存计算结果
		String[] hex=s.split(" ");             //这里我们从第hex[1]开始，因为0那个是空格
		int l1=hex.length;
		for(int j=1;j<l1;j++){                  //从左到右遍历后缀表达式
			if(hex[j].matches("\\d+")||hex[j].matches("\\d+/\\d+")||hex[j].matches("\\d+'\\d+/\\d+")){
				cs.push(hex[j]);               //如果遍历到的是数值则直接入栈
			}
			else{                             //如果遍历到的是运算符，将栈顶的两个数值弹出，计算结果后，再将结果压进栈
				String s1=cs.pop();
				String s2=cs.pop();
				result=Operate(hex[j],s2,s1);
				cs.push(result);
				if(tagg==0)break;                //如果标记为0，停止计算，此时的result是无效的
			}
		}
		result=cs.pop();                        
	}                                            
	

	public String Operate(String op,String s1,String s2){   //计算最简表达式
		String result=" ";                                  //将两个数值统一化为分数的形式进行计算
		if(s1.matches("\\d+")){                              //将自然数s1化为分数，分母为1
			f1=s1+"/"+1;                                     
		}
		if(s1.matches("\\d+/\\d+")){                        
			f1=s1;
		}
		if(s1.matches("\\d+'\\d+/\\d+")){                    //如果s1为" ' / "这种形式，将其化为假分数
			String[] ss1=s1.split("'");
			String sz1=ss1[0];
			String sf=ss1[1];
			String[] sff=sf.split("/");
			String sfz=sff[0];
			String sfm=sff[1];
			int z1=Integer.parseInt(sz1);
			int fz=Integer.parseInt(sfz);
			int rfm=Integer.parseInt(sfm);
			int rfz=z1*rfm+fz;
			f1=rfz+"/"+rfm;			
		}
		if(s2.matches("\\d+")){                              //s2的转化规则与s1相同
			f2=s2+"/"+1;
		}
		if(s2.matches("\\d+/\\d+")){
			f2=s2;
		}
		if(s2.matches("\\d+'\\d+/\\d+")){
			String[] ss2=s2.split("'");
			String sz2=ss2[0];
			String sf=ss2[1];
			String[] sff=sf.split("/");
			String sfz=sff[0];
			String sfm=sff[1];
			int z2=Integer.parseInt(sz2);
			int fz=Integer.parseInt(sfz);
			int rfm=Integer.parseInt(sfm);
			int rfz=z2*rfm+fz;
			f2=rfz+"/"+rfm;				
		}
		
		switch(op){                                          //判断操作符属于哪一类
		case "+":
			result=faddf(f1,f2);
			break;
	    
		case "-":
			result=fminusf(f1,f2);
			break;
		
		case "*":
			result=fmulf(f1,f2);
			break;
			
		case "÷":
			result=fdevf(f1,f2);
		    break;
		
		
		}
		return result;
	}
	
    public String faddf(String s1,String s2){          //实现两个分数相加
		
		String[] s11=s1.split("/");                    //分别获得两个分数的分子分母
		String sfz1=s11[0];
		String sfm1=s11[1];
		String[] s22=s2.split("/");
		String sfz2=s22[0];
		String sfm2=s22[1];
		int fz1=Integer.parseInt(sfz1);
		int fm1=Integer.parseInt(sfm1);
		int fz2=Integer.parseInt(sfz2);
		int fm2=Integer.parseInt(sfm2);
		int y1=fz1*fm2;                                //对两个分数进行通分
		int y2=fz2*fm1;
		int y3=y1+y2;
		int rfm=fm1*fm2;
		if(rfm==1)return String.valueOf(y3);           //如果和的分母为1，直接输出分子
		int z=y3/rfm;
		int rfz=y3-z*rfm;
		if(rfz==0)return String.valueOf(z);            //分数化简后只剩下整数部分
		else if(z==0)return rfz+"/"+rfm;               //和的整数部分为0且分数部分不为0的情况
		else return z+"'"+rfz+"/"+rfm;                 //和的整数部分和分数部分都不为0的情况
		
	}
	
	public String fminusf(String s1,String s2){           //实现两个分数相减 
		                                                 
		
		String[] ss1=s1.split("/");                       //分别获得两个分数的分子分母   
		String sfz1=ss1[0];                               
		String sfm1=ss1[1];                              
		int fz1=Integer.parseInt(sfz1);
		int fm1=Integer.parseInt(sfm1);
		String[] ss2=s2.split("/");
		String sfz2=ss2[0];
		String sfm2=ss2[1];
		int fz2=Integer.parseInt(sfz2);
		int fm2=Integer.parseInt(sfm2);
		int z1m2=fz1*fm2;                                  //对这两个分数进行通分
		int z2m1=fz2*fm1;
		int rfm=fm1*fm2;
		int cha=z1m2-z2m1;
		if(rfm==1){                                        //如果得到的两个分数的差的分母为1
			if(cha<0){tagg=0;return "invalid";}             //且得到的差的分子为负数，tagg=0
			else return String.valueOf(cha);               //否则直接返回差的分子部分
		}
	                                                       //如果得到的差的分母不为1
		if(cha<0){                                         //且差的分子部分为负数
			tagg=0;                                         //则tagg=0
			return "invaild";
		}else if(cha==0){                                  //如果在差的分母不为1的情况下分子为0
			return "0";                                    //则返回字符串0
		}else{                                             //如果差的分母不为1，且分子大于0
			int z=cha/rfm;                                 //尝试将得到的差化为真分数的形式
			if(z==0){
				return cha+"/"+rfm;
			}else{
				int rfz=cha-z*rfm;
				if(rfz==0) return String.valueOf(z);
				else return z+"'"+rfz+"/"+rfm;
			}
		}
		
	}
	
	public String fmulf(String s1,String s2){                //实现两个分数相乘
	
		String[] s11=s1.split("/");                         //分别获得两个分数的分子分母
		String sfz1=s11[0];
		String sfm1=s11[1];
		String[] s22=s2.split("/");
		String sfz2=s22[0];
		String sfm2=s22[1];
		int fz1=Integer.parseInt(sfz1);
		int fm1=Integer.parseInt(sfm1);
		int fz2=Integer.parseInt(sfz2);
		int fm2=Integer.parseInt(sfm2);
		int z=fz1*fz2;                                      //分子相乘
		int fm=fm1*fm2;                                     //分母相乘
		int y=z/fm;
		if(fm==1)return String.valueOf(z);                  //如果积的分母部分为1，则直接返回分子部分
	
		if(z==0){                                           //如果积的分子为0，则直接返回字符串0
			return "0";
		}else if(y==0){                                     //如果不符合以上两种情况，则将得到的积转化为规定的真分数的形式
			return z+"/"+fm;
		}else{
			int fz=z-y*fm;
			if(fz==0)return String.valueOf(y);
			else return y+"'"+fz+"/"+fm;
		}
	}
	
	public String fdevf(String s1,String s2){               //实现两个分数相除
		String[] s11=s1.split("/");                         //分别获得两个分数的分子分母
		String sfz1=s11[0];
		String sfm1=s11[1];
		String[] s22=s2.split("/");
		String sfz2=s22[0];
		String sfm2=s22[1];
		int fz1=Integer.parseInt(sfz1);
		int fm1=Integer.parseInt(sfm1);
		int fz2=Integer.parseInt(sfz2);
		int fm2=Integer.parseInt(sfm2);
		if(fz2==0){tagg=0;return "invalid";}                  //如果第二个分数为0，tagg=0
		if(fz1==0)return "0";                                //如果第一个分数的分子为0，则直接返回字符串0
		String d1=s1;                                        //否则，第一个分数保持不变
		String d2=fm2+"/"+fz2;                               //第二个分数转化为其倒数
		return fmulf(d1,d2);                                 //除运算转变为乘运算
	}
	
	
	
}


