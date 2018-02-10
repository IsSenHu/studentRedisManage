$(function () {
    $("#timeOut").click(acceptSmsVerifyCode);
    $("#registUser").click(registUser);
    $("input[name='passwordAgain']").keyup(checkPasswordAgain);
    $("input[name='password']").keyup(checkPassword);
    $("input[name='mobilePhone']").keyup(checkPhone);
    $("input[name='mobilePhone']").blur(checkPhone);
    $("#userLogin").click(userLogin);
    $("#showAdd").click(showItAdd);
    $("#closeAdd").click(closeItAdd);
    $("#closeupodate").click(closeItUpdate);
    $(".update").click(showStudentInfo);
    var date = new Date();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if(month < 10){
        month = '0' + month;
    }
    if(day < 10){
        day = '0' + day;
    }
    date.getMonth();
    $("input[name='birthday']").val(date.getFullYear() + '-' + month + '-' + day);
});

function acceptSmsVerifyCode() {
    if(!checkPhone()){
        $("#phoneInfo").text('请输入手机号！').css("color", "red");
        return;
    }
    var mobilePhone = $("input[name='mobilePhone']").val();
    $.ajax({
        type : 'post',
        url : '/studentManage/sendSms.action',
        data : 'mobilePhone=' + mobilePhone,
        success : function (data) {
            var json = $.parseJSON(data);
            if("ok" == json.result){
                $(".clickA").attr("disabled", true);
                var timeOut = $("#timeOut");
                var time = 60;
                var showTime = self.setInterval(function () {
                    if(time >= 0){
                        timeOut.text('(' + time + ')秒后可重新获取');
                        time--;
                    }else {
                        timeOut.text('点击获取短信验证码');
                        $(".clickA").attr("disabled", false);
                        self.clearInterval(showTime)
                    }
                }, 1000);
            }else if (json.result == "existed"){
                $("#msg").text("该手机已被注册").css("color", "red").slideDown("slow");
                self.setTimeout(function () {
                    $("#msg").hide();
                }, 3000);
            }else {
                alert('验证码获取失败，请重试！');
            }
        }
    });
}
function registUser() {
    var m = checkPhone();
    var n = checkPassword();
    var l = checkPasswordAgain();
    if(!(m && n && l)){
        return;
    }
    var valiCode = $("input[name='valiCode']").val();
    if(valiCode == ''){
        showErrorMsg("请输入验证码");
        return;
    }
    var password = $("input[name='password']").val();
    var mobilePhone = $("input[name='mobilePhone']").val();
    $.ajax({
        type : 'post',
        url : '/studentManage/userRegist.action',
        data : 'valiCode=' + valiCode + '&password=' + password + '&mobilePhone=' + mobilePhone,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.result == "faile"){
                showErrorMsg("验证码错误");
            }else if (json.result == "existed"){
                showErrorMsg("该手机已被注册");
            }else {
                showOkMsg("注册成功");
                self.setTimeout(function () {
                    location.href="/studentManage/toLogin.action";
                }, 2000);
            }
        }
    });
}
function checkPassword() {
    var password = $("input[name='password']").val();
    if(password.length < 6 || password.length > 16){
        $("#passwordInfo").text("密码长度在6-16之间！").css("color", "red");
        return false;
    }else if(password.indexOf(" ") != -1){
        $("#passwordInfo").text("密码不能有空格！").css("color", "red");
        return false;
    }else {
        $("#passwordInfo").text("输入正确！").css("color", "green");
        return true;
    }
}
function checkPasswordAgain() {
    var password = $("input[name='password']").val();
    var passwordAgain = $("input[name='passwordAgain']").val();
    if(password != passwordAgain){
        $("#passwordAgainInfo").text("两次密码不一致！").css("color", "red");
        return false;
    } else if(passwordAgain == ''){
        $("#passwordAgainInfo").text("请输入密码！").css("color", "red");
        return false;
    }else {
        $("#passwordAgainInfo").text("两次密码一致！").css("color", "green");
        return true;
    }
}
function checkPhone() {
    //获取手机号
    var mobilePhone = $("input[name='mobilePhone']").val();
    //手机号正则
    var phoneReg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if(!phoneReg.test(mobilePhone)){
        $("#phoneInfo").text('手机号格式不正确！').css("color", "red");
        return false;
    }else {
        $("#phoneInfo").text('输入正确！').css("color", "green");
        return true;
    }
}
function userLogin() {
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();
    var rememberMe = $("input[name='rememberMe']").val();
    $.ajax({
        type : 'post',
        url : '/studentManage/userLogin.action',
        data : 'username=' + username + '&password=' + password + '&rememberMe=' + rememberMe,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.result == "ok"){
                location.assign("/studentManage/pageStudent.action?currentPage=1");
            }else if(json.result == "forbidden"){
                showErrorMsg("暂时无法登录");
            }else if(json.result == "faile"){
                showErrorMsg("用户名或密码错误，请重试");
            }else {
                showErrorMsg("该用户不存在，请检查用户名");
            }
        }
    });
}
function showErrorMsg(msg) {
    var divMsg = $("#msg");
    divMsg.text(msg).css("color", "red").slideDown("slow");
    self.setTimeout(function () {
        divMsg.hide();
    }, 3000);
}
function showOkMsg(msg) {
    var divMsg = $("#msg");
    divMsg.text(msg).css("color", "green").slideDown("slow");
    self.setTimeout(function () {
        divMsg.hide();
    }, 3000);
}
function showItAdd() {
    $("#add").css("display", "block");
}
function closeItAdd() {
    $("#add").css("display", "none");
}
function closeItUpdate() {
    $("#updateStudent").css("display", "none");
}
function addStudent(currentPage) {
    /*
        * 1,先得到输入的所有信息
        * */
    var name = $("input[name='name']").val();
    var birthday = $("input[name='birthday']").val();
    var avgscore = $("input[name='avgScore']").val();
    var description = $("textarea[name='description']").val();
    var jsonStr = 'name=' + name + '&birthday=' + birthday + '&avgscore=' + avgscore + '&description=' + description;
    if(isNaN(parseInt(avgscore))){
        $("input[name='avgScore']").val("输入的不是一个数字").css("color", "red").click(function () {
            if($("input[name='avgScore']").css("color") == "rgb(255, 0, 0)"){
                $("input[name='avgScore']").val('').css("color", "rgb(0, 0, 0)");
            }
        });
        return;
    }
    $.ajax({
        type : 'post',
        url : '/studentManage/addStudent.action',
        data : jsonStr,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.result && json.result == "ok"){
                //添加成功
                location.assign('/studentManage/pageStudent.action?currentPage=' + currentPage);
                return;
            }
            if(json.nameError){
                $("input[name='name']").val(json.nameError).css("color", "red").click(function () {
                    if($("input[name='name']").css("color") == "rgb(255, 0, 0)"){
                        $("input[name='name']").val('').css("color", "rgb(0, 0, 0)");
                    }
                });
            }
            if(json.avgscoreError){
                $("input[name='avgScore']").val(json.avgscoreError).css("color", "red").click(function () {
                    if($("input[name='avgScore']").css("color") == "rgb(255, 0, 0)"){
                        $("input[name='avgScore']").val('').css("color", "rgb(0, 0, 0)");
                    }
                });
            }
            if(json.descriptionError){
                $("input[name='description']").text(json.descriptionError).css("color", "red").click(function () {
                    if($("input[name='description']").css("color") == "rgb(255, 0, 0)"){
                        $("input[name='description']").text('').css("color", "rgb(0, 0, 0)");
                    }
                });
            }
        }
    });
}
function deleteStudent(obj,currentPage) {
    var studentId = obj.value;
    $.ajax({
        type : 'post',
        url : '/studentManage/deleteStudent.action',
        data : 'studentId=' + studentId,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.result == "ok"){
                location.href = '/studentManage/pageStudent.action?currentPage=' + currentPage;
            }else if(json.result == "faile"){
                alert("删除失败");
            }
        }
    });
}
function showStudentInfo() {
    var studentId = this.value;
    $.ajax({
        type : 'post',
        url : '/studentManage/showStudentInfo.action',
        data : 'studentId=' + studentId,
        success : function (data) {
            var json = $.parseJSON(data);
            $("input[name='name2']").val(json.name);
            $("input[name='birthday2']").val(json.birthday.substr(0, 10));
            $("input[name='avgScore2']").val(json.avgscore);
            $("textarea[name='description2']").val(json.description);
            $("input[name='studentId']").val(json.studentId);
            $("#updateStudent").css("display", "block");
        }
    });
}
function updateStudent(currentPage) {
    var studentId = $("input[name='studentId']").val();
    var name = $("input[name='name2']").val();
    var birthday = $("input[name='birthday2']").val();
    var avgscore = $("input[name='avgScore2']").val();
    var description = $("textarea[name='description2']").val();
    var jsonStr = 'studentId=' + studentId + '&name=' + name + '&birthday=' + birthday + '&avgscore=' + avgscore + '&description=' + description;
    $.ajax({
       type : 'post',
       url : '/studentManage/updateStudent.action',
       data : jsonStr,
       success : function (data) {
           var json = $.parseJSON(data)
               if(json.result == "ok"){
                   alert("修改成功");
                   location.href = '/studentManage/pageStudent.action?currentPage=' + currentPage;
               }else {
                   alert("修改失败");
               }
           }
    });
}
// function pageStudent(currentPage) {
//     $.ajax({
//         type : 'post',
//         url : '/studentManage/pageStudents.action',
//         data : 'currentPage=' + currentPage,
//         success : function (data) {
//             var json = $.parseJSON(data);
//             var page = json.page;
//             for(var i = 0; i < page.length; i++){
//                 var student = page[i];
//                 var birthday = student.birthday;
//                 birthday = birthday.substr(0,4) + '年' + birthday.substr(5,2) + '月' + birthday.substr(8,2) + '日';
//                 var child = '<tr><td style="vertical-align: middle;">' + student.studentId + '</td>';
//                 child = child + '<td style="vertical-align: middle;">' + student.name +'</td>';
//                 child = child + '<td style="vertical-align: middle;">' + birthday + '</td>';
//                 child = child + '<td style="vertical-align: middle;">' + student.avgscore + '</td>';
//                 child = child + '<td style="vertical-align: middle;">' + '<button value="' + student.studentId + '" ' +
//                     'style="border-radius: 5px; height: 35px; width: 50px; padding: 0px;" type="button" ' +
//                     'class="btn btn-warning update" onclick="showStudentInfo(this)">修改</button>' +
//                     ' <button value="'+ student.studentId +'" onclick="deleteStudent(this,' + currentPage + ')" ' +
//                     'style="border-radius: 5px; height: 35px; width: 50px; padding: 0px;" type="button" ' +
//                     'class="btn btn-danger delete">删除</button>' + '</td></tr>';
//                 $("#parentTbody").append(child);
//             }
//         }
//     });
// }