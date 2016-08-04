$(function(){
    var sliderLeft,
        left,
        right;
    var todayImgSize = $("#todayImgSize").val();
    function swichRight () {
        if(! $(".tuijian-right-main").is(":animated") ){
            sliderLeft = $(".tuijian-right-main").css("left").split('p')[0];
            
            if (sliderLeft == -Number(todayImgSize)*890) {
                left = "-890px";
            } else {
                left = sliderLeft - 890 +"px";
            }
            
            checkRight(sliderLeft, left);

            $(".tuijian-right-main").animate({
                left: left
            }, 500);
        }
        
    }

    function checkRight (right) {
        if (right == -Number(todayImgSize)*890) {
            $(".tuijian-right-main").css("left", "0px");
        }
    }

    function swichLeft () {
        if(!$(".tuijian-right-main").is(":animated") ){
            sliderLeft = $(".tuijian-right-main").css("left").split('p')[0];

            if (sliderLeft == -890) {
                right = -Number(todayImgSize)*890;
            } else {
                right = parseInt(sliderLeft) + 890 +"px";
            }

            checkLeft(sliderLeft);
            $(".tuijian-right-main").animate({
                left: right
            }, 500);
        }
        
    }

    function checkLeft (left) {
        if (left == -890) {
            $(".tuijian-right-main").css("left", -Number(todayImgSize)*890 + "px");
        }
    }

    $(".swich-right a").click(function(){
        swichRight();
    })

    $(".swich-left a").click(function(){
        swichLeft();
    })

    function navLeftMenu (nodeLeft, color, nodeRight) {
        this.nodeLeft = nodeLeft;
        this.color = color;
        this.nodeRight = nodeRight;
        this.init();
    }

    navLeftMenu.prototype.init = function() {
        var nodeLeft = this.nodeLeft,
            color = this.color,
            nodeRight = this.nodeRight;
        nodeLeft.mouseenter(function(){
            nodeLeft.css("background-color", color);
            nodeLeft.css("width", "200px");
            nodeRight.css('display', 'block');
        });

        nodeRight.mouseenter(function(){
            nodeLeft.css("background-color", color);
            nodeLeft.css("width", "200px");
            nodeRight.css('display', 'block');
        });

        nodeLeft.mouseleave(function(){
            nodeLeft.css("background-color", "white");
            nodeLeft.css("width", "199px");
            nodeRight.css('display', 'none');
        });

        nodeRight.mouseleave(function(){
            nodeLeft.css("background-color", "white");
            nodeLeft.css("width", "199px");
            nodeRight.css('display', 'none');
        });
    }

    var navLeft_1 = new navLeftMenu($('#nav-left-list-1'), '#f0f0f0', $('.nav-left-next-1')),
        navLeft_2 = new navLeftMenu($('#nav-left-list-2'), '#f0f0f0', $('.nav-left-next-2')),
        navLeft_3 = new navLeftMenu($('#nav-left-list-3'), '#f0f0f0', $('.nav-left-next-3')),
        navLeft_4 = new navLeftMenu($('#nav-left-list-4'), '#f0f0f0', $('.nav-left-next-4')),
        navLeft_5 = new navLeftMenu($('#nav-left-list-5'), '#f0f0f0', $('.nav-left-next-5')),
        navLeft_6 = new navLeftMenu($('#nav-left-list-6'), '#f0f0f0', $('.nav-left-next-6'));

    $('.list-pr-btn').mouseenter(function(){
        $(this).attr('id', "list-pr-btn-back");
    });

    $('.list-pr-btn').mouseleave(function(){
        $(this).attr("id", 'list-pr-btn-not');
    });

})