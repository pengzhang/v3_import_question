package com.ctb.v3_import_question;

import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
public class YFileChooser implements ActionListener{
    JFrame frame=new JFrame("错题本题库导入工具");
    JTabbedPane tabPane=new JTabbedPane();//选项卡布局
    Container con=new Container();//布局1
    Container con1=new Container();//布局2
    JLabel label1=new JLabel("选择目录");
    JTextField text1=new JTextField();
    JButton button1=new JButton("选择");
    JButton button2=new JButton("开始导入");
    JFileChooser jfc=new JFileChooser();//文件选择器
    YFileChooser(){
        jfc.setCurrentDirectory(new File("d:\\"));//文件选择器的初始目录定为d盘
        //下面两行是取得屏幕的高度和宽度
        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int)(lx/2)-150,(int)(ly/2)-150));//设定窗口出现位置
        frame.setSize(300,150);//设定窗口大小
        frame.setContentPane(tabPane);//设置布局
       //下面设定标签等的出现位置和高宽
        label1.setBounds(10,10,70,20);
        text1.setBounds(80,10,120,20);
        button1.setBounds(210,10,50,20);
        button2.setBounds(90,40,100,40);

        button1.addActionListener(this);//添加事件处理
        button2.addActionListener(this);
        con.add(label1);
        con.add(text1);
        con.add(button1);
        con.add(button2);
        con.add(jfc);
        tabPane.add("目录选择",con);//添加布局1
        frame.setVisible(true);//窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
    }
    public void actionPerformed(ActionEvent e){//事件处理
    	File f= null;
        if(e.getSource().equals(button1)){//判断触发方法的按钮是哪个
            jfc.setFileSelectionMode(1);//设定只能选择到文件夹
            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
            if(state==1){
                return;//撤销则返回
            }
            else{
                f=jfc.getSelectedFile();//f为选择到的目录
                text1.setText(f.getAbsolutePath());
            }
        }
        
        if(e.getSource().equals(button2)){//判断触发方法的按钮是哪个
        	try {
        		System.out.println("开始导入");
				execute(f.getAbsolutePath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    }
    
    
    public static void execute(String filePath) throws IOException{
    	String path = filePath;
		System.out.println(path);
		String[] subjects = { "数学", "化学", "物理", "英语" };
		for (String subject : subjects) {
			List<File> fs = FileScan.scanFolder(path, subject);
			if (fs.size() > 0) {
				for (File file : fs) {
					String file_txt = FileScan.htmlTotextFile(file);
					Question.uploadQuestion(file_txt, subject);
				}
			} else {
				System.out.println("该"+subject+"学科目录下没有文件");
			}
		}
    }

}
