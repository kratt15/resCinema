<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Mes réservations" />
<jsp:include page="/WEB-INF/jsp/layout/header.jspf" />
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
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${reservations}" var="r">
            <tr>
              <td>${r.seance.film.title}</td>
              <td>${r.seance.salle.name}</td>
              <td>${dtf.format(r.seance.startTime.toInstant())}</td>
              <td><span class="badge text-bg-info">${r.status}</span></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/layout/footer.jspf" />
