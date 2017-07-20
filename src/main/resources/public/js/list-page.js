/**
 * Created by xieqiang on 2016/11/20.
 */

$(function () {
  ajaxClick('lock','PUT');
  ajaxClick('trash','DELETE');
});

function ajaxClick(name,type){
    $("span[name='"+name+"']").click(function () {
        $.ajax({
            type:type,
            url:$(this).attr("data"),
            success:function (data) {
                if (data && typeof data == 'string') {//admin系统管理方面的接口
                    alert(data);
                } else if (typeof data == 'object') {//blog站点管理接口
                    if (data.success) {
                        window.location.reload();
                    } else {
                        alert(data);
                    }
                }else{
                    window.location.reload();
                }
            },
            error:function (e) {
                console.log(e);
                alert("未知错误:"+e);
            }
        })
    })
}