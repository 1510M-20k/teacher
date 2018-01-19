package com.ztl.common;


import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class MysqlHelp {
	
	private static String rootPath = "com.ztl";//目录
	private static String path =  System.getProperty("user.dir")+ "/src/";//项目路径
	private static String jsppath =  System.getProperty("user.dir")+ "/webapp/WEB-INF";//jsp项目路径
	private static Map<String, String> map = new HashMap<String, String>();//selectstr查询//insertstr插入//updatestr更新//remarkstr注释
	private static String upperName;//首字母大写实体名
	private static String lowerName;//首字母小写实体名
	private static int type=1;//1有分页查询,
	//用户自定义
	private static String packName = "user";//包名
	private static String tableName = "t_user";//表名
	private static String entityName = "User";//类名
	public static void main(String[] args) {
    	//准备
    	upperName = upperStr(entityName);//首字母大写
    	lowerName = lowerStr(entityName);//首字母小写
    	boolean res = getFields(tableName);//类class 生成sql//获取字段信息 
    	if(res == true){
    		//代码生成
        	createModel();//生成model实体类
        	createDaoXml();//生成Dao.xml
        	createDaoJava();//生成Dao.java
        	createServiceJava();//生成service.java
        	createServiceImplJava();//生成serviceImpl.java
        	createControllerJava();//生成Controller.java
        	createlistjsp();//生成list.jsp
    	}
	}
	/**
	 * 生成list.jsp
	 */
	public static void createlistjsp(){
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html>\r\n");
		sb.append("<%@page language=\"java\" pageEncoding=\"UTF-8\"%>\r\n");
		sb.append("<%@include file=\"/include/header.inc\"%>\r\n");
		sb.append("<html>\r\n");
		sb.append("<head>\r\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
		sb.append("<title>Insert title here</title>\r\n");
		sb.append("</head>\r\n");
		sb.append("<body>\r\n");
		sb.append("<h1>欢迎来到我的地盘</h1>\r\n");
		sb.append("</body>\r\n");
		sb.append("</html>\r\n");
		String resPath = jsppath+"/"+lowerName+"/list.jsp";
		System.out.println("resPath=" + resPath);
		try {
			FileUtils.writeStringToFile(new File(resPath), sb.toString(),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成Controller.java
	 * @param packName//包名
	 * @param tableName//表名
	 * @param entityName//类名
	 * @param path//系统项目路径
	 */
	public static void createControllerJava(){
		StringBuffer sb = new StringBuffer();
		sb.append("package "  +rootPath+"."+ packName+ ".controller;\r\n\r\n");
		sb.append("import java.util.HashMap;"+"\r\n");
		if(type == 1){
			sb.append("import java.util.List;\r\n");
 		 } 
		sb.append("import java.util.Map;"+"\r\n");
		sb.append("import javax.servlet.http.HttpServletRequest;"+"\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;"+"\r\n");
		sb.append("import org.springframework.stereotype.Controller;"+"\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;"+"\r\n");
		sb.append("import org.springframework.web.bind.annotation.ResponseBody;"+"\r\n");
		sb.append("import org.springframework.web.servlet.ModelAndView;"+"\r\n");
		sb.append("import com.ztl.base.BaseController;\r\n");
		//sb.append("import com.ztl.operation_log.annotation.Log;"+"\r\n");
		//sb.append("import com.ztl.operation_log.enumeration.LogType;"+"\r\n");
		sb.append("import "+rootPath+"."+ packName+".model."+upperName+";\r\n");
		sb.append("import "+rootPath+"."+ packName+".service."+upperName+"Service;\r\n\r\n");
		sb.append("@Controller\r\n");
		sb.append("@RequestMapping(\"/"+lowerName+"\")\r\n");
		sb.append("public class "+upperName+"Controller extends BaseController{\r\n\r\n");
		sb.append("\t@Autowired\r\n");
		sb.append("\tprivate "+upperName+"Service "+lowerName+"Service;\r\n\r\n");
		
		sb.append("\t//新增\r\n");
		sb.append("\t@RequestMapping(\"/insert\")\r\n");
		sb.append("\tpublic Map<String, Object> insert(HttpServletRequest request,"+upperName+" "+lowerName+"){\r\n");
		sb.append("\t\tMap<String, Object> map = new HashMap<String, Object>();\r\n");
		sb.append("\t\t"+lowerName+"Service.insert("+lowerName+");\r\n");
		sb.append("\t\tmap.put(\"success\", true);\r\n");
		sb.append("\t\treturn map;\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\t//修改\r\n");
		sb.append("\t@RequestMapping(\"/update\")\r\n");
		sb.append("\tpublic Map<String, Object> update(HttpServletRequest request,"+upperName+" "+lowerName+"){\r\n");
		sb.append("\t\tMap<String, Object> map = new HashMap<String, Object>();\r\n");
		sb.append("\t\t"+lowerName+"Service.update("+lowerName+");\r\n");
		sb.append("\t\tmap.put(\"success\", true);\r\n");
		sb.append("\t\treturn map;\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\t//删除\r\n");
		sb.append("\t@RequestMapping(\"/delete\")\r\n");
		sb.append("\tpublic Map<String, Object> delete(HttpServletRequest request,String id){\r\n");
		sb.append("\t\tMap<String, Object> map = new HashMap<String, Object>();\r\n");
		sb.append("\t\t"+lowerName+"Service.delete(id);\r\n");
		sb.append("\t\tmap.put(\"success\", true);\r\n");
		sb.append("\t\treturn map;\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\t//进入编辑页面\r\n");
		sb.append("\t@RequestMapping(\"/edit\")\r\n");
		sb.append("\tpublic ModelAndView edit(HttpServletRequest request,String id){\r\n");
		sb.append("\t\t"+upperName+" "+lowerName+" = "+lowerName+"Service.getById(id);\r\n");
		sb.append("\t\trequest.setAttribute(\""+lowerName+"\", "+lowerName+");\r\n");
		sb.append("\t\treturn new ModelAndView(\""+lowerName+"/edit\");\r\n");
		sb.append("\t}\r\n\r\n");
		if(type == 1){
			sb.append("\t//查询分页列表\r\n");
			sb.append("\t@RequestMapping(\"/list\")\r\n");
			sb.append("\tpublic ModelAndView list(HttpServletRequest request,"+upperName+" "+lowerName+"){\r\n");
			sb.append("\t\tint count = "+lowerName+"Service.getCount("+lowerName+");\r\n");
			sb.append("\t\t"+lowerName+".setTotalCount(count);\r\n");
			sb.append("\t\t"+lowerName+".calculatePage();\r\n");
			sb.append("\t\tList<"+upperName+"> "+lowerName+"list = "+lowerName+"Service.getAllList("+lowerName+");\r\n");
			sb.append("\t\trequest.setAttribute(\""+lowerName+"list\", "+lowerName+"list);\r\n");
			sb.append("\t\treturn new ModelAndView(\""+lowerName+"/list\");\r\n");
			sb.append("\t}\r\n\r\n");
			sb.append("\t//获取jsonlist\r\n");
			sb.append("\t@RequestMapping(\"/getJsonlist\")\r\n");
			sb.append("\t@ResponseBody\r\n");
			sb.append("\tpublic List<"+upperName+"> getJsonlist(HttpServletRequest request,"+upperName+" "+lowerName+"){\r\n");
			sb.append("\t\tint count = "+lowerName+"Service.getCount("+lowerName+");\r\n");
			sb.append("\t\t"+lowerName+".setTotalCount(count);\r\n");
			sb.append("\t\t"+lowerName+".calculatePage();\r\n");
			sb.append("\t\tList<"+upperName+"> "+lowerName+"list = "+lowerName+"Service.getAllList("+lowerName+");\r\n");
			sb.append("\t\treturn "+lowerName+"list;\r\n");
			sb.append("\t}\r\n\r\n");
 		 } 
		sb.append("}");
		String packPath = path+ rootPath.replaceAll("\\.", "/")+"/"+packName+"/controller";
	    File file = new File(packPath);
	    if(!file.exists()){
	    	file.mkdirs();
	    }
	    String resPath = packPath+"/"+upperName+ "Controller.java";
		System.out.println("resPath=" + resPath);
		try {
			FileUtils.writeStringToFile(new File(resPath), sb.toString(),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 创建model实体
	 * @param packName//包名
	 * @param tableName//表名
	 */
	private static void createModel() {
		String[] colnames =  map.get("selectstr").split(",");
		String[] remarks =  map.get("remarkstr").split(",");
		 StringBuffer sb = new StringBuffer();
         sb.append("package " + rootPath+"."+packName+".model;\r\n\r\n");
         if(type == 1){
 			sb.append("import com.ztl.base.BaseModel;\r\n");
 		 } 
         sb.append("public class " + upperName); 
         if(type == 1){
  			sb.append(" extends BaseModel");
  		 } 
         sb.append("{\r\n\r\n"); 
         //定义属性
         for (int i = 0; i < colnames.length; i++) {
             sb.append("\tprivate String " + colnames[i] + ";//"+remarks[i]+"\r\n");
         }
         sb.append("\r\n");
         //定义get/set方法
         for (int i = 0; i < colnames.length; i++) {
             sb.append("\tpublic void set" + upperStr(colnames[i]) + "(String " + colnames[i]+ "){\r\n");
             sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
             sb.append("\t}\r\n\r\n");

             sb.append("\tpublic String get"+ upperStr(colnames[i]) + "(){\r\n");
             sb.append("\t\treturn " + colnames[i] + ";\r\n");
             sb.append("\t}\r\n\r\n");
         }
         sb.append("}\r\n");
         String content = sb.toString();
    	
        String filepath = path  +rootPath.replaceAll("\\.", "/")+"/"+packName+"/model";
        File file = new File(filepath);
        if(!file.exists()){
        	file.mkdirs();
        }
        String resPath = filepath+"/"+upperName+ ".java";
        System.out.println("resPath=" + resPath);
        try {
			FileUtils.writeStringToFile(new File(resPath), content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成serviceImpl.java
	 * @param packName//包名
	 * @param tableName//表名
	 * @param entityName//类名
	 * @param path//系统项目路径
	 */
	public static void createServiceImplJava(){
		StringBuffer sb = new StringBuffer();
		sb.append("package "  +rootPath+"."+ packName+ ".service.impl;\r\n\r\n");
		if(type == 1){
			sb.append("import java.util.List;\r\n");
		}
		sb.append("import "+rootPath+"."+ packName+".model."+upperName+";\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;"+"\r\n");
		sb.append("import org.springframework.stereotype.Service;"+"\r\n");
		sb.append("import "+rootPath+"."+ packName+".dao."+upperName+"Dao;\r\n");
		sb.append("import "+rootPath+"."+ packName+".service."+upperName+"Service;\r\n\r\n");
		sb.append("@Service\r\n");
		sb.append("public class "+upperName+"ServiceImpl implements "+upperName+"Service {\r\n\r\n");
		sb.append("\t@Autowired\r\n");
		sb.append("\tprivate "+upperName+"Dao "+lowerName+"Dao;\r\n\r\n");
		sb.append("\t//新增\r\n");
		sb.append("\t@Override\r\n");
		sb.append("\tpublic int insert("+upperName+" "+lowerName+"){\r\n");
		sb.append("\t\treturn "+lowerName+"Dao.insert("+lowerName+");\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("\t//修改\r\n");
		sb.append("\t@Override\r\n");
		sb.append("\tpublic int update("+upperName+" "+lowerName+"){\r\n");
		sb.append("\t\treturn "+lowerName+"Dao.update("+lowerName+");\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("\t//删除\r\n");
		sb.append("\t@Override\r\n");
		sb.append("\tpublic int delete(String id){\r\n");
		sb.append("\t\treturn "+lowerName+"Dao.delete(id);\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("\t//根据Id查询单条记录\r\n");
		sb.append("\t@Override\r\n");
		sb.append("\tpublic "+upperName+" getById(String id){\r\n");
		sb.append("\t\treturn "+lowerName+"Dao.getById(id);\r\n");
		sb.append("\t}\r\n\r\n");
		if(type == 1){
			sb.append("\t//分页查询列表\r\n");
			sb.append("\t@Override\r\n");
			sb.append("\tpublic List<"+upperName+"> getAllList("+upperName+" "+lowerName+"){\r\n");
			sb.append("\t\treturn "+lowerName+"Dao.getAllList("+lowerName+");\r\n");
			sb.append("\t}\r\n\r\n");
			sb.append("\t//分页查询数量\r\n");
			sb.append("\t@Override\r\n");
			sb.append("\tpublic int getCount("+upperName+" "+lowerName+"){\r\n");
			sb.append("\t\treturn "+lowerName+"Dao.getCount("+lowerName+");\r\n");
			sb.append("\t}\r\n\r\n");
		}
		sb.append("}");
		String packPath = path+ rootPath.replaceAll("\\.", "/")+"/"+packName+"/service/impl";
	    File file = new File(packPath);
	    if(!file.exists()){
	    	file.mkdirs();
	    }
	    String resPath = packPath+"/"+upperName+ "ServiceImpl.java";
		System.out.println("resPath=" + resPath);
		try {
			FileUtils.writeStringToFile(new File(resPath), sb.toString(),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成service.java
	 * @param packName//包名
	 * @param tableName//表名
	 * @param entityName//类名
	 * @param path//系统项目路径
	 */
	public static void createServiceJava(){
		StringBuffer sb = new StringBuffer();
		sb.append("package " +rootPath+"."+packName+".service;\r\n\r\n");
		if(type == 1){
			sb.append("import java.util.List;\r\n");
		}
		sb.append("import "+rootPath+"."+ packName+".model."+upperName+";\r\n\r\n");
		sb.append("public interface "+upperName+"Service {\r\n\r\n");
		sb.append("\t//新增\r\n");
		sb.append("\tpublic int insert("+upperName+" "+lowerName+");\r\n\r\n");
		sb.append("\t//修改\r\n");
		sb.append("\tpublic int update("+upperName+" "+lowerName+");\r\n\r\n");
		sb.append("\t//删除\r\n");
		sb.append("\tpublic int delete(String id);\r\n\r\n");
		sb.append("\t//根据Id查询单条记录\r\n");
		sb.append("\tpublic "+upperName+" getById(String id);\r\n\r\n");
		if(type == 1){
			sb.append("\t//分页查询列表\r\n");
			sb.append("\tpublic List<"+upperName+"> getAllList("+upperName+" "+lowerName+");\r\n\r\n");
			sb.append("\t//分页查询数量\r\n");
			sb.append("\tpublic int getCount("+upperName+" "+lowerName+");\r\n\r\n");
		}
		sb.append("}");
		String packPath = path+ rootPath.replaceAll("\\.", "/")+"/"+packName+"/service/";
	    File file = new File(packPath);
	    if(!file.exists()){
	    	file.mkdirs();
	    }
	    String resPath = packPath+"/"+upperName+ "Service.java";
        System.out.println("resPath=" + resPath);
        try {
			FileUtils.writeStringToFile(new File(resPath), sb.toString(),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成dao.java
	 * @param packName//包名
	 * @param tableName//表名
	 * @param entityName//类名
	 * @param path//系统项目路径
	 */
	public static void createDaoJava(){
		StringBuffer sb = new StringBuffer();
		sb.append("package " +rootPath+"."+packName+".dao;\r\n\r\n");
		if(type == 1){
			sb.append("import java.util.List;\r\n");
		}
		sb.append("import "+rootPath+"."+ packName+".model."+upperName+";\r\n\r\n");
		sb.append("public interface "+upperName+"Dao {\r\n\r\n");
		sb.append("\t//新增\r\n");
		sb.append("\tpublic int insert("+upperName+" "+lowerName+");\r\n\r\n");
		sb.append("\t//修改\r\n");
		sb.append("\tpublic int update("+upperName+" "+lowerName+");\r\n\r\n");
		sb.append("\t//删除\r\n");
		sb.append("\tpublic int delete(String id);\r\n\r\n");
		sb.append("\t//根据Id查询单条记录\r\n");
		sb.append("\tpublic "+upperName+" getById(String id);\r\n\r\n");
		if(type == 1){
			sb.append("\t//分页查询列表\r\n");
			sb.append("\tpublic List<"+upperName+"> getAllList("+upperName+" "+lowerName+");\r\n\r\n");
			sb.append("\t//分页查询数量\r\n");
			sb.append("\tpublic int getCount("+upperName+" "+lowerName+");\r\n\r\n");
		}
		sb.append("}");
		String packPath = path+ rootPath.replaceAll("\\.", "/")+"/"+packName+"/dao/";
	       File file = new File(packPath);
	       if(!file.exists()){
	    	   file.mkdirs();
	       }
	    String resPath = packPath+"/"+upperName+ "Dao.java";
        System.out.println("resPath=" + resPath);
		try {
			FileUtils.writeStringToFile(new File(resPath), sb.toString(),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成dao.xml
	 * @param fields//sql
	 * @param packName//包名
	 * @param tableName//表名
	 * @param entityName//类名
	 * @param path//系统项目路径
	 */
	public static void createDaoXml(){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\r\n\r\n");
		String daostr = rootPath+ "."+packName+".dao."+upperName+"Dao";
		String entstr = rootPath+ "."+packName+".model."+upperName;
		sb.append("<mapper namespace=\""+daostr+"\">\r\n");
		sb.append("\t<resultMap id=\""+lowerName+"\" type=\""+entstr+"\"></resultMap>\r\n\r\n");
		sb.append("\t<!-- 新增 -->\r\n");
		sb.append("\t<insert id=\"insert\" parameterType=\""+entstr+"\">\r\n");
		sb.append("\t\tinsert into "+tableName+"("+map.get("selectstr")+")\r\n");
		sb.append("\t\tvalues("+map.get("insertstr")+")\r\n\t</insert>"+"\r\n\r\n");
		sb.append("\t<!-- 修改 -->\r\n");
		sb.append("\t<update id=\"update\" parameterType=\""+entstr+"\">\r\n");
		sb.append("\t\tupdate "+tableName+map.get("updatestr"));
		sb.append("\r\n\t</update>"+"\r\n\r\n");
		sb.append("\t<!-- 删除 -->\r\n");
		sb.append("\t<delete id=\"delete\" parameterType=\"String\">\r\n");
		sb.append("\t\tdelete from "+tableName+" where id=#{id}\r\n");
		sb.append("\t</delete>"+"\r\n\r\n");
		sb.append("\t<!-- 根据id查询单条-->\r\n");
		sb.append("\t<select id=\"getById\" resultMap=\""+lowerName+"\" parameterType=\"String\">\r\n");
		sb.append("\t\tselect "+map.get("selectstr")+" from "+tableName+"\r\n");
		sb.append("\t\twhere id = #{id}\r\n\t</select>"+"\r\n\r\n");
		if(type == 1){
			sb.append("\t<!-- 分页查询列表-->\r\n");
			sb.append("\t<select id=\"getAllList\" resultMap=\""+lowerName+"\" parameterType=\""+entstr+"\">\r\n");
			sb.append("\t\tselect "+map.get("selectstr")+" from "+tableName+"\r\n");
			sb.append("\t\tlimit #{startPos},#{pageSize}\r\n\t</select>"+"\r\n\r\n");
			sb.append("\t<!-- 分页查询数量-->\r\n");
			sb.append("\t<select id=\"getCount\" resultType=\"int\" parameterType=\""+entstr+"\">\r\n");
			sb.append("\t\tselect count(1) from "+tableName+"\r\n");
			sb.append("\t</select>"+"\r\n\r\n");
		}
		sb.append("</mapper>");
		String packPath = path+ rootPath.replaceAll("\\.", "/")+"/"+packName+"/dao/";
        File file = new File(packPath);
        if(!file.exists()){
        	file.mkdirs();
        }
		String resPath = packPath+"/"+upperName + "Dao.xml";
		System.out.println("resPath=" + resPath);
		try {
			FileUtils.writeStringToFile(new File(resPath), sb.toString(),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取字段信息
	 * @param tableName表名
	 * @return
	 */
	public static boolean getFields(String tableName) {
		String selectstr="";
		String insertstr="";
		String updatestr="\r\n\t\t<set>";
		String remarkstr="";
		try {
            DatabaseMetaData dbmd=DbConUtil.getcon().getMetaData();
            ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
            rs.last();
            int count = rs.getRow();
            if(count ==0){
            	System.out.println("表不存在或表没有可用字段");
            	return false;
            }
            rs.beforeFirst();
            String tiaojian="";
            for(int i=0;i<count;i++){
            	rs.next();
            	//rs.getString("TYPE_NAME");字段类型
            	String select = rs.getString("COLUMN_NAME");//字段名称
    			String insert = "#{"+select+"}";
    			String update ="";
    			if(i==0){
    				tiaojian=select + "=#{"+select+"}";
    			}else{
    				update ="\t\t\t<if test=\""+select+" != null and "+select+" != '' \">\r\n\t\t\t\t"+ select + "=#{"+select+"},\r\n"+"\t\t\t</if>";
    			}
    			String remark = rs.getString("REMARKS");//字段注释
    			selectstr +=select+",";
    			insertstr +=insert+",";
    			updatestr +=update+"\r\n";
    			remarkstr +=remark+",";
            }
            selectstr = selectstr.substring(0,selectstr.length()-1);
            insertstr = insertstr.substring(0,insertstr.length()-1);
            updatestr = updatestr+"\t\t</set>\r\n\t\twhere "+tiaojian;
            remarkstr = remarkstr.substring(0,remarkstr.length()-1);
            //System.out.println(selectstr);
    		//System.out.println(insertstr);
    		//System.out.println(updatestr);
    		//System.out.println(remarkstr);
    		map.put("selectstr", selectstr);
    		map.put("insertstr", insertstr);
    		map.put("updatestr", updatestr);
    		map.put("remarkstr", remarkstr);
    		return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}
	 /**
	    * 把输入字符串的首字母改成大写
	    * 
	    * @param str
	    * @return
	    */
	    private static String upperStr(String str) {
	        char[] ch = str.toCharArray();
	        if (ch[0] >= 'a' && ch[0] <= 'z') {
	            ch[0] = (char) (ch[0]-32);
	        }
	        return new String(ch);
	    }
	    /**
	     * 把输入字符串的首字母改成 小写
	     * 
	     * @param str
	     * @return
	     */
	    private static String lowerStr(String str) {
	    	char[] ch = str.toCharArray();
	    	if (ch[0] >= 'A' && ch[0] <= 'Z') {
	    		ch[0] = (char) (ch[0]+32);
	    	}
	    	return new String(ch);
	    }
	    
}
