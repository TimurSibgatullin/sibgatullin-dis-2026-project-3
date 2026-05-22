<#import "../layout/base.ftlh" as base>
<@base.layout title="Автор: ${author.nickname}">
    <h2>${author.nickname}</h2>
    <p>${author.bio!}</p>

    <h3>Книги автора</h3>
    <#if authorBooks?size == 0>
        <p>У этого автора пока нет книг.</p>
    <#else>
        <ul>
            <#list authorBooks as book>
                <li><a href="${currentContext}/book?id=${book.id}">${book.title}</a></li>
            </#list>
        </ul>
    </#if>

    <h3>Комментарии автора</h3>
    <#if authorComments?size == 0>
        <p>Автор пока не оставлял комментариев.</p>
    <#else>
        <ul>
            <#list authorComments as c>
                <li>К книге <a href="${currentContext}/book?id=${c.book.id}">${c.book.title}</a>: ${c.text}</li>
            </#list>
        </ul>
    </#if>
</@base.layout>