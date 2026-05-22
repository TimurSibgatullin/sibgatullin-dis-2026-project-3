<#import "../layout/base.ftlh" as base>
<@base.layout title="Загрузка книги">
    <h2>Загрузка книги автором ${user.nickname}</h2>

    <#if error??>
        <div style="color: red;">${error}</div>
    </#if>

    <form method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${user.id}">
        <p>
            <div>
                <label for="title">Название книги:</label><br>
                <input type="text" id="title" name="title" required>
            </div>
        </p>
        <p>
            <div>
                <label for="description">Описание:</label><br>
                <textarea id="description" name="description" rows="5" cols="40" required></textarea>
            </div>
        </p>
        <p>
            <div>
                <label for="genre">Жанр:</label><br>
                <select id="genre" name="genre" required>
                    <#list genres as genre>
                        <option value="${genre.id}">${genre.name}</option>
                    </#list>
                </select>
            </div>
        </p>
        <p>
            <div>
                <label for="file">Файл книги (PDF, EPUB, FB2, MOBI, DOCX, TXT):</label><br>
                <input type="file" id="file" name="file"
                       accept=".pdf,.epub,.fb2,.mobi,.docx,.txt"
                       required>
            </div>
        </p>
        <div style="margin-top: 10px;">
            <button type="submit" class="usual-button">Загрузить</button>
        </div>
    </form>
</@base.layout>