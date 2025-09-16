<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Mes réservations</title>
  </head>
  <body>
    <h2>Mes réservations</h2>
    <table border="1" cellpadding="6">
      <tr>
        <th>Film</th>
        <th>Salle</th>
        <th>Début</th>
        <th>Statut</th>
      </tr>
      <c:forEach items="${reservations}" var="r">
        <tr>
          <td>${r.seance.film.title}</td>
          <td>${r.seance.salle.name}</td>
          <td>${r.seance.startTime}</td>
          <td>${r.status}</td>
        </tr>
      </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/app/home">Retour</a>
  </body>
</html>
