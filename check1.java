package ruangong2;

import ruangong2.option;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ruangong2.TreeNode;


public class check1{
	option oo=new option();             
	int []fhs=new int[10000];  //放有效符号个数的字符串数组
	String por="";
	int cc=1;                  //全局变量控制check2
	int c=0;
	int tag5=1;             //标记表达式属不属于有效表达式
	public check1(int i,expression exp1,String []bds) {
		int k=i;                //用户规定的表达式的个数
		
		String b=exp1.expression1;         //当前 产生的表达式
		int d=exp1.fhnum;                  //当前产生的表达式的符号个数
		
	    String e=oo.ZTH(b);          //调用ZTH()将当前生成的表达式转换为后缀表达式
			 String []e1=e.split(" ");  //将后缀表达式用空格分割放入字符串数组
			 int e12=e1.length-1;
			 String []e13=new String[e12];
			 for(int e14=0;e14<e13.length;e14++) {    //去掉后缀表达式最前面的空格
				 e13[e14]=e1[e14+1];
			 }
			 List<TreeNode> list1=buildtree(e13);      //将后缀表示式转换为二叉树并返回广度搜索后的结点列表
		for(c=0;c<k;c++) {                                       
			if(bds[c]==null) break;               //当有效表达式数组为空时
			if((fhs[c])==d) {                      //先判断符号个数，当符号个数相同时，进行查重
		                                           
                String e2=oo.ZTH(bds[c]);        //调用后缀函数获取后缀表达式
                String []e21=e2.split(" ");
                int e23=e21.length-1;
                String []e22=new String[e23];
                for(int e24=0;e24<e22.length;e24++)
                {
				     e22[e24]=e21[e24+1];
			    }                                           
                List<TreeNode> list2=buildtree(e22);        //记录有效表达式的结点列表，作为后续查重比较的参照对象
                List<TreeNode> list3=buildtree(e22);
                List<TreeNode> list4=buildtree(e22);
                List<TreeNode> list5=buildtree(e22);
                List<TreeNode> list6=buildtree(e22);
                List<TreeNode> list7=buildtree(e22);
                List<TreeNode> list8=buildtree(e22);
                List<TreeNode> list9=buildtree(e22);
                List<TreeNode> list10=buildtree(e22);
                List<TreeNode> list11=buildtree(e22);
                List<TreeNode> list12=buildtree(e22);
                List<TreeNode> list13=buildtree(e22);
                List<TreeNode> list14=buildtree(e22);
                List<TreeNode> list15=buildtree(e22);
                
                boolean ch=check2(list1,list2,list3,list4,list5,list6,list7,list8,list9,list10,list11,list12,list13,list14,list15);
			    if(ch==false) { tag5=0; break;}               //如果当前表达式与有效表达式重叠，则舍弃并推出循环
		
		}
		}	
		if (tag5==1) {                                         //如果不重叠，将当前表达式写入有效表达式数组
			bds[c]=b;
			
		}
	}
	public List<TreeNode> buildtree(String []o) {             //将后缀表达式转换为二叉树，返回广度搜索后的结点列表
		Stack<TreeNode> stack=new Stack<TreeNode>();
		List<TreeNode> nodelist=new ArrayList<TreeNode>();
		Queue<TreeNode> myQueue=new LinkedList<TreeNode>();
		List<TreeNode> myQueue1=new ArrayList<TreeNode>();
		 TreeNode root=new TreeNode(" ");
		for(int i=0;i<o.length;i++)                         //将后缀表达式转换为二叉树
		{
              if(o[i].equals("+")||o[i].equals("-")||o[i].equals("*")||o[i].equals("÷"))
              {  
            	  root=new TreeNode(o[i]);
            	  root.right=stack.pop();
            	  root.left=stack.pop();
            	  stack.push(root);
              }
              else
              {
            	  stack.push(new TreeNode(o[i]));
              }
				}
		((LinkedList<TreeNode>) myQueue).add(root);            //广度搜索二叉树，记录广度搜索二叉树后的结点列表   
		while(!myQueue.isEmpty()) {
			TreeNode node=myQueue.poll();
			myQueue1.add(node);
		    if(null!=node.left) {
		    	((LinkedList<TreeNode>) myQueue).add(node.left);
		    }
		    if(null!=node.right) {
		    	((LinkedList<TreeNode>) myQueue).add(node.right);
		    }
		}
		
		return myQueue1; 
} 
	

