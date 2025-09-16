<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Séance</title>
  </head>
  <body>
    <h2>Sélection des places</h2>
    <c:if test="${not empty error}"
      ><div style="color: red">${error}</div></c:if
    >
    <form
      method="post"
      action="${pageContext.request.contextPath}/app/reserver"
    >
      <input type="hidden" name="seanceId" value="${seanceId}" />
      <table border="1" cellpadding="4">
        <c:forEach begin="1" end="${rows}" var="r">
          <tr>
            <c:forEach begin="1" end="${seatsPerRow}" var="c">
              <td>
                <c:set var="key" value="${r}-${c}" />
                <c:choose>
                  <c:when test="${occupiedKeys.contains(key)}">
                    Occupée
                  </c:when>
                  <c:otherwise>
                    <label
                      ><input type="checkbox" name="seats" value="${r}-${c}" />
                      ${r}-${c}</label
                    >
                  </c:otherwise>
                </c:choose>
              </td>
            </c:forEach>
          </tr>
        </c:forEach>
      </table>
      <button type="submit">Valider la réservation</button>
    </form>
  </body>
</html>
