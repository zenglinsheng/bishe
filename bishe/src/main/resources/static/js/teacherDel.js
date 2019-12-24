
layui.use(['form','layer','table','jquery'],function () {
    let form=layui.form,
        layer=layui.layer,
        table=layui.table,
        $ = layui.jquery;

    table.render({
        elem:'#userList'
        ,id:'userList'
        ,url: 'http://localhost:8088/teacher/selectTeachers' //数据接口
        ,where: {'deleteStatus':1}
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
            where: {'jobNumber':jobNumber,'username':username,'currentTitle':currentTitle,'telephone':telephone,'email':email,'deleteStatus':1}
        });
        return false;
    });

    //批量恢复
    $(".recoverAll").click(function () {
        let checkStatus = table.checkStatus('userList'),//监听复选框的选中事件，关联的是js中渲染table的id
            data = checkStatus.data,
            ids = "";
        if (data.length>0){
            for(let i=0;i<data.length;i++){
                ids+=data[i].userId+",";
            }
            ids=ids.substring(0,ids.length-1);
            layer.confirm('确定恢复选中老师？',{icon:3,title:'提示信息'},function () {
                $.get('http://localhost:8088/teacher/recoverTeachers',{ids:ids},function (res) {
                    if(JSON.parse(res).code == 0){
                        layer.msg('恢复成功！',{icon:6,time:200},function () {
                            location.href='http://localhost:8088/teacher/del';
                        })
                    }
                    else {
                        layer.msg('恢复失败！',{icon:5,time:200},function () {
                            location.href='http://localhost:8088/teacher/del';
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
            case 'detail':
                layer.open({
                    type: 2,
                    title: "老师信息",
                    closeBtn: 2,
                    area: ['60%', '80%'],
                    shade: 0.2,
                    id: (new Date()).valueOf(), //设定一个id，防止重复弹出 时间戳1280977330748
                    btnAlign: 'r',
                    moveType: 0, //拖拽模式，0或者1
                    content: 'http://localhost:8088/teacher/detail',
                    success: function(layero, index){
                        $.ajax({
                            url:'http://localhost:8088/teacher/selectOne?userId='+obj.data.userId,
                            dataType:'json',
                            success:function(res){
                                var body = layer.getChildFrame('body', index);
                                body.find(".jobNumber").val(obj.data.jobNumber);
                                body.find(".username").val(res.data.username);
                                body.find(".telephone").val(res.data.telephone);
                                body.find(".email").val(res.data.email);
                                body.find(".nativePlace").val(res.data.nativePlace);
                                body.find(".birthdayStr").val(res.data.birthdayStr);
                                body.find(".genderStr").val(res.data.genderStr);
                                body.find(".maritalStatusStr").val(res.data.maritalStatusStr);
                                body.find(".politicalAppearanceStr").val(res.data.politicalAppearanceStr);
                                body.find(".topTitleStr").val(res.data.topTitleStr);
                                body.find(".currentTitleStr").val(res.data.currentTitleStr);
                                body.find(".firstDegreeStr").val(res.data.firstDegreeStr);
                                body.find(".highestDegreeStr").val(res.data.highestDegreeStr);
                                body.find(".compilationStr").val(res.data.compilationStr);
                                body.find(".nation").val(res.data.nation);
                                body.find(".workingTimeStr").val(res.data.workingTimeStr);
                                body.find(".idNumber").val(res.data.idNumber);
                                body.find(".entranceTimeStr").val(res.data.entranceTimeStr);
                                body.find(".residentialTel").val(res.data.residentialTel);
                                body.find(".zipCode").val(res.data.zipCode);
                                body.find(".administrativePosition").val(res.data.administrativePosition);
                                body.find(".registeredResidence").val(res.data.registeredResidence);
                                body.find(".familyAddress").val(res.data.familyAddress);
                            }
                        });
                    }

                });

                break;
            case 'changeStu':
                console.log("恢复老师")
                layer.confirm('确定恢复选中老师？',{icon:3,title:"提示信息"},function (index) {
                    $.post("http://localhost:8088/teacher/recoverTeachers",{ids:data.userId},function (res) {
                        if(JSON.parse(res).code == 0){
                            top.layer.msg("恢复成功！");
                            window.location.href="http://localhost:8088/teacher/del";
                        }
                        else {top.layer.msg("恢复失败！");}
                    })
                });
                break;
        }
    });

})

