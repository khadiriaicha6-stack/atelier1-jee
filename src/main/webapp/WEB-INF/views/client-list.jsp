<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jspf" %>
<h2>Liste des clients</h2>
<a class="btn btn-ok" href="${pageContext.request.contextPath}/clients?action=new">+ Nouveau client</a>
<table>
    <tr><th>ID</th><th>Nom</th><th>Prénom</th><th>Email</th><th>Téléphone</th><th>Adresse</th><th>Actions</th></tr>
    <c:forEach var="c" items="${clients}">
        <tr>
            <td>${c.id}</td>
            <td><c:out value="${c.nom}"/></td>
            <td><c:out value="${c.prenom}"/></td>
            <td><c:out value="${c.email}"/></td>
            <td><c:out value="${c.telephone}"/></td>
            <td><c:out value="${c.adresse}"/></td>
            <td>
                <a class="btn" href="${pageContext.request.contextPath}/clients?action=edit&id=${c.id}">Modifier</a>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/clients?action=delete&id=${c.id}"
                   onclick="return confirm('Supprimer ce client ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty clients}"><tr><td colspan="7">Aucun client.</td></tr></c:if>
</table>
<%@ include file="footer.jspf" %>