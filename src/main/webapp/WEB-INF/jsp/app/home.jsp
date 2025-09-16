<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Accueil</title>
  </head>
  <body>
    <h2>Séances du jour</h2>
    <table border="1" cellpadding="6">
      <tr>
        <th>Film</th>
        <th>Salle</th>
        <th>Début</th>
        <th>Action</th>
      </tr>
      <c:forEach items="${seances}" var="s">
        <tr>
          <td>${s.film.title}</td>
          <td>${s.salle.name}</td>
          <td>${s.startTime}</td>
          <td>
            <a href="${pageContext.request.contextPath}/app/seance?id=${s.id}"
              >Réserver</a
            >
          </td>
        </tr>
      </c:forEach>
    </table>
    <form method="post" action="${pageContext.request.contextPath}/logout">
      <button type="submit">Déconnexion</button>
    </form>
  </body>
</html>
