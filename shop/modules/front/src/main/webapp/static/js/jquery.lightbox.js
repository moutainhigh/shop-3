(function(win, doc, $){

	var msie = document.querySelector ? doc.documentMode : ("XMLHttpRequest" in win ? 7 : 6);

	function css(element, css){
		for(var i in css){
			try{
				element.style[i] = css[i];
			} catch(ex){}
		}
		return element;
	}

	function create(tag, cssobj) {
		return css(doc.createElement(tag), cssobj);
	}

	function currStyle(tag) {
		return tag.currentStyle || {};
	}

	function lightbox(html){
		var	table = create("section", {
				position: "fixed",
				display: "table",
				height: "100%",
				width: "100%",
				left: "-300%",
				top: 0
			}),
			cell = create("div", {
				verticalAlign: "middle",
				display: "table-cell",
				textAlign: "center"
			}),
			dialog = create("dialog", {
				display: "inline-block",
				position: "relative",
				textAlign: "left"
			});

		table.className = "lightbox";
		table.appendChild(cell);
		cell.appendChild(dialog);

		//IE6，IE7下对话框垂直居中
		if(msie){
			if( msie < 8 ){
				css(cell, {
					verticalAlign: "baseline",
					position: "absolute",
					display: "block",
					width: "100%",
					top: "50%",
					left: 0
				});
				css(dialog, {
					top: "-50%"
				});

				//IE6下跟随滚动
				if( msie < 7 ) {

					//IE6 hack position: "fixed"
					(function(html){
						//IE6 hack position: "fixed"
						if(currStyle(html).backgroundImage === 'none'){
							css(html, {
								backgroundImage: "url(about:blank)",
								backgroundAttachment: "fixed",
								backgroundRepeat: "no-repeat"
							});
						}
					})(doc.documentElement);

					css(table, {
						position: "absolute" 
					});

					function expre(prop, value) {
						table.style.setExpression(prop, "offsetParent." + value);
					}

					setTimeout(function(){
						expre("height", "clientHeight");
						expre("width", "clientWidth");
						expre("top", "scrollTop");
					},1);
				}
			} else if (currStyle(table).backgroundColor !== "transparent") {
				//避免IE9下背景色和滤镜同时生效
				try{
					table.filters.item('DXImageTransform.Microsoft.Gradient').Enabled = false;
				}catch(ex){}
			}
		}

		//填充对话框内容
		if( html ){
			if( html.jquery ){
				html.appendTo(dialog);
			}else if(typeof html === "string"){
				dialog.innerHTML = html || "";
			} else {
				dialog.appendChild(html);
			}
		}

		if(msie < 7){
			//IE6下用就是方式支持max-width、min-width、修正宽度自动计算不正确的问题
			table.onresize = function ie6resize(){
				setRuntimeStyle("");
				//IE6 hack max-width\min-width IE6下对话框宽度自动计算
				var currentStyle = currStyle(dialog),
					maxWidth = currentStyle['max-width'],
					minWidth = currentStyle['min-width'],
					width = currentStyle.width,
					pixPaddingWidth,
					scrollHeight,
					pixMaxWidth,
					pixMinWidth;
	
				function getClientWidth(){
					return dialog.clientWidth;
				}

				function getScrollWidth(){
					return dialog.scrollWidth;
				}
				function setRuntimeStyle(val, name) {
					dialog.runtimeStyle[name || "width"] = val;
				}
	
				if(/auto/i.test(width)){
					setRuntimeStyle("hidden", "overflow");
					setRuntimeStyle(0);
					pixPaddingWidth = getClientWidth();

					setRuntimeStyle(!minWidth || /auto/i.test(minWidth) ? 0 : minWidth);
					pixMinWidth = Math.max(getClientWidth(), getScrollWidth()) - pixPaddingWidth;

					setRuntimeStyle(maxWidth || "auto");
					pixMaxWidth = getClientWidth() - pixPaddingWidth;
					scrollHeight = dialog.scrollHeight;

					//二分法查找正确的宽度
					while(pixMaxWidth - pixMinWidth > 1){
						width = Math.round((pixMaxWidth - pixMinWidth) / 2) + pixMinWidth;
						setRuntimeStyle(width);
						if((getScrollWidth() > getClientWidth()) || (dialog.scrollHeight > scrollHeight)){
							pixMinWidth = width;
						} else {
							pixMaxWidth = width;
						}
					};
					setRuntimeStyle(pixMaxWidth);
				} else {
					setRuntimeStyle("", "overflow");
				}
			};
		}

		if($){
			$(".profile").add(doc.body || doc.documentElement).last().append(table);
		}else{
			(doc.body || doc.documentElement).appendChild(table);
		}

		return {
			show: function(){
				return this.toggle(true);
			},
			hide: function(){
				return this.toggle(false);
			},
			toggle: function(on) {
				css(table, {
					left: on ? 0 : "-300%"
				});
				return this;
			},
			dialog: dialog,
			lightbox: table
		};
	}

	win.lightbox = lightbox;

	if($) {
		$.fn.extend({
			lightbox: function(toggle){
				return lightbox(this);
			},
			dialog: function(opt){
				var dia = $('<div class="dialog">').html('<h2>' + (this.attr("title") || "&nbsp;") +'<a class="close" href="#">X</a></h2>'),
					box = lightbox(dia.append(this));
				$(box.dialog).delegate(".close", "click", function(){
					box.hide();
					return false;
				});
				return box;
			}
		});
	}

})(this, this.document, this.jQuery);