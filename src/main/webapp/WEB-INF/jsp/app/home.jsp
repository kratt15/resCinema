<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Séances" />
<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
<h3 class="mb-3">Séances</h3>
<form method="get" action="${pageContext.request.contextPath}/app/home" class="row g-3 mb-3">
  <div class="col-sm-6 col-md-4">
    <label class="form-label">Film</label>
    <select name="filmId" class="form-select">
      <option value="">Tous</option>
      <c:forEach items="${films}" var="f">
        <option value="${f.id}" ${selectedFilmId == f.id ? 'selected' : ''}>${f.title}</option>
      </c:forEach>
    </select>
  </div>
  <div class="col-sm-6 col-md-4">
    <label class="form-label">Date</label>
    <input type="date" name="date" class="form-control" value="${selectedDate}"/>
  </div>
  <div class="col-12 col-md-4 d-flex align-items-end">
    <button class="btn btn-primary me-2" type="submit">Rechercher</button>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/app/home">Réinitialiser</a>
  </div>
  <div class="col-12"><hr/></div>
</form>
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
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
