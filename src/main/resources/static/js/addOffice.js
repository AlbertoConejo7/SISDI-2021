$(document).ready(function () {
    $('[data-toggle="popover"]').popover();
    $("#btnSaveFiles").click(function (e) {
        e.preventDefault();
        saveFiles();
        $("#loadingDocs").addClass("show-img");
        $("#loadingDocs").removeClass("hide-img");
        $("#btnSaveFiles").prop("disabled", true);
    });
});


function saveResponseTime() {
    var limite = document.getElementById("responseTimeValue");
    var dateLimit = document.getElementById("dateLimit");
    var error = document.getElementById("errorResponseTime");

    if (limite.value == "") {
        error.innerText = "Campo requerido";
        error.classList.add("show-message");
        error.classList.remove("hide-message");
        limite.classList.add("error-input");
    } else {
        error.innerText = "Error";
        $("#responseTime .close").click();
        error.classList.remove("show-message");
        error.classList.add("hide-message");
        limite.classList.remove("error-input");
        showAlert();
        dateLimit.value = limite.value;
    }
}

function mostrarResultados(datos) {
    console.log(datos);
}

function showAlert() {
    var alertSave = $("#alert-save");
    alertSave.addClass("show");
    alertSave.removeClass("fade");
    window.setTimeout(function () {
        alertSave.addClass("fade");
        alertSave.removeClass("show");
    }, 3000);
}
;

function saveFiles() {
    var form = $('#adjuntofiles')[0];
    var message = $('#messageErrorFiles');
    var data = new FormData(form);
    console.log(data.get("adjunto2"));
    $.ajax({
        url: "/filesUpload",
        type: "POST",
        data: data,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        dataType: "json",
        success: function (res) {
            console.log(res);
            printInfoOthers(res);
            message.removeClass("show-img");
            message.addClass("hide-img");
        },
        error: function (err) {
           console.error(err);
           $("#btnSaveFiles").prop("disabled", false);
           $("#loadingDocs").addClass("hide-img");
           $("#loadingDocs").removeClass("show-img");
           message.removeClass("hide-img");
           message.addClass("show-img");
        }
    });
}
function printInfoOthers(res) {
    var allDocs="";
    for(var i=0; i<res.length;i++){
        allDocs+=res[i].name+", ";
        console.log(res[i].name);
    }
    $("#attachedOtherModal .close").click();
     $("#btnSaveFiles").prop("disabled", false);
     $("#loadingDocs").addClass("hide-img");
     $("#loadingDocs").removeClass("show-img");
    var otherFiles = $("#otherFiles");
    otherFiles.val(allDocs);
}
function departamentosFunction(select){
    generarTitulo(select);
    listFuncionarios(select);
}
function generarTitulo(select) {
    var receptor = select.value;
    var emisor = $("#emisorField").val();
    $.ajax({
        type: "POST",
        url: '/offNumber',
        data: {"emisor": emisor, "receptor": receptor},
        dataType: 'json',
        success: function (res) {
            $("#offnumber").val(res.Offnumber);
        },
        error: function (err) {
            console.error(err);
        }
    });
    
}
function listFuncionarios(select) {
    var name = select.value;
    $('#userPorDep').find('option').remove().end();
    $.ajax({
        type: "POST",
        url: '/userDepartment',
        data: {"name": name},
        dataType: 'json',
        success: function (res) {
            generateSelect(res);
        },
        error: function (err) {
            console.error(err);
        }
    });
}
function generateSelect(res) {
    var o = $('<option/>');
    o.attr({'value': ""}).text("Seleccione Receptor");
    $('#userPorDep').append(o);

    for (var i = 0; i < res.length; i++) {
        var option = $('<option/>');
        option.attr({'value': res[i].Name}).text(res[i].Name);
        $('#userPorDep').append(option);
    }
    ;
}