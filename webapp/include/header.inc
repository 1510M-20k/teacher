<%
	String basePath = request.getScheme()+"://"+request.getServerName();
	if(!"80".equals(request.getServerPort()+"")){
		basePath =  basePath +":"+request.getServerPort();
	}
	basePath += request.getContextPath();
	if(basePath.lastIndexOf("/")!=basePath.length()-1){
		basePath = basePath + "/";
	}
	request.setAttribute("ctx",basePath);
	String imgPath = basePath+"uploadFile/";
	request.setAttribute("imgPath",imgPath);				
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
