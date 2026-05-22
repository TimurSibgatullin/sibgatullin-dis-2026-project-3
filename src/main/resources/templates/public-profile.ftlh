<#ftl encoding="UTF-8">
<#import "layout/base.ftlh" as base>
<@base.layout title="Пользователь ${anotherUser.nickname}">
    <h2>Профиль пользователя ${anotherUser.nickname}</h2>
    <p><h4>О пользователе:</h4>
    <label>${anotherUser.description}</label></p>
    <#if anotherUser.role == "author" || anotherUser.role == "admin">
        <h3>Книги этого автора:</h3>
        <#if myBooks?size == 0>
            <p>Автор пока не добавил ни одной книги.</p>
        <#else>
            <ul>
                <#list myBooks as book>
                    <li><a href="${currentContext}/book?id=${book.id}">${book.title}</a></li>
                </#list>
            </ul>
        </#if>
    </#if>
    <#if anotherUser.author??>
        <h3>Комментарии пользователя: ${anotherUser.nickname}</h3>
        <#if myComments?size == 0>
            <p>Пользователь пока не оставлял комментариев.</p>
        <#else>
            <ul>
                <#list myComments as comment>
                    <li>Комментарий к: <a href="${currentContext}/book?id=${comment.book.id}">${comment.book.title}</a> от ${comment.created_at} — ${comment.text}</li>
                </#list>
            </ul>
        </#if>
    </#if>
</@base.layout>
