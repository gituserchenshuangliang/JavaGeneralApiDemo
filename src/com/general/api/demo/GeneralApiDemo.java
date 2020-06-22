package com.general.api.demo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Java通用API
 * @author Cherry
 * 2020年4月17日
 */
public class GeneralApiDemo {
	public static void main(String[] args) throws SecurityException, IOException {
		loggerShow();
//		loggerPropertiesShow();
//		resouceBundleShow();
//		regShow();
//	    beanShow();
//		otherShow();
	}
	
    /*
     * Logging会自动发现并使用
     * Log4j log4j-api-2.x.jar 
     * log4j-core-2.x.jar
     * log4j-jcl-2.x.jar
     */
	public static void loggerShow() throws SecurityException, IOException {
		Logger logger = Logger.getLogger(GeneralApiDemo.class.getName());
		
		Logger log = Logger.getGlobal();
		log.info("GLOBAL()");
		
		//OFF,Level.SEVERE,Level.WARNING,Level.INFO,Level.CONFIG,Level. FINE,Level.FINER,Level.FINEST,Level.ALL
		Level level = Level.INFO;
		logger.log(level, "log()传入Leve实例");
		
		logger.info("简化操作");
		logger.warning(() -> "警告信息");
		
		//logger.setLevel(Level.OFF);
		logger.info("关闭日志");
		
		//getHandlers()获取Handler数组
		for(Handler h :logger.getParent().getHandlers()) {
			System.out.println(h.getLevel());
		}
		
		Level[] levels = {Level.SEVERE,Level.WARNING,Level.INFO,Level.CONFIG,Level. FINE,Level.FINER,Level.FINEST,Level.ALL,Level.OFF};
		for (Level l : levels) {
			//logger.log(l,l.getName());
		}
		
		//Handler子类MemoryHandler和StreamHandler,StreamHandler子类ConsoleHandler,FileHandler,SocketHandler
		FileHandler fileHandler = new FileHandler("%h/config.log");
		//%h/config%g.log
		fileHandler.setLevel(Level.INFO);
		//logger.addHandler(new ConsoleHandler());
		logger.addHandler(fileHandler);
		logger.info("已生成config.log,格式XML");
	}
	
	//使用log.properties配置日志
	public static void loggerPropertiesShow() {
		Logger logger = Logger.getLogger(GeneralApiDemo.class.getName());
		ResourceBundle bundle = ResourceBundle.getBundle("logging");
		System.out.println(bundle.getString("handlers"));
		logger.setResourceBundle(bundle);
		logger.info("使用日志配置文件");
	}
	
	//资源,地区，基础名称
	public static void resouceBundleShow() {
		//将show.properties文件放置到classpath下面
		ResourceBundle bundle = ResourceBundle.getBundle("show");
		String name = bundle.getString("name");
		System.out.println(name);
		
		//地区信息 语言+地区
		Locale locale = new Locale("zh","SH");
		//加载show_zh_SH.properties
		ResourceBundle src = ResourceBundle.getBundle("show", locale);
		String country = src.getString("country");
		System.out.println(country);
	}
	
	public static void regShow() {
		//^[a-zA-z]+\d*@([a-zA-Z]+\.)+com
		String reg = "X";
		String text = "对酒X当歌，人X生几X何";
		regExp(reg, text);
	}
	
	//正则表达式
	public static boolean regExp(String reg,String text) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(text);
		while(matcher.find()) {
			System.out.printf("从%d开始到%d匹配到%s%n",matcher.start(),matcher.end(),matcher.group());
		}
		return matcher.matches();
	}
	
	//JavaBean
	public static void beanShow() {
        //java.beans
        try {
            var c = new Clothes("Red", 'L');
            
            BeanInfo bean = Introspector.getBeanInfo(c.getClass());
            
            PropertyDescriptor[] pro = bean.getPropertyDescriptors();
            for (PropertyDescriptor p : pro) {
                System.out.printf("%s,%s,%s,%s",p.getDisplayName(),p.getName(),p.getPropertyType().getName(),p.getValue("color").toString());
                Method m = p.getReadMethod();
                System.out.println(m.getName()+"--"+m.getModifiers()+"--"+m.getParameterCount());
            }
            
            MethodDescriptor[] method = bean.getMethodDescriptors();
            for (MethodDescriptor m : method) {
                String a = m.getDisplayName();
                String b = m.getName();
                System.out.println(a +"="+b);
                Method me = m.getMethod();
                System.out.println(me.getName()+"--"+me.getModifiers()+"--"+me.getParameterCount());
            }
            
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
	}
	
	//StringJoiner,Arrays,Stream
	public static void otherShow() {
		String join = String.join("-", "java","is","cool");
		StringJoiner sj = new StringJoiner("*");
		sj.add("A");
		sj.add("B");
		sj.add("C");
		System.out.println(join+"\n"+sj.toString());
		
		int[] array = new int[] {12,15,12,56};
		IntStream s = Arrays.stream(array);
		int count = s.reduce((x,y)-> x+y ).getAsInt();
		System.out.println(count);
		IntStream.range(1, 10).forEach(System.out::println);
	}
	
	//按照\s空格分割字符串
	public static void splitStr() {
	    String str = "a b  c   d e  f";
        String[] arr = str.split("\\s+");
        for (String s : arr) {
            System.out.println(s);
        }
	}
}
