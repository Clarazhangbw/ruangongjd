package ruangong2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.swing.*;
import ruangong2.option;
public class Frame extends JFrame implements ActionListener{
	
	option op=new option();
	String file_path;
	String rans_path;                    
	String yans_path;
	Font F1=new Font("等线",0,24);            
	Font F2=new Font("黑体",0,20);
	Color C1=new Color(238,238,238);
	Color C2=new Color(112,48,160);
	Color C3=new Color(255,255,255);
	//定义组件
	JPanel jp1=new JPanel();                            
	JLabel Label1=new JLabel("      欢迎来到小学生四则运算题目产生系统");
	JPanel jp2=new JPanel();
	JPanel jp21=new JPanel();
	
	JPanel jp22=new JPanel();
	JLabel Label2=new JLabel("题目数量:");
	JTextField jtf1=new JTextField(12);
	
	JPanel jp23=new JPanel();
	JLabel Label3=new JLabel("数值范围:");
	JTextField jtf2=new JTextField(12);
	
	JPanel jp24=new JPanel();
	
	JPanel jp3=new JPanel();
	JButton gn=new JButton("生成");
	JButton check=new JButton("校对");
	JLabel Label4=new JLabel("         ");
	
	   
	public Frame(){
		//添加组件，设计用户界面
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		this.setResizable(false);
		this.setLocation(700, 250);
		
	    jp1.setLayout(new BorderLayout());          //"欢迎...系统"标签
		jp1.setPreferredSize(new Dimension(500,100));
		jp1.setBackground(C2);
		Label1.setFont(F1);
		Label1.setForeground(C3);
		jp1.add(Label1,BorderLayout.CENTER);
		this.add(jp1,BorderLayout.NORTH);
		
		jp2.setLayout(new GridLayout(4,1));
		jp2.setPreferredSize(new Dimension(500,250));
		jp2.setBackground(C1);
		jp2.add(jp21);
		
		Label2.setFont(F2);                          //"题目数量"标签及输入框
		jtf1.setPreferredSize(new Dimension(30,30));
		jp22.add(Label2);
		jp22.add(jtf1);
		jp2.add(jp22);
		
		Label3.setFont(F2);                          //"数值标签"标签及输入框
		jtf2.setPreferredSize(new Dimension(15,30));
		jp23.add(Label3);
		jp23.add(jtf2);
		jp2.add(jp23);
		
		jp2.add(jp24);
		this.add(jp2,BorderLayout.CENTER);
		
		jp3.add(gn);                                 //"生成"按钮
		gn.setFont(F2);
		gn.setForeground(C3);
		gn.setBackground(C2);
		gn.setFocusPainted(false);
		gn.setPreferredSize(new Dimension(80,35));
		jp3.add(Label4);
		jp3.add(check);
		check.setFont(F2);                           //"校对"按钮
		check.setFocusPainted(false);
		check.setForeground(C3);
		check.setBackground(C2);
		check.setPreferredSize(new Dimension(80,35));
		jp3.setPreferredSize(new Dimension(500,150));
		jp3.setBackground(C1);
		this.add(jp3,BorderLayout.SOUTH);
		
		gn.setActionCommand("生成");                //设置按钮监听
		gn.addActionListener(this);
		check.setActionCommand("校对");
		check.addActionListener(this);
	 }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand().equals("生成")) {        
			if(jtf1.getText().matches("\\d+")&jtf2.getText().matches("\\d+")) {
				String jtf11=jtf1.getText();            //获得用户输入的题目数量
				String jtf22=jtf2.getText();            //获取用户输入的题目数值范围
				int tnum=Integer.parseInt(jtf11);       //记录题目的数量值
				int tran=Integer.parseInt(jtf22);      //记录数值的范围
				JFileChooser jfc=new JFileChooser();    //弹出选择文件框
				if(jfc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
					File file=jfc.getSelectedFile();    
					file_path=file.getAbsolutePath();   //获取用户选择的存放题目文件的绝对路径
			
					String []bds=new String[tnum];       //放有效题目的数组
					String []ans=new String[tnum];       //放有效答案的数组 
					for(int ooo=0;;ooo++) {             //循环生成题目，计算后查重，若表达式有效，则存进有效表达式数组
				        expression exp3=new expression(tran);
				        String exp3hz=op.ZTH(exp3.expression1);
				        op.Compute(exp3hz);
				        if(op.tagg==1) {
				           check1 ck1=new check1(tnum,exp3,bds);
				           ans[ck1.c]=op.result;	
					       if(ck1.c==tnum-1) break;
					    }
					}
					
					String []fpsplit=file_path.split("\\.");  
					String ans_file_path=fpsplit[0]+"_answer"+"."+fpsplit[1];  //根据题目文件的路径产生答案文件的路径
					File answerfile=new File(ans_file_path);                   //在该路径生成答案文件
					try {                                                     //将有效表达式数组写入题目文件
						OutputStream out1=new FileOutputStream(file,true);
						for(int u=0;u<tnum;u++) {
							try {
								int xuhao=u+1;
			                    String xh=String.valueOf(xuhao);
								out1.write(xh.getBytes());
								out1.write('.');
								out1.write(' ');
								out1.write(bds[u].getBytes());
								out1.write('\r');
								out1.write('\n');
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {                                                 //将对应的答案写入答案文件
						OutputStream out2=new FileOutputStream(answerfile,true);
						for(int u1=0;u1<tnum;u1++) {
							try {
								int xuhao1=u1+1;
			                    String xh1=String.valueOf(xuhao1);
								out2.write(xh1.getBytes());
								out2.write('.');
								out2.write(' ');
								out2.write(ans[u1].getBytes());
								out2.write('\r');
								out2.write('\n');
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "题目与答案文件均生成完毕");
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "无法生成题目，输入框内应为自然数");
			}
		}
		 if(e.getActionCommand().equals("校对")) {
			                                               
			 String tql=jtf1.getText();         //获取用户输入的题目数量     
		     int tmsl=Integer.parseInt(tql);    //记录题目数量
			 int xb1=0;
			 int xb2=0;
			 int []wrongxb=new int[tmsl];       //创建一个放错误题目的序号的数组
			 int []rightxb=new int[tmsl];       //创建一个放正确题目的序号的数组
			 int wrong=0;                       //记录错的数量
			 int right=0;                      //记录对的数量
			 String []bzsz=new String[tmsl];    //放标准答案的数组
			 String []yhsz=new String[tmsl];    //放用户答案的数组
			 JOptionPane.showMessageDialog(this, "请选择标准答案文件"); 
			 JFileChooser jfc5=new JFileChooser();  
			 if(jfc5.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
				 File rans=jfc5.getSelectedFile();
			     rans_path=rans.getAbsolutePath();               //获取用户选择的标准答案答案文件路径
			 }
			 JOptionPane.showMessageDialog(this, "请选择你的答案文件");
			 JFileChooser jfc6=new JFileChooser();
			 if(jfc6.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
				 File yans=jfc6.getSelectedFile();
			     yans_path=yans.getAbsolutePath();              //获取用户选择的用户答案文件路径
			 }
			 try {
				BufferedReader br1=new BufferedReader(new InputStreamReader(new FileInputStream(rans_path)));
				for(String line1=br1.readLine();line1!=null;) {
					bzsz[xb1]=line1;
					line1=br1.readLine();                     //逐行读取标准答案，放进标准答案数组
					xb1++;
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 try {
					BufferedReader br2=new BufferedReader(new InputStreamReader(new FileInputStream(yans_path)));
					for(String line2=br2.readLine();line2!=null;) {
						yhsz[xb2]=line2;                   //逐行读取用户答案，放进用户答案数组
						line2=br2.readLine();
						xb2++;
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 for(int spl=0;spl<tmsl;spl++) {             //用.分割获取答案序号及答案
				 String []fg1=bzsz[spl].split("\\. ");
				 String []fg2=yhsz[spl].split("\\. ");
				 String bj=op.Operate("-",fg1[1],fg2[1]);   //两个答案数值相减，若相等则用户答案正确，否则答案错误
				 if(bj.equals("0")) {
					 rightxb[right]=spl+1;
					 right++;                              //记录正确答案的题目序号及数量
				 }
				 else {
					 wrongxb[wrong]=spl+1;
					 wrong++;                              //记录错误答案的题目序号及数量
				 }
			 }
			 String []ranpafg=rans_path.split("\\.");
			 String grade_path=ranpafg[0]+"_grade"+"."+ranpafg[1];  //创建成绩文件，放在答案文件所在的路径中
		     File gradefile=new File(grade_path);
			 String correct1="";
			 String wrong1="";
			 for(int coco=0;coco<right;coco++) {               //形成校对结果
				 if(coco<right-1) {
				      correct1=correct1+rightxb[coco]+",";
				 }
				 if(coco==right-1) {
					 correct1=correct1+rightxb[coco];
				 }
			 }
			 for(int ydd=0;ydd<wrong;ydd++) {
				 if(ydd<wrong-1) {
				      wrong1=wrong1+wrongxb[ydd]+",";
				 }
				 if(ydd==wrong-1) {
					 wrong1=wrong1+wrongxb[ydd];
				 }
			 }
			 String correct="Correct:"+right+"("+correct1+")";
			 String Wrong="Wrong:"+wrong+"("+wrong1+")";
			 try {                                        //将校对结果写入校对文件中
				OutputStream out3=new FileOutputStream(gradefile,true);
				try {
					out3.write(correct.getBytes());
					out3.write('\r');
					out3.write('\n');
					out3.write(Wrong.getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 JOptionPane.showMessageDialog(this, "已生成校对文件");
		 }
	}
	

}

