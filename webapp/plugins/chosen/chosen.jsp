<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
  <head>
    <title>My JSP 'chosen.jsp' starting page</title>
   <link rel="stylesheet" href="${ctx}chosen/chosen.css" />
   <script type="text/javascript" src="${ctx}js/jquery-1.9.1.min.js"></script><!--提示框-->
   <script type="text/javascript" src="${ctx}chosen/chosen.jquery.min.js"></script><!--提示框-->
  </head>
  
  <body>
 <select class="chzn-select" name="field2" id="field2" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
							<option value=""></option>
							<option value="">全部</option>
							<option value="">1</option>
							<option value="">2</option>
					  	</select>
 
  <script type="text/javascript">
  $(function() {
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
	});
  </script>
   </body>
</html>
