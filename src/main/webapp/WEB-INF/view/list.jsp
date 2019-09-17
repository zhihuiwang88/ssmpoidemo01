<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>table list</title>
<link rel="stylesheet" href="../static/layui/css/layui.css">
</head>
<body>
<h2>表格首页</h2>
<script type="text/javascript" src="../static/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../static/layui/jquery-3.3.1.js"></script>
<!-- 数据操作按钮1 -->
	<div class="layui-btn-group demoTable">
		<button class="layui-btn" type="button" id="btnadd">
			<i class="layui-icon">&#xe654;</i>添加
		</button>
		<button class="layui-btn" type="button" id="btndel">
			<i class="layui-icon layui-icon-delete"></i>批量删除
		</button>
		<button class="layui-btn" type="button" id="btnexport">
			<i class="layui-icon layui-icon-download-circle"></i>导出数据
		</button>
		<button class="layui-btn" type="button" id="btnalldata">
			<i class="layui-icon layui-icon-download-circle"></i>全部导出
		</button>
	</div>
	
	
	
	<table id="stable" lay-filter="schtest"></table>
	
	<script type="text/html" id="bartable">
       <a class="layui-btn layui-btn-xs" lay-event="edit">
           <i class="layui-icon layui-icon-edit"></i>编辑
       </a>
   </script>
	
<script>
	
/* 数据库时间显示到页面的函数 
 *  https://baike.baidu.com/item/date/9879981?fr=aladdin
 */
function createTime(v){
	var date = new Date(v);
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    m = m<10?'0'+m:m;
    var d = date.getDate();
    d = d<10?("0"+d):d;
    var h = date.getHours();
    h = h<10?("0"+h):h;
    var M = date.getMinutes();
    M = M<10?("0"+M):M;
    var s = date.getSeconds();
    s = s<10?("0"+s):s;
    var str = y + "-" + m + "-" + d + " " + h + ":" + M + ":" + s;
    return str;
}




