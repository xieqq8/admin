/**
 * Created by xieqiang on 2016/12/18.
 */
$(function () {

    
    $("form").submit(function (event) {
        var form=$(this);
        var data = form.serializeJSON();
        var json = JSON.stringify(data, null, 4);
        console.log(json);
        $.ajax({
            url:form.attr("action"),
            type:'post',
            contentType: 'application/json;charset=utf-8',
            data: json,
            dataType:'json',
            success:function (data) {
                if (data.success){
                    alert("操作成功");
                }else{
                    alert(data.msg);
                }
            },
            error:function (e) {
                console.log(e);
                alert("未知错误:"+e);
            }
        });
        event.preventDefault();
    })
});
