package com.ztl.createcode;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ztl.base.BaseController;
import com.ztl.common.DbConUtil;
import com.ztl.common.DelAllFile;
import com.ztl.common.FileDownload;
import com.ztl.common.FileZip;
import com.ztl.common.Freemarker;
import com.ztl.common.FreemarkerIN;
import com.ztl.common.PageData;
import com.ztl.common.PathUtil;
import com.ztl.common.StringTools;


/** 
 * 类名称：FreemarkerController
 * 创建人：FH 
 * 创建时间：2015年1月12日
 * @version
 */
@Controller
@RequestMapping(value="/createCode")
public class CreateCodeController extends BaseController {
	/**
	 * 进入生成代码页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/createCode")
	public ModelAndView edit_pwd(HttpServletRequest request) {
		return new ModelAndView("admin/createCode");
	}
	/**
	 * 页面生成代码
	 */
	@RequestMapping(value="/create{type}")
	public void proCode(HttpServletResponse response,@PathVariable("type") String type) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		/* ============================================================================================= */
		String packageName = pd.getString("packageName");  			//包名				========1
		String objectName = pd.getString("objectName");	   			//类名				========2
		String tabletop = pd.getString("tabletop");	   				//表前缀				========3
		String remark = pd.getString("remark");	   				//业务名称				========3
		String tablename = tabletop+objectName.toLowerCase();//表名
		int zindext = StringTools.str2Int( pd.getString("zindex"));	//表字段个数
		List<String[]> fieldList = new ArrayList<String[]>();   	//属性集合			========4
		fieldList.add(new String[]{"id","String","注解ID","否"});
		for(int i=0; i<zindext; i++){
			fieldList.add(pd.getString("field"+i).split(",fh,"));	//属性放到集合里面
		}
		fieldList.add(new String[]{"create_time","Date","创建时间","否"});
		fieldList.add(new String[]{"last_update_time","Date","最后修改时间","否"});
		Map<String,Object> data = new HashMap<String,Object>();		//创建数据模型
		data.put("fieldList", fieldList);
		data.put("remark", remark);						//业务名称
		data.put("packageName", packageName);						//包名
		data.put("objectName", objectName);							//类名
		data.put("objectNameLower", objectName.toLowerCase());		//类名(全小写)
		data.put("objectNameUpper", objectName.toUpperCase());		//类名(全大写)
		data.put("tablename",tablename);								//表前缀	
		data.put("nowDate", new Date());							//当前日期
		/* ============================================================================================= */
		createMain(response, type, packageName, objectName, tablename, data);
		System.out.println("生成成功！=============");
	}
	/**
	 * mian方法运行生成代码
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//用户自定义
		String packageName ="testftl";  			//包名				
		String tablename = "testftl";	   		//表名		
		String objectName = "Testftl";	   			//类名	
		String remark = "菜单";	   			//类名	
		//开始生成
		Map<String,Object> data = new HashMap<String,Object>();		//创建数据模型
		data.put("fieldList", getFields(tablename));
		data.put("remark", remark);						//业务名称
		data.put("packageName", packageName);						//包名
		data.put("objectName", objectName);							//类名
		data.put("objectNameLower", objectName.toLowerCase());		//类名(全小写)
		data.put("objectNameUpper", objectName.toUpperCase());		//类名(全大写)
		data.put("tablename",tablename);								//表前缀	
		data.put("nowDate", new Date());
		createMain(null, "", packageName, objectName, tablename, data);
	}
	/**
	 * 获取字段信息
	 * @param tableName表名
	 * @return
	 */
	public static List<String[]> getFields(String tableName) {
		List<String[]> fieldList = new ArrayList<String[]>();  
		try {
            DatabaseMetaData dbmd=DbConUtil.getcon().getMetaData();
            ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
            fieldList.add(new String[]{"id","String","主键ID","否"});
            rs.next();
            while(rs.next()) { 
            	String type = rs.getString("TYPE_NAME");//字段类型
            	String name = rs.getString("COLUMN_NAME");//字段名称
            	String remark = rs.getString("REMARKS");//字段注释
            	String[] field= new String[4];
            	field[0]=name;
            	field[2]=remark;
        		if(type.equals("DATETIME")){
        			field[1]="Date";
            		field[3]="否";
        		} else	if(type.equals("INT")){
            			field[1]="Integer";
                		field[3]="是";
        		} else{
        			field[1]="String";
            		field[3]="是";
        		}
            	fieldList.add(field);
            }
    		return fieldList;
        } catch (Exception e) {
            e.printStackTrace();
            return fieldList;
        }
	}
	/**
	 * 生成代码主要方法
	 * @param response
	 * @param type：out生成代码打包导出，否则，生成到代码内部，刷新项目可见
	 * @param packageName 包名
	 * @param objectName  类名
	 * @param tablename 表名
	 * @param data 表字段数据
	 * @throws Exception
	 */
	private static void createMain(HttpServletResponse response, String type,String packageName, String objectName, String tablename,Map<String, Object> data) throws Exception {
		if("out".equals(type)){ //生成外部代码
			DelAllFile.delFolder(PathUtil.getClasspath()+"admin/ftl"); //生成代码前,先清空之前生成的代码
			String filePath = "admin/ftl/code/";						//存放路径
			/*生成controller*/
			Freemarker.printFile("controllerTemplate.ftl", data, packageName+"/controller/"+objectName+"Controller.java", filePath);
			/*生成service*/
			Freemarker.printFile("serviceImplTemplate.ftl", data, packageName+"/service/impl/"+objectName+"ServiceImpl.java", filePath);
			Freemarker.printFile("serviceTemplate.ftl", data, packageName+"/service/"+objectName+"Service.java", filePath);
			/*生成DAO*/
			Freemarker.printFile("daojavaTemplate.ftl", data, packageName+"/dao/"+objectName+"Dao.java", filePath);
			Freemarker.printFile("daoxmlTemplate.ftl", data, packageName+"/dao/"+objectName+"Dao.xml", filePath);
			/*生成SQL脚本*/
			Freemarker.printFile("mysql_SQL_Template.ftl", data, packageName+"/mysql数据库脚本/"+tablename+".sql", filePath);
			Freemarker.printFile("oracle_SQL_Template.ftl", data,packageName+ "/oracle数据库脚本/"+tablename+".sql", filePath);
			/*生成jsp页面*/
			Freemarker.printFile("jsp_list_Template.ftl", data, packageName+"/jsp/"+objectName.toLowerCase()+"_list.jsp", filePath);
			Freemarker.printFile("jsp_edit_Template.ftl", data, packageName+"/jsp/"+objectName.toLowerCase()+"_edit.jsp", filePath);
			/*生成说明文档*/
			//Freemarker.printFile("docTemplate.ftl", data, packageName+"/说明.doc", filePath);
			//Freemarker.print("mysql_SQL_Template.ftl", data);  //控制台打印
			/*生成的全部代码压缩成zip文件*/
			FileZip.zip(PathUtil.getClasspath()+"admin/ftl/code", PathUtil.getClasspath()+"admin/ftl/code.zip");
			System.out.println(PathUtil.getClasspath()+"admin/ftl/code");
			/*下载代码*/
			FileDownload.fileDownload(response, PathUtil.getClasspath()+"admin/ftl/code.zip", "code.zip");
		} else{//代码直接生成到项目里
			String rootPath = "com.ztl";//目录
		    String javaPath = "E:/myworkspace/ztl_portal/src/"+ rootPath.replaceAll("\\.", "/")+"/"+packageName;//Java包名
			String jsppath =  "E:/myworkspace/ztl_portal/webapp/WEB-INF"+"/"+packageName;//jsp包名
			/*生成controller*/
		    FreemarkerIN.printFile("controllerTemplate.ftl", data, javaPath+"/controller"+"/"+objectName+ "Controller.java");
			/*生成service*/
		    FreemarkerIN.printFile("serviceImplTemplate.ftl", data, javaPath+"/service"+"/impl"+"/"+objectName+"ServiceImpl.java");
		    FreemarkerIN.printFile("serviceTemplate.ftl", data, javaPath+"/service"+"/"+objectName+"Service.java");
			/*生成DAO*/
		    FreemarkerIN.printFile("daojavaTemplate.ftl", data,javaPath+"/dao"+"/"+objectName+"Dao.java");
		    FreemarkerIN.printFile("daoxmlTemplate.ftl", data,javaPath+"/dao"+"/"+objectName+"Dao.xml");
			/*生成SQL脚本*/
		    FreemarkerIN.print("mysql_SQL_Template.ftl", data);  //控制台打印
		    FreemarkerIN.print("oracle_SQL_Template.ftl", data);  //控制台打印
			/*生成jsp页面*/
		    FreemarkerIN.printFile("jsp_list_Template.ftl", data,jsppath+"/"+objectName.toLowerCase()+"_list.jsp");
		    FreemarkerIN.printFile("jsp_edit_Template.ftl", data,jsppath+"/"+objectName.toLowerCase()+"_edit.jsp");
		}
	}
	
}
