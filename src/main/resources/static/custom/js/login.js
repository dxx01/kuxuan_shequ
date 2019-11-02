$(document).ready(function () {

    /**
     * input输入框获取焦点和失去焦点,改变登录的disabled
     */
    $(".lLoginForm input").on("keyup", function () {
        $('#loginError').css('visibility', 'hidden');
        //处理空格
        this.value = this.value.replace(/\s+/g, '');
        var email1 = $("#email1").val();
        var pass1 = $("#pass1").val();
        if ("" != email1 && "" != pass1) {
            $('#loginButton').attr("disabled", false).css({'background-color': '#3385ff', 'color': 'white'});
        } else {
            $('#loginButton').attr("disabled", 'disabled').css({'background-color': '#f5f5f5', 'color': '#c2c2c2'});
        }
    });
    /**
     * input输入框获取焦点和失去焦点,改变注册的disabled，同时验证一些表单
     */
    $(".lRegisterForm input").on("keyup", function () {
        $('#RegisterError').css('visibility', 'hidden');
        //处理空格
        this.value = this.value.replace(/\s+/g, '');
        var name = $("#name").val();
        var email2 = $("#email2").val();
        var yzm = $("#yzm").val();
        var pass2 = $("#pass2").val();
        if ("" != name && "" != email2 && "" != yzm && "" != pass2) {
            $('#registerButton').attr("disabled", false).css({'background-color': '#3385ff', 'color': 'white'});
        } else {
            $('#registerButton').attr("disabled", 'disabled').css({'background-color': '#f5f5f5', 'color': '#c2c2c2'});
        }
    });

    /**
     * 切换登录和注册
     */
    $(".lTab").on("click", function () {
        $('.lTab').removeClass('lActive');
        $('form').removeClass('isShow');
        $(this).addClass('lActive');
        var html = this.innerText
        if (html === '登录') {
            $('.lLoginForm').addClass('isShow');
            $('.lRegisterForm input').val('');
        } else {
            $('.lRegisterForm').addClass('isShow');
            $('.lLoginForm input').val('');
        }
    });


    /**
     * 登录
     */
    $('#loginButton').on('click', function () {
        var email = $('#email1').val();
        var pass = $('#pass1').val();
        $.ajax({
            type: 'post',
            url: '/login',
            data: {
                email: email,
                pass: pass
            },
            dataType:"text",
            success: function (data) {
                if (data == '账号或密码错误') {
                    $('#loginError').css('visibility', 'visible');
                } else if ('ok' === data) {
                    window.location.href = "http://localhost/index";
                }
            }
        })
    });

    /**
     * 注册按钮
     */
    $('#registerButton').on('click', function () {
        var name = $('#name').val();
        var email = $('#email2').val();
        var pass = $('#pass2').val();
        if (false == boolean) {
            $('#RegisterError').css('visibility', 'visible');
            $('#RegisterError').html('无效验证码，请重新获取！');
            $('#registerButton').attr("disabled", 'disabled').css({'background-color': '#f5f5f5', 'color': '#c2c2c2'});
            time = 0;
            return false;
        }
        if (email != cachEmail) {
            $('#RegisterError').css('visibility', 'visible');
            $('#RegisterError').html('邮箱与验证码邮箱不匹配');
            $('#registerButton').attr("disabled", 'disabled').css({'background-color': '#f5f5f5', 'color': '#c2c2c2'});
            time = 0;
            return false;
        }
        $.ajax({
            type: 'GET',
            url: '/register',
            data: {
                name: name,
                email: email,
                pass: pass
            },
            dataType: "json",  // 服务器响应的数据类型
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (200 == data.code) {
                    window.location.href = "http://localhost/index";
                }
            }
        });
    });

});


var time = 120; //全局变量
/**
 * 60秒处理
 * @param e
 */
function sixStop(e) {
    var yzm = $('#yzm').val();
    if (yzm == code) {
        boolean = true;
    }
    if (0 == time) {
        e.removeAttribute("disabled");
        $(e).css({'pointer-events': '', 'background': '#3385ff'});
        e.innerText = '获取验证码';
        time = 120;
    } else {
        e.setAttribute("disabled", true);
        $(e).css({'pointer-events': 'none', 'background': '#a9a9a9'});
        e.innerText = time;
        time--;
        setTimeout(function () {
            sixStop(e);
        }, 1000);
    }
}

/**
 * 获取验证码
 * @param e
 */
var code;//全局变量接受返回的yzm
var boolean = false;
var cachEmail;//缓存邮箱
function getyzm(e) {
    $('#RegisterError').css('visibility', 'hidden');
    sixStop(e);
    var email = $('#email2').val();
    $.ajax({
        type: 'post',
        url: '/getYzm',
        data: {
            email: email
        },
        success: function (data) {
            if ('邮箱不能为空' == data || '请输入正确的邮箱' == data || '该邮箱已被注册' == data) {
                $('#RegisterError').css('visibility', 'visible');
                $('#RegisterError').html(data);
                time = 0;
            } else {
                cachEmail = email;
                code = data;
            }
        }
    });
}



