<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- <%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %> --%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%-- <c:set var="ctxStatic" value="http://static.baoxiliao.com/static/v1"/> --%>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxStaticJS" value="${pageContext.request.contextPath}/static"/>
<%-- <c:set var="ctxAssets" value="http://static.baoxiliao.com/assets/v1"/> --%>
<c:set var="ctxAssets" value="${pageContext.request.contextPath}/assets"/>
<c:set var="ctxAssetsJS" value="${pageContext.request.contextPath}/assets"/>