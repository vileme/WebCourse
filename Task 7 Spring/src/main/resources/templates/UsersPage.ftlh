<#import "./common.ftlh" as c>

<@c.page>
<div class="datatable">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Created At</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
    <#list users as user>
    <form method="post" action="/users" id="users">
    <tr>
        <td>${user.id}</td>
        <td>${user.login}</td>
        <td>${user.creationTime}</td>
        <td>
            <div class="button-field">
            <#if user.disabled==true>
                <input type="submit" value="Enable">
                <input type="hidden" name="userLogin" value=${user.login}>
            <#else>
                <input type="hidden" name="userLogin" value=${user.login}>
                <input type="submit" value="Disable">
            </#if>
            </div>
        </td>
    </tr>
    </form>
    </#list>
        </tbody>
    </table>
</div>
</@c.page>
