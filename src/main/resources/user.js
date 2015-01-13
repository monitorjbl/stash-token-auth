AJS.toInit(function () {
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

    populateForm();
});