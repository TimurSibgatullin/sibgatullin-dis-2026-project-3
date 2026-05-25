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
        <p><div style="color:green; margin-bottom: 10px;">${success}</div></p>
    </#if>
    <#if error??>
        <p><div style="color:red; margin-bottom: 10px;">${error}</div></p>
    </#if>
</#macro>