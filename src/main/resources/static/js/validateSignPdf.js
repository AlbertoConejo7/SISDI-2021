$(document).ready(function () {
    $("#btnSubmit").click(function (e) {
        e.preventDefault();
        doAjax();
    });
});
$(".readonly").keydown(function (e) {
    e.preventDefault();
});
function doAjax() {
    var form = $('#adjuntofile')[0];
    var data = new FormData(form);
    console.log(data.get("adjunto"));
    $.ajax({
        url: "/singleFileUpload",
        type: "POST",
        data: data,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        dataType: "json",
        success: function (res) {
            console.log(res.name);
            $("#attachedModal .close").click();
            printInfo(res);
        },
        error: function (err) {
            console.error(err);
        }
    });
}
function printInfo(res) {
    var inputFile = $("#inputFile");
    var pSigned = $("#isSigned");
    inputFile.val(res.name);
    if (res.signed) {
        inputFile.removeClass("c-false");
        inputFile.addClass("c-true");
        pSigned.text("El Oficio " + res.name + " se encuentra firmado");
    } else {
        inputFile.removeClass("c-true");
        inputFile.addClass("c-false");
        pSigned.text("El Oficio " + res.name + " no se encuentra firmado");
    }
}