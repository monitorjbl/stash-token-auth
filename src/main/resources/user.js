var userInit = function () {
    var $ = AJS.$;
    var baseUrl = $("meta[name='application-base-url']").attr("content");

    function populateForm() {
        $.ajax({
            url: baseUrl + "/rest/auth-token/1.0/user",
            dataType: "json",
            success: function (config) {
                $("#token").val(config.token);
            }
        });
    }

    function regenerateToken() {
        $.ajax({
            url: baseUrl + "/rest/auth-token/1.0/user/regenerate-token",
            success: function () {
                window.location.reload();
            }
        });
    }

    populateForm();

    $("#keyGenButton").click(function () {
        if (confirm("Are you sure you want to regenerate this token?"))
            regenerateToken();
    });
};