<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Admin" />
<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
<h3 class="mb-3">Administration</h3>
<div class="row g-3">
  <div class="col-12 col-md-4">
    <div class="card shadow-sm text-center">
      <div class="card-body">
        <h5 class="card-title">Gestion Films</h5>
        <a
          href="${pageContext.request.contextPath}/admin/films"
          class="btn btn-primary"
          >Accéder</a
        >
      </div>
    </div>
  </div>
  <div class="col-12 col-md-4">
    <div class="card shadow-sm text-center">
      <div class="card-body">
        <h5 class="card-title">Gestion Salles</h5>
        <a
          href="${pageContext.request.contextPath}/admin/salles"
          class="btn btn-primary"
          >Accéder</a
        >
      </div>
    </div>
  </div>
  <div class="col-12 col-md-4">
    <div class="card shadow-sm text-center">
      <div class="card-body">
        <h5 class="card-title">Gestion Séances</h5>
        <a
          href="${pageContext.request.contextPath}/admin/seances"
          class="btn btn-primary"
          >Accéder</a
        >
      </div>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
