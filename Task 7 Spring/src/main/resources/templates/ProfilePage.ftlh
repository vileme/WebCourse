<#import "./common.ftlh" as c>

<@c.page>
<div class="datatable">

    <#if userInfo??>
    <div class="caption">Profile</div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Created At</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${userInfo.id}</td>
            <td>${userInfo.login}</td>
            <td>${userInfo.creationTime}</td>
        </tr>
        </tbody>
    </table>
    <#else>
            <div class="body">
                No such user
            </div>
</div>
    </#if>
</@c.page>
