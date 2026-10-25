<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jspf" %>
<h2>Liste des produits</h2>
<a class="btn btn-ok" href="${pageContext.request.contextPath}/produits?action=new">+ Nouveau produit</a>
<table>
    <tr><th>ID</th><th>Désignation</th><th>Prix (DH)</th><th>Stock</th><th>Actions</th></tr>
    <c:forEach var="p" items="${produits}">
        <tr>
            <td>${p.id}</td>
            <td><c:out value="${p.designation}"/></td>
            <td><fmt:formatNumber value="${p.prix}" minFractionDigits="2"/></td>
            <td>${p.quantiteStock}</td>
            <td>
                <a class="btn" href="${pageContext.request.contextPath}/produits?action=edit&id=${p.id}">Modifier</a>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/produits?action=delete&id=${p.id}"
                   onclick="return confirm('Supprimer ce produit ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty produits}"><tr><td colspan="5">Aucun produit.</td></tr></c:if>
</table>
<%@ include file="footer.jspf" %>