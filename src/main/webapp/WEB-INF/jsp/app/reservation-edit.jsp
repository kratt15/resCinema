<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Modifier réservation" />
<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
<h3 class="mb-3">Modifier la réservation</h3>
<c:if test="${not empty error}"
  ><div class="alert alert-danger">${error}</div></c:if
>
<form
  method="post"
  action="${pageContext.request.contextPath}/app/reservation/edit"
>
  <input type="hidden" name="reservationId" value="${reservationId}" />
  <input type="hidden" name="seanceId" value="${seanceId}" />
  <div class="card shadow-sm mb-3">
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-bordered seat-grid">
          <tbody>
            <c:forEach begin="1" end="${rows}" var="r">
              <tr>
                <c:forEach begin="1" end="${seatsPerRow}" var="c">
                  <td>
                    <c:set var="key" value="${r}-${c}" />
                    <c:choose>
                      <c:when test="${occupiedKeys.contains(key)}">
                        <span class="badge text-bg-secondary">Occupée</span>
                      </c:when>
                      <c:otherwise>
                        <div class="form-check">
                          <input class="form-check-input" type="checkbox"
                          name="seats" value="${r}-${c}" id="seat_${r}_${c}"
                          ${selectedKeys.contains(key) ? 'checked' : ''}>
                          <label class="form-check-label" for="seat_${r}_${c}"
                            >${r}-${c}</label
                          >
                        </div>
                      </c:otherwise>
                    </c:choose>
                  </td>
                </c:forEach>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <div class="d-grid gap-2 d-sm-flex justify-content-sm-end">
    <a
      class="btn btn-outline-secondary"
      href="${pageContext.request.contextPath}/app/mes-reservations"
      >Annuler</a
    >
    <button class="btn btn-primary" type="submit">Enregistrer</button>
  </div>
</form>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
