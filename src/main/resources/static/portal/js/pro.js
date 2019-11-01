$(function(){
	/************商品筛选***************/
	$(".choice .default").click(function(){
		$(this).siblings("ul").toggle();
		$(this).toggleClass("on");
	});
	$(".choice .select li").click(function(){
		var txt = $(this).text();
		$(".choice .default").text(txt).removeClass("on");
		$(this).parent("ul").slideUp('fast');
	});
/**********************************************共用*****************************************************/
/**********************商品提示************************************/
	$(".proIntro .smallImg p img").hover(function(){
		$(this).parents(".smallImg").find("span").remove();
		var lf = $(this).position().left;
		var con = $(this).attr("alt");
		$(this).parent("p").addClass('on');
		$(this).parents(".smallImg").append('<span>'+con+'</span>');
		$(".smallImg").find("span").css("left",lf);
	},function(){
		$(this).parents(".smallImg").find("span").remove();
		//mine 给选中的颜色添加了selected Class,避免移除鼠标后就非选中
		if(!$(this).parent("p").hasClass("selected")){
            $(this).parent("p").removeClass('on');
		}
	});
	$(".proIntro .smallImg img").click(function(){
		var offset = $(this).attr("data-src");
		//小弹框和详情页图片大小不一样
		$(this).parents(".proCon").find('.proImg').children(".det").attr("src",offset).css({'width':'580px','height':'580px'});
		$(this).parents(".proCon").find('.proImg').children(".list").attr("src",offset).css({'width':'360px','height':'360px'});
		$(this).parents(".smallImg").find("span").remove();
		$(this).parent("p").addClass('on').siblings().removeClass('on');
		//移除鼠标离开事件
		$(this).off("mouseleave").parent('p').siblings().find('img').on('mouseleave',function(){
			$(this).parents(".smallImg").find("span").remove();
			$(this).parent("p").removeClass('on');
		})
	});
	/*************************小图切换大图*****************************/
	$(".smallImg > img").mouseover(function(){
		$(this).css("border","1px solid #c10000").siblings("img").css("border","none");
		var src = $(this).attr("data-src");
		$(this).parent().siblings(".det").attr("src",src).css({'width':'580px','height':'580px'});
		$(this).parent().siblings(".list").attr("src",src).css({'width':'360px','height':'360px'});
	})
	
	/**********proDetail tab***************/
	$(".msgTit a").click(function(){
		var index = $(this).index();
		$(this).addClass("on").siblings().removeClass("on");
		$(".msgAll").children("div").eq(index).show().siblings().hide();
	});
	
	/***********************order***************/
	$(".addre").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
	})
	$(".way img").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
	})
	$(".dis span").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
	});
	/************************ok************************/
	var seconds = $(".ok span").text();
	function time(){
		seconds--;
		$(".ok span").text(seconds);
		if(seconds==0){
			var redirectURL = $(".ok a").attr("href");
			window.location.href=redirectURL;
		}
	}
	setInterval(time,1000);
	/************************forget************************/
	$(".next").click(function(){
		$(".two").show();
		$(".one").hide();
		$(".forCon ul li").eq(1).addClass("on").siblings("li").removeClass("on");
	});
})
