<#macro csrf>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</#macro>

<#macro fieldError field>
    <#if field?? && field?has_content>
        <span class="field-error">${field}</span>
    </#if>
</#macro>

<#macro submitButton text="Сохранить" className="usual-button">
    <button type="submit" class="${className}" data-submit-btn>${text}</button>
</#macro>

<#macro flashMessages>
    <#if success??>
        <div class="alert success">${success}</div>
    </#if>
    <#if error??>
        <div class="alert error">${error}</div>
    </#if>
</#macro>