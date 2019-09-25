/*全局变量*/
var contentMap = {};

/**
 *启动所有弹出框提示
 */
$(function () {
    $('[data-toggle="popover"]').popover(); //弹出框
    $('[data-toggle="tooltip"]').tooltip(); //工具提示
    $('#contentWords').css({
        'color': '#999',
        'font-size': '13px',
        'margin-right': '28px',
        'height': '34px',
        'line-height': '34px'
    });
})


$(document).click(function () {
    document.body.addEventListener("click", function () {
        $('#content').attr('rows', 1);
        $('#cancel').css('display', 'none');
    }, true)
});


/**
 *变大输入框
 */
function changeBig(e) {
    e.setAttribute('rows', 5);
    $('#cancel').css('display', 'block');
}

/**
 * 清楚popover属性
 */
function clearContent() {
    $('#content').popover("destroy");
}

/**
 *提示输入框还可以输入多少字
 */
function residueContent() {
    var contentNum = $('#content').val().length;
    var num = 1000 - contentNum;
    $('#contentWords').html('还可以输入' + num + '字');
}


/**
 *显示回复、查看回复、删除
 */
function changeShow(e) {
    var id = e.id;
    $('#qSpan-' + id).css('visibility', 'visible');
}

/**
 *隐藏回复、查看回复、删除
 */
function changeHidden(e) {
    var id = e.id;
    $('#qSpan-' + id).css('visibility', 'hidden');
}


/**
 * 取消回复
 */
function cancel() {
    $('#content').attr('rows', 5);
    $('#cancel').css('display', 'block');
    $('#content').val('').attr('placeholder', '想对作者说些什么');
    $('#toId').val('');
    $('#commentParentId').val('');
    var contentNum = $('#content').val().length;
    var num = 1000 - contentNum;
    $('#contentWords').html('还可以输入' + num + '字');

}


/**
 * 显示img提示内容
 */
$('#qq').on('mouseover', function () {
    $('#qq').tooltip({
        toggle: "tooltip",
        placement: "right",
        html: true,
        title: '<div class="panel panel-default" style="text-align: left">\n' +
            '\t\t\t\t<div class="panel-heading" style="padding: 10px">站长个人信息介绍</div>\n' +
            '\t\t\t\t<div class="panel-body" style="color: black;padding: 10px">\n' +
            '\t\t\t\t\t站长是一个<br>\n' +
            '\t\t\t\t\t青春、阳光、积极、活泼的<span style="font-size: 20px; color: red;">18</span>岁程序猿。\n' +
            '\t\t\t\t\t<h5>扫一扫二维码，加我qq或技术交流群</h5>\n' +
            '\t\t\t\t</div>\n' +
            '\t\t\t</div>',
        template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner" style="background: white;"></div></div>'
    });
    $('#qq').tooltip('show');
});

/**
 *回复评论
 */
function huiFu(e) {
    var id = e.getAttribute('data-id');//编号
    var pId = e.getAttribute('data-pId');//父级id
    var toId = e.getAttribute('data-toId');//@谁谁谁id
    var content = e.getAttribute('data-content');//评论内容
    var type = e.getAttribute('data-type');//评论类型
    $('#pId').val(pId);
    $('#type').val(type);
    $('#toId').val(toId);
    $('#id').val(id);
    $('#content').attr('placeholder', '回复：' + content);
    //显示按钮
    $('#cancel').css('display', 'black');

}

/**
 * handlebars 模板处理
 * @param id
 * @param name
 * @param data
 */
function handlebarsTemplate(id, name, data) {
    var source = $("#" + name).html();
    //模板渲染
    var template = Handlebars.compile(source);

    //时间格式处理
    Handlebars.registerHelper('format', function (gmt) {
        var time = new Date(gmt);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s
    });

    //处理删除
    Handlebars.registerHelper("if_deleteShow", function (x1, x2, options) {
        if (x1 == x2) {
            //满足条件执行
            return options.fn(this);
        } else {
            //不满足执行{{else}}部分
            return options.inverse(this);
        }
    });

    //是否选中damuzhi
    Handlebars.registerHelper("if_damuzhi", function (val) {
        if('1' == val){
            return 'damuzhi';
        }else{
            return '';
        }
    });

    //是否点赞数为0个
    Handlebars.registerHelper("if_zero", function (val) {
        if('0' == val){
            return '';
        }else{
            return val;
        }
    });

    //传输数据
    var html = template(data);
    //模版装载到dom节点上
    $("#childComment" + id).html(html);
}


function jiHe(id) {
    for (var i in contentMap) {
        if (i === id) {
            return contentMap[i];
        }
    }
    return null;
}

/**
 *切换子评论
 */

