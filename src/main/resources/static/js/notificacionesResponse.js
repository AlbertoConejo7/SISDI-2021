$(document).ready(function () {
    doAjaxRes("oficiosRes");
});
function doAjaxRes(dir) {
    var data = "index";
    $.ajax({
        url: "/notificationResponse",
        type: "POST",
        data: data,
        dataType: "json",
        success: function (res) {
            if (res.length == 0 && dir!="pageMessages") {
                $('#oficiosRes').prepend($("<span />", {
                    html: "No hay Oficios sin responder"
                }));
            } else {
                for (var i = 0; i < res.length; i++) {
                    verifiedDate(res[i], dir);
                }
            }
        },
        error: function (err) {
            console.error(err);
        }
    });
}
function verifiedDate(obj, dir) {
    var day = new Date(obj.Deadline + "T00:00");
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    if (day < today) {
        createAlert(obj, "danger", dir);
    } else {
        createAlert(obj, "info", dir);
    }

}
function createAlert(obj, type, dir) {

    var alertClasses = "alert alert-" + type;
    var strong = "";

    if (type == "danger") {
        strong = "La fecha límite de respuesta ya venció, debe responderlo pronto";
    } else {
        strong = "La fecha límite de respuesta es: " + obj.Deadline;
    }

    var msg = $("<div />", {
        "class": alertClasses
    });

    $("<h6 />", {
        html: obj.Offnumber
    }).appendTo(msg);

    $("<strong />", {
        html: strong + "<br/>"
    }).appendTo(msg);

    $("<a />", {
        name: "link",
        href: "/offices/pendingOffice",
        text: "Oficios Recibidos"
    }).appendTo(msg);

    $('#'+dir).prepend(msg);
}
