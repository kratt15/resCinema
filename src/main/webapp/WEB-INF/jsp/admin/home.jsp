<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Admin</title>
  </head>
  <body>
    <h2>Admin - Films</h2>
    <ul>
      <c:forEach items="${films}" var="f">
        <li>${f.title} (${f.durationMinutes} min)</li>
      </c:forEach>
    </ul>

    <h2>Salles</h2>
    <ul>
      <c:forEach items="${salles}" var="s">
        <li>${s.name} - ${s.numRows}x${s.seatsPerRow}</li>
      </c:forEach>
    </ul>

    <h2>Séances</h2>
    <table border="1" cellpadding="6">
      <tr>
        <th>Film</th>
        <th>Salle</th>
        <th>Début</th>
        <th>Prix</th>
      </tr>
      <c:forEach items="${seances}" var="x">
        <tr>
          <td>${x.film.title}</td>
          <td>${x.salle.name}</td>
          <td>${x.startTime}</td>
          <td>${x.price}</td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
