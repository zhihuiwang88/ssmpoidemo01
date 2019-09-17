<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>table update</title>
<link rel="stylesheet" href="../static/layui/css/layui.css" >
</head>
<body>
<h2>修改页面</h2>
<script type="text/javascript" src="../static/layui/layui.js" charset="utf-8"></script>
<!-- 修改数据页面 -->
<form class="layui-form" >
<div class="layui-form-item ">
    <label class="layui-form-label ">ID</label>
    <div class="layui-input-block ">
      <input type="text" name="userid"  class="layui-input" id="schid" disabled="disabled">
    </div>
  </div>
  <div class="layui-form-item ">
    <label class="layui-form-label ">姓名 <span style="color:red">*</span></label>
    <div class="layui-input-block ">
      <input type="text" name="name" id="schname" lay-verify="username" autocomplete="off" placeholder="请输入2-6个汉字" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">年龄 <font color='red'>*</font></label>
    <div class="layui-input-block">
      <input type="text" name="age"  id="schage" lay-verify="userage" autocomplete="off" placeholder="请输入年龄：1-150岁" class="layui-input">
    </div>
  </div>
  
    <div class="layui-form-item">
    <label class="layui-form-label">邮箱 <font color='red'>*</font></label>
    <div class="layui-input-block">
      <input type="text" name="email" id="schemail" lay-verify="useremail" autocomplete="off" placeholder="请输入邮箱格式：zhongguo@china.com" class="layui-input">
    </div>
  </div>
  
 <div class="layui-form-item">
    <label class="layui-form-label">单选框</label>
    <div class="layui-input-block">
      <input type="radio" name="sex" value="男" title="男" id="schsex" >
      <input type="radio" name="sex" value="女" title="女" id="schsex" >
    </div>
  </div>
  
   <div class="layui-upload">
      <button type="button" class="layui-btn" id="uploadimage" style="margin-left:60px;">
        <i class="layui-icon">&#xe67c;</i>上传图片 
       </button>
       <font color='red'>*</font>
       	<div class="layui-inline layui-word-aux">图片格式：JPG PNG JPEG  大小：1M</div>
       	<div class="layui-upload-list" >
		    <input type="hidden" id="imgupad" >
		    
		 </div>
		  <div class="layui-upload-list">
		   <img class="layui-upload-img" id="imgupload" style="width: 100px;height: 100px;margin-left:65px;">
		   <p id="demoText"></p>
		 </div>
   </div> 
  
  <div class="layui-form-item">
    <div class="layui-input-block" style="margin-left:350px;">
    <button type="button" class="layui-btn layui-btn-primary" id="updcolse">关闭</button> 
      <button class="layui-btn" lay-submit="" lay-filter="btnsumit"  id="btnupd">立即更新</button>
    </div>
    </div>
    
</form>


<!-- form表单 -->
<script>
layui.use(['form','layer','upload','jquery'],function(){
	var  schform = layui.form, schupload = layui.upload, $ = layui.jquery ,layer = layui.layer;
	
	/* 表单验证 
	* https://c.runoob.com/front-end/854
	*console.log(useremail.test(value));
	*/
	schform.verify({
		username: function(value){
		var	usename = /^[\u4e00-\u9fa5]{2,6}$/;
		if(!usename.test(value)) {
			return "姓名格式：2-6个汉字";
		   }
		},
		userage: function(value){
			var useage = /^(?:[1-9][0-9]?|1[01234][0-9]|150)$/;
			if(!useage.test(value)){
				return "年龄范围：1-150岁";
			}
		},
		useremail: function(value){
			var useremail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!useremail.test(value)){
				return "邮箱格式：beijing2020@china2020.net";
			}
		}
	});
	
	/* 关闭按钮 */
	$("#updcolse").on('click',function(){
		//先得到当前iframe层的索引
		var index = parent.layer.getFrameIndex(window.name); 
		parent.layer.close(index); 
	})

	
	/* 普通图片上传 */
  var uploadInst = schupload.render({
    elem: '#uploadimage',
    url: './upload',
    method: 'post',
    acceptMime: 'image/jpg,image/png,image/jpeg',
	accept: 'images',  // 定义上传的类型为图片
    size: 1024,  // 上传图片的大小KB
    exts: 'jpg|png|jpeg',  // 允许上传的文件后缀
    auto: false, 
    bindAction: '#btnupd',
    choose: function(obj){
      
      //预读本地文件示例，不支持ie8
      obj.preview(function(index, file, result){
    	  
    	  // input隐藏域添加img
    	 $('#imgupad').attr('value',file.name);
    	// 图片链接（base64）
        $('#imgupload').attr('src', result); 
      });
    }
    ,done: function(res){
    	var status = res.code;
    	
    	switch (status) {
         case 110:
			return layer.msg(res.msg,{icon: 5,time: 1200});
			break;
		default:
			break;
		}    	
    
    }
    ,error: function(){
    	layer.msg('上传失败',{icon: 5,time: 1100});
      //演示失败状态，并实现重传
      var demoText = $('#demoText');
      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload" style="margin-left:0px;">重试</a>');
      demoText.find('.demo-reload').on('click', function(){
        uploadInst.upload();
      });
    }
    
  });

	
		/* 提交按钮事件
		* https://blog.csdn.net/yin13037173186/article/details/83302628
		*/
	schform.on('submit(btnsumit)',function(data){
		
		var field = data.field;
		console.log(field)
		var file = data.field.file;
		    file = $('#imgupad').attr('value');
		     
		   
		 $.ajax({
			  url:'./updateById',
			  type:'post',
			  data:{"id":field.userid,"username":field.name,"userage":field.age,"mailbox":field.email,"usergender":field.sex,"headportrait":file},
			  success:function(result){	
				  if(result.data == null) {
					  layer.msg(result.msg,{icon: 5,time: 1200});
				  }	
				    // 根据status 状态提醒信息
				 	var	status = result.code;
				 	
				 	switch (status) {
					case 113:
						return layer.msg(result.msg,{icon: 5,time: 1200});
						break;
					case 114:
						return layer.msg(result.msg,{icon: 5,time: 1200});
						break;	
					case 200:
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.msg(result.msg, {icon: 6,time:900});
						parent.layer.close(index);
						//刷新整个表格
						//window.parent.location.reload();	
						break;		
					default:
						break;
					}	
			  }
		  })
		return false; 
	})
	
	
	
	/* 最后一个 */
});

</script>
</body>
</html>