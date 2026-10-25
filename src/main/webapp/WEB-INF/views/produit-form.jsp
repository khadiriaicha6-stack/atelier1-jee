<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jspf" %>
<h2>
    <c:choose>
        <c:when test="${produit.id == 0}">Nouveau produit</c:when>
        <c:otherwise>Modifier le produit</c:otherwise>
    </c:choose>
</h2>
<form method="post" action="${pageContext.request.contextPath}/produits">
    <input type="hidden" name="id" value="${produit.id}">
    Désignation<br><input type="text" name="designation" value="<c:out value='${produit.designation}'/>" required><br>
    Prix (DH)<br><input type="number" step="0.01" min="0" name="prix" value="${produit.prix}" required><br>
    Quantité en stock<br><input type="number" min="0" name="quantiteStock" value="${produit.quantiteStock}" required><br><br>
    <button class="btn btn-ok" type="submit">Enregistrer</button>
    <a class="btn" href="${pageContext.request.contextPath}/produits">Annuler</a>
</form>
<%@ include file="footer.jspf" %>