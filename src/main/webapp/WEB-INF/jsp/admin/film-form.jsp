<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Admin - Éditer Film" />
<c:set var="isNew" value="${empty film || film.id == 0}" />
<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
<h3 class="mb-3">${isNew ? 'Ajouter' : 'Éditer'} un film</h3>
<form method="post" action="${pageContext.request.contextPath}/admin/films">
  <input type="hidden" name="action" value="${isNew ? 'create' : 'update'}" />
  <input type="hidden" name="id" value="${film.id}" />
  <div class="mb-3">
    <label class="form-label">Titre</label>
    <input name="title" class="form-control" value="${film.title}" required />
  </div>
  <div class="mb-3">
    <label class="form-label">Description</label>
    <textarea name="description" class="form-control">
${film.description}</textarea
    >
  </div>
  <div class="mb-3">
    <label class="form-label">Durée (minutes)</label>
    <input
      name="duration"
      type="number"
      class="form-control"
      value="${film.durationMinutes}"
      required
    />
  </div>
  <div class="d-grid gap-2 d-sm-flex justify-content-sm-end">
    <a
      class="btn btn-outline-secondary"
      href="${pageContext.request.contextPath}/admin/films"
      >Annuler</a
    >
    <button class="btn btn-primary" type="submit">Enregistrer</button>
  </div>
</form>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