	public boolean check2(List<TreeNode> list1,List<TreeNode> list2,List<TreeNode> list3,List<TreeNode> list4,List<TreeNode> list5,List<TreeNode> list6,List<TreeNode> list7,List<TreeNode> list8,List<TreeNode> list9,List<TreeNode> list10,List<TreeNode> list11,List<TreeNode> list12,List<TreeNode> list13,List<TreeNode> list14,List<TreeNode> list15) 
	{                                           //利用蛮力法查重
		int []addr=new int[3];                  
		int num=0;
		por="";
		postOrderRe(list1.get(0));
		String list1por=por;
		for(int r=0;r<list2.size();r++) {
			if(list2.get(r).val=="+"||list2.get(r).val=="*")
			{
				addr[num]=r;                   //记录列表中“+”或“*”的树节点的位置
				num++;	                        //记录列表中“+”或“*”的个数
			}
		}                                       //根据列表中“+”或“*”的个数，分析相应的情况
		if(num==0) {                            //如果无+或*，则直接比较后缀表达式，若相同则重叠
			por="";
			postOrderRe(list2.get(0));
			String list2por=por;
			if(list1por.equals(list2por))  return false;
			else return true;
		}
		if(num==1) {            //如果无+或*的个数为1，则根据+或*的位置，获取其另一个同构的二叉树，转换成后缀表达式后与有效表达式的后缀比较，若相同则重叠
			TreeNode trn;               
			por="";
			postOrderRe(list2.get(0));               
			String listpor2=por; 
			if(list1por.equals(listpor2)) return false;  //将当前表达式的后缀表达式与有效表达式的后缀表达式比较，若相同则重叠
			trn=list3.get(addr[0]).left;                 //若不同，调换“+”或“*”位置的结点的左右子树，再判断，若相同则重叠
			list3.get(addr[0]).left=list3.get(addr[0]).right;
			list3.get(addr[0]).right=trn;
			por="";
			postOrderRe(list3.get(0));
			String listpor3=por;
			if(list1por.equals(listpor3))  return false;
			else return true;
		}
		if(num==2) {	    /*如果+或*的个数为2，则根据+或*的位置，分别获取另外三种与其同构的二叉树，加上原本的有效表达式，一共四种情况，
		                                                                分别转换成后缀表达式后与当前表达式的后缀比较，若相同则重叠*/
			List<TreeNode> l3=new ArrayList<TreeNode>();
			List<TreeNode> l4=new ArrayList<TreeNode>();
			List<TreeNode> l5=new ArrayList<TreeNode>();
			List<TreeNode> l6=new ArrayList<TreeNode>();
			l3=list4;
			l4=list5;
			l5=list6;
			l6=list7;
			TreeNode trn4,trn5,trn6,trn7;
			por="";
			postOrderRe(l3.get(0));
			String listpor3=por;
			if(list1por.equals(listpor3))  return false;
			
			trn4=l4.get(addr[0]).left;
			l4.get(addr[0]).left=l4.get(addr[0]).right;
			l4.get(addr[0]).right=trn4;
			por="";
			postOrderRe(l4.get(0));
			String listpor4=por;
			if(list1por.equals(listpor4))  return false;
			
			trn5=l5.get(addr[1]).left;
			l5.get(addr[1]).left=l5.get(addr[1]).right;
			l5.get(addr[1]).right=trn5;
			por="";
			postOrderRe(l5.get(0));
			String listpor5=por;
			if(list1por.equals(listpor5))  return false;
			
			trn6=l6.get(addr[0]).left;
			l6.get(addr[0]).left=l6.get(addr[0]).right;
			l6.get(addr[0]).right=trn6;
			trn7=l6.get(addr[1]).left;
			l6.get(addr[1]).left=l6.get(addr[1]).right;
			l6.get(addr[1]).right=trn7;
			por="";
			postOrderRe(l6.get(0));
			String listpor6=por;
			if(list1por.equals(listpor6))  return false;
			return true;
		}
		
		if(num==3) {        /*如果+或*的个数为3，则根据+或*的位置，分别获取另外七种与其同构的二叉树，加上原本的有效表达式，一共八种情况，
                                                                                         分别转换成后缀表达式后与当前表达式的后缀比较，若相同则重叠*/		
			List<TreeNode> l7=new ArrayList<TreeNode>();
			List<TreeNode> l8=new ArrayList<TreeNode>();
			List<TreeNode> l9=new ArrayList<TreeNode>();
			List<TreeNode> l10=new ArrayList<TreeNode>();
			List<TreeNode> l11=new ArrayList<TreeNode>();
			List<TreeNode> l12=new ArrayList<TreeNode>();
			List<TreeNode> l13=new ArrayList<TreeNode>();
			List<TreeNode> l14=new ArrayList<TreeNode>();
			l7=list8;
			l8=list9;
			l9=list10;
			l10=list11;
			l11=list12;
			l12=list13;
			l13=list14;
			l14=list15;
			TreeNode trn8,trn9,trn10,trn11,trn12,trn13,trn14,trn15,trn16,trn17,trn18,trn19;
			por="";
			postOrderRe(l7.get(0));
			String listpor7=por;
			if(list1por.equals(listpor7))  return false;
			
			trn8=l8.get(addr[0]).left;
			l8.get(addr[0]).left=l8.get(addr[0]).right;
			l8.get(addr[0]).right=trn8;
			por="";
			postOrderRe(l8.get(0));
			String listpor8=por;
			if(list1por.equals(listpor8))  return false;
			
			trn9=l9.get(addr[1]).left;
			l9.get(addr[1]).left=l9.get(addr[1]).right;
			l9.get(addr[1]).right=trn9;
			por="";
			postOrderRe(l9.get(0));
			String listpor9=por;
			if(list1por.equals(listpor9))  return false;
			
			trn10=l10.get(addr[2]).left;
			l10.get(addr[2]).left=l10.get(addr[2]).right;
			l10.get(addr[2]).right=trn10;
			por="";
			postOrderRe(l10.get(0));
			String listpor10=por;
			if(list1por.equals(listpor10))  return false;
			
			trn11=l11.get(addr[0]).left;
			l11.get(addr[0]).left=l11.get(addr[0]).right;
			l11.get(addr[0]).right=trn11;
			trn12=l11.get(addr[1]).left;
			l11.get(addr[1]).left=l11.get(addr[1]).right;
			l11.get(addr[1]).right=trn12;
			por="";
			postOrderRe(l11.get(0));
			String listpor11=por;
			if(list1por.equals(listpor11))  return false;
			
			trn13=l12.get(addr[0]).left;
			l12.get(addr[0]).left=l12.get(addr[0]).right;
			l12.get(addr[0]).right=trn13;
			trn14=l12.get(addr[2]).left;
			l12.get(addr[2]).left=l12.get(addr[2]).right;
			l12.get(addr[2]).right=trn14;
			por="";
			postOrderRe(l12.get(0));
			String listpor12=por;
			if(list1por.equals(listpor12))  return false;
			
			trn15=l13.get(addr[1]).left;
			l13.get(addr[1]).left=l13.get(addr[1]).right;
			l13.get(addr[1]).right=trn15;
			trn16=l13.get(addr[2]).left;
			l13.get(addr[2]).left=l13.get(addr[2]).right;
			l13.get(addr[2]).right=trn16;
			por="";
			postOrderRe(l13.get(0));
			String listpor13=por;
			if(list1por.equals(listpor13))  return false;
			
			trn17=l14.get(addr[0]).left;
			l14.get(addr[0]).left=l14.get(addr[0]).right;
			l14.get(addr[0]).right=trn17;
			trn18=l14.get(addr[1]).left;
			l14.get(addr[1]).left=l14.get(addr[1]).right;
			l14.get(addr[1]).right=trn18;
			trn19=l14.get(addr[2]).left;
			l14.get(addr[2]).left=l14.get(addr[2]).right;
			l14.get(addr[2]).right=trn19;
			por="";
			postOrderRe(l14.get(0));
			String listpor14=por;
			if(list1por.equals(listpor14))  return false;
			return true;
		}return true;
	}
	
	public void postOrderRe(TreeNode biTree) {    //后序遍历二叉树，将二叉树转换为后缀表达式
		
		if(biTree!=null) 
		 {
			postOrderRe(biTree.left);
			postOrderRe(biTree.right);
		    por+=biTree.val;
		}
	}
}	

