<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page session="false" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
</head>

<body>
<s:form action="memcache" namespace="/manage" method="post" theme="simple">
			<table class="table table-bordered">
				<tr>
					<td colspan="4">
						<button method="memcache!flushMemcache.action" class="btn btn-danger" onclick="return submitIDs(this,'确定刷新选择的记录缓存?');">
							<i class="icon-remove-sign icon-white"></i> 刷新
						</button>
					</td>
				</tr>
			</table>
			
			<div class="alert alert-info">
				注意：图片尺寸请尽量保持在630*180，否则超出的部分会显示不出来。
			</div>
			
			<table class="table table-bordered">
				<tr style="background-color: #dff0d8">
					<th width="20"><input type="checkbox" id="firstCheckbox"/></th>
					<th style="display: none;">id</th>
					<th>缓存对象</th>
					<th>描述</th>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="1"/></td>
					<td>&nbsp;产品缓存</td>
					<td>&nbsp;刷新所有产品缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="2"/></td>
					<td>&nbsp;活动产品缓存</td>
					<td>&nbsp;刷新所有有活动的产品缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="3"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;产品属性</td>
					<td>&nbsp;刷新有是有产品的属性缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="4"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;产品参数</td>
					<td>&nbsp;刷新所有产品的参数缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="5"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;热门推荐产品</td>
					<td>&nbsp;刷新热门推荐产品缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="6"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;热门产品</td>
					<td>&nbsp;刷新热门产品缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="7"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;最新产品</td>
					<td>&nbsp;刷新最新产品缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="8"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;首页图缓存</td>
					<td>&nbsp;刷新首页图缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="9"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;目录缓存</td>
					<td>&nbsp;刷新目录缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="10"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;账户缓存</td>
					<td>&nbsp;刷新所有账户缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="11"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;赠品缓存</td>
					<td>&nbsp;刷新所有赠品缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="12"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;活动缓存</td>
					<td>&nbsp;刷新所有活动缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="13"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;键值缓存</td>
					<td>&nbsp;刷新所有键值缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="14"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;通知模板缓存</td>
					<td>&nbsp;刷新所有通模板缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="15"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;广告缓存</td>
					<td>&nbsp;刷新所有广告缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="16"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;地址缓存</td>
					<td>&nbsp;刷新所有地址缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="17"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;地区缓存</td>
					<td>&nbsp;刷新所有地区缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="18"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;快递缓存</td>
					<td>&nbsp;刷新所有快递缓存</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="ids" value="19"/></td>
					<td style="display: none;">&nbsp;</td>
					<td>&nbsp;支付方式缓存</td>
					<td>&nbsp;刷新所有支付缓存</td>
				</tr>
			</table>
</s:form>
</body>
</html>
