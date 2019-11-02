//全局变量
var tags = [];  //input中缓存的
var tagsAll = []; //缓存所有的

/**
 * 启动提示
 */
$(function () {
    $('[data-toggle="popover"]').popover(); //弹出框
    $('[data-toggle="tooltip"]').tooltip(); //工具提示
})


/**
 * 初始化时获取所有的tag标签
 * @param data
 */
function addAllTags(data) {
    /*for (i in data.tag) {
        for (j in data.tag[i].tags) {
           tagsAll.push(data.tag[i].tags[j]);
        }
    }*/
    for (i in data) {
        for (j in data[i].tags) {
            tagsAll.push(data[i].tags[j]);
        }
    }
}


/**
 * 展示tag
 */
function showTag() {
    $('#tags-templates').css('display', 'block');
    if(0 == tags.length){
        var textList = $('#chooseAfter-template').children('div');//标签集合
        var tag = textListTool(textList);
        if('' != tag){
            tags = tag.split(',');
        }
    }
    var isShow = $('#tags-templates').attr('data-true');
    $('#tag').focus();//input获取焦点
    if ('no' === isShow) {
        $('#tags-templates').attr('data-true','yes');
        $.ajax({
            type: "GET",  // 请求方式
            url: "/getTag",  // 目标资源
            dataType: "json",  // 服务器响应的数据类型
            contentType: "application/json;charset=utf-8",
            success: function (data) {  // readystate == 4 && status == 200
                console.log(data)
                var list = {};
                list['data'] = data;
                addAllTags(data);//把所有标签添加到缓存
                //获取模板
                var source = $("#tags-template").html();
                //模板渲染
                var template = Handlebars.compile(source);
                //传输数据
                var html = template(list);
                //模版装载到dom节点上
                $("#tags-templates").html(html);
                $('#tab-content div:first-child').addClass('active');
                $('#publish-tabList li:first-child').addClass('active');
            }
        });
    }
}

/**
 * 监听全局点击事件
 */
$(document).click(function () {
    document.body.addEventListener("click", function () {
        $('#tags-templates').css('display', 'none');
    }, true)
});

/**
 * 点击不同类型的tag维持tag显示
 */
function keep() {
    $('#tags-templates').css('display', 'block');
}




/**
 * 移除或添加的公共方法
 * @param tags
 */
function handlebarsChoseOrRemove(tags) {
    var content = {};
    content['tagList'] = tags;
    //获取模板
    var source = $("#tagsAndRemove-template").html();
    //模板渲染
    var template = Handlebars.compile(source);
    //传输数据
    var html = template(content);
    //模版装载到dom节点上
    $("#chooseAfter-template").html(html);
}

/**
 * 选择标签超过五个处理函数
 */
function exceedFive() {
    var size = $('#chooseAfter-template').children('div').length;
    if (size >= 5) {
        $('#publish-popover').popover({
            animation: true,
            container: 'body',
            placement: 'top',
            trigger: 'focus',
            //   delay:{ show: 3000, hide: 1000 },//点击三秒后展示/一秒后隐藏
            content: '您已经选满五个啦，不能在选了！'
        });
        $('#publish-popover').popover("show");
        setTimeout(function () {
            $('#publish-popover').popover('destroy')
        }, 1500);
        return false;
    }
    return true;
}

/**
 * 判断标签是否选择的公共处理函数
 * @returns {boolean}
 */
function isChoose(tag) {
    //判断标签是否已选择
    for (i in tags) {
        if (tags[i] == tag) {
            $('#f-' + i).css('background-color', '#fdd747')
            setTimeout(function () {
                $('#f-' + i).css('background-color', '#e7f2ed')
            }, 1000);
            return false;
        }
    }
    return true;
}

/**
 * 选择标签
 */
function choose(e) {
    var isTrue = true;
    $('#tags-templates').css('display', 'block');
    var tag = e.innerText;
    isTrue = exceedFive();//调用标签超过五个处理函数
    if (true === isTrue) {
        isTrue = isChoose(tag);//调用判断标签是否已选择函数
        if (true === isTrue) {
            tags.push(tag)//添加到数组中
            $('#chooseAfter-template').empty();//清空
            handlebarsChoseOrRemove(tags);
        }
    }
}

/**
 * 删除标签
 * @param e
 */
