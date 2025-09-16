<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Inscription</title>
  </head>
  <body>
    <h2>Inscription</h2>
    <c:if test="${not empty error}"
      ><div style="color: red">${error}</div></c:if
    >
    <form method="post">
      <label>Email: <input name="email" type="email" required /></label><br />
      <label
        >Mot de passe: <input name="password" type="password" required /></label
      ><br />
      <button type="submit">Créer un compte</button>
    </form>
    <a href="${pageContext.request.contextPath}/login">Se connecter</a>
  </body>
</html>
