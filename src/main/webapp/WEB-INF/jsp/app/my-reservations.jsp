<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Mes réservations" />
<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
<h3 class="mb-3">Mes réservations</h3>
<div class="card shadow-sm">
  <div class="card-body p-0">
    <div class="table-responsive">
      <table class="table table-striped table-hover mb-0 align-middle">
        <thead class="table-light">
          <tr>
            <th>Film</th>
            <th>Salle</th>
            <th>Début</th>
            <th>Statut</th>
            <th style="width: 1%"></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${reservations}" var="r">
            <tr>
              <td>${r.seance.film.title}</td>
              <td>${r.seance.salle.name}</td>
              <td>${dtf.format(r.seance.startTime.toInstant())}</td>
              <td><span class="badge text-bg-info">${r.status}</span></td>
              <td class="text-end">
                <a
                  class="btn btn-sm btn-outline-primary me-1"
                  href="${pageContext.request.contextPath}/app/reservation/edit?id=${r.id}"
                  >Modifier</a
                >
                <form
                  method="post"
                  action="${pageContext.request.contextPath}/app/reservation/cancel"
                  style="display: inline"
                >
                  <input type="hidden" name="id" value="${r.id}" />
                  <button
                    class="btn btn-sm btn-outline-danger"
                    type="submit"
                    onclick="return confirm('Annuler cette réservation ?');"
                  >
                    Annuler
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
