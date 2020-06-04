<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	<link rel="stylesheet" href="${APP_PATH}/css/pagination.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				  <%@ include file="/WEB-INF/jsp/common/menu.jsp" %>
			</div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
	<form class="form-inline" role="form" style="float:left;">
	  <div class="form-group has-feedback">
	    <div class="input-group">
	      <div  class="input-group-addon">查询条件</div>
	      <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
	    </div>
	  </div>
	  <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
	</form>
	<button type="button" class="btn btn-danger" id="deleteBatchBtn" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
	<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/user/toAdd.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
	<br>
	 <hr style="clear:both;">
	          <div class="table-responsive">
	            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input id="allCheckbox" type="checkbox"></th>
                  <th>账号</th>
                  <th>名称</th>
                  <th>邮箱地址</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
              
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
					<!-- 	 <ul class="pagination"> </ul>  -->
					<div id="Pagination" class="pagination" >
					 </td>
				 </tr> 

			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>
    
    
    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	  <script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
	   <script type="text/javascript" src="${APP_PATH }/script/menu.js"></script> 
	   <script src="${APP_PATH}/jquery/pagination/jquery.pagination.js"></script>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			     queryPageUser(1); 
			     showMenu(); 
            });
            
       /*      $("tbody .btn-success").click(function(){
                window.location.href = "assignRole.html";
            });
            $("tbody .btn-primary").click(function(){
                window.location.href = "edit.html";
            }); */
            
         
        function pageChange(pageno){
            	queryPageUser(pageno)
            }  
            
            
        
        //数据查询
             var jsonObj = {
        			"pageno" : 1,
        			"pagesize" : 10 
        		};
            
              var loadingIndex = -1 ;
            function queryPageUser(pageno) {
            	jsonObj.pageno = pageno ;
				$.ajax({
						url : "${APP_PATH}/user/doindex.do",
						type : "POST",
						data : jsonObj,
				 		beforeSend : function () {
							loadingIndex = layer.load(2,{time:10*1000});
							return true;
						}, 
						success : function (result) {
							layer.close(loadingIndex);
							if(result.success){
								 var page = result.page;
								var data = page.datas;
								var content = '';
	            				
	            				$.each(data,function(i,n){
	            					content+='<tr>';
	                				content+='  <td>'+(i+1)+'</td>';
	                				content+='  <td><input class="delete"  type="checkbox" id="'+n.id+'" name = "'+n.loginacct+'"></td>';
	                				content+='  <td>'+n.loginacct+'</td>';
	                				content+='  <td>'+n.username+'</td>';
	                				content+='  <td>'+n.email+'</td>';
	                				content+='  <td>';
	                				content+='	  <button type="button" onclick="window.location.href=\'${APP_PATH}/user/assignRole.htm?id='+n.id+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
	                				content+='	  <button type="button" onclick="window.location.href=\'${APP_PATH }/user/toUpdate.htm?id='+n.id+'\'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
	                				content+='	  <button type="button" onclick="deleteUser('+n.id+',\''+n.loginacct+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
	                				content+='  </td>';
	                				content+='</tr>';
	            				});
	            				
	            				
	            				$("tbody").html(content);
	            	
	            				var contentBar = '';
	            				
	            				if(page.pageno==1 ){
	            					contentBar+='<li class="disabled"><a href="#">上一页</a></li>';
	            				}else{
	            					contentBar+='<li><a href="#" onclick="pageChange('+(page.pageno-1)+')">上一页</a></li>';
	            				}
	            				
	            				for(var i = 1 ; i<= page.totalno ; i++ ){            					
	            					contentBar+='<li';
										if(page.pageno==i){
											contentBar+=' class="active"';
										}
									contentBar+='><a href="#" onclick="pageChange('+i+')">'+i+'</a></li>';
	            				}
								
								if(page.pageno==page.totalno ){
	            					contentBar+='<li class="disabled"><a href="#">下一页</a></li>';
	            				}else{
	            					contentBar+='<li><a href="#" onclick="pageChange('+(page.pageno+1)+')">下一页</a></li>';
	            				}
	            				
	            				$(".pagination").html(contentBar); 
	            				
	            			
	            				
	            				
	            				// 创建分页
	           		  		 /* 	var num_entries = page.totalsize ;
	            				$("#Pagination").pagination(num_entries, {
	            					num_edge_entries: 1, //边缘页数
	            					num_display_entries: 2, //主体页数
	            					callback: queryPageUser, //查询当前页的数据.
	            					items_per_page:page.pagesize, //每页显示1项
	            					current_page:(page.pageno-1), //当前页,索引从0开始
	            					prev_text:"上一页",
	            					next_text:"下一页"
	            				});  */
							}else{
								layer.msg(result.message,{time:1000,icon:5,shift:6});
							}
						},
						error : function () {
							layer.msg("加载数据失败",{time:1000,icon:5,shift:6});
						}	
				}); 
			}  
            //条件查询
            $("#queryBtn").click(function () {
				var queryText = $("#queryText").val();
				jsonObj.queryText = queryText ;
				queryPageUser(1);
			});
            
            
            //删除
            function deleteUser(id,loginacct){
            	
            	layer.confirm("确定删除["+loginacct+"]用户吗？",  {icon: 3, title:'提示'}, function(cindex){
    			    layer.close(cindex);
    			    
    			    $.ajax({
                		type : "POST",
                		data : {
                			"id" : id
                		},
                		url : "${APP_PATH}/user/dodeleteUser.do",
                		beforeSend : function(){
                			return true;
                		},
                		success : function(result){
                			if(result.success){
                				window.location.href="${APP_PATH}/user/index.htm";
                			}else{
                				layer.msg("删除失败",result.message,{time:1000, icon:5,shift:6});
                			}
                		},
                		error : function(){
                			layer.msg("服务器异常",{time:1000, icon:5,shift:6});
                		}
                	});
    			    
    			}, function(cindex){
    			    layer.close(cindex);
    			});
            }
            
            
            
            //批量删除
            $("#allCheckbox").click(function(){
            	 checkedStatus =  this.checked;
            	 
            	 
            	//$("tbody tr td input[type='checkbox']").prop("checked",checkedStatus);
            	// $("tbody tr td input[type='checkbox']").attr("checked",checkedStatus);
            	   tbodyCheckbox = $("tbody tr td input[type='checkbox']");
            	   $.each(tbodyCheckbox,function(i,n){
            		 n.checked = checkedStatus;
            	 });	
            	   
            	   
            });
            
         
            
            
            $("#deleteBatchBtn").click(function(){
            	var selectCheckbox =  $("tbody tr td input:checked");
            	
            	if(selectCheckbox.length==0){
            		layer.msg("请选择你要删除的用户",{time:1000, icon:5,shift:6});
    				return false;
    			}
            	
            	 /*var idstr = "";
            	
             	$.each(selectCheckbox,function(i,n){
             		
             		if(i!=0){
             			idstr += "&";
             		}	
            		idstr += "id="+ n.id;
            	}); */

            	
            	var jsonObj = {};
            	
            	$.each(selectCheckbox,function(i,n){
            		jsonObj["datas["+i+"].id"] = n.id;
            		jsonObj["datas["+i+"].loginacct"] = n.name;
            	});
            	
            	
            	layer.confirm("确定删除这些用户吗？",  {icon: 3, title:'提示'}, function(cindex){
    			    layer.close(cindex);
    			    
    			    $.ajax({
                		type : "POST",
                		//data : idstr,
                		data : jsonObj,
                		url : "${APP_PATH}/user/doDeleteBatch.do",
                		beforeSend : function(){
                			
                			return true;
                		},
                		success : function(result){
                			if(result.success){
                				window.location.href="${APP_PATH}/user/index.htm";
                			}else{
                				layer.msg("删除失败",result.message,{time:1000, icon:5,shift:6});
                			}
                		},
                		error : function(){
                			layer.msg("服务器异常",{time:1000, icon:5,shift:6});
                		}
                	});
    			    
    			}, function(cindex){
    			    layer.close(cindex);
    			});
            	 
            });
        </script>
  </body>
</html>
