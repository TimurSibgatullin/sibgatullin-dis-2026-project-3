<#import "layout/base.ftlh" as base>
<@base.layout title="Главная страница">
    <link rel="stylesheet" href="${currentContext}/static/css/style.css">

    <div class="container">

        <#if user??>
            <h2>Добро пожаловать, ${nickname!}.</h2>
        </#if>

        <h2>Выберите жанр, чтобы начать читать:</h2>
        <ul>
            <#list genres as genre>
                <li>
                    <a href="${currentContext}/books?genreId=${genre.id}">${genre.name}</a><br>
                    <label>Описание: ${genre.description}</label>
                </li>
            </#list>
        </ul>
        <p>
        <h2>Последние книги</h2>
        <ul>
            <#if books?size == 0>
                <li>Пока нет добавленных книг.</li>
            <#else>
                <#list books as book>
                    <li>
                        <a href="${currentContext}/book?id=${book.id}">${book.title}</a> —
                        <a href="${currentContext}/user?id=${book.author.id}">${book.author.nickname}</a>
                    </li>
                </#list>
            </#if>
        </ul>
        </p>
    </div>
</@base.layout>