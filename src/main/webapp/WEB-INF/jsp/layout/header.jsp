<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${empty pageTitle ? 'resCinema' : pageTitle}"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        body { background-color:#f8f9fa; }
        .navbar-brand { font-weight:600; }
        .seat-grid td { text-align:center; vertical-align:middle; }
        .sidebar {
            min-height: calc(100vh - 76px);
            background-color: #fff;
            border-right: 1px solid #dee2e6;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
        .sidebar .nav-link {
            color: #495057;
            border-radius: 0.375rem;
            margin: 0.125rem 0;
        }
        .sidebar .nav-link:hover {
            background-color: #e9ecef;
        }
        .sidebar .nav-link.active {
            background-color: #0d6efd;
            color: white;
        }
        .main-content {
            min-height: calc(100vh - 76px);
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom shadow-sm">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">resCinema</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarAuth" aria-controls="navbarAuth" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarAuth">
      <div class="navbar-nav ms-auto">
        <c:choose>
            <c:when test="${empty sessionScope.userId}">
                <a class="nav-link" href="${pageContext.request.contextPath}/login">Se connecter</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/register">S'inscrire</a>
            </c:when>
            <c:otherwise>
                <form method="post" action="${pageContext.request.contextPath}/logout" class="d-flex">
                    <button class="btn btn-outline-danger" type="submit">Déconnexion</button>
                </form>
            </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</nav>

<div class="container-fluid">
  <div class="row">
    <c:if test="${not empty sessionScope.userId}">
      <nav class="col-md-3 col-lg-2 d-md-block sidebar collapse">
        <div class="position-sticky pt-3">
          <ul class="nav flex-column">
            <li class="nav-item">
              <a class="nav-link ${pageTitle == 'Séances' ? 'active' : ''}" href="${pageContext.request.contextPath}/app/home">
                <i class="bi bi-film"></i> Séances
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link ${pageTitle == 'Mes réservations' ? 'active' : ''}" href="${pageContext.request.contextPath}/app/mes-reservations">
                <i class="bi bi-ticket-perforated"></i> Mes réservations
              </a>
            </li>
            <c:if test="${sessionScope.role eq 'ADMIN'}">
              <hr>
              <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                <span>Administration</span>
              </h6>
              <li class="nav-item">
                <a class="nav-link ${pageTitle == 'Admin - Films' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/films">
                  <i class="bi bi-collection-play"></i> Films
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link ${pageTitle == 'Admin - Salles' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/salles">
                  <i class="bi bi-building"></i> Salles
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link ${pageTitle == 'Admin - Séances' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/seances">
                  <i class="bi bi-calendar-event"></i> Séances
                </a>
              </li>
            </c:if>
          </ul>
        </div>
      </nav>
    </c:if>
    
    <c:set var="mainColClass" value="${empty sessionScope.userId ? 'col-12' : 'col-md-9 ms-sm-auto col-lg-10'}" />
    <main class="${mainColClass} px-md-4 main-content">
      <div class="py-4">