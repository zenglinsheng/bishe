/*
 * @Author: https://github.com/WangEn
 * @Author: https://gitee.com/lovetime/
 * @Date:   2018-03-27
 * @lastModify 2018-3-28
 * +----------------------------------------------------------------------
 * | WeAdmin 表格table中多个删除等操作公用js
 * | 有改用时直接复制到对应页面也不影响使用
 * +----------------------------------------------------------------------
 */
layui.extend({
	admin: '{/}../js/admin'
});
layui.use(['laydate', 'jquery', 'admin'], function() {
	var laydate = layui.laydate,
		$ = layui.jquery,
		admin = layui.admin;
	//执行一个laydate实例
	laydate.render({
		elem: '#start' //指定元素
	});
	//执行一个laydate实例
	laydate.render({
		elem: '#end' //指定元素
	});
	/*用户-停用*/
	window.member_stop = function (obj, id) {
		layer.confirm('确认要停用吗？', function(index) {
			if($(obj).attr('title') == '启用') {

				//发异步把用户状态进行更改
				$(obj).attr('title', '停用')
				$(obj).find('i').html('&#xe62f;');

				$(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
				layer.msg('已停用!', {
					icon: 5,
					time: 1000
				});

			} else {
				$(obj).attr('title', '启用')
				$(obj).find('i').html('&#xe601;');

				$(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
				layer.msg('已启用!', {
					icon: 5,
					time: 1000
				});
			}
		});
	}

});


$(function () {
    layui.use(['form','layer','table','jquery','upload'],function () {
        let form=layui.form,
            layer=layui.layer,
            table=layui.table,
            $ = layui.jquery,
			upload = layui.upload;

		table.render({
            elem:'#userList'
            ,id:'userList'
            ,url: 'http://localhost:8088/student/selectStudents' //数据接口
			,where: {'deleteStatus':0}
            ,page: true //开启分页
            ,text:"数据加载失败"
            ,cols: [[ //表头
                {checkbox:true,width:'50',fixed: 'left'}//单选框
                ,{field: 'number', title: '学号', width:200, sort: true, fixed: 'left', align:"center"}
                ,{field: 'username', title: '姓名', width:200, align:"center"}
                ,{field: 'className', title: '班级', width:200, align:"center"}
                ,{field: 'gender', title: '性别', width:200, align:"center",templet:function(d){
						return  d.gender == 0 ? "男":"<span class='layui-red'>女</span>";
					}}
				,{field: 'age', title: '年龄', width:200, align:"center"}
				,{field: 'telephone', title: '电话', width:200, align:"center"}
				,{field: 'email', title: '邮箱', width:200, align:"center"}
                ,{field: 'toolBar', title: '操作', align:'center',width:200,toolbar:'#userBar'}//工具列
            ]]
        });

		//搜索
		form.on('submit(search)',function (data) {
			var obj = document.getElementById("selectId"); //定位id
			number =data.field.number;
			username =data.field.username;
			classId =data.field.classId;
			telephone =data.field.telephone;
			email =data.field.email;
			table.reload('userList',{
				where: {'number':number,'username':username,'classId':classId,'telephone':telephone,'email':email,'deleteStatus':0}
			});
			return false;
		});

		//批量删除
		$(".delAll").click(function () {
			let checkStatus = table.checkStatus('userList'),//监听复选框的选中事件，关联的是js中渲染table的id
				data = checkStatus.data,
				ids = "";
			if (data.length>0){
				for(let i=0;i<data.length;i++){
					ids+=data[i].userId+",";
				}
				ids=ids.substring(0,ids.length-1);
				layer.confirm('确定删除选中学生？',{icon:3,title:'提示信息'},function () {
					$.get('http://localhost:8088/student/deleteStudents',{ids:ids},function (res) {
						if(JSON.parse(res).code == 0){
							layer.msg('删除成功！',{icon:6,time:200},function () {
								location.href='http://localhost:8088/student/list';
							})
						}
						else {
							layer.msg('删除失败！',{icon:5,time:200},function () {
								location.href='http://localhost:8088/student/list';
							})
						}
					})
				})
			}
		});

		//工具列操作
		table.on('tool(userList)',function (obj) {
			let data = obj.data;
			switch(obj.event){
				case 'edit':
					layer.open({
						type: 2,
						title: "修改学生信息",
						closeBtn: 2,
						area: ['50%', '95%'],
						shade: 0.2,
						id: (new Date()).valueOf(), //设定一个id，防止重复弹出 时间戳1280977330748
						btnAlign: 'r',
						moveType: 0, //拖拽模式，0或者1
						content: 'http://localhost:8088/student/edit?userId='+obj.data.userId,
						success: function(layero, index){
							$.ajax({
								url:'http://localhost:8088/student/selectOne?userId='+obj.data.userId,
								dataType:'json',
								success:function(res){
									var body = layer.getChildFrame('body', index);
									body.find(".userId").val(res.data.userId);
									body.find(".username").val(res.data.username);
									body.find(".birthdayStr").val(res.data.birthdayStr);
									body.find(".number").val(res.data.number);
									body.find(".className").val(res.data.className);
									body.find(".telephone").val(res.data.telephone);
									body.find(".email").val(res.data.email);
									if (res.data.gender == 0){
										body.find("#gender").attr("checked","checked");
									}else {
										body.find("#gender1").attr("checked","checked");
									}

									if (res.data.maritalStatus == 0){
										body.find("#maritalStatus").attr("checked","checked");
									}else if(res.data.maritalStatus == 1){
										body.find("#maritalStatus1").attr("checked","checked");
									}else if(res.data.maritalStatus == 2){
										body.find("#maritalStatus2").attr("checked","checked");
									}

									if (res.data.politicalAppearance == 0){
										body.find("#politicalAppearance").attr("checked","checked");
									}else if(res.data.politicalAppearance == 1){
										body.find("#politicalAppearance1").attr("checked","checked");
									}else if(res.data.politicalAppearance == 2){
										body.find("#politicalAppearance2").attr("checked","checked");
									}
									body.find(".nativePlace").val(res.data.nativePlace);
									body.find(".nation").val(res.data.nation);
									body.find(".entranceTimeStr").val(res.data.entranceTimeStr);
									body.find(".graduationSchool").val(res.data.graduationSchool);
									body.find(".idNumber").val(res.data.idNumber);
									body.find(".residentialTel").val(res.data.residentialTel);
									body.find(".zipCode").val(res.data.zipCode);
									body.find(".registeredResidence").val(res.data.registeredResidence);
									body.find(".familyAddress").val(res.data.familyAddress);
								}
							});
						}

					});

					break;
				case 'changeStu':
					layer.confirm('确定删除选中学生？',{icon:3,title:"提示信息"},function (index) {
						$.post("http://localhost:8088/student/deleteStudents",{ids:data.userId},function (res) {
							if(JSON.parse(res).code == 0){
								top.layer.msg("删除成功！");
								window.location.href="http://localhost:8088/student/list";
								layer.close(index);
							}
							else {top.layer.msg("删除失败！");}
						})
					});
					break;
			}
		});

		//动态添加下拉框 同时可以设置默认值
		$.ajax({
			url:'http://localhost:8088/class/selectAllClasses',
			dataType:'json',
			//type:'post',
			success:function(res){
				$.each(res.data,function(index,item){
					//option 第一个参数是页面显示的值，第二个参数是传递到后台的值
					$('#selectId').append(new Option(item.classname,item.classId));//往下拉菜单里添加元素
					//设置value（这个值就可以是在更新的时候后台传递到前台的值）为2的值为默认选中
					$('#selectId').val(0);
				})

				form.render(); //更新全部表单内容
			}
		});

		$(document).on('click',"#export",function(){
			var number = document.getElementById("number").value;
			var username = document.getElementById("username").value;
			var classId = document.getElementById("selectId").value;
			var telephone = document.getElementById("telephone").value;
			var email = document.getElementById("email").value;
			window.location.href="http://localhost:8088/student/exportStudentsByConditions?number="+number+"&username="+username
			+"&classId="+classId+"&telephone="+telephone+"&email="+email;
		});

		upload.render({
			elem: '#import'
			,url: "http://localhost:8088/student/importStudents"
			,accept: 'file' //普通文件
			,multiple: true
			,done: function(res){
				if(res.desc == "OK"){
					top.layer.msg("导入成功！");
				}else {
					top.layer.msg("导入失败！");
				}
			}
		});

	})

});




