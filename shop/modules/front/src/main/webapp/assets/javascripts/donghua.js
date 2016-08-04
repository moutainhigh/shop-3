window.onload = function () {
    $(".close-btn").click(function () {
        $(".animate").remove();
    })
    $(".animate").animate({height: "100%"},{duration: 1000, queue: false, complete: function() {
        $('.background2').animate({top: "-520px"},{ duration: 1500, queue: false, complete: function() {
            personShow();
            setTimeout(function(){
                removeCl ('.person-left', 'bounceInLeft');
                removeCl ('.person-right', 'bounceInRight');
                setTimeout(function () {
                    personLeftSpeak(".left-word");
                    setTimeout(function () {
                        personLeftSpeakOver(".left-word");
                        setTimeout(function () {
                            personRightSpeak(".right-word");
                            setTimeout(function () {
                                personRightSpeakOver(".right-word");
                                setTimeout(function () {
                                    personLeftSpeak();
                                    personRightSpeak(".together-word");
                                    setTimeout(function () {
                                        speakWord(".together-word", ".together-word .together1", ".together-word .together2");
                                        setTimeout(function () {
                                            speakOver();
                                            setTimeout(function () {
                                                personHide();
                                                setTimeout(function () {
                                                    $('.background2').animate({top: "-100px"},{duration: 1500, queue: false, complete: function () {
                                                        $(".animate").animate({height: "0px"}, 2000)
                                                    }})
                                                }, 800)
                                            },1500);
                                        },4000)
                                    },3000)
                                },500)
                            },2000)
                        },200)
                    },2000)
                },500);
            }, 1000);
        }}) 
    }});

    function personShow () {
        $('.person-left').addClass('animated bounceInLeft').css('opacity',"1");
        $('.person-right').addClass('animated bounceInRight').css('opacity',"1");
    }

    function personHide () {
        $('.person-left').addClass('animated bounceOutLeft');
        $('.person-right').addClass('animated bounceOutRight');
    }

    function removeCl (Dom, Class) {
        $(Dom).removeClass(Class)
    }

    function personLeftSpeak (dom) {
        $('.person-left .left1').css("opacity", "0");
        $('.person-left .left2').css("opacity", "1");
        $(dom).addClass('animated bounceIn');
    }

    function personLeftSpeakOver (dom) {
        $('.person-left .left2').css("opacity", "0");
        $('.person-left .left1').css("opacity", "1");
        removeCl(dom,"bounceIn");
    }

    function personRightSpeak (dom) {
        $('.person-right .right1').css("opacity", "0");
        $('.person-right .right2').css("opacity", "1");
        $(dom).addClass('animated bounceIn');
    }

    function personRightSpeakOver (dom) {
        $('.person-right .right2').css("opacity", "0");
        $('.person-right .right1').css("opacity", "1");
        removeCl(dom,"bounceIn");
    }

    function speakWord (dom1,dom2,dom3) {
        removeCl(dom1,"bounceIn");
        $(dom2).css("opacity", "0");
        setTimeout(function () {
            $(dom3).css("opacity", "1");
            $(dom1).addClass('animated bounceIn');
        },400)
        
    }

    function speakOver () {
        $('.person-left .left2').css("opacity", "0");
        $('.person-left .left1').css("opacity", "0");
        $('.person-right .right2').css("opacity", "0");
        $('.person-right .right1').css("opacity", "0");
        $('.person-left .left3').css("opacity", "1");
        $('.person-right .right3').css("opacity", "1");
        removeCl('.together-word',"bounceIn");
        $('.together-word').css('opacity',"0");
    }

} 