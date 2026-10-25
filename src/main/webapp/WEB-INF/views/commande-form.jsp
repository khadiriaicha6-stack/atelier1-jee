<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jspf" %>
<h2>
    <c:choose>
        <c:when test="${commande.id == 0}">Nouvelle commande</c:when>
        <c:otherwise>Modifier la commande n° ${commande.id}</c:otherwise>
    </c:choose>
</h2>
<form method="post" action="${pageContext.request.contextPath}/commandes">
    <input type="hidden" name="action" value="save">
    <input type="hidden" name="id" value="${commande.id}">

    Client<br>
    <select name="clientId" required>
        <c:forEach var="cl" items="${clients}">
            <option value="${cl.id}" <c:if test="${cl.id == commande.client.id}">selected</c:if>>
                <c:out value="${cl.nom}"/> <c:out value="${cl.prenom}"/>
            </option>
        </c:forEach>
    </select><br>

    Date<br>
    <c:choose>
        <c:when test="${commande.id == 0}">
            <input type="date" name="dateCommande" value="${today}" required>
        </c:when>
        <c:otherwise>
            <input type="date" name="dateCommande" value="${commande.dateCommande}" required>
        </c:otherwise>
    </c:choose>
    <br><br>
    <button class="btn btn-ok" type="submit">Enregistrer</button>
    <a class="btn" href="${pageContext.request.contextPath}/commandes">Annuler</a>
</form>
<%@ include file="footer.jspf" %>