function remove(e) {
    $('#tags-templates').css('display', 'block');
    var id = e.id;
    var index = id.replace("c-", '');
    delete tags[index];
    $('#chooseAfter-template').empty();//清空
    handlebarsChoseOrRemove(tags);
}

/**
 * 对将要发布的问题标签做处理
 * @param data
 * @returns {string|string}
 */
function textListTool(data) {
    var tag = '';
    var lenght = data.length;
    if (0 == lenght) {
        return tag;
    }
    for (i in data) {
        if (i == data.length - 1) {
            var tag2 = data[i].innerText;
            var tag3 = tag2.replace("×", '');
            tag += tag3;
            return tag;
        }
        var tag0 = data[i].innerText;
        var tag1 = tag0.replace('×', ',');
        tag += tag1;
    }
    return tag;
}

/**
 * 验证表单
 * @returns {boolean}
 */
function check() {
    var title = $('#title').val();//标题
    if (null == title || '' == title || undefined == title) {
        $('.titleError').html('标题不能为空');
        setTimeout(function () {
            $('.titleError').html('');
        }, 2000);
        return false;
    }
    var title = $('#description').val();//标题
    if (null == title || '' == title || undefined == title) {
        $('.descriptionError').html('描述不能为空');
        setTimeout(function () {
            $('.descriptionError').html('');
        }, 2000);
        return false;
    }
    var textList = $('#chooseAfter-template').children('div');//标签集合
    var tag = textListTool(textList);
    if (null == tag || '' == tag || undefined == tag) {
        $('.tagError').html('标签至少一个');
        setTimeout(function () {
            $('.tagError').html('');
        }, 2000);
        return false;
    }
    return true;
}


/**
 * 发布问题
 */
function faBu() {
    var id = $('#id').val();//获取提问的编号
    var title = $('#title').val();//标题
    var description = $('#description').val();//问题描述
    var textList = $('#chooseAfter-template').children('div');//标签集合
    var tag = textListTool(textList);
    var isTrue = check();//调用表单验证函数
    if (true != isTrue) {
        return false;
    }
    $.ajax({
        type: 'POST',
        url: '/publish',
        data: {
            'id': id,
            'title': title,
            'description': description,
            'tag': tag
        },
        success: function (data) {
            if (data.code == 200) {
                var isTrue = confirm(data.message + '，是否回到主页？');
                if (isTrue) {
                    window.location.href = "http://localhost/index";
                } else {
                    window.location.reload();
                }
            } else {
                if (data.code == 2003) {
                    var isTrue = confirm(data.message);
                    if (isTrue) {
                        window.open("https://github.com/login/oauth/authorize?client_id=d7b9062b3e2c2bce1433&redirect_uri=http://localhost/callback&scope=user&state=1");
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
 * input输入框添加标签
 */
function addTag(e) {
    if(0 == tags.length){
        var textList = $('#chooseAfter-template').children('div');//标签集合
        var tag = textListTool(textList);
        tags = tag.split(',');
    }
    var isTrue = true;
    var tag = e.value;
    var symbol = tag.charAt(tag.length - 1);
    var tag1 = tag.replace(symbol, '');
    if (',' === symbol || ';' === symbol || '，' === symbol) {
        isTrue = exceedFive();
        if (true === isTrue) {
            isTrue = isChoose(tag1);
            if (true === isTrue || undefined === isTrue) {
                //判断标签库有没有匹配标签
                for(i in tagsAll){
                    if(tagsAll[i] == tag1){
                        tags.push(tag1)//添加到数组中
                        $('#chooseAfter-template').empty();//清空
                        handlebarsChoseOrRemove(tags);//模板处理
                        e.value = '';
                        return;
                    }
                }
                //没有匹配标签处理
                $('#publish-popover').popover({
                    animation: true,
                    container: 'body',
                    placement: 'top',
                    trigger: 'focus',
                    //   delay:{ show: 3000, hide: 1000 },//点击三秒后展示/一秒后隐藏
                    content: '抱歉，没有与之匹配的标签库标签。'
                });
                e.value = '';
                $('#publish-popover').popover("show");
                setTimeout(function () {
                    $('#publish-popover').popover('destroy')
                }, 1500);
                return;
            }
        } else {
            e.value = '';
        }
    }

}

