/* layui表格 */
layui.use(['table','jquery','upload','layer','laypage'],function(){
      var schtable = layui.table, $ = layui.jquery, 
          schupload = layui.upload, schlayer = layui.layer ,laypage = layui.laypage;
     
         //执行渲染
		var tableIns=schtable.render({
			elem:'#stable',
			url:'./selectAll',
			loading:false,
            page:true,
            limit:2,
            limits:[2,6,11],
            
			cols:[[
				{type:'checkbox'},
				{field:'id',title:'ID',align:'center',width:80},
				{field:'username',title:'姓名',align:'center',width:100},
				{field:'userage',title:'年龄',align:'center',width:80},
				{field:'mailbox',title:'邮箱',align:'left',width:200},
				{field:'usergender',title:'性别',align:'center',width:80},
				{field:'headportrait',title:'头像',align:'left',width:160},
{field:'creationtime',title:'创建时间',align:'left',width:170,templet:function (d){return createTime(d.creationtime);}},
{field:'modifytime',title:'修改时间',align:'left',width:170,templet:"<div>{{layui.util.toDateString(d.modifytime,'yyyy-MM-dd HH:mm:ss')}}</div>"},
{title:'操作',align:'center',width:80,toolbar: '#bartable'}
				]],
				
		});

        
         
		/*新增数据  */
         $("#btnadd").on('click',function(){	
        	 layer.open({
       		  type: 2,
       		  title: '新增页面',
       		  shadeClose: true,
       		  shade: 0.8,
     		  shadeClose:false,
       		  area: ['600px', '500px'],
       		  content: './addList'
       		});
         });
         
     	/*修改数据  
     	* 监听行工具条
     	*/
        schtable.on('tool(schtest)',function(obj){
        	var datas = obj.data;
        	//console.log(datas)
        	
        	
        	var  img = datas.headportrait;
        	var layEvent = obj.event;
        	if(layEvent === 'edit') {
        		layer.open({
             		  type: 2,
             		  title: '修改页面',
             		  shadeClose: true,
             		  shade: 0.8,
           		      shadeClose:false,
           		      
             		  area: ['600px', '550px'],
             		  content: './updateParam',
             		 success:function(layero,index){
             			
             			 // 获取body内容
             			 var tableBody = schlayer.getChildFrame('body',index);
             			 // 得到ifrmae的窗口对象
             			 var iframeWin = window[layero.find('iframe')[0]['name']];
             			 // 执行iframe方法  
             			tableBody.find('#schid').val(datas.id);
             			
             			tableBody.find('#schname').val(datas.username);
             			tableBody.find('#schage').val(datas.userage); 
             			tableBody.find('#schemail').val(datas.mailbox);
             			tableBody.find('#schsex').val(datas.usergender); 
             			tableBody.find('#imgupad').val(datas.headportrait); 
             			
             		 }
             		});
        	}
        }) 
     	
        
        
     	/*批量删除  */
       
        	$("#btndel").on('click',function(){
        		var checkStatus = schtable.checkStatus('stable'); 
        		var   len = checkStatus.data.length;
        		var   array =  checkStatus.data;

        		if(len == 0) {
        			return schlayer.msg("请选一行数据",{icon:6,time:1000});
        		} 

        			// 多个id之间用,号隔开
        		    var  ids = "";
        			for (var i = 0; i < array.length; i++) {
						ids += array[i].id + ",";					
        		      }
        			
        		schlayer.confirm('真的删除ID为'+ids+'的用户么',function(index){
        			
        	           $.ajax({
        	        	   url:'deleteByIds',
        	        	   type:'post',
        	        	   data:{'ids':ids},
        	        	   success:function(res){
        	        		   if(res.code == 120) {
        	        			   return schlayer.msg(res.msg,{icon:5,time:1000});
        	        		   }
        	        		   if(res.code == 200) {
        	        			   // 刷新表格
        	        			   window.parent.location.reload();
        	        			   return schlayer.msg(res.msg,{icon:6,time:800});
        	        		   }
        	        	   }
        	           })
        	           
       				schlayer.close(index);
        		})
        	})  
  	
        	
        	/** 
        	*
        	*导出Excel数据
        	* 获取表头
        	* 获取数据
        	*
        	*/
        	$("#btnexport").on('click',function(){
        		var checkStatus = schtable.checkStatus('stable'); 
        		var   len = checkStatus.data.length;      		
        		if(len == 0) {
        			return schlayer.msg("请选择要导出的数据",{icon:6,time:1000});
        		} 
        		 var   array =  checkStatus.data;
                 var obj = JSON.stringify(array);
                 
        		schlayer.open({
    				  content: '确定导出数据？',
    				  yes:function(){
    					  $.ajax({
    		        			url:'./exportExcel',
    		        			type:'post',
    		        			data:obj,
    		        			contentType:"application/json;charset=utf-8",
    		        			success:function(res){
    		        			  if(res.code == 200) {
    		        				  return schlayer.msg(res.msg,{icon:6,time:600});
    		        			  }else if(res.code == 100) {
    		        				  return schlayer.msg(res.msg,{icon:7,time:2000});
    		        			  }else {
    		        				  return schlayer.msg("导出数据失败",{icon:5,time:1500});
    		        			  }
    		        			}
    		        		})
    				  }
        		})
        		})
        	
        		
        		
        		/*
        		*导出全部数据
        		*
        		*/
        		$("#btnalldata").on('click',function(){
        			schlayer.open({
        				  content: '导出全部数据？',
        				  yes:function(){
        					  $.ajax({
        	            			url:'./exportExcelAll',
        	            			type:'post',
        	            			success:function(res){
        	            			  if(res.code == 200) {
        	            				  return schlayer.msg(res.msg,{icon:6,time:600});
        	            			  }else if(res.code == 101) {
        	            				  return schlayer.msg(res.msg,{icon:7,time:2000});
        	            			  }else {
        	            				  return schlayer.msg("导出数据失败",{icon:5,time:1500});
        	            			  }
        	            				
        	            			}
        	            		})
        				  }
        				})   
            		
            		})
 		
		   //最后一个
			});
			
			
			
</script>
</body>
</html>