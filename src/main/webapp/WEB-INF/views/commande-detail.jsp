<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jspf" %>
<h2>Commande n° ${commande.id}</h2>
<p>
    <b>Client :</b> <c:out value="${commande.client.nom}"/> <c:out value="${commande.client.prenom}"/><br>
    <b>Date :</b> <fmt:formatDate value="${commande.dateCommande}" pattern="dd/MM/yyyy"/>
</p>

<h3>Lignes de la commande</h3>
<table>
    <tr><th>Produit</th><th>Prix unitaire (DH)</th><th>Quantité</th><th>Total (DH)</th><th>Action</th></tr>
    <c:forEach var="l" items="${commande.lignes}">
        <tr>
            <td><c:out value="${l.produit.designation}"/></td>
            <td><fmt:formatNumber value="${l.prixUnitaire}" minFractionDigits="2"/></td>
            <td>${l.quantite}</td>
            <td><fmt:formatNumber value="${l.total}" minFractionDigits="2"/></td>
            <td>
                <a class="btn btn-danger"
                   href="${pageContext.request.contextPath}/commandes?action=deleteLine&id=${commande.id}&ligneId=${l.id}"
                   onclick="return confirm('Supprimer cette ligne ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty commande.lignes}"><tr><td colspan="5">Aucune ligne.</td></tr></c:if>
    <tr>
        <td colspan="3" class="total">TOTAL</td>
        <td colspan="2"><b><fmt:formatNumber value="${commande.total}" minFractionDigits="2"/> DH</b></td>
    </tr>
</table>

<h3>Ajouter une ligne</h3>
<form method="post" action="${pageContext.request.contextPath}/commandes">
    <input type="hidden" name="action" value="addLine">
    <input type="hidden" name="commandeId" value="${commande.id}">
    Produit<br>
    <select name="produitId" required>
        <c:forEach var="p" items="${produits}">
            <option value="${p.id}">
                <c:out value="${p.designation}"/> (<fmt:formatNumber value="${p.prix}" minFractionDigits="2"/> DH)
            </option>
        </c:forEach>
    </select><br>
    Quantité<br>
    <input type="number" name="quantite" min="1" value="1" required><br><br>
    <button class="btn btn-ok" type="submit">Ajouter la ligne</button>
    <a class="btn" href="${pageContext.request.contextPath}/commandes">Retour à la liste</a>
</form>
<%@ include file="footer.jspf" %>