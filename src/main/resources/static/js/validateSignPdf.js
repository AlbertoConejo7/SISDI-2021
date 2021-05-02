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
    var imgS= $("#imgSingned");
    var imgNS= $("#imgNoSingned");
    var aS= $("#aSingned");
    var aNS= $("#aNoSingned");

    inputFile.val(res.name);
    if (res.signed) {
        inputFile.removeClass("c-false");
        inputFile.addClass("c-true");
        //img
        imgS.removeClass("hide-img");
        imgNS.removeClass("show-img");
        imgS.addClass("show-img");
        imgNS.addClass("hide-img");
        //a
        aS.removeClass("hide-img");
        aNS.removeClass("show-img");
        aS.addClass("show-img");
        aNS.addClass("hide-img");
        
    } else {
        inputFile.removeClass("c-true");
        inputFile.addClass("c-false");
        //img
        imgS.removeClass("show-img");
        imgNS.removeClass("hide-img");
        imgS.addClass("hide-img");
        imgNS.addClass("show-img");
        //a
        aS.removeClass("show-img");
        aNS.removeClass("hide-img");
        aS.addClass("hide-img");
        aNS.addClass("show-img");
    }
}
