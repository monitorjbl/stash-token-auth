var adminInit = function () {
    var $ = AJS.$;
    var baseUrl = $("meta[name='application-base-url']").attr("content");

    function populateForm() {
        $.ajax({
            url: baseUrl + "/rest/auth-token/1.0/admin",
            dataType: "json",
            success: function (config) {
                $("#ttl").attr("value", config.ttl);
                $("#key").attr("value", config.key);
                $("#enabled").prop("checked", config.enabled);

                applyPathConfig(config, "admin");
                applyPathConfig(config, "repo");
                applyPathConfig(config, "project");
            }
        });
    }

    function applyPathConfig(config, prefix) {
        var sub = config[prefix + "Paths"];
        $('input[type="checkbox"][name^="' + prefix + '."]').each(function (i, v) {
            if (sub[$(v).attr("name").replace(prefix + ".", "")]) {
                $(v).prop("checked", true);
            }
        });
    }

    function readPathConfig(config, prefix) {
        var sub = config[prefix + "Paths"];
        if (sub == undefined) {
            sub = {};
            config[prefix + "Paths"] = sub;
        }
        console.log(config);
        $('input[type="checkbox"][name^="' + prefix + '."]').each(function (i, v) {
            sub[$(v).attr("name").replace(prefix + ".", "")] = $(v).prop("checked");
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
        var config = {
            ttl: $("#ttl").attr("value"),
            key: $("#key").attr("value"),
            enabled: $("#enabled").prop("checked")
        };
        readPathConfig(config, "admin");
        readPathConfig(config, "project");
        readPathConfig(config, "repo");

        console.log(config);
        $.ajax({
            url: baseUrl + "/rest/auth-token/1.0/admin",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(config),
            processData: false,
            success: function () {
                window.location.reload();
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