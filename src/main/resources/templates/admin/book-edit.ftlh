<#import "../layout/base.ftlh" as base>
<@base.layout title="Изменение книги">
    <h2>Изменение книги</h2>

    <#if error??>
        <div style="color: red;">${error}</div>
    </#if>

    <form method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${book.id}">
        <p>
            <label for="title">Название книги:</label><br>
            <input type="text" id="title" name="title" value="${book.title}" required>
        </p>

        <p>
            <label for="description">Описание:</label><br>
            <textarea id="description" name="description" rows="5" cols="40" required>${book.description}</textarea>
        </p>

        <p>
            <label for="genre">Жанр:</label><br>
            <select id="genre" name="genre" required>
                <#list genres as genre>
                    <option value="${genre.id}">${genre.name}</option>
                </#list>
            </select>
        </p>

        <p>
            <label for="file">Файл книги (PDF, EPUB, FB2, MOBI, DOCX, TXT):</label><br>
            <input type="file" id="file" name="file"
                   accept=".pdf,.epub,.fb2,.mobi,.docx,.txt">
        </p>

        <div style="margin-top: 10px;">
            <button type="submit" class="usual-button">Обновить</button>
        </div>
        <p>
    </form>
    <form action="${currentContext}/admin/edit-book" method="post">
        <p>
            <input type="hidden" name="id" value="${book.id}">
            <input type="hidden" name="delete" value="true">
            <input type="submit" value="Удалить">
        </p>
    </form>
    </p>
</@base.layout>