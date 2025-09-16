<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Séances" />
<jsp:include page="/WEB-INF/jsp/layout/header.jspf" />
<h3 class="mb-3">Séances du jour</h3>
<div class="card shadow-sm">
  <div class="card-body p-0">
    <div class="table-responsive">
      <table class="table table-striped table-hover mb-0 align-middle">
        <thead class="table-light">
          <tr>
            <th>Film</th>
            <th>Salle</th>
            <th>Début</th>
            <th style="width: 1%"></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${seances}" var="s">
            <tr>
              <td>${s.film.title}</td>
              <td>${s.salle.name}</td>
              <td>${dtf.format(s.startTime.toInstant())}</td>
              <td class="text-end">
                <a
                  class="btn btn-sm btn-primary"
                  href="${pageContext.request.contextPath}/app/seance?id=${s.id}"
                  >Réserver</a
                >
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/layout/footer.jspf" />
