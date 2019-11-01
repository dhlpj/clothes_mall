//	导航固定顶部
$(function(){
	$(window).scroll(function(){
		var ws=$(window).scrollTop();
		if(ws>60){
			$(".head").addClass("ding").css({"background":"rgba(255,255,255,"+ws/300+")"});
		}else{
			$(".head").removeClass("ding").css({"background":"#fff"});
		}
	});
});
//mine 搜索
$("#searchForm").submit(function () {
    var searchText = $(this).find(":text").val();
    if(searchText!=""&&$.trim(searchText)==""){//搜索条件为空格
        alert("请输入正确的查询条件");
        return false;
    }
    if(searchText==""){
        searchText = "T恤";//默认的搜索条件
    }
    window.location.href = "/portal/search?key="+$.trim(searchText);
    return false;//防止跳转,二次提交
});
$("#searchForm :button").click(function () {
    var searchText = $(this).prev().val();
    if(searchText!=""&&$.trim(searchText)==""){//搜索条件为空格
        alert("请输入正确的查询条件");
        return false;
    }
    if(searchText==""){
        searchText = "T恤";//默认的搜索条件
    }
    window.location.href = "/portal/search.html?key="+$.trim(searchText);
    return false;//防止跳转,二次提交
});
//mine 退出登录
$("#logout").on("click",function(){
    $.ajax({
        type: "DELETE",
        url: "/portal/user/logout",
        success: function(data){
            if(data.code==200){
                alert("注销成功")
                window.location.reload();
            }else{
                alert(data.msg);
            }
        }
    });
});