<!DOCTYPE html>
<#import "../layout/base.ftlh" as base>
<@base.layout title="Изменение жанра">
    <html lang="ru">
    <body>
    <h2>Измените данные в полях</h2>
    <div style="color:red">${errormessage!}</div>
    <form action="${currentContext}/admin/edit-genre" method="post">
        <p>
            <label>Название</label>
            <input type="text" name="name" value="${genre.name}" required>
        </p>
        <p>
            <label>Описание</label>
            <textarea id="text" name="description" rows="5" cols="40" required>${genre.description}</textarea>
        </p>
        <input type="submit" value="Изменить">
        <input type="hidden" name="id" value="${genre.id}">
    </form>
    <form action="${currentContext}/admin/edit-genre" method="post">
        <p>
            <input type="hidden" name="id" value="${genre.id}">
            <input type="hidden" name="delete" value="true">
            <input type="submit" value="Удалить">
        </p>
    </form>
    </body>
    </html>
</@base.layout>