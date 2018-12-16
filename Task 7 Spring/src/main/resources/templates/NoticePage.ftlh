<#import "/spring.ftl" as spring>
<#import "./common.ftlh" as c>


<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>


<@c.page>
<div class="add-article form-box">
    <div class="header">Add Article</div>
    <div class="body">
        <form method="post" action="/notice" id="article">
            <input type="hidden" name="action" value="article">
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text"></textarea>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Create">
            </div>

                <@error 'noticeForm.text'/>
        </form>
    </div>
</div>
</@c.page>