AJS.toInit(function () {
    var $ = AJS.$;
    var baseUrl = $("meta[name='application-base-url']").attr("content");

    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    function populateForm() {
        $.ajax({
            url: baseUrl + "/rest/auth-token-admin/1.0/",
            dataType: "json",
            success: function (config) {
                $("#ttl").attr("value", config.ttl);
                $("#enabled").prop("checked", config.enabled);
            }
        });
    }

    function updateConfig() {
        $.ajax({
            url: baseUrl + "/rest/auth-token-admin/1.0/",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify($('#admin').serializeObject()),
            processData: false
        });
    }

    populateForm();

    $("#admin").submit(function (e) {
        e.preventDefault();
        updateConfig();
    });
});