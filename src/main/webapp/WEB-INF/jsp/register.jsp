<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Inscription" />
<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
<div class="row justify-content-center">
  <div class="col-12 col-md-6 col-lg-4">
    <div class="card shadow-sm">
      <div class="card-body">
        <h4 class="card-title mb-3">Inscription</h4>
        <c:if test="${not empty error}"
          ><div class="alert alert-danger">${error}</div></c:if
        >
        <form method="post">
          <div class="mb-3">
            <label class="form-label">Email</label>
            <input name="email" type="email" class="form-control" required />
          </div>
          <div class="mb-3">
            <label class="form-label">Mot de passe</label>
            <input
              name="password"
              type="password"
              class="form-control"
              required
            />
          </div>
          <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary">
              Créer un compte
            </button>
          </div>
        </form>
        <div class="mt-3 text-center">
          <a href="${pageContext.request.contextPath}/login">Se connecter</a>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
