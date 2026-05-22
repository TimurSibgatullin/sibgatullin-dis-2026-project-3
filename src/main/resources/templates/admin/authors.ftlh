<#import "../layout/base.ftlh" as base>
<@base.layout title="Все авторы">
    <!DOCTYPE html>
    <html lang="ru">
    <h2>Список всех авторов</h2>
    <label>Нажмите чтобы попасть в профиль</label>
    <#if authors?size == 0>
        <p>Авторов пока нет.</p>
    <#else>
        <#list authors as author>
            <p>
                <form method="get" action="${currentContext}/admin/edit-author">
                    <input type="hidden" name="id" value="${author.id}" required>
                    <button type="submit" class="usual-button">${author.nickname}</button>
                </form>
            </p>
        </#list>
    </#if>
    </html>
</@base.layout>
