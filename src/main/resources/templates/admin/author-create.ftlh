<!DOCTYPE html>
<#import "../layout/base.ftlh" as base>
<@base.layout title="Создание автора">
<html lang="ru">
<body>
<h2>Внесите данные в поля</h2>
<div style="color:red">${errormessage!}</div>
<form action="${currentContext}/admin/create-author" method="post">
    <p>
        <label>Имя</label>
        <input type="text" name="nickname" value="" required>
    </p>
    <p>
        <label>Описание</label>
        <textarea type="text" name="description" required rows="5" cols="40"></textarea>
    </p>
    <input type="submit" value="Cоздать">
</form>
</body>
</html>
</@base.layout>