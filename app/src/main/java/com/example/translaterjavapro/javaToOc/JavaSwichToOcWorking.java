package com.example.translaterjavapro.javaToOc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JavaSwichToOcWorking extends JavaSwichToOc{
	static String path = "F:/decks_gather01/Anki-Android-2.5.4/AnkiDroid/src/main/java/com/ichi2/libanki/";
//	static String fileName = "Sched.java";
	JavaSwichToOcWorking j;
	static String names[] = {"AnkiPackageExporter", "Card", "CardStats", "Collection", "Consts", "Decks", "Finder", "LaTeX", "Media", 
		"Models", "Note", "Sched", "Sound", "Stats", "Storage", "Tags", "Utils"};
	
	
	public static void main(String[] args) {
		for (int i = 0; i < names.length; i++) {
			
			//0. 设置fname;
			JavaSwichToOcWorking j = new JavaSwichToOcWorking();
//		j.fname = (fileName.split("\\."))[0];
			//1. 获得数据源；
//			String fileName = new Scanner(System.in).nextLine();
//			fileName += ".java";
			String fileName = names[i] + ".java";
			String inputf = path+fileName;
			File file = new File(inputf);
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//2. 获得目标数据体；
			FileOutputStream fos = null;
			OutputStreamWriter osw = null;
			BufferedWriter  bw = null;
			try {
				String outputf = path+"__z__"+fileName;
				fos = new FileOutputStream(new File(outputf));
				osw = new OutputStreamWriter(fos, "UTF-8");
				bw=new BufferedWriter(osw);
			} catch (Exception e) {
				
			}
			
			
			
			//3. 开始干活儿；
			j.dowork(reader, bw);
			//4. 关闭流；
			try {
				reader.close();
				bw.close();
				osw.close();
				fos.close();
			} catch (Exception e) {
				
			}
		}
	}
	private void dowork(BufferedReader reader, BufferedWriter bw) {
		String tempString = null;
        int line = 1;
        // 一次读入一行，直到读入null为文件结束
        try {
			while ((tempString = reader.readLine()) != null) {
				Pattern p = Pattern.compile("[ |\t]*(private|public|protected|class.*\\{.*|interface.*\\{.*)+.*");
				Matcher m = p.matcher(tempString);
				
				if(m.find()){
//					bw.write("//"+tempString+"\t\n");
					//转换成oc代码；
					String ocTempString = getOcStr(tempString);
					if(ocTempString != null){
						System.out.println(ocTempString);
						bw.write(ocTempString);
					}
					bw.write("\t\n\t\n");
					System.out.println();
				}else{
				}
			    line++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getOcStr(String ts) {
		String ocStr = "出错啦！";
		
		//它是错误代码；或仅仅是注释代码；
		String regex1_err ="([ \t]*[/*\\*]+)[ a-zA-Z]*";
		Matcher m1 =Pattern.compile(regex1_err).matcher(ts);
			
		//它是一个内部类声明；或是接口声明；
		String regex2_class =".*(class|interface).*\\{[ \t]*";
		Matcher m2 =Pattern.compile(regex2_class).matcher(ts);
		
		//它是一个同步方法；
		String regex3_err =".*synchronized.*";
		Matcher m3 =Pattern.compile(regex3_err).matcher(ts);
		
		//它是一个一般方法；
		String regex4_err ="[ \t]*(private|public|protected)+ +(static|final|synchronized)* ?([\\[\\]<>a-zA-Z]+)[ \t]+([_a-zA-Z]+)\\((.*)\\).*";
		Matcher m4 =Pattern.compile(regex4_err).matcher(ts);
		
		//它是一个常量；
		String regex5_err =".*static final.*";
		Matcher m5 =Pattern.compile(regex5_err).matcher(ts);
		
		//它是一个构造方法；
		String regex7_err ="[ \t]*(private|public|protected)+ ([_a-zA-Z]+)\\((.*)\\).*";
		Matcher m7 =Pattern.compile(regex7_err).matcher(ts);
		
		//它是一个成员属性；
		String regex6_err =".+;[ \t]*";
		Matcher m6 =Pattern.compile(regex6_err).matcher(ts);
		
		if(m1.find()){
			//它是错误代码；或仅仅是注释代码；
			ocStr = null;
			
		}else if (m2.matches()) {
			//它是一个内部类声明；或是接口声明；
			ocStr = "//" + ts + "\t\n" + "//这是内部类或接口的声明开始";
		}else if (m7.matches() && !ts.contains("class")) {
			//它是一个构造方法；
			ocStr = "//" + ts + "\t\n" + _getConstructorMethodStr(ts);
		}else if (m3.matches()) {
			//它是一个同步方法；
			ocStr = "//" + ts + "\t\n" + _getNormalMethodStr(ts);
		}else if (m4.matches()) {
			//它是一个一般方法；
			ocStr = "//" + ts + "\t\n" + _getNormalMethodStr(ts);
		}else if (m5.matches()) {
			//它是一个常量；
			ocStr = "//" + ts + "\t\n" + _getConstStr(ts);
		}else if (m6.matches()) {
			//它是一个成员属性；
			ocStr = "//" + ts + "\t\n" + _getVarStr(ts);
		}else{
			//它是一个不确定，要仔细研究；
			ocStr = "//" + ts + "\t\n" + "//请谨慎，这个不完整" + _getExceStr(ts);
		}
		
		return ocStr;
	}

}
