<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Каталог книг</title>
    </head>
    <body>
        <h1>Каталог книг</h1>
        <ul>
            <jsp:useBean id="books" scope="request" type="java.util.List"/>
            <c:forEach var="book" items="${books}">
                <li>
                    <a href="${pageContext.request.contextPath}/comments?bookId=${book.id}">
                            ${book.title} — ${book.author} (${book.pubYear})
                    </a>
                </li>
            </c:forEach>
        </ul>

        <h3>Додати книгу</h3>
        <form method="post" action="${pageContext.request.contextPath}/books">
            Назва: <input type="text" name="title"><br>
            Автор: <input type="text" name="author"><br>
            Дата публікації: <input type="number" name="pubYear"><br>
            <button type="submit">Додати</button>
        </form>
    </body>
</html>