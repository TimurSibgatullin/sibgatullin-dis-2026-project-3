<header>
    <h1>FreeLib</h1>
    <nav>
        <a href="${currentContext}/home">Главная</a>
        <#if user??>
            | <a href="${currentContext}/profile">Профиль</a>
            <#if user.isAuthor>
                | <a href="${currentContext}/upload">Загрузить книгу</a>
            </#if>
            <#if user.isAdmin>
                | <a href="${currentContext}/admin">Админ-панель</a>
            </#if>
            | <a href="${currentContext}/account/settings">Настройки</a>
            | <form action="${currentContext}/auth/logout" method="post" style="display:inline;">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button type="submit" class="link-button">Выход</button>
        </form>
        <#else>
            | <a href="${currentContext}/auth/login">Вход</a>
            | <a href="${currentContext}/auth/register">Регистрация</a>
        </#if>
    </nav>
    <hr>
</header>