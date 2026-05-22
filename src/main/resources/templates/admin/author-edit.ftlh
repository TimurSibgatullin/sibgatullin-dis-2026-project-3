<!DOCTYPE html>
<#import "../layout/base.ftlh" as base>
<@base.layout title="Профиль автора">
    <html lang="ru">
    <body>
    <h2>Вы зашли в профиль автора: ${author.nickname}</h2>
    <h3>Вы можете изменить данные в полях:</h3>
    <div style="color:red">${errormessage!}</div>
    <form action="${currentContext}/admin/edit-author" method="post">
        <p>
            <label>Имя</label>
            <input type="text" name="nickname" value="${author.nickname}" required>
        </p>
        <p>
            <label>О авторе</label>
            <textarea type="text" name="description" required rows="5" cols="40">${author.description!}</textarea>
        </p>
        <input type="submit" value="Изменить">
        <input type="hidden" name="id" value="${author.id}">
    </form>
    <form action="${currentContext}/admin/edit-author" method="post">
        <p>
            <input type="hidden" name="id" value="${author.id}">
            <input type="hidden" name="delete" value="true">
            <input type="submit" value="Удалить">
        </p>
    </form>
    <h3>Ваши книги:</h3>
    <#if myBooks?size == 0>
        <p>Вы пока не добавили ни одной книги.</p>
    <#else>
        <#list myBooks as book>
            <p>
                <form method="get" action="${currentContext}/admin/edit-book">
                    <input type="hidden" name="id" value="${book.id}" required>
                    <button type="submit" class="usual-button">${book.title}</button>
                </form>
            </p>
        </#list>
    </#if>
    <a href="${currentContext}/admin/upload?id=${author.id}">Добавить новую книгу</a>

    </body>
    </html>
</@base.layout>