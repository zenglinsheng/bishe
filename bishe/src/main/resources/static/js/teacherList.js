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
            ,url: 'http://localhost:8088/teacher/selectTeachers' //数据接口
            ,where: {'deleteStatus':0}
            ,page: true //开启分页
            ,text:"数据加载失败"
            ,cols: [[ //表头
                {checkbox:true,width:'50',fixed: 'left'}//单选框
                ,{field: 'jobNumber', title: '工号', width:200, sort: true, fixed: 'left', align:"center"}
                ,{field: 'username', title: '姓名', width:200, align:"center"}
                ,{field: 'currentTitle', title: '职称', width:200, align:"center",templet:function(d){
                        if (d.currentTitle == 0) {
                            return "讲师";
                        }else if(d.currentTitle == 1) {
                            return "助教";
                        }else if(d.currentTitle == 2) {
                            return "副教授";
                        }else {
                            return "教授";
                        }
                    }}
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
            jobNumber =data.field.jobNumber;
            username =data.field.username;
            currentTitle =data.field.currentTitle;
            telephone =data.field.telephone;
            email =data.field.email;
            table.reload('userList',{
                where: {'jobNumber':jobNumber,'username':username,'currentTitle':currentTitle,'telephone':telephone,'email':email,'deleteStatus':0}
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
                    $.get('http://localhost:8088/teacher/deleteTeachers',{ids:ids},function (res) {
                        if(JSON.parse(res).code == 0){
                            layer.msg('删除成功！',{icon:6,time:200},function () {
                                location.href='http://localhost:8088/teacher/list';
                            })
                        }
                        else {
                            layer.msg('删除失败！',{icon:5,time:200},function () {
                                location.href='http://localhost:8088/teacher/list';
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
                        title: "修改老师信息",
                        closeBtn: 2,
                        area: ['60%', '98%'],
                        shade: 0.2,
                        id: (new Date()).valueOf(), //设定一个id，防止重复弹出 时间戳1280977330748
                        btnAlign: 'r',
                        moveType: 0, //拖拽模式，0或者1
                        content: 'http://localhost:8088/teacher/edit',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            $.ajax({
                                url:'http://localhost:8088/teacher/selectOne?userId='+obj.data.userId,
                                dataType:'json',
                                async: false,
                                success:function(res){
                                    body.find(".userId").val(res.data.userId);
                                    body.find(".username").val(res.data.username);
                                    body.find(".birthdayStr").val(res.data.birthdayStr);
                                    body.find(".jobNumber").val(res.data.jobNumber);
                                    body.find(".telephone").val(res.data.telephone);
                                    body.find(".email").val(res.data.email);
                                    body.find(".nativePlace").val(res.data.nativePlace);
                                    //性别
                                    if (res.data.gender == 0){
                                        body.find("#gender").attr("checked","checked");
                                    }else {
                                        body.find("#gender1").attr("checked","checked");
                                    }
                                    //婚姻状况
                                    if (res.data.maritalStatus == 0){
                                        body.find("#maritalStatus").attr("checked","checked");
                                    }else if(res.data.maritalStatus == 1){
                                        body.find("#maritalStatus1").attr("checked","checked");
                                    }else if(res.data.maritalStatus == 2){
                                        body.find("#maritalStatus2").attr("checked","checked");
                                    }
                                    //政治面貌
                                    if (res.data.politicalAppearance == 0){
                                        body.find("#politicalAppearance").attr("checked","checked");
                                    }else if(res.data.politicalAppearance == 1){
                                        body.find("#politicalAppearance1").attr("checked","checked");
                                    }else if(res.data.politicalAppearance == 2){
                                        body.find("#politicalAppearance2").attr("checked","checked");
                                    }
                                    //政治面貌
                                    if (res.data.politicalAppearance == 0){
                                        body.find("#politicalAppearance").attr("checked","checked");
                                    }else if(res.data.politicalAppearance == 1){
                                        body.find("#politicalAppearance1").attr("checked","checked");
                                    }else if(res.data.politicalAppearance == 2){
                                        body.find("#politicalAppearance2").attr("checked","checked");
                                    }
                                    //最高职称
                                    if (res.data.topTitle == 0){
                                        body.find("#topTitle").attr("checked","checked");
                                    }else if(res.data.topTitle == 1){
                                        body.find("#topTitle1").attr("checked","checked");
                                    }else if(res.data.topTitle == 2){
                                        body.find("#topTitle2").attr("checked","checked");
                                    }else if(res.data.topTitle == 3){
                                        body.find("#topTitle3").attr("checked","checked");
                                    }
                                    //现聘职称
                                    if (res.data.currentTitle == 0){
                                        body.find("#currentTitle").attr("checked","checked");
                                    }else if(res.data.currentTitle == 1){
                                        body.find("#currentTitle1").attr("checked","checked");
                                    }else if(res.data.currentTitle == 2){
                                        body.find("#currentTitle2").attr("checked","checked");
                                    }else if(res.data.currentTitle == 3){
                                        body.find("#currentTitle3").attr("checked","checked");
                                    }
                                    //第一学历
                                    if (res.data.firstDegree == 0){
                                        body.find("#firstDegree").attr("checked","checked");
                                    }else if(res.data.firstDegree == 1){
                                        body.find("#firstDegree1").attr("checked","checked");
                                    }else if(res.data.firstDegree == 2){
                                        body.find("#firstDegree2").attr("checked","checked");
                                    }
                                    //最高学历
                                    if (res.data.highestDegree == 0){
                                        body.find("#highestDegree").attr("checked","checked");
                                    }else if(res.data.highestDegree == 1){
                                        body.find("#highestDegree1").attr("checked","checked");
                                    }else if(res.data.highestDegree == 2){
                                        body.find("#highestDegree2").attr("checked","checked");
                                    }
                                    //编制情况
                                    if (res.data.compilation == 0){
                                        body.find("#compilation").attr("checked","checked");
                                    }else if(res.data.compilation == 1){
                                        body.find("#compilation2").attr("checked","checked");
                                    }

                                    body.find(".nation").val(res.data.nation);
                                    body.find(".workingTimeStr").val(res.data.workingTimeStr);
                                    body.find(".entranceTimeStr").val(res.data.entranceTimeStr);
                                    body.find(".idNumber").val(res.data.idNumber);
                                    body.find(".residentialTel").val(res.data.residentialTel);
                                    body.find(".administrativePosition").val(res.data.administrativePosition);
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
                        $.post("http://localhost:8088/teacher/deleteTeachers",{ids:data.userId},function (res) {
                            if(JSON.parse(res).code == 0){
                                top.layer.msg("删除成功！");
                                window.location.href="http://localhost:8088/teacher/list";
                                layer.close(index);
                            }
                            else {top.layer.msg("删除失败！");}
                        })
                    });
                    break;
            }
        });

        $(document).on('click',"#export",function(){
            var jobNumber = document.getElementById("jobNumber").value;
            var username = document.getElementById("username").value;
            var currentTitle = document.getElementById("selectId").value;
            var telephone = document.getElementById("telephone").value;
            var email = document.getElementById("email").value;
            window.location.href="http://localhost:8088/teacher/exportTeachersByConditions?jobNumber="+jobNumber+"&username="+username
                +"&currentTitle="+currentTitle+"&telephone="+telephone+"&email="+email;
        });

        upload.render({
            elem: '#import'
            ,url: "http://localhost:8088/teacher/importTeachers"
            ,accept: 'file' //普通文件
            ,multiple: true
            ,done: function(res){
                console.log(res)
                if(res.desc == "OK"){
                    top.layer.msg("导入成功！");
                }else {
                    top.layer.msg("导入失败！");
                }
            }
        });

    })

});