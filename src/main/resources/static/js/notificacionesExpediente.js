$(document).ready(function () {
    doAjaxExp("expedientesTras");
});
function doAjaxExp(dir) {
    var data = "index";
    $.ajax({
        url: "/notificationExpediente",
        type: "POST",
        data: data,
        dataType: "json",
        success: function (res) {
            console.log(res.length);
            if (res.length == 1 && dir != "pageMessages") {
                if (res[0].Tipo === "Trasladar") {
                    $('#expedientesTras').prepend($("<span />", {
                        html: "No hay Expedientes para trasladar"
                    }));
                } else {
                    $('#expedientesTras').prepend($("<span />", {
                        html: "No hay Expedientes trasladados"
                    }));
                }
            } else {
                if (res[0].Tipo === "Trasladar") {
                    for (var i = 1; i < res.length; i++) {
                        createAlertTras(res[i], dir);
                    }
                }
                if (res[0].Tipo === "Central") {
                    for (var i = 1; i < res.length; i++) {
                        createAlertCent(res[i], dir);
                    }
                }
            }
        },
        error: function (err) {
            console.error(err);
        }
    });
}

function createAlertTras(obj, dir) {

    var alertClasses = "alert alert-info";

    var msg = $("<div/>", {
        "class": alertClasses
    });

    $("<h6 />", {
        html: obj.Filename
    }).appendTo(msg);
    $("<strong />", {
        html: "La fecha de almacenaje para el expediente se venció, por favor realizar el traslado. <br/>"
    }).appendTo(msg);

    $("<a />", {
        name: "link",
        href: "/offices/pendingExpediente",
        text: "Expedientes"
    }).appendTo(msg);


    $('#' + dir).prepend(msg);
}

function createAlertCent(obj, dir) {

    var alertClasses = "alert alert-info";

    var msg = $("<div/>", {
        "class": alertClasses
    });

    $("<h6 />", {
        html: obj.Filename
    }).appendTo(msg);
    $("<strong />", {
        html: "Se realizo el traslado de este Oficio, por que el plazo de almacenaje venció <br/>"
    }).appendTo(msg);

    $("<a />", {
        name: "link",
        href: "/offices/pendingExpediente",
        text: "Expedientes"
    }).appendTo(msg);


    $('#' + dir).prepend(msg);
}
