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
};

$(document).ready(function () {
    $('[data-toggle="popover"]').popover();
});