var isVerifyCode = false;
var isSend = false;
$(function () {
  $('input,textarea').attr('autocomplete', 'off');
  var ctx = $('#ctx').val();
  var province = $('#province').val();
  var city = $('#city').val();
  var errCode = $('#errCode').val();
  var infoMap = {
	'name': '请输入你的姓名',
	'mobile': '请输入11位手机号',
	'verifyCode': '按右图填写，不区分大小写',
	'mobileVerify': '请输入6位短信验证码',
	'time': '请选择预约时间',
	'demand': '请填写详细需求'
  };
  if(typeof province != 'undefined' && province)
	$('#provinceId').select2().select2('val', province);
  else
	$('#provinceId').select2();
  
  if(typeof city != 'undefined' && city)
	$('#cityId').select2().select2('val', city);
  else
	$('#cityId').select2();
  $('#countyId').select2();
  
  if(errCode == '1001') {// 请输入正确的手机号
	  var _mobile = $('#mobile');
	  _mobile.next().find('p').html('您输入的手机号码格式有误，需为 11 位数字格式');
	  _mobile.next().find('p').css('display', ''); 
	  _mobile.parent().addClass('input-err');
  } else if(errCode == '2001') {// 验证码错误
	  var _verifyCode = $('#verifyCode');
	  _verifyCode.next().find('p').html('验证码错误');
	  _verifyCode.next().find('p').css('display', ''); 
	  _verifyCode.parent().addClass('input-err');
  } else if(errCode == '2002') {// 手机验证码错误
	  var _mobileVerify = $('#mobileVerify');
	  _mobileVerify.next().find('p').html('手机验证码错误');
	  _mobileVerify.next().find('p').css('display', ''); 
	  _mobileVerify.parent().addClass('input-err');
  }  
  
  $('#provinceId').change(function(){
	var provinceId = $(this).val();
	var provinceName = $('#provinceId option:selected').text();
	$('#provinceName').val(provinceName);
	$('#address').val(provinceName);
	$.ajax({
	    type: "post",
	    data: {
	    	provinceId: provinceId
	    },
	    url: ctx + "/common/findCities",
	    beforeSend: function () {
	        //$(".loading").show();
	    },
	    success: function (data) {
	    	$('#cityId option:gt(0)').remove();
	    	$('#countyId option:gt(0)').remove();
	    	for(var i = 0; i < data.length; i++) {
	    		var a = data[i];
	    		$('#cityId').append('<option value="' + a.id + '">' + a.name + '</option>');
	    	}
	    	$('#cityId').val('');
	    	$('#countyId').val('');
	    	$('#cityId').select2();
	    	$('#countyId').select2();
	    },
	    complete: function () {
	        //$(".loading").hide();
	    },
	    error: function (data) {
	        //log.error("error: " + data.responseText);
	    }
	});
  });

  $('#cityId').change(function(){
	var cityId = $(this).val();
	var c = $('.city-name[data-key="' + cityId + '"]');
	if(!c || typeof c[0] == 'undefined' || !c[0]) {
		alert('请所选择的城市还没开通预约功能，请尝试拨打官方客户电话或直接购物！');
		return;
	} else {
		$('.city-name').removeClass('city-select');
		c.addClass('city-select');
	}
	var cityName = $('#cityId option:selected').text();
	$('#cityName').val(cityName);
	var provinceName = $('#provinceId option:selected').text();
	$('#provinceName').val(provinceName);
	$('#address').val(provinceName+cityName);
	$.ajax({
	    type: "post",
	    data: {
	    	cityId: cityId
	    },
	    url: ctx + "/common/findCounties",
	    beforeSend: function () {
	        //$(".loading").show();
	    },
	    success: function (data) {
	    	$('#countyId option:gt(0)').remove();
	    	for(var i = 0; i < data.length; i++) {
	    		var a = data[i];
	    		$('#countyId').append('<option value="' + a.id + '">' + a.name + '</option>');
	    	}
	    	$('#countyId').val('');
	    	$('#countyId').select2();
	    },
	    complete: function () {
	        //$(".loading").hide();
	    },
	    error: function (data) {
	    }
	});
  });
  
  $('#countyId').change(function(){
	var countyName = $('#countyId option:selected').text();
	$('#countyName').val(countyName);
	var cityName = $('#cityId option:selected').text();
	$('#cityName').val(cityName);
	var provinceName = $('#provinceId option:selected').text();
	$('#provinceName').val(provinceName);
	$('#address').val(provinceName+cityName+countyName);
  });
   
  $('.option-input input,.option-input textarea').focus(function(){
	var _this = $(this);
	_this.parent().removeClass('input-err');
	var id = _this.attr('id');
	console.log(id + ',' + infoMap[id]);
	var p = _this.next().find('p');
	p.html(infoMap[id]);
	p.css('display', '');
  }).blur(function(){
	var _this = $(this);
	var id = _this.attr('id');
	var val = _this.val();
	var p = _this.next().find('p');
	p.css('display', 'none');
	_this.parent().removeClass('input-err');
	isVerifyCode = true;
	if((typeof val == 'undefined' || !val) && id != 'demand' ) {
	  if(id == 'time' && $('.datetimepicker').is(':visible')) {
		  
	  } else {
		  p.html(infoMap[id]);
		  p.css('display', '');
		  _this.parent().addClass('input-err');
	  }
	} else if(id == 'mobile') {
	  if(!val.isMobile()) {
		p.html('您输入的手机号码格式有误，需为 11 位数字格式');
		p.css('display', '');
		_this.parent().addClass('input-err');
	  }
	} else if(id == 'verifyCode') {
	  $.post(ctx + "/checkVerifyCode", {
	    verifyCode: val
      }, function (data) {
    	if(data.s == 0) {
    	  p.css('display', 'none');
    	  _this.parent().removeClass('input-err');
    	  isVerifyCode = true;
        } else {
          p.html('请按右图输入验证码，不区分大小写');
    	  p.css('display', '');
    	  _this.parent().addClass('input-err');
    	  isVerifyCode = false;
        }
      });
	} else if(id == 'mobileVerify') {
	  if(!isSend){
	    p.html('请先获得手机验证码');
	    p.css('display', '');
	    _this.parent().addClass('input-err');  
	  }
	} else {
	  p.css('display', 'none');
	  _this.parent().removeClass('input-err');
	}
  });
  var _m = $('#mobile');
  var _v = $('#verifyCode');
  var _n = $('#name');
  var t;
  var wait = 60;
  function time(o) {
    var _this = $(o);
    if (wait == 0) {
      _this.attr('disabled', false);
      _this.html('获取验证码');
      wait = 60;
    } else {
      _this.attr('disabled', true);
      _this.html(wait + '秒后重新获取');
      wait--;
      t = setTimeout(function() {
          time(o);
      },
      1000);
    }
  }
  
  $('#mobileVerifyBtn').click(function(){
	  isSend = true;
      var mobile = _m.val(), verifyCode = _v.val(), b = true, name= _n.val();
      if(name.isEmpty()) {
    	  _n.next().find('p').html('请先填写您的姓名');
    	  _n.next().find('p').css('display', '');
    	  _n.parent().addClass('input-err');
          b = false;
      }
      if(mobile.isEmpty() || !mobile.isMobile()) {
    	  _m.next().find('p').html('您输入的手机号码格式有误，需为 11 位数字格式');
    	  _m.next().find('p').css('display', '');
  		  _m.parent().addClass('input-err');
          b = false;
      }
      if(verifyCode.isEmpty() || !verifyCode.isVerifyCode() || !isVerifyCode) {
          _v.next().find('p').html('请按右图输入验证码，不区分大小写');
    	  _v.next().find('p').css('display', '');
  		  _v.parent().addClass('input-err');
          b = false;
      }
      if(b) {
      	time(this);
        $.post(ctx + "/getMcode", {
          mobile: mobile,
          verifyCode: verifyCode,
          type: "6"
        }, function (data) {
          if(data.s != 0) {
            if(data.c == 1001) {
              _m.next().find('p').html(data.m);
              _m.next().find('p').css('display', ''); 
              _m.parent().addClass('input-err');
            }
          }
        });
      }
  });
  
  $('.appointment-time-option input').datetimepicker({
  	format: 'yyyy年mm月dd日 hh:ii',
  	startDate: new Date(),
  	minuteStep: 30,
  	autoclose: true,
  	forceParse: false
  }).on('changeDate', function(ev){
	var _this = $('#time');
	var p = _this.next().find('p');
	p.css('display', 'none');
	_this.parent().removeClass('input-err');
  }); 
  
/*  $(".appointment-time-option input").focus(function(){
	  $(".datetimepicker").css("display","block");
	});*/

  //地图加载
  var map = new AMap.Map("mapContainer",{
    resizeEnable: true,
    view: new AMap.View2D({
      center:new AMap.LngLat(116.397428,39.90923),//地图中心点
      zoom:13 //地图显示的缩放级别
    }),
    keyboardEnable:false
  });

  document.getElementById("address").onkeydown = function() {
	var provinceId = $('#provinceId').val();
	var cityId = $('#cityId').val();
	var countyId = $('#countyId').val();
	if(!provinceId || !cityId) {
	  var popOver;
	  function closeAlert() {
	    popOver.close();
	  }
	  var pop = {
		title: '错误',
		imgType: "err",
		text: "请先选择省/市",
		btnLeft: "确定",
		btnRight: "取消",
		btnLeftCallback: closeAlert,
		btnRightCallback: closeAlert
	  };
	  
	  popOver = $.superAlert(pop);
      return false;
	}
  };
  
  document.getElementById("address").onkeyup = keydown;
  document.getElementById("address").onfocus = keydown;
//  $('body').click(function(e){ 
//	var _this = $(e.target);
//	var id = _this.attr('id');
//	console.log(_this.attr('id') + ',' + _this.parent().attr('id'));
//	if(id!='address') {
//		$('#result1').hide();
//	}
//	
//  });
  
  //输入提示
  function autoSearch() {
      var keywords = document.getElementById("address").value;
      var city = $('#cityName').val();
      var auto;
      //加载输入提示插件
          map.plugin(["AMap.Autocomplete"], function() {
          var autoOptions = {
              city: city //城市，默认全国
          };
          auto = new AMap.Autocomplete(autoOptions);
          //查询成功时返回查询结果
          if ( keywords.length > 0) {
              AMap.event.addListener(auto,"complete",autocomplete_CallBack);
              auto.search(keywords);
          }
          else {
              document.getElementById("result1").style.display = "none";
          }
      });
  }
  function autocomplete_CallBack(data) {
      var resultStr = "";
      var tipArr = data.tips;
      //var len=tipArr.length;
      if (tipArr&&tipArr.length>0) {                 
          for (var i = 0; i < tipArr.length; i++) {
              resultStr += "<div data-location='" + tipArr[i].location + "' data-name='" + tipArr[i].name 
              + "' data-district='" + tipArr[i].district + "' id='divid" + (i + 1) 
              + " 'style=\"font-size: 13px;cursor:pointer;padding:5px 5px 5px 5px;\">" 
              + tipArr[i].name + "<span style='color:#C1C1C1;'>("+ tipArr[i].district + ")</span></div>";
          }
      }
      else  {
          resultStr = "<div class='not-found'> π__π 亲,人家找不到结果!<br />要不试试：<br />1.请确保所有字词拼写正确<br />2.尝试不同的关键字<br />3.尝试更宽泛的关键字</div>";
      }
  
      document.getElementById("result1").curSelect = -1;
      document.getElementById("result1").tipArr = tipArr;
      document.getElementById("result1").innerHTML = resultStr;
      document.getElementById("result1").style.display = "block";
      selectResult($("#result1 div"));
  }

  function selectResult (results) {
    results.click(function () {
      var _this = $(this);
      var district = _this.attr('data-district');
      var name = _this.attr('data-name');
      var location = _this.attr('data-location');
      var text = district + name;
      //text = text.split('<')[0];
      document.getElementById("address").value = text;
      document.getElementById("location").value = location;
      document.getElementById("result1").style.display = "none";
    });
  }
    
  function focus_callback() {
      if (navigator.userAgent.indexOf("MSIE") > 0) {
          document.getElementById("address").onpropertychange = autoSearch;
      }
  }
  function keydown(event){
      var key = (event || window.event).keyCode;
      var result = document.getElementById("result1");
      var cur = result.curSelect;
      if(key===40){//down key
      if(cur + 1 < result.childNodes.length){
              if(result.childNodes[cur]){
                  result.childNodes[cur].style.background='';
              }
              result.curSelect=cur+1;
              result.childNodes[cur+1].style.background='#CAE1FF';
              document.getElementById("address").value = result.tipArr[cur+1].name;
          }
      }else if(key===38){//up key
          if(cur-1>=0){
              if(result.childNodes[cur]){
                  result.childNodes[cur].style.background='';
              }
              result.curSelect=cur-1;
              result.childNodes[cur-1].style.background='#CAE1FF';
              document.getElementById("address").value = result.tipArr[cur-1].name;
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

  $('.demand-request li img').click(function () {
    if ($(this).attr('src') == ctx + "/assets/image/check-img-yes.png") {
      $(this).attr('src', ctx + "/assets/image/check-img-no.png");
      $('.demand-request li img').removeAttr('id');
    } else {
      $('.demand-request li img').attr('src', ctx + "/assets/image/check-img-no.png");
      $('.demand-request li img').removeAttr('id');
      $(this).attr('src', ctx + "/assets/image/check-img-yes.png");
      $(this).attr('id', "request-checked");
    }   
  })

  $('.request-close').click(function () {
    $('.demand-popup-main').css("display", "none");
    $('.demand-popup').css("display", "none");
  })

  $('.demand-option .option-input textarea').click(function () {
    $('.demand-popup-main').css("display", "block");
    $('.demand-popup').css("display", "block");
    
    $('.demand-popup').css("height", $(document).height());
  })

  $('.request-submit').click(function () {
	  var text = '';
      if ($('#request-checked').next().attr('class') == 'form-control request-text') {
        text = $('#request-checked').next().val();
        $('.demand-option .option-input input').attr('value', text)
        $('.demand-popup-main').css("display", "none");
        $('.demand-popup').css("display", "none");
        $('.demand-request li img').removeAttr('id');
      } else {
        text = $('#request-checked').next().text();
        $('.demand-option .option-input input').attr('value', text);
        $('.demand-popup-main').css("display", "none");
        $('.demand-popup').css("display", "none");
        $('.demand-request li img').removeAttr('id');
      }
      $('#demand').val(text);
  });

  $('#btnSubmit').click(function(){
	  var _this = $(this);
	  var _name = $('#name');
	  var _mobile = $('#mobile');
	  var _verifyCode = $('#verifyCode');
	  var _mobileVerify = $('#mobileVerify');
	  var _time = $('#time');
	  var _province = $('#provinceId');
	  var _city = $('#cityId');
	  var _county = $('#county');
	  var _address = $('#address');
	  var _location = $('#location');
	  var isVerify = true;
	  var cityId = _city.val();
	  var c = $('.city-name[data-key="' + cityId + '"]');
	  if(!c || typeof c[0] == 'undefined' || !c[0]) {
		alert('请所选择的城市还没开通预约功能，请尝试拨打官方客户电话或直接购物！');
		return;
	  }
		
	  if(!isVerifyCode) {
		  _verifyCode.next().find('p').html('请按右图输入验证码，不区分大小写');
		  _verifyCode.next().find('p').css('display', ''); 
		  _verifyCode.parent().addClass('input-err');
		  isVerify = false;
	  }
	  var name = _name.val();
	  var mobile = _mobile.val();
	  var mobileVerify = _mobileVerify.val();
	  var time = _time.val();
	  if(!name) {
		  _name.next().find('p').html(infoMap['name']);
		  _name.next().find('p').css('display', ''); 
		  _name.parent().addClass('input-err');
		  isVerify = false;
	  }
	  if(!mobile) {
		  _mobile.next().find('p').html(infoMap['mobile']);
		  _mobile.next().find('p').css('display', ''); 
		  _mobile.parent().addClass('input-err');
		  isVerify = false;
	  }
	  if(!mobile.isMobile()) {
		  _mobile.next().find('p').html('您输入的手机号码格式有误，需为 11 位数字格式');
		  _mobile.next().find('p').css('display', ''); 
		  _mobile.parent().addClass('input-err');
		  isVerify = false;
	  }
	  if(!mobileVerify) {
		  _mobileVerify.next().find('p').html(infoMap['mobileVerify']);
		  _mobileVerify.next().find('p').css('display', ''); 
		  _mobileVerify.parent().addClass('input-err');
		  isVerify = false;
	  }
	  if(!time) {
		  _time.next().find('p').html(infoMap['time']);
		  _time.next().find('p').css('display', ''); 
		  _time.parent().addClass('input-err');
		  isVerify = false;
	  }
	  if(!_province.val() || !_city.val()) {
		  _address.next().find('p').html('请选择省/市');
		  _address.next().find('p').css('display', ''); 
		  _address.parent().addClass('input-err');
		  isVerify = false;
	  }
	  if(!_address.val() || !_location.val()) {
		  _address.next().find('p').html('请填写详细地址');
		  _address.next().find('p').css('display', ''); 
		  _address.parent().addClass('input-err');
		  isVerify = false;
	  }
	  if(isVerify){
		  //document.getElementById('bookingForm').submit();
		  $.ajax({
			    type: "post",
			    data: {
			    	name: name,
			    	mobile: mobile,
			    	verifyCode: _verifyCode.val(),
			    	mobileVerify: mobileVerify,
			    	provinceId: _province.val(),
			    	provinceName: _province.find('option:selected').text(),
			    	cityId: _city.val(),
			    	cityName: _city.find('option:selected').text(),
			    	countyId: _county.val(),
			    	countyName: _county.find('option:selected').text(),
			    	location: _location.val(),
			    	address: _address.val(),
			    	time: _time.val(),
			    	demand: $('#demand').val()
			    },
			    url: ctx + "/booking",
			    beforeSend: function () {
			        //$(".loading").show();
			    	_this.html('提交中...');
			    	_this.attr('disabled', 'disabled');
			    },
			    success: function (data) {
			    	if(data.s == 0) {
			    		//alert('预约成功');
			    		window.location.href = ctx + '/booking/success';
			    	} else {
			    		if (data.c == '1001') {//手机号错误
			    			//_mobile.focus();
			    			_mobile.next().find('p').html(data.m);
			    			_mobile.next().find('p').css('display', ''); 
			    			_mobile.parent().addClass('input-err');
			    		} else if(data.c == '2001') {// 验证码错误
			    			//_verifyCode.focus();
			    			_verifyCode.next().find('p').html(data.m);
			    			_verifyCode.next().find('p').css('display', ''); 
			    			_verifyCode.parent().addClass('input-err');
			    		} else if(data.c == '2002') {// 手机验证码错误
			    			//_mobileVerify.focus();
			    			_mobileVerify.next().find('p').html(data.m);
		    			    _mobileVerify.next().find('p').css('display', ''); 
		    			    _mobileVerify.parent().addClass('input-err');
			    		}
			    	}
			    },
			    complete: function () {
			        //$(".loading").hide();
			    	_this.html('预约');
			    	_this.removeAttr('disabled');
			    },
			    error: function (data) {
			        //log.error("error: " + data.responseText);
			    }
			});
			
	  }
  });
});