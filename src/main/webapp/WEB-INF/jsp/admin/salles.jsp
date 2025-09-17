<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Admin - Salles" />
<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
<h3 class="mb-3">Salles</h3>
<a
  class="btn btn-success mb-3"
  href="${pageContext.request.contextPath}/admin/salles?action=edit&id=0"
  >Ajouter une salle</a
>
<div class="card shadow-sm">
  <div class="card-body p-0">
    <div class="table-responsive">
      <table class="table table-striped table-hover mb-0 align-middle">
        <thead class="table-light">
          <tr>
            <th>Nom</th>
            <th>Rangées</th>
            <th>Sièges/rangée</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${salles}" var="s">
            <tr>
              <td>${s.name}</td>
              <td>${s.numRows}</td>
              <td>${s.seatsPerRow}</td>
              <td>
                <a
                  class="btn btn-sm btn-primary"
                  href="${pageContext.request.contextPath}/admin/salles?action=edit&id=${s.id}"
                  >Éditer</a
                >
                <form
                  method="post"
                  action="${pageContext.request.contextPath}/admin/salles"
                  style="display: inline"
                >
                  <input type="hidden" name="action" value="delete" />
                  <input type="hidden" name="id" value="${s.id}" />
                  <button
                    class="btn btn-sm btn-danger"
                    type="submit"
                    onclick="return confirm('Confirmer la suppression?');"
                  >
                    Supprimer
                  </button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
