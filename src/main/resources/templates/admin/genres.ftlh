<#import "../layout/base.ftlh" as base>
<@base.layout title="Все жанры">
    <h2>Список всех жанров</h2>
    <#if genres?size == 0>
        <p>Жанров пока нет.</p>
    <#else>
        <ul>
            <#list genres as genre>
                <li>
                    <form method="get" action="${currentContext}/admin/edit-genre" style="display:inline;">
                        <input type="hidden" name="id" value="${genre.id}">
                        <button type="submit" class="link-button">${genre.name}</button>
                    </form>
                    — ${genre.description!}
                </li>
            </#list>
        </ul>
    </#if>

    <p style="margin-top: 15px;">
        <a href="${currentContext}/admin/create-genre">Создать новый жанр</a> |
        <a href="${currentContext}/admin">Назад в админ-панель</a>
    </p>
</@base.layout>