$(function(){
	//getOrderInfo页面的修改
	//添加地址mine
	$(".addAddress").click(function () {
        $(".mask").show();
        $(".adddz").show();
    });
//	订单页面  修改地址
	$(".edit").click(function(){
		$(".mask").show();
		$(".adddz").show();
		//获得信息进行回显
		var shippingId = parseInt($(this).parents(".addre").attr("shippingid"));
        var receiverText = $(this).parents(".tit").children("p").eq(0).text();
		var totalAddressText = $(this).parents(".tit").next().children().eq(0).text();//获取详细的地址信息，以空格分开
        var mobileText = $(this).parents(".tit").next().children().eq(1).text();
        var zipText = $(this).parents(".tit").next().children().eq(2).text();
		var info = totalAddressText.split(" ");
        $(".adddz").find(":input[name='id']").val(shippingId);
        $(".adddz").find(":input[name='receiver']").val(receiverText);
		$(".adddz").find(":input[name='province']").val(info[0]);
        $(".adddz").find(":input[name='city']").val(info[1]);
        $(".adddz").find(":input[name='district']").val(info[2]);
        $(".adddz").find(":input[name='address']").val(info[3]);
        $(".adddz").find(":input[name='mobile']").val(mobileText);
        $(".adddz").find(":input[name='zip']").val(zipText);
	});

	$(".delete").click(function () {
		var $addreDiv = $(this).parents(".addre");
		var id = $addreDiv.attr("shippingid");
        $.ajax({
            type : "DELETE",  //提交方式
            url : "/portal/user/shipping/"+id,//路径
            success : function(res) {//返回数据根据结果进行相应的处理
                if(res.code==200){
                    $addreDiv.remove();
                }else{
                    alert(res.msg);
                }
            }
        });
    });
	$(".shippingBtn>input").click(function(){
		if($(this).val()=="保存"){
			//向后台发送请求
			var $form = $(this).parents("form");
            var id = parseInt($form.find(":input[name='id']").val());
            if(id){//修改操作
                $.ajax({
                    type : "PUT",  //提交方式
                    url : "/portal/user/shipping/"+id,//路径
                    data : $form.serialize(),
                    success : function(res) {//返回数据根据结果进行相应的处理
                        if(res.code==200){
                            window.location.reload();
                            $(".mask").hide();
                            $(".adddz").hide();
                        }else{
                            alert(res.msg);
                        }
                    }
                });
			}else{//新增操作
                $.ajax({
                    type : "POST",  //提交方式
                    url : "/portal/user/shipping",//路径
                    data : $form.serialize(),
                    success : function(res) {//返回数据根据结果进行相应的处理
                        if(res.code==200){
                            window.location.reload();
                        }else{
                            alert(res.msg);
                        }
                    }
                });
            }
			/*$(".bj").hide();
			$(".xg").hide();
			$(".remima").hide();
			$(".pj").hide();
			$(".chak").hide();*/
		}else{
			$(".mask").hide();
			$(".adddz").hide();
			/*$(".bj").hide();
			$(".xg").hide();
			$(".remima").hide();
			$(".pj").hide();
			$(".chak").hide();*/
		}
        $(".adddz form").find(":input[name='id']").val("");
        $(".adddz form")[0].reset();//重置表单的内容
	});

//订单评价
$(".commentOrder").click(function () {
    $(".mask").show();
    $(".comment").remove();//删除之前生成的
    $(".commentBtn").show();//显示按钮
    var orderId = $(this).parents(".dkuang").attr("id");
    $("#orderId").val(orderId);//表单中加入orderId
    $(this).parents(".dkuang").children(".shohou").each(function () {
       var productId = $(this).attr("productid");
       var orderDetailId = $(this).attr("orderdetailid");
       var productName = $(this).children("p").first().children("a").first().text();
       var imgSrc = $(this).find("img").attr("src");
       var $commentDiv = $($("#commentDiv").html());
       $commentDiv.attr("productId",productId);
       $commentDiv.attr("orderdetailid",orderDetailId);
       $commentDiv.find("img").next().text(productName);
       $commentDiv.find("img").attr("src",imgSrc);
       $(".commentBtn").before($commentDiv);
    });
    $(".pj").show();
});

$(".commentBtn>input").click(function () {
	if($(this).val()=="保存"){
        var comments = [];
	    //获取表单数据
        var orderId = $("#orderId").val();
        $("form > .comment").each(function(){
            var productId = $(this).attr("productid");
            var orderDetailId = $(this).attr("orderdetailid");
            var commentText = $(this).children("textarea").val();
            var comment = {};
            comment.orderId = orderId;
            comment.productId = productId;
            comment.orderDetailId = orderDetailId;
            comment.commentText = commentText;
            comments.push(comment);
        });
        $.ajax({
            type : "POST",  //提交方式
            url : "/portal/user/comment/"+orderId,//路径
            data: JSON.stringify(comments),
            dataType: "json",
            contentType: "application/json",
            success : function(res) {//返回数据根据结果进行相应的处理
                if(res.code==200){
                    alert("评价成功");
                    window.location.reload();
                    $(".mask").hide();
                    $(".pj").hide();
                }else{
                    alert(res.msg);
                }
            }
        });
	}else{
        $(".mask").hide();
        $(".pj").hide();
	}
});

//查看评价
$(".commentDetail").click(function () {
    $(".mask").show();
    $(".comment").remove();//删除之前生成的
    var orderId = $(this).parents(".dkuang").attr("id");
    var $orderItems = $(this).parents(".dkuang").children(".shohou");
    $.ajax({
        type : "GET",  //提交方式
        url : "/portal/user/comment/"+orderId,//路径
        success : function(res) {//返回数据根据结果进行相应的处理
            if(res.code==200){//
                var comments = res.data;
                $orderItems.each(function () {
                    var productId = $(this).attr("productid");
                    var orderDetailId = parseInt($(this).attr("orderdetailid"));
                    var productName = $(this).children("p").first().children("a").first().text();
                    var imgSrc = $(this).find("img").attr("src");
                    var $commentDiv = $($("#commentDiv").html());
                    $commentDiv.attr("productId",productId);
                    $commentDiv.attr("orderdetailid",orderDetailId);
                    $commentDiv.find("img").next().text(productName);
                    $commentDiv.find("img").attr("src",imgSrc);
                    for(var i=0;i<comments.length;i++){
                        if(comments[i].orderDetailId==orderDetailId){
                            $commentDiv.find("textarea").val(comments[i].commentText);
                        }
                    }
                    $commentDiv.find("textarea").attr("readonly",true);//设置为只读
                    $(".commentBtn").before($commentDiv);
                    $(".commentBtn").hide();//隐藏按钮
                });
            }else{
                alert(res.msg);
            }
        }
    });
    $(".pj").show();
});

//	个人信息 编辑
	$("#edit1").click(function(){
		$(".mask").show();
		$(".bj").show();
	});
	$("#edit2").click(function(){
		$(".mask").show();
		$(".xg").show();
	});
	
//修改头像
	$("#avatar").click(function(){
		$(".mask").show();
		$(".avatar").show();
	});

//	弹框关闭按钮
	$(".gb").click(function(){
		$(".mask").hide();
		$(".bj").hide();
		$(".xg").hide();
		$(".remima").hide();
		$(".avatar").hide();
		$(".pj").hide();
		$(".chak").hide();
	});
	
//	address
	$("#addxad").click(function(){
		$(".mask").show();
		$(".adddz").show();
	});
	$(".dizhi").hover(function(){
		var txt="";
		txt='<p class="addp"><a href="#"  id="readd">修改</a><a href="#" id="deladd">删除</a></p>'
		$(this).append(txt);
		$("#readd").click(function(){//修改/*TODO 修改这里*/
			$(".mask").show();
            $(".adddz").show();
            //获得信息进行回显
            var shippingId = parseInt($(this).parents(".dizhi").attr("shippingid"));
            var receiverText = $(this).parents(".dizhi").children("p").eq(0).text();
            var mobileText = $(this).parents(".dizhi").children("p").eq(1).text();//获取详细的地址信息，以空格分开
            var totalAddressText = $(this).parents(".dizhi").children("p").eq(2).text();
            var address = $(this).parents(".dizhi").children("p").eq(3).text();
            var zipText = $(this).parents(".dizhi").children("p").eq(4).text();
            var info = totalAddressText.split(" ");
            $(".adddz").find(":input[name='id']").val(shippingId);
            $(".adddz").find(":input[name='receiver']").val(receiverText);
            $(".adddz").find(":input[name='province']").val(info[0]);
            $(".adddz").find(":input[name='city']").val(info[1]);
            $(".adddz").find(":input[name='district']").val(info[2]);
            $(".adddz").find(":input[name='address']").val(address);
            $(".adddz").find(":input[name='mobile']").val(mobileText);
            $(".adddz").find(":input[name='zip']").val(zipText);
		});
		$("#deladd").click(function(){
			var $dizhiDiv = $(this).parents(".dizhi");
			var id = $dizhiDiv.attr("shippingid");
			$.ajax({
				type : "DELETE",  //提交方式
				url : "/portal/user/shipping/"+id,//路径
				success : function(res) {//返回数据根据结果进行相应的处理
					if(res.code==200){
						$dizhiDiv.remove();
					}else{
						alert(res.msg);
					}
				}
			});
		});
	},function(){
		$(".addp").remove();
	});

});
