$('#choice-city-input').click(function () {
    $('#choice-city-input').blur();
    $('#choice-city-pain').animate({left: "0px"}, 300, function () {
        $('.index-pain').addClass('pain-close');
    })
})

$('.choice-city-title img').click(function () {
    $('.index-pain').removeClass('pain-close');
    $('#choice-city-pain').animate({left: "100%"}, 300)
})

$('#Detailed-city').click(function () {
    $('#Detailed-city').blur();
})

var windowsArr = [];
var marker = [];
var mapObj = new AMap.Map("mapContainer", {
    resizeEnable: true,
    view: new AMap.View2D({
        resizeEnable: true,
        center:new AMap.LngLat(116.397428,39.90923),//地图中心点
        zoom:13//地图显示的缩放级别
    }),
    keyboardEnable:false
});
    
// document.getElementById("keyword").onkeyup = keydown;
//输入提示
//

$('#keyword').bind('input', function(){
        keydown();
      })

function autoSearch() {
    var keywords = document.getElementById("keyword").value;
    var auto;
    //加载输入提示插件
        AMap.service(["AMap.Autocomplete"], function() {
        var autoOptions = {
            city: "" //城市，默认全国
        };
        auto = new AMap.Autocomplete(autoOptions);
        //查询成功时返回查询结果
        if ( keywords.length > 0) {
            auto.search(keywords, function(status, result){
                autocomplete_CallBack(result);
            });
        }
        else {
            document.getElementById("result1").style.display = "none";
        }
    });
}
 
//输出输入提示结果的回调函数
function autocomplete_CallBack(data) {
    var resultStr = "";
    var tipArr = data.tips;
    if (tipArr&&tipArr.length>0) {                 
        for (var i = 0; i < tipArr.length; i++) {
            resultStr += "<div class='result-list' id='divid" + (i + 1) + "' onmouseover='openMarkerTipById(" + (i + 1)
                        + ",this)' onclick='selectResult(" + i + ")' onmouseout='onmouseout_MarkerStyle(" + (i + 1)
                        + ",this)' style=\"font-size: 13px;cursor:pointer;padding:5px 0px 5px 0px;\"" + "data=" + tipArr[i].adcode + ">" + tipArr[i].name + "<span style='color:#C1C1C1;'>"+ tipArr[i].district + "</span></div>";
        }
    }
    else  {
        resultStr = " π__π 亲,人家找不到结果!<br />要不试试：<br />1.请确保所有字词拼写正确<br />2.尝试不同的关键字<br />3.尝试更宽泛的关键字";
    }
    document.getElementById("result1").curSelect = -1;
    document.getElementById("result1").tipArr = tipArr;
    document.getElementById("result1").innerHTML = resultStr;
    document.getElementById("result1").style.display = "block";
}
 
//输入提示框鼠标滑过时的样式
function openMarkerTipById(pointid, thiss) {  //根据id打开搜索结果点tip 
    thiss.style.background = '#CAE1FF';
}
 
//输入提示框鼠标移出时的样式
function onmouseout_MarkerStyle(pointid, thiss) {  //鼠标移开后点样式恢复 
    thiss.style.background = "";
}
 
//从输入提示框中选择关键字并查询
function selectResult(index) {
    if(index<0){
        return;
    }
    if (navigator.userAgent.indexOf("MSIE") > 0) {
        document.getElementById("keyword").onpropertychange = null;
        document.getElementById("keyword").onfocus = focus_callback;
    }
    //截取输入提示的关键字部分
    var text = document.getElementById("divid" + (index + 1)).innerHTML.replace(/<[^>].*?>.*<\/[^>].*?>/g,"");
    var cityCode = document.getElementById("divid" + (index + 1)).getAttribute('data');
    document.getElementById("keyword").value = text;
    document.getElementById("result1").style.display = "none";
    //根据选择的输入提示关键字查询
    mapObj.plugin(["AMap.PlaceSearch"], function() {       
        var msearch = new AMap.PlaceSearch();  //构造地点查询类
        AMap.event.addListener(msearch, "complete", placeSearch_CallBack); //查询成功时的回调函数
        msearch.setCity(cityCode);
        msearch.search(text);  //关键字查询查询
    });
}
 
//定位选择输入提示关键字
function focus_callback() {
    if (navigator.userAgent.indexOf("MSIE") > 0) {
        document.getElementById("keyword").onpropertychange = autoSearch;
   }
}
 
