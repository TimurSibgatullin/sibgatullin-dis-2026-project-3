<#ftl encoding="UTF-8">
<#import "../layout/base.ftlh" as base>
<@base.layout title="Регистрация">
<!DOCTYPE html>
<html lang="ru">
<body>
<h2>Регистрация</h2>
<div style="color:red">${errormessage!}</div>
<form action="${currentContext}/auth/register" method="post">
    <p>
        <div>
            <label>Введите логин</label>
            <input type="text" name="login" required>
        </div>
    </p>
    <p>
        <div>
            <label>Введите пароль</label >
            <input type="password" name="password" required>
        </div>
    </p>
    <p>
        <div>
            <label>Повторите пароль</label>
            <input type="password" name="passwordConfirm" required>
        </div>
    </p>
    <p>
        <div>
            <label>Выберите роль</label>
            <select id="role" name="role">
                <option value="reader" selected>Читатель</option>
                <option value="author">Автор</option>
            </select>
        </div>
    </p>
    <p>
    <div>
        <label>Введите никнейм</label>
        <input type="text" name="nickname" required>
    </div>
    </p>
    <p>
        <label>Расскажите о себе</label>
        <textarea type="text" name="description" rows="5" cols="40"></textarea>
    </p>
    <input type="submit" value="Зарегистрироваться">
</form>
</body>
</html>
</@base.layout>