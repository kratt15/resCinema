<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Admin - Éditer Séance"/>
<c:set var="isNew" value="${empty seance || seance.id == 0}"/>
<jsp:include page="/WEB-INF/jsp/layout/header.jspf"/>
<h3 class="mb-3">${isNew ? 'Ajouter' : 'Éditer'} une séance</h3>
<form method="post" action="${pageContext.request.contextPath}/admin/seances">
  <input type="hidden" name="action" value="${isNew ? 'create' : 'update'}"/>
  <input type="hidden" name="id" value="${seance.id}"/>
  <div class="mb-3">
    <label class="form-label">Film</label>
    <select name="filmId" class="form-select" required>
      <c:forEach items="${films}" var="f">
        <option value="${f.id}" ${seance.film.id == f.id ? 'selected' : ''}>${f.title}</option>
      </c:forEach>
    </select>
  </div>
  <div class="mb-3">
    <label class="form-label">Salle</label>
    <select name="salleId" class="form-select" required>
      <c:forEach items="${salles}" var="s">
        <option value="${s.id}" ${seance.salle.id == s.id ? 'selected' : ''}>${s.name}</option>
      </c:forEach>
    </select>
  </div>
  <div class="mb-3">
    <label class="form-label">Début (ISO)</label>
    <input name="startTime" type="datetime-local" class="form-control" value="${seance.startTime}" required/>
  </div>
  <div class="mb-3">
    <label class="form-label">Prix</label>
    <input name="price" type="number" step="0.01" class="form-control" value="${seance.price}" required/>
  </div>
  <div class="d-grid gap-2 d-sm-flex justify-content-sm-end">
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/seances">Annuler</a>
    <button class="btn btn-primary" type="submit">Enregistrer</button>
  </div>
</form>
<jsp:include page="/WEB-INF/jsp/layout/footer.jspf"/>
