<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Admin - Éditer Salle" />
<c:set var="isNew" value="${empty salle || salle.id == 0}" />
<jsp:include page="/WEB-INF/jsp/layout/header.jspf" />
<h3 class="mb-3">${isNew ? 'Ajouter' : 'Éditer'} une salle</h3>
<form method="post" action="${pageContext.request.contextPath}/admin/salles">
  <input type="hidden" name="action" value="${isNew ? 'create' : 'update'}" />
  <input type="hidden" name="id" value="${salle.id}" />
  <div class="mb-3">
    <label class="form-label">Nom</label>
    <input name="name" class="form-control" value="${salle.name}" required />
  </div>
  <div class="mb-3">
    <label class="form-label">Nombre de rangées</label>
    <input
      name="numRows"
      type="number"
      class="form-control"
      value="${salle.numRows}"
      required
    />
  </div>
  <div class="mb-3">
    <label class="form-label">Sièges par rangée</label>
    <input
      name="seatsPerRow"
      type="number"
      class="form-control"
      value="${salle.seatsPerRow}"
      required
    />
  </div>
  <div class="d-grid gap-2 d-sm-flex justify-content-sm-end">
    <a
      class="btn btn-outline-secondary"
      href="${pageContext.request.contextPath}/admin/salles"
      >Annuler</a
    >
    <button class="btn btn-primary" type="submit">Enregistrer</button>
  </div>
</form>
<jsp:include page="/WEB-INF/jsp/layout/footer.jspf" />
