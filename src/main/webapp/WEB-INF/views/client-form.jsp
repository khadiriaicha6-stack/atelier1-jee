<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jspf" %>
<h2>
    <c:choose>
        <c:when test="${client.id == 0}">Nouveau client</c:when>
        <c:otherwise>Modifier le client</c:otherwise>
    </c:choose>
</h2>
<form method="post" action="${pageContext.request.contextPath}/clients">
    <input type="hidden" name="id" value="${client.id}">
    Nom<br><input type="text" name="nom" value="<c:out value='${client.nom}'/>" required><br>
    Prénom<br><input type="text" name="prenom" value="<c:out value='${client.prenom}'/>" required><br>
    Email<br><input type="email" name="email" value="<c:out value='${client.email}'/>"><br>
    Téléphone<br><input type="text" name="telephone" value="<c:out value='${client.telephone}'/>"><br>
    Adresse<br><input type="text" name="adresse" value="<c:out value='${client.adresse}'/>"><br><br>
    <button class="btn btn-ok" type="submit">Enregistrer</button>
    <a class="btn" href="${pageContext.request.contextPath}/clients">Annuler</a>
</form>
<%@ include file="footer.jspf" %>