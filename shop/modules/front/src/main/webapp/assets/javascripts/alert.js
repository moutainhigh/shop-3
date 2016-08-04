/*  使用方式例子  */

// $('.search-img').click(function () {
//         var pop = {
//               title: "呵呵",                    弹框title 
//               imgType: "success",        类型 success err warning 
//               text: "呵呵",                  弹框文字   
//               btnLeft: "ok",                    左边按钮文字
//               btnRight: "notok",                右边按钮文字
//               btnLeftCallback: showAlert1,      左边按钮函数
//               btnRightCallback: showAlert2      右边按钮函数
//             };
//           function showAlert1 () {
//             alert('怪我咯？');
//           }

//           function showAlert2 () {
//             alert('呵呵');
//           }

//           $.superAlert(pop);   调用弹框插件传递pop对象
//       })
//       
//       
//       方法： close() 关闭方法
//       
//  



window.onload = function(){
   $('head').append("<style>#lightbox h1.info::before{content: '';display: inline-block;height: 100%;vertical-align: middle;}#lightbox h1.info::after{    content: '';display: table;clear: both;}</style>");
   $('head').append("<style>.success-img{background: url(../assets/image/success.jpg) no-repeat}</style>")
   $('head').append("<style>.err-img{background: url(../assets/image/err.jpg) no-repeat}</style>")
   $('head').append("<style>.warning-img{background: url(../assets/image/warning.jpg) no-repeat}</style>")
   jQuery.extend({
        superAlert: function (pop) {

            function popOver (pop) {
                this.pop = pop;
                this.init();
            }

            popOver.prototype.init = function() {
                var back = "<div class='lightbox'></div>",
                    bodyHeight = $(document).height(),
                    pop = this.pop,
                    popMain = '<div id="lightbox">'+
                                '<div class="container-l">'+
                                    '<div class="title"><span class="closeBtn">×</span></div>'+
                                    '<div class="content">'+
                                        '<h1 class="info"><p></p></h1>'+
                                        '<p class="ctrl">'+
                                            '<input id="lightbox_payment_done" type="submit" class="formbutton" value="">'+
                                            '<input id="lightbox_payment_failed" type="submit" class="formbutton" value="">'+
                                        '</p>'+
                                        '<div class="clear"></div>'+
                                    '</div>'+
                                '</div>'+
                              '</div>';

                

                $('body').append(back);
                $('.lightbox').css({
                    "position": "absolute",
                    "width": "100%",
                    "height": "100%",
                    "background-color": "black",
                    "opacity": "0.5",
                    "z-index": "98",
                    "top": "0",
                    "filter": "progid:DXImageTransform.Microsoft.Alpha(opacity=70)",
                    "max-height": "100%",
                    "min-height": bodyHeight
                    
                })

                $('body').append(popMain);

                $('#lightbox').css({
                    "position": "fixed",
                    "left": "50%",
                    "top": "150px",
                    "margin": "0 0 0 -201px",
                    "background": "#ddd",
                    "z-index": "99",
                    "box-shadow": "0 0 20px #4A4A4A"
                })

                $('#lightbox .container-l').css({
                    "position": "relative",
                    "display": "block",
                    "border": "#aeaeae 1px solid",
                    "width": "400px",
                    "background": "#fff"
                })

                $('#lightbox .title').css({
                    "background": "#f9f9f9",
                    "display": "14px",
                    "line-height": "40px",
                    "height": "40px",
                    "border-bottom": "1px solid #aeaeae",
                    "font-weight": "bold",
                    "padding-left": "20px",
                    "padding-right": "20px"
                })

                $('.closeBtn').css({
                    "float": "right",
                    "font-size": "21px",
                    "font-weight": "700",
                    "color": "#A2A2A2",
                    "text-shadow": "0 1px 0 #fff",
                    "cursor": "pointer"

                })

                $('#lightbox .content').css({
                    "line-height": "30px",
                    "padding": "20px"
                })

                $('#lightbox h1.info').css({
                    "font-size": "13px",
                    "height": "100px",
                    "margin-top": "0",
                    "margin-bottom": "0px",
                    "padding-left": "100px"
                })
         
                
                $('#lightbox h1.info p').css({
                    "font-size": "13px",
                    "line-height": "15px",
                    "vertical-align": "middle",
                    "display": "inline-block",
                	"font-size": "13px",
                	"max-width": "100%",
                	"margin-bottom": "0"
                })

                if (pop.imgType) {
                    if (pop.imgType == 'success') {
                        $('#lightbox h1.info').addClass('success-img');
                    }
                    else if (pop.imgType == 'err') {
                        $('#lightbox h1.info').addClass('err-img');
                    }
                    else if (pop.imgType == 'warning'){
                        $('#lightbox h1.info').addClass('warning-img');
                    }

                }

                $('#lightbox .ctrl').css({
                    "margin-top": "0px",
                    "float": "right",
                    "margin-bottom": "0"
                })

                $('#lightbox .p').css({
                    "font-size": "14px",
                    "margin-top": "0",
                    "margin-bottom": "0"
                })

                $('input.formbutton').css({
                    "padding": "4px 1em",
                    "border": "2px solid",
                    "border-color": "#e186b1 #751b4c #751b4c #e186b1",
                    "background": "#ed145b",
                    "color": "#fff",
                    "letter-spacing": ".1em",
                    "cursor": "pointer",
                    "line-height": "17px"
                })

                $('#lightbox_payment_done').css("margin-right", "10px");

                $('#lightbox .title').prepend(pop.title);
                $('#lightbox h1.info p').text(pop.text);
                $('#lightbox_payment_done').attr("value", pop.btnLeft);
                $('#lightbox_payment_failed').attr("value", pop.btnRight);
                $('#lightbox_payment_done').click(function () {
                    pop.btnLeftCallback()
                });
                $('#lightbox_payment_failed').click(function () {
                    pop.btnRightCallback()
                });

                $('.closeBtn').click(function () {
                    $('.lightbox').remove();
                    $('#lightbox').remove();
                });
                
            };

            popOver.prototype.close = function () {
                $('.lightbox').remove();
                $('#lightbox').remove();
            }

            var popOver = new popOver(pop);

            return popOver;
        }
    });   
}

