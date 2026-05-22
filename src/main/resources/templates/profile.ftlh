<#ftl encoding="UTF-8">
<#import "layout/base.ftlh" as base>
<@base.layout title="Профиль">
    <h2>Здравствуйте, ${user.nickname}!</h2>
    <h3>Книги в избранном:</h3>
    <#if myFavoriteBooks?size == 0>
        <p>Вы пока не добавили ни одной книги в избранное.</p>
    <#else>
        <ul>
            <#list myFavoriteBooks as book>
                <li><a href="${currentContext}/book?id=${book.id}">${book.title}</a></li>
            </#list>
        </ul>
    <p>
    </#if>
    <#if user.role == "author" || user.role == "admin">
        <h3>Ваши книги:</h3>
        <#if myBooks?size == 0>
            <p>Вы пока не добавили ни одной книги.</p>
        <#else>
            <ul>
                <#list myBooks as book>
                    <li><a href="${currentContext}/book?id=${book.id}">${book.title}</a></li>
                </#list>
            </ul>
        </#if>
        <p><a href="${currentContext}/upload">Добавить новую книгу</a></p>
    </#if>
    </p>
    <p>
        <h3>Ваши комментарии:</h3>
        <#if myComments?size == 0>
            <p>Вы пока не оставляли комментариев.</p>
        <#else>
            <ul>
                <#list myComments as comment>
                    <li>Комментарий к: <a href="${currentContext}/book?id=${comment.book.id}">${comment.book.title}</a> от ${comment.created_at} — ${comment.text}</li>
                </#list>
            </ul>
        </#if>
        <form action="${currentContext}/profile-edit" method="get">
            <p>
                <input type="submit" value="Редактировать профиль">
            </p>
        </form>
    </p>

</@base.layout>
