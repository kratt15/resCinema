<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Admin" />
<jsp:include page="/WEB-INF/jsp/layout/header.jspf" />
<h3 class="mb-3">Administration</h3>
<div class="row g-3">
  <div class="col-12 col-lg-4">
    <div class="card shadow-sm h-100">
      <div class="card-body">
        <h5 class="card-title">Films</h5>
        <ul class="list-group list-group-flush">
          <c:forEach items="${films}" var="f">
            <li
              class="list-group-item d-flex justify-content-between align-items-center"
            >
              <span>${f.title}</span>
              <span class="badge text-bg-secondary"
                >${f.durationMinutes} min</span
              >
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
  <div class="col-12 col-lg-4">
    <div class="card shadow-sm h-100">
      <div class="card-body">
        <h5 class="card-title">Salles</h5>
        <ul class="list-group list-group-flush">
          <c:forEach items="${salles}" var="s">
            <li class="list-group-item">
              ${s.name} - ${s.numRows}x${s.seatsPerRow}
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
  <div class="col-12 col-lg-4">
    <div class="card shadow-sm h-100">
      <div class="card-body">
        <h5 class="card-title">Séances</h5>
        <div class="table-responsive">
          <table class="table table-sm align-middle">
            <thead class="table-light">
              <tr>
                <th>Film</th>
                <th>Salle</th>
                <th>Début</th>
                <th>Prix</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${seances}" var="x">
                <tr>
                  <td>${x.film.title}</td>
                  <td>${x.salle.name}</td>
                  <td>
                    <c:choose
                      ><c:when test="${not empty dtf}"
                        >${dtf.format(x.startTime.toInstant())}</c:when
                      ><c:otherwise>${x.startTime}</c:otherwise></c:choose
                    >
                  </td>
                  <td>${x.price}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/layout/footer.jspf" />
