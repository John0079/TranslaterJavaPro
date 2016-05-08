package com.example.translaterjavapro.javaToOc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JavaSwichToOc {
	protected String fname;



	protected String _getConstructorMethodStr(String str3) {
		String regex ="[ \t]*(private|public|protected)+ ([_a-zA-Z]+)\\((.*)\\).*";
//		String regex ="[ \t]*(private|public|protected)";
//		String str3 = "        public Object runFilter(Object arg, Object... args) {	";
		String conp[] = {"我错啦", "我错啦", "我错啦", "我错啦", "我错啦"};
		Matcher m = Pattern.compile(regex).matcher(str3);
		if(m.find()){
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
//			System.out.println(m.group(4));
//			System.out.println(m.group(5));
			String conp1[] = {m.group(1), m.group(2), m.group(3)};
			conp = conp1;
		}
		String re = getReturnType(conp[1]);
		String name = getMemberName(conp[1]);
		String vars = conp[2].equals("") ? "" : getVars(conp[2]);
		re = "- (instancetype)";
		name = "init";
		
		String zhushi = "/**构造方法注释*/" + "\t\n";
		zhushi += re + name + vars + ";";
		
		return zhushi;
	}

	protected String _getExceStr(String str3) {
		String regex ="[ \t]*(private|public|protected)+ (static|final|synchronized)* ?([\\[\\]<>a-zA-Z]+)[ \t]+([_a-zA-Z]+)(.*=.*)";
		// String str3 = "public static Pattern sExpressionPattern = Pattern.compile(\"\\[\\$\\](.+?)\\[/\\$\\]\",";
		// static NSString * const XMGCommentId = @"comment";
		
		String conp[] = {"我错啦", "我错啦", "我错啦", "我错啦", "我错啦"};
		Matcher m = Pattern.compile(regex).matcher(str3);
		if(m.find()){
//			System.out.println(m.group(1));
//			System.out.println(m.group(2));
//			System.out.println(m.group(3));
//			System.out.println(m.group(4));
//			System.out.println(m.group(5));
			String conp1[] = {m.group(1), m.group(2), m.group(3), m.group(4), m.group(5)};
			conp = conp1;
		}
		String re = confType(conp[2]);
		String name = getMemberName(conp[3]);
		String vars = conp[4];
		String constStr = "/**常量注释*/" + "\t\n";
		constStr += "static " + re + " const" + name + vars;
		
		return constStr;
	}

	protected String _getVarStr(String str2) {
		String regex ="[ \t]*(private|public|protected)+ +(static|synchronized)*([\\[\\]<>a-zA-Z]+)[ \t]+([_a-zA-Z]+)(.*)";
		
		// static NSString * const XMGCommentId = @"comment";
		
		String conp[] = {"我错啦", "我错啦", "我错啦", "我错啦", "我错啦"};
		Matcher m = Pattern.compile(regex).matcher(str2);
		if(m.find()){
//			System.out.println(m.group(1));
//			System.out.println(m.group(2));
//			System.out.println(m.group(3));
//			System.out.println(m.group(4));
//			System.out.println(m.group(5));
			String conp1[] = {m.group(1), m.group(2), m.group(3), m.group(4)};
			conp = conp1;
		}
		String typeProperty = confProperty(conp[2]);
		String name = getMemberName(conp[3]).trim();
		String memberVarStr = "/**注释*/" + "\t\n";
		memberVarStr += typeProperty + name + ";";
		
		return memberVarStr;
	}


	protected String _getConstStr(String str1) {
		String regex ="[ \t]*(private|public|protected)+ +static final ([\\[\\]<>a-zA-Z]+)[ \t]+([_a-zA-Z]+)(.*)";
		
		// static NSString * const XMGCommentId = @"comment";
		
		String conp[] = {"我错啦", "我错啦", "我错啦", "我错啦", "我错啦"};
		Matcher m = Pattern.compile(regex).matcher(str1);
		if(m.find()){
//			System.out.println(m.group(1));
//			System.out.println(m.group(2));
//			System.out.println(m.group(3));
//			System.out.println(m.group(4));
//			System.out.println(m.group(5));
			String conp1[] = {m.group(1), m.group(2), m.group(3), m.group(4)};
			conp = conp1;
		}
		String re = confType(conp[1]);
		String name = getMemberName(conp[2]);
		String vars = conp[3];
		String constStr = "/**常量注释*/" + "\t\n";
		constStr += "static " + re + " const" + name + vars;
		
		return constStr;
	}

	protected String _getNormalMethodStr(String str) {
		String regex ="[ \t]*(private|public|protected)+ +(static|final|synchronized)* ?([\\[\\]<>a-zA-Z]+)[ \t]+([_a-zA-Z]+)\\((.*)\\).*";
		
		String conp[] = {"我错啦", "我错啦", "我错啦", "我错啦", "我错啦"};
		Matcher m = Pattern.compile(regex).matcher(str);
		if(m.find()){
//			System.out.println(m.group(1));
//			System.out.println(m.group(2));
//			System.out.println(m.group(3));
//			System.out.println(m.group(4));
//			System.out.println(m.group(5));
			String conp1[] = {m.group(1), m.group(2), m.group(3), m.group(4), m.group(5)};
			conp = conp1;
		}
		String re = getReturnType(conp[2]);
		String name = getMemberName(conp[3]).trim();
		String vars =conp[4].equals("") ? "" : getVars(conp[4]);
		
		if(name.trim().equals("toString")){
			name = "discription";
		}else if (name.trim().equals("clone")) {
			name = "copy";
		}
		

		String zhushi = "/**方法注释*/" + "\t\n";
		zhushi += re + name + vars + ";";
		
		return zhushi;
	}

	protected String confProperty(String type) {
//		@property (nonatomic, assign) NSInteger mNid;
		String property = "";
		switch (type) {
		case "String":
			property = "@property (nonatomic, copy) NSString *";
			break;
		case "boolean":
			property = "@property (nonatomic, assign) BOOL ";
			break;
		case "int":
		case "long":
			property = "@property (nonatomic, assign) NSInteger ";
			break;
		case "double":
			property = "@property (nonatomic, strong) NSNumber *";
			break;
		default:
			property = "@property (nonatomic, strong) " + confType(type);
			break;
		}
		return property;
	}

	protected String getReturnType(String str) {
		String string = confType(str);
		if(str.contains("<")){
			return "//小心，这里可能出错！！"+"-("+string+")";
		}
		else {
			return "-("+string+")";
		}
	}

	protected String confType(String str) {
		String s = str + " *";
		if(str.contains("<")){
			str = str.substring(0, str.indexOf('<'));
		}else if (str.contains("[")) {
			str = "Array";
		}
		switch (str) {
		case "String":
			s = "NSString *";
			break;
		case "int":
			s = "NSInteger";
			break;
		case "long":
			s = "NSInteger";
			break;
		case "boolean":
			s = "BOOL";
			break;
		case "double":
			s = "NSNumber *";
			break;
		case "Map":
		case "HashMap":
			s = "NSDictionary *";
			break;
		case "Array":
		case "List":
		case "ArrayList":
		case "LinkedList":
			s = "NSArray *";
			break;
		default:
			break;
		}
		return s;
	}

	protected String getMemberName(String string) {
		return " "+string;
	}

	protected String getVars(String string) {
		//-(NSDictionary *)_getQA:(BOOL)reload browser:(BOOL)browser;
		//public arralist<hhaf> rem(long did, boolean cardsToo, boolean childrenToo)
		String newVar = null;
		try {
			newVar = "With";
			String vars[] = string.split(",");
			for (int i = 0; i < vars.length; i++) {
				String str = vars[i];
				String pair[] = str.trim().split(" ");
				newVar += pair[1] +":" + "(" + confType(pair[0]) + ")" + pair[1];
				String endStr = ((i == (vars.length - 1))?"":" ");
				newVar += endStr;
			}
			
		} catch (Exception e) {
			newVar = "匹配出错啦！你自己弄吧！";
		}
		return newVar;
	}
	
}