function changeChildBox(e) {
    var id = e.getAttribute('data-id');
    var commentCount = e.getAttribute('data-commentCount');
    var isShow = document.getElementById('childBox-' + id).style.display;
    var content = jiHe(id)
    if (content == null) {
        $.ajax({
            type: 'GET',
            url: '/selectChileByParentId',
            contentType: "application/json;charset=utf-8",
            data: {
                'parentId': id
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == 200) {
                    contentMap[id] = data.data;

                    handlebarsTemplate(id, 'childComment-template', data.data);
                    var a = $('#commentator').val();
                    var b = $('#loginId').val();
                    if (a == b) {
                        $('#deleteShow' + a).css('display', 'block');
                    }
                    if ('none' === isShow) {
                        e.innerHTML = '收起回复';
                        $('#childBox-' + id).css('display', 'block');
                    } else {
                        $('#childBox-' + id).css('display', 'none');
                        e.innerHTML = '查看回复(' + commentCount + ')';
                    }
                    $('#content').val('');
                } else {
                    if (data.code == 2003) {
                        var isTrue = confirm(data.message);
                        if (isTrue) {
                            window.open("https://github.com/login/oauth/authorize?client_id=d7b9062b3e2c2bce1433&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                            window.localStorage.setItem("closable", true);
                        }
                    } else {
                        alert(data.message);
                    }
                }
            }
        });
    } else {
        handlebarsTemplate(id, 'childComment-template', content);
        var a = $('#commentator').val();
        var b = $('#loginId').val();
        if (a == b) {
            $('#deleteShow').css('display', 'block');
        }
        if ('none' === isShow) {
            e.innerHTML = '收起回复';
            $('#childBox-' + id).css('display', 'block');
        } else {
            $('#childBox-' + id).css('display', 'none');
            e.innerHTML = '查看回复(' + commentCount + ')';
        }
    }


}


/**
 * 提交评论
 */
function parent() {
    var id = $('#id').val();//编号
    var parentId = $('#parentId').val(); //问题编号
    var pId = $('#pId').val();//父级编号
    var type = $('#type').val();//评论类型
    var content = $('#content').val();  //评论内容
    var toId = $('#toId').val(); //@谁谁谁id
    if (null == content || content == undefined || content == '') {
        $('#content').popover({
            animation: true,
            container: 'body',
            placement: 'top',
            trigger: 'focus',
            //   delay:{ show: 3000, hide: 1000 },//点击三秒后展示/一秒后隐藏
            content: '评论不能为空'
        });
        $('#content').popover("show");
        return false;
    }
    if (type == 2) {
        parentId = pId;
    }

    comment(id, parentId, content, type, toId);
    $('#content').val('');
    $('#content').attr('placeholder', '相对作者说些什么')
    $('#type').val(1);
    $('#toId').val(0);
    $('#pId').val(0);
    $('#id').val('');
}

/**
 *删除评论
 */
function deleteComment(id, parentId) {
    $.ajax({
        type: 'GET',
        url: '/deletecomment',
        contentType: "application/json;charset=utf-8",
        data: {
            'id': id,
            'parentId': parentId
        },
        dataType: 'json',
        success: function (data) {
            if (data.code == 200) {
                window.location.reload();
                $('#content').val('');
            } else {
                if (data.code == 2003) {
                    var isTrue = confirm(data.message);
                    if (isTrue) {
                        window.open("https://github.com/login/oauth/authorize?client_id=d7b9062b3e2c2bce1433&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(data.message);
                }
            }
        }
    });
}


/**
 * ajax插入评论函数
 */
function comment(id, parentId, content, type, toId) {
    $.ajax({
        type: 'POST',
        url: '/comment',
        data: JSON.stringify({
            'id': id,
            'parentId': parentId,
            'content': content,
            'type': type,
            'toId': toId
        }),
        contentType: "application/json;charset=utf-8",
        dataType: 'json',
        success: function (data) {
            if (data.code == 200) {
                window.location.reload();
            } else {
                if (data.code == 2003) {

                    $('#loginMtk').modal({show: true});
                } else {
                    alert(data.message);
                }
            }
        }
    });
}


/**
 *点赞
 */
function dianZan(e) {
    var commentId = e.getAttribute('data-commentId');
    var userId = e.getAttribute('data-userId');
    var questionId = $('#parentId').val();
    var likeCount = $('#zan' + commentId).html();
    if (0 == userId) {
        $('#loginMtk').modal({show: true});
        return false;
    }
    var css = $('#zan' + commentId).attr('class');
    var damuzhi = css.substr(css.length-7,css.length);
    var status = 1;
    if('damuzhi'== damuzhi) {
        status = 0;
    }
    $.ajax({
        type: 'POST',
        url: '/dianZanInsert',
        data: JSON.stringify({
            'userId': userId,
            'commentId': commentId,
            'questionId': questionId,
            'status': status
        }),
        contentType: "application/json;charset=utf-8",
        dataType: 'json',
        success: function (data) {
            if (data.code == 200) {
                if('damuzhi'== damuzhi){
                    $('#zan' + commentId).removeClass('damuzhi');
                    if(1 == likeCount){
                        $('#zan' + commentId).html('');
                    }else{
                        $('#zan' + commentId).html((likeCount-0)-(1-0));

                    }
                }else{
                    $('#zan' + commentId).addClass('damuzhi');
                    $('#zan' + commentId).html((likeCount-0)+(1-0));
                }
            } else {
                alert(data.message);
            }
        }
    });
}