<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jspf" %>
<h2>Liste des commandes</h2>
<a class="btn btn-ok" href="${pageContext.request.contextPath}/commandes?action=new">+ Nouvelle commande</a>
<table>
    <tr><th>N°</th><th>Date</th><th>Client</th><th>Lignes</th><th>Total (DH)</th><th>Actions</th></tr>
    <c:forEach var="cmd" items="${commandes}">
        <tr>
            <td>${cmd.id}</td>
            <td><fmt:formatDate value="${cmd.dateCommande}" pattern="dd/MM/yyyy"/></td>
            <td><c:out value="${cmd.client.nom}"/> <c:out value="${cmd.client.prenom}"/></td>
            <td>${cmd.lignes.size()}</td>
            <td><fmt:formatNumber value="${cmd.total}" minFractionDigits="2"/></td>
            <td>
                <a class="btn" href="${pageContext.request.contextPath}/commandes?action=view&id=${cmd.id}">Détail</a>
                <a class="btn" href="${pageContext.request.contextPath}/commandes?action=edit&id=${cmd.id}">Modifier</a>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/commandes?action=delete&id=${cmd.id}"
                   onclick="return confirm('Supprimer cette commande et ses lignes ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty commandes}"><tr><td colspan="6">Aucune commande.</td></tr></c:if>
</table>
<%@ include file="footer.jspf" %>