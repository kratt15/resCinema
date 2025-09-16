<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Connexion</title>
  </head>
  <body>
    <h2>Connexion</h2>
    <c:if test="${not empty error}"
      ><div style="color: red">${error}</div></c:if
    >
    <form method="post">
      <label>Email: <input name="email" type="email" required /></label><br />
      <label
        >Mot de passe: <input name="password" type="password" required /></label
      ><br />
      <button type="submit">Se connecter</button>
    </form>
    <a href="${pageContext.request.contextPath}/register">Créer un compte</a>
  </body>
</html>