//输出关键字查询结果的回调函数
function placeSearch_CallBack(data) {
    //清空地图上的InfoWindow和Marker
    windowsArr = [];
    marker     = [];
    mapObj.clearMap();
    var resultStr1 = "";
    var poiArr = data.poiList.pois;
    var resultCount = poiArr.length;
    for (var i = 0; i < resultCount; i++) {
        resultStr1 += "<div id='divid" + (i + 1) + "' onmouseover='openMarkerTipById1(" + i + ",this)' onmouseout='onmouseout_MarkerStyle(" + (i + 1) + ",this)' style=\"font-size: 12px;cursor:pointer;padding:0px 0 4px 2px; border-bottom:1px solid #C1FFC1;\"><table><tr><td><img src=\"http://webapi.amap.com/images/" + (i + 1) + ".png\"></td>" + "<td><h3><font color=\"#00a6ac\">名称: " + poiArr[i].name + "</font></h3>";
            resultStr1 += TipContents(poiArr[i].type, poiArr[i].address, poiArr[i].tel) + "</td></tr></table></div>";
            addmarker(i, poiArr[i]);
    }
    mapObj.setFitView();
}
 
//鼠标滑过查询结果改变背景样式，根据id打开信息窗体
function openMarkerTipById1(pointid, thiss) {
    thiss.style.background = '#CAE1FF';
    windowsArr[pointid].open(mapObj, marker[pointid]);
}
 
//添加查询结果的marker&infowindow   
function addmarker(i, d) {
    var lngX = d.location.getLng();
    var latY = d.location.getLat();
    var markerOption = {
        map:mapObj,
        icon:"http://webapi.amap.com/images/" + (i + 1) + ".png",
        position:new AMap.LngLat(lngX, latY)
    };
    var mar = new AMap.Marker(markerOption);         
    marker.push(new AMap.LngLat(lngX, latY));
 
    var infoWindow = new AMap.InfoWindow({
        content:"<h3><font color=\"#00a6ac\">  " + (i + 1) + ". " + d.name + "</font></h3>" + TipContents(d.type, d.address, d.tel),
        size:new AMap.Size(300, 0),
        autoMove:true, 
        offset:new AMap.Pixel(0,-30)
    });
    windowsArr.push(infoWindow);
    var aa = function (e) {infoWindow.open(mapObj, mar.getPosition());};
    AMap.event.addListener(mar, "mouseover", aa);
}
 
//infowindow显示内容
function TipContents(type, address, tel) {  //窗体内容
    var str = "  地址：" + address||"暂无" + "<br />  电话：" + tel||"暂无" + " <br />  类型：" + type||"暂无";
    return str;
}
function keydown(event){
    var key = (event||window.event).keyCode;
    var result = document.getElementById("result1")
    var cur = result.curSelect;
    if(key===40){//down
        if(cur + 1 < result.childNodes.length){
            if(result.childNodes[cur]){
                result.childNodes[cur].style.background='';
            }
            result.curSelect=cur+1;
            result.childNodes[cur+1].style.background='#CAE1FF';
            document.getElementById("keyword").value = result.tipArr[cur+1].name;
        }
    }else if(key===38){//up
        if(cur-1>=0){
            if(result.childNodes[cur]){
                result.childNodes[cur].style.background='';
            }
            result.curSelect=cur-1;
            result.childNodes[cur-1].style.background='#CAE1FF';
            document.getElementById("keyword").value = result.tipArr[cur-1].name;
        }
    }else if(key === 13){
        var res = document.getElementById("result1");
        if(res && res['curSelect'] !== -1){
            selectResult(document.getElementById("result1").curSelect);
        }
    }else{
        autoSearch();
    }
}

$('.choice-city-detail-submit-btn').click(function () {
    var city = $("#keyword").val();
    $('#Detailed-city').val(city);
})

$.fn.datetimepicker.dates['zh-CN'] = {
  days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
  daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
  daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
  months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
  monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
  today: "今天",
  suffix: [],
  meridiem: ["上午", "下午"]
};

$('#datetimepicker').datetimepicker({
  format: 'yyyy-mm-dd hh:ii',
  language:  'zh-CN',
  autoclose: true,
  startDate: new Date(),
  minuteStep: 30
});

// $('.datetimepicker').css({
//     "left": "50%",
//     "margin-left": "-119px",
//     "top": "50%",
//     "margin-top": "-142px" 
// })

$('#datetimepicker').click(function () {
    $('.datetimepicker-background').css('display', 'block');
    $(this).blur();
    // $('.datetimepicker').css({
    //     "left": "50%",
    //     "margin-left": "-119px",
    //     "top": "50%",
    //     "margin-top": "-142px",
    //     "position": "fixed",
    //     "z-index": "999"
    // })
})

$('.datetimepicker-background').click(function () {
    if ($('.datetimepicker').css("display")  == 'none') {
        $('.datetimepicker-background').css('display', 'none');
    } else {
        $('.datetimepicker-background').css('display', 'block');
    }
})