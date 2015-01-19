var adminInit = function () {
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
            url: baseUrl + "/rest/auth-token/1.0/admin",
            dataType: "json",
            success: function (config) {
                $("#ttl").attr("value", config.ttl);
                $("#key").attr("value", config.key);
                $("#enabled").prop("checked", config.enabled);
            }
        });
    }

    function generateKey() {
        $.ajax({
            url: baseUrl + "/plugins/servlet/auth-token/keygen",
            dataType: "json",
            success: function (key) {
                $("#key").attr("value", key);
            }
        });
    }

    function updateConfig() {
        console.log("updating config");
        $.ajax({
            url: baseUrl + "/rest/auth-token/1.0/admin",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify($('#admin').serializeObject()),
            processData: false,
            success: function () {
                //window.location.reload();
            }
        });
    }

    populateForm();

    $("#admin").submit(function (e) {
        e.preventDefault();
        updateConfig();
    });

    $("#keyGenButton").click(function (e) {
        e.preventDefault();
        if (confirm("Are you sure you want to regenerate the server key? This will invalidate all active tokens.")) {
            generateKey();
        }
    })
};