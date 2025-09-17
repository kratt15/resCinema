<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Admin - Films" />
<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
<h3 class="mb-3">Films</h3>
<a
  class="btn btn-success mb-3"
  href="${pageContext.request.contextPath}/admin/films?action=edit&id=0"
  >Ajouter un film</a
>
<div class="card shadow-sm">
  <div class="card-body p-0">
    <div class="table-responsive">
      <table class="table table-striped table-hover mb-0 align-middle">
        <thead class="table-light">
          <tr>
            <th>Titre</th>
            <th>Description</th>
            <th>Durée</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${films}" var="f">
            <tr>
              <td>${f.title}</td>
              <td>${f.description}</td>
              <td>${f.durationMinutes} min</td>
              <td>
                <a
                  class="btn btn-sm btn-primary"
                  href="${pageContext.request.contextPath}/admin/films?action=edit&id=${f.id}"
                  >Éditer</a
                >
                <form
                  method="post"
                  action="${pageContext.request.contextPath}/admin/films"
                  style="display: inline"
                >
                  <input type="hidden" name="action" value="delete" />
                  <input type="hidden" name="id" value="${f.id}" />
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
