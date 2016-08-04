 $(function () {
	 var ctx = $("#ctx").val();
	 var ids = $("#ids").val();
	 $(".go-to-settlement").click(function(){
		window.location.href =  ctx + "/wx/prepay?ids=" + ids + "&addressId=6&payCode=alipayescow&remark=11111";
	 });
 });