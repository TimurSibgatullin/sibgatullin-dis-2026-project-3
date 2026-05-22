<#import "../layout/base.ftlh" as base>
<@base.layout title="Все жанры">
    <!DOCTYPE html>
    <html lang="ru">
    <h2>Список всех жанров</h2>
    <label>Нажмите чтобы редактировать</label>
    <#if genres?size == 0>
        <p>Жанров пока нет.</p>
    <#else>
        <#list genres as genre>
            <p>
                <form method="get" action="${currentContext}/admin/edit-genre">
                    <input type="hidden" name="id" value="${genre.id}" required>
                    <button type="submit" class="usual-button">${genre.name}</button>
                </form>
            </p>
        </#list>
    </#if>
    </html>
</@base.layout>